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

public class Variabile implements Serializable
{
    private String _nome;
    private VariabileChoice _variabileChoice;
    
    public String getNome() {
        return this._nome;
    }
    
    public VariabileChoice getVariabileChoice() {
        return this._variabileChoice;
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
    
    public void setNome(final String nome) {
        this._nome = nome;
    }
    
    public void setVariabileChoice(final VariabileChoice variabileChoice) {
        this._variabileChoice = variabileChoice;
    }
    
    public static Variabile unmarshal(final Reader reader) throws MarshalException, ValidationException {
        return (Variabile)Unmarshaller.unmarshal(Variabile.class, reader);
    }
    
    public void validate() throws ValidationException {
        final Validator validator = new Validator();
        validator.validate((Object)this);
    }
}
