/**
 * WsViewOstRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsViewOstRequest  implements java.io.Serializable {
    private com.hyperborea.sira.ws.WsOstRef wsOstRef;

    public WsViewOstRequest() {
    }

    public WsViewOstRequest(
           com.hyperborea.sira.ws.WsOstRef wsOstRef) {
           this.wsOstRef = wsOstRef;
    }


    /**
     * Gets the wsOstRef value for this WsViewOstRequest.
     * 
     * @return wsOstRef
     */
    public com.hyperborea.sira.ws.WsOstRef getWsOstRef() {
        return wsOstRef;
    }


    /**
     * Sets the wsOstRef value for this WsViewOstRequest.
     * 
     * @param wsOstRef
     */
    public void setWsOstRef(com.hyperborea.sira.ws.WsOstRef wsOstRef) {
        this.wsOstRef = wsOstRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsViewOstRequest)) return false;
        WsViewOstRequest other = (WsViewOstRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
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
        if (getWsOstRef() != null) {
            _hashCode += getWsOstRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsViewOstRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewOstRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
