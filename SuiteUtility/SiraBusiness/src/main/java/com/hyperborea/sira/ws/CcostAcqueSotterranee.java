/**
 * CcostAcqueSotterranee.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostAcqueSotterranee  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CcostAcquifero ccostAcquifero;

    private com.hyperborea.sira.ws.CcostComplessoAcquifero ccostComplessoacquifero;

    private com.hyperborea.sira.ws.CcostCorpoIdrSott ccostCorpoIdrSott;

    private java.lang.String descrizioneClima;

    private java.lang.String descrizioneFaun;

    private java.lang.String descrizioneGeo;

    private java.lang.String descrizioneNatura;

    private java.lang.String descrizioneVeg;

    private java.lang.Integer idCcost;

    private java.lang.Float latMax;

    private java.lang.Float latMin;

    private com.hyperborea.sira.ws.LitologieAcquifero[] litologieAcquiferos;

    private java.lang.Float longMax;

    private java.lang.Float longMin;

    private java.lang.Float quotaMax;

    private java.lang.String stChimico;

    private java.lang.String stQuantitativo;

    private java.lang.Float supBacinoAff;

    private java.lang.Float superficie;

    private java.lang.String tipologia;

    private com.hyperborea.sira.ws.UnitaIdrogeologica[] unitaIdrogeologicas;

    private com.hyperborea.sira.ws.VocClasseChimica vocClasseChimica;

    private com.hyperborea.sira.ws.VocClasseQuantitativo vocClasseQuantitativo;

    private com.hyperborea.sira.ws.VocClassifLitologia vocClassifLitologia;

    private com.hyperborea.sira.ws.VocClassificazioneEta vocClassificazioneEta;

    private com.hyperborea.sira.ws.VocGradoQualita vocGradoSfruttamento;

    private com.hyperborea.sira.ws.VocGradoQualita vocPermeabilita;

    private com.hyperborea.sira.ws.VocGradoQualita vocPotenzialita;

    private com.hyperborea.sira.ws.VocGradoQualita vocProduttivita;

    private com.hyperborea.sira.ws.VocTipologiaAcqueSott vocTipologiaAcqueSott;

    public CcostAcqueSotterranee() {
    }

    public CcostAcqueSotterranee(
           com.hyperborea.sira.ws.CcostAcquifero ccostAcquifero,
           com.hyperborea.sira.ws.CcostComplessoAcquifero ccostComplessoacquifero,
           com.hyperborea.sira.ws.CcostCorpoIdrSott ccostCorpoIdrSott,
           java.lang.String descrizioneClima,
           java.lang.String descrizioneFaun,
           java.lang.String descrizioneGeo,
           java.lang.String descrizioneNatura,
           java.lang.String descrizioneVeg,
           java.lang.Integer idCcost,
           java.lang.Float latMax,
           java.lang.Float latMin,
           com.hyperborea.sira.ws.LitologieAcquifero[] litologieAcquiferos,
           java.lang.Float longMax,
           java.lang.Float longMin,
           java.lang.Float quotaMax,
           java.lang.String stChimico,
           java.lang.String stQuantitativo,
           java.lang.Float supBacinoAff,
           java.lang.Float superficie,
           java.lang.String tipologia,
           com.hyperborea.sira.ws.UnitaIdrogeologica[] unitaIdrogeologicas,
           com.hyperborea.sira.ws.VocClasseChimica vocClasseChimica,
           com.hyperborea.sira.ws.VocClasseQuantitativo vocClasseQuantitativo,
           com.hyperborea.sira.ws.VocClassifLitologia vocClassifLitologia,
           com.hyperborea.sira.ws.VocClassificazioneEta vocClassificazioneEta,
           com.hyperborea.sira.ws.VocGradoQualita vocGradoSfruttamento,
           com.hyperborea.sira.ws.VocGradoQualita vocPermeabilita,
           com.hyperborea.sira.ws.VocGradoQualita vocPotenzialita,
           com.hyperborea.sira.ws.VocGradoQualita vocProduttivita,
           com.hyperborea.sira.ws.VocTipologiaAcqueSott vocTipologiaAcqueSott) {
           this.ccostAcquifero = ccostAcquifero;
           this.ccostComplessoacquifero = ccostComplessoacquifero;
           this.ccostCorpoIdrSott = ccostCorpoIdrSott;
           this.descrizioneClima = descrizioneClima;
           this.descrizioneFaun = descrizioneFaun;
           this.descrizioneGeo = descrizioneGeo;
           this.descrizioneNatura = descrizioneNatura;
           this.descrizioneVeg = descrizioneVeg;
           this.idCcost = idCcost;
           this.latMax = latMax;
           this.latMin = latMin;
           this.litologieAcquiferos = litologieAcquiferos;
           this.longMax = longMax;
           this.longMin = longMin;
           this.quotaMax = quotaMax;
           this.stChimico = stChimico;
           this.stQuantitativo = stQuantitativo;
           this.supBacinoAff = supBacinoAff;
           this.superficie = superficie;
           this.tipologia = tipologia;
           this.unitaIdrogeologicas = unitaIdrogeologicas;
           this.vocClasseChimica = vocClasseChimica;
           this.vocClasseQuantitativo = vocClasseQuantitativo;
           this.vocClassifLitologia = vocClassifLitologia;
           this.vocClassificazioneEta = vocClassificazioneEta;
           this.vocGradoSfruttamento = vocGradoSfruttamento;
           this.vocPermeabilita = vocPermeabilita;
           this.vocPotenzialita = vocPotenzialita;
           this.vocProduttivita = vocProduttivita;
           this.vocTipologiaAcqueSott = vocTipologiaAcqueSott;
    }


    /**
     * Gets the ccostAcquifero value for this CcostAcqueSotterranee.
     * 
     * @return ccostAcquifero
     */
    public com.hyperborea.sira.ws.CcostAcquifero getCcostAcquifero() {
        return ccostAcquifero;
    }


    /**
     * Sets the ccostAcquifero value for this CcostAcqueSotterranee.
     * 
     * @param ccostAcquifero
     */
    public void setCcostAcquifero(com.hyperborea.sira.ws.CcostAcquifero ccostAcquifero) {
        this.ccostAcquifero = ccostAcquifero;
    }


    /**
     * Gets the ccostComplessoacquifero value for this CcostAcqueSotterranee.
     * 
     * @return ccostComplessoacquifero
     */
    public com.hyperborea.sira.ws.CcostComplessoAcquifero getCcostComplessoacquifero() {
        return ccostComplessoacquifero;
    }


    /**
     * Sets the ccostComplessoacquifero value for this CcostAcqueSotterranee.
     * 
     * @param ccostComplessoacquifero
     */
    public void setCcostComplessoacquifero(com.hyperborea.sira.ws.CcostComplessoAcquifero ccostComplessoacquifero) {
        this.ccostComplessoacquifero = ccostComplessoacquifero;
    }


    /**
     * Gets the ccostCorpoIdrSott value for this CcostAcqueSotterranee.
     * 
     * @return ccostCorpoIdrSott
     */
    public com.hyperborea.sira.ws.CcostCorpoIdrSott getCcostCorpoIdrSott() {
        return ccostCorpoIdrSott;
    }


    /**
     * Sets the ccostCorpoIdrSott value for this CcostAcqueSotterranee.
     * 
     * @param ccostCorpoIdrSott
     */
    public void setCcostCorpoIdrSott(com.hyperborea.sira.ws.CcostCorpoIdrSott ccostCorpoIdrSott) {
        this.ccostCorpoIdrSott = ccostCorpoIdrSott;
    }


    /**
     * Gets the descrizioneClima value for this CcostAcqueSotterranee.
     * 
     * @return descrizioneClima
     */
    public java.lang.String getDescrizioneClima() {
        return descrizioneClima;
    }


    /**
     * Sets the descrizioneClima value for this CcostAcqueSotterranee.
     * 
     * @param descrizioneClima
     */
    public void setDescrizioneClima(java.lang.String descrizioneClima) {
        this.descrizioneClima = descrizioneClima;
    }


    /**
     * Gets the descrizioneFaun value for this CcostAcqueSotterranee.
     * 
     * @return descrizioneFaun
     */
    public java.lang.String getDescrizioneFaun() {
        return descrizioneFaun;
    }


    /**
     * Sets the descrizioneFaun value for this CcostAcqueSotterranee.
     * 
     * @param descrizioneFaun
     */
    public void setDescrizioneFaun(java.lang.String descrizioneFaun) {
        this.descrizioneFaun = descrizioneFaun;
    }


    /**
     * Gets the descrizioneGeo value for this CcostAcqueSotterranee.
     * 
     * @return descrizioneGeo
     */
    public java.lang.String getDescrizioneGeo() {
        return descrizioneGeo;
    }


    /**
     * Sets the descrizioneGeo value for this CcostAcqueSotterranee.
     * 
     * @param descrizioneGeo
     */
    public void setDescrizioneGeo(java.lang.String descrizioneGeo) {
        this.descrizioneGeo = descrizioneGeo;
    }


    /**
     * Gets the descrizioneNatura value for this CcostAcqueSotterranee.
     * 
     * @return descrizioneNatura
     */
    public java.lang.String getDescrizioneNatura() {
        return descrizioneNatura;
    }


    /**
     * Sets the descrizioneNatura value for this CcostAcqueSotterranee.
     * 
     * @param descrizioneNatura
     */
    public void setDescrizioneNatura(java.lang.String descrizioneNatura) {
        this.descrizioneNatura = descrizioneNatura;
    }


    /**
     * Gets the descrizioneVeg value for this CcostAcqueSotterranee.
     * 
     * @return descrizioneVeg
     */
    public java.lang.String getDescrizioneVeg() {
        return descrizioneVeg;
    }


    /**
     * Sets the descrizioneVeg value for this CcostAcqueSotterranee.
     * 
     * @param descrizioneVeg
     */
    public void setDescrizioneVeg(java.lang.String descrizioneVeg) {
        this.descrizioneVeg = descrizioneVeg;
    }


    /**
     * Gets the idCcost value for this CcostAcqueSotterranee.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostAcqueSotterranee.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the latMax value for this CcostAcqueSotterranee.
     * 
     * @return latMax
     */
    public java.lang.Float getLatMax() {
        return latMax;
    }


    /**
     * Sets the latMax value for this CcostAcqueSotterranee.
     * 
     * @param latMax
     */
    public void setLatMax(java.lang.Float latMax) {
        this.latMax = latMax;
    }


    /**
     * Gets the latMin value for this CcostAcqueSotterranee.
     * 
     * @return latMin
     */
    public java.lang.Float getLatMin() {
        return latMin;
    }


    /**
     * Sets the latMin value for this CcostAcqueSotterranee.
     * 
     * @param latMin
     */
    public void setLatMin(java.lang.Float latMin) {
        this.latMin = latMin;
    }


    /**
     * Gets the litologieAcquiferos value for this CcostAcqueSotterranee.
     * 
     * @return litologieAcquiferos
     */
    public com.hyperborea.sira.ws.LitologieAcquifero[] getLitologieAcquiferos() {
        return litologieAcquiferos;
    }


    /**
     * Sets the litologieAcquiferos value for this CcostAcqueSotterranee.
     * 
     * @param litologieAcquiferos
     */
    public void setLitologieAcquiferos(com.hyperborea.sira.ws.LitologieAcquifero[] litologieAcquiferos) {
        this.litologieAcquiferos = litologieAcquiferos;
    }

    public com.hyperborea.sira.ws.LitologieAcquifero getLitologieAcquiferos(int i) {
        return this.litologieAcquiferos[i];
    }

    public void setLitologieAcquiferos(int i, com.hyperborea.sira.ws.LitologieAcquifero _value) {
        this.litologieAcquiferos[i] = _value;
    }


    /**
     * Gets the longMax value for this CcostAcqueSotterranee.
     * 
     * @return longMax
     */
    public java.lang.Float getLongMax() {
        return longMax;
    }


    /**
     * Sets the longMax value for this CcostAcqueSotterranee.
     * 
     * @param longMax
     */
    public void setLongMax(java.lang.Float longMax) {
        this.longMax = longMax;
    }


    /**
     * Gets the longMin value for this CcostAcqueSotterranee.
     * 
     * @return longMin
     */
    public java.lang.Float getLongMin() {
        return longMin;
    }


    /**
     * Sets the longMin value for this CcostAcqueSotterranee.
     * 
     * @param longMin
     */
    public void setLongMin(java.lang.Float longMin) {
        this.longMin = longMin;
    }


    /**
     * Gets the quotaMax value for this CcostAcqueSotterranee.
     * 
     * @return quotaMax
     */
    public java.lang.Float getQuotaMax() {
        return quotaMax;
    }


    /**
     * Sets the quotaMax value for this CcostAcqueSotterranee.
     * 
     * @param quotaMax
     */
    public void setQuotaMax(java.lang.Float quotaMax) {
        this.quotaMax = quotaMax;
    }


    /**
     * Gets the stChimico value for this CcostAcqueSotterranee.
     * 
     * @return stChimico
     */
    public java.lang.String getStChimico() {
        return stChimico;
    }


    /**
     * Sets the stChimico value for this CcostAcqueSotterranee.
     * 
     * @param stChimico
     */
    public void setStChimico(java.lang.String stChimico) {
        this.stChimico = stChimico;
    }


    /**
     * Gets the stQuantitativo value for this CcostAcqueSotterranee.
     * 
     * @return stQuantitativo
     */
    public java.lang.String getStQuantitativo() {
        return stQuantitativo;
    }


    /**
     * Sets the stQuantitativo value for this CcostAcqueSotterranee.
     * 
     * @param stQuantitativo
     */
    public void setStQuantitativo(java.lang.String stQuantitativo) {
        this.stQuantitativo = stQuantitativo;
    }


    /**
     * Gets the supBacinoAff value for this CcostAcqueSotterranee.
     * 
     * @return supBacinoAff
     */
    public java.lang.Float getSupBacinoAff() {
        return supBacinoAff;
    }


    /**
     * Sets the supBacinoAff value for this CcostAcqueSotterranee.
     * 
     * @param supBacinoAff
     */
    public void setSupBacinoAff(java.lang.Float supBacinoAff) {
        this.supBacinoAff = supBacinoAff;
    }


    /**
     * Gets the superficie value for this CcostAcqueSotterranee.
     * 
     * @return superficie
     */
    public java.lang.Float getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this CcostAcqueSotterranee.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Float superficie) {
        this.superficie = superficie;
    }


    /**
     * Gets the tipologia value for this CcostAcqueSotterranee.
     * 
     * @return tipologia
     */
    public java.lang.String getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this CcostAcqueSotterranee.
     * 
     * @param tipologia
     */
    public void setTipologia(java.lang.String tipologia) {
        this.tipologia = tipologia;
    }


    /**
     * Gets the unitaIdrogeologicas value for this CcostAcqueSotterranee.
     * 
     * @return unitaIdrogeologicas
     */
    public com.hyperborea.sira.ws.UnitaIdrogeologica[] getUnitaIdrogeologicas() {
        return unitaIdrogeologicas;
    }


    /**
     * Sets the unitaIdrogeologicas value for this CcostAcqueSotterranee.
     * 
     * @param unitaIdrogeologicas
     */
    public void setUnitaIdrogeologicas(com.hyperborea.sira.ws.UnitaIdrogeologica[] unitaIdrogeologicas) {
        this.unitaIdrogeologicas = unitaIdrogeologicas;
    }

    public com.hyperborea.sira.ws.UnitaIdrogeologica getUnitaIdrogeologicas(int i) {
        return this.unitaIdrogeologicas[i];
    }

    public void setUnitaIdrogeologicas(int i, com.hyperborea.sira.ws.UnitaIdrogeologica _value) {
        this.unitaIdrogeologicas[i] = _value;
    }


    /**
     * Gets the vocClasseChimica value for this CcostAcqueSotterranee.
     * 
     * @return vocClasseChimica
     */
    public com.hyperborea.sira.ws.VocClasseChimica getVocClasseChimica() {
        return vocClasseChimica;
    }


    /**
     * Sets the vocClasseChimica value for this CcostAcqueSotterranee.
     * 
     * @param vocClasseChimica
     */
    public void setVocClasseChimica(com.hyperborea.sira.ws.VocClasseChimica vocClasseChimica) {
        this.vocClasseChimica = vocClasseChimica;
    }


    /**
     * Gets the vocClasseQuantitativo value for this CcostAcqueSotterranee.
     * 
     * @return vocClasseQuantitativo
     */
    public com.hyperborea.sira.ws.VocClasseQuantitativo getVocClasseQuantitativo() {
        return vocClasseQuantitativo;
    }


    /**
     * Sets the vocClasseQuantitativo value for this CcostAcqueSotterranee.
     * 
     * @param vocClasseQuantitativo
     */
    public void setVocClasseQuantitativo(com.hyperborea.sira.ws.VocClasseQuantitativo vocClasseQuantitativo) {
        this.vocClasseQuantitativo = vocClasseQuantitativo;
    }


    /**
     * Gets the vocClassifLitologia value for this CcostAcqueSotterranee.
     * 
     * @return vocClassifLitologia
     */
    public com.hyperborea.sira.ws.VocClassifLitologia getVocClassifLitologia() {
        return vocClassifLitologia;
    }


    /**
     * Sets the vocClassifLitologia value for this CcostAcqueSotterranee.
     * 
     * @param vocClassifLitologia
     */
    public void setVocClassifLitologia(com.hyperborea.sira.ws.VocClassifLitologia vocClassifLitologia) {
        this.vocClassifLitologia = vocClassifLitologia;
    }


    /**
     * Gets the vocClassificazioneEta value for this CcostAcqueSotterranee.
     * 
     * @return vocClassificazioneEta
     */
    public com.hyperborea.sira.ws.VocClassificazioneEta getVocClassificazioneEta() {
        return vocClassificazioneEta;
    }


    /**
     * Sets the vocClassificazioneEta value for this CcostAcqueSotterranee.
     * 
     * @param vocClassificazioneEta
     */
    public void setVocClassificazioneEta(com.hyperborea.sira.ws.VocClassificazioneEta vocClassificazioneEta) {
        this.vocClassificazioneEta = vocClassificazioneEta;
    }


    /**
     * Gets the vocGradoSfruttamento value for this CcostAcqueSotterranee.
     * 
     * @return vocGradoSfruttamento
     */
    public com.hyperborea.sira.ws.VocGradoQualita getVocGradoSfruttamento() {
        return vocGradoSfruttamento;
    }


    /**
     * Sets the vocGradoSfruttamento value for this CcostAcqueSotterranee.
     * 
     * @param vocGradoSfruttamento
     */
    public void setVocGradoSfruttamento(com.hyperborea.sira.ws.VocGradoQualita vocGradoSfruttamento) {
        this.vocGradoSfruttamento = vocGradoSfruttamento;
    }


    /**
     * Gets the vocPermeabilita value for this CcostAcqueSotterranee.
     * 
     * @return vocPermeabilita
     */
    public com.hyperborea.sira.ws.VocGradoQualita getVocPermeabilita() {
        return vocPermeabilita;
    }


    /**
     * Sets the vocPermeabilita value for this CcostAcqueSotterranee.
     * 
     * @param vocPermeabilita
     */
    public void setVocPermeabilita(com.hyperborea.sira.ws.VocGradoQualita vocPermeabilita) {
        this.vocPermeabilita = vocPermeabilita;
    }


    /**
     * Gets the vocPotenzialita value for this CcostAcqueSotterranee.
     * 
     * @return vocPotenzialita
     */
    public com.hyperborea.sira.ws.VocGradoQualita getVocPotenzialita() {
        return vocPotenzialita;
    }


    /**
     * Sets the vocPotenzialita value for this CcostAcqueSotterranee.
     * 
     * @param vocPotenzialita
     */
    public void setVocPotenzialita(com.hyperborea.sira.ws.VocGradoQualita vocPotenzialita) {
        this.vocPotenzialita = vocPotenzialita;
    }


    /**
     * Gets the vocProduttivita value for this CcostAcqueSotterranee.
     * 
     * @return vocProduttivita
     */
    public com.hyperborea.sira.ws.VocGradoQualita getVocProduttivita() {
        return vocProduttivita;
    }


    /**
     * Sets the vocProduttivita value for this CcostAcqueSotterranee.
     * 
     * @param vocProduttivita
     */
    public void setVocProduttivita(com.hyperborea.sira.ws.VocGradoQualita vocProduttivita) {
        this.vocProduttivita = vocProduttivita;
    }


    /**
     * Gets the vocTipologiaAcqueSott value for this CcostAcqueSotterranee.
     * 
     * @return vocTipologiaAcqueSott
     */
    public com.hyperborea.sira.ws.VocTipologiaAcqueSott getVocTipologiaAcqueSott() {
        return vocTipologiaAcqueSott;
    }


    /**
     * Sets the vocTipologiaAcqueSott value for this CcostAcqueSotterranee.
     * 
     * @param vocTipologiaAcqueSott
     */
    public void setVocTipologiaAcqueSott(com.hyperborea.sira.ws.VocTipologiaAcqueSott vocTipologiaAcqueSott) {
        this.vocTipologiaAcqueSott = vocTipologiaAcqueSott;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostAcqueSotterranee)) return false;
        CcostAcqueSotterranee other = (CcostAcqueSotterranee) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ccostAcquifero==null && other.getCcostAcquifero()==null) || 
             (this.ccostAcquifero!=null &&
              this.ccostAcquifero.equals(other.getCcostAcquifero()))) &&
            ((this.ccostComplessoacquifero==null && other.getCcostComplessoacquifero()==null) || 
             (this.ccostComplessoacquifero!=null &&
              this.ccostComplessoacquifero.equals(other.getCcostComplessoacquifero()))) &&
            ((this.ccostCorpoIdrSott==null && other.getCcostCorpoIdrSott()==null) || 
             (this.ccostCorpoIdrSott!=null &&
              this.ccostCorpoIdrSott.equals(other.getCcostCorpoIdrSott()))) &&
            ((this.descrizioneClima==null && other.getDescrizioneClima()==null) || 
             (this.descrizioneClima!=null &&
              this.descrizioneClima.equals(other.getDescrizioneClima()))) &&
            ((this.descrizioneFaun==null && other.getDescrizioneFaun()==null) || 
             (this.descrizioneFaun!=null &&
              this.descrizioneFaun.equals(other.getDescrizioneFaun()))) &&
            ((this.descrizioneGeo==null && other.getDescrizioneGeo()==null) || 
             (this.descrizioneGeo!=null &&
              this.descrizioneGeo.equals(other.getDescrizioneGeo()))) &&
            ((this.descrizioneNatura==null && other.getDescrizioneNatura()==null) || 
             (this.descrizioneNatura!=null &&
              this.descrizioneNatura.equals(other.getDescrizioneNatura()))) &&
            ((this.descrizioneVeg==null && other.getDescrizioneVeg()==null) || 
             (this.descrizioneVeg!=null &&
              this.descrizioneVeg.equals(other.getDescrizioneVeg()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.latMax==null && other.getLatMax()==null) || 
             (this.latMax!=null &&
              this.latMax.equals(other.getLatMax()))) &&
            ((this.latMin==null && other.getLatMin()==null) || 
             (this.latMin!=null &&
              this.latMin.equals(other.getLatMin()))) &&
            ((this.litologieAcquiferos==null && other.getLitologieAcquiferos()==null) || 
             (this.litologieAcquiferos!=null &&
              java.util.Arrays.equals(this.litologieAcquiferos, other.getLitologieAcquiferos()))) &&
            ((this.longMax==null && other.getLongMax()==null) || 
             (this.longMax!=null &&
              this.longMax.equals(other.getLongMax()))) &&
            ((this.longMin==null && other.getLongMin()==null) || 
             (this.longMin!=null &&
              this.longMin.equals(other.getLongMin()))) &&
            ((this.quotaMax==null && other.getQuotaMax()==null) || 
             (this.quotaMax!=null &&
              this.quotaMax.equals(other.getQuotaMax()))) &&
            ((this.stChimico==null && other.getStChimico()==null) || 
             (this.stChimico!=null &&
              this.stChimico.equals(other.getStChimico()))) &&
            ((this.stQuantitativo==null && other.getStQuantitativo()==null) || 
             (this.stQuantitativo!=null &&
              this.stQuantitativo.equals(other.getStQuantitativo()))) &&
            ((this.supBacinoAff==null && other.getSupBacinoAff()==null) || 
             (this.supBacinoAff!=null &&
              this.supBacinoAff.equals(other.getSupBacinoAff()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie()))) &&
            ((this.tipologia==null && other.getTipologia()==null) || 
             (this.tipologia!=null &&
              this.tipologia.equals(other.getTipologia()))) &&
            ((this.unitaIdrogeologicas==null && other.getUnitaIdrogeologicas()==null) || 
             (this.unitaIdrogeologicas!=null &&
              java.util.Arrays.equals(this.unitaIdrogeologicas, other.getUnitaIdrogeologicas()))) &&
            ((this.vocClasseChimica==null && other.getVocClasseChimica()==null) || 
             (this.vocClasseChimica!=null &&
              this.vocClasseChimica.equals(other.getVocClasseChimica()))) &&
            ((this.vocClasseQuantitativo==null && other.getVocClasseQuantitativo()==null) || 
             (this.vocClasseQuantitativo!=null &&
              this.vocClasseQuantitativo.equals(other.getVocClasseQuantitativo()))) &&
            ((this.vocClassifLitologia==null && other.getVocClassifLitologia()==null) || 
             (this.vocClassifLitologia!=null &&
              this.vocClassifLitologia.equals(other.getVocClassifLitologia()))) &&
            ((this.vocClassificazioneEta==null && other.getVocClassificazioneEta()==null) || 
             (this.vocClassificazioneEta!=null &&
              this.vocClassificazioneEta.equals(other.getVocClassificazioneEta()))) &&
            ((this.vocGradoSfruttamento==null && other.getVocGradoSfruttamento()==null) || 
             (this.vocGradoSfruttamento!=null &&
              this.vocGradoSfruttamento.equals(other.getVocGradoSfruttamento()))) &&
            ((this.vocPermeabilita==null && other.getVocPermeabilita()==null) || 
             (this.vocPermeabilita!=null &&
              this.vocPermeabilita.equals(other.getVocPermeabilita()))) &&
            ((this.vocPotenzialita==null && other.getVocPotenzialita()==null) || 
             (this.vocPotenzialita!=null &&
              this.vocPotenzialita.equals(other.getVocPotenzialita()))) &&
            ((this.vocProduttivita==null && other.getVocProduttivita()==null) || 
             (this.vocProduttivita!=null &&
              this.vocProduttivita.equals(other.getVocProduttivita()))) &&
            ((this.vocTipologiaAcqueSott==null && other.getVocTipologiaAcqueSott()==null) || 
             (this.vocTipologiaAcqueSott!=null &&
              this.vocTipologiaAcqueSott.equals(other.getVocTipologiaAcqueSott())));
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
        if (getCcostAcquifero() != null) {
            _hashCode += getCcostAcquifero().hashCode();
        }
        if (getCcostComplessoacquifero() != null) {
            _hashCode += getCcostComplessoacquifero().hashCode();
        }
        if (getCcostCorpoIdrSott() != null) {
            _hashCode += getCcostCorpoIdrSott().hashCode();
        }
        if (getDescrizioneClima() != null) {
            _hashCode += getDescrizioneClima().hashCode();
        }
        if (getDescrizioneFaun() != null) {
            _hashCode += getDescrizioneFaun().hashCode();
        }
        if (getDescrizioneGeo() != null) {
            _hashCode += getDescrizioneGeo().hashCode();
        }
        if (getDescrizioneNatura() != null) {
            _hashCode += getDescrizioneNatura().hashCode();
        }
        if (getDescrizioneVeg() != null) {
            _hashCode += getDescrizioneVeg().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getLatMax() != null) {
            _hashCode += getLatMax().hashCode();
        }
        if (getLatMin() != null) {
            _hashCode += getLatMin().hashCode();
        }
        if (getLitologieAcquiferos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLitologieAcquiferos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLitologieAcquiferos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getLongMax() != null) {
            _hashCode += getLongMax().hashCode();
        }
        if (getLongMin() != null) {
            _hashCode += getLongMin().hashCode();
        }
        if (getQuotaMax() != null) {
            _hashCode += getQuotaMax().hashCode();
        }
        if (getStChimico() != null) {
            _hashCode += getStChimico().hashCode();
        }
        if (getStQuantitativo() != null) {
            _hashCode += getStQuantitativo().hashCode();
        }
        if (getSupBacinoAff() != null) {
            _hashCode += getSupBacinoAff().hashCode();
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        if (getUnitaIdrogeologicas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUnitaIdrogeologicas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUnitaIdrogeologicas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVocClasseChimica() != null) {
            _hashCode += getVocClasseChimica().hashCode();
        }
        if (getVocClasseQuantitativo() != null) {
            _hashCode += getVocClasseQuantitativo().hashCode();
        }
        if (getVocClassifLitologia() != null) {
            _hashCode += getVocClassifLitologia().hashCode();
        }
        if (getVocClassificazioneEta() != null) {
            _hashCode += getVocClassificazioneEta().hashCode();
        }
        if (getVocGradoSfruttamento() != null) {
            _hashCode += getVocGradoSfruttamento().hashCode();
        }
        if (getVocPermeabilita() != null) {
            _hashCode += getVocPermeabilita().hashCode();
        }
        if (getVocPotenzialita() != null) {
            _hashCode += getVocPotenzialita().hashCode();
        }
        if (getVocProduttivita() != null) {
            _hashCode += getVocProduttivita().hashCode();
        }
        if (getVocTipologiaAcqueSott() != null) {
            _hashCode += getVocTipologiaAcqueSott().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostAcqueSotterranee.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAcqueSotterranee"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostAcquifero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostAcquifero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAcquifero"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostComplessoacquifero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostComplessoacquifero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostComplessoAcquifero"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostCorpoIdrSott");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostCorpoIdrSott"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCorpoIdrSott"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneClima");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneClima"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneFaun");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneFaun"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneGeo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneGeo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneNatura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneNatura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneVeg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneVeg"));
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
        elemField.setFieldName("latMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "latMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("latMin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "latMin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("litologieAcquiferos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "litologieAcquiferos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "litologieAcquifero"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("longMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "longMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("longMin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "longMin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quotaMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quotaMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stChimico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stChimico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stQuantitativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stQuantitativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supBacinoAff");
        elemField.setXmlName(new javax.xml.namespace.QName("", "supBacinoAff"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
        elemField.setFieldName("unitaIdrogeologicas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unitaIdrogeologicas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "unitaIdrogeologica"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocClasseChimica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocClasseChimica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClasseChimica"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocClasseQuantitativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocClasseQuantitativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClasseQuantitativo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocClassifLitologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocClassifLitologia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClassifLitologia"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocClassificazioneEta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocClassificazioneEta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClassificazioneEta"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocGradoSfruttamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocGradoSfruttamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocGradoQualita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocPermeabilita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocPermeabilita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocGradoQualita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocPotenzialita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocPotenzialita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocGradoQualita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocProduttivita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocProduttivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocGradoQualita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaAcqueSott");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaAcqueSott"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaAcqueSott"));
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
