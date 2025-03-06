/**
 * CcostSorgAcusticaLineare.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostSorgAcusticaLineare  implements java.io.Serializable {
    private java.lang.Float fasciaPertinenza;

    private java.lang.Integer idCcost;

    private java.lang.Float lunghezza;

    private com.hyperborea.sira.ws.VocTipologiaFerrovia vocTipologiaFerrovia;

    private com.hyperborea.sira.ws.VocTipologiaInfr vocTipologiaInfr;

    private com.hyperborea.sira.ws.VocTipologiaStrada vocTipologiaStrada;

    public CcostSorgAcusticaLineare() {
    }

    public CcostSorgAcusticaLineare(
           java.lang.Float fasciaPertinenza,
           java.lang.Integer idCcost,
           java.lang.Float lunghezza,
           com.hyperborea.sira.ws.VocTipologiaFerrovia vocTipologiaFerrovia,
           com.hyperborea.sira.ws.VocTipologiaInfr vocTipologiaInfr,
           com.hyperborea.sira.ws.VocTipologiaStrada vocTipologiaStrada) {
           this.fasciaPertinenza = fasciaPertinenza;
           this.idCcost = idCcost;
           this.lunghezza = lunghezza;
           this.vocTipologiaFerrovia = vocTipologiaFerrovia;
           this.vocTipologiaInfr = vocTipologiaInfr;
           this.vocTipologiaStrada = vocTipologiaStrada;
    }


    /**
     * Gets the fasciaPertinenza value for this CcostSorgAcusticaLineare.
     * 
     * @return fasciaPertinenza
     */
    public java.lang.Float getFasciaPertinenza() {
        return fasciaPertinenza;
    }


    /**
     * Sets the fasciaPertinenza value for this CcostSorgAcusticaLineare.
     * 
     * @param fasciaPertinenza
     */
    public void setFasciaPertinenza(java.lang.Float fasciaPertinenza) {
        this.fasciaPertinenza = fasciaPertinenza;
    }


    /**
     * Gets the idCcost value for this CcostSorgAcusticaLineare.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostSorgAcusticaLineare.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the lunghezza value for this CcostSorgAcusticaLineare.
     * 
     * @return lunghezza
     */
    public java.lang.Float getLunghezza() {
        return lunghezza;
    }


    /**
     * Sets the lunghezza value for this CcostSorgAcusticaLineare.
     * 
     * @param lunghezza
     */
    public void setLunghezza(java.lang.Float lunghezza) {
        this.lunghezza = lunghezza;
    }


    /**
     * Gets the vocTipologiaFerrovia value for this CcostSorgAcusticaLineare.
     * 
     * @return vocTipologiaFerrovia
     */
    public com.hyperborea.sira.ws.VocTipologiaFerrovia getVocTipologiaFerrovia() {
        return vocTipologiaFerrovia;
    }


    /**
     * Sets the vocTipologiaFerrovia value for this CcostSorgAcusticaLineare.
     * 
     * @param vocTipologiaFerrovia
     */
    public void setVocTipologiaFerrovia(com.hyperborea.sira.ws.VocTipologiaFerrovia vocTipologiaFerrovia) {
        this.vocTipologiaFerrovia = vocTipologiaFerrovia;
    }


    /**
     * Gets the vocTipologiaInfr value for this CcostSorgAcusticaLineare.
     * 
     * @return vocTipologiaInfr
     */
    public com.hyperborea.sira.ws.VocTipologiaInfr getVocTipologiaInfr() {
        return vocTipologiaInfr;
    }


    /**
     * Sets the vocTipologiaInfr value for this CcostSorgAcusticaLineare.
     * 
     * @param vocTipologiaInfr
     */
    public void setVocTipologiaInfr(com.hyperborea.sira.ws.VocTipologiaInfr vocTipologiaInfr) {
        this.vocTipologiaInfr = vocTipologiaInfr;
    }


    /**
     * Gets the vocTipologiaStrada value for this CcostSorgAcusticaLineare.
     * 
     * @return vocTipologiaStrada
     */
    public com.hyperborea.sira.ws.VocTipologiaStrada getVocTipologiaStrada() {
        return vocTipologiaStrada;
    }


    /**
     * Sets the vocTipologiaStrada value for this CcostSorgAcusticaLineare.
     * 
     * @param vocTipologiaStrada
     */
    public void setVocTipologiaStrada(com.hyperborea.sira.ws.VocTipologiaStrada vocTipologiaStrada) {
        this.vocTipologiaStrada = vocTipologiaStrada;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostSorgAcusticaLineare)) return false;
        CcostSorgAcusticaLineare other = (CcostSorgAcusticaLineare) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fasciaPertinenza==null && other.getFasciaPertinenza()==null) || 
             (this.fasciaPertinenza!=null &&
              this.fasciaPertinenza.equals(other.getFasciaPertinenza()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.lunghezza==null && other.getLunghezza()==null) || 
             (this.lunghezza!=null &&
              this.lunghezza.equals(other.getLunghezza()))) &&
            ((this.vocTipologiaFerrovia==null && other.getVocTipologiaFerrovia()==null) || 
             (this.vocTipologiaFerrovia!=null &&
              this.vocTipologiaFerrovia.equals(other.getVocTipologiaFerrovia()))) &&
            ((this.vocTipologiaInfr==null && other.getVocTipologiaInfr()==null) || 
             (this.vocTipologiaInfr!=null &&
              this.vocTipologiaInfr.equals(other.getVocTipologiaInfr()))) &&
            ((this.vocTipologiaStrada==null && other.getVocTipologiaStrada()==null) || 
             (this.vocTipologiaStrada!=null &&
              this.vocTipologiaStrada.equals(other.getVocTipologiaStrada())));
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
        if (getFasciaPertinenza() != null) {
            _hashCode += getFasciaPertinenza().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getLunghezza() != null) {
            _hashCode += getLunghezza().hashCode();
        }
        if (getVocTipologiaFerrovia() != null) {
            _hashCode += getVocTipologiaFerrovia().hashCode();
        }
        if (getVocTipologiaInfr() != null) {
            _hashCode += getVocTipologiaInfr().hashCode();
        }
        if (getVocTipologiaStrada() != null) {
            _hashCode += getVocTipologiaStrada().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostSorgAcusticaLineare.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSorgAcusticaLineare"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fasciaPertinenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fasciaPertinenza"));
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
        elemField.setFieldName("lunghezza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lunghezza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaFerrovia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaFerrovia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaFerrovia"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaInfr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaInfr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaInfr"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaStrada");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaStrada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaStrada"));
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
