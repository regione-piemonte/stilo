/**
 * CcostScarichiIdrici.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostScarichiIdrici  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Float abitantiEquivalenti;

    private java.lang.Integer abitantiServiti;

    private java.lang.String codiceIdentificazione;

    private com.hyperborea.sira.ws.CpL3A[] cpL3As;

    private java.lang.Float dimensioneTub1;

    private java.lang.Float dimensioneTub2;

    private java.lang.Float distanzaFoce;

    private java.lang.Float durata;

    private java.lang.Float frequenza;

    private java.lang.Integer idCcost;

    private java.lang.String impiantoTrattamento;

    private java.lang.Float lunghCondotta;

    private java.lang.String modalitaAcquis;

    private java.lang.Float portataOraria;

    private java.lang.Float portataOrariaMax;

    private java.lang.Integer scaricoInCiSensibile;

    private java.lang.String sfioratore;

    private java.lang.String sostanzePericolose;

    private java.lang.String sponda;

    private com.hyperborea.sira.ws.VocTipoRecettore tipoRecettore;

    private com.hyperborea.sira.ws.VocTipoScarico tipoScarico;

    private com.hyperborea.sira.ws.VocSidTipoTubazione tipoTubazione;

    private com.hyperborea.sira.ws.ValoriLimite[] valoriLimites;

    private java.lang.Float volumeAgosto;

    private java.lang.Float volumeAnnuo;

    private java.lang.Float volumeAnnuoMax;

    private java.lang.Float volumeAprile;

    private java.lang.Float volumeDicembre;

    private java.lang.Float volumeFebbraio;

    private java.lang.Float volumeGennaio;

    private java.lang.Float volumeGiugno;

    private java.lang.Float volumeLuglio;

    private java.lang.Float volumeMaggio;

    private java.lang.Float volumeMarzo;

    private java.lang.Float volumeNovembre;

    private java.lang.Float volumeOttobre;

    private java.lang.Float volumeSettembre;

    public CcostScarichiIdrici() {
    }

    public CcostScarichiIdrici(
           java.lang.Float abitantiEquivalenti,
           java.lang.Integer abitantiServiti,
           java.lang.String codiceIdentificazione,
           com.hyperborea.sira.ws.CpL3A[] cpL3As,
           java.lang.Float dimensioneTub1,
           java.lang.Float dimensioneTub2,
           java.lang.Float distanzaFoce,
           java.lang.Float durata,
           java.lang.Float frequenza,
           java.lang.Integer idCcost,
           java.lang.String impiantoTrattamento,
           java.lang.Float lunghCondotta,
           java.lang.String modalitaAcquis,
           java.lang.Float portataOraria,
           java.lang.Float portataOrariaMax,
           java.lang.Integer scaricoInCiSensibile,
           java.lang.String sfioratore,
           java.lang.String sostanzePericolose,
           java.lang.String sponda,
           com.hyperborea.sira.ws.VocTipoRecettore tipoRecettore,
           com.hyperborea.sira.ws.VocTipoScarico tipoScarico,
           com.hyperborea.sira.ws.VocSidTipoTubazione tipoTubazione,
           com.hyperborea.sira.ws.ValoriLimite[] valoriLimites,
           java.lang.Float volumeAgosto,
           java.lang.Float volumeAnnuo,
           java.lang.Float volumeAnnuoMax,
           java.lang.Float volumeAprile,
           java.lang.Float volumeDicembre,
           java.lang.Float volumeFebbraio,
           java.lang.Float volumeGennaio,
           java.lang.Float volumeGiugno,
           java.lang.Float volumeLuglio,
           java.lang.Float volumeMaggio,
           java.lang.Float volumeMarzo,
           java.lang.Float volumeNovembre,
           java.lang.Float volumeOttobre,
           java.lang.Float volumeSettembre) {
        this.abitantiEquivalenti = abitantiEquivalenti;
        this.abitantiServiti = abitantiServiti;
        this.codiceIdentificazione = codiceIdentificazione;
        this.cpL3As = cpL3As;
        this.dimensioneTub1 = dimensioneTub1;
        this.dimensioneTub2 = dimensioneTub2;
        this.distanzaFoce = distanzaFoce;
        this.durata = durata;
        this.frequenza = frequenza;
        this.idCcost = idCcost;
        this.impiantoTrattamento = impiantoTrattamento;
        this.lunghCondotta = lunghCondotta;
        this.modalitaAcquis = modalitaAcquis;
        this.portataOraria = portataOraria;
        this.portataOrariaMax = portataOrariaMax;
        this.scaricoInCiSensibile = scaricoInCiSensibile;
        this.sfioratore = sfioratore;
        this.sostanzePericolose = sostanzePericolose;
        this.sponda = sponda;
        this.tipoRecettore = tipoRecettore;
        this.tipoScarico = tipoScarico;
        this.tipoTubazione = tipoTubazione;
        this.valoriLimites = valoriLimites;
        this.volumeAgosto = volumeAgosto;
        this.volumeAnnuo = volumeAnnuo;
        this.volumeAnnuoMax = volumeAnnuoMax;
        this.volumeAprile = volumeAprile;
        this.volumeDicembre = volumeDicembre;
        this.volumeFebbraio = volumeFebbraio;
        this.volumeGennaio = volumeGennaio;
        this.volumeGiugno = volumeGiugno;
        this.volumeLuglio = volumeLuglio;
        this.volumeMaggio = volumeMaggio;
        this.volumeMarzo = volumeMarzo;
        this.volumeNovembre = volumeNovembre;
        this.volumeOttobre = volumeOttobre;
        this.volumeSettembre = volumeSettembre;
    }


    /**
     * Gets the abitantiEquivalenti value for this CcostScarichiIdrici.
     * 
     * @return abitantiEquivalenti
     */
    public java.lang.Float getAbitantiEquivalenti() {
        return abitantiEquivalenti;
    }


    /**
     * Sets the abitantiEquivalenti value for this CcostScarichiIdrici.
     * 
     * @param abitantiEquivalenti
     */
    public void setAbitantiEquivalenti(java.lang.Float abitantiEquivalenti) {
        this.abitantiEquivalenti = abitantiEquivalenti;
    }


    /**
     * Gets the abitantiServiti value for this CcostScarichiIdrici.
     * 
     * @return abitantiServiti
     */
    public java.lang.Integer getAbitantiServiti() {
        return abitantiServiti;
    }


    /**
     * Sets the abitantiServiti value for this CcostScarichiIdrici.
     * 
     * @param abitantiServiti
     */
    public void setAbitantiServiti(java.lang.Integer abitantiServiti) {
        this.abitantiServiti = abitantiServiti;
    }


    /**
     * Gets the codiceIdentificazione value for this CcostScarichiIdrici.
     * 
     * @return codiceIdentificazione
     */
    public java.lang.String getCodiceIdentificazione() {
        return codiceIdentificazione;
    }


    /**
     * Sets the codiceIdentificazione value for this CcostScarichiIdrici.
     * 
     * @param codiceIdentificazione
     */
    public void setCodiceIdentificazione(java.lang.String codiceIdentificazione) {
        this.codiceIdentificazione = codiceIdentificazione;
    }


    /**
     * Gets the cpL3As value for this CcostScarichiIdrici.
     * 
     * @return cpL3As
     */
    public com.hyperborea.sira.ws.CpL3A[] getCpL3As() {
        return cpL3As;
    }


    /**
     * Sets the cpL3As value for this CcostScarichiIdrici.
     * 
     * @param cpL3As
     */
    public void setCpL3As(com.hyperborea.sira.ws.CpL3A[] cpL3As) {
        this.cpL3As = cpL3As;
    }

    public com.hyperborea.sira.ws.CpL3A getCpL3As(int i) {
        return this.cpL3As[i];
    }

    public void setCpL3As(int i, com.hyperborea.sira.ws.CpL3A _value) {
        this.cpL3As[i] = _value;
    }


    /**
     * Gets the dimensioneTub1 value for this CcostScarichiIdrici.
     * 
     * @return dimensioneTub1
     */
    public java.lang.Float getDimensioneTub1() {
        return dimensioneTub1;
    }


    /**
     * Sets the dimensioneTub1 value for this CcostScarichiIdrici.
     * 
     * @param dimensioneTub1
     */
    public void setDimensioneTub1(java.lang.Float dimensioneTub1) {
        this.dimensioneTub1 = dimensioneTub1;
    }


    /**
     * Gets the dimensioneTub2 value for this CcostScarichiIdrici.
     * 
     * @return dimensioneTub2
     */
    public java.lang.Float getDimensioneTub2() {
        return dimensioneTub2;
    }


    /**
     * Sets the dimensioneTub2 value for this CcostScarichiIdrici.
     * 
     * @param dimensioneTub2
     */
    public void setDimensioneTub2(java.lang.Float dimensioneTub2) {
        this.dimensioneTub2 = dimensioneTub2;
    }


    /**
     * Gets the distanzaFoce value for this CcostScarichiIdrici.
     * 
     * @return distanzaFoce
     */
    public java.lang.Float getDistanzaFoce() {
        return distanzaFoce;
    }


    /**
     * Sets the distanzaFoce value for this CcostScarichiIdrici.
     * 
     * @param distanzaFoce
     */
    public void setDistanzaFoce(java.lang.Float distanzaFoce) {
        this.distanzaFoce = distanzaFoce;
    }


    /**
     * Gets the durata value for this CcostScarichiIdrici.
     * 
     * @return durata
     */
    public java.lang.Float getDurata() {
        return durata;
    }


    /**
     * Sets the durata value for this CcostScarichiIdrici.
     * 
     * @param durata
     */
    public void setDurata(java.lang.Float durata) {
        this.durata = durata;
    }


    /**
     * Gets the frequenza value for this CcostScarichiIdrici.
     * 
     * @return frequenza
     */
    public java.lang.Float getFrequenza() {
        return frequenza;
    }


    /**
     * Sets the frequenza value for this CcostScarichiIdrici.
     * 
     * @param frequenza
     */
    public void setFrequenza(java.lang.Float frequenza) {
        this.frequenza = frequenza;
    }


    /**
     * Gets the idCcost value for this CcostScarichiIdrici.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostScarichiIdrici.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the impiantoTrattamento value for this CcostScarichiIdrici.
     * 
     * @return impiantoTrattamento
     */
    public java.lang.String getImpiantoTrattamento() {
        return impiantoTrattamento;
    }


    /**
     * Sets the impiantoTrattamento value for this CcostScarichiIdrici.
     * 
     * @param impiantoTrattamento
     */
    public void setImpiantoTrattamento(java.lang.String impiantoTrattamento) {
        this.impiantoTrattamento = impiantoTrattamento;
    }


    /**
     * Gets the lunghCondotta value for this CcostScarichiIdrici.
     * 
     * @return lunghCondotta
     */
    public java.lang.Float getLunghCondotta() {
        return lunghCondotta;
    }


    /**
     * Sets the lunghCondotta value for this CcostScarichiIdrici.
     * 
     * @param lunghCondotta
     */
    public void setLunghCondotta(java.lang.Float lunghCondotta) {
        this.lunghCondotta = lunghCondotta;
    }


    /**
     * Gets the modalitaAcquis value for this CcostScarichiIdrici.
     * 
     * @return modalitaAcquis
     */
    public java.lang.String getModalitaAcquis() {
        return modalitaAcquis;
    }


    /**
     * Sets the modalitaAcquis value for this CcostScarichiIdrici.
     * 
     * @param modalitaAcquis
     */
    public void setModalitaAcquis(java.lang.String modalitaAcquis) {
        this.modalitaAcquis = modalitaAcquis;
    }


    /**
     * Gets the portataOraria value for this CcostScarichiIdrici.
     * 
     * @return portataOraria
     */
    public java.lang.Float getPortataOraria() {
        return portataOraria;
    }


    /**
     * Sets the portataOraria value for this CcostScarichiIdrici.
     * 
     * @param portataOraria
     */
    public void setPortataOraria(java.lang.Float portataOraria) {
        this.portataOraria = portataOraria;
    }


    /**
     * Gets the portataOrariaMax value for this CcostScarichiIdrici.
     * 
     * @return portataOrariaMax
     */
    public java.lang.Float getPortataOrariaMax() {
        return portataOrariaMax;
    }


    /**
     * Sets the portataOrariaMax value for this CcostScarichiIdrici.
     * 
     * @param portataOrariaMax
     */
    public void setPortataOrariaMax(java.lang.Float portataOrariaMax) {
        this.portataOrariaMax = portataOrariaMax;
    }


    /**
     * Gets the scaricoInCiSensibile value for this CcostScarichiIdrici.
     * 
     * @return scaricoInCiSensibile
     */
    public java.lang.Integer getScaricoInCiSensibile() {
        return scaricoInCiSensibile;
    }


    /**
     * Sets the scaricoInCiSensibile value for this CcostScarichiIdrici.
     * 
     * @param scaricoInCiSensibile
     */
    public void setScaricoInCiSensibile(java.lang.Integer scaricoInCiSensibile) {
        this.scaricoInCiSensibile = scaricoInCiSensibile;
    }


    /**
     * Gets the sfioratore value for this CcostScarichiIdrici.
     * 
     * @return sfioratore
     */
    public java.lang.String getSfioratore() {
        return sfioratore;
    }


    /**
     * Sets the sfioratore value for this CcostScarichiIdrici.
     * 
     * @param sfioratore
     */
    public void setSfioratore(java.lang.String sfioratore) {
        this.sfioratore = sfioratore;
    }


    /**
     * Gets the sostanzePericolose value for this CcostScarichiIdrici.
     * 
     * @return sostanzePericolose
     */
    public java.lang.String getSostanzePericolose() {
        return sostanzePericolose;
    }


    /**
     * Sets the sostanzePericolose value for this CcostScarichiIdrici.
     * 
     * @param sostanzePericolose
     */
    public void setSostanzePericolose(java.lang.String sostanzePericolose) {
        this.sostanzePericolose = sostanzePericolose;
    }


    /**
     * Gets the sponda value for this CcostScarichiIdrici.
     * 
     * @return sponda
     */
    public java.lang.String getSponda() {
        return sponda;
    }


    /**
     * Sets the sponda value for this CcostScarichiIdrici.
     * 
     * @param sponda
     */
    public void setSponda(java.lang.String sponda) {
        this.sponda = sponda;
    }


    /**
     * Gets the tipoRecettore value for this CcostScarichiIdrici.
     * 
     * @return tipoRecettore
     */
    public com.hyperborea.sira.ws.VocTipoRecettore getTipoRecettore() {
        return tipoRecettore;
    }


    /**
     * Sets the tipoRecettore value for this CcostScarichiIdrici.
     * 
     * @param tipoRecettore
     */
    public void setTipoRecettore(com.hyperborea.sira.ws.VocTipoRecettore tipoRecettore) {
        this.tipoRecettore = tipoRecettore;
    }


    /**
     * Gets the tipoScarico value for this CcostScarichiIdrici.
     * 
     * @return tipoScarico
     */
    public com.hyperborea.sira.ws.VocTipoScarico getTipoScarico() {
        return tipoScarico;
    }


    /**
     * Sets the tipoScarico value for this CcostScarichiIdrici.
     * 
     * @param tipoScarico
     */
    public void setTipoScarico(com.hyperborea.sira.ws.VocTipoScarico tipoScarico) {
        this.tipoScarico = tipoScarico;
    }


    /**
     * Gets the tipoTubazione value for this CcostScarichiIdrici.
     * 
     * @return tipoTubazione
     */
    public com.hyperborea.sira.ws.VocSidTipoTubazione getTipoTubazione() {
        return tipoTubazione;
    }


    /**
     * Sets the tipoTubazione value for this CcostScarichiIdrici.
     * 
     * @param tipoTubazione
     */
    public void setTipoTubazione(com.hyperborea.sira.ws.VocSidTipoTubazione tipoTubazione) {
        this.tipoTubazione = tipoTubazione;
    }


    /**
     * Gets the valoriLimites value for this CcostScarichiIdrici.
     * 
     * @return valoriLimites
     */
    public com.hyperborea.sira.ws.ValoriLimite[] getValoriLimites() {
        return valoriLimites;
    }


    /**
     * Sets the valoriLimites value for this CcostScarichiIdrici.
     * 
     * @param valoriLimites
     */
    public void setValoriLimites(com.hyperborea.sira.ws.ValoriLimite[] valoriLimites) {
        this.valoriLimites = valoriLimites;
    }

    public com.hyperborea.sira.ws.ValoriLimite getValoriLimites(int i) {
        return this.valoriLimites[i];
    }

    public void setValoriLimites(int i, com.hyperborea.sira.ws.ValoriLimite _value) {
        this.valoriLimites[i] = _value;
    }


    /**
     * Gets the volumeAgosto value for this CcostScarichiIdrici.
     * 
     * @return volumeAgosto
     */
    public java.lang.Float getVolumeAgosto() {
        return volumeAgosto;
    }


    /**
     * Sets the volumeAgosto value for this CcostScarichiIdrici.
     * 
     * @param volumeAgosto
     */
    public void setVolumeAgosto(java.lang.Float volumeAgosto) {
        this.volumeAgosto = volumeAgosto;
    }


    /**
     * Gets the volumeAnnuo value for this CcostScarichiIdrici.
     * 
     * @return volumeAnnuo
     */
    public java.lang.Float getVolumeAnnuo() {
        return volumeAnnuo;
    }


    /**
     * Sets the volumeAnnuo value for this CcostScarichiIdrici.
     * 
     * @param volumeAnnuo
     */
    public void setVolumeAnnuo(java.lang.Float volumeAnnuo) {
        this.volumeAnnuo = volumeAnnuo;
    }


    /**
     * Gets the volumeAnnuoMax value for this CcostScarichiIdrici.
     * 
     * @return volumeAnnuoMax
     */
    public java.lang.Float getVolumeAnnuoMax() {
        return volumeAnnuoMax;
    }


    /**
     * Sets the volumeAnnuoMax value for this CcostScarichiIdrici.
     * 
     * @param volumeAnnuoMax
     */
    public void setVolumeAnnuoMax(java.lang.Float volumeAnnuoMax) {
        this.volumeAnnuoMax = volumeAnnuoMax;
    }


    /**
     * Gets the volumeAprile value for this CcostScarichiIdrici.
     * 
     * @return volumeAprile
     */
    public java.lang.Float getVolumeAprile() {
        return volumeAprile;
    }


    /**
     * Sets the volumeAprile value for this CcostScarichiIdrici.
     * 
     * @param volumeAprile
     */
    public void setVolumeAprile(java.lang.Float volumeAprile) {
        this.volumeAprile = volumeAprile;
    }


    /**
     * Gets the volumeDicembre value for this CcostScarichiIdrici.
     * 
     * @return volumeDicembre
     */
    public java.lang.Float getVolumeDicembre() {
        return volumeDicembre;
    }


    /**
     * Sets the volumeDicembre value for this CcostScarichiIdrici.
     * 
     * @param volumeDicembre
     */
    public void setVolumeDicembre(java.lang.Float volumeDicembre) {
        this.volumeDicembre = volumeDicembre;
    }


    /**
     * Gets the volumeFebbraio value for this CcostScarichiIdrici.
     * 
     * @return volumeFebbraio
     */
    public java.lang.Float getVolumeFebbraio() {
        return volumeFebbraio;
    }


    /**
     * Sets the volumeFebbraio value for this CcostScarichiIdrici.
     * 
     * @param volumeFebbraio
     */
    public void setVolumeFebbraio(java.lang.Float volumeFebbraio) {
        this.volumeFebbraio = volumeFebbraio;
    }


    /**
     * Gets the volumeGennaio value for this CcostScarichiIdrici.
     * 
     * @return volumeGennaio
     */
    public java.lang.Float getVolumeGennaio() {
        return volumeGennaio;
    }


    /**
     * Sets the volumeGennaio value for this CcostScarichiIdrici.
     * 
     * @param volumeGennaio
     */
    public void setVolumeGennaio(java.lang.Float volumeGennaio) {
        this.volumeGennaio = volumeGennaio;
    }


    /**
     * Gets the volumeGiugno value for this CcostScarichiIdrici.
     * 
     * @return volumeGiugno
     */
    public java.lang.Float getVolumeGiugno() {
        return volumeGiugno;
    }


    /**
     * Sets the volumeGiugno value for this CcostScarichiIdrici.
     * 
     * @param volumeGiugno
     */
    public void setVolumeGiugno(java.lang.Float volumeGiugno) {
        this.volumeGiugno = volumeGiugno;
    }


    /**
     * Gets the volumeLuglio value for this CcostScarichiIdrici.
     * 
     * @return volumeLuglio
     */
    public java.lang.Float getVolumeLuglio() {
        return volumeLuglio;
    }


    /**
     * Sets the volumeLuglio value for this CcostScarichiIdrici.
     * 
     * @param volumeLuglio
     */
    public void setVolumeLuglio(java.lang.Float volumeLuglio) {
        this.volumeLuglio = volumeLuglio;
    }


    /**
     * Gets the volumeMaggio value for this CcostScarichiIdrici.
     * 
     * @return volumeMaggio
     */
    public java.lang.Float getVolumeMaggio() {
        return volumeMaggio;
    }


    /**
     * Sets the volumeMaggio value for this CcostScarichiIdrici.
     * 
     * @param volumeMaggio
     */
    public void setVolumeMaggio(java.lang.Float volumeMaggio) {
        this.volumeMaggio = volumeMaggio;
    }


    /**
     * Gets the volumeMarzo value for this CcostScarichiIdrici.
     * 
     * @return volumeMarzo
     */
    public java.lang.Float getVolumeMarzo() {
        return volumeMarzo;
    }


    /**
     * Sets the volumeMarzo value for this CcostScarichiIdrici.
     * 
     * @param volumeMarzo
     */
    public void setVolumeMarzo(java.lang.Float volumeMarzo) {
        this.volumeMarzo = volumeMarzo;
    }


    /**
     * Gets the volumeNovembre value for this CcostScarichiIdrici.
     * 
     * @return volumeNovembre
     */
    public java.lang.Float getVolumeNovembre() {
        return volumeNovembre;
    }


    /**
     * Sets the volumeNovembre value for this CcostScarichiIdrici.
     * 
     * @param volumeNovembre
     */
    public void setVolumeNovembre(java.lang.Float volumeNovembre) {
        this.volumeNovembre = volumeNovembre;
    }


    /**
     * Gets the volumeOttobre value for this CcostScarichiIdrici.
     * 
     * @return volumeOttobre
     */
    public java.lang.Float getVolumeOttobre() {
        return volumeOttobre;
    }


    /**
     * Sets the volumeOttobre value for this CcostScarichiIdrici.
     * 
     * @param volumeOttobre
     */
    public void setVolumeOttobre(java.lang.Float volumeOttobre) {
        this.volumeOttobre = volumeOttobre;
    }


    /**
     * Gets the volumeSettembre value for this CcostScarichiIdrici.
     * 
     * @return volumeSettembre
     */
    public java.lang.Float getVolumeSettembre() {
        return volumeSettembre;
    }


    /**
     * Sets the volumeSettembre value for this CcostScarichiIdrici.
     * 
     * @param volumeSettembre
     */
    public void setVolumeSettembre(java.lang.Float volumeSettembre) {
        this.volumeSettembre = volumeSettembre;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostScarichiIdrici)) return false;
        CcostScarichiIdrici other = (CcostScarichiIdrici) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.abitantiEquivalenti==null && other.getAbitantiEquivalenti()==null) || 
             (this.abitantiEquivalenti!=null &&
              this.abitantiEquivalenti.equals(other.getAbitantiEquivalenti()))) &&
            ((this.abitantiServiti==null && other.getAbitantiServiti()==null) || 
             (this.abitantiServiti!=null &&
              this.abitantiServiti.equals(other.getAbitantiServiti()))) &&
            ((this.codiceIdentificazione==null && other.getCodiceIdentificazione()==null) || 
             (this.codiceIdentificazione!=null &&
              this.codiceIdentificazione.equals(other.getCodiceIdentificazione()))) &&
            ((this.cpL3As==null && other.getCpL3As()==null) || 
             (this.cpL3As!=null &&
              java.util.Arrays.equals(this.cpL3As, other.getCpL3As()))) &&
            ((this.dimensioneTub1==null && other.getDimensioneTub1()==null) || 
             (this.dimensioneTub1!=null &&
              this.dimensioneTub1.equals(other.getDimensioneTub1()))) &&
            ((this.dimensioneTub2==null && other.getDimensioneTub2()==null) || 
             (this.dimensioneTub2!=null &&
              this.dimensioneTub2.equals(other.getDimensioneTub2()))) &&
            ((this.distanzaFoce==null && other.getDistanzaFoce()==null) || 
             (this.distanzaFoce!=null &&
              this.distanzaFoce.equals(other.getDistanzaFoce()))) &&
            ((this.durata==null && other.getDurata()==null) || 
             (this.durata!=null &&
              this.durata.equals(other.getDurata()))) &&
            ((this.frequenza==null && other.getFrequenza()==null) || 
             (this.frequenza!=null &&
              this.frequenza.equals(other.getFrequenza()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.impiantoTrattamento==null && other.getImpiantoTrattamento()==null) || 
             (this.impiantoTrattamento!=null &&
              this.impiantoTrattamento.equals(other.getImpiantoTrattamento()))) &&
            ((this.lunghCondotta==null && other.getLunghCondotta()==null) || 
             (this.lunghCondotta!=null &&
              this.lunghCondotta.equals(other.getLunghCondotta()))) &&
            ((this.modalitaAcquis==null && other.getModalitaAcquis()==null) || 
             (this.modalitaAcquis!=null &&
              this.modalitaAcquis.equals(other.getModalitaAcquis()))) &&
            ((this.portataOraria==null && other.getPortataOraria()==null) || 
             (this.portataOraria!=null &&
              this.portataOraria.equals(other.getPortataOraria()))) &&
            ((this.portataOrariaMax==null && other.getPortataOrariaMax()==null) || 
             (this.portataOrariaMax!=null &&
              this.portataOrariaMax.equals(other.getPortataOrariaMax()))) &&
            ((this.scaricoInCiSensibile==null && other.getScaricoInCiSensibile()==null) || 
             (this.scaricoInCiSensibile!=null &&
              this.scaricoInCiSensibile.equals(other.getScaricoInCiSensibile()))) &&
            ((this.sfioratore==null && other.getSfioratore()==null) || 
             (this.sfioratore!=null &&
              this.sfioratore.equals(other.getSfioratore()))) &&
            ((this.sostanzePericolose==null && other.getSostanzePericolose()==null) || 
             (this.sostanzePericolose!=null &&
              this.sostanzePericolose.equals(other.getSostanzePericolose()))) &&
            ((this.sponda==null && other.getSponda()==null) || 
             (this.sponda!=null &&
              this.sponda.equals(other.getSponda()))) &&
            ((this.tipoRecettore==null && other.getTipoRecettore()==null) || 
             (this.tipoRecettore!=null &&
              this.tipoRecettore.equals(other.getTipoRecettore()))) &&
            ((this.tipoScarico==null && other.getTipoScarico()==null) || 
             (this.tipoScarico!=null &&
              this.tipoScarico.equals(other.getTipoScarico()))) &&
            ((this.tipoTubazione==null && other.getTipoTubazione()==null) || 
             (this.tipoTubazione!=null &&
              this.tipoTubazione.equals(other.getTipoTubazione()))) &&
            ((this.valoriLimites==null && other.getValoriLimites()==null) || 
             (this.valoriLimites!=null &&
              java.util.Arrays.equals(this.valoriLimites, other.getValoriLimites()))) &&
            ((this.volumeAgosto==null && other.getVolumeAgosto()==null) || 
             (this.volumeAgosto!=null &&
              this.volumeAgosto.equals(other.getVolumeAgosto()))) &&
            ((this.volumeAnnuo==null && other.getVolumeAnnuo()==null) || 
             (this.volumeAnnuo!=null &&
              this.volumeAnnuo.equals(other.getVolumeAnnuo()))) &&
            ((this.volumeAnnuoMax==null && other.getVolumeAnnuoMax()==null) || 
             (this.volumeAnnuoMax!=null &&
              this.volumeAnnuoMax.equals(other.getVolumeAnnuoMax()))) &&
            ((this.volumeAprile==null && other.getVolumeAprile()==null) || 
             (this.volumeAprile!=null &&
              this.volumeAprile.equals(other.getVolumeAprile()))) &&
            ((this.volumeDicembre==null && other.getVolumeDicembre()==null) || 
             (this.volumeDicembre!=null &&
              this.volumeDicembre.equals(other.getVolumeDicembre()))) &&
            ((this.volumeFebbraio==null && other.getVolumeFebbraio()==null) || 
             (this.volumeFebbraio!=null &&
              this.volumeFebbraio.equals(other.getVolumeFebbraio()))) &&
            ((this.volumeGennaio==null && other.getVolumeGennaio()==null) || 
             (this.volumeGennaio!=null &&
              this.volumeGennaio.equals(other.getVolumeGennaio()))) &&
            ((this.volumeGiugno==null && other.getVolumeGiugno()==null) || 
             (this.volumeGiugno!=null &&
              this.volumeGiugno.equals(other.getVolumeGiugno()))) &&
            ((this.volumeLuglio==null && other.getVolumeLuglio()==null) || 
             (this.volumeLuglio!=null &&
              this.volumeLuglio.equals(other.getVolumeLuglio()))) &&
            ((this.volumeMaggio==null && other.getVolumeMaggio()==null) || 
             (this.volumeMaggio!=null &&
              this.volumeMaggio.equals(other.getVolumeMaggio()))) &&
            ((this.volumeMarzo==null && other.getVolumeMarzo()==null) || 
             (this.volumeMarzo!=null &&
              this.volumeMarzo.equals(other.getVolumeMarzo()))) &&
            ((this.volumeNovembre==null && other.getVolumeNovembre()==null) || 
             (this.volumeNovembre!=null &&
              this.volumeNovembre.equals(other.getVolumeNovembre()))) &&
            ((this.volumeOttobre==null && other.getVolumeOttobre()==null) || 
             (this.volumeOttobre!=null &&
              this.volumeOttobre.equals(other.getVolumeOttobre()))) &&
            ((this.volumeSettembre==null && other.getVolumeSettembre()==null) || 
             (this.volumeSettembre!=null &&
              this.volumeSettembre.equals(other.getVolumeSettembre())));
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
        if (getAbitantiEquivalenti() != null) {
            _hashCode += getAbitantiEquivalenti().hashCode();
        }
        if (getAbitantiServiti() != null) {
            _hashCode += getAbitantiServiti().hashCode();
        }
        if (getCodiceIdentificazione() != null) {
            _hashCode += getCodiceIdentificazione().hashCode();
        }
        if (getCpL3As() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCpL3As());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCpL3As(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDimensioneTub1() != null) {
            _hashCode += getDimensioneTub1().hashCode();
        }
        if (getDimensioneTub2() != null) {
            _hashCode += getDimensioneTub2().hashCode();
        }
        if (getDistanzaFoce() != null) {
            _hashCode += getDistanzaFoce().hashCode();
        }
        if (getDurata() != null) {
            _hashCode += getDurata().hashCode();
        }
        if (getFrequenza() != null) {
            _hashCode += getFrequenza().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getImpiantoTrattamento() != null) {
            _hashCode += getImpiantoTrattamento().hashCode();
        }
        if (getLunghCondotta() != null) {
            _hashCode += getLunghCondotta().hashCode();
        }
        if (getModalitaAcquis() != null) {
            _hashCode += getModalitaAcquis().hashCode();
        }
        if (getPortataOraria() != null) {
            _hashCode += getPortataOraria().hashCode();
        }
        if (getPortataOrariaMax() != null) {
            _hashCode += getPortataOrariaMax().hashCode();
        }
        if (getScaricoInCiSensibile() != null) {
            _hashCode += getScaricoInCiSensibile().hashCode();
        }
        if (getSfioratore() != null) {
            _hashCode += getSfioratore().hashCode();
        }
        if (getSostanzePericolose() != null) {
            _hashCode += getSostanzePericolose().hashCode();
        }
        if (getSponda() != null) {
            _hashCode += getSponda().hashCode();
        }
        if (getTipoRecettore() != null) {
            _hashCode += getTipoRecettore().hashCode();
        }
        if (getTipoScarico() != null) {
            _hashCode += getTipoScarico().hashCode();
        }
        if (getTipoTubazione() != null) {
            _hashCode += getTipoTubazione().hashCode();
        }
        if (getValoriLimites() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getValoriLimites());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getValoriLimites(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVolumeAgosto() != null) {
            _hashCode += getVolumeAgosto().hashCode();
        }
        if (getVolumeAnnuo() != null) {
            _hashCode += getVolumeAnnuo().hashCode();
        }
        if (getVolumeAnnuoMax() != null) {
            _hashCode += getVolumeAnnuoMax().hashCode();
        }
        if (getVolumeAprile() != null) {
            _hashCode += getVolumeAprile().hashCode();
        }
        if (getVolumeDicembre() != null) {
            _hashCode += getVolumeDicembre().hashCode();
        }
        if (getVolumeFebbraio() != null) {
            _hashCode += getVolumeFebbraio().hashCode();
        }
        if (getVolumeGennaio() != null) {
            _hashCode += getVolumeGennaio().hashCode();
        }
        if (getVolumeGiugno() != null) {
            _hashCode += getVolumeGiugno().hashCode();
        }
        if (getVolumeLuglio() != null) {
            _hashCode += getVolumeLuglio().hashCode();
        }
        if (getVolumeMaggio() != null) {
            _hashCode += getVolumeMaggio().hashCode();
        }
        if (getVolumeMarzo() != null) {
            _hashCode += getVolumeMarzo().hashCode();
        }
        if (getVolumeNovembre() != null) {
            _hashCode += getVolumeNovembre().hashCode();
        }
        if (getVolumeOttobre() != null) {
            _hashCode += getVolumeOttobre().hashCode();
        }
        if (getVolumeSettembre() != null) {
            _hashCode += getVolumeSettembre().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostScarichiIdrici.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostScarichiIdrici"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("abitantiEquivalenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "abitantiEquivalenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("abitantiServiti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "abitantiServiti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceIdentificazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceIdentificazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cpL3As");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cpL3as"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "cpL3A"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dimensioneTub1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dimensioneTub1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dimensioneTub2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dimensioneTub2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("distanzaFoce");
        elemField.setXmlName(new javax.xml.namespace.QName("", "distanzaFoce"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("durata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "durata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("frequenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "frequenza"));
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
        elemField.setFieldName("impiantoTrattamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "impiantoTrattamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lunghCondotta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lunghCondotta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modalitaAcquis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modalitaAcquis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataOraria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataOraria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataOrariaMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataOrariaMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scaricoInCiSensibile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scaricoInCiSensibile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sfioratore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sfioratore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sostanzePericolose");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sostanzePericolose"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sponda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sponda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoRecettore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoRecettore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoRecettore"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoScarico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoScarico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoScarico"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoTubazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoTubazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocSidTipoTubazione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valoriLimites");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valoriLimites"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "valoriLimite"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeAgosto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeAgosto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeAnnuo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeAnnuo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeAnnuoMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeAnnuoMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeAprile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeAprile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeDicembre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeDicembre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeFebbraio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeFebbraio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeGennaio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeGennaio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeGiugno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeGiugno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeLuglio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeLuglio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeMaggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeMaggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeMarzo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeMarzo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeNovembre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeNovembre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeOttobre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeOttobre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeSettembre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeSettembre"));
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
