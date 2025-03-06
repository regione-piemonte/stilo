// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction.parameters;

import eng.util.GetProperties;
import javax.servlet.http.HttpSession;
import eng.storefunction.HttpSessionParameter;
import eng.storefunction.StoreParameter;

public class EnteCodeParameter extends StoreParameter implements HttpSessionParameter
{
    public EnteCodeParameter(final int index) {
        super(index, null, null, 12, 2);
    }
    
    @Override
    public void setHttpSession(final HttpSession session) {
        final Object obj = GetProperties.getUserProperty("UP_ID_ENTE", session);
        if (obj != null) {
            this.setValue(obj);
        }
    }
    
    @Override
    public StoreParameter cloneMe() {
        return new EnteCodeParameter(this.index);
    }
}
