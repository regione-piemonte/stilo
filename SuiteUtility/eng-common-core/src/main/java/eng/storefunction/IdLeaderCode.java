// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import eng.database.exception.EngException;

public class IdLeaderCode extends HttpPrimitive
{
    public IdLeaderCode(final int position) throws EngException {
        this(position, null);
    }
    
    public IdLeaderCode(final int position, final String name) throws EngException {
        super(position, 4, name);
        this.setModeIn();
    }
    
    @Override
    public void makeValue(final Object IdLeaderCode) throws EngException {
        this.setValuePrimitive((String)IdLeaderCode);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("(IdLeaderCode:");
        sb.append(super.toString());
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public Parameter cloneMe() throws EngException {
        final IdLeaderCode p = new IdLeaderCode(this.getPosition(), this.getName());
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
