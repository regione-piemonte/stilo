/**
 * WsCountSoggettiFisiciResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsCountSoggettiFisiciResponse  implements java.io.Serializable {
    private java.lang.Long numeroSoggettiFisici;

    public WsCountSoggettiFisiciResponse() {
    }

    public WsCountSoggettiFisiciResponse(
           java.lang.Long numeroSoggettiFisici) {
           this.numeroSoggettiFisici = numeroSoggettiFisici;
    }


    /**
     * Gets the numeroSoggettiFisici value for this WsCountSoggettiFisiciResponse.
     * 
     * @return numeroSoggettiFisici
     */
    public java.lang.Long getNumeroSoggettiFisici() {
        return numeroSoggettiFisici;
    }


    /**
     * Sets the numeroSoggettiFisici value for this WsCountSoggettiFisiciResponse.
     * 
     * @param numeroSoggettiFisici
     */
    public void setNumeroSoggettiFisici(java.lang.Long numeroSoggettiFisici) {
        this.numeroSoggettiFisici = numeroSoggettiFisici;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsCountSoggettiFisiciResponse)) return false;
        WsCountSoggettiFisiciResponse other = (WsCountSoggettiFisiciResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.numeroSoggettiFisici==null && other.getNumeroSoggettiFisici()==null) || 
             (this.numeroSoggettiFisici!=null &&
              this.numeroSoggettiFisici.equals(other.getNumeroSoggettiFisici())));
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
        if (getNumeroSoggettiFisici() != null) {
            _hashCode += getNumeroSoggettiFisici().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsCountSoggettiFisiciResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCountSoggettiFisiciResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroSoggettiFisici");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroSoggettiFisici"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
