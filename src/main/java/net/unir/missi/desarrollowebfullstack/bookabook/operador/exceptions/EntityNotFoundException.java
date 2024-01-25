package net.unir.missi.desarrollowebfullstack.bookabook.operador.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String errorMessage, Throwable err)
    {
        super(errorMessage, err);
    }
}
