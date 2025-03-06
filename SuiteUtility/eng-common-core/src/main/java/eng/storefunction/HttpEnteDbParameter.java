// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import eng.database.exception.EngException;
import eng.util.GetProperties;
import javax.servlet.http.HttpServletRequest;

public class HttpEnteDbParameter extends HttpPrimitive
{
    private static final String _DEFAULT_NAME = "Valore letto da DB";
    
    public HttpEnteDbParameter(final int position, final int type, final String name, final String extName) {
        super(position, type, (name == null) ? "Valore letto da DB" : name, extName);
    }
    
    public HttpEnteDbParameter(final int position, final int type, final String name, final String extName, final boolean required) {
        super(position, type, (name == null) ? "Valore letto da DB" : name, extName, required);
    }
    
    @Override
    public void makeValue(final Object httpRequest) throws EngException {
        String value = null;
        final String name = this.getName();
        final String extName = this.getExtendedName();
        if (name != null && !"Valore letto da DB".equals(name)) {
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
        }
        if (value == null) {
            value = GetProperties.getEnteProperty(extName, ((HttpServletRequest)httpRequest).getSession());
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
        final HttpEnteDbParameter p = new HttpEnteDbParameter(this.getPosition(), this.getType(), this.getName(), this.getExtendedName());
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
