/**
 * CcostImpProdEnTermicaFt.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostImpProdEnTermicaFt  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CcostImpiantoTermico ccostImpiantoTermico;

    private java.lang.Integer idCcost;

    public CcostImpProdEnTermicaFt() {
    }

    public CcostImpProdEnTermicaFt(
           com.hyperborea.sira.ws.CcostImpiantoTermico ccostImpiantoTermico,
           java.lang.Integer idCcost) {
           this.ccostImpiantoTermico = ccostImpiantoTermico;
           this.idCcost = idCcost;
    }


    /**
     * Gets the ccostImpiantoTermico value for this CcostImpProdEnTermicaFt.
     * 
     * @return ccostImpiantoTermico
     */
    public com.hyperborea.sira.ws.CcostImpiantoTermico getCcostImpiantoTermico() {
        return ccostImpiantoTermico;
    }


    /**
     * Sets the ccostImpiantoTermico value for this CcostImpProdEnTermicaFt.
     * 
     * @param ccostImpiantoTermico
     */
    public void setCcostImpiantoTermico(com.hyperborea.sira.ws.CcostImpiantoTermico ccostImpiantoTermico) {
        this.ccostImpiantoTermico = ccostImpiantoTermico;
    }


    /**
     * Gets the idCcost value for this CcostImpProdEnTermicaFt.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostImpProdEnTermicaFt.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostImpProdEnTermicaFt)) return false;
        CcostImpProdEnTermicaFt other = (CcostImpProdEnTermicaFt) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ccostImpiantoTermico==null && other.getCcostImpiantoTermico()==null) || 
             (this.ccostImpiantoTermico!=null &&
              this.ccostImpiantoTermico.equals(other.getCcostImpiantoTermico()))) &&
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
        if (getCcostImpiantoTermico() != null) {
            _hashCode += getCcostImpiantoTermico().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostImpProdEnTermicaFt.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpProdEnTermicaFt"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostImpiantoTermico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostImpiantoTermico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpiantoTermico"));
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
