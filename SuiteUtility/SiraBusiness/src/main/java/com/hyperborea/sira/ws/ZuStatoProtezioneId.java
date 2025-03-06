/**
 * ZuStatoProtezioneId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ZuStatoProtezioneId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.VocProtezione vocProtezione;

    public ZuStatoProtezioneId() {
    }

    public ZuStatoProtezioneId(
           com.hyperborea.sira.ws.VocProtezione vocProtezione) {
           this.vocProtezione = vocProtezione;
    }


    /**
     * Gets the vocProtezione value for this ZuStatoProtezioneId.
     * 
     * @return vocProtezione
     */
    public com.hyperborea.sira.ws.VocProtezione getVocProtezione() {
        return vocProtezione;
    }


    /**
     * Sets the vocProtezione value for this ZuStatoProtezioneId.
     * 
     * @param vocProtezione
     */
    public void setVocProtezione(com.hyperborea.sira.ws.VocProtezione vocProtezione) {
        this.vocProtezione = vocProtezione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZuStatoProtezioneId)) return false;
        ZuStatoProtezioneId other = (ZuStatoProtezioneId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.vocProtezione==null && other.getVocProtezione()==null) || 
             (this.vocProtezione!=null &&
              this.vocProtezione.equals(other.getVocProtezione())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getVocProtezione() != null) {
            _hashCode += getVocProtezione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZuStatoProtezioneId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zuStatoProtezioneId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocProtezione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocProtezione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocProtezione"));
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
