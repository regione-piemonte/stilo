// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import eng.database.exception.EngException;

public class ReturnCode extends HttpPrimitive
{
    private static final String NAME_DEFAULT = "ReturnCode";
    
    public ReturnCode(final int position) throws EngException {
        this(position, "ReturnCode");
    }
    
    public ReturnCode(final int position, final String name) throws EngException {
        super(position, 4, (name == null) ? "ReturnCode" : name);
        this.setModeOut();
    }
    
    public boolean isSuccess() {
        if (!super.isValueNull()) {
            final int valore = Integer.parseInt(super.getValuePrimitive());
            return valore == 1;
        }
        return false;
    }
    
    @Override
    public void makeValue(final Object o) throws EngException {
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("(ReturnCode:");
        sb.append(super.toString());
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public Parameter cloneMe() throws EngException {
        final ReturnCode p = new ReturnCode(this.getPosition(), this.getName());
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
