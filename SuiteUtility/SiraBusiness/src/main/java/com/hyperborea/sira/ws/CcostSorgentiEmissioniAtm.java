/**
 * CcostSorgentiEmissioniAtm.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostSorgentiEmissioniAtm  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CcostAreeEmissioneAtm ccostAreeEmissioneAtm;

    private com.hyperborea.sira.ws.CcostPuntiEmissioneAtm ccostPuntiEmissioneAtm;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.TrattamentoEmissioni trattamentoEmissioni;

    public CcostSorgentiEmissioniAtm() {
    }

    public CcostSorgentiEmissioniAtm(
           com.hyperborea.sira.ws.CcostAreeEmissioneAtm ccostAreeEmissioneAtm,
           com.hyperborea.sira.ws.CcostPuntiEmissioneAtm ccostPuntiEmissioneAtm,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.TrattamentoEmissioni trattamentoEmissioni) {
           this.ccostAreeEmissioneAtm = ccostAreeEmissioneAtm;
           this.ccostPuntiEmissioneAtm = ccostPuntiEmissioneAtm;
           this.idCcost = idCcost;
           this.trattamentoEmissioni = trattamentoEmissioni;
    }


    /**
     * Gets the ccostAreeEmissioneAtm value for this CcostSorgentiEmissioniAtm.
     * 
     * @return ccostAreeEmissioneAtm
     */
    public com.hyperborea.sira.ws.CcostAreeEmissioneAtm getCcostAreeEmissioneAtm() {
        return ccostAreeEmissioneAtm;
    }


    /**
     * Sets the ccostAreeEmissioneAtm value for this CcostSorgentiEmissioniAtm.
     * 
     * @param ccostAreeEmissioneAtm
     */
    public void setCcostAreeEmissioneAtm(com.hyperborea.sira.ws.CcostAreeEmissioneAtm ccostAreeEmissioneAtm) {
        this.ccostAreeEmissioneAtm = ccostAreeEmissioneAtm;
    }


    /**
     * Gets the ccostPuntiEmissioneAtm value for this CcostSorgentiEmissioniAtm.
     * 
     * @return ccostPuntiEmissioneAtm
     */
    public com.hyperborea.sira.ws.CcostPuntiEmissioneAtm getCcostPuntiEmissioneAtm() {
        return ccostPuntiEmissioneAtm;
    }


    /**
     * Sets the ccostPuntiEmissioneAtm value for this CcostSorgentiEmissioniAtm.
     * 
     * @param ccostPuntiEmissioneAtm
     */
    public void setCcostPuntiEmissioneAtm(com.hyperborea.sira.ws.CcostPuntiEmissioneAtm ccostPuntiEmissioneAtm) {
        this.ccostPuntiEmissioneAtm = ccostPuntiEmissioneAtm;
    }


    /**
     * Gets the idCcost value for this CcostSorgentiEmissioniAtm.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostSorgentiEmissioniAtm.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the trattamentoEmissioni value for this CcostSorgentiEmissioniAtm.
     * 
     * @return trattamentoEmissioni
     */
    public com.hyperborea.sira.ws.TrattamentoEmissioni getTrattamentoEmissioni() {
        return trattamentoEmissioni;
    }


    /**
     * Sets the trattamentoEmissioni value for this CcostSorgentiEmissioniAtm.
     * 
     * @param trattamentoEmissioni
     */
    public void setTrattamentoEmissioni(com.hyperborea.sira.ws.TrattamentoEmissioni trattamentoEmissioni) {
        this.trattamentoEmissioni = trattamentoEmissioni;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostSorgentiEmissioniAtm)) return false;
        CcostSorgentiEmissioniAtm other = (CcostSorgentiEmissioniAtm) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ccostAreeEmissioneAtm==null && other.getCcostAreeEmissioneAtm()==null) || 
             (this.ccostAreeEmissioneAtm!=null &&
              this.ccostAreeEmissioneAtm.equals(other.getCcostAreeEmissioneAtm()))) &&
            ((this.ccostPuntiEmissioneAtm==null && other.getCcostPuntiEmissioneAtm()==null) || 
             (this.ccostPuntiEmissioneAtm!=null &&
              this.ccostPuntiEmissioneAtm.equals(other.getCcostPuntiEmissioneAtm()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.trattamentoEmissioni==null && other.getTrattamentoEmissioni()==null) || 
             (this.trattamentoEmissioni!=null &&
              this.trattamentoEmissioni.equals(other.getTrattamentoEmissioni())));
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
        if (getCcostAreeEmissioneAtm() != null) {
            _hashCode += getCcostAreeEmissioneAtm().hashCode();
        }
        if (getCcostPuntiEmissioneAtm() != null) {
            _hashCode += getCcostPuntiEmissioneAtm().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getTrattamentoEmissioni() != null) {
            _hashCode += getTrattamentoEmissioni().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostSorgentiEmissioniAtm.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSorgentiEmissioniAtm"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostAreeEmissioneAtm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostAreeEmissioneAtm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAreeEmissioneAtm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostPuntiEmissioneAtm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostPuntiEmissioneAtm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostPuntiEmissioneAtm"));
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
        elemField.setFieldName("trattamentoEmissioni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "trattamentoEmissioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "trattamentoEmissioni"));
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
