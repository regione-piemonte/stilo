/**
 * CcostAreaPericoloIdrogeo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostAreaPericoloIdrogeo  implements java.io.Serializable {
    private java.lang.Integer idCcost;

    private java.lang.Float superficie;

    private com.hyperborea.sira.ws.VocClassePericoloIdrogeo vocClassePericoloIdrogeo;

    private com.hyperborea.sira.ws.VocTipoPericoloIdrogeo vocTipoPericoloIdrogeo;

    public CcostAreaPericoloIdrogeo() {
    }

    public CcostAreaPericoloIdrogeo(
           java.lang.Integer idCcost,
           java.lang.Float superficie,
           com.hyperborea.sira.ws.VocClassePericoloIdrogeo vocClassePericoloIdrogeo,
           com.hyperborea.sira.ws.VocTipoPericoloIdrogeo vocTipoPericoloIdrogeo) {
           this.idCcost = idCcost;
           this.superficie = superficie;
           this.vocClassePericoloIdrogeo = vocClassePericoloIdrogeo;
           this.vocTipoPericoloIdrogeo = vocTipoPericoloIdrogeo;
    }


    /**
     * Gets the idCcost value for this CcostAreaPericoloIdrogeo.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostAreaPericoloIdrogeo.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the superficie value for this CcostAreaPericoloIdrogeo.
     * 
     * @return superficie
     */
    public java.lang.Float getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this CcostAreaPericoloIdrogeo.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Float superficie) {
        this.superficie = superficie;
    }


    /**
     * Gets the vocClassePericoloIdrogeo value for this CcostAreaPericoloIdrogeo.
     * 
     * @return vocClassePericoloIdrogeo
     */
    public com.hyperborea.sira.ws.VocClassePericoloIdrogeo getVocClassePericoloIdrogeo() {
        return vocClassePericoloIdrogeo;
    }


    /**
     * Sets the vocClassePericoloIdrogeo value for this CcostAreaPericoloIdrogeo.
     * 
     * @param vocClassePericoloIdrogeo
     */
    public void setVocClassePericoloIdrogeo(com.hyperborea.sira.ws.VocClassePericoloIdrogeo vocClassePericoloIdrogeo) {
        this.vocClassePericoloIdrogeo = vocClassePericoloIdrogeo;
    }


    /**
     * Gets the vocTipoPericoloIdrogeo value for this CcostAreaPericoloIdrogeo.
     * 
     * @return vocTipoPericoloIdrogeo
     */
    public com.hyperborea.sira.ws.VocTipoPericoloIdrogeo getVocTipoPericoloIdrogeo() {
        return vocTipoPericoloIdrogeo;
    }


    /**
     * Sets the vocTipoPericoloIdrogeo value for this CcostAreaPericoloIdrogeo.
     * 
     * @param vocTipoPericoloIdrogeo
     */
    public void setVocTipoPericoloIdrogeo(com.hyperborea.sira.ws.VocTipoPericoloIdrogeo vocTipoPericoloIdrogeo) {
        this.vocTipoPericoloIdrogeo = vocTipoPericoloIdrogeo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostAreaPericoloIdrogeo)) return false;
        CcostAreaPericoloIdrogeo other = (CcostAreaPericoloIdrogeo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie()))) &&
            ((this.vocClassePericoloIdrogeo==null && other.getVocClassePericoloIdrogeo()==null) || 
             (this.vocClassePericoloIdrogeo!=null &&
              this.vocClassePericoloIdrogeo.equals(other.getVocClassePericoloIdrogeo()))) &&
            ((this.vocTipoPericoloIdrogeo==null && other.getVocTipoPericoloIdrogeo()==null) || 
             (this.vocTipoPericoloIdrogeo!=null &&
              this.vocTipoPericoloIdrogeo.equals(other.getVocTipoPericoloIdrogeo())));
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
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        if (getVocClassePericoloIdrogeo() != null) {
            _hashCode += getVocClassePericoloIdrogeo().hashCode();
        }
        if (getVocTipoPericoloIdrogeo() != null) {
            _hashCode += getVocTipoPericoloIdrogeo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostAreaPericoloIdrogeo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAreaPericoloIdrogeo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocClassePericoloIdrogeo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocClassePericoloIdrogeo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClassePericoloIdrogeo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoPericoloIdrogeo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoPericoloIdrogeo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoPericoloIdrogeo"));
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
