/**
 * CcostAnySearchIntegerRangeCriteria.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostAnySearchIntegerRangeCriteria  extends com.hyperborea.sira.ws.CcostAnySearchCriteria  implements java.io.Serializable {
    private java.lang.String fieldName;

    private java.lang.Integer fieldValueFrom;

    private java.lang.Integer fieldValueTo;

    public CcostAnySearchIntegerRangeCriteria() {
    }

    public CcostAnySearchIntegerRangeCriteria(
           java.lang.String fieldName,
           java.lang.Integer fieldValueFrom,
           java.lang.Integer fieldValueTo) {
        this.fieldName = fieldName;
        this.fieldValueFrom = fieldValueFrom;
        this.fieldValueTo = fieldValueTo;
    }


    /**
     * Gets the fieldName value for this CcostAnySearchIntegerRangeCriteria.
     * 
     * @return fieldName
     */
    public java.lang.String getFieldName() {
        return fieldName;
    }


    /**
     * Sets the fieldName value for this CcostAnySearchIntegerRangeCriteria.
     * 
     * @param fieldName
     */
    public void setFieldName(java.lang.String fieldName) {
        this.fieldName = fieldName;
    }


    /**
     * Gets the fieldValueFrom value for this CcostAnySearchIntegerRangeCriteria.
     * 
     * @return fieldValueFrom
     */
    public java.lang.Integer getFieldValueFrom() {
        return fieldValueFrom;
    }


    /**
     * Sets the fieldValueFrom value for this CcostAnySearchIntegerRangeCriteria.
     * 
     * @param fieldValueFrom
     */
    public void setFieldValueFrom(java.lang.Integer fieldValueFrom) {
        this.fieldValueFrom = fieldValueFrom;
    }


    /**
     * Gets the fieldValueTo value for this CcostAnySearchIntegerRangeCriteria.
     * 
     * @return fieldValueTo
     */
    public java.lang.Integer getFieldValueTo() {
        return fieldValueTo;
    }


    /**
     * Sets the fieldValueTo value for this CcostAnySearchIntegerRangeCriteria.
     * 
     * @param fieldValueTo
     */
    public void setFieldValueTo(java.lang.Integer fieldValueTo) {
        this.fieldValueTo = fieldValueTo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostAnySearchIntegerRangeCriteria)) return false;
        CcostAnySearchIntegerRangeCriteria other = (CcostAnySearchIntegerRangeCriteria) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.fieldName==null && other.getFieldName()==null) || 
             (this.fieldName!=null &&
              this.fieldName.equals(other.getFieldName()))) &&
            ((this.fieldValueFrom==null && other.getFieldValueFrom()==null) || 
             (this.fieldValueFrom!=null &&
              this.fieldValueFrom.equals(other.getFieldValueFrom()))) &&
            ((this.fieldValueTo==null && other.getFieldValueTo()==null) || 
             (this.fieldValueTo!=null &&
              this.fieldValueTo.equals(other.getFieldValueTo())));
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
        if (getFieldName() != null) {
            _hashCode += getFieldName().hashCode();
        }
        if (getFieldValueFrom() != null) {
            _hashCode += getFieldValueFrom().hashCode();
        }
        if (getFieldValueTo() != null) {
            _hashCode += getFieldValueTo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostAnySearchIntegerRangeCriteria.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchIntegerRangeCriteria"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fieldName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldValueFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fieldValueFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fieldValueTo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fieldValueTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
