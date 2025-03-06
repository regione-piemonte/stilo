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

public class Attributo implements Serializable
{
    private String _nome;
    private Vector _valoreList;
    
    public Attributo() {
        this._valoreList = new Vector();
    }
    
    public void addValore(final String vValore) throws IndexOutOfBoundsException {
        this._valoreList.addElement(vValore);
    }
    
    public void addValore(final int index, final String vValore) throws IndexOutOfBoundsException {
        this._valoreList.add(index, vValore);
    }
    
    public Enumeration enumerateValore() {
        return this._valoreList.elements();
    }
    
    public String getNome() {
        return this._nome;
    }
    
    public String getValore(final int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this._valoreList.size()) {
            throw new IndexOutOfBoundsException("getValore: Index value '" + index + "' not in range [0.." + (this._valoreList.size() - 1) + "]");
        }
        return this._valoreList.get(index);
    }
    
    public String[] getValore() {
        final int size = this._valoreList.size();
        final String[] array = new String[size];
        for (int index = 0; index < size; ++index) {
            array[index] = this._valoreList.get(index);
        }
        return array;
    }
    
    public int getValoreCount() {
        return this._valoreList.size();
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
    
    public void removeAllValore() {
        this._valoreList.clear();
    }
    
    public boolean removeValore(final String vValore) {
        final boolean removed = this._valoreList.remove(vValore);
        return removed;
    }
    
    public String removeValoreAt(final int index) {
        final Object obj = this._valoreList.remove(index);
        return (String)obj;
    }
    
    public void setNome(final String nome) {
        this._nome = nome;
    }
    
    public void setValore(final int index, final String vValore) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this._valoreList.size()) {
            throw new IndexOutOfBoundsException("setValore: Index value '" + index + "' not in range [0.." + (this._valoreList.size() - 1) + "]");
        }
        this._valoreList.set(index, vValore);
    }
    
    public void setValore(final String[] vValoreArray) {
        this._valoreList.clear();
        for (int i = 0; i < vValoreArray.length; ++i) {
            this._valoreList.add(vValoreArray[i]);
        }
    }
    
    public static Attributo unmarshal(final Reader reader) throws MarshalException, ValidationException {
        return (Attributo)Unmarshaller.unmarshal(Attributo.class, reader);
    }
    
    public void validate() throws ValidationException {
        final Validator validator = new Validator();
        validator.validate((Object)this);
    }
}
