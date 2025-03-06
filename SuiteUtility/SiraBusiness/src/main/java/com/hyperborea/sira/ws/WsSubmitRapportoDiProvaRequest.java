/**
 * WsSubmitRapportoDiProvaRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsSubmitRapportoDiProvaRequest  implements java.io.Serializable {
    private com.hyperborea.sira.ws.Campione campione;

    public WsSubmitRapportoDiProvaRequest() {
    }

    public WsSubmitRapportoDiProvaRequest(
           com.hyperborea.sira.ws.Campione campione) {
           this.campione = campione;
    }


    /**
     * Gets the campione value for this WsSubmitRapportoDiProvaRequest.
     * 
     * @return campione
     */
    public com.hyperborea.sira.ws.Campione getCampione() {
        return campione;
    }


    /**
     * Sets the campione value for this WsSubmitRapportoDiProvaRequest.
     * 
     * @param campione
     */
    public void setCampione(com.hyperborea.sira.ws.Campione campione) {
        this.campione = campione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsSubmitRapportoDiProvaRequest)) return false;
        WsSubmitRapportoDiProvaRequest other = (WsSubmitRapportoDiProvaRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.campione==null && other.getCampione()==null) || 
             (this.campione!=null &&
              this.campione.equals(other.getCampione())));
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
        if (getCampione() != null) {
            _hashCode += getCampione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsSubmitRapportoDiProvaRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSubmitRapportoDiProvaRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "campione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "campione"));
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
