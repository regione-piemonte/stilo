/**
 * Specie.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Specie  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private org.apache.axis.types.UnsignedShort allegatoIi;

    private org.apache.axis.types.UnsignedShort allegatoIv;

    private java.lang.Integer alloctona;

    private java.lang.String codice;

    private java.lang.Integer contingenteNum;

    private java.lang.String denominazione;

    private java.lang.String fenologia;

    private java.lang.Integer idSpecie;

    private java.lang.String minacciaGlo;

    private java.lang.String minacciaNaz;

    private java.lang.String nomecomune;

    private java.lang.String nomecomuneFr;

    private java.lang.String protezioneCom;

    private java.lang.String protezioneReg;

    private java.lang.String regno;

    private com.hyperborea.sira.ws.TipoIsolamento tipoIsolamento;

    private com.hyperborea.sira.ws.TipologieSpecie tipologieSpecie;

    public Specie() {
    }

    public Specie(
           org.apache.axis.types.UnsignedShort allegatoIi,
           org.apache.axis.types.UnsignedShort allegatoIv,
           java.lang.Integer alloctona,
           java.lang.String codice,
           java.lang.Integer contingenteNum,
           java.lang.String denominazione,
           java.lang.String fenologia,
           java.lang.Integer idSpecie,
           java.lang.String minacciaGlo,
           java.lang.String minacciaNaz,
           java.lang.String nomecomune,
           java.lang.String nomecomuneFr,
           java.lang.String protezioneCom,
           java.lang.String protezioneReg,
           java.lang.String regno,
           com.hyperborea.sira.ws.TipoIsolamento tipoIsolamento,
           com.hyperborea.sira.ws.TipologieSpecie tipologieSpecie) {
        this.allegatoIi = allegatoIi;
        this.allegatoIv = allegatoIv;
        this.alloctona = alloctona;
        this.codice = codice;
        this.contingenteNum = contingenteNum;
        this.denominazione = denominazione;
        this.fenologia = fenologia;
        this.idSpecie = idSpecie;
        this.minacciaGlo = minacciaGlo;
        this.minacciaNaz = minacciaNaz;
        this.nomecomune = nomecomune;
        this.nomecomuneFr = nomecomuneFr;
        this.protezioneCom = protezioneCom;
        this.protezioneReg = protezioneReg;
        this.regno = regno;
        this.tipoIsolamento = tipoIsolamento;
        this.tipologieSpecie = tipologieSpecie;
    }


    /**
     * Gets the allegatoIi value for this Specie.
     * 
     * @return allegatoIi
     */
    public org.apache.axis.types.UnsignedShort getAllegatoIi() {
        return allegatoIi;
    }


    /**
     * Sets the allegatoIi value for this Specie.
     * 
     * @param allegatoIi
     */
    public void setAllegatoIi(org.apache.axis.types.UnsignedShort allegatoIi) {
        this.allegatoIi = allegatoIi;
    }


    /**
     * Gets the allegatoIv value for this Specie.
     * 
     * @return allegatoIv
     */
    public org.apache.axis.types.UnsignedShort getAllegatoIv() {
        return allegatoIv;
    }


    /**
     * Sets the allegatoIv value for this Specie.
     * 
     * @param allegatoIv
     */
    public void setAllegatoIv(org.apache.axis.types.UnsignedShort allegatoIv) {
        this.allegatoIv = allegatoIv;
    }


    /**
     * Gets the alloctona value for this Specie.
     * 
     * @return alloctona
     */
    public java.lang.Integer getAlloctona() {
        return alloctona;
    }


    /**
     * Sets the alloctona value for this Specie.
     * 
     * @param alloctona
     */
    public void setAlloctona(java.lang.Integer alloctona) {
        this.alloctona = alloctona;
    }


    /**
     * Gets the codice value for this Specie.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this Specie.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the contingenteNum value for this Specie.
     * 
     * @return contingenteNum
     */
    public java.lang.Integer getContingenteNum() {
        return contingenteNum;
    }


    /**
     * Sets the contingenteNum value for this Specie.
     * 
     * @param contingenteNum
     */
    public void setContingenteNum(java.lang.Integer contingenteNum) {
        this.contingenteNum = contingenteNum;
    }


    /**
     * Gets the denominazione value for this Specie.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this Specie.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the fenologia value for this Specie.
     * 
     * @return fenologia
     */
    public java.lang.String getFenologia() {
        return fenologia;
    }


    /**
     * Sets the fenologia value for this Specie.
     * 
     * @param fenologia
     */
    public void setFenologia(java.lang.String fenologia) {
        this.fenologia = fenologia;
    }


    /**
     * Gets the idSpecie value for this Specie.
     * 
     * @return idSpecie
     */
    public java.lang.Integer getIdSpecie() {
        return idSpecie;
    }


    /**
     * Sets the idSpecie value for this Specie.
     * 
     * @param idSpecie
     */
    public void setIdSpecie(java.lang.Integer idSpecie) {
        this.idSpecie = idSpecie;
    }


    /**
     * Gets the minacciaGlo value for this Specie.
     * 
     * @return minacciaGlo
     */
    public java.lang.String getMinacciaGlo() {
        return minacciaGlo;
    }


    /**
     * Sets the minacciaGlo value for this Specie.
     * 
     * @param minacciaGlo
     */
    public void setMinacciaGlo(java.lang.String minacciaGlo) {
        this.minacciaGlo = minacciaGlo;
    }


    /**
     * Gets the minacciaNaz value for this Specie.
     * 
     * @return minacciaNaz
     */
    public java.lang.String getMinacciaNaz() {
        return minacciaNaz;
    }


    /**
     * Sets the minacciaNaz value for this Specie.
     * 
     * @param minacciaNaz
     */
    public void setMinacciaNaz(java.lang.String minacciaNaz) {
        this.minacciaNaz = minacciaNaz;
    }


    /**
     * Gets the nomecomune value for this Specie.
     * 
     * @return nomecomune
     */
    public java.lang.String getNomecomune() {
        return nomecomune;
    }


    /**
     * Sets the nomecomune value for this Specie.
     * 
     * @param nomecomune
     */
    public void setNomecomune(java.lang.String nomecomune) {
        this.nomecomune = nomecomune;
    }


    /**
     * Gets the nomecomuneFr value for this Specie.
     * 
     * @return nomecomuneFr
     */
    public java.lang.String getNomecomuneFr() {
        return nomecomuneFr;
    }


    /**
     * Sets the nomecomuneFr value for this Specie.
     * 
     * @param nomecomuneFr
     */
    public void setNomecomuneFr(java.lang.String nomecomuneFr) {
        this.nomecomuneFr = nomecomuneFr;
    }


    /**
     * Gets the protezioneCom value for this Specie.
     * 
     * @return protezioneCom
     */
    public java.lang.String getProtezioneCom() {
        return protezioneCom;
    }


    /**
     * Sets the protezioneCom value for this Specie.
     * 
     * @param protezioneCom
     */
    public void setProtezioneCom(java.lang.String protezioneCom) {
        this.protezioneCom = protezioneCom;
    }


    /**
     * Gets the protezioneReg value for this Specie.
     * 
     * @return protezioneReg
     */
    public java.lang.String getProtezioneReg() {
        return protezioneReg;
    }


    /**
     * Sets the protezioneReg value for this Specie.
     * 
     * @param protezioneReg
     */
    public void setProtezioneReg(java.lang.String protezioneReg) {
        this.protezioneReg = protezioneReg;
    }


    /**
     * Gets the regno value for this Specie.
     * 
     * @return regno
     */
    public java.lang.String getRegno() {
        return regno;
    }


    /**
     * Sets the regno value for this Specie.
     * 
     * @param regno
     */
    public void setRegno(java.lang.String regno) {
        this.regno = regno;
    }


    /**
     * Gets the tipoIsolamento value for this Specie.
     * 
     * @return tipoIsolamento
     */
    public com.hyperborea.sira.ws.TipoIsolamento getTipoIsolamento() {
        return tipoIsolamento;
    }


    /**
     * Sets the tipoIsolamento value for this Specie.
     * 
     * @param tipoIsolamento
     */
    public void setTipoIsolamento(com.hyperborea.sira.ws.TipoIsolamento tipoIsolamento) {
        this.tipoIsolamento = tipoIsolamento;
    }


    /**
     * Gets the tipologieSpecie value for this Specie.
     * 
     * @return tipologieSpecie
     */
    public com.hyperborea.sira.ws.TipologieSpecie getTipologieSpecie() {
        return tipologieSpecie;
    }


    /**
     * Sets the tipologieSpecie value for this Specie.
     * 
     * @param tipologieSpecie
     */
    public void setTipologieSpecie(com.hyperborea.sira.ws.TipologieSpecie tipologieSpecie) {
        this.tipologieSpecie = tipologieSpecie;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Specie)) return false;
        Specie other = (Specie) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.allegatoIi==null && other.getAllegatoIi()==null) || 
             (this.allegatoIi!=null &&
              this.allegatoIi.equals(other.getAllegatoIi()))) &&
            ((this.allegatoIv==null && other.getAllegatoIv()==null) || 
             (this.allegatoIv!=null &&
              this.allegatoIv.equals(other.getAllegatoIv()))) &&
            ((this.alloctona==null && other.getAlloctona()==null) || 
             (this.alloctona!=null &&
              this.alloctona.equals(other.getAlloctona()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.contingenteNum==null && other.getContingenteNum()==null) || 
             (this.contingenteNum!=null &&
              this.contingenteNum.equals(other.getContingenteNum()))) &&
            ((this.denominazione==null && other.getDenominazione()==null) || 
             (this.denominazione!=null &&
              this.denominazione.equals(other.getDenominazione()))) &&
            ((this.fenologia==null && other.getFenologia()==null) || 
             (this.fenologia!=null &&
              this.fenologia.equals(other.getFenologia()))) &&
            ((this.idSpecie==null && other.getIdSpecie()==null) || 
             (this.idSpecie!=null &&
              this.idSpecie.equals(other.getIdSpecie()))) &&
            ((this.minacciaGlo==null && other.getMinacciaGlo()==null) || 
             (this.minacciaGlo!=null &&
              this.minacciaGlo.equals(other.getMinacciaGlo()))) &&
            ((this.minacciaNaz==null && other.getMinacciaNaz()==null) || 
             (this.minacciaNaz!=null &&
              this.minacciaNaz.equals(other.getMinacciaNaz()))) &&
            ((this.nomecomune==null && other.getNomecomune()==null) || 
             (this.nomecomune!=null &&
              this.nomecomune.equals(other.getNomecomune()))) &&
            ((this.nomecomuneFr==null && other.getNomecomuneFr()==null) || 
             (this.nomecomuneFr!=null &&
              this.nomecomuneFr.equals(other.getNomecomuneFr()))) &&
            ((this.protezioneCom==null && other.getProtezioneCom()==null) || 
             (this.protezioneCom!=null &&
              this.protezioneCom.equals(other.getProtezioneCom()))) &&
            ((this.protezioneReg==null && other.getProtezioneReg()==null) || 
             (this.protezioneReg!=null &&
              this.protezioneReg.equals(other.getProtezioneReg()))) &&
            ((this.regno==null && other.getRegno()==null) || 
             (this.regno!=null &&
              this.regno.equals(other.getRegno()))) &&
            ((this.tipoIsolamento==null && other.getTipoIsolamento()==null) || 
             (this.tipoIsolamento!=null &&
              this.tipoIsolamento.equals(other.getTipoIsolamento()))) &&
            ((this.tipologieSpecie==null && other.getTipologieSpecie()==null) || 
             (this.tipologieSpecie!=null &&
              this.tipologieSpecie.equals(other.getTipologieSpecie())));
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
        if (getAllegatoIi() != null) {
            _hashCode += getAllegatoIi().hashCode();
        }
        if (getAllegatoIv() != null) {
            _hashCode += getAllegatoIv().hashCode();
        }
        if (getAlloctona() != null) {
            _hashCode += getAlloctona().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getContingenteNum() != null) {
            _hashCode += getContingenteNum().hashCode();
        }
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
        }
        if (getFenologia() != null) {
            _hashCode += getFenologia().hashCode();
        }
        if (getIdSpecie() != null) {
            _hashCode += getIdSpecie().hashCode();
        }
        if (getMinacciaGlo() != null) {
            _hashCode += getMinacciaGlo().hashCode();
        }
        if (getMinacciaNaz() != null) {
            _hashCode += getMinacciaNaz().hashCode();
        }
        if (getNomecomune() != null) {
            _hashCode += getNomecomune().hashCode();
        }
        if (getNomecomuneFr() != null) {
            _hashCode += getNomecomuneFr().hashCode();
        }
        if (getProtezioneCom() != null) {
            _hashCode += getProtezioneCom().hashCode();
        }
        if (getProtezioneReg() != null) {
            _hashCode += getProtezioneReg().hashCode();
        }
        if (getRegno() != null) {
            _hashCode += getRegno().hashCode();
        }
        if (getTipoIsolamento() != null) {
            _hashCode += getTipoIsolamento().hashCode();
        }
        if (getTipologieSpecie() != null) {
            _hashCode += getTipologieSpecie().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Specie.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "specie"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allegatoIi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "allegatoIi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allegatoIv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "allegatoIv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alloctona");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alloctona"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contingenteNum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contingenteNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fenologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fenologia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSpecie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idSpecie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minacciaGlo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "minacciaGlo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minacciaNaz");
        elemField.setXmlName(new javax.xml.namespace.QName("", "minacciaNaz"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomecomune");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomecomune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomecomuneFr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomecomuneFr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protezioneCom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "protezioneCom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protezioneReg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "protezioneReg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "regno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoIsolamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoIsolamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipoIsolamento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologieSpecie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologieSpecie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieSpecie"));
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
