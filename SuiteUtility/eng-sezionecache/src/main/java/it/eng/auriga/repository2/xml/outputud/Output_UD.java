// 
// Decompiled by Procyon v0.5.36
// 

package it.eng.auriga.repository2.xml.outputud;

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

public class Output_UD implements Serializable
{
    private int _idUD;
    private boolean _has_idUD;
    private Vector _registrazioneDataUDList;
    private Vector _versioneElettronicaNonCaricataList;
    
    public Output_UD() {
        this._registrazioneDataUDList = new Vector();
        this._versioneElettronicaNonCaricataList = new Vector();
    }
    
    public void addRegistrazioneDataUD(final RegistrazioneDataUD vRegistrazioneDataUD) throws IndexOutOfBoundsException {
        this._registrazioneDataUDList.addElement(vRegistrazioneDataUD);
    }
    
    public void addRegistrazioneDataUD(final int index, final RegistrazioneDataUD vRegistrazioneDataUD) throws IndexOutOfBoundsException {
        this._registrazioneDataUDList.add(index, vRegistrazioneDataUD);
    }
    
    public void addVersioneElettronicaNonCaricata(final VersioneElettronicaNonCaricata vVersioneElettronicaNonCaricata) throws IndexOutOfBoundsException {
        this._versioneElettronicaNonCaricataList.addElement(vVersioneElettronicaNonCaricata);
    }
    
    public void addVersioneElettronicaNonCaricata(final int index, final VersioneElettronicaNonCaricata vVersioneElettronicaNonCaricata) throws IndexOutOfBoundsException {
        this._versioneElettronicaNonCaricataList.add(index, vVersioneElettronicaNonCaricata);
    }
    
    public void deleteIdUD() {
        this._has_idUD = false;
    }
    
    public Enumeration enumerateRegistrazioneDataUD() {
        return this._registrazioneDataUDList.elements();
    }
    
    public Enumeration enumerateVersioneElettronicaNonCaricata() {
        return this._versioneElettronicaNonCaricataList.elements();
    }
    
    public int getIdUD() {
        return this._idUD;
    }
    
    public RegistrazioneDataUD getRegistrazioneDataUD(final int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this._registrazioneDataUDList.size()) {
            throw new IndexOutOfBoundsException("getRegistrazioneDataUD: Index value '" + index + "' not in range [0.." + (this._registrazioneDataUDList.size() - 1) + "]");
        }
        return this._registrazioneDataUDList.get(index);
    }
    
    public RegistrazioneDataUD[] getRegistrazioneDataUD() {
        final int size = this._registrazioneDataUDList.size();
        final RegistrazioneDataUD[] array = new RegistrazioneDataUD[size];
        for (int index = 0; index < size; ++index) {
            array[index] = this._registrazioneDataUDList.get(index);
        }
        return array;
    }
    
    public int getRegistrazioneDataUDCount() {
        return this._registrazioneDataUDList.size();
    }
    
    public VersioneElettronicaNonCaricata getVersioneElettronicaNonCaricata(final int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this._versioneElettronicaNonCaricataList.size()) {
            throw new IndexOutOfBoundsException("getVersioneElettronicaNonCaricata: Index value '" + index + "' not in range [0.." + (this._versioneElettronicaNonCaricataList.size() - 1) + "]");
        }
        return this._versioneElettronicaNonCaricataList.get(index);
    }
    
    public VersioneElettronicaNonCaricata[] getVersioneElettronicaNonCaricata() {
        final int size = this._versioneElettronicaNonCaricataList.size();
        final VersioneElettronicaNonCaricata[] array = new VersioneElettronicaNonCaricata[size];
        for (int index = 0; index < size; ++index) {
            array[index] = this._versioneElettronicaNonCaricataList.get(index);
        }
        return array;
    }
    
    public int getVersioneElettronicaNonCaricataCount() {
        return this._versioneElettronicaNonCaricataList.size();
    }
    
    public boolean hasIdUD() {
        return this._has_idUD;
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
    
    public void removeAllRegistrazioneDataUD() {
        this._registrazioneDataUDList.clear();
    }
    
    public void removeAllVersioneElettronicaNonCaricata() {
        this._versioneElettronicaNonCaricataList.clear();
    }
    
    public boolean removeRegistrazioneDataUD(final RegistrazioneDataUD vRegistrazioneDataUD) {
        final boolean removed = this._registrazioneDataUDList.remove(vRegistrazioneDataUD);
        return removed;
    }
    
    public RegistrazioneDataUD removeRegistrazioneDataUDAt(final int index) {
        final Object obj = this._registrazioneDataUDList.remove(index);
        return (RegistrazioneDataUD)obj;
    }
    
    public boolean removeVersioneElettronicaNonCaricata(final VersioneElettronicaNonCaricata vVersioneElettronicaNonCaricata) {
        final boolean removed = this._versioneElettronicaNonCaricataList.remove(vVersioneElettronicaNonCaricata);
        return removed;
    }
    
    public VersioneElettronicaNonCaricata removeVersioneElettronicaNonCaricataAt(final int index) {
        final Object obj = this._versioneElettronicaNonCaricataList.remove(index);
        return (VersioneElettronicaNonCaricata)obj;
    }
    
    public void setIdUD(final int idUD) {
        this._idUD = idUD;
        this._has_idUD = true;
    }
    
    public void setRegistrazioneDataUD(final int index, final RegistrazioneDataUD vRegistrazioneDataUD) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this._registrazioneDataUDList.size()) {
            throw new IndexOutOfBoundsException("setRegistrazioneDataUD: Index value '" + index + "' not in range [0.." + (this._registrazioneDataUDList.size() - 1) + "]");
        }
        this._registrazioneDataUDList.set(index, vRegistrazioneDataUD);
    }
    
    public void setRegistrazioneDataUD(final RegistrazioneDataUD[] vRegistrazioneDataUDArray) {
        this._registrazioneDataUDList.clear();
        for (int i = 0; i < vRegistrazioneDataUDArray.length; ++i) {
            this._registrazioneDataUDList.add(vRegistrazioneDataUDArray[i]);
        }
    }
    
    public void setVersioneElettronicaNonCaricata(final int index, final VersioneElettronicaNonCaricata vVersioneElettronicaNonCaricata) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this._versioneElettronicaNonCaricataList.size()) {
            throw new IndexOutOfBoundsException("setVersioneElettronicaNonCaricata: Index value '" + index + "' not in range [0.." + (this._versioneElettronicaNonCaricataList.size() - 1) + "]");
        }
        this._versioneElettronicaNonCaricataList.set(index, vVersioneElettronicaNonCaricata);
    }
    
    public void setVersioneElettronicaNonCaricata(final VersioneElettronicaNonCaricata[] vVersioneElettronicaNonCaricataArray) {
        this._versioneElettronicaNonCaricataList.clear();
        for (int i = 0; i < vVersioneElettronicaNonCaricataArray.length; ++i) {
            this._versioneElettronicaNonCaricataList.add(vVersioneElettronicaNonCaricataArray[i]);
        }
    }
    
    public static Output_UD unmarshal(final Reader reader) throws MarshalException, ValidationException {
        return (Output_UD)Unmarshaller.unmarshal(Output_UD.class, reader);
    }
    
    public void validate() throws ValidationException {
        final Validator validator = new Validator();
        validator.validate((Object)this);
    }
}
