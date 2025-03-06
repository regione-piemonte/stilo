/**
 * CcostStrutturaEf.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostStrutturaEf  implements java.io.Serializable {
    private java.lang.Integer agibilita;

    private java.lang.Integer annoInizioTitolo;

    private com.hyperborea.sira.ws.ApprovvAntincendio approvvAntincendio;

    private com.hyperborea.sira.ws.AutoparchiAntincendio autoparchiAntincendio;

    private com.hyperborea.sira.ws.CcostAutoparcoEf ccostAutoparcoEf;

    private com.hyperborea.sira.ws.CcostFabbricatoEf ccostFabbricatoEf;

    private com.hyperborea.sira.ws.CcostOpificioEf ccostOpificioEf;

    private java.lang.String codice;

    private java.lang.String codiceRischi;

    private java.util.Calendar dataCostruzione;

    private java.util.Calendar dataManutenzione;

    private java.lang.String datiConcessione;

    private java.lang.String destinazioneUso;

    private java.lang.Integer durataTitolo;

    private java.lang.String estremiContratto;

    private java.lang.String estremiTitolo;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.ImpiantiStrutturaEf[] impiantiStrutturaEfs;

    private java.lang.String servizioTerritoriale;

    private java.lang.Float superficie;

    private java.lang.String utilizzoMilitare;

    private com.hyperborea.sira.ws.VedetteAntincendio vedetteAntincendio;

    private com.hyperborea.sira.ws.VocEfStatoUso vocEfStatoUso;

    private com.hyperborea.sira.ws.VocEfTipologiaTitoloGestione vocEfTipologiaTitoloGestione;

    public CcostStrutturaEf() {
    }

    public CcostStrutturaEf(
           java.lang.Integer agibilita,
           java.lang.Integer annoInizioTitolo,
           com.hyperborea.sira.ws.ApprovvAntincendio approvvAntincendio,
           com.hyperborea.sira.ws.AutoparchiAntincendio autoparchiAntincendio,
           com.hyperborea.sira.ws.CcostAutoparcoEf ccostAutoparcoEf,
           com.hyperborea.sira.ws.CcostFabbricatoEf ccostFabbricatoEf,
           com.hyperborea.sira.ws.CcostOpificioEf ccostOpificioEf,
           java.lang.String codice,
           java.lang.String codiceRischi,
           java.util.Calendar dataCostruzione,
           java.util.Calendar dataManutenzione,
           java.lang.String datiConcessione,
           java.lang.String destinazioneUso,
           java.lang.Integer durataTitolo,
           java.lang.String estremiContratto,
           java.lang.String estremiTitolo,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.ImpiantiStrutturaEf[] impiantiStrutturaEfs,
           java.lang.String servizioTerritoriale,
           java.lang.Float superficie,
           java.lang.String utilizzoMilitare,
           com.hyperborea.sira.ws.VedetteAntincendio vedetteAntincendio,
           com.hyperborea.sira.ws.VocEfStatoUso vocEfStatoUso,
           com.hyperborea.sira.ws.VocEfTipologiaTitoloGestione vocEfTipologiaTitoloGestione) {
           this.agibilita = agibilita;
           this.annoInizioTitolo = annoInizioTitolo;
           this.approvvAntincendio = approvvAntincendio;
           this.autoparchiAntincendio = autoparchiAntincendio;
           this.ccostAutoparcoEf = ccostAutoparcoEf;
           this.ccostFabbricatoEf = ccostFabbricatoEf;
           this.ccostOpificioEf = ccostOpificioEf;
           this.codice = codice;
           this.codiceRischi = codiceRischi;
           this.dataCostruzione = dataCostruzione;
           this.dataManutenzione = dataManutenzione;
           this.datiConcessione = datiConcessione;
           this.destinazioneUso = destinazioneUso;
           this.durataTitolo = durataTitolo;
           this.estremiContratto = estremiContratto;
           this.estremiTitolo = estremiTitolo;
           this.idCcost = idCcost;
           this.impiantiStrutturaEfs = impiantiStrutturaEfs;
           this.servizioTerritoriale = servizioTerritoriale;
           this.superficie = superficie;
           this.utilizzoMilitare = utilizzoMilitare;
           this.vedetteAntincendio = vedetteAntincendio;
           this.vocEfStatoUso = vocEfStatoUso;
           this.vocEfTipologiaTitoloGestione = vocEfTipologiaTitoloGestione;
    }


    /**
     * Gets the agibilita value for this CcostStrutturaEf.
     * 
     * @return agibilita
     */
    public java.lang.Integer getAgibilita() {
        return agibilita;
    }


    /**
     * Sets the agibilita value for this CcostStrutturaEf.
     * 
     * @param agibilita
     */
    public void setAgibilita(java.lang.Integer agibilita) {
        this.agibilita = agibilita;
    }


    /**
     * Gets the annoInizioTitolo value for this CcostStrutturaEf.
     * 
     * @return annoInizioTitolo
     */
    public java.lang.Integer getAnnoInizioTitolo() {
        return annoInizioTitolo;
    }


    /**
     * Sets the annoInizioTitolo value for this CcostStrutturaEf.
     * 
     * @param annoInizioTitolo
     */
    public void setAnnoInizioTitolo(java.lang.Integer annoInizioTitolo) {
        this.annoInizioTitolo = annoInizioTitolo;
    }


    /**
     * Gets the approvvAntincendio value for this CcostStrutturaEf.
     * 
     * @return approvvAntincendio
     */
    public com.hyperborea.sira.ws.ApprovvAntincendio getApprovvAntincendio() {
        return approvvAntincendio;
    }


    /**
     * Sets the approvvAntincendio value for this CcostStrutturaEf.
     * 
     * @param approvvAntincendio
     */
    public void setApprovvAntincendio(com.hyperborea.sira.ws.ApprovvAntincendio approvvAntincendio) {
        this.approvvAntincendio = approvvAntincendio;
    }


    /**
     * Gets the autoparchiAntincendio value for this CcostStrutturaEf.
     * 
     * @return autoparchiAntincendio
     */
    public com.hyperborea.sira.ws.AutoparchiAntincendio getAutoparchiAntincendio() {
        return autoparchiAntincendio;
    }


    /**
     * Sets the autoparchiAntincendio value for this CcostStrutturaEf.
     * 
     * @param autoparchiAntincendio
     */
    public void setAutoparchiAntincendio(com.hyperborea.sira.ws.AutoparchiAntincendio autoparchiAntincendio) {
        this.autoparchiAntincendio = autoparchiAntincendio;
    }


    /**
     * Gets the ccostAutoparcoEf value for this CcostStrutturaEf.
     * 
     * @return ccostAutoparcoEf
     */
    public com.hyperborea.sira.ws.CcostAutoparcoEf getCcostAutoparcoEf() {
        return ccostAutoparcoEf;
    }


    /**
     * Sets the ccostAutoparcoEf value for this CcostStrutturaEf.
     * 
     * @param ccostAutoparcoEf
     */
    public void setCcostAutoparcoEf(com.hyperborea.sira.ws.CcostAutoparcoEf ccostAutoparcoEf) {
        this.ccostAutoparcoEf = ccostAutoparcoEf;
    }


    /**
     * Gets the ccostFabbricatoEf value for this CcostStrutturaEf.
     * 
     * @return ccostFabbricatoEf
     */
    public com.hyperborea.sira.ws.CcostFabbricatoEf getCcostFabbricatoEf() {
        return ccostFabbricatoEf;
    }


    /**
     * Sets the ccostFabbricatoEf value for this CcostStrutturaEf.
     * 
     * @param ccostFabbricatoEf
     */
    public void setCcostFabbricatoEf(com.hyperborea.sira.ws.CcostFabbricatoEf ccostFabbricatoEf) {
        this.ccostFabbricatoEf = ccostFabbricatoEf;
    }


    /**
     * Gets the ccostOpificioEf value for this CcostStrutturaEf.
     * 
     * @return ccostOpificioEf
     */
    public com.hyperborea.sira.ws.CcostOpificioEf getCcostOpificioEf() {
        return ccostOpificioEf;
    }


    /**
     * Sets the ccostOpificioEf value for this CcostStrutturaEf.
     * 
     * @param ccostOpificioEf
     */
    public void setCcostOpificioEf(com.hyperborea.sira.ws.CcostOpificioEf ccostOpificioEf) {
        this.ccostOpificioEf = ccostOpificioEf;
    }


    /**
     * Gets the codice value for this CcostStrutturaEf.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this CcostStrutturaEf.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the codiceRischi value for this CcostStrutturaEf.
     * 
     * @return codiceRischi
     */
    public java.lang.String getCodiceRischi() {
        return codiceRischi;
    }


    /**
     * Sets the codiceRischi value for this CcostStrutturaEf.
     * 
     * @param codiceRischi
     */
    public void setCodiceRischi(java.lang.String codiceRischi) {
        this.codiceRischi = codiceRischi;
    }


    /**
     * Gets the dataCostruzione value for this CcostStrutturaEf.
     * 
     * @return dataCostruzione
     */
    public java.util.Calendar getDataCostruzione() {
        return dataCostruzione;
    }


    /**
     * Sets the dataCostruzione value for this CcostStrutturaEf.
     * 
     * @param dataCostruzione
     */
    public void setDataCostruzione(java.util.Calendar dataCostruzione) {
        this.dataCostruzione = dataCostruzione;
    }


    /**
     * Gets the dataManutenzione value for this CcostStrutturaEf.
     * 
     * @return dataManutenzione
     */
    public java.util.Calendar getDataManutenzione() {
        return dataManutenzione;
    }


    /**
     * Sets the dataManutenzione value for this CcostStrutturaEf.
     * 
     * @param dataManutenzione
     */
    public void setDataManutenzione(java.util.Calendar dataManutenzione) {
        this.dataManutenzione = dataManutenzione;
    }


    /**
     * Gets the datiConcessione value for this CcostStrutturaEf.
     * 
     * @return datiConcessione
     */
    public java.lang.String getDatiConcessione() {
        return datiConcessione;
    }


    /**
     * Sets the datiConcessione value for this CcostStrutturaEf.
     * 
     * @param datiConcessione
     */
    public void setDatiConcessione(java.lang.String datiConcessione) {
        this.datiConcessione = datiConcessione;
    }


    /**
     * Gets the destinazioneUso value for this CcostStrutturaEf.
     * 
     * @return destinazioneUso
     */
    public java.lang.String getDestinazioneUso() {
        return destinazioneUso;
    }


    /**
     * Sets the destinazioneUso value for this CcostStrutturaEf.
     * 
     * @param destinazioneUso
     */
    public void setDestinazioneUso(java.lang.String destinazioneUso) {
        this.destinazioneUso = destinazioneUso;
    }


    /**
     * Gets the durataTitolo value for this CcostStrutturaEf.
     * 
     * @return durataTitolo
     */
    public java.lang.Integer getDurataTitolo() {
        return durataTitolo;
    }


    /**
     * Sets the durataTitolo value for this CcostStrutturaEf.
     * 
     * @param durataTitolo
     */
    public void setDurataTitolo(java.lang.Integer durataTitolo) {
        this.durataTitolo = durataTitolo;
    }


    /**
     * Gets the estremiContratto value for this CcostStrutturaEf.
     * 
     * @return estremiContratto
     */
    public java.lang.String getEstremiContratto() {
        return estremiContratto;
    }


    /**
     * Sets the estremiContratto value for this CcostStrutturaEf.
     * 
     * @param estremiContratto
     */
    public void setEstremiContratto(java.lang.String estremiContratto) {
        this.estremiContratto = estremiContratto;
    }


    /**
     * Gets the estremiTitolo value for this CcostStrutturaEf.
     * 
     * @return estremiTitolo
     */
    public java.lang.String getEstremiTitolo() {
        return estremiTitolo;
    }


    /**
     * Sets the estremiTitolo value for this CcostStrutturaEf.
     * 
     * @param estremiTitolo
     */
    public void setEstremiTitolo(java.lang.String estremiTitolo) {
        this.estremiTitolo = estremiTitolo;
    }


    /**
     * Gets the idCcost value for this CcostStrutturaEf.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostStrutturaEf.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the impiantiStrutturaEfs value for this CcostStrutturaEf.
     * 
     * @return impiantiStrutturaEfs
     */
    public com.hyperborea.sira.ws.ImpiantiStrutturaEf[] getImpiantiStrutturaEfs() {
        return impiantiStrutturaEfs;
    }


    /**
     * Sets the impiantiStrutturaEfs value for this CcostStrutturaEf.
     * 
     * @param impiantiStrutturaEfs
     */
    public void setImpiantiStrutturaEfs(com.hyperborea.sira.ws.ImpiantiStrutturaEf[] impiantiStrutturaEfs) {
        this.impiantiStrutturaEfs = impiantiStrutturaEfs;
    }

    public com.hyperborea.sira.ws.ImpiantiStrutturaEf getImpiantiStrutturaEfs(int i) {
        return this.impiantiStrutturaEfs[i];
    }

    public void setImpiantiStrutturaEfs(int i, com.hyperborea.sira.ws.ImpiantiStrutturaEf _value) {
        this.impiantiStrutturaEfs[i] = _value;
    }


    /**
     * Gets the servizioTerritoriale value for this CcostStrutturaEf.
     * 
     * @return servizioTerritoriale
     */
    public java.lang.String getServizioTerritoriale() {
        return servizioTerritoriale;
    }


    /**
     * Sets the servizioTerritoriale value for this CcostStrutturaEf.
     * 
     * @param servizioTerritoriale
     */
    public void setServizioTerritoriale(java.lang.String servizioTerritoriale) {
        this.servizioTerritoriale = servizioTerritoriale;
    }


    /**
     * Gets the superficie value for this CcostStrutturaEf.
     * 
     * @return superficie
     */
    public java.lang.Float getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this CcostStrutturaEf.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Float superficie) {
        this.superficie = superficie;
    }


    /**
     * Gets the utilizzoMilitare value for this CcostStrutturaEf.
     * 
     * @return utilizzoMilitare
     */
    public java.lang.String getUtilizzoMilitare() {
        return utilizzoMilitare;
    }


    /**
     * Sets the utilizzoMilitare value for this CcostStrutturaEf.
     * 
     * @param utilizzoMilitare
     */
    public void setUtilizzoMilitare(java.lang.String utilizzoMilitare) {
        this.utilizzoMilitare = utilizzoMilitare;
    }


    /**
     * Gets the vedetteAntincendio value for this CcostStrutturaEf.
     * 
     * @return vedetteAntincendio
     */
    public com.hyperborea.sira.ws.VedetteAntincendio getVedetteAntincendio() {
        return vedetteAntincendio;
    }


    /**
     * Sets the vedetteAntincendio value for this CcostStrutturaEf.
     * 
     * @param vedetteAntincendio
     */
    public void setVedetteAntincendio(com.hyperborea.sira.ws.VedetteAntincendio vedetteAntincendio) {
        this.vedetteAntincendio = vedetteAntincendio;
    }


    /**
     * Gets the vocEfStatoUso value for this CcostStrutturaEf.
     * 
     * @return vocEfStatoUso
     */
    public com.hyperborea.sira.ws.VocEfStatoUso getVocEfStatoUso() {
        return vocEfStatoUso;
    }


    /**
     * Sets the vocEfStatoUso value for this CcostStrutturaEf.
     * 
     * @param vocEfStatoUso
     */
    public void setVocEfStatoUso(com.hyperborea.sira.ws.VocEfStatoUso vocEfStatoUso) {
        this.vocEfStatoUso = vocEfStatoUso;
    }


    /**
     * Gets the vocEfTipologiaTitoloGestione value for this CcostStrutturaEf.
     * 
     * @return vocEfTipologiaTitoloGestione
     */
    public com.hyperborea.sira.ws.VocEfTipologiaTitoloGestione getVocEfTipologiaTitoloGestione() {
        return vocEfTipologiaTitoloGestione;
    }


    /**
     * Sets the vocEfTipologiaTitoloGestione value for this CcostStrutturaEf.
     * 
     * @param vocEfTipologiaTitoloGestione
     */
    public void setVocEfTipologiaTitoloGestione(com.hyperborea.sira.ws.VocEfTipologiaTitoloGestione vocEfTipologiaTitoloGestione) {
        this.vocEfTipologiaTitoloGestione = vocEfTipologiaTitoloGestione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostStrutturaEf)) return false;
        CcostStrutturaEf other = (CcostStrutturaEf) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.agibilita==null && other.getAgibilita()==null) || 
             (this.agibilita!=null &&
              this.agibilita.equals(other.getAgibilita()))) &&
            ((this.annoInizioTitolo==null && other.getAnnoInizioTitolo()==null) || 
             (this.annoInizioTitolo!=null &&
              this.annoInizioTitolo.equals(other.getAnnoInizioTitolo()))) &&
            ((this.approvvAntincendio==null && other.getApprovvAntincendio()==null) || 
             (this.approvvAntincendio!=null &&
              this.approvvAntincendio.equals(other.getApprovvAntincendio()))) &&
            ((this.autoparchiAntincendio==null && other.getAutoparchiAntincendio()==null) || 
             (this.autoparchiAntincendio!=null &&
              this.autoparchiAntincendio.equals(other.getAutoparchiAntincendio()))) &&
            ((this.ccostAutoparcoEf==null && other.getCcostAutoparcoEf()==null) || 
             (this.ccostAutoparcoEf!=null &&
              this.ccostAutoparcoEf.equals(other.getCcostAutoparcoEf()))) &&
            ((this.ccostFabbricatoEf==null && other.getCcostFabbricatoEf()==null) || 
             (this.ccostFabbricatoEf!=null &&
              this.ccostFabbricatoEf.equals(other.getCcostFabbricatoEf()))) &&
            ((this.ccostOpificioEf==null && other.getCcostOpificioEf()==null) || 
             (this.ccostOpificioEf!=null &&
              this.ccostOpificioEf.equals(other.getCcostOpificioEf()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.codiceRischi==null && other.getCodiceRischi()==null) || 
             (this.codiceRischi!=null &&
              this.codiceRischi.equals(other.getCodiceRischi()))) &&
            ((this.dataCostruzione==null && other.getDataCostruzione()==null) || 
             (this.dataCostruzione!=null &&
              this.dataCostruzione.equals(other.getDataCostruzione()))) &&
            ((this.dataManutenzione==null && other.getDataManutenzione()==null) || 
             (this.dataManutenzione!=null &&
              this.dataManutenzione.equals(other.getDataManutenzione()))) &&
            ((this.datiConcessione==null && other.getDatiConcessione()==null) || 
             (this.datiConcessione!=null &&
              this.datiConcessione.equals(other.getDatiConcessione()))) &&
            ((this.destinazioneUso==null && other.getDestinazioneUso()==null) || 
             (this.destinazioneUso!=null &&
              this.destinazioneUso.equals(other.getDestinazioneUso()))) &&
            ((this.durataTitolo==null && other.getDurataTitolo()==null) || 
             (this.durataTitolo!=null &&
              this.durataTitolo.equals(other.getDurataTitolo()))) &&
            ((this.estremiContratto==null && other.getEstremiContratto()==null) || 
             (this.estremiContratto!=null &&
              this.estremiContratto.equals(other.getEstremiContratto()))) &&
            ((this.estremiTitolo==null && other.getEstremiTitolo()==null) || 
             (this.estremiTitolo!=null &&
              this.estremiTitolo.equals(other.getEstremiTitolo()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.impiantiStrutturaEfs==null && other.getImpiantiStrutturaEfs()==null) || 
             (this.impiantiStrutturaEfs!=null &&
              java.util.Arrays.equals(this.impiantiStrutturaEfs, other.getImpiantiStrutturaEfs()))) &&
            ((this.servizioTerritoriale==null && other.getServizioTerritoriale()==null) || 
             (this.servizioTerritoriale!=null &&
              this.servizioTerritoriale.equals(other.getServizioTerritoriale()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie()))) &&
            ((this.utilizzoMilitare==null && other.getUtilizzoMilitare()==null) || 
             (this.utilizzoMilitare!=null &&
              this.utilizzoMilitare.equals(other.getUtilizzoMilitare()))) &&
            ((this.vedetteAntincendio==null && other.getVedetteAntincendio()==null) || 
             (this.vedetteAntincendio!=null &&
              this.vedetteAntincendio.equals(other.getVedetteAntincendio()))) &&
            ((this.vocEfStatoUso==null && other.getVocEfStatoUso()==null) || 
             (this.vocEfStatoUso!=null &&
              this.vocEfStatoUso.equals(other.getVocEfStatoUso()))) &&
            ((this.vocEfTipologiaTitoloGestione==null && other.getVocEfTipologiaTitoloGestione()==null) || 
             (this.vocEfTipologiaTitoloGestione!=null &&
              this.vocEfTipologiaTitoloGestione.equals(other.getVocEfTipologiaTitoloGestione())));
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
        if (getAgibilita() != null) {
            _hashCode += getAgibilita().hashCode();
        }
        if (getAnnoInizioTitolo() != null) {
            _hashCode += getAnnoInizioTitolo().hashCode();
        }
        if (getApprovvAntincendio() != null) {
            _hashCode += getApprovvAntincendio().hashCode();
        }
        if (getAutoparchiAntincendio() != null) {
            _hashCode += getAutoparchiAntincendio().hashCode();
        }
        if (getCcostAutoparcoEf() != null) {
            _hashCode += getCcostAutoparcoEf().hashCode();
        }
        if (getCcostFabbricatoEf() != null) {
            _hashCode += getCcostFabbricatoEf().hashCode();
        }
        if (getCcostOpificioEf() != null) {
            _hashCode += getCcostOpificioEf().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getCodiceRischi() != null) {
            _hashCode += getCodiceRischi().hashCode();
        }
        if (getDataCostruzione() != null) {
            _hashCode += getDataCostruzione().hashCode();
        }
        if (getDataManutenzione() != null) {
            _hashCode += getDataManutenzione().hashCode();
        }
        if (getDatiConcessione() != null) {
            _hashCode += getDatiConcessione().hashCode();
        }
        if (getDestinazioneUso() != null) {
            _hashCode += getDestinazioneUso().hashCode();
        }
        if (getDurataTitolo() != null) {
            _hashCode += getDurataTitolo().hashCode();
        }
        if (getEstremiContratto() != null) {
            _hashCode += getEstremiContratto().hashCode();
        }
        if (getEstremiTitolo() != null) {
            _hashCode += getEstremiTitolo().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getImpiantiStrutturaEfs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getImpiantiStrutturaEfs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getImpiantiStrutturaEfs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getServizioTerritoriale() != null) {
            _hashCode += getServizioTerritoriale().hashCode();
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        if (getUtilizzoMilitare() != null) {
            _hashCode += getUtilizzoMilitare().hashCode();
        }
        if (getVedetteAntincendio() != null) {
            _hashCode += getVedetteAntincendio().hashCode();
        }
        if (getVocEfStatoUso() != null) {
            _hashCode += getVocEfStatoUso().hashCode();
        }
        if (getVocEfTipologiaTitoloGestione() != null) {
            _hashCode += getVocEfTipologiaTitoloGestione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostStrutturaEf.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostStrutturaEf"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agibilita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agibilita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("approvvAntincendio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "approvvAntincendio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "approvvAntincendio"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoparchiAntincendio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "autoparchiAntincendio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "autoparchiAntincendio"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostAutoparcoEf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostAutoparcoEf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAutoparcoEf"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostFabbricatoEf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostFabbricatoEf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostFabbricatoEf"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostOpificioEf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostOpificioEf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostOpificioEf"));
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
        elemField.setFieldName("codiceRischi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceRischi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataCostruzione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataCostruzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataManutenzione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataManutenzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datiConcessione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datiConcessione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinazioneUso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destinazioneUso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("estremiContratto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estremiContratto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("impiantiStrutturaEfs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "impiantiStrutturaEfs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "impiantiStrutturaEf"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servizioTerritoriale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servizioTerritoriale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("utilizzoMilitare");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilizzoMilitare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vedetteAntincendio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vedetteAntincendio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vedetteAntincendio"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfStatoUso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfStatoUso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfStatoUso"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfTipologiaTitoloGestione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfTipologiaTitoloGestione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipologiaTitoloGestione"));
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
