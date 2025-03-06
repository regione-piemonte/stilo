/**
 * WsCountSoggettiFisiciRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsCountSoggettiFisiciRequest  implements java.io.Serializable {
    private com.hyperborea.sira.ws.SoggettiFisiciSearchCommand command;

    private com.hyperborea.sira.ws.WsToken wsToken;

    public WsCountSoggettiFisiciRequest() {
    }

    public WsCountSoggettiFisiciRequest(
           com.hyperborea.sira.ws.SoggettiFisiciSearchCommand command,
           com.hyperborea.sira.ws.WsToken wsToken) {
           this.command = command;
           this.wsToken = wsToken;
    }


    /**
     * Gets the command value for this WsCountSoggettiFisiciRequest.
     * 
     * @return command
     */
    public com.hyperborea.sira.ws.SoggettiFisiciSearchCommand getCommand() {
        return command;
    }


    /**
     * Sets the command value for this WsCountSoggettiFisiciRequest.
     * 
     * @param command
     */
    public void setCommand(com.hyperborea.sira.ws.SoggettiFisiciSearchCommand command) {
        this.command = command;
    }


    /**
     * Gets the wsToken value for this WsCountSoggettiFisiciRequest.
     * 
     * @return wsToken
     */
    public com.hyperborea.sira.ws.WsToken getWsToken() {
        return wsToken;
    }


    /**
     * Sets the wsToken value for this WsCountSoggettiFisiciRequest.
     * 
     * @param wsToken
     */
    public void setWsToken(com.hyperborea.sira.ws.WsToken wsToken) {
        this.wsToken = wsToken;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsCountSoggettiFisiciRequest)) return false;
        WsCountSoggettiFisiciRequest other = (WsCountSoggettiFisiciRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.command==null && other.getCommand()==null) || 
             (this.command!=null &&
              this.command.equals(other.getCommand()))) &&
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
        if (getCommand() != null) {
            _hashCode += getCommand().hashCode();
        }
        if (getWsToken() != null) {
            _hashCode += getWsToken().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsCountSoggettiFisiciRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCountSoggettiFisiciRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("command");
        elemField.setXmlName(new javax.xml.namespace.QName("", "command"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "soggettiFisiciSearchCommand"));
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
