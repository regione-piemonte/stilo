// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import eng.database.exception.EngException;
import it.eng.auriga.repository2.xml.sezionecache.SezioneCache;
import java.util.Vector;

public abstract class Parameter
{
    public static final int MODE_IN = 1;
    public static final int MODE_OUT = 2;
    public static final int MODE_INOUT = 3;
    private static final int TYPE_ENGINEERING_ARRAY = 22222;
    private static final String ATTRIBUTE_SUFFIX = "#attribute";
    private final int position;
    private final int type;
    private final String name;
    private final String extendedName;
    private final boolean required;
    private int mode;
    private String valuePrimitive;
    private Vector valueArray;
    private SezioneCache valueSezioneCache;
    private boolean valueIsNull;
    
    public Parameter(final int position, final int type, final String name, final String extendedName, final boolean required) {
        this.mode = 1;
        this.valuePrimitive = null;
        this.valueArray = null;
        this.valueSezioneCache = null;
        this.valueIsNull = true;
        this.position = position;
        this.type = type;
        this.name = name;
        this.extendedName = extendedName;
        this.required = required;
    }
    
    public Parameter(final int position, final int type, final String name, final String extendedName) {
        this.mode = 1;
        this.valuePrimitive = null;
        this.valueArray = null;
        this.valueSezioneCache = null;
        this.valueIsNull = true;
        this.position = position;
        this.type = type;
        this.name = name;
        this.extendedName = extendedName;
        this.required = false;
    }
    
    public Parameter(final int position, final int type, final String name) {
        this.mode = 1;
        this.valuePrimitive = null;
        this.valueArray = null;
        this.valueSezioneCache = null;
        this.valueIsNull = true;
        this.position = position;
        this.type = type;
        this.name = name;
        this.extendedName = "";
        this.required = false;
    }
    
    public abstract void makeValue(final Object p0) throws EngException;
    
    public String getName() {
        return this.name;
    }
    
    public int getPosition() {
        return this.position;
    }
    
    public int getType() {
        return this.type;
    }
    
    public String getExtendedName() {
        return this.extendedName;
    }
    
    public boolean isRequired() {
        return this.required;
    }
    
    public static int getTypeArray() {
        return 22222;
    }
    
    public String getValuePrimitive() {
        return this.valuePrimitive;
    }
    
    public Vector getValueArray() {
        return this.valueArray;
    }
    
    public boolean isModeIn() {
        return this.mode == 1;
    }
    
    public boolean isModeOut() {
        return this.mode == 2;
    }
    
    public boolean isModeInOut() {
        return this.mode == 3;
    }
    
    public static boolean isNameAttribute(final String name) {
        return name != null && name.endsWith("#attribute");
    }
    
    public boolean isPrimitive() {
        return this.type != 22222 && this.type != 2005;
    }
    
    public boolean isArray() {
        return this.type == 22222;
    }
    
    public boolean isValueNull() {
        return this.valueIsNull;
    }
    
    void setValuePrimitive(String value) {
        if (value == null) {
            value = "";
        }
        this.valuePrimitive = value;
        this.valueIsNull = this.valuePrimitive.equals("");
    }
    
    void setValueArray(final Vector value) {
        this.valueArray = value;
        this.valueIsNull = (this.valueArray.size() == 0);
    }
    
    public Parameter setModeIn() throws EngException {
        this.mode = 1;
        return this;
    }
    
    public Parameter setModeInOut() throws EngException {
        this.mode = 3;
        return this;
    }
    
    public Parameter setModeOut() throws EngException {
        this.mode = 2;
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("(Parameter:");
        sb.append(this.position + "," + this.type + "," + this.mode + "," + this.valueIsNull);
        if (this.valuePrimitive != null) {
            sb.append("," + this.valuePrimitive);
        }
        if (this.valueArray != null) {
            for (int i = 0; i < this.valueArray.size(); ++i) {
                sb.append("\n" + this.valueArray.elementAt(i) + ((i == this.valueArray.size() - 1) ? "\n" : ","));
            }
        }
        return sb.toString();
    }
    
    public abstract Parameter cloneMe() throws EngException;
    
    public SezioneCache getValueSezioneCache() {
        return this.valueSezioneCache;
    }
    
    public void setValueSezioneCache(final SezioneCache valueSezioneCache) {
        this.valueSezioneCache = valueSezioneCache;
        this.valueIsNull = (valueSezioneCache == null);
    }
}
