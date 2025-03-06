// 
// Decompiled by Procyon v0.5.36
// 

package eng.database.exception;

public class EngSqlNoApplException extends EngException
{
    public EngSqlNoApplException(final String message) {
        super(message);
        this.codError = 50000;
    }
}
