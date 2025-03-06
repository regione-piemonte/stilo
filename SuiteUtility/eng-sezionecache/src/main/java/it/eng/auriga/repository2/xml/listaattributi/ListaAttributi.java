// 
// Decompiled by Procyon v0.5.36
// 

package it.eng.auriga.repository2.xml.listaattributi;

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

public class ListaAttributi implements Serializable
{
    private Vector _attributoList;
    
    public ListaAttributi() {
        this._attributoList = new Vector();
    }
    
    public void addAttributo(final Attributo vAttributo) throws IndexOutOfBoundsException {
        this._attributoList.addElement(vAttributo);
    }
    
    public void addAttributo(final int index, final Attributo vAttributo) throws IndexOutOfBoundsException {
        this._attributoList.add(index, vAttributo);
    }
    
    public Enumeration enumerateAttributo() {
        return this._attributoList.elements();
    }
    
    public Attributo getAttributo(final int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this._attributoList.size()) {
            throw new IndexOutOfBoundsException("getAttributo: Index value '" + index + "' not in range [0.." + (this._attributoList.size() - 1) + "]");
        }
        return this._attributoList.get(index);
    }
    
    public Attributo[] getAttributo() {
        final int size = this._attributoList.size();
        final Attributo[] array = new Attributo[size];
        for (int index = 0; index < size; ++index) {
            array[index] = this._attributoList.get(index);
        }
        return array;
    }
    
    public int getAttributoCount() {
        return this._attributoList.size();
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
    
    public void removeAllAttributo() {
        this._attributoList.clear();
    }
    
    public boolean removeAttributo(final Attributo vAttributo) {
        final boolean removed = this._attributoList.remove(vAttributo);
        return removed;
    }
    
    public Attributo removeAttributoAt(final int index) {
        final Object obj = this._attributoList.remove(index);
        return (Attributo)obj;
    }
    
    public void setAttributo(final int index, final Attributo vAttributo) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this._attributoList.size()) {
            throw new IndexOutOfBoundsException("setAttributo: Index value '" + index + "' not in range [0.." + (this._attributoList.size() - 1) + "]");
        }
        this._attributoList.set(index, vAttributo);
    }
    
    public void setAttributo(final Attributo[] vAttributoArray) {
        this._attributoList.clear();
        for (int i = 0; i < vAttributoArray.length; ++i) {
            this._attributoList.add(vAttributoArray[i]);
        }
    }
    
    public static ListaAttributi unmarshal(final Reader reader) throws MarshalException, ValidationException {
        return (ListaAttributi)Unmarshaller.unmarshal(ListaAttributi.class, reader);
    }
    
    public void validate() throws ValidationException {
        final Validator validator = new Validator();
        validator.validate((Object)this);
    }
}
