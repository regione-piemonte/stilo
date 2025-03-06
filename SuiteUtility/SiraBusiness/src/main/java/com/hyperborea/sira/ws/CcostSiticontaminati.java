/**
 * CcostSiticontaminati.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostSiticontaminati  implements java.io.Serializable {
    private java.lang.Integer abbancamentiFiniNum;

    private java.lang.Float abbancamentiFiniSup;

    private java.lang.Float abbancamentiFiniVol;

    private java.lang.String accessibilitaControlli;

    private java.lang.String accessibilitaNonAutorizzati;

    private java.lang.Float acqueSottVolumeEffettivo;

    private java.lang.Float acqueSottVolumeProgetto;

    private java.lang.Float acqueSupVolumeEffettivo;

    private java.lang.Float acqueSupVolumeProgetto;

    private java.lang.Float altezzaRifiuti;

    private java.lang.String area;

    private java.lang.Integer art18;

    private java.lang.Integer art7;

    private java.lang.Integer art8;

    private java.lang.Integer art9;

    private java.lang.String attEstrattivaCausa;

    private java.lang.Integer attEstrattivaPresenza;

    private java.lang.String attEstrattivaSorgente;

    private java.lang.Integer baciniFanghiNum;

    private java.lang.Float baciniFanghiSup;

    private java.lang.Float baciniFanghiVol;

    private com.hyperborea.sira.ws.Bonifiche[] bonifiches;

    private java.lang.String causaRifiuti;

    private java.lang.String codAttivita;

    private java.lang.String codNazionale;

    private java.lang.String codRegionale;

    private java.util.Calendar dataArchiviazione;

    private java.util.Calendar dataRinuncia;

    private java.util.Calendar dataScadenza;

    private java.lang.String descrizione;

    private java.lang.String destUsoPrevalente;

    private java.lang.String destUsoProgetto;

    private java.lang.Integer discaricheMinerarieNum;

    private java.lang.Float discaricheMinerarieSup;

    private java.lang.Float discaricheMinerarieVol;

    private java.lang.Integer effettivamenteContaminato;

    private java.lang.String eventiAccidentaliSorgente;

    private java.lang.String eventiIncidentaliTipo;

    private java.lang.String fineEstr;

    private java.lang.Integer idCcost;

    private java.lang.String impiantoTrattamento;

    private java.lang.String importo;

    private java.lang.String inizEstr;

    private java.lang.Integer iscrittoAnagrafe;

    private com.hyperborea.sira.ws.MatriciContaminate[] matriciContaminates;

    private java.lang.String messaInSicurezzaMineraria;

    private java.lang.String mineraliColtivati;

    private java.lang.String naturaAttivita;

    private java.lang.String note;

    private java.lang.String notizie;

    private java.lang.Integer numImpiantiTrattamento;

    private java.lang.String posizioneAmministrativa;

    private java.lang.Integer priorita;

    private java.lang.String processoProduttivo;

    private com.hyperborea.sira.ws.RelSiticontOstMinerari[] relSiticontOstMineraris;

    private java.lang.Integer scaviACieloApertoNum;

    private java.lang.Float scaviACieloApertoSup;

    private java.lang.Float scaviACieloApertoVol;

    private java.lang.Integer sitoComunitario;

    private java.lang.Integer sitoDiIntNaz;

    private java.lang.Integer sitoMinerario;

    private java.lang.Integer sorgInqAccidentali;

    private java.lang.Integer sorgInqEventi;

    private java.lang.Integer sorgInqGestione;

    private java.lang.Integer sorgInqRifiuti;

    private java.lang.Integer sorgInqSversamenti;

    private java.lang.String sorgenteEventi;

    private java.lang.String sorgenteGestione;

    private java.lang.String sorgenteRifiuti;

    private java.lang.String statoAttivita;

    private java.lang.String statoContam;

    private java.lang.Float stimaSoggFalda;

    private java.lang.Float stimaSoggFalda2;

    private java.lang.Float stimaSoggFalda3;

    private java.lang.Float suoloSottosuoloVolEffettivo;

    private java.lang.Float suoloSottosuoloVolProgetto;

    private java.lang.Float suoloSuperficieEffettiva;

    private java.lang.Float suoloSuperficieProgetto;

    private java.lang.Float supContAccertata;

    private java.lang.Float supContStimata;

    private java.lang.Float superficieOccupata;

    private java.lang.String tipoAbbandonoRifiuti;

    private java.lang.String tipoEventiAccidentali;

    private java.lang.String tipoFalda;

    private java.lang.String tipoFalda2;

    private java.lang.String tipoFalda3;

    private java.lang.String tipoGestione;

    private java.lang.String tipoImpiantoTrattamento;

    private java.lang.String tipoInt;

    private java.lang.String tipoRifiuti;

    private java.lang.String tipoSversamenti;

    private com.hyperborea.sira.ws.VocTipoSitiCont vocTipoSitiCont;

    private java.lang.Float volumeAccertato;

    private java.lang.Float volumeStimato;

    public CcostSiticontaminati() {
    }

    public CcostSiticontaminati(
           java.lang.Integer abbancamentiFiniNum,
           java.lang.Float abbancamentiFiniSup,
           java.lang.Float abbancamentiFiniVol,
           java.lang.String accessibilitaControlli,
           java.lang.String accessibilitaNonAutorizzati,
           java.lang.Float acqueSottVolumeEffettivo,
           java.lang.Float acqueSottVolumeProgetto,
           java.lang.Float acqueSupVolumeEffettivo,
           java.lang.Float acqueSupVolumeProgetto,
           java.lang.Float altezzaRifiuti,
           java.lang.String area,
           java.lang.Integer art18,
           java.lang.Integer art7,
           java.lang.Integer art8,
           java.lang.Integer art9,
           java.lang.String attEstrattivaCausa,
           java.lang.Integer attEstrattivaPresenza,
           java.lang.String attEstrattivaSorgente,
           java.lang.Integer baciniFanghiNum,
           java.lang.Float baciniFanghiSup,
           java.lang.Float baciniFanghiVol,
           com.hyperborea.sira.ws.Bonifiche[] bonifiches,
           java.lang.String causaRifiuti,
           java.lang.String codAttivita,
           java.lang.String codNazionale,
           java.lang.String codRegionale,
           java.util.Calendar dataArchiviazione,
           java.util.Calendar dataRinuncia,
           java.util.Calendar dataScadenza,
           java.lang.String descrizione,
           java.lang.String destUsoPrevalente,
           java.lang.String destUsoProgetto,
           java.lang.Integer discaricheMinerarieNum,
           java.lang.Float discaricheMinerarieSup,
           java.lang.Float discaricheMinerarieVol,
           java.lang.Integer effettivamenteContaminato,
           java.lang.String eventiAccidentaliSorgente,
           java.lang.String eventiIncidentaliTipo,
           java.lang.String fineEstr,
           java.lang.Integer idCcost,
           java.lang.String impiantoTrattamento,
           java.lang.String importo,
           java.lang.String inizEstr,
           java.lang.Integer iscrittoAnagrafe,
           com.hyperborea.sira.ws.MatriciContaminate[] matriciContaminates,
           java.lang.String messaInSicurezzaMineraria,
           java.lang.String mineraliColtivati,
           java.lang.String naturaAttivita,
           java.lang.String note,
           java.lang.String notizie,
           java.lang.Integer numImpiantiTrattamento,
           java.lang.String posizioneAmministrativa,
           java.lang.Integer priorita,
           java.lang.String processoProduttivo,
           com.hyperborea.sira.ws.RelSiticontOstMinerari[] relSiticontOstMineraris,
           java.lang.Integer scaviACieloApertoNum,
           java.lang.Float scaviACieloApertoSup,
           java.lang.Float scaviACieloApertoVol,
           java.lang.Integer sitoComunitario,
           java.lang.Integer sitoDiIntNaz,
           java.lang.Integer sitoMinerario,
           java.lang.Integer sorgInqAccidentali,
           java.lang.Integer sorgInqEventi,
           java.lang.Integer sorgInqGestione,
           java.lang.Integer sorgInqRifiuti,
           java.lang.Integer sorgInqSversamenti,
           java.lang.String sorgenteEventi,
           java.lang.String sorgenteGestione,
           java.lang.String sorgenteRifiuti,
           java.lang.String statoAttivita,
           java.lang.String statoContam,
           java.lang.Float stimaSoggFalda,
           java.lang.Float stimaSoggFalda2,
           java.lang.Float stimaSoggFalda3,
           java.lang.Float suoloSottosuoloVolEffettivo,
           java.lang.Float suoloSottosuoloVolProgetto,
           java.lang.Float suoloSuperficieEffettiva,
           java.lang.Float suoloSuperficieProgetto,
           java.lang.Float supContAccertata,
           java.lang.Float supContStimata,
           java.lang.Float superficieOccupata,
           java.lang.String tipoAbbandonoRifiuti,
           java.lang.String tipoEventiAccidentali,
           java.lang.String tipoFalda,
           java.lang.String tipoFalda2,
           java.lang.String tipoFalda3,
           java.lang.String tipoGestione,
           java.lang.String tipoImpiantoTrattamento,
           java.lang.String tipoInt,
           java.lang.String tipoRifiuti,
           java.lang.String tipoSversamenti,
           com.hyperborea.sira.ws.VocTipoSitiCont vocTipoSitiCont,
           java.lang.Float volumeAccertato,
           java.lang.Float volumeStimato) {
           this.abbancamentiFiniNum = abbancamentiFiniNum;
           this.abbancamentiFiniSup = abbancamentiFiniSup;
           this.abbancamentiFiniVol = abbancamentiFiniVol;
           this.accessibilitaControlli = accessibilitaControlli;
           this.accessibilitaNonAutorizzati = accessibilitaNonAutorizzati;
           this.acqueSottVolumeEffettivo = acqueSottVolumeEffettivo;
           this.acqueSottVolumeProgetto = acqueSottVolumeProgetto;
           this.acqueSupVolumeEffettivo = acqueSupVolumeEffettivo;
           this.acqueSupVolumeProgetto = acqueSupVolumeProgetto;
           this.altezzaRifiuti = altezzaRifiuti;
           this.area = area;
           this.art18 = art18;
           this.art7 = art7;
           this.art8 = art8;
           this.art9 = art9;
           this.attEstrattivaCausa = attEstrattivaCausa;
           this.attEstrattivaPresenza = attEstrattivaPresenza;
           this.attEstrattivaSorgente = attEstrattivaSorgente;
           this.baciniFanghiNum = baciniFanghiNum;
           this.baciniFanghiSup = baciniFanghiSup;
           this.baciniFanghiVol = baciniFanghiVol;
           this.bonifiches = bonifiches;
           this.causaRifiuti = causaRifiuti;
           this.codAttivita = codAttivita;
           this.codNazionale = codNazionale;
           this.codRegionale = codRegionale;
           this.dataArchiviazione = dataArchiviazione;
           this.dataRinuncia = dataRinuncia;
           this.dataScadenza = dataScadenza;
           this.descrizione = descrizione;
           this.destUsoPrevalente = destUsoPrevalente;
           this.destUsoProgetto = destUsoProgetto;
           this.discaricheMinerarieNum = discaricheMinerarieNum;
           this.discaricheMinerarieSup = discaricheMinerarieSup;
           this.discaricheMinerarieVol = discaricheMinerarieVol;
           this.effettivamenteContaminato = effettivamenteContaminato;
           this.eventiAccidentaliSorgente = eventiAccidentaliSorgente;
           this.eventiIncidentaliTipo = eventiIncidentaliTipo;
           this.fineEstr = fineEstr;
           this.idCcost = idCcost;
           this.impiantoTrattamento = impiantoTrattamento;
           this.importo = importo;
           this.inizEstr = inizEstr;
           this.iscrittoAnagrafe = iscrittoAnagrafe;
           this.matriciContaminates = matriciContaminates;
           this.messaInSicurezzaMineraria = messaInSicurezzaMineraria;
           this.mineraliColtivati = mineraliColtivati;
           this.naturaAttivita = naturaAttivita;
           this.note = note;
           this.notizie = notizie;
           this.numImpiantiTrattamento = numImpiantiTrattamento;
           this.posizioneAmministrativa = posizioneAmministrativa;
           this.priorita = priorita;
           this.processoProduttivo = processoProduttivo;
           this.relSiticontOstMineraris = relSiticontOstMineraris;
           this.scaviACieloApertoNum = scaviACieloApertoNum;
           this.scaviACieloApertoSup = scaviACieloApertoSup;
           this.scaviACieloApertoVol = scaviACieloApertoVol;
           this.sitoComunitario = sitoComunitario;
           this.sitoDiIntNaz = sitoDiIntNaz;
           this.sitoMinerario = sitoMinerario;
           this.sorgInqAccidentali = sorgInqAccidentali;
           this.sorgInqEventi = sorgInqEventi;
           this.sorgInqGestione = sorgInqGestione;
           this.sorgInqRifiuti = sorgInqRifiuti;
           this.sorgInqSversamenti = sorgInqSversamenti;
           this.sorgenteEventi = sorgenteEventi;
           this.sorgenteGestione = sorgenteGestione;
           this.sorgenteRifiuti = sorgenteRifiuti;
           this.statoAttivita = statoAttivita;
           this.statoContam = statoContam;
           this.stimaSoggFalda = stimaSoggFalda;
           this.stimaSoggFalda2 = stimaSoggFalda2;
           this.stimaSoggFalda3 = stimaSoggFalda3;
           this.suoloSottosuoloVolEffettivo = suoloSottosuoloVolEffettivo;
           this.suoloSottosuoloVolProgetto = suoloSottosuoloVolProgetto;
           this.suoloSuperficieEffettiva = suoloSuperficieEffettiva;
           this.suoloSuperficieProgetto = suoloSuperficieProgetto;
           this.supContAccertata = supContAccertata;
           this.supContStimata = supContStimata;
           this.superficieOccupata = superficieOccupata;
           this.tipoAbbandonoRifiuti = tipoAbbandonoRifiuti;
           this.tipoEventiAccidentali = tipoEventiAccidentali;
           this.tipoFalda = tipoFalda;
           this.tipoFalda2 = tipoFalda2;
           this.tipoFalda3 = tipoFalda3;
           this.tipoGestione = tipoGestione;
           this.tipoImpiantoTrattamento = tipoImpiantoTrattamento;
           this.tipoInt = tipoInt;
           this.tipoRifiuti = tipoRifiuti;
           this.tipoSversamenti = tipoSversamenti;
           this.vocTipoSitiCont = vocTipoSitiCont;
           this.volumeAccertato = volumeAccertato;
           this.volumeStimato = volumeStimato;
    }


    /**
     * Gets the abbancamentiFiniNum value for this CcostSiticontaminati.
     * 
     * @return abbancamentiFiniNum
     */
    public java.lang.Integer getAbbancamentiFiniNum() {
        return abbancamentiFiniNum;
    }


    /**
     * Sets the abbancamentiFiniNum value for this CcostSiticontaminati.
     * 
     * @param abbancamentiFiniNum
     */
    public void setAbbancamentiFiniNum(java.lang.Integer abbancamentiFiniNum) {
        this.abbancamentiFiniNum = abbancamentiFiniNum;
    }


    /**
     * Gets the abbancamentiFiniSup value for this CcostSiticontaminati.
     * 
     * @return abbancamentiFiniSup
     */
    public java.lang.Float getAbbancamentiFiniSup() {
        return abbancamentiFiniSup;
    }


    /**
     * Sets the abbancamentiFiniSup value for this CcostSiticontaminati.
     * 
     * @param abbancamentiFiniSup
     */
    public void setAbbancamentiFiniSup(java.lang.Float abbancamentiFiniSup) {
        this.abbancamentiFiniSup = abbancamentiFiniSup;
    }


    /**
     * Gets the abbancamentiFiniVol value for this CcostSiticontaminati.
     * 
     * @return abbancamentiFiniVol
     */
    public java.lang.Float getAbbancamentiFiniVol() {
        return abbancamentiFiniVol;
    }


    /**
     * Sets the abbancamentiFiniVol value for this CcostSiticontaminati.
     * 
     * @param abbancamentiFiniVol
     */
    public void setAbbancamentiFiniVol(java.lang.Float abbancamentiFiniVol) {
        this.abbancamentiFiniVol = abbancamentiFiniVol;
    }


    /**
     * Gets the accessibilitaControlli value for this CcostSiticontaminati.
     * 
     * @return accessibilitaControlli
     */
    public java.lang.String getAccessibilitaControlli() {
        return accessibilitaControlli;
    }


    /**
     * Sets the accessibilitaControlli value for this CcostSiticontaminati.
     * 
     * @param accessibilitaControlli
     */
    public void setAccessibilitaControlli(java.lang.String accessibilitaControlli) {
        this.accessibilitaControlli = accessibilitaControlli;
    }


    /**
     * Gets the accessibilitaNonAutorizzati value for this CcostSiticontaminati.
     * 
     * @return accessibilitaNonAutorizzati
     */
    public java.lang.String getAccessibilitaNonAutorizzati() {
        return accessibilitaNonAutorizzati;
    }


    /**
     * Sets the accessibilitaNonAutorizzati value for this CcostSiticontaminati.
     * 
     * @param accessibilitaNonAutorizzati
     */
    public void setAccessibilitaNonAutorizzati(java.lang.String accessibilitaNonAutorizzati) {
        this.accessibilitaNonAutorizzati = accessibilitaNonAutorizzati;
    }


    /**
     * Gets the acqueSottVolumeEffettivo value for this CcostSiticontaminati.
     * 
     * @return acqueSottVolumeEffettivo
     */
    public java.lang.Float getAcqueSottVolumeEffettivo() {
        return acqueSottVolumeEffettivo;
    }


    /**
     * Sets the acqueSottVolumeEffettivo value for this CcostSiticontaminati.
     * 
     * @param acqueSottVolumeEffettivo
     */
    public void setAcqueSottVolumeEffettivo(java.lang.Float acqueSottVolumeEffettivo) {
        this.acqueSottVolumeEffettivo = acqueSottVolumeEffettivo;
    }


    /**
     * Gets the acqueSottVolumeProgetto value for this CcostSiticontaminati.
     * 
     * @return acqueSottVolumeProgetto
     */
    public java.lang.Float getAcqueSottVolumeProgetto() {
        return acqueSottVolumeProgetto;
    }


    /**
     * Sets the acqueSottVolumeProgetto value for this CcostSiticontaminati.
     * 
     * @param acqueSottVolumeProgetto
     */
    public void setAcqueSottVolumeProgetto(java.lang.Float acqueSottVolumeProgetto) {
        this.acqueSottVolumeProgetto = acqueSottVolumeProgetto;
    }


    /**
     * Gets the acqueSupVolumeEffettivo value for this CcostSiticontaminati.
     * 
     * @return acqueSupVolumeEffettivo
     */
    public java.lang.Float getAcqueSupVolumeEffettivo() {
        return acqueSupVolumeEffettivo;
    }


    /**
     * Sets the acqueSupVolumeEffettivo value for this CcostSiticontaminati.
     * 
     * @param acqueSupVolumeEffettivo
     */
    public void setAcqueSupVolumeEffettivo(java.lang.Float acqueSupVolumeEffettivo) {
        this.acqueSupVolumeEffettivo = acqueSupVolumeEffettivo;
    }


    /**
     * Gets the acqueSupVolumeProgetto value for this CcostSiticontaminati.
     * 
     * @return acqueSupVolumeProgetto
     */
    public java.lang.Float getAcqueSupVolumeProgetto() {
        return acqueSupVolumeProgetto;
    }


    /**
     * Sets the acqueSupVolumeProgetto value for this CcostSiticontaminati.
     * 
     * @param acqueSupVolumeProgetto
     */
    public void setAcqueSupVolumeProgetto(java.lang.Float acqueSupVolumeProgetto) {
        this.acqueSupVolumeProgetto = acqueSupVolumeProgetto;
    }


    /**
     * Gets the altezzaRifiuti value for this CcostSiticontaminati.
     * 
     * @return altezzaRifiuti
     */
    public java.lang.Float getAltezzaRifiuti() {
        return altezzaRifiuti;
    }


    /**
     * Sets the altezzaRifiuti value for this CcostSiticontaminati.
     * 
     * @param altezzaRifiuti
     */
    public void setAltezzaRifiuti(java.lang.Float altezzaRifiuti) {
        this.altezzaRifiuti = altezzaRifiuti;
    }


    /**
     * Gets the area value for this CcostSiticontaminati.
     * 
     * @return area
     */
    public java.lang.String getArea() {
        return area;
    }


    /**
     * Sets the area value for this CcostSiticontaminati.
     * 
     * @param area
     */
    public void setArea(java.lang.String area) {
        this.area = area;
    }


    /**
     * Gets the art18 value for this CcostSiticontaminati.
     * 
     * @return art18
     */
    public java.lang.Integer getArt18() {
        return art18;
    }


    /**
     * Sets the art18 value for this CcostSiticontaminati.
     * 
     * @param art18
     */
    public void setArt18(java.lang.Integer art18) {
        this.art18 = art18;
    }


    /**
     * Gets the art7 value for this CcostSiticontaminati.
     * 
     * @return art7
     */
    public java.lang.Integer getArt7() {
        return art7;
    }


    /**
     * Sets the art7 value for this CcostSiticontaminati.
     * 
     * @param art7
     */
    public void setArt7(java.lang.Integer art7) {
        this.art7 = art7;
    }


    /**
     * Gets the art8 value for this CcostSiticontaminati.
     * 
     * @return art8
     */
    public java.lang.Integer getArt8() {
        return art8;
    }


    /**
     * Sets the art8 value for this CcostSiticontaminati.
     * 
     * @param art8
     */
    public void setArt8(java.lang.Integer art8) {
        this.art8 = art8;
    }


    /**
     * Gets the art9 value for this CcostSiticontaminati.
     * 
     * @return art9
     */
    public java.lang.Integer getArt9() {
        return art9;
    }


    /**
     * Sets the art9 value for this CcostSiticontaminati.
     * 
     * @param art9
     */
    public void setArt9(java.lang.Integer art9) {
        this.art9 = art9;
    }


    /**
     * Gets the attEstrattivaCausa value for this CcostSiticontaminati.
     * 
     * @return attEstrattivaCausa
     */
    public java.lang.String getAttEstrattivaCausa() {
        return attEstrattivaCausa;
    }


    /**
     * Sets the attEstrattivaCausa value for this CcostSiticontaminati.
     * 
     * @param attEstrattivaCausa
     */
    public void setAttEstrattivaCausa(java.lang.String attEstrattivaCausa) {
        this.attEstrattivaCausa = attEstrattivaCausa;
    }


    /**
     * Gets the attEstrattivaPresenza value for this CcostSiticontaminati.
     * 
     * @return attEstrattivaPresenza
     */
    public java.lang.Integer getAttEstrattivaPresenza() {
        return attEstrattivaPresenza;
    }


    /**
     * Sets the attEstrattivaPresenza value for this CcostSiticontaminati.
     * 
     * @param attEstrattivaPresenza
     */
    public void setAttEstrattivaPresenza(java.lang.Integer attEstrattivaPresenza) {
        this.attEstrattivaPresenza = attEstrattivaPresenza;
    }


    /**
     * Gets the attEstrattivaSorgente value for this CcostSiticontaminati.
     * 
     * @return attEstrattivaSorgente
     */
    public java.lang.String getAttEstrattivaSorgente() {
        return attEstrattivaSorgente;
    }


    /**
     * Sets the attEstrattivaSorgente value for this CcostSiticontaminati.
     * 
     * @param attEstrattivaSorgente
     */
    public void setAttEstrattivaSorgente(java.lang.String attEstrattivaSorgente) {
        this.attEstrattivaSorgente = attEstrattivaSorgente;
    }


    /**
     * Gets the baciniFanghiNum value for this CcostSiticontaminati.
     * 
     * @return baciniFanghiNum
     */
    public java.lang.Integer getBaciniFanghiNum() {
        return baciniFanghiNum;
    }


    /**
     * Sets the baciniFanghiNum value for this CcostSiticontaminati.
     * 
     * @param baciniFanghiNum
     */
    public void setBaciniFanghiNum(java.lang.Integer baciniFanghiNum) {
        this.baciniFanghiNum = baciniFanghiNum;
    }


    /**
     * Gets the baciniFanghiSup value for this CcostSiticontaminati.
     * 
     * @return baciniFanghiSup
     */
    public java.lang.Float getBaciniFanghiSup() {
        return baciniFanghiSup;
    }


    /**
     * Sets the baciniFanghiSup value for this CcostSiticontaminati.
     * 
     * @param baciniFanghiSup
     */
    public void setBaciniFanghiSup(java.lang.Float baciniFanghiSup) {
        this.baciniFanghiSup = baciniFanghiSup;
    }


    /**
     * Gets the baciniFanghiVol value for this CcostSiticontaminati.
     * 
     * @return baciniFanghiVol
     */
    public java.lang.Float getBaciniFanghiVol() {
        return baciniFanghiVol;
    }


    /**
     * Sets the baciniFanghiVol value for this CcostSiticontaminati.
     * 
     * @param baciniFanghiVol
     */
    public void setBaciniFanghiVol(java.lang.Float baciniFanghiVol) {
        this.baciniFanghiVol = baciniFanghiVol;
    }


    /**
     * Gets the bonifiches value for this CcostSiticontaminati.
     * 
     * @return bonifiches
     */
    public com.hyperborea.sira.ws.Bonifiche[] getBonifiches() {
        return bonifiches;
    }


    /**
     * Sets the bonifiches value for this CcostSiticontaminati.
     * 
     * @param bonifiches
     */
    public void setBonifiches(com.hyperborea.sira.ws.Bonifiche[] bonifiches) {
        this.bonifiches = bonifiches;
    }

    public com.hyperborea.sira.ws.Bonifiche getBonifiches(int i) {
        return this.bonifiches[i];
    }

    public void setBonifiches(int i, com.hyperborea.sira.ws.Bonifiche _value) {
        this.bonifiches[i] = _value;
    }


    /**
     * Gets the causaRifiuti value for this CcostSiticontaminati.
     * 
     * @return causaRifiuti
     */
    public java.lang.String getCausaRifiuti() {
        return causaRifiuti;
    }


    /**
     * Sets the causaRifiuti value for this CcostSiticontaminati.
     * 
     * @param causaRifiuti
     */
    public void setCausaRifiuti(java.lang.String causaRifiuti) {
        this.causaRifiuti = causaRifiuti;
    }


    /**
     * Gets the codAttivita value for this CcostSiticontaminati.
     * 
     * @return codAttivita
     */
    public java.lang.String getCodAttivita() {
        return codAttivita;
    }


    /**
     * Sets the codAttivita value for this CcostSiticontaminati.
     * 
     * @param codAttivita
     */
    public void setCodAttivita(java.lang.String codAttivita) {
        this.codAttivita = codAttivita;
    }


    /**
     * Gets the codNazionale value for this CcostSiticontaminati.
     * 
     * @return codNazionale
     */
    public java.lang.String getCodNazionale() {
        return codNazionale;
    }


    /**
     * Sets the codNazionale value for this CcostSiticontaminati.
     * 
     * @param codNazionale
     */
    public void setCodNazionale(java.lang.String codNazionale) {
        this.codNazionale = codNazionale;
    }


    /**
     * Gets the codRegionale value for this CcostSiticontaminati.
     * 
     * @return codRegionale
     */
    public java.lang.String getCodRegionale() {
        return codRegionale;
    }


    /**
     * Sets the codRegionale value for this CcostSiticontaminati.
     * 
     * @param codRegionale
     */
    public void setCodRegionale(java.lang.String codRegionale) {
        this.codRegionale = codRegionale;
    }


    /**
     * Gets the dataArchiviazione value for this CcostSiticontaminati.
     * 
     * @return dataArchiviazione
     */
    public java.util.Calendar getDataArchiviazione() {
        return dataArchiviazione;
    }


    /**
     * Sets the dataArchiviazione value for this CcostSiticontaminati.
     * 
     * @param dataArchiviazione
     */
    public void setDataArchiviazione(java.util.Calendar dataArchiviazione) {
        this.dataArchiviazione = dataArchiviazione;
    }


    /**
     * Gets the dataRinuncia value for this CcostSiticontaminati.
     * 
     * @return dataRinuncia
     */
    public java.util.Calendar getDataRinuncia() {
        return dataRinuncia;
    }


    /**
     * Sets the dataRinuncia value for this CcostSiticontaminati.
     * 
     * @param dataRinuncia
     */
    public void setDataRinuncia(java.util.Calendar dataRinuncia) {
        this.dataRinuncia = dataRinuncia;
    }


    /**
     * Gets the dataScadenza value for this CcostSiticontaminati.
     * 
     * @return dataScadenza
     */
    public java.util.Calendar getDataScadenza() {
        return dataScadenza;
    }


    /**
     * Sets the dataScadenza value for this CcostSiticontaminati.
     * 
     * @param dataScadenza
     */
    public void setDataScadenza(java.util.Calendar dataScadenza) {
        this.dataScadenza = dataScadenza;
    }


    /**
     * Gets the descrizione value for this CcostSiticontaminati.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this CcostSiticontaminati.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the destUsoPrevalente value for this CcostSiticontaminati.
     * 
     * @return destUsoPrevalente
     */
    public java.lang.String getDestUsoPrevalente() {
        return destUsoPrevalente;
    }


    /**
     * Sets the destUsoPrevalente value for this CcostSiticontaminati.
     * 
     * @param destUsoPrevalente
     */
    public void setDestUsoPrevalente(java.lang.String destUsoPrevalente) {
        this.destUsoPrevalente = destUsoPrevalente;
    }


    /**
     * Gets the destUsoProgetto value for this CcostSiticontaminati.
     * 
     * @return destUsoProgetto
     */
    public java.lang.String getDestUsoProgetto() {
        return destUsoProgetto;
    }


    /**
     * Sets the destUsoProgetto value for this CcostSiticontaminati.
     * 
     * @param destUsoProgetto
     */
    public void setDestUsoProgetto(java.lang.String destUsoProgetto) {
        this.destUsoProgetto = destUsoProgetto;
    }


    /**
     * Gets the discaricheMinerarieNum value for this CcostSiticontaminati.
     * 
     * @return discaricheMinerarieNum
     */
    public java.lang.Integer getDiscaricheMinerarieNum() {
        return discaricheMinerarieNum;
    }


    /**
     * Sets the discaricheMinerarieNum value for this CcostSiticontaminati.
     * 
     * @param discaricheMinerarieNum
     */
    public void setDiscaricheMinerarieNum(java.lang.Integer discaricheMinerarieNum) {
        this.discaricheMinerarieNum = discaricheMinerarieNum;
    }


    /**
     * Gets the discaricheMinerarieSup value for this CcostSiticontaminati.
     * 
     * @return discaricheMinerarieSup
     */
    public java.lang.Float getDiscaricheMinerarieSup() {
        return discaricheMinerarieSup;
    }


    /**
     * Sets the discaricheMinerarieSup value for this CcostSiticontaminati.
     * 
     * @param discaricheMinerarieSup
     */
    public void setDiscaricheMinerarieSup(java.lang.Float discaricheMinerarieSup) {
        this.discaricheMinerarieSup = discaricheMinerarieSup;
    }


    /**
     * Gets the discaricheMinerarieVol value for this CcostSiticontaminati.
     * 
     * @return discaricheMinerarieVol
     */
    public java.lang.Float getDiscaricheMinerarieVol() {
        return discaricheMinerarieVol;
    }


    /**
     * Sets the discaricheMinerarieVol value for this CcostSiticontaminati.
     * 
     * @param discaricheMinerarieVol
     */
    public void setDiscaricheMinerarieVol(java.lang.Float discaricheMinerarieVol) {
        this.discaricheMinerarieVol = discaricheMinerarieVol;
    }


    /**
     * Gets the effettivamenteContaminato value for this CcostSiticontaminati.
     * 
     * @return effettivamenteContaminato
     */
    public java.lang.Integer getEffettivamenteContaminato() {
        return effettivamenteContaminato;
    }


    /**
     * Sets the effettivamenteContaminato value for this CcostSiticontaminati.
     * 
     * @param effettivamenteContaminato
     */
    public void setEffettivamenteContaminato(java.lang.Integer effettivamenteContaminato) {
        this.effettivamenteContaminato = effettivamenteContaminato;
    }


    /**
     * Gets the eventiAccidentaliSorgente value for this CcostSiticontaminati.
     * 
     * @return eventiAccidentaliSorgente
     */
    public java.lang.String getEventiAccidentaliSorgente() {
        return eventiAccidentaliSorgente;
    }


    /**
     * Sets the eventiAccidentaliSorgente value for this CcostSiticontaminati.
     * 
     * @param eventiAccidentaliSorgente
     */
    public void setEventiAccidentaliSorgente(java.lang.String eventiAccidentaliSorgente) {
        this.eventiAccidentaliSorgente = eventiAccidentaliSorgente;
    }


    /**
     * Gets the eventiIncidentaliTipo value for this CcostSiticontaminati.
     * 
     * @return eventiIncidentaliTipo
     */
    public java.lang.String getEventiIncidentaliTipo() {
        return eventiIncidentaliTipo;
    }


    /**
     * Sets the eventiIncidentaliTipo value for this CcostSiticontaminati.
     * 
     * @param eventiIncidentaliTipo
     */
    public void setEventiIncidentaliTipo(java.lang.String eventiIncidentaliTipo) {
        this.eventiIncidentaliTipo = eventiIncidentaliTipo;
    }


    /**
     * Gets the fineEstr value for this CcostSiticontaminati.
     * 
     * @return fineEstr
     */
    public java.lang.String getFineEstr() {
        return fineEstr;
    }


    /**
     * Sets the fineEstr value for this CcostSiticontaminati.
     * 
     * @param fineEstr
     */
    public void setFineEstr(java.lang.String fineEstr) {
        this.fineEstr = fineEstr;
    }


    /**
     * Gets the idCcost value for this CcostSiticontaminati.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostSiticontaminati.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the impiantoTrattamento value for this CcostSiticontaminati.
     * 
     * @return impiantoTrattamento
     */
    public java.lang.String getImpiantoTrattamento() {
        return impiantoTrattamento;
    }


    /**
     * Sets the impiantoTrattamento value for this CcostSiticontaminati.
     * 
     * @param impiantoTrattamento
     */
    public void setImpiantoTrattamento(java.lang.String impiantoTrattamento) {
        this.impiantoTrattamento = impiantoTrattamento;
    }


    /**
     * Gets the importo value for this CcostSiticontaminati.
     * 
     * @return importo
     */
    public java.lang.String getImporto() {
        return importo;
    }


    /**
     * Sets the importo value for this CcostSiticontaminati.
     * 
     * @param importo
     */
    public void setImporto(java.lang.String importo) {
        this.importo = importo;
    }


    /**
     * Gets the inizEstr value for this CcostSiticontaminati.
     * 
     * @return inizEstr
     */
    public java.lang.String getInizEstr() {
        return inizEstr;
    }


    /**
     * Sets the inizEstr value for this CcostSiticontaminati.
     * 
     * @param inizEstr
     */
    public void setInizEstr(java.lang.String inizEstr) {
        this.inizEstr = inizEstr;
    }


    /**
     * Gets the iscrittoAnagrafe value for this CcostSiticontaminati.
     * 
     * @return iscrittoAnagrafe
     */
    public java.lang.Integer getIscrittoAnagrafe() {
        return iscrittoAnagrafe;
    }


    /**
     * Sets the iscrittoAnagrafe value for this CcostSiticontaminati.
     * 
     * @param iscrittoAnagrafe
     */
    public void setIscrittoAnagrafe(java.lang.Integer iscrittoAnagrafe) {
        this.iscrittoAnagrafe = iscrittoAnagrafe;
    }


    /**
     * Gets the matriciContaminates value for this CcostSiticontaminati.
     * 
     * @return matriciContaminates
     */
    public com.hyperborea.sira.ws.MatriciContaminate[] getMatriciContaminates() {
        return matriciContaminates;
    }


    /**
     * Sets the matriciContaminates value for this CcostSiticontaminati.
     * 
     * @param matriciContaminates
     */
    public void setMatriciContaminates(com.hyperborea.sira.ws.MatriciContaminate[] matriciContaminates) {
        this.matriciContaminates = matriciContaminates;
    }

    public com.hyperborea.sira.ws.MatriciContaminate getMatriciContaminates(int i) {
        return this.matriciContaminates[i];
    }

    public void setMatriciContaminates(int i, com.hyperborea.sira.ws.MatriciContaminate _value) {
        this.matriciContaminates[i] = _value;
    }


    /**
     * Gets the messaInSicurezzaMineraria value for this CcostSiticontaminati.
     * 
     * @return messaInSicurezzaMineraria
     */
    public java.lang.String getMessaInSicurezzaMineraria() {
        return messaInSicurezzaMineraria;
    }


    /**
     * Sets the messaInSicurezzaMineraria value for this CcostSiticontaminati.
     * 
     * @param messaInSicurezzaMineraria
     */
    public void setMessaInSicurezzaMineraria(java.lang.String messaInSicurezzaMineraria) {
        this.messaInSicurezzaMineraria = messaInSicurezzaMineraria;
    }


    /**
     * Gets the mineraliColtivati value for this CcostSiticontaminati.
     * 
     * @return mineraliColtivati
     */
    public java.lang.String getMineraliColtivati() {
        return mineraliColtivati;
    }


    /**
     * Sets the mineraliColtivati value for this CcostSiticontaminati.
     * 
     * @param mineraliColtivati
     */
    public void setMineraliColtivati(java.lang.String mineraliColtivati) {
        this.mineraliColtivati = mineraliColtivati;
    }


    /**
     * Gets the naturaAttivita value for this CcostSiticontaminati.
     * 
     * @return naturaAttivita
     */
    public java.lang.String getNaturaAttivita() {
        return naturaAttivita;
    }


    /**
     * Sets the naturaAttivita value for this CcostSiticontaminati.
     * 
     * @param naturaAttivita
     */
    public void setNaturaAttivita(java.lang.String naturaAttivita) {
        this.naturaAttivita = naturaAttivita;
    }


    /**
     * Gets the note value for this CcostSiticontaminati.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this CcostSiticontaminati.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the notizie value for this CcostSiticontaminati.
     * 
     * @return notizie
     */
    public java.lang.String getNotizie() {
        return notizie;
    }


    /**
     * Sets the notizie value for this CcostSiticontaminati.
     * 
     * @param notizie
     */
    public void setNotizie(java.lang.String notizie) {
        this.notizie = notizie;
    }


    /**
     * Gets the numImpiantiTrattamento value for this CcostSiticontaminati.
     * 
     * @return numImpiantiTrattamento
     */
    public java.lang.Integer getNumImpiantiTrattamento() {
        return numImpiantiTrattamento;
    }


    /**
     * Sets the numImpiantiTrattamento value for this CcostSiticontaminati.
     * 
     * @param numImpiantiTrattamento
     */
    public void setNumImpiantiTrattamento(java.lang.Integer numImpiantiTrattamento) {
        this.numImpiantiTrattamento = numImpiantiTrattamento;
    }


    /**
     * Gets the posizioneAmministrativa value for this CcostSiticontaminati.
     * 
     * @return posizioneAmministrativa
     */
    public java.lang.String getPosizioneAmministrativa() {
        return posizioneAmministrativa;
    }


    /**
     * Sets the posizioneAmministrativa value for this CcostSiticontaminati.
     * 
     * @param posizioneAmministrativa
     */
    public void setPosizioneAmministrativa(java.lang.String posizioneAmministrativa) {
        this.posizioneAmministrativa = posizioneAmministrativa;
    }


    /**
     * Gets the priorita value for this CcostSiticontaminati.
     * 
     * @return priorita
     */
    public java.lang.Integer getPriorita() {
        return priorita;
    }


    /**
     * Sets the priorita value for this CcostSiticontaminati.
     * 
     * @param priorita
     */
    public void setPriorita(java.lang.Integer priorita) {
        this.priorita = priorita;
    }


    /**
     * Gets the processoProduttivo value for this CcostSiticontaminati.
     * 
     * @return processoProduttivo
     */
    public java.lang.String getProcessoProduttivo() {
        return processoProduttivo;
    }


    /**
     * Sets the processoProduttivo value for this CcostSiticontaminati.
     * 
     * @param processoProduttivo
     */
    public void setProcessoProduttivo(java.lang.String processoProduttivo) {
        this.processoProduttivo = processoProduttivo;
    }


    /**
     * Gets the relSiticontOstMineraris value for this CcostSiticontaminati.
     * 
     * @return relSiticontOstMineraris
     */
    public com.hyperborea.sira.ws.RelSiticontOstMinerari[] getRelSiticontOstMineraris() {
        return relSiticontOstMineraris;
    }


    /**
     * Sets the relSiticontOstMineraris value for this CcostSiticontaminati.
     * 
     * @param relSiticontOstMineraris
     */
    public void setRelSiticontOstMineraris(com.hyperborea.sira.ws.RelSiticontOstMinerari[] relSiticontOstMineraris) {
        this.relSiticontOstMineraris = relSiticontOstMineraris;
    }

    public com.hyperborea.sira.ws.RelSiticontOstMinerari getRelSiticontOstMineraris(int i) {
        return this.relSiticontOstMineraris[i];
    }

    public void setRelSiticontOstMineraris(int i, com.hyperborea.sira.ws.RelSiticontOstMinerari _value) {
        this.relSiticontOstMineraris[i] = _value;
    }


    /**
     * Gets the scaviACieloApertoNum value for this CcostSiticontaminati.
     * 
     * @return scaviACieloApertoNum
     */
    public java.lang.Integer getScaviACieloApertoNum() {
        return scaviACieloApertoNum;
    }


    /**
     * Sets the scaviACieloApertoNum value for this CcostSiticontaminati.
     * 
     * @param scaviACieloApertoNum
     */
    public void setScaviACieloApertoNum(java.lang.Integer scaviACieloApertoNum) {
        this.scaviACieloApertoNum = scaviACieloApertoNum;
    }


    /**
     * Gets the scaviACieloApertoSup value for this CcostSiticontaminati.
     * 
     * @return scaviACieloApertoSup
     */
    public java.lang.Float getScaviACieloApertoSup() {
        return scaviACieloApertoSup;
    }


    /**
     * Sets the scaviACieloApertoSup value for this CcostSiticontaminati.
     * 
     * @param scaviACieloApertoSup
     */
    public void setScaviACieloApertoSup(java.lang.Float scaviACieloApertoSup) {
        this.scaviACieloApertoSup = scaviACieloApertoSup;
    }


    /**
     * Gets the scaviACieloApertoVol value for this CcostSiticontaminati.
     * 
     * @return scaviACieloApertoVol
     */
    public java.lang.Float getScaviACieloApertoVol() {
        return scaviACieloApertoVol;
    }


    /**
     * Sets the scaviACieloApertoVol value for this CcostSiticontaminati.
     * 
     * @param scaviACieloApertoVol
     */
    public void setScaviACieloApertoVol(java.lang.Float scaviACieloApertoVol) {
        this.scaviACieloApertoVol = scaviACieloApertoVol;
    }


    /**
     * Gets the sitoComunitario value for this CcostSiticontaminati.
     * 
     * @return sitoComunitario
     */
    public java.lang.Integer getSitoComunitario() {
        return sitoComunitario;
    }


    /**
     * Sets the sitoComunitario value for this CcostSiticontaminati.
     * 
     * @param sitoComunitario
     */
    public void setSitoComunitario(java.lang.Integer sitoComunitario) {
        this.sitoComunitario = sitoComunitario;
    }


    /**
     * Gets the sitoDiIntNaz value for this CcostSiticontaminati.
     * 
     * @return sitoDiIntNaz
     */
    public java.lang.Integer getSitoDiIntNaz() {
        return sitoDiIntNaz;
    }


    /**
     * Sets the sitoDiIntNaz value for this CcostSiticontaminati.
     * 
     * @param sitoDiIntNaz
     */
    public void setSitoDiIntNaz(java.lang.Integer sitoDiIntNaz) {
        this.sitoDiIntNaz = sitoDiIntNaz;
    }


    /**
     * Gets the sitoMinerario value for this CcostSiticontaminati.
     * 
     * @return sitoMinerario
     */
    public java.lang.Integer getSitoMinerario() {
        return sitoMinerario;
    }


    /**
     * Sets the sitoMinerario value for this CcostSiticontaminati.
     * 
     * @param sitoMinerario
     */
    public void setSitoMinerario(java.lang.Integer sitoMinerario) {
        this.sitoMinerario = sitoMinerario;
    }


    /**
     * Gets the sorgInqAccidentali value for this CcostSiticontaminati.
     * 
     * @return sorgInqAccidentali
     */
    public java.lang.Integer getSorgInqAccidentali() {
        return sorgInqAccidentali;
    }


    /**
     * Sets the sorgInqAccidentali value for this CcostSiticontaminati.
     * 
     * @param sorgInqAccidentali
     */
    public void setSorgInqAccidentali(java.lang.Integer sorgInqAccidentali) {
        this.sorgInqAccidentali = sorgInqAccidentali;
    }


    /**
     * Gets the sorgInqEventi value for this CcostSiticontaminati.
     * 
     * @return sorgInqEventi
     */
    public java.lang.Integer getSorgInqEventi() {
        return sorgInqEventi;
    }


    /**
     * Sets the sorgInqEventi value for this CcostSiticontaminati.
     * 
     * @param sorgInqEventi
     */
    public void setSorgInqEventi(java.lang.Integer sorgInqEventi) {
        this.sorgInqEventi = sorgInqEventi;
    }


    /**
     * Gets the sorgInqGestione value for this CcostSiticontaminati.
     * 
     * @return sorgInqGestione
     */
    public java.lang.Integer getSorgInqGestione() {
        return sorgInqGestione;
    }


    /**
     * Sets the sorgInqGestione value for this CcostSiticontaminati.
     * 
     * @param sorgInqGestione
     */
    public void setSorgInqGestione(java.lang.Integer sorgInqGestione) {
        this.sorgInqGestione = sorgInqGestione;
    }


    /**
     * Gets the sorgInqRifiuti value for this CcostSiticontaminati.
     * 
     * @return sorgInqRifiuti
     */
    public java.lang.Integer getSorgInqRifiuti() {
        return sorgInqRifiuti;
    }


    /**
     * Sets the sorgInqRifiuti value for this CcostSiticontaminati.
     * 
     * @param sorgInqRifiuti
     */
    public void setSorgInqRifiuti(java.lang.Integer sorgInqRifiuti) {
        this.sorgInqRifiuti = sorgInqRifiuti;
    }


    /**
     * Gets the sorgInqSversamenti value for this CcostSiticontaminati.
     * 
     * @return sorgInqSversamenti
     */
    public java.lang.Integer getSorgInqSversamenti() {
        return sorgInqSversamenti;
    }


    /**
     * Sets the sorgInqSversamenti value for this CcostSiticontaminati.
     * 
     * @param sorgInqSversamenti
     */
    public void setSorgInqSversamenti(java.lang.Integer sorgInqSversamenti) {
        this.sorgInqSversamenti = sorgInqSversamenti;
    }


    /**
     * Gets the sorgenteEventi value for this CcostSiticontaminati.
     * 
     * @return sorgenteEventi
     */
    public java.lang.String getSorgenteEventi() {
        return sorgenteEventi;
    }


    /**
     * Sets the sorgenteEventi value for this CcostSiticontaminati.
     * 
     * @param sorgenteEventi
     */
    public void setSorgenteEventi(java.lang.String sorgenteEventi) {
        this.sorgenteEventi = sorgenteEventi;
    }


    /**
     * Gets the sorgenteGestione value for this CcostSiticontaminati.
     * 
     * @return sorgenteGestione
     */
    public java.lang.String getSorgenteGestione() {
        return sorgenteGestione;
    }


    /**
     * Sets the sorgenteGestione value for this CcostSiticontaminati.
     * 
     * @param sorgenteGestione
     */
    public void setSorgenteGestione(java.lang.String sorgenteGestione) {
        this.sorgenteGestione = sorgenteGestione;
    }


    /**
     * Gets the sorgenteRifiuti value for this CcostSiticontaminati.
     * 
     * @return sorgenteRifiuti
     */
    public java.lang.String getSorgenteRifiuti() {
        return sorgenteRifiuti;
    }


    /**
     * Sets the sorgenteRifiuti value for this CcostSiticontaminati.
     * 
     * @param sorgenteRifiuti
     */
    public void setSorgenteRifiuti(java.lang.String sorgenteRifiuti) {
        this.sorgenteRifiuti = sorgenteRifiuti;
    }


    /**
     * Gets the statoAttivita value for this CcostSiticontaminati.
     * 
     * @return statoAttivita
     */
    public java.lang.String getStatoAttivita() {
        return statoAttivita;
    }


    /**
     * Sets the statoAttivita value for this CcostSiticontaminati.
     * 
     * @param statoAttivita
     */
    public void setStatoAttivita(java.lang.String statoAttivita) {
        this.statoAttivita = statoAttivita;
    }


    /**
     * Gets the statoContam value for this CcostSiticontaminati.
     * 
     * @return statoContam
     */
    public java.lang.String getStatoContam() {
        return statoContam;
    }


    /**
     * Sets the statoContam value for this CcostSiticontaminati.
     * 
     * @param statoContam
     */
    public void setStatoContam(java.lang.String statoContam) {
        this.statoContam = statoContam;
    }


    /**
     * Gets the stimaSoggFalda value for this CcostSiticontaminati.
     * 
     * @return stimaSoggFalda
     */
    public java.lang.Float getStimaSoggFalda() {
        return stimaSoggFalda;
    }


    /**
     * Sets the stimaSoggFalda value for this CcostSiticontaminati.
     * 
     * @param stimaSoggFalda
     */
    public void setStimaSoggFalda(java.lang.Float stimaSoggFalda) {
        this.stimaSoggFalda = stimaSoggFalda;
    }


    /**
     * Gets the stimaSoggFalda2 value for this CcostSiticontaminati.
     * 
     * @return stimaSoggFalda2
     */
    public java.lang.Float getStimaSoggFalda2() {
        return stimaSoggFalda2;
    }


    /**
     * Sets the stimaSoggFalda2 value for this CcostSiticontaminati.
     * 
     * @param stimaSoggFalda2
     */
    public void setStimaSoggFalda2(java.lang.Float stimaSoggFalda2) {
        this.stimaSoggFalda2 = stimaSoggFalda2;
    }


    /**
     * Gets the stimaSoggFalda3 value for this CcostSiticontaminati.
     * 
     * @return stimaSoggFalda3
     */
    public java.lang.Float getStimaSoggFalda3() {
        return stimaSoggFalda3;
    }


    /**
     * Sets the stimaSoggFalda3 value for this CcostSiticontaminati.
     * 
     * @param stimaSoggFalda3
     */
    public void setStimaSoggFalda3(java.lang.Float stimaSoggFalda3) {
        this.stimaSoggFalda3 = stimaSoggFalda3;
    }


    /**
     * Gets the suoloSottosuoloVolEffettivo value for this CcostSiticontaminati.
     * 
     * @return suoloSottosuoloVolEffettivo
     */
    public java.lang.Float getSuoloSottosuoloVolEffettivo() {
        return suoloSottosuoloVolEffettivo;
    }


    /**
     * Sets the suoloSottosuoloVolEffettivo value for this CcostSiticontaminati.
     * 
     * @param suoloSottosuoloVolEffettivo
     */
    public void setSuoloSottosuoloVolEffettivo(java.lang.Float suoloSottosuoloVolEffettivo) {
        this.suoloSottosuoloVolEffettivo = suoloSottosuoloVolEffettivo;
    }


    /**
     * Gets the suoloSottosuoloVolProgetto value for this CcostSiticontaminati.
     * 
     * @return suoloSottosuoloVolProgetto
     */
    public java.lang.Float getSuoloSottosuoloVolProgetto() {
        return suoloSottosuoloVolProgetto;
    }


    /**
     * Sets the suoloSottosuoloVolProgetto value for this CcostSiticontaminati.
     * 
     * @param suoloSottosuoloVolProgetto
     */
    public void setSuoloSottosuoloVolProgetto(java.lang.Float suoloSottosuoloVolProgetto) {
        this.suoloSottosuoloVolProgetto = suoloSottosuoloVolProgetto;
    }


    /**
     * Gets the suoloSuperficieEffettiva value for this CcostSiticontaminati.
     * 
     * @return suoloSuperficieEffettiva
     */
    public java.lang.Float getSuoloSuperficieEffettiva() {
        return suoloSuperficieEffettiva;
    }


    /**
     * Sets the suoloSuperficieEffettiva value for this CcostSiticontaminati.
     * 
     * @param suoloSuperficieEffettiva
     */
    public void setSuoloSuperficieEffettiva(java.lang.Float suoloSuperficieEffettiva) {
        this.suoloSuperficieEffettiva = suoloSuperficieEffettiva;
    }


    /**
     * Gets the suoloSuperficieProgetto value for this CcostSiticontaminati.
     * 
     * @return suoloSuperficieProgetto
     */
    public java.lang.Float getSuoloSuperficieProgetto() {
        return suoloSuperficieProgetto;
    }


    /**
     * Sets the suoloSuperficieProgetto value for this CcostSiticontaminati.
     * 
     * @param suoloSuperficieProgetto
     */
    public void setSuoloSuperficieProgetto(java.lang.Float suoloSuperficieProgetto) {
        this.suoloSuperficieProgetto = suoloSuperficieProgetto;
    }


    /**
     * Gets the supContAccertata value for this CcostSiticontaminati.
     * 
     * @return supContAccertata
     */
    public java.lang.Float getSupContAccertata() {
        return supContAccertata;
    }


    /**
     * Sets the supContAccertata value for this CcostSiticontaminati.
     * 
     * @param supContAccertata
     */
    public void setSupContAccertata(java.lang.Float supContAccertata) {
        this.supContAccertata = supContAccertata;
    }


    /**
     * Gets the supContStimata value for this CcostSiticontaminati.
     * 
     * @return supContStimata
     */
    public java.lang.Float getSupContStimata() {
        return supContStimata;
    }


    /**
     * Sets the supContStimata value for this CcostSiticontaminati.
     * 
     * @param supContStimata
     */
    public void setSupContStimata(java.lang.Float supContStimata) {
        this.supContStimata = supContStimata;
    }


    /**
     * Gets the superficieOccupata value for this CcostSiticontaminati.
     * 
     * @return superficieOccupata
     */
    public java.lang.Float getSuperficieOccupata() {
        return superficieOccupata;
    }


    /**
     * Sets the superficieOccupata value for this CcostSiticontaminati.
     * 
     * @param superficieOccupata
     */
    public void setSuperficieOccupata(java.lang.Float superficieOccupata) {
        this.superficieOccupata = superficieOccupata;
    }


    /**
     * Gets the tipoAbbandonoRifiuti value for this CcostSiticontaminati.
     * 
     * @return tipoAbbandonoRifiuti
     */
    public java.lang.String getTipoAbbandonoRifiuti() {
        return tipoAbbandonoRifiuti;
    }


    /**
     * Sets the tipoAbbandonoRifiuti value for this CcostSiticontaminati.
     * 
     * @param tipoAbbandonoRifiuti
     */
    public void setTipoAbbandonoRifiuti(java.lang.String tipoAbbandonoRifiuti) {
        this.tipoAbbandonoRifiuti = tipoAbbandonoRifiuti;
    }


    /**
     * Gets the tipoEventiAccidentali value for this CcostSiticontaminati.
     * 
     * @return tipoEventiAccidentali
     */
    public java.lang.String getTipoEventiAccidentali() {
        return tipoEventiAccidentali;
    }


    /**
     * Sets the tipoEventiAccidentali value for this CcostSiticontaminati.
     * 
     * @param tipoEventiAccidentali
     */
    public void setTipoEventiAccidentali(java.lang.String tipoEventiAccidentali) {
        this.tipoEventiAccidentali = tipoEventiAccidentali;
    }


    /**
     * Gets the tipoFalda value for this CcostSiticontaminati.
     * 
     * @return tipoFalda
     */
    public java.lang.String getTipoFalda() {
        return tipoFalda;
    }


    /**
     * Sets the tipoFalda value for this CcostSiticontaminati.
     * 
     * @param tipoFalda
     */
    public void setTipoFalda(java.lang.String tipoFalda) {
        this.tipoFalda = tipoFalda;
    }


    /**
     * Gets the tipoFalda2 value for this CcostSiticontaminati.
     * 
     * @return tipoFalda2
     */
    public java.lang.String getTipoFalda2() {
        return tipoFalda2;
    }


    /**
     * Sets the tipoFalda2 value for this CcostSiticontaminati.
     * 
     * @param tipoFalda2
     */
    public void setTipoFalda2(java.lang.String tipoFalda2) {
        this.tipoFalda2 = tipoFalda2;
    }


    /**
     * Gets the tipoFalda3 value for this CcostSiticontaminati.
     * 
     * @return tipoFalda3
     */
    public java.lang.String getTipoFalda3() {
        return tipoFalda3;
    }


    /**
     * Sets the tipoFalda3 value for this CcostSiticontaminati.
     * 
     * @param tipoFalda3
     */
    public void setTipoFalda3(java.lang.String tipoFalda3) {
        this.tipoFalda3 = tipoFalda3;
    }


    /**
     * Gets the tipoGestione value for this CcostSiticontaminati.
     * 
     * @return tipoGestione
     */
    public java.lang.String getTipoGestione() {
        return tipoGestione;
    }


    /**
     * Sets the tipoGestione value for this CcostSiticontaminati.
     * 
     * @param tipoGestione
     */
    public void setTipoGestione(java.lang.String tipoGestione) {
        this.tipoGestione = tipoGestione;
    }


    /**
     * Gets the tipoImpiantoTrattamento value for this CcostSiticontaminati.
     * 
     * @return tipoImpiantoTrattamento
     */
    public java.lang.String getTipoImpiantoTrattamento() {
        return tipoImpiantoTrattamento;
    }


    /**
     * Sets the tipoImpiantoTrattamento value for this CcostSiticontaminati.
     * 
     * @param tipoImpiantoTrattamento
     */
    public void setTipoImpiantoTrattamento(java.lang.String tipoImpiantoTrattamento) {
        this.tipoImpiantoTrattamento = tipoImpiantoTrattamento;
    }


    /**
     * Gets the tipoInt value for this CcostSiticontaminati.
     * 
     * @return tipoInt
     */
    public java.lang.String getTipoInt() {
        return tipoInt;
    }


    /**
     * Sets the tipoInt value for this CcostSiticontaminati.
     * 
     * @param tipoInt
     */
    public void setTipoInt(java.lang.String tipoInt) {
        this.tipoInt = tipoInt;
    }


    /**
     * Gets the tipoRifiuti value for this CcostSiticontaminati.
     * 
     * @return tipoRifiuti
     */
    public java.lang.String getTipoRifiuti() {
        return tipoRifiuti;
    }


    /**
     * Sets the tipoRifiuti value for this CcostSiticontaminati.
     * 
     * @param tipoRifiuti
     */
    public void setTipoRifiuti(java.lang.String tipoRifiuti) {
        this.tipoRifiuti = tipoRifiuti;
    }


    /**
     * Gets the tipoSversamenti value for this CcostSiticontaminati.
     * 
     * @return tipoSversamenti
     */
    public java.lang.String getTipoSversamenti() {
        return tipoSversamenti;
    }


    /**
     * Sets the tipoSversamenti value for this CcostSiticontaminati.
     * 
     * @param tipoSversamenti
     */
    public void setTipoSversamenti(java.lang.String tipoSversamenti) {
        this.tipoSversamenti = tipoSversamenti;
    }


    /**
     * Gets the vocTipoSitiCont value for this CcostSiticontaminati.
     * 
     * @return vocTipoSitiCont
     */
    public com.hyperborea.sira.ws.VocTipoSitiCont getVocTipoSitiCont() {
        return vocTipoSitiCont;
    }


    /**
     * Sets the vocTipoSitiCont value for this CcostSiticontaminati.
     * 
     * @param vocTipoSitiCont
     */
    public void setVocTipoSitiCont(com.hyperborea.sira.ws.VocTipoSitiCont vocTipoSitiCont) {
        this.vocTipoSitiCont = vocTipoSitiCont;
    }


    /**
     * Gets the volumeAccertato value for this CcostSiticontaminati.
     * 
     * @return volumeAccertato
     */
    public java.lang.Float getVolumeAccertato() {
        return volumeAccertato;
    }


    /**
     * Sets the volumeAccertato value for this CcostSiticontaminati.
     * 
     * @param volumeAccertato
     */
    public void setVolumeAccertato(java.lang.Float volumeAccertato) {
        this.volumeAccertato = volumeAccertato;
    }


    /**
     * Gets the volumeStimato value for this CcostSiticontaminati.
     * 
     * @return volumeStimato
     */
    public java.lang.Float getVolumeStimato() {
        return volumeStimato;
    }


    /**
     * Sets the volumeStimato value for this CcostSiticontaminati.
     * 
     * @param volumeStimato
     */
    public void setVolumeStimato(java.lang.Float volumeStimato) {
        this.volumeStimato = volumeStimato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostSiticontaminati)) return false;
        CcostSiticontaminati other = (CcostSiticontaminati) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.abbancamentiFiniNum==null && other.getAbbancamentiFiniNum()==null) || 
             (this.abbancamentiFiniNum!=null &&
              this.abbancamentiFiniNum.equals(other.getAbbancamentiFiniNum()))) &&
            ((this.abbancamentiFiniSup==null && other.getAbbancamentiFiniSup()==null) || 
             (this.abbancamentiFiniSup!=null &&
              this.abbancamentiFiniSup.equals(other.getAbbancamentiFiniSup()))) &&
            ((this.abbancamentiFiniVol==null && other.getAbbancamentiFiniVol()==null) || 
             (this.abbancamentiFiniVol!=null &&
              this.abbancamentiFiniVol.equals(other.getAbbancamentiFiniVol()))) &&
            ((this.accessibilitaControlli==null && other.getAccessibilitaControlli()==null) || 
             (this.accessibilitaControlli!=null &&
              this.accessibilitaControlli.equals(other.getAccessibilitaControlli()))) &&
            ((this.accessibilitaNonAutorizzati==null && other.getAccessibilitaNonAutorizzati()==null) || 
             (this.accessibilitaNonAutorizzati!=null &&
              this.accessibilitaNonAutorizzati.equals(other.getAccessibilitaNonAutorizzati()))) &&
            ((this.acqueSottVolumeEffettivo==null && other.getAcqueSottVolumeEffettivo()==null) || 
             (this.acqueSottVolumeEffettivo!=null &&
              this.acqueSottVolumeEffettivo.equals(other.getAcqueSottVolumeEffettivo()))) &&
            ((this.acqueSottVolumeProgetto==null && other.getAcqueSottVolumeProgetto()==null) || 
             (this.acqueSottVolumeProgetto!=null &&
              this.acqueSottVolumeProgetto.equals(other.getAcqueSottVolumeProgetto()))) &&
            ((this.acqueSupVolumeEffettivo==null && other.getAcqueSupVolumeEffettivo()==null) || 
             (this.acqueSupVolumeEffettivo!=null &&
              this.acqueSupVolumeEffettivo.equals(other.getAcqueSupVolumeEffettivo()))) &&
            ((this.acqueSupVolumeProgetto==null && other.getAcqueSupVolumeProgetto()==null) || 
             (this.acqueSupVolumeProgetto!=null &&
              this.acqueSupVolumeProgetto.equals(other.getAcqueSupVolumeProgetto()))) &&
            ((this.altezzaRifiuti==null && other.getAltezzaRifiuti()==null) || 
             (this.altezzaRifiuti!=null &&
              this.altezzaRifiuti.equals(other.getAltezzaRifiuti()))) &&
            ((this.area==null && other.getArea()==null) || 
             (this.area!=null &&
              this.area.equals(other.getArea()))) &&
            ((this.art18==null && other.getArt18()==null) || 
             (this.art18!=null &&
              this.art18.equals(other.getArt18()))) &&
            ((this.art7==null && other.getArt7()==null) || 
             (this.art7!=null &&
              this.art7.equals(other.getArt7()))) &&
            ((this.art8==null && other.getArt8()==null) || 
             (this.art8!=null &&
              this.art8.equals(other.getArt8()))) &&
            ((this.art9==null && other.getArt9()==null) || 
             (this.art9!=null &&
              this.art9.equals(other.getArt9()))) &&
            ((this.attEstrattivaCausa==null && other.getAttEstrattivaCausa()==null) || 
             (this.attEstrattivaCausa!=null &&
              this.attEstrattivaCausa.equals(other.getAttEstrattivaCausa()))) &&
            ((this.attEstrattivaPresenza==null && other.getAttEstrattivaPresenza()==null) || 
             (this.attEstrattivaPresenza!=null &&
              this.attEstrattivaPresenza.equals(other.getAttEstrattivaPresenza()))) &&
            ((this.attEstrattivaSorgente==null && other.getAttEstrattivaSorgente()==null) || 
             (this.attEstrattivaSorgente!=null &&
              this.attEstrattivaSorgente.equals(other.getAttEstrattivaSorgente()))) &&
            ((this.baciniFanghiNum==null && other.getBaciniFanghiNum()==null) || 
             (this.baciniFanghiNum!=null &&
              this.baciniFanghiNum.equals(other.getBaciniFanghiNum()))) &&
            ((this.baciniFanghiSup==null && other.getBaciniFanghiSup()==null) || 
             (this.baciniFanghiSup!=null &&
              this.baciniFanghiSup.equals(other.getBaciniFanghiSup()))) &&
            ((this.baciniFanghiVol==null && other.getBaciniFanghiVol()==null) || 
             (this.baciniFanghiVol!=null &&
              this.baciniFanghiVol.equals(other.getBaciniFanghiVol()))) &&
            ((this.bonifiches==null && other.getBonifiches()==null) || 
             (this.bonifiches!=null &&
              java.util.Arrays.equals(this.bonifiches, other.getBonifiches()))) &&
            ((this.causaRifiuti==null && other.getCausaRifiuti()==null) || 
             (this.causaRifiuti!=null &&
              this.causaRifiuti.equals(other.getCausaRifiuti()))) &&
            ((this.codAttivita==null && other.getCodAttivita()==null) || 
             (this.codAttivita!=null &&
              this.codAttivita.equals(other.getCodAttivita()))) &&
            ((this.codNazionale==null && other.getCodNazionale()==null) || 
             (this.codNazionale!=null &&
              this.codNazionale.equals(other.getCodNazionale()))) &&
            ((this.codRegionale==null && other.getCodRegionale()==null) || 
             (this.codRegionale!=null &&
              this.codRegionale.equals(other.getCodRegionale()))) &&
            ((this.dataArchiviazione==null && other.getDataArchiviazione()==null) || 
             (this.dataArchiviazione!=null &&
              this.dataArchiviazione.equals(other.getDataArchiviazione()))) &&
            ((this.dataRinuncia==null && other.getDataRinuncia()==null) || 
             (this.dataRinuncia!=null &&
              this.dataRinuncia.equals(other.getDataRinuncia()))) &&
            ((this.dataScadenza==null && other.getDataScadenza()==null) || 
             (this.dataScadenza!=null &&
              this.dataScadenza.equals(other.getDataScadenza()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.destUsoPrevalente==null && other.getDestUsoPrevalente()==null) || 
             (this.destUsoPrevalente!=null &&
              this.destUsoPrevalente.equals(other.getDestUsoPrevalente()))) &&
            ((this.destUsoProgetto==null && other.getDestUsoProgetto()==null) || 
             (this.destUsoProgetto!=null &&
              this.destUsoProgetto.equals(other.getDestUsoProgetto()))) &&
            ((this.discaricheMinerarieNum==null && other.getDiscaricheMinerarieNum()==null) || 
             (this.discaricheMinerarieNum!=null &&
              this.discaricheMinerarieNum.equals(other.getDiscaricheMinerarieNum()))) &&
            ((this.discaricheMinerarieSup==null && other.getDiscaricheMinerarieSup()==null) || 
             (this.discaricheMinerarieSup!=null &&
              this.discaricheMinerarieSup.equals(other.getDiscaricheMinerarieSup()))) &&
            ((this.discaricheMinerarieVol==null && other.getDiscaricheMinerarieVol()==null) || 
             (this.discaricheMinerarieVol!=null &&
              this.discaricheMinerarieVol.equals(other.getDiscaricheMinerarieVol()))) &&
            ((this.effettivamenteContaminato==null && other.getEffettivamenteContaminato()==null) || 
             (this.effettivamenteContaminato!=null &&
              this.effettivamenteContaminato.equals(other.getEffettivamenteContaminato()))) &&
            ((this.eventiAccidentaliSorgente==null && other.getEventiAccidentaliSorgente()==null) || 
             (this.eventiAccidentaliSorgente!=null &&
              this.eventiAccidentaliSorgente.equals(other.getEventiAccidentaliSorgente()))) &&
            ((this.eventiIncidentaliTipo==null && other.getEventiIncidentaliTipo()==null) || 
             (this.eventiIncidentaliTipo!=null &&
              this.eventiIncidentaliTipo.equals(other.getEventiIncidentaliTipo()))) &&
            ((this.fineEstr==null && other.getFineEstr()==null) || 
             (this.fineEstr!=null &&
              this.fineEstr.equals(other.getFineEstr()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.impiantoTrattamento==null && other.getImpiantoTrattamento()==null) || 
             (this.impiantoTrattamento!=null &&
              this.impiantoTrattamento.equals(other.getImpiantoTrattamento()))) &&
            ((this.importo==null && other.getImporto()==null) || 
             (this.importo!=null &&
              this.importo.equals(other.getImporto()))) &&
            ((this.inizEstr==null && other.getInizEstr()==null) || 
             (this.inizEstr!=null &&
              this.inizEstr.equals(other.getInizEstr()))) &&
            ((this.iscrittoAnagrafe==null && other.getIscrittoAnagrafe()==null) || 
             (this.iscrittoAnagrafe!=null &&
              this.iscrittoAnagrafe.equals(other.getIscrittoAnagrafe()))) &&
            ((this.matriciContaminates==null && other.getMatriciContaminates()==null) || 
             (this.matriciContaminates!=null &&
              java.util.Arrays.equals(this.matriciContaminates, other.getMatriciContaminates()))) &&
            ((this.messaInSicurezzaMineraria==null && other.getMessaInSicurezzaMineraria()==null) || 
             (this.messaInSicurezzaMineraria!=null &&
              this.messaInSicurezzaMineraria.equals(other.getMessaInSicurezzaMineraria()))) &&
            ((this.mineraliColtivati==null && other.getMineraliColtivati()==null) || 
             (this.mineraliColtivati!=null &&
              this.mineraliColtivati.equals(other.getMineraliColtivati()))) &&
            ((this.naturaAttivita==null && other.getNaturaAttivita()==null) || 
             (this.naturaAttivita!=null &&
              this.naturaAttivita.equals(other.getNaturaAttivita()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.notizie==null && other.getNotizie()==null) || 
             (this.notizie!=null &&
              this.notizie.equals(other.getNotizie()))) &&
            ((this.numImpiantiTrattamento==null && other.getNumImpiantiTrattamento()==null) || 
             (this.numImpiantiTrattamento!=null &&
              this.numImpiantiTrattamento.equals(other.getNumImpiantiTrattamento()))) &&
            ((this.posizioneAmministrativa==null && other.getPosizioneAmministrativa()==null) || 
             (this.posizioneAmministrativa!=null &&
              this.posizioneAmministrativa.equals(other.getPosizioneAmministrativa()))) &&
            ((this.priorita==null && other.getPriorita()==null) || 
             (this.priorita!=null &&
              this.priorita.equals(other.getPriorita()))) &&
            ((this.processoProduttivo==null && other.getProcessoProduttivo()==null) || 
             (this.processoProduttivo!=null &&
              this.processoProduttivo.equals(other.getProcessoProduttivo()))) &&
            ((this.relSiticontOstMineraris==null && other.getRelSiticontOstMineraris()==null) || 
             (this.relSiticontOstMineraris!=null &&
              java.util.Arrays.equals(this.relSiticontOstMineraris, other.getRelSiticontOstMineraris()))) &&
            ((this.scaviACieloApertoNum==null && other.getScaviACieloApertoNum()==null) || 
             (this.scaviACieloApertoNum!=null &&
              this.scaviACieloApertoNum.equals(other.getScaviACieloApertoNum()))) &&
            ((this.scaviACieloApertoSup==null && other.getScaviACieloApertoSup()==null) || 
             (this.scaviACieloApertoSup!=null &&
              this.scaviACieloApertoSup.equals(other.getScaviACieloApertoSup()))) &&
            ((this.scaviACieloApertoVol==null && other.getScaviACieloApertoVol()==null) || 
             (this.scaviACieloApertoVol!=null &&
              this.scaviACieloApertoVol.equals(other.getScaviACieloApertoVol()))) &&
            ((this.sitoComunitario==null && other.getSitoComunitario()==null) || 
             (this.sitoComunitario!=null &&
              this.sitoComunitario.equals(other.getSitoComunitario()))) &&
            ((this.sitoDiIntNaz==null && other.getSitoDiIntNaz()==null) || 
             (this.sitoDiIntNaz!=null &&
              this.sitoDiIntNaz.equals(other.getSitoDiIntNaz()))) &&
            ((this.sitoMinerario==null && other.getSitoMinerario()==null) || 
             (this.sitoMinerario!=null &&
              this.sitoMinerario.equals(other.getSitoMinerario()))) &&
            ((this.sorgInqAccidentali==null && other.getSorgInqAccidentali()==null) || 
             (this.sorgInqAccidentali!=null &&
              this.sorgInqAccidentali.equals(other.getSorgInqAccidentali()))) &&
            ((this.sorgInqEventi==null && other.getSorgInqEventi()==null) || 
             (this.sorgInqEventi!=null &&
              this.sorgInqEventi.equals(other.getSorgInqEventi()))) &&
            ((this.sorgInqGestione==null && other.getSorgInqGestione()==null) || 
             (this.sorgInqGestione!=null &&
              this.sorgInqGestione.equals(other.getSorgInqGestione()))) &&
            ((this.sorgInqRifiuti==null && other.getSorgInqRifiuti()==null) || 
             (this.sorgInqRifiuti!=null &&
              this.sorgInqRifiuti.equals(other.getSorgInqRifiuti()))) &&
            ((this.sorgInqSversamenti==null && other.getSorgInqSversamenti()==null) || 
             (this.sorgInqSversamenti!=null &&
              this.sorgInqSversamenti.equals(other.getSorgInqSversamenti()))) &&
            ((this.sorgenteEventi==null && other.getSorgenteEventi()==null) || 
             (this.sorgenteEventi!=null &&
              this.sorgenteEventi.equals(other.getSorgenteEventi()))) &&
            ((this.sorgenteGestione==null && other.getSorgenteGestione()==null) || 
             (this.sorgenteGestione!=null &&
              this.sorgenteGestione.equals(other.getSorgenteGestione()))) &&
            ((this.sorgenteRifiuti==null && other.getSorgenteRifiuti()==null) || 
             (this.sorgenteRifiuti!=null &&
              this.sorgenteRifiuti.equals(other.getSorgenteRifiuti()))) &&
            ((this.statoAttivita==null && other.getStatoAttivita()==null) || 
             (this.statoAttivita!=null &&
              this.statoAttivita.equals(other.getStatoAttivita()))) &&
            ((this.statoContam==null && other.getStatoContam()==null) || 
             (this.statoContam!=null &&
              this.statoContam.equals(other.getStatoContam()))) &&
            ((this.stimaSoggFalda==null && other.getStimaSoggFalda()==null) || 
             (this.stimaSoggFalda!=null &&
              this.stimaSoggFalda.equals(other.getStimaSoggFalda()))) &&
            ((this.stimaSoggFalda2==null && other.getStimaSoggFalda2()==null) || 
             (this.stimaSoggFalda2!=null &&
              this.stimaSoggFalda2.equals(other.getStimaSoggFalda2()))) &&
            ((this.stimaSoggFalda3==null && other.getStimaSoggFalda3()==null) || 
             (this.stimaSoggFalda3!=null &&
              this.stimaSoggFalda3.equals(other.getStimaSoggFalda3()))) &&
            ((this.suoloSottosuoloVolEffettivo==null && other.getSuoloSottosuoloVolEffettivo()==null) || 
             (this.suoloSottosuoloVolEffettivo!=null &&
              this.suoloSottosuoloVolEffettivo.equals(other.getSuoloSottosuoloVolEffettivo()))) &&
            ((this.suoloSottosuoloVolProgetto==null && other.getSuoloSottosuoloVolProgetto()==null) || 
             (this.suoloSottosuoloVolProgetto!=null &&
              this.suoloSottosuoloVolProgetto.equals(other.getSuoloSottosuoloVolProgetto()))) &&
            ((this.suoloSuperficieEffettiva==null && other.getSuoloSuperficieEffettiva()==null) || 
             (this.suoloSuperficieEffettiva!=null &&
              this.suoloSuperficieEffettiva.equals(other.getSuoloSuperficieEffettiva()))) &&
            ((this.suoloSuperficieProgetto==null && other.getSuoloSuperficieProgetto()==null) || 
             (this.suoloSuperficieProgetto!=null &&
              this.suoloSuperficieProgetto.equals(other.getSuoloSuperficieProgetto()))) &&
            ((this.supContAccertata==null && other.getSupContAccertata()==null) || 
             (this.supContAccertata!=null &&
              this.supContAccertata.equals(other.getSupContAccertata()))) &&
            ((this.supContStimata==null && other.getSupContStimata()==null) || 
             (this.supContStimata!=null &&
              this.supContStimata.equals(other.getSupContStimata()))) &&
            ((this.superficieOccupata==null && other.getSuperficieOccupata()==null) || 
             (this.superficieOccupata!=null &&
              this.superficieOccupata.equals(other.getSuperficieOccupata()))) &&
            ((this.tipoAbbandonoRifiuti==null && other.getTipoAbbandonoRifiuti()==null) || 
             (this.tipoAbbandonoRifiuti!=null &&
              this.tipoAbbandonoRifiuti.equals(other.getTipoAbbandonoRifiuti()))) &&
            ((this.tipoEventiAccidentali==null && other.getTipoEventiAccidentali()==null) || 
             (this.tipoEventiAccidentali!=null &&
              this.tipoEventiAccidentali.equals(other.getTipoEventiAccidentali()))) &&
            ((this.tipoFalda==null && other.getTipoFalda()==null) || 
             (this.tipoFalda!=null &&
              this.tipoFalda.equals(other.getTipoFalda()))) &&
            ((this.tipoFalda2==null && other.getTipoFalda2()==null) || 
             (this.tipoFalda2!=null &&
              this.tipoFalda2.equals(other.getTipoFalda2()))) &&
            ((this.tipoFalda3==null && other.getTipoFalda3()==null) || 
             (this.tipoFalda3!=null &&
              this.tipoFalda3.equals(other.getTipoFalda3()))) &&
            ((this.tipoGestione==null && other.getTipoGestione()==null) || 
             (this.tipoGestione!=null &&
              this.tipoGestione.equals(other.getTipoGestione()))) &&
            ((this.tipoImpiantoTrattamento==null && other.getTipoImpiantoTrattamento()==null) || 
             (this.tipoImpiantoTrattamento!=null &&
              this.tipoImpiantoTrattamento.equals(other.getTipoImpiantoTrattamento()))) &&
            ((this.tipoInt==null && other.getTipoInt()==null) || 
             (this.tipoInt!=null &&
              this.tipoInt.equals(other.getTipoInt()))) &&
            ((this.tipoRifiuti==null && other.getTipoRifiuti()==null) || 
             (this.tipoRifiuti!=null &&
              this.tipoRifiuti.equals(other.getTipoRifiuti()))) &&
            ((this.tipoSversamenti==null && other.getTipoSversamenti()==null) || 
             (this.tipoSversamenti!=null &&
              this.tipoSversamenti.equals(other.getTipoSversamenti()))) &&
            ((this.vocTipoSitiCont==null && other.getVocTipoSitiCont()==null) || 
             (this.vocTipoSitiCont!=null &&
              this.vocTipoSitiCont.equals(other.getVocTipoSitiCont()))) &&
            ((this.volumeAccertato==null && other.getVolumeAccertato()==null) || 
             (this.volumeAccertato!=null &&
              this.volumeAccertato.equals(other.getVolumeAccertato()))) &&
            ((this.volumeStimato==null && other.getVolumeStimato()==null) || 
             (this.volumeStimato!=null &&
              this.volumeStimato.equals(other.getVolumeStimato())));
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
        if (getAbbancamentiFiniNum() != null) {
            _hashCode += getAbbancamentiFiniNum().hashCode();
        }
        if (getAbbancamentiFiniSup() != null) {
            _hashCode += getAbbancamentiFiniSup().hashCode();
        }
        if (getAbbancamentiFiniVol() != null) {
            _hashCode += getAbbancamentiFiniVol().hashCode();
        }
        if (getAccessibilitaControlli() != null) {
            _hashCode += getAccessibilitaControlli().hashCode();
        }
        if (getAccessibilitaNonAutorizzati() != null) {
            _hashCode += getAccessibilitaNonAutorizzati().hashCode();
        }
        if (getAcqueSottVolumeEffettivo() != null) {
            _hashCode += getAcqueSottVolumeEffettivo().hashCode();
        }
        if (getAcqueSottVolumeProgetto() != null) {
            _hashCode += getAcqueSottVolumeProgetto().hashCode();
        }
        if (getAcqueSupVolumeEffettivo() != null) {
            _hashCode += getAcqueSupVolumeEffettivo().hashCode();
        }
        if (getAcqueSupVolumeProgetto() != null) {
            _hashCode += getAcqueSupVolumeProgetto().hashCode();
        }
        if (getAltezzaRifiuti() != null) {
            _hashCode += getAltezzaRifiuti().hashCode();
        }
        if (getArea() != null) {
            _hashCode += getArea().hashCode();
        }
        if (getArt18() != null) {
            _hashCode += getArt18().hashCode();
        }
        if (getArt7() != null) {
            _hashCode += getArt7().hashCode();
        }
        if (getArt8() != null) {
            _hashCode += getArt8().hashCode();
        }
        if (getArt9() != null) {
            _hashCode += getArt9().hashCode();
        }
        if (getAttEstrattivaCausa() != null) {
            _hashCode += getAttEstrattivaCausa().hashCode();
        }
        if (getAttEstrattivaPresenza() != null) {
            _hashCode += getAttEstrattivaPresenza().hashCode();
        }
        if (getAttEstrattivaSorgente() != null) {
            _hashCode += getAttEstrattivaSorgente().hashCode();
        }
        if (getBaciniFanghiNum() != null) {
            _hashCode += getBaciniFanghiNum().hashCode();
        }
        if (getBaciniFanghiSup() != null) {
            _hashCode += getBaciniFanghiSup().hashCode();
        }
        if (getBaciniFanghiVol() != null) {
            _hashCode += getBaciniFanghiVol().hashCode();
        }
        if (getBonifiches() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBonifiches());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBonifiches(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCausaRifiuti() != null) {
            _hashCode += getCausaRifiuti().hashCode();
        }
        if (getCodAttivita() != null) {
            _hashCode += getCodAttivita().hashCode();
        }
        if (getCodNazionale() != null) {
            _hashCode += getCodNazionale().hashCode();
        }
        if (getCodRegionale() != null) {
            _hashCode += getCodRegionale().hashCode();
        }
        if (getDataArchiviazione() != null) {
            _hashCode += getDataArchiviazione().hashCode();
        }
        if (getDataRinuncia() != null) {
            _hashCode += getDataRinuncia().hashCode();
        }
        if (getDataScadenza() != null) {
            _hashCode += getDataScadenza().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getDestUsoPrevalente() != null) {
            _hashCode += getDestUsoPrevalente().hashCode();
        }
        if (getDestUsoProgetto() != null) {
            _hashCode += getDestUsoProgetto().hashCode();
        }
        if (getDiscaricheMinerarieNum() != null) {
            _hashCode += getDiscaricheMinerarieNum().hashCode();
        }
        if (getDiscaricheMinerarieSup() != null) {
            _hashCode += getDiscaricheMinerarieSup().hashCode();
        }
        if (getDiscaricheMinerarieVol() != null) {
            _hashCode += getDiscaricheMinerarieVol().hashCode();
        }
        if (getEffettivamenteContaminato() != null) {
            _hashCode += getEffettivamenteContaminato().hashCode();
        }
        if (getEventiAccidentaliSorgente() != null) {
            _hashCode += getEventiAccidentaliSorgente().hashCode();
        }
        if (getEventiIncidentaliTipo() != null) {
            _hashCode += getEventiIncidentaliTipo().hashCode();
        }
        if (getFineEstr() != null) {
            _hashCode += getFineEstr().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getImpiantoTrattamento() != null) {
            _hashCode += getImpiantoTrattamento().hashCode();
        }
        if (getImporto() != null) {
            _hashCode += getImporto().hashCode();
        }
        if (getInizEstr() != null) {
            _hashCode += getInizEstr().hashCode();
        }
        if (getIscrittoAnagrafe() != null) {
            _hashCode += getIscrittoAnagrafe().hashCode();
        }
        if (getMatriciContaminates() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMatriciContaminates());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMatriciContaminates(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMessaInSicurezzaMineraria() != null) {
            _hashCode += getMessaInSicurezzaMineraria().hashCode();
        }
        if (getMineraliColtivati() != null) {
            _hashCode += getMineraliColtivati().hashCode();
        }
        if (getNaturaAttivita() != null) {
            _hashCode += getNaturaAttivita().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getNotizie() != null) {
            _hashCode += getNotizie().hashCode();
        }
        if (getNumImpiantiTrattamento() != null) {
            _hashCode += getNumImpiantiTrattamento().hashCode();
        }
        if (getPosizioneAmministrativa() != null) {
            _hashCode += getPosizioneAmministrativa().hashCode();
        }
        if (getPriorita() != null) {
            _hashCode += getPriorita().hashCode();
        }
        if (getProcessoProduttivo() != null) {
            _hashCode += getProcessoProduttivo().hashCode();
        }
        if (getRelSiticontOstMineraris() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRelSiticontOstMineraris());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRelSiticontOstMineraris(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getScaviACieloApertoNum() != null) {
            _hashCode += getScaviACieloApertoNum().hashCode();
        }
        if (getScaviACieloApertoSup() != null) {
            _hashCode += getScaviACieloApertoSup().hashCode();
        }
        if (getScaviACieloApertoVol() != null) {
            _hashCode += getScaviACieloApertoVol().hashCode();
        }
        if (getSitoComunitario() != null) {
            _hashCode += getSitoComunitario().hashCode();
        }
        if (getSitoDiIntNaz() != null) {
            _hashCode += getSitoDiIntNaz().hashCode();
        }
        if (getSitoMinerario() != null) {
            _hashCode += getSitoMinerario().hashCode();
        }
        if (getSorgInqAccidentali() != null) {
            _hashCode += getSorgInqAccidentali().hashCode();
        }
        if (getSorgInqEventi() != null) {
            _hashCode += getSorgInqEventi().hashCode();
        }
        if (getSorgInqGestione() != null) {
            _hashCode += getSorgInqGestione().hashCode();
        }
        if (getSorgInqRifiuti() != null) {
            _hashCode += getSorgInqRifiuti().hashCode();
        }
        if (getSorgInqSversamenti() != null) {
            _hashCode += getSorgInqSversamenti().hashCode();
        }
        if (getSorgenteEventi() != null) {
            _hashCode += getSorgenteEventi().hashCode();
        }
        if (getSorgenteGestione() != null) {
            _hashCode += getSorgenteGestione().hashCode();
        }
        if (getSorgenteRifiuti() != null) {
            _hashCode += getSorgenteRifiuti().hashCode();
        }
        if (getStatoAttivita() != null) {
            _hashCode += getStatoAttivita().hashCode();
        }
        if (getStatoContam() != null) {
            _hashCode += getStatoContam().hashCode();
        }
        if (getStimaSoggFalda() != null) {
            _hashCode += getStimaSoggFalda().hashCode();
        }
        if (getStimaSoggFalda2() != null) {
            _hashCode += getStimaSoggFalda2().hashCode();
        }
        if (getStimaSoggFalda3() != null) {
            _hashCode += getStimaSoggFalda3().hashCode();
        }
        if (getSuoloSottosuoloVolEffettivo() != null) {
            _hashCode += getSuoloSottosuoloVolEffettivo().hashCode();
        }
        if (getSuoloSottosuoloVolProgetto() != null) {
            _hashCode += getSuoloSottosuoloVolProgetto().hashCode();
        }
        if (getSuoloSuperficieEffettiva() != null) {
            _hashCode += getSuoloSuperficieEffettiva().hashCode();
        }
        if (getSuoloSuperficieProgetto() != null) {
            _hashCode += getSuoloSuperficieProgetto().hashCode();
        }
        if (getSupContAccertata() != null) {
            _hashCode += getSupContAccertata().hashCode();
        }
        if (getSupContStimata() != null) {
            _hashCode += getSupContStimata().hashCode();
        }
        if (getSuperficieOccupata() != null) {
            _hashCode += getSuperficieOccupata().hashCode();
        }
        if (getTipoAbbandonoRifiuti() != null) {
            _hashCode += getTipoAbbandonoRifiuti().hashCode();
        }
        if (getTipoEventiAccidentali() != null) {
            _hashCode += getTipoEventiAccidentali().hashCode();
        }
        if (getTipoFalda() != null) {
            _hashCode += getTipoFalda().hashCode();
        }
        if (getTipoFalda2() != null) {
            _hashCode += getTipoFalda2().hashCode();
        }
        if (getTipoFalda3() != null) {
            _hashCode += getTipoFalda3().hashCode();
        }
        if (getTipoGestione() != null) {
            _hashCode += getTipoGestione().hashCode();
        }
        if (getTipoImpiantoTrattamento() != null) {
            _hashCode += getTipoImpiantoTrattamento().hashCode();
        }
        if (getTipoInt() != null) {
            _hashCode += getTipoInt().hashCode();
        }
        if (getTipoRifiuti() != null) {
            _hashCode += getTipoRifiuti().hashCode();
        }
        if (getTipoSversamenti() != null) {
            _hashCode += getTipoSversamenti().hashCode();
        }
        if (getVocTipoSitiCont() != null) {
            _hashCode += getVocTipoSitiCont().hashCode();
        }
        if (getVolumeAccertato() != null) {
            _hashCode += getVolumeAccertato().hashCode();
        }
        if (getVolumeStimato() != null) {
            _hashCode += getVolumeStimato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostSiticontaminati.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSiticontaminati"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("abbancamentiFiniNum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "abbancamentiFiniNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("abbancamentiFiniSup");
        elemField.setXmlName(new javax.xml.namespace.QName("", "abbancamentiFiniSup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("abbancamentiFiniVol");
        elemField.setXmlName(new javax.xml.namespace.QName("", "abbancamentiFiniVol"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessibilitaControlli");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accessibilitaControlli"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessibilitaNonAutorizzati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accessibilitaNonAutorizzati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acqueSottVolumeEffettivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "acqueSottVolumeEffettivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acqueSottVolumeProgetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "acqueSottVolumeProgetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acqueSupVolumeEffettivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "acqueSupVolumeEffettivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acqueSupVolumeProgetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "acqueSupVolumeProgetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altezzaRifiuti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "altezzaRifiuti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("area");
        elemField.setXmlName(new javax.xml.namespace.QName("", "area"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("art18");
        elemField.setXmlName(new javax.xml.namespace.QName("", "art18"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("art7");
        elemField.setXmlName(new javax.xml.namespace.QName("", "art7"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("art8");
        elemField.setXmlName(new javax.xml.namespace.QName("", "art8"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("art9");
        elemField.setXmlName(new javax.xml.namespace.QName("", "art9"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attEstrattivaCausa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attEstrattivaCausa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attEstrattivaPresenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attEstrattivaPresenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attEstrattivaSorgente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attEstrattivaSorgente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("baciniFanghiNum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "baciniFanghiNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("baciniFanghiSup");
        elemField.setXmlName(new javax.xml.namespace.QName("", "baciniFanghiSup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("baciniFanghiVol");
        elemField.setXmlName(new javax.xml.namespace.QName("", "baciniFanghiVol"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonifiches");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bonifiches"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "bonifiche"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("causaRifiuti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "causaRifiuti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codAttivita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codAttivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codNazionale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codNazionale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codRegionale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codRegionale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataArchiviazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataArchiviazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataRinuncia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataRinuncia"));
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
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destUsoPrevalente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destUsoPrevalente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destUsoProgetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destUsoProgetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("discaricheMinerarieNum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "discaricheMinerarieNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("discaricheMinerarieSup");
        elemField.setXmlName(new javax.xml.namespace.QName("", "discaricheMinerarieSup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("discaricheMinerarieVol");
        elemField.setXmlName(new javax.xml.namespace.QName("", "discaricheMinerarieVol"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effettivamenteContaminato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "effettivamenteContaminato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventiAccidentaliSorgente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eventiAccidentaliSorgente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventiIncidentaliTipo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eventiIncidentaliTipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fineEstr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fineEstr"));
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
        elemField.setFieldName("impiantoTrattamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "impiantoTrattamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inizEstr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inizEstr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("iscrittoAnagrafe");
        elemField.setXmlName(new javax.xml.namespace.QName("", "iscrittoAnagrafe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matriciContaminates");
        elemField.setXmlName(new javax.xml.namespace.QName("", "matriciContaminates"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "matriciContaminate"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messaInSicurezzaMineraria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "messaInSicurezzaMineraria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mineraliColtivati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mineraliColtivati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("naturaAttivita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "naturaAttivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("notizie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "notizie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numImpiantiTrattamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numImpiantiTrattamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("posizioneAmministrativa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "posizioneAmministrativa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priorita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "priorita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processoProduttivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "processoProduttivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("relSiticontOstMineraris");
        elemField.setXmlName(new javax.xml.namespace.QName("", "relSiticontOstMineraris"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relSiticontOstMinerari"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scaviACieloApertoNum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scaviACieloApertoNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scaviACieloApertoSup");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scaviACieloApertoSup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scaviACieloApertoVol");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scaviACieloApertoVol"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sitoComunitario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sitoComunitario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sitoDiIntNaz");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sitoDiIntNaz"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sitoMinerario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sitoMinerario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sorgInqAccidentali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sorgInqAccidentali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sorgInqEventi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sorgInqEventi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sorgInqGestione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sorgInqGestione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sorgInqRifiuti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sorgInqRifiuti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sorgInqSversamenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sorgInqSversamenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sorgenteEventi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sorgenteEventi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sorgenteGestione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sorgenteGestione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sorgenteRifiuti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sorgenteRifiuti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoAttivita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statoAttivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoContam");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statoContam"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stimaSoggFalda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stimaSoggFalda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stimaSoggFalda2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stimaSoggFalda2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stimaSoggFalda3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stimaSoggFalda3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suoloSottosuoloVolEffettivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "suoloSottosuoloVolEffettivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suoloSottosuoloVolProgetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "suoloSottosuoloVolProgetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suoloSuperficieEffettiva");
        elemField.setXmlName(new javax.xml.namespace.QName("", "suoloSuperficieEffettiva"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suoloSuperficieProgetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "suoloSuperficieProgetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supContAccertata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "supContAccertata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supContStimata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "supContStimata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieOccupata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieOccupata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoAbbandonoRifiuti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoAbbandonoRifiuti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoEventiAccidentali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoEventiAccidentali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoFalda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoFalda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoFalda2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoFalda2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoFalda3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoFalda3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoGestione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoGestione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoImpiantoTrattamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoImpiantoTrattamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoInt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoInt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoRifiuti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoRifiuti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoSversamenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoSversamenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoSitiCont");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoSitiCont"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoSitiCont"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeAccertato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeAccertato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeStimato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeStimato"));
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
