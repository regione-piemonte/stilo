/**
 * WsMetadataDescriptorBase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsMetadataDescriptorBase  implements java.io.Serializable {
    private java.lang.String propertyLabel;

    private java.lang.String propertyName;

    private java.lang.String propertyType;

    private com.hyperborea.sira.ws.WsPropertyDescriptor[] wsPropertyDescriptors;

    public WsMetadataDescriptorBase() {
    }

    public WsMetadataDescriptorBase(
           java.lang.String propertyLabel,
           java.lang.String propertyName,
           java.lang.String propertyType,
           com.hyperborea.sira.ws.WsPropertyDescriptor[] wsPropertyDescriptors) {
           this.propertyLabel = propertyLabel;
           this.propertyName = propertyName;
           this.propertyType = propertyType;
           this.wsPropertyDescriptors = wsPropertyDescriptors;
    }


    /**
     * Gets the propertyLabel value for this WsMetadataDescriptorBase.
     * 
     * @return propertyLabel
     */
    public java.lang.String getPropertyLabel() {
        return propertyLabel;
    }


    /**
     * Sets the propertyLabel value for this WsMetadataDescriptorBase.
     * 
     * @param propertyLabel
     */
    public void setPropertyLabel(java.lang.String propertyLabel) {
        this.propertyLabel = propertyLabel;
    }


    /**
     * Gets the propertyName value for this WsMetadataDescriptorBase.
     * 
     * @return propertyName
     */
    public java.lang.String getPropertyName() {
        return propertyName;
    }


    /**
     * Sets the propertyName value for this WsMetadataDescriptorBase.
     * 
     * @param propertyName
     */
    public void setPropertyName(java.lang.String propertyName) {
        this.propertyName = propertyName;
    }


    /**
     * Gets the propertyType value for this WsMetadataDescriptorBase.
     * 
     * @return propertyType
     */
    public java.lang.String getPropertyType() {
        return propertyType;
    }


    /**
     * Sets the propertyType value for this WsMetadataDescriptorBase.
     * 
     * @param propertyType
     */
    public void setPropertyType(java.lang.String propertyType) {
        this.propertyType = propertyType;
    }


    /**
     * Gets the wsPropertyDescriptors value for this WsMetadataDescriptorBase.
     * 
     * @return wsPropertyDescriptors
     */
    public com.hyperborea.sira.ws.WsPropertyDescriptor[] getWsPropertyDescriptors() {
        return wsPropertyDescriptors;
    }


    /**
     * Sets the wsPropertyDescriptors value for this WsMetadataDescriptorBase.
     * 
     * @param wsPropertyDescriptors
     */
    public void setWsPropertyDescriptors(com.hyperborea.sira.ws.WsPropertyDescriptor[] wsPropertyDescriptors) {
        this.wsPropertyDescriptors = wsPropertyDescriptors;
    }

    public com.hyperborea.sira.ws.WsPropertyDescriptor getWsPropertyDescriptors(int i) {
        return this.wsPropertyDescriptors[i];
    }

    public void setWsPropertyDescriptors(int i, com.hyperborea.sira.ws.WsPropertyDescriptor _value) {
        this.wsPropertyDescriptors[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsMetadataDescriptorBase)) return false;
        WsMetadataDescriptorBase other = (WsMetadataDescriptorBase) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.propertyLabel==null && other.getPropertyLabel()==null) || 
             (this.propertyLabel!=null &&
              this.propertyLabel.equals(other.getPropertyLabel()))) &&
            ((this.propertyName==null && other.getPropertyName()==null) || 
             (this.propertyName!=null &&
              this.propertyName.equals(other.getPropertyName()))) &&
            ((this.propertyType==null && other.getPropertyType()==null) || 
             (this.propertyType!=null &&
              this.propertyType.equals(other.getPropertyType()))) &&
            ((this.wsPropertyDescriptors==null && other.getWsPropertyDescriptors()==null) || 
             (this.wsPropertyDescriptors!=null &&
              java.util.Arrays.equals(this.wsPropertyDescriptors, other.getWsPropertyDescriptors())));
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
        if (getPropertyLabel() != null) {
            _hashCode += getPropertyLabel().hashCode();
        }
        if (getPropertyName() != null) {
            _hashCode += getPropertyName().hashCode();
        }
        if (getPropertyType() != null) {
            _hashCode += getPropertyType().hashCode();
        }
        if (getWsPropertyDescriptors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getWsPropertyDescriptors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getWsPropertyDescriptors(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsMetadataDescriptorBase.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsMetadataDescriptorBase"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("propertyLabel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "propertyLabel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("propertyName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "propertyName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("propertyType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "propertyType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsPropertyDescriptors");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsPropertyDescriptors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsPropertyDescriptor"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
