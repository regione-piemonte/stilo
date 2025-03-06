/**
 * WsGetCategoriaMetadataRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsGetCategoriaMetadataRequest  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CostNostId costNostId;

    public WsGetCategoriaMetadataRequest() {
    }

    public WsGetCategoriaMetadataRequest(
           com.hyperborea.sira.ws.CostNostId costNostId) {
           this.costNostId = costNostId;
    }


    /**
     * Gets the costNostId value for this WsGetCategoriaMetadataRequest.
     * 
     * @return costNostId
     */
    public com.hyperborea.sira.ws.CostNostId getCostNostId() {
        return costNostId;
    }


    /**
     * Sets the costNostId value for this WsGetCategoriaMetadataRequest.
     * 
     * @param costNostId
     */
    public void setCostNostId(com.hyperborea.sira.ws.CostNostId costNostId) {
        this.costNostId = costNostId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsGetCategoriaMetadataRequest)) return false;
        WsGetCategoriaMetadataRequest other = (WsGetCategoriaMetadataRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.costNostId==null && other.getCostNostId()==null) || 
             (this.costNostId!=null &&
              this.costNostId.equals(other.getCostNostId())));
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
        if (getCostNostId() != null) {
            _hashCode += getCostNostId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsGetCategoriaMetadataRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetCategoriaMetadataRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("costNostId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "costNostId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "costNostId"));
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
