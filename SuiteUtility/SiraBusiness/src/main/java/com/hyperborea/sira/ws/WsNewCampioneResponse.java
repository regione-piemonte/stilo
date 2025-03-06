/**
 * WsNewCampioneResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsNewCampioneResponse  implements java.io.Serializable {
    private com.hyperborea.sira.ws.WsCampioneRef wsCampioneRef;

    public WsNewCampioneResponse() {
    }

    public WsNewCampioneResponse(
           com.hyperborea.sira.ws.WsCampioneRef wsCampioneRef) {
           this.wsCampioneRef = wsCampioneRef;
    }


    /**
     * Gets the wsCampioneRef value for this WsNewCampioneResponse.
     * 
     * @return wsCampioneRef
     */
    public com.hyperborea.sira.ws.WsCampioneRef getWsCampioneRef() {
        return wsCampioneRef;
    }


    /**
     * Sets the wsCampioneRef value for this WsNewCampioneResponse.
     * 
     * @param wsCampioneRef
     */
    public void setWsCampioneRef(com.hyperborea.sira.ws.WsCampioneRef wsCampioneRef) {
        this.wsCampioneRef = wsCampioneRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsNewCampioneResponse)) return false;
        WsNewCampioneResponse other = (WsNewCampioneResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.wsCampioneRef==null && other.getWsCampioneRef()==null) || 
             (this.wsCampioneRef!=null &&
              this.wsCampioneRef.equals(other.getWsCampioneRef())));
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
        if (getWsCampioneRef() != null) {
            _hashCode += getWsCampioneRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsNewCampioneResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewCampioneResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsCampioneRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsCampioneRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCampioneRef"));
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
