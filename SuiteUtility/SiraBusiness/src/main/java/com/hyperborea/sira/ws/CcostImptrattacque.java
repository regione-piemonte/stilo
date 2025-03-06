/**
 * CcostImptrattacque.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostImptrattacque  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CcostImpdepurazione ccostImpdepurazione;

    private java.lang.Integer idCcost;

    public CcostImptrattacque() {
    }

    public CcostImptrattacque(
           com.hyperborea.sira.ws.CcostImpdepurazione ccostImpdepurazione,
           java.lang.Integer idCcost) {
        this.ccostImpdepurazione = ccostImpdepurazione;
        this.idCcost = idCcost;
    }


    /**
     * Gets the ccostImpdepurazione value for this CcostImptrattacque.
     * 
     * @return ccostImpdepurazione
     */
    public com.hyperborea.sira.ws.CcostImpdepurazione getCcostImpdepurazione() {
        return ccostImpdepurazione;
    }


    /**
     * Sets the ccostImpdepurazione value for this CcostImptrattacque.
     * 
     * @param ccostImpdepurazione
     */
    public void setCcostImpdepurazione(com.hyperborea.sira.ws.CcostImpdepurazione ccostImpdepurazione) {
        this.ccostImpdepurazione = ccostImpdepurazione;
    }


    /**
     * Gets the idCcost value for this CcostImptrattacque.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostImptrattacque.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostImptrattacque)) return false;
        CcostImptrattacque other = (CcostImptrattacque) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.ccostImpdepurazione==null && other.getCcostImpdepurazione()==null) || 
             (this.ccostImpdepurazione!=null &&
              this.ccostImpdepurazione.equals(other.getCcostImpdepurazione()))) &&
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
        int _hashCode = super.hashCode();
        if (getCcostImpdepurazione() != null) {
            _hashCode += getCcostImpdepurazione().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostImptrattacque.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImptrattacque"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostImpdepurazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostImpdepurazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpdepurazione"));
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
