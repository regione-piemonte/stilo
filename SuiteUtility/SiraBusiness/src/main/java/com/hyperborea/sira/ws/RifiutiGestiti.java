/**
 * RifiutiGestiti.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class RifiutiGestiti  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.RifiutiGestitiId id;

    private com.hyperborea.sira.ws.RifiutiCer rifiutiCer;

    public RifiutiGestiti() {
    }

    public RifiutiGestiti(
           com.hyperborea.sira.ws.RifiutiGestitiId id,
           com.hyperborea.sira.ws.RifiutiCer rifiutiCer) {
        this.id = id;
        this.rifiutiCer = rifiutiCer;
    }


    /**
     * Gets the id value for this RifiutiGestiti.
     * 
     * @return id
     */
    public com.hyperborea.sira.ws.RifiutiGestitiId getId() {
        return id;
    }


    /**
     * Sets the id value for this RifiutiGestiti.
     * 
     * @param id
     */
    public void setId(com.hyperborea.sira.ws.RifiutiGestitiId id) {
        this.id = id;
    }


    /**
     * Gets the rifiutiCer value for this RifiutiGestiti.
     * 
     * @return rifiutiCer
     */
    public com.hyperborea.sira.ws.RifiutiCer getRifiutiCer() {
        return rifiutiCer;
    }


    /**
     * Sets the rifiutiCer value for this RifiutiGestiti.
     * 
     * @param rifiutiCer
     */
    public void setRifiutiCer(com.hyperborea.sira.ws.RifiutiCer rifiutiCer) {
        this.rifiutiCer = rifiutiCer;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RifiutiGestiti)) return false;
        RifiutiGestiti other = (RifiutiGestiti) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.rifiutiCer==null && other.getRifiutiCer()==null) || 
             (this.rifiutiCer!=null &&
              this.rifiutiCer.equals(other.getRifiutiCer())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getRifiutiCer() != null) {
            _hashCode += getRifiutiCer().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RifiutiGestiti.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "rifiutiGestiti"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "rifiutiGestitiId"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rifiutiCer");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rifiutiCer"));
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
