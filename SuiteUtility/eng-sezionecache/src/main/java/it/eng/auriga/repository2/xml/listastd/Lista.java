// 
// Decompiled by Procyon v0.5.36
// 

package it.eng.auriga.repository2.xml.listastd;

import org.exolab.castor.xml.Validator;
import org.exolab.castor.xml.Unmarshaller;
import java.io.Reader;
import java.io.IOException;
import org.xml.sax.ContentHandler;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import java.io.Writer;
import org.exolab.castor.xml.ValidationException;
import java.util.Enumeration;
import java.util.Vector;
import java.io.Serializable;

public class Lista implements Serializable
{
    private Vector _rigaList;
    
    public Lista() {
        this._rigaList = new Vector();
    }
    
    public void addRiga(final Riga vRiga) throws IndexOutOfBoundsException {
        this._rigaList.addElement(vRiga);
    }
    
    public void addRiga(final int index, final Riga vRiga) throws IndexOutOfBoundsException {
        this._rigaList.add(index, vRiga);
    }
    
    public Enumeration enumerateRiga() {
        return this._rigaList.elements();
    }
    
    public Riga getRiga(final int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this._rigaList.size()) {
            throw new IndexOutOfBoundsException("getRiga: Index value '" + index + "' not in range [0.." + (this._rigaList.size() - 1) + "]");
        }
        return this._rigaList.get(index);
    }
    
    public Riga[] getRiga() {
        final int size = this._rigaList.size();
        final Riga[] array = new Riga[size];
        for (int index = 0; index < size; ++index) {
            array[index] = this._rigaList.get(index);
        }
        return array;
    }
    
    public int getRigaCount() {
        return this._rigaList.size();
    }
    
    public boolean isValid() {
        try {
            this.validate();
        }
        catch (ValidationException vex) {
            return false;
        }
        return true;
    }
    
    public void marshal(final Writer out) throws MarshalException, ValidationException {
        Marshaller.marshal((Object)this, out);
    }
    
    public void marshal(final ContentHandler handler) throws IOException, MarshalException, ValidationException {
        Marshaller.marshal((Object)this, handler);
    }
    
    public void removeAllRiga() {
        this._rigaList.clear();
    }
    
    public boolean removeRiga(final Riga vRiga) {
        final boolean removed = this._rigaList.remove(vRiga);
        return removed;
    }
    
    public Riga removeRigaAt(final int index) {
        final Object obj = this._rigaList.remove(index);
        return (Riga)obj;
    }
    
    public void setRiga(final int index, final Riga vRiga) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this._rigaList.size()) {
            throw new IndexOutOfBoundsException("setRiga: Index value '" + index + "' not in range [0.." + (this._rigaList.size() - 1) + "]");
        }
        this._rigaList.set(index, vRiga);
    }
    
    public void setRiga(final Riga[] vRigaArray) {
        this._rigaList.clear();
        for (int i = 0; i < vRigaArray.length; ++i) {
            this._rigaList.add(vRigaArray[i]);
        }
    }
    
    public static Lista unmarshal(final Reader reader) throws MarshalException, ValidationException {
        return (Lista)Unmarshaller.unmarshal(Lista.class, reader);
    }
    
    public void validate() throws ValidationException {
        final Validator validator = new Validator();
        validator.validate((Object)this);
    }
}
