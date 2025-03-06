// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import eng.database.exception.EngException;

public class HttpStatic extends HttpPrimitive
{
    private static final String NAME_DEFAULT = "Valore fisso";
    private String value;
    
    public HttpStatic(final int position, final int tipo, final String valore) throws EngException {
        super(position, tipo, "Valore fisso");
        this.value = "";
        this.value = valore;
    }
    
    @Override
    public String getValuePrimitive() {
        return this.value;
    }
    
    public void setValuePrimitive(final String value) {
    }
    
    public boolean isSuccess() {
        if (!super.isValueNull()) {
            final int valore = Integer.parseInt(this.getValuePrimitive());
            return valore == 1;
        }
        return false;
    }
    
    @Override
    public void makeValue(final Object o) throws EngException {
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("(Valore fisso:");
        sb.append(super.toString());
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public Parameter cloneMe() throws EngException {
        final HttpStatic p = new HttpStatic(this.getPosition(), this.getType(), this.getValuePrimitive());
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
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
    
    @Override
    public boolean isValueNull() {
        return this.value == null;
    }
}
