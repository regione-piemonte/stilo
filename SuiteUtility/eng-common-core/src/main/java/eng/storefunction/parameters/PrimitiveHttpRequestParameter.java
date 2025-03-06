// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction.parameters;

import javax.servlet.http.HttpServletRequest;
import eng.storefunction.HttpRequestParameter;
import eng.storefunction.StoreParameter;

public class PrimitiveHttpRequestParameter extends StoreParameter implements HttpRequestParameter
{
    public PrimitiveHttpRequestParameter(final int index, final String name) {
        super(index, name, null, 12, 1);
    }
    
    public PrimitiveHttpRequestParameter(final int index, final String name, final int inout) {
        super(index, name, null, 12, inout);
    }
    
    @Override
    public void setHttpRequest(final HttpServletRequest request) {
        if (request == null || this.name == null) {
            return;
        }
        final Object val = request.getParameter(this.name);
        if (val != null) {
            this.setValue(val);
        }
    }
    
    @Override
    public StoreParameter cloneMe() {
        return new PrimitiveHttpRequestParameter(this.index, this.name, this.inOut);
    }
}
