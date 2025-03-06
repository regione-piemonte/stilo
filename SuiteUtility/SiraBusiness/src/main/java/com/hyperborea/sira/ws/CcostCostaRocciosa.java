/**
 * CcostCostaRocciosa.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostCostaRocciosa  implements java.io.Serializable {
    private java.lang.Integer annoRilievo;

    private java.lang.String fenomenoDissesto;

    private java.lang.Integer idCcost;

    private java.lang.String litologia;

    private java.lang.Float lunghezza;

    private java.lang.String morfologia;

    private java.lang.String tipologia;

    private java.lang.String usoCosta;

    private com.hyperborea.sira.ws.VocTendEvolCRocc vocTendEvolCRocc;

    public CcostCostaRocciosa() {
    }

    public CcostCostaRocciosa(
           java.lang.Integer annoRilievo,
           java.lang.String fenomenoDissesto,
           java.lang.Integer idCcost,
           java.lang.String litologia,
           java.lang.Float lunghezza,
           java.lang.String morfologia,
           java.lang.String tipologia,
           java.lang.String usoCosta,
           com.hyperborea.sira.ws.VocTendEvolCRocc vocTendEvolCRocc) {
           this.annoRilievo = annoRilievo;
           this.fenomenoDissesto = fenomenoDissesto;
           this.idCcost = idCcost;
           this.litologia = litologia;
           this.lunghezza = lunghezza;
           this.morfologia = morfologia;
           this.tipologia = tipologia;
           this.usoCosta = usoCosta;
           this.vocTendEvolCRocc = vocTendEvolCRocc;
    }


    /**
     * Gets the annoRilievo value for this CcostCostaRocciosa.
     * 
     * @return annoRilievo
     */
    public java.lang.Integer getAnnoRilievo() {
        return annoRilievo;
    }


    /**
     * Sets the annoRilievo value for this CcostCostaRocciosa.
     * 
     * @param annoRilievo
     */
    public void setAnnoRilievo(java.lang.Integer annoRilievo) {
        this.annoRilievo = annoRilievo;
    }


    /**
     * Gets the fenomenoDissesto value for this CcostCostaRocciosa.
     * 
     * @return fenomenoDissesto
     */
    public java.lang.String getFenomenoDissesto() {
        return fenomenoDissesto;
    }


    /**
     * Sets the fenomenoDissesto value for this CcostCostaRocciosa.
     * 
     * @param fenomenoDissesto
     */
    public void setFenomenoDissesto(java.lang.String fenomenoDissesto) {
        this.fenomenoDissesto = fenomenoDissesto;
    }


    /**
     * Gets the idCcost value for this CcostCostaRocciosa.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostCostaRocciosa.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the litologia value for this CcostCostaRocciosa.
     * 
     * @return litologia
     */
    public java.lang.String getLitologia() {
        return litologia;
    }


    /**
     * Sets the litologia value for this CcostCostaRocciosa.
     * 
     * @param litologia
     */
    public void setLitologia(java.lang.String litologia) {
        this.litologia = litologia;
    }


    /**
     * Gets the lunghezza value for this CcostCostaRocciosa.
     * 
     * @return lunghezza
     */
    public java.lang.Float getLunghezza() {
        return lunghezza;
    }


    /**
     * Sets the lunghezza value for this CcostCostaRocciosa.
     * 
     * @param lunghezza
     */
    public void setLunghezza(java.lang.Float lunghezza) {
        this.lunghezza = lunghezza;
    }


    /**
     * Gets the morfologia value for this CcostCostaRocciosa.
     * 
     * @return morfologia
     */
    public java.lang.String getMorfologia() {
        return morfologia;
    }


    /**
     * Sets the morfologia value for this CcostCostaRocciosa.
     * 
     * @param morfologia
     */
    public void setMorfologia(java.lang.String morfologia) {
        this.morfologia = morfologia;
    }


    /**
     * Gets the tipologia value for this CcostCostaRocciosa.
     * 
     * @return tipologia
     */
    public java.lang.String getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this CcostCostaRocciosa.
     * 
     * @param tipologia
     */
    public void setTipologia(java.lang.String tipologia) {
        this.tipologia = tipologia;
    }


    /**
     * Gets the usoCosta value for this CcostCostaRocciosa.
     * 
     * @return usoCosta
     */
    public java.lang.String getUsoCosta() {
        return usoCosta;
    }


    /**
     * Sets the usoCosta value for this CcostCostaRocciosa.
     * 
     * @param usoCosta
     */
    public void setUsoCosta(java.lang.String usoCosta) {
        this.usoCosta = usoCosta;
    }


    /**
     * Gets the vocTendEvolCRocc value for this CcostCostaRocciosa.
     * 
     * @return vocTendEvolCRocc
     */
    public com.hyperborea.sira.ws.VocTendEvolCRocc getVocTendEvolCRocc() {
        return vocTendEvolCRocc;
    }


    /**
     * Sets the vocTendEvolCRocc value for this CcostCostaRocciosa.
     * 
     * @param vocTendEvolCRocc
     */
    public void setVocTendEvolCRocc(com.hyperborea.sira.ws.VocTendEvolCRocc vocTendEvolCRocc) {
        this.vocTendEvolCRocc = vocTendEvolCRocc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostCostaRocciosa)) return false;
        CcostCostaRocciosa other = (CcostCostaRocciosa) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.annoRilievo==null && other.getAnnoRilievo()==null) || 
             (this.annoRilievo!=null &&
              this.annoRilievo.equals(other.getAnnoRilievo()))) &&
            ((this.fenomenoDissesto==null && other.getFenomenoDissesto()==null) || 
             (this.fenomenoDissesto!=null &&
              this.fenomenoDissesto.equals(other.getFenomenoDissesto()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.litologia==null && other.getLitologia()==null) || 
             (this.litologia!=null &&
              this.litologia.equals(other.getLitologia()))) &&
            ((this.lunghezza==null && other.getLunghezza()==null) || 
             (this.lunghezza!=null &&
              this.lunghezza.equals(other.getLunghezza()))) &&
            ((this.morfologia==null && other.getMorfologia()==null) || 
             (this.morfologia!=null &&
              this.morfologia.equals(other.getMorfologia()))) &&
            ((this.tipologia==null && other.getTipologia()==null) || 
             (this.tipologia!=null &&
              this.tipologia.equals(other.getTipologia()))) &&
            ((this.usoCosta==null && other.getUsoCosta()==null) || 
             (this.usoCosta!=null &&
              this.usoCosta.equals(other.getUsoCosta()))) &&
            ((this.vocTendEvolCRocc==null && other.getVocTendEvolCRocc()==null) || 
             (this.vocTendEvolCRocc!=null &&
              this.vocTendEvolCRocc.equals(other.getVocTendEvolCRocc())));
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
        if (getAnnoRilievo() != null) {
            _hashCode += getAnnoRilievo().hashCode();
        }
        if (getFenomenoDissesto() != null) {
            _hashCode += getFenomenoDissesto().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getLitologia() != null) {
            _hashCode += getLitologia().hashCode();
        }
        if (getLunghezza() != null) {
            _hashCode += getLunghezza().hashCode();
        }
        if (getMorfologia() != null) {
            _hashCode += getMorfologia().hashCode();
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        if (getUsoCosta() != null) {
            _hashCode += getUsoCosta().hashCode();
        }
        if (getVocTendEvolCRocc() != null) {
            _hashCode += getVocTendEvolCRocc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostCostaRocciosa.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCostaRocciosa"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoRilievo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoRilievo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fenomenoDissesto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fenomenoDissesto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("litologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "litologia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("morfologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "morfologia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usoCosta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "usoCosta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTendEvolCRocc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTendEvolCRocc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTendEvolCRocc"));
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
