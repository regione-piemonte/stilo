// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import eng.database.exception.EngException;

public class EnteCode extends HttpPrimitive
{
    public EnteCode(final int position) throws EngException {
        this(position, null);
    }
    
    public EnteCode(final int position, final String name) throws EngException {
        super(position, 4, name);
        this.setModeIn();
    }
    
    @Override
    public void makeValue(final Object userCode) throws EngException {
        this.setValuePrimitive((String)userCode);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("(EnteCode:");
        sb.append(super.toString());
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public Parameter cloneMe() throws EngException {
        final EnteCode p = new EnteCode(this.getPosition(), this.getName());
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
