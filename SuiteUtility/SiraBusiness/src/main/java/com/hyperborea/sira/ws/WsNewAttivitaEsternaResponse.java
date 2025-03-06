/**
 * WsNewAttivitaEsternaResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsNewAttivitaEsternaResponse  implements java.io.Serializable {
    private com.hyperborea.sira.ws.WsAttivitaEsternaRef wsAttivitaEsternaRef;

    private com.hyperborea.sira.ws.WsToken wsToken;

    public WsNewAttivitaEsternaResponse() {
    }

    public WsNewAttivitaEsternaResponse(
           com.hyperborea.sira.ws.WsAttivitaEsternaRef wsAttivitaEsternaRef,
           com.hyperborea.sira.ws.WsToken wsToken) {
           this.wsAttivitaEsternaRef = wsAttivitaEsternaRef;
           this.wsToken = wsToken;
    }


    /**
     * Gets the wsAttivitaEsternaRef value for this WsNewAttivitaEsternaResponse.
     * 
     * @return wsAttivitaEsternaRef
     */
    public com.hyperborea.sira.ws.WsAttivitaEsternaRef getWsAttivitaEsternaRef() {
        return wsAttivitaEsternaRef;
    }


    /**
     * Sets the wsAttivitaEsternaRef value for this WsNewAttivitaEsternaResponse.
     * 
     * @param wsAttivitaEsternaRef
     */
    public void setWsAttivitaEsternaRef(com.hyperborea.sira.ws.WsAttivitaEsternaRef wsAttivitaEsternaRef) {
        this.wsAttivitaEsternaRef = wsAttivitaEsternaRef;
    }


    /**
     * Gets the wsToken value for this WsNewAttivitaEsternaResponse.
     * 
     * @return wsToken
     */
    public com.hyperborea.sira.ws.WsToken getWsToken() {
        return wsToken;
    }


    /**
     * Sets the wsToken value for this WsNewAttivitaEsternaResponse.
     * 
     * @param wsToken
     */
    public void setWsToken(com.hyperborea.sira.ws.WsToken wsToken) {
        this.wsToken = wsToken;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsNewAttivitaEsternaResponse)) return false;
        WsNewAttivitaEsternaResponse other = (WsNewAttivitaEsternaResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.wsAttivitaEsternaRef==null && other.getWsAttivitaEsternaRef()==null) || 
             (this.wsAttivitaEsternaRef!=null &&
              this.wsAttivitaEsternaRef.equals(other.getWsAttivitaEsternaRef()))) &&
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
        if (getWsAttivitaEsternaRef() != null) {
            _hashCode += getWsAttivitaEsternaRef().hashCode();
        }
        if (getWsToken() != null) {
            _hashCode += getWsToken().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsNewAttivitaEsternaResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewAttivitaEsternaResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsAttivitaEsternaRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsAttivitaEsternaRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsAttivitaEsternaRef"));
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
