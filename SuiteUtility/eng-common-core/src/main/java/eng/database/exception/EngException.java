// 
// Decompiled by Procyon v0.5.36
// 

package eng.database.exception;

import java.util.Date;

public class EngException extends Exception
{
    public static final int NO_APPLICATION_ERR = 50000;
    public static final int NO_VALID_FORM = 60000;
    protected int codError;
    
    public EngException(final int codError) {
        this.codError = codError;
    }
    
    public EngException(final String message) {
        super(message);
        System.err.println(new Date().toString() + message);
    }
    
    public int getErrorCod() {
        return this.codError;
    }
}
