/**
 * ProdottiForestaCertificata.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProdottiForestaCertificata  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String denominazione;

    private java.lang.Integer idProdotto;

    private com.hyperborea.sira.ws.Specie specie;

    private com.hyperborea.sira.ws.VocEfAttivitaPrimaria vocEfAttivitaPrimariaFc;

    private com.hyperborea.sira.ws.VocEfOutputCategory vocEfOutputCategory;

    private com.hyperborea.sira.ws.VocEfTipologiaProdotto vocEfTipologiaProdottoFc;

    public ProdottiForestaCertificata() {
    }

    public ProdottiForestaCertificata(
           java.lang.String denominazione,
           java.lang.Integer idProdotto,
           com.hyperborea.sira.ws.Specie specie,
           com.hyperborea.sira.ws.VocEfAttivitaPrimaria vocEfAttivitaPrimariaFc,
           com.hyperborea.sira.ws.VocEfOutputCategory vocEfOutputCategory,
           com.hyperborea.sira.ws.VocEfTipologiaProdotto vocEfTipologiaProdottoFc) {
        this.denominazione = denominazione;
        this.idProdotto = idProdotto;
        this.specie = specie;
        this.vocEfAttivitaPrimariaFc = vocEfAttivitaPrimariaFc;
        this.vocEfOutputCategory = vocEfOutputCategory;
        this.vocEfTipologiaProdottoFc = vocEfTipologiaProdottoFc;
    }


    /**
     * Gets the denominazione value for this ProdottiForestaCertificata.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this ProdottiForestaCertificata.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the idProdotto value for this ProdottiForestaCertificata.
     * 
     * @return idProdotto
     */
    public java.lang.Integer getIdProdotto() {
        return idProdotto;
    }


    /**
     * Sets the idProdotto value for this ProdottiForestaCertificata.
     * 
     * @param idProdotto
     */
    public void setIdProdotto(java.lang.Integer idProdotto) {
        this.idProdotto = idProdotto;
    }


    /**
     * Gets the specie value for this ProdottiForestaCertificata.
     * 
     * @return specie
     */
    public com.hyperborea.sira.ws.Specie getSpecie() {
        return specie;
    }


    /**
     * Sets the specie value for this ProdottiForestaCertificata.
     * 
     * @param specie
     */
    public void setSpecie(com.hyperborea.sira.ws.Specie specie) {
        this.specie = specie;
    }


    /**
     * Gets the vocEfAttivitaPrimariaFc value for this ProdottiForestaCertificata.
     * 
     * @return vocEfAttivitaPrimariaFc
     */
    public com.hyperborea.sira.ws.VocEfAttivitaPrimaria getVocEfAttivitaPrimariaFc() {
        return vocEfAttivitaPrimariaFc;
    }


    /**
     * Sets the vocEfAttivitaPrimariaFc value for this ProdottiForestaCertificata.
     * 
     * @param vocEfAttivitaPrimariaFc
     */
    public void setVocEfAttivitaPrimariaFc(com.hyperborea.sira.ws.VocEfAttivitaPrimaria vocEfAttivitaPrimariaFc) {
        this.vocEfAttivitaPrimariaFc = vocEfAttivitaPrimariaFc;
    }


    /**
     * Gets the vocEfOutputCategory value for this ProdottiForestaCertificata.
     * 
     * @return vocEfOutputCategory
     */
    public com.hyperborea.sira.ws.VocEfOutputCategory getVocEfOutputCategory() {
        return vocEfOutputCategory;
    }


    /**
     * Sets the vocEfOutputCategory value for this ProdottiForestaCertificata.
     * 
     * @param vocEfOutputCategory
     */
    public void setVocEfOutputCategory(com.hyperborea.sira.ws.VocEfOutputCategory vocEfOutputCategory) {
        this.vocEfOutputCategory = vocEfOutputCategory;
    }


    /**
     * Gets the vocEfTipologiaProdottoFc value for this ProdottiForestaCertificata.
     * 
     * @return vocEfTipologiaProdottoFc
     */
    public com.hyperborea.sira.ws.VocEfTipologiaProdotto getVocEfTipologiaProdottoFc() {
        return vocEfTipologiaProdottoFc;
    }


    /**
     * Sets the vocEfTipologiaProdottoFc value for this ProdottiForestaCertificata.
     * 
     * @param vocEfTipologiaProdottoFc
     */
    public void setVocEfTipologiaProdottoFc(com.hyperborea.sira.ws.VocEfTipologiaProdotto vocEfTipologiaProdottoFc) {
        this.vocEfTipologiaProdottoFc = vocEfTipologiaProdottoFc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdottiForestaCertificata)) return false;
        ProdottiForestaCertificata other = (ProdottiForestaCertificata) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.denominazione==null && other.getDenominazione()==null) || 
             (this.denominazione!=null &&
              this.denominazione.equals(other.getDenominazione()))) &&
            ((this.idProdotto==null && other.getIdProdotto()==null) || 
             (this.idProdotto!=null &&
              this.idProdotto.equals(other.getIdProdotto()))) &&
            ((this.specie==null && other.getSpecie()==null) || 
             (this.specie!=null &&
              this.specie.equals(other.getSpecie()))) &&
            ((this.vocEfAttivitaPrimariaFc==null && other.getVocEfAttivitaPrimariaFc()==null) || 
             (this.vocEfAttivitaPrimariaFc!=null &&
              this.vocEfAttivitaPrimariaFc.equals(other.getVocEfAttivitaPrimariaFc()))) &&
            ((this.vocEfOutputCategory==null && other.getVocEfOutputCategory()==null) || 
             (this.vocEfOutputCategory!=null &&
              this.vocEfOutputCategory.equals(other.getVocEfOutputCategory()))) &&
            ((this.vocEfTipologiaProdottoFc==null && other.getVocEfTipologiaProdottoFc()==null) || 
             (this.vocEfTipologiaProdottoFc!=null &&
              this.vocEfTipologiaProdottoFc.equals(other.getVocEfTipologiaProdottoFc())));
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
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
        }
        if (getIdProdotto() != null) {
            _hashCode += getIdProdotto().hashCode();
        }
        if (getSpecie() != null) {
            _hashCode += getSpecie().hashCode();
        }
        if (getVocEfAttivitaPrimariaFc() != null) {
            _hashCode += getVocEfAttivitaPrimariaFc().hashCode();
        }
        if (getVocEfOutputCategory() != null) {
            _hashCode += getVocEfOutputCategory().hashCode();
        }
        if (getVocEfTipologiaProdottoFc() != null) {
            _hashCode += getVocEfTipologiaProdottoFc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdottiForestaCertificata.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodottiForestaCertificata"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idProdotto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idProdotto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "specie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "specie"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfAttivitaPrimariaFc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfAttivitaPrimariaFc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfAttivitaPrimaria"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfOutputCategory");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfOutputCategory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfOutputCategory"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfTipologiaProdottoFc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfTipologiaProdottoFc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipologiaProdotto"));
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
