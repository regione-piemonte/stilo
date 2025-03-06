/**
 * WsViewSchedaMonitoraggioResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsViewSchedaMonitoraggioResponse  implements java.io.Serializable {
    private com.hyperborea.sira.ws.SchedaMonitoraggio schedaMonitoraggio;

    public WsViewSchedaMonitoraggioResponse() {
    }

    public WsViewSchedaMonitoraggioResponse(
           com.hyperborea.sira.ws.SchedaMonitoraggio schedaMonitoraggio) {
           this.schedaMonitoraggio = schedaMonitoraggio;
    }


    /**
     * Gets the schedaMonitoraggio value for this WsViewSchedaMonitoraggioResponse.
     * 
     * @return schedaMonitoraggio
     */
    public com.hyperborea.sira.ws.SchedaMonitoraggio getSchedaMonitoraggio() {
        return schedaMonitoraggio;
    }


    /**
     * Sets the schedaMonitoraggio value for this WsViewSchedaMonitoraggioResponse.
     * 
     * @param schedaMonitoraggio
     */
    public void setSchedaMonitoraggio(com.hyperborea.sira.ws.SchedaMonitoraggio schedaMonitoraggio) {
        this.schedaMonitoraggio = schedaMonitoraggio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsViewSchedaMonitoraggioResponse)) return false;
        WsViewSchedaMonitoraggioResponse other = (WsViewSchedaMonitoraggioResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.schedaMonitoraggio==null && other.getSchedaMonitoraggio()==null) || 
             (this.schedaMonitoraggio!=null &&
              this.schedaMonitoraggio.equals(other.getSchedaMonitoraggio())));
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
        if (getSchedaMonitoraggio() != null) {
            _hashCode += getSchedaMonitoraggio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsViewSchedaMonitoraggioResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewSchedaMonitoraggioResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schedaMonitoraggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "schedaMonitoraggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "schedaMonitoraggio"));
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
