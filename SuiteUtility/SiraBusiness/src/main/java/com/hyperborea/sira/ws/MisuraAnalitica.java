/**
 * MisuraAnalitica.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class MisuraAnalitica  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idMisura;

    private org.apache.axis.types.UnsignedShort minoreMaggioreDi;

    public MisuraAnalitica() {
    }

    public MisuraAnalitica(
           java.lang.Integer idMisura,
           org.apache.axis.types.UnsignedShort minoreMaggioreDi) {
        this.idMisura = idMisura;
        this.minoreMaggioreDi = minoreMaggioreDi;
    }


    /**
     * Gets the idMisura value for this MisuraAnalitica.
     * 
     * @return idMisura
     */
    public java.lang.Integer getIdMisura() {
        return idMisura;
    }


    /**
     * Sets the idMisura value for this MisuraAnalitica.
     * 
     * @param idMisura
     */
    public void setIdMisura(java.lang.Integer idMisura) {
        this.idMisura = idMisura;
    }


    /**
     * Gets the minoreMaggioreDi value for this MisuraAnalitica.
     * 
     * @return minoreMaggioreDi
     */
    public org.apache.axis.types.UnsignedShort getMinoreMaggioreDi() {
        return minoreMaggioreDi;
    }


    /**
     * Sets the minoreMaggioreDi value for this MisuraAnalitica.
     * 
     * @param minoreMaggioreDi
     */
    public void setMinoreMaggioreDi(org.apache.axis.types.UnsignedShort minoreMaggioreDi) {
        this.minoreMaggioreDi = minoreMaggioreDi;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MisuraAnalitica)) return false;
        MisuraAnalitica other = (MisuraAnalitica) obj;
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
            ((this.minoreMaggioreDi==null && other.getMinoreMaggioreDi()==null) || 
             (this.minoreMaggioreDi!=null &&
              this.minoreMaggioreDi.equals(other.getMinoreMaggioreDi())));
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
        if (getMinoreMaggioreDi() != null) {
            _hashCode += getMinoreMaggioreDi().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MisuraAnalitica.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "misuraAnalitica"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMisura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idMisura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minoreMaggioreDi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "minoreMaggioreDi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
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
