/**
 * CcostFabbricatoEf.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostFabbricatoEf  implements java.io.Serializable {
    private java.lang.Float altezza;

    private java.lang.Integer idCcost;

    private java.lang.Integer numPiani;

    private com.hyperborea.sira.ws.VocEfTipoFabbr vocEfTipoFabbr;

    public CcostFabbricatoEf() {
    }

    public CcostFabbricatoEf(
           java.lang.Float altezza,
           java.lang.Integer idCcost,
           java.lang.Integer numPiani,
           com.hyperborea.sira.ws.VocEfTipoFabbr vocEfTipoFabbr) {
           this.altezza = altezza;
           this.idCcost = idCcost;
           this.numPiani = numPiani;
           this.vocEfTipoFabbr = vocEfTipoFabbr;
    }


    /**
     * Gets the altezza value for this CcostFabbricatoEf.
     * 
     * @return altezza
     */
    public java.lang.Float getAltezza() {
        return altezza;
    }


    /**
     * Sets the altezza value for this CcostFabbricatoEf.
     * 
     * @param altezza
     */
    public void setAltezza(java.lang.Float altezza) {
        this.altezza = altezza;
    }


    /**
     * Gets the idCcost value for this CcostFabbricatoEf.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostFabbricatoEf.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the numPiani value for this CcostFabbricatoEf.
     * 
     * @return numPiani
     */
    public java.lang.Integer getNumPiani() {
        return numPiani;
    }


    /**
     * Sets the numPiani value for this CcostFabbricatoEf.
     * 
     * @param numPiani
     */
    public void setNumPiani(java.lang.Integer numPiani) {
        this.numPiani = numPiani;
    }


    /**
     * Gets the vocEfTipoFabbr value for this CcostFabbricatoEf.
     * 
     * @return vocEfTipoFabbr
     */
    public com.hyperborea.sira.ws.VocEfTipoFabbr getVocEfTipoFabbr() {
        return vocEfTipoFabbr;
    }


    /**
     * Sets the vocEfTipoFabbr value for this CcostFabbricatoEf.
     * 
     * @param vocEfTipoFabbr
     */
    public void setVocEfTipoFabbr(com.hyperborea.sira.ws.VocEfTipoFabbr vocEfTipoFabbr) {
        this.vocEfTipoFabbr = vocEfTipoFabbr;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostFabbricatoEf)) return false;
        CcostFabbricatoEf other = (CcostFabbricatoEf) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.altezza==null && other.getAltezza()==null) || 
             (this.altezza!=null &&
              this.altezza.equals(other.getAltezza()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.numPiani==null && other.getNumPiani()==null) || 
             (this.numPiani!=null &&
              this.numPiani.equals(other.getNumPiani()))) &&
            ((this.vocEfTipoFabbr==null && other.getVocEfTipoFabbr()==null) || 
             (this.vocEfTipoFabbr!=null &&
              this.vocEfTipoFabbr.equals(other.getVocEfTipoFabbr())));
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
        if (getAltezza() != null) {
            _hashCode += getAltezza().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getNumPiani() != null) {
            _hashCode += getNumPiani().hashCode();
        }
        if (getVocEfTipoFabbr() != null) {
            _hashCode += getVocEfTipoFabbr().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostFabbricatoEf.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostFabbricatoEf"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altezza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "altezza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
        elemField.setFieldName("numPiani");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numPiani"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfTipoFabbr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfTipoFabbr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipoFabbr"));
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
