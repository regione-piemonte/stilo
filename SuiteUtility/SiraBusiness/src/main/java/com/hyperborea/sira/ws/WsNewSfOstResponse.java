/**
 * WsNewSfOstResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsNewSfOstResponse  implements java.io.Serializable {
    private com.hyperborea.sira.ws.SfOst sfOst;

    public WsNewSfOstResponse() {
    }

    public WsNewSfOstResponse(
           com.hyperborea.sira.ws.SfOst sfOst) {
           this.sfOst = sfOst;
    }


    /**
     * Gets the sfOst value for this WsNewSfOstResponse.
     * 
     * @return sfOst
     */
    public com.hyperborea.sira.ws.SfOst getSfOst() {
        return sfOst;
    }


    /**
     * Sets the sfOst value for this WsNewSfOstResponse.
     * 
     * @param sfOst
     */
    public void setSfOst(com.hyperborea.sira.ws.SfOst sfOst) {
        this.sfOst = sfOst;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsNewSfOstResponse)) return false;
        WsNewSfOstResponse other = (WsNewSfOstResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sfOst==null && other.getSfOst()==null) || 
             (this.sfOst!=null &&
              this.sfOst.equals(other.getSfOst())));
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
        if (getSfOst() != null) {
            _hashCode += getSfOst().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsNewSfOstResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewSfOstResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sfOst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sfOst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sfOst"));
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
