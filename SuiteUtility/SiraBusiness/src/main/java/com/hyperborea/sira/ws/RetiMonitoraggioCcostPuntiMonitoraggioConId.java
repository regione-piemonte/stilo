/**
 * RetiMonitoraggioCcostPuntiMonitoraggioConId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class RetiMonitoraggioCcostPuntiMonitoraggioConId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.RetiMonitoraggio retiMonitoraggio;

    public RetiMonitoraggioCcostPuntiMonitoraggioConId() {
    }

    public RetiMonitoraggioCcostPuntiMonitoraggioConId(
           com.hyperborea.sira.ws.RetiMonitoraggio retiMonitoraggio) {
           this.retiMonitoraggio = retiMonitoraggio;
    }


    /**
     * Gets the retiMonitoraggio value for this RetiMonitoraggioCcostPuntiMonitoraggioConId.
     * 
     * @return retiMonitoraggio
     */
    public com.hyperborea.sira.ws.RetiMonitoraggio getRetiMonitoraggio() {
        return retiMonitoraggio;
    }


    /**
     * Sets the retiMonitoraggio value for this RetiMonitoraggioCcostPuntiMonitoraggioConId.
     * 
     * @param retiMonitoraggio
     */
    public void setRetiMonitoraggio(com.hyperborea.sira.ws.RetiMonitoraggio retiMonitoraggio) {
        this.retiMonitoraggio = retiMonitoraggio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RetiMonitoraggioCcostPuntiMonitoraggioConId)) return false;
        RetiMonitoraggioCcostPuntiMonitoraggioConId other = (RetiMonitoraggioCcostPuntiMonitoraggioConId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.retiMonitoraggio==null && other.getRetiMonitoraggio()==null) || 
             (this.retiMonitoraggio!=null &&
              this.retiMonitoraggio.equals(other.getRetiMonitoraggio())));
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
        if (getRetiMonitoraggio() != null) {
            _hashCode += getRetiMonitoraggio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RetiMonitoraggioCcostPuntiMonitoraggioConId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "retiMonitoraggioCcostPuntiMonitoraggioConId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retiMonitoraggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "retiMonitoraggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "retiMonitoraggio"));
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
