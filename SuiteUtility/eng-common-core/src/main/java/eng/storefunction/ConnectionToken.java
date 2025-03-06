// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import eng.database.exception.EngException;

public class ConnectionToken extends HttpPrimitive
{
    public ConnectionToken(final int position) throws EngException {
        this(position, null);
    }
    
    public ConnectionToken(final int position, final String name) throws EngException {
        super(position, 12, name);
        this.setModeIn();
    }
    
    @Override
    public void makeValue(final Object connectionToken) throws EngException {
        this.setValuePrimitive((String)connectionToken);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("(ConnectionToken :");
        sb.append(super.toString());
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public Parameter cloneMe() throws EngException {
        final ConnectionToken p = new ConnectionToken(this.getPosition(), this.getName());
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
