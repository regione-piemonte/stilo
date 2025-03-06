/**
 * ModStocRifNonPerAutodemId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ModStocRifNonPerAutodemId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CcostIgrAutodemolizione ccostIgrAutodemolizione;

    private java.lang.String valore;

    public ModStocRifNonPerAutodemId() {
    }

    public ModStocRifNonPerAutodemId(
           com.hyperborea.sira.ws.CcostIgrAutodemolizione ccostIgrAutodemolizione,
           java.lang.String valore) {
           this.ccostIgrAutodemolizione = ccostIgrAutodemolizione;
           this.valore = valore;
    }


    /**
     * Gets the ccostIgrAutodemolizione value for this ModStocRifNonPerAutodemId.
     * 
     * @return ccostIgrAutodemolizione
     */
    public com.hyperborea.sira.ws.CcostIgrAutodemolizione getCcostIgrAutodemolizione() {
        return ccostIgrAutodemolizione;
    }


    /**
     * Sets the ccostIgrAutodemolizione value for this ModStocRifNonPerAutodemId.
     * 
     * @param ccostIgrAutodemolizione
     */
    public void setCcostIgrAutodemolizione(com.hyperborea.sira.ws.CcostIgrAutodemolizione ccostIgrAutodemolizione) {
        this.ccostIgrAutodemolizione = ccostIgrAutodemolizione;
    }


    /**
     * Gets the valore value for this ModStocRifNonPerAutodemId.
     * 
     * @return valore
     */
    public java.lang.String getValore() {
        return valore;
    }


    /**
     * Sets the valore value for this ModStocRifNonPerAutodemId.
     * 
     * @param valore
     */
    public void setValore(java.lang.String valore) {
        this.valore = valore;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ModStocRifNonPerAutodemId)) return false;
        ModStocRifNonPerAutodemId other = (ModStocRifNonPerAutodemId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ccostIgrAutodemolizione==null && other.getCcostIgrAutodemolizione()==null) || 
             (this.ccostIgrAutodemolizione!=null &&
              this.ccostIgrAutodemolizione.equals(other.getCcostIgrAutodemolizione()))) &&
            ((this.valore==null && other.getValore()==null) || 
             (this.valore!=null &&
              this.valore.equals(other.getValore())));
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
        if (getCcostIgrAutodemolizione() != null) {
            _hashCode += getCcostIgrAutodemolizione().hashCode();
        }
        if (getValore() != null) {
            _hashCode += getValore().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ModStocRifNonPerAutodemId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifNonPerAutodemId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostIgrAutodemolizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostIgrAutodemolizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrAutodemolizione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
