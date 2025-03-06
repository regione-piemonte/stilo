/**
 * WsViewFonteResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsViewFonteResponse  implements java.io.Serializable {
    private com.hyperborea.sira.ws.WsFonteGenerica wsFonteGenerica;

    public WsViewFonteResponse() {
    }

    public WsViewFonteResponse(
           com.hyperborea.sira.ws.WsFonteGenerica wsFonteGenerica) {
           this.wsFonteGenerica = wsFonteGenerica;
    }


    /**
     * Gets the wsFonteGenerica value for this WsViewFonteResponse.
     * 
     * @return wsFonteGenerica
     */
    public com.hyperborea.sira.ws.WsFonteGenerica getWsFonteGenerica() {
        return wsFonteGenerica;
    }


    /**
     * Sets the wsFonteGenerica value for this WsViewFonteResponse.
     * 
     * @param wsFonteGenerica
     */
    public void setWsFonteGenerica(com.hyperborea.sira.ws.WsFonteGenerica wsFonteGenerica) {
        this.wsFonteGenerica = wsFonteGenerica;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsViewFonteResponse)) return false;
        WsViewFonteResponse other = (WsViewFonteResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.wsFonteGenerica==null && other.getWsFonteGenerica()==null) || 
             (this.wsFonteGenerica!=null &&
              this.wsFonteGenerica.equals(other.getWsFonteGenerica())));
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
        if (getWsFonteGenerica() != null) {
            _hashCode += getWsFonteGenerica().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsViewFonteResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewFonteResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsFonteGenerica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsFonteGenerica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsFonteGenerica"));
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
