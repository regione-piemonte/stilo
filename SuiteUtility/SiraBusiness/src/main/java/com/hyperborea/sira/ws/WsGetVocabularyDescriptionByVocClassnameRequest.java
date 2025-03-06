/**
 * WsGetVocabularyDescriptionByVocClassnameRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsGetVocabularyDescriptionByVocClassnameRequest  implements java.io.Serializable {
    private java.lang.String vocClassName;

    public WsGetVocabularyDescriptionByVocClassnameRequest() {
    }

    public WsGetVocabularyDescriptionByVocClassnameRequest(
           java.lang.String vocClassName) {
           this.vocClassName = vocClassName;
    }


    /**
     * Gets the vocClassName value for this WsGetVocabularyDescriptionByVocClassnameRequest.
     * 
     * @return vocClassName
     */
    public java.lang.String getVocClassName() {
        return vocClassName;
    }


    /**
     * Sets the vocClassName value for this WsGetVocabularyDescriptionByVocClassnameRequest.
     * 
     * @param vocClassName
     */
    public void setVocClassName(java.lang.String vocClassName) {
        this.vocClassName = vocClassName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsGetVocabularyDescriptionByVocClassnameRequest)) return false;
        WsGetVocabularyDescriptionByVocClassnameRequest other = (WsGetVocabularyDescriptionByVocClassnameRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.vocClassName==null && other.getVocClassName()==null) || 
             (this.vocClassName!=null &&
              this.vocClassName.equals(other.getVocClassName())));
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
        if (getVocClassName() != null) {
            _hashCode += getVocClassName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsGetVocabularyDescriptionByVocClassnameRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetVocabularyDescriptionByVocClassnameRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocClassName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocClassName"));
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
