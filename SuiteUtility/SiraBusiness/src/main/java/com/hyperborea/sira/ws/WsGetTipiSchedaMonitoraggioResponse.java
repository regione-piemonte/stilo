/**
 * WsGetTipiSchedaMonitoraggioResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsGetTipiSchedaMonitoraggioResponse  implements java.io.Serializable {
    private com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioDescriptor descriptor;

    public WsGetTipiSchedaMonitoraggioResponse() {
    }

    public WsGetTipiSchedaMonitoraggioResponse(
           com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioDescriptor descriptor) {
           this.descriptor = descriptor;
    }


    /**
     * Gets the descriptor value for this WsGetTipiSchedaMonitoraggioResponse.
     * 
     * @return descriptor
     */
    public com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioDescriptor getDescriptor() {
        return descriptor;
    }


    /**
     * Sets the descriptor value for this WsGetTipiSchedaMonitoraggioResponse.
     * 
     * @param descriptor
     */
    public void setDescriptor(com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsGetTipiSchedaMonitoraggioResponse)) return false;
        WsGetTipiSchedaMonitoraggioResponse other = (WsGetTipiSchedaMonitoraggioResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.descriptor==null && other.getDescriptor()==null) || 
             (this.descriptor!=null &&
              this.descriptor.equals(other.getDescriptor())));
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
        if (getDescriptor() != null) {
            _hashCode += getDescriptor().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsGetTipiSchedaMonitoraggioResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipiSchedaMonitoraggioResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descriptor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descriptor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTipiSchedaMonitoraggioDescriptor"));
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
