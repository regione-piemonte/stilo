/**
 * RifiutiGestitiId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class RifiutiGestitiId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.RifiutiCer rifiutiCer;

    public RifiutiGestitiId() {
    }

    public RifiutiGestitiId(
           com.hyperborea.sira.ws.RifiutiCer rifiutiCer) {
           this.rifiutiCer = rifiutiCer;
    }


    /**
     * Gets the rifiutiCer value for this RifiutiGestitiId.
     * 
     * @return rifiutiCer
     */
    public com.hyperborea.sira.ws.RifiutiCer getRifiutiCer() {
        return rifiutiCer;
    }


    /**
     * Sets the rifiutiCer value for this RifiutiGestitiId.
     * 
     * @param rifiutiCer
     */
    public void setRifiutiCer(com.hyperborea.sira.ws.RifiutiCer rifiutiCer) {
        this.rifiutiCer = rifiutiCer;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RifiutiGestitiId)) return false;
        RifiutiGestitiId other = (RifiutiGestitiId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rifiutiCer==null && other.getRifiutiCer()==null) || 
             (this.rifiutiCer!=null &&
              this.rifiutiCer.equals(other.getRifiutiCer())));
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
        if (getRifiutiCer() != null) {
            _hashCode += getRifiutiCer().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RifiutiGestitiId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "rifiutiGestitiId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rifiutiCer");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rifiutiCer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "rifiutiCer"));
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
