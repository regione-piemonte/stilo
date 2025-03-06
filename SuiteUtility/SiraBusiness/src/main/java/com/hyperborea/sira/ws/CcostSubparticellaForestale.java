/**
 * CcostSubparticellaForestale.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostSubparticellaForestale  implements java.io.Serializable {
    private java.lang.Float altezzaDominante;

    private java.lang.Float altezzaMedia;

    private java.lang.Integer annoInizioTitolo;

    private java.lang.Integer arbustetiBoschiPascolati;

    private java.lang.Float classeProvvigInf;

    private java.lang.Float classeProvvigSup;

    private java.lang.String codice;

    private java.lang.Float coefficienteCoperturaArborea;

    private com.hyperborea.sira.ws.CompSpecSubparticella[] compSpecSubparticellas;

    private java.lang.Integer consuetudini;

    private com.hyperborea.sira.ws.DanniSubparticella[] danniSubparticellas;

    private java.lang.Float diametroMedio;

    private java.lang.Integer durataTitolo;

    private java.lang.Integer enfiteusi;

    private java.lang.String estremiTitolo;

    private java.lang.Integer etaPrevalente;

    private java.lang.Integer fidePascolo;

    private java.lang.Integer idCcost;

    private java.lang.String interventiRecenti;

    private java.lang.Float numMedioPolloniCeppaia;

    private java.lang.Float percPrimaSpecie;

    private java.lang.Float percPrimaSpecieRinn;

    private java.lang.Float percSecondaSpecie;

    private java.lang.Float percSecondaSpecieRinn;

    private java.lang.Integer presenzaGravami;

    private java.lang.Integer presenzaServitu;

    private com.hyperborea.sira.ws.Specie primaSpecie;

    private com.hyperborea.sira.ws.Specie primaSpecieRinn;

    private java.lang.Float primoTurnoMatricine;

    private java.lang.Integer rimbBuche;

    private java.lang.Float rimbSestoImpSulleFile;

    private java.lang.Float rimbSestoImpTraFile;

    private com.hyperborea.sira.ws.Specie secondaSpecie;

    private com.hyperborea.sira.ws.Specie secondaSpecieRinn;

    private java.lang.Float secondoTurnoMatricine;

    private java.lang.Float superficie;

    private java.lang.Float tarePercentuale;

    private java.lang.Float tareSuperficie;

    private java.lang.Float terzoTurnoMatricine;

    private java.lang.Integer usiCivici;

    private com.hyperborea.sira.ws.VocEfAbbRinnFustaie vocEfAbbRinnFustaie;

    private com.hyperborea.sira.ws.VocEfAccidentalita vocEfAccidentalita;

    private com.hyperborea.sira.ws.VocEfClasseEta vocEfClasseEta;

    private com.hyperborea.sira.ws.VocEfDistrMatricinatura vocEfDistrMatricinatura;

    private com.hyperborea.sira.ws.VocEfEntitaMatricinatura vocEfEntitaMatricinatura;

    private com.hyperborea.sira.ws.VocEfFunzionePrevalente vocEfFunzionePrevalente;

    private com.hyperborea.sira.ws.VocEfGEvolCopForestale vocEfGEvolCopForestale;

    private com.hyperborea.sira.ws.VocEfModelloCombustibile vocEfModelloCombustibile;

    private com.hyperborea.sira.ws.VocEfNumCeppaieEttaro vocEfNumCeppaieEttaro;

    private com.hyperborea.sira.ws.VocEfOrSelvicolturale vocEfOrSelvicolturale;

    private com.hyperborea.sira.ws.VocEfOrigineBosco vocEfOrigineBosco;

    private com.hyperborea.sira.ws.VocEfPietrosita vocEfPietrosita;

    private com.hyperborea.sira.ws.VocEfProfonditaSuolo vocEfProfonditaSuolo;

    private com.hyperborea.sira.ws.VocEfRimbPrepTerreno vocEfRimbPrepTerreno;

    private com.hyperborea.sira.ws.VocEfRocciosita vocEfRocciosita;

    private com.hyperborea.sira.ws.VocEfStatoVegetativoRinn vocEfStatoVegetativoRinn;

    private com.hyperborea.sira.ws.VocEfTipoColturale vocEfTipoColturale;

    private com.hyperborea.sira.ws.VocEfTipoCopForestale vocEfTipoCopForestale;

    private com.hyperborea.sira.ws.VocEfTipologiaUsoSuolo vocEfTipologiaUsoSuolo;

    public CcostSubparticellaForestale() {
    }

    public CcostSubparticellaForestale(
           java.lang.Float altezzaDominante,
           java.lang.Float altezzaMedia,
           java.lang.Integer annoInizioTitolo,
           java.lang.Integer arbustetiBoschiPascolati,
           java.lang.Float classeProvvigInf,
           java.lang.Float classeProvvigSup,
           java.lang.String codice,
           java.lang.Float coefficienteCoperturaArborea,
           com.hyperborea.sira.ws.CompSpecSubparticella[] compSpecSubparticellas,
           java.lang.Integer consuetudini,
           com.hyperborea.sira.ws.DanniSubparticella[] danniSubparticellas,
           java.lang.Float diametroMedio,
           java.lang.Integer durataTitolo,
           java.lang.Integer enfiteusi,
           java.lang.String estremiTitolo,
           java.lang.Integer etaPrevalente,
           java.lang.Integer fidePascolo,
           java.lang.Integer idCcost,
           java.lang.String interventiRecenti,
           java.lang.Float numMedioPolloniCeppaia,
           java.lang.Float percPrimaSpecie,
           java.lang.Float percPrimaSpecieRinn,
           java.lang.Float percSecondaSpecie,
           java.lang.Float percSecondaSpecieRinn,
           java.lang.Integer presenzaGravami,
           java.lang.Integer presenzaServitu,
           com.hyperborea.sira.ws.Specie primaSpecie,
           com.hyperborea.sira.ws.Specie primaSpecieRinn,
           java.lang.Float primoTurnoMatricine,
           java.lang.Integer rimbBuche,
           java.lang.Float rimbSestoImpSulleFile,
           java.lang.Float rimbSestoImpTraFile,
           com.hyperborea.sira.ws.Specie secondaSpecie,
           com.hyperborea.sira.ws.Specie secondaSpecieRinn,
           java.lang.Float secondoTurnoMatricine,
           java.lang.Float superficie,
           java.lang.Float tarePercentuale,
           java.lang.Float tareSuperficie,
           java.lang.Float terzoTurnoMatricine,
           java.lang.Integer usiCivici,
           com.hyperborea.sira.ws.VocEfAbbRinnFustaie vocEfAbbRinnFustaie,
           com.hyperborea.sira.ws.VocEfAccidentalita vocEfAccidentalita,
           com.hyperborea.sira.ws.VocEfClasseEta vocEfClasseEta,
           com.hyperborea.sira.ws.VocEfDistrMatricinatura vocEfDistrMatricinatura,
           com.hyperborea.sira.ws.VocEfEntitaMatricinatura vocEfEntitaMatricinatura,
           com.hyperborea.sira.ws.VocEfFunzionePrevalente vocEfFunzionePrevalente,
           com.hyperborea.sira.ws.VocEfGEvolCopForestale vocEfGEvolCopForestale,
           com.hyperborea.sira.ws.VocEfModelloCombustibile vocEfModelloCombustibile,
           com.hyperborea.sira.ws.VocEfNumCeppaieEttaro vocEfNumCeppaieEttaro,
           com.hyperborea.sira.ws.VocEfOrSelvicolturale vocEfOrSelvicolturale,
           com.hyperborea.sira.ws.VocEfOrigineBosco vocEfOrigineBosco,
           com.hyperborea.sira.ws.VocEfPietrosita vocEfPietrosita,
           com.hyperborea.sira.ws.VocEfProfonditaSuolo vocEfProfonditaSuolo,
           com.hyperborea.sira.ws.VocEfRimbPrepTerreno vocEfRimbPrepTerreno,
           com.hyperborea.sira.ws.VocEfRocciosita vocEfRocciosita,
           com.hyperborea.sira.ws.VocEfStatoVegetativoRinn vocEfStatoVegetativoRinn,
           com.hyperborea.sira.ws.VocEfTipoColturale vocEfTipoColturale,
           com.hyperborea.sira.ws.VocEfTipoCopForestale vocEfTipoCopForestale,
           com.hyperborea.sira.ws.VocEfTipologiaUsoSuolo vocEfTipologiaUsoSuolo) {
           this.altezzaDominante = altezzaDominante;
           this.altezzaMedia = altezzaMedia;
           this.annoInizioTitolo = annoInizioTitolo;
           this.arbustetiBoschiPascolati = arbustetiBoschiPascolati;
           this.classeProvvigInf = classeProvvigInf;
           this.classeProvvigSup = classeProvvigSup;
           this.codice = codice;
           this.coefficienteCoperturaArborea = coefficienteCoperturaArborea;
           this.compSpecSubparticellas = compSpecSubparticellas;
           this.consuetudini = consuetudini;
           this.danniSubparticellas = danniSubparticellas;
           this.diametroMedio = diametroMedio;
           this.durataTitolo = durataTitolo;
           this.enfiteusi = enfiteusi;
           this.estremiTitolo = estremiTitolo;
           this.etaPrevalente = etaPrevalente;
           this.fidePascolo = fidePascolo;
           this.idCcost = idCcost;
           this.interventiRecenti = interventiRecenti;
           this.numMedioPolloniCeppaia = numMedioPolloniCeppaia;
           this.percPrimaSpecie = percPrimaSpecie;
           this.percPrimaSpecieRinn = percPrimaSpecieRinn;
           this.percSecondaSpecie = percSecondaSpecie;
           this.percSecondaSpecieRinn = percSecondaSpecieRinn;
           this.presenzaGravami = presenzaGravami;
           this.presenzaServitu = presenzaServitu;
           this.primaSpecie = primaSpecie;
           this.primaSpecieRinn = primaSpecieRinn;
           this.primoTurnoMatricine = primoTurnoMatricine;
           this.rimbBuche = rimbBuche;
           this.rimbSestoImpSulleFile = rimbSestoImpSulleFile;
           this.rimbSestoImpTraFile = rimbSestoImpTraFile;
           this.secondaSpecie = secondaSpecie;
           this.secondaSpecieRinn = secondaSpecieRinn;
           this.secondoTurnoMatricine = secondoTurnoMatricine;
           this.superficie = superficie;
           this.tarePercentuale = tarePercentuale;
           this.tareSuperficie = tareSuperficie;
           this.terzoTurnoMatricine = terzoTurnoMatricine;
           this.usiCivici = usiCivici;
           this.vocEfAbbRinnFustaie = vocEfAbbRinnFustaie;
           this.vocEfAccidentalita = vocEfAccidentalita;
           this.vocEfClasseEta = vocEfClasseEta;
           this.vocEfDistrMatricinatura = vocEfDistrMatricinatura;
           this.vocEfEntitaMatricinatura = vocEfEntitaMatricinatura;
           this.vocEfFunzionePrevalente = vocEfFunzionePrevalente;
           this.vocEfGEvolCopForestale = vocEfGEvolCopForestale;
           this.vocEfModelloCombustibile = vocEfModelloCombustibile;
           this.vocEfNumCeppaieEttaro = vocEfNumCeppaieEttaro;
           this.vocEfOrSelvicolturale = vocEfOrSelvicolturale;
           this.vocEfOrigineBosco = vocEfOrigineBosco;
           this.vocEfPietrosita = vocEfPietrosita;
           this.vocEfProfonditaSuolo = vocEfProfonditaSuolo;
           this.vocEfRimbPrepTerreno = vocEfRimbPrepTerreno;
           this.vocEfRocciosita = vocEfRocciosita;
           this.vocEfStatoVegetativoRinn = vocEfStatoVegetativoRinn;
           this.vocEfTipoColturale = vocEfTipoColturale;
           this.vocEfTipoCopForestale = vocEfTipoCopForestale;
           this.vocEfTipologiaUsoSuolo = vocEfTipologiaUsoSuolo;
    }


    /**
     * Gets the altezzaDominante value for this CcostSubparticellaForestale.
     * 
     * @return altezzaDominante
     */
    public java.lang.Float getAltezzaDominante() {
        return altezzaDominante;
    }


    /**
     * Sets the altezzaDominante value for this CcostSubparticellaForestale.
     * 
     * @param altezzaDominante
     */
    public void setAltezzaDominante(java.lang.Float altezzaDominante) {
        this.altezzaDominante = altezzaDominante;
    }


    /**
     * Gets the altezzaMedia value for this CcostSubparticellaForestale.
     * 
     * @return altezzaMedia
     */
    public java.lang.Float getAltezzaMedia() {
        return altezzaMedia;
    }


    /**
     * Sets the altezzaMedia value for this CcostSubparticellaForestale.
     * 
     * @param altezzaMedia
     */
    public void setAltezzaMedia(java.lang.Float altezzaMedia) {
        this.altezzaMedia = altezzaMedia;
    }


    /**
     * Gets the annoInizioTitolo value for this CcostSubparticellaForestale.
     * 
     * @return annoInizioTitolo
     */
    public java.lang.Integer getAnnoInizioTitolo() {
        return annoInizioTitolo;
    }


    /**
     * Sets the annoInizioTitolo value for this CcostSubparticellaForestale.
     * 
     * @param annoInizioTitolo
     */
    public void setAnnoInizioTitolo(java.lang.Integer annoInizioTitolo) {
        this.annoInizioTitolo = annoInizioTitolo;
    }


    /**
     * Gets the arbustetiBoschiPascolati value for this CcostSubparticellaForestale.
     * 
     * @return arbustetiBoschiPascolati
     */
    public java.lang.Integer getArbustetiBoschiPascolati() {
        return arbustetiBoschiPascolati;
    }


    /**
     * Sets the arbustetiBoschiPascolati value for this CcostSubparticellaForestale.
     * 
     * @param arbustetiBoschiPascolati
     */
    public void setArbustetiBoschiPascolati(java.lang.Integer arbustetiBoschiPascolati) {
        this.arbustetiBoschiPascolati = arbustetiBoschiPascolati;
    }


    /**
     * Gets the classeProvvigInf value for this CcostSubparticellaForestale.
     * 
     * @return classeProvvigInf
     */
    public java.lang.Float getClasseProvvigInf() {
        return classeProvvigInf;
    }


    /**
     * Sets the classeProvvigInf value for this CcostSubparticellaForestale.
     * 
     * @param classeProvvigInf
     */
    public void setClasseProvvigInf(java.lang.Float classeProvvigInf) {
        this.classeProvvigInf = classeProvvigInf;
    }


    /**
     * Gets the classeProvvigSup value for this CcostSubparticellaForestale.
     * 
     * @return classeProvvigSup
     */
    public java.lang.Float getClasseProvvigSup() {
        return classeProvvigSup;
    }


    /**
     * Sets the classeProvvigSup value for this CcostSubparticellaForestale.
     * 
     * @param classeProvvigSup
     */
    public void setClasseProvvigSup(java.lang.Float classeProvvigSup) {
        this.classeProvvigSup = classeProvvigSup;
    }


    /**
     * Gets the codice value for this CcostSubparticellaForestale.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this CcostSubparticellaForestale.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the coefficienteCoperturaArborea value for this CcostSubparticellaForestale.
     * 
     * @return coefficienteCoperturaArborea
     */
    public java.lang.Float getCoefficienteCoperturaArborea() {
        return coefficienteCoperturaArborea;
    }


    /**
     * Sets the coefficienteCoperturaArborea value for this CcostSubparticellaForestale.
     * 
     * @param coefficienteCoperturaArborea
     */
    public void setCoefficienteCoperturaArborea(java.lang.Float coefficienteCoperturaArborea) {
        this.coefficienteCoperturaArborea = coefficienteCoperturaArborea;
    }


    /**
     * Gets the compSpecSubparticellas value for this CcostSubparticellaForestale.
     * 
     * @return compSpecSubparticellas
     */
    public com.hyperborea.sira.ws.CompSpecSubparticella[] getCompSpecSubparticellas() {
        return compSpecSubparticellas;
    }


    /**
     * Sets the compSpecSubparticellas value for this CcostSubparticellaForestale.
     * 
     * @param compSpecSubparticellas
     */
    public void setCompSpecSubparticellas(com.hyperborea.sira.ws.CompSpecSubparticella[] compSpecSubparticellas) {
        this.compSpecSubparticellas = compSpecSubparticellas;
    }

    public com.hyperborea.sira.ws.CompSpecSubparticella getCompSpecSubparticellas(int i) {
        return this.compSpecSubparticellas[i];
    }

    public void setCompSpecSubparticellas(int i, com.hyperborea.sira.ws.CompSpecSubparticella _value) {
        this.compSpecSubparticellas[i] = _value;
    }


    /**
     * Gets the consuetudini value for this CcostSubparticellaForestale.
     * 
     * @return consuetudini
     */
    public java.lang.Integer getConsuetudini() {
        return consuetudini;
    }


    /**
     * Sets the consuetudini value for this CcostSubparticellaForestale.
     * 
     * @param consuetudini
     */
    public void setConsuetudini(java.lang.Integer consuetudini) {
        this.consuetudini = consuetudini;
    }


    /**
     * Gets the danniSubparticellas value for this CcostSubparticellaForestale.
     * 
     * @return danniSubparticellas
     */
    public com.hyperborea.sira.ws.DanniSubparticella[] getDanniSubparticellas() {
        return danniSubparticellas;
    }


    /**
     * Sets the danniSubparticellas value for this CcostSubparticellaForestale.
     * 
     * @param danniSubparticellas
     */
    public void setDanniSubparticellas(com.hyperborea.sira.ws.DanniSubparticella[] danniSubparticellas) {
        this.danniSubparticellas = danniSubparticellas;
    }

    public com.hyperborea.sira.ws.DanniSubparticella getDanniSubparticellas(int i) {
        return this.danniSubparticellas[i];
    }

    public void setDanniSubparticellas(int i, com.hyperborea.sira.ws.DanniSubparticella _value) {
        this.danniSubparticellas[i] = _value;
    }


    /**
     * Gets the diametroMedio value for this CcostSubparticellaForestale.
     * 
     * @return diametroMedio
     */
    public java.lang.Float getDiametroMedio() {
        return diametroMedio;
    }


    /**
     * Sets the diametroMedio value for this CcostSubparticellaForestale.
     * 
     * @param diametroMedio
     */
    public void setDiametroMedio(java.lang.Float diametroMedio) {
        this.diametroMedio = diametroMedio;
    }


    /**
     * Gets the durataTitolo value for this CcostSubparticellaForestale.
     * 
     * @return durataTitolo
     */
    public java.lang.Integer getDurataTitolo() {
        return durataTitolo;
    }


    /**
     * Sets the durataTitolo value for this CcostSubparticellaForestale.
     * 
     * @param durataTitolo
     */
    public void setDurataTitolo(java.lang.Integer durataTitolo) {
        this.durataTitolo = durataTitolo;
    }


    /**
     * Gets the enfiteusi value for this CcostSubparticellaForestale.
     * 
     * @return enfiteusi
     */
    public java.lang.Integer getEnfiteusi() {
        return enfiteusi;
    }


    /**
     * Sets the enfiteusi value for this CcostSubparticellaForestale.
     * 
     * @param enfiteusi
     */
    public void setEnfiteusi(java.lang.Integer enfiteusi) {
        this.enfiteusi = enfiteusi;
    }


    /**
     * Gets the estremiTitolo value for this CcostSubparticellaForestale.
     * 
     * @return estremiTitolo
     */
    public java.lang.String getEstremiTitolo() {
        return estremiTitolo;
    }


    /**
     * Sets the estremiTitolo value for this CcostSubparticellaForestale.
     * 
     * @param estremiTitolo
     */
    public void setEstremiTitolo(java.lang.String estremiTitolo) {
        this.estremiTitolo = estremiTitolo;
    }


    /**
     * Gets the etaPrevalente value for this CcostSubparticellaForestale.
     * 
     * @return etaPrevalente
     */
    public java.lang.Integer getEtaPrevalente() {
        return etaPrevalente;
    }


    /**
     * Sets the etaPrevalente value for this CcostSubparticellaForestale.
     * 
     * @param etaPrevalente
     */
    public void setEtaPrevalente(java.lang.Integer etaPrevalente) {
        this.etaPrevalente = etaPrevalente;
    }


    /**
     * Gets the fidePascolo value for this CcostSubparticellaForestale.
     * 
     * @return fidePascolo
     */
    public java.lang.Integer getFidePascolo() {
        return fidePascolo;
    }


    /**
     * Sets the fidePascolo value for this CcostSubparticellaForestale.
     * 
     * @param fidePascolo
     */
    public void setFidePascolo(java.lang.Integer fidePascolo) {
        this.fidePascolo = fidePascolo;
    }


    /**
     * Gets the idCcost value for this CcostSubparticellaForestale.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostSubparticellaForestale.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the interventiRecenti value for this CcostSubparticellaForestale.
     * 
     * @return interventiRecenti
     */
    public java.lang.String getInterventiRecenti() {
        return interventiRecenti;
    }


    /**
     * Sets the interventiRecenti value for this CcostSubparticellaForestale.
     * 
     * @param interventiRecenti
     */
    public void setInterventiRecenti(java.lang.String interventiRecenti) {
        this.interventiRecenti = interventiRecenti;
    }


    /**
     * Gets the numMedioPolloniCeppaia value for this CcostSubparticellaForestale.
     * 
     * @return numMedioPolloniCeppaia
     */
    public java.lang.Float getNumMedioPolloniCeppaia() {
        return numMedioPolloniCeppaia;
    }


    /**
     * Sets the numMedioPolloniCeppaia value for this CcostSubparticellaForestale.
     * 
     * @param numMedioPolloniCeppaia
     */
    public void setNumMedioPolloniCeppaia(java.lang.Float numMedioPolloniCeppaia) {
        this.numMedioPolloniCeppaia = numMedioPolloniCeppaia;
    }


    /**
     * Gets the percPrimaSpecie value for this CcostSubparticellaForestale.
     * 
     * @return percPrimaSpecie
     */
    public java.lang.Float getPercPrimaSpecie() {
        return percPrimaSpecie;
    }


    /**
     * Sets the percPrimaSpecie value for this CcostSubparticellaForestale.
     * 
     * @param percPrimaSpecie
     */
    public void setPercPrimaSpecie(java.lang.Float percPrimaSpecie) {
        this.percPrimaSpecie = percPrimaSpecie;
    }


    /**
     * Gets the percPrimaSpecieRinn value for this CcostSubparticellaForestale.
     * 
     * @return percPrimaSpecieRinn
     */
    public java.lang.Float getPercPrimaSpecieRinn() {
        return percPrimaSpecieRinn;
    }


    /**
     * Sets the percPrimaSpecieRinn value for this CcostSubparticellaForestale.
     * 
     * @param percPrimaSpecieRinn
     */
    public void setPercPrimaSpecieRinn(java.lang.Float percPrimaSpecieRinn) {
        this.percPrimaSpecieRinn = percPrimaSpecieRinn;
    }


    /**
     * Gets the percSecondaSpecie value for this CcostSubparticellaForestale.
     * 
     * @return percSecondaSpecie
     */
    public java.lang.Float getPercSecondaSpecie() {
        return percSecondaSpecie;
    }


    /**
     * Sets the percSecondaSpecie value for this CcostSubparticellaForestale.
     * 
     * @param percSecondaSpecie
     */
    public void setPercSecondaSpecie(java.lang.Float percSecondaSpecie) {
        this.percSecondaSpecie = percSecondaSpecie;
    }


    /**
     * Gets the percSecondaSpecieRinn value for this CcostSubparticellaForestale.
     * 
     * @return percSecondaSpecieRinn
     */
    public java.lang.Float getPercSecondaSpecieRinn() {
        return percSecondaSpecieRinn;
    }


    /**
     * Sets the percSecondaSpecieRinn value for this CcostSubparticellaForestale.
     * 
     * @param percSecondaSpecieRinn
     */
    public void setPercSecondaSpecieRinn(java.lang.Float percSecondaSpecieRinn) {
        this.percSecondaSpecieRinn = percSecondaSpecieRinn;
    }


    /**
     * Gets the presenzaGravami value for this CcostSubparticellaForestale.
     * 
     * @return presenzaGravami
     */
    public java.lang.Integer getPresenzaGravami() {
        return presenzaGravami;
    }


    /**
     * Sets the presenzaGravami value for this CcostSubparticellaForestale.
     * 
     * @param presenzaGravami
     */
    public void setPresenzaGravami(java.lang.Integer presenzaGravami) {
        this.presenzaGravami = presenzaGravami;
    }


    /**
     * Gets the presenzaServitu value for this CcostSubparticellaForestale.
     * 
     * @return presenzaServitu
     */
    public java.lang.Integer getPresenzaServitu() {
        return presenzaServitu;
    }


    /**
     * Sets the presenzaServitu value for this CcostSubparticellaForestale.
     * 
     * @param presenzaServitu
     */
    public void setPresenzaServitu(java.lang.Integer presenzaServitu) {
        this.presenzaServitu = presenzaServitu;
    }


    /**
     * Gets the primaSpecie value for this CcostSubparticellaForestale.
     * 
     * @return primaSpecie
     */
    public com.hyperborea.sira.ws.Specie getPrimaSpecie() {
        return primaSpecie;
    }


    /**
     * Sets the primaSpecie value for this CcostSubparticellaForestale.
     * 
     * @param primaSpecie
     */
    public void setPrimaSpecie(com.hyperborea.sira.ws.Specie primaSpecie) {
        this.primaSpecie = primaSpecie;
    }


    /**
     * Gets the primaSpecieRinn value for this CcostSubparticellaForestale.
     * 
     * @return primaSpecieRinn
     */
    public com.hyperborea.sira.ws.Specie getPrimaSpecieRinn() {
        return primaSpecieRinn;
    }


    /**
     * Sets the primaSpecieRinn value for this CcostSubparticellaForestale.
     * 
     * @param primaSpecieRinn
     */
    public void setPrimaSpecieRinn(com.hyperborea.sira.ws.Specie primaSpecieRinn) {
        this.primaSpecieRinn = primaSpecieRinn;
    }


    /**
     * Gets the primoTurnoMatricine value for this CcostSubparticellaForestale.
     * 
     * @return primoTurnoMatricine
     */
    public java.lang.Float getPrimoTurnoMatricine() {
        return primoTurnoMatricine;
    }


    /**
     * Sets the primoTurnoMatricine value for this CcostSubparticellaForestale.
     * 
     * @param primoTurnoMatricine
     */
    public void setPrimoTurnoMatricine(java.lang.Float primoTurnoMatricine) {
        this.primoTurnoMatricine = primoTurnoMatricine;
    }


    /**
     * Gets the rimbBuche value for this CcostSubparticellaForestale.
     * 
     * @return rimbBuche
     */
    public java.lang.Integer getRimbBuche() {
        return rimbBuche;
    }


    /**
     * Sets the rimbBuche value for this CcostSubparticellaForestale.
     * 
     * @param rimbBuche
     */
    public void setRimbBuche(java.lang.Integer rimbBuche) {
        this.rimbBuche = rimbBuche;
    }


    /**
     * Gets the rimbSestoImpSulleFile value for this CcostSubparticellaForestale.
     * 
     * @return rimbSestoImpSulleFile
     */
    public java.lang.Float getRimbSestoImpSulleFile() {
        return rimbSestoImpSulleFile;
    }


    /**
     * Sets the rimbSestoImpSulleFile value for this CcostSubparticellaForestale.
     * 
     * @param rimbSestoImpSulleFile
     */
    public void setRimbSestoImpSulleFile(java.lang.Float rimbSestoImpSulleFile) {
        this.rimbSestoImpSulleFile = rimbSestoImpSulleFile;
    }


    /**
     * Gets the rimbSestoImpTraFile value for this CcostSubparticellaForestale.
     * 
     * @return rimbSestoImpTraFile
     */
    public java.lang.Float getRimbSestoImpTraFile() {
        return rimbSestoImpTraFile;
    }


    /**
     * Sets the rimbSestoImpTraFile value for this CcostSubparticellaForestale.
     * 
     * @param rimbSestoImpTraFile
     */
    public void setRimbSestoImpTraFile(java.lang.Float rimbSestoImpTraFile) {
        this.rimbSestoImpTraFile = rimbSestoImpTraFile;
    }


    /**
     * Gets the secondaSpecie value for this CcostSubparticellaForestale.
     * 
     * @return secondaSpecie
     */
    public com.hyperborea.sira.ws.Specie getSecondaSpecie() {
        return secondaSpecie;
    }


    /**
     * Sets the secondaSpecie value for this CcostSubparticellaForestale.
     * 
     * @param secondaSpecie
     */
    public void setSecondaSpecie(com.hyperborea.sira.ws.Specie secondaSpecie) {
        this.secondaSpecie = secondaSpecie;
    }


    /**
     * Gets the secondaSpecieRinn value for this CcostSubparticellaForestale.
     * 
     * @return secondaSpecieRinn
     */
    public com.hyperborea.sira.ws.Specie getSecondaSpecieRinn() {
        return secondaSpecieRinn;
    }


    /**
     * Sets the secondaSpecieRinn value for this CcostSubparticellaForestale.
     * 
     * @param secondaSpecieRinn
     */
    public void setSecondaSpecieRinn(com.hyperborea.sira.ws.Specie secondaSpecieRinn) {
        this.secondaSpecieRinn = secondaSpecieRinn;
    }


    /**
     * Gets the secondoTurnoMatricine value for this CcostSubparticellaForestale.
     * 
     * @return secondoTurnoMatricine
     */
    public java.lang.Float getSecondoTurnoMatricine() {
        return secondoTurnoMatricine;
    }


    /**
     * Sets the secondoTurnoMatricine value for this CcostSubparticellaForestale.
     * 
     * @param secondoTurnoMatricine
     */
    public void setSecondoTurnoMatricine(java.lang.Float secondoTurnoMatricine) {
        this.secondoTurnoMatricine = secondoTurnoMatricine;
    }


    /**
     * Gets the superficie value for this CcostSubparticellaForestale.
     * 
     * @return superficie
     */
    public java.lang.Float getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this CcostSubparticellaForestale.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Float superficie) {
        this.superficie = superficie;
    }


    /**
     * Gets the tarePercentuale value for this CcostSubparticellaForestale.
     * 
     * @return tarePercentuale
     */
    public java.lang.Float getTarePercentuale() {
        return tarePercentuale;
    }


    /**
     * Sets the tarePercentuale value for this CcostSubparticellaForestale.
     * 
     * @param tarePercentuale
     */
    public void setTarePercentuale(java.lang.Float tarePercentuale) {
        this.tarePercentuale = tarePercentuale;
    }


    /**
     * Gets the tareSuperficie value for this CcostSubparticellaForestale.
     * 
     * @return tareSuperficie
     */
    public java.lang.Float getTareSuperficie() {
        return tareSuperficie;
    }


    /**
     * Sets the tareSuperficie value for this CcostSubparticellaForestale.
     * 
     * @param tareSuperficie
     */
    public void setTareSuperficie(java.lang.Float tareSuperficie) {
        this.tareSuperficie = tareSuperficie;
    }


    /**
     * Gets the terzoTurnoMatricine value for this CcostSubparticellaForestale.
     * 
     * @return terzoTurnoMatricine
     */
    public java.lang.Float getTerzoTurnoMatricine() {
        return terzoTurnoMatricine;
    }


    /**
     * Sets the terzoTurnoMatricine value for this CcostSubparticellaForestale.
     * 
     * @param terzoTurnoMatricine
     */
    public void setTerzoTurnoMatricine(java.lang.Float terzoTurnoMatricine) {
        this.terzoTurnoMatricine = terzoTurnoMatricine;
    }


    /**
     * Gets the usiCivici value for this CcostSubparticellaForestale.
     * 
     * @return usiCivici
     */
    public java.lang.Integer getUsiCivici() {
        return usiCivici;
    }


    /**
     * Sets the usiCivici value for this CcostSubparticellaForestale.
     * 
     * @param usiCivici
     */
    public void setUsiCivici(java.lang.Integer usiCivici) {
        this.usiCivici = usiCivici;
    }


    /**
     * Gets the vocEfAbbRinnFustaie value for this CcostSubparticellaForestale.
     * 
     * @return vocEfAbbRinnFustaie
     */
    public com.hyperborea.sira.ws.VocEfAbbRinnFustaie getVocEfAbbRinnFustaie() {
        return vocEfAbbRinnFustaie;
    }


    /**
     * Sets the vocEfAbbRinnFustaie value for this CcostSubparticellaForestale.
     * 
     * @param vocEfAbbRinnFustaie
     */
    public void setVocEfAbbRinnFustaie(com.hyperborea.sira.ws.VocEfAbbRinnFustaie vocEfAbbRinnFustaie) {
        this.vocEfAbbRinnFustaie = vocEfAbbRinnFustaie;
    }


    /**
     * Gets the vocEfAccidentalita value for this CcostSubparticellaForestale.
     * 
     * @return vocEfAccidentalita
     */
    public com.hyperborea.sira.ws.VocEfAccidentalita getVocEfAccidentalita() {
        return vocEfAccidentalita;
    }


    /**
     * Sets the vocEfAccidentalita value for this CcostSubparticellaForestale.
     * 
     * @param vocEfAccidentalita
     */
    public void setVocEfAccidentalita(com.hyperborea.sira.ws.VocEfAccidentalita vocEfAccidentalita) {
        this.vocEfAccidentalita = vocEfAccidentalita;
    }


    /**
     * Gets the vocEfClasseEta value for this CcostSubparticellaForestale.
     * 
     * @return vocEfClasseEta
     */
    public com.hyperborea.sira.ws.VocEfClasseEta getVocEfClasseEta() {
        return vocEfClasseEta;
    }


    /**
     * Sets the vocEfClasseEta value for this CcostSubparticellaForestale.
     * 
     * @param vocEfClasseEta
     */
    public void setVocEfClasseEta(com.hyperborea.sira.ws.VocEfClasseEta vocEfClasseEta) {
        this.vocEfClasseEta = vocEfClasseEta;
    }


    /**
     * Gets the vocEfDistrMatricinatura value for this CcostSubparticellaForestale.
     * 
     * @return vocEfDistrMatricinatura
     */
    public com.hyperborea.sira.ws.VocEfDistrMatricinatura getVocEfDistrMatricinatura() {
        return vocEfDistrMatricinatura;
    }


    /**
     * Sets the vocEfDistrMatricinatura value for this CcostSubparticellaForestale.
     * 
     * @param vocEfDistrMatricinatura
     */
    public void setVocEfDistrMatricinatura(com.hyperborea.sira.ws.VocEfDistrMatricinatura vocEfDistrMatricinatura) {
        this.vocEfDistrMatricinatura = vocEfDistrMatricinatura;
    }


    /**
     * Gets the vocEfEntitaMatricinatura value for this CcostSubparticellaForestale.
     * 
     * @return vocEfEntitaMatricinatura
     */
    public com.hyperborea.sira.ws.VocEfEntitaMatricinatura getVocEfEntitaMatricinatura() {
        return vocEfEntitaMatricinatura;
    }


    /**
     * Sets the vocEfEntitaMatricinatura value for this CcostSubparticellaForestale.
     * 
     * @param vocEfEntitaMatricinatura
     */
    public void setVocEfEntitaMatricinatura(com.hyperborea.sira.ws.VocEfEntitaMatricinatura vocEfEntitaMatricinatura) {
        this.vocEfEntitaMatricinatura = vocEfEntitaMatricinatura;
    }


    /**
     * Gets the vocEfFunzionePrevalente value for this CcostSubparticellaForestale.
     * 
     * @return vocEfFunzionePrevalente
     */
    public com.hyperborea.sira.ws.VocEfFunzionePrevalente getVocEfFunzionePrevalente() {
        return vocEfFunzionePrevalente;
    }


    /**
     * Sets the vocEfFunzionePrevalente value for this CcostSubparticellaForestale.
     * 
     * @param vocEfFunzionePrevalente
     */
    public void setVocEfFunzionePrevalente(com.hyperborea.sira.ws.VocEfFunzionePrevalente vocEfFunzionePrevalente) {
        this.vocEfFunzionePrevalente = vocEfFunzionePrevalente;
    }


    /**
     * Gets the vocEfGEvolCopForestale value for this CcostSubparticellaForestale.
     * 
     * @return vocEfGEvolCopForestale
     */
    public com.hyperborea.sira.ws.VocEfGEvolCopForestale getVocEfGEvolCopForestale() {
        return vocEfGEvolCopForestale;
    }


    /**
     * Sets the vocEfGEvolCopForestale value for this CcostSubparticellaForestale.
     * 
     * @param vocEfGEvolCopForestale
     */
    public void setVocEfGEvolCopForestale(com.hyperborea.sira.ws.VocEfGEvolCopForestale vocEfGEvolCopForestale) {
        this.vocEfGEvolCopForestale = vocEfGEvolCopForestale;
    }


    /**
     * Gets the vocEfModelloCombustibile value for this CcostSubparticellaForestale.
     * 
     * @return vocEfModelloCombustibile
     */
    public com.hyperborea.sira.ws.VocEfModelloCombustibile getVocEfModelloCombustibile() {
        return vocEfModelloCombustibile;
    }


    /**
     * Sets the vocEfModelloCombustibile value for this CcostSubparticellaForestale.
     * 
     * @param vocEfModelloCombustibile
     */
    public void setVocEfModelloCombustibile(com.hyperborea.sira.ws.VocEfModelloCombustibile vocEfModelloCombustibile) {
        this.vocEfModelloCombustibile = vocEfModelloCombustibile;
    }


    /**
     * Gets the vocEfNumCeppaieEttaro value for this CcostSubparticellaForestale.
     * 
     * @return vocEfNumCeppaieEttaro
     */
    public com.hyperborea.sira.ws.VocEfNumCeppaieEttaro getVocEfNumCeppaieEttaro() {
        return vocEfNumCeppaieEttaro;
    }


    /**
     * Sets the vocEfNumCeppaieEttaro value for this CcostSubparticellaForestale.
     * 
     * @param vocEfNumCeppaieEttaro
     */
    public void setVocEfNumCeppaieEttaro(com.hyperborea.sira.ws.VocEfNumCeppaieEttaro vocEfNumCeppaieEttaro) {
        this.vocEfNumCeppaieEttaro = vocEfNumCeppaieEttaro;
    }


    /**
     * Gets the vocEfOrSelvicolturale value for this CcostSubparticellaForestale.
     * 
     * @return vocEfOrSelvicolturale
     */
    public com.hyperborea.sira.ws.VocEfOrSelvicolturale getVocEfOrSelvicolturale() {
        return vocEfOrSelvicolturale;
    }


    /**
     * Sets the vocEfOrSelvicolturale value for this CcostSubparticellaForestale.
     * 
     * @param vocEfOrSelvicolturale
     */
    public void setVocEfOrSelvicolturale(com.hyperborea.sira.ws.VocEfOrSelvicolturale vocEfOrSelvicolturale) {
        this.vocEfOrSelvicolturale = vocEfOrSelvicolturale;
    }


    /**
     * Gets the vocEfOrigineBosco value for this CcostSubparticellaForestale.
     * 
     * @return vocEfOrigineBosco
     */
    public com.hyperborea.sira.ws.VocEfOrigineBosco getVocEfOrigineBosco() {
        return vocEfOrigineBosco;
    }


    /**
     * Sets the vocEfOrigineBosco value for this CcostSubparticellaForestale.
     * 
     * @param vocEfOrigineBosco
     */
    public void setVocEfOrigineBosco(com.hyperborea.sira.ws.VocEfOrigineBosco vocEfOrigineBosco) {
        this.vocEfOrigineBosco = vocEfOrigineBosco;
    }


    /**
     * Gets the vocEfPietrosita value for this CcostSubparticellaForestale.
     * 
     * @return vocEfPietrosita
     */
    public com.hyperborea.sira.ws.VocEfPietrosita getVocEfPietrosita() {
        return vocEfPietrosita;
    }


    /**
     * Sets the vocEfPietrosita value for this CcostSubparticellaForestale.
     * 
     * @param vocEfPietrosita
     */
    public void setVocEfPietrosita(com.hyperborea.sira.ws.VocEfPietrosita vocEfPietrosita) {
        this.vocEfPietrosita = vocEfPietrosita;
    }


    /**
     * Gets the vocEfProfonditaSuolo value for this CcostSubparticellaForestale.
     * 
     * @return vocEfProfonditaSuolo
     */
    public com.hyperborea.sira.ws.VocEfProfonditaSuolo getVocEfProfonditaSuolo() {
        return vocEfProfonditaSuolo;
    }


    /**
     * Sets the vocEfProfonditaSuolo value for this CcostSubparticellaForestale.
     * 
     * @param vocEfProfonditaSuolo
     */
    public void setVocEfProfonditaSuolo(com.hyperborea.sira.ws.VocEfProfonditaSuolo vocEfProfonditaSuolo) {
        this.vocEfProfonditaSuolo = vocEfProfonditaSuolo;
    }


    /**
     * Gets the vocEfRimbPrepTerreno value for this CcostSubparticellaForestale.
     * 
     * @return vocEfRimbPrepTerreno
     */
    public com.hyperborea.sira.ws.VocEfRimbPrepTerreno getVocEfRimbPrepTerreno() {
        return vocEfRimbPrepTerreno;
    }


    /**
     * Sets the vocEfRimbPrepTerreno value for this CcostSubparticellaForestale.
     * 
     * @param vocEfRimbPrepTerreno
     */
    public void setVocEfRimbPrepTerreno(com.hyperborea.sira.ws.VocEfRimbPrepTerreno vocEfRimbPrepTerreno) {
        this.vocEfRimbPrepTerreno = vocEfRimbPrepTerreno;
    }


    /**
     * Gets the vocEfRocciosita value for this CcostSubparticellaForestale.
     * 
     * @return vocEfRocciosita
     */
    public com.hyperborea.sira.ws.VocEfRocciosita getVocEfRocciosita() {
        return vocEfRocciosita;
    }


    /**
     * Sets the vocEfRocciosita value for this CcostSubparticellaForestale.
     * 
     * @param vocEfRocciosita
     */
    public void setVocEfRocciosita(com.hyperborea.sira.ws.VocEfRocciosita vocEfRocciosita) {
        this.vocEfRocciosita = vocEfRocciosita;
    }


    /**
     * Gets the vocEfStatoVegetativoRinn value for this CcostSubparticellaForestale.
     * 
     * @return vocEfStatoVegetativoRinn
     */
    public com.hyperborea.sira.ws.VocEfStatoVegetativoRinn getVocEfStatoVegetativoRinn() {
        return vocEfStatoVegetativoRinn;
    }


    /**
     * Sets the vocEfStatoVegetativoRinn value for this CcostSubparticellaForestale.
     * 
     * @param vocEfStatoVegetativoRinn
     */
    public void setVocEfStatoVegetativoRinn(com.hyperborea.sira.ws.VocEfStatoVegetativoRinn vocEfStatoVegetativoRinn) {
        this.vocEfStatoVegetativoRinn = vocEfStatoVegetativoRinn;
    }


    /**
     * Gets the vocEfTipoColturale value for this CcostSubparticellaForestale.
     * 
     * @return vocEfTipoColturale
     */
    public com.hyperborea.sira.ws.VocEfTipoColturale getVocEfTipoColturale() {
        return vocEfTipoColturale;
    }


    /**
     * Sets the vocEfTipoColturale value for this CcostSubparticellaForestale.
     * 
     * @param vocEfTipoColturale
     */
    public void setVocEfTipoColturale(com.hyperborea.sira.ws.VocEfTipoColturale vocEfTipoColturale) {
        this.vocEfTipoColturale = vocEfTipoColturale;
    }


    /**
     * Gets the vocEfTipoCopForestale value for this CcostSubparticellaForestale.
     * 
     * @return vocEfTipoCopForestale
     */
    public com.hyperborea.sira.ws.VocEfTipoCopForestale getVocEfTipoCopForestale() {
        return vocEfTipoCopForestale;
    }


    /**
     * Sets the vocEfTipoCopForestale value for this CcostSubparticellaForestale.
     * 
     * @param vocEfTipoCopForestale
     */
    public void setVocEfTipoCopForestale(com.hyperborea.sira.ws.VocEfTipoCopForestale vocEfTipoCopForestale) {
        this.vocEfTipoCopForestale = vocEfTipoCopForestale;
    }


    /**
     * Gets the vocEfTipologiaUsoSuolo value for this CcostSubparticellaForestale.
     * 
     * @return vocEfTipologiaUsoSuolo
     */
    public com.hyperborea.sira.ws.VocEfTipologiaUsoSuolo getVocEfTipologiaUsoSuolo() {
        return vocEfTipologiaUsoSuolo;
    }


    /**
     * Sets the vocEfTipologiaUsoSuolo value for this CcostSubparticellaForestale.
     * 
     * @param vocEfTipologiaUsoSuolo
     */
    public void setVocEfTipologiaUsoSuolo(com.hyperborea.sira.ws.VocEfTipologiaUsoSuolo vocEfTipologiaUsoSuolo) {
        this.vocEfTipologiaUsoSuolo = vocEfTipologiaUsoSuolo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostSubparticellaForestale)) return false;
        CcostSubparticellaForestale other = (CcostSubparticellaForestale) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.altezzaDominante==null && other.getAltezzaDominante()==null) || 
             (this.altezzaDominante!=null &&
              this.altezzaDominante.equals(other.getAltezzaDominante()))) &&
            ((this.altezzaMedia==null && other.getAltezzaMedia()==null) || 
             (this.altezzaMedia!=null &&
              this.altezzaMedia.equals(other.getAltezzaMedia()))) &&
            ((this.annoInizioTitolo==null && other.getAnnoInizioTitolo()==null) || 
             (this.annoInizioTitolo!=null &&
              this.annoInizioTitolo.equals(other.getAnnoInizioTitolo()))) &&
            ((this.arbustetiBoschiPascolati==null && other.getArbustetiBoschiPascolati()==null) || 
             (this.arbustetiBoschiPascolati!=null &&
              this.arbustetiBoschiPascolati.equals(other.getArbustetiBoschiPascolati()))) &&
            ((this.classeProvvigInf==null && other.getClasseProvvigInf()==null) || 
             (this.classeProvvigInf!=null &&
              this.classeProvvigInf.equals(other.getClasseProvvigInf()))) &&
            ((this.classeProvvigSup==null && other.getClasseProvvigSup()==null) || 
             (this.classeProvvigSup!=null &&
              this.classeProvvigSup.equals(other.getClasseProvvigSup()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.coefficienteCoperturaArborea==null && other.getCoefficienteCoperturaArborea()==null) || 
             (this.coefficienteCoperturaArborea!=null &&
              this.coefficienteCoperturaArborea.equals(other.getCoefficienteCoperturaArborea()))) &&
            ((this.compSpecSubparticellas==null && other.getCompSpecSubparticellas()==null) || 
             (this.compSpecSubparticellas!=null &&
              java.util.Arrays.equals(this.compSpecSubparticellas, other.getCompSpecSubparticellas()))) &&
            ((this.consuetudini==null && other.getConsuetudini()==null) || 
             (this.consuetudini!=null &&
              this.consuetudini.equals(other.getConsuetudini()))) &&
            ((this.danniSubparticellas==null && other.getDanniSubparticellas()==null) || 
             (this.danniSubparticellas!=null &&
              java.util.Arrays.equals(this.danniSubparticellas, other.getDanniSubparticellas()))) &&
            ((this.diametroMedio==null && other.getDiametroMedio()==null) || 
             (this.diametroMedio!=null &&
              this.diametroMedio.equals(other.getDiametroMedio()))) &&
            ((this.durataTitolo==null && other.getDurataTitolo()==null) || 
             (this.durataTitolo!=null &&
              this.durataTitolo.equals(other.getDurataTitolo()))) &&
            ((this.enfiteusi==null && other.getEnfiteusi()==null) || 
             (this.enfiteusi!=null &&
              this.enfiteusi.equals(other.getEnfiteusi()))) &&
            ((this.estremiTitolo==null && other.getEstremiTitolo()==null) || 
             (this.estremiTitolo!=null &&
              this.estremiTitolo.equals(other.getEstremiTitolo()))) &&
            ((this.etaPrevalente==null && other.getEtaPrevalente()==null) || 
             (this.etaPrevalente!=null &&
              this.etaPrevalente.equals(other.getEtaPrevalente()))) &&
            ((this.fidePascolo==null && other.getFidePascolo()==null) || 
             (this.fidePascolo!=null &&
              this.fidePascolo.equals(other.getFidePascolo()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.interventiRecenti==null && other.getInterventiRecenti()==null) || 
             (this.interventiRecenti!=null &&
              this.interventiRecenti.equals(other.getInterventiRecenti()))) &&
            ((this.numMedioPolloniCeppaia==null && other.getNumMedioPolloniCeppaia()==null) || 
             (this.numMedioPolloniCeppaia!=null &&
              this.numMedioPolloniCeppaia.equals(other.getNumMedioPolloniCeppaia()))) &&
            ((this.percPrimaSpecie==null && other.getPercPrimaSpecie()==null) || 
             (this.percPrimaSpecie!=null &&
              this.percPrimaSpecie.equals(other.getPercPrimaSpecie()))) &&
            ((this.percPrimaSpecieRinn==null && other.getPercPrimaSpecieRinn()==null) || 
             (this.percPrimaSpecieRinn!=null &&
              this.percPrimaSpecieRinn.equals(other.getPercPrimaSpecieRinn()))) &&
            ((this.percSecondaSpecie==null && other.getPercSecondaSpecie()==null) || 
             (this.percSecondaSpecie!=null &&
              this.percSecondaSpecie.equals(other.getPercSecondaSpecie()))) &&
            ((this.percSecondaSpecieRinn==null && other.getPercSecondaSpecieRinn()==null) || 
             (this.percSecondaSpecieRinn!=null &&
              this.percSecondaSpecieRinn.equals(other.getPercSecondaSpecieRinn()))) &&
            ((this.presenzaGravami==null && other.getPresenzaGravami()==null) || 
             (this.presenzaGravami!=null &&
              this.presenzaGravami.equals(other.getPresenzaGravami()))) &&
            ((this.presenzaServitu==null && other.getPresenzaServitu()==null) || 
             (this.presenzaServitu!=null &&
              this.presenzaServitu.equals(other.getPresenzaServitu()))) &&
            ((this.primaSpecie==null && other.getPrimaSpecie()==null) || 
             (this.primaSpecie!=null &&
              this.primaSpecie.equals(other.getPrimaSpecie()))) &&
            ((this.primaSpecieRinn==null && other.getPrimaSpecieRinn()==null) || 
             (this.primaSpecieRinn!=null &&
              this.primaSpecieRinn.equals(other.getPrimaSpecieRinn()))) &&
            ((this.primoTurnoMatricine==null && other.getPrimoTurnoMatricine()==null) || 
             (this.primoTurnoMatricine!=null &&
              this.primoTurnoMatricine.equals(other.getPrimoTurnoMatricine()))) &&
            ((this.rimbBuche==null && other.getRimbBuche()==null) || 
             (this.rimbBuche!=null &&
              this.rimbBuche.equals(other.getRimbBuche()))) &&
            ((this.rimbSestoImpSulleFile==null && other.getRimbSestoImpSulleFile()==null) || 
             (this.rimbSestoImpSulleFile!=null &&
              this.rimbSestoImpSulleFile.equals(other.getRimbSestoImpSulleFile()))) &&
            ((this.rimbSestoImpTraFile==null && other.getRimbSestoImpTraFile()==null) || 
             (this.rimbSestoImpTraFile!=null &&
              this.rimbSestoImpTraFile.equals(other.getRimbSestoImpTraFile()))) &&
            ((this.secondaSpecie==null && other.getSecondaSpecie()==null) || 
             (this.secondaSpecie!=null &&
              this.secondaSpecie.equals(other.getSecondaSpecie()))) &&
            ((this.secondaSpecieRinn==null && other.getSecondaSpecieRinn()==null) || 
             (this.secondaSpecieRinn!=null &&
              this.secondaSpecieRinn.equals(other.getSecondaSpecieRinn()))) &&
            ((this.secondoTurnoMatricine==null && other.getSecondoTurnoMatricine()==null) || 
             (this.secondoTurnoMatricine!=null &&
              this.secondoTurnoMatricine.equals(other.getSecondoTurnoMatricine()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie()))) &&
            ((this.tarePercentuale==null && other.getTarePercentuale()==null) || 
             (this.tarePercentuale!=null &&
              this.tarePercentuale.equals(other.getTarePercentuale()))) &&
            ((this.tareSuperficie==null && other.getTareSuperficie()==null) || 
             (this.tareSuperficie!=null &&
              this.tareSuperficie.equals(other.getTareSuperficie()))) &&
            ((this.terzoTurnoMatricine==null && other.getTerzoTurnoMatricine()==null) || 
             (this.terzoTurnoMatricine!=null &&
              this.terzoTurnoMatricine.equals(other.getTerzoTurnoMatricine()))) &&
            ((this.usiCivici==null && other.getUsiCivici()==null) || 
             (this.usiCivici!=null &&
              this.usiCivici.equals(other.getUsiCivici()))) &&
            ((this.vocEfAbbRinnFustaie==null && other.getVocEfAbbRinnFustaie()==null) || 
             (this.vocEfAbbRinnFustaie!=null &&
              this.vocEfAbbRinnFustaie.equals(other.getVocEfAbbRinnFustaie()))) &&
            ((this.vocEfAccidentalita==null && other.getVocEfAccidentalita()==null) || 
             (this.vocEfAccidentalita!=null &&
              this.vocEfAccidentalita.equals(other.getVocEfAccidentalita()))) &&
            ((this.vocEfClasseEta==null && other.getVocEfClasseEta()==null) || 
             (this.vocEfClasseEta!=null &&
              this.vocEfClasseEta.equals(other.getVocEfClasseEta()))) &&
            ((this.vocEfDistrMatricinatura==null && other.getVocEfDistrMatricinatura()==null) || 
             (this.vocEfDistrMatricinatura!=null &&
              this.vocEfDistrMatricinatura.equals(other.getVocEfDistrMatricinatura()))) &&
            ((this.vocEfEntitaMatricinatura==null && other.getVocEfEntitaMatricinatura()==null) || 
             (this.vocEfEntitaMatricinatura!=null &&
              this.vocEfEntitaMatricinatura.equals(other.getVocEfEntitaMatricinatura()))) &&
            ((this.vocEfFunzionePrevalente==null && other.getVocEfFunzionePrevalente()==null) || 
             (this.vocEfFunzionePrevalente!=null &&
              this.vocEfFunzionePrevalente.equals(other.getVocEfFunzionePrevalente()))) &&
            ((this.vocEfGEvolCopForestale==null && other.getVocEfGEvolCopForestale()==null) || 
             (this.vocEfGEvolCopForestale!=null &&
              this.vocEfGEvolCopForestale.equals(other.getVocEfGEvolCopForestale()))) &&
            ((this.vocEfModelloCombustibile==null && other.getVocEfModelloCombustibile()==null) || 
             (this.vocEfModelloCombustibile!=null &&
              this.vocEfModelloCombustibile.equals(other.getVocEfModelloCombustibile()))) &&
            ((this.vocEfNumCeppaieEttaro==null && other.getVocEfNumCeppaieEttaro()==null) || 
             (this.vocEfNumCeppaieEttaro!=null &&
              this.vocEfNumCeppaieEttaro.equals(other.getVocEfNumCeppaieEttaro()))) &&
            ((this.vocEfOrSelvicolturale==null && other.getVocEfOrSelvicolturale()==null) || 
             (this.vocEfOrSelvicolturale!=null &&
              this.vocEfOrSelvicolturale.equals(other.getVocEfOrSelvicolturale()))) &&
            ((this.vocEfOrigineBosco==null && other.getVocEfOrigineBosco()==null) || 
             (this.vocEfOrigineBosco!=null &&
              this.vocEfOrigineBosco.equals(other.getVocEfOrigineBosco()))) &&
            ((this.vocEfPietrosita==null && other.getVocEfPietrosita()==null) || 
             (this.vocEfPietrosita!=null &&
              this.vocEfPietrosita.equals(other.getVocEfPietrosita()))) &&
            ((this.vocEfProfonditaSuolo==null && other.getVocEfProfonditaSuolo()==null) || 
             (this.vocEfProfonditaSuolo!=null &&
              this.vocEfProfonditaSuolo.equals(other.getVocEfProfonditaSuolo()))) &&
            ((this.vocEfRimbPrepTerreno==null && other.getVocEfRimbPrepTerreno()==null) || 
             (this.vocEfRimbPrepTerreno!=null &&
              this.vocEfRimbPrepTerreno.equals(other.getVocEfRimbPrepTerreno()))) &&
            ((this.vocEfRocciosita==null && other.getVocEfRocciosita()==null) || 
             (this.vocEfRocciosita!=null &&
              this.vocEfRocciosita.equals(other.getVocEfRocciosita()))) &&
            ((this.vocEfStatoVegetativoRinn==null && other.getVocEfStatoVegetativoRinn()==null) || 
             (this.vocEfStatoVegetativoRinn!=null &&
              this.vocEfStatoVegetativoRinn.equals(other.getVocEfStatoVegetativoRinn()))) &&
            ((this.vocEfTipoColturale==null && other.getVocEfTipoColturale()==null) || 
             (this.vocEfTipoColturale!=null &&
              this.vocEfTipoColturale.equals(other.getVocEfTipoColturale()))) &&
            ((this.vocEfTipoCopForestale==null && other.getVocEfTipoCopForestale()==null) || 
             (this.vocEfTipoCopForestale!=null &&
              this.vocEfTipoCopForestale.equals(other.getVocEfTipoCopForestale()))) &&
            ((this.vocEfTipologiaUsoSuolo==null && other.getVocEfTipologiaUsoSuolo()==null) || 
             (this.vocEfTipologiaUsoSuolo!=null &&
              this.vocEfTipologiaUsoSuolo.equals(other.getVocEfTipologiaUsoSuolo())));
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
        if (getAltezzaDominante() != null) {
            _hashCode += getAltezzaDominante().hashCode();
        }
        if (getAltezzaMedia() != null) {
            _hashCode += getAltezzaMedia().hashCode();
        }
        if (getAnnoInizioTitolo() != null) {
            _hashCode += getAnnoInizioTitolo().hashCode();
        }
        if (getArbustetiBoschiPascolati() != null) {
            _hashCode += getArbustetiBoschiPascolati().hashCode();
        }
        if (getClasseProvvigInf() != null) {
            _hashCode += getClasseProvvigInf().hashCode();
        }
        if (getClasseProvvigSup() != null) {
            _hashCode += getClasseProvvigSup().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getCoefficienteCoperturaArborea() != null) {
            _hashCode += getCoefficienteCoperturaArborea().hashCode();
        }
        if (getCompSpecSubparticellas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCompSpecSubparticellas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCompSpecSubparticellas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getConsuetudini() != null) {
            _hashCode += getConsuetudini().hashCode();
        }
        if (getDanniSubparticellas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDanniSubparticellas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDanniSubparticellas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDiametroMedio() != null) {
            _hashCode += getDiametroMedio().hashCode();
        }
        if (getDurataTitolo() != null) {
            _hashCode += getDurataTitolo().hashCode();
        }
        if (getEnfiteusi() != null) {
            _hashCode += getEnfiteusi().hashCode();
        }
        if (getEstremiTitolo() != null) {
            _hashCode += getEstremiTitolo().hashCode();
        }
        if (getEtaPrevalente() != null) {
            _hashCode += getEtaPrevalente().hashCode();
        }
        if (getFidePascolo() != null) {
            _hashCode += getFidePascolo().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getInterventiRecenti() != null) {
            _hashCode += getInterventiRecenti().hashCode();
        }
        if (getNumMedioPolloniCeppaia() != null) {
            _hashCode += getNumMedioPolloniCeppaia().hashCode();
        }
        if (getPercPrimaSpecie() != null) {
            _hashCode += getPercPrimaSpecie().hashCode();
        }
        if (getPercPrimaSpecieRinn() != null) {
            _hashCode += getPercPrimaSpecieRinn().hashCode();
        }
        if (getPercSecondaSpecie() != null) {
            _hashCode += getPercSecondaSpecie().hashCode();
        }
        if (getPercSecondaSpecieRinn() != null) {
            _hashCode += getPercSecondaSpecieRinn().hashCode();
        }
        if (getPresenzaGravami() != null) {
            _hashCode += getPresenzaGravami().hashCode();
        }
        if (getPresenzaServitu() != null) {
            _hashCode += getPresenzaServitu().hashCode();
        }
        if (getPrimaSpecie() != null) {
            _hashCode += getPrimaSpecie().hashCode();
        }
        if (getPrimaSpecieRinn() != null) {
            _hashCode += getPrimaSpecieRinn().hashCode();
        }
        if (getPrimoTurnoMatricine() != null) {
            _hashCode += getPrimoTurnoMatricine().hashCode();
        }
        if (getRimbBuche() != null) {
            _hashCode += getRimbBuche().hashCode();
        }
        if (getRimbSestoImpSulleFile() != null) {
            _hashCode += getRimbSestoImpSulleFile().hashCode();
        }
        if (getRimbSestoImpTraFile() != null) {
            _hashCode += getRimbSestoImpTraFile().hashCode();
        }
        if (getSecondaSpecie() != null) {
            _hashCode += getSecondaSpecie().hashCode();
        }
        if (getSecondaSpecieRinn() != null) {
            _hashCode += getSecondaSpecieRinn().hashCode();
        }
        if (getSecondoTurnoMatricine() != null) {
            _hashCode += getSecondoTurnoMatricine().hashCode();
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        if (getTarePercentuale() != null) {
            _hashCode += getTarePercentuale().hashCode();
        }
        if (getTareSuperficie() != null) {
            _hashCode += getTareSuperficie().hashCode();
        }
        if (getTerzoTurnoMatricine() != null) {
            _hashCode += getTerzoTurnoMatricine().hashCode();
        }
        if (getUsiCivici() != null) {
            _hashCode += getUsiCivici().hashCode();
        }
        if (getVocEfAbbRinnFustaie() != null) {
            _hashCode += getVocEfAbbRinnFustaie().hashCode();
        }
        if (getVocEfAccidentalita() != null) {
            _hashCode += getVocEfAccidentalita().hashCode();
        }
        if (getVocEfClasseEta() != null) {
            _hashCode += getVocEfClasseEta().hashCode();
        }
        if (getVocEfDistrMatricinatura() != null) {
            _hashCode += getVocEfDistrMatricinatura().hashCode();
        }
        if (getVocEfEntitaMatricinatura() != null) {
            _hashCode += getVocEfEntitaMatricinatura().hashCode();
        }
        if (getVocEfFunzionePrevalente() != null) {
            _hashCode += getVocEfFunzionePrevalente().hashCode();
        }
        if (getVocEfGEvolCopForestale() != null) {
            _hashCode += getVocEfGEvolCopForestale().hashCode();
        }
        if (getVocEfModelloCombustibile() != null) {
            _hashCode += getVocEfModelloCombustibile().hashCode();
        }
        if (getVocEfNumCeppaieEttaro() != null) {
            _hashCode += getVocEfNumCeppaieEttaro().hashCode();
        }
        if (getVocEfOrSelvicolturale() != null) {
            _hashCode += getVocEfOrSelvicolturale().hashCode();
        }
        if (getVocEfOrigineBosco() != null) {
            _hashCode += getVocEfOrigineBosco().hashCode();
        }
        if (getVocEfPietrosita() != null) {
            _hashCode += getVocEfPietrosita().hashCode();
        }
        if (getVocEfProfonditaSuolo() != null) {
            _hashCode += getVocEfProfonditaSuolo().hashCode();
        }
        if (getVocEfRimbPrepTerreno() != null) {
            _hashCode += getVocEfRimbPrepTerreno().hashCode();
        }
        if (getVocEfRocciosita() != null) {
            _hashCode += getVocEfRocciosita().hashCode();
        }
        if (getVocEfStatoVegetativoRinn() != null) {
            _hashCode += getVocEfStatoVegetativoRinn().hashCode();
        }
        if (getVocEfTipoColturale() != null) {
            _hashCode += getVocEfTipoColturale().hashCode();
        }
        if (getVocEfTipoCopForestale() != null) {
            _hashCode += getVocEfTipoCopForestale().hashCode();
        }
        if (getVocEfTipologiaUsoSuolo() != null) {
            _hashCode += getVocEfTipologiaUsoSuolo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostSubparticellaForestale.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSubparticellaForestale"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altezzaDominante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "altezzaDominante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altezzaMedia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "altezzaMedia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoInizioTitolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoInizioTitolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("arbustetiBoschiPascolati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "arbustetiBoschiPascolati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classeProvvigInf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "classeProvvigInf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classeProvvigSup");
        elemField.setXmlName(new javax.xml.namespace.QName("", "classeProvvigSup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coefficienteCoperturaArborea");
        elemField.setXmlName(new javax.xml.namespace.QName("", "coefficienteCoperturaArborea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("compSpecSubparticellas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "compSpecSubparticellas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "compSpecSubparticella"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consuetudini");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consuetudini"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("danniSubparticellas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "danniSubparticellas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "danniSubparticella"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("diametroMedio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "diametroMedio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("durataTitolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "durataTitolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enfiteusi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "enfiteusi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estremiTitolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estremiTitolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("etaPrevalente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "etaPrevalente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fidePascolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fidePascolo"));
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
        elemField.setFieldName("interventiRecenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "interventiRecenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numMedioPolloniCeppaia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numMedioPolloniCeppaia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percPrimaSpecie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "percPrimaSpecie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percPrimaSpecieRinn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "percPrimaSpecieRinn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percSecondaSpecie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "percSecondaSpecie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percSecondaSpecieRinn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "percSecondaSpecieRinn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("presenzaGravami");
        elemField.setXmlName(new javax.xml.namespace.QName("", "presenzaGravami"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("presenzaServitu");
        elemField.setXmlName(new javax.xml.namespace.QName("", "presenzaServitu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaSpecie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primaSpecie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "specie"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaSpecieRinn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primaSpecieRinn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "specie"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primoTurnoMatricine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primoTurnoMatricine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rimbBuche");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rimbBuche"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rimbSestoImpSulleFile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rimbSestoImpSulleFile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rimbSestoImpTraFile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rimbSestoImpTraFile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("secondaSpecie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "secondaSpecie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "specie"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("secondaSpecieRinn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "secondaSpecieRinn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "specie"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("secondoTurnoMatricine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "secondoTurnoMatricine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
        elemField.setFieldName("tarePercentuale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tarePercentuale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tareSuperficie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tareSuperficie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terzoTurnoMatricine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "terzoTurnoMatricine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usiCivici");
        elemField.setXmlName(new javax.xml.namespace.QName("", "usiCivici"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfAbbRinnFustaie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfAbbRinnFustaie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfAbbRinnFustaie"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfAccidentalita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfAccidentalita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfAccidentalita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfClasseEta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfClasseEta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfClasseEta"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfDistrMatricinatura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfDistrMatricinatura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfDistrMatricinatura"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfEntitaMatricinatura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfEntitaMatricinatura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfEntitaMatricinatura"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfFunzionePrevalente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfFunzionePrevalente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfFunzionePrevalente"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfGEvolCopForestale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfGEvolCopForestale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfGEvolCopForestale"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfModelloCombustibile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfModelloCombustibile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfModelloCombustibile"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfNumCeppaieEttaro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfNumCeppaieEttaro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfNumCeppaieEttaro"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfOrSelvicolturale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfOrSelvicolturale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfOrSelvicolturale"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfOrigineBosco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfOrigineBosco"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfOrigineBosco"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfPietrosita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfPietrosita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfPietrosita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfProfonditaSuolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfProfonditaSuolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfProfonditaSuolo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfRimbPrepTerreno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfRimbPrepTerreno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfRimbPrepTerreno"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfRocciosita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfRocciosita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfRocciosita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfStatoVegetativoRinn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfStatoVegetativoRinn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfStatoVegetativoRinn"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfTipoColturale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfTipoColturale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipoColturale"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfTipoCopForestale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfTipoCopForestale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipoCopForestale"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfTipologiaUsoSuolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfTipologiaUsoSuolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipologiaUsoSuolo"));
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
