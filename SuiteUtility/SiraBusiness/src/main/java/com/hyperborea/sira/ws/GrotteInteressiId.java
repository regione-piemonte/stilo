/**
 * GrotteInteressiId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class GrotteInteressiId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.VocInteresseGrotte vocInteresseGrotte;

    public GrotteInteressiId() {
    }

    public GrotteInteressiId(
           com.hyperborea.sira.ws.VocInteresseGrotte vocInteresseGrotte) {
           this.vocInteresseGrotte = vocInteresseGrotte;
    }


    /**
     * Gets the vocInteresseGrotte value for this GrotteInteressiId.
     * 
     * @return vocInteresseGrotte
     */
    public com.hyperborea.sira.ws.VocInteresseGrotte getVocInteresseGrotte() {
        return vocInteresseGrotte;
    }


    /**
     * Sets the vocInteresseGrotte value for this GrotteInteressiId.
     * 
     * @param vocInteresseGrotte
     */
    public void setVocInteresseGrotte(com.hyperborea.sira.ws.VocInteresseGrotte vocInteresseGrotte) {
        this.vocInteresseGrotte = vocInteresseGrotte;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GrotteInteressiId)) return false;
        GrotteInteressiId other = (GrotteInteressiId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.vocInteresseGrotte==null && other.getVocInteresseGrotte()==null) || 
             (this.vocInteresseGrotte!=null &&
              this.vocInteresseGrotte.equals(other.getVocInteresseGrotte())));
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
        if (getVocInteresseGrotte() != null) {
            _hashCode += getVocInteresseGrotte().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GrotteInteressiId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "grotteInteressiId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocInteresseGrotte");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocInteresseGrotte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocInteresseGrotte"));
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
