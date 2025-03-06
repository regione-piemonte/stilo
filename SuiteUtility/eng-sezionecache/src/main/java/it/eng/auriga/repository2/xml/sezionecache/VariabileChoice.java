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

public class VariabileChoice implements Serializable
{
    private String _valoreSemplice;
    private Lista _lista;
    
    public Lista getLista() {
        return this._lista;
    }
    
    public String getValoreSemplice() {
        return this._valoreSemplice;
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
    
    public void setLista(final Lista lista) {
        this._lista = lista;
    }
    
    public void setValoreSemplice(final String valoreSemplice) {
        this._valoreSemplice = valoreSemplice;
    }
    
    public static VariabileChoice unmarshal(final Reader reader) throws MarshalException, ValidationException {
        return (VariabileChoice)Unmarshaller.unmarshal(VariabileChoice.class, reader);
    }
    
    public void validate() throws ValidationException {
        final Validator validator = new Validator();
        validator.validate((Object)this);
    }
}
