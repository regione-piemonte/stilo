/**
 * WsStatiOstRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsStatiOstRequest  implements java.io.Serializable {
    private com.hyperborea.sira.ws.StatiOst statiOst;

    private com.hyperborea.sira.ws.WsToken wsToken;

    public WsStatiOstRequest() {
    }

    public WsStatiOstRequest(
           com.hyperborea.sira.ws.StatiOst statiOst,
           com.hyperborea.sira.ws.WsToken wsToken) {
           this.statiOst = statiOst;
           this.wsToken = wsToken;
    }


    /**
     * Gets the statiOst value for this WsStatiOstRequest.
     * 
     * @return statiOst
     */
    public com.hyperborea.sira.ws.StatiOst getStatiOst() {
        return statiOst;
    }


    /**
     * Sets the statiOst value for this WsStatiOstRequest.
     * 
     * @param statiOst
     */
    public void setStatiOst(com.hyperborea.sira.ws.StatiOst statiOst) {
        this.statiOst = statiOst;
    }


    /**
     * Gets the wsToken value for this WsStatiOstRequest.
     * 
     * @return wsToken
     */
    public com.hyperborea.sira.ws.WsToken getWsToken() {
        return wsToken;
    }


    /**
     * Sets the wsToken value for this WsStatiOstRequest.
     * 
     * @param wsToken
     */
    public void setWsToken(com.hyperborea.sira.ws.WsToken wsToken) {
        this.wsToken = wsToken;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsStatiOstRequest)) return false;
        WsStatiOstRequest other = (WsStatiOstRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.statiOst==null && other.getStatiOst()==null) || 
             (this.statiOst!=null &&
              this.statiOst.equals(other.getStatiOst()))) &&
            ((this.wsToken==null && other.getWsToken()==null) || 
             (this.wsToken!=null &&
              this.wsToken.equals(other.getWsToken())));
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
        if (getStatiOst() != null) {
            _hashCode += getStatiOst().hashCode();
        }
        if (getWsToken() != null) {
            _hashCode += getWsToken().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsStatiOstRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsStatiOstRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statiOst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statiOst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "statiOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsToken");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsToken"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsToken"));
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
