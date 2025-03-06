/**
 * WsViewFonteRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsViewFonteRequest  implements java.io.Serializable {
    private com.hyperborea.sira.ws.WsFonteRef wsFonteRef;

    public WsViewFonteRequest() {
    }

    public WsViewFonteRequest(
           com.hyperborea.sira.ws.WsFonteRef wsFonteRef) {
           this.wsFonteRef = wsFonteRef;
    }


    /**
     * Gets the wsFonteRef value for this WsViewFonteRequest.
     * 
     * @return wsFonteRef
     */
    public com.hyperborea.sira.ws.WsFonteRef getWsFonteRef() {
        return wsFonteRef;
    }


    /**
     * Sets the wsFonteRef value for this WsViewFonteRequest.
     * 
     * @param wsFonteRef
     */
    public void setWsFonteRef(com.hyperborea.sira.ws.WsFonteRef wsFonteRef) {
        this.wsFonteRef = wsFonteRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsViewFonteRequest)) return false;
        WsViewFonteRequest other = (WsViewFonteRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.wsFonteRef==null && other.getWsFonteRef()==null) || 
             (this.wsFonteRef!=null &&
              this.wsFonteRef.equals(other.getWsFonteRef())));
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
        if (getWsFonteRef() != null) {
            _hashCode += getWsFonteRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsViewFonteRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewFonteRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsFonteRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsFonteRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsFonteRef"));
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
