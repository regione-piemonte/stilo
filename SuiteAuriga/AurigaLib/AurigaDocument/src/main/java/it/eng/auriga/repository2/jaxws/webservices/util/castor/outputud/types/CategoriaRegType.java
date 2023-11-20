/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.0.4</a>, using an XML
 * Schema.
 * $Id: CategoriaRegType.java,v 1.1 2008/09/29 16:05:39 str Exp $
 */

package it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.Hashtable;

/**
 * Class CategoriaRegType.
 * 
 * @version $Revision: 1.1 $ $Date: 2008/09/29 16:05:39 $
 */
public class CategoriaRegType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The PG type
     */
    public static final int PG_TYPE = 0;

    /**
     * The instance of the PG type
     */
    public static final CategoriaRegType PG = new CategoriaRegType(PG_TYPE, "PG");

    /**
     * The PP type
     */
    public static final int PP_TYPE = 1;

    /**
     * The instance of the PP type
     */
    public static final CategoriaRegType PP = new CategoriaRegType(PP_TYPE, "PP");

    /**
     * The R type
     */
    public static final int R_TYPE = 2;

    /**
     * The instance of the R type
     */
    public static final CategoriaRegType R = new CategoriaRegType(R_TYPE, "R");

    /**
     * The I type
     */
    public static final int I_TYPE = 3;

    /**
     * The instance of the I type
     */
    public static final CategoriaRegType I = new CategoriaRegType(I_TYPE, "I");

    /**
     * Field _memberTable
     */
    private static java.util.Hashtable _memberTable = init();

    /**
     * Field type
     */
    private int type = -1;

    /**
     * Field stringValue
     */
    private java.lang.String stringValue = null;


      //----------------/
     //- Constructors -/
    //----------------/

    private CategoriaRegType(int type, java.lang.String value) 
     {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- it.eng.auriga.addon.openoffice.core.outputud.types.CategoriaRegType(int, java.lang.String)


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate
     * 
     * Returns an enumeration of all possible instances of
     * CategoriaRegType
     * 
     * @return an Enumeration over all possible instances of
     * CategoriaRegType
     */
    public static java.util.Enumeration enumerate()
    {
        return _memberTable.elements();
    } //-- java.util.Enumeration enumerate() 

    /**
     * Method getType
     * 
     * Returns the type of this CategoriaRegType
     * 
     * @return the type of this CategoriaRegType
     */
    public int getType()
    {
        return this.type;
    } //-- int getType() 

    /**
     * Method init
     * 
     * 
     * 
     * @return the initialized Hashtable for the member table
     */
    private static java.util.Hashtable init()
    {
        Hashtable members = new Hashtable();
        members.put("PG", PG);
        members.put("PP", PP);
        members.put("R", R);
        members.put("I", I);
        return members;
    } //-- java.util.Hashtable init() 

    /**
     * Method readResolve
     * 
     *  will be called during deserialization to replace the
     * deserialized object with the correct constant instance.
     * 
     * @return this deserialized object
     */
    private java.lang.Object readResolve()
    {
        return valueOf(this.stringValue);
    } //-- java.lang.Object readResolve() 

    /**
     * Method toString
     * 
     * Returns the String representation of this CategoriaRegType
     * 
     * @return the String representation of this CategoriaRegType
     */
    public java.lang.String toString()
    {
        return this.stringValue;
    } //-- java.lang.String toString() 

    /**
     * Method valueOf
     * 
     * Returns a new CategoriaRegType based on the given String
     * value.
     * 
     * @param string
     * @return the CategoriaRegType value of parameter 'string'
     */
    public static it.eng.auriga.repository2.jaxws.webservices.util.castor.outputud.types.CategoriaRegType valueOf(java.lang.String string)
    {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid CategoriaRegType";
            throw new IllegalArgumentException(err);
        }
        return (CategoriaRegType) obj;
    } //-- it.eng.auriga.addon.openoffice.core.outputud.types.CategoriaRegType valueOf(java.lang.String) 

}
