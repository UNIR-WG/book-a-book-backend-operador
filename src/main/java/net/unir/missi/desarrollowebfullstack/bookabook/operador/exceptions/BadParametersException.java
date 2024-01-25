package net.unir.missi.desarrollowebfullstack.bookabook.operador.exceptions;

public class BadParametersException extends RuntimeException{
    public BadParametersException(String errorMessage, Throwable err)
    {
        super(errorMessage, err);
    }
}
