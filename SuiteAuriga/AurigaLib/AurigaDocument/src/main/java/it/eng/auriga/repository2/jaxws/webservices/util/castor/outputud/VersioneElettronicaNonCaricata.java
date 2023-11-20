/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.0.4</a>, using an XML
 * Schema.
 * $Id: VersioneElettronicaNonCaricata.java,v 1.1 2008/09/29 16:05:39 str Exp $
 */

package it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Documenti elettronici (passati come attach del messaggio SOAP)
 * che non e' stato possibile caricare nella repository
 * 
 * @version $Revision: 1.1 $ $Date: 2008/09/29 16:05:39 $
 */
public class VersioneElettronicaNonCaricata implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Indica quale degli attach in input al Web Service non e'
     * stato caricato
     */
    private int _nroAttachmentAssociato;

    /**
     * keeps track of state for field: _nroAttachmentAssociato
     */
    private boolean _has_nroAttachmentAssociato;

    /**
     * Nome con cui doveva essere salvato il file attach che non e'
     * stato caricato
     */
    private java.lang.String _nomeFile;


      //----------------/
     //- Constructors -/
    //----------------/

    public VersioneElettronicaNonCaricata() 
     {
        super();
    } //-- it.eng.auriga.addon.openoffice.core.outputud.VersioneElettronicaNonCaricata()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteNroAttachmentAssociato()
    {
        this._has_nroAttachmentAssociato= false;
    } //-- void deleteNroAttachmentAssociato() 

    /**
     * Returns the value of field 'nomeFile'. The field 'nomeFile'
     * has the following description: Nome con cui doveva essere
     * salvato il file attach che non e' stato caricato
     * 
     * @return the value of field 'NomeFile'.
     */
    public java.lang.String getNomeFile()
    {
        return this._nomeFile;
    } //-- java.lang.String getNomeFile() 

    /**
     * Returns the value of field 'nroAttachmentAssociato'. The
     * field 'nroAttachmentAssociato' has the following
     * description: Indica quale degli attach in input al Web
     * Service non e' stato caricato
     * 
     * @return the value of field 'NroAttachmentAssociato'.
     */
    public int getNroAttachmentAssociato()
    {
        return this._nroAttachmentAssociato;
    } //-- int getNroAttachmentAssociato() 

    /**
     * Method hasNroAttachmentAssociato
     * 
     * 
     * 
     * @return true if at least one NroAttachmentAssociato has been
     * added
     */
    public boolean hasNroAttachmentAssociato()
    {
        return this._has_nroAttachmentAssociato;
    } //-- boolean hasNroAttachmentAssociato() 

    /**
     * Method isValid
     * 
     * 
     * 
     * @return true if this object is valid according to the schema
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * 
     * 
     * @param out
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * 
     * 
     * @param handler
     * @throws java.io.IOException if an IOException occurs during
     * marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Sets the value of field 'nomeFile'. The field 'nomeFile' has
     * the following description: Nome con cui doveva essere
     * salvato il file attach che non e' stato caricato
     * 
     * @param nomeFile the value of field 'nomeFile'.
     */
    public void setNomeFile(java.lang.String nomeFile)
    {
        this._nomeFile = nomeFile;
    } //-- void setNomeFile(java.lang.String) 

    /**
     * Sets the value of field 'nroAttachmentAssociato'. The field
     * 'nroAttachmentAssociato' has the following description:
     * Indica quale degli attach in input al Web Service non e'
     * stato caricato
     * 
     * @param nroAttachmentAssociato the value of field
     * 'nroAttachmentAssociato'.
     */
    public void setNroAttachmentAssociato(int nroAttachmentAssociato)
    {
        this._nroAttachmentAssociato = nroAttachmentAssociato;
        this._has_nroAttachmentAssociato = true;
    } //-- void setNroAttachmentAssociato(int) 

    /**
     * Method unmarshal
     * 
     * 
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled
     * it.eng.auriga.addon.openoffice.core.outputud.VersioneElettronicaNonCaricata
     */
    public static it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata) Unmarshaller.unmarshal(it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata.class, reader);
    } //-- it.eng.auriga.addon.openoffice.core.outputud.VersioneElettronicaNonCaricata unmarshal(java.io.Reader) 

    /**
     * 
     * 
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
