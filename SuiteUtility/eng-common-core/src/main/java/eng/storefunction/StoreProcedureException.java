// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

public class StoreProcedureException extends Exception
{
    protected int returnCode;
    
    public StoreProcedureException(final int returnCode, final String message) {
        super(message);
        this.returnCode = 0;
        this.returnCode = returnCode;
    }
    
    public int getReturnCode() {
        return this.returnCode;
    }
}
