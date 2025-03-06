/**
 * WsNewUbicazioniOstResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsNewUbicazioniOstResponse  implements java.io.Serializable {
    private com.hyperborea.sira.ws.WsToken wsToken;

    private com.hyperborea.sira.ws.WsUbicazioniOstRef wsUbicazioniOstRef;

    public WsNewUbicazioniOstResponse() {
    }

    public WsNewUbicazioniOstResponse(
           com.hyperborea.sira.ws.WsToken wsToken,
           com.hyperborea.sira.ws.WsUbicazioniOstRef wsUbicazioniOstRef) {
           this.wsToken = wsToken;
           this.wsUbicazioniOstRef = wsUbicazioniOstRef;
    }


    /**
     * Gets the wsToken value for this WsNewUbicazioniOstResponse.
     * 
     * @return wsToken
     */
    public com.hyperborea.sira.ws.WsToken getWsToken() {
        return wsToken;
    }


    /**
     * Sets the wsToken value for this WsNewUbicazioniOstResponse.
     * 
     * @param wsToken
     */
    public void setWsToken(com.hyperborea.sira.ws.WsToken wsToken) {
        this.wsToken = wsToken;
    }


    /**
     * Gets the wsUbicazioniOstRef value for this WsNewUbicazioniOstResponse.
     * 
     * @return wsUbicazioniOstRef
     */
    public com.hyperborea.sira.ws.WsUbicazioniOstRef getWsUbicazioniOstRef() {
        return wsUbicazioniOstRef;
    }


    /**
     * Sets the wsUbicazioniOstRef value for this WsNewUbicazioniOstResponse.
     * 
     * @param wsUbicazioniOstRef
     */
    public void setWsUbicazioniOstRef(com.hyperborea.sira.ws.WsUbicazioniOstRef wsUbicazioniOstRef) {
        this.wsUbicazioniOstRef = wsUbicazioniOstRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsNewUbicazioniOstResponse)) return false;
        WsNewUbicazioniOstResponse other = (WsNewUbicazioniOstResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.wsToken==null && other.getWsToken()==null) || 
             (this.wsToken!=null &&
              this.wsToken.equals(other.getWsToken()))) &&
            ((this.wsUbicazioniOstRef==null && other.getWsUbicazioniOstRef()==null) || 
             (this.wsUbicazioniOstRef!=null &&
              this.wsUbicazioniOstRef.equals(other.getWsUbicazioniOstRef())));
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
        if (getWsToken() != null) {
            _hashCode += getWsToken().hashCode();
        }
        if (getWsUbicazioniOstRef() != null) {
            _hashCode += getWsUbicazioniOstRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsNewUbicazioniOstResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewUbicazioniOstResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsToken");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsToken"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsToken"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsUbicazioniOstRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsUbicazioniOstRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsUbicazioniOstRef"));
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
