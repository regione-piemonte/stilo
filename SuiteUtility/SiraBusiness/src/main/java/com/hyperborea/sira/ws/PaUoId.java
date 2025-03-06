/**
 * PaUoId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class PaUoId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.PraticheAmministrative praticheAmministrative;

    private com.hyperborea.sira.ws.UnitaOrganizzative unitaOrganizzative;

    public PaUoId() {
    }

    public PaUoId(
           com.hyperborea.sira.ws.PraticheAmministrative praticheAmministrative,
           com.hyperborea.sira.ws.UnitaOrganizzative unitaOrganizzative) {
           this.praticheAmministrative = praticheAmministrative;
           this.unitaOrganizzative = unitaOrganizzative;
    }


    /**
     * Gets the praticheAmministrative value for this PaUoId.
     * 
     * @return praticheAmministrative
     */
    public com.hyperborea.sira.ws.PraticheAmministrative getPraticheAmministrative() {
        return praticheAmministrative;
    }


    /**
     * Sets the praticheAmministrative value for this PaUoId.
     * 
     * @param praticheAmministrative
     */
    public void setPraticheAmministrative(com.hyperborea.sira.ws.PraticheAmministrative praticheAmministrative) {
        this.praticheAmministrative = praticheAmministrative;
    }


    /**
     * Gets the unitaOrganizzative value for this PaUoId.
     * 
     * @return unitaOrganizzative
     */
    public com.hyperborea.sira.ws.UnitaOrganizzative getUnitaOrganizzative() {
        return unitaOrganizzative;
    }


    /**
     * Sets the unitaOrganizzative value for this PaUoId.
     * 
     * @param unitaOrganizzative
     */
    public void setUnitaOrganizzative(com.hyperborea.sira.ws.UnitaOrganizzative unitaOrganizzative) {
        this.unitaOrganizzative = unitaOrganizzative;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PaUoId)) return false;
        PaUoId other = (PaUoId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.praticheAmministrative==null && other.getPraticheAmministrative()==null) || 
             (this.praticheAmministrative!=null &&
              this.praticheAmministrative.equals(other.getPraticheAmministrative()))) &&
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
        if (getPraticheAmministrative() != null) {
            _hashCode += getPraticheAmministrative().hashCode();
        }
        if (getUnitaOrganizzative() != null) {
            _hashCode += getUnitaOrganizzative().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PaUoId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "paUoId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("praticheAmministrative");
        elemField.setXmlName(new javax.xml.namespace.QName("", "praticheAmministrative"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "praticheAmministrative"));
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
