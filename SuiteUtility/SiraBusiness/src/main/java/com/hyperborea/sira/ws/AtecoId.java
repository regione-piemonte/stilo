/**
 * AtecoId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class AtecoId  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String ateco;

    private java.lang.String elenco;

    public AtecoId() {
    }

    public AtecoId(
           java.lang.String ateco,
           java.lang.String elenco) {
        this.ateco = ateco;
        this.elenco = elenco;
    }


    /**
     * Gets the ateco value for this AtecoId.
     * 
     * @return ateco
     */
    public java.lang.String getAteco() {
        return ateco;
    }


    /**
     * Sets the ateco value for this AtecoId.
     * 
     * @param ateco
     */
    public void setAteco(java.lang.String ateco) {
        this.ateco = ateco;
    }


    /**
     * Gets the elenco value for this AtecoId.
     * 
     * @return elenco
     */
    public java.lang.String getElenco() {
        return elenco;
    }


    /**
     * Sets the elenco value for this AtecoId.
     * 
     * @param elenco
     */
    public void setElenco(java.lang.String elenco) {
        this.elenco = elenco;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AtecoId)) return false;
        AtecoId other = (AtecoId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.ateco==null && other.getAteco()==null) || 
             (this.ateco!=null &&
              this.ateco.equals(other.getAteco()))) &&
            ((this.elenco==null && other.getElenco()==null) || 
             (this.elenco!=null &&
              this.elenco.equals(other.getElenco())));
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
        if (getAteco() != null) {
            _hashCode += getAteco().hashCode();
        }
        if (getElenco() != null) {
            _hashCode += getElenco().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AtecoId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "atecoId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ateco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ateco"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elenco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "elenco"));
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
