// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction.parameters;

import eng.storefunction.StoreParameter;

public class AutoCommitParameter extends StoreParameter
{
    public AutoCommitParameter(final int index, final String commit_type) {
        super(index, "FlgAutoCommitIn", commit_type, 12, 1);
    }
    
    @Override
    public StoreParameter cloneMe() {
        return new AutoCommitParameter(this.index, this.getValue() + "");
    }
}
