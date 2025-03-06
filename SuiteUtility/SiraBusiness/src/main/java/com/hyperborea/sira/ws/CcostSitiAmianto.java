/**
 * CcostSitiAmianto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostSitiAmianto  implements java.io.Serializable {
    private java.lang.Integer annoAttivazione;

    private com.hyperborea.sira.ws.BonificheAmianto[] bonificheAmiantos;

    private java.lang.Float coeffClasse;

    private java.lang.Integer concFibreAerodisperse;

    private java.lang.Float fattoreA;

    private java.lang.Float fattoreB;

    private java.lang.Float fattoreC;

    private java.lang.Float fattoreD;

    private java.lang.Integer flagCauseDispersione;

    private java.lang.Integer flagConfinamento;

    private java.lang.Integer flagDatiEpidemiologici;

    private java.lang.Integer flagDatiEpidemiologiciNat;

    private java.lang.Integer flagMaterialeCompatto;

    private java.lang.Integer flagMaterialeFriabile;

    private java.lang.Integer flagPresenzaAmiantoAltro;

    private java.lang.Integer flagPresenzaAmiantoPavim;

    private java.lang.Integer flagPresenzaAmiantoTetto;

    private java.lang.Integer flagUrbanizzazione;

    private java.lang.Integer flagUrbanizzazioneNat;

    private java.lang.Integer flagValutazione;

    private java.lang.Float i01;

    private java.lang.Float i02;

    private java.lang.Float i03;

    private java.lang.Float i04;

    private java.lang.Float i05;

    private java.lang.Float i06;

    private java.lang.Float i07;

    private java.lang.Float i08;

    private java.lang.Float i09;

    private java.lang.Float i10;

    private java.lang.Float i11;

    private java.lang.Float i12;

    private java.lang.Float i13;

    private java.lang.Float i14;

    private java.lang.Float i15;

    private java.lang.Float i16;

    private java.lang.Float i17;

    private java.lang.Integer idCcost;

    private java.lang.Float in1;

    private java.lang.Float in2;

    private java.lang.Float in3;

    private java.lang.Float in4;

    private java.lang.Float in5;

    private java.lang.Float in6;

    private java.lang.Integer livPriorita;

    private java.lang.Float punteggio;

    private com.hyperborea.sira.ws.VocAmAccessibilita vocAmAccessibilita;

    private com.hyperborea.sira.ws.VocAmAttivitaFunzione vocAmAttivitaFunzione;

    private com.hyperborea.sira.ws.VocAmDensitaPopolazione vocAmDensitaPopolazione;

    private com.hyperborea.sira.ws.VocAmDistanzaAbitato vocAmDistanzaAbitato;

    private com.hyperborea.sira.ws.VocAmEstensione vocAmEstensione;

    private com.hyperborea.sira.ws.VocAmEstensioneAffioramento vocAmEstensioneAffioramento;

    private com.hyperborea.sira.ws.VocAmEtaMedia vocAmEtaMedia;

    private com.hyperborea.sira.ws.VocAmFibreAerodisperse vocAmFibreAerodisperse;

    private com.hyperborea.sira.ws.VocAmFreqUtilizzo vocAmFreqUtilizzo;

    private com.hyperborea.sira.ws.VocAmMaterialeAffioramento vocAmMaterialeAffioramento;

    private com.hyperborea.sira.ws.VocAmPeriodoDismissione vocAmPeriodoDismissione;

    private com.hyperborea.sira.ws.VocAmPresenzaAffioramento vocAmPresenzaAffioramento;

    private com.hyperborea.sira.ws.VocAmQuantitaCompatto vocAmQuantitaCompatto;

    private com.hyperborea.sira.ws.VocAmQuantitaFriabile vocAmQuantitaFriabile;

    private com.hyperborea.sira.ws.VocAmStatoConservazione vocAmStatoConservazione;

    private com.hyperborea.sira.ws.VocAmSuperficieEsposta vocAmSuperficieEsposta;

    private com.hyperborea.sira.ws.VocAmTipologia vocAmTipologia;

    private com.hyperborea.sira.ws.VocAmTipologiaAmianto vocAmTipologiaAmianto;

    private com.hyperborea.sira.ws.VocAmUso vocAmUso;

    public CcostSitiAmianto() {
    }

    public CcostSitiAmianto(
           java.lang.Integer annoAttivazione,
           com.hyperborea.sira.ws.BonificheAmianto[] bonificheAmiantos,
           java.lang.Float coeffClasse,
           java.lang.Integer concFibreAerodisperse,
           java.lang.Float fattoreA,
           java.lang.Float fattoreB,
           java.lang.Float fattoreC,
           java.lang.Float fattoreD,
           java.lang.Integer flagCauseDispersione,
           java.lang.Integer flagConfinamento,
           java.lang.Integer flagDatiEpidemiologici,
           java.lang.Integer flagDatiEpidemiologiciNat,
           java.lang.Integer flagMaterialeCompatto,
           java.lang.Integer flagMaterialeFriabile,
           java.lang.Integer flagPresenzaAmiantoAltro,
           java.lang.Integer flagPresenzaAmiantoPavim,
           java.lang.Integer flagPresenzaAmiantoTetto,
           java.lang.Integer flagUrbanizzazione,
           java.lang.Integer flagUrbanizzazioneNat,
           java.lang.Integer flagValutazione,
           java.lang.Float i01,
           java.lang.Float i02,
           java.lang.Float i03,
           java.lang.Float i04,
           java.lang.Float i05,
           java.lang.Float i06,
           java.lang.Float i07,
           java.lang.Float i08,
           java.lang.Float i09,
           java.lang.Float i10,
           java.lang.Float i11,
           java.lang.Float i12,
           java.lang.Float i13,
           java.lang.Float i14,
           java.lang.Float i15,
           java.lang.Float i16,
           java.lang.Float i17,
           java.lang.Integer idCcost,
           java.lang.Float in1,
           java.lang.Float in2,
           java.lang.Float in3,
           java.lang.Float in4,
           java.lang.Float in5,
           java.lang.Float in6,
           java.lang.Integer livPriorita,
           java.lang.Float punteggio,
           com.hyperborea.sira.ws.VocAmAccessibilita vocAmAccessibilita,
           com.hyperborea.sira.ws.VocAmAttivitaFunzione vocAmAttivitaFunzione,
           com.hyperborea.sira.ws.VocAmDensitaPopolazione vocAmDensitaPopolazione,
           com.hyperborea.sira.ws.VocAmDistanzaAbitato vocAmDistanzaAbitato,
           com.hyperborea.sira.ws.VocAmEstensione vocAmEstensione,
           com.hyperborea.sira.ws.VocAmEstensioneAffioramento vocAmEstensioneAffioramento,
           com.hyperborea.sira.ws.VocAmEtaMedia vocAmEtaMedia,
           com.hyperborea.sira.ws.VocAmFibreAerodisperse vocAmFibreAerodisperse,
           com.hyperborea.sira.ws.VocAmFreqUtilizzo vocAmFreqUtilizzo,
           com.hyperborea.sira.ws.VocAmMaterialeAffioramento vocAmMaterialeAffioramento,
           com.hyperborea.sira.ws.VocAmPeriodoDismissione vocAmPeriodoDismissione,
           com.hyperborea.sira.ws.VocAmPresenzaAffioramento vocAmPresenzaAffioramento,
           com.hyperborea.sira.ws.VocAmQuantitaCompatto vocAmQuantitaCompatto,
           com.hyperborea.sira.ws.VocAmQuantitaFriabile vocAmQuantitaFriabile,
           com.hyperborea.sira.ws.VocAmStatoConservazione vocAmStatoConservazione,
           com.hyperborea.sira.ws.VocAmSuperficieEsposta vocAmSuperficieEsposta,
           com.hyperborea.sira.ws.VocAmTipologia vocAmTipologia,
           com.hyperborea.sira.ws.VocAmTipologiaAmianto vocAmTipologiaAmianto,
           com.hyperborea.sira.ws.VocAmUso vocAmUso) {
           this.annoAttivazione = annoAttivazione;
           this.bonificheAmiantos = bonificheAmiantos;
           this.coeffClasse = coeffClasse;
           this.concFibreAerodisperse = concFibreAerodisperse;
           this.fattoreA = fattoreA;
           this.fattoreB = fattoreB;
           this.fattoreC = fattoreC;
           this.fattoreD = fattoreD;
           this.flagCauseDispersione = flagCauseDispersione;
           this.flagConfinamento = flagConfinamento;
           this.flagDatiEpidemiologici = flagDatiEpidemiologici;
           this.flagDatiEpidemiologiciNat = flagDatiEpidemiologiciNat;
           this.flagMaterialeCompatto = flagMaterialeCompatto;
           this.flagMaterialeFriabile = flagMaterialeFriabile;
           this.flagPresenzaAmiantoAltro = flagPresenzaAmiantoAltro;
           this.flagPresenzaAmiantoPavim = flagPresenzaAmiantoPavim;
           this.flagPresenzaAmiantoTetto = flagPresenzaAmiantoTetto;
           this.flagUrbanizzazione = flagUrbanizzazione;
           this.flagUrbanizzazioneNat = flagUrbanizzazioneNat;
           this.flagValutazione = flagValutazione;
           this.i01 = i01;
           this.i02 = i02;
           this.i03 = i03;
           this.i04 = i04;
           this.i05 = i05;
           this.i06 = i06;
           this.i07 = i07;
           this.i08 = i08;
           this.i09 = i09;
           this.i10 = i10;
           this.i11 = i11;
           this.i12 = i12;
           this.i13 = i13;
           this.i14 = i14;
           this.i15 = i15;
           this.i16 = i16;
           this.i17 = i17;
           this.idCcost = idCcost;
           this.in1 = in1;
           this.in2 = in2;
           this.in3 = in3;
           this.in4 = in4;
           this.in5 = in5;
           this.in6 = in6;
           this.livPriorita = livPriorita;
           this.punteggio = punteggio;
           this.vocAmAccessibilita = vocAmAccessibilita;
           this.vocAmAttivitaFunzione = vocAmAttivitaFunzione;
           this.vocAmDensitaPopolazione = vocAmDensitaPopolazione;
           this.vocAmDistanzaAbitato = vocAmDistanzaAbitato;
           this.vocAmEstensione = vocAmEstensione;
           this.vocAmEstensioneAffioramento = vocAmEstensioneAffioramento;
           this.vocAmEtaMedia = vocAmEtaMedia;
           this.vocAmFibreAerodisperse = vocAmFibreAerodisperse;
           this.vocAmFreqUtilizzo = vocAmFreqUtilizzo;
           this.vocAmMaterialeAffioramento = vocAmMaterialeAffioramento;
           this.vocAmPeriodoDismissione = vocAmPeriodoDismissione;
           this.vocAmPresenzaAffioramento = vocAmPresenzaAffioramento;
           this.vocAmQuantitaCompatto = vocAmQuantitaCompatto;
           this.vocAmQuantitaFriabile = vocAmQuantitaFriabile;
           this.vocAmStatoConservazione = vocAmStatoConservazione;
           this.vocAmSuperficieEsposta = vocAmSuperficieEsposta;
           this.vocAmTipologia = vocAmTipologia;
           this.vocAmTipologiaAmianto = vocAmTipologiaAmianto;
           this.vocAmUso = vocAmUso;
    }


    /**
     * Gets the annoAttivazione value for this CcostSitiAmianto.
     * 
     * @return annoAttivazione
     */
    public java.lang.Integer getAnnoAttivazione() {
        return annoAttivazione;
    }


    /**
     * Sets the annoAttivazione value for this CcostSitiAmianto.
     * 
     * @param annoAttivazione
     */
    public void setAnnoAttivazione(java.lang.Integer annoAttivazione) {
        this.annoAttivazione = annoAttivazione;
    }


    /**
     * Gets the bonificheAmiantos value for this CcostSitiAmianto.
     * 
     * @return bonificheAmiantos
     */
    public com.hyperborea.sira.ws.BonificheAmianto[] getBonificheAmiantos() {
        return bonificheAmiantos;
    }


    /**
     * Sets the bonificheAmiantos value for this CcostSitiAmianto.
     * 
     * @param bonificheAmiantos
     */
    public void setBonificheAmiantos(com.hyperborea.sira.ws.BonificheAmianto[] bonificheAmiantos) {
        this.bonificheAmiantos = bonificheAmiantos;
    }

    public com.hyperborea.sira.ws.BonificheAmianto getBonificheAmiantos(int i) {
        return this.bonificheAmiantos[i];
    }

    public void setBonificheAmiantos(int i, com.hyperborea.sira.ws.BonificheAmianto _value) {
        this.bonificheAmiantos[i] = _value;
    }


    /**
     * Gets the coeffClasse value for this CcostSitiAmianto.
     * 
     * @return coeffClasse
     */
    public java.lang.Float getCoeffClasse() {
        return coeffClasse;
    }


    /**
     * Sets the coeffClasse value for this CcostSitiAmianto.
     * 
     * @param coeffClasse
     */
    public void setCoeffClasse(java.lang.Float coeffClasse) {
        this.coeffClasse = coeffClasse;
    }


    /**
     * Gets the concFibreAerodisperse value for this CcostSitiAmianto.
     * 
     * @return concFibreAerodisperse
     */
    public java.lang.Integer getConcFibreAerodisperse() {
        return concFibreAerodisperse;
    }


    /**
     * Sets the concFibreAerodisperse value for this CcostSitiAmianto.
     * 
     * @param concFibreAerodisperse
     */
    public void setConcFibreAerodisperse(java.lang.Integer concFibreAerodisperse) {
        this.concFibreAerodisperse = concFibreAerodisperse;
    }


    /**
     * Gets the fattoreA value for this CcostSitiAmianto.
     * 
     * @return fattoreA
     */
    public java.lang.Float getFattoreA() {
        return fattoreA;
    }


    /**
     * Sets the fattoreA value for this CcostSitiAmianto.
     * 
     * @param fattoreA
     */
    public void setFattoreA(java.lang.Float fattoreA) {
        this.fattoreA = fattoreA;
    }


    /**
     * Gets the fattoreB value for this CcostSitiAmianto.
     * 
     * @return fattoreB
     */
    public java.lang.Float getFattoreB() {
        return fattoreB;
    }


    /**
     * Sets the fattoreB value for this CcostSitiAmianto.
     * 
     * @param fattoreB
     */
    public void setFattoreB(java.lang.Float fattoreB) {
        this.fattoreB = fattoreB;
    }


    /**
     * Gets the fattoreC value for this CcostSitiAmianto.
     * 
     * @return fattoreC
     */
    public java.lang.Float getFattoreC() {
        return fattoreC;
    }


    /**
     * Sets the fattoreC value for this CcostSitiAmianto.
     * 
     * @param fattoreC
     */
    public void setFattoreC(java.lang.Float fattoreC) {
        this.fattoreC = fattoreC;
    }


    /**
     * Gets the fattoreD value for this CcostSitiAmianto.
     * 
     * @return fattoreD
     */
    public java.lang.Float getFattoreD() {
        return fattoreD;
    }


    /**
     * Sets the fattoreD value for this CcostSitiAmianto.
     * 
     * @param fattoreD
     */
    public void setFattoreD(java.lang.Float fattoreD) {
        this.fattoreD = fattoreD;
    }


    /**
     * Gets the flagCauseDispersione value for this CcostSitiAmianto.
     * 
     * @return flagCauseDispersione
     */
    public java.lang.Integer getFlagCauseDispersione() {
        return flagCauseDispersione;
    }


    /**
     * Sets the flagCauseDispersione value for this CcostSitiAmianto.
     * 
     * @param flagCauseDispersione
     */
    public void setFlagCauseDispersione(java.lang.Integer flagCauseDispersione) {
        this.flagCauseDispersione = flagCauseDispersione;
    }


    /**
     * Gets the flagConfinamento value for this CcostSitiAmianto.
     * 
     * @return flagConfinamento
     */
    public java.lang.Integer getFlagConfinamento() {
        return flagConfinamento;
    }


    /**
     * Sets the flagConfinamento value for this CcostSitiAmianto.
     * 
     * @param flagConfinamento
     */
    public void setFlagConfinamento(java.lang.Integer flagConfinamento) {
        this.flagConfinamento = flagConfinamento;
    }


    /**
     * Gets the flagDatiEpidemiologici value for this CcostSitiAmianto.
     * 
     * @return flagDatiEpidemiologici
     */
    public java.lang.Integer getFlagDatiEpidemiologici() {
        return flagDatiEpidemiologici;
    }


    /**
     * Sets the flagDatiEpidemiologici value for this CcostSitiAmianto.
     * 
     * @param flagDatiEpidemiologici
     */
    public void setFlagDatiEpidemiologici(java.lang.Integer flagDatiEpidemiologici) {
        this.flagDatiEpidemiologici = flagDatiEpidemiologici;
    }


    /**
     * Gets the flagDatiEpidemiologiciNat value for this CcostSitiAmianto.
     * 
     * @return flagDatiEpidemiologiciNat
     */
    public java.lang.Integer getFlagDatiEpidemiologiciNat() {
        return flagDatiEpidemiologiciNat;
    }


    /**
     * Sets the flagDatiEpidemiologiciNat value for this CcostSitiAmianto.
     * 
     * @param flagDatiEpidemiologiciNat
     */
    public void setFlagDatiEpidemiologiciNat(java.lang.Integer flagDatiEpidemiologiciNat) {
        this.flagDatiEpidemiologiciNat = flagDatiEpidemiologiciNat;
    }


    /**
     * Gets the flagMaterialeCompatto value for this CcostSitiAmianto.
     * 
     * @return flagMaterialeCompatto
     */
    public java.lang.Integer getFlagMaterialeCompatto() {
        return flagMaterialeCompatto;
    }


    /**
     * Sets the flagMaterialeCompatto value for this CcostSitiAmianto.
     * 
     * @param flagMaterialeCompatto
     */
    public void setFlagMaterialeCompatto(java.lang.Integer flagMaterialeCompatto) {
        this.flagMaterialeCompatto = flagMaterialeCompatto;
    }


    /**
     * Gets the flagMaterialeFriabile value for this CcostSitiAmianto.
     * 
     * @return flagMaterialeFriabile
     */
    public java.lang.Integer getFlagMaterialeFriabile() {
        return flagMaterialeFriabile;
    }


    /**
     * Sets the flagMaterialeFriabile value for this CcostSitiAmianto.
     * 
     * @param flagMaterialeFriabile
     */
    public void setFlagMaterialeFriabile(java.lang.Integer flagMaterialeFriabile) {
        this.flagMaterialeFriabile = flagMaterialeFriabile;
    }


    /**
     * Gets the flagPresenzaAmiantoAltro value for this CcostSitiAmianto.
     * 
     * @return flagPresenzaAmiantoAltro
     */
    public java.lang.Integer getFlagPresenzaAmiantoAltro() {
        return flagPresenzaAmiantoAltro;
    }


    /**
     * Sets the flagPresenzaAmiantoAltro value for this CcostSitiAmianto.
     * 
     * @param flagPresenzaAmiantoAltro
     */
    public void setFlagPresenzaAmiantoAltro(java.lang.Integer flagPresenzaAmiantoAltro) {
        this.flagPresenzaAmiantoAltro = flagPresenzaAmiantoAltro;
    }


    /**
     * Gets the flagPresenzaAmiantoPavim value for this CcostSitiAmianto.
     * 
     * @return flagPresenzaAmiantoPavim
     */
    public java.lang.Integer getFlagPresenzaAmiantoPavim() {
        return flagPresenzaAmiantoPavim;
    }


    /**
     * Sets the flagPresenzaAmiantoPavim value for this CcostSitiAmianto.
     * 
     * @param flagPresenzaAmiantoPavim
     */
    public void setFlagPresenzaAmiantoPavim(java.lang.Integer flagPresenzaAmiantoPavim) {
        this.flagPresenzaAmiantoPavim = flagPresenzaAmiantoPavim;
    }


    /**
     * Gets the flagPresenzaAmiantoTetto value for this CcostSitiAmianto.
     * 
     * @return flagPresenzaAmiantoTetto
     */
    public java.lang.Integer getFlagPresenzaAmiantoTetto() {
        return flagPresenzaAmiantoTetto;
    }


    /**
     * Sets the flagPresenzaAmiantoTetto value for this CcostSitiAmianto.
     * 
     * @param flagPresenzaAmiantoTetto
     */
    public void setFlagPresenzaAmiantoTetto(java.lang.Integer flagPresenzaAmiantoTetto) {
        this.flagPresenzaAmiantoTetto = flagPresenzaAmiantoTetto;
    }


    /**
     * Gets the flagUrbanizzazione value for this CcostSitiAmianto.
     * 
     * @return flagUrbanizzazione
     */
    public java.lang.Integer getFlagUrbanizzazione() {
        return flagUrbanizzazione;
    }


    /**
     * Sets the flagUrbanizzazione value for this CcostSitiAmianto.
     * 
     * @param flagUrbanizzazione
     */
    public void setFlagUrbanizzazione(java.lang.Integer flagUrbanizzazione) {
        this.flagUrbanizzazione = flagUrbanizzazione;
    }


    /**
     * Gets the flagUrbanizzazioneNat value for this CcostSitiAmianto.
     * 
     * @return flagUrbanizzazioneNat
     */
    public java.lang.Integer getFlagUrbanizzazioneNat() {
        return flagUrbanizzazioneNat;
    }


    /**
     * Sets the flagUrbanizzazioneNat value for this CcostSitiAmianto.
     * 
     * @param flagUrbanizzazioneNat
     */
    public void setFlagUrbanizzazioneNat(java.lang.Integer flagUrbanizzazioneNat) {
        this.flagUrbanizzazioneNat = flagUrbanizzazioneNat;
    }


    /**
     * Gets the flagValutazione value for this CcostSitiAmianto.
     * 
     * @return flagValutazione
     */
    public java.lang.Integer getFlagValutazione() {
        return flagValutazione;
    }


    /**
     * Sets the flagValutazione value for this CcostSitiAmianto.
     * 
     * @param flagValutazione
     */
    public void setFlagValutazione(java.lang.Integer flagValutazione) {
        this.flagValutazione = flagValutazione;
    }


    /**
     * Gets the i01 value for this CcostSitiAmianto.
     * 
     * @return i01
     */
    public java.lang.Float getI01() {
        return i01;
    }


    /**
     * Sets the i01 value for this CcostSitiAmianto.
     * 
     * @param i01
     */
    public void setI01(java.lang.Float i01) {
        this.i01 = i01;
    }


    /**
     * Gets the i02 value for this CcostSitiAmianto.
     * 
     * @return i02
     */
    public java.lang.Float getI02() {
        return i02;
    }


    /**
     * Sets the i02 value for this CcostSitiAmianto.
     * 
     * @param i02
     */
    public void setI02(java.lang.Float i02) {
        this.i02 = i02;
    }


    /**
     * Gets the i03 value for this CcostSitiAmianto.
     * 
     * @return i03
     */
    public java.lang.Float getI03() {
        return i03;
    }


    /**
     * Sets the i03 value for this CcostSitiAmianto.
     * 
     * @param i03
     */
    public void setI03(java.lang.Float i03) {
        this.i03 = i03;
    }


    /**
     * Gets the i04 value for this CcostSitiAmianto.
     * 
     * @return i04
     */
    public java.lang.Float getI04() {
        return i04;
    }


    /**
     * Sets the i04 value for this CcostSitiAmianto.
     * 
     * @param i04
     */
    public void setI04(java.lang.Float i04) {
        this.i04 = i04;
    }


    /**
     * Gets the i05 value for this CcostSitiAmianto.
     * 
     * @return i05
     */
    public java.lang.Float getI05() {
        return i05;
    }


    /**
     * Sets the i05 value for this CcostSitiAmianto.
     * 
     * @param i05
     */
    public void setI05(java.lang.Float i05) {
        this.i05 = i05;
    }


    /**
     * Gets the i06 value for this CcostSitiAmianto.
     * 
     * @return i06
     */
    public java.lang.Float getI06() {
        return i06;
    }


    /**
     * Sets the i06 value for this CcostSitiAmianto.
     * 
     * @param i06
     */
    public void setI06(java.lang.Float i06) {
        this.i06 = i06;
    }


    /**
     * Gets the i07 value for this CcostSitiAmianto.
     * 
     * @return i07
     */
    public java.lang.Float getI07() {
        return i07;
    }


    /**
     * Sets the i07 value for this CcostSitiAmianto.
     * 
     * @param i07
     */
    public void setI07(java.lang.Float i07) {
        this.i07 = i07;
    }


    /**
     * Gets the i08 value for this CcostSitiAmianto.
     * 
     * @return i08
     */
    public java.lang.Float getI08() {
        return i08;
    }


    /**
     * Sets the i08 value for this CcostSitiAmianto.
     * 
     * @param i08
     */
    public void setI08(java.lang.Float i08) {
        this.i08 = i08;
    }


    /**
     * Gets the i09 value for this CcostSitiAmianto.
     * 
     * @return i09
     */
    public java.lang.Float getI09() {
        return i09;
    }


    /**
     * Sets the i09 value for this CcostSitiAmianto.
     * 
     * @param i09
     */
    public void setI09(java.lang.Float i09) {
        this.i09 = i09;
    }


    /**
     * Gets the i10 value for this CcostSitiAmianto.
     * 
     * @return i10
     */
    public java.lang.Float getI10() {
        return i10;
    }


    /**
     * Sets the i10 value for this CcostSitiAmianto.
     * 
     * @param i10
     */
    public void setI10(java.lang.Float i10) {
        this.i10 = i10;
    }


    /**
     * Gets the i11 value for this CcostSitiAmianto.
     * 
     * @return i11
     */
    public java.lang.Float getI11() {
        return i11;
    }


    /**
     * Sets the i11 value for this CcostSitiAmianto.
     * 
     * @param i11
     */
    public void setI11(java.lang.Float i11) {
        this.i11 = i11;
    }


    /**
     * Gets the i12 value for this CcostSitiAmianto.
     * 
     * @return i12
     */
    public java.lang.Float getI12() {
        return i12;
    }


    /**
     * Sets the i12 value for this CcostSitiAmianto.
     * 
     * @param i12
     */
    public void setI12(java.lang.Float i12) {
        this.i12 = i12;
    }


    /**
     * Gets the i13 value for this CcostSitiAmianto.
     * 
     * @return i13
     */
    public java.lang.Float getI13() {
        return i13;
    }


    /**
     * Sets the i13 value for this CcostSitiAmianto.
     * 
     * @param i13
     */
    public void setI13(java.lang.Float i13) {
        this.i13 = i13;
    }


    /**
     * Gets the i14 value for this CcostSitiAmianto.
     * 
     * @return i14
     */
    public java.lang.Float getI14() {
        return i14;
    }


    /**
     * Sets the i14 value for this CcostSitiAmianto.
     * 
     * @param i14
     */
    public void setI14(java.lang.Float i14) {
        this.i14 = i14;
    }


    /**
     * Gets the i15 value for this CcostSitiAmianto.
     * 
     * @return i15
     */
    public java.lang.Float getI15() {
        return i15;
    }


    /**
     * Sets the i15 value for this CcostSitiAmianto.
     * 
     * @param i15
     */
    public void setI15(java.lang.Float i15) {
        this.i15 = i15;
    }


    /**
     * Gets the i16 value for this CcostSitiAmianto.
     * 
     * @return i16
     */
    public java.lang.Float getI16() {
        return i16;
    }


    /**
     * Sets the i16 value for this CcostSitiAmianto.
     * 
     * @param i16
     */
    public void setI16(java.lang.Float i16) {
        this.i16 = i16;
    }


    /**
     * Gets the i17 value for this CcostSitiAmianto.
     * 
     * @return i17
     */
    public java.lang.Float getI17() {
        return i17;
    }


    /**
     * Sets the i17 value for this CcostSitiAmianto.
     * 
     * @param i17
     */
    public void setI17(java.lang.Float i17) {
        this.i17 = i17;
    }


    /**
     * Gets the idCcost value for this CcostSitiAmianto.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostSitiAmianto.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the in1 value for this CcostSitiAmianto.
     * 
     * @return in1
     */
    public java.lang.Float getIn1() {
        return in1;
    }


    /**
     * Sets the in1 value for this CcostSitiAmianto.
     * 
     * @param in1
     */
    public void setIn1(java.lang.Float in1) {
        this.in1 = in1;
    }


    /**
     * Gets the in2 value for this CcostSitiAmianto.
     * 
     * @return in2
     */
    public java.lang.Float getIn2() {
        return in2;
    }


    /**
     * Sets the in2 value for this CcostSitiAmianto.
     * 
     * @param in2
     */
    public void setIn2(java.lang.Float in2) {
        this.in2 = in2;
    }


    /**
     * Gets the in3 value for this CcostSitiAmianto.
     * 
     * @return in3
     */
    public java.lang.Float getIn3() {
        return in3;
    }


    /**
     * Sets the in3 value for this CcostSitiAmianto.
     * 
     * @param in3
     */
    public void setIn3(java.lang.Float in3) {
        this.in3 = in3;
    }


    /**
     * Gets the in4 value for this CcostSitiAmianto.
     * 
     * @return in4
     */
    public java.lang.Float getIn4() {
        return in4;
    }


    /**
     * Sets the in4 value for this CcostSitiAmianto.
     * 
     * @param in4
     */
    public void setIn4(java.lang.Float in4) {
        this.in4 = in4;
    }


    /**
     * Gets the in5 value for this CcostSitiAmianto.
     * 
     * @return in5
     */
    public java.lang.Float getIn5() {
        return in5;
    }


    /**
     * Sets the in5 value for this CcostSitiAmianto.
     * 
     * @param in5
     */
    public void setIn5(java.lang.Float in5) {
        this.in5 = in5;
    }


    /**
     * Gets the in6 value for this CcostSitiAmianto.
     * 
     * @return in6
     */
    public java.lang.Float getIn6() {
        return in6;
    }


    /**
     * Sets the in6 value for this CcostSitiAmianto.
     * 
     * @param in6
     */
    public void setIn6(java.lang.Float in6) {
        this.in6 = in6;
    }


    /**
     * Gets the livPriorita value for this CcostSitiAmianto.
     * 
     * @return livPriorita
     */
    public java.lang.Integer getLivPriorita() {
        return livPriorita;
    }


    /**
     * Sets the livPriorita value for this CcostSitiAmianto.
     * 
     * @param livPriorita
     */
    public void setLivPriorita(java.lang.Integer livPriorita) {
        this.livPriorita = livPriorita;
    }


    /**
     * Gets the punteggio value for this CcostSitiAmianto.
     * 
     * @return punteggio
     */
    public java.lang.Float getPunteggio() {
        return punteggio;
    }


    /**
     * Sets the punteggio value for this CcostSitiAmianto.
     * 
     * @param punteggio
     */
    public void setPunteggio(java.lang.Float punteggio) {
        this.punteggio = punteggio;
    }


    /**
     * Gets the vocAmAccessibilita value for this CcostSitiAmianto.
     * 
     * @return vocAmAccessibilita
     */
    public com.hyperborea.sira.ws.VocAmAccessibilita getVocAmAccessibilita() {
        return vocAmAccessibilita;
    }


    /**
     * Sets the vocAmAccessibilita value for this CcostSitiAmianto.
     * 
     * @param vocAmAccessibilita
     */
    public void setVocAmAccessibilita(com.hyperborea.sira.ws.VocAmAccessibilita vocAmAccessibilita) {
        this.vocAmAccessibilita = vocAmAccessibilita;
    }


    /**
     * Gets the vocAmAttivitaFunzione value for this CcostSitiAmianto.
     * 
     * @return vocAmAttivitaFunzione
     */
    public com.hyperborea.sira.ws.VocAmAttivitaFunzione getVocAmAttivitaFunzione() {
        return vocAmAttivitaFunzione;
    }


    /**
     * Sets the vocAmAttivitaFunzione value for this CcostSitiAmianto.
     * 
     * @param vocAmAttivitaFunzione
     */
    public void setVocAmAttivitaFunzione(com.hyperborea.sira.ws.VocAmAttivitaFunzione vocAmAttivitaFunzione) {
        this.vocAmAttivitaFunzione = vocAmAttivitaFunzione;
    }


    /**
     * Gets the vocAmDensitaPopolazione value for this CcostSitiAmianto.
     * 
     * @return vocAmDensitaPopolazione
     */
    public com.hyperborea.sira.ws.VocAmDensitaPopolazione getVocAmDensitaPopolazione() {
        return vocAmDensitaPopolazione;
    }


    /**
     * Sets the vocAmDensitaPopolazione value for this CcostSitiAmianto.
     * 
     * @param vocAmDensitaPopolazione
     */
    public void setVocAmDensitaPopolazione(com.hyperborea.sira.ws.VocAmDensitaPopolazione vocAmDensitaPopolazione) {
        this.vocAmDensitaPopolazione = vocAmDensitaPopolazione;
    }


    /**
     * Gets the vocAmDistanzaAbitato value for this CcostSitiAmianto.
     * 
     * @return vocAmDistanzaAbitato
     */
    public com.hyperborea.sira.ws.VocAmDistanzaAbitato getVocAmDistanzaAbitato() {
        return vocAmDistanzaAbitato;
    }


    /**
     * Sets the vocAmDistanzaAbitato value for this CcostSitiAmianto.
     * 
     * @param vocAmDistanzaAbitato
     */
    public void setVocAmDistanzaAbitato(com.hyperborea.sira.ws.VocAmDistanzaAbitato vocAmDistanzaAbitato) {
        this.vocAmDistanzaAbitato = vocAmDistanzaAbitato;
    }


    /**
     * Gets the vocAmEstensione value for this CcostSitiAmianto.
     * 
     * @return vocAmEstensione
     */
    public com.hyperborea.sira.ws.VocAmEstensione getVocAmEstensione() {
        return vocAmEstensione;
    }


    /**
     * Sets the vocAmEstensione value for this CcostSitiAmianto.
     * 
     * @param vocAmEstensione
     */
    public void setVocAmEstensione(com.hyperborea.sira.ws.VocAmEstensione vocAmEstensione) {
        this.vocAmEstensione = vocAmEstensione;
    }


    /**
     * Gets the vocAmEstensioneAffioramento value for this CcostSitiAmianto.
     * 
     * @return vocAmEstensioneAffioramento
     */
    public com.hyperborea.sira.ws.VocAmEstensioneAffioramento getVocAmEstensioneAffioramento() {
        return vocAmEstensioneAffioramento;
    }


    /**
     * Sets the vocAmEstensioneAffioramento value for this CcostSitiAmianto.
     * 
     * @param vocAmEstensioneAffioramento
     */
    public void setVocAmEstensioneAffioramento(com.hyperborea.sira.ws.VocAmEstensioneAffioramento vocAmEstensioneAffioramento) {
        this.vocAmEstensioneAffioramento = vocAmEstensioneAffioramento;
    }


    /**
     * Gets the vocAmEtaMedia value for this CcostSitiAmianto.
     * 
     * @return vocAmEtaMedia
     */
    public com.hyperborea.sira.ws.VocAmEtaMedia getVocAmEtaMedia() {
        return vocAmEtaMedia;
    }


    /**
     * Sets the vocAmEtaMedia value for this CcostSitiAmianto.
     * 
     * @param vocAmEtaMedia
     */
    public void setVocAmEtaMedia(com.hyperborea.sira.ws.VocAmEtaMedia vocAmEtaMedia) {
        this.vocAmEtaMedia = vocAmEtaMedia;
    }


    /**
     * Gets the vocAmFibreAerodisperse value for this CcostSitiAmianto.
     * 
     * @return vocAmFibreAerodisperse
     */
    public com.hyperborea.sira.ws.VocAmFibreAerodisperse getVocAmFibreAerodisperse() {
        return vocAmFibreAerodisperse;
    }


    /**
     * Sets the vocAmFibreAerodisperse value for this CcostSitiAmianto.
     * 
     * @param vocAmFibreAerodisperse
     */
    public void setVocAmFibreAerodisperse(com.hyperborea.sira.ws.VocAmFibreAerodisperse vocAmFibreAerodisperse) {
        this.vocAmFibreAerodisperse = vocAmFibreAerodisperse;
    }


    /**
     * Gets the vocAmFreqUtilizzo value for this CcostSitiAmianto.
     * 
     * @return vocAmFreqUtilizzo
     */
    public com.hyperborea.sira.ws.VocAmFreqUtilizzo getVocAmFreqUtilizzo() {
        return vocAmFreqUtilizzo;
    }


    /**
     * Sets the vocAmFreqUtilizzo value for this CcostSitiAmianto.
     * 
     * @param vocAmFreqUtilizzo
     */
    public void setVocAmFreqUtilizzo(com.hyperborea.sira.ws.VocAmFreqUtilizzo vocAmFreqUtilizzo) {
        this.vocAmFreqUtilizzo = vocAmFreqUtilizzo;
    }


    /**
     * Gets the vocAmMaterialeAffioramento value for this CcostSitiAmianto.
     * 
     * @return vocAmMaterialeAffioramento
     */
    public com.hyperborea.sira.ws.VocAmMaterialeAffioramento getVocAmMaterialeAffioramento() {
        return vocAmMaterialeAffioramento;
    }


    /**
     * Sets the vocAmMaterialeAffioramento value for this CcostSitiAmianto.
     * 
     * @param vocAmMaterialeAffioramento
     */
    public void setVocAmMaterialeAffioramento(com.hyperborea.sira.ws.VocAmMaterialeAffioramento vocAmMaterialeAffioramento) {
        this.vocAmMaterialeAffioramento = vocAmMaterialeAffioramento;
    }


    /**
     * Gets the vocAmPeriodoDismissione value for this CcostSitiAmianto.
     * 
     * @return vocAmPeriodoDismissione
     */
    public com.hyperborea.sira.ws.VocAmPeriodoDismissione getVocAmPeriodoDismissione() {
        return vocAmPeriodoDismissione;
    }


    /**
     * Sets the vocAmPeriodoDismissione value for this CcostSitiAmianto.
     * 
     * @param vocAmPeriodoDismissione
     */
    public void setVocAmPeriodoDismissione(com.hyperborea.sira.ws.VocAmPeriodoDismissione vocAmPeriodoDismissione) {
        this.vocAmPeriodoDismissione = vocAmPeriodoDismissione;
    }


    /**
     * Gets the vocAmPresenzaAffioramento value for this CcostSitiAmianto.
     * 
     * @return vocAmPresenzaAffioramento
     */
    public com.hyperborea.sira.ws.VocAmPresenzaAffioramento getVocAmPresenzaAffioramento() {
        return vocAmPresenzaAffioramento;
    }


    /**
     * Sets the vocAmPresenzaAffioramento value for this CcostSitiAmianto.
     * 
     * @param vocAmPresenzaAffioramento
     */
    public void setVocAmPresenzaAffioramento(com.hyperborea.sira.ws.VocAmPresenzaAffioramento vocAmPresenzaAffioramento) {
        this.vocAmPresenzaAffioramento = vocAmPresenzaAffioramento;
    }


    /**
     * Gets the vocAmQuantitaCompatto value for this CcostSitiAmianto.
     * 
     * @return vocAmQuantitaCompatto
     */
    public com.hyperborea.sira.ws.VocAmQuantitaCompatto getVocAmQuantitaCompatto() {
        return vocAmQuantitaCompatto;
    }


    /**
     * Sets the vocAmQuantitaCompatto value for this CcostSitiAmianto.
     * 
     * @param vocAmQuantitaCompatto
     */
    public void setVocAmQuantitaCompatto(com.hyperborea.sira.ws.VocAmQuantitaCompatto vocAmQuantitaCompatto) {
        this.vocAmQuantitaCompatto = vocAmQuantitaCompatto;
    }


    /**
     * Gets the vocAmQuantitaFriabile value for this CcostSitiAmianto.
     * 
     * @return vocAmQuantitaFriabile
     */
    public com.hyperborea.sira.ws.VocAmQuantitaFriabile getVocAmQuantitaFriabile() {
        return vocAmQuantitaFriabile;
    }


    /**
     * Sets the vocAmQuantitaFriabile value for this CcostSitiAmianto.
     * 
     * @param vocAmQuantitaFriabile
     */
    public void setVocAmQuantitaFriabile(com.hyperborea.sira.ws.VocAmQuantitaFriabile vocAmQuantitaFriabile) {
        this.vocAmQuantitaFriabile = vocAmQuantitaFriabile;
    }


    /**
     * Gets the vocAmStatoConservazione value for this CcostSitiAmianto.
     * 
     * @return vocAmStatoConservazione
     */
    public com.hyperborea.sira.ws.VocAmStatoConservazione getVocAmStatoConservazione() {
        return vocAmStatoConservazione;
    }


    /**
     * Sets the vocAmStatoConservazione value for this CcostSitiAmianto.
     * 
     * @param vocAmStatoConservazione
     */
    public void setVocAmStatoConservazione(com.hyperborea.sira.ws.VocAmStatoConservazione vocAmStatoConservazione) {
        this.vocAmStatoConservazione = vocAmStatoConservazione;
    }


    /**
     * Gets the vocAmSuperficieEsposta value for this CcostSitiAmianto.
     * 
     * @return vocAmSuperficieEsposta
     */
    public com.hyperborea.sira.ws.VocAmSuperficieEsposta getVocAmSuperficieEsposta() {
        return vocAmSuperficieEsposta;
    }


    /**
     * Sets the vocAmSuperficieEsposta value for this CcostSitiAmianto.
     * 
     * @param vocAmSuperficieEsposta
     */
    public void setVocAmSuperficieEsposta(com.hyperborea.sira.ws.VocAmSuperficieEsposta vocAmSuperficieEsposta) {
        this.vocAmSuperficieEsposta = vocAmSuperficieEsposta;
    }


    /**
     * Gets the vocAmTipologia value for this CcostSitiAmianto.
     * 
     * @return vocAmTipologia
     */
    public com.hyperborea.sira.ws.VocAmTipologia getVocAmTipologia() {
        return vocAmTipologia;
    }


    /**
     * Sets the vocAmTipologia value for this CcostSitiAmianto.
     * 
     * @param vocAmTipologia
     */
    public void setVocAmTipologia(com.hyperborea.sira.ws.VocAmTipologia vocAmTipologia) {
        this.vocAmTipologia = vocAmTipologia;
    }


    /**
     * Gets the vocAmTipologiaAmianto value for this CcostSitiAmianto.
     * 
     * @return vocAmTipologiaAmianto
     */
    public com.hyperborea.sira.ws.VocAmTipologiaAmianto getVocAmTipologiaAmianto() {
        return vocAmTipologiaAmianto;
    }


    /**
     * Sets the vocAmTipologiaAmianto value for this CcostSitiAmianto.
     * 
     * @param vocAmTipologiaAmianto
     */
    public void setVocAmTipologiaAmianto(com.hyperborea.sira.ws.VocAmTipologiaAmianto vocAmTipologiaAmianto) {
        this.vocAmTipologiaAmianto = vocAmTipologiaAmianto;
    }


    /**
     * Gets the vocAmUso value for this CcostSitiAmianto.
     * 
     * @return vocAmUso
     */
    public com.hyperborea.sira.ws.VocAmUso getVocAmUso() {
        return vocAmUso;
    }


    /**
     * Sets the vocAmUso value for this CcostSitiAmianto.
     * 
     * @param vocAmUso
     */
    public void setVocAmUso(com.hyperborea.sira.ws.VocAmUso vocAmUso) {
        this.vocAmUso = vocAmUso;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostSitiAmianto)) return false;
        CcostSitiAmianto other = (CcostSitiAmianto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.annoAttivazione==null && other.getAnnoAttivazione()==null) || 
             (this.annoAttivazione!=null &&
              this.annoAttivazione.equals(other.getAnnoAttivazione()))) &&
            ((this.bonificheAmiantos==null && other.getBonificheAmiantos()==null) || 
             (this.bonificheAmiantos!=null &&
              java.util.Arrays.equals(this.bonificheAmiantos, other.getBonificheAmiantos()))) &&
            ((this.coeffClasse==null && other.getCoeffClasse()==null) || 
             (this.coeffClasse!=null &&
              this.coeffClasse.equals(other.getCoeffClasse()))) &&
            ((this.concFibreAerodisperse==null && other.getConcFibreAerodisperse()==null) || 
             (this.concFibreAerodisperse!=null &&
              this.concFibreAerodisperse.equals(other.getConcFibreAerodisperse()))) &&
            ((this.fattoreA==null && other.getFattoreA()==null) || 
             (this.fattoreA!=null &&
              this.fattoreA.equals(other.getFattoreA()))) &&
            ((this.fattoreB==null && other.getFattoreB()==null) || 
             (this.fattoreB!=null &&
              this.fattoreB.equals(other.getFattoreB()))) &&
            ((this.fattoreC==null && other.getFattoreC()==null) || 
             (this.fattoreC!=null &&
              this.fattoreC.equals(other.getFattoreC()))) &&
            ((this.fattoreD==null && other.getFattoreD()==null) || 
             (this.fattoreD!=null &&
              this.fattoreD.equals(other.getFattoreD()))) &&
            ((this.flagCauseDispersione==null && other.getFlagCauseDispersione()==null) || 
             (this.flagCauseDispersione!=null &&
              this.flagCauseDispersione.equals(other.getFlagCauseDispersione()))) &&
            ((this.flagConfinamento==null && other.getFlagConfinamento()==null) || 
             (this.flagConfinamento!=null &&
              this.flagConfinamento.equals(other.getFlagConfinamento()))) &&
            ((this.flagDatiEpidemiologici==null && other.getFlagDatiEpidemiologici()==null) || 
             (this.flagDatiEpidemiologici!=null &&
              this.flagDatiEpidemiologici.equals(other.getFlagDatiEpidemiologici()))) &&
            ((this.flagDatiEpidemiologiciNat==null && other.getFlagDatiEpidemiologiciNat()==null) || 
             (this.flagDatiEpidemiologiciNat!=null &&
              this.flagDatiEpidemiologiciNat.equals(other.getFlagDatiEpidemiologiciNat()))) &&
            ((this.flagMaterialeCompatto==null && other.getFlagMaterialeCompatto()==null) || 
             (this.flagMaterialeCompatto!=null &&
              this.flagMaterialeCompatto.equals(other.getFlagMaterialeCompatto()))) &&
            ((this.flagMaterialeFriabile==null && other.getFlagMaterialeFriabile()==null) || 
             (this.flagMaterialeFriabile!=null &&
              this.flagMaterialeFriabile.equals(other.getFlagMaterialeFriabile()))) &&
            ((this.flagPresenzaAmiantoAltro==null && other.getFlagPresenzaAmiantoAltro()==null) || 
             (this.flagPresenzaAmiantoAltro!=null &&
              this.flagPresenzaAmiantoAltro.equals(other.getFlagPresenzaAmiantoAltro()))) &&
            ((this.flagPresenzaAmiantoPavim==null && other.getFlagPresenzaAmiantoPavim()==null) || 
             (this.flagPresenzaAmiantoPavim!=null &&
              this.flagPresenzaAmiantoPavim.equals(other.getFlagPresenzaAmiantoPavim()))) &&
            ((this.flagPresenzaAmiantoTetto==null && other.getFlagPresenzaAmiantoTetto()==null) || 
             (this.flagPresenzaAmiantoTetto!=null &&
              this.flagPresenzaAmiantoTetto.equals(other.getFlagPresenzaAmiantoTetto()))) &&
            ((this.flagUrbanizzazione==null && other.getFlagUrbanizzazione()==null) || 
             (this.flagUrbanizzazione!=null &&
              this.flagUrbanizzazione.equals(other.getFlagUrbanizzazione()))) &&
            ((this.flagUrbanizzazioneNat==null && other.getFlagUrbanizzazioneNat()==null) || 
             (this.flagUrbanizzazioneNat!=null &&
              this.flagUrbanizzazioneNat.equals(other.getFlagUrbanizzazioneNat()))) &&
            ((this.flagValutazione==null && other.getFlagValutazione()==null) || 
             (this.flagValutazione!=null &&
              this.flagValutazione.equals(other.getFlagValutazione()))) &&
            ((this.i01==null && other.getI01()==null) || 
             (this.i01!=null &&
              this.i01.equals(other.getI01()))) &&
            ((this.i02==null && other.getI02()==null) || 
             (this.i02!=null &&
              this.i02.equals(other.getI02()))) &&
            ((this.i03==null && other.getI03()==null) || 
             (this.i03!=null &&
              this.i03.equals(other.getI03()))) &&
            ((this.i04==null && other.getI04()==null) || 
             (this.i04!=null &&
              this.i04.equals(other.getI04()))) &&
            ((this.i05==null && other.getI05()==null) || 
             (this.i05!=null &&
              this.i05.equals(other.getI05()))) &&
            ((this.i06==null && other.getI06()==null) || 
             (this.i06!=null &&
              this.i06.equals(other.getI06()))) &&
            ((this.i07==null && other.getI07()==null) || 
             (this.i07!=null &&
              this.i07.equals(other.getI07()))) &&
            ((this.i08==null && other.getI08()==null) || 
             (this.i08!=null &&
              this.i08.equals(other.getI08()))) &&
            ((this.i09==null && other.getI09()==null) || 
             (this.i09!=null &&
              this.i09.equals(other.getI09()))) &&
            ((this.i10==null && other.getI10()==null) || 
             (this.i10!=null &&
              this.i10.equals(other.getI10()))) &&
            ((this.i11==null && other.getI11()==null) || 
             (this.i11!=null &&
              this.i11.equals(other.getI11()))) &&
            ((this.i12==null && other.getI12()==null) || 
             (this.i12!=null &&
              this.i12.equals(other.getI12()))) &&
            ((this.i13==null && other.getI13()==null) || 
             (this.i13!=null &&
              this.i13.equals(other.getI13()))) &&
            ((this.i14==null && other.getI14()==null) || 
             (this.i14!=null &&
              this.i14.equals(other.getI14()))) &&
            ((this.i15==null && other.getI15()==null) || 
             (this.i15!=null &&
              this.i15.equals(other.getI15()))) &&
            ((this.i16==null && other.getI16()==null) || 
             (this.i16!=null &&
              this.i16.equals(other.getI16()))) &&
            ((this.i17==null && other.getI17()==null) || 
             (this.i17!=null &&
              this.i17.equals(other.getI17()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.in1==null && other.getIn1()==null) || 
             (this.in1!=null &&
              this.in1.equals(other.getIn1()))) &&
            ((this.in2==null && other.getIn2()==null) || 
             (this.in2!=null &&
              this.in2.equals(other.getIn2()))) &&
            ((this.in3==null && other.getIn3()==null) || 
             (this.in3!=null &&
              this.in3.equals(other.getIn3()))) &&
            ((this.in4==null && other.getIn4()==null) || 
             (this.in4!=null &&
              this.in4.equals(other.getIn4()))) &&
            ((this.in5==null && other.getIn5()==null) || 
             (this.in5!=null &&
              this.in5.equals(other.getIn5()))) &&
            ((this.in6==null && other.getIn6()==null) || 
             (this.in6!=null &&
              this.in6.equals(other.getIn6()))) &&
            ((this.livPriorita==null && other.getLivPriorita()==null) || 
             (this.livPriorita!=null &&
              this.livPriorita.equals(other.getLivPriorita()))) &&
            ((this.punteggio==null && other.getPunteggio()==null) || 
             (this.punteggio!=null &&
              this.punteggio.equals(other.getPunteggio()))) &&
            ((this.vocAmAccessibilita==null && other.getVocAmAccessibilita()==null) || 
             (this.vocAmAccessibilita!=null &&
              this.vocAmAccessibilita.equals(other.getVocAmAccessibilita()))) &&
            ((this.vocAmAttivitaFunzione==null && other.getVocAmAttivitaFunzione()==null) || 
             (this.vocAmAttivitaFunzione!=null &&
              this.vocAmAttivitaFunzione.equals(other.getVocAmAttivitaFunzione()))) &&
            ((this.vocAmDensitaPopolazione==null && other.getVocAmDensitaPopolazione()==null) || 
             (this.vocAmDensitaPopolazione!=null &&
              this.vocAmDensitaPopolazione.equals(other.getVocAmDensitaPopolazione()))) &&
            ((this.vocAmDistanzaAbitato==null && other.getVocAmDistanzaAbitato()==null) || 
             (this.vocAmDistanzaAbitato!=null &&
              this.vocAmDistanzaAbitato.equals(other.getVocAmDistanzaAbitato()))) &&
            ((this.vocAmEstensione==null && other.getVocAmEstensione()==null) || 
             (this.vocAmEstensione!=null &&
              this.vocAmEstensione.equals(other.getVocAmEstensione()))) &&
            ((this.vocAmEstensioneAffioramento==null && other.getVocAmEstensioneAffioramento()==null) || 
             (this.vocAmEstensioneAffioramento!=null &&
              this.vocAmEstensioneAffioramento.equals(other.getVocAmEstensioneAffioramento()))) &&
            ((this.vocAmEtaMedia==null && other.getVocAmEtaMedia()==null) || 
             (this.vocAmEtaMedia!=null &&
              this.vocAmEtaMedia.equals(other.getVocAmEtaMedia()))) &&
            ((this.vocAmFibreAerodisperse==null && other.getVocAmFibreAerodisperse()==null) || 
             (this.vocAmFibreAerodisperse!=null &&
              this.vocAmFibreAerodisperse.equals(other.getVocAmFibreAerodisperse()))) &&
            ((this.vocAmFreqUtilizzo==null && other.getVocAmFreqUtilizzo()==null) || 
             (this.vocAmFreqUtilizzo!=null &&
              this.vocAmFreqUtilizzo.equals(other.getVocAmFreqUtilizzo()))) &&
            ((this.vocAmMaterialeAffioramento==null && other.getVocAmMaterialeAffioramento()==null) || 
             (this.vocAmMaterialeAffioramento!=null &&
              this.vocAmMaterialeAffioramento.equals(other.getVocAmMaterialeAffioramento()))) &&
            ((this.vocAmPeriodoDismissione==null && other.getVocAmPeriodoDismissione()==null) || 
             (this.vocAmPeriodoDismissione!=null &&
              this.vocAmPeriodoDismissione.equals(other.getVocAmPeriodoDismissione()))) &&
            ((this.vocAmPresenzaAffioramento==null && other.getVocAmPresenzaAffioramento()==null) || 
             (this.vocAmPresenzaAffioramento!=null &&
              this.vocAmPresenzaAffioramento.equals(other.getVocAmPresenzaAffioramento()))) &&
            ((this.vocAmQuantitaCompatto==null && other.getVocAmQuantitaCompatto()==null) || 
             (this.vocAmQuantitaCompatto!=null &&
              this.vocAmQuantitaCompatto.equals(other.getVocAmQuantitaCompatto()))) &&
            ((this.vocAmQuantitaFriabile==null && other.getVocAmQuantitaFriabile()==null) || 
             (this.vocAmQuantitaFriabile!=null &&
              this.vocAmQuantitaFriabile.equals(other.getVocAmQuantitaFriabile()))) &&
            ((this.vocAmStatoConservazione==null && other.getVocAmStatoConservazione()==null) || 
             (this.vocAmStatoConservazione!=null &&
              this.vocAmStatoConservazione.equals(other.getVocAmStatoConservazione()))) &&
            ((this.vocAmSuperficieEsposta==null && other.getVocAmSuperficieEsposta()==null) || 
             (this.vocAmSuperficieEsposta!=null &&
              this.vocAmSuperficieEsposta.equals(other.getVocAmSuperficieEsposta()))) &&
            ((this.vocAmTipologia==null && other.getVocAmTipologia()==null) || 
             (this.vocAmTipologia!=null &&
              this.vocAmTipologia.equals(other.getVocAmTipologia()))) &&
            ((this.vocAmTipologiaAmianto==null && other.getVocAmTipologiaAmianto()==null) || 
             (this.vocAmTipologiaAmianto!=null &&
              this.vocAmTipologiaAmianto.equals(other.getVocAmTipologiaAmianto()))) &&
            ((this.vocAmUso==null && other.getVocAmUso()==null) || 
             (this.vocAmUso!=null &&
              this.vocAmUso.equals(other.getVocAmUso())));
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
        if (getAnnoAttivazione() != null) {
            _hashCode += getAnnoAttivazione().hashCode();
        }
        if (getBonificheAmiantos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBonificheAmiantos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBonificheAmiantos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCoeffClasse() != null) {
            _hashCode += getCoeffClasse().hashCode();
        }
        if (getConcFibreAerodisperse() != null) {
            _hashCode += getConcFibreAerodisperse().hashCode();
        }
        if (getFattoreA() != null) {
            _hashCode += getFattoreA().hashCode();
        }
        if (getFattoreB() != null) {
            _hashCode += getFattoreB().hashCode();
        }
        if (getFattoreC() != null) {
            _hashCode += getFattoreC().hashCode();
        }
        if (getFattoreD() != null) {
            _hashCode += getFattoreD().hashCode();
        }
        if (getFlagCauseDispersione() != null) {
            _hashCode += getFlagCauseDispersione().hashCode();
        }
        if (getFlagConfinamento() != null) {
            _hashCode += getFlagConfinamento().hashCode();
        }
        if (getFlagDatiEpidemiologici() != null) {
            _hashCode += getFlagDatiEpidemiologici().hashCode();
        }
        if (getFlagDatiEpidemiologiciNat() != null) {
            _hashCode += getFlagDatiEpidemiologiciNat().hashCode();
        }
        if (getFlagMaterialeCompatto() != null) {
            _hashCode += getFlagMaterialeCompatto().hashCode();
        }
        if (getFlagMaterialeFriabile() != null) {
            _hashCode += getFlagMaterialeFriabile().hashCode();
        }
        if (getFlagPresenzaAmiantoAltro() != null) {
            _hashCode += getFlagPresenzaAmiantoAltro().hashCode();
        }
        if (getFlagPresenzaAmiantoPavim() != null) {
            _hashCode += getFlagPresenzaAmiantoPavim().hashCode();
        }
        if (getFlagPresenzaAmiantoTetto() != null) {
            _hashCode += getFlagPresenzaAmiantoTetto().hashCode();
        }
        if (getFlagUrbanizzazione() != null) {
            _hashCode += getFlagUrbanizzazione().hashCode();
        }
        if (getFlagUrbanizzazioneNat() != null) {
            _hashCode += getFlagUrbanizzazioneNat().hashCode();
        }
        if (getFlagValutazione() != null) {
            _hashCode += getFlagValutazione().hashCode();
        }
        if (getI01() != null) {
            _hashCode += getI01().hashCode();
        }
        if (getI02() != null) {
            _hashCode += getI02().hashCode();
        }
        if (getI03() != null) {
            _hashCode += getI03().hashCode();
        }
        if (getI04() != null) {
            _hashCode += getI04().hashCode();
        }
        if (getI05() != null) {
            _hashCode += getI05().hashCode();
        }
        if (getI06() != null) {
            _hashCode += getI06().hashCode();
        }
        if (getI07() != null) {
            _hashCode += getI07().hashCode();
        }
        if (getI08() != null) {
            _hashCode += getI08().hashCode();
        }
        if (getI09() != null) {
            _hashCode += getI09().hashCode();
        }
        if (getI10() != null) {
            _hashCode += getI10().hashCode();
        }
        if (getI11() != null) {
            _hashCode += getI11().hashCode();
        }
        if (getI12() != null) {
            _hashCode += getI12().hashCode();
        }
        if (getI13() != null) {
            _hashCode += getI13().hashCode();
        }
        if (getI14() != null) {
            _hashCode += getI14().hashCode();
        }
        if (getI15() != null) {
            _hashCode += getI15().hashCode();
        }
        if (getI16() != null) {
            _hashCode += getI16().hashCode();
        }
        if (getI17() != null) {
            _hashCode += getI17().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getIn1() != null) {
            _hashCode += getIn1().hashCode();
        }
        if (getIn2() != null) {
            _hashCode += getIn2().hashCode();
        }
        if (getIn3() != null) {
            _hashCode += getIn3().hashCode();
        }
        if (getIn4() != null) {
            _hashCode += getIn4().hashCode();
        }
        if (getIn5() != null) {
            _hashCode += getIn5().hashCode();
        }
        if (getIn6() != null) {
            _hashCode += getIn6().hashCode();
        }
        if (getLivPriorita() != null) {
            _hashCode += getLivPriorita().hashCode();
        }
        if (getPunteggio() != null) {
            _hashCode += getPunteggio().hashCode();
        }
        if (getVocAmAccessibilita() != null) {
            _hashCode += getVocAmAccessibilita().hashCode();
        }
        if (getVocAmAttivitaFunzione() != null) {
            _hashCode += getVocAmAttivitaFunzione().hashCode();
        }
        if (getVocAmDensitaPopolazione() != null) {
            _hashCode += getVocAmDensitaPopolazione().hashCode();
        }
        if (getVocAmDistanzaAbitato() != null) {
            _hashCode += getVocAmDistanzaAbitato().hashCode();
        }
        if (getVocAmEstensione() != null) {
            _hashCode += getVocAmEstensione().hashCode();
        }
        if (getVocAmEstensioneAffioramento() != null) {
            _hashCode += getVocAmEstensioneAffioramento().hashCode();
        }
        if (getVocAmEtaMedia() != null) {
            _hashCode += getVocAmEtaMedia().hashCode();
        }
        if (getVocAmFibreAerodisperse() != null) {
            _hashCode += getVocAmFibreAerodisperse().hashCode();
        }
        if (getVocAmFreqUtilizzo() != null) {
            _hashCode += getVocAmFreqUtilizzo().hashCode();
        }
        if (getVocAmMaterialeAffioramento() != null) {
            _hashCode += getVocAmMaterialeAffioramento().hashCode();
        }
        if (getVocAmPeriodoDismissione() != null) {
            _hashCode += getVocAmPeriodoDismissione().hashCode();
        }
        if (getVocAmPresenzaAffioramento() != null) {
            _hashCode += getVocAmPresenzaAffioramento().hashCode();
        }
        if (getVocAmQuantitaCompatto() != null) {
            _hashCode += getVocAmQuantitaCompatto().hashCode();
        }
        if (getVocAmQuantitaFriabile() != null) {
            _hashCode += getVocAmQuantitaFriabile().hashCode();
        }
        if (getVocAmStatoConservazione() != null) {
            _hashCode += getVocAmStatoConservazione().hashCode();
        }
        if (getVocAmSuperficieEsposta() != null) {
            _hashCode += getVocAmSuperficieEsposta().hashCode();
        }
        if (getVocAmTipologia() != null) {
            _hashCode += getVocAmTipologia().hashCode();
        }
        if (getVocAmTipologiaAmianto() != null) {
            _hashCode += getVocAmTipologiaAmianto().hashCode();
        }
        if (getVocAmUso() != null) {
            _hashCode += getVocAmUso().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostSitiAmianto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSitiAmianto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoAttivazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoAttivazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonificheAmiantos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bonificheAmiantos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "bonificheAmianto"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coeffClasse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "coeffClasse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("concFibreAerodisperse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "concFibreAerodisperse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fattoreA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fattoreA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fattoreB");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fattoreB"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fattoreC");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fattoreC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fattoreD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fattoreD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagCauseDispersione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagCauseDispersione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagConfinamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagConfinamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagDatiEpidemiologici");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagDatiEpidemiologici"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagDatiEpidemiologiciNat");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagDatiEpidemiologiciNat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagMaterialeCompatto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagMaterialeCompatto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagMaterialeFriabile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagMaterialeFriabile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagPresenzaAmiantoAltro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagPresenzaAmiantoAltro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagPresenzaAmiantoPavim");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagPresenzaAmiantoPavim"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagPresenzaAmiantoTetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagPresenzaAmiantoTetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagUrbanizzazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagUrbanizzazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagUrbanizzazioneNat");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagUrbanizzazioneNat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagValutazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagValutazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i01");
        elemField.setXmlName(new javax.xml.namespace.QName("", "i01"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i02");
        elemField.setXmlName(new javax.xml.namespace.QName("", "i02"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i03");
        elemField.setXmlName(new javax.xml.namespace.QName("", "i03"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i04");
        elemField.setXmlName(new javax.xml.namespace.QName("", "i04"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i05");
        elemField.setXmlName(new javax.xml.namespace.QName("", "i05"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i06");
        elemField.setXmlName(new javax.xml.namespace.QName("", "i06"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i07");
        elemField.setXmlName(new javax.xml.namespace.QName("", "i07"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i08");
        elemField.setXmlName(new javax.xml.namespace.QName("", "i08"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i09");
        elemField.setXmlName(new javax.xml.namespace.QName("", "i09"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i10");
        elemField.setXmlName(new javax.xml.namespace.QName("", "i10"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i11");
        elemField.setXmlName(new javax.xml.namespace.QName("", "i11"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i12");
        elemField.setXmlName(new javax.xml.namespace.QName("", "i12"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i13");
        elemField.setXmlName(new javax.xml.namespace.QName("", "i13"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i14");
        elemField.setXmlName(new javax.xml.namespace.QName("", "i14"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i15");
        elemField.setXmlName(new javax.xml.namespace.QName("", "i15"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i16");
        elemField.setXmlName(new javax.xml.namespace.QName("", "i16"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i17");
        elemField.setXmlName(new javax.xml.namespace.QName("", "i17"));
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
        elemField.setFieldName("in1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "in1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "in2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "in3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "in4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in5");
        elemField.setXmlName(new javax.xml.namespace.QName("", "in5"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in6");
        elemField.setXmlName(new javax.xml.namespace.QName("", "in6"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("livPriorita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "livPriorita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("punteggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "punteggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmAccessibilita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmAccessibilita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmAccessibilita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmAttivitaFunzione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmAttivitaFunzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmAttivitaFunzione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmDensitaPopolazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmDensitaPopolazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmDensitaPopolazione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmDistanzaAbitato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmDistanzaAbitato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmDistanzaAbitato"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmEstensione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmEstensione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmEstensione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmEstensioneAffioramento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmEstensioneAffioramento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmEstensioneAffioramento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmEtaMedia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmEtaMedia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmEtaMedia"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmFibreAerodisperse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmFibreAerodisperse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmFibreAerodisperse"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmFreqUtilizzo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmFreqUtilizzo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmFreqUtilizzo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmMaterialeAffioramento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmMaterialeAffioramento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmMaterialeAffioramento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmPeriodoDismissione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmPeriodoDismissione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmPeriodoDismissione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmPresenzaAffioramento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmPresenzaAffioramento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmPresenzaAffioramento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmQuantitaCompatto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmQuantitaCompatto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmQuantitaCompatto"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmQuantitaFriabile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmQuantitaFriabile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmQuantitaFriabile"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmStatoConservazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmStatoConservazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmStatoConservazione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmSuperficieEsposta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmSuperficieEsposta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmSuperficieEsposta"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmTipologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmTipologia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmTipologia"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmTipologiaAmianto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmTipologiaAmianto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmTipologiaAmianto"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmUso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmUso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmUso"));
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
