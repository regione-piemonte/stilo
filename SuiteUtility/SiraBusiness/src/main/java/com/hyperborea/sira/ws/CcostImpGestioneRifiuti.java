/**
 * CcostImpGestioneRifiuti.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostImpGestioneRifiuti  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String bacinoUtenza;

    private com.hyperborea.sira.ws.CcostIgrAutodemolizione ccostIgrAutodemolizione;

    private com.hyperborea.sira.ws.CcostIgrCoinceneritore ccostIgrCoinceneritore;

    private com.hyperborea.sira.ws.CcostIgrCompostaggioCdr ccostIgrCompostaggioCdr;

    private com.hyperborea.sira.ws.CcostIgrDiscarica ccostIgrDiscarica;

    private com.hyperborea.sira.ws.CcostIgrInceneritore ccostIgrInceneritore;

    private com.hyperborea.sira.ws.CcostIgrMobile ccostIgrMobile;

    private com.hyperborea.sira.ws.CcostIgrRecupero ccostIgrRecupero;

    private com.hyperborea.sira.ws.CcostIgrSelezione ccostIgrSelezione;

    private com.hyperborea.sira.ws.CcostIgrStoccaggioProvv ccostIgrStoccaggioProvv;

    private com.hyperborea.sira.ws.CcostIgrTrattAnaerobico ccostIgrTrattAnaerobico;

    private com.hyperborea.sira.ws.CcostIgrTrattChifisBio ccostIgrTrattChifisBio;

    private com.hyperborea.sira.ws.CcostIgrTrattamentoRaee ccostIgrTrattamentoRaee;

    private java.lang.Integer garanziaFinanziaria;

    private java.lang.Integer garanziaFinanziariaPostOp;

    private java.lang.Integer idCcost;

    private java.lang.String inserimentoPpRs;

    private java.lang.String inserimentoPpRsu;

    private java.lang.Float messaRiservaMax;

    private java.lang.Float messaRiservaMaxRnp;

    private java.lang.Float messaRiservaMaxRp;

    private java.lang.Float movimentazioneAnnua;

    private java.lang.Float movimentazioneAnnuaRnp;

    private java.lang.Float movimentazioneAnnuaRp;

    private java.lang.String numIscrizioneAlboGestori;

    private com.hyperborea.sira.ws.Opdl152CcostIgr[] opdl152CcostIgrs;

    private java.lang.Float stoccaggioMax;

    private java.lang.Float stoccaggioMaxRnp;

    private java.lang.Float stoccaggioMaxRp;

    private java.lang.String tipologiaIsed;

    private java.lang.String tipologiaRifiuti;

    private java.lang.String umMa;

    private java.lang.String umMarnp;

    private java.lang.String umMarp;

    private java.lang.String umMr;

    private java.lang.String umMrrnp;

    private java.lang.String umMrrp;

    private java.lang.String umSmr;

    private java.lang.String umSmrnp;

    private java.lang.String umSmrp;

    public CcostImpGestioneRifiuti() {
    }

    public CcostImpGestioneRifiuti(
           java.lang.String bacinoUtenza,
           com.hyperborea.sira.ws.CcostIgrAutodemolizione ccostIgrAutodemolizione,
           com.hyperborea.sira.ws.CcostIgrCoinceneritore ccostIgrCoinceneritore,
           com.hyperborea.sira.ws.CcostIgrCompostaggioCdr ccostIgrCompostaggioCdr,
           com.hyperborea.sira.ws.CcostIgrDiscarica ccostIgrDiscarica,
           com.hyperborea.sira.ws.CcostIgrInceneritore ccostIgrInceneritore,
           com.hyperborea.sira.ws.CcostIgrMobile ccostIgrMobile,
           com.hyperborea.sira.ws.CcostIgrRecupero ccostIgrRecupero,
           com.hyperborea.sira.ws.CcostIgrSelezione ccostIgrSelezione,
           com.hyperborea.sira.ws.CcostIgrStoccaggioProvv ccostIgrStoccaggioProvv,
           com.hyperborea.sira.ws.CcostIgrTrattAnaerobico ccostIgrTrattAnaerobico,
           com.hyperborea.sira.ws.CcostIgrTrattChifisBio ccostIgrTrattChifisBio,
           com.hyperborea.sira.ws.CcostIgrTrattamentoRaee ccostIgrTrattamentoRaee,
           java.lang.Integer garanziaFinanziaria,
           java.lang.Integer garanziaFinanziariaPostOp,
           java.lang.Integer idCcost,
           java.lang.String inserimentoPpRs,
           java.lang.String inserimentoPpRsu,
           java.lang.Float messaRiservaMax,
           java.lang.Float messaRiservaMaxRnp,
           java.lang.Float messaRiservaMaxRp,
           java.lang.Float movimentazioneAnnua,
           java.lang.Float movimentazioneAnnuaRnp,
           java.lang.Float movimentazioneAnnuaRp,
           java.lang.String numIscrizioneAlboGestori,
           com.hyperborea.sira.ws.Opdl152CcostIgr[] opdl152CcostIgrs,
           java.lang.Float stoccaggioMax,
           java.lang.Float stoccaggioMaxRnp,
           java.lang.Float stoccaggioMaxRp,
           java.lang.String tipologiaIsed,
           java.lang.String tipologiaRifiuti,
           java.lang.String umMa,
           java.lang.String umMarnp,
           java.lang.String umMarp,
           java.lang.String umMr,
           java.lang.String umMrrnp,
           java.lang.String umMrrp,
           java.lang.String umSmr,
           java.lang.String umSmrnp,
           java.lang.String umSmrp) {
        this.bacinoUtenza = bacinoUtenza;
        this.ccostIgrAutodemolizione = ccostIgrAutodemolizione;
        this.ccostIgrCoinceneritore = ccostIgrCoinceneritore;
        this.ccostIgrCompostaggioCdr = ccostIgrCompostaggioCdr;
        this.ccostIgrDiscarica = ccostIgrDiscarica;
        this.ccostIgrInceneritore = ccostIgrInceneritore;
        this.ccostIgrMobile = ccostIgrMobile;
        this.ccostIgrRecupero = ccostIgrRecupero;
        this.ccostIgrSelezione = ccostIgrSelezione;
        this.ccostIgrStoccaggioProvv = ccostIgrStoccaggioProvv;
        this.ccostIgrTrattAnaerobico = ccostIgrTrattAnaerobico;
        this.ccostIgrTrattChifisBio = ccostIgrTrattChifisBio;
        this.ccostIgrTrattamentoRaee = ccostIgrTrattamentoRaee;
        this.garanziaFinanziaria = garanziaFinanziaria;
        this.garanziaFinanziariaPostOp = garanziaFinanziariaPostOp;
        this.idCcost = idCcost;
        this.inserimentoPpRs = inserimentoPpRs;
        this.inserimentoPpRsu = inserimentoPpRsu;
        this.messaRiservaMax = messaRiservaMax;
        this.messaRiservaMaxRnp = messaRiservaMaxRnp;
        this.messaRiservaMaxRp = messaRiservaMaxRp;
        this.movimentazioneAnnua = movimentazioneAnnua;
        this.movimentazioneAnnuaRnp = movimentazioneAnnuaRnp;
        this.movimentazioneAnnuaRp = movimentazioneAnnuaRp;
        this.numIscrizioneAlboGestori = numIscrizioneAlboGestori;
        this.opdl152CcostIgrs = opdl152CcostIgrs;
        this.stoccaggioMax = stoccaggioMax;
        this.stoccaggioMaxRnp = stoccaggioMaxRnp;
        this.stoccaggioMaxRp = stoccaggioMaxRp;
        this.tipologiaIsed = tipologiaIsed;
        this.tipologiaRifiuti = tipologiaRifiuti;
        this.umMa = umMa;
        this.umMarnp = umMarnp;
        this.umMarp = umMarp;
        this.umMr = umMr;
        this.umMrrnp = umMrrnp;
        this.umMrrp = umMrrp;
        this.umSmr = umSmr;
        this.umSmrnp = umSmrnp;
        this.umSmrp = umSmrp;
    }


    /**
     * Gets the bacinoUtenza value for this CcostImpGestioneRifiuti.
     * 
     * @return bacinoUtenza
     */
    public java.lang.String getBacinoUtenza() {
        return bacinoUtenza;
    }


    /**
     * Sets the bacinoUtenza value for this CcostImpGestioneRifiuti.
     * 
     * @param bacinoUtenza
     */
    public void setBacinoUtenza(java.lang.String bacinoUtenza) {
        this.bacinoUtenza = bacinoUtenza;
    }


    /**
     * Gets the ccostIgrAutodemolizione value for this CcostImpGestioneRifiuti.
     * 
     * @return ccostIgrAutodemolizione
     */
    public com.hyperborea.sira.ws.CcostIgrAutodemolizione getCcostIgrAutodemolizione() {
        return ccostIgrAutodemolizione;
    }


    /**
     * Sets the ccostIgrAutodemolizione value for this CcostImpGestioneRifiuti.
     * 
     * @param ccostIgrAutodemolizione
     */
    public void setCcostIgrAutodemolizione(com.hyperborea.sira.ws.CcostIgrAutodemolizione ccostIgrAutodemolizione) {
        this.ccostIgrAutodemolizione = ccostIgrAutodemolizione;
    }


    /**
     * Gets the ccostIgrCoinceneritore value for this CcostImpGestioneRifiuti.
     * 
     * @return ccostIgrCoinceneritore
     */
    public com.hyperborea.sira.ws.CcostIgrCoinceneritore getCcostIgrCoinceneritore() {
        return ccostIgrCoinceneritore;
    }


    /**
     * Sets the ccostIgrCoinceneritore value for this CcostImpGestioneRifiuti.
     * 
     * @param ccostIgrCoinceneritore
     */
    public void setCcostIgrCoinceneritore(com.hyperborea.sira.ws.CcostIgrCoinceneritore ccostIgrCoinceneritore) {
        this.ccostIgrCoinceneritore = ccostIgrCoinceneritore;
    }


    /**
     * Gets the ccostIgrCompostaggioCdr value for this CcostImpGestioneRifiuti.
     * 
     * @return ccostIgrCompostaggioCdr
     */
    public com.hyperborea.sira.ws.CcostIgrCompostaggioCdr getCcostIgrCompostaggioCdr() {
        return ccostIgrCompostaggioCdr;
    }


    /**
     * Sets the ccostIgrCompostaggioCdr value for this CcostImpGestioneRifiuti.
     * 
     * @param ccostIgrCompostaggioCdr
     */
    public void setCcostIgrCompostaggioCdr(com.hyperborea.sira.ws.CcostIgrCompostaggioCdr ccostIgrCompostaggioCdr) {
        this.ccostIgrCompostaggioCdr = ccostIgrCompostaggioCdr;
    }


    /**
     * Gets the ccostIgrDiscarica value for this CcostImpGestioneRifiuti.
     * 
     * @return ccostIgrDiscarica
     */
    public com.hyperborea.sira.ws.CcostIgrDiscarica getCcostIgrDiscarica() {
        return ccostIgrDiscarica;
    }


    /**
     * Sets the ccostIgrDiscarica value for this CcostImpGestioneRifiuti.
     * 
     * @param ccostIgrDiscarica
     */
    public void setCcostIgrDiscarica(com.hyperborea.sira.ws.CcostIgrDiscarica ccostIgrDiscarica) {
        this.ccostIgrDiscarica = ccostIgrDiscarica;
    }


    /**
     * Gets the ccostIgrInceneritore value for this CcostImpGestioneRifiuti.
     * 
     * @return ccostIgrInceneritore
     */
    public com.hyperborea.sira.ws.CcostIgrInceneritore getCcostIgrInceneritore() {
        return ccostIgrInceneritore;
    }


    /**
     * Sets the ccostIgrInceneritore value for this CcostImpGestioneRifiuti.
     * 
     * @param ccostIgrInceneritore
     */
    public void setCcostIgrInceneritore(com.hyperborea.sira.ws.CcostIgrInceneritore ccostIgrInceneritore) {
        this.ccostIgrInceneritore = ccostIgrInceneritore;
    }


    /**
     * Gets the ccostIgrMobile value for this CcostImpGestioneRifiuti.
     * 
     * @return ccostIgrMobile
     */
    public com.hyperborea.sira.ws.CcostIgrMobile getCcostIgrMobile() {
        return ccostIgrMobile;
    }


    /**
     * Sets the ccostIgrMobile value for this CcostImpGestioneRifiuti.
     * 
     * @param ccostIgrMobile
     */
    public void setCcostIgrMobile(com.hyperborea.sira.ws.CcostIgrMobile ccostIgrMobile) {
        this.ccostIgrMobile = ccostIgrMobile;
    }


    /**
     * Gets the ccostIgrRecupero value for this CcostImpGestioneRifiuti.
     * 
     * @return ccostIgrRecupero
     */
    public com.hyperborea.sira.ws.CcostIgrRecupero getCcostIgrRecupero() {
        return ccostIgrRecupero;
    }


    /**
     * Sets the ccostIgrRecupero value for this CcostImpGestioneRifiuti.
     * 
     * @param ccostIgrRecupero
     */
    public void setCcostIgrRecupero(com.hyperborea.sira.ws.CcostIgrRecupero ccostIgrRecupero) {
        this.ccostIgrRecupero = ccostIgrRecupero;
    }


    /**
     * Gets the ccostIgrSelezione value for this CcostImpGestioneRifiuti.
     * 
     * @return ccostIgrSelezione
     */
    public com.hyperborea.sira.ws.CcostIgrSelezione getCcostIgrSelezione() {
        return ccostIgrSelezione;
    }


    /**
     * Sets the ccostIgrSelezione value for this CcostImpGestioneRifiuti.
     * 
     * @param ccostIgrSelezione
     */
    public void setCcostIgrSelezione(com.hyperborea.sira.ws.CcostIgrSelezione ccostIgrSelezione) {
        this.ccostIgrSelezione = ccostIgrSelezione;
    }


    /**
     * Gets the ccostIgrStoccaggioProvv value for this CcostImpGestioneRifiuti.
     * 
     * @return ccostIgrStoccaggioProvv
     */
    public com.hyperborea.sira.ws.CcostIgrStoccaggioProvv getCcostIgrStoccaggioProvv() {
        return ccostIgrStoccaggioProvv;
    }


    /**
     * Sets the ccostIgrStoccaggioProvv value for this CcostImpGestioneRifiuti.
     * 
     * @param ccostIgrStoccaggioProvv
     */
    public void setCcostIgrStoccaggioProvv(com.hyperborea.sira.ws.CcostIgrStoccaggioProvv ccostIgrStoccaggioProvv) {
        this.ccostIgrStoccaggioProvv = ccostIgrStoccaggioProvv;
    }


    /**
     * Gets the ccostIgrTrattAnaerobico value for this CcostImpGestioneRifiuti.
     * 
     * @return ccostIgrTrattAnaerobico
     */
    public com.hyperborea.sira.ws.CcostIgrTrattAnaerobico getCcostIgrTrattAnaerobico() {
        return ccostIgrTrattAnaerobico;
    }


    /**
     * Sets the ccostIgrTrattAnaerobico value for this CcostImpGestioneRifiuti.
     * 
     * @param ccostIgrTrattAnaerobico
     */
    public void setCcostIgrTrattAnaerobico(com.hyperborea.sira.ws.CcostIgrTrattAnaerobico ccostIgrTrattAnaerobico) {
        this.ccostIgrTrattAnaerobico = ccostIgrTrattAnaerobico;
    }


    /**
     * Gets the ccostIgrTrattChifisBio value for this CcostImpGestioneRifiuti.
     * 
     * @return ccostIgrTrattChifisBio
     */
    public com.hyperborea.sira.ws.CcostIgrTrattChifisBio getCcostIgrTrattChifisBio() {
        return ccostIgrTrattChifisBio;
    }


    /**
     * Sets the ccostIgrTrattChifisBio value for this CcostImpGestioneRifiuti.
     * 
     * @param ccostIgrTrattChifisBio
     */
    public void setCcostIgrTrattChifisBio(com.hyperborea.sira.ws.CcostIgrTrattChifisBio ccostIgrTrattChifisBio) {
        this.ccostIgrTrattChifisBio = ccostIgrTrattChifisBio;
    }


    /**
     * Gets the ccostIgrTrattamentoRaee value for this CcostImpGestioneRifiuti.
     * 
     * @return ccostIgrTrattamentoRaee
     */
    public com.hyperborea.sira.ws.CcostIgrTrattamentoRaee getCcostIgrTrattamentoRaee() {
        return ccostIgrTrattamentoRaee;
    }


    /**
     * Sets the ccostIgrTrattamentoRaee value for this CcostImpGestioneRifiuti.
     * 
     * @param ccostIgrTrattamentoRaee
     */
    public void setCcostIgrTrattamentoRaee(com.hyperborea.sira.ws.CcostIgrTrattamentoRaee ccostIgrTrattamentoRaee) {
        this.ccostIgrTrattamentoRaee = ccostIgrTrattamentoRaee;
    }


    /**
     * Gets the garanziaFinanziaria value for this CcostImpGestioneRifiuti.
     * 
     * @return garanziaFinanziaria
     */
    public java.lang.Integer getGaranziaFinanziaria() {
        return garanziaFinanziaria;
    }


    /**
     * Sets the garanziaFinanziaria value for this CcostImpGestioneRifiuti.
     * 
     * @param garanziaFinanziaria
     */
    public void setGaranziaFinanziaria(java.lang.Integer garanziaFinanziaria) {
        this.garanziaFinanziaria = garanziaFinanziaria;
    }


    /**
     * Gets the garanziaFinanziariaPostOp value for this CcostImpGestioneRifiuti.
     * 
     * @return garanziaFinanziariaPostOp
     */
    public java.lang.Integer getGaranziaFinanziariaPostOp() {
        return garanziaFinanziariaPostOp;
    }


    /**
     * Sets the garanziaFinanziariaPostOp value for this CcostImpGestioneRifiuti.
     * 
     * @param garanziaFinanziariaPostOp
     */
    public void setGaranziaFinanziariaPostOp(java.lang.Integer garanziaFinanziariaPostOp) {
        this.garanziaFinanziariaPostOp = garanziaFinanziariaPostOp;
    }


    /**
     * Gets the idCcost value for this CcostImpGestioneRifiuti.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostImpGestioneRifiuti.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the inserimentoPpRs value for this CcostImpGestioneRifiuti.
     * 
     * @return inserimentoPpRs
     */
    public java.lang.String getInserimentoPpRs() {
        return inserimentoPpRs;
    }


    /**
     * Sets the inserimentoPpRs value for this CcostImpGestioneRifiuti.
     * 
     * @param inserimentoPpRs
     */
    public void setInserimentoPpRs(java.lang.String inserimentoPpRs) {
        this.inserimentoPpRs = inserimentoPpRs;
    }


    /**
     * Gets the inserimentoPpRsu value for this CcostImpGestioneRifiuti.
     * 
     * @return inserimentoPpRsu
     */
    public java.lang.String getInserimentoPpRsu() {
        return inserimentoPpRsu;
    }


    /**
     * Sets the inserimentoPpRsu value for this CcostImpGestioneRifiuti.
     * 
     * @param inserimentoPpRsu
     */
    public void setInserimentoPpRsu(java.lang.String inserimentoPpRsu) {
        this.inserimentoPpRsu = inserimentoPpRsu;
    }


    /**
     * Gets the messaRiservaMax value for this CcostImpGestioneRifiuti.
     * 
     * @return messaRiservaMax
     */
    public java.lang.Float getMessaRiservaMax() {
        return messaRiservaMax;
    }


    /**
     * Sets the messaRiservaMax value for this CcostImpGestioneRifiuti.
     * 
     * @param messaRiservaMax
     */
    public void setMessaRiservaMax(java.lang.Float messaRiservaMax) {
        this.messaRiservaMax = messaRiservaMax;
    }


    /**
     * Gets the messaRiservaMaxRnp value for this CcostImpGestioneRifiuti.
     * 
     * @return messaRiservaMaxRnp
     */
    public java.lang.Float getMessaRiservaMaxRnp() {
        return messaRiservaMaxRnp;
    }


    /**
     * Sets the messaRiservaMaxRnp value for this CcostImpGestioneRifiuti.
     * 
     * @param messaRiservaMaxRnp
     */
    public void setMessaRiservaMaxRnp(java.lang.Float messaRiservaMaxRnp) {
        this.messaRiservaMaxRnp = messaRiservaMaxRnp;
    }


    /**
     * Gets the messaRiservaMaxRp value for this CcostImpGestioneRifiuti.
     * 
     * @return messaRiservaMaxRp
     */
    public java.lang.Float getMessaRiservaMaxRp() {
        return messaRiservaMaxRp;
    }


    /**
     * Sets the messaRiservaMaxRp value for this CcostImpGestioneRifiuti.
     * 
     * @param messaRiservaMaxRp
     */
    public void setMessaRiservaMaxRp(java.lang.Float messaRiservaMaxRp) {
        this.messaRiservaMaxRp = messaRiservaMaxRp;
    }


    /**
     * Gets the movimentazioneAnnua value for this CcostImpGestioneRifiuti.
     * 
     * @return movimentazioneAnnua
     */
    public java.lang.Float getMovimentazioneAnnua() {
        return movimentazioneAnnua;
    }


    /**
     * Sets the movimentazioneAnnua value for this CcostImpGestioneRifiuti.
     * 
     * @param movimentazioneAnnua
     */
    public void setMovimentazioneAnnua(java.lang.Float movimentazioneAnnua) {
        this.movimentazioneAnnua = movimentazioneAnnua;
    }


    /**
     * Gets the movimentazioneAnnuaRnp value for this CcostImpGestioneRifiuti.
     * 
     * @return movimentazioneAnnuaRnp
     */
    public java.lang.Float getMovimentazioneAnnuaRnp() {
        return movimentazioneAnnuaRnp;
    }


    /**
     * Sets the movimentazioneAnnuaRnp value for this CcostImpGestioneRifiuti.
     * 
     * @param movimentazioneAnnuaRnp
     */
    public void setMovimentazioneAnnuaRnp(java.lang.Float movimentazioneAnnuaRnp) {
        this.movimentazioneAnnuaRnp = movimentazioneAnnuaRnp;
    }


    /**
     * Gets the movimentazioneAnnuaRp value for this CcostImpGestioneRifiuti.
     * 
     * @return movimentazioneAnnuaRp
     */
    public java.lang.Float getMovimentazioneAnnuaRp() {
        return movimentazioneAnnuaRp;
    }


    /**
     * Sets the movimentazioneAnnuaRp value for this CcostImpGestioneRifiuti.
     * 
     * @param movimentazioneAnnuaRp
     */
    public void setMovimentazioneAnnuaRp(java.lang.Float movimentazioneAnnuaRp) {
        this.movimentazioneAnnuaRp = movimentazioneAnnuaRp;
    }


    /**
     * Gets the numIscrizioneAlboGestori value for this CcostImpGestioneRifiuti.
     * 
     * @return numIscrizioneAlboGestori
     */
    public java.lang.String getNumIscrizioneAlboGestori() {
        return numIscrizioneAlboGestori;
    }


    /**
     * Sets the numIscrizioneAlboGestori value for this CcostImpGestioneRifiuti.
     * 
     * @param numIscrizioneAlboGestori
     */
    public void setNumIscrizioneAlboGestori(java.lang.String numIscrizioneAlboGestori) {
        this.numIscrizioneAlboGestori = numIscrizioneAlboGestori;
    }


    /**
     * Gets the opdl152CcostIgrs value for this CcostImpGestioneRifiuti.
     * 
     * @return opdl152CcostIgrs
     */
    public com.hyperborea.sira.ws.Opdl152CcostIgr[] getOpdl152CcostIgrs() {
        return opdl152CcostIgrs;
    }


    /**
     * Sets the opdl152CcostIgrs value for this CcostImpGestioneRifiuti.
     * 
     * @param opdl152CcostIgrs
     */
    public void setOpdl152CcostIgrs(com.hyperborea.sira.ws.Opdl152CcostIgr[] opdl152CcostIgrs) {
        this.opdl152CcostIgrs = opdl152CcostIgrs;
    }

    public com.hyperborea.sira.ws.Opdl152CcostIgr getOpdl152CcostIgrs(int i) {
        return this.opdl152CcostIgrs[i];
    }

    public void setOpdl152CcostIgrs(int i, com.hyperborea.sira.ws.Opdl152CcostIgr _value) {
        this.opdl152CcostIgrs[i] = _value;
    }


    /**
     * Gets the stoccaggioMax value for this CcostImpGestioneRifiuti.
     * 
     * @return stoccaggioMax
     */
    public java.lang.Float getStoccaggioMax() {
        return stoccaggioMax;
    }


    /**
     * Sets the stoccaggioMax value for this CcostImpGestioneRifiuti.
     * 
     * @param stoccaggioMax
     */
    public void setStoccaggioMax(java.lang.Float stoccaggioMax) {
        this.stoccaggioMax = stoccaggioMax;
    }


    /**
     * Gets the stoccaggioMaxRnp value for this CcostImpGestioneRifiuti.
     * 
     * @return stoccaggioMaxRnp
     */
    public java.lang.Float getStoccaggioMaxRnp() {
        return stoccaggioMaxRnp;
    }


    /**
     * Sets the stoccaggioMaxRnp value for this CcostImpGestioneRifiuti.
     * 
     * @param stoccaggioMaxRnp
     */
    public void setStoccaggioMaxRnp(java.lang.Float stoccaggioMaxRnp) {
        this.stoccaggioMaxRnp = stoccaggioMaxRnp;
    }


    /**
     * Gets the stoccaggioMaxRp value for this CcostImpGestioneRifiuti.
     * 
     * @return stoccaggioMaxRp
     */
    public java.lang.Float getStoccaggioMaxRp() {
        return stoccaggioMaxRp;
    }


    /**
     * Sets the stoccaggioMaxRp value for this CcostImpGestioneRifiuti.
     * 
     * @param stoccaggioMaxRp
     */
    public void setStoccaggioMaxRp(java.lang.Float stoccaggioMaxRp) {
        this.stoccaggioMaxRp = stoccaggioMaxRp;
    }


    /**
     * Gets the tipologiaIsed value for this CcostImpGestioneRifiuti.
     * 
     * @return tipologiaIsed
     */
    public java.lang.String getTipologiaIsed() {
        return tipologiaIsed;
    }


    /**
     * Sets the tipologiaIsed value for this CcostImpGestioneRifiuti.
     * 
     * @param tipologiaIsed
     */
    public void setTipologiaIsed(java.lang.String tipologiaIsed) {
        this.tipologiaIsed = tipologiaIsed;
    }


    /**
     * Gets the tipologiaRifiuti value for this CcostImpGestioneRifiuti.
     * 
     * @return tipologiaRifiuti
     */
    public java.lang.String getTipologiaRifiuti() {
        return tipologiaRifiuti;
    }


    /**
     * Sets the tipologiaRifiuti value for this CcostImpGestioneRifiuti.
     * 
     * @param tipologiaRifiuti
     */
    public void setTipologiaRifiuti(java.lang.String tipologiaRifiuti) {
        this.tipologiaRifiuti = tipologiaRifiuti;
    }


    /**
     * Gets the umMa value for this CcostImpGestioneRifiuti.
     * 
     * @return umMa
     */
    public java.lang.String getUmMa() {
        return umMa;
    }


    /**
     * Sets the umMa value for this CcostImpGestioneRifiuti.
     * 
     * @param umMa
     */
    public void setUmMa(java.lang.String umMa) {
        this.umMa = umMa;
    }


    /**
     * Gets the umMarnp value for this CcostImpGestioneRifiuti.
     * 
     * @return umMarnp
     */
    public java.lang.String getUmMarnp() {
        return umMarnp;
    }


    /**
     * Sets the umMarnp value for this CcostImpGestioneRifiuti.
     * 
     * @param umMarnp
     */
    public void setUmMarnp(java.lang.String umMarnp) {
        this.umMarnp = umMarnp;
    }


    /**
     * Gets the umMarp value for this CcostImpGestioneRifiuti.
     * 
     * @return umMarp
     */
    public java.lang.String getUmMarp() {
        return umMarp;
    }


    /**
     * Sets the umMarp value for this CcostImpGestioneRifiuti.
     * 
     * @param umMarp
     */
    public void setUmMarp(java.lang.String umMarp) {
        this.umMarp = umMarp;
    }


    /**
     * Gets the umMr value for this CcostImpGestioneRifiuti.
     * 
     * @return umMr
     */
    public java.lang.String getUmMr() {
        return umMr;
    }


    /**
     * Sets the umMr value for this CcostImpGestioneRifiuti.
     * 
     * @param umMr
     */
    public void setUmMr(java.lang.String umMr) {
        this.umMr = umMr;
    }


    /**
     * Gets the umMrrnp value for this CcostImpGestioneRifiuti.
     * 
     * @return umMrrnp
     */
    public java.lang.String getUmMrrnp() {
        return umMrrnp;
    }


    /**
     * Sets the umMrrnp value for this CcostImpGestioneRifiuti.
     * 
     * @param umMrrnp
     */
    public void setUmMrrnp(java.lang.String umMrrnp) {
        this.umMrrnp = umMrrnp;
    }


    /**
     * Gets the umMrrp value for this CcostImpGestioneRifiuti.
     * 
     * @return umMrrp
     */
    public java.lang.String getUmMrrp() {
        return umMrrp;
    }


    /**
     * Sets the umMrrp value for this CcostImpGestioneRifiuti.
     * 
     * @param umMrrp
     */
    public void setUmMrrp(java.lang.String umMrrp) {
        this.umMrrp = umMrrp;
    }


    /**
     * Gets the umSmr value for this CcostImpGestioneRifiuti.
     * 
     * @return umSmr
     */
    public java.lang.String getUmSmr() {
        return umSmr;
    }


    /**
     * Sets the umSmr value for this CcostImpGestioneRifiuti.
     * 
     * @param umSmr
     */
    public void setUmSmr(java.lang.String umSmr) {
        this.umSmr = umSmr;
    }


    /**
     * Gets the umSmrnp value for this CcostImpGestioneRifiuti.
     * 
     * @return umSmrnp
     */
    public java.lang.String getUmSmrnp() {
        return umSmrnp;
    }


    /**
     * Sets the umSmrnp value for this CcostImpGestioneRifiuti.
     * 
     * @param umSmrnp
     */
    public void setUmSmrnp(java.lang.String umSmrnp) {
        this.umSmrnp = umSmrnp;
    }


    /**
     * Gets the umSmrp value for this CcostImpGestioneRifiuti.
     * 
     * @return umSmrp
     */
    public java.lang.String getUmSmrp() {
        return umSmrp;
    }


    /**
     * Sets the umSmrp value for this CcostImpGestioneRifiuti.
     * 
     * @param umSmrp
     */
    public void setUmSmrp(java.lang.String umSmrp) {
        this.umSmrp = umSmrp;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostImpGestioneRifiuti)) return false;
        CcostImpGestioneRifiuti other = (CcostImpGestioneRifiuti) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.bacinoUtenza==null && other.getBacinoUtenza()==null) || 
             (this.bacinoUtenza!=null &&
              this.bacinoUtenza.equals(other.getBacinoUtenza()))) &&
            ((this.ccostIgrAutodemolizione==null && other.getCcostIgrAutodemolizione()==null) || 
             (this.ccostIgrAutodemolizione!=null &&
              this.ccostIgrAutodemolizione.equals(other.getCcostIgrAutodemolizione()))) &&
            ((this.ccostIgrCoinceneritore==null && other.getCcostIgrCoinceneritore()==null) || 
             (this.ccostIgrCoinceneritore!=null &&
              this.ccostIgrCoinceneritore.equals(other.getCcostIgrCoinceneritore()))) &&
            ((this.ccostIgrCompostaggioCdr==null && other.getCcostIgrCompostaggioCdr()==null) || 
             (this.ccostIgrCompostaggioCdr!=null &&
              this.ccostIgrCompostaggioCdr.equals(other.getCcostIgrCompostaggioCdr()))) &&
            ((this.ccostIgrDiscarica==null && other.getCcostIgrDiscarica()==null) || 
             (this.ccostIgrDiscarica!=null &&
              this.ccostIgrDiscarica.equals(other.getCcostIgrDiscarica()))) &&
            ((this.ccostIgrInceneritore==null && other.getCcostIgrInceneritore()==null) || 
             (this.ccostIgrInceneritore!=null &&
              this.ccostIgrInceneritore.equals(other.getCcostIgrInceneritore()))) &&
            ((this.ccostIgrMobile==null && other.getCcostIgrMobile()==null) || 
             (this.ccostIgrMobile!=null &&
              this.ccostIgrMobile.equals(other.getCcostIgrMobile()))) &&
            ((this.ccostIgrRecupero==null && other.getCcostIgrRecupero()==null) || 
             (this.ccostIgrRecupero!=null &&
              this.ccostIgrRecupero.equals(other.getCcostIgrRecupero()))) &&
            ((this.ccostIgrSelezione==null && other.getCcostIgrSelezione()==null) || 
             (this.ccostIgrSelezione!=null &&
              this.ccostIgrSelezione.equals(other.getCcostIgrSelezione()))) &&
            ((this.ccostIgrStoccaggioProvv==null && other.getCcostIgrStoccaggioProvv()==null) || 
             (this.ccostIgrStoccaggioProvv!=null &&
              this.ccostIgrStoccaggioProvv.equals(other.getCcostIgrStoccaggioProvv()))) &&
            ((this.ccostIgrTrattAnaerobico==null && other.getCcostIgrTrattAnaerobico()==null) || 
             (this.ccostIgrTrattAnaerobico!=null &&
              this.ccostIgrTrattAnaerobico.equals(other.getCcostIgrTrattAnaerobico()))) &&
            ((this.ccostIgrTrattChifisBio==null && other.getCcostIgrTrattChifisBio()==null) || 
             (this.ccostIgrTrattChifisBio!=null &&
              this.ccostIgrTrattChifisBio.equals(other.getCcostIgrTrattChifisBio()))) &&
            ((this.ccostIgrTrattamentoRaee==null && other.getCcostIgrTrattamentoRaee()==null) || 
             (this.ccostIgrTrattamentoRaee!=null &&
              this.ccostIgrTrattamentoRaee.equals(other.getCcostIgrTrattamentoRaee()))) &&
            ((this.garanziaFinanziaria==null && other.getGaranziaFinanziaria()==null) || 
             (this.garanziaFinanziaria!=null &&
              this.garanziaFinanziaria.equals(other.getGaranziaFinanziaria()))) &&
            ((this.garanziaFinanziariaPostOp==null && other.getGaranziaFinanziariaPostOp()==null) || 
             (this.garanziaFinanziariaPostOp!=null &&
              this.garanziaFinanziariaPostOp.equals(other.getGaranziaFinanziariaPostOp()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.inserimentoPpRs==null && other.getInserimentoPpRs()==null) || 
             (this.inserimentoPpRs!=null &&
              this.inserimentoPpRs.equals(other.getInserimentoPpRs()))) &&
            ((this.inserimentoPpRsu==null && other.getInserimentoPpRsu()==null) || 
             (this.inserimentoPpRsu!=null &&
              this.inserimentoPpRsu.equals(other.getInserimentoPpRsu()))) &&
            ((this.messaRiservaMax==null && other.getMessaRiservaMax()==null) || 
             (this.messaRiservaMax!=null &&
              this.messaRiservaMax.equals(other.getMessaRiservaMax()))) &&
            ((this.messaRiservaMaxRnp==null && other.getMessaRiservaMaxRnp()==null) || 
             (this.messaRiservaMaxRnp!=null &&
              this.messaRiservaMaxRnp.equals(other.getMessaRiservaMaxRnp()))) &&
            ((this.messaRiservaMaxRp==null && other.getMessaRiservaMaxRp()==null) || 
             (this.messaRiservaMaxRp!=null &&
              this.messaRiservaMaxRp.equals(other.getMessaRiservaMaxRp()))) &&
            ((this.movimentazioneAnnua==null && other.getMovimentazioneAnnua()==null) || 
             (this.movimentazioneAnnua!=null &&
              this.movimentazioneAnnua.equals(other.getMovimentazioneAnnua()))) &&
            ((this.movimentazioneAnnuaRnp==null && other.getMovimentazioneAnnuaRnp()==null) || 
             (this.movimentazioneAnnuaRnp!=null &&
              this.movimentazioneAnnuaRnp.equals(other.getMovimentazioneAnnuaRnp()))) &&
            ((this.movimentazioneAnnuaRp==null && other.getMovimentazioneAnnuaRp()==null) || 
             (this.movimentazioneAnnuaRp!=null &&
              this.movimentazioneAnnuaRp.equals(other.getMovimentazioneAnnuaRp()))) &&
            ((this.numIscrizioneAlboGestori==null && other.getNumIscrizioneAlboGestori()==null) || 
             (this.numIscrizioneAlboGestori!=null &&
              this.numIscrizioneAlboGestori.equals(other.getNumIscrizioneAlboGestori()))) &&
            ((this.opdl152CcostIgrs==null && other.getOpdl152CcostIgrs()==null) || 
             (this.opdl152CcostIgrs!=null &&
              java.util.Arrays.equals(this.opdl152CcostIgrs, other.getOpdl152CcostIgrs()))) &&
            ((this.stoccaggioMax==null && other.getStoccaggioMax()==null) || 
             (this.stoccaggioMax!=null &&
              this.stoccaggioMax.equals(other.getStoccaggioMax()))) &&
            ((this.stoccaggioMaxRnp==null && other.getStoccaggioMaxRnp()==null) || 
             (this.stoccaggioMaxRnp!=null &&
              this.stoccaggioMaxRnp.equals(other.getStoccaggioMaxRnp()))) &&
            ((this.stoccaggioMaxRp==null && other.getStoccaggioMaxRp()==null) || 
             (this.stoccaggioMaxRp!=null &&
              this.stoccaggioMaxRp.equals(other.getStoccaggioMaxRp()))) &&
            ((this.tipologiaIsed==null && other.getTipologiaIsed()==null) || 
             (this.tipologiaIsed!=null &&
              this.tipologiaIsed.equals(other.getTipologiaIsed()))) &&
            ((this.tipologiaRifiuti==null && other.getTipologiaRifiuti()==null) || 
             (this.tipologiaRifiuti!=null &&
              this.tipologiaRifiuti.equals(other.getTipologiaRifiuti()))) &&
            ((this.umMa==null && other.getUmMa()==null) || 
             (this.umMa!=null &&
              this.umMa.equals(other.getUmMa()))) &&
            ((this.umMarnp==null && other.getUmMarnp()==null) || 
             (this.umMarnp!=null &&
              this.umMarnp.equals(other.getUmMarnp()))) &&
            ((this.umMarp==null && other.getUmMarp()==null) || 
             (this.umMarp!=null &&
              this.umMarp.equals(other.getUmMarp()))) &&
            ((this.umMr==null && other.getUmMr()==null) || 
             (this.umMr!=null &&
              this.umMr.equals(other.getUmMr()))) &&
            ((this.umMrrnp==null && other.getUmMrrnp()==null) || 
             (this.umMrrnp!=null &&
              this.umMrrnp.equals(other.getUmMrrnp()))) &&
            ((this.umMrrp==null && other.getUmMrrp()==null) || 
             (this.umMrrp!=null &&
              this.umMrrp.equals(other.getUmMrrp()))) &&
            ((this.umSmr==null && other.getUmSmr()==null) || 
             (this.umSmr!=null &&
              this.umSmr.equals(other.getUmSmr()))) &&
            ((this.umSmrnp==null && other.getUmSmrnp()==null) || 
             (this.umSmrnp!=null &&
              this.umSmrnp.equals(other.getUmSmrnp()))) &&
            ((this.umSmrp==null && other.getUmSmrp()==null) || 
             (this.umSmrp!=null &&
              this.umSmrp.equals(other.getUmSmrp())));
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
        if (getBacinoUtenza() != null) {
            _hashCode += getBacinoUtenza().hashCode();
        }
        if (getCcostIgrAutodemolizione() != null) {
            _hashCode += getCcostIgrAutodemolizione().hashCode();
        }
        if (getCcostIgrCoinceneritore() != null) {
            _hashCode += getCcostIgrCoinceneritore().hashCode();
        }
        if (getCcostIgrCompostaggioCdr() != null) {
            _hashCode += getCcostIgrCompostaggioCdr().hashCode();
        }
        if (getCcostIgrDiscarica() != null) {
            _hashCode += getCcostIgrDiscarica().hashCode();
        }
        if (getCcostIgrInceneritore() != null) {
            _hashCode += getCcostIgrInceneritore().hashCode();
        }
        if (getCcostIgrMobile() != null) {
            _hashCode += getCcostIgrMobile().hashCode();
        }
        if (getCcostIgrRecupero() != null) {
            _hashCode += getCcostIgrRecupero().hashCode();
        }
        if (getCcostIgrSelezione() != null) {
            _hashCode += getCcostIgrSelezione().hashCode();
        }
        if (getCcostIgrStoccaggioProvv() != null) {
            _hashCode += getCcostIgrStoccaggioProvv().hashCode();
        }
        if (getCcostIgrTrattAnaerobico() != null) {
            _hashCode += getCcostIgrTrattAnaerobico().hashCode();
        }
        if (getCcostIgrTrattChifisBio() != null) {
            _hashCode += getCcostIgrTrattChifisBio().hashCode();
        }
        if (getCcostIgrTrattamentoRaee() != null) {
            _hashCode += getCcostIgrTrattamentoRaee().hashCode();
        }
        if (getGaranziaFinanziaria() != null) {
            _hashCode += getGaranziaFinanziaria().hashCode();
        }
        if (getGaranziaFinanziariaPostOp() != null) {
            _hashCode += getGaranziaFinanziariaPostOp().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getInserimentoPpRs() != null) {
            _hashCode += getInserimentoPpRs().hashCode();
        }
        if (getInserimentoPpRsu() != null) {
            _hashCode += getInserimentoPpRsu().hashCode();
        }
        if (getMessaRiservaMax() != null) {
            _hashCode += getMessaRiservaMax().hashCode();
        }
        if (getMessaRiservaMaxRnp() != null) {
            _hashCode += getMessaRiservaMaxRnp().hashCode();
        }
        if (getMessaRiservaMaxRp() != null) {
            _hashCode += getMessaRiservaMaxRp().hashCode();
        }
        if (getMovimentazioneAnnua() != null) {
            _hashCode += getMovimentazioneAnnua().hashCode();
        }
        if (getMovimentazioneAnnuaRnp() != null) {
            _hashCode += getMovimentazioneAnnuaRnp().hashCode();
        }
        if (getMovimentazioneAnnuaRp() != null) {
            _hashCode += getMovimentazioneAnnuaRp().hashCode();
        }
        if (getNumIscrizioneAlboGestori() != null) {
            _hashCode += getNumIscrizioneAlboGestori().hashCode();
        }
        if (getOpdl152CcostIgrs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOpdl152CcostIgrs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOpdl152CcostIgrs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStoccaggioMax() != null) {
            _hashCode += getStoccaggioMax().hashCode();
        }
        if (getStoccaggioMaxRnp() != null) {
            _hashCode += getStoccaggioMaxRnp().hashCode();
        }
        if (getStoccaggioMaxRp() != null) {
            _hashCode += getStoccaggioMaxRp().hashCode();
        }
        if (getTipologiaIsed() != null) {
            _hashCode += getTipologiaIsed().hashCode();
        }
        if (getTipologiaRifiuti() != null) {
            _hashCode += getTipologiaRifiuti().hashCode();
        }
        if (getUmMa() != null) {
            _hashCode += getUmMa().hashCode();
        }
        if (getUmMarnp() != null) {
            _hashCode += getUmMarnp().hashCode();
        }
        if (getUmMarp() != null) {
            _hashCode += getUmMarp().hashCode();
        }
        if (getUmMr() != null) {
            _hashCode += getUmMr().hashCode();
        }
        if (getUmMrrnp() != null) {
            _hashCode += getUmMrrnp().hashCode();
        }
        if (getUmMrrp() != null) {
            _hashCode += getUmMrrp().hashCode();
        }
        if (getUmSmr() != null) {
            _hashCode += getUmSmr().hashCode();
        }
        if (getUmSmrnp() != null) {
            _hashCode += getUmSmrnp().hashCode();
        }
        if (getUmSmrp() != null) {
            _hashCode += getUmSmrp().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostImpGestioneRifiuti.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpGestioneRifiuti"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bacinoUtenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bacinoUtenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostIgrAutodemolizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostIgrAutodemolizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrAutodemolizione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostIgrCoinceneritore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostIgrCoinceneritore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrCoinceneritore"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostIgrCompostaggioCdr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostIgrCompostaggioCdr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrCompostaggioCdr"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostIgrDiscarica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostIgrDiscarica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrDiscarica"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostIgrInceneritore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostIgrInceneritore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrInceneritore"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostIgrMobile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostIgrMobile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrMobile"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostIgrRecupero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostIgrRecupero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrRecupero"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostIgrSelezione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostIgrSelezione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrSelezione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostIgrStoccaggioProvv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostIgrStoccaggioProvv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrStoccaggioProvv"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostIgrTrattAnaerobico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostIgrTrattAnaerobico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrTrattAnaerobico"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostIgrTrattChifisBio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostIgrTrattChifisBio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrTrattChifisBio"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostIgrTrattamentoRaee");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostIgrTrattamentoRaee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrTrattamentoRaee"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("garanziaFinanziaria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "garanziaFinanziaria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("garanziaFinanziariaPostOp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "garanziaFinanziariaPostOp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("inserimentoPpRs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inserimentoPpRs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inserimentoPpRsu");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inserimentoPpRsu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messaRiservaMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "messaRiservaMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messaRiservaMaxRnp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "messaRiservaMaxRnp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messaRiservaMaxRp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "messaRiservaMaxRp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("movimentazioneAnnua");
        elemField.setXmlName(new javax.xml.namespace.QName("", "movimentazioneAnnua"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("movimentazioneAnnuaRnp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "movimentazioneAnnuaRnp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("movimentazioneAnnuaRp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "movimentazioneAnnuaRp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numIscrizioneAlboGestori");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numIscrizioneAlboGestori"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opdl152CcostIgrs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opdl152CcostIgrs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "opdl152CcostIgr"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stoccaggioMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stoccaggioMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stoccaggioMaxRnp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stoccaggioMaxRnp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stoccaggioMaxRp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stoccaggioMaxRp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologiaIsed");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologiaIsed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologiaRifiuti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologiaRifiuti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umMa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umMa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umMarnp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umMarnp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umMarp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umMarp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umMr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umMr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umMrrnp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umMrrnp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umMrrp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umMrrp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umSmr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umSmr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umSmrnp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umSmrnp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umSmrp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umSmrp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
