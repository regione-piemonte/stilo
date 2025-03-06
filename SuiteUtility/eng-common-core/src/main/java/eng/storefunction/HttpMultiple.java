// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import eng.database.exception.EngException;

public class HttpMultiple extends HttpPrimitive
{
    public HttpMultiple(final int position, final String name) {
        super(position, 12, name);
    }
    
    @Override
    public Parameter cloneMe() throws EngException {
        final HttpMultiple p = new HttpMultiple(this.getPosition(), this.getName());
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
