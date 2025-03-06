/**
 * VocabularyItemExtra.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class VocabularyItemExtra  implements java.io.Serializable {
    private java.lang.String propertyName;

    private java.lang.Float valFloat;

    private java.lang.Integer valInteger;

    private java.lang.String valString;

    private com.hyperborea.sira.ws.VoceDiVocabolario valVocItem;

    public VocabularyItemExtra() {
    }

    public VocabularyItemExtra(
           java.lang.String propertyName,
           java.lang.Float valFloat,
           java.lang.Integer valInteger,
           java.lang.String valString,
           com.hyperborea.sira.ws.VoceDiVocabolario valVocItem) {
           this.propertyName = propertyName;
           this.valFloat = valFloat;
           this.valInteger = valInteger;
           this.valString = valString;
           this.valVocItem = valVocItem;
    }


    /**
     * Gets the propertyName value for this VocabularyItemExtra.
     * 
     * @return propertyName
     */
    public java.lang.String getPropertyName() {
        return propertyName;
    }


    /**
     * Sets the propertyName value for this VocabularyItemExtra.
     * 
     * @param propertyName
     */
    public void setPropertyName(java.lang.String propertyName) {
        this.propertyName = propertyName;
    }


    /**
     * Gets the valFloat value for this VocabularyItemExtra.
     * 
     * @return valFloat
     */
    public java.lang.Float getValFloat() {
        return valFloat;
    }


    /**
     * Sets the valFloat value for this VocabularyItemExtra.
     * 
     * @param valFloat
     */
    public void setValFloat(java.lang.Float valFloat) {
        this.valFloat = valFloat;
    }


    /**
     * Gets the valInteger value for this VocabularyItemExtra.
     * 
     * @return valInteger
     */
    public java.lang.Integer getValInteger() {
        return valInteger;
    }


    /**
     * Sets the valInteger value for this VocabularyItemExtra.
     * 
     * @param valInteger
     */
    public void setValInteger(java.lang.Integer valInteger) {
        this.valInteger = valInteger;
    }


    /**
     * Gets the valString value for this VocabularyItemExtra.
     * 
     * @return valString
     */
    public java.lang.String getValString() {
        return valString;
    }


    /**
     * Sets the valString value for this VocabularyItemExtra.
     * 
     * @param valString
     */
    public void setValString(java.lang.String valString) {
        this.valString = valString;
    }


    /**
     * Gets the valVocItem value for this VocabularyItemExtra.
     * 
     * @return valVocItem
     */
    public com.hyperborea.sira.ws.VoceDiVocabolario getValVocItem() {
        return valVocItem;
    }


    /**
     * Sets the valVocItem value for this VocabularyItemExtra.
     * 
     * @param valVocItem
     */
    public void setValVocItem(com.hyperborea.sira.ws.VoceDiVocabolario valVocItem) {
        this.valVocItem = valVocItem;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VocabularyItemExtra)) return false;
        VocabularyItemExtra other = (VocabularyItemExtra) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.propertyName==null && other.getPropertyName()==null) || 
             (this.propertyName!=null &&
              this.propertyName.equals(other.getPropertyName()))) &&
            ((this.valFloat==null && other.getValFloat()==null) || 
             (this.valFloat!=null &&
              this.valFloat.equals(other.getValFloat()))) &&
            ((this.valInteger==null && other.getValInteger()==null) || 
             (this.valInteger!=null &&
              this.valInteger.equals(other.getValInteger()))) &&
            ((this.valString==null && other.getValString()==null) || 
             (this.valString!=null &&
              this.valString.equals(other.getValString()))) &&
            ((this.valVocItem==null && other.getValVocItem()==null) || 
             (this.valVocItem!=null &&
              this.valVocItem.equals(other.getValVocItem())));
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
        if (getPropertyName() != null) {
            _hashCode += getPropertyName().hashCode();
        }
        if (getValFloat() != null) {
            _hashCode += getValFloat().hashCode();
        }
        if (getValInteger() != null) {
            _hashCode += getValInteger().hashCode();
        }
        if (getValString() != null) {
            _hashCode += getValString().hashCode();
        }
        if (getValVocItem() != null) {
            _hashCode += getValVocItem().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VocabularyItemExtra.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocabularyItemExtra"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("propertyName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "propertyName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valFloat");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valFloat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valInteger");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valInteger"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valString");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valString"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valVocItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valVocItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "voceDiVocabolario"));
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
