/**
 * SchedaMonitoraggio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class SchedaMonitoraggio  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String altreProvince;

    private java.lang.String autore;

    private java.lang.String committenteProgetto;

    private java.util.Calendar dataCompilazione;

    private java.util.Calendar dataRil;

    private java.lang.Double esposizioneMaxGradi;

    private java.lang.Double esposizioneMediaGradi;

    private java.lang.Double esposizioneMinGradi;

    private com.hyperborea.sira.ws.VocFonti fonte;

    private com.hyperborea.sira.ws.Habitat habitat;

    private java.lang.Integer idScheda;

    private java.lang.Double inclinazioneMaxGradi;

    private java.lang.Double inclinazioneMediaGradi;

    private java.lang.Double inclinazioneMinGradi;

    private java.lang.Integer minFineRil;

    private java.lang.Integer minInizioRil;

    private java.lang.String nomeCompilatore;

    private java.lang.Integer numScheda;

    private java.lang.Integer oggettoNonRilevato;

    private java.lang.Integer oraFineRil;

    private java.lang.Integer oraInizioRil;

    private com.hyperborea.sira.ws.VocProprieta proprieta;

    private java.lang.Double quotaMaxSlmM;

    private java.lang.Double quotaMediaSlmM;

    private java.lang.Double quotaMinSlmM;

    private java.lang.Integer schedaGeoriferita;

    private java.lang.Integer schedaValidata;

    private com.hyperborea.sira.ws.Specie specie;

    private java.lang.String titoloProgetto;

    private java.lang.String ucId;

    private com.hyperborea.sira.ws.ValCampiRipSchedeMon[] valCampiRipSchedeMons;

    private com.hyperborea.sira.ws.ValCampiSchedeMon[] valCampiSchedeMons;

    private com.hyperborea.sira.ws.WsAttivitaEsternaRef wsAttivitaEsternaRef;

    private com.hyperborea.sira.ws.WsCcostRef wsCcostRef;

    private com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioRef wsTipiSchedaMonitoraggioRef;

    public SchedaMonitoraggio() {
    }

    public SchedaMonitoraggio(
           java.lang.String altreProvince,
           java.lang.String autore,
           java.lang.String committenteProgetto,
           java.util.Calendar dataCompilazione,
           java.util.Calendar dataRil,
           java.lang.Double esposizioneMaxGradi,
           java.lang.Double esposizioneMediaGradi,
           java.lang.Double esposizioneMinGradi,
           com.hyperborea.sira.ws.VocFonti fonte,
           com.hyperborea.sira.ws.Habitat habitat,
           java.lang.Integer idScheda,
           java.lang.Double inclinazioneMaxGradi,
           java.lang.Double inclinazioneMediaGradi,
           java.lang.Double inclinazioneMinGradi,
           java.lang.Integer minFineRil,
           java.lang.Integer minInizioRil,
           java.lang.String nomeCompilatore,
           java.lang.Integer numScheda,
           java.lang.Integer oggettoNonRilevato,
           java.lang.Integer oraFineRil,
           java.lang.Integer oraInizioRil,
           com.hyperborea.sira.ws.VocProprieta proprieta,
           java.lang.Double quotaMaxSlmM,
           java.lang.Double quotaMediaSlmM,
           java.lang.Double quotaMinSlmM,
           java.lang.Integer schedaGeoriferita,
           java.lang.Integer schedaValidata,
           com.hyperborea.sira.ws.Specie specie,
           java.lang.String titoloProgetto,
           java.lang.String ucId,
           com.hyperborea.sira.ws.ValCampiRipSchedeMon[] valCampiRipSchedeMons,
           com.hyperborea.sira.ws.ValCampiSchedeMon[] valCampiSchedeMons,
           com.hyperborea.sira.ws.WsAttivitaEsternaRef wsAttivitaEsternaRef,
           com.hyperborea.sira.ws.WsCcostRef wsCcostRef,
           com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioRef wsTipiSchedaMonitoraggioRef) {
        this.altreProvince = altreProvince;
        this.autore = autore;
        this.committenteProgetto = committenteProgetto;
        this.dataCompilazione = dataCompilazione;
        this.dataRil = dataRil;
        this.esposizioneMaxGradi = esposizioneMaxGradi;
        this.esposizioneMediaGradi = esposizioneMediaGradi;
        this.esposizioneMinGradi = esposizioneMinGradi;
        this.fonte = fonte;
        this.habitat = habitat;
        this.idScheda = idScheda;
        this.inclinazioneMaxGradi = inclinazioneMaxGradi;
        this.inclinazioneMediaGradi = inclinazioneMediaGradi;
        this.inclinazioneMinGradi = inclinazioneMinGradi;
        this.minFineRil = minFineRil;
        this.minInizioRil = minInizioRil;
        this.nomeCompilatore = nomeCompilatore;
        this.numScheda = numScheda;
        this.oggettoNonRilevato = oggettoNonRilevato;
        this.oraFineRil = oraFineRil;
        this.oraInizioRil = oraInizioRil;
        this.proprieta = proprieta;
        this.quotaMaxSlmM = quotaMaxSlmM;
        this.quotaMediaSlmM = quotaMediaSlmM;
        this.quotaMinSlmM = quotaMinSlmM;
        this.schedaGeoriferita = schedaGeoriferita;
        this.schedaValidata = schedaValidata;
        this.specie = specie;
        this.titoloProgetto = titoloProgetto;
        this.ucId = ucId;
        this.valCampiRipSchedeMons = valCampiRipSchedeMons;
        this.valCampiSchedeMons = valCampiSchedeMons;
        this.wsAttivitaEsternaRef = wsAttivitaEsternaRef;
        this.wsCcostRef = wsCcostRef;
        this.wsTipiSchedaMonitoraggioRef = wsTipiSchedaMonitoraggioRef;
    }


    /**
     * Gets the altreProvince value for this SchedaMonitoraggio.
     * 
     * @return altreProvince
     */
    public java.lang.String getAltreProvince() {
        return altreProvince;
    }


    /**
     * Sets the altreProvince value for this SchedaMonitoraggio.
     * 
     * @param altreProvince
     */
    public void setAltreProvince(java.lang.String altreProvince) {
        this.altreProvince = altreProvince;
    }


    /**
     * Gets the autore value for this SchedaMonitoraggio.
     * 
     * @return autore
     */
    public java.lang.String getAutore() {
        return autore;
    }


    /**
     * Sets the autore value for this SchedaMonitoraggio.
     * 
     * @param autore
     */
    public void setAutore(java.lang.String autore) {
        this.autore = autore;
    }


    /**
     * Gets the committenteProgetto value for this SchedaMonitoraggio.
     * 
     * @return committenteProgetto
     */
    public java.lang.String getCommittenteProgetto() {
        return committenteProgetto;
    }


    /**
     * Sets the committenteProgetto value for this SchedaMonitoraggio.
     * 
     * @param committenteProgetto
     */
    public void setCommittenteProgetto(java.lang.String committenteProgetto) {
        this.committenteProgetto = committenteProgetto;
    }


    /**
     * Gets the dataCompilazione value for this SchedaMonitoraggio.
     * 
     * @return dataCompilazione
     */
    public java.util.Calendar getDataCompilazione() {
        return dataCompilazione;
    }


    /**
     * Sets the dataCompilazione value for this SchedaMonitoraggio.
     * 
     * @param dataCompilazione
     */
    public void setDataCompilazione(java.util.Calendar dataCompilazione) {
        this.dataCompilazione = dataCompilazione;
    }


    /**
     * Gets the dataRil value for this SchedaMonitoraggio.
     * 
     * @return dataRil
     */
    public java.util.Calendar getDataRil() {
        return dataRil;
    }


    /**
     * Sets the dataRil value for this SchedaMonitoraggio.
     * 
     * @param dataRil
     */
    public void setDataRil(java.util.Calendar dataRil) {
        this.dataRil = dataRil;
    }


    /**
     * Gets the esposizioneMaxGradi value for this SchedaMonitoraggio.
     * 
     * @return esposizioneMaxGradi
     */
    public java.lang.Double getEsposizioneMaxGradi() {
        return esposizioneMaxGradi;
    }


    /**
     * Sets the esposizioneMaxGradi value for this SchedaMonitoraggio.
     * 
     * @param esposizioneMaxGradi
     */
    public void setEsposizioneMaxGradi(java.lang.Double esposizioneMaxGradi) {
        this.esposizioneMaxGradi = esposizioneMaxGradi;
    }


    /**
     * Gets the esposizioneMediaGradi value for this SchedaMonitoraggio.
     * 
     * @return esposizioneMediaGradi
     */
    public java.lang.Double getEsposizioneMediaGradi() {
        return esposizioneMediaGradi;
    }


    /**
     * Sets the esposizioneMediaGradi value for this SchedaMonitoraggio.
     * 
     * @param esposizioneMediaGradi
     */
    public void setEsposizioneMediaGradi(java.lang.Double esposizioneMediaGradi) {
        this.esposizioneMediaGradi = esposizioneMediaGradi;
    }


    /**
     * Gets the esposizioneMinGradi value for this SchedaMonitoraggio.
     * 
     * @return esposizioneMinGradi
     */
    public java.lang.Double getEsposizioneMinGradi() {
        return esposizioneMinGradi;
    }


    /**
     * Sets the esposizioneMinGradi value for this SchedaMonitoraggio.
     * 
     * @param esposizioneMinGradi
     */
    public void setEsposizioneMinGradi(java.lang.Double esposizioneMinGradi) {
        this.esposizioneMinGradi = esposizioneMinGradi;
    }


    /**
     * Gets the fonte value for this SchedaMonitoraggio.
     * 
     * @return fonte
     */
    public com.hyperborea.sira.ws.VocFonti getFonte() {
        return fonte;
    }


    /**
     * Sets the fonte value for this SchedaMonitoraggio.
     * 
     * @param fonte
     */
    public void setFonte(com.hyperborea.sira.ws.VocFonti fonte) {
        this.fonte = fonte;
    }


    /**
     * Gets the habitat value for this SchedaMonitoraggio.
     * 
     * @return habitat
     */
    public com.hyperborea.sira.ws.Habitat getHabitat() {
        return habitat;
    }


    /**
     * Sets the habitat value for this SchedaMonitoraggio.
     * 
     * @param habitat
     */
    public void setHabitat(com.hyperborea.sira.ws.Habitat habitat) {
        this.habitat = habitat;
    }


    /**
     * Gets the idScheda value for this SchedaMonitoraggio.
     * 
     * @return idScheda
     */
    public java.lang.Integer getIdScheda() {
        return idScheda;
    }


    /**
     * Sets the idScheda value for this SchedaMonitoraggio.
     * 
     * @param idScheda
     */
    public void setIdScheda(java.lang.Integer idScheda) {
        this.idScheda = idScheda;
    }


    /**
     * Gets the inclinazioneMaxGradi value for this SchedaMonitoraggio.
     * 
     * @return inclinazioneMaxGradi
     */
    public java.lang.Double getInclinazioneMaxGradi() {
        return inclinazioneMaxGradi;
    }


    /**
     * Sets the inclinazioneMaxGradi value for this SchedaMonitoraggio.
     * 
     * @param inclinazioneMaxGradi
     */
    public void setInclinazioneMaxGradi(java.lang.Double inclinazioneMaxGradi) {
        this.inclinazioneMaxGradi = inclinazioneMaxGradi;
    }


    /**
     * Gets the inclinazioneMediaGradi value for this SchedaMonitoraggio.
     * 
     * @return inclinazioneMediaGradi
     */
    public java.lang.Double getInclinazioneMediaGradi() {
        return inclinazioneMediaGradi;
    }


    /**
     * Sets the inclinazioneMediaGradi value for this SchedaMonitoraggio.
     * 
     * @param inclinazioneMediaGradi
     */
    public void setInclinazioneMediaGradi(java.lang.Double inclinazioneMediaGradi) {
        this.inclinazioneMediaGradi = inclinazioneMediaGradi;
    }


    /**
     * Gets the inclinazioneMinGradi value for this SchedaMonitoraggio.
     * 
     * @return inclinazioneMinGradi
     */
    public java.lang.Double getInclinazioneMinGradi() {
        return inclinazioneMinGradi;
    }


    /**
     * Sets the inclinazioneMinGradi value for this SchedaMonitoraggio.
     * 
     * @param inclinazioneMinGradi
     */
    public void setInclinazioneMinGradi(java.lang.Double inclinazioneMinGradi) {
        this.inclinazioneMinGradi = inclinazioneMinGradi;
    }


    /**
     * Gets the minFineRil value for this SchedaMonitoraggio.
     * 
     * @return minFineRil
     */
    public java.lang.Integer getMinFineRil() {
        return minFineRil;
    }


    /**
     * Sets the minFineRil value for this SchedaMonitoraggio.
     * 
     * @param minFineRil
     */
    public void setMinFineRil(java.lang.Integer minFineRil) {
        this.minFineRil = minFineRil;
    }


    /**
     * Gets the minInizioRil value for this SchedaMonitoraggio.
     * 
     * @return minInizioRil
     */
    public java.lang.Integer getMinInizioRil() {
        return minInizioRil;
    }


    /**
     * Sets the minInizioRil value for this SchedaMonitoraggio.
     * 
     * @param minInizioRil
     */
    public void setMinInizioRil(java.lang.Integer minInizioRil) {
        this.minInizioRil = minInizioRil;
    }


    /**
     * Gets the nomeCompilatore value for this SchedaMonitoraggio.
     * 
     * @return nomeCompilatore
     */
    public java.lang.String getNomeCompilatore() {
        return nomeCompilatore;
    }


    /**
     * Sets the nomeCompilatore value for this SchedaMonitoraggio.
     * 
     * @param nomeCompilatore
     */
    public void setNomeCompilatore(java.lang.String nomeCompilatore) {
        this.nomeCompilatore = nomeCompilatore;
    }


    /**
     * Gets the numScheda value for this SchedaMonitoraggio.
     * 
     * @return numScheda
     */
    public java.lang.Integer getNumScheda() {
        return numScheda;
    }


    /**
     * Sets the numScheda value for this SchedaMonitoraggio.
     * 
     * @param numScheda
     */
    public void setNumScheda(java.lang.Integer numScheda) {
        this.numScheda = numScheda;
    }


    /**
     * Gets the oggettoNonRilevato value for this SchedaMonitoraggio.
     * 
     * @return oggettoNonRilevato
     */
    public java.lang.Integer getOggettoNonRilevato() {
        return oggettoNonRilevato;
    }


    /**
     * Sets the oggettoNonRilevato value for this SchedaMonitoraggio.
     * 
     * @param oggettoNonRilevato
     */
    public void setOggettoNonRilevato(java.lang.Integer oggettoNonRilevato) {
        this.oggettoNonRilevato = oggettoNonRilevato;
    }


    /**
     * Gets the oraFineRil value for this SchedaMonitoraggio.
     * 
     * @return oraFineRil
     */
    public java.lang.Integer getOraFineRil() {
        return oraFineRil;
    }


    /**
     * Sets the oraFineRil value for this SchedaMonitoraggio.
     * 
     * @param oraFineRil
     */
    public void setOraFineRil(java.lang.Integer oraFineRil) {
        this.oraFineRil = oraFineRil;
    }


    /**
     * Gets the oraInizioRil value for this SchedaMonitoraggio.
     * 
     * @return oraInizioRil
     */
    public java.lang.Integer getOraInizioRil() {
        return oraInizioRil;
    }


    /**
     * Sets the oraInizioRil value for this SchedaMonitoraggio.
     * 
     * @param oraInizioRil
     */
    public void setOraInizioRil(java.lang.Integer oraInizioRil) {
        this.oraInizioRil = oraInizioRil;
    }


    /**
     * Gets the proprieta value for this SchedaMonitoraggio.
     * 
     * @return proprieta
     */
    public com.hyperborea.sira.ws.VocProprieta getProprieta() {
        return proprieta;
    }


    /**
     * Sets the proprieta value for this SchedaMonitoraggio.
     * 
     * @param proprieta
     */
    public void setProprieta(com.hyperborea.sira.ws.VocProprieta proprieta) {
        this.proprieta = proprieta;
    }


    /**
     * Gets the quotaMaxSlmM value for this SchedaMonitoraggio.
     * 
     * @return quotaMaxSlmM
     */
    public java.lang.Double getQuotaMaxSlmM() {
        return quotaMaxSlmM;
    }


    /**
     * Sets the quotaMaxSlmM value for this SchedaMonitoraggio.
     * 
     * @param quotaMaxSlmM
     */
    public void setQuotaMaxSlmM(java.lang.Double quotaMaxSlmM) {
        this.quotaMaxSlmM = quotaMaxSlmM;
    }


    /**
     * Gets the quotaMediaSlmM value for this SchedaMonitoraggio.
     * 
     * @return quotaMediaSlmM
     */
    public java.lang.Double getQuotaMediaSlmM() {
        return quotaMediaSlmM;
    }


    /**
     * Sets the quotaMediaSlmM value for this SchedaMonitoraggio.
     * 
     * @param quotaMediaSlmM
     */
    public void setQuotaMediaSlmM(java.lang.Double quotaMediaSlmM) {
        this.quotaMediaSlmM = quotaMediaSlmM;
    }


    /**
     * Gets the quotaMinSlmM value for this SchedaMonitoraggio.
     * 
     * @return quotaMinSlmM
     */
    public java.lang.Double getQuotaMinSlmM() {
        return quotaMinSlmM;
    }


    /**
     * Sets the quotaMinSlmM value for this SchedaMonitoraggio.
     * 
     * @param quotaMinSlmM
     */
    public void setQuotaMinSlmM(java.lang.Double quotaMinSlmM) {
        this.quotaMinSlmM = quotaMinSlmM;
    }


    /**
     * Gets the schedaGeoriferita value for this SchedaMonitoraggio.
     * 
     * @return schedaGeoriferita
     */
    public java.lang.Integer getSchedaGeoriferita() {
        return schedaGeoriferita;
    }


    /**
     * Sets the schedaGeoriferita value for this SchedaMonitoraggio.
     * 
     * @param schedaGeoriferita
     */
    public void setSchedaGeoriferita(java.lang.Integer schedaGeoriferita) {
        this.schedaGeoriferita = schedaGeoriferita;
    }


    /**
     * Gets the schedaValidata value for this SchedaMonitoraggio.
     * 
     * @return schedaValidata
     */
    public java.lang.Integer getSchedaValidata() {
        return schedaValidata;
    }


    /**
     * Sets the schedaValidata value for this SchedaMonitoraggio.
     * 
     * @param schedaValidata
     */
    public void setSchedaValidata(java.lang.Integer schedaValidata) {
        this.schedaValidata = schedaValidata;
    }


    /**
     * Gets the specie value for this SchedaMonitoraggio.
     * 
     * @return specie
     */
    public com.hyperborea.sira.ws.Specie getSpecie() {
        return specie;
    }


    /**
     * Sets the specie value for this SchedaMonitoraggio.
     * 
     * @param specie
     */
    public void setSpecie(com.hyperborea.sira.ws.Specie specie) {
        this.specie = specie;
    }


    /**
     * Gets the titoloProgetto value for this SchedaMonitoraggio.
     * 
     * @return titoloProgetto
     */
    public java.lang.String getTitoloProgetto() {
        return titoloProgetto;
    }


    /**
     * Sets the titoloProgetto value for this SchedaMonitoraggio.
     * 
     * @param titoloProgetto
     */
    public void setTitoloProgetto(java.lang.String titoloProgetto) {
        this.titoloProgetto = titoloProgetto;
    }


    /**
     * Gets the ucId value for this SchedaMonitoraggio.
     * 
     * @return ucId
     */
    public java.lang.String getUcId() {
        return ucId;
    }


    /**
     * Sets the ucId value for this SchedaMonitoraggio.
     * 
     * @param ucId
     */
    public void setUcId(java.lang.String ucId) {
        this.ucId = ucId;
    }


    /**
     * Gets the valCampiRipSchedeMons value for this SchedaMonitoraggio.
     * 
     * @return valCampiRipSchedeMons
     */
    public com.hyperborea.sira.ws.ValCampiRipSchedeMon[] getValCampiRipSchedeMons() {
        return valCampiRipSchedeMons;
    }


    /**
     * Sets the valCampiRipSchedeMons value for this SchedaMonitoraggio.
     * 
     * @param valCampiRipSchedeMons
     */
    public void setValCampiRipSchedeMons(com.hyperborea.sira.ws.ValCampiRipSchedeMon[] valCampiRipSchedeMons) {
        this.valCampiRipSchedeMons = valCampiRipSchedeMons;
    }

    public com.hyperborea.sira.ws.ValCampiRipSchedeMon getValCampiRipSchedeMons(int i) {
        return this.valCampiRipSchedeMons[i];
    }

    public void setValCampiRipSchedeMons(int i, com.hyperborea.sira.ws.ValCampiRipSchedeMon _value) {
        this.valCampiRipSchedeMons[i] = _value;
    }


    /**
     * Gets the valCampiSchedeMons value for this SchedaMonitoraggio.
     * 
     * @return valCampiSchedeMons
     */
    public com.hyperborea.sira.ws.ValCampiSchedeMon[] getValCampiSchedeMons() {
        return valCampiSchedeMons;
    }


    /**
     * Sets the valCampiSchedeMons value for this SchedaMonitoraggio.
     * 
     * @param valCampiSchedeMons
     */
    public void setValCampiSchedeMons(com.hyperborea.sira.ws.ValCampiSchedeMon[] valCampiSchedeMons) {
        this.valCampiSchedeMons = valCampiSchedeMons;
    }

    public com.hyperborea.sira.ws.ValCampiSchedeMon getValCampiSchedeMons(int i) {
        return this.valCampiSchedeMons[i];
    }

    public void setValCampiSchedeMons(int i, com.hyperborea.sira.ws.ValCampiSchedeMon _value) {
        this.valCampiSchedeMons[i] = _value;
    }


    /**
     * Gets the wsAttivitaEsternaRef value for this SchedaMonitoraggio.
     * 
     * @return wsAttivitaEsternaRef
     */
    public com.hyperborea.sira.ws.WsAttivitaEsternaRef getWsAttivitaEsternaRef() {
        return wsAttivitaEsternaRef;
    }


    /**
     * Sets the wsAttivitaEsternaRef value for this SchedaMonitoraggio.
     * 
     * @param wsAttivitaEsternaRef
     */
    public void setWsAttivitaEsternaRef(com.hyperborea.sira.ws.WsAttivitaEsternaRef wsAttivitaEsternaRef) {
        this.wsAttivitaEsternaRef = wsAttivitaEsternaRef;
    }


    /**
     * Gets the wsCcostRef value for this SchedaMonitoraggio.
     * 
     * @return wsCcostRef
     */
    public com.hyperborea.sira.ws.WsCcostRef getWsCcostRef() {
        return wsCcostRef;
    }


    /**
     * Sets the wsCcostRef value for this SchedaMonitoraggio.
     * 
     * @param wsCcostRef
     */
    public void setWsCcostRef(com.hyperborea.sira.ws.WsCcostRef wsCcostRef) {
        this.wsCcostRef = wsCcostRef;
    }


    /**
     * Gets the wsTipiSchedaMonitoraggioRef value for this SchedaMonitoraggio.
     * 
     * @return wsTipiSchedaMonitoraggioRef
     */
    public com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioRef getWsTipiSchedaMonitoraggioRef() {
        return wsTipiSchedaMonitoraggioRef;
    }


    /**
     * Sets the wsTipiSchedaMonitoraggioRef value for this SchedaMonitoraggio.
     * 
     * @param wsTipiSchedaMonitoraggioRef
     */
    public void setWsTipiSchedaMonitoraggioRef(com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioRef wsTipiSchedaMonitoraggioRef) {
        this.wsTipiSchedaMonitoraggioRef = wsTipiSchedaMonitoraggioRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SchedaMonitoraggio)) return false;
        SchedaMonitoraggio other = (SchedaMonitoraggio) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.altreProvince==null && other.getAltreProvince()==null) || 
             (this.altreProvince!=null &&
              this.altreProvince.equals(other.getAltreProvince()))) &&
            ((this.autore==null && other.getAutore()==null) || 
             (this.autore!=null &&
              this.autore.equals(other.getAutore()))) &&
            ((this.committenteProgetto==null && other.getCommittenteProgetto()==null) || 
             (this.committenteProgetto!=null &&
              this.committenteProgetto.equals(other.getCommittenteProgetto()))) &&
            ((this.dataCompilazione==null && other.getDataCompilazione()==null) || 
             (this.dataCompilazione!=null &&
              this.dataCompilazione.equals(other.getDataCompilazione()))) &&
            ((this.dataRil==null && other.getDataRil()==null) || 
             (this.dataRil!=null &&
              this.dataRil.equals(other.getDataRil()))) &&
            ((this.esposizioneMaxGradi==null && other.getEsposizioneMaxGradi()==null) || 
             (this.esposizioneMaxGradi!=null &&
              this.esposizioneMaxGradi.equals(other.getEsposizioneMaxGradi()))) &&
            ((this.esposizioneMediaGradi==null && other.getEsposizioneMediaGradi()==null) || 
             (this.esposizioneMediaGradi!=null &&
              this.esposizioneMediaGradi.equals(other.getEsposizioneMediaGradi()))) &&
            ((this.esposizioneMinGradi==null && other.getEsposizioneMinGradi()==null) || 
             (this.esposizioneMinGradi!=null &&
              this.esposizioneMinGradi.equals(other.getEsposizioneMinGradi()))) &&
            ((this.fonte==null && other.getFonte()==null) || 
             (this.fonte!=null &&
              this.fonte.equals(other.getFonte()))) &&
            ((this.habitat==null && other.getHabitat()==null) || 
             (this.habitat!=null &&
              this.habitat.equals(other.getHabitat()))) &&
            ((this.idScheda==null && other.getIdScheda()==null) || 
             (this.idScheda!=null &&
              this.idScheda.equals(other.getIdScheda()))) &&
            ((this.inclinazioneMaxGradi==null && other.getInclinazioneMaxGradi()==null) || 
             (this.inclinazioneMaxGradi!=null &&
              this.inclinazioneMaxGradi.equals(other.getInclinazioneMaxGradi()))) &&
            ((this.inclinazioneMediaGradi==null && other.getInclinazioneMediaGradi()==null) || 
             (this.inclinazioneMediaGradi!=null &&
              this.inclinazioneMediaGradi.equals(other.getInclinazioneMediaGradi()))) &&
            ((this.inclinazioneMinGradi==null && other.getInclinazioneMinGradi()==null) || 
             (this.inclinazioneMinGradi!=null &&
              this.inclinazioneMinGradi.equals(other.getInclinazioneMinGradi()))) &&
            ((this.minFineRil==null && other.getMinFineRil()==null) || 
             (this.minFineRil!=null &&
              this.minFineRil.equals(other.getMinFineRil()))) &&
            ((this.minInizioRil==null && other.getMinInizioRil()==null) || 
             (this.minInizioRil!=null &&
              this.minInizioRil.equals(other.getMinInizioRil()))) &&
            ((this.nomeCompilatore==null && other.getNomeCompilatore()==null) || 
             (this.nomeCompilatore!=null &&
              this.nomeCompilatore.equals(other.getNomeCompilatore()))) &&
            ((this.numScheda==null && other.getNumScheda()==null) || 
             (this.numScheda!=null &&
              this.numScheda.equals(other.getNumScheda()))) &&
            ((this.oggettoNonRilevato==null && other.getOggettoNonRilevato()==null) || 
             (this.oggettoNonRilevato!=null &&
              this.oggettoNonRilevato.equals(other.getOggettoNonRilevato()))) &&
            ((this.oraFineRil==null && other.getOraFineRil()==null) || 
             (this.oraFineRil!=null &&
              this.oraFineRil.equals(other.getOraFineRil()))) &&
            ((this.oraInizioRil==null && other.getOraInizioRil()==null) || 
             (this.oraInizioRil!=null &&
              this.oraInizioRil.equals(other.getOraInizioRil()))) &&
            ((this.proprieta==null && other.getProprieta()==null) || 
             (this.proprieta!=null &&
              this.proprieta.equals(other.getProprieta()))) &&
            ((this.quotaMaxSlmM==null && other.getQuotaMaxSlmM()==null) || 
             (this.quotaMaxSlmM!=null &&
              this.quotaMaxSlmM.equals(other.getQuotaMaxSlmM()))) &&
            ((this.quotaMediaSlmM==null && other.getQuotaMediaSlmM()==null) || 
             (this.quotaMediaSlmM!=null &&
              this.quotaMediaSlmM.equals(other.getQuotaMediaSlmM()))) &&
            ((this.quotaMinSlmM==null && other.getQuotaMinSlmM()==null) || 
             (this.quotaMinSlmM!=null &&
              this.quotaMinSlmM.equals(other.getQuotaMinSlmM()))) &&
            ((this.schedaGeoriferita==null && other.getSchedaGeoriferita()==null) || 
             (this.schedaGeoriferita!=null &&
              this.schedaGeoriferita.equals(other.getSchedaGeoriferita()))) &&
            ((this.schedaValidata==null && other.getSchedaValidata()==null) || 
             (this.schedaValidata!=null &&
              this.schedaValidata.equals(other.getSchedaValidata()))) &&
            ((this.specie==null && other.getSpecie()==null) || 
             (this.specie!=null &&
              this.specie.equals(other.getSpecie()))) &&
            ((this.titoloProgetto==null && other.getTitoloProgetto()==null) || 
             (this.titoloProgetto!=null &&
              this.titoloProgetto.equals(other.getTitoloProgetto()))) &&
            ((this.ucId==null && other.getUcId()==null) || 
             (this.ucId!=null &&
              this.ucId.equals(other.getUcId()))) &&
            ((this.valCampiRipSchedeMons==null && other.getValCampiRipSchedeMons()==null) || 
             (this.valCampiRipSchedeMons!=null &&
              java.util.Arrays.equals(this.valCampiRipSchedeMons, other.getValCampiRipSchedeMons()))) &&
            ((this.valCampiSchedeMons==null && other.getValCampiSchedeMons()==null) || 
             (this.valCampiSchedeMons!=null &&
              java.util.Arrays.equals(this.valCampiSchedeMons, other.getValCampiSchedeMons()))) &&
            ((this.wsAttivitaEsternaRef==null && other.getWsAttivitaEsternaRef()==null) || 
             (this.wsAttivitaEsternaRef!=null &&
              this.wsAttivitaEsternaRef.equals(other.getWsAttivitaEsternaRef()))) &&
            ((this.wsCcostRef==null && other.getWsCcostRef()==null) || 
             (this.wsCcostRef!=null &&
              this.wsCcostRef.equals(other.getWsCcostRef()))) &&
            ((this.wsTipiSchedaMonitoraggioRef==null && other.getWsTipiSchedaMonitoraggioRef()==null) || 
             (this.wsTipiSchedaMonitoraggioRef!=null &&
              this.wsTipiSchedaMonitoraggioRef.equals(other.getWsTipiSchedaMonitoraggioRef())));
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
        if (getAltreProvince() != null) {
            _hashCode += getAltreProvince().hashCode();
        }
        if (getAutore() != null) {
            _hashCode += getAutore().hashCode();
        }
        if (getCommittenteProgetto() != null) {
            _hashCode += getCommittenteProgetto().hashCode();
        }
        if (getDataCompilazione() != null) {
            _hashCode += getDataCompilazione().hashCode();
        }
        if (getDataRil() != null) {
            _hashCode += getDataRil().hashCode();
        }
        if (getEsposizioneMaxGradi() != null) {
            _hashCode += getEsposizioneMaxGradi().hashCode();
        }
        if (getEsposizioneMediaGradi() != null) {
            _hashCode += getEsposizioneMediaGradi().hashCode();
        }
        if (getEsposizioneMinGradi() != null) {
            _hashCode += getEsposizioneMinGradi().hashCode();
        }
        if (getFonte() != null) {
            _hashCode += getFonte().hashCode();
        }
        if (getHabitat() != null) {
            _hashCode += getHabitat().hashCode();
        }
        if (getIdScheda() != null) {
            _hashCode += getIdScheda().hashCode();
        }
        if (getInclinazioneMaxGradi() != null) {
            _hashCode += getInclinazioneMaxGradi().hashCode();
        }
        if (getInclinazioneMediaGradi() != null) {
            _hashCode += getInclinazioneMediaGradi().hashCode();
        }
        if (getInclinazioneMinGradi() != null) {
            _hashCode += getInclinazioneMinGradi().hashCode();
        }
        if (getMinFineRil() != null) {
            _hashCode += getMinFineRil().hashCode();
        }
        if (getMinInizioRil() != null) {
            _hashCode += getMinInizioRil().hashCode();
        }
        if (getNomeCompilatore() != null) {
            _hashCode += getNomeCompilatore().hashCode();
        }
        if (getNumScheda() != null) {
            _hashCode += getNumScheda().hashCode();
        }
        if (getOggettoNonRilevato() != null) {
            _hashCode += getOggettoNonRilevato().hashCode();
        }
        if (getOraFineRil() != null) {
            _hashCode += getOraFineRil().hashCode();
        }
        if (getOraInizioRil() != null) {
            _hashCode += getOraInizioRil().hashCode();
        }
        if (getProprieta() != null) {
            _hashCode += getProprieta().hashCode();
        }
        if (getQuotaMaxSlmM() != null) {
            _hashCode += getQuotaMaxSlmM().hashCode();
        }
        if (getQuotaMediaSlmM() != null) {
            _hashCode += getQuotaMediaSlmM().hashCode();
        }
        if (getQuotaMinSlmM() != null) {
            _hashCode += getQuotaMinSlmM().hashCode();
        }
        if (getSchedaGeoriferita() != null) {
            _hashCode += getSchedaGeoriferita().hashCode();
        }
        if (getSchedaValidata() != null) {
            _hashCode += getSchedaValidata().hashCode();
        }
        if (getSpecie() != null) {
            _hashCode += getSpecie().hashCode();
        }
        if (getTitoloProgetto() != null) {
            _hashCode += getTitoloProgetto().hashCode();
        }
        if (getUcId() != null) {
            _hashCode += getUcId().hashCode();
        }
        if (getValCampiRipSchedeMons() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getValCampiRipSchedeMons());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getValCampiRipSchedeMons(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getValCampiSchedeMons() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getValCampiSchedeMons());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getValCampiSchedeMons(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getWsAttivitaEsternaRef() != null) {
            _hashCode += getWsAttivitaEsternaRef().hashCode();
        }
        if (getWsCcostRef() != null) {
            _hashCode += getWsCcostRef().hashCode();
        }
        if (getWsTipiSchedaMonitoraggioRef() != null) {
            _hashCode += getWsTipiSchedaMonitoraggioRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SchedaMonitoraggio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "schedaMonitoraggio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altreProvince");
        elemField.setXmlName(new javax.xml.namespace.QName("", "altreProvince"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "autore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("committenteProgetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "committenteProgetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataCompilazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataCompilazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataRil");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataRil"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esposizioneMaxGradi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "esposizioneMaxGradi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esposizioneMediaGradi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "esposizioneMediaGradi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esposizioneMinGradi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "esposizioneMinGradi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fonte");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fonte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocFonti"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("habitat");
        elemField.setXmlName(new javax.xml.namespace.QName("", "habitat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "habitat"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idScheda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idScheda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inclinazioneMaxGradi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inclinazioneMaxGradi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inclinazioneMediaGradi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inclinazioneMediaGradi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inclinazioneMinGradi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inclinazioneMinGradi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minFineRil");
        elemField.setXmlName(new javax.xml.namespace.QName("", "minFineRil"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minInizioRil");
        elemField.setXmlName(new javax.xml.namespace.QName("", "minInizioRil"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeCompilatore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeCompilatore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numScheda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numScheda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oggettoNonRilevato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oggettoNonRilevato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oraFineRil");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oraFineRil"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oraInizioRil");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oraInizioRil"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("proprieta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "proprieta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocProprieta"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quotaMaxSlmM");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quotaMaxSlmM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quotaMediaSlmM");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quotaMediaSlmM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quotaMinSlmM");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quotaMinSlmM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schedaGeoriferita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "schedaGeoriferita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schedaValidata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "schedaValidata"));
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
        elemField.setFieldName("titoloProgetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "titoloProgetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ucId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ucId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valCampiRipSchedeMons");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valCampiRipSchedeMons"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "valCampiRipSchedeMon"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valCampiSchedeMons");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valCampiSchedeMons"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "valCampiSchedeMon"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsAttivitaEsternaRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsAttivitaEsternaRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsAttivitaEsternaRef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsCcostRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsCcostRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCcostRef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsTipiSchedaMonitoraggioRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsTipiSchedaMonitoraggioRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTipiSchedaMonitoraggioRef"));
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
