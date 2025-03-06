/**
 * CostNostId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CostNostId  implements java.io.Serializable {
    private java.lang.Integer codCost;

    private java.lang.Integer codNost;

    public CostNostId() {
    }

    public CostNostId(
           java.lang.Integer codCost,
           java.lang.Integer codNost) {
           this.codCost = codCost;
           this.codNost = codNost;
    }


    /**
     * Gets the codCost value for this CostNostId.
     * 
     * @return codCost
     */
    public java.lang.Integer getCodCost() {
        return codCost;
    }


    /**
     * Sets the codCost value for this CostNostId.
     * 
     * @param codCost
     */
    public void setCodCost(java.lang.Integer codCost) {
        this.codCost = codCost;
    }


    /**
     * Gets the codNost value for this CostNostId.
     * 
     * @return codNost
     */
    public java.lang.Integer getCodNost() {
        return codNost;
    }


    /**
     * Sets the codNost value for this CostNostId.
     * 
     * @param codNost
     */
    public void setCodNost(java.lang.Integer codNost) {
        this.codNost = codNost;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CostNostId)) return false;
        CostNostId other = (CostNostId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codCost==null && other.getCodCost()==null) || 
             (this.codCost!=null &&
              this.codCost.equals(other.getCodCost()))) &&
            ((this.codNost==null && other.getCodNost()==null) || 
             (this.codNost!=null &&
              this.codNost.equals(other.getCodNost())));
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
        if (getCodCost() != null) {
            _hashCode += getCodCost().hashCode();
        }
        if (getCodNost() != null) {
            _hashCode += getCodNost().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CostNostId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "costNostId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codCost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codCost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codNost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codNost"));
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
