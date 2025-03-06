/**
 * InquinantiEmAcquaId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class InquinantiEmAcquaId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.ParametriAmbientali parametriAmbientali;

    public InquinantiEmAcquaId() {
    }

    public InquinantiEmAcquaId(
           com.hyperborea.sira.ws.ParametriAmbientali parametriAmbientali) {
           this.parametriAmbientali = parametriAmbientali;
    }


    /**
     * Gets the parametriAmbientali value for this InquinantiEmAcquaId.
     * 
     * @return parametriAmbientali
     */
    public com.hyperborea.sira.ws.ParametriAmbientali getParametriAmbientali() {
        return parametriAmbientali;
    }


    /**
     * Sets the parametriAmbientali value for this InquinantiEmAcquaId.
     * 
     * @param parametriAmbientali
     */
    public void setParametriAmbientali(com.hyperborea.sira.ws.ParametriAmbientali parametriAmbientali) {
        this.parametriAmbientali = parametriAmbientali;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InquinantiEmAcquaId)) return false;
        InquinantiEmAcquaId other = (InquinantiEmAcquaId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.parametriAmbientali==null && other.getParametriAmbientali()==null) || 
             (this.parametriAmbientali!=null &&
              this.parametriAmbientali.equals(other.getParametriAmbientali())));
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
        if (getParametriAmbientali() != null) {
            _hashCode += getParametriAmbientali().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InquinantiEmAcquaId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "inquinantiEmAcquaId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parametriAmbientali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parametriAmbientali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "parametriAmbientali"));
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
