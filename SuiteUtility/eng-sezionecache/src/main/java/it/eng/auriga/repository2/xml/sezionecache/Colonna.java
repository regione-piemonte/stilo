// 
// Decompiled by Procyon v0.5.36
// 

package it.eng.auriga.repository2.xml.sezionecache;

import org.exolab.castor.xml.Validator;
import org.exolab.castor.xml.Unmarshaller;
import java.io.Reader;
import java.io.IOException;
import org.xml.sax.ContentHandler;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import java.io.Writer;
import org.exolab.castor.xml.ValidationException;
import java.io.Serializable;

public class Colonna implements Serializable
{
    private int _nro;
    private boolean _has_nro;
    private String _content;
    
    public Colonna() {
        this.setContent(this._content = "");
    }
    
    public void deleteNro() {
        this._has_nro = false;
    }
    
    public String getContent() {
        return this._content;
    }
    
    public int getNro() {
        return this._nro;
    }
    
    public boolean hasNro() {
        return this._has_nro;
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
    
    public void setContent(final String content) {
        this._content = content;
    }
    
    public void setNro(final int nro) {
        this._nro = nro;
        this._has_nro = true;
    }
    
    public static Colonna unmarshal(final Reader reader) throws MarshalException, ValidationException {
        return (Colonna)Unmarshaller.unmarshal(Colonna.class, reader);
    }
    
    public void validate() throws ValidationException {
        final Validator validator = new Validator();
        validator.validate((Object)this);
    }
}
