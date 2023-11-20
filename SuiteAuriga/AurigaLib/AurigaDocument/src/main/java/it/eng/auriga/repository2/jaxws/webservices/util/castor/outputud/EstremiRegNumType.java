/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.0.4</a>, using an XML
 * Schema.
 * $Id: EstremiRegNumType.java,v 1.1 2008/09/29 16:05:38 str Exp $
 */

package it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class EstremiRegNumType.
 * 
 * @version $Revision: 1.1 $ $Date: 2008/09/29 16:05:38 $
 */
public class EstremiRegNumType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Categoria di registrazione / numerazione; valori ammessi
     * PG=Protocollo Generale; PP=Protocollo Particolare;
     * R=Repertorio; I=Numerazione data internamente al sistema
     * documentale
     */
    private it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.types.CategoriaRegType _categoriaReg;

    /**
     * Sigla che identifica il registro/tipo della numerazione
     */
    private java.lang.String _siglaReg;

    /**
     * Field _annoReg
     */
    private int _annoReg;

    /**
     * keeps track of state for field: _annoReg
     */
    private boolean _has_annoReg;

    /**
     * Field _numReg
     */
    private int _numReg;

    /**
     * keeps track of state for field: _numReg
     */
    private boolean _has_numReg;


      //----------------/
     //- Constructors -/
    //----------------/

    public EstremiRegNumType() 
     {
        super();
    } //-- it.eng.auriga.addon.openoffice.core.outputud.EstremiRegNumType()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteAnnoReg()
    {
        this._has_annoReg= false;
    } //-- void deleteAnnoReg() 

    /**
     */
    public void deleteNumReg()
    {
        this._has_numReg= false;
    } //-- void deleteNumReg() 

    /**
     * Returns the value of field 'annoReg'.
     * 
     * @return the value of field 'AnnoReg'.
     */
    public int getAnnoReg()
    {
        return this._annoReg;
    } //-- int getAnnoReg() 

    /**
     * Returns the value of field 'categoriaReg'. The field
     * 'categoriaReg' has the following description: Categoria di
     * registrazione / numerazione; valori ammessi PG=Protocollo
     * Generale; PP=Protocollo Particolare; R=Repertorio;
     * I=Numerazione data internamente al sistema documentale
     * 
     * @return the value of field 'CategoriaReg'.
     */
    public it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.types.CategoriaRegType getCategoriaReg()
    {
        return this._categoriaReg;
    } //-- it.eng.auriga.addon.openoffice.core.outputud.types.CategoriaRegType getCategoriaReg() 

    /**
     * Returns the value of field 'numReg'.
     * 
     * @return the value of field 'NumReg'.
     */
    public int getNumReg()
    {
        return this._numReg;
    } //-- int getNumReg() 

    /**
     * Returns the value of field 'siglaReg'. The field 'siglaReg'
     * has the following description: Sigla che identifica il
     * registro/tipo della numerazione
     * 
     * @return the value of field 'SiglaReg'.
     */
    public java.lang.String getSiglaReg()
    {
        return this._siglaReg;
    } //-- java.lang.String getSiglaReg() 

    /**
     * Method hasAnnoReg
     * 
     * 
     * 
     * @return true if at least one AnnoReg has been added
     */
    public boolean hasAnnoReg()
    {
        return this._has_annoReg;
    } //-- boolean hasAnnoReg() 

    /**
     * Method hasNumReg
     * 
     * 
     * 
     * @return true if at least one NumReg has been added
     */
    public boolean hasNumReg()
    {
        return this._has_numReg;
    } //-- boolean hasNumReg() 

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
     * Sets the value of field 'annoReg'.
     * 
     * @param annoReg the value of field 'annoReg'.
     */
    public void setAnnoReg(int annoReg)
    {
        this._annoReg = annoReg;
        this._has_annoReg = true;
    } //-- void setAnnoReg(int) 

    /**
     * Sets the value of field 'categoriaReg'. The field
     * 'categoriaReg' has the following description: Categoria di
     * registrazione / numerazione; valori ammessi PG=Protocollo
     * Generale; PP=Protocollo Particolare; R=Repertorio;
     * I=Numerazione data internamente al sistema documentale
     * 
     * @param categoriaReg the value of field 'categoriaReg'.
     */
    public void setCategoriaReg(it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.types.CategoriaRegType categoriaReg)
    {
        this._categoriaReg = categoriaReg;
    } //-- void setCategoriaReg(it.eng.auriga.addon.openoffice.core.outputud.types.CategoriaRegType) 

    /**
     * Sets the value of field 'numReg'.
     * 
     * @param numReg the value of field 'numReg'.
     */
    public void setNumReg(int numReg)
    {
        this._numReg = numReg;
        this._has_numReg = true;
    } //-- void setNumReg(int) 

    /**
     * Sets the value of field 'siglaReg'. The field 'siglaReg' has
     * the following description: Sigla che identifica il
     * registro/tipo della numerazione
     * 
     * @param siglaReg the value of field 'siglaReg'.
     */
    public void setSiglaReg(java.lang.String siglaReg)
    {
        this._siglaReg = siglaReg;
    } //-- void setSiglaReg(java.lang.String) 

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
     * it.eng.auriga.addon.openoffice.core.outputud.EstremiRegNumTyp
     */
    public static it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.EstremiRegNumType unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.EstremiRegNumType) Unmarshaller.unmarshal(it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.EstremiRegNumType.class, reader);
    } //-- it.eng.auriga.addon.openoffice.core.outputud.EstremiRegNumType unmarshal(java.io.Reader) 

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
