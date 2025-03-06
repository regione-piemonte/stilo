/**
 * WsNewRostResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsNewRostResponse  implements java.io.Serializable {
    private com.hyperborea.sira.ws.RelazioniOst relazioniOst;

    public WsNewRostResponse() {
    }

    public WsNewRostResponse(
           com.hyperborea.sira.ws.RelazioniOst relazioniOst) {
           this.relazioniOst = relazioniOst;
    }


    /**
     * Gets the relazioniOst value for this WsNewRostResponse.
     * 
     * @return relazioniOst
     */
    public com.hyperborea.sira.ws.RelazioniOst getRelazioniOst() {
        return relazioniOst;
    }


    /**
     * Sets the relazioniOst value for this WsNewRostResponse.
     * 
     * @param relazioniOst
     */
    public void setRelazioniOst(com.hyperborea.sira.ws.RelazioniOst relazioniOst) {
        this.relazioniOst = relazioniOst;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsNewRostResponse)) return false;
        WsNewRostResponse other = (WsNewRostResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.relazioniOst==null && other.getRelazioniOst()==null) || 
             (this.relazioniOst!=null &&
              this.relazioniOst.equals(other.getRelazioniOst())));
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
        if (getRelazioniOst() != null) {
            _hashCode += getRelazioniOst().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsNewRostResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewRostResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("relazioniOst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "relazioniOst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relazioniOst"));
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
