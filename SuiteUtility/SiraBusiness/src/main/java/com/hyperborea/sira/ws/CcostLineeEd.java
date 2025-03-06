/**
 * CcostLineeEd.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostLineeEd  implements java.io.Serializable {
    private java.lang.String codiceTerna;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.LineeEdTensioneNominale vocTensioneNominale;

    private com.hyperborea.sira.ws.LineeEdTipoCorrente vocTipoCorrente;

    public CcostLineeEd() {
    }

    public CcostLineeEd(
           java.lang.String codiceTerna,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.LineeEdTensioneNominale vocTensioneNominale,
           com.hyperborea.sira.ws.LineeEdTipoCorrente vocTipoCorrente) {
           this.codiceTerna = codiceTerna;
           this.idCcost = idCcost;
           this.vocTensioneNominale = vocTensioneNominale;
           this.vocTipoCorrente = vocTipoCorrente;
    }


    /**
     * Gets the codiceTerna value for this CcostLineeEd.
     * 
     * @return codiceTerna
     */
    public java.lang.String getCodiceTerna() {
        return codiceTerna;
    }


    /**
     * Sets the codiceTerna value for this CcostLineeEd.
     * 
     * @param codiceTerna
     */
    public void setCodiceTerna(java.lang.String codiceTerna) {
        this.codiceTerna = codiceTerna;
    }


    /**
     * Gets the idCcost value for this CcostLineeEd.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostLineeEd.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the vocTensioneNominale value for this CcostLineeEd.
     * 
     * @return vocTensioneNominale
     */
    public com.hyperborea.sira.ws.LineeEdTensioneNominale getVocTensioneNominale() {
        return vocTensioneNominale;
    }


    /**
     * Sets the vocTensioneNominale value for this CcostLineeEd.
     * 
     * @param vocTensioneNominale
     */
    public void setVocTensioneNominale(com.hyperborea.sira.ws.LineeEdTensioneNominale vocTensioneNominale) {
        this.vocTensioneNominale = vocTensioneNominale;
    }


    /**
     * Gets the vocTipoCorrente value for this CcostLineeEd.
     * 
     * @return vocTipoCorrente
     */
    public com.hyperborea.sira.ws.LineeEdTipoCorrente getVocTipoCorrente() {
        return vocTipoCorrente;
    }


    /**
     * Sets the vocTipoCorrente value for this CcostLineeEd.
     * 
     * @param vocTipoCorrente
     */
    public void setVocTipoCorrente(com.hyperborea.sira.ws.LineeEdTipoCorrente vocTipoCorrente) {
        this.vocTipoCorrente = vocTipoCorrente;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostLineeEd)) return false;
        CcostLineeEd other = (CcostLineeEd) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceTerna==null && other.getCodiceTerna()==null) || 
             (this.codiceTerna!=null &&
              this.codiceTerna.equals(other.getCodiceTerna()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.vocTensioneNominale==null && other.getVocTensioneNominale()==null) || 
             (this.vocTensioneNominale!=null &&
              this.vocTensioneNominale.equals(other.getVocTensioneNominale()))) &&
            ((this.vocTipoCorrente==null && other.getVocTipoCorrente()==null) || 
             (this.vocTipoCorrente!=null &&
              this.vocTipoCorrente.equals(other.getVocTipoCorrente())));
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
        if (getCodiceTerna() != null) {
            _hashCode += getCodiceTerna().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getVocTensioneNominale() != null) {
            _hashCode += getVocTensioneNominale().hashCode();
        }
        if (getVocTipoCorrente() != null) {
            _hashCode += getVocTipoCorrente().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostLineeEd.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostLineeEd"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceTerna");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceTerna"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTensioneNominale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTensioneNominale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "lineeEdTensioneNominale"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoCorrente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoCorrente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "lineeEdTipoCorrente"));
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
