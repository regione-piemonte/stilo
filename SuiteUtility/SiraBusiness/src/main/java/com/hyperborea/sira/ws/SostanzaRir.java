/**
 * SostanzaRir.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class SostanzaRir  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String codiceCas;

    private java.lang.String codiceOnu;

    private java.lang.String colore;

    private java.lang.Float densitaCondensato;

    private java.lang.String etichette;

    private java.lang.String fomulaMinima;

    private java.lang.String formulaMolecolare;

    private java.lang.String formulaStruttura;

    private java.lang.String frasiR;

    private java.lang.String frasiS;

    private java.lang.Integer idSostanzaRir;

    private java.lang.Float limiteArt5;

    private java.lang.Float limiteArt6;

    private java.lang.Float limiteArt8;

    private java.lang.Float limiteInfiammabilitaInf;

    private java.lang.Float limiteInfiammabilitaSup;

    private java.lang.String nomeCas;

    private java.lang.String nomeSostanza;

    private java.lang.String odore;

    private java.lang.Float pesoSpecificoVapori;

    private java.lang.Float puntoEbollizione;

    private java.lang.Float puntoFusione;

    private java.lang.Float puntoInfiammabilita;

    private java.lang.String reazioniPericolose;

    private com.hyperborea.sira.ws.Solventi[] solventis;

    private com.hyperborea.sira.ws.Sostanze334 sostanza334;

    private com.hyperborea.sira.ws.SostanzaRirClass334[] sostanzaRirClass334S;

    private com.hyperborea.sira.ws.VocStatoFisico statoFisico;

    private java.lang.Float tempAutoAccensione;

    private java.lang.Float tensioneVapore;

    public SostanzaRir() {
    }

    public SostanzaRir(
           java.lang.String codiceCas,
           java.lang.String codiceOnu,
           java.lang.String colore,
           java.lang.Float densitaCondensato,
           java.lang.String etichette,
           java.lang.String fomulaMinima,
           java.lang.String formulaMolecolare,
           java.lang.String formulaStruttura,
           java.lang.String frasiR,
           java.lang.String frasiS,
           java.lang.Integer idSostanzaRir,
           java.lang.Float limiteArt5,
           java.lang.Float limiteArt6,
           java.lang.Float limiteArt8,
           java.lang.Float limiteInfiammabilitaInf,
           java.lang.Float limiteInfiammabilitaSup,
           java.lang.String nomeCas,
           java.lang.String nomeSostanza,
           java.lang.String odore,
           java.lang.Float pesoSpecificoVapori,
           java.lang.Float puntoEbollizione,
           java.lang.Float puntoFusione,
           java.lang.Float puntoInfiammabilita,
           java.lang.String reazioniPericolose,
           com.hyperborea.sira.ws.Solventi[] solventis,
           com.hyperborea.sira.ws.Sostanze334 sostanza334,
           com.hyperborea.sira.ws.SostanzaRirClass334[] sostanzaRirClass334S,
           com.hyperborea.sira.ws.VocStatoFisico statoFisico,
           java.lang.Float tempAutoAccensione,
           java.lang.Float tensioneVapore) {
        this.codiceCas = codiceCas;
        this.codiceOnu = codiceOnu;
        this.colore = colore;
        this.densitaCondensato = densitaCondensato;
        this.etichette = etichette;
        this.fomulaMinima = fomulaMinima;
        this.formulaMolecolare = formulaMolecolare;
        this.formulaStruttura = formulaStruttura;
        this.frasiR = frasiR;
        this.frasiS = frasiS;
        this.idSostanzaRir = idSostanzaRir;
        this.limiteArt5 = limiteArt5;
        this.limiteArt6 = limiteArt6;
        this.limiteArt8 = limiteArt8;
        this.limiteInfiammabilitaInf = limiteInfiammabilitaInf;
        this.limiteInfiammabilitaSup = limiteInfiammabilitaSup;
        this.nomeCas = nomeCas;
        this.nomeSostanza = nomeSostanza;
        this.odore = odore;
        this.pesoSpecificoVapori = pesoSpecificoVapori;
        this.puntoEbollizione = puntoEbollizione;
        this.puntoFusione = puntoFusione;
        this.puntoInfiammabilita = puntoInfiammabilita;
        this.reazioniPericolose = reazioniPericolose;
        this.solventis = solventis;
        this.sostanza334 = sostanza334;
        this.sostanzaRirClass334S = sostanzaRirClass334S;
        this.statoFisico = statoFisico;
        this.tempAutoAccensione = tempAutoAccensione;
        this.tensioneVapore = tensioneVapore;
    }


    /**
     * Gets the codiceCas value for this SostanzaRir.
     * 
     * @return codiceCas
     */
    public java.lang.String getCodiceCas() {
        return codiceCas;
    }


    /**
     * Sets the codiceCas value for this SostanzaRir.
     * 
     * @param codiceCas
     */
    public void setCodiceCas(java.lang.String codiceCas) {
        this.codiceCas = codiceCas;
    }


    /**
     * Gets the codiceOnu value for this SostanzaRir.
     * 
     * @return codiceOnu
     */
    public java.lang.String getCodiceOnu() {
        return codiceOnu;
    }


    /**
     * Sets the codiceOnu value for this SostanzaRir.
     * 
     * @param codiceOnu
     */
    public void setCodiceOnu(java.lang.String codiceOnu) {
        this.codiceOnu = codiceOnu;
    }


    /**
     * Gets the colore value for this SostanzaRir.
     * 
     * @return colore
     */
    public java.lang.String getColore() {
        return colore;
    }


    /**
     * Sets the colore value for this SostanzaRir.
     * 
     * @param colore
     */
    public void setColore(java.lang.String colore) {
        this.colore = colore;
    }


    /**
     * Gets the densitaCondensato value for this SostanzaRir.
     * 
     * @return densitaCondensato
     */
    public java.lang.Float getDensitaCondensato() {
        return densitaCondensato;
    }


    /**
     * Sets the densitaCondensato value for this SostanzaRir.
     * 
     * @param densitaCondensato
     */
    public void setDensitaCondensato(java.lang.Float densitaCondensato) {
        this.densitaCondensato = densitaCondensato;
    }


    /**
     * Gets the etichette value for this SostanzaRir.
     * 
     * @return etichette
     */
    public java.lang.String getEtichette() {
        return etichette;
    }


    /**
     * Sets the etichette value for this SostanzaRir.
     * 
     * @param etichette
     */
    public void setEtichette(java.lang.String etichette) {
        this.etichette = etichette;
    }


    /**
     * Gets the fomulaMinima value for this SostanzaRir.
     * 
     * @return fomulaMinima
     */
    public java.lang.String getFomulaMinima() {
        return fomulaMinima;
    }


    /**
     * Sets the fomulaMinima value for this SostanzaRir.
     * 
     * @param fomulaMinima
     */
    public void setFomulaMinima(java.lang.String fomulaMinima) {
        this.fomulaMinima = fomulaMinima;
    }


    /**
     * Gets the formulaMolecolare value for this SostanzaRir.
     * 
     * @return formulaMolecolare
     */
    public java.lang.String getFormulaMolecolare() {
        return formulaMolecolare;
    }


    /**
     * Sets the formulaMolecolare value for this SostanzaRir.
     * 
     * @param formulaMolecolare
     */
    public void setFormulaMolecolare(java.lang.String formulaMolecolare) {
        this.formulaMolecolare = formulaMolecolare;
    }


    /**
     * Gets the formulaStruttura value for this SostanzaRir.
     * 
     * @return formulaStruttura
     */
    public java.lang.String getFormulaStruttura() {
        return formulaStruttura;
    }


    /**
     * Sets the formulaStruttura value for this SostanzaRir.
     * 
     * @param formulaStruttura
     */
    public void setFormulaStruttura(java.lang.String formulaStruttura) {
        this.formulaStruttura = formulaStruttura;
    }


    /**
     * Gets the frasiR value for this SostanzaRir.
     * 
     * @return frasiR
     */
    public java.lang.String getFrasiR() {
        return frasiR;
    }


    /**
     * Sets the frasiR value for this SostanzaRir.
     * 
     * @param frasiR
     */
    public void setFrasiR(java.lang.String frasiR) {
        this.frasiR = frasiR;
    }


    /**
     * Gets the frasiS value for this SostanzaRir.
     * 
     * @return frasiS
     */
    public java.lang.String getFrasiS() {
        return frasiS;
    }


    /**
     * Sets the frasiS value for this SostanzaRir.
     * 
     * @param frasiS
     */
    public void setFrasiS(java.lang.String frasiS) {
        this.frasiS = frasiS;
    }


    /**
     * Gets the idSostanzaRir value for this SostanzaRir.
     * 
     * @return idSostanzaRir
     */
    public java.lang.Integer getIdSostanzaRir() {
        return idSostanzaRir;
    }


    /**
     * Sets the idSostanzaRir value for this SostanzaRir.
     * 
     * @param idSostanzaRir
     */
    public void setIdSostanzaRir(java.lang.Integer idSostanzaRir) {
        this.idSostanzaRir = idSostanzaRir;
    }


    /**
     * Gets the limiteArt5 value for this SostanzaRir.
     * 
     * @return limiteArt5
     */
    public java.lang.Float getLimiteArt5() {
        return limiteArt5;
    }


    /**
     * Sets the limiteArt5 value for this SostanzaRir.
     * 
     * @param limiteArt5
     */
    public void setLimiteArt5(java.lang.Float limiteArt5) {
        this.limiteArt5 = limiteArt5;
    }


    /**
     * Gets the limiteArt6 value for this SostanzaRir.
     * 
     * @return limiteArt6
     */
    public java.lang.Float getLimiteArt6() {
        return limiteArt6;
    }


    /**
     * Sets the limiteArt6 value for this SostanzaRir.
     * 
     * @param limiteArt6
     */
    public void setLimiteArt6(java.lang.Float limiteArt6) {
        this.limiteArt6 = limiteArt6;
    }


    /**
     * Gets the limiteArt8 value for this SostanzaRir.
     * 
     * @return limiteArt8
     */
    public java.lang.Float getLimiteArt8() {
        return limiteArt8;
    }


    /**
     * Sets the limiteArt8 value for this SostanzaRir.
     * 
     * @param limiteArt8
     */
    public void setLimiteArt8(java.lang.Float limiteArt8) {
        this.limiteArt8 = limiteArt8;
    }


    /**
     * Gets the limiteInfiammabilitaInf value for this SostanzaRir.
     * 
     * @return limiteInfiammabilitaInf
     */
    public java.lang.Float getLimiteInfiammabilitaInf() {
        return limiteInfiammabilitaInf;
    }


    /**
     * Sets the limiteInfiammabilitaInf value for this SostanzaRir.
     * 
     * @param limiteInfiammabilitaInf
     */
    public void setLimiteInfiammabilitaInf(java.lang.Float limiteInfiammabilitaInf) {
        this.limiteInfiammabilitaInf = limiteInfiammabilitaInf;
    }


    /**
     * Gets the limiteInfiammabilitaSup value for this SostanzaRir.
     * 
     * @return limiteInfiammabilitaSup
     */
    public java.lang.Float getLimiteInfiammabilitaSup() {
        return limiteInfiammabilitaSup;
    }


    /**
     * Sets the limiteInfiammabilitaSup value for this SostanzaRir.
     * 
     * @param limiteInfiammabilitaSup
     */
    public void setLimiteInfiammabilitaSup(java.lang.Float limiteInfiammabilitaSup) {
        this.limiteInfiammabilitaSup = limiteInfiammabilitaSup;
    }


    /**
     * Gets the nomeCas value for this SostanzaRir.
     * 
     * @return nomeCas
     */
    public java.lang.String getNomeCas() {
        return nomeCas;
    }


    /**
     * Sets the nomeCas value for this SostanzaRir.
     * 
     * @param nomeCas
     */
    public void setNomeCas(java.lang.String nomeCas) {
        this.nomeCas = nomeCas;
    }


    /**
     * Gets the nomeSostanza value for this SostanzaRir.
     * 
     * @return nomeSostanza
     */
    public java.lang.String getNomeSostanza() {
        return nomeSostanza;
    }


    /**
     * Sets the nomeSostanza value for this SostanzaRir.
     * 
     * @param nomeSostanza
     */
    public void setNomeSostanza(java.lang.String nomeSostanza) {
        this.nomeSostanza = nomeSostanza;
    }


    /**
     * Gets the odore value for this SostanzaRir.
     * 
     * @return odore
     */
    public java.lang.String getOdore() {
        return odore;
    }


    /**
     * Sets the odore value for this SostanzaRir.
     * 
     * @param odore
     */
    public void setOdore(java.lang.String odore) {
        this.odore = odore;
    }


    /**
     * Gets the pesoSpecificoVapori value for this SostanzaRir.
     * 
     * @return pesoSpecificoVapori
     */
    public java.lang.Float getPesoSpecificoVapori() {
        return pesoSpecificoVapori;
    }


    /**
     * Sets the pesoSpecificoVapori value for this SostanzaRir.
     * 
     * @param pesoSpecificoVapori
     */
    public void setPesoSpecificoVapori(java.lang.Float pesoSpecificoVapori) {
        this.pesoSpecificoVapori = pesoSpecificoVapori;
    }


    /**
     * Gets the puntoEbollizione value for this SostanzaRir.
     * 
     * @return puntoEbollizione
     */
    public java.lang.Float getPuntoEbollizione() {
        return puntoEbollizione;
    }


    /**
     * Sets the puntoEbollizione value for this SostanzaRir.
     * 
     * @param puntoEbollizione
     */
    public void setPuntoEbollizione(java.lang.Float puntoEbollizione) {
        this.puntoEbollizione = puntoEbollizione;
    }


    /**
     * Gets the puntoFusione value for this SostanzaRir.
     * 
     * @return puntoFusione
     */
    public java.lang.Float getPuntoFusione() {
        return puntoFusione;
    }


    /**
     * Sets the puntoFusione value for this SostanzaRir.
     * 
     * @param puntoFusione
     */
    public void setPuntoFusione(java.lang.Float puntoFusione) {
        this.puntoFusione = puntoFusione;
    }


    /**
     * Gets the puntoInfiammabilita value for this SostanzaRir.
     * 
     * @return puntoInfiammabilita
     */
    public java.lang.Float getPuntoInfiammabilita() {
        return puntoInfiammabilita;
    }


    /**
     * Sets the puntoInfiammabilita value for this SostanzaRir.
     * 
     * @param puntoInfiammabilita
     */
    public void setPuntoInfiammabilita(java.lang.Float puntoInfiammabilita) {
        this.puntoInfiammabilita = puntoInfiammabilita;
    }


    /**
     * Gets the reazioniPericolose value for this SostanzaRir.
     * 
     * @return reazioniPericolose
     */
    public java.lang.String getReazioniPericolose() {
        return reazioniPericolose;
    }


    /**
     * Sets the reazioniPericolose value for this SostanzaRir.
     * 
     * @param reazioniPericolose
     */
    public void setReazioniPericolose(java.lang.String reazioniPericolose) {
        this.reazioniPericolose = reazioniPericolose;
    }


    /**
     * Gets the solventis value for this SostanzaRir.
     * 
     * @return solventis
     */
    public com.hyperborea.sira.ws.Solventi[] getSolventis() {
        return solventis;
    }


    /**
     * Sets the solventis value for this SostanzaRir.
     * 
     * @param solventis
     */
    public void setSolventis(com.hyperborea.sira.ws.Solventi[] solventis) {
        this.solventis = solventis;
    }

    public com.hyperborea.sira.ws.Solventi getSolventis(int i) {
        return this.solventis[i];
    }

    public void setSolventis(int i, com.hyperborea.sira.ws.Solventi _value) {
        this.solventis[i] = _value;
    }


    /**
     * Gets the sostanza334 value for this SostanzaRir.
     * 
     * @return sostanza334
     */
    public com.hyperborea.sira.ws.Sostanze334 getSostanza334() {
        return sostanza334;
    }


    /**
     * Sets the sostanza334 value for this SostanzaRir.
     * 
     * @param sostanza334
     */
    public void setSostanza334(com.hyperborea.sira.ws.Sostanze334 sostanza334) {
        this.sostanza334 = sostanza334;
    }


    /**
     * Gets the sostanzaRirClass334S value for this SostanzaRir.
     * 
     * @return sostanzaRirClass334S
     */
    public com.hyperborea.sira.ws.SostanzaRirClass334[] getSostanzaRirClass334S() {
        return sostanzaRirClass334S;
    }


    /**
     * Sets the sostanzaRirClass334S value for this SostanzaRir.
     * 
     * @param sostanzaRirClass334S
     */
    public void setSostanzaRirClass334S(com.hyperborea.sira.ws.SostanzaRirClass334[] sostanzaRirClass334S) {
        this.sostanzaRirClass334S = sostanzaRirClass334S;
    }

    public com.hyperborea.sira.ws.SostanzaRirClass334 getSostanzaRirClass334S(int i) {
        return this.sostanzaRirClass334S[i];
    }

    public void setSostanzaRirClass334S(int i, com.hyperborea.sira.ws.SostanzaRirClass334 _value) {
        this.sostanzaRirClass334S[i] = _value;
    }


    /**
     * Gets the statoFisico value for this SostanzaRir.
     * 
     * @return statoFisico
     */
    public com.hyperborea.sira.ws.VocStatoFisico getStatoFisico() {
        return statoFisico;
    }


    /**
     * Sets the statoFisico value for this SostanzaRir.
     * 
     * @param statoFisico
     */
    public void setStatoFisico(com.hyperborea.sira.ws.VocStatoFisico statoFisico) {
        this.statoFisico = statoFisico;
    }


    /**
     * Gets the tempAutoAccensione value for this SostanzaRir.
     * 
     * @return tempAutoAccensione
     */
    public java.lang.Float getTempAutoAccensione() {
        return tempAutoAccensione;
    }


    /**
     * Sets the tempAutoAccensione value for this SostanzaRir.
     * 
     * @param tempAutoAccensione
     */
    public void setTempAutoAccensione(java.lang.Float tempAutoAccensione) {
        this.tempAutoAccensione = tempAutoAccensione;
    }


    /**
     * Gets the tensioneVapore value for this SostanzaRir.
     * 
     * @return tensioneVapore
     */
    public java.lang.Float getTensioneVapore() {
        return tensioneVapore;
    }


    /**
     * Sets the tensioneVapore value for this SostanzaRir.
     * 
     * @param tensioneVapore
     */
    public void setTensioneVapore(java.lang.Float tensioneVapore) {
        this.tensioneVapore = tensioneVapore;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SostanzaRir)) return false;
        SostanzaRir other = (SostanzaRir) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codiceCas==null && other.getCodiceCas()==null) || 
             (this.codiceCas!=null &&
              this.codiceCas.equals(other.getCodiceCas()))) &&
            ((this.codiceOnu==null && other.getCodiceOnu()==null) || 
             (this.codiceOnu!=null &&
              this.codiceOnu.equals(other.getCodiceOnu()))) &&
            ((this.colore==null && other.getColore()==null) || 
             (this.colore!=null &&
              this.colore.equals(other.getColore()))) &&
            ((this.densitaCondensato==null && other.getDensitaCondensato()==null) || 
             (this.densitaCondensato!=null &&
              this.densitaCondensato.equals(other.getDensitaCondensato()))) &&
            ((this.etichette==null && other.getEtichette()==null) || 
             (this.etichette!=null &&
              this.etichette.equals(other.getEtichette()))) &&
            ((this.fomulaMinima==null && other.getFomulaMinima()==null) || 
             (this.fomulaMinima!=null &&
              this.fomulaMinima.equals(other.getFomulaMinima()))) &&
            ((this.formulaMolecolare==null && other.getFormulaMolecolare()==null) || 
             (this.formulaMolecolare!=null &&
              this.formulaMolecolare.equals(other.getFormulaMolecolare()))) &&
            ((this.formulaStruttura==null && other.getFormulaStruttura()==null) || 
             (this.formulaStruttura!=null &&
              this.formulaStruttura.equals(other.getFormulaStruttura()))) &&
            ((this.frasiR==null && other.getFrasiR()==null) || 
             (this.frasiR!=null &&
              this.frasiR.equals(other.getFrasiR()))) &&
            ((this.frasiS==null && other.getFrasiS()==null) || 
             (this.frasiS!=null &&
              this.frasiS.equals(other.getFrasiS()))) &&
            ((this.idSostanzaRir==null && other.getIdSostanzaRir()==null) || 
             (this.idSostanzaRir!=null &&
              this.idSostanzaRir.equals(other.getIdSostanzaRir()))) &&
            ((this.limiteArt5==null && other.getLimiteArt5()==null) || 
             (this.limiteArt5!=null &&
              this.limiteArt5.equals(other.getLimiteArt5()))) &&
            ((this.limiteArt6==null && other.getLimiteArt6()==null) || 
             (this.limiteArt6!=null &&
              this.limiteArt6.equals(other.getLimiteArt6()))) &&
            ((this.limiteArt8==null && other.getLimiteArt8()==null) || 
             (this.limiteArt8!=null &&
              this.limiteArt8.equals(other.getLimiteArt8()))) &&
            ((this.limiteInfiammabilitaInf==null && other.getLimiteInfiammabilitaInf()==null) || 
             (this.limiteInfiammabilitaInf!=null &&
              this.limiteInfiammabilitaInf.equals(other.getLimiteInfiammabilitaInf()))) &&
            ((this.limiteInfiammabilitaSup==null && other.getLimiteInfiammabilitaSup()==null) || 
             (this.limiteInfiammabilitaSup!=null &&
              this.limiteInfiammabilitaSup.equals(other.getLimiteInfiammabilitaSup()))) &&
            ((this.nomeCas==null && other.getNomeCas()==null) || 
             (this.nomeCas!=null &&
              this.nomeCas.equals(other.getNomeCas()))) &&
            ((this.nomeSostanza==null && other.getNomeSostanza()==null) || 
             (this.nomeSostanza!=null &&
              this.nomeSostanza.equals(other.getNomeSostanza()))) &&
            ((this.odore==null && other.getOdore()==null) || 
             (this.odore!=null &&
              this.odore.equals(other.getOdore()))) &&
            ((this.pesoSpecificoVapori==null && other.getPesoSpecificoVapori()==null) || 
             (this.pesoSpecificoVapori!=null &&
              this.pesoSpecificoVapori.equals(other.getPesoSpecificoVapori()))) &&
            ((this.puntoEbollizione==null && other.getPuntoEbollizione()==null) || 
             (this.puntoEbollizione!=null &&
              this.puntoEbollizione.equals(other.getPuntoEbollizione()))) &&
            ((this.puntoFusione==null && other.getPuntoFusione()==null) || 
             (this.puntoFusione!=null &&
              this.puntoFusione.equals(other.getPuntoFusione()))) &&
            ((this.puntoInfiammabilita==null && other.getPuntoInfiammabilita()==null) || 
             (this.puntoInfiammabilita!=null &&
              this.puntoInfiammabilita.equals(other.getPuntoInfiammabilita()))) &&
            ((this.reazioniPericolose==null && other.getReazioniPericolose()==null) || 
             (this.reazioniPericolose!=null &&
              this.reazioniPericolose.equals(other.getReazioniPericolose()))) &&
            ((this.solventis==null && other.getSolventis()==null) || 
             (this.solventis!=null &&
              java.util.Arrays.equals(this.solventis, other.getSolventis()))) &&
            ((this.sostanza334==null && other.getSostanza334()==null) || 
             (this.sostanza334!=null &&
              this.sostanza334.equals(other.getSostanza334()))) &&
            ((this.sostanzaRirClass334S==null && other.getSostanzaRirClass334S()==null) || 
             (this.sostanzaRirClass334S!=null &&
              java.util.Arrays.equals(this.sostanzaRirClass334S, other.getSostanzaRirClass334S()))) &&
            ((this.statoFisico==null && other.getStatoFisico()==null) || 
             (this.statoFisico!=null &&
              this.statoFisico.equals(other.getStatoFisico()))) &&
            ((this.tempAutoAccensione==null && other.getTempAutoAccensione()==null) || 
             (this.tempAutoAccensione!=null &&
              this.tempAutoAccensione.equals(other.getTempAutoAccensione()))) &&
            ((this.tensioneVapore==null && other.getTensioneVapore()==null) || 
             (this.tensioneVapore!=null &&
              this.tensioneVapore.equals(other.getTensioneVapore())));
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
        if (getCodiceCas() != null) {
            _hashCode += getCodiceCas().hashCode();
        }
        if (getCodiceOnu() != null) {
            _hashCode += getCodiceOnu().hashCode();
        }
        if (getColore() != null) {
            _hashCode += getColore().hashCode();
        }
        if (getDensitaCondensato() != null) {
            _hashCode += getDensitaCondensato().hashCode();
        }
        if (getEtichette() != null) {
            _hashCode += getEtichette().hashCode();
        }
        if (getFomulaMinima() != null) {
            _hashCode += getFomulaMinima().hashCode();
        }
        if (getFormulaMolecolare() != null) {
            _hashCode += getFormulaMolecolare().hashCode();
        }
        if (getFormulaStruttura() != null) {
            _hashCode += getFormulaStruttura().hashCode();
        }
        if (getFrasiR() != null) {
            _hashCode += getFrasiR().hashCode();
        }
        if (getFrasiS() != null) {
            _hashCode += getFrasiS().hashCode();
        }
        if (getIdSostanzaRir() != null) {
            _hashCode += getIdSostanzaRir().hashCode();
        }
        if (getLimiteArt5() != null) {
            _hashCode += getLimiteArt5().hashCode();
        }
        if (getLimiteArt6() != null) {
            _hashCode += getLimiteArt6().hashCode();
        }
        if (getLimiteArt8() != null) {
            _hashCode += getLimiteArt8().hashCode();
        }
        if (getLimiteInfiammabilitaInf() != null) {
            _hashCode += getLimiteInfiammabilitaInf().hashCode();
        }
        if (getLimiteInfiammabilitaSup() != null) {
            _hashCode += getLimiteInfiammabilitaSup().hashCode();
        }
        if (getNomeCas() != null) {
            _hashCode += getNomeCas().hashCode();
        }
        if (getNomeSostanza() != null) {
            _hashCode += getNomeSostanza().hashCode();
        }
        if (getOdore() != null) {
            _hashCode += getOdore().hashCode();
        }
        if (getPesoSpecificoVapori() != null) {
            _hashCode += getPesoSpecificoVapori().hashCode();
        }
        if (getPuntoEbollizione() != null) {
            _hashCode += getPuntoEbollizione().hashCode();
        }
        if (getPuntoFusione() != null) {
            _hashCode += getPuntoFusione().hashCode();
        }
        if (getPuntoInfiammabilita() != null) {
            _hashCode += getPuntoInfiammabilita().hashCode();
        }
        if (getReazioniPericolose() != null) {
            _hashCode += getReazioniPericolose().hashCode();
        }
        if (getSolventis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSolventis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSolventis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSostanza334() != null) {
            _hashCode += getSostanza334().hashCode();
        }
        if (getSostanzaRirClass334S() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSostanzaRirClass334S());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSostanzaRirClass334S(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStatoFisico() != null) {
            _hashCode += getStatoFisico().hashCode();
        }
        if (getTempAutoAccensione() != null) {
            _hashCode += getTempAutoAccensione().hashCode();
        }
        if (getTensioneVapore() != null) {
            _hashCode += getTensioneVapore().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SostanzaRir.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostanzaRir"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceCas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceCas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceOnu");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceOnu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("colore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "colore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("densitaCondensato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "densitaCondensato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("etichette");
        elemField.setXmlName(new javax.xml.namespace.QName("", "etichette"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fomulaMinima");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fomulaMinima"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formulaMolecolare");
        elemField.setXmlName(new javax.xml.namespace.QName("", "formulaMolecolare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formulaStruttura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "formulaStruttura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("frasiR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "frasiR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("frasiS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "frasiS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSostanzaRir");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idSostanzaRir"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limiteArt5");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limiteArt5"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limiteArt6");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limiteArt6"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limiteArt8");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limiteArt8"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limiteInfiammabilitaInf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limiteInfiammabilitaInf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limiteInfiammabilitaSup");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limiteInfiammabilitaSup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeCas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeCas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeSostanza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeSostanza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("odore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "odore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pesoSpecificoVapori");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pesoSpecificoVapori"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("puntoEbollizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "puntoEbollizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("puntoFusione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "puntoFusione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("puntoInfiammabilita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "puntoInfiammabilita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reazioniPericolose");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reazioniPericolose"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("solventis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "solventis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "solventi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sostanza334");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sostanza334"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostanze334"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sostanzaRirClass334S");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sostanzaRirClass334s"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostanzaRirClass334"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoFisico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statoFisico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocStatoFisico"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tempAutoAccensione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tempAutoAccensione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tensioneVapore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tensioneVapore"));
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
