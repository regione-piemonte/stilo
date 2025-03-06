// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction.parameters;

import eng.storefunction.StoreParameter;

public class ErrorContextParameter extends StoreParameter
{
    public ErrorContextParameter(final int index) {
        super(index, "ErrorContext", null, 12, 2);
    }
    
    @Override
    public StoreParameter cloneMe() {
        return new ErrorContextParameter(this.index);
    }
}
