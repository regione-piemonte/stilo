/**
 * WsNewSchedaMonitoraggioRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsNewSchedaMonitoraggioRequest  implements java.io.Serializable {
    private java.lang.Integer idTipoScheda;

    private com.hyperborea.sira.ws.SchedaMonitoraggio schedaMonitoraggio;

    public WsNewSchedaMonitoraggioRequest() {
    }

    public WsNewSchedaMonitoraggioRequest(
           java.lang.Integer idTipoScheda,
           com.hyperborea.sira.ws.SchedaMonitoraggio schedaMonitoraggio) {
           this.idTipoScheda = idTipoScheda;
           this.schedaMonitoraggio = schedaMonitoraggio;
    }


    /**
     * Gets the idTipoScheda value for this WsNewSchedaMonitoraggioRequest.
     * 
     * @return idTipoScheda
     */
    public java.lang.Integer getIdTipoScheda() {
        return idTipoScheda;
    }


    /**
     * Sets the idTipoScheda value for this WsNewSchedaMonitoraggioRequest.
     * 
     * @param idTipoScheda
     */
    public void setIdTipoScheda(java.lang.Integer idTipoScheda) {
        this.idTipoScheda = idTipoScheda;
    }


    /**
     * Gets the schedaMonitoraggio value for this WsNewSchedaMonitoraggioRequest.
     * 
     * @return schedaMonitoraggio
     */
    public com.hyperborea.sira.ws.SchedaMonitoraggio getSchedaMonitoraggio() {
        return schedaMonitoraggio;
    }


    /**
     * Sets the schedaMonitoraggio value for this WsNewSchedaMonitoraggioRequest.
     * 
     * @param schedaMonitoraggio
     */
    public void setSchedaMonitoraggio(com.hyperborea.sira.ws.SchedaMonitoraggio schedaMonitoraggio) {
        this.schedaMonitoraggio = schedaMonitoraggio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsNewSchedaMonitoraggioRequest)) return false;
        WsNewSchedaMonitoraggioRequest other = (WsNewSchedaMonitoraggioRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idTipoScheda==null && other.getIdTipoScheda()==null) || 
             (this.idTipoScheda!=null &&
              this.idTipoScheda.equals(other.getIdTipoScheda()))) &&
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
        if (getIdTipoScheda() != null) {
            _hashCode += getIdTipoScheda().hashCode();
        }
        if (getSchedaMonitoraggio() != null) {
            _hashCode += getSchedaMonitoraggio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsNewSchedaMonitoraggioRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewSchedaMonitoraggioRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTipoScheda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTipoScheda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
