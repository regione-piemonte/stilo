/**
 * WsFontiDatiRef.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsFontiDatiRef  implements java.io.Serializable {
    private java.lang.Integer idFontiDati;

    public WsFontiDatiRef() {
    }

    public WsFontiDatiRef(
           java.lang.Integer idFontiDati) {
           this.idFontiDati = idFontiDati;
    }


    /**
     * Gets the idFontiDati value for this WsFontiDatiRef.
     * 
     * @return idFontiDati
     */
    public java.lang.Integer getIdFontiDati() {
        return idFontiDati;
    }


    /**
     * Sets the idFontiDati value for this WsFontiDatiRef.
     * 
     * @param idFontiDati
     */
    public void setIdFontiDati(java.lang.Integer idFontiDati) {
        this.idFontiDati = idFontiDati;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsFontiDatiRef)) return false;
        WsFontiDatiRef other = (WsFontiDatiRef) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idFontiDati==null && other.getIdFontiDati()==null) || 
             (this.idFontiDati!=null &&
              this.idFontiDati.equals(other.getIdFontiDati())));
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
        if (getIdFontiDati() != null) {
            _hashCode += getIdFontiDati().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsFontiDatiRef.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsFontiDatiRef"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFontiDati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idFontiDati"));
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
