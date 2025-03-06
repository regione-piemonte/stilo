/**
 * PemFat.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class PemFat  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String dispositivoTecnico;

    private com.hyperborea.sira.ws.PemFatId id;

    public PemFat() {
    }

    public PemFat(
           java.lang.String dispositivoTecnico,
           com.hyperborea.sira.ws.PemFatId id) {
        this.dispositivoTecnico = dispositivoTecnico;
        this.id = id;
    }


    /**
     * Gets the dispositivoTecnico value for this PemFat.
     * 
     * @return dispositivoTecnico
     */
    public java.lang.String getDispositivoTecnico() {
        return dispositivoTecnico;
    }


    /**
     * Sets the dispositivoTecnico value for this PemFat.
     * 
     * @param dispositivoTecnico
     */
    public void setDispositivoTecnico(java.lang.String dispositivoTecnico) {
        this.dispositivoTecnico = dispositivoTecnico;
    }


    /**
     * Gets the id value for this PemFat.
     * 
     * @return id
     */
    public com.hyperborea.sira.ws.PemFatId getId() {
        return id;
    }


    /**
     * Sets the id value for this PemFat.
     * 
     * @param id
     */
    public void setId(com.hyperborea.sira.ws.PemFatId id) {
        this.id = id;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PemFat)) return false;
        PemFat other = (PemFat) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.dispositivoTecnico==null && other.getDispositivoTecnico()==null) || 
             (this.dispositivoTecnico!=null &&
              this.dispositivoTecnico.equals(other.getDispositivoTecnico()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId())));
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
        if (getDispositivoTecnico() != null) {
            _hashCode += getDispositivoTecnico().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PemFat.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pemFat"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dispositivoTecnico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dispositivoTecnico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pemFatId"));
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
