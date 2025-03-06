/**
 * CcostPozzi.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostPozzi  implements java.io.Serializable {
    private java.lang.Integer annoRealizzazione;

    private com.hyperborea.sira.ws.ApprovvAntincendio approvvAntincendio;

    private java.lang.String cementazione;

    private java.lang.String codGeniocivile;

    private com.hyperborea.sira.ws.ColLitostratigrafica[] colLitostratigraficas;

    private java.lang.String contatore;

    private java.lang.Float diametro;

    private java.lang.Float diametroRiv;

    private java.lang.String dispPrelievo;

    private java.lang.String elettControllo;

    private java.lang.Float fabGiornaliero;

    private java.lang.Float finestratureFine;

    private java.lang.Float finestratureInizio;

    private java.lang.Float giorniEmu;

    private java.lang.Integer idCcost;

    private java.lang.Float livPiezometro;

    private java.lang.Float livelloStatico;

    private java.lang.Float lungCementazione;

    private java.lang.Float lungFiltri;

    private java.lang.String materialeRiv;

    private java.lang.String modUtilizzo;

    private java.lang.Integer nucleoFamiliare;

    private java.lang.Float oreEmu;

    private java.lang.Float portataConc;

    private java.lang.Float portataEsercizio;

    private java.lang.Float portataMax;

    private java.lang.Float prelievoGiornaliero;

    private java.lang.Float profondita;

    private java.lang.String serbatoioAcc;

    private java.lang.Float soggiacenza;

    private java.lang.String stratigrafia;

    private java.lang.String strumMisura;

    private java.lang.Float supIrrigata;

    private java.lang.String tipoFinestrature;

    private java.lang.String tipoPozzo;

    private java.lang.String tipoSoggiacenza;

    private java.lang.String usoConc;

    private java.lang.Float volMedAnnuo;

    private java.lang.Float volumeAccumulo;

    public CcostPozzi() {
    }

    public CcostPozzi(
           java.lang.Integer annoRealizzazione,
           com.hyperborea.sira.ws.ApprovvAntincendio approvvAntincendio,
           java.lang.String cementazione,
           java.lang.String codGeniocivile,
           com.hyperborea.sira.ws.ColLitostratigrafica[] colLitostratigraficas,
           java.lang.String contatore,
           java.lang.Float diametro,
           java.lang.Float diametroRiv,
           java.lang.String dispPrelievo,
           java.lang.String elettControllo,
           java.lang.Float fabGiornaliero,
           java.lang.Float finestratureFine,
           java.lang.Float finestratureInizio,
           java.lang.Float giorniEmu,
           java.lang.Integer idCcost,
           java.lang.Float livPiezometro,
           java.lang.Float livelloStatico,
           java.lang.Float lungCementazione,
           java.lang.Float lungFiltri,
           java.lang.String materialeRiv,
           java.lang.String modUtilizzo,
           java.lang.Integer nucleoFamiliare,
           java.lang.Float oreEmu,
           java.lang.Float portataConc,
           java.lang.Float portataEsercizio,
           java.lang.Float portataMax,
           java.lang.Float prelievoGiornaliero,
           java.lang.Float profondita,
           java.lang.String serbatoioAcc,
           java.lang.Float soggiacenza,
           java.lang.String stratigrafia,
           java.lang.String strumMisura,
           java.lang.Float supIrrigata,
           java.lang.String tipoFinestrature,
           java.lang.String tipoPozzo,
           java.lang.String tipoSoggiacenza,
           java.lang.String usoConc,
           java.lang.Float volMedAnnuo,
           java.lang.Float volumeAccumulo) {
           this.annoRealizzazione = annoRealizzazione;
           this.approvvAntincendio = approvvAntincendio;
           this.cementazione = cementazione;
           this.codGeniocivile = codGeniocivile;
           this.colLitostratigraficas = colLitostratigraficas;
           this.contatore = contatore;
           this.diametro = diametro;
           this.diametroRiv = diametroRiv;
           this.dispPrelievo = dispPrelievo;
           this.elettControllo = elettControllo;
           this.fabGiornaliero = fabGiornaliero;
           this.finestratureFine = finestratureFine;
           this.finestratureInizio = finestratureInizio;
           this.giorniEmu = giorniEmu;
           this.idCcost = idCcost;
           this.livPiezometro = livPiezometro;
           this.livelloStatico = livelloStatico;
           this.lungCementazione = lungCementazione;
           this.lungFiltri = lungFiltri;
           this.materialeRiv = materialeRiv;
           this.modUtilizzo = modUtilizzo;
           this.nucleoFamiliare = nucleoFamiliare;
           this.oreEmu = oreEmu;
           this.portataConc = portataConc;
           this.portataEsercizio = portataEsercizio;
           this.portataMax = portataMax;
           this.prelievoGiornaliero = prelievoGiornaliero;
           this.profondita = profondita;
           this.serbatoioAcc = serbatoioAcc;
           this.soggiacenza = soggiacenza;
           this.stratigrafia = stratigrafia;
           this.strumMisura = strumMisura;
           this.supIrrigata = supIrrigata;
           this.tipoFinestrature = tipoFinestrature;
           this.tipoPozzo = tipoPozzo;
           this.tipoSoggiacenza = tipoSoggiacenza;
           this.usoConc = usoConc;
           this.volMedAnnuo = volMedAnnuo;
           this.volumeAccumulo = volumeAccumulo;
    }


    /**
     * Gets the annoRealizzazione value for this CcostPozzi.
     * 
     * @return annoRealizzazione
     */
    public java.lang.Integer getAnnoRealizzazione() {
        return annoRealizzazione;
    }


    /**
     * Sets the annoRealizzazione value for this CcostPozzi.
     * 
     * @param annoRealizzazione
     */
    public void setAnnoRealizzazione(java.lang.Integer annoRealizzazione) {
        this.annoRealizzazione = annoRealizzazione;
    }


    /**
     * Gets the approvvAntincendio value for this CcostPozzi.
     * 
     * @return approvvAntincendio
     */
    public com.hyperborea.sira.ws.ApprovvAntincendio getApprovvAntincendio() {
        return approvvAntincendio;
    }


    /**
     * Sets the approvvAntincendio value for this CcostPozzi.
     * 
     * @param approvvAntincendio
     */
    public void setApprovvAntincendio(com.hyperborea.sira.ws.ApprovvAntincendio approvvAntincendio) {
        this.approvvAntincendio = approvvAntincendio;
    }


    /**
     * Gets the cementazione value for this CcostPozzi.
     * 
     * @return cementazione
     */
    public java.lang.String getCementazione() {
        return cementazione;
    }


    /**
     * Sets the cementazione value for this CcostPozzi.
     * 
     * @param cementazione
     */
    public void setCementazione(java.lang.String cementazione) {
        this.cementazione = cementazione;
    }


    /**
     * Gets the codGeniocivile value for this CcostPozzi.
     * 
     * @return codGeniocivile
     */
    public java.lang.String getCodGeniocivile() {
        return codGeniocivile;
    }


    /**
     * Sets the codGeniocivile value for this CcostPozzi.
     * 
     * @param codGeniocivile
     */
    public void setCodGeniocivile(java.lang.String codGeniocivile) {
        this.codGeniocivile = codGeniocivile;
    }


    /**
     * Gets the colLitostratigraficas value for this CcostPozzi.
     * 
     * @return colLitostratigraficas
     */
    public com.hyperborea.sira.ws.ColLitostratigrafica[] getColLitostratigraficas() {
        return colLitostratigraficas;
    }


    /**
     * Sets the colLitostratigraficas value for this CcostPozzi.
     * 
     * @param colLitostratigraficas
     */
    public void setColLitostratigraficas(com.hyperborea.sira.ws.ColLitostratigrafica[] colLitostratigraficas) {
        this.colLitostratigraficas = colLitostratigraficas;
    }

    public com.hyperborea.sira.ws.ColLitostratigrafica getColLitostratigraficas(int i) {
        return this.colLitostratigraficas[i];
    }

    public void setColLitostratigraficas(int i, com.hyperborea.sira.ws.ColLitostratigrafica _value) {
        this.colLitostratigraficas[i] = _value;
    }


    /**
     * Gets the contatore value for this CcostPozzi.
     * 
     * @return contatore
     */
    public java.lang.String getContatore() {
        return contatore;
    }


    /**
     * Sets the contatore value for this CcostPozzi.
     * 
     * @param contatore
     */
    public void setContatore(java.lang.String contatore) {
        this.contatore = contatore;
    }


    /**
     * Gets the diametro value for this CcostPozzi.
     * 
     * @return diametro
     */
    public java.lang.Float getDiametro() {
        return diametro;
    }


    /**
     * Sets the diametro value for this CcostPozzi.
     * 
     * @param diametro
     */
    public void setDiametro(java.lang.Float diametro) {
        this.diametro = diametro;
    }


    /**
     * Gets the diametroRiv value for this CcostPozzi.
     * 
     * @return diametroRiv
     */
    public java.lang.Float getDiametroRiv() {
        return diametroRiv;
    }


    /**
     * Sets the diametroRiv value for this CcostPozzi.
     * 
     * @param diametroRiv
     */
    public void setDiametroRiv(java.lang.Float diametroRiv) {
        this.diametroRiv = diametroRiv;
    }


    /**
     * Gets the dispPrelievo value for this CcostPozzi.
     * 
     * @return dispPrelievo
     */
    public java.lang.String getDispPrelievo() {
        return dispPrelievo;
    }


    /**
     * Sets the dispPrelievo value for this CcostPozzi.
     * 
     * @param dispPrelievo
     */
    public void setDispPrelievo(java.lang.String dispPrelievo) {
        this.dispPrelievo = dispPrelievo;
    }


    /**
     * Gets the elettControllo value for this CcostPozzi.
     * 
     * @return elettControllo
     */
    public java.lang.String getElettControllo() {
        return elettControllo;
    }


    /**
     * Sets the elettControllo value for this CcostPozzi.
     * 
     * @param elettControllo
     */
    public void setElettControllo(java.lang.String elettControllo) {
        this.elettControllo = elettControllo;
    }


    /**
     * Gets the fabGiornaliero value for this CcostPozzi.
     * 
     * @return fabGiornaliero
     */
    public java.lang.Float getFabGiornaliero() {
        return fabGiornaliero;
    }


    /**
     * Sets the fabGiornaliero value for this CcostPozzi.
     * 
     * @param fabGiornaliero
     */
    public void setFabGiornaliero(java.lang.Float fabGiornaliero) {
        this.fabGiornaliero = fabGiornaliero;
    }


    /**
     * Gets the finestratureFine value for this CcostPozzi.
     * 
     * @return finestratureFine
     */
    public java.lang.Float getFinestratureFine() {
        return finestratureFine;
    }


    /**
     * Sets the finestratureFine value for this CcostPozzi.
     * 
     * @param finestratureFine
     */
    public void setFinestratureFine(java.lang.Float finestratureFine) {
        this.finestratureFine = finestratureFine;
    }


    /**
     * Gets the finestratureInizio value for this CcostPozzi.
     * 
     * @return finestratureInizio
     */
    public java.lang.Float getFinestratureInizio() {
        return finestratureInizio;
    }


    /**
     * Sets the finestratureInizio value for this CcostPozzi.
     * 
     * @param finestratureInizio
     */
    public void setFinestratureInizio(java.lang.Float finestratureInizio) {
        this.finestratureInizio = finestratureInizio;
    }


    /**
     * Gets the giorniEmu value for this CcostPozzi.
     * 
     * @return giorniEmu
     */
    public java.lang.Float getGiorniEmu() {
        return giorniEmu;
    }


    /**
     * Sets the giorniEmu value for this CcostPozzi.
     * 
     * @param giorniEmu
     */
    public void setGiorniEmu(java.lang.Float giorniEmu) {
        this.giorniEmu = giorniEmu;
    }


    /**
     * Gets the idCcost value for this CcostPozzi.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostPozzi.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the livPiezometro value for this CcostPozzi.
     * 
     * @return livPiezometro
     */
    public java.lang.Float getLivPiezometro() {
        return livPiezometro;
    }


    /**
     * Sets the livPiezometro value for this CcostPozzi.
     * 
     * @param livPiezometro
     */
    public void setLivPiezometro(java.lang.Float livPiezometro) {
        this.livPiezometro = livPiezometro;
    }


    /**
     * Gets the livelloStatico value for this CcostPozzi.
     * 
     * @return livelloStatico
     */
    public java.lang.Float getLivelloStatico() {
        return livelloStatico;
    }


    /**
     * Sets the livelloStatico value for this CcostPozzi.
     * 
     * @param livelloStatico
     */
    public void setLivelloStatico(java.lang.Float livelloStatico) {
        this.livelloStatico = livelloStatico;
    }


    /**
     * Gets the lungCementazione value for this CcostPozzi.
     * 
     * @return lungCementazione
     */
    public java.lang.Float getLungCementazione() {
        return lungCementazione;
    }


    /**
     * Sets the lungCementazione value for this CcostPozzi.
     * 
     * @param lungCementazione
     */
    public void setLungCementazione(java.lang.Float lungCementazione) {
        this.lungCementazione = lungCementazione;
    }


    /**
     * Gets the lungFiltri value for this CcostPozzi.
     * 
     * @return lungFiltri
     */
    public java.lang.Float getLungFiltri() {
        return lungFiltri;
    }


    /**
     * Sets the lungFiltri value for this CcostPozzi.
     * 
     * @param lungFiltri
     */
    public void setLungFiltri(java.lang.Float lungFiltri) {
        this.lungFiltri = lungFiltri;
    }


    /**
     * Gets the materialeRiv value for this CcostPozzi.
     * 
     * @return materialeRiv
     */
    public java.lang.String getMaterialeRiv() {
        return materialeRiv;
    }


    /**
     * Sets the materialeRiv value for this CcostPozzi.
     * 
     * @param materialeRiv
     */
    public void setMaterialeRiv(java.lang.String materialeRiv) {
        this.materialeRiv = materialeRiv;
    }


    /**
     * Gets the modUtilizzo value for this CcostPozzi.
     * 
     * @return modUtilizzo
     */
    public java.lang.String getModUtilizzo() {
        return modUtilizzo;
    }


    /**
     * Sets the modUtilizzo value for this CcostPozzi.
     * 
     * @param modUtilizzo
     */
    public void setModUtilizzo(java.lang.String modUtilizzo) {
        this.modUtilizzo = modUtilizzo;
    }


    /**
     * Gets the nucleoFamiliare value for this CcostPozzi.
     * 
     * @return nucleoFamiliare
     */
    public java.lang.Integer getNucleoFamiliare() {
        return nucleoFamiliare;
    }


    /**
     * Sets the nucleoFamiliare value for this CcostPozzi.
     * 
     * @param nucleoFamiliare
     */
    public void setNucleoFamiliare(java.lang.Integer nucleoFamiliare) {
        this.nucleoFamiliare = nucleoFamiliare;
    }


    /**
     * Gets the oreEmu value for this CcostPozzi.
     * 
     * @return oreEmu
     */
    public java.lang.Float getOreEmu() {
        return oreEmu;
    }


    /**
     * Sets the oreEmu value for this CcostPozzi.
     * 
     * @param oreEmu
     */
    public void setOreEmu(java.lang.Float oreEmu) {
        this.oreEmu = oreEmu;
    }


    /**
     * Gets the portataConc value for this CcostPozzi.
     * 
     * @return portataConc
     */
    public java.lang.Float getPortataConc() {
        return portataConc;
    }


    /**
     * Sets the portataConc value for this CcostPozzi.
     * 
     * @param portataConc
     */
    public void setPortataConc(java.lang.Float portataConc) {
        this.portataConc = portataConc;
    }


    /**
     * Gets the portataEsercizio value for this CcostPozzi.
     * 
     * @return portataEsercizio
     */
    public java.lang.Float getPortataEsercizio() {
        return portataEsercizio;
    }


    /**
     * Sets the portataEsercizio value for this CcostPozzi.
     * 
     * @param portataEsercizio
     */
    public void setPortataEsercizio(java.lang.Float portataEsercizio) {
        this.portataEsercizio = portataEsercizio;
    }


    /**
     * Gets the portataMax value for this CcostPozzi.
     * 
     * @return portataMax
     */
    public java.lang.Float getPortataMax() {
        return portataMax;
    }


    /**
     * Sets the portataMax value for this CcostPozzi.
     * 
     * @param portataMax
     */
    public void setPortataMax(java.lang.Float portataMax) {
        this.portataMax = portataMax;
    }


    /**
     * Gets the prelievoGiornaliero value for this CcostPozzi.
     * 
     * @return prelievoGiornaliero
     */
    public java.lang.Float getPrelievoGiornaliero() {
        return prelievoGiornaliero;
    }


    /**
     * Sets the prelievoGiornaliero value for this CcostPozzi.
     * 
     * @param prelievoGiornaliero
     */
    public void setPrelievoGiornaliero(java.lang.Float prelievoGiornaliero) {
        this.prelievoGiornaliero = prelievoGiornaliero;
    }


    /**
     * Gets the profondita value for this CcostPozzi.
     * 
     * @return profondita
     */
    public java.lang.Float getProfondita() {
        return profondita;
    }


    /**
     * Sets the profondita value for this CcostPozzi.
     * 
     * @param profondita
     */
    public void setProfondita(java.lang.Float profondita) {
        this.profondita = profondita;
    }


    /**
     * Gets the serbatoioAcc value for this CcostPozzi.
     * 
     * @return serbatoioAcc
     */
    public java.lang.String getSerbatoioAcc() {
        return serbatoioAcc;
    }


    /**
     * Sets the serbatoioAcc value for this CcostPozzi.
     * 
     * @param serbatoioAcc
     */
    public void setSerbatoioAcc(java.lang.String serbatoioAcc) {
        this.serbatoioAcc = serbatoioAcc;
    }


    /**
     * Gets the soggiacenza value for this CcostPozzi.
     * 
     * @return soggiacenza
     */
    public java.lang.Float getSoggiacenza() {
        return soggiacenza;
    }


    /**
     * Sets the soggiacenza value for this CcostPozzi.
     * 
     * @param soggiacenza
     */
    public void setSoggiacenza(java.lang.Float soggiacenza) {
        this.soggiacenza = soggiacenza;
    }


    /**
     * Gets the stratigrafia value for this CcostPozzi.
     * 
     * @return stratigrafia
     */
    public java.lang.String getStratigrafia() {
        return stratigrafia;
    }


    /**
     * Sets the stratigrafia value for this CcostPozzi.
     * 
     * @param stratigrafia
     */
    public void setStratigrafia(java.lang.String stratigrafia) {
        this.stratigrafia = stratigrafia;
    }


    /**
     * Gets the strumMisura value for this CcostPozzi.
     * 
     * @return strumMisura
     */
    public java.lang.String getStrumMisura() {
        return strumMisura;
    }


    /**
     * Sets the strumMisura value for this CcostPozzi.
     * 
     * @param strumMisura
     */
    public void setStrumMisura(java.lang.String strumMisura) {
        this.strumMisura = strumMisura;
    }


    /**
     * Gets the supIrrigata value for this CcostPozzi.
     * 
     * @return supIrrigata
     */
    public java.lang.Float getSupIrrigata() {
        return supIrrigata;
    }


    /**
     * Sets the supIrrigata value for this CcostPozzi.
     * 
     * @param supIrrigata
     */
    public void setSupIrrigata(java.lang.Float supIrrigata) {
        this.supIrrigata = supIrrigata;
    }


    /**
     * Gets the tipoFinestrature value for this CcostPozzi.
     * 
     * @return tipoFinestrature
     */
    public java.lang.String getTipoFinestrature() {
        return tipoFinestrature;
    }


    /**
     * Sets the tipoFinestrature value for this CcostPozzi.
     * 
     * @param tipoFinestrature
     */
    public void setTipoFinestrature(java.lang.String tipoFinestrature) {
        this.tipoFinestrature = tipoFinestrature;
    }


    /**
     * Gets the tipoPozzo value for this CcostPozzi.
     * 
     * @return tipoPozzo
     */
    public java.lang.String getTipoPozzo() {
        return tipoPozzo;
    }


    /**
     * Sets the tipoPozzo value for this CcostPozzi.
     * 
     * @param tipoPozzo
     */
    public void setTipoPozzo(java.lang.String tipoPozzo) {
        this.tipoPozzo = tipoPozzo;
    }


    /**
     * Gets the tipoSoggiacenza value for this CcostPozzi.
     * 
     * @return tipoSoggiacenza
     */
    public java.lang.String getTipoSoggiacenza() {
        return tipoSoggiacenza;
    }


    /**
     * Sets the tipoSoggiacenza value for this CcostPozzi.
     * 
     * @param tipoSoggiacenza
     */
    public void setTipoSoggiacenza(java.lang.String tipoSoggiacenza) {
        this.tipoSoggiacenza = tipoSoggiacenza;
    }


    /**
     * Gets the usoConc value for this CcostPozzi.
     * 
     * @return usoConc
     */
    public java.lang.String getUsoConc() {
        return usoConc;
    }


    /**
     * Sets the usoConc value for this CcostPozzi.
     * 
     * @param usoConc
     */
    public void setUsoConc(java.lang.String usoConc) {
        this.usoConc = usoConc;
    }


    /**
     * Gets the volMedAnnuo value for this CcostPozzi.
     * 
     * @return volMedAnnuo
     */
    public java.lang.Float getVolMedAnnuo() {
        return volMedAnnuo;
    }


    /**
     * Sets the volMedAnnuo value for this CcostPozzi.
     * 
     * @param volMedAnnuo
     */
    public void setVolMedAnnuo(java.lang.Float volMedAnnuo) {
        this.volMedAnnuo = volMedAnnuo;
    }


    /**
     * Gets the volumeAccumulo value for this CcostPozzi.
     * 
     * @return volumeAccumulo
     */
    public java.lang.Float getVolumeAccumulo() {
        return volumeAccumulo;
    }


    /**
     * Sets the volumeAccumulo value for this CcostPozzi.
     * 
     * @param volumeAccumulo
     */
    public void setVolumeAccumulo(java.lang.Float volumeAccumulo) {
        this.volumeAccumulo = volumeAccumulo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostPozzi)) return false;
        CcostPozzi other = (CcostPozzi) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.annoRealizzazione==null && other.getAnnoRealizzazione()==null) || 
             (this.annoRealizzazione!=null &&
              this.annoRealizzazione.equals(other.getAnnoRealizzazione()))) &&
            ((this.approvvAntincendio==null && other.getApprovvAntincendio()==null) || 
             (this.approvvAntincendio!=null &&
              this.approvvAntincendio.equals(other.getApprovvAntincendio()))) &&
            ((this.cementazione==null && other.getCementazione()==null) || 
             (this.cementazione!=null &&
              this.cementazione.equals(other.getCementazione()))) &&
            ((this.codGeniocivile==null && other.getCodGeniocivile()==null) || 
             (this.codGeniocivile!=null &&
              this.codGeniocivile.equals(other.getCodGeniocivile()))) &&
            ((this.colLitostratigraficas==null && other.getColLitostratigraficas()==null) || 
             (this.colLitostratigraficas!=null &&
              java.util.Arrays.equals(this.colLitostratigraficas, other.getColLitostratigraficas()))) &&
            ((this.contatore==null && other.getContatore()==null) || 
             (this.contatore!=null &&
              this.contatore.equals(other.getContatore()))) &&
            ((this.diametro==null && other.getDiametro()==null) || 
             (this.diametro!=null &&
              this.diametro.equals(other.getDiametro()))) &&
            ((this.diametroRiv==null && other.getDiametroRiv()==null) || 
             (this.diametroRiv!=null &&
              this.diametroRiv.equals(other.getDiametroRiv()))) &&
            ((this.dispPrelievo==null && other.getDispPrelievo()==null) || 
             (this.dispPrelievo!=null &&
              this.dispPrelievo.equals(other.getDispPrelievo()))) &&
            ((this.elettControllo==null && other.getElettControllo()==null) || 
             (this.elettControllo!=null &&
              this.elettControllo.equals(other.getElettControllo()))) &&
            ((this.fabGiornaliero==null && other.getFabGiornaliero()==null) || 
             (this.fabGiornaliero!=null &&
              this.fabGiornaliero.equals(other.getFabGiornaliero()))) &&
            ((this.finestratureFine==null && other.getFinestratureFine()==null) || 
             (this.finestratureFine!=null &&
              this.finestratureFine.equals(other.getFinestratureFine()))) &&
            ((this.finestratureInizio==null && other.getFinestratureInizio()==null) || 
             (this.finestratureInizio!=null &&
              this.finestratureInizio.equals(other.getFinestratureInizio()))) &&
            ((this.giorniEmu==null && other.getGiorniEmu()==null) || 
             (this.giorniEmu!=null &&
              this.giorniEmu.equals(other.getGiorniEmu()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.livPiezometro==null && other.getLivPiezometro()==null) || 
             (this.livPiezometro!=null &&
              this.livPiezometro.equals(other.getLivPiezometro()))) &&
            ((this.livelloStatico==null && other.getLivelloStatico()==null) || 
             (this.livelloStatico!=null &&
              this.livelloStatico.equals(other.getLivelloStatico()))) &&
            ((this.lungCementazione==null && other.getLungCementazione()==null) || 
             (this.lungCementazione!=null &&
              this.lungCementazione.equals(other.getLungCementazione()))) &&
            ((this.lungFiltri==null && other.getLungFiltri()==null) || 
             (this.lungFiltri!=null &&
              this.lungFiltri.equals(other.getLungFiltri()))) &&
            ((this.materialeRiv==null && other.getMaterialeRiv()==null) || 
             (this.materialeRiv!=null &&
              this.materialeRiv.equals(other.getMaterialeRiv()))) &&
            ((this.modUtilizzo==null && other.getModUtilizzo()==null) || 
             (this.modUtilizzo!=null &&
              this.modUtilizzo.equals(other.getModUtilizzo()))) &&
            ((this.nucleoFamiliare==null && other.getNucleoFamiliare()==null) || 
             (this.nucleoFamiliare!=null &&
              this.nucleoFamiliare.equals(other.getNucleoFamiliare()))) &&
            ((this.oreEmu==null && other.getOreEmu()==null) || 
             (this.oreEmu!=null &&
              this.oreEmu.equals(other.getOreEmu()))) &&
            ((this.portataConc==null && other.getPortataConc()==null) || 
             (this.portataConc!=null &&
              this.portataConc.equals(other.getPortataConc()))) &&
            ((this.portataEsercizio==null && other.getPortataEsercizio()==null) || 
             (this.portataEsercizio!=null &&
              this.portataEsercizio.equals(other.getPortataEsercizio()))) &&
            ((this.portataMax==null && other.getPortataMax()==null) || 
             (this.portataMax!=null &&
              this.portataMax.equals(other.getPortataMax()))) &&
            ((this.prelievoGiornaliero==null && other.getPrelievoGiornaliero()==null) || 
             (this.prelievoGiornaliero!=null &&
              this.prelievoGiornaliero.equals(other.getPrelievoGiornaliero()))) &&
            ((this.profondita==null && other.getProfondita()==null) || 
             (this.profondita!=null &&
              this.profondita.equals(other.getProfondita()))) &&
            ((this.serbatoioAcc==null && other.getSerbatoioAcc()==null) || 
             (this.serbatoioAcc!=null &&
              this.serbatoioAcc.equals(other.getSerbatoioAcc()))) &&
            ((this.soggiacenza==null && other.getSoggiacenza()==null) || 
             (this.soggiacenza!=null &&
              this.soggiacenza.equals(other.getSoggiacenza()))) &&
            ((this.stratigrafia==null && other.getStratigrafia()==null) || 
             (this.stratigrafia!=null &&
              this.stratigrafia.equals(other.getStratigrafia()))) &&
            ((this.strumMisura==null && other.getStrumMisura()==null) || 
             (this.strumMisura!=null &&
              this.strumMisura.equals(other.getStrumMisura()))) &&
            ((this.supIrrigata==null && other.getSupIrrigata()==null) || 
             (this.supIrrigata!=null &&
              this.supIrrigata.equals(other.getSupIrrigata()))) &&
            ((this.tipoFinestrature==null && other.getTipoFinestrature()==null) || 
             (this.tipoFinestrature!=null &&
              this.tipoFinestrature.equals(other.getTipoFinestrature()))) &&
            ((this.tipoPozzo==null && other.getTipoPozzo()==null) || 
             (this.tipoPozzo!=null &&
              this.tipoPozzo.equals(other.getTipoPozzo()))) &&
            ((this.tipoSoggiacenza==null && other.getTipoSoggiacenza()==null) || 
             (this.tipoSoggiacenza!=null &&
              this.tipoSoggiacenza.equals(other.getTipoSoggiacenza()))) &&
            ((this.usoConc==null && other.getUsoConc()==null) || 
             (this.usoConc!=null &&
              this.usoConc.equals(other.getUsoConc()))) &&
            ((this.volMedAnnuo==null && other.getVolMedAnnuo()==null) || 
             (this.volMedAnnuo!=null &&
              this.volMedAnnuo.equals(other.getVolMedAnnuo()))) &&
            ((this.volumeAccumulo==null && other.getVolumeAccumulo()==null) || 
             (this.volumeAccumulo!=null &&
              this.volumeAccumulo.equals(other.getVolumeAccumulo())));
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
        if (getAnnoRealizzazione() != null) {
            _hashCode += getAnnoRealizzazione().hashCode();
        }
        if (getApprovvAntincendio() != null) {
            _hashCode += getApprovvAntincendio().hashCode();
        }
        if (getCementazione() != null) {
            _hashCode += getCementazione().hashCode();
        }
        if (getCodGeniocivile() != null) {
            _hashCode += getCodGeniocivile().hashCode();
        }
        if (getColLitostratigraficas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getColLitostratigraficas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getColLitostratigraficas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getContatore() != null) {
            _hashCode += getContatore().hashCode();
        }
        if (getDiametro() != null) {
            _hashCode += getDiametro().hashCode();
        }
        if (getDiametroRiv() != null) {
            _hashCode += getDiametroRiv().hashCode();
        }
        if (getDispPrelievo() != null) {
            _hashCode += getDispPrelievo().hashCode();
        }
        if (getElettControllo() != null) {
            _hashCode += getElettControllo().hashCode();
        }
        if (getFabGiornaliero() != null) {
            _hashCode += getFabGiornaliero().hashCode();
        }
        if (getFinestratureFine() != null) {
            _hashCode += getFinestratureFine().hashCode();
        }
        if (getFinestratureInizio() != null) {
            _hashCode += getFinestratureInizio().hashCode();
        }
        if (getGiorniEmu() != null) {
            _hashCode += getGiorniEmu().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getLivPiezometro() != null) {
            _hashCode += getLivPiezometro().hashCode();
        }
        if (getLivelloStatico() != null) {
            _hashCode += getLivelloStatico().hashCode();
        }
        if (getLungCementazione() != null) {
            _hashCode += getLungCementazione().hashCode();
        }
        if (getLungFiltri() != null) {
            _hashCode += getLungFiltri().hashCode();
        }
        if (getMaterialeRiv() != null) {
            _hashCode += getMaterialeRiv().hashCode();
        }
        if (getModUtilizzo() != null) {
            _hashCode += getModUtilizzo().hashCode();
        }
        if (getNucleoFamiliare() != null) {
            _hashCode += getNucleoFamiliare().hashCode();
        }
        if (getOreEmu() != null) {
            _hashCode += getOreEmu().hashCode();
        }
        if (getPortataConc() != null) {
            _hashCode += getPortataConc().hashCode();
        }
        if (getPortataEsercizio() != null) {
            _hashCode += getPortataEsercizio().hashCode();
        }
        if (getPortataMax() != null) {
            _hashCode += getPortataMax().hashCode();
        }
        if (getPrelievoGiornaliero() != null) {
            _hashCode += getPrelievoGiornaliero().hashCode();
        }
        if (getProfondita() != null) {
            _hashCode += getProfondita().hashCode();
        }
        if (getSerbatoioAcc() != null) {
            _hashCode += getSerbatoioAcc().hashCode();
        }
        if (getSoggiacenza() != null) {
            _hashCode += getSoggiacenza().hashCode();
        }
        if (getStratigrafia() != null) {
            _hashCode += getStratigrafia().hashCode();
        }
        if (getStrumMisura() != null) {
            _hashCode += getStrumMisura().hashCode();
        }
        if (getSupIrrigata() != null) {
            _hashCode += getSupIrrigata().hashCode();
        }
        if (getTipoFinestrature() != null) {
            _hashCode += getTipoFinestrature().hashCode();
        }
        if (getTipoPozzo() != null) {
            _hashCode += getTipoPozzo().hashCode();
        }
        if (getTipoSoggiacenza() != null) {
            _hashCode += getTipoSoggiacenza().hashCode();
        }
        if (getUsoConc() != null) {
            _hashCode += getUsoConc().hashCode();
        }
        if (getVolMedAnnuo() != null) {
            _hashCode += getVolMedAnnuo().hashCode();
        }
        if (getVolumeAccumulo() != null) {
            _hashCode += getVolumeAccumulo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostPozzi.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostPozzi"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoRealizzazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoRealizzazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("approvvAntincendio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "approvvAntincendio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "approvvAntincendio"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cementazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cementazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codGeniocivile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codGeniocivile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("colLitostratigraficas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "colLitostratigraficas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "colLitostratigrafica"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contatore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contatore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("diametro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "diametro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("diametroRiv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "diametroRiv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dispPrelievo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dispPrelievo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elettControllo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "elettControllo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fabGiornaliero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fabGiornaliero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finestratureFine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "finestratureFine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finestratureInizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "finestratureInizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("giorniEmu");
        elemField.setXmlName(new javax.xml.namespace.QName("", "giorniEmu"));
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
        elemField.setFieldName("livPiezometro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "livPiezometro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("livelloStatico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "livelloStatico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lungCementazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lungCementazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lungFiltri");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lungFiltri"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("materialeRiv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "materialeRiv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modUtilizzo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modUtilizzo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nucleoFamiliare");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nucleoFamiliare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oreEmu");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oreEmu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataConc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataConc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataEsercizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataEsercizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prelievoGiornaliero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prelievoGiornaliero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profondita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profondita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serbatoioAcc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serbatoioAcc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soggiacenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soggiacenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stratigrafia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stratigrafia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strumMisura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "strumMisura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supIrrigata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "supIrrigata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoFinestrature");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoFinestrature"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoPozzo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoPozzo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoSoggiacenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoSoggiacenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usoConc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "usoConc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volMedAnnuo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volMedAnnuo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeAccumulo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeAccumulo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
