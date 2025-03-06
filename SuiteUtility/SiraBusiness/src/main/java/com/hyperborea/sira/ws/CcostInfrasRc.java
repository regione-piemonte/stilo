/**
 * CcostInfrasRc.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostInfrasRc  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CcostImpiantiRc ccostImpiantiRc;

    private com.hyperborea.sira.ws.CcostSitiRc ccostSitiRc;

    private java.lang.Integer idCcost;

    public CcostInfrasRc() {
    }

    public CcostInfrasRc(
           com.hyperborea.sira.ws.CcostImpiantiRc ccostImpiantiRc,
           com.hyperborea.sira.ws.CcostSitiRc ccostSitiRc,
           java.lang.Integer idCcost) {
           this.ccostImpiantiRc = ccostImpiantiRc;
           this.ccostSitiRc = ccostSitiRc;
           this.idCcost = idCcost;
    }


    /**
     * Gets the ccostImpiantiRc value for this CcostInfrasRc.
     * 
     * @return ccostImpiantiRc
     */
    public com.hyperborea.sira.ws.CcostImpiantiRc getCcostImpiantiRc() {
        return ccostImpiantiRc;
    }


    /**
     * Sets the ccostImpiantiRc value for this CcostInfrasRc.
     * 
     * @param ccostImpiantiRc
     */
    public void setCcostImpiantiRc(com.hyperborea.sira.ws.CcostImpiantiRc ccostImpiantiRc) {
        this.ccostImpiantiRc = ccostImpiantiRc;
    }


    /**
     * Gets the ccostSitiRc value for this CcostInfrasRc.
     * 
     * @return ccostSitiRc
     */
    public com.hyperborea.sira.ws.CcostSitiRc getCcostSitiRc() {
        return ccostSitiRc;
    }


    /**
     * Sets the ccostSitiRc value for this CcostInfrasRc.
     * 
     * @param ccostSitiRc
     */
    public void setCcostSitiRc(com.hyperborea.sira.ws.CcostSitiRc ccostSitiRc) {
        this.ccostSitiRc = ccostSitiRc;
    }


    /**
     * Gets the idCcost value for this CcostInfrasRc.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostInfrasRc.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostInfrasRc)) return false;
        CcostInfrasRc other = (CcostInfrasRc) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ccostImpiantiRc==null && other.getCcostImpiantiRc()==null) || 
             (this.ccostImpiantiRc!=null &&
              this.ccostImpiantiRc.equals(other.getCcostImpiantiRc()))) &&
            ((this.ccostSitiRc==null && other.getCcostSitiRc()==null) || 
             (this.ccostSitiRc!=null &&
              this.ccostSitiRc.equals(other.getCcostSitiRc()))) &&
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
        if (getCcostImpiantiRc() != null) {
            _hashCode += getCcostImpiantiRc().hashCode();
        }
        if (getCcostSitiRc() != null) {
            _hashCode += getCcostSitiRc().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostInfrasRc.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostInfrasRc"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostImpiantiRc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostImpiantiRc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpiantiRc"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostSitiRc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostSitiRc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSitiRc"));
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
