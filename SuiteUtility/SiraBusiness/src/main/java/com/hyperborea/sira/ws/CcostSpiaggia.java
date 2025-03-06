/**
 * CcostSpiaggia.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostSpiaggia  implements java.io.Serializable {
    private java.lang.Float altezzaDune;

    private java.lang.Float bilancioSedimentario;

    private java.lang.String carryngCapacity;

    private java.lang.Integer idCcost;

    private java.lang.Float larghezzaMassima;

    private java.lang.String litologia;

    private java.lang.Float lunghezza;

    private com.hyperborea.sira.ws.MaterialeCostiero materialeCostiero;

    private java.lang.Integer numeroBaciniAfferenti;

    private java.lang.Float pendenzaMedCostaSom;

    private java.lang.Integer presenzaDune;

    private java.lang.Integer profiliSpiaggia;

    private java.lang.Float profondMedFondale;

    private java.lang.Float profonditaChiusura;

    private java.lang.Integer sezioniSpiaggia;

    private java.lang.Float superficie;

    private java.lang.Float superficieBaciniAfferenti;

    private java.lang.Float superficieDune;

    private java.lang.String tipologia;

    private com.hyperborea.sira.ws.VocGradoAntrSpiaggia vocGradoAntrSpiaggia;

    private com.hyperborea.sira.ws.VocTendEvolSpiaggia vocTendEvolSpiaggia;

    private com.hyperborea.sira.ws.VocTessituraSpiaggia vocTessituraSpiaggia;

    private com.hyperborea.sira.ws.VocTipoDune vocTipoDune;

    private com.hyperborea.sira.ws.VocTipoVegetazione vocTipoVegetazione;

    private com.hyperborea.sira.ws.VocVarchiSpiaggia vocVarchiSpiaggia;

    private com.hyperborea.sira.ws.RipascimentoAreaCostiera[] ripascimentoAreaCostieras;

    public CcostSpiaggia() {
    }

    public CcostSpiaggia(
           java.lang.Float altezzaDune,
           java.lang.Float bilancioSedimentario,
           java.lang.String carryngCapacity,
           java.lang.Integer idCcost,
           java.lang.Float larghezzaMassima,
           java.lang.String litologia,
           java.lang.Float lunghezza,
           com.hyperborea.sira.ws.MaterialeCostiero materialeCostiero,
           java.lang.Integer numeroBaciniAfferenti,
           java.lang.Float pendenzaMedCostaSom,
           java.lang.Integer presenzaDune,
           java.lang.Integer profiliSpiaggia,
           java.lang.Float profondMedFondale,
           java.lang.Float profonditaChiusura,
           java.lang.Integer sezioniSpiaggia,
           java.lang.Float superficie,
           java.lang.Float superficieBaciniAfferenti,
           java.lang.Float superficieDune,
           java.lang.String tipologia,
           com.hyperborea.sira.ws.VocGradoAntrSpiaggia vocGradoAntrSpiaggia,
           com.hyperborea.sira.ws.VocTendEvolSpiaggia vocTendEvolSpiaggia,
           com.hyperborea.sira.ws.VocTessituraSpiaggia vocTessituraSpiaggia,
           com.hyperborea.sira.ws.VocTipoDune vocTipoDune,
           com.hyperborea.sira.ws.VocTipoVegetazione vocTipoVegetazione,
           com.hyperborea.sira.ws.VocVarchiSpiaggia vocVarchiSpiaggia,
           com.hyperborea.sira.ws.RipascimentoAreaCostiera[] ripascimentoAreaCostieras) {
           this.altezzaDune = altezzaDune;
           this.bilancioSedimentario = bilancioSedimentario;
           this.carryngCapacity = carryngCapacity;
           this.idCcost = idCcost;
           this.larghezzaMassima = larghezzaMassima;
           this.litologia = litologia;
           this.lunghezza = lunghezza;
           this.materialeCostiero = materialeCostiero;
           this.numeroBaciniAfferenti = numeroBaciniAfferenti;
           this.pendenzaMedCostaSom = pendenzaMedCostaSom;
           this.presenzaDune = presenzaDune;
           this.profiliSpiaggia = profiliSpiaggia;
           this.profondMedFondale = profondMedFondale;
           this.profonditaChiusura = profonditaChiusura;
           this.sezioniSpiaggia = sezioniSpiaggia;
           this.superficie = superficie;
           this.superficieBaciniAfferenti = superficieBaciniAfferenti;
           this.superficieDune = superficieDune;
           this.tipologia = tipologia;
           this.vocGradoAntrSpiaggia = vocGradoAntrSpiaggia;
           this.vocTendEvolSpiaggia = vocTendEvolSpiaggia;
           this.vocTessituraSpiaggia = vocTessituraSpiaggia;
           this.vocTipoDune = vocTipoDune;
           this.vocTipoVegetazione = vocTipoVegetazione;
           this.vocVarchiSpiaggia = vocVarchiSpiaggia;
           this.ripascimentoAreaCostieras = ripascimentoAreaCostieras;
    }


    /**
     * Gets the altezzaDune value for this CcostSpiaggia.
     * 
     * @return altezzaDune
     */
    public java.lang.Float getAltezzaDune() {
        return altezzaDune;
    }


    /**
     * Sets the altezzaDune value for this CcostSpiaggia.
     * 
     * @param altezzaDune
     */
    public void setAltezzaDune(java.lang.Float altezzaDune) {
        this.altezzaDune = altezzaDune;
    }


    /**
     * Gets the bilancioSedimentario value for this CcostSpiaggia.
     * 
     * @return bilancioSedimentario
     */
    public java.lang.Float getBilancioSedimentario() {
        return bilancioSedimentario;
    }


    /**
     * Sets the bilancioSedimentario value for this CcostSpiaggia.
     * 
     * @param bilancioSedimentario
     */
    public void setBilancioSedimentario(java.lang.Float bilancioSedimentario) {
        this.bilancioSedimentario = bilancioSedimentario;
    }


    /**
     * Gets the carryngCapacity value for this CcostSpiaggia.
     * 
     * @return carryngCapacity
     */
    public java.lang.String getCarryngCapacity() {
        return carryngCapacity;
    }


    /**
     * Sets the carryngCapacity value for this CcostSpiaggia.
     * 
     * @param carryngCapacity
     */
    public void setCarryngCapacity(java.lang.String carryngCapacity) {
        this.carryngCapacity = carryngCapacity;
    }


    /**
     * Gets the idCcost value for this CcostSpiaggia.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostSpiaggia.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the larghezzaMassima value for this CcostSpiaggia.
     * 
     * @return larghezzaMassima
     */
    public java.lang.Float getLarghezzaMassima() {
        return larghezzaMassima;
    }


    /**
     * Sets the larghezzaMassima value for this CcostSpiaggia.
     * 
     * @param larghezzaMassima
     */
    public void setLarghezzaMassima(java.lang.Float larghezzaMassima) {
        this.larghezzaMassima = larghezzaMassima;
    }


    /**
     * Gets the litologia value for this CcostSpiaggia.
     * 
     * @return litologia
     */
    public java.lang.String getLitologia() {
        return litologia;
    }


    /**
     * Sets the litologia value for this CcostSpiaggia.
     * 
     * @param litologia
     */
    public void setLitologia(java.lang.String litologia) {
        this.litologia = litologia;
    }


    /**
     * Gets the lunghezza value for this CcostSpiaggia.
     * 
     * @return lunghezza
     */
    public java.lang.Float getLunghezza() {
        return lunghezza;
    }


    /**
     * Sets the lunghezza value for this CcostSpiaggia.
     * 
     * @param lunghezza
     */
    public void setLunghezza(java.lang.Float lunghezza) {
        this.lunghezza = lunghezza;
    }


    /**
     * Gets the materialeCostiero value for this CcostSpiaggia.
     * 
     * @return materialeCostiero
     */
    public com.hyperborea.sira.ws.MaterialeCostiero getMaterialeCostiero() {
        return materialeCostiero;
    }


    /**
     * Sets the materialeCostiero value for this CcostSpiaggia.
     * 
     * @param materialeCostiero
     */
    public void setMaterialeCostiero(com.hyperborea.sira.ws.MaterialeCostiero materialeCostiero) {
        this.materialeCostiero = materialeCostiero;
    }


    /**
     * Gets the numeroBaciniAfferenti value for this CcostSpiaggia.
     * 
     * @return numeroBaciniAfferenti
     */
    public java.lang.Integer getNumeroBaciniAfferenti() {
        return numeroBaciniAfferenti;
    }


    /**
     * Sets the numeroBaciniAfferenti value for this CcostSpiaggia.
     * 
     * @param numeroBaciniAfferenti
     */
    public void setNumeroBaciniAfferenti(java.lang.Integer numeroBaciniAfferenti) {
        this.numeroBaciniAfferenti = numeroBaciniAfferenti;
    }


    /**
     * Gets the pendenzaMedCostaSom value for this CcostSpiaggia.
     * 
     * @return pendenzaMedCostaSom
     */
    public java.lang.Float getPendenzaMedCostaSom() {
        return pendenzaMedCostaSom;
    }


    /**
     * Sets the pendenzaMedCostaSom value for this CcostSpiaggia.
     * 
     * @param pendenzaMedCostaSom
     */
    public void setPendenzaMedCostaSom(java.lang.Float pendenzaMedCostaSom) {
        this.pendenzaMedCostaSom = pendenzaMedCostaSom;
    }


    /**
     * Gets the presenzaDune value for this CcostSpiaggia.
     * 
     * @return presenzaDune
     */
    public java.lang.Integer getPresenzaDune() {
        return presenzaDune;
    }


    /**
     * Sets the presenzaDune value for this CcostSpiaggia.
     * 
     * @param presenzaDune
     */
    public void setPresenzaDune(java.lang.Integer presenzaDune) {
        this.presenzaDune = presenzaDune;
    }


    /**
     * Gets the profiliSpiaggia value for this CcostSpiaggia.
     * 
     * @return profiliSpiaggia
     */
    public java.lang.Integer getProfiliSpiaggia() {
        return profiliSpiaggia;
    }


    /**
     * Sets the profiliSpiaggia value for this CcostSpiaggia.
     * 
     * @param profiliSpiaggia
     */
    public void setProfiliSpiaggia(java.lang.Integer profiliSpiaggia) {
        this.profiliSpiaggia = profiliSpiaggia;
    }


    /**
     * Gets the profondMedFondale value for this CcostSpiaggia.
     * 
     * @return profondMedFondale
     */
    public java.lang.Float getProfondMedFondale() {
        return profondMedFondale;
    }


    /**
     * Sets the profondMedFondale value for this CcostSpiaggia.
     * 
     * @param profondMedFondale
     */
    public void setProfondMedFondale(java.lang.Float profondMedFondale) {
        this.profondMedFondale = profondMedFondale;
    }


    /**
     * Gets the profonditaChiusura value for this CcostSpiaggia.
     * 
     * @return profonditaChiusura
     */
    public java.lang.Float getProfonditaChiusura() {
        return profonditaChiusura;
    }


    /**
     * Sets the profonditaChiusura value for this CcostSpiaggia.
     * 
     * @param profonditaChiusura
     */
    public void setProfonditaChiusura(java.lang.Float profonditaChiusura) {
        this.profonditaChiusura = profonditaChiusura;
    }


    /**
     * Gets the sezioniSpiaggia value for this CcostSpiaggia.
     * 
     * @return sezioniSpiaggia
     */
    public java.lang.Integer getSezioniSpiaggia() {
        return sezioniSpiaggia;
    }


    /**
     * Sets the sezioniSpiaggia value for this CcostSpiaggia.
     * 
     * @param sezioniSpiaggia
     */
    public void setSezioniSpiaggia(java.lang.Integer sezioniSpiaggia) {
        this.sezioniSpiaggia = sezioniSpiaggia;
    }


    /**
     * Gets the superficie value for this CcostSpiaggia.
     * 
     * @return superficie
     */
    public java.lang.Float getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this CcostSpiaggia.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Float superficie) {
        this.superficie = superficie;
    }


    /**
     * Gets the superficieBaciniAfferenti value for this CcostSpiaggia.
     * 
     * @return superficieBaciniAfferenti
     */
    public java.lang.Float getSuperficieBaciniAfferenti() {
        return superficieBaciniAfferenti;
    }


    /**
     * Sets the superficieBaciniAfferenti value for this CcostSpiaggia.
     * 
     * @param superficieBaciniAfferenti
     */
    public void setSuperficieBaciniAfferenti(java.lang.Float superficieBaciniAfferenti) {
        this.superficieBaciniAfferenti = superficieBaciniAfferenti;
    }


    /**
     * Gets the superficieDune value for this CcostSpiaggia.
     * 
     * @return superficieDune
     */
    public java.lang.Float getSuperficieDune() {
        return superficieDune;
    }


    /**
     * Sets the superficieDune value for this CcostSpiaggia.
     * 
     * @param superficieDune
     */
    public void setSuperficieDune(java.lang.Float superficieDune) {
        this.superficieDune = superficieDune;
    }


    /**
     * Gets the tipologia value for this CcostSpiaggia.
     * 
     * @return tipologia
     */
    public java.lang.String getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this CcostSpiaggia.
     * 
     * @param tipologia
     */
    public void setTipologia(java.lang.String tipologia) {
        this.tipologia = tipologia;
    }


    /**
     * Gets the vocGradoAntrSpiaggia value for this CcostSpiaggia.
     * 
     * @return vocGradoAntrSpiaggia
     */
    public com.hyperborea.sira.ws.VocGradoAntrSpiaggia getVocGradoAntrSpiaggia() {
        return vocGradoAntrSpiaggia;
    }


    /**
     * Sets the vocGradoAntrSpiaggia value for this CcostSpiaggia.
     * 
     * @param vocGradoAntrSpiaggia
     */
    public void setVocGradoAntrSpiaggia(com.hyperborea.sira.ws.VocGradoAntrSpiaggia vocGradoAntrSpiaggia) {
        this.vocGradoAntrSpiaggia = vocGradoAntrSpiaggia;
    }


    /**
     * Gets the vocTendEvolSpiaggia value for this CcostSpiaggia.
     * 
     * @return vocTendEvolSpiaggia
     */
    public com.hyperborea.sira.ws.VocTendEvolSpiaggia getVocTendEvolSpiaggia() {
        return vocTendEvolSpiaggia;
    }


    /**
     * Sets the vocTendEvolSpiaggia value for this CcostSpiaggia.
     * 
     * @param vocTendEvolSpiaggia
     */
    public void setVocTendEvolSpiaggia(com.hyperborea.sira.ws.VocTendEvolSpiaggia vocTendEvolSpiaggia) {
        this.vocTendEvolSpiaggia = vocTendEvolSpiaggia;
    }


    /**
     * Gets the vocTessituraSpiaggia value for this CcostSpiaggia.
     * 
     * @return vocTessituraSpiaggia
     */
    public com.hyperborea.sira.ws.VocTessituraSpiaggia getVocTessituraSpiaggia() {
        return vocTessituraSpiaggia;
    }


    /**
     * Sets the vocTessituraSpiaggia value for this CcostSpiaggia.
     * 
     * @param vocTessituraSpiaggia
     */
    public void setVocTessituraSpiaggia(com.hyperborea.sira.ws.VocTessituraSpiaggia vocTessituraSpiaggia) {
        this.vocTessituraSpiaggia = vocTessituraSpiaggia;
    }


    /**
     * Gets the vocTipoDune value for this CcostSpiaggia.
     * 
     * @return vocTipoDune
     */
    public com.hyperborea.sira.ws.VocTipoDune getVocTipoDune() {
        return vocTipoDune;
    }


    /**
     * Sets the vocTipoDune value for this CcostSpiaggia.
     * 
     * @param vocTipoDune
     */
    public void setVocTipoDune(com.hyperborea.sira.ws.VocTipoDune vocTipoDune) {
        this.vocTipoDune = vocTipoDune;
    }


    /**
     * Gets the vocTipoVegetazione value for this CcostSpiaggia.
     * 
     * @return vocTipoVegetazione
     */
    public com.hyperborea.sira.ws.VocTipoVegetazione getVocTipoVegetazione() {
        return vocTipoVegetazione;
    }


    /**
     * Sets the vocTipoVegetazione value for this CcostSpiaggia.
     * 
     * @param vocTipoVegetazione
     */
    public void setVocTipoVegetazione(com.hyperborea.sira.ws.VocTipoVegetazione vocTipoVegetazione) {
        this.vocTipoVegetazione = vocTipoVegetazione;
    }


    /**
     * Gets the vocVarchiSpiaggia value for this CcostSpiaggia.
     * 
     * @return vocVarchiSpiaggia
     */
    public com.hyperborea.sira.ws.VocVarchiSpiaggia getVocVarchiSpiaggia() {
        return vocVarchiSpiaggia;
    }


    /**
     * Sets the vocVarchiSpiaggia value for this CcostSpiaggia.
     * 
     * @param vocVarchiSpiaggia
     */
    public void setVocVarchiSpiaggia(com.hyperborea.sira.ws.VocVarchiSpiaggia vocVarchiSpiaggia) {
        this.vocVarchiSpiaggia = vocVarchiSpiaggia;
    }


    /**
     * Gets the ripascimentoAreaCostieras value for this CcostSpiaggia.
     * 
     * @return ripascimentoAreaCostieras
     */
    public com.hyperborea.sira.ws.RipascimentoAreaCostiera[] getRipascimentoAreaCostieras() {
        return ripascimentoAreaCostieras;
    }


    /**
     * Sets the ripascimentoAreaCostieras value for this CcostSpiaggia.
     * 
     * @param ripascimentoAreaCostieras
     */
    public void setRipascimentoAreaCostieras(com.hyperborea.sira.ws.RipascimentoAreaCostiera[] ripascimentoAreaCostieras) {
        this.ripascimentoAreaCostieras = ripascimentoAreaCostieras;
    }

    public com.hyperborea.sira.ws.RipascimentoAreaCostiera getRipascimentoAreaCostieras(int i) {
        return this.ripascimentoAreaCostieras[i];
    }

    public void setRipascimentoAreaCostieras(int i, com.hyperborea.sira.ws.RipascimentoAreaCostiera _value) {
        this.ripascimentoAreaCostieras[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostSpiaggia)) return false;
        CcostSpiaggia other = (CcostSpiaggia) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.altezzaDune==null && other.getAltezzaDune()==null) || 
             (this.altezzaDune!=null &&
              this.altezzaDune.equals(other.getAltezzaDune()))) &&
            ((this.bilancioSedimentario==null && other.getBilancioSedimentario()==null) || 
             (this.bilancioSedimentario!=null &&
              this.bilancioSedimentario.equals(other.getBilancioSedimentario()))) &&
            ((this.carryngCapacity==null && other.getCarryngCapacity()==null) || 
             (this.carryngCapacity!=null &&
              this.carryngCapacity.equals(other.getCarryngCapacity()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.larghezzaMassima==null && other.getLarghezzaMassima()==null) || 
             (this.larghezzaMassima!=null &&
              this.larghezzaMassima.equals(other.getLarghezzaMassima()))) &&
            ((this.litologia==null && other.getLitologia()==null) || 
             (this.litologia!=null &&
              this.litologia.equals(other.getLitologia()))) &&
            ((this.lunghezza==null && other.getLunghezza()==null) || 
             (this.lunghezza!=null &&
              this.lunghezza.equals(other.getLunghezza()))) &&
            ((this.materialeCostiero==null && other.getMaterialeCostiero()==null) || 
             (this.materialeCostiero!=null &&
              this.materialeCostiero.equals(other.getMaterialeCostiero()))) &&
            ((this.numeroBaciniAfferenti==null && other.getNumeroBaciniAfferenti()==null) || 
             (this.numeroBaciniAfferenti!=null &&
              this.numeroBaciniAfferenti.equals(other.getNumeroBaciniAfferenti()))) &&
            ((this.pendenzaMedCostaSom==null && other.getPendenzaMedCostaSom()==null) || 
             (this.pendenzaMedCostaSom!=null &&
              this.pendenzaMedCostaSom.equals(other.getPendenzaMedCostaSom()))) &&
            ((this.presenzaDune==null && other.getPresenzaDune()==null) || 
             (this.presenzaDune!=null &&
              this.presenzaDune.equals(other.getPresenzaDune()))) &&
            ((this.profiliSpiaggia==null && other.getProfiliSpiaggia()==null) || 
             (this.profiliSpiaggia!=null &&
              this.profiliSpiaggia.equals(other.getProfiliSpiaggia()))) &&
            ((this.profondMedFondale==null && other.getProfondMedFondale()==null) || 
             (this.profondMedFondale!=null &&
              this.profondMedFondale.equals(other.getProfondMedFondale()))) &&
            ((this.profonditaChiusura==null && other.getProfonditaChiusura()==null) || 
             (this.profonditaChiusura!=null &&
              this.profonditaChiusura.equals(other.getProfonditaChiusura()))) &&
            ((this.sezioniSpiaggia==null && other.getSezioniSpiaggia()==null) || 
             (this.sezioniSpiaggia!=null &&
              this.sezioniSpiaggia.equals(other.getSezioniSpiaggia()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie()))) &&
            ((this.superficieBaciniAfferenti==null && other.getSuperficieBaciniAfferenti()==null) || 
             (this.superficieBaciniAfferenti!=null &&
              this.superficieBaciniAfferenti.equals(other.getSuperficieBaciniAfferenti()))) &&
            ((this.superficieDune==null && other.getSuperficieDune()==null) || 
             (this.superficieDune!=null &&
              this.superficieDune.equals(other.getSuperficieDune()))) &&
            ((this.tipologia==null && other.getTipologia()==null) || 
             (this.tipologia!=null &&
              this.tipologia.equals(other.getTipologia()))) &&
            ((this.vocGradoAntrSpiaggia==null && other.getVocGradoAntrSpiaggia()==null) || 
             (this.vocGradoAntrSpiaggia!=null &&
              this.vocGradoAntrSpiaggia.equals(other.getVocGradoAntrSpiaggia()))) &&
            ((this.vocTendEvolSpiaggia==null && other.getVocTendEvolSpiaggia()==null) || 
             (this.vocTendEvolSpiaggia!=null &&
              this.vocTendEvolSpiaggia.equals(other.getVocTendEvolSpiaggia()))) &&
            ((this.vocTessituraSpiaggia==null && other.getVocTessituraSpiaggia()==null) || 
             (this.vocTessituraSpiaggia!=null &&
              this.vocTessituraSpiaggia.equals(other.getVocTessituraSpiaggia()))) &&
            ((this.vocTipoDune==null && other.getVocTipoDune()==null) || 
             (this.vocTipoDune!=null &&
              this.vocTipoDune.equals(other.getVocTipoDune()))) &&
            ((this.vocTipoVegetazione==null && other.getVocTipoVegetazione()==null) || 
             (this.vocTipoVegetazione!=null &&
              this.vocTipoVegetazione.equals(other.getVocTipoVegetazione()))) &&
            ((this.vocVarchiSpiaggia==null && other.getVocVarchiSpiaggia()==null) || 
             (this.vocVarchiSpiaggia!=null &&
              this.vocVarchiSpiaggia.equals(other.getVocVarchiSpiaggia()))) &&
            ((this.ripascimentoAreaCostieras==null && other.getRipascimentoAreaCostieras()==null) || 
             (this.ripascimentoAreaCostieras!=null &&
              java.util.Arrays.equals(this.ripascimentoAreaCostieras, other.getRipascimentoAreaCostieras())));
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
        if (getAltezzaDune() != null) {
            _hashCode += getAltezzaDune().hashCode();
        }
        if (getBilancioSedimentario() != null) {
            _hashCode += getBilancioSedimentario().hashCode();
        }
        if (getCarryngCapacity() != null) {
            _hashCode += getCarryngCapacity().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getLarghezzaMassima() != null) {
            _hashCode += getLarghezzaMassima().hashCode();
        }
        if (getLitologia() != null) {
            _hashCode += getLitologia().hashCode();
        }
        if (getLunghezza() != null) {
            _hashCode += getLunghezza().hashCode();
        }
        if (getMaterialeCostiero() != null) {
            _hashCode += getMaterialeCostiero().hashCode();
        }
        if (getNumeroBaciniAfferenti() != null) {
            _hashCode += getNumeroBaciniAfferenti().hashCode();
        }
        if (getPendenzaMedCostaSom() != null) {
            _hashCode += getPendenzaMedCostaSom().hashCode();
        }
        if (getPresenzaDune() != null) {
            _hashCode += getPresenzaDune().hashCode();
        }
        if (getProfiliSpiaggia() != null) {
            _hashCode += getProfiliSpiaggia().hashCode();
        }
        if (getProfondMedFondale() != null) {
            _hashCode += getProfondMedFondale().hashCode();
        }
        if (getProfonditaChiusura() != null) {
            _hashCode += getProfonditaChiusura().hashCode();
        }
        if (getSezioniSpiaggia() != null) {
            _hashCode += getSezioniSpiaggia().hashCode();
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        if (getSuperficieBaciniAfferenti() != null) {
            _hashCode += getSuperficieBaciniAfferenti().hashCode();
        }
        if (getSuperficieDune() != null) {
            _hashCode += getSuperficieDune().hashCode();
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        if (getVocGradoAntrSpiaggia() != null) {
            _hashCode += getVocGradoAntrSpiaggia().hashCode();
        }
        if (getVocTendEvolSpiaggia() != null) {
            _hashCode += getVocTendEvolSpiaggia().hashCode();
        }
        if (getVocTessituraSpiaggia() != null) {
            _hashCode += getVocTessituraSpiaggia().hashCode();
        }
        if (getVocTipoDune() != null) {
            _hashCode += getVocTipoDune().hashCode();
        }
        if (getVocTipoVegetazione() != null) {
            _hashCode += getVocTipoVegetazione().hashCode();
        }
        if (getVocVarchiSpiaggia() != null) {
            _hashCode += getVocVarchiSpiaggia().hashCode();
        }
        if (getRipascimentoAreaCostieras() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRipascimentoAreaCostieras());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRipascimentoAreaCostieras(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostSpiaggia.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSpiaggia"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altezzaDune");
        elemField.setXmlName(new javax.xml.namespace.QName("", "altezzaDune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bilancioSedimentario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bilancioSedimentario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("carryngCapacity");
        elemField.setXmlName(new javax.xml.namespace.QName("", "carryngCapacity"));
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
        elemField.setFieldName("larghezzaMassima");
        elemField.setXmlName(new javax.xml.namespace.QName("", "larghezzaMassima"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
        elemField.setFieldName("materialeCostiero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "materialeCostiero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "materialeCostiero"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroBaciniAfferenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroBaciniAfferenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pendenzaMedCostaSom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pendenzaMedCostaSom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("presenzaDune");
        elemField.setXmlName(new javax.xml.namespace.QName("", "presenzaDune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profiliSpiaggia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profiliSpiaggia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profondMedFondale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profondMedFondale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profonditaChiusura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profonditaChiusura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sezioniSpiaggia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sezioniSpiaggia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("superficieBaciniAfferenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieBaciniAfferenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieDune");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieDune"));
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
        elemField.setFieldName("vocGradoAntrSpiaggia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocGradoAntrSpiaggia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocGradoAntrSpiaggia"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTendEvolSpiaggia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTendEvolSpiaggia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTendEvolSpiaggia"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTessituraSpiaggia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTessituraSpiaggia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTessituraSpiaggia"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoDune");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoDune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoDune"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoVegetazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoVegetazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoVegetazione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocVarchiSpiaggia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocVarchiSpiaggia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocVarchiSpiaggia"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ripascimentoAreaCostieras");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ripascimentoAreaCostieras"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ripascimentoAreaCostiera"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
