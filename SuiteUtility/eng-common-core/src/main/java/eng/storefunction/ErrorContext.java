// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import eng.database.exception.EngException;

public class ErrorContext extends HttpPrimitive
{
    private static final String NAME_DEFAULT = "ErrorContext";
    
    public ErrorContext(final int position) throws EngException {
        this(position, "ErrorContext");
    }
    
    public ErrorContext(final int position, final String name) throws EngException {
        super(position, 12, (name == null) ? "ErrorContext" : name);
        this.setModeOut();
    }
    
    @Override
    public void makeValue(final Object o) throws EngException {
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("(ErrorContext:");
        sb.append(super.toString());
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public Parameter cloneMe() throws EngException {
        final ErrorContext p = new ErrorContext(this.getPosition(), this.getName());
        if (this.isModeIn()) {
            p.setModeIn();
        }
        else if (this.isModeOut()) {
            p.setModeOut();
        }
        else if (this.isModeInOut()) {
            p.setModeInOut();
        }
        return p;
    }
}
