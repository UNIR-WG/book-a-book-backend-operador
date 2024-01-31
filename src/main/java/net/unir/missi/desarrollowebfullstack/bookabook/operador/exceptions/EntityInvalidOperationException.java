package net.unir.missi.desarrollowebfullstack.bookabook.operador.exceptions;

import jakarta.ws.rs.NotAllowedException;

public class EntityInvalidOperationException  extends RuntimeException {
    public EntityInvalidOperationException(String errorMessage, Throwable err) { super(errorMessage, err); }
}
