/**
 * WsGetTipoCampioneRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsGetTipoCampioneRequest  implements java.io.Serializable {
    private com.hyperborea.sira.ws.WsTipoCampioneRef wsTipoCampioneRef;

    public WsGetTipoCampioneRequest() {
    }

    public WsGetTipoCampioneRequest(
           com.hyperborea.sira.ws.WsTipoCampioneRef wsTipoCampioneRef) {
           this.wsTipoCampioneRef = wsTipoCampioneRef;
    }


    /**
     * Gets the wsTipoCampioneRef value for this WsGetTipoCampioneRequest.
     * 
     * @return wsTipoCampioneRef
     */
    public com.hyperborea.sira.ws.WsTipoCampioneRef getWsTipoCampioneRef() {
        return wsTipoCampioneRef;
    }


    /**
     * Sets the wsTipoCampioneRef value for this WsGetTipoCampioneRequest.
     * 
     * @param wsTipoCampioneRef
     */
    public void setWsTipoCampioneRef(com.hyperborea.sira.ws.WsTipoCampioneRef wsTipoCampioneRef) {
        this.wsTipoCampioneRef = wsTipoCampioneRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsGetTipoCampioneRequest)) return false;
        WsGetTipoCampioneRequest other = (WsGetTipoCampioneRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.wsTipoCampioneRef==null && other.getWsTipoCampioneRef()==null) || 
             (this.wsTipoCampioneRef!=null &&
              this.wsTipoCampioneRef.equals(other.getWsTipoCampioneRef())));
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
        if (getWsTipoCampioneRef() != null) {
            _hashCode += getWsTipoCampioneRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsGetTipoCampioneRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipoCampioneRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsTipoCampioneRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsTipoCampioneRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTipoCampioneRef"));
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
