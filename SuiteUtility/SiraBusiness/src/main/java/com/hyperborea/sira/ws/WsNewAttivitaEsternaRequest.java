/**
 * WsNewAttivitaEsternaRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsNewAttivitaEsternaRequest  implements java.io.Serializable {
    private com.hyperborea.sira.ws.AttivitaEsterna attivitaEsterna;

    private com.hyperborea.sira.ws.WsToken wsToken;

    public WsNewAttivitaEsternaRequest() {
    }

    public WsNewAttivitaEsternaRequest(
           com.hyperborea.sira.ws.AttivitaEsterna attivitaEsterna,
           com.hyperborea.sira.ws.WsToken wsToken) {
           this.attivitaEsterna = attivitaEsterna;
           this.wsToken = wsToken;
    }


    /**
     * Gets the attivitaEsterna value for this WsNewAttivitaEsternaRequest.
     * 
     * @return attivitaEsterna
     */
    public com.hyperborea.sira.ws.AttivitaEsterna getAttivitaEsterna() {
        return attivitaEsterna;
    }


    /**
     * Sets the attivitaEsterna value for this WsNewAttivitaEsternaRequest.
     * 
     * @param attivitaEsterna
     */
    public void setAttivitaEsterna(com.hyperborea.sira.ws.AttivitaEsterna attivitaEsterna) {
        this.attivitaEsterna = attivitaEsterna;
    }


    /**
     * Gets the wsToken value for this WsNewAttivitaEsternaRequest.
     * 
     * @return wsToken
     */
    public com.hyperborea.sira.ws.WsToken getWsToken() {
        return wsToken;
    }


    /**
     * Sets the wsToken value for this WsNewAttivitaEsternaRequest.
     * 
     * @param wsToken
     */
    public void setWsToken(com.hyperborea.sira.ws.WsToken wsToken) {
        this.wsToken = wsToken;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsNewAttivitaEsternaRequest)) return false;
        WsNewAttivitaEsternaRequest other = (WsNewAttivitaEsternaRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.attivitaEsterna==null && other.getAttivitaEsterna()==null) || 
             (this.attivitaEsterna!=null &&
              this.attivitaEsterna.equals(other.getAttivitaEsterna()))) &&
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
        if (getAttivitaEsterna() != null) {
            _hashCode += getAttivitaEsterna().hashCode();
        }
        if (getWsToken() != null) {
            _hashCode += getWsToken().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsNewAttivitaEsternaRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewAttivitaEsternaRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attivitaEsterna");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attivitaEsterna"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attivitaEsterna"));
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
