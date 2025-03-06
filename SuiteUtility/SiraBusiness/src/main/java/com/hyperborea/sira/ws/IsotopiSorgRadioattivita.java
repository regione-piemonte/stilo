/**
 * IsotopiSorgRadioattivita.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class IsotopiSorgRadioattivita  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer id;

    private com.hyperborea.sira.ws.VocIsotopo vocIsotopo;

    public IsotopiSorgRadioattivita() {
    }

    public IsotopiSorgRadioattivita(
           java.lang.Integer id,
           com.hyperborea.sira.ws.VocIsotopo vocIsotopo) {
        this.id = id;
        this.vocIsotopo = vocIsotopo;
    }


    /**
     * Gets the id value for this IsotopiSorgRadioattivita.
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }


    /**
     * Sets the id value for this IsotopiSorgRadioattivita.
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }


    /**
     * Gets the vocIsotopo value for this IsotopiSorgRadioattivita.
     * 
     * @return vocIsotopo
     */
    public com.hyperborea.sira.ws.VocIsotopo getVocIsotopo() {
        return vocIsotopo;
    }


    /**
     * Sets the vocIsotopo value for this IsotopiSorgRadioattivita.
     * 
     * @param vocIsotopo
     */
    public void setVocIsotopo(com.hyperborea.sira.ws.VocIsotopo vocIsotopo) {
        this.vocIsotopo = vocIsotopo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IsotopiSorgRadioattivita)) return false;
        IsotopiSorgRadioattivita other = (IsotopiSorgRadioattivita) obj;
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
            ((this.vocIsotopo==null && other.getVocIsotopo()==null) || 
             (this.vocIsotopo!=null &&
              this.vocIsotopo.equals(other.getVocIsotopo())));
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
        if (getVocIsotopo() != null) {
            _hashCode += getVocIsotopo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IsotopiSorgRadioattivita.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "isotopiSorgRadioattivita"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocIsotopo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocIsotopo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocIsotopo"));
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
