/**
 * WsVisitedOst.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsVisitedOst  implements java.io.Serializable {
    private com.hyperborea.sira.ws.WsCcostRef wsCcostRef;

    private com.hyperborea.sira.ws.WsOstRef wsOstRef;

    public WsVisitedOst() {
    }

    public WsVisitedOst(
           com.hyperborea.sira.ws.WsCcostRef wsCcostRef,
           com.hyperborea.sira.ws.WsOstRef wsOstRef) {
           this.wsCcostRef = wsCcostRef;
           this.wsOstRef = wsOstRef;
    }


    /**
     * Gets the wsCcostRef value for this WsVisitedOst.
     * 
     * @return wsCcostRef
     */
    public com.hyperborea.sira.ws.WsCcostRef getWsCcostRef() {
        return wsCcostRef;
    }


    /**
     * Sets the wsCcostRef value for this WsVisitedOst.
     * 
     * @param wsCcostRef
     */
    public void setWsCcostRef(com.hyperborea.sira.ws.WsCcostRef wsCcostRef) {
        this.wsCcostRef = wsCcostRef;
    }


    /**
     * Gets the wsOstRef value for this WsVisitedOst.
     * 
     * @return wsOstRef
     */
    public com.hyperborea.sira.ws.WsOstRef getWsOstRef() {
        return wsOstRef;
    }


    /**
     * Sets the wsOstRef value for this WsVisitedOst.
     * 
     * @param wsOstRef
     */
    public void setWsOstRef(com.hyperborea.sira.ws.WsOstRef wsOstRef) {
        this.wsOstRef = wsOstRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsVisitedOst)) return false;
        WsVisitedOst other = (WsVisitedOst) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.wsCcostRef==null && other.getWsCcostRef()==null) || 
             (this.wsCcostRef!=null &&
              this.wsCcostRef.equals(other.getWsCcostRef()))) &&
            ((this.wsOstRef==null && other.getWsOstRef()==null) || 
             (this.wsOstRef!=null &&
              this.wsOstRef.equals(other.getWsOstRef())));
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
        if (getWsCcostRef() != null) {
            _hashCode += getWsCcostRef().hashCode();
        }
        if (getWsOstRef() != null) {
            _hashCode += getWsOstRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsVisitedOst.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsVisitedOst"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsCcostRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsCcostRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCcostRef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsOstRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsOstRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsOstRef"));
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
