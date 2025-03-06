/**
 * CcostImpiantoProdEnTermica.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostImpiantoProdEnTermica  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CcostImpProdEnTermicaFer ccostImpProdEnTermicaFer;

    private com.hyperborea.sira.ws.CcostImpProdEnTermicaFt ccostImpProdEnTermicaFt;

    private java.lang.Integer idCcost;

    public CcostImpiantoProdEnTermica() {
    }

    public CcostImpiantoProdEnTermica(
           com.hyperborea.sira.ws.CcostImpProdEnTermicaFer ccostImpProdEnTermicaFer,
           com.hyperborea.sira.ws.CcostImpProdEnTermicaFt ccostImpProdEnTermicaFt,
           java.lang.Integer idCcost) {
           this.ccostImpProdEnTermicaFer = ccostImpProdEnTermicaFer;
           this.ccostImpProdEnTermicaFt = ccostImpProdEnTermicaFt;
           this.idCcost = idCcost;
    }


    /**
     * Gets the ccostImpProdEnTermicaFer value for this CcostImpiantoProdEnTermica.
     * 
     * @return ccostImpProdEnTermicaFer
     */
    public com.hyperborea.sira.ws.CcostImpProdEnTermicaFer getCcostImpProdEnTermicaFer() {
        return ccostImpProdEnTermicaFer;
    }


    /**
     * Sets the ccostImpProdEnTermicaFer value for this CcostImpiantoProdEnTermica.
     * 
     * @param ccostImpProdEnTermicaFer
     */
    public void setCcostImpProdEnTermicaFer(com.hyperborea.sira.ws.CcostImpProdEnTermicaFer ccostImpProdEnTermicaFer) {
        this.ccostImpProdEnTermicaFer = ccostImpProdEnTermicaFer;
    }


    /**
     * Gets the ccostImpProdEnTermicaFt value for this CcostImpiantoProdEnTermica.
     * 
     * @return ccostImpProdEnTermicaFt
     */
    public com.hyperborea.sira.ws.CcostImpProdEnTermicaFt getCcostImpProdEnTermicaFt() {
        return ccostImpProdEnTermicaFt;
    }


    /**
     * Sets the ccostImpProdEnTermicaFt value for this CcostImpiantoProdEnTermica.
     * 
     * @param ccostImpProdEnTermicaFt
     */
    public void setCcostImpProdEnTermicaFt(com.hyperborea.sira.ws.CcostImpProdEnTermicaFt ccostImpProdEnTermicaFt) {
        this.ccostImpProdEnTermicaFt = ccostImpProdEnTermicaFt;
    }


    /**
     * Gets the idCcost value for this CcostImpiantoProdEnTermica.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostImpiantoProdEnTermica.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostImpiantoProdEnTermica)) return false;
        CcostImpiantoProdEnTermica other = (CcostImpiantoProdEnTermica) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ccostImpProdEnTermicaFer==null && other.getCcostImpProdEnTermicaFer()==null) || 
             (this.ccostImpProdEnTermicaFer!=null &&
              this.ccostImpProdEnTermicaFer.equals(other.getCcostImpProdEnTermicaFer()))) &&
            ((this.ccostImpProdEnTermicaFt==null && other.getCcostImpProdEnTermicaFt()==null) || 
             (this.ccostImpProdEnTermicaFt!=null &&
              this.ccostImpProdEnTermicaFt.equals(other.getCcostImpProdEnTermicaFt()))) &&
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
        if (getCcostImpProdEnTermicaFer() != null) {
            _hashCode += getCcostImpProdEnTermicaFer().hashCode();
        }
        if (getCcostImpProdEnTermicaFt() != null) {
            _hashCode += getCcostImpProdEnTermicaFt().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostImpiantoProdEnTermica.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpiantoProdEnTermica"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostImpProdEnTermicaFer");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostImpProdEnTermicaFer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpProdEnTermicaFer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostImpProdEnTermicaFt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostImpProdEnTermicaFt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpProdEnTermicaFt"));
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
