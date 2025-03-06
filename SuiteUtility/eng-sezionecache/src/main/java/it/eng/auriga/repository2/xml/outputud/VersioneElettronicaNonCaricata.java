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
import java.io.Serializable;

public class VersioneElettronicaNonCaricata implements Serializable
{
    private int _nroAttachmentAssociato;
    private boolean _has_nroAttachmentAssociato;
    private String _nomeFile;
    
    public void deleteNroAttachmentAssociato() {
        this._has_nroAttachmentAssociato = false;
    }
    
    public String getNomeFile() {
        return this._nomeFile;
    }
    
    public int getNroAttachmentAssociato() {
        return this._nroAttachmentAssociato;
    }
    
    public boolean hasNroAttachmentAssociato() {
        return this._has_nroAttachmentAssociato;
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
    
    public void setNomeFile(final String nomeFile) {
        this._nomeFile = nomeFile;
    }
    
    public void setNroAttachmentAssociato(final int nroAttachmentAssociato) {
        this._nroAttachmentAssociato = nroAttachmentAssociato;
        this._has_nroAttachmentAssociato = true;
    }
    
    public static VersioneElettronicaNonCaricata unmarshal(final Reader reader) throws MarshalException, ValidationException {
        return (VersioneElettronicaNonCaricata)Unmarshaller.unmarshal(VersioneElettronicaNonCaricata.class, reader);
    }
    
    public void validate() throws ValidationException {
        final Validator validator = new Validator();
        validator.validate((Object)this);
    }
}
