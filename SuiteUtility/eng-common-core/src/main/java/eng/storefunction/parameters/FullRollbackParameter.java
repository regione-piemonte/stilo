// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction.parameters;

import eng.storefunction.StoreParameter;

public class FullRollbackParameter extends StoreParameter
{
    public FullRollbackParameter(final int index, final String rollback_type) {
        super(index, "FlgRollBckFullIn", "" + rollback_type, 12, 1);
    }
    
    @Override
    public StoreParameter cloneMe() {
        return new FullRollbackParameter(this.index, this.getValue() + "");
    }
}
