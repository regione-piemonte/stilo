// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction.parameters;

import eng.storefunction.StoreParameter;

public class ErrorCodeParameter extends StoreParameter
{
    public ErrorCodeParameter(final int index) {
        super(index, "ErrorCode", null, 4, 2);
    }
    
    @Override
    public StoreParameter cloneMe() {
        return new ErrorCodeParameter(this.index);
    }
}
