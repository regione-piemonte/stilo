/**
 * VocAttivita.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class VocAttivita  extends com.hyperborea.sira.ws.VocabolariDiDominio  implements java.io.Serializable {
    private java.lang.String codDescrizione;

    public VocAttivita() {
    }

    public VocAttivita(
           java.lang.Integer attivo,
           java.lang.String codice,
           java.lang.String descrizione,
           java.lang.Integer id,
           java.lang.Integer ordine,
           java.lang.String tipo,
           java.lang.String codDescrizione) {
        super(
            attivo,
            codice,
            descrizione,
            id,
            ordine,
            tipo);
        this.codDescrizione = codDescrizione;
    }


    /**
     * Gets the codDescrizione value for this VocAttivita.
     * 
     * @return codDescrizione
     */
    public java.lang.String getCodDescrizione() {
        return codDescrizione;
    }


    /**
     * Sets the codDescrizione value for this VocAttivita.
     * 
     * @param codDescrizione
     */
    public void setCodDescrizione(java.lang.String codDescrizione) {
        this.codDescrizione = codDescrizione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VocAttivita)) return false;
        VocAttivita other = (VocAttivita) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codDescrizione==null && other.getCodDescrizione()==null) || 
             (this.codDescrizione!=null &&
              this.codDescrizione.equals(other.getCodDescrizione())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getCodDescrizione() != null) {
            _hashCode += getCodDescrizione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VocAttivita.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAttivita"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codDescrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codDescrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
