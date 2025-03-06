/**
 * MisureStrumentali.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class MisureStrumentali  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idMisura;

    private com.hyperborea.sira.ws.Misure misure;

    private com.hyperborea.sira.ws.StrumentiMisura strumentiMisura;

    public MisureStrumentali() {
    }

    public MisureStrumentali(
           java.lang.Integer idMisura,
           com.hyperborea.sira.ws.Misure misure,
           com.hyperborea.sira.ws.StrumentiMisura strumentiMisura) {
        this.idMisura = idMisura;
        this.misure = misure;
        this.strumentiMisura = strumentiMisura;
    }


    /**
     * Gets the idMisura value for this MisureStrumentali.
     * 
     * @return idMisura
     */
    public java.lang.Integer getIdMisura() {
        return idMisura;
    }


    /**
     * Sets the idMisura value for this MisureStrumentali.
     * 
     * @param idMisura
     */
    public void setIdMisura(java.lang.Integer idMisura) {
        this.idMisura = idMisura;
    }


    /**
     * Gets the misure value for this MisureStrumentali.
     * 
     * @return misure
     */
    public com.hyperborea.sira.ws.Misure getMisure() {
        return misure;
    }


    /**
     * Sets the misure value for this MisureStrumentali.
     * 
     * @param misure
     */
    public void setMisure(com.hyperborea.sira.ws.Misure misure) {
        this.misure = misure;
    }


    /**
     * Gets the strumentiMisura value for this MisureStrumentali.
     * 
     * @return strumentiMisura
     */
    public com.hyperborea.sira.ws.StrumentiMisura getStrumentiMisura() {
        return strumentiMisura;
    }


    /**
     * Sets the strumentiMisura value for this MisureStrumentali.
     * 
     * @param strumentiMisura
     */
    public void setStrumentiMisura(com.hyperborea.sira.ws.StrumentiMisura strumentiMisura) {
        this.strumentiMisura = strumentiMisura;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MisureStrumentali)) return false;
        MisureStrumentali other = (MisureStrumentali) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idMisura==null && other.getIdMisura()==null) || 
             (this.idMisura!=null &&
              this.idMisura.equals(other.getIdMisura()))) &&
            ((this.misure==null && other.getMisure()==null) || 
             (this.misure!=null &&
              this.misure.equals(other.getMisure()))) &&
            ((this.strumentiMisura==null && other.getStrumentiMisura()==null) || 
             (this.strumentiMisura!=null &&
              this.strumentiMisura.equals(other.getStrumentiMisura())));
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
        if (getIdMisura() != null) {
            _hashCode += getIdMisura().hashCode();
        }
        if (getMisure() != null) {
            _hashCode += getMisure().hashCode();
        }
        if (getStrumentiMisura() != null) {
            _hashCode += getStrumentiMisura().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MisureStrumentali.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "misureStrumentali"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMisura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idMisura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("misure");
        elemField.setXmlName(new javax.xml.namespace.QName("", "misure"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "misure"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strumentiMisura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "strumentiMisura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "strumentiMisura"));
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
