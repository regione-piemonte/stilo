// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction.parameters;

import eng.storefunction.StoreParameter;

public class ReturnCodeParameter extends StoreParameter
{
    public ReturnCodeParameter(final int index) {
        super(index, null, null, 4, 2);
    }
    
    @Override
    public StoreParameter cloneMe() {
        return new ReturnCodeParameter(this.index);
    }
}
