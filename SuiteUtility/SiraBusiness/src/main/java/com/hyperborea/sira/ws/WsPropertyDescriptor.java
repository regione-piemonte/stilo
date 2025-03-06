/**
 * WsPropertyDescriptor.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsPropertyDescriptor  extends com.hyperborea.sira.ws.WsMetadataDescriptorBase  implements java.io.Serializable {
    private boolean mandatory;

    private boolean multiple;

    private com.hyperborea.sira.ws.VocabularyDescription vocabularyDescription;

    public WsPropertyDescriptor() {
    }

    public WsPropertyDescriptor(
           java.lang.String propertyLabel,
           java.lang.String propertyName,
           java.lang.String propertyType,
           com.hyperborea.sira.ws.WsPropertyDescriptor[] wsPropertyDescriptors,
           boolean mandatory,
           boolean multiple,
           com.hyperborea.sira.ws.VocabularyDescription vocabularyDescription) {
        super(
            propertyLabel,
            propertyName,
            propertyType,
            wsPropertyDescriptors);
        this.mandatory = mandatory;
        this.multiple = multiple;
        this.vocabularyDescription = vocabularyDescription;
    }


    /**
     * Gets the mandatory value for this WsPropertyDescriptor.
     * 
     * @return mandatory
     */
    public boolean isMandatory() {
        return mandatory;
    }


    /**
     * Sets the mandatory value for this WsPropertyDescriptor.
     * 
     * @param mandatory
     */
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }


    /**
     * Gets the multiple value for this WsPropertyDescriptor.
     * 
     * @return multiple
     */
    public boolean isMultiple() {
        return multiple;
    }


    /**
     * Sets the multiple value for this WsPropertyDescriptor.
     * 
     * @param multiple
     */
    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }


    /**
     * Gets the vocabularyDescription value for this WsPropertyDescriptor.
     * 
     * @return vocabularyDescription
     */
    public com.hyperborea.sira.ws.VocabularyDescription getVocabularyDescription() {
        return vocabularyDescription;
    }


    /**
     * Sets the vocabularyDescription value for this WsPropertyDescriptor.
     * 
     * @param vocabularyDescription
     */
    public void setVocabularyDescription(com.hyperborea.sira.ws.VocabularyDescription vocabularyDescription) {
        this.vocabularyDescription = vocabularyDescription;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsPropertyDescriptor)) return false;
        WsPropertyDescriptor other = (WsPropertyDescriptor) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.mandatory == other.isMandatory() &&
            this.multiple == other.isMultiple() &&
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
        int _hashCode = super.hashCode();
        _hashCode += (isMandatory() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isMultiple() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getVocabularyDescription() != null) {
            _hashCode += getVocabularyDescription().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsPropertyDescriptor.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsPropertyDescriptor"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mandatory");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mandatory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("multiple");
        elemField.setXmlName(new javax.xml.namespace.QName("", "multiple"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
