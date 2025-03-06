/**
 * CcostOpificioEf.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostOpificioEf  implements java.io.Serializable {
    private java.lang.Integer idCcost;

    private java.lang.Float quantita;

    private java.lang.String tipoProduzione;

    public CcostOpificioEf() {
    }

    public CcostOpificioEf(
           java.lang.Integer idCcost,
           java.lang.Float quantita,
           java.lang.String tipoProduzione) {
           this.idCcost = idCcost;
           this.quantita = quantita;
           this.tipoProduzione = tipoProduzione;
    }


    /**
     * Gets the idCcost value for this CcostOpificioEf.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostOpificioEf.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the quantita value for this CcostOpificioEf.
     * 
     * @return quantita
     */
    public java.lang.Float getQuantita() {
        return quantita;
    }


    /**
     * Sets the quantita value for this CcostOpificioEf.
     * 
     * @param quantita
     */
    public void setQuantita(java.lang.Float quantita) {
        this.quantita = quantita;
    }


    /**
     * Gets the tipoProduzione value for this CcostOpificioEf.
     * 
     * @return tipoProduzione
     */
    public java.lang.String getTipoProduzione() {
        return tipoProduzione;
    }


    /**
     * Sets the tipoProduzione value for this CcostOpificioEf.
     * 
     * @param tipoProduzione
     */
    public void setTipoProduzione(java.lang.String tipoProduzione) {
        this.tipoProduzione = tipoProduzione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostOpificioEf)) return false;
        CcostOpificioEf other = (CcostOpificioEf) obj;
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
            ((this.quantita==null && other.getQuantita()==null) || 
             (this.quantita!=null &&
              this.quantita.equals(other.getQuantita()))) &&
            ((this.tipoProduzione==null && other.getTipoProduzione()==null) || 
             (this.tipoProduzione!=null &&
              this.tipoProduzione.equals(other.getTipoProduzione())));
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
        if (getQuantita() != null) {
            _hashCode += getQuantita().hashCode();
        }
        if (getTipoProduzione() != null) {
            _hashCode += getTipoProduzione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostOpificioEf.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostOpificioEf"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quantita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoProduzione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoProduzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
