package net.unir.missi.desarrollowebfullstack.bookabook.operador.service;

import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.clients.BuscadorClient;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.config.LoanMerger;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.config.LoanMergerNonEmpty;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.config.LoanRequestToLoanConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.config.LoanToLoanResponseConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.exceptions.BadParametersException;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.exceptions.EntityInvalidOperationException;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.exceptions.EntityNotFoundException;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.BookResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.ClientResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.api.LoanResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.sql.Loan;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LoanService implements ILoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BuscadorClient buscadorClient;

    @Autowired
    private LoanRequestToLoanConverter loanRequestToLoanConverter;

    @Autowired
    private LoanToLoanResponseConverter loanToLoanResponseConverter;

    @Autowired
    private LoanMerger loanMerger;

    @Autowired
    private LoanMergerNonEmpty loanMergerNonEmpty;

    // CRUD

    @Override
    public List<LoanResponse> getAllLoans(Long bookId, Long clientId, LocalDate loanDate, LocalDate returnDate, LocalDate dueDate, Boolean isReturned, Integer renewalCount) {
        Loan loan = new Loan(null, bookId, clientId, loanDate, returnDate, dueDate, isReturned, renewalCount);
        List<Loan> loanList;
        if (this.isValidSyntaxLoanForNulls(loan)) {
            loanList = loanRepository.search(bookId, clientId, loanDate, returnDate, dueDate, isReturned, renewalCount);
        }
        else {
            loanList = loanRepository.findAll();
        }
        return loanList.stream().map((Loan l) -> this.loanToLoanResponseConverter.convert(l)).collect(Collectors.toList());
    }

    @Override
    public LoanResponse createLoan(LoanRequest request) {
        // Convert to in-memory loan
        Loan loan = this.loanRequestToLoanConverter.convert(request);

        // If loan has null values or wrong values throw 400
        if (!this.isValidSyntaxLoanForNulls(Objects.requireNonNull(loan))
                        || ! this.isValidSyntaxLoanForZeroes(loan))
        {
            throw new BadParametersException("One or more parameters of the request are wrong", null);
        }

        // If loan referencing non-existing books throw 404
        if (! isExistingBook(loan.getBookId().toString()))
        {
            throw new EntityNotFoundException("Book id " + loan.getBookId() + " does not exist.", null);
        }
        // If loan referencing non-existing clients throw 404
        if(! isExistingClient(loan.getClientId().toString()))
        {
            throw new EntityNotFoundException("Client id " + loan.getClientId() + " does not exist.", null);
        }

        List<Loan> foundLoanedBook = loanRepository.findByBookIdAndIsReturned(request.getBookId(), false);
        Logger.getGlobal().warning("Book Count: " + foundLoanedBook.stream().count());

        if((long) foundLoanedBook.size() != 0) {
            Logger.getGlobal().warning("Error: Book " + request.getBookId() + " cannot be loaned because it's already loaned to other client");
            throw new EntityInvalidOperationException("Error: Book " + request.getBookId() + " cannot be loaned because it's already loaned to other client", null);
        }

        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(loan.getLoanDate().plusDays(7));

        // Implicit else: valid loan is saved in the DB
        Loan createdLoan = loanRepository.save(loan);
        return this.loanToLoanResponseConverter.convert(createdLoan);
    }

    @Override
    public LoanResponse getLoanById(Long id) {
        // If loan does not exist throw 404
        Loan loan = loanRepository.findById(id);
        if (loan == null)
        {
            throw new EntityNotFoundException("Loan with id " + id.toString() + " does not exist", null);
        }

        return this.loanToLoanResponseConverter.convert(loan);
    }

    @Override
    public LoanResponse modifyAllLoanData(LoanRequest loanRequest, Long id) {
        Loan loan = this.loanRequestToLoanConverter.convert(loanRequest);

        // If loan has null values or wrong values throw 400
        if (
                ! this.isValidSyntaxLoanForNulls(Objects.requireNonNull(loan))
                        || ! this.isValidSyntaxLoanForZeroes(loan))
        {
            throw new BadParametersException("One or more parameters of the request are wrong", null);
        }

        // If loan does not exist throw 404
        Loan loanMatched = loanRepository.findById(id);
        if (loanMatched == null)
        {
            throw new EntityNotFoundException("Loan with id " + id.toString() + " does not exist", null);
        }

        // If loan referencing non-existing books throw 404
        if (! isExistingBook(loan.getBookId().toString()))
        {
            throw new EntityNotFoundException("Book id " + loan.getBookId() + " does not exist.", null);
        }

        // If loan referencing non-existing clients throw 404
        if(! isExistingClient(loan.getClientId().toString()))
        {
            throw new EntityNotFoundException("Client id " + loan.getClientId() + " does not exist.", null);
        }

        // Update values of matched loan with values of received request
        Loan mergedLoan = this.loanMerger.merge(loanMatched, loan);

        // Save updated loan
        mergedLoan = this.loanRepository.save(mergedLoan);

        // Return response
        return this.loanToLoanResponseConverter.convert(mergedLoan);
    }

    @Override
    public LoanResponse modifyLoan(LoanRequest loanRequest, Long id) {
        Loan loan = this.loanRequestToLoanConverter.convert(loanRequest);

        // If loan has null values or wrong values throw 400
        if (!this.isValidSyntaxLoanForNulls(Objects.requireNonNull(loan))
                        || ! this.isValidSyntaxLoanForZeroes(loan))
        {
            throw new BadParametersException("One or more parameters of the request are wrong", null);
        }
        // If loan does not exist throw 404
        Loan loanMatched = loanRepository.findById(id);
        if (loanMatched == null)
        {
            throw new EntityNotFoundException("Loan with id " + id.toString() + " does not exist", null);
        }

        // If loan referencing non-existing books throw 404
        if (! isExistingBook(loan.getBookId().toString()))
        {
            throw new EntityNotFoundException("Book id " + loan.getBookId() + " does not exist.", null);
        }

        // If loan referencing non-existing clients throw 404
        if(! isExistingClient(loan.getClientId().toString()))
        {
            throw new EntityNotFoundException("Client id " + loan.getClientId() + " does not exist.", null);
        }

        if(loan.getIsReturned()) {
            loan.setReturnDate(LocalDate.now());
        }
        else
        {
            loan.setDueDate(LocalDate.now().plusDays(7));
            loan.setRenewalCount(loan.getRenewalCount() + 1);
        }

        // Update values of matched loan with values of received request
        Loan mergedLoan = this.loanMergerNonEmpty.merge(loanMatched, loan);

        // Save updated loan
        mergedLoan = this.loanRepository.save(mergedLoan);

        // Return response
        return this.loanToLoanResponseConverter.convert(mergedLoan);
    }

    @Override
    public LoanResponse deleteLoan(String id) {
        // If loan does not exist throw 404
        Loan loanMatched = loanRepository.findById(Long.valueOf(id));
        if (loanMatched == null)
        {
            throw new EntityNotFoundException("Loan with id " + id.toString() + " does not exist", null);
        }

        // Create search instance
        Loan l = new Loan();
        l.setId(Long.valueOf(id));

        Loan loan = this.loanRepository.delete(l);
        return this.loanToLoanResponseConverter.convert(loan);
    }


    // SPECIALIZATION

    @Override
    public List<LoanResponse> getLoansByClientId(Long clientId) {
        List<Loan> loansList = loanRepository.findByClientId(clientId);
        return loansList.stream().map((Loan l) -> this.loanToLoanResponseConverter.convert(l)).collect(Collectors.toList());
    }

    // PRIVATE METHODS

    private boolean isExistingBook(String id) {
        try {
            ResponseEntity<BookResponse> book = buscadorClient.getBook(id);
            return book != null;
        }
        catch(Exception e) {
            return false;
        }
    }

    private BookResponse getBook(String id) {
        try {
            ResponseEntity<BookResponse> book = buscadorClient.getBook(id);
            BookResponse response = book.getBody();
            return response;
        }
        catch(Exception e) {
            return null;
        }
    }

    private boolean isExistingClient(String id) {
        try {
            ResponseEntity<ClientResponse> client = buscadorClient.getClient(id);
            return client != null;
        }
        catch(Exception e) {
            return false;
        }
    }

    private boolean isValidSyntaxLoanForNulls(Loan loan)
    {
        return loan.getBookId() != null
                && loan.getClientId() != null
                //&& loan.getReturnDate() != null
                && loan.getIsReturned() != null
                && loan.getRenewalCount() != null;
    }

    private boolean isValidSyntaxLoanForZeroes(Loan loan)
    {
        return loan.getBookId() != 0
                && loan.getClientId() != 0;
    }
}
