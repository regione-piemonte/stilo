// 
// Decompiled by Procyon v0.5.36
// 

package it.eng.auriga.repository2.xml.outputud.types;

import java.util.Enumeration;
import java.util.Hashtable;
import java.io.Serializable;

public class CategoriaRegType implements Serializable
{
    public static final int PG_TYPE = 0;
    public static final CategoriaRegType PG;
    public static final int PP_TYPE = 1;
    public static final CategoriaRegType PP;
    public static final int R_TYPE = 2;
    public static final CategoriaRegType R;
    public static final int I_TYPE = 3;
    public static final CategoriaRegType I;
    private static Hashtable _memberTable;
    private int type;
    private String stringValue;
    
    private CategoriaRegType(final int type, final String value) {
        this.type = -1;
        this.stringValue = null;
        this.type = type;
        this.stringValue = value;
    }
    
    public static Enumeration enumerate() {
        return CategoriaRegType._memberTable.elements();
    }
    
    public int getType() {
        return this.type;
    }
    
    private static Hashtable init() {
        final Hashtable members = new Hashtable();
        members.put("PG", CategoriaRegType.PG);
        members.put("PP", CategoriaRegType.PP);
        members.put("R", CategoriaRegType.R);
        members.put("I", CategoriaRegType.I);
        return members;
    }
    
    private Object readResolve() {
        return valueOf(this.stringValue);
    }
    
    public String toString() {
        return this.stringValue;
    }
    
    public static CategoriaRegType valueOf(final String string) {
        Object obj = null;
        if (string != null) {
            obj = CategoriaRegType._memberTable.get(string);
        }
        if (obj == null) {
            final String err = "'" + string + "' is not a valid CategoriaRegType";
            throw new IllegalArgumentException(err);
        }
        return (CategoriaRegType)obj;
    }
    
    static {
        PG = new CategoriaRegType(0, "PG");
        PP = new CategoriaRegType(1, "PP");
        R = new CategoriaRegType(2, "R");
        I = new CategoriaRegType(3, "I");
        CategoriaRegType._memberTable = init();
    }
}
