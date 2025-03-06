/**
 * WsAttivitaEsternaRef.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsAttivitaEsternaRef  implements java.io.Serializable {
    private java.lang.Integer idAttivitaEsterna;

    public WsAttivitaEsternaRef() {
    }

    public WsAttivitaEsternaRef(
           java.lang.Integer idAttivitaEsterna) {
           this.idAttivitaEsterna = idAttivitaEsterna;
    }


    /**
     * Gets the idAttivitaEsterna value for this WsAttivitaEsternaRef.
     * 
     * @return idAttivitaEsterna
     */
    public java.lang.Integer getIdAttivitaEsterna() {
        return idAttivitaEsterna;
    }


    /**
     * Sets the idAttivitaEsterna value for this WsAttivitaEsternaRef.
     * 
     * @param idAttivitaEsterna
     */
    public void setIdAttivitaEsterna(java.lang.Integer idAttivitaEsterna) {
        this.idAttivitaEsterna = idAttivitaEsterna;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsAttivitaEsternaRef)) return false;
        WsAttivitaEsternaRef other = (WsAttivitaEsternaRef) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idAttivitaEsterna==null && other.getIdAttivitaEsterna()==null) || 
             (this.idAttivitaEsterna!=null &&
              this.idAttivitaEsterna.equals(other.getIdAttivitaEsterna())));
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
        if (getIdAttivitaEsterna() != null) {
            _hashCode += getIdAttivitaEsterna().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsAttivitaEsternaRef.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsAttivitaEsternaRef"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAttivitaEsterna");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idAttivitaEsterna"));
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
