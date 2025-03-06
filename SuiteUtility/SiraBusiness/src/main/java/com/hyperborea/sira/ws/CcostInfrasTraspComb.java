/**
 * CcostInfrasTraspComb.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostInfrasTraspComb  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CcostGasdotto ccostGasdotto;

    private java.lang.Integer idCcost;

    public CcostInfrasTraspComb() {
    }

    public CcostInfrasTraspComb(
           com.hyperborea.sira.ws.CcostGasdotto ccostGasdotto,
           java.lang.Integer idCcost) {
           this.ccostGasdotto = ccostGasdotto;
           this.idCcost = idCcost;
    }


    /**
     * Gets the ccostGasdotto value for this CcostInfrasTraspComb.
     * 
     * @return ccostGasdotto
     */
    public com.hyperborea.sira.ws.CcostGasdotto getCcostGasdotto() {
        return ccostGasdotto;
    }


    /**
     * Sets the ccostGasdotto value for this CcostInfrasTraspComb.
     * 
     * @param ccostGasdotto
     */
    public void setCcostGasdotto(com.hyperborea.sira.ws.CcostGasdotto ccostGasdotto) {
        this.ccostGasdotto = ccostGasdotto;
    }


    /**
     * Gets the idCcost value for this CcostInfrasTraspComb.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostInfrasTraspComb.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostInfrasTraspComb)) return false;
        CcostInfrasTraspComb other = (CcostInfrasTraspComb) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ccostGasdotto==null && other.getCcostGasdotto()==null) || 
             (this.ccostGasdotto!=null &&
              this.ccostGasdotto.equals(other.getCcostGasdotto()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost())));
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
        if (getCcostGasdotto() != null) {
            _hashCode += getCcostGasdotto().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostInfrasTraspComb.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostInfrasTraspComb"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostGasdotto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostGasdotto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostGasdotto"));
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
