// 
// Decompiled by Procyon v0.5.36
// 

package eng.database.exception;

public class EngFormException extends EngException
{
    public EngFormException(final String message) {
        super(message);
        this.codError = 60000;
    }
}
