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
import it.eng.auriga.repository2.xml.outputud.types.CategoriaRegType;
import java.io.Serializable;

public class EstremiRegNumType implements Serializable
{
    private CategoriaRegType _categoriaReg;
    private String _siglaReg;
    private int _annoReg;
    private boolean _has_annoReg;
    private int _numReg;
    private boolean _has_numReg;
    
    public void deleteAnnoReg() {
        this._has_annoReg = false;
    }
    
    public void deleteNumReg() {
        this._has_numReg = false;
    }
    
    public int getAnnoReg() {
        return this._annoReg;
    }
    
    public CategoriaRegType getCategoriaReg() {
        return this._categoriaReg;
    }
    
    public int getNumReg() {
        return this._numReg;
    }
    
    public String getSiglaReg() {
        return this._siglaReg;
    }
    
    public boolean hasAnnoReg() {
        return this._has_annoReg;
    }
    
    public boolean hasNumReg() {
        return this._has_numReg;
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
    
    public void setAnnoReg(final int annoReg) {
        this._annoReg = annoReg;
        this._has_annoReg = true;
    }
    
    public void setCategoriaReg(final CategoriaRegType categoriaReg) {
        this._categoriaReg = categoriaReg;
    }
    
    public void setNumReg(final int numReg) {
        this._numReg = numReg;
        this._has_numReg = true;
    }
    
    public void setSiglaReg(final String siglaReg) {
        this._siglaReg = siglaReg;
    }
    
    public static EstremiRegNumType unmarshal(final Reader reader) throws MarshalException, ValidationException {
        return (EstremiRegNumType)Unmarshaller.unmarshal(EstremiRegNumType.class, reader);
    }
    
    public void validate() throws ValidationException {
        final Validator validator = new Validator();
        validator.validate((Object)this);
    }
}
