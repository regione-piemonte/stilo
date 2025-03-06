// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import eng.database.exception.EngException;
import javax.servlet.http.HttpServletRequest;

public class HttpPrimitive extends Parameter
{
    public HttpPrimitive(final int position, final int type) {
        this(position, type, null);
    }
    
    public HttpPrimitive(final int position, final int type, final String name) {
        super(position, type, name);
    }
    
    public HttpPrimitive(final int position, final int type, final String name, final String extName) {
        super(position, type, name, extName);
    }
    
    public HttpPrimitive(final int position, final int type, final String name, final String extName, final boolean required) {
        super(position, type, name, extName, required);
    }
    
    @Override
    public void makeValue(final Object httpRequest) throws EngException {
        String value = null;
        final String name = this.getName();
        if (((HttpServletRequest)httpRequest).getAttribute(name) != null) {
            value = (String)((HttpServletRequest)httpRequest).getAttribute(name);
        }
        if (value == null) {
            if (Parameter.isNameAttribute(name)) {
                final Object valueAttribute = ((HttpServletRequest)httpRequest).getAttribute(name);
                value = ((valueAttribute instanceof String) ? ((String)valueAttribute) : null);
            }
            else {
                value = ((HttpServletRequest)httpRequest).getParameter(name);
            }
        }
        if (value == null) {
            value = "";
        }
        this.setValuePrimitive(value);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("(HttpPrimitive:");
        sb.append(super.toString() + ",");
        sb.append(this.getName());
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public Parameter cloneMe() throws EngException {
        final HttpPrimitive p = new HttpPrimitive(this.getPosition(), this.getType(), this.getName());
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
