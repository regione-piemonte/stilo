/**
 * WsGetCategoriaInfoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsGetCategoriaInfoResponse  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CostNost costNost;

    public WsGetCategoriaInfoResponse() {
    }

    public WsGetCategoriaInfoResponse(
           com.hyperborea.sira.ws.CostNost costNost) {
           this.costNost = costNost;
    }


    /**
     * Gets the costNost value for this WsGetCategoriaInfoResponse.
     * 
     * @return costNost
     */
    public com.hyperborea.sira.ws.CostNost getCostNost() {
        return costNost;
    }


    /**
     * Sets the costNost value for this WsGetCategoriaInfoResponse.
     * 
     * @param costNost
     */
    public void setCostNost(com.hyperborea.sira.ws.CostNost costNost) {
        this.costNost = costNost;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsGetCategoriaInfoResponse)) return false;
        WsGetCategoriaInfoResponse other = (WsGetCategoriaInfoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.costNost==null && other.getCostNost()==null) || 
             (this.costNost!=null &&
              this.costNost.equals(other.getCostNost())));
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
        if (getCostNost() != null) {
            _hashCode += getCostNost().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsGetCategoriaInfoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetCategoriaInfoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("costNost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "costNost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "costNost"));
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
