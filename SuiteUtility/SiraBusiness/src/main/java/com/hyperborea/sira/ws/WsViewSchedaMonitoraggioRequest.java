/**
 * WsViewSchedaMonitoraggioRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsViewSchedaMonitoraggioRequest  implements java.io.Serializable {
    private java.lang.Integer idScheda;

    public WsViewSchedaMonitoraggioRequest() {
    }

    public WsViewSchedaMonitoraggioRequest(
           java.lang.Integer idScheda) {
           this.idScheda = idScheda;
    }


    /**
     * Gets the idScheda value for this WsViewSchedaMonitoraggioRequest.
     * 
     * @return idScheda
     */
    public java.lang.Integer getIdScheda() {
        return idScheda;
    }


    /**
     * Sets the idScheda value for this WsViewSchedaMonitoraggioRequest.
     * 
     * @param idScheda
     */
    public void setIdScheda(java.lang.Integer idScheda) {
        this.idScheda = idScheda;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsViewSchedaMonitoraggioRequest)) return false;
        WsViewSchedaMonitoraggioRequest other = (WsViewSchedaMonitoraggioRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idScheda==null && other.getIdScheda()==null) || 
             (this.idScheda!=null &&
              this.idScheda.equals(other.getIdScheda())));
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
        if (getIdScheda() != null) {
            _hashCode += getIdScheda().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsViewSchedaMonitoraggioRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewSchedaMonitoraggioRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idScheda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idScheda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
