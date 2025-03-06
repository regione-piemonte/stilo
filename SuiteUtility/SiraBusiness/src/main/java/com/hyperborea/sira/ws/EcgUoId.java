/**
 * EcgUoId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class EcgUoId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGoverno;

    private com.hyperborea.sira.ws.UnitaOrganizzative unitaOrganizzative;

    public EcgUoId() {
    }

    public EcgUoId(
           com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGoverno,
           com.hyperborea.sira.ws.UnitaOrganizzative unitaOrganizzative) {
           this.entiControlloGoverno = entiControlloGoverno;
           this.unitaOrganizzative = unitaOrganizzative;
    }


    /**
     * Gets the entiControlloGoverno value for this EcgUoId.
     * 
     * @return entiControlloGoverno
     */
    public com.hyperborea.sira.ws.EntiControlloGoverno getEntiControlloGoverno() {
        return entiControlloGoverno;
    }


    /**
     * Sets the entiControlloGoverno value for this EcgUoId.
     * 
     * @param entiControlloGoverno
     */
    public void setEntiControlloGoverno(com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGoverno) {
        this.entiControlloGoverno = entiControlloGoverno;
    }


    /**
     * Gets the unitaOrganizzative value for this EcgUoId.
     * 
     * @return unitaOrganizzative
     */
    public com.hyperborea.sira.ws.UnitaOrganizzative getUnitaOrganizzative() {
        return unitaOrganizzative;
    }


    /**
     * Sets the unitaOrganizzative value for this EcgUoId.
     * 
     * @param unitaOrganizzative
     */
    public void setUnitaOrganizzative(com.hyperborea.sira.ws.UnitaOrganizzative unitaOrganizzative) {
        this.unitaOrganizzative = unitaOrganizzative;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EcgUoId)) return false;
        EcgUoId other = (EcgUoId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.entiControlloGoverno==null && other.getEntiControlloGoverno()==null) || 
             (this.entiControlloGoverno!=null &&
              this.entiControlloGoverno.equals(other.getEntiControlloGoverno()))) &&
            ((this.unitaOrganizzative==null && other.getUnitaOrganizzative()==null) || 
             (this.unitaOrganizzative!=null &&
              this.unitaOrganizzative.equals(other.getUnitaOrganizzative())));
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
        if (getEntiControlloGoverno() != null) {
            _hashCode += getEntiControlloGoverno().hashCode();
        }
        if (getUnitaOrganizzative() != null) {
            _hashCode += getUnitaOrganizzative().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EcgUoId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ecgUoId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entiControlloGoverno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entiControlloGoverno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "entiControlloGoverno"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitaOrganizzative");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unitaOrganizzative"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "unitaOrganizzative"));
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
