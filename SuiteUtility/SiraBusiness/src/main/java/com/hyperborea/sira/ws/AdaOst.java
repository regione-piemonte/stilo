/**
 * AdaOst.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class AdaOst  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.AdaOstId id;

    private com.hyperborea.sira.ws.StatiAmministrativi statiAmministrativi;

    public AdaOst() {
    }

    public AdaOst(
           com.hyperborea.sira.ws.AdaOstId id,
           com.hyperborea.sira.ws.StatiAmministrativi statiAmministrativi) {
        this.id = id;
        this.statiAmministrativi = statiAmministrativi;
    }


    /**
     * Gets the id value for this AdaOst.
     * 
     * @return id
     */
    public com.hyperborea.sira.ws.AdaOstId getId() {
        return id;
    }


    /**
     * Sets the id value for this AdaOst.
     * 
     * @param id
     */
    public void setId(com.hyperborea.sira.ws.AdaOstId id) {
        this.id = id;
    }


    /**
     * Gets the statiAmministrativi value for this AdaOst.
     * 
     * @return statiAmministrativi
     */
    public com.hyperborea.sira.ws.StatiAmministrativi getStatiAmministrativi() {
        return statiAmministrativi;
    }


    /**
     * Sets the statiAmministrativi value for this AdaOst.
     * 
     * @param statiAmministrativi
     */
    public void setStatiAmministrativi(com.hyperborea.sira.ws.StatiAmministrativi statiAmministrativi) {
        this.statiAmministrativi = statiAmministrativi;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AdaOst)) return false;
        AdaOst other = (AdaOst) obj;
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
            ((this.statiAmministrativi==null && other.getStatiAmministrativi()==null) || 
             (this.statiAmministrativi!=null &&
              this.statiAmministrativi.equals(other.getStatiAmministrativi())));
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
        if (getStatiAmministrativi() != null) {
            _hashCode += getStatiAmministrativi().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AdaOst.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "adaOst"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "adaOstId"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statiAmministrativi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statiAmministrativi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "statiAmministrativi"));
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
