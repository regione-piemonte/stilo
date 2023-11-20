/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.0.4</a>, using an XML
 * Schema.
 * $Id$
 */

package it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * ROOT element dello schema. Contiene l'output specifico dei Web
 * Service di creazione e aggiornamento di unit� documentaria (non
 * l'esito del processo di creazione/aggiornamento dell'unit�
 * documentaria e l'eventuale messaggio di errore)
 * 
 * @version $Revision$ $Date$
 */
public class Output_UD implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/
	
	/**
     * Identificativo assegnato al documento elettoronico nel
     * sistema documentale
     */
    private long _idDOC;

    /**
     * keeps track of state for field: _idDOC
     */
    private boolean _has_idDOC;

    /**
     * Identificativo univoco assegnato all'unita' documentaria nel
     * sistema documentale
     */
    private long _idUD;

    /**
     * keeps track of state for field: _idUD
     */
    private boolean _has_idUD;

    /**
     * Identificativo univoco assegnato al documento primario
     * dell'unit� documentaria
     */
    private long _idDocPrimario;

    /**
     * keeps track of state for field: _idDocPrimario
     */
    private boolean _has_idDocPrimario;

    /**
     * Sigla, anno e numero delle nuove registrazioni/numerazioni
     * assegnate dal sistema documentale all'unita'
     * creata/aggiornata (se il WS ha avuto successo e se nell'xml
     * in input ad esso era valorizzato l'elemento
     * RegistrazioneDaDare)
     */
    private java.util.Vector _registrazioneDataUDList;

    /**
     * Documenti elettronici (passati come attach del messaggio
     * SOAP) che non è stato possibile caricare nella repository
     */
    private java.util.Vector _versioneElettronicaNonCaricataList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Output_UD() 
     {
        super();
        this._registrazioneDataUDList = new java.util.Vector();
        this._versioneElettronicaNonCaricataList = new java.util.Vector();
    } //-- it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.Output_UD()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vRegistrazioneDataUD
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addRegistrazioneDataUD(it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD vRegistrazioneDataUD)
        throws java.lang.IndexOutOfBoundsException
    {
        this._registrazioneDataUDList.addElement(vRegistrazioneDataUD);
    } //-- void addRegistrazioneDataUD(it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD) 

    /**
     * 
     * 
     * @param index
     * @param vRegistrazioneDataUD
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addRegistrazioneDataUD(int index, it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD vRegistrazioneDataUD)
        throws java.lang.IndexOutOfBoundsException
    {
        this._registrazioneDataUDList.add(index, vRegistrazioneDataUD);
    } //-- void addRegistrazioneDataUD(int, it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD) 

    /**
     * 
     * 
     * @param vVersioneElettronicaNonCaricata
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addVersioneElettronicaNonCaricata(it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata vVersioneElettronicaNonCaricata)
        throws java.lang.IndexOutOfBoundsException
    {
        this._versioneElettronicaNonCaricataList.addElement(vVersioneElettronicaNonCaricata);
    } //-- void addVersioneElettronicaNonCaricata(it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata) 

    /**
     * 
     * 
     * @param index
     * @param vVersioneElettronicaNonCaricata
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addVersioneElettronicaNonCaricata(int index, it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata vVersioneElettronicaNonCaricata)
        throws java.lang.IndexOutOfBoundsException
    {
        this._versioneElettronicaNonCaricataList.add(index, vVersioneElettronicaNonCaricata);
    } //-- void addVersioneElettronicaNonCaricata(int, it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata) 

    /**
     */
    public void deleteIdDocPrimario()
    {
        this._has_idDocPrimario= false;
    } //-- void deleteIdDocPrimario() 

    /**
     */
    public void deleteIdUD()
    {
        this._has_idUD= false;
    } //-- void deleteIdUD() 

    /**
     * Method enumerateRegistrazioneDataUD
     * 
     * 
     * 
     * @return an Enumeration over all
     * it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD
     * elements
     */
    public java.util.Enumeration enumerateRegistrazioneDataUD()
    {
        return this._registrazioneDataUDList.elements();
    } //-- java.util.Enumeration enumerateRegistrazioneDataUD() 

    /**
     * Method enumerateVersioneElettronicaNonCaricata
     * 
     * 
     * 
     * @return an Enumeration over all
     * it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata
     * elements
     */
    public java.util.Enumeration enumerateVersioneElettronicaNonCaricata()
    {
        return this._versioneElettronicaNonCaricataList.elements();
    } //-- java.util.Enumeration enumerateVersioneElettronicaNonCaricata() 

    /**
     * Returns the value of field 'idDocPrimario'. The field
     * 'idDocPrimario' has the following description:
     * Identificativo univoco assegnato al documento primario
     * dell'unit� documentaria
     * 
     * @return the value of field 'IdDocPrimario'.
     */
    public long getIdDocPrimario()
    {
        return this._idDocPrimario;
    } //-- int getIdDocPrimario() 

    /**
     * Returns the value of field 'idUD'. The field 'idUD' has the
     * following description: Identificativo univoco assegnato
     * all'unit� documentaria nel sistema documentale
     * 
     * @return the value of field 'IdUD'.
     */
    public long getIdUD()
    {
        return this._idUD;
    } //-- int getIdUD() 
    
    public long getIdDOC()
    {
        return this._idDOC;
    } 

    /**
     * Method getRegistrazioneDataUD
     * 
     * 
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD
     * at the given index
     */
    public it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD getRegistrazioneDataUD(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        // check bounds for index
        if (index < 0 || index >= this._registrazioneDataUDList.size()) {
            throw new IndexOutOfBoundsException("getRegistrazioneDataUD: Index value '" + index + "' not in range [0.." + (this._registrazioneDataUDList.size() - 1) + "]");
        }
        
        return (it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD) _registrazioneDataUDList.get(index);
    } //-- it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD getRegistrazioneDataUD(int) 

    /**
     * Method getRegistrazioneDataUD
     * 
     * 
     * 
     * @return this collection as an Array
     */
    public it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD[] getRegistrazioneDataUD()
    {
        int size = this._registrazioneDataUDList.size();
        it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD[] array = new it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD[size];
        for (int index = 0; index < size; index++){
            array[index] = (it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD) _registrazioneDataUDList.get(index);
        }
        
        return array;
    } //-- it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD[] getRegistrazioneDataUD() 

    /**
     * Method getRegistrazioneDataUDCount
     * 
     * 
     * 
     * @return the size of this collection
     */
    public int getRegistrazioneDataUDCount()
    {
        return this._registrazioneDataUDList.size();
    } //-- int getRegistrazioneDataUDCount() 

    /**
     * Method getVersioneElettronicaNonCaricata
     * 
     * 
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata
     * at the given index
     */
    public it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata getVersioneElettronicaNonCaricata(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        // check bounds for index
        if (index < 0 || index >= this._versioneElettronicaNonCaricataList.size()) {
            throw new IndexOutOfBoundsException("getVersioneElettronicaNonCaricata: Index value '" + index + "' not in range [0.." + (this._versioneElettronicaNonCaricataList.size() - 1) + "]");
        }
        
        return (it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata) _versioneElettronicaNonCaricataList.get(index);
    } //-- it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata getVersioneElettronicaNonCaricata(int) 

    /**
     * Method getVersioneElettronicaNonCaricata
     * 
     * 
     * 
     * @return this collection as an Array
     */
    public it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata[] getVersioneElettronicaNonCaricata()
    {
        int size = this._versioneElettronicaNonCaricataList.size();
        it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata[] array = new it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata[size];
        for (int index = 0; index < size; index++){
            array[index] = (it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata) _versioneElettronicaNonCaricataList.get(index);
        }
        
        return array;
    } //-- it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata[] getVersioneElettronicaNonCaricata() 

    /**
     * Method getVersioneElettronicaNonCaricataCount
     * 
     * 
     * 
     * @return the size of this collection
     */
    public int getVersioneElettronicaNonCaricataCount()
    {
        return this._versioneElettronicaNonCaricataList.size();
    } //-- int getVersioneElettronicaNonCaricataCount() 

    /**
     * Method hasIdDocPrimario
     * 
     * 
     * 
     * @return true if at least one IdDocPrimario has been added
     */
    public boolean hasIdDocPrimario()
    {
        return this._has_idDocPrimario;
    } //-- boolean hasIdDocPrimario() 

    /**
     * Method hasIdUD
     * 
     * 
     * 
     * @return true if at least one IdUD has been added
     */
    public boolean hasIdUD()
    {
        return this._has_idUD;
    } //-- boolean hasIdUD() 
    
    /**
     * Method hasIdDOC
     * 
     * 
     * 
     * @return true if at least one IdDOC has been added
     */
    public boolean hasIdDOC()
    {
        return this._has_idDOC;
    }
    

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
     */
    public void removeAllRegistrazioneDataUD()
    {
        this._registrazioneDataUDList.clear();
    } //-- void removeAllRegistrazioneDataUD() 

    /**
     */
    public void removeAllVersioneElettronicaNonCaricata()
    {
        this._versioneElettronicaNonCaricataList.clear();
    } //-- void removeAllVersioneElettronicaNonCaricata() 

    /**
     * Method removeRegistrazioneDataUD
     * 
     * 
     * 
     * @param vRegistrazioneDataUD
     * @return true if the object was removed from the collection.
     */
    public boolean removeRegistrazioneDataUD(it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD vRegistrazioneDataUD)
    {
        boolean removed = _registrazioneDataUDList.remove(vRegistrazioneDataUD);
        return removed;
    } //-- boolean removeRegistrazioneDataUD(it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD) 

    /**
     * Method removeRegistrazioneDataUDAt
     * 
     * 
     * 
     * @param index
     * @return the element removed from the collection
     */
    public it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD removeRegistrazioneDataUDAt(int index)
    {
        Object obj = this._registrazioneDataUDList.remove(index);
        return (it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD) obj;
    } //-- it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD removeRegistrazioneDataUDAt(int) 

    /**
     * Method removeVersioneElettronicaNonCaricata
     * 
     * 
     * 
     * @param vVersioneElettronicaNonCaricata
     * @return true if the object was removed from the collection.
     */
    public boolean removeVersioneElettronicaNonCaricata(it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata vVersioneElettronicaNonCaricata)
    {
        boolean removed = _versioneElettronicaNonCaricataList.remove(vVersioneElettronicaNonCaricata);
        return removed;
    } //-- boolean removeVersioneElettronicaNonCaricata(it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata) 

    /**
     * Method removeVersioneElettronicaNonCaricataAt
     * 
     * 
     * 
     * @param index
     * @return the element removed from the collection
     */
    public it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata removeVersioneElettronicaNonCaricataAt(int index)
    {
        Object obj = this._versioneElettronicaNonCaricataList.remove(index);
        return (it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata) obj;
    } //-- it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata removeVersioneElettronicaNonCaricataAt(int) 

    /**
     * Sets the value of field 'idDocPrimario'. The field
     * 'idDocPrimario' has the following description:
     * Identificativo univoco assegnato al documento primario
     * dell'unit� documentaria
     * 
     * @param idDocPrimario the value of field 'idDocPrimario'.
     */
    public void setIdDocPrimario(long idDocPrimario)
    {
        this._idDocPrimario = idDocPrimario;
        this._has_idDocPrimario = true;
    } //-- void setIdDocPrimario(long) 

    /**
     * Sets the value of field 'idUD'. The field 'idUD' has the
     * following description: Identificativo univoco assegnato
     * all'unit� documentaria nel sistema documentale
     * 
     * @param idUD the value of field 'idUD'.
     */
    public void setIdUD(long idUD)
    {
        this._idUD = idUD;
        this._has_idUD = true;
    } //-- void setIdUD(long) 
    
    /**
     * Sets the value of field 'idDOC'. The field 'idDOC' has the
     * following description: Identificativo assegnato
     * al documento elettronico nel sistema documentale
     * 
     * @param idDOC the value of field 'idDOC'.
     */
    public void setIdDOC(long idDOC)
    {
        this._idDOC = idDOC;
        this._has_idDOC = true;
    }

    /**
     * 
     * 
     * @param index
     * @param vRegistrazioneDataUD
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setRegistrazioneDataUD(int index, it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD vRegistrazioneDataUD)
        throws java.lang.IndexOutOfBoundsException
    {
        // check bounds for index
        if (index < 0 || index >= this._registrazioneDataUDList.size()) {
            throw new IndexOutOfBoundsException("setRegistrazioneDataUD: Index value '" + index + "' not in range [0.." + (this._registrazioneDataUDList.size() - 1) + "]");
        }
        
        this._registrazioneDataUDList.set(index, vRegistrazioneDataUD);
    } //-- void setRegistrazioneDataUD(int, it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD) 

    /**
     * 
     * 
     * @param vRegistrazioneDataUDArray
     */
    public void setRegistrazioneDataUD(it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD[] vRegistrazioneDataUDArray)
    {
        //-- copy array
        _registrazioneDataUDList.clear();
        
        for (int i = 0; i < vRegistrazioneDataUDArray.length; i++) {
                this._registrazioneDataUDList.add(vRegistrazioneDataUDArray[i]);
        }
    } //-- void setRegistrazioneDataUD(it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.RegistrazioneDataUD) 

    /**
     * 
     * 
     * @param index
     * @param vVersioneElettronicaNonCaricata
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setVersioneElettronicaNonCaricata(int index, it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata vVersioneElettronicaNonCaricata)
        throws java.lang.IndexOutOfBoundsException
    {
        // check bounds for index
        if (index < 0 || index >= this._versioneElettronicaNonCaricataList.size()) {
            throw new IndexOutOfBoundsException("setVersioneElettronicaNonCaricata: Index value '" + index + "' not in range [0.." + (this._versioneElettronicaNonCaricataList.size() - 1) + "]");
        }
        
        this._versioneElettronicaNonCaricataList.set(index, vVersioneElettronicaNonCaricata);
    } //-- void setVersioneElettronicaNonCaricata(int, it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata) 

    /**
     * 
     * 
     * @param vVersioneElettronicaNonCaricataArray
     */
    public void setVersioneElettronicaNonCaricata(it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata[] vVersioneElettronicaNonCaricataArray)
    {
        //-- copy array
        _versioneElettronicaNonCaricataList.clear();
        
        for (int i = 0; i < vVersioneElettronicaNonCaricataArray.length; i++) {
                this._versioneElettronicaNonCaricataList.add(vVersioneElettronicaNonCaricataArray[i]);
        }
    } //-- void setVersioneElettronicaNonCaricata(it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.VersioneElettronicaNonCaricata) 

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
     * it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.Output_UD
     */
    public static it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.Output_UD unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.Output_UD) Unmarshaller.unmarshal(it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.Output_UD.class, reader);
    } //-- it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.Output_UD unmarshal(java.io.Reader) 

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
