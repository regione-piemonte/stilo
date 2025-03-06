/**
 * RelSottoschede.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class RelSottoschede  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.RelSottoschedePK id;

    private java.lang.String nomeSottoscheda;

    public RelSottoschede() {
    }

    public RelSottoschede(
           com.hyperborea.sira.ws.RelSottoschedePK id,
           java.lang.String nomeSottoscheda) {
        this.id = id;
        this.nomeSottoscheda = nomeSottoscheda;
    }


    /**
     * Gets the id value for this RelSottoschede.
     * 
     * @return id
     */
    public com.hyperborea.sira.ws.RelSottoschedePK getId() {
        return id;
    }


    /**
     * Sets the id value for this RelSottoschede.
     * 
     * @param id
     */
    public void setId(com.hyperborea.sira.ws.RelSottoschedePK id) {
        this.id = id;
    }


    /**
     * Gets the nomeSottoscheda value for this RelSottoschede.
     * 
     * @return nomeSottoscheda
     */
    public java.lang.String getNomeSottoscheda() {
        return nomeSottoscheda;
    }


    /**
     * Sets the nomeSottoscheda value for this RelSottoschede.
     * 
     * @param nomeSottoscheda
     */
    public void setNomeSottoscheda(java.lang.String nomeSottoscheda) {
        this.nomeSottoscheda = nomeSottoscheda;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RelSottoschede)) return false;
        RelSottoschede other = (RelSottoschede) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.nomeSottoscheda==null && other.getNomeSottoscheda()==null) || 
             (this.nomeSottoscheda!=null &&
              this.nomeSottoscheda.equals(other.getNomeSottoscheda())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getNomeSottoscheda() != null) {
            _hashCode += getNomeSottoscheda().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RelSottoschede.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relSottoschede"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relSottoschedePK"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeSottoscheda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeSottoscheda"));
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
