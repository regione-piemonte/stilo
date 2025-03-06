/**
 * SostanzaRirClass334Id.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class SostanzaRirClass334Id  implements java.io.Serializable {
    private com.hyperborea.sira.ws.Classificazione334 classificazione334;

    public SostanzaRirClass334Id() {
    }

    public SostanzaRirClass334Id(
           com.hyperborea.sira.ws.Classificazione334 classificazione334) {
           this.classificazione334 = classificazione334;
    }


    /**
     * Gets the classificazione334 value for this SostanzaRirClass334Id.
     * 
     * @return classificazione334
     */
    public com.hyperborea.sira.ws.Classificazione334 getClassificazione334() {
        return classificazione334;
    }


    /**
     * Sets the classificazione334 value for this SostanzaRirClass334Id.
     * 
     * @param classificazione334
     */
    public void setClassificazione334(com.hyperborea.sira.ws.Classificazione334 classificazione334) {
        this.classificazione334 = classificazione334;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SostanzaRirClass334Id)) return false;
        SostanzaRirClass334Id other = (SostanzaRirClass334Id) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.classificazione334==null && other.getClassificazione334()==null) || 
             (this.classificazione334!=null &&
              this.classificazione334.equals(other.getClassificazione334())));
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
        if (getClassificazione334() != null) {
            _hashCode += getClassificazione334().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SostanzaRirClass334Id.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostanzaRirClass334Id"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classificazione334");
        elemField.setXmlName(new javax.xml.namespace.QName("", "classificazione334"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "classificazione334"));
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
