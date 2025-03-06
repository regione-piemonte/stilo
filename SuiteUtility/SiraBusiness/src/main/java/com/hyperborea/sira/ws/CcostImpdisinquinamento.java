/**
 * CcostImpdisinquinamento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostImpdisinquinamento  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CcostImpGestioneRifiuti ccostImpGestioneRifiuti;

    private com.hyperborea.sira.ws.CcostImptrattacque ccostImptrattacque;

    private java.lang.Integer idCcost;

    public CcostImpdisinquinamento() {
    }

    public CcostImpdisinquinamento(
           com.hyperborea.sira.ws.CcostImpGestioneRifiuti ccostImpGestioneRifiuti,
           com.hyperborea.sira.ws.CcostImptrattacque ccostImptrattacque,
           java.lang.Integer idCcost) {
           this.ccostImpGestioneRifiuti = ccostImpGestioneRifiuti;
           this.ccostImptrattacque = ccostImptrattacque;
           this.idCcost = idCcost;
    }


    /**
     * Gets the ccostImpGestioneRifiuti value for this CcostImpdisinquinamento.
     * 
     * @return ccostImpGestioneRifiuti
     */
    public com.hyperborea.sira.ws.CcostImpGestioneRifiuti getCcostImpGestioneRifiuti() {
        return ccostImpGestioneRifiuti;
    }


    /**
     * Sets the ccostImpGestioneRifiuti value for this CcostImpdisinquinamento.
     * 
     * @param ccostImpGestioneRifiuti
     */
    public void setCcostImpGestioneRifiuti(com.hyperborea.sira.ws.CcostImpGestioneRifiuti ccostImpGestioneRifiuti) {
        this.ccostImpGestioneRifiuti = ccostImpGestioneRifiuti;
    }


    /**
     * Gets the ccostImptrattacque value for this CcostImpdisinquinamento.
     * 
     * @return ccostImptrattacque
     */
    public com.hyperborea.sira.ws.CcostImptrattacque getCcostImptrattacque() {
        return ccostImptrattacque;
    }


    /**
     * Sets the ccostImptrattacque value for this CcostImpdisinquinamento.
     * 
     * @param ccostImptrattacque
     */
    public void setCcostImptrattacque(com.hyperborea.sira.ws.CcostImptrattacque ccostImptrattacque) {
        this.ccostImptrattacque = ccostImptrattacque;
    }


    /**
     * Gets the idCcost value for this CcostImpdisinquinamento.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostImpdisinquinamento.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostImpdisinquinamento)) return false;
        CcostImpdisinquinamento other = (CcostImpdisinquinamento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ccostImpGestioneRifiuti==null && other.getCcostImpGestioneRifiuti()==null) || 
             (this.ccostImpGestioneRifiuti!=null &&
              this.ccostImpGestioneRifiuti.equals(other.getCcostImpGestioneRifiuti()))) &&
            ((this.ccostImptrattacque==null && other.getCcostImptrattacque()==null) || 
             (this.ccostImptrattacque!=null &&
              this.ccostImptrattacque.equals(other.getCcostImptrattacque()))) &&
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
        if (getCcostImpGestioneRifiuti() != null) {
            _hashCode += getCcostImpGestioneRifiuti().hashCode();
        }
        if (getCcostImptrattacque() != null) {
            _hashCode += getCcostImptrattacque().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostImpdisinquinamento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpdisinquinamento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostImpGestioneRifiuti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostImpGestioneRifiuti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpGestioneRifiuti"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostImptrattacque");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostImptrattacque"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImptrattacque"));
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
