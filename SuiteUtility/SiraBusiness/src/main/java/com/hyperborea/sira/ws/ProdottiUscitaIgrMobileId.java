/**
 * ProdottiUscitaIgrMobileId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProdottiUscitaIgrMobileId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.RifiutiCer rifiutoCer;

    public ProdottiUscitaIgrMobileId() {
    }

    public ProdottiUscitaIgrMobileId(
           com.hyperborea.sira.ws.RifiutiCer rifiutoCer) {
           this.rifiutoCer = rifiutoCer;
    }


    /**
     * Gets the rifiutoCer value for this ProdottiUscitaIgrMobileId.
     * 
     * @return rifiutoCer
     */
    public com.hyperborea.sira.ws.RifiutiCer getRifiutoCer() {
        return rifiutoCer;
    }


    /**
     * Sets the rifiutoCer value for this ProdottiUscitaIgrMobileId.
     * 
     * @param rifiutoCer
     */
    public void setRifiutoCer(com.hyperborea.sira.ws.RifiutiCer rifiutoCer) {
        this.rifiutoCer = rifiutoCer;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdottiUscitaIgrMobileId)) return false;
        ProdottiUscitaIgrMobileId other = (ProdottiUscitaIgrMobileId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rifiutoCer==null && other.getRifiutoCer()==null) || 
             (this.rifiutoCer!=null &&
              this.rifiutoCer.equals(other.getRifiutoCer())));
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
        if (getRifiutoCer() != null) {
            _hashCode += getRifiutoCer().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdottiUscitaIgrMobileId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodottiUscitaIgrMobileId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rifiutoCer");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rifiutoCer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "rifiutiCer"));
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
