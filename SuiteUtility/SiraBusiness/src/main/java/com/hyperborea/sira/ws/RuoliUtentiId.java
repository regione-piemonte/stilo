/**
 * RuoliUtentiId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class RuoliUtentiId  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String idRuolo;

    private com.hyperborea.sira.ws.Utenti utenti;

    public RuoliUtentiId() {
    }

    public RuoliUtentiId(
           java.lang.String idRuolo,
           com.hyperborea.sira.ws.Utenti utenti) {
        this.idRuolo = idRuolo;
        this.utenti = utenti;
    }


    /**
     * Gets the idRuolo value for this RuoliUtentiId.
     * 
     * @return idRuolo
     */
    public java.lang.String getIdRuolo() {
        return idRuolo;
    }


    /**
     * Sets the idRuolo value for this RuoliUtentiId.
     * 
     * @param idRuolo
     */
    public void setIdRuolo(java.lang.String idRuolo) {
        this.idRuolo = idRuolo;
    }


    /**
     * Gets the utenti value for this RuoliUtentiId.
     * 
     * @return utenti
     */
    public com.hyperborea.sira.ws.Utenti getUtenti() {
        return utenti;
    }


    /**
     * Sets the utenti value for this RuoliUtentiId.
     * 
     * @param utenti
     */
    public void setUtenti(com.hyperborea.sira.ws.Utenti utenti) {
        this.utenti = utenti;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RuoliUtentiId)) return false;
        RuoliUtentiId other = (RuoliUtentiId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idRuolo==null && other.getIdRuolo()==null) || 
             (this.idRuolo!=null &&
              this.idRuolo.equals(other.getIdRuolo()))) &&
            ((this.utenti==null && other.getUtenti()==null) || 
             (this.utenti!=null &&
              this.utenti.equals(other.getUtenti())));
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
        if (getIdRuolo() != null) {
            _hashCode += getIdRuolo().hashCode();
        }
        if (getUtenti() != null) {
            _hashCode += getUtenti().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RuoliUtentiId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ruoliUtentiId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idRuolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idRuolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utenti"));
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
