/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * StatoDocumentoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.albopretorio.ws;

public class StatoDocumentoType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected StatoDocumentoType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Sconosciuto = "Sconosciuto";
    public static final java.lang.String _Caricato = "Caricato";
    public static final java.lang.String _InFirmaDocumento = "InFirmaDocumento";
    public static final java.lang.String _InFirmaConformita = "InFirmaConformita";
    public static final java.lang.String _InEsposizione = "InEsposizione";
    public static final java.lang.String _InFirmaEsecutivita = "InFirmaEsecutivita";
    public static final java.lang.String _Archiviato = "Archiviato";
    public static final java.lang.String _Annullato = "Annullato";
    public static final java.lang.String _Errore = "Errore";
    public static final StatoDocumentoType Sconosciuto = new StatoDocumentoType(_Sconosciuto);
    public static final StatoDocumentoType Caricato = new StatoDocumentoType(_Caricato);
    public static final StatoDocumentoType InFirmaDocumento = new StatoDocumentoType(_InFirmaDocumento);
    public static final StatoDocumentoType InFirmaConformita = new StatoDocumentoType(_InFirmaConformita);
    public static final StatoDocumentoType InEsposizione = new StatoDocumentoType(_InEsposizione);
    public static final StatoDocumentoType InFirmaEsecutivita = new StatoDocumentoType(_InFirmaEsecutivita);
    public static final StatoDocumentoType Archiviato = new StatoDocumentoType(_Archiviato);
    public static final StatoDocumentoType Annullato = new StatoDocumentoType(_Annullato);
    public static final StatoDocumentoType Errore = new StatoDocumentoType(_Errore);
    public java.lang.String getValue() { return _value_;}
    public static StatoDocumentoType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        StatoDocumentoType enumeration = (StatoDocumentoType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static StatoDocumentoType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StatoDocumentoType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://it.intersail/wse", "StatoDocumentoType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
