// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction.parameters;

import eng.storefunction.StoreParameter;

public class ErrorMessageParameter extends StoreParameter
{
    public ErrorMessageParameter(final int index) {
        super(index, "ErrorMessage", null, 12, 2);
    }
    
    @Override
    public StoreParameter cloneMe() {
        return new ErrorMessageParameter(this.index);
    }
}
