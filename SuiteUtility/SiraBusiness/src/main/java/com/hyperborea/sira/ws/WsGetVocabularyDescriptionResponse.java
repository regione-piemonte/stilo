/**
 * WsGetVocabularyDescriptionResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsGetVocabularyDescriptionResponse  implements java.io.Serializable {
    private com.hyperborea.sira.ws.VocabularyDescription vocabularyDescription;

    public WsGetVocabularyDescriptionResponse() {
    }

    public WsGetVocabularyDescriptionResponse(
           com.hyperborea.sira.ws.VocabularyDescription vocabularyDescription) {
           this.vocabularyDescription = vocabularyDescription;
    }


    /**
     * Gets the vocabularyDescription value for this WsGetVocabularyDescriptionResponse.
     * 
     * @return vocabularyDescription
     */
    public com.hyperborea.sira.ws.VocabularyDescription getVocabularyDescription() {
        return vocabularyDescription;
    }


    /**
     * Sets the vocabularyDescription value for this WsGetVocabularyDescriptionResponse.
     * 
     * @param vocabularyDescription
     */
    public void setVocabularyDescription(com.hyperborea.sira.ws.VocabularyDescription vocabularyDescription) {
        this.vocabularyDescription = vocabularyDescription;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsGetVocabularyDescriptionResponse)) return false;
        WsGetVocabularyDescriptionResponse other = (WsGetVocabularyDescriptionResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.vocabularyDescription==null && other.getVocabularyDescription()==null) || 
             (this.vocabularyDescription!=null &&
              this.vocabularyDescription.equals(other.getVocabularyDescription())));
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
        if (getVocabularyDescription() != null) {
            _hashCode += getVocabularyDescription().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsGetVocabularyDescriptionResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetVocabularyDescriptionResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocabularyDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocabularyDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocabularyDescription"));
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
