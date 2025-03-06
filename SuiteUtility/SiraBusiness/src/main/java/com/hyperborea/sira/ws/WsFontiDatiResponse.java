/**
 * WsFontiDatiResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsFontiDatiResponse  implements java.io.Serializable {
    private com.hyperborea.sira.ws.WsFontiDatiRef wsFontiDatiRef;

    public WsFontiDatiResponse() {
    }

    public WsFontiDatiResponse(
           com.hyperborea.sira.ws.WsFontiDatiRef wsFontiDatiRef) {
           this.wsFontiDatiRef = wsFontiDatiRef;
    }


    /**
     * Gets the wsFontiDatiRef value for this WsFontiDatiResponse.
     * 
     * @return wsFontiDatiRef
     */
    public com.hyperborea.sira.ws.WsFontiDatiRef getWsFontiDatiRef() {
        return wsFontiDatiRef;
    }


    /**
     * Sets the wsFontiDatiRef value for this WsFontiDatiResponse.
     * 
     * @param wsFontiDatiRef
     */
    public void setWsFontiDatiRef(com.hyperborea.sira.ws.WsFontiDatiRef wsFontiDatiRef) {
        this.wsFontiDatiRef = wsFontiDatiRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsFontiDatiResponse)) return false;
        WsFontiDatiResponse other = (WsFontiDatiResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.wsFontiDatiRef==null && other.getWsFontiDatiRef()==null) || 
             (this.wsFontiDatiRef!=null &&
              this.wsFontiDatiRef.equals(other.getWsFontiDatiRef())));
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
        if (getWsFontiDatiRef() != null) {
            _hashCode += getWsFontiDatiRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsFontiDatiResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsFontiDatiResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsFontiDatiRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsFontiDatiRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsFontiDatiRef"));
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
