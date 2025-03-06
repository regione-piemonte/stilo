/**
 * CaratterizzazioniOst.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CaratterizzazioniOst  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CcostAmbitoTerritoriale ccostAmbitoTerritoriale;

    private com.hyperborea.sira.ws.CcostEs ccostElementiSignificativi;

    private com.hyperborea.sira.ws.CcostImpdisinquinamento ccostImpdisinquinamento;

    private com.hyperborea.sira.ws.CcostImpiantoProdEnergia ccostImpiantoProdEnergia;

    private com.hyperborea.sira.ws.CcostInfrasTerrit ccostInfrasTerrit;

    private com.hyperborea.sira.ws.CcostOggIdrografico ccostOggIdrografico;

    private com.hyperborea.sira.ws.CcostSoggettiGiuridici ccostSoggettiGiuridici;

    private java.util.Calendar dataFine;

    private java.util.Calendar dataInizio;

    private java.util.Calendar dataValPFR;

    private java.util.Calendar dataValUtente;

    private java.lang.String denoAlias;

    private java.lang.String denominazione;

    private org.apache.axis.types.UnsignedShort flagUfficiale;

    private org.apache.axis.types.UnsignedShort flagValPFR;

    private org.apache.axis.types.UnsignedShort flagValUtente;

    private com.hyperborea.sira.ws.FontiDati fontiDati;

    private java.lang.Integer idCcost;

    private java.lang.String idOstEsterno;

    private java.lang.String idUtente;

    private java.lang.String idUtentePFR;

    private java.lang.String note;

    private com.hyperborea.sira.ws.TipologieFontiDati tipologieFontiDati;

    private com.hyperborea.sira.ws.UbicazioniOst ubicazioniOst;

    private com.hyperborea.sira.ws.WsFonteRef wsFonteRef;

    public CaratterizzazioniOst() {
    }

    public CaratterizzazioniOst(
           com.hyperborea.sira.ws.CcostAmbitoTerritoriale ccostAmbitoTerritoriale,
           com.hyperborea.sira.ws.CcostEs ccostElementiSignificativi,
           com.hyperborea.sira.ws.CcostImpdisinquinamento ccostImpdisinquinamento,
           com.hyperborea.sira.ws.CcostImpiantoProdEnergia ccostImpiantoProdEnergia,
           com.hyperborea.sira.ws.CcostInfrasTerrit ccostInfrasTerrit,
           com.hyperborea.sira.ws.CcostOggIdrografico ccostOggIdrografico,
           com.hyperborea.sira.ws.CcostSoggettiGiuridici ccostSoggettiGiuridici,
           java.util.Calendar dataFine,
           java.util.Calendar dataInizio,
           java.util.Calendar dataValPFR,
           java.util.Calendar dataValUtente,
           java.lang.String denoAlias,
           java.lang.String denominazione,
           org.apache.axis.types.UnsignedShort flagUfficiale,
           org.apache.axis.types.UnsignedShort flagValPFR,
           org.apache.axis.types.UnsignedShort flagValUtente,
           com.hyperborea.sira.ws.FontiDati fontiDati,
           java.lang.Integer idCcost,
           java.lang.String idOstEsterno,
           java.lang.String idUtente,
           java.lang.String idUtentePFR,
           java.lang.String note,
           com.hyperborea.sira.ws.TipologieFontiDati tipologieFontiDati,
           com.hyperborea.sira.ws.UbicazioniOst ubicazioniOst,
           com.hyperborea.sira.ws.WsFonteRef wsFonteRef) {
           this.ccostAmbitoTerritoriale = ccostAmbitoTerritoriale;
           this.ccostElementiSignificativi = ccostElementiSignificativi;
           this.ccostImpdisinquinamento = ccostImpdisinquinamento;
           this.ccostImpiantoProdEnergia = ccostImpiantoProdEnergia;
           this.ccostInfrasTerrit = ccostInfrasTerrit;
           this.ccostOggIdrografico = ccostOggIdrografico;
           this.ccostSoggettiGiuridici = ccostSoggettiGiuridici;
           this.dataFine = dataFine;
           this.dataInizio = dataInizio;
           this.dataValPFR = dataValPFR;
           this.dataValUtente = dataValUtente;
           this.denoAlias = denoAlias;
           this.denominazione = denominazione;
           this.flagUfficiale = flagUfficiale;
           this.flagValPFR = flagValPFR;
           this.flagValUtente = flagValUtente;
           this.fontiDati = fontiDati;
           this.idCcost = idCcost;
           this.idOstEsterno = idOstEsterno;
           this.idUtente = idUtente;
           this.idUtentePFR = idUtentePFR;
           this.note = note;
           this.tipologieFontiDati = tipologieFontiDati;
           this.ubicazioniOst = ubicazioniOst;
           this.wsFonteRef = wsFonteRef;
    }


    /**
     * Gets the ccostAmbitoTerritoriale value for this CaratterizzazioniOst.
     * 
     * @return ccostAmbitoTerritoriale
     */
    public com.hyperborea.sira.ws.CcostAmbitoTerritoriale getCcostAmbitoTerritoriale() {
        return ccostAmbitoTerritoriale;
    }


    /**
     * Sets the ccostAmbitoTerritoriale value for this CaratterizzazioniOst.
     * 
     * @param ccostAmbitoTerritoriale
     */
    public void setCcostAmbitoTerritoriale(com.hyperborea.sira.ws.CcostAmbitoTerritoriale ccostAmbitoTerritoriale) {
        this.ccostAmbitoTerritoriale = ccostAmbitoTerritoriale;
    }


    /**
     * Gets the ccostElementiSignificativi value for this CaratterizzazioniOst.
     * 
     * @return ccostElementiSignificativi
     */
    public com.hyperborea.sira.ws.CcostEs getCcostElementiSignificativi() {
        return ccostElementiSignificativi;
    }


    /**
     * Sets the ccostElementiSignificativi value for this CaratterizzazioniOst.
     * 
     * @param ccostElementiSignificativi
     */
    public void setCcostElementiSignificativi(com.hyperborea.sira.ws.CcostEs ccostElementiSignificativi) {
        this.ccostElementiSignificativi = ccostElementiSignificativi;
    }


    /**
     * Gets the ccostImpdisinquinamento value for this CaratterizzazioniOst.
     * 
     * @return ccostImpdisinquinamento
     */
    public com.hyperborea.sira.ws.CcostImpdisinquinamento getCcostImpdisinquinamento() {
        return ccostImpdisinquinamento;
    }


    /**
     * Sets the ccostImpdisinquinamento value for this CaratterizzazioniOst.
     * 
     * @param ccostImpdisinquinamento
     */
    public void setCcostImpdisinquinamento(com.hyperborea.sira.ws.CcostImpdisinquinamento ccostImpdisinquinamento) {
        this.ccostImpdisinquinamento = ccostImpdisinquinamento;
    }


    /**
     * Gets the ccostImpiantoProdEnergia value for this CaratterizzazioniOst.
     * 
     * @return ccostImpiantoProdEnergia
     */
    public com.hyperborea.sira.ws.CcostImpiantoProdEnergia getCcostImpiantoProdEnergia() {
        return ccostImpiantoProdEnergia;
    }


    /**
     * Sets the ccostImpiantoProdEnergia value for this CaratterizzazioniOst.
     * 
     * @param ccostImpiantoProdEnergia
     */
    public void setCcostImpiantoProdEnergia(com.hyperborea.sira.ws.CcostImpiantoProdEnergia ccostImpiantoProdEnergia) {
        this.ccostImpiantoProdEnergia = ccostImpiantoProdEnergia;
    }


    /**
     * Gets the ccostInfrasTerrit value for this CaratterizzazioniOst.
     * 
     * @return ccostInfrasTerrit
     */
    public com.hyperborea.sira.ws.CcostInfrasTerrit getCcostInfrasTerrit() {
        return ccostInfrasTerrit;
    }


    /**
     * Sets the ccostInfrasTerrit value for this CaratterizzazioniOst.
     * 
     * @param ccostInfrasTerrit
     */
    public void setCcostInfrasTerrit(com.hyperborea.sira.ws.CcostInfrasTerrit ccostInfrasTerrit) {
        this.ccostInfrasTerrit = ccostInfrasTerrit;
    }


    /**
     * Gets the ccostOggIdrografico value for this CaratterizzazioniOst.
     * 
     * @return ccostOggIdrografico
     */
    public com.hyperborea.sira.ws.CcostOggIdrografico getCcostOggIdrografico() {
        return ccostOggIdrografico;
    }


    /**
     * Sets the ccostOggIdrografico value for this CaratterizzazioniOst.
     * 
     * @param ccostOggIdrografico
     */
    public void setCcostOggIdrografico(com.hyperborea.sira.ws.CcostOggIdrografico ccostOggIdrografico) {
        this.ccostOggIdrografico = ccostOggIdrografico;
    }


    /**
     * Gets the ccostSoggettiGiuridici value for this CaratterizzazioniOst.
     * 
     * @return ccostSoggettiGiuridici
     */
    public com.hyperborea.sira.ws.CcostSoggettiGiuridici getCcostSoggettiGiuridici() {
        return ccostSoggettiGiuridici;
    }


    /**
     * Sets the ccostSoggettiGiuridici value for this CaratterizzazioniOst.
     * 
     * @param ccostSoggettiGiuridici
     */
    public void setCcostSoggettiGiuridici(com.hyperborea.sira.ws.CcostSoggettiGiuridici ccostSoggettiGiuridici) {
        this.ccostSoggettiGiuridici = ccostSoggettiGiuridici;
    }


    /**
     * Gets the dataFine value for this CaratterizzazioniOst.
     * 
     * @return dataFine
     */
    public java.util.Calendar getDataFine() {
        return dataFine;
    }


    /**
     * Sets the dataFine value for this CaratterizzazioniOst.
     * 
     * @param dataFine
     */
    public void setDataFine(java.util.Calendar dataFine) {
        this.dataFine = dataFine;
    }


    /**
     * Gets the dataInizio value for this CaratterizzazioniOst.
     * 
     * @return dataInizio
     */
    public java.util.Calendar getDataInizio() {
        return dataInizio;
    }


    /**
     * Sets the dataInizio value for this CaratterizzazioniOst.
     * 
     * @param dataInizio
     */
    public void setDataInizio(java.util.Calendar dataInizio) {
        this.dataInizio = dataInizio;
    }


    /**
     * Gets the dataValPFR value for this CaratterizzazioniOst.
     * 
     * @return dataValPFR
     */
    public java.util.Calendar getDataValPFR() {
        return dataValPFR;
    }


    /**
     * Sets the dataValPFR value for this CaratterizzazioniOst.
     * 
     * @param dataValPFR
     */
    public void setDataValPFR(java.util.Calendar dataValPFR) {
        this.dataValPFR = dataValPFR;
    }


    /**
     * Gets the dataValUtente value for this CaratterizzazioniOst.
     * 
     * @return dataValUtente
     */
    public java.util.Calendar getDataValUtente() {
        return dataValUtente;
    }


    /**
     * Sets the dataValUtente value for this CaratterizzazioniOst.
     * 
     * @param dataValUtente
     */
    public void setDataValUtente(java.util.Calendar dataValUtente) {
        this.dataValUtente = dataValUtente;
    }


    /**
     * Gets the denoAlias value for this CaratterizzazioniOst.
     * 
     * @return denoAlias
     */
    public java.lang.String getDenoAlias() {
        return denoAlias;
    }


    /**
     * Sets the denoAlias value for this CaratterizzazioniOst.
     * 
     * @param denoAlias
     */
    public void setDenoAlias(java.lang.String denoAlias) {
        this.denoAlias = denoAlias;
    }


    /**
     * Gets the denominazione value for this CaratterizzazioniOst.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this CaratterizzazioniOst.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the flagUfficiale value for this CaratterizzazioniOst.
     * 
     * @return flagUfficiale
     */
    public org.apache.axis.types.UnsignedShort getFlagUfficiale() {
        return flagUfficiale;
    }


    /**
     * Sets the flagUfficiale value for this CaratterizzazioniOst.
     * 
     * @param flagUfficiale
     */
    public void setFlagUfficiale(org.apache.axis.types.UnsignedShort flagUfficiale) {
        this.flagUfficiale = flagUfficiale;
    }


    /**
     * Gets the flagValPFR value for this CaratterizzazioniOst.
     * 
     * @return flagValPFR
     */
    public org.apache.axis.types.UnsignedShort getFlagValPFR() {
        return flagValPFR;
    }


    /**
     * Sets the flagValPFR value for this CaratterizzazioniOst.
     * 
     * @param flagValPFR
     */
    public void setFlagValPFR(org.apache.axis.types.UnsignedShort flagValPFR) {
        this.flagValPFR = flagValPFR;
    }


    /**
     * Gets the flagValUtente value for this CaratterizzazioniOst.
     * 
     * @return flagValUtente
     */
    public org.apache.axis.types.UnsignedShort getFlagValUtente() {
        return flagValUtente;
    }


    /**
     * Sets the flagValUtente value for this CaratterizzazioniOst.
     * 
     * @param flagValUtente
     */
    public void setFlagValUtente(org.apache.axis.types.UnsignedShort flagValUtente) {
        this.flagValUtente = flagValUtente;
    }


    /**
     * Gets the fontiDati value for this CaratterizzazioniOst.
     * 
     * @return fontiDati
     */
    public com.hyperborea.sira.ws.FontiDati getFontiDati() {
        return fontiDati;
    }


    /**
     * Sets the fontiDati value for this CaratterizzazioniOst.
     * 
     * @param fontiDati
     */
    public void setFontiDati(com.hyperborea.sira.ws.FontiDati fontiDati) {
        this.fontiDati = fontiDati;
    }


    /**
     * Gets the idCcost value for this CaratterizzazioniOst.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CaratterizzazioniOst.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the idOstEsterno value for this CaratterizzazioniOst.
     * 
     * @return idOstEsterno
     */
    public java.lang.String getIdOstEsterno() {
        return idOstEsterno;
    }


    /**
     * Sets the idOstEsterno value for this CaratterizzazioniOst.
     * 
     * @param idOstEsterno
     */
    public void setIdOstEsterno(java.lang.String idOstEsterno) {
        this.idOstEsterno = idOstEsterno;
    }


    /**
     * Gets the idUtente value for this CaratterizzazioniOst.
     * 
     * @return idUtente
     */
    public java.lang.String getIdUtente() {
        return idUtente;
    }


    /**
     * Sets the idUtente value for this CaratterizzazioniOst.
     * 
     * @param idUtente
     */
    public void setIdUtente(java.lang.String idUtente) {
        this.idUtente = idUtente;
    }


    /**
     * Gets the idUtentePFR value for this CaratterizzazioniOst.
     * 
     * @return idUtentePFR
     */
    public java.lang.String getIdUtentePFR() {
        return idUtentePFR;
    }


    /**
     * Sets the idUtentePFR value for this CaratterizzazioniOst.
     * 
     * @param idUtentePFR
     */
    public void setIdUtentePFR(java.lang.String idUtentePFR) {
        this.idUtentePFR = idUtentePFR;
    }


    /**
     * Gets the note value for this CaratterizzazioniOst.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this CaratterizzazioniOst.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the tipologieFontiDati value for this CaratterizzazioniOst.
     * 
     * @return tipologieFontiDati
     */
    public com.hyperborea.sira.ws.TipologieFontiDati getTipologieFontiDati() {
        return tipologieFontiDati;
    }


    /**
     * Sets the tipologieFontiDati value for this CaratterizzazioniOst.
     * 
     * @param tipologieFontiDati
     */
    public void setTipologieFontiDati(com.hyperborea.sira.ws.TipologieFontiDati tipologieFontiDati) {
        this.tipologieFontiDati = tipologieFontiDati;
    }


    /**
     * Gets the ubicazioniOst value for this CaratterizzazioniOst.
     * 
     * @return ubicazioniOst
     */
    public com.hyperborea.sira.ws.UbicazioniOst getUbicazioniOst() {
        return ubicazioniOst;
    }


    /**
     * Sets the ubicazioniOst value for this CaratterizzazioniOst.
     * 
     * @param ubicazioniOst
     */
    public void setUbicazioniOst(com.hyperborea.sira.ws.UbicazioniOst ubicazioniOst) {
        this.ubicazioniOst = ubicazioniOst;
    }


    /**
     * Gets the wsFonteRef value for this CaratterizzazioniOst.
     * 
     * @return wsFonteRef
     */
    public com.hyperborea.sira.ws.WsFonteRef getWsFonteRef() {
        return wsFonteRef;
    }


    /**
     * Sets the wsFonteRef value for this CaratterizzazioniOst.
     * 
     * @param wsFonteRef
     */
    public void setWsFonteRef(com.hyperborea.sira.ws.WsFonteRef wsFonteRef) {
        this.wsFonteRef = wsFonteRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CaratterizzazioniOst)) return false;
        CaratterizzazioniOst other = (CaratterizzazioniOst) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ccostAmbitoTerritoriale==null && other.getCcostAmbitoTerritoriale()==null) || 
             (this.ccostAmbitoTerritoriale!=null &&
              this.ccostAmbitoTerritoriale.equals(other.getCcostAmbitoTerritoriale()))) &&
            ((this.ccostElementiSignificativi==null && other.getCcostElementiSignificativi()==null) || 
             (this.ccostElementiSignificativi!=null &&
              this.ccostElementiSignificativi.equals(other.getCcostElementiSignificativi()))) &&
            ((this.ccostImpdisinquinamento==null && other.getCcostImpdisinquinamento()==null) || 
             (this.ccostImpdisinquinamento!=null &&
              this.ccostImpdisinquinamento.equals(other.getCcostImpdisinquinamento()))) &&
            ((this.ccostImpiantoProdEnergia==null && other.getCcostImpiantoProdEnergia()==null) || 
             (this.ccostImpiantoProdEnergia!=null &&
              this.ccostImpiantoProdEnergia.equals(other.getCcostImpiantoProdEnergia()))) &&
            ((this.ccostInfrasTerrit==null && other.getCcostInfrasTerrit()==null) || 
             (this.ccostInfrasTerrit!=null &&
              this.ccostInfrasTerrit.equals(other.getCcostInfrasTerrit()))) &&
            ((this.ccostOggIdrografico==null && other.getCcostOggIdrografico()==null) || 
             (this.ccostOggIdrografico!=null &&
              this.ccostOggIdrografico.equals(other.getCcostOggIdrografico()))) &&
            ((this.ccostSoggettiGiuridici==null && other.getCcostSoggettiGiuridici()==null) || 
             (this.ccostSoggettiGiuridici!=null &&
              this.ccostSoggettiGiuridici.equals(other.getCcostSoggettiGiuridici()))) &&
            ((this.dataFine==null && other.getDataFine()==null) || 
             (this.dataFine!=null &&
              this.dataFine.equals(other.getDataFine()))) &&
            ((this.dataInizio==null && other.getDataInizio()==null) || 
             (this.dataInizio!=null &&
              this.dataInizio.equals(other.getDataInizio()))) &&
            ((this.dataValPFR==null && other.getDataValPFR()==null) || 
             (this.dataValPFR!=null &&
              this.dataValPFR.equals(other.getDataValPFR()))) &&
            ((this.dataValUtente==null && other.getDataValUtente()==null) || 
             (this.dataValUtente!=null &&
              this.dataValUtente.equals(other.getDataValUtente()))) &&
            ((this.denoAlias==null && other.getDenoAlias()==null) || 
             (this.denoAlias!=null &&
              this.denoAlias.equals(other.getDenoAlias()))) &&
            ((this.denominazione==null && other.getDenominazione()==null) || 
             (this.denominazione!=null &&
              this.denominazione.equals(other.getDenominazione()))) &&
            ((this.flagUfficiale==null && other.getFlagUfficiale()==null) || 
             (this.flagUfficiale!=null &&
              this.flagUfficiale.equals(other.getFlagUfficiale()))) &&
            ((this.flagValPFR==null && other.getFlagValPFR()==null) || 
             (this.flagValPFR!=null &&
              this.flagValPFR.equals(other.getFlagValPFR()))) &&
            ((this.flagValUtente==null && other.getFlagValUtente()==null) || 
             (this.flagValUtente!=null &&
              this.flagValUtente.equals(other.getFlagValUtente()))) &&
            ((this.fontiDati==null && other.getFontiDati()==null) || 
             (this.fontiDati!=null &&
              this.fontiDati.equals(other.getFontiDati()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.idOstEsterno==null && other.getIdOstEsterno()==null) || 
             (this.idOstEsterno!=null &&
              this.idOstEsterno.equals(other.getIdOstEsterno()))) &&
            ((this.idUtente==null && other.getIdUtente()==null) || 
             (this.idUtente!=null &&
              this.idUtente.equals(other.getIdUtente()))) &&
            ((this.idUtentePFR==null && other.getIdUtentePFR()==null) || 
             (this.idUtentePFR!=null &&
              this.idUtentePFR.equals(other.getIdUtentePFR()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.tipologieFontiDati==null && other.getTipologieFontiDati()==null) || 
             (this.tipologieFontiDati!=null &&
              this.tipologieFontiDati.equals(other.getTipologieFontiDati()))) &&
            ((this.ubicazioniOst==null && other.getUbicazioniOst()==null) || 
             (this.ubicazioniOst!=null &&
              this.ubicazioniOst.equals(other.getUbicazioniOst()))) &&
            ((this.wsFonteRef==null && other.getWsFonteRef()==null) || 
             (this.wsFonteRef!=null &&
              this.wsFonteRef.equals(other.getWsFonteRef())));
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
        if (getCcostAmbitoTerritoriale() != null) {
            _hashCode += getCcostAmbitoTerritoriale().hashCode();
        }
        if (getCcostElementiSignificativi() != null) {
            _hashCode += getCcostElementiSignificativi().hashCode();
        }
        if (getCcostImpdisinquinamento() != null) {
            _hashCode += getCcostImpdisinquinamento().hashCode();
        }
        if (getCcostImpiantoProdEnergia() != null) {
            _hashCode += getCcostImpiantoProdEnergia().hashCode();
        }
        if (getCcostInfrasTerrit() != null) {
            _hashCode += getCcostInfrasTerrit().hashCode();
        }
        if (getCcostOggIdrografico() != null) {
            _hashCode += getCcostOggIdrografico().hashCode();
        }
        if (getCcostSoggettiGiuridici() != null) {
            _hashCode += getCcostSoggettiGiuridici().hashCode();
        }
        if (getDataFine() != null) {
            _hashCode += getDataFine().hashCode();
        }
        if (getDataInizio() != null) {
            _hashCode += getDataInizio().hashCode();
        }
        if (getDataValPFR() != null) {
            _hashCode += getDataValPFR().hashCode();
        }
        if (getDataValUtente() != null) {
            _hashCode += getDataValUtente().hashCode();
        }
        if (getDenoAlias() != null) {
            _hashCode += getDenoAlias().hashCode();
        }
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
        }
        if (getFlagUfficiale() != null) {
            _hashCode += getFlagUfficiale().hashCode();
        }
        if (getFlagValPFR() != null) {
            _hashCode += getFlagValPFR().hashCode();
        }
        if (getFlagValUtente() != null) {
            _hashCode += getFlagValUtente().hashCode();
        }
        if (getFontiDati() != null) {
            _hashCode += getFontiDati().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getIdOstEsterno() != null) {
            _hashCode += getIdOstEsterno().hashCode();
        }
        if (getIdUtente() != null) {
            _hashCode += getIdUtente().hashCode();
        }
        if (getIdUtentePFR() != null) {
            _hashCode += getIdUtentePFR().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getTipologieFontiDati() != null) {
            _hashCode += getTipologieFontiDati().hashCode();
        }
        if (getUbicazioniOst() != null) {
            _hashCode += getUbicazioniOst().hashCode();
        }
        if (getWsFonteRef() != null) {
            _hashCode += getWsFonteRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CaratterizzazioniOst.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "caratterizzazioniOst"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostAmbitoTerritoriale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostAmbitoTerritoriale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAmbitoTerritoriale"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostElementiSignificativi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostElementiSignificativi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostEs"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostImpdisinquinamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostImpdisinquinamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpdisinquinamento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostImpiantoProdEnergia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostImpiantoProdEnergia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpiantoProdEnergia"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostInfrasTerrit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostInfrasTerrit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostInfrasTerrit"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostOggIdrografico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostOggIdrografico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostOggIdrografico"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostSoggettiGiuridici");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostSoggettiGiuridici"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSoggettiGiuridici"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataFine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataValPFR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataValPFR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataValUtente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataValUtente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denoAlias");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denoAlias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagUfficiale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagUfficiale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagValPFR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagValPFR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagValUtente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagValUtente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fontiDati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fontiDati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "fontiDati"));
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
        elemField.setFieldName("idOstEsterno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idOstEsterno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idUtente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idUtente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idUtentePFR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idUtentePFR"));
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
        elemField.setFieldName("tipologieFontiDati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologieFontiDati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieFontiDati"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ubicazioniOst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ubicazioniOst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ubicazioniOst"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsFonteRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsFonteRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsFonteRef"));
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
