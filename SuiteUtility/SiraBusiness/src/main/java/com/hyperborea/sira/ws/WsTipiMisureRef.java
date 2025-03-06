/**
 * WsTipiMisureRef.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsTipiMisureRef  implements java.io.Serializable {
    private java.lang.Integer idTipoMisure;

    public WsTipiMisureRef() {
    }

    public WsTipiMisureRef(
           java.lang.Integer idTipoMisure) {
           this.idTipoMisure = idTipoMisure;
    }


    /**
     * Gets the idTipoMisure value for this WsTipiMisureRef.
     * 
     * @return idTipoMisure
     */
    public java.lang.Integer getIdTipoMisure() {
        return idTipoMisure;
    }


    /**
     * Sets the idTipoMisure value for this WsTipiMisureRef.
     * 
     * @param idTipoMisure
     */
    public void setIdTipoMisure(java.lang.Integer idTipoMisure) {
        this.idTipoMisure = idTipoMisure;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsTipiMisureRef)) return false;
        WsTipiMisureRef other = (WsTipiMisureRef) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idTipoMisure==null && other.getIdTipoMisure()==null) || 
             (this.idTipoMisure!=null &&
              this.idTipoMisure.equals(other.getIdTipoMisure())));
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
        if (getIdTipoMisure() != null) {
            _hashCode += getIdTipoMisure().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsTipiMisureRef.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTipiMisureRef"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTipoMisure");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTipoMisure"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
