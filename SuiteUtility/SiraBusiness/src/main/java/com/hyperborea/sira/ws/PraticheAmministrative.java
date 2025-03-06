/**
 * PraticheAmministrative.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class PraticheAmministrative  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.Allegati[] allegatis;

    private java.lang.Integer annoPa;

    private com.hyperborea.sira.ws.AttiDisposizioniAmm attiDisposizioniAmm;

    private com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst;

    private com.hyperborea.sira.ws.CaratterizzazioniOst[] caratterizzazioniOsts;

    private java.util.Calendar dataAvvio;

    private java.util.Calendar dataChiusura;

    private java.util.Calendar dataModStato;

    private java.util.Calendar dataProtChiusura;

    private java.util.Calendar dataProtRichiesta;

    private java.util.Calendar dataScadenza;

    private java.lang.String diIniziativa;

    private java.lang.String diParte;

    private com.hyperborea.sira.ws.DichiarazioniAmbientali dichiarazioniAmbientali;

    private com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGoverno;

    private com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGovernoForEcgRichiedenteParere;

    private com.hyperborea.sira.ws.FasiProcedimenti fasiProcedimenti;

    private java.lang.Integer idPa;

    private com.hyperborea.sira.ws.Misure[] misures;

    private com.hyperborea.sira.ws.TipologieMotivazioni motivazione;

    private java.lang.String note;

    private java.lang.String numeroPa;

    private java.lang.String oggettoPa;

    private com.hyperborea.sira.ws.PaAttivita[] paAttivitas;

    private com.hyperborea.sira.ws.PaOst[] paOsts;

    private com.hyperborea.sira.ws.PaUo[] paUos;

    private java.lang.String parere;

    private java.lang.String prescrizioni;

    private java.lang.String protDocChiusura;

    private java.lang.String protRichiesta;

    private java.lang.String riservataArpa;

    private com.hyperborea.sira.ws.StatiProcedimento statiProcedimento;

    private java.lang.String tipoRichiedente;

    private com.hyperborea.sira.ws.TipologieEsiti tipologieEsiti;

    private com.hyperborea.sira.ws.TipologieFunzioni tipologieFunzioni;

    private com.hyperborea.sira.ws.Utenti utenteModStato;

    private com.hyperborea.sira.ws.Personale utenti;

    public PraticheAmministrative() {
    }

    public PraticheAmministrative(
           com.hyperborea.sira.ws.Allegati[] allegatis,
           java.lang.Integer annoPa,
           com.hyperborea.sira.ws.AttiDisposizioniAmm attiDisposizioniAmm,
           com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst,
           com.hyperborea.sira.ws.CaratterizzazioniOst[] caratterizzazioniOsts,
           java.util.Calendar dataAvvio,
           java.util.Calendar dataChiusura,
           java.util.Calendar dataModStato,
           java.util.Calendar dataProtChiusura,
           java.util.Calendar dataProtRichiesta,
           java.util.Calendar dataScadenza,
           java.lang.String diIniziativa,
           java.lang.String diParte,
           com.hyperborea.sira.ws.DichiarazioniAmbientali dichiarazioniAmbientali,
           com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGoverno,
           com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGovernoForEcgRichiedenteParere,
           com.hyperborea.sira.ws.FasiProcedimenti fasiProcedimenti,
           java.lang.Integer idPa,
           com.hyperborea.sira.ws.Misure[] misures,
           com.hyperborea.sira.ws.TipologieMotivazioni motivazione,
           java.lang.String note,
           java.lang.String numeroPa,
           java.lang.String oggettoPa,
           com.hyperborea.sira.ws.PaAttivita[] paAttivitas,
           com.hyperborea.sira.ws.PaOst[] paOsts,
           com.hyperborea.sira.ws.PaUo[] paUos,
           java.lang.String parere,
           java.lang.String prescrizioni,
           java.lang.String protDocChiusura,
           java.lang.String protRichiesta,
           java.lang.String riservataArpa,
           com.hyperborea.sira.ws.StatiProcedimento statiProcedimento,
           java.lang.String tipoRichiedente,
           com.hyperborea.sira.ws.TipologieEsiti tipologieEsiti,
           com.hyperborea.sira.ws.TipologieFunzioni tipologieFunzioni,
           com.hyperborea.sira.ws.Utenti utenteModStato,
           com.hyperborea.sira.ws.Personale utenti) {
        this.allegatis = allegatis;
        this.annoPa = annoPa;
        this.attiDisposizioniAmm = attiDisposizioniAmm;
        this.caratterizzazioniOst = caratterizzazioniOst;
        this.caratterizzazioniOsts = caratterizzazioniOsts;
        this.dataAvvio = dataAvvio;
        this.dataChiusura = dataChiusura;
        this.dataModStato = dataModStato;
        this.dataProtChiusura = dataProtChiusura;
        this.dataProtRichiesta = dataProtRichiesta;
        this.dataScadenza = dataScadenza;
        this.diIniziativa = diIniziativa;
        this.diParte = diParte;
        this.dichiarazioniAmbientali = dichiarazioniAmbientali;
        this.entiControlloGoverno = entiControlloGoverno;
        this.entiControlloGovernoForEcgRichiedenteParere = entiControlloGovernoForEcgRichiedenteParere;
        this.fasiProcedimenti = fasiProcedimenti;
        this.idPa = idPa;
        this.misures = misures;
        this.motivazione = motivazione;
        this.note = note;
        this.numeroPa = numeroPa;
        this.oggettoPa = oggettoPa;
        this.paAttivitas = paAttivitas;
        this.paOsts = paOsts;
        this.paUos = paUos;
        this.parere = parere;
        this.prescrizioni = prescrizioni;
        this.protDocChiusura = protDocChiusura;
        this.protRichiesta = protRichiesta;
        this.riservataArpa = riservataArpa;
        this.statiProcedimento = statiProcedimento;
        this.tipoRichiedente = tipoRichiedente;
        this.tipologieEsiti = tipologieEsiti;
        this.tipologieFunzioni = tipologieFunzioni;
        this.utenteModStato = utenteModStato;
        this.utenti = utenti;
    }


    /**
     * Gets the allegatis value for this PraticheAmministrative.
     * 
     * @return allegatis
     */
    public com.hyperborea.sira.ws.Allegati[] getAllegatis() {
        return allegatis;
    }


    /**
     * Sets the allegatis value for this PraticheAmministrative.
     * 
     * @param allegatis
     */
    public void setAllegatis(com.hyperborea.sira.ws.Allegati[] allegatis) {
        this.allegatis = allegatis;
    }

    public com.hyperborea.sira.ws.Allegati getAllegatis(int i) {
        return this.allegatis[i];
    }

    public void setAllegatis(int i, com.hyperborea.sira.ws.Allegati _value) {
        this.allegatis[i] = _value;
    }


    /**
     * Gets the annoPa value for this PraticheAmministrative.
     * 
     * @return annoPa
     */
    public java.lang.Integer getAnnoPa() {
        return annoPa;
    }


    /**
     * Sets the annoPa value for this PraticheAmministrative.
     * 
     * @param annoPa
     */
    public void setAnnoPa(java.lang.Integer annoPa) {
        this.annoPa = annoPa;
    }


    /**
     * Gets the attiDisposizioniAmm value for this PraticheAmministrative.
     * 
     * @return attiDisposizioniAmm
     */
    public com.hyperborea.sira.ws.AttiDisposizioniAmm getAttiDisposizioniAmm() {
        return attiDisposizioniAmm;
    }


    /**
     * Sets the attiDisposizioniAmm value for this PraticheAmministrative.
     * 
     * @param attiDisposizioniAmm
     */
    public void setAttiDisposizioniAmm(com.hyperborea.sira.ws.AttiDisposizioniAmm attiDisposizioniAmm) {
        this.attiDisposizioniAmm = attiDisposizioniAmm;
    }


    /**
     * Gets the caratterizzazioniOst value for this PraticheAmministrative.
     * 
     * @return caratterizzazioniOst
     */
    public com.hyperborea.sira.ws.CaratterizzazioniOst getCaratterizzazioniOst() {
        return caratterizzazioniOst;
    }


    /**
     * Sets the caratterizzazioniOst value for this PraticheAmministrative.
     * 
     * @param caratterizzazioniOst
     */
    public void setCaratterizzazioniOst(com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst) {
        this.caratterizzazioniOst = caratterizzazioniOst;
    }


    /**
     * Gets the caratterizzazioniOsts value for this PraticheAmministrative.
     * 
     * @return caratterizzazioniOsts
     */
    public com.hyperborea.sira.ws.CaratterizzazioniOst[] getCaratterizzazioniOsts() {
        return caratterizzazioniOsts;
    }


    /**
     * Sets the caratterizzazioniOsts value for this PraticheAmministrative.
     * 
     * @param caratterizzazioniOsts
     */
    public void setCaratterizzazioniOsts(com.hyperborea.sira.ws.CaratterizzazioniOst[] caratterizzazioniOsts) {
        this.caratterizzazioniOsts = caratterizzazioniOsts;
    }

    public com.hyperborea.sira.ws.CaratterizzazioniOst getCaratterizzazioniOsts(int i) {
        return this.caratterizzazioniOsts[i];
    }

    public void setCaratterizzazioniOsts(int i, com.hyperborea.sira.ws.CaratterizzazioniOst _value) {
        this.caratterizzazioniOsts[i] = _value;
    }


    /**
     * Gets the dataAvvio value for this PraticheAmministrative.
     * 
     * @return dataAvvio
     */
    public java.util.Calendar getDataAvvio() {
        return dataAvvio;
    }


    /**
     * Sets the dataAvvio value for this PraticheAmministrative.
     * 
     * @param dataAvvio
     */
    public void setDataAvvio(java.util.Calendar dataAvvio) {
        this.dataAvvio = dataAvvio;
    }


    /**
     * Gets the dataChiusura value for this PraticheAmministrative.
     * 
     * @return dataChiusura
     */
    public java.util.Calendar getDataChiusura() {
        return dataChiusura;
    }


    /**
     * Sets the dataChiusura value for this PraticheAmministrative.
     * 
     * @param dataChiusura
     */
    public void setDataChiusura(java.util.Calendar dataChiusura) {
        this.dataChiusura = dataChiusura;
    }


    /**
     * Gets the dataModStato value for this PraticheAmministrative.
     * 
     * @return dataModStato
     */
    public java.util.Calendar getDataModStato() {
        return dataModStato;
    }


    /**
     * Sets the dataModStato value for this PraticheAmministrative.
     * 
     * @param dataModStato
     */
    public void setDataModStato(java.util.Calendar dataModStato) {
        this.dataModStato = dataModStato;
    }


    /**
     * Gets the dataProtChiusura value for this PraticheAmministrative.
     * 
     * @return dataProtChiusura
     */
    public java.util.Calendar getDataProtChiusura() {
        return dataProtChiusura;
    }


    /**
     * Sets the dataProtChiusura value for this PraticheAmministrative.
     * 
     * @param dataProtChiusura
     */
    public void setDataProtChiusura(java.util.Calendar dataProtChiusura) {
        this.dataProtChiusura = dataProtChiusura;
    }


    /**
     * Gets the dataProtRichiesta value for this PraticheAmministrative.
     * 
     * @return dataProtRichiesta
     */
    public java.util.Calendar getDataProtRichiesta() {
        return dataProtRichiesta;
    }


    /**
     * Sets the dataProtRichiesta value for this PraticheAmministrative.
     * 
     * @param dataProtRichiesta
     */
    public void setDataProtRichiesta(java.util.Calendar dataProtRichiesta) {
        this.dataProtRichiesta = dataProtRichiesta;
    }


    /**
     * Gets the dataScadenza value for this PraticheAmministrative.
     * 
     * @return dataScadenza
     */
    public java.util.Calendar getDataScadenza() {
        return dataScadenza;
    }


    /**
     * Sets the dataScadenza value for this PraticheAmministrative.
     * 
     * @param dataScadenza
     */
    public void setDataScadenza(java.util.Calendar dataScadenza) {
        this.dataScadenza = dataScadenza;
    }


    /**
     * Gets the diIniziativa value for this PraticheAmministrative.
     * 
     * @return diIniziativa
     */
    public java.lang.String getDiIniziativa() {
        return diIniziativa;
    }


    /**
     * Sets the diIniziativa value for this PraticheAmministrative.
     * 
     * @param diIniziativa
     */
    public void setDiIniziativa(java.lang.String diIniziativa) {
        this.diIniziativa = diIniziativa;
    }


    /**
     * Gets the diParte value for this PraticheAmministrative.
     * 
     * @return diParte
     */
    public java.lang.String getDiParte() {
        return diParte;
    }


    /**
     * Sets the diParte value for this PraticheAmministrative.
     * 
     * @param diParte
     */
    public void setDiParte(java.lang.String diParte) {
        this.diParte = diParte;
    }


    /**
     * Gets the dichiarazioniAmbientali value for this PraticheAmministrative.
     * 
     * @return dichiarazioniAmbientali
     */
    public com.hyperborea.sira.ws.DichiarazioniAmbientali getDichiarazioniAmbientali() {
        return dichiarazioniAmbientali;
    }


    /**
     * Sets the dichiarazioniAmbientali value for this PraticheAmministrative.
     * 
     * @param dichiarazioniAmbientali
     */
    public void setDichiarazioniAmbientali(com.hyperborea.sira.ws.DichiarazioniAmbientali dichiarazioniAmbientali) {
        this.dichiarazioniAmbientali = dichiarazioniAmbientali;
    }


    /**
     * Gets the entiControlloGoverno value for this PraticheAmministrative.
     * 
     * @return entiControlloGoverno
     */
    public com.hyperborea.sira.ws.EntiControlloGoverno getEntiControlloGoverno() {
        return entiControlloGoverno;
    }


    /**
     * Sets the entiControlloGoverno value for this PraticheAmministrative.
     * 
     * @param entiControlloGoverno
     */
    public void setEntiControlloGoverno(com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGoverno) {
        this.entiControlloGoverno = entiControlloGoverno;
    }


    /**
     * Gets the entiControlloGovernoForEcgRichiedenteParere value for this PraticheAmministrative.
     * 
     * @return entiControlloGovernoForEcgRichiedenteParere
     */
    public com.hyperborea.sira.ws.EntiControlloGoverno getEntiControlloGovernoForEcgRichiedenteParere() {
        return entiControlloGovernoForEcgRichiedenteParere;
    }


    /**
     * Sets the entiControlloGovernoForEcgRichiedenteParere value for this PraticheAmministrative.
     * 
     * @param entiControlloGovernoForEcgRichiedenteParere
     */
    public void setEntiControlloGovernoForEcgRichiedenteParere(com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGovernoForEcgRichiedenteParere) {
        this.entiControlloGovernoForEcgRichiedenteParere = entiControlloGovernoForEcgRichiedenteParere;
    }


    /**
     * Gets the fasiProcedimenti value for this PraticheAmministrative.
     * 
     * @return fasiProcedimenti
     */
    public com.hyperborea.sira.ws.FasiProcedimenti getFasiProcedimenti() {
        return fasiProcedimenti;
    }


    /**
     * Sets the fasiProcedimenti value for this PraticheAmministrative.
     * 
     * @param fasiProcedimenti
     */
    public void setFasiProcedimenti(com.hyperborea.sira.ws.FasiProcedimenti fasiProcedimenti) {
        this.fasiProcedimenti = fasiProcedimenti;
    }


    /**
     * Gets the idPa value for this PraticheAmministrative.
     * 
     * @return idPa
     */
    public java.lang.Integer getIdPa() {
        return idPa;
    }


    /**
     * Sets the idPa value for this PraticheAmministrative.
     * 
     * @param idPa
     */
    public void setIdPa(java.lang.Integer idPa) {
        this.idPa = idPa;
    }


    /**
     * Gets the misures value for this PraticheAmministrative.
     * 
     * @return misures
     */
    public com.hyperborea.sira.ws.Misure[] getMisures() {
        return misures;
    }


    /**
     * Sets the misures value for this PraticheAmministrative.
     * 
     * @param misures
     */
    public void setMisures(com.hyperborea.sira.ws.Misure[] misures) {
        this.misures = misures;
    }

    public com.hyperborea.sira.ws.Misure getMisures(int i) {
        return this.misures[i];
    }

    public void setMisures(int i, com.hyperborea.sira.ws.Misure _value) {
        this.misures[i] = _value;
    }


    /**
     * Gets the motivazione value for this PraticheAmministrative.
     * 
     * @return motivazione
     */
    public com.hyperborea.sira.ws.TipologieMotivazioni getMotivazione() {
        return motivazione;
    }


    /**
     * Sets the motivazione value for this PraticheAmministrative.
     * 
     * @param motivazione
     */
    public void setMotivazione(com.hyperborea.sira.ws.TipologieMotivazioni motivazione) {
        this.motivazione = motivazione;
    }


    /**
     * Gets the note value for this PraticheAmministrative.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this PraticheAmministrative.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the numeroPa value for this PraticheAmministrative.
     * 
     * @return numeroPa
     */
    public java.lang.String getNumeroPa() {
        return numeroPa;
    }


    /**
     * Sets the numeroPa value for this PraticheAmministrative.
     * 
     * @param numeroPa
     */
    public void setNumeroPa(java.lang.String numeroPa) {
        this.numeroPa = numeroPa;
    }


    /**
     * Gets the oggettoPa value for this PraticheAmministrative.
     * 
     * @return oggettoPa
     */
    public java.lang.String getOggettoPa() {
        return oggettoPa;
    }


    /**
     * Sets the oggettoPa value for this PraticheAmministrative.
     * 
     * @param oggettoPa
     */
    public void setOggettoPa(java.lang.String oggettoPa) {
        this.oggettoPa = oggettoPa;
    }


    /**
     * Gets the paAttivitas value for this PraticheAmministrative.
     * 
     * @return paAttivitas
     */
    public com.hyperborea.sira.ws.PaAttivita[] getPaAttivitas() {
        return paAttivitas;
    }


    /**
     * Sets the paAttivitas value for this PraticheAmministrative.
     * 
     * @param paAttivitas
     */
    public void setPaAttivitas(com.hyperborea.sira.ws.PaAttivita[] paAttivitas) {
        this.paAttivitas = paAttivitas;
    }

    public com.hyperborea.sira.ws.PaAttivita getPaAttivitas(int i) {
        return this.paAttivitas[i];
    }

    public void setPaAttivitas(int i, com.hyperborea.sira.ws.PaAttivita _value) {
        this.paAttivitas[i] = _value;
    }


    /**
     * Gets the paOsts value for this PraticheAmministrative.
     * 
     * @return paOsts
     */
    public com.hyperborea.sira.ws.PaOst[] getPaOsts() {
        return paOsts;
    }


    /**
     * Sets the paOsts value for this PraticheAmministrative.
     * 
     * @param paOsts
     */
    public void setPaOsts(com.hyperborea.sira.ws.PaOst[] paOsts) {
        this.paOsts = paOsts;
    }

    public com.hyperborea.sira.ws.PaOst getPaOsts(int i) {
        return this.paOsts[i];
    }

    public void setPaOsts(int i, com.hyperborea.sira.ws.PaOst _value) {
        this.paOsts[i] = _value;
    }


    /**
     * Gets the paUos value for this PraticheAmministrative.
     * 
     * @return paUos
     */
    public com.hyperborea.sira.ws.PaUo[] getPaUos() {
        return paUos;
    }


    /**
     * Sets the paUos value for this PraticheAmministrative.
     * 
     * @param paUos
     */
    public void setPaUos(com.hyperborea.sira.ws.PaUo[] paUos) {
        this.paUos = paUos;
    }

    public com.hyperborea.sira.ws.PaUo getPaUos(int i) {
        return this.paUos[i];
    }

    public void setPaUos(int i, com.hyperborea.sira.ws.PaUo _value) {
        this.paUos[i] = _value;
    }


    /**
     * Gets the parere value for this PraticheAmministrative.
     * 
     * @return parere
     */
    public java.lang.String getParere() {
        return parere;
    }


    /**
     * Sets the parere value for this PraticheAmministrative.
     * 
     * @param parere
     */
    public void setParere(java.lang.String parere) {
        this.parere = parere;
    }


    /**
     * Gets the prescrizioni value for this PraticheAmministrative.
     * 
     * @return prescrizioni
     */
    public java.lang.String getPrescrizioni() {
        return prescrizioni;
    }


    /**
     * Sets the prescrizioni value for this PraticheAmministrative.
     * 
     * @param prescrizioni
     */
    public void setPrescrizioni(java.lang.String prescrizioni) {
        this.prescrizioni = prescrizioni;
    }


    /**
     * Gets the protDocChiusura value for this PraticheAmministrative.
     * 
     * @return protDocChiusura
     */
    public java.lang.String getProtDocChiusura() {
        return protDocChiusura;
    }


    /**
     * Sets the protDocChiusura value for this PraticheAmministrative.
     * 
     * @param protDocChiusura
     */
    public void setProtDocChiusura(java.lang.String protDocChiusura) {
        this.protDocChiusura = protDocChiusura;
    }


    /**
     * Gets the protRichiesta value for this PraticheAmministrative.
     * 
     * @return protRichiesta
     */
    public java.lang.String getProtRichiesta() {
        return protRichiesta;
    }


    /**
     * Sets the protRichiesta value for this PraticheAmministrative.
     * 
     * @param protRichiesta
     */
    public void setProtRichiesta(java.lang.String protRichiesta) {
        this.protRichiesta = protRichiesta;
    }


    /**
     * Gets the riservataArpa value for this PraticheAmministrative.
     * 
     * @return riservataArpa
     */
    public java.lang.String getRiservataArpa() {
        return riservataArpa;
    }


    /**
     * Sets the riservataArpa value for this PraticheAmministrative.
     * 
     * @param riservataArpa
     */
    public void setRiservataArpa(java.lang.String riservataArpa) {
        this.riservataArpa = riservataArpa;
    }


    /**
     * Gets the statiProcedimento value for this PraticheAmministrative.
     * 
     * @return statiProcedimento
     */
    public com.hyperborea.sira.ws.StatiProcedimento getStatiProcedimento() {
        return statiProcedimento;
    }


    /**
     * Sets the statiProcedimento value for this PraticheAmministrative.
     * 
     * @param statiProcedimento
     */
    public void setStatiProcedimento(com.hyperborea.sira.ws.StatiProcedimento statiProcedimento) {
        this.statiProcedimento = statiProcedimento;
    }


    /**
     * Gets the tipoRichiedente value for this PraticheAmministrative.
     * 
     * @return tipoRichiedente
     */
    public java.lang.String getTipoRichiedente() {
        return tipoRichiedente;
    }


    /**
     * Sets the tipoRichiedente value for this PraticheAmministrative.
     * 
     * @param tipoRichiedente
     */
    public void setTipoRichiedente(java.lang.String tipoRichiedente) {
        this.tipoRichiedente = tipoRichiedente;
    }


    /**
     * Gets the tipologieEsiti value for this PraticheAmministrative.
     * 
     * @return tipologieEsiti
     */
    public com.hyperborea.sira.ws.TipologieEsiti getTipologieEsiti() {
        return tipologieEsiti;
    }


    /**
     * Sets the tipologieEsiti value for this PraticheAmministrative.
     * 
     * @param tipologieEsiti
     */
    public void setTipologieEsiti(com.hyperborea.sira.ws.TipologieEsiti tipologieEsiti) {
        this.tipologieEsiti = tipologieEsiti;
    }


    /**
     * Gets the tipologieFunzioni value for this PraticheAmministrative.
     * 
     * @return tipologieFunzioni
     */
    public com.hyperborea.sira.ws.TipologieFunzioni getTipologieFunzioni() {
        return tipologieFunzioni;
    }


    /**
     * Sets the tipologieFunzioni value for this PraticheAmministrative.
     * 
     * @param tipologieFunzioni
     */
    public void setTipologieFunzioni(com.hyperborea.sira.ws.TipologieFunzioni tipologieFunzioni) {
        this.tipologieFunzioni = tipologieFunzioni;
    }


    /**
     * Gets the utenteModStato value for this PraticheAmministrative.
     * 
     * @return utenteModStato
     */
    public com.hyperborea.sira.ws.Utenti getUtenteModStato() {
        return utenteModStato;
    }


    /**
     * Sets the utenteModStato value for this PraticheAmministrative.
     * 
     * @param utenteModStato
     */
    public void setUtenteModStato(com.hyperborea.sira.ws.Utenti utenteModStato) {
        this.utenteModStato = utenteModStato;
    }


    /**
     * Gets the utenti value for this PraticheAmministrative.
     * 
     * @return utenti
     */
    public com.hyperborea.sira.ws.Personale getUtenti() {
        return utenti;
    }


    /**
     * Sets the utenti value for this PraticheAmministrative.
     * 
     * @param utenti
     */
    public void setUtenti(com.hyperborea.sira.ws.Personale utenti) {
        this.utenti = utenti;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PraticheAmministrative)) return false;
        PraticheAmministrative other = (PraticheAmministrative) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.allegatis==null && other.getAllegatis()==null) || 
             (this.allegatis!=null &&
              java.util.Arrays.equals(this.allegatis, other.getAllegatis()))) &&
            ((this.annoPa==null && other.getAnnoPa()==null) || 
             (this.annoPa!=null &&
              this.annoPa.equals(other.getAnnoPa()))) &&
            ((this.attiDisposizioniAmm==null && other.getAttiDisposizioniAmm()==null) || 
             (this.attiDisposizioniAmm!=null &&
              this.attiDisposizioniAmm.equals(other.getAttiDisposizioniAmm()))) &&
            ((this.caratterizzazioniOst==null && other.getCaratterizzazioniOst()==null) || 
             (this.caratterizzazioniOst!=null &&
              this.caratterizzazioniOst.equals(other.getCaratterizzazioniOst()))) &&
            ((this.caratterizzazioniOsts==null && other.getCaratterizzazioniOsts()==null) || 
             (this.caratterizzazioniOsts!=null &&
              java.util.Arrays.equals(this.caratterizzazioniOsts, other.getCaratterizzazioniOsts()))) &&
            ((this.dataAvvio==null && other.getDataAvvio()==null) || 
             (this.dataAvvio!=null &&
              this.dataAvvio.equals(other.getDataAvvio()))) &&
            ((this.dataChiusura==null && other.getDataChiusura()==null) || 
             (this.dataChiusura!=null &&
              this.dataChiusura.equals(other.getDataChiusura()))) &&
            ((this.dataModStato==null && other.getDataModStato()==null) || 
             (this.dataModStato!=null &&
              this.dataModStato.equals(other.getDataModStato()))) &&
            ((this.dataProtChiusura==null && other.getDataProtChiusura()==null) || 
             (this.dataProtChiusura!=null &&
              this.dataProtChiusura.equals(other.getDataProtChiusura()))) &&
            ((this.dataProtRichiesta==null && other.getDataProtRichiesta()==null) || 
             (this.dataProtRichiesta!=null &&
              this.dataProtRichiesta.equals(other.getDataProtRichiesta()))) &&
            ((this.dataScadenza==null && other.getDataScadenza()==null) || 
             (this.dataScadenza!=null &&
              this.dataScadenza.equals(other.getDataScadenza()))) &&
            ((this.diIniziativa==null && other.getDiIniziativa()==null) || 
             (this.diIniziativa!=null &&
              this.diIniziativa.equals(other.getDiIniziativa()))) &&
            ((this.diParte==null && other.getDiParte()==null) || 
             (this.diParte!=null &&
              this.diParte.equals(other.getDiParte()))) &&
            ((this.dichiarazioniAmbientali==null && other.getDichiarazioniAmbientali()==null) || 
             (this.dichiarazioniAmbientali!=null &&
              this.dichiarazioniAmbientali.equals(other.getDichiarazioniAmbientali()))) &&
            ((this.entiControlloGoverno==null && other.getEntiControlloGoverno()==null) || 
             (this.entiControlloGoverno!=null &&
              this.entiControlloGoverno.equals(other.getEntiControlloGoverno()))) &&
            ((this.entiControlloGovernoForEcgRichiedenteParere==null && other.getEntiControlloGovernoForEcgRichiedenteParere()==null) || 
             (this.entiControlloGovernoForEcgRichiedenteParere!=null &&
              this.entiControlloGovernoForEcgRichiedenteParere.equals(other.getEntiControlloGovernoForEcgRichiedenteParere()))) &&
            ((this.fasiProcedimenti==null && other.getFasiProcedimenti()==null) || 
             (this.fasiProcedimenti!=null &&
              this.fasiProcedimenti.equals(other.getFasiProcedimenti()))) &&
            ((this.idPa==null && other.getIdPa()==null) || 
             (this.idPa!=null &&
              this.idPa.equals(other.getIdPa()))) &&
            ((this.misures==null && other.getMisures()==null) || 
             (this.misures!=null &&
              java.util.Arrays.equals(this.misures, other.getMisures()))) &&
            ((this.motivazione==null && other.getMotivazione()==null) || 
             (this.motivazione!=null &&
              this.motivazione.equals(other.getMotivazione()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.numeroPa==null && other.getNumeroPa()==null) || 
             (this.numeroPa!=null &&
              this.numeroPa.equals(other.getNumeroPa()))) &&
            ((this.oggettoPa==null && other.getOggettoPa()==null) || 
             (this.oggettoPa!=null &&
              this.oggettoPa.equals(other.getOggettoPa()))) &&
            ((this.paAttivitas==null && other.getPaAttivitas()==null) || 
             (this.paAttivitas!=null &&
              java.util.Arrays.equals(this.paAttivitas, other.getPaAttivitas()))) &&
            ((this.paOsts==null && other.getPaOsts()==null) || 
             (this.paOsts!=null &&
              java.util.Arrays.equals(this.paOsts, other.getPaOsts()))) &&
            ((this.paUos==null && other.getPaUos()==null) || 
             (this.paUos!=null &&
              java.util.Arrays.equals(this.paUos, other.getPaUos()))) &&
            ((this.parere==null && other.getParere()==null) || 
             (this.parere!=null &&
              this.parere.equals(other.getParere()))) &&
            ((this.prescrizioni==null && other.getPrescrizioni()==null) || 
             (this.prescrizioni!=null &&
              this.prescrizioni.equals(other.getPrescrizioni()))) &&
            ((this.protDocChiusura==null && other.getProtDocChiusura()==null) || 
             (this.protDocChiusura!=null &&
              this.protDocChiusura.equals(other.getProtDocChiusura()))) &&
            ((this.protRichiesta==null && other.getProtRichiesta()==null) || 
             (this.protRichiesta!=null &&
              this.protRichiesta.equals(other.getProtRichiesta()))) &&
            ((this.riservataArpa==null && other.getRiservataArpa()==null) || 
             (this.riservataArpa!=null &&
              this.riservataArpa.equals(other.getRiservataArpa()))) &&
            ((this.statiProcedimento==null && other.getStatiProcedimento()==null) || 
             (this.statiProcedimento!=null &&
              this.statiProcedimento.equals(other.getStatiProcedimento()))) &&
            ((this.tipoRichiedente==null && other.getTipoRichiedente()==null) || 
             (this.tipoRichiedente!=null &&
              this.tipoRichiedente.equals(other.getTipoRichiedente()))) &&
            ((this.tipologieEsiti==null && other.getTipologieEsiti()==null) || 
             (this.tipologieEsiti!=null &&
              this.tipologieEsiti.equals(other.getTipologieEsiti()))) &&
            ((this.tipologieFunzioni==null && other.getTipologieFunzioni()==null) || 
             (this.tipologieFunzioni!=null &&
              this.tipologieFunzioni.equals(other.getTipologieFunzioni()))) &&
            ((this.utenteModStato==null && other.getUtenteModStato()==null) || 
             (this.utenteModStato!=null &&
              this.utenteModStato.equals(other.getUtenteModStato()))) &&
            ((this.utenti==null && other.getUtenti()==null) || 
             (this.utenti!=null &&
              this.utenti.equals(other.getUtenti())));
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
        if (getAllegatis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAllegatis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAllegatis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAnnoPa() != null) {
            _hashCode += getAnnoPa().hashCode();
        }
        if (getAttiDisposizioniAmm() != null) {
            _hashCode += getAttiDisposizioniAmm().hashCode();
        }
        if (getCaratterizzazioniOst() != null) {
            _hashCode += getCaratterizzazioniOst().hashCode();
        }
        if (getCaratterizzazioniOsts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCaratterizzazioniOsts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCaratterizzazioniOsts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDataAvvio() != null) {
            _hashCode += getDataAvvio().hashCode();
        }
        if (getDataChiusura() != null) {
            _hashCode += getDataChiusura().hashCode();
        }
        if (getDataModStato() != null) {
            _hashCode += getDataModStato().hashCode();
        }
        if (getDataProtChiusura() != null) {
            _hashCode += getDataProtChiusura().hashCode();
        }
        if (getDataProtRichiesta() != null) {
            _hashCode += getDataProtRichiesta().hashCode();
        }
        if (getDataScadenza() != null) {
            _hashCode += getDataScadenza().hashCode();
        }
        if (getDiIniziativa() != null) {
            _hashCode += getDiIniziativa().hashCode();
        }
        if (getDiParte() != null) {
            _hashCode += getDiParte().hashCode();
        }
        if (getDichiarazioniAmbientali() != null) {
            _hashCode += getDichiarazioniAmbientali().hashCode();
        }
        if (getEntiControlloGoverno() != null) {
            _hashCode += getEntiControlloGoverno().hashCode();
        }
        if (getEntiControlloGovernoForEcgRichiedenteParere() != null) {
            _hashCode += getEntiControlloGovernoForEcgRichiedenteParere().hashCode();
        }
        if (getFasiProcedimenti() != null) {
            _hashCode += getFasiProcedimenti().hashCode();
        }
        if (getIdPa() != null) {
            _hashCode += getIdPa().hashCode();
        }
        if (getMisures() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMisures());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMisures(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMotivazione() != null) {
            _hashCode += getMotivazione().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getNumeroPa() != null) {
            _hashCode += getNumeroPa().hashCode();
        }
        if (getOggettoPa() != null) {
            _hashCode += getOggettoPa().hashCode();
        }
        if (getPaAttivitas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPaAttivitas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPaAttivitas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPaOsts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPaOsts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPaOsts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPaUos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPaUos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPaUos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getParere() != null) {
            _hashCode += getParere().hashCode();
        }
        if (getPrescrizioni() != null) {
            _hashCode += getPrescrizioni().hashCode();
        }
        if (getProtDocChiusura() != null) {
            _hashCode += getProtDocChiusura().hashCode();
        }
        if (getProtRichiesta() != null) {
            _hashCode += getProtRichiesta().hashCode();
        }
        if (getRiservataArpa() != null) {
            _hashCode += getRiservataArpa().hashCode();
        }
        if (getStatiProcedimento() != null) {
            _hashCode += getStatiProcedimento().hashCode();
        }
        if (getTipoRichiedente() != null) {
            _hashCode += getTipoRichiedente().hashCode();
        }
        if (getTipologieEsiti() != null) {
            _hashCode += getTipologieEsiti().hashCode();
        }
        if (getTipologieFunzioni() != null) {
            _hashCode += getTipologieFunzioni().hashCode();
        }
        if (getUtenteModStato() != null) {
            _hashCode += getUtenteModStato().hashCode();
        }
        if (getUtenti() != null) {
            _hashCode += getUtenti().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PraticheAmministrative.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "praticheAmministrative"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allegatis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "allegatis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "allegati"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoPa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoPa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attiDisposizioniAmm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attiDisposizioniAmm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attiDisposizioniAmm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caratterizzazioniOst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caratterizzazioniOst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "caratterizzazioniOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caratterizzazioniOsts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caratterizzazioniOsts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "caratterizzazioniOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataAvvio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataAvvio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataChiusura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataChiusura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataModStato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataModStato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataProtChiusura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataProtChiusura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataProtRichiesta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataProtRichiesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataScadenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataScadenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("diIniziativa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "diIniziativa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("diParte");
        elemField.setXmlName(new javax.xml.namespace.QName("", "diParte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dichiarazioniAmbientali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dichiarazioniAmbientali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "dichiarazioniAmbientali"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entiControlloGoverno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entiControlloGoverno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "entiControlloGoverno"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entiControlloGovernoForEcgRichiedenteParere");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entiControlloGovernoForEcgRichiedenteParere"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "entiControlloGoverno"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fasiProcedimenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fasiProcedimenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "fasiProcedimenti"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idPa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("misures");
        elemField.setXmlName(new javax.xml.namespace.QName("", "misures"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "misure"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("motivazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "motivazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieMotivazioni"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("note");
        elemField.setXmlName(new javax.xml.namespace.QName("", "note"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroPa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroPa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oggettoPa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oggettoPa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paAttivitas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paAttivitas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "paAttivita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paOsts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paOsts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "paOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paUos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paUos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "paUo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parere");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parere"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prescrizioni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prescrizioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protDocChiusura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "protDocChiusura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protRichiesta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "protRichiesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riservataArpa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riservataArpa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statiProcedimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statiProcedimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "statiProcedimento"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoRichiedente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoRichiedente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologieEsiti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologieEsiti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieEsiti"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologieFunzioni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologieFunzioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieFunzioni"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utenteModStato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utenteModStato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utenti"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "personale"));
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
