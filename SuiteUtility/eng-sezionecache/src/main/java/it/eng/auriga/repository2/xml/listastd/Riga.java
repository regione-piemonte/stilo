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

public class Riga implements Serializable
{
    private Vector _colonnaList;
    
    public Riga() {
        this._colonnaList = new Vector();
    }
    
    public void addColonna(final Colonna vColonna) throws IndexOutOfBoundsException {
        this._colonnaList.addElement(vColonna);
    }
    
    public void addColonna(final int index, final Colonna vColonna) throws IndexOutOfBoundsException {
        this._colonnaList.add(index, vColonna);
    }
    
    public Enumeration enumerateColonna() {
        return this._colonnaList.elements();
    }
    
    public Colonna getColonna(final int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this._colonnaList.size()) {
            throw new IndexOutOfBoundsException("getColonna: Index value '" + index + "' not in range [0.." + (this._colonnaList.size() - 1) + "]");
        }
        return this._colonnaList.get(index);
    }
    
    public Colonna[] getColonna() {
        final int size = this._colonnaList.size();
        final Colonna[] array = new Colonna[size];
        for (int index = 0; index < size; ++index) {
            array[index] = this._colonnaList.get(index);
        }
        return array;
    }
    
    public int getColonnaCount() {
        return this._colonnaList.size();
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
    
    public void removeAllColonna() {
        this._colonnaList.clear();
    }
    
    public boolean removeColonna(final Colonna vColonna) {
        final boolean removed = this._colonnaList.remove(vColonna);
        return removed;
    }
    
    public Colonna removeColonnaAt(final int index) {
        final Object obj = this._colonnaList.remove(index);
        return (Colonna)obj;
    }
    
    public void setColonna(final int index, final Colonna vColonna) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this._colonnaList.size()) {
            throw new IndexOutOfBoundsException("setColonna: Index value '" + index + "' not in range [0.." + (this._colonnaList.size() - 1) + "]");
        }
        this._colonnaList.set(index, vColonna);
    }
    
    public void setColonna(final Colonna[] vColonnaArray) {
        this._colonnaList.clear();
        for (int i = 0; i < vColonnaArray.length; ++i) {
            this._colonnaList.add(vColonnaArray[i]);
        }
    }
    
    public static Riga unmarshal(final Reader reader) throws MarshalException, ValidationException {
        return (Riga)Unmarshaller.unmarshal(Riga.class, reader);
    }
    
    public void validate() throws ValidationException {
        final Validator validator = new Validator();
        validator.validate((Object)this);
    }
}
