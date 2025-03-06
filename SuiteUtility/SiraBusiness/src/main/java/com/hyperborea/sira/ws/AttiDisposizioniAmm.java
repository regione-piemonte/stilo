/**
 * AttiDisposizioniAmm.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class AttiDisposizioniAmm  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.AdaOst[] adaOsts;

    private com.hyperborea.sira.ws.Allegati[] allegatis;

    private java.lang.String approvazioneProgetto;

    private com.hyperborea.sira.ws.AttiDisposizioniAmm attiDisposizioniAmm;

    private com.hyperborea.sira.ws.AttiDisposizioniAmm[] attiDisposizioniAmms;

    private java.lang.String autorizzazioneEsercizio;

    private com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst;

    private com.hyperborea.sira.ws.CaratterizzazioniOst[] caratterizzazioniOsts;

    private java.lang.String codiceAdaEnte;

    private java.util.Calendar dataAda;

    private java.util.Calendar dataIscrizione;

    private java.util.Calendar dataNotificaAda;

    private java.util.Calendar dataScadenzaAda;

    private java.lang.String descrizione;

    private com.hyperborea.sira.ws.DichiarazioniAmbientali dichiarazioniAmbientali;

    private com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGoverno;

    private com.hyperborea.sira.ws.FasiProcedimenti fasiProcedimenti;

    private java.lang.Integer idAda;

    private java.lang.String note;

    private java.lang.Integer numIscrizione;

    private java.lang.String numProt;

    private java.lang.String numSottofascicolo;

    private com.hyperborea.sira.ws.OggettiAda oggettiAda;

    private com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministratives;

    private com.hyperborea.sira.ws.PrescrizioniAda[] prescrizioniAdas;

    private com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi;

    private com.hyperborea.sira.ws.SoggettiFisici soggettiFisici;

    private com.hyperborea.sira.ws.TipologieProvvedimenti tipologieProvvedimenti;

    private com.hyperborea.sira.ws.VocTipoAda vocTipoAda;

    public AttiDisposizioniAmm() {
    }

    public AttiDisposizioniAmm(
           com.hyperborea.sira.ws.AdaOst[] adaOsts,
           com.hyperborea.sira.ws.Allegati[] allegatis,
           java.lang.String approvazioneProgetto,
           com.hyperborea.sira.ws.AttiDisposizioniAmm attiDisposizioniAmm,
           com.hyperborea.sira.ws.AttiDisposizioniAmm[] attiDisposizioniAmms,
           java.lang.String autorizzazioneEsercizio,
           com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst,
           com.hyperborea.sira.ws.CaratterizzazioniOst[] caratterizzazioniOsts,
           java.lang.String codiceAdaEnte,
           java.util.Calendar dataAda,
           java.util.Calendar dataIscrizione,
           java.util.Calendar dataNotificaAda,
           java.util.Calendar dataScadenzaAda,
           java.lang.String descrizione,
           com.hyperborea.sira.ws.DichiarazioniAmbientali dichiarazioniAmbientali,
           com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGoverno,
           com.hyperborea.sira.ws.FasiProcedimenti fasiProcedimenti,
           java.lang.Integer idAda,
           java.lang.String note,
           java.lang.Integer numIscrizione,
           java.lang.String numProt,
           java.lang.String numSottofascicolo,
           com.hyperborea.sira.ws.OggettiAda oggettiAda,
           com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministratives,
           com.hyperborea.sira.ws.PrescrizioniAda[] prescrizioniAdas,
           com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi,
           com.hyperborea.sira.ws.SoggettiFisici soggettiFisici,
           com.hyperborea.sira.ws.TipologieProvvedimenti tipologieProvvedimenti,
           com.hyperborea.sira.ws.VocTipoAda vocTipoAda) {
        this.adaOsts = adaOsts;
        this.allegatis = allegatis;
        this.approvazioneProgetto = approvazioneProgetto;
        this.attiDisposizioniAmm = attiDisposizioniAmm;
        this.attiDisposizioniAmms = attiDisposizioniAmms;
        this.autorizzazioneEsercizio = autorizzazioneEsercizio;
        this.caratterizzazioniOst = caratterizzazioniOst;
        this.caratterizzazioniOsts = caratterizzazioniOsts;
        this.codiceAdaEnte = codiceAdaEnte;
        this.dataAda = dataAda;
        this.dataIscrizione = dataIscrizione;
        this.dataNotificaAda = dataNotificaAda;
        this.dataScadenzaAda = dataScadenzaAda;
        this.descrizione = descrizione;
        this.dichiarazioniAmbientali = dichiarazioniAmbientali;
        this.entiControlloGoverno = entiControlloGoverno;
        this.fasiProcedimenti = fasiProcedimenti;
        this.idAda = idAda;
        this.note = note;
        this.numIscrizione = numIscrizione;
        this.numProt = numProt;
        this.numSottofascicolo = numSottofascicolo;
        this.oggettiAda = oggettiAda;
        this.praticheAmministratives = praticheAmministratives;
        this.prescrizioniAdas = prescrizioniAdas;
        this.riferimentiNormativi = riferimentiNormativi;
        this.soggettiFisici = soggettiFisici;
        this.tipologieProvvedimenti = tipologieProvvedimenti;
        this.vocTipoAda = vocTipoAda;
    }


    /**
     * Gets the adaOsts value for this AttiDisposizioniAmm.
     * 
     * @return adaOsts
     */
    public com.hyperborea.sira.ws.AdaOst[] getAdaOsts() {
        return adaOsts;
    }


    /**
     * Sets the adaOsts value for this AttiDisposizioniAmm.
     * 
     * @param adaOsts
     */
    public void setAdaOsts(com.hyperborea.sira.ws.AdaOst[] adaOsts) {
        this.adaOsts = adaOsts;
    }

    public com.hyperborea.sira.ws.AdaOst getAdaOsts(int i) {
        return this.adaOsts[i];
    }

    public void setAdaOsts(int i, com.hyperborea.sira.ws.AdaOst _value) {
        this.adaOsts[i] = _value;
    }


    /**
     * Gets the allegatis value for this AttiDisposizioniAmm.
     * 
     * @return allegatis
     */
    public com.hyperborea.sira.ws.Allegati[] getAllegatis() {
        return allegatis;
    }


    /**
     * Sets the allegatis value for this AttiDisposizioniAmm.
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
     * Gets the approvazioneProgetto value for this AttiDisposizioniAmm.
     * 
     * @return approvazioneProgetto
     */
    public java.lang.String getApprovazioneProgetto() {
        return approvazioneProgetto;
    }


    /**
     * Sets the approvazioneProgetto value for this AttiDisposizioniAmm.
     * 
     * @param approvazioneProgetto
     */
    public void setApprovazioneProgetto(java.lang.String approvazioneProgetto) {
        this.approvazioneProgetto = approvazioneProgetto;
    }


    /**
     * Gets the attiDisposizioniAmm value for this AttiDisposizioniAmm.
     * 
     * @return attiDisposizioniAmm
     */
    public com.hyperborea.sira.ws.AttiDisposizioniAmm getAttiDisposizioniAmm() {
        return attiDisposizioniAmm;
    }


    /**
     * Sets the attiDisposizioniAmm value for this AttiDisposizioniAmm.
     * 
     * @param attiDisposizioniAmm
     */
    public void setAttiDisposizioniAmm(com.hyperborea.sira.ws.AttiDisposizioniAmm attiDisposizioniAmm) {
        this.attiDisposizioniAmm = attiDisposizioniAmm;
    }


    /**
     * Gets the attiDisposizioniAmms value for this AttiDisposizioniAmm.
     * 
     * @return attiDisposizioniAmms
     */
    public com.hyperborea.sira.ws.AttiDisposizioniAmm[] getAttiDisposizioniAmms() {
        return attiDisposizioniAmms;
    }


    /**
     * Sets the attiDisposizioniAmms value for this AttiDisposizioniAmm.
     * 
     * @param attiDisposizioniAmms
     */
    public void setAttiDisposizioniAmms(com.hyperborea.sira.ws.AttiDisposizioniAmm[] attiDisposizioniAmms) {
        this.attiDisposizioniAmms = attiDisposizioniAmms;
    }

    public com.hyperborea.sira.ws.AttiDisposizioniAmm getAttiDisposizioniAmms(int i) {
        return this.attiDisposizioniAmms[i];
    }

    public void setAttiDisposizioniAmms(int i, com.hyperborea.sira.ws.AttiDisposizioniAmm _value) {
        this.attiDisposizioniAmms[i] = _value;
    }


    /**
     * Gets the autorizzazioneEsercizio value for this AttiDisposizioniAmm.
     * 
     * @return autorizzazioneEsercizio
     */
    public java.lang.String getAutorizzazioneEsercizio() {
        return autorizzazioneEsercizio;
    }


    /**
     * Sets the autorizzazioneEsercizio value for this AttiDisposizioniAmm.
     * 
     * @param autorizzazioneEsercizio
     */
    public void setAutorizzazioneEsercizio(java.lang.String autorizzazioneEsercizio) {
        this.autorizzazioneEsercizio = autorizzazioneEsercizio;
    }


    /**
     * Gets the caratterizzazioniOst value for this AttiDisposizioniAmm.
     * 
     * @return caratterizzazioniOst
     */
    public com.hyperborea.sira.ws.CaratterizzazioniOst getCaratterizzazioniOst() {
        return caratterizzazioniOst;
    }


    /**
     * Sets the caratterizzazioniOst value for this AttiDisposizioniAmm.
     * 
     * @param caratterizzazioniOst
     */
    public void setCaratterizzazioniOst(com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst) {
        this.caratterizzazioniOst = caratterizzazioniOst;
    }


    /**
     * Gets the caratterizzazioniOsts value for this AttiDisposizioniAmm.
     * 
     * @return caratterizzazioniOsts
     */
    public com.hyperborea.sira.ws.CaratterizzazioniOst[] getCaratterizzazioniOsts() {
        return caratterizzazioniOsts;
    }


    /**
     * Sets the caratterizzazioniOsts value for this AttiDisposizioniAmm.
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
     * Gets the codiceAdaEnte value for this AttiDisposizioniAmm.
     * 
     * @return codiceAdaEnte
     */
    public java.lang.String getCodiceAdaEnte() {
        return codiceAdaEnte;
    }


    /**
     * Sets the codiceAdaEnte value for this AttiDisposizioniAmm.
     * 
     * @param codiceAdaEnte
     */
    public void setCodiceAdaEnte(java.lang.String codiceAdaEnte) {
        this.codiceAdaEnte = codiceAdaEnte;
    }


    /**
     * Gets the dataAda value for this AttiDisposizioniAmm.
     * 
     * @return dataAda
     */
    public java.util.Calendar getDataAda() {
        return dataAda;
    }


    /**
     * Sets the dataAda value for this AttiDisposizioniAmm.
     * 
     * @param dataAda
     */
    public void setDataAda(java.util.Calendar dataAda) {
        this.dataAda = dataAda;
    }


    /**
     * Gets the dataIscrizione value for this AttiDisposizioniAmm.
     * 
     * @return dataIscrizione
     */
    public java.util.Calendar getDataIscrizione() {
        return dataIscrizione;
    }


    /**
     * Sets the dataIscrizione value for this AttiDisposizioniAmm.
     * 
     * @param dataIscrizione
     */
    public void setDataIscrizione(java.util.Calendar dataIscrizione) {
        this.dataIscrizione = dataIscrizione;
    }


    /**
     * Gets the dataNotificaAda value for this AttiDisposizioniAmm.
     * 
     * @return dataNotificaAda
     */
    public java.util.Calendar getDataNotificaAda() {
        return dataNotificaAda;
    }


    /**
     * Sets the dataNotificaAda value for this AttiDisposizioniAmm.
     * 
     * @param dataNotificaAda
     */
    public void setDataNotificaAda(java.util.Calendar dataNotificaAda) {
        this.dataNotificaAda = dataNotificaAda;
    }


    /**
     * Gets the dataScadenzaAda value for this AttiDisposizioniAmm.
     * 
     * @return dataScadenzaAda
     */
    public java.util.Calendar getDataScadenzaAda() {
        return dataScadenzaAda;
    }


    /**
     * Sets the dataScadenzaAda value for this AttiDisposizioniAmm.
     * 
     * @param dataScadenzaAda
     */
    public void setDataScadenzaAda(java.util.Calendar dataScadenzaAda) {
        this.dataScadenzaAda = dataScadenzaAda;
    }


    /**
     * Gets the descrizione value for this AttiDisposizioniAmm.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this AttiDisposizioniAmm.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the dichiarazioniAmbientali value for this AttiDisposizioniAmm.
     * 
     * @return dichiarazioniAmbientali
     */
    public com.hyperborea.sira.ws.DichiarazioniAmbientali getDichiarazioniAmbientali() {
        return dichiarazioniAmbientali;
    }


    /**
     * Sets the dichiarazioniAmbientali value for this AttiDisposizioniAmm.
     * 
     * @param dichiarazioniAmbientali
     */
    public void setDichiarazioniAmbientali(com.hyperborea.sira.ws.DichiarazioniAmbientali dichiarazioniAmbientali) {
        this.dichiarazioniAmbientali = dichiarazioniAmbientali;
    }


    /**
     * Gets the entiControlloGoverno value for this AttiDisposizioniAmm.
     * 
     * @return entiControlloGoverno
     */
    public com.hyperborea.sira.ws.EntiControlloGoverno getEntiControlloGoverno() {
        return entiControlloGoverno;
    }


    /**
     * Sets the entiControlloGoverno value for this AttiDisposizioniAmm.
     * 
     * @param entiControlloGoverno
     */
    public void setEntiControlloGoverno(com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGoverno) {
        this.entiControlloGoverno = entiControlloGoverno;
    }


    /**
     * Gets the fasiProcedimenti value for this AttiDisposizioniAmm.
     * 
     * @return fasiProcedimenti
     */
    public com.hyperborea.sira.ws.FasiProcedimenti getFasiProcedimenti() {
        return fasiProcedimenti;
    }


    /**
     * Sets the fasiProcedimenti value for this AttiDisposizioniAmm.
     * 
     * @param fasiProcedimenti
     */
    public void setFasiProcedimenti(com.hyperborea.sira.ws.FasiProcedimenti fasiProcedimenti) {
        this.fasiProcedimenti = fasiProcedimenti;
    }


    /**
     * Gets the idAda value for this AttiDisposizioniAmm.
     * 
     * @return idAda
     */
    public java.lang.Integer getIdAda() {
        return idAda;
    }


    /**
     * Sets the idAda value for this AttiDisposizioniAmm.
     * 
     * @param idAda
     */
    public void setIdAda(java.lang.Integer idAda) {
        this.idAda = idAda;
    }


    /**
     * Gets the note value for this AttiDisposizioniAmm.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this AttiDisposizioniAmm.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the numIscrizione value for this AttiDisposizioniAmm.
     * 
     * @return numIscrizione
     */
    public java.lang.Integer getNumIscrizione() {
        return numIscrizione;
    }


    /**
     * Sets the numIscrizione value for this AttiDisposizioniAmm.
     * 
     * @param numIscrizione
     */
    public void setNumIscrizione(java.lang.Integer numIscrizione) {
        this.numIscrizione = numIscrizione;
    }


    /**
     * Gets the numProt value for this AttiDisposizioniAmm.
     * 
     * @return numProt
     */
    public java.lang.String getNumProt() {
        return numProt;
    }


    /**
     * Sets the numProt value for this AttiDisposizioniAmm.
     * 
     * @param numProt
     */
    public void setNumProt(java.lang.String numProt) {
        this.numProt = numProt;
    }


    /**
     * Gets the numSottofascicolo value for this AttiDisposizioniAmm.
     * 
     * @return numSottofascicolo
     */
    public java.lang.String getNumSottofascicolo() {
        return numSottofascicolo;
    }


    /**
     * Sets the numSottofascicolo value for this AttiDisposizioniAmm.
     * 
     * @param numSottofascicolo
     */
    public void setNumSottofascicolo(java.lang.String numSottofascicolo) {
        this.numSottofascicolo = numSottofascicolo;
    }


    /**
     * Gets the oggettiAda value for this AttiDisposizioniAmm.
     * 
     * @return oggettiAda
     */
    public com.hyperborea.sira.ws.OggettiAda getOggettiAda() {
        return oggettiAda;
    }


    /**
     * Sets the oggettiAda value for this AttiDisposizioniAmm.
     * 
     * @param oggettiAda
     */
    public void setOggettiAda(com.hyperborea.sira.ws.OggettiAda oggettiAda) {
        this.oggettiAda = oggettiAda;
    }


    /**
     * Gets the praticheAmministratives value for this AttiDisposizioniAmm.
     * 
     * @return praticheAmministratives
     */
    public com.hyperborea.sira.ws.PraticheAmministrative[] getPraticheAmministratives() {
        return praticheAmministratives;
    }


    /**
     * Sets the praticheAmministratives value for this AttiDisposizioniAmm.
     * 
     * @param praticheAmministratives
     */
    public void setPraticheAmministratives(com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministratives) {
        this.praticheAmministratives = praticheAmministratives;
    }

    public com.hyperborea.sira.ws.PraticheAmministrative getPraticheAmministratives(int i) {
        return this.praticheAmministratives[i];
    }

    public void setPraticheAmministratives(int i, com.hyperborea.sira.ws.PraticheAmministrative _value) {
        this.praticheAmministratives[i] = _value;
    }


    /**
     * Gets the prescrizioniAdas value for this AttiDisposizioniAmm.
     * 
     * @return prescrizioniAdas
     */
    public com.hyperborea.sira.ws.PrescrizioniAda[] getPrescrizioniAdas() {
        return prescrizioniAdas;
    }


    /**
     * Sets the prescrizioniAdas value for this AttiDisposizioniAmm.
     * 
     * @param prescrizioniAdas
     */
    public void setPrescrizioniAdas(com.hyperborea.sira.ws.PrescrizioniAda[] prescrizioniAdas) {
        this.prescrizioniAdas = prescrizioniAdas;
    }

    public com.hyperborea.sira.ws.PrescrizioniAda getPrescrizioniAdas(int i) {
        return this.prescrizioniAdas[i];
    }

    public void setPrescrizioniAdas(int i, com.hyperborea.sira.ws.PrescrizioniAda _value) {
        this.prescrizioniAdas[i] = _value;
    }


    /**
     * Gets the riferimentiNormativi value for this AttiDisposizioniAmm.
     * 
     * @return riferimentiNormativi
     */
    public com.hyperborea.sira.ws.RiferimentiNormativi getRiferimentiNormativi() {
        return riferimentiNormativi;
    }


    /**
     * Sets the riferimentiNormativi value for this AttiDisposizioniAmm.
     * 
     * @param riferimentiNormativi
     */
    public void setRiferimentiNormativi(com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi) {
        this.riferimentiNormativi = riferimentiNormativi;
    }


    /**
     * Gets the soggettiFisici value for this AttiDisposizioniAmm.
     * 
     * @return soggettiFisici
     */
    public com.hyperborea.sira.ws.SoggettiFisici getSoggettiFisici() {
        return soggettiFisici;
    }


    /**
     * Sets the soggettiFisici value for this AttiDisposizioniAmm.
     * 
     * @param soggettiFisici
     */
    public void setSoggettiFisici(com.hyperborea.sira.ws.SoggettiFisici soggettiFisici) {
        this.soggettiFisici = soggettiFisici;
    }


    /**
     * Gets the tipologieProvvedimenti value for this AttiDisposizioniAmm.
     * 
     * @return tipologieProvvedimenti
     */
    public com.hyperborea.sira.ws.TipologieProvvedimenti getTipologieProvvedimenti() {
        return tipologieProvvedimenti;
    }


    /**
     * Sets the tipologieProvvedimenti value for this AttiDisposizioniAmm.
     * 
     * @param tipologieProvvedimenti
     */
    public void setTipologieProvvedimenti(com.hyperborea.sira.ws.TipologieProvvedimenti tipologieProvvedimenti) {
        this.tipologieProvvedimenti = tipologieProvvedimenti;
    }


    /**
     * Gets the vocTipoAda value for this AttiDisposizioniAmm.
     * 
     * @return vocTipoAda
     */
    public com.hyperborea.sira.ws.VocTipoAda getVocTipoAda() {
        return vocTipoAda;
    }


    /**
     * Sets the vocTipoAda value for this AttiDisposizioniAmm.
     * 
     * @param vocTipoAda
     */
    public void setVocTipoAda(com.hyperborea.sira.ws.VocTipoAda vocTipoAda) {
        this.vocTipoAda = vocTipoAda;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AttiDisposizioniAmm)) return false;
        AttiDisposizioniAmm other = (AttiDisposizioniAmm) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.adaOsts==null && other.getAdaOsts()==null) || 
             (this.adaOsts!=null &&
              java.util.Arrays.equals(this.adaOsts, other.getAdaOsts()))) &&
            ((this.allegatis==null && other.getAllegatis()==null) || 
             (this.allegatis!=null &&
              java.util.Arrays.equals(this.allegatis, other.getAllegatis()))) &&
            ((this.approvazioneProgetto==null && other.getApprovazioneProgetto()==null) || 
             (this.approvazioneProgetto!=null &&
              this.approvazioneProgetto.equals(other.getApprovazioneProgetto()))) &&
            ((this.attiDisposizioniAmm==null && other.getAttiDisposizioniAmm()==null) || 
             (this.attiDisposizioniAmm!=null &&
              this.attiDisposizioniAmm.equals(other.getAttiDisposizioniAmm()))) &&
            ((this.attiDisposizioniAmms==null && other.getAttiDisposizioniAmms()==null) || 
             (this.attiDisposizioniAmms!=null &&
              java.util.Arrays.equals(this.attiDisposizioniAmms, other.getAttiDisposizioniAmms()))) &&
            ((this.autorizzazioneEsercizio==null && other.getAutorizzazioneEsercizio()==null) || 
             (this.autorizzazioneEsercizio!=null &&
              this.autorizzazioneEsercizio.equals(other.getAutorizzazioneEsercizio()))) &&
            ((this.caratterizzazioniOst==null && other.getCaratterizzazioniOst()==null) || 
             (this.caratterizzazioniOst!=null &&
              this.caratterizzazioniOst.equals(other.getCaratterizzazioniOst()))) &&
            ((this.caratterizzazioniOsts==null && other.getCaratterizzazioniOsts()==null) || 
             (this.caratterizzazioniOsts!=null &&
              java.util.Arrays.equals(this.caratterizzazioniOsts, other.getCaratterizzazioniOsts()))) &&
            ((this.codiceAdaEnte==null && other.getCodiceAdaEnte()==null) || 
             (this.codiceAdaEnte!=null &&
              this.codiceAdaEnte.equals(other.getCodiceAdaEnte()))) &&
            ((this.dataAda==null && other.getDataAda()==null) || 
             (this.dataAda!=null &&
              this.dataAda.equals(other.getDataAda()))) &&
            ((this.dataIscrizione==null && other.getDataIscrizione()==null) || 
             (this.dataIscrizione!=null &&
              this.dataIscrizione.equals(other.getDataIscrizione()))) &&
            ((this.dataNotificaAda==null && other.getDataNotificaAda()==null) || 
             (this.dataNotificaAda!=null &&
              this.dataNotificaAda.equals(other.getDataNotificaAda()))) &&
            ((this.dataScadenzaAda==null && other.getDataScadenzaAda()==null) || 
             (this.dataScadenzaAda!=null &&
              this.dataScadenzaAda.equals(other.getDataScadenzaAda()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.dichiarazioniAmbientali==null && other.getDichiarazioniAmbientali()==null) || 
             (this.dichiarazioniAmbientali!=null &&
              this.dichiarazioniAmbientali.equals(other.getDichiarazioniAmbientali()))) &&
            ((this.entiControlloGoverno==null && other.getEntiControlloGoverno()==null) || 
             (this.entiControlloGoverno!=null &&
              this.entiControlloGoverno.equals(other.getEntiControlloGoverno()))) &&
            ((this.fasiProcedimenti==null && other.getFasiProcedimenti()==null) || 
             (this.fasiProcedimenti!=null &&
              this.fasiProcedimenti.equals(other.getFasiProcedimenti()))) &&
            ((this.idAda==null && other.getIdAda()==null) || 
             (this.idAda!=null &&
              this.idAda.equals(other.getIdAda()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.numIscrizione==null && other.getNumIscrizione()==null) || 
             (this.numIscrizione!=null &&
              this.numIscrizione.equals(other.getNumIscrizione()))) &&
            ((this.numProt==null && other.getNumProt()==null) || 
             (this.numProt!=null &&
              this.numProt.equals(other.getNumProt()))) &&
            ((this.numSottofascicolo==null && other.getNumSottofascicolo()==null) || 
             (this.numSottofascicolo!=null &&
              this.numSottofascicolo.equals(other.getNumSottofascicolo()))) &&
            ((this.oggettiAda==null && other.getOggettiAda()==null) || 
             (this.oggettiAda!=null &&
              this.oggettiAda.equals(other.getOggettiAda()))) &&
            ((this.praticheAmministratives==null && other.getPraticheAmministratives()==null) || 
             (this.praticheAmministratives!=null &&
              java.util.Arrays.equals(this.praticheAmministratives, other.getPraticheAmministratives()))) &&
            ((this.prescrizioniAdas==null && other.getPrescrizioniAdas()==null) || 
             (this.prescrizioniAdas!=null &&
              java.util.Arrays.equals(this.prescrizioniAdas, other.getPrescrizioniAdas()))) &&
            ((this.riferimentiNormativi==null && other.getRiferimentiNormativi()==null) || 
             (this.riferimentiNormativi!=null &&
              this.riferimentiNormativi.equals(other.getRiferimentiNormativi()))) &&
            ((this.soggettiFisici==null && other.getSoggettiFisici()==null) || 
             (this.soggettiFisici!=null &&
              this.soggettiFisici.equals(other.getSoggettiFisici()))) &&
            ((this.tipologieProvvedimenti==null && other.getTipologieProvvedimenti()==null) || 
             (this.tipologieProvvedimenti!=null &&
              this.tipologieProvvedimenti.equals(other.getTipologieProvvedimenti()))) &&
            ((this.vocTipoAda==null && other.getVocTipoAda()==null) || 
             (this.vocTipoAda!=null &&
              this.vocTipoAda.equals(other.getVocTipoAda())));
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
        if (getAdaOsts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAdaOsts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAdaOsts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
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
        if (getApprovazioneProgetto() != null) {
            _hashCode += getApprovazioneProgetto().hashCode();
        }
        if (getAttiDisposizioniAmm() != null) {
            _hashCode += getAttiDisposizioniAmm().hashCode();
        }
        if (getAttiDisposizioniAmms() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAttiDisposizioniAmms());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAttiDisposizioniAmms(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAutorizzazioneEsercizio() != null) {
            _hashCode += getAutorizzazioneEsercizio().hashCode();
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
        if (getCodiceAdaEnte() != null) {
            _hashCode += getCodiceAdaEnte().hashCode();
        }
        if (getDataAda() != null) {
            _hashCode += getDataAda().hashCode();
        }
        if (getDataIscrizione() != null) {
            _hashCode += getDataIscrizione().hashCode();
        }
        if (getDataNotificaAda() != null) {
            _hashCode += getDataNotificaAda().hashCode();
        }
        if (getDataScadenzaAda() != null) {
            _hashCode += getDataScadenzaAda().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getDichiarazioniAmbientali() != null) {
            _hashCode += getDichiarazioniAmbientali().hashCode();
        }
        if (getEntiControlloGoverno() != null) {
            _hashCode += getEntiControlloGoverno().hashCode();
        }
        if (getFasiProcedimenti() != null) {
            _hashCode += getFasiProcedimenti().hashCode();
        }
        if (getIdAda() != null) {
            _hashCode += getIdAda().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getNumIscrizione() != null) {
            _hashCode += getNumIscrizione().hashCode();
        }
        if (getNumProt() != null) {
            _hashCode += getNumProt().hashCode();
        }
        if (getNumSottofascicolo() != null) {
            _hashCode += getNumSottofascicolo().hashCode();
        }
        if (getOggettiAda() != null) {
            _hashCode += getOggettiAda().hashCode();
        }
        if (getPraticheAmministratives() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPraticheAmministratives());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPraticheAmministratives(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPrescrizioniAdas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPrescrizioniAdas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPrescrizioniAdas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRiferimentiNormativi() != null) {
            _hashCode += getRiferimentiNormativi().hashCode();
        }
        if (getSoggettiFisici() != null) {
            _hashCode += getSoggettiFisici().hashCode();
        }
        if (getTipologieProvvedimenti() != null) {
            _hashCode += getTipologieProvvedimenti().hashCode();
        }
        if (getVocTipoAda() != null) {
            _hashCode += getVocTipoAda().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AttiDisposizioniAmm.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attiDisposizioniAmm"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adaOsts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "adaOsts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "adaOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allegatis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "allegatis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "allegati"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("approvazioneProgetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "approvazioneProgetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("attiDisposizioniAmms");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attiDisposizioniAmms"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attiDisposizioniAmm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autorizzazioneEsercizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "autorizzazioneEsercizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("codiceAdaEnte");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceAdaEnte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataAda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataAda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataIscrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataIscrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataNotificaAda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataNotificaAda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataScadenzaAda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataScadenzaAda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("fasiProcedimenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fasiProcedimenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "fasiProcedimenti"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idAda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("numIscrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numIscrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numProt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numProt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numSottofascicolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numSottofascicolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oggettiAda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oggettiAda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "oggettiAda"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("praticheAmministratives");
        elemField.setXmlName(new javax.xml.namespace.QName("", "praticheAmministratives"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "praticheAmministrative"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prescrizioniAdas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prescrizioniAdas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prescrizioniAda"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riferimentiNormativi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riferimentiNormativi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "riferimentiNormativi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soggettiFisici");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soggettiFisici"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "soggettiFisici"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologieProvvedimenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologieProvvedimenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieProvvedimenti"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoAda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoAda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoAda"));
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
