/**
 * CcostImpdepurazione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostImpdepurazione  implements java.io.Serializable {
    private java.lang.Integer abitantiServiti;

    private java.lang.String adeguamento152;

    private java.lang.Integer alberi;

    private java.lang.String altroLaboratorio;

    private java.lang.String altroTipoTrattamento;

    private java.lang.Integer annoChiusura;

    private java.lang.Integer annoEsercizio;

    private java.lang.Integer annoVolBollettato;

    private java.lang.Integer annoVolumeTrattato;

    private java.lang.Integer asfalto;

    private java.lang.Integer automazione;

    private java.lang.Float bod5;

    private java.lang.Integer cancello;

    private java.lang.Float capIdrProg;

    private java.lang.Float capIdrServ;

    private java.lang.Float capOrgProg;

    private java.lang.Float capOrgServ;

    private java.lang.Float capacitaEstProgetto;

    private java.lang.Float capacitaEstPrra;

    private java.lang.Float capacitaInvProgetto;

    private java.lang.Float capacitaInvPrra;

    private java.lang.Float caricoOrganicoMedio;

    private java.lang.Integer cemento;

    private java.lang.String codiceNazionale;

    private java.lang.String codiceRegionale;

    private java.lang.Integer competenzaConsortile;

    private java.lang.Integer competenzaPrra;

    private java.lang.Float costoGestione;

    private com.hyperborea.sira.ws.DepuratoreCompetenzaPrra[] depuratoreCompetenzaPrras;

    private com.hyperborea.sira.ws.DepuratoreCompetenza[] depuratoreCompetenzas;

    private com.hyperborea.sira.ws.DepuratoreSezioni[] depuratoreSezionis;

    private com.hyperborea.sira.ws.DepuratoreTipoOpere[] depuratoreTipoOperes;

    private java.lang.String destLineaFanghi;

    private java.lang.Float distanzaAbitazioni;

    private java.lang.Integer equivalentiIndustriali;

    private java.lang.Integer fluttuanti;

    private java.lang.Integer fognaturaSeparata;

    private java.lang.Integer idCcost;

    private java.lang.String illuminazione;

    private java.lang.Integer informatizzazioneDati;

    private java.lang.Integer labAutoanalisiCent;

    private java.lang.Integer labAutoanalisiExt;

    private java.lang.Integer labAutoanalisiInt;

    private org.apache.axis.types.UnsignedShort lineaFanghi;

    private java.lang.String localitaSmaltimento;

    private java.lang.String mesePunta;

    private java.lang.Integer misuratorePortata;

    private java.lang.Integer misuratoriOnline;

    private java.lang.String misuratoriTipo;

    private java.lang.String noteInterventi;

    private java.lang.String noteSmaltRiutilizzo;

    private java.lang.Integer numInterventiOrd;

    private java.lang.Integer numInterventiStraord;

    private java.lang.Integer numLineeBiologiche;

    private java.lang.Integer numLineeFanghi;

    private java.lang.Float parametriEst;

    private java.lang.Float parametriInv;

    private java.lang.Float percSecco;

    private java.lang.Float percUmidita;

    private com.hyperborea.sira.ws.PianiStralcio pianiStralcio;

    private java.lang.Float portataBypass;

    private java.lang.Float portataEstPar;

    private java.lang.Float portataEstPot;

    private java.lang.Float portataEstProgetto;

    private java.lang.Float portataEstPrra;

    private java.lang.Float portataInvPar;

    private java.lang.Float portataInvPot;

    private java.lang.Float portataInvProgetto;

    private java.lang.Float portataInvPrra;

    private java.lang.Float portataPuntaMista;

    private java.lang.Float potenzialitaEst;

    private java.lang.Float potenzialitaInv;

    private java.lang.Integer predNuoveUtenze;

    private java.lang.Float quantitaFanghi;

    private java.lang.Float quantitaResidui;

    private java.lang.Integer recinzione;

    private java.lang.Float refluoAgricolo;

    private java.lang.Float refluoAltro;

    private java.lang.Float refluoDomestico;

    private java.lang.Float refluoIndustriale;

    private java.lang.Float refluoZootecnico;

    private java.lang.Integer residenti;

    private java.lang.String riutilizzoAltroTesto;

    private java.lang.Float riutilizzoAltroValore;

    private java.lang.Float riutilizzoAmbientale;

    private java.lang.Float riutilizzoCivile;

    private java.lang.Float riutilizzoIndustriale;

    private java.lang.Float riutilizzoIrriguo;

    private java.lang.String schemaPrra;

    private java.lang.String segnaletica;

    private java.lang.Float smaltFanghiAgricoltura;

    private java.lang.Float smaltFanghiCompostato;

    private java.lang.Float smaltFanghiDiscarica;

    private java.lang.Float smaltFanghiInceneritore;

    private java.lang.String statoAttualeNu;

    private java.lang.String statoFunzionalita;

    private java.lang.Float tariffaDepuratore;

    private java.lang.Float tariffaFogne;

    private java.lang.Float tariffeAccantonate;

    private java.lang.Float tariffeRiscosse;

    private java.lang.Integer telecontrollo;

    private java.lang.Integer terraBattuta;

    private java.lang.String tipoAltroRefluo;

    private java.lang.String tipoAutomazione;

    private java.lang.String tipoFonteFinNu;

    private com.hyperborea.sira.ws.ImpDepTipiProcesso tipoProcesso;

    private com.hyperborea.sira.ws.ImpDepTipiTrattamento tipoTrattamento;

    private com.hyperborea.sira.ws.ImpDepTrattamenti trattamento;

    private java.lang.String ubicazioneMisuratore;

    private java.lang.String utilizzoFinaleFanghi;

    private java.lang.Float volumeBollettato;

    private java.lang.Float volumeTrattato;

    public CcostImpdepurazione() {
    }

    public CcostImpdepurazione(
           java.lang.Integer abitantiServiti,
           java.lang.String adeguamento152,
           java.lang.Integer alberi,
           java.lang.String altroLaboratorio,
           java.lang.String altroTipoTrattamento,
           java.lang.Integer annoChiusura,
           java.lang.Integer annoEsercizio,
           java.lang.Integer annoVolBollettato,
           java.lang.Integer annoVolumeTrattato,
           java.lang.Integer asfalto,
           java.lang.Integer automazione,
           java.lang.Float bod5,
           java.lang.Integer cancello,
           java.lang.Float capIdrProg,
           java.lang.Float capIdrServ,
           java.lang.Float capOrgProg,
           java.lang.Float capOrgServ,
           java.lang.Float capacitaEstProgetto,
           java.lang.Float capacitaEstPrra,
           java.lang.Float capacitaInvProgetto,
           java.lang.Float capacitaInvPrra,
           java.lang.Float caricoOrganicoMedio,
           java.lang.Integer cemento,
           java.lang.String codiceNazionale,
           java.lang.String codiceRegionale,
           java.lang.Integer competenzaConsortile,
           java.lang.Integer competenzaPrra,
           java.lang.Float costoGestione,
           com.hyperborea.sira.ws.DepuratoreCompetenzaPrra[] depuratoreCompetenzaPrras,
           com.hyperborea.sira.ws.DepuratoreCompetenza[] depuratoreCompetenzas,
           com.hyperborea.sira.ws.DepuratoreSezioni[] depuratoreSezionis,
           com.hyperborea.sira.ws.DepuratoreTipoOpere[] depuratoreTipoOperes,
           java.lang.String destLineaFanghi,
           java.lang.Float distanzaAbitazioni,
           java.lang.Integer equivalentiIndustriali,
           java.lang.Integer fluttuanti,
           java.lang.Integer fognaturaSeparata,
           java.lang.Integer idCcost,
           java.lang.String illuminazione,
           java.lang.Integer informatizzazioneDati,
           java.lang.Integer labAutoanalisiCent,
           java.lang.Integer labAutoanalisiExt,
           java.lang.Integer labAutoanalisiInt,
           org.apache.axis.types.UnsignedShort lineaFanghi,
           java.lang.String localitaSmaltimento,
           java.lang.String mesePunta,
           java.lang.Integer misuratorePortata,
           java.lang.Integer misuratoriOnline,
           java.lang.String misuratoriTipo,
           java.lang.String noteInterventi,
           java.lang.String noteSmaltRiutilizzo,
           java.lang.Integer numInterventiOrd,
           java.lang.Integer numInterventiStraord,
           java.lang.Integer numLineeBiologiche,
           java.lang.Integer numLineeFanghi,
           java.lang.Float parametriEst,
           java.lang.Float parametriInv,
           java.lang.Float percSecco,
           java.lang.Float percUmidita,
           com.hyperborea.sira.ws.PianiStralcio pianiStralcio,
           java.lang.Float portataBypass,
           java.lang.Float portataEstPar,
           java.lang.Float portataEstPot,
           java.lang.Float portataEstProgetto,
           java.lang.Float portataEstPrra,
           java.lang.Float portataInvPar,
           java.lang.Float portataInvPot,
           java.lang.Float portataInvProgetto,
           java.lang.Float portataInvPrra,
           java.lang.Float portataPuntaMista,
           java.lang.Float potenzialitaEst,
           java.lang.Float potenzialitaInv,
           java.lang.Integer predNuoveUtenze,
           java.lang.Float quantitaFanghi,
           java.lang.Float quantitaResidui,
           java.lang.Integer recinzione,
           java.lang.Float refluoAgricolo,
           java.lang.Float refluoAltro,
           java.lang.Float refluoDomestico,
           java.lang.Float refluoIndustriale,
           java.lang.Float refluoZootecnico,
           java.lang.Integer residenti,
           java.lang.String riutilizzoAltroTesto,
           java.lang.Float riutilizzoAltroValore,
           java.lang.Float riutilizzoAmbientale,
           java.lang.Float riutilizzoCivile,
           java.lang.Float riutilizzoIndustriale,
           java.lang.Float riutilizzoIrriguo,
           java.lang.String schemaPrra,
           java.lang.String segnaletica,
           java.lang.Float smaltFanghiAgricoltura,
           java.lang.Float smaltFanghiCompostato,
           java.lang.Float smaltFanghiDiscarica,
           java.lang.Float smaltFanghiInceneritore,
           java.lang.String statoAttualeNu,
           java.lang.String statoFunzionalita,
           java.lang.Float tariffaDepuratore,
           java.lang.Float tariffaFogne,
           java.lang.Float tariffeAccantonate,
           java.lang.Float tariffeRiscosse,
           java.lang.Integer telecontrollo,
           java.lang.Integer terraBattuta,
           java.lang.String tipoAltroRefluo,
           java.lang.String tipoAutomazione,
           java.lang.String tipoFonteFinNu,
           com.hyperborea.sira.ws.ImpDepTipiProcesso tipoProcesso,
           com.hyperborea.sira.ws.ImpDepTipiTrattamento tipoTrattamento,
           com.hyperborea.sira.ws.ImpDepTrattamenti trattamento,
           java.lang.String ubicazioneMisuratore,
           java.lang.String utilizzoFinaleFanghi,
           java.lang.Float volumeBollettato,
           java.lang.Float volumeTrattato) {
           this.abitantiServiti = abitantiServiti;
           this.adeguamento152 = adeguamento152;
           this.alberi = alberi;
           this.altroLaboratorio = altroLaboratorio;
           this.altroTipoTrattamento = altroTipoTrattamento;
           this.annoChiusura = annoChiusura;
           this.annoEsercizio = annoEsercizio;
           this.annoVolBollettato = annoVolBollettato;
           this.annoVolumeTrattato = annoVolumeTrattato;
           this.asfalto = asfalto;
           this.automazione = automazione;
           this.bod5 = bod5;
           this.cancello = cancello;
           this.capIdrProg = capIdrProg;
           this.capIdrServ = capIdrServ;
           this.capOrgProg = capOrgProg;
           this.capOrgServ = capOrgServ;
           this.capacitaEstProgetto = capacitaEstProgetto;
           this.capacitaEstPrra = capacitaEstPrra;
           this.capacitaInvProgetto = capacitaInvProgetto;
           this.capacitaInvPrra = capacitaInvPrra;
           this.caricoOrganicoMedio = caricoOrganicoMedio;
           this.cemento = cemento;
           this.codiceNazionale = codiceNazionale;
           this.codiceRegionale = codiceRegionale;
           this.competenzaConsortile = competenzaConsortile;
           this.competenzaPrra = competenzaPrra;
           this.costoGestione = costoGestione;
           this.depuratoreCompetenzaPrras = depuratoreCompetenzaPrras;
           this.depuratoreCompetenzas = depuratoreCompetenzas;
           this.depuratoreSezionis = depuratoreSezionis;
           this.depuratoreTipoOperes = depuratoreTipoOperes;
           this.destLineaFanghi = destLineaFanghi;
           this.distanzaAbitazioni = distanzaAbitazioni;
           this.equivalentiIndustriali = equivalentiIndustriali;
           this.fluttuanti = fluttuanti;
           this.fognaturaSeparata = fognaturaSeparata;
           this.idCcost = idCcost;
           this.illuminazione = illuminazione;
           this.informatizzazioneDati = informatizzazioneDati;
           this.labAutoanalisiCent = labAutoanalisiCent;
           this.labAutoanalisiExt = labAutoanalisiExt;
           this.labAutoanalisiInt = labAutoanalisiInt;
           this.lineaFanghi = lineaFanghi;
           this.localitaSmaltimento = localitaSmaltimento;
           this.mesePunta = mesePunta;
           this.misuratorePortata = misuratorePortata;
           this.misuratoriOnline = misuratoriOnline;
           this.misuratoriTipo = misuratoriTipo;
           this.noteInterventi = noteInterventi;
           this.noteSmaltRiutilizzo = noteSmaltRiutilizzo;
           this.numInterventiOrd = numInterventiOrd;
           this.numInterventiStraord = numInterventiStraord;
           this.numLineeBiologiche = numLineeBiologiche;
           this.numLineeFanghi = numLineeFanghi;
           this.parametriEst = parametriEst;
           this.parametriInv = parametriInv;
           this.percSecco = percSecco;
           this.percUmidita = percUmidita;
           this.pianiStralcio = pianiStralcio;
           this.portataBypass = portataBypass;
           this.portataEstPar = portataEstPar;
           this.portataEstPot = portataEstPot;
           this.portataEstProgetto = portataEstProgetto;
           this.portataEstPrra = portataEstPrra;
           this.portataInvPar = portataInvPar;
           this.portataInvPot = portataInvPot;
           this.portataInvProgetto = portataInvProgetto;
           this.portataInvPrra = portataInvPrra;
           this.portataPuntaMista = portataPuntaMista;
           this.potenzialitaEst = potenzialitaEst;
           this.potenzialitaInv = potenzialitaInv;
           this.predNuoveUtenze = predNuoveUtenze;
           this.quantitaFanghi = quantitaFanghi;
           this.quantitaResidui = quantitaResidui;
           this.recinzione = recinzione;
           this.refluoAgricolo = refluoAgricolo;
           this.refluoAltro = refluoAltro;
           this.refluoDomestico = refluoDomestico;
           this.refluoIndustriale = refluoIndustriale;
           this.refluoZootecnico = refluoZootecnico;
           this.residenti = residenti;
           this.riutilizzoAltroTesto = riutilizzoAltroTesto;
           this.riutilizzoAltroValore = riutilizzoAltroValore;
           this.riutilizzoAmbientale = riutilizzoAmbientale;
           this.riutilizzoCivile = riutilizzoCivile;
           this.riutilizzoIndustriale = riutilizzoIndustriale;
           this.riutilizzoIrriguo = riutilizzoIrriguo;
           this.schemaPrra = schemaPrra;
           this.segnaletica = segnaletica;
           this.smaltFanghiAgricoltura = smaltFanghiAgricoltura;
           this.smaltFanghiCompostato = smaltFanghiCompostato;
           this.smaltFanghiDiscarica = smaltFanghiDiscarica;
           this.smaltFanghiInceneritore = smaltFanghiInceneritore;
           this.statoAttualeNu = statoAttualeNu;
           this.statoFunzionalita = statoFunzionalita;
           this.tariffaDepuratore = tariffaDepuratore;
           this.tariffaFogne = tariffaFogne;
           this.tariffeAccantonate = tariffeAccantonate;
           this.tariffeRiscosse = tariffeRiscosse;
           this.telecontrollo = telecontrollo;
           this.terraBattuta = terraBattuta;
           this.tipoAltroRefluo = tipoAltroRefluo;
           this.tipoAutomazione = tipoAutomazione;
           this.tipoFonteFinNu = tipoFonteFinNu;
           this.tipoProcesso = tipoProcesso;
           this.tipoTrattamento = tipoTrattamento;
           this.trattamento = trattamento;
           this.ubicazioneMisuratore = ubicazioneMisuratore;
           this.utilizzoFinaleFanghi = utilizzoFinaleFanghi;
           this.volumeBollettato = volumeBollettato;
           this.volumeTrattato = volumeTrattato;
    }


    /**
     * Gets the abitantiServiti value for this CcostImpdepurazione.
     * 
     * @return abitantiServiti
     */
    public java.lang.Integer getAbitantiServiti() {
        return abitantiServiti;
    }


    /**
     * Sets the abitantiServiti value for this CcostImpdepurazione.
     * 
     * @param abitantiServiti
     */
    public void setAbitantiServiti(java.lang.Integer abitantiServiti) {
        this.abitantiServiti = abitantiServiti;
    }


    /**
     * Gets the adeguamento152 value for this CcostImpdepurazione.
     * 
     * @return adeguamento152
     */
    public java.lang.String getAdeguamento152() {
        return adeguamento152;
    }


    /**
     * Sets the adeguamento152 value for this CcostImpdepurazione.
     * 
     * @param adeguamento152
     */
    public void setAdeguamento152(java.lang.String adeguamento152) {
        this.adeguamento152 = adeguamento152;
    }


    /**
     * Gets the alberi value for this CcostImpdepurazione.
     * 
     * @return alberi
     */
    public java.lang.Integer getAlberi() {
        return alberi;
    }


    /**
     * Sets the alberi value for this CcostImpdepurazione.
     * 
     * @param alberi
     */
    public void setAlberi(java.lang.Integer alberi) {
        this.alberi = alberi;
    }


    /**
     * Gets the altroLaboratorio value for this CcostImpdepurazione.
     * 
     * @return altroLaboratorio
     */
    public java.lang.String getAltroLaboratorio() {
        return altroLaboratorio;
    }


    /**
     * Sets the altroLaboratorio value for this CcostImpdepurazione.
     * 
     * @param altroLaboratorio
     */
    public void setAltroLaboratorio(java.lang.String altroLaboratorio) {
        this.altroLaboratorio = altroLaboratorio;
    }


    /**
     * Gets the altroTipoTrattamento value for this CcostImpdepurazione.
     * 
     * @return altroTipoTrattamento
     */
    public java.lang.String getAltroTipoTrattamento() {
        return altroTipoTrattamento;
    }


    /**
     * Sets the altroTipoTrattamento value for this CcostImpdepurazione.
     * 
     * @param altroTipoTrattamento
     */
    public void setAltroTipoTrattamento(java.lang.String altroTipoTrattamento) {
        this.altroTipoTrattamento = altroTipoTrattamento;
    }


    /**
     * Gets the annoChiusura value for this CcostImpdepurazione.
     * 
     * @return annoChiusura
     */
    public java.lang.Integer getAnnoChiusura() {
        return annoChiusura;
    }


    /**
     * Sets the annoChiusura value for this CcostImpdepurazione.
     * 
     * @param annoChiusura
     */
    public void setAnnoChiusura(java.lang.Integer annoChiusura) {
        this.annoChiusura = annoChiusura;
    }


    /**
     * Gets the annoEsercizio value for this CcostImpdepurazione.
     * 
     * @return annoEsercizio
     */
    public java.lang.Integer getAnnoEsercizio() {
        return annoEsercizio;
    }


    /**
     * Sets the annoEsercizio value for this CcostImpdepurazione.
     * 
     * @param annoEsercizio
     */
    public void setAnnoEsercizio(java.lang.Integer annoEsercizio) {
        this.annoEsercizio = annoEsercizio;
    }


    /**
     * Gets the annoVolBollettato value for this CcostImpdepurazione.
     * 
     * @return annoVolBollettato
     */
    public java.lang.Integer getAnnoVolBollettato() {
        return annoVolBollettato;
    }


    /**
     * Sets the annoVolBollettato value for this CcostImpdepurazione.
     * 
     * @param annoVolBollettato
     */
    public void setAnnoVolBollettato(java.lang.Integer annoVolBollettato) {
        this.annoVolBollettato = annoVolBollettato;
    }


    /**
     * Gets the annoVolumeTrattato value for this CcostImpdepurazione.
     * 
     * @return annoVolumeTrattato
     */
    public java.lang.Integer getAnnoVolumeTrattato() {
        return annoVolumeTrattato;
    }


    /**
     * Sets the annoVolumeTrattato value for this CcostImpdepurazione.
     * 
     * @param annoVolumeTrattato
     */
    public void setAnnoVolumeTrattato(java.lang.Integer annoVolumeTrattato) {
        this.annoVolumeTrattato = annoVolumeTrattato;
    }


    /**
     * Gets the asfalto value for this CcostImpdepurazione.
     * 
     * @return asfalto
     */
    public java.lang.Integer getAsfalto() {
        return asfalto;
    }


    /**
     * Sets the asfalto value for this CcostImpdepurazione.
     * 
     * @param asfalto
     */
    public void setAsfalto(java.lang.Integer asfalto) {
        this.asfalto = asfalto;
    }


    /**
     * Gets the automazione value for this CcostImpdepurazione.
     * 
     * @return automazione
     */
    public java.lang.Integer getAutomazione() {
        return automazione;
    }


    /**
     * Sets the automazione value for this CcostImpdepurazione.
     * 
     * @param automazione
     */
    public void setAutomazione(java.lang.Integer automazione) {
        this.automazione = automazione;
    }


    /**
     * Gets the bod5 value for this CcostImpdepurazione.
     * 
     * @return bod5
     */
    public java.lang.Float getBod5() {
        return bod5;
    }


    /**
     * Sets the bod5 value for this CcostImpdepurazione.
     * 
     * @param bod5
     */
    public void setBod5(java.lang.Float bod5) {
        this.bod5 = bod5;
    }


    /**
     * Gets the cancello value for this CcostImpdepurazione.
     * 
     * @return cancello
     */
    public java.lang.Integer getCancello() {
        return cancello;
    }


    /**
     * Sets the cancello value for this CcostImpdepurazione.
     * 
     * @param cancello
     */
    public void setCancello(java.lang.Integer cancello) {
        this.cancello = cancello;
    }


    /**
     * Gets the capIdrProg value for this CcostImpdepurazione.
     * 
     * @return capIdrProg
     */
    public java.lang.Float getCapIdrProg() {
        return capIdrProg;
    }


    /**
     * Sets the capIdrProg value for this CcostImpdepurazione.
     * 
     * @param capIdrProg
     */
    public void setCapIdrProg(java.lang.Float capIdrProg) {
        this.capIdrProg = capIdrProg;
    }


    /**
     * Gets the capIdrServ value for this CcostImpdepurazione.
     * 
     * @return capIdrServ
     */
    public java.lang.Float getCapIdrServ() {
        return capIdrServ;
    }


    /**
     * Sets the capIdrServ value for this CcostImpdepurazione.
     * 
     * @param capIdrServ
     */
    public void setCapIdrServ(java.lang.Float capIdrServ) {
        this.capIdrServ = capIdrServ;
    }


    /**
     * Gets the capOrgProg value for this CcostImpdepurazione.
     * 
     * @return capOrgProg
     */
    public java.lang.Float getCapOrgProg() {
        return capOrgProg;
    }


    /**
     * Sets the capOrgProg value for this CcostImpdepurazione.
     * 
     * @param capOrgProg
     */
    public void setCapOrgProg(java.lang.Float capOrgProg) {
        this.capOrgProg = capOrgProg;
    }


    /**
     * Gets the capOrgServ value for this CcostImpdepurazione.
     * 
     * @return capOrgServ
     */
    public java.lang.Float getCapOrgServ() {
        return capOrgServ;
    }


    /**
     * Sets the capOrgServ value for this CcostImpdepurazione.
     * 
     * @param capOrgServ
     */
    public void setCapOrgServ(java.lang.Float capOrgServ) {
        this.capOrgServ = capOrgServ;
    }


    /**
     * Gets the capacitaEstProgetto value for this CcostImpdepurazione.
     * 
     * @return capacitaEstProgetto
     */
    public java.lang.Float getCapacitaEstProgetto() {
        return capacitaEstProgetto;
    }


    /**
     * Sets the capacitaEstProgetto value for this CcostImpdepurazione.
     * 
     * @param capacitaEstProgetto
     */
    public void setCapacitaEstProgetto(java.lang.Float capacitaEstProgetto) {
        this.capacitaEstProgetto = capacitaEstProgetto;
    }


    /**
     * Gets the capacitaEstPrra value for this CcostImpdepurazione.
     * 
     * @return capacitaEstPrra
     */
    public java.lang.Float getCapacitaEstPrra() {
        return capacitaEstPrra;
    }


    /**
     * Sets the capacitaEstPrra value for this CcostImpdepurazione.
     * 
     * @param capacitaEstPrra
     */
    public void setCapacitaEstPrra(java.lang.Float capacitaEstPrra) {
        this.capacitaEstPrra = capacitaEstPrra;
    }


    /**
     * Gets the capacitaInvProgetto value for this CcostImpdepurazione.
     * 
     * @return capacitaInvProgetto
     */
    public java.lang.Float getCapacitaInvProgetto() {
        return capacitaInvProgetto;
    }


    /**
     * Sets the capacitaInvProgetto value for this CcostImpdepurazione.
     * 
     * @param capacitaInvProgetto
     */
    public void setCapacitaInvProgetto(java.lang.Float capacitaInvProgetto) {
        this.capacitaInvProgetto = capacitaInvProgetto;
    }


    /**
     * Gets the capacitaInvPrra value for this CcostImpdepurazione.
     * 
     * @return capacitaInvPrra
     */
    public java.lang.Float getCapacitaInvPrra() {
        return capacitaInvPrra;
    }


    /**
     * Sets the capacitaInvPrra value for this CcostImpdepurazione.
     * 
     * @param capacitaInvPrra
     */
    public void setCapacitaInvPrra(java.lang.Float capacitaInvPrra) {
        this.capacitaInvPrra = capacitaInvPrra;
    }


    /**
     * Gets the caricoOrganicoMedio value for this CcostImpdepurazione.
     * 
     * @return caricoOrganicoMedio
     */
    public java.lang.Float getCaricoOrganicoMedio() {
        return caricoOrganicoMedio;
    }


    /**
     * Sets the caricoOrganicoMedio value for this CcostImpdepurazione.
     * 
     * @param caricoOrganicoMedio
     */
    public void setCaricoOrganicoMedio(java.lang.Float caricoOrganicoMedio) {
        this.caricoOrganicoMedio = caricoOrganicoMedio;
    }


    /**
     * Gets the cemento value for this CcostImpdepurazione.
     * 
     * @return cemento
     */
    public java.lang.Integer getCemento() {
        return cemento;
    }


    /**
     * Sets the cemento value for this CcostImpdepurazione.
     * 
     * @param cemento
     */
    public void setCemento(java.lang.Integer cemento) {
        this.cemento = cemento;
    }


    /**
     * Gets the codiceNazionale value for this CcostImpdepurazione.
     * 
     * @return codiceNazionale
     */
    public java.lang.String getCodiceNazionale() {
        return codiceNazionale;
    }


    /**
     * Sets the codiceNazionale value for this CcostImpdepurazione.
     * 
     * @param codiceNazionale
     */
    public void setCodiceNazionale(java.lang.String codiceNazionale) {
        this.codiceNazionale = codiceNazionale;
    }


    /**
     * Gets the codiceRegionale value for this CcostImpdepurazione.
     * 
     * @return codiceRegionale
     */
    public java.lang.String getCodiceRegionale() {
        return codiceRegionale;
    }


    /**
     * Sets the codiceRegionale value for this CcostImpdepurazione.
     * 
     * @param codiceRegionale
     */
    public void setCodiceRegionale(java.lang.String codiceRegionale) {
        this.codiceRegionale = codiceRegionale;
    }


    /**
     * Gets the competenzaConsortile value for this CcostImpdepurazione.
     * 
     * @return competenzaConsortile
     */
    public java.lang.Integer getCompetenzaConsortile() {
        return competenzaConsortile;
    }


    /**
     * Sets the competenzaConsortile value for this CcostImpdepurazione.
     * 
     * @param competenzaConsortile
     */
    public void setCompetenzaConsortile(java.lang.Integer competenzaConsortile) {
        this.competenzaConsortile = competenzaConsortile;
    }


    /**
     * Gets the competenzaPrra value for this CcostImpdepurazione.
     * 
     * @return competenzaPrra
     */
    public java.lang.Integer getCompetenzaPrra() {
        return competenzaPrra;
    }


    /**
     * Sets the competenzaPrra value for this CcostImpdepurazione.
     * 
     * @param competenzaPrra
     */
    public void setCompetenzaPrra(java.lang.Integer competenzaPrra) {
        this.competenzaPrra = competenzaPrra;
    }


    /**
     * Gets the costoGestione value for this CcostImpdepurazione.
     * 
     * @return costoGestione
     */
    public java.lang.Float getCostoGestione() {
        return costoGestione;
    }


    /**
     * Sets the costoGestione value for this CcostImpdepurazione.
     * 
     * @param costoGestione
     */
    public void setCostoGestione(java.lang.Float costoGestione) {
        this.costoGestione = costoGestione;
    }


    /**
     * Gets the depuratoreCompetenzaPrras value for this CcostImpdepurazione.
     * 
     * @return depuratoreCompetenzaPrras
     */
    public com.hyperborea.sira.ws.DepuratoreCompetenzaPrra[] getDepuratoreCompetenzaPrras() {
        return depuratoreCompetenzaPrras;
    }


    /**
     * Sets the depuratoreCompetenzaPrras value for this CcostImpdepurazione.
     * 
     * @param depuratoreCompetenzaPrras
     */
    public void setDepuratoreCompetenzaPrras(com.hyperborea.sira.ws.DepuratoreCompetenzaPrra[] depuratoreCompetenzaPrras) {
        this.depuratoreCompetenzaPrras = depuratoreCompetenzaPrras;
    }

    public com.hyperborea.sira.ws.DepuratoreCompetenzaPrra getDepuratoreCompetenzaPrras(int i) {
        return this.depuratoreCompetenzaPrras[i];
    }

    public void setDepuratoreCompetenzaPrras(int i, com.hyperborea.sira.ws.DepuratoreCompetenzaPrra _value) {
        this.depuratoreCompetenzaPrras[i] = _value;
    }


    /**
     * Gets the depuratoreCompetenzas value for this CcostImpdepurazione.
     * 
     * @return depuratoreCompetenzas
     */
    public com.hyperborea.sira.ws.DepuratoreCompetenza[] getDepuratoreCompetenzas() {
        return depuratoreCompetenzas;
    }


    /**
     * Sets the depuratoreCompetenzas value for this CcostImpdepurazione.
     * 
     * @param depuratoreCompetenzas
     */
    public void setDepuratoreCompetenzas(com.hyperborea.sira.ws.DepuratoreCompetenza[] depuratoreCompetenzas) {
        this.depuratoreCompetenzas = depuratoreCompetenzas;
    }

    public com.hyperborea.sira.ws.DepuratoreCompetenza getDepuratoreCompetenzas(int i) {
        return this.depuratoreCompetenzas[i];
    }

    public void setDepuratoreCompetenzas(int i, com.hyperborea.sira.ws.DepuratoreCompetenza _value) {
        this.depuratoreCompetenzas[i] = _value;
    }


    /**
     * Gets the depuratoreSezionis value for this CcostImpdepurazione.
     * 
     * @return depuratoreSezionis
     */
    public com.hyperborea.sira.ws.DepuratoreSezioni[] getDepuratoreSezionis() {
        return depuratoreSezionis;
    }


    /**
     * Sets the depuratoreSezionis value for this CcostImpdepurazione.
     * 
     * @param depuratoreSezionis
     */
    public void setDepuratoreSezionis(com.hyperborea.sira.ws.DepuratoreSezioni[] depuratoreSezionis) {
        this.depuratoreSezionis = depuratoreSezionis;
    }

    public com.hyperborea.sira.ws.DepuratoreSezioni getDepuratoreSezionis(int i) {
        return this.depuratoreSezionis[i];
    }

    public void setDepuratoreSezionis(int i, com.hyperborea.sira.ws.DepuratoreSezioni _value) {
        this.depuratoreSezionis[i] = _value;
    }


    /**
     * Gets the depuratoreTipoOperes value for this CcostImpdepurazione.
     * 
     * @return depuratoreTipoOperes
     */
    public com.hyperborea.sira.ws.DepuratoreTipoOpere[] getDepuratoreTipoOperes() {
        return depuratoreTipoOperes;
    }


    /**
     * Sets the depuratoreTipoOperes value for this CcostImpdepurazione.
     * 
     * @param depuratoreTipoOperes
     */
    public void setDepuratoreTipoOperes(com.hyperborea.sira.ws.DepuratoreTipoOpere[] depuratoreTipoOperes) {
        this.depuratoreTipoOperes = depuratoreTipoOperes;
    }

    public com.hyperborea.sira.ws.DepuratoreTipoOpere getDepuratoreTipoOperes(int i) {
        return this.depuratoreTipoOperes[i];
    }

    public void setDepuratoreTipoOperes(int i, com.hyperborea.sira.ws.DepuratoreTipoOpere _value) {
        this.depuratoreTipoOperes[i] = _value;
    }


    /**
     * Gets the destLineaFanghi value for this CcostImpdepurazione.
     * 
     * @return destLineaFanghi
     */
    public java.lang.String getDestLineaFanghi() {
        return destLineaFanghi;
    }


    /**
     * Sets the destLineaFanghi value for this CcostImpdepurazione.
     * 
     * @param destLineaFanghi
     */
    public void setDestLineaFanghi(java.lang.String destLineaFanghi) {
        this.destLineaFanghi = destLineaFanghi;
    }


    /**
     * Gets the distanzaAbitazioni value for this CcostImpdepurazione.
     * 
     * @return distanzaAbitazioni
     */
    public java.lang.Float getDistanzaAbitazioni() {
        return distanzaAbitazioni;
    }


    /**
     * Sets the distanzaAbitazioni value for this CcostImpdepurazione.
     * 
     * @param distanzaAbitazioni
     */
    public void setDistanzaAbitazioni(java.lang.Float distanzaAbitazioni) {
        this.distanzaAbitazioni = distanzaAbitazioni;
    }


    /**
     * Gets the equivalentiIndustriali value for this CcostImpdepurazione.
     * 
     * @return equivalentiIndustriali
     */
    public java.lang.Integer getEquivalentiIndustriali() {
        return equivalentiIndustriali;
    }


    /**
     * Sets the equivalentiIndustriali value for this CcostImpdepurazione.
     * 
     * @param equivalentiIndustriali
     */
    public void setEquivalentiIndustriali(java.lang.Integer equivalentiIndustriali) {
        this.equivalentiIndustriali = equivalentiIndustriali;
    }


    /**
     * Gets the fluttuanti value for this CcostImpdepurazione.
     * 
     * @return fluttuanti
     */
    public java.lang.Integer getFluttuanti() {
        return fluttuanti;
    }


    /**
     * Sets the fluttuanti value for this CcostImpdepurazione.
     * 
     * @param fluttuanti
     */
    public void setFluttuanti(java.lang.Integer fluttuanti) {
        this.fluttuanti = fluttuanti;
    }


    /**
     * Gets the fognaturaSeparata value for this CcostImpdepurazione.
     * 
     * @return fognaturaSeparata
     */
    public java.lang.Integer getFognaturaSeparata() {
        return fognaturaSeparata;
    }


    /**
     * Sets the fognaturaSeparata value for this CcostImpdepurazione.
     * 
     * @param fognaturaSeparata
     */
    public void setFognaturaSeparata(java.lang.Integer fognaturaSeparata) {
        this.fognaturaSeparata = fognaturaSeparata;
    }


    /**
     * Gets the idCcost value for this CcostImpdepurazione.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostImpdepurazione.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the illuminazione value for this CcostImpdepurazione.
     * 
     * @return illuminazione
     */
    public java.lang.String getIlluminazione() {
        return illuminazione;
    }


    /**
     * Sets the illuminazione value for this CcostImpdepurazione.
     * 
     * @param illuminazione
     */
    public void setIlluminazione(java.lang.String illuminazione) {
        this.illuminazione = illuminazione;
    }


    /**
     * Gets the informatizzazioneDati value for this CcostImpdepurazione.
     * 
     * @return informatizzazioneDati
     */
    public java.lang.Integer getInformatizzazioneDati() {
        return informatizzazioneDati;
    }


    /**
     * Sets the informatizzazioneDati value for this CcostImpdepurazione.
     * 
     * @param informatizzazioneDati
     */
    public void setInformatizzazioneDati(java.lang.Integer informatizzazioneDati) {
        this.informatizzazioneDati = informatizzazioneDati;
    }


    /**
     * Gets the labAutoanalisiCent value for this CcostImpdepurazione.
     * 
     * @return labAutoanalisiCent
     */
    public java.lang.Integer getLabAutoanalisiCent() {
        return labAutoanalisiCent;
    }


    /**
     * Sets the labAutoanalisiCent value for this CcostImpdepurazione.
     * 
     * @param labAutoanalisiCent
     */
    public void setLabAutoanalisiCent(java.lang.Integer labAutoanalisiCent) {
        this.labAutoanalisiCent = labAutoanalisiCent;
    }


    /**
     * Gets the labAutoanalisiExt value for this CcostImpdepurazione.
     * 
     * @return labAutoanalisiExt
     */
    public java.lang.Integer getLabAutoanalisiExt() {
        return labAutoanalisiExt;
    }


    /**
     * Sets the labAutoanalisiExt value for this CcostImpdepurazione.
     * 
     * @param labAutoanalisiExt
     */
    public void setLabAutoanalisiExt(java.lang.Integer labAutoanalisiExt) {
        this.labAutoanalisiExt = labAutoanalisiExt;
    }


    /**
     * Gets the labAutoanalisiInt value for this CcostImpdepurazione.
     * 
     * @return labAutoanalisiInt
     */
    public java.lang.Integer getLabAutoanalisiInt() {
        return labAutoanalisiInt;
    }


    /**
     * Sets the labAutoanalisiInt value for this CcostImpdepurazione.
     * 
     * @param labAutoanalisiInt
     */
    public void setLabAutoanalisiInt(java.lang.Integer labAutoanalisiInt) {
        this.labAutoanalisiInt = labAutoanalisiInt;
    }


    /**
     * Gets the lineaFanghi value for this CcostImpdepurazione.
     * 
     * @return lineaFanghi
     */
    public org.apache.axis.types.UnsignedShort getLineaFanghi() {
        return lineaFanghi;
    }


    /**
     * Sets the lineaFanghi value for this CcostImpdepurazione.
     * 
     * @param lineaFanghi
     */
    public void setLineaFanghi(org.apache.axis.types.UnsignedShort lineaFanghi) {
        this.lineaFanghi = lineaFanghi;
    }


    /**
     * Gets the localitaSmaltimento value for this CcostImpdepurazione.
     * 
     * @return localitaSmaltimento
     */
    public java.lang.String getLocalitaSmaltimento() {
        return localitaSmaltimento;
    }


    /**
     * Sets the localitaSmaltimento value for this CcostImpdepurazione.
     * 
     * @param localitaSmaltimento
     */
    public void setLocalitaSmaltimento(java.lang.String localitaSmaltimento) {
        this.localitaSmaltimento = localitaSmaltimento;
    }


    /**
     * Gets the mesePunta value for this CcostImpdepurazione.
     * 
     * @return mesePunta
     */
    public java.lang.String getMesePunta() {
        return mesePunta;
    }


    /**
     * Sets the mesePunta value for this CcostImpdepurazione.
     * 
     * @param mesePunta
     */
    public void setMesePunta(java.lang.String mesePunta) {
        this.mesePunta = mesePunta;
    }


    /**
     * Gets the misuratorePortata value for this CcostImpdepurazione.
     * 
     * @return misuratorePortata
     */
    public java.lang.Integer getMisuratorePortata() {
        return misuratorePortata;
    }


    /**
     * Sets the misuratorePortata value for this CcostImpdepurazione.
     * 
     * @param misuratorePortata
     */
    public void setMisuratorePortata(java.lang.Integer misuratorePortata) {
        this.misuratorePortata = misuratorePortata;
    }


    /**
     * Gets the misuratoriOnline value for this CcostImpdepurazione.
     * 
     * @return misuratoriOnline
     */
    public java.lang.Integer getMisuratoriOnline() {
        return misuratoriOnline;
    }


    /**
     * Sets the misuratoriOnline value for this CcostImpdepurazione.
     * 
     * @param misuratoriOnline
     */
    public void setMisuratoriOnline(java.lang.Integer misuratoriOnline) {
        this.misuratoriOnline = misuratoriOnline;
    }


    /**
     * Gets the misuratoriTipo value for this CcostImpdepurazione.
     * 
     * @return misuratoriTipo
     */
    public java.lang.String getMisuratoriTipo() {
        return misuratoriTipo;
    }


    /**
     * Sets the misuratoriTipo value for this CcostImpdepurazione.
     * 
     * @param misuratoriTipo
     */
    public void setMisuratoriTipo(java.lang.String misuratoriTipo) {
        this.misuratoriTipo = misuratoriTipo;
    }


    /**
     * Gets the noteInterventi value for this CcostImpdepurazione.
     * 
     * @return noteInterventi
     */
    public java.lang.String getNoteInterventi() {
        return noteInterventi;
    }


    /**
     * Sets the noteInterventi value for this CcostImpdepurazione.
     * 
     * @param noteInterventi
     */
    public void setNoteInterventi(java.lang.String noteInterventi) {
        this.noteInterventi = noteInterventi;
    }


    /**
     * Gets the noteSmaltRiutilizzo value for this CcostImpdepurazione.
     * 
     * @return noteSmaltRiutilizzo
     */
    public java.lang.String getNoteSmaltRiutilizzo() {
        return noteSmaltRiutilizzo;
    }


    /**
     * Sets the noteSmaltRiutilizzo value for this CcostImpdepurazione.
     * 
     * @param noteSmaltRiutilizzo
     */
    public void setNoteSmaltRiutilizzo(java.lang.String noteSmaltRiutilizzo) {
        this.noteSmaltRiutilizzo = noteSmaltRiutilizzo;
    }


    /**
     * Gets the numInterventiOrd value for this CcostImpdepurazione.
     * 
     * @return numInterventiOrd
     */
    public java.lang.Integer getNumInterventiOrd() {
        return numInterventiOrd;
    }


    /**
     * Sets the numInterventiOrd value for this CcostImpdepurazione.
     * 
     * @param numInterventiOrd
     */
    public void setNumInterventiOrd(java.lang.Integer numInterventiOrd) {
        this.numInterventiOrd = numInterventiOrd;
    }


    /**
     * Gets the numInterventiStraord value for this CcostImpdepurazione.
     * 
     * @return numInterventiStraord
     */
    public java.lang.Integer getNumInterventiStraord() {
        return numInterventiStraord;
    }


    /**
     * Sets the numInterventiStraord value for this CcostImpdepurazione.
     * 
     * @param numInterventiStraord
     */
    public void setNumInterventiStraord(java.lang.Integer numInterventiStraord) {
        this.numInterventiStraord = numInterventiStraord;
    }


    /**
     * Gets the numLineeBiologiche value for this CcostImpdepurazione.
     * 
     * @return numLineeBiologiche
     */
    public java.lang.Integer getNumLineeBiologiche() {
        return numLineeBiologiche;
    }


    /**
     * Sets the numLineeBiologiche value for this CcostImpdepurazione.
     * 
     * @param numLineeBiologiche
     */
    public void setNumLineeBiologiche(java.lang.Integer numLineeBiologiche) {
        this.numLineeBiologiche = numLineeBiologiche;
    }


    /**
     * Gets the numLineeFanghi value for this CcostImpdepurazione.
     * 
     * @return numLineeFanghi
     */
    public java.lang.Integer getNumLineeFanghi() {
        return numLineeFanghi;
    }


    /**
     * Sets the numLineeFanghi value for this CcostImpdepurazione.
     * 
     * @param numLineeFanghi
     */
    public void setNumLineeFanghi(java.lang.Integer numLineeFanghi) {
        this.numLineeFanghi = numLineeFanghi;
    }


    /**
     * Gets the parametriEst value for this CcostImpdepurazione.
     * 
     * @return parametriEst
     */
    public java.lang.Float getParametriEst() {
        return parametriEst;
    }


    /**
     * Sets the parametriEst value for this CcostImpdepurazione.
     * 
     * @param parametriEst
     */
    public void setParametriEst(java.lang.Float parametriEst) {
        this.parametriEst = parametriEst;
    }


    /**
     * Gets the parametriInv value for this CcostImpdepurazione.
     * 
     * @return parametriInv
     */
    public java.lang.Float getParametriInv() {
        return parametriInv;
    }


    /**
     * Sets the parametriInv value for this CcostImpdepurazione.
     * 
     * @param parametriInv
     */
    public void setParametriInv(java.lang.Float parametriInv) {
        this.parametriInv = parametriInv;
    }


    /**
     * Gets the percSecco value for this CcostImpdepurazione.
     * 
     * @return percSecco
     */
    public java.lang.Float getPercSecco() {
        return percSecco;
    }


    /**
     * Sets the percSecco value for this CcostImpdepurazione.
     * 
     * @param percSecco
     */
    public void setPercSecco(java.lang.Float percSecco) {
        this.percSecco = percSecco;
    }


    /**
     * Gets the percUmidita value for this CcostImpdepurazione.
     * 
     * @return percUmidita
     */
    public java.lang.Float getPercUmidita() {
        return percUmidita;
    }


    /**
     * Sets the percUmidita value for this CcostImpdepurazione.
     * 
     * @param percUmidita
     */
    public void setPercUmidita(java.lang.Float percUmidita) {
        this.percUmidita = percUmidita;
    }


    /**
     * Gets the pianiStralcio value for this CcostImpdepurazione.
     * 
     * @return pianiStralcio
     */
    public com.hyperborea.sira.ws.PianiStralcio getPianiStralcio() {
        return pianiStralcio;
    }


    /**
     * Sets the pianiStralcio value for this CcostImpdepurazione.
     * 
     * @param pianiStralcio
     */
    public void setPianiStralcio(com.hyperborea.sira.ws.PianiStralcio pianiStralcio) {
        this.pianiStralcio = pianiStralcio;
    }


    /**
     * Gets the portataBypass value for this CcostImpdepurazione.
     * 
     * @return portataBypass
     */
    public java.lang.Float getPortataBypass() {
        return portataBypass;
    }


    /**
     * Sets the portataBypass value for this CcostImpdepurazione.
     * 
     * @param portataBypass
     */
    public void setPortataBypass(java.lang.Float portataBypass) {
        this.portataBypass = portataBypass;
    }


    /**
     * Gets the portataEstPar value for this CcostImpdepurazione.
     * 
     * @return portataEstPar
     */
    public java.lang.Float getPortataEstPar() {
        return portataEstPar;
    }


    /**
     * Sets the portataEstPar value for this CcostImpdepurazione.
     * 
     * @param portataEstPar
     */
    public void setPortataEstPar(java.lang.Float portataEstPar) {
        this.portataEstPar = portataEstPar;
    }


    /**
     * Gets the portataEstPot value for this CcostImpdepurazione.
     * 
     * @return portataEstPot
     */
    public java.lang.Float getPortataEstPot() {
        return portataEstPot;
    }


    /**
     * Sets the portataEstPot value for this CcostImpdepurazione.
     * 
     * @param portataEstPot
     */
    public void setPortataEstPot(java.lang.Float portataEstPot) {
        this.portataEstPot = portataEstPot;
    }


    /**
     * Gets the portataEstProgetto value for this CcostImpdepurazione.
     * 
     * @return portataEstProgetto
     */
    public java.lang.Float getPortataEstProgetto() {
        return portataEstProgetto;
    }


    /**
     * Sets the portataEstProgetto value for this CcostImpdepurazione.
     * 
     * @param portataEstProgetto
     */
    public void setPortataEstProgetto(java.lang.Float portataEstProgetto) {
        this.portataEstProgetto = portataEstProgetto;
    }


    /**
     * Gets the portataEstPrra value for this CcostImpdepurazione.
     * 
     * @return portataEstPrra
     */
    public java.lang.Float getPortataEstPrra() {
        return portataEstPrra;
    }


    /**
     * Sets the portataEstPrra value for this CcostImpdepurazione.
     * 
     * @param portataEstPrra
     */
    public void setPortataEstPrra(java.lang.Float portataEstPrra) {
        this.portataEstPrra = portataEstPrra;
    }


    /**
     * Gets the portataInvPar value for this CcostImpdepurazione.
     * 
     * @return portataInvPar
     */
    public java.lang.Float getPortataInvPar() {
        return portataInvPar;
    }


    /**
     * Sets the portataInvPar value for this CcostImpdepurazione.
     * 
     * @param portataInvPar
     */
    public void setPortataInvPar(java.lang.Float portataInvPar) {
        this.portataInvPar = portataInvPar;
    }


    /**
     * Gets the portataInvPot value for this CcostImpdepurazione.
     * 
     * @return portataInvPot
     */
    public java.lang.Float getPortataInvPot() {
        return portataInvPot;
    }


    /**
     * Sets the portataInvPot value for this CcostImpdepurazione.
     * 
     * @param portataInvPot
     */
    public void setPortataInvPot(java.lang.Float portataInvPot) {
        this.portataInvPot = portataInvPot;
    }


    /**
     * Gets the portataInvProgetto value for this CcostImpdepurazione.
     * 
     * @return portataInvProgetto
     */
    public java.lang.Float getPortataInvProgetto() {
        return portataInvProgetto;
    }


    /**
     * Sets the portataInvProgetto value for this CcostImpdepurazione.
     * 
     * @param portataInvProgetto
     */
    public void setPortataInvProgetto(java.lang.Float portataInvProgetto) {
        this.portataInvProgetto = portataInvProgetto;
    }


    /**
     * Gets the portataInvPrra value for this CcostImpdepurazione.
     * 
     * @return portataInvPrra
     */
    public java.lang.Float getPortataInvPrra() {
        return portataInvPrra;
    }


    /**
     * Sets the portataInvPrra value for this CcostImpdepurazione.
     * 
     * @param portataInvPrra
     */
    public void setPortataInvPrra(java.lang.Float portataInvPrra) {
        this.portataInvPrra = portataInvPrra;
    }


    /**
     * Gets the portataPuntaMista value for this CcostImpdepurazione.
     * 
     * @return portataPuntaMista
     */
    public java.lang.Float getPortataPuntaMista() {
        return portataPuntaMista;
    }


    /**
     * Sets the portataPuntaMista value for this CcostImpdepurazione.
     * 
     * @param portataPuntaMista
     */
    public void setPortataPuntaMista(java.lang.Float portataPuntaMista) {
        this.portataPuntaMista = portataPuntaMista;
    }


    /**
     * Gets the potenzialitaEst value for this CcostImpdepurazione.
     * 
     * @return potenzialitaEst
     */
    public java.lang.Float getPotenzialitaEst() {
        return potenzialitaEst;
    }


    /**
     * Sets the potenzialitaEst value for this CcostImpdepurazione.
     * 
     * @param potenzialitaEst
     */
    public void setPotenzialitaEst(java.lang.Float potenzialitaEst) {
        this.potenzialitaEst = potenzialitaEst;
    }


    /**
     * Gets the potenzialitaInv value for this CcostImpdepurazione.
     * 
     * @return potenzialitaInv
     */
    public java.lang.Float getPotenzialitaInv() {
        return potenzialitaInv;
    }


    /**
     * Sets the potenzialitaInv value for this CcostImpdepurazione.
     * 
     * @param potenzialitaInv
     */
    public void setPotenzialitaInv(java.lang.Float potenzialitaInv) {
        this.potenzialitaInv = potenzialitaInv;
    }


    /**
     * Gets the predNuoveUtenze value for this CcostImpdepurazione.
     * 
     * @return predNuoveUtenze
     */
    public java.lang.Integer getPredNuoveUtenze() {
        return predNuoveUtenze;
    }


    /**
     * Sets the predNuoveUtenze value for this CcostImpdepurazione.
     * 
     * @param predNuoveUtenze
     */
    public void setPredNuoveUtenze(java.lang.Integer predNuoveUtenze) {
        this.predNuoveUtenze = predNuoveUtenze;
    }


    /**
     * Gets the quantitaFanghi value for this CcostImpdepurazione.
     * 
     * @return quantitaFanghi
     */
    public java.lang.Float getQuantitaFanghi() {
        return quantitaFanghi;
    }


    /**
     * Sets the quantitaFanghi value for this CcostImpdepurazione.
     * 
     * @param quantitaFanghi
     */
    public void setQuantitaFanghi(java.lang.Float quantitaFanghi) {
        this.quantitaFanghi = quantitaFanghi;
    }


    /**
     * Gets the quantitaResidui value for this CcostImpdepurazione.
     * 
     * @return quantitaResidui
     */
    public java.lang.Float getQuantitaResidui() {
        return quantitaResidui;
    }


    /**
     * Sets the quantitaResidui value for this CcostImpdepurazione.
     * 
     * @param quantitaResidui
     */
    public void setQuantitaResidui(java.lang.Float quantitaResidui) {
        this.quantitaResidui = quantitaResidui;
    }


    /**
     * Gets the recinzione value for this CcostImpdepurazione.
     * 
     * @return recinzione
     */
    public java.lang.Integer getRecinzione() {
        return recinzione;
    }


    /**
     * Sets the recinzione value for this CcostImpdepurazione.
     * 
     * @param recinzione
     */
    public void setRecinzione(java.lang.Integer recinzione) {
        this.recinzione = recinzione;
    }


    /**
     * Gets the refluoAgricolo value for this CcostImpdepurazione.
     * 
     * @return refluoAgricolo
     */
    public java.lang.Float getRefluoAgricolo() {
        return refluoAgricolo;
    }


    /**
     * Sets the refluoAgricolo value for this CcostImpdepurazione.
     * 
     * @param refluoAgricolo
     */
    public void setRefluoAgricolo(java.lang.Float refluoAgricolo) {
        this.refluoAgricolo = refluoAgricolo;
    }


    /**
     * Gets the refluoAltro value for this CcostImpdepurazione.
     * 
     * @return refluoAltro
     */
    public java.lang.Float getRefluoAltro() {
        return refluoAltro;
    }


    /**
     * Sets the refluoAltro value for this CcostImpdepurazione.
     * 
     * @param refluoAltro
     */
    public void setRefluoAltro(java.lang.Float refluoAltro) {
        this.refluoAltro = refluoAltro;
    }


    /**
     * Gets the refluoDomestico value for this CcostImpdepurazione.
     * 
     * @return refluoDomestico
     */
    public java.lang.Float getRefluoDomestico() {
        return refluoDomestico;
    }


    /**
     * Sets the refluoDomestico value for this CcostImpdepurazione.
     * 
     * @param refluoDomestico
     */
    public void setRefluoDomestico(java.lang.Float refluoDomestico) {
        this.refluoDomestico = refluoDomestico;
    }


    /**
     * Gets the refluoIndustriale value for this CcostImpdepurazione.
     * 
     * @return refluoIndustriale
     */
    public java.lang.Float getRefluoIndustriale() {
        return refluoIndustriale;
    }


    /**
     * Sets the refluoIndustriale value for this CcostImpdepurazione.
     * 
     * @param refluoIndustriale
     */
    public void setRefluoIndustriale(java.lang.Float refluoIndustriale) {
        this.refluoIndustriale = refluoIndustriale;
    }


    /**
     * Gets the refluoZootecnico value for this CcostImpdepurazione.
     * 
     * @return refluoZootecnico
     */
    public java.lang.Float getRefluoZootecnico() {
        return refluoZootecnico;
    }


    /**
     * Sets the refluoZootecnico value for this CcostImpdepurazione.
     * 
     * @param refluoZootecnico
     */
    public void setRefluoZootecnico(java.lang.Float refluoZootecnico) {
        this.refluoZootecnico = refluoZootecnico;
    }


    /**
     * Gets the residenti value for this CcostImpdepurazione.
     * 
     * @return residenti
     */
    public java.lang.Integer getResidenti() {
        return residenti;
    }


    /**
     * Sets the residenti value for this CcostImpdepurazione.
     * 
     * @param residenti
     */
    public void setResidenti(java.lang.Integer residenti) {
        this.residenti = residenti;
    }


    /**
     * Gets the riutilizzoAltroTesto value for this CcostImpdepurazione.
     * 
     * @return riutilizzoAltroTesto
     */
    public java.lang.String getRiutilizzoAltroTesto() {
        return riutilizzoAltroTesto;
    }


    /**
     * Sets the riutilizzoAltroTesto value for this CcostImpdepurazione.
     * 
     * @param riutilizzoAltroTesto
     */
    public void setRiutilizzoAltroTesto(java.lang.String riutilizzoAltroTesto) {
        this.riutilizzoAltroTesto = riutilizzoAltroTesto;
    }


    /**
     * Gets the riutilizzoAltroValore value for this CcostImpdepurazione.
     * 
     * @return riutilizzoAltroValore
     */
    public java.lang.Float getRiutilizzoAltroValore() {
        return riutilizzoAltroValore;
    }


    /**
     * Sets the riutilizzoAltroValore value for this CcostImpdepurazione.
     * 
     * @param riutilizzoAltroValore
     */
    public void setRiutilizzoAltroValore(java.lang.Float riutilizzoAltroValore) {
        this.riutilizzoAltroValore = riutilizzoAltroValore;
    }


    /**
     * Gets the riutilizzoAmbientale value for this CcostImpdepurazione.
     * 
     * @return riutilizzoAmbientale
     */
    public java.lang.Float getRiutilizzoAmbientale() {
        return riutilizzoAmbientale;
    }


    /**
     * Sets the riutilizzoAmbientale value for this CcostImpdepurazione.
     * 
     * @param riutilizzoAmbientale
     */
    public void setRiutilizzoAmbientale(java.lang.Float riutilizzoAmbientale) {
        this.riutilizzoAmbientale = riutilizzoAmbientale;
    }


    /**
     * Gets the riutilizzoCivile value for this CcostImpdepurazione.
     * 
     * @return riutilizzoCivile
     */
    public java.lang.Float getRiutilizzoCivile() {
        return riutilizzoCivile;
    }


    /**
     * Sets the riutilizzoCivile value for this CcostImpdepurazione.
     * 
     * @param riutilizzoCivile
     */
    public void setRiutilizzoCivile(java.lang.Float riutilizzoCivile) {
        this.riutilizzoCivile = riutilizzoCivile;
    }


    /**
     * Gets the riutilizzoIndustriale value for this CcostImpdepurazione.
     * 
     * @return riutilizzoIndustriale
     */
    public java.lang.Float getRiutilizzoIndustriale() {
        return riutilizzoIndustriale;
    }


    /**
     * Sets the riutilizzoIndustriale value for this CcostImpdepurazione.
     * 
     * @param riutilizzoIndustriale
     */
    public void setRiutilizzoIndustriale(java.lang.Float riutilizzoIndustriale) {
        this.riutilizzoIndustriale = riutilizzoIndustriale;
    }


    /**
     * Gets the riutilizzoIrriguo value for this CcostImpdepurazione.
     * 
     * @return riutilizzoIrriguo
     */
    public java.lang.Float getRiutilizzoIrriguo() {
        return riutilizzoIrriguo;
    }


    /**
     * Sets the riutilizzoIrriguo value for this CcostImpdepurazione.
     * 
     * @param riutilizzoIrriguo
     */
    public void setRiutilizzoIrriguo(java.lang.Float riutilizzoIrriguo) {
        this.riutilizzoIrriguo = riutilizzoIrriguo;
    }


    /**
     * Gets the schemaPrra value for this CcostImpdepurazione.
     * 
     * @return schemaPrra
     */
    public java.lang.String getSchemaPrra() {
        return schemaPrra;
    }


    /**
     * Sets the schemaPrra value for this CcostImpdepurazione.
     * 
     * @param schemaPrra
     */
    public void setSchemaPrra(java.lang.String schemaPrra) {
        this.schemaPrra = schemaPrra;
    }


    /**
     * Gets the segnaletica value for this CcostImpdepurazione.
     * 
     * @return segnaletica
     */
    public java.lang.String getSegnaletica() {
        return segnaletica;
    }


    /**
     * Sets the segnaletica value for this CcostImpdepurazione.
     * 
     * @param segnaletica
     */
    public void setSegnaletica(java.lang.String segnaletica) {
        this.segnaletica = segnaletica;
    }


    /**
     * Gets the smaltFanghiAgricoltura value for this CcostImpdepurazione.
     * 
     * @return smaltFanghiAgricoltura
     */
    public java.lang.Float getSmaltFanghiAgricoltura() {
        return smaltFanghiAgricoltura;
    }


    /**
     * Sets the smaltFanghiAgricoltura value for this CcostImpdepurazione.
     * 
     * @param smaltFanghiAgricoltura
     */
    public void setSmaltFanghiAgricoltura(java.lang.Float smaltFanghiAgricoltura) {
        this.smaltFanghiAgricoltura = smaltFanghiAgricoltura;
    }


    /**
     * Gets the smaltFanghiCompostato value for this CcostImpdepurazione.
     * 
     * @return smaltFanghiCompostato
     */
    public java.lang.Float getSmaltFanghiCompostato() {
        return smaltFanghiCompostato;
    }


    /**
     * Sets the smaltFanghiCompostato value for this CcostImpdepurazione.
     * 
     * @param smaltFanghiCompostato
     */
    public void setSmaltFanghiCompostato(java.lang.Float smaltFanghiCompostato) {
        this.smaltFanghiCompostato = smaltFanghiCompostato;
    }


    /**
     * Gets the smaltFanghiDiscarica value for this CcostImpdepurazione.
     * 
     * @return smaltFanghiDiscarica
     */
    public java.lang.Float getSmaltFanghiDiscarica() {
        return smaltFanghiDiscarica;
    }


    /**
     * Sets the smaltFanghiDiscarica value for this CcostImpdepurazione.
     * 
     * @param smaltFanghiDiscarica
     */
    public void setSmaltFanghiDiscarica(java.lang.Float smaltFanghiDiscarica) {
        this.smaltFanghiDiscarica = smaltFanghiDiscarica;
    }


    /**
     * Gets the smaltFanghiInceneritore value for this CcostImpdepurazione.
     * 
     * @return smaltFanghiInceneritore
     */
    public java.lang.Float getSmaltFanghiInceneritore() {
        return smaltFanghiInceneritore;
    }


    /**
     * Sets the smaltFanghiInceneritore value for this CcostImpdepurazione.
     * 
     * @param smaltFanghiInceneritore
     */
    public void setSmaltFanghiInceneritore(java.lang.Float smaltFanghiInceneritore) {
        this.smaltFanghiInceneritore = smaltFanghiInceneritore;
    }


    /**
     * Gets the statoAttualeNu value for this CcostImpdepurazione.
     * 
     * @return statoAttualeNu
     */
    public java.lang.String getStatoAttualeNu() {
        return statoAttualeNu;
    }


    /**
     * Sets the statoAttualeNu value for this CcostImpdepurazione.
     * 
     * @param statoAttualeNu
     */
    public void setStatoAttualeNu(java.lang.String statoAttualeNu) {
        this.statoAttualeNu = statoAttualeNu;
    }


    /**
     * Gets the statoFunzionalita value for this CcostImpdepurazione.
     * 
     * @return statoFunzionalita
     */
    public java.lang.String getStatoFunzionalita() {
        return statoFunzionalita;
    }


    /**
     * Sets the statoFunzionalita value for this CcostImpdepurazione.
     * 
     * @param statoFunzionalita
     */
    public void setStatoFunzionalita(java.lang.String statoFunzionalita) {
        this.statoFunzionalita = statoFunzionalita;
    }


    /**
     * Gets the tariffaDepuratore value for this CcostImpdepurazione.
     * 
     * @return tariffaDepuratore
     */
    public java.lang.Float getTariffaDepuratore() {
        return tariffaDepuratore;
    }


    /**
     * Sets the tariffaDepuratore value for this CcostImpdepurazione.
     * 
     * @param tariffaDepuratore
     */
    public void setTariffaDepuratore(java.lang.Float tariffaDepuratore) {
        this.tariffaDepuratore = tariffaDepuratore;
    }


    /**
     * Gets the tariffaFogne value for this CcostImpdepurazione.
     * 
     * @return tariffaFogne
     */
    public java.lang.Float getTariffaFogne() {
        return tariffaFogne;
    }


    /**
     * Sets the tariffaFogne value for this CcostImpdepurazione.
     * 
     * @param tariffaFogne
     */
    public void setTariffaFogne(java.lang.Float tariffaFogne) {
        this.tariffaFogne = tariffaFogne;
    }


    /**
     * Gets the tariffeAccantonate value for this CcostImpdepurazione.
     * 
     * @return tariffeAccantonate
     */
    public java.lang.Float getTariffeAccantonate() {
        return tariffeAccantonate;
    }


    /**
     * Sets the tariffeAccantonate value for this CcostImpdepurazione.
     * 
     * @param tariffeAccantonate
     */
    public void setTariffeAccantonate(java.lang.Float tariffeAccantonate) {
        this.tariffeAccantonate = tariffeAccantonate;
    }


    /**
     * Gets the tariffeRiscosse value for this CcostImpdepurazione.
     * 
     * @return tariffeRiscosse
     */
    public java.lang.Float getTariffeRiscosse() {
        return tariffeRiscosse;
    }


    /**
     * Sets the tariffeRiscosse value for this CcostImpdepurazione.
     * 
     * @param tariffeRiscosse
     */
    public void setTariffeRiscosse(java.lang.Float tariffeRiscosse) {
        this.tariffeRiscosse = tariffeRiscosse;
    }


    /**
     * Gets the telecontrollo value for this CcostImpdepurazione.
     * 
     * @return telecontrollo
     */
    public java.lang.Integer getTelecontrollo() {
        return telecontrollo;
    }


    /**
     * Sets the telecontrollo value for this CcostImpdepurazione.
     * 
     * @param telecontrollo
     */
    public void setTelecontrollo(java.lang.Integer telecontrollo) {
        this.telecontrollo = telecontrollo;
    }


    /**
     * Gets the terraBattuta value for this CcostImpdepurazione.
     * 
     * @return terraBattuta
     */
    public java.lang.Integer getTerraBattuta() {
        return terraBattuta;
    }


    /**
     * Sets the terraBattuta value for this CcostImpdepurazione.
     * 
     * @param terraBattuta
     */
    public void setTerraBattuta(java.lang.Integer terraBattuta) {
        this.terraBattuta = terraBattuta;
    }


    /**
     * Gets the tipoAltroRefluo value for this CcostImpdepurazione.
     * 
     * @return tipoAltroRefluo
     */
    public java.lang.String getTipoAltroRefluo() {
        return tipoAltroRefluo;
    }


    /**
     * Sets the tipoAltroRefluo value for this CcostImpdepurazione.
     * 
     * @param tipoAltroRefluo
     */
    public void setTipoAltroRefluo(java.lang.String tipoAltroRefluo) {
        this.tipoAltroRefluo = tipoAltroRefluo;
    }


    /**
     * Gets the tipoAutomazione value for this CcostImpdepurazione.
     * 
     * @return tipoAutomazione
     */
    public java.lang.String getTipoAutomazione() {
        return tipoAutomazione;
    }


    /**
     * Sets the tipoAutomazione value for this CcostImpdepurazione.
     * 
     * @param tipoAutomazione
     */
    public void setTipoAutomazione(java.lang.String tipoAutomazione) {
        this.tipoAutomazione = tipoAutomazione;
    }


    /**
     * Gets the tipoFonteFinNu value for this CcostImpdepurazione.
     * 
     * @return tipoFonteFinNu
     */
    public java.lang.String getTipoFonteFinNu() {
        return tipoFonteFinNu;
    }


    /**
     * Sets the tipoFonteFinNu value for this CcostImpdepurazione.
     * 
     * @param tipoFonteFinNu
     */
    public void setTipoFonteFinNu(java.lang.String tipoFonteFinNu) {
        this.tipoFonteFinNu = tipoFonteFinNu;
    }


    /**
     * Gets the tipoProcesso value for this CcostImpdepurazione.
     * 
     * @return tipoProcesso
     */
    public com.hyperborea.sira.ws.ImpDepTipiProcesso getTipoProcesso() {
        return tipoProcesso;
    }


    /**
     * Sets the tipoProcesso value for this CcostImpdepurazione.
     * 
     * @param tipoProcesso
     */
    public void setTipoProcesso(com.hyperborea.sira.ws.ImpDepTipiProcesso tipoProcesso) {
        this.tipoProcesso = tipoProcesso;
    }


    /**
     * Gets the tipoTrattamento value for this CcostImpdepurazione.
     * 
     * @return tipoTrattamento
     */
    public com.hyperborea.sira.ws.ImpDepTipiTrattamento getTipoTrattamento() {
        return tipoTrattamento;
    }


    /**
     * Sets the tipoTrattamento value for this CcostImpdepurazione.
     * 
     * @param tipoTrattamento
     */
    public void setTipoTrattamento(com.hyperborea.sira.ws.ImpDepTipiTrattamento tipoTrattamento) {
        this.tipoTrattamento = tipoTrattamento;
    }


    /**
     * Gets the trattamento value for this CcostImpdepurazione.
     * 
     * @return trattamento
     */
    public com.hyperborea.sira.ws.ImpDepTrattamenti getTrattamento() {
        return trattamento;
    }


    /**
     * Sets the trattamento value for this CcostImpdepurazione.
     * 
     * @param trattamento
     */
    public void setTrattamento(com.hyperborea.sira.ws.ImpDepTrattamenti trattamento) {
        this.trattamento = trattamento;
    }


    /**
     * Gets the ubicazioneMisuratore value for this CcostImpdepurazione.
     * 
     * @return ubicazioneMisuratore
     */
    public java.lang.String getUbicazioneMisuratore() {
        return ubicazioneMisuratore;
    }


    /**
     * Sets the ubicazioneMisuratore value for this CcostImpdepurazione.
     * 
     * @param ubicazioneMisuratore
     */
    public void setUbicazioneMisuratore(java.lang.String ubicazioneMisuratore) {
        this.ubicazioneMisuratore = ubicazioneMisuratore;
    }


    /**
     * Gets the utilizzoFinaleFanghi value for this CcostImpdepurazione.
     * 
     * @return utilizzoFinaleFanghi
     */
    public java.lang.String getUtilizzoFinaleFanghi() {
        return utilizzoFinaleFanghi;
    }


    /**
     * Sets the utilizzoFinaleFanghi value for this CcostImpdepurazione.
     * 
     * @param utilizzoFinaleFanghi
     */
    public void setUtilizzoFinaleFanghi(java.lang.String utilizzoFinaleFanghi) {
        this.utilizzoFinaleFanghi = utilizzoFinaleFanghi;
    }


    /**
     * Gets the volumeBollettato value for this CcostImpdepurazione.
     * 
     * @return volumeBollettato
     */
    public java.lang.Float getVolumeBollettato() {
        return volumeBollettato;
    }


    /**
     * Sets the volumeBollettato value for this CcostImpdepurazione.
     * 
     * @param volumeBollettato
     */
    public void setVolumeBollettato(java.lang.Float volumeBollettato) {
        this.volumeBollettato = volumeBollettato;
    }


    /**
     * Gets the volumeTrattato value for this CcostImpdepurazione.
     * 
     * @return volumeTrattato
     */
    public java.lang.Float getVolumeTrattato() {
        return volumeTrattato;
    }


    /**
     * Sets the volumeTrattato value for this CcostImpdepurazione.
     * 
     * @param volumeTrattato
     */
    public void setVolumeTrattato(java.lang.Float volumeTrattato) {
        this.volumeTrattato = volumeTrattato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostImpdepurazione)) return false;
        CcostImpdepurazione other = (CcostImpdepurazione) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.abitantiServiti==null && other.getAbitantiServiti()==null) || 
             (this.abitantiServiti!=null &&
              this.abitantiServiti.equals(other.getAbitantiServiti()))) &&
            ((this.adeguamento152==null && other.getAdeguamento152()==null) || 
             (this.adeguamento152!=null &&
              this.adeguamento152.equals(other.getAdeguamento152()))) &&
            ((this.alberi==null && other.getAlberi()==null) || 
             (this.alberi!=null &&
              this.alberi.equals(other.getAlberi()))) &&
            ((this.altroLaboratorio==null && other.getAltroLaboratorio()==null) || 
             (this.altroLaboratorio!=null &&
              this.altroLaboratorio.equals(other.getAltroLaboratorio()))) &&
            ((this.altroTipoTrattamento==null && other.getAltroTipoTrattamento()==null) || 
             (this.altroTipoTrattamento!=null &&
              this.altroTipoTrattamento.equals(other.getAltroTipoTrattamento()))) &&
            ((this.annoChiusura==null && other.getAnnoChiusura()==null) || 
             (this.annoChiusura!=null &&
              this.annoChiusura.equals(other.getAnnoChiusura()))) &&
            ((this.annoEsercizio==null && other.getAnnoEsercizio()==null) || 
             (this.annoEsercizio!=null &&
              this.annoEsercizio.equals(other.getAnnoEsercizio()))) &&
            ((this.annoVolBollettato==null && other.getAnnoVolBollettato()==null) || 
             (this.annoVolBollettato!=null &&
              this.annoVolBollettato.equals(other.getAnnoVolBollettato()))) &&
            ((this.annoVolumeTrattato==null && other.getAnnoVolumeTrattato()==null) || 
             (this.annoVolumeTrattato!=null &&
              this.annoVolumeTrattato.equals(other.getAnnoVolumeTrattato()))) &&
            ((this.asfalto==null && other.getAsfalto()==null) || 
             (this.asfalto!=null &&
              this.asfalto.equals(other.getAsfalto()))) &&
            ((this.automazione==null && other.getAutomazione()==null) || 
             (this.automazione!=null &&
              this.automazione.equals(other.getAutomazione()))) &&
            ((this.bod5==null && other.getBod5()==null) || 
             (this.bod5!=null &&
              this.bod5.equals(other.getBod5()))) &&
            ((this.cancello==null && other.getCancello()==null) || 
             (this.cancello!=null &&
              this.cancello.equals(other.getCancello()))) &&
            ((this.capIdrProg==null && other.getCapIdrProg()==null) || 
             (this.capIdrProg!=null &&
              this.capIdrProg.equals(other.getCapIdrProg()))) &&
            ((this.capIdrServ==null && other.getCapIdrServ()==null) || 
             (this.capIdrServ!=null &&
              this.capIdrServ.equals(other.getCapIdrServ()))) &&
            ((this.capOrgProg==null && other.getCapOrgProg()==null) || 
             (this.capOrgProg!=null &&
              this.capOrgProg.equals(other.getCapOrgProg()))) &&
            ((this.capOrgServ==null && other.getCapOrgServ()==null) || 
             (this.capOrgServ!=null &&
              this.capOrgServ.equals(other.getCapOrgServ()))) &&
            ((this.capacitaEstProgetto==null && other.getCapacitaEstProgetto()==null) || 
             (this.capacitaEstProgetto!=null &&
              this.capacitaEstProgetto.equals(other.getCapacitaEstProgetto()))) &&
            ((this.capacitaEstPrra==null && other.getCapacitaEstPrra()==null) || 
             (this.capacitaEstPrra!=null &&
              this.capacitaEstPrra.equals(other.getCapacitaEstPrra()))) &&
            ((this.capacitaInvProgetto==null && other.getCapacitaInvProgetto()==null) || 
             (this.capacitaInvProgetto!=null &&
              this.capacitaInvProgetto.equals(other.getCapacitaInvProgetto()))) &&
            ((this.capacitaInvPrra==null && other.getCapacitaInvPrra()==null) || 
             (this.capacitaInvPrra!=null &&
              this.capacitaInvPrra.equals(other.getCapacitaInvPrra()))) &&
            ((this.caricoOrganicoMedio==null && other.getCaricoOrganicoMedio()==null) || 
             (this.caricoOrganicoMedio!=null &&
              this.caricoOrganicoMedio.equals(other.getCaricoOrganicoMedio()))) &&
            ((this.cemento==null && other.getCemento()==null) || 
             (this.cemento!=null &&
              this.cemento.equals(other.getCemento()))) &&
            ((this.codiceNazionale==null && other.getCodiceNazionale()==null) || 
             (this.codiceNazionale!=null &&
              this.codiceNazionale.equals(other.getCodiceNazionale()))) &&
            ((this.codiceRegionale==null && other.getCodiceRegionale()==null) || 
             (this.codiceRegionale!=null &&
              this.codiceRegionale.equals(other.getCodiceRegionale()))) &&
            ((this.competenzaConsortile==null && other.getCompetenzaConsortile()==null) || 
             (this.competenzaConsortile!=null &&
              this.competenzaConsortile.equals(other.getCompetenzaConsortile()))) &&
            ((this.competenzaPrra==null && other.getCompetenzaPrra()==null) || 
             (this.competenzaPrra!=null &&
              this.competenzaPrra.equals(other.getCompetenzaPrra()))) &&
            ((this.costoGestione==null && other.getCostoGestione()==null) || 
             (this.costoGestione!=null &&
              this.costoGestione.equals(other.getCostoGestione()))) &&
            ((this.depuratoreCompetenzaPrras==null && other.getDepuratoreCompetenzaPrras()==null) || 
             (this.depuratoreCompetenzaPrras!=null &&
              java.util.Arrays.equals(this.depuratoreCompetenzaPrras, other.getDepuratoreCompetenzaPrras()))) &&
            ((this.depuratoreCompetenzas==null && other.getDepuratoreCompetenzas()==null) || 
             (this.depuratoreCompetenzas!=null &&
              java.util.Arrays.equals(this.depuratoreCompetenzas, other.getDepuratoreCompetenzas()))) &&
            ((this.depuratoreSezionis==null && other.getDepuratoreSezionis()==null) || 
             (this.depuratoreSezionis!=null &&
              java.util.Arrays.equals(this.depuratoreSezionis, other.getDepuratoreSezionis()))) &&
            ((this.depuratoreTipoOperes==null && other.getDepuratoreTipoOperes()==null) || 
             (this.depuratoreTipoOperes!=null &&
              java.util.Arrays.equals(this.depuratoreTipoOperes, other.getDepuratoreTipoOperes()))) &&
            ((this.destLineaFanghi==null && other.getDestLineaFanghi()==null) || 
             (this.destLineaFanghi!=null &&
              this.destLineaFanghi.equals(other.getDestLineaFanghi()))) &&
            ((this.distanzaAbitazioni==null && other.getDistanzaAbitazioni()==null) || 
             (this.distanzaAbitazioni!=null &&
              this.distanzaAbitazioni.equals(other.getDistanzaAbitazioni()))) &&
            ((this.equivalentiIndustriali==null && other.getEquivalentiIndustriali()==null) || 
             (this.equivalentiIndustriali!=null &&
              this.equivalentiIndustriali.equals(other.getEquivalentiIndustriali()))) &&
            ((this.fluttuanti==null && other.getFluttuanti()==null) || 
             (this.fluttuanti!=null &&
              this.fluttuanti.equals(other.getFluttuanti()))) &&
            ((this.fognaturaSeparata==null && other.getFognaturaSeparata()==null) || 
             (this.fognaturaSeparata!=null &&
              this.fognaturaSeparata.equals(other.getFognaturaSeparata()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.illuminazione==null && other.getIlluminazione()==null) || 
             (this.illuminazione!=null &&
              this.illuminazione.equals(other.getIlluminazione()))) &&
            ((this.informatizzazioneDati==null && other.getInformatizzazioneDati()==null) || 
             (this.informatizzazioneDati!=null &&
              this.informatizzazioneDati.equals(other.getInformatizzazioneDati()))) &&
            ((this.labAutoanalisiCent==null && other.getLabAutoanalisiCent()==null) || 
             (this.labAutoanalisiCent!=null &&
              this.labAutoanalisiCent.equals(other.getLabAutoanalisiCent()))) &&
            ((this.labAutoanalisiExt==null && other.getLabAutoanalisiExt()==null) || 
             (this.labAutoanalisiExt!=null &&
              this.labAutoanalisiExt.equals(other.getLabAutoanalisiExt()))) &&
            ((this.labAutoanalisiInt==null && other.getLabAutoanalisiInt()==null) || 
             (this.labAutoanalisiInt!=null &&
              this.labAutoanalisiInt.equals(other.getLabAutoanalisiInt()))) &&
            ((this.lineaFanghi==null && other.getLineaFanghi()==null) || 
             (this.lineaFanghi!=null &&
              this.lineaFanghi.equals(other.getLineaFanghi()))) &&
            ((this.localitaSmaltimento==null && other.getLocalitaSmaltimento()==null) || 
             (this.localitaSmaltimento!=null &&
              this.localitaSmaltimento.equals(other.getLocalitaSmaltimento()))) &&
            ((this.mesePunta==null && other.getMesePunta()==null) || 
             (this.mesePunta!=null &&
              this.mesePunta.equals(other.getMesePunta()))) &&
            ((this.misuratorePortata==null && other.getMisuratorePortata()==null) || 
             (this.misuratorePortata!=null &&
              this.misuratorePortata.equals(other.getMisuratorePortata()))) &&
            ((this.misuratoriOnline==null && other.getMisuratoriOnline()==null) || 
             (this.misuratoriOnline!=null &&
              this.misuratoriOnline.equals(other.getMisuratoriOnline()))) &&
            ((this.misuratoriTipo==null && other.getMisuratoriTipo()==null) || 
             (this.misuratoriTipo!=null &&
              this.misuratoriTipo.equals(other.getMisuratoriTipo()))) &&
            ((this.noteInterventi==null && other.getNoteInterventi()==null) || 
             (this.noteInterventi!=null &&
              this.noteInterventi.equals(other.getNoteInterventi()))) &&
            ((this.noteSmaltRiutilizzo==null && other.getNoteSmaltRiutilizzo()==null) || 
             (this.noteSmaltRiutilizzo!=null &&
              this.noteSmaltRiutilizzo.equals(other.getNoteSmaltRiutilizzo()))) &&
            ((this.numInterventiOrd==null && other.getNumInterventiOrd()==null) || 
             (this.numInterventiOrd!=null &&
              this.numInterventiOrd.equals(other.getNumInterventiOrd()))) &&
            ((this.numInterventiStraord==null && other.getNumInterventiStraord()==null) || 
             (this.numInterventiStraord!=null &&
              this.numInterventiStraord.equals(other.getNumInterventiStraord()))) &&
            ((this.numLineeBiologiche==null && other.getNumLineeBiologiche()==null) || 
             (this.numLineeBiologiche!=null &&
              this.numLineeBiologiche.equals(other.getNumLineeBiologiche()))) &&
            ((this.numLineeFanghi==null && other.getNumLineeFanghi()==null) || 
             (this.numLineeFanghi!=null &&
              this.numLineeFanghi.equals(other.getNumLineeFanghi()))) &&
            ((this.parametriEst==null && other.getParametriEst()==null) || 
             (this.parametriEst!=null &&
              this.parametriEst.equals(other.getParametriEst()))) &&
            ((this.parametriInv==null && other.getParametriInv()==null) || 
             (this.parametriInv!=null &&
              this.parametriInv.equals(other.getParametriInv()))) &&
            ((this.percSecco==null && other.getPercSecco()==null) || 
             (this.percSecco!=null &&
              this.percSecco.equals(other.getPercSecco()))) &&
            ((this.percUmidita==null && other.getPercUmidita()==null) || 
             (this.percUmidita!=null &&
              this.percUmidita.equals(other.getPercUmidita()))) &&
            ((this.pianiStralcio==null && other.getPianiStralcio()==null) || 
             (this.pianiStralcio!=null &&
              this.pianiStralcio.equals(other.getPianiStralcio()))) &&
            ((this.portataBypass==null && other.getPortataBypass()==null) || 
             (this.portataBypass!=null &&
              this.portataBypass.equals(other.getPortataBypass()))) &&
            ((this.portataEstPar==null && other.getPortataEstPar()==null) || 
             (this.portataEstPar!=null &&
              this.portataEstPar.equals(other.getPortataEstPar()))) &&
            ((this.portataEstPot==null && other.getPortataEstPot()==null) || 
             (this.portataEstPot!=null &&
              this.portataEstPot.equals(other.getPortataEstPot()))) &&
            ((this.portataEstProgetto==null && other.getPortataEstProgetto()==null) || 
             (this.portataEstProgetto!=null &&
              this.portataEstProgetto.equals(other.getPortataEstProgetto()))) &&
            ((this.portataEstPrra==null && other.getPortataEstPrra()==null) || 
             (this.portataEstPrra!=null &&
              this.portataEstPrra.equals(other.getPortataEstPrra()))) &&
            ((this.portataInvPar==null && other.getPortataInvPar()==null) || 
             (this.portataInvPar!=null &&
              this.portataInvPar.equals(other.getPortataInvPar()))) &&
            ((this.portataInvPot==null && other.getPortataInvPot()==null) || 
             (this.portataInvPot!=null &&
              this.portataInvPot.equals(other.getPortataInvPot()))) &&
            ((this.portataInvProgetto==null && other.getPortataInvProgetto()==null) || 
             (this.portataInvProgetto!=null &&
              this.portataInvProgetto.equals(other.getPortataInvProgetto()))) &&
            ((this.portataInvPrra==null && other.getPortataInvPrra()==null) || 
             (this.portataInvPrra!=null &&
              this.portataInvPrra.equals(other.getPortataInvPrra()))) &&
            ((this.portataPuntaMista==null && other.getPortataPuntaMista()==null) || 
             (this.portataPuntaMista!=null &&
              this.portataPuntaMista.equals(other.getPortataPuntaMista()))) &&
            ((this.potenzialitaEst==null && other.getPotenzialitaEst()==null) || 
             (this.potenzialitaEst!=null &&
              this.potenzialitaEst.equals(other.getPotenzialitaEst()))) &&
            ((this.potenzialitaInv==null && other.getPotenzialitaInv()==null) || 
             (this.potenzialitaInv!=null &&
              this.potenzialitaInv.equals(other.getPotenzialitaInv()))) &&
            ((this.predNuoveUtenze==null && other.getPredNuoveUtenze()==null) || 
             (this.predNuoveUtenze!=null &&
              this.predNuoveUtenze.equals(other.getPredNuoveUtenze()))) &&
            ((this.quantitaFanghi==null && other.getQuantitaFanghi()==null) || 
             (this.quantitaFanghi!=null &&
              this.quantitaFanghi.equals(other.getQuantitaFanghi()))) &&
            ((this.quantitaResidui==null && other.getQuantitaResidui()==null) || 
             (this.quantitaResidui!=null &&
              this.quantitaResidui.equals(other.getQuantitaResidui()))) &&
            ((this.recinzione==null && other.getRecinzione()==null) || 
             (this.recinzione!=null &&
              this.recinzione.equals(other.getRecinzione()))) &&
            ((this.refluoAgricolo==null && other.getRefluoAgricolo()==null) || 
             (this.refluoAgricolo!=null &&
              this.refluoAgricolo.equals(other.getRefluoAgricolo()))) &&
            ((this.refluoAltro==null && other.getRefluoAltro()==null) || 
             (this.refluoAltro!=null &&
              this.refluoAltro.equals(other.getRefluoAltro()))) &&
            ((this.refluoDomestico==null && other.getRefluoDomestico()==null) || 
             (this.refluoDomestico!=null &&
              this.refluoDomestico.equals(other.getRefluoDomestico()))) &&
            ((this.refluoIndustriale==null && other.getRefluoIndustriale()==null) || 
             (this.refluoIndustriale!=null &&
              this.refluoIndustriale.equals(other.getRefluoIndustriale()))) &&
            ((this.refluoZootecnico==null && other.getRefluoZootecnico()==null) || 
             (this.refluoZootecnico!=null &&
              this.refluoZootecnico.equals(other.getRefluoZootecnico()))) &&
            ((this.residenti==null && other.getResidenti()==null) || 
             (this.residenti!=null &&
              this.residenti.equals(other.getResidenti()))) &&
            ((this.riutilizzoAltroTesto==null && other.getRiutilizzoAltroTesto()==null) || 
             (this.riutilizzoAltroTesto!=null &&
              this.riutilizzoAltroTesto.equals(other.getRiutilizzoAltroTesto()))) &&
            ((this.riutilizzoAltroValore==null && other.getRiutilizzoAltroValore()==null) || 
             (this.riutilizzoAltroValore!=null &&
              this.riutilizzoAltroValore.equals(other.getRiutilizzoAltroValore()))) &&
            ((this.riutilizzoAmbientale==null && other.getRiutilizzoAmbientale()==null) || 
             (this.riutilizzoAmbientale!=null &&
              this.riutilizzoAmbientale.equals(other.getRiutilizzoAmbientale()))) &&
            ((this.riutilizzoCivile==null && other.getRiutilizzoCivile()==null) || 
             (this.riutilizzoCivile!=null &&
              this.riutilizzoCivile.equals(other.getRiutilizzoCivile()))) &&
            ((this.riutilizzoIndustriale==null && other.getRiutilizzoIndustriale()==null) || 
             (this.riutilizzoIndustriale!=null &&
              this.riutilizzoIndustriale.equals(other.getRiutilizzoIndustriale()))) &&
            ((this.riutilizzoIrriguo==null && other.getRiutilizzoIrriguo()==null) || 
             (this.riutilizzoIrriguo!=null &&
              this.riutilizzoIrriguo.equals(other.getRiutilizzoIrriguo()))) &&
            ((this.schemaPrra==null && other.getSchemaPrra()==null) || 
             (this.schemaPrra!=null &&
              this.schemaPrra.equals(other.getSchemaPrra()))) &&
            ((this.segnaletica==null && other.getSegnaletica()==null) || 
             (this.segnaletica!=null &&
              this.segnaletica.equals(other.getSegnaletica()))) &&
            ((this.smaltFanghiAgricoltura==null && other.getSmaltFanghiAgricoltura()==null) || 
             (this.smaltFanghiAgricoltura!=null &&
              this.smaltFanghiAgricoltura.equals(other.getSmaltFanghiAgricoltura()))) &&
            ((this.smaltFanghiCompostato==null && other.getSmaltFanghiCompostato()==null) || 
             (this.smaltFanghiCompostato!=null &&
              this.smaltFanghiCompostato.equals(other.getSmaltFanghiCompostato()))) &&
            ((this.smaltFanghiDiscarica==null && other.getSmaltFanghiDiscarica()==null) || 
             (this.smaltFanghiDiscarica!=null &&
              this.smaltFanghiDiscarica.equals(other.getSmaltFanghiDiscarica()))) &&
            ((this.smaltFanghiInceneritore==null && other.getSmaltFanghiInceneritore()==null) || 
             (this.smaltFanghiInceneritore!=null &&
              this.smaltFanghiInceneritore.equals(other.getSmaltFanghiInceneritore()))) &&
            ((this.statoAttualeNu==null && other.getStatoAttualeNu()==null) || 
             (this.statoAttualeNu!=null &&
              this.statoAttualeNu.equals(other.getStatoAttualeNu()))) &&
            ((this.statoFunzionalita==null && other.getStatoFunzionalita()==null) || 
             (this.statoFunzionalita!=null &&
              this.statoFunzionalita.equals(other.getStatoFunzionalita()))) &&
            ((this.tariffaDepuratore==null && other.getTariffaDepuratore()==null) || 
             (this.tariffaDepuratore!=null &&
              this.tariffaDepuratore.equals(other.getTariffaDepuratore()))) &&
            ((this.tariffaFogne==null && other.getTariffaFogne()==null) || 
             (this.tariffaFogne!=null &&
              this.tariffaFogne.equals(other.getTariffaFogne()))) &&
            ((this.tariffeAccantonate==null && other.getTariffeAccantonate()==null) || 
             (this.tariffeAccantonate!=null &&
              this.tariffeAccantonate.equals(other.getTariffeAccantonate()))) &&
            ((this.tariffeRiscosse==null && other.getTariffeRiscosse()==null) || 
             (this.tariffeRiscosse!=null &&
              this.tariffeRiscosse.equals(other.getTariffeRiscosse()))) &&
            ((this.telecontrollo==null && other.getTelecontrollo()==null) || 
             (this.telecontrollo!=null &&
              this.telecontrollo.equals(other.getTelecontrollo()))) &&
            ((this.terraBattuta==null && other.getTerraBattuta()==null) || 
             (this.terraBattuta!=null &&
              this.terraBattuta.equals(other.getTerraBattuta()))) &&
            ((this.tipoAltroRefluo==null && other.getTipoAltroRefluo()==null) || 
             (this.tipoAltroRefluo!=null &&
              this.tipoAltroRefluo.equals(other.getTipoAltroRefluo()))) &&
            ((this.tipoAutomazione==null && other.getTipoAutomazione()==null) || 
             (this.tipoAutomazione!=null &&
              this.tipoAutomazione.equals(other.getTipoAutomazione()))) &&
            ((this.tipoFonteFinNu==null && other.getTipoFonteFinNu()==null) || 
             (this.tipoFonteFinNu!=null &&
              this.tipoFonteFinNu.equals(other.getTipoFonteFinNu()))) &&
            ((this.tipoProcesso==null && other.getTipoProcesso()==null) || 
             (this.tipoProcesso!=null &&
              this.tipoProcesso.equals(other.getTipoProcesso()))) &&
            ((this.tipoTrattamento==null && other.getTipoTrattamento()==null) || 
             (this.tipoTrattamento!=null &&
              this.tipoTrattamento.equals(other.getTipoTrattamento()))) &&
            ((this.trattamento==null && other.getTrattamento()==null) || 
             (this.trattamento!=null &&
              this.trattamento.equals(other.getTrattamento()))) &&
            ((this.ubicazioneMisuratore==null && other.getUbicazioneMisuratore()==null) || 
             (this.ubicazioneMisuratore!=null &&
              this.ubicazioneMisuratore.equals(other.getUbicazioneMisuratore()))) &&
            ((this.utilizzoFinaleFanghi==null && other.getUtilizzoFinaleFanghi()==null) || 
             (this.utilizzoFinaleFanghi!=null &&
              this.utilizzoFinaleFanghi.equals(other.getUtilizzoFinaleFanghi()))) &&
            ((this.volumeBollettato==null && other.getVolumeBollettato()==null) || 
             (this.volumeBollettato!=null &&
              this.volumeBollettato.equals(other.getVolumeBollettato()))) &&
            ((this.volumeTrattato==null && other.getVolumeTrattato()==null) || 
             (this.volumeTrattato!=null &&
              this.volumeTrattato.equals(other.getVolumeTrattato())));
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
        if (getAbitantiServiti() != null) {
            _hashCode += getAbitantiServiti().hashCode();
        }
        if (getAdeguamento152() != null) {
            _hashCode += getAdeguamento152().hashCode();
        }
        if (getAlberi() != null) {
            _hashCode += getAlberi().hashCode();
        }
        if (getAltroLaboratorio() != null) {
            _hashCode += getAltroLaboratorio().hashCode();
        }
        if (getAltroTipoTrattamento() != null) {
            _hashCode += getAltroTipoTrattamento().hashCode();
        }
        if (getAnnoChiusura() != null) {
            _hashCode += getAnnoChiusura().hashCode();
        }
        if (getAnnoEsercizio() != null) {
            _hashCode += getAnnoEsercizio().hashCode();
        }
        if (getAnnoVolBollettato() != null) {
            _hashCode += getAnnoVolBollettato().hashCode();
        }
        if (getAnnoVolumeTrattato() != null) {
            _hashCode += getAnnoVolumeTrattato().hashCode();
        }
        if (getAsfalto() != null) {
            _hashCode += getAsfalto().hashCode();
        }
        if (getAutomazione() != null) {
            _hashCode += getAutomazione().hashCode();
        }
        if (getBod5() != null) {
            _hashCode += getBod5().hashCode();
        }
        if (getCancello() != null) {
            _hashCode += getCancello().hashCode();
        }
        if (getCapIdrProg() != null) {
            _hashCode += getCapIdrProg().hashCode();
        }
        if (getCapIdrServ() != null) {
            _hashCode += getCapIdrServ().hashCode();
        }
        if (getCapOrgProg() != null) {
            _hashCode += getCapOrgProg().hashCode();
        }
        if (getCapOrgServ() != null) {
            _hashCode += getCapOrgServ().hashCode();
        }
        if (getCapacitaEstProgetto() != null) {
            _hashCode += getCapacitaEstProgetto().hashCode();
        }
        if (getCapacitaEstPrra() != null) {
            _hashCode += getCapacitaEstPrra().hashCode();
        }
        if (getCapacitaInvProgetto() != null) {
            _hashCode += getCapacitaInvProgetto().hashCode();
        }
        if (getCapacitaInvPrra() != null) {
            _hashCode += getCapacitaInvPrra().hashCode();
        }
        if (getCaricoOrganicoMedio() != null) {
            _hashCode += getCaricoOrganicoMedio().hashCode();
        }
        if (getCemento() != null) {
            _hashCode += getCemento().hashCode();
        }
        if (getCodiceNazionale() != null) {
            _hashCode += getCodiceNazionale().hashCode();
        }
        if (getCodiceRegionale() != null) {
            _hashCode += getCodiceRegionale().hashCode();
        }
        if (getCompetenzaConsortile() != null) {
            _hashCode += getCompetenzaConsortile().hashCode();
        }
        if (getCompetenzaPrra() != null) {
            _hashCode += getCompetenzaPrra().hashCode();
        }
        if (getCostoGestione() != null) {
            _hashCode += getCostoGestione().hashCode();
        }
        if (getDepuratoreCompetenzaPrras() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDepuratoreCompetenzaPrras());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDepuratoreCompetenzaPrras(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDepuratoreCompetenzas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDepuratoreCompetenzas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDepuratoreCompetenzas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDepuratoreSezionis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDepuratoreSezionis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDepuratoreSezionis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDepuratoreTipoOperes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDepuratoreTipoOperes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDepuratoreTipoOperes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDestLineaFanghi() != null) {
            _hashCode += getDestLineaFanghi().hashCode();
        }
        if (getDistanzaAbitazioni() != null) {
            _hashCode += getDistanzaAbitazioni().hashCode();
        }
        if (getEquivalentiIndustriali() != null) {
            _hashCode += getEquivalentiIndustriali().hashCode();
        }
        if (getFluttuanti() != null) {
            _hashCode += getFluttuanti().hashCode();
        }
        if (getFognaturaSeparata() != null) {
            _hashCode += getFognaturaSeparata().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getIlluminazione() != null) {
            _hashCode += getIlluminazione().hashCode();
        }
        if (getInformatizzazioneDati() != null) {
            _hashCode += getInformatizzazioneDati().hashCode();
        }
        if (getLabAutoanalisiCent() != null) {
            _hashCode += getLabAutoanalisiCent().hashCode();
        }
        if (getLabAutoanalisiExt() != null) {
            _hashCode += getLabAutoanalisiExt().hashCode();
        }
        if (getLabAutoanalisiInt() != null) {
            _hashCode += getLabAutoanalisiInt().hashCode();
        }
        if (getLineaFanghi() != null) {
            _hashCode += getLineaFanghi().hashCode();
        }
        if (getLocalitaSmaltimento() != null) {
            _hashCode += getLocalitaSmaltimento().hashCode();
        }
        if (getMesePunta() != null) {
            _hashCode += getMesePunta().hashCode();
        }
        if (getMisuratorePortata() != null) {
            _hashCode += getMisuratorePortata().hashCode();
        }
        if (getMisuratoriOnline() != null) {
            _hashCode += getMisuratoriOnline().hashCode();
        }
        if (getMisuratoriTipo() != null) {
            _hashCode += getMisuratoriTipo().hashCode();
        }
        if (getNoteInterventi() != null) {
            _hashCode += getNoteInterventi().hashCode();
        }
        if (getNoteSmaltRiutilizzo() != null) {
            _hashCode += getNoteSmaltRiutilizzo().hashCode();
        }
        if (getNumInterventiOrd() != null) {
            _hashCode += getNumInterventiOrd().hashCode();
        }
        if (getNumInterventiStraord() != null) {
            _hashCode += getNumInterventiStraord().hashCode();
        }
        if (getNumLineeBiologiche() != null) {
            _hashCode += getNumLineeBiologiche().hashCode();
        }
        if (getNumLineeFanghi() != null) {
            _hashCode += getNumLineeFanghi().hashCode();
        }
        if (getParametriEst() != null) {
            _hashCode += getParametriEst().hashCode();
        }
        if (getParametriInv() != null) {
            _hashCode += getParametriInv().hashCode();
        }
        if (getPercSecco() != null) {
            _hashCode += getPercSecco().hashCode();
        }
        if (getPercUmidita() != null) {
            _hashCode += getPercUmidita().hashCode();
        }
        if (getPianiStralcio() != null) {
            _hashCode += getPianiStralcio().hashCode();
        }
        if (getPortataBypass() != null) {
            _hashCode += getPortataBypass().hashCode();
        }
        if (getPortataEstPar() != null) {
            _hashCode += getPortataEstPar().hashCode();
        }
        if (getPortataEstPot() != null) {
            _hashCode += getPortataEstPot().hashCode();
        }
        if (getPortataEstProgetto() != null) {
            _hashCode += getPortataEstProgetto().hashCode();
        }
        if (getPortataEstPrra() != null) {
            _hashCode += getPortataEstPrra().hashCode();
        }
        if (getPortataInvPar() != null) {
            _hashCode += getPortataInvPar().hashCode();
        }
        if (getPortataInvPot() != null) {
            _hashCode += getPortataInvPot().hashCode();
        }
        if (getPortataInvProgetto() != null) {
            _hashCode += getPortataInvProgetto().hashCode();
        }
        if (getPortataInvPrra() != null) {
            _hashCode += getPortataInvPrra().hashCode();
        }
        if (getPortataPuntaMista() != null) {
            _hashCode += getPortataPuntaMista().hashCode();
        }
        if (getPotenzialitaEst() != null) {
            _hashCode += getPotenzialitaEst().hashCode();
        }
        if (getPotenzialitaInv() != null) {
            _hashCode += getPotenzialitaInv().hashCode();
        }
        if (getPredNuoveUtenze() != null) {
            _hashCode += getPredNuoveUtenze().hashCode();
        }
        if (getQuantitaFanghi() != null) {
            _hashCode += getQuantitaFanghi().hashCode();
        }
        if (getQuantitaResidui() != null) {
            _hashCode += getQuantitaResidui().hashCode();
        }
        if (getRecinzione() != null) {
            _hashCode += getRecinzione().hashCode();
        }
        if (getRefluoAgricolo() != null) {
            _hashCode += getRefluoAgricolo().hashCode();
        }
        if (getRefluoAltro() != null) {
            _hashCode += getRefluoAltro().hashCode();
        }
        if (getRefluoDomestico() != null) {
            _hashCode += getRefluoDomestico().hashCode();
        }
        if (getRefluoIndustriale() != null) {
            _hashCode += getRefluoIndustriale().hashCode();
        }
        if (getRefluoZootecnico() != null) {
            _hashCode += getRefluoZootecnico().hashCode();
        }
        if (getResidenti() != null) {
            _hashCode += getResidenti().hashCode();
        }
        if (getRiutilizzoAltroTesto() != null) {
            _hashCode += getRiutilizzoAltroTesto().hashCode();
        }
        if (getRiutilizzoAltroValore() != null) {
            _hashCode += getRiutilizzoAltroValore().hashCode();
        }
        if (getRiutilizzoAmbientale() != null) {
            _hashCode += getRiutilizzoAmbientale().hashCode();
        }
        if (getRiutilizzoCivile() != null) {
            _hashCode += getRiutilizzoCivile().hashCode();
        }
        if (getRiutilizzoIndustriale() != null) {
            _hashCode += getRiutilizzoIndustriale().hashCode();
        }
        if (getRiutilizzoIrriguo() != null) {
            _hashCode += getRiutilizzoIrriguo().hashCode();
        }
        if (getSchemaPrra() != null) {
            _hashCode += getSchemaPrra().hashCode();
        }
        if (getSegnaletica() != null) {
            _hashCode += getSegnaletica().hashCode();
        }
        if (getSmaltFanghiAgricoltura() != null) {
            _hashCode += getSmaltFanghiAgricoltura().hashCode();
        }
        if (getSmaltFanghiCompostato() != null) {
            _hashCode += getSmaltFanghiCompostato().hashCode();
        }
        if (getSmaltFanghiDiscarica() != null) {
            _hashCode += getSmaltFanghiDiscarica().hashCode();
        }
        if (getSmaltFanghiInceneritore() != null) {
            _hashCode += getSmaltFanghiInceneritore().hashCode();
        }
        if (getStatoAttualeNu() != null) {
            _hashCode += getStatoAttualeNu().hashCode();
        }
        if (getStatoFunzionalita() != null) {
            _hashCode += getStatoFunzionalita().hashCode();
        }
        if (getTariffaDepuratore() != null) {
            _hashCode += getTariffaDepuratore().hashCode();
        }
        if (getTariffaFogne() != null) {
            _hashCode += getTariffaFogne().hashCode();
        }
        if (getTariffeAccantonate() != null) {
            _hashCode += getTariffeAccantonate().hashCode();
        }
        if (getTariffeRiscosse() != null) {
            _hashCode += getTariffeRiscosse().hashCode();
        }
        if (getTelecontrollo() != null) {
            _hashCode += getTelecontrollo().hashCode();
        }
        if (getTerraBattuta() != null) {
            _hashCode += getTerraBattuta().hashCode();
        }
        if (getTipoAltroRefluo() != null) {
            _hashCode += getTipoAltroRefluo().hashCode();
        }
        if (getTipoAutomazione() != null) {
            _hashCode += getTipoAutomazione().hashCode();
        }
        if (getTipoFonteFinNu() != null) {
            _hashCode += getTipoFonteFinNu().hashCode();
        }
        if (getTipoProcesso() != null) {
            _hashCode += getTipoProcesso().hashCode();
        }
        if (getTipoTrattamento() != null) {
            _hashCode += getTipoTrattamento().hashCode();
        }
        if (getTrattamento() != null) {
            _hashCode += getTrattamento().hashCode();
        }
        if (getUbicazioneMisuratore() != null) {
            _hashCode += getUbicazioneMisuratore().hashCode();
        }
        if (getUtilizzoFinaleFanghi() != null) {
            _hashCode += getUtilizzoFinaleFanghi().hashCode();
        }
        if (getVolumeBollettato() != null) {
            _hashCode += getVolumeBollettato().hashCode();
        }
        if (getVolumeTrattato() != null) {
            _hashCode += getVolumeTrattato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostImpdepurazione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpdepurazione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("abitantiServiti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "abitantiServiti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adeguamento152");
        elemField.setXmlName(new javax.xml.namespace.QName("", "adeguamento152"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alberi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "alberi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altroLaboratorio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "altroLaboratorio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altroTipoTrattamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "altroTipoTrattamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoChiusura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoChiusura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoEsercizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoEsercizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoVolBollettato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoVolBollettato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoVolumeTrattato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoVolumeTrattato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asfalto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asfalto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("automazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "automazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bod5");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bod5"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cancello");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cancello"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capIdrProg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capIdrProg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capIdrServ");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capIdrServ"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capOrgProg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capOrgProg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capOrgServ");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capOrgServ"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacitaEstProgetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capacitaEstProgetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacitaEstPrra");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capacitaEstPrra"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacitaInvProgetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capacitaInvProgetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacitaInvPrra");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capacitaInvPrra"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caricoOrganicoMedio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caricoOrganicoMedio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cemento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cemento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceNazionale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceNazionale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceRegionale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceRegionale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("competenzaConsortile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "competenzaConsortile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("competenzaPrra");
        elemField.setXmlName(new javax.xml.namespace.QName("", "competenzaPrra"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("costoGestione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "costoGestione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depuratoreCompetenzaPrras");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depuratoreCompetenzaPrras"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "depuratoreCompetenzaPrra"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depuratoreCompetenzas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depuratoreCompetenzas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "depuratoreCompetenza"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depuratoreSezionis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depuratoreSezionis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "depuratoreSezioni"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depuratoreTipoOperes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depuratoreTipoOperes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "depuratoreTipoOpere"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destLineaFanghi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destLineaFanghi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("distanzaAbitazioni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "distanzaAbitazioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("equivalentiIndustriali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "equivalentiIndustriali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fluttuanti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fluttuanti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fognaturaSeparata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fognaturaSeparata"));
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
        elemField.setFieldName("illuminazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "illuminazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("informatizzazioneDati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "informatizzazioneDati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("labAutoanalisiCent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "labAutoanalisiCent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("labAutoanalisiExt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "labAutoanalisiExt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("labAutoanalisiInt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "labAutoanalisiInt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lineaFanghi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lineaFanghi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localitaSmaltimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "localitaSmaltimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mesePunta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mesePunta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("misuratorePortata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "misuratorePortata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("misuratoriOnline");
        elemField.setXmlName(new javax.xml.namespace.QName("", "misuratoriOnline"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("misuratoriTipo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "misuratoriTipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noteInterventi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "noteInterventi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noteSmaltRiutilizzo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "noteSmaltRiutilizzo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numInterventiOrd");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numInterventiOrd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numInterventiStraord");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numInterventiStraord"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numLineeBiologiche");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numLineeBiologiche"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numLineeFanghi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numLineeFanghi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parametriEst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parametriEst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parametriInv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parametriInv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percSecco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "percSecco"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percUmidita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "percUmidita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pianiStralcio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pianiStralcio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pianiStralcio"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataBypass");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataBypass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataEstPar");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataEstPar"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataEstPot");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataEstPot"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataEstProgetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataEstProgetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataEstPrra");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataEstPrra"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataInvPar");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataInvPar"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataInvPot");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataInvPot"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataInvProgetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataInvProgetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataInvPrra");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataInvPrra"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataPuntaMista");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataPuntaMista"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzialitaEst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzialitaEst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzialitaInv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzialitaInv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("predNuoveUtenze");
        elemField.setXmlName(new javax.xml.namespace.QName("", "predNuoveUtenze"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantitaFanghi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quantitaFanghi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantitaResidui");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quantitaResidui"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recinzione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "recinzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refluoAgricolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "refluoAgricolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refluoAltro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "refluoAltro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refluoDomestico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "refluoDomestico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refluoIndustriale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "refluoIndustriale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refluoZootecnico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "refluoZootecnico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("residenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "residenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riutilizzoAltroTesto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riutilizzoAltroTesto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riutilizzoAltroValore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riutilizzoAltroValore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riutilizzoAmbientale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riutilizzoAmbientale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riutilizzoCivile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riutilizzoCivile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riutilizzoIndustriale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riutilizzoIndustriale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riutilizzoIrriguo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riutilizzoIrriguo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schemaPrra");
        elemField.setXmlName(new javax.xml.namespace.QName("", "schemaPrra"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segnaletica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "segnaletica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("smaltFanghiAgricoltura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "smaltFanghiAgricoltura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("smaltFanghiCompostato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "smaltFanghiCompostato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("smaltFanghiDiscarica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "smaltFanghiDiscarica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("smaltFanghiInceneritore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "smaltFanghiInceneritore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoAttualeNu");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statoAttualeNu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoFunzionalita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statoFunzionalita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tariffaDepuratore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tariffaDepuratore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tariffaFogne");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tariffaFogne"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tariffeAccantonate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tariffeAccantonate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tariffeRiscosse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tariffeRiscosse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("telecontrollo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "telecontrollo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terraBattuta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "terraBattuta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoAltroRefluo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoAltroRefluo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoAutomazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoAutomazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoFonteFinNu");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoFonteFinNu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoProcesso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoProcesso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "impDepTipiProcesso"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoTrattamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoTrattamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "impDepTipiTrattamento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trattamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "trattamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "impDepTrattamenti"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ubicazioneMisuratore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ubicazioneMisuratore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utilizzoFinaleFanghi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilizzoFinaleFanghi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeBollettato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeBollettato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeTrattato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeTrattato"));
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
