/**
 * PressioneAcqueId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class PressioneAcqueId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.VocPressioneAcque vocPressioneAcque;

    public PressioneAcqueId() {
    }

    public PressioneAcqueId(
           com.hyperborea.sira.ws.VocPressioneAcque vocPressioneAcque) {
           this.vocPressioneAcque = vocPressioneAcque;
    }


    /**
     * Gets the vocPressioneAcque value for this PressioneAcqueId.
     * 
     * @return vocPressioneAcque
     */
    public com.hyperborea.sira.ws.VocPressioneAcque getVocPressioneAcque() {
        return vocPressioneAcque;
    }


    /**
     * Sets the vocPressioneAcque value for this PressioneAcqueId.
     * 
     * @param vocPressioneAcque
     */
    public void setVocPressioneAcque(com.hyperborea.sira.ws.VocPressioneAcque vocPressioneAcque) {
        this.vocPressioneAcque = vocPressioneAcque;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PressioneAcqueId)) return false;
        PressioneAcqueId other = (PressioneAcqueId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.vocPressioneAcque==null && other.getVocPressioneAcque()==null) || 
             (this.vocPressioneAcque!=null &&
              this.vocPressioneAcque.equals(other.getVocPressioneAcque())));
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
        if (getVocPressioneAcque() != null) {
            _hashCode += getVocPressioneAcque().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PressioneAcqueId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pressioneAcqueId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocPressioneAcque");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocPressioneAcque"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocPressioneAcque"));
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
