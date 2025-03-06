/**
 * CcostIgrMobile.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostIgrMobile  implements java.io.Serializable {
    private java.lang.Float bilancio;

    private java.lang.Float capGiorMassimaRnp;

    private java.lang.Float capGiorMassimaRp;

    private java.lang.Float capGiorPrevistaRnp;

    private java.lang.Float capGiorPrevistaRp;

    private java.lang.Float capOraMassimaRnp;

    private java.lang.Float capOraMassimaRp;

    private java.lang.Float capRecupero;

    private java.lang.Integer idCcost;

    private java.lang.String lineeTecnologiche;

    private java.lang.Float matEntrata;

    private java.lang.Float perditeProc;

    private java.lang.String presidiAmbientali;

    private java.lang.Float prodUscita;

    private com.hyperborea.sira.ws.ProdottiFinitiIgrMobile[] prodottiFinitiIgrMobiles;

    private com.hyperborea.sira.ws.ProdottiUscitaIgrMobile[] prodottiUscitaIgrMobiles;

    private java.lang.String ricoveroImpianto;

    private java.lang.Float rifEntrata;

    private java.lang.Float rifUscitaRecExt;

    private java.lang.Float rifUscitaRecInt;

    private java.lang.Float rifUscitaSmaExt;

    private java.lang.Float rifUscitaSmaInt;

    private com.hyperborea.sira.ws.RifiutiProdottiIgrMobile[] rifiutiProdottiIgrMobiles;

    private java.lang.String tipiTrattamento;

    private com.hyperborea.sira.ws.IgrQuantitaGiornoUm umCgmrnp;

    private com.hyperborea.sira.ws.IgrQuantitaGiornoUm umCgmrp;

    private com.hyperborea.sira.ws.IgrQuantitaGiornoUm umCgprnp;

    private com.hyperborea.sira.ws.IgrQuantitaGiornoUm umCgprp;

    private com.hyperborea.sira.ws.IgrQuantitaOraUm umComrnp;

    private com.hyperborea.sira.ws.IgrQuantitaOraUm umComrp;

    public CcostIgrMobile() {
    }

    public CcostIgrMobile(
           java.lang.Float bilancio,
           java.lang.Float capGiorMassimaRnp,
           java.lang.Float capGiorMassimaRp,
           java.lang.Float capGiorPrevistaRnp,
           java.lang.Float capGiorPrevistaRp,
           java.lang.Float capOraMassimaRnp,
           java.lang.Float capOraMassimaRp,
           java.lang.Float capRecupero,
           java.lang.Integer idCcost,
           java.lang.String lineeTecnologiche,
           java.lang.Float matEntrata,
           java.lang.Float perditeProc,
           java.lang.String presidiAmbientali,
           java.lang.Float prodUscita,
           com.hyperborea.sira.ws.ProdottiFinitiIgrMobile[] prodottiFinitiIgrMobiles,
           com.hyperborea.sira.ws.ProdottiUscitaIgrMobile[] prodottiUscitaIgrMobiles,
           java.lang.String ricoveroImpianto,
           java.lang.Float rifEntrata,
           java.lang.Float rifUscitaRecExt,
           java.lang.Float rifUscitaRecInt,
           java.lang.Float rifUscitaSmaExt,
           java.lang.Float rifUscitaSmaInt,
           com.hyperborea.sira.ws.RifiutiProdottiIgrMobile[] rifiutiProdottiIgrMobiles,
           java.lang.String tipiTrattamento,
           com.hyperborea.sira.ws.IgrQuantitaGiornoUm umCgmrnp,
           com.hyperborea.sira.ws.IgrQuantitaGiornoUm umCgmrp,
           com.hyperborea.sira.ws.IgrQuantitaGiornoUm umCgprnp,
           com.hyperborea.sira.ws.IgrQuantitaGiornoUm umCgprp,
           com.hyperborea.sira.ws.IgrQuantitaOraUm umComrnp,
           com.hyperborea.sira.ws.IgrQuantitaOraUm umComrp) {
           this.bilancio = bilancio;
           this.capGiorMassimaRnp = capGiorMassimaRnp;
           this.capGiorMassimaRp = capGiorMassimaRp;
           this.capGiorPrevistaRnp = capGiorPrevistaRnp;
           this.capGiorPrevistaRp = capGiorPrevistaRp;
           this.capOraMassimaRnp = capOraMassimaRnp;
           this.capOraMassimaRp = capOraMassimaRp;
           this.capRecupero = capRecupero;
           this.idCcost = idCcost;
           this.lineeTecnologiche = lineeTecnologiche;
           this.matEntrata = matEntrata;
           this.perditeProc = perditeProc;
           this.presidiAmbientali = presidiAmbientali;
           this.prodUscita = prodUscita;
           this.prodottiFinitiIgrMobiles = prodottiFinitiIgrMobiles;
           this.prodottiUscitaIgrMobiles = prodottiUscitaIgrMobiles;
           this.ricoveroImpianto = ricoveroImpianto;
           this.rifEntrata = rifEntrata;
           this.rifUscitaRecExt = rifUscitaRecExt;
           this.rifUscitaRecInt = rifUscitaRecInt;
           this.rifUscitaSmaExt = rifUscitaSmaExt;
           this.rifUscitaSmaInt = rifUscitaSmaInt;
           this.rifiutiProdottiIgrMobiles = rifiutiProdottiIgrMobiles;
           this.tipiTrattamento = tipiTrattamento;
           this.umCgmrnp = umCgmrnp;
           this.umCgmrp = umCgmrp;
           this.umCgprnp = umCgprnp;
           this.umCgprp = umCgprp;
           this.umComrnp = umComrnp;
           this.umComrp = umComrp;
    }


    /**
     * Gets the bilancio value for this CcostIgrMobile.
     * 
     * @return bilancio
     */
    public java.lang.Float getBilancio() {
        return bilancio;
    }


    /**
     * Sets the bilancio value for this CcostIgrMobile.
     * 
     * @param bilancio
     */
    public void setBilancio(java.lang.Float bilancio) {
        this.bilancio = bilancio;
    }


    /**
     * Gets the capGiorMassimaRnp value for this CcostIgrMobile.
     * 
     * @return capGiorMassimaRnp
     */
    public java.lang.Float getCapGiorMassimaRnp() {
        return capGiorMassimaRnp;
    }


    /**
     * Sets the capGiorMassimaRnp value for this CcostIgrMobile.
     * 
     * @param capGiorMassimaRnp
     */
    public void setCapGiorMassimaRnp(java.lang.Float capGiorMassimaRnp) {
        this.capGiorMassimaRnp = capGiorMassimaRnp;
    }


    /**
     * Gets the capGiorMassimaRp value for this CcostIgrMobile.
     * 
     * @return capGiorMassimaRp
     */
    public java.lang.Float getCapGiorMassimaRp() {
        return capGiorMassimaRp;
    }


    /**
     * Sets the capGiorMassimaRp value for this CcostIgrMobile.
     * 
     * @param capGiorMassimaRp
     */
    public void setCapGiorMassimaRp(java.lang.Float capGiorMassimaRp) {
        this.capGiorMassimaRp = capGiorMassimaRp;
    }


    /**
     * Gets the capGiorPrevistaRnp value for this CcostIgrMobile.
     * 
     * @return capGiorPrevistaRnp
     */
    public java.lang.Float getCapGiorPrevistaRnp() {
        return capGiorPrevistaRnp;
    }


    /**
     * Sets the capGiorPrevistaRnp value for this CcostIgrMobile.
     * 
     * @param capGiorPrevistaRnp
     */
    public void setCapGiorPrevistaRnp(java.lang.Float capGiorPrevistaRnp) {
        this.capGiorPrevistaRnp = capGiorPrevistaRnp;
    }


    /**
     * Gets the capGiorPrevistaRp value for this CcostIgrMobile.
     * 
     * @return capGiorPrevistaRp
     */
    public java.lang.Float getCapGiorPrevistaRp() {
        return capGiorPrevistaRp;
    }


    /**
     * Sets the capGiorPrevistaRp value for this CcostIgrMobile.
     * 
     * @param capGiorPrevistaRp
     */
    public void setCapGiorPrevistaRp(java.lang.Float capGiorPrevistaRp) {
        this.capGiorPrevistaRp = capGiorPrevistaRp;
    }


    /**
     * Gets the capOraMassimaRnp value for this CcostIgrMobile.
     * 
     * @return capOraMassimaRnp
     */
    public java.lang.Float getCapOraMassimaRnp() {
        return capOraMassimaRnp;
    }


    /**
     * Sets the capOraMassimaRnp value for this CcostIgrMobile.
     * 
     * @param capOraMassimaRnp
     */
    public void setCapOraMassimaRnp(java.lang.Float capOraMassimaRnp) {
        this.capOraMassimaRnp = capOraMassimaRnp;
    }


    /**
     * Gets the capOraMassimaRp value for this CcostIgrMobile.
     * 
     * @return capOraMassimaRp
     */
    public java.lang.Float getCapOraMassimaRp() {
        return capOraMassimaRp;
    }


    /**
     * Sets the capOraMassimaRp value for this CcostIgrMobile.
     * 
     * @param capOraMassimaRp
     */
    public void setCapOraMassimaRp(java.lang.Float capOraMassimaRp) {
        this.capOraMassimaRp = capOraMassimaRp;
    }


    /**
     * Gets the capRecupero value for this CcostIgrMobile.
     * 
     * @return capRecupero
     */
    public java.lang.Float getCapRecupero() {
        return capRecupero;
    }


    /**
     * Sets the capRecupero value for this CcostIgrMobile.
     * 
     * @param capRecupero
     */
    public void setCapRecupero(java.lang.Float capRecupero) {
        this.capRecupero = capRecupero;
    }


    /**
     * Gets the idCcost value for this CcostIgrMobile.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostIgrMobile.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the lineeTecnologiche value for this CcostIgrMobile.
     * 
     * @return lineeTecnologiche
     */
    public java.lang.String getLineeTecnologiche() {
        return lineeTecnologiche;
    }


    /**
     * Sets the lineeTecnologiche value for this CcostIgrMobile.
     * 
     * @param lineeTecnologiche
     */
    public void setLineeTecnologiche(java.lang.String lineeTecnologiche) {
        this.lineeTecnologiche = lineeTecnologiche;
    }


    /**
     * Gets the matEntrata value for this CcostIgrMobile.
     * 
     * @return matEntrata
     */
    public java.lang.Float getMatEntrata() {
        return matEntrata;
    }


    /**
     * Sets the matEntrata value for this CcostIgrMobile.
     * 
     * @param matEntrata
     */
    public void setMatEntrata(java.lang.Float matEntrata) {
        this.matEntrata = matEntrata;
    }


    /**
     * Gets the perditeProc value for this CcostIgrMobile.
     * 
     * @return perditeProc
     */
    public java.lang.Float getPerditeProc() {
        return perditeProc;
    }


    /**
     * Sets the perditeProc value for this CcostIgrMobile.
     * 
     * @param perditeProc
     */
    public void setPerditeProc(java.lang.Float perditeProc) {
        this.perditeProc = perditeProc;
    }


    /**
     * Gets the presidiAmbientali value for this CcostIgrMobile.
     * 
     * @return presidiAmbientali
     */
    public java.lang.String getPresidiAmbientali() {
        return presidiAmbientali;
    }


    /**
     * Sets the presidiAmbientali value for this CcostIgrMobile.
     * 
     * @param presidiAmbientali
     */
    public void setPresidiAmbientali(java.lang.String presidiAmbientali) {
        this.presidiAmbientali = presidiAmbientali;
    }


    /**
     * Gets the prodUscita value for this CcostIgrMobile.
     * 
     * @return prodUscita
     */
    public java.lang.Float getProdUscita() {
        return prodUscita;
    }


    /**
     * Sets the prodUscita value for this CcostIgrMobile.
     * 
     * @param prodUscita
     */
    public void setProdUscita(java.lang.Float prodUscita) {
        this.prodUscita = prodUscita;
    }


    /**
     * Gets the prodottiFinitiIgrMobiles value for this CcostIgrMobile.
     * 
     * @return prodottiFinitiIgrMobiles
     */
    public com.hyperborea.sira.ws.ProdottiFinitiIgrMobile[] getProdottiFinitiIgrMobiles() {
        return prodottiFinitiIgrMobiles;
    }


    /**
     * Sets the prodottiFinitiIgrMobiles value for this CcostIgrMobile.
     * 
     * @param prodottiFinitiIgrMobiles
     */
    public void setProdottiFinitiIgrMobiles(com.hyperborea.sira.ws.ProdottiFinitiIgrMobile[] prodottiFinitiIgrMobiles) {
        this.prodottiFinitiIgrMobiles = prodottiFinitiIgrMobiles;
    }

    public com.hyperborea.sira.ws.ProdottiFinitiIgrMobile getProdottiFinitiIgrMobiles(int i) {
        return this.prodottiFinitiIgrMobiles[i];
    }

    public void setProdottiFinitiIgrMobiles(int i, com.hyperborea.sira.ws.ProdottiFinitiIgrMobile _value) {
        this.prodottiFinitiIgrMobiles[i] = _value;
    }


    /**
     * Gets the prodottiUscitaIgrMobiles value for this CcostIgrMobile.
     * 
     * @return prodottiUscitaIgrMobiles
     */
    public com.hyperborea.sira.ws.ProdottiUscitaIgrMobile[] getProdottiUscitaIgrMobiles() {
        return prodottiUscitaIgrMobiles;
    }


    /**
     * Sets the prodottiUscitaIgrMobiles value for this CcostIgrMobile.
     * 
     * @param prodottiUscitaIgrMobiles
     */
    public void setProdottiUscitaIgrMobiles(com.hyperborea.sira.ws.ProdottiUscitaIgrMobile[] prodottiUscitaIgrMobiles) {
        this.prodottiUscitaIgrMobiles = prodottiUscitaIgrMobiles;
    }

    public com.hyperborea.sira.ws.ProdottiUscitaIgrMobile getProdottiUscitaIgrMobiles(int i) {
        return this.prodottiUscitaIgrMobiles[i];
    }

    public void setProdottiUscitaIgrMobiles(int i, com.hyperborea.sira.ws.ProdottiUscitaIgrMobile _value) {
        this.prodottiUscitaIgrMobiles[i] = _value;
    }


    /**
     * Gets the ricoveroImpianto value for this CcostIgrMobile.
     * 
     * @return ricoveroImpianto
     */
    public java.lang.String getRicoveroImpianto() {
        return ricoveroImpianto;
    }


    /**
     * Sets the ricoveroImpianto value for this CcostIgrMobile.
     * 
     * @param ricoveroImpianto
     */
    public void setRicoveroImpianto(java.lang.String ricoveroImpianto) {
        this.ricoveroImpianto = ricoveroImpianto;
    }


    /**
     * Gets the rifEntrata value for this CcostIgrMobile.
     * 
     * @return rifEntrata
     */
    public java.lang.Float getRifEntrata() {
        return rifEntrata;
    }


    /**
     * Sets the rifEntrata value for this CcostIgrMobile.
     * 
     * @param rifEntrata
     */
    public void setRifEntrata(java.lang.Float rifEntrata) {
        this.rifEntrata = rifEntrata;
    }


    /**
     * Gets the rifUscitaRecExt value for this CcostIgrMobile.
     * 
     * @return rifUscitaRecExt
     */
    public java.lang.Float getRifUscitaRecExt() {
        return rifUscitaRecExt;
    }


    /**
     * Sets the rifUscitaRecExt value for this CcostIgrMobile.
     * 
     * @param rifUscitaRecExt
     */
    public void setRifUscitaRecExt(java.lang.Float rifUscitaRecExt) {
        this.rifUscitaRecExt = rifUscitaRecExt;
    }


    /**
     * Gets the rifUscitaRecInt value for this CcostIgrMobile.
     * 
     * @return rifUscitaRecInt
     */
    public java.lang.Float getRifUscitaRecInt() {
        return rifUscitaRecInt;
    }


    /**
     * Sets the rifUscitaRecInt value for this CcostIgrMobile.
     * 
     * @param rifUscitaRecInt
     */
    public void setRifUscitaRecInt(java.lang.Float rifUscitaRecInt) {
        this.rifUscitaRecInt = rifUscitaRecInt;
    }


    /**
     * Gets the rifUscitaSmaExt value for this CcostIgrMobile.
     * 
     * @return rifUscitaSmaExt
     */
    public java.lang.Float getRifUscitaSmaExt() {
        return rifUscitaSmaExt;
    }


    /**
     * Sets the rifUscitaSmaExt value for this CcostIgrMobile.
     * 
     * @param rifUscitaSmaExt
     */
    public void setRifUscitaSmaExt(java.lang.Float rifUscitaSmaExt) {
        this.rifUscitaSmaExt = rifUscitaSmaExt;
    }


    /**
     * Gets the rifUscitaSmaInt value for this CcostIgrMobile.
     * 
     * @return rifUscitaSmaInt
     */
    public java.lang.Float getRifUscitaSmaInt() {
        return rifUscitaSmaInt;
    }


    /**
     * Sets the rifUscitaSmaInt value for this CcostIgrMobile.
     * 
     * @param rifUscitaSmaInt
     */
    public void setRifUscitaSmaInt(java.lang.Float rifUscitaSmaInt) {
        this.rifUscitaSmaInt = rifUscitaSmaInt;
    }


    /**
     * Gets the rifiutiProdottiIgrMobiles value for this CcostIgrMobile.
     * 
     * @return rifiutiProdottiIgrMobiles
     */
    public com.hyperborea.sira.ws.RifiutiProdottiIgrMobile[] getRifiutiProdottiIgrMobiles() {
        return rifiutiProdottiIgrMobiles;
    }


    /**
     * Sets the rifiutiProdottiIgrMobiles value for this CcostIgrMobile.
     * 
     * @param rifiutiProdottiIgrMobiles
     */
    public void setRifiutiProdottiIgrMobiles(com.hyperborea.sira.ws.RifiutiProdottiIgrMobile[] rifiutiProdottiIgrMobiles) {
        this.rifiutiProdottiIgrMobiles = rifiutiProdottiIgrMobiles;
    }

    public com.hyperborea.sira.ws.RifiutiProdottiIgrMobile getRifiutiProdottiIgrMobiles(int i) {
        return this.rifiutiProdottiIgrMobiles[i];
    }

    public void setRifiutiProdottiIgrMobiles(int i, com.hyperborea.sira.ws.RifiutiProdottiIgrMobile _value) {
        this.rifiutiProdottiIgrMobiles[i] = _value;
    }


    /**
     * Gets the tipiTrattamento value for this CcostIgrMobile.
     * 
     * @return tipiTrattamento
     */
    public java.lang.String getTipiTrattamento() {
        return tipiTrattamento;
    }


    /**
     * Sets the tipiTrattamento value for this CcostIgrMobile.
     * 
     * @param tipiTrattamento
     */
    public void setTipiTrattamento(java.lang.String tipiTrattamento) {
        this.tipiTrattamento = tipiTrattamento;
    }


    /**
     * Gets the umCgmrnp value for this CcostIgrMobile.
     * 
     * @return umCgmrnp
     */
    public com.hyperborea.sira.ws.IgrQuantitaGiornoUm getUmCgmrnp() {
        return umCgmrnp;
    }


    /**
     * Sets the umCgmrnp value for this CcostIgrMobile.
     * 
     * @param umCgmrnp
     */
    public void setUmCgmrnp(com.hyperborea.sira.ws.IgrQuantitaGiornoUm umCgmrnp) {
        this.umCgmrnp = umCgmrnp;
    }


    /**
     * Gets the umCgmrp value for this CcostIgrMobile.
     * 
     * @return umCgmrp
     */
    public com.hyperborea.sira.ws.IgrQuantitaGiornoUm getUmCgmrp() {
        return umCgmrp;
    }


    /**
     * Sets the umCgmrp value for this CcostIgrMobile.
     * 
     * @param umCgmrp
     */
    public void setUmCgmrp(com.hyperborea.sira.ws.IgrQuantitaGiornoUm umCgmrp) {
        this.umCgmrp = umCgmrp;
    }


    /**
     * Gets the umCgprnp value for this CcostIgrMobile.
     * 
     * @return umCgprnp
     */
    public com.hyperborea.sira.ws.IgrQuantitaGiornoUm getUmCgprnp() {
        return umCgprnp;
    }


    /**
     * Sets the umCgprnp value for this CcostIgrMobile.
     * 
     * @param umCgprnp
     */
    public void setUmCgprnp(com.hyperborea.sira.ws.IgrQuantitaGiornoUm umCgprnp) {
        this.umCgprnp = umCgprnp;
    }


    /**
     * Gets the umCgprp value for this CcostIgrMobile.
     * 
     * @return umCgprp
     */
    public com.hyperborea.sira.ws.IgrQuantitaGiornoUm getUmCgprp() {
        return umCgprp;
    }


    /**
     * Sets the umCgprp value for this CcostIgrMobile.
     * 
     * @param umCgprp
     */
    public void setUmCgprp(com.hyperborea.sira.ws.IgrQuantitaGiornoUm umCgprp) {
        this.umCgprp = umCgprp;
    }


    /**
     * Gets the umComrnp value for this CcostIgrMobile.
     * 
     * @return umComrnp
     */
    public com.hyperborea.sira.ws.IgrQuantitaOraUm getUmComrnp() {
        return umComrnp;
    }


    /**
     * Sets the umComrnp value for this CcostIgrMobile.
     * 
     * @param umComrnp
     */
    public void setUmComrnp(com.hyperborea.sira.ws.IgrQuantitaOraUm umComrnp) {
        this.umComrnp = umComrnp;
    }


    /**
     * Gets the umComrp value for this CcostIgrMobile.
     * 
     * @return umComrp
     */
    public com.hyperborea.sira.ws.IgrQuantitaOraUm getUmComrp() {
        return umComrp;
    }


    /**
     * Sets the umComrp value for this CcostIgrMobile.
     * 
     * @param umComrp
     */
    public void setUmComrp(com.hyperborea.sira.ws.IgrQuantitaOraUm umComrp) {
        this.umComrp = umComrp;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostIgrMobile)) return false;
        CcostIgrMobile other = (CcostIgrMobile) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bilancio==null && other.getBilancio()==null) || 
             (this.bilancio!=null &&
              this.bilancio.equals(other.getBilancio()))) &&
            ((this.capGiorMassimaRnp==null && other.getCapGiorMassimaRnp()==null) || 
             (this.capGiorMassimaRnp!=null &&
              this.capGiorMassimaRnp.equals(other.getCapGiorMassimaRnp()))) &&
            ((this.capGiorMassimaRp==null && other.getCapGiorMassimaRp()==null) || 
             (this.capGiorMassimaRp!=null &&
              this.capGiorMassimaRp.equals(other.getCapGiorMassimaRp()))) &&
            ((this.capGiorPrevistaRnp==null && other.getCapGiorPrevistaRnp()==null) || 
             (this.capGiorPrevistaRnp!=null &&
              this.capGiorPrevistaRnp.equals(other.getCapGiorPrevistaRnp()))) &&
            ((this.capGiorPrevistaRp==null && other.getCapGiorPrevistaRp()==null) || 
             (this.capGiorPrevistaRp!=null &&
              this.capGiorPrevistaRp.equals(other.getCapGiorPrevistaRp()))) &&
            ((this.capOraMassimaRnp==null && other.getCapOraMassimaRnp()==null) || 
             (this.capOraMassimaRnp!=null &&
              this.capOraMassimaRnp.equals(other.getCapOraMassimaRnp()))) &&
            ((this.capOraMassimaRp==null && other.getCapOraMassimaRp()==null) || 
             (this.capOraMassimaRp!=null &&
              this.capOraMassimaRp.equals(other.getCapOraMassimaRp()))) &&
            ((this.capRecupero==null && other.getCapRecupero()==null) || 
             (this.capRecupero!=null &&
              this.capRecupero.equals(other.getCapRecupero()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.lineeTecnologiche==null && other.getLineeTecnologiche()==null) || 
             (this.lineeTecnologiche!=null &&
              this.lineeTecnologiche.equals(other.getLineeTecnologiche()))) &&
            ((this.matEntrata==null && other.getMatEntrata()==null) || 
             (this.matEntrata!=null &&
              this.matEntrata.equals(other.getMatEntrata()))) &&
            ((this.perditeProc==null && other.getPerditeProc()==null) || 
             (this.perditeProc!=null &&
              this.perditeProc.equals(other.getPerditeProc()))) &&
            ((this.presidiAmbientali==null && other.getPresidiAmbientali()==null) || 
             (this.presidiAmbientali!=null &&
              this.presidiAmbientali.equals(other.getPresidiAmbientali()))) &&
            ((this.prodUscita==null && other.getProdUscita()==null) || 
             (this.prodUscita!=null &&
              this.prodUscita.equals(other.getProdUscita()))) &&
            ((this.prodottiFinitiIgrMobiles==null && other.getProdottiFinitiIgrMobiles()==null) || 
             (this.prodottiFinitiIgrMobiles!=null &&
              java.util.Arrays.equals(this.prodottiFinitiIgrMobiles, other.getProdottiFinitiIgrMobiles()))) &&
            ((this.prodottiUscitaIgrMobiles==null && other.getProdottiUscitaIgrMobiles()==null) || 
             (this.prodottiUscitaIgrMobiles!=null &&
              java.util.Arrays.equals(this.prodottiUscitaIgrMobiles, other.getProdottiUscitaIgrMobiles()))) &&
            ((this.ricoveroImpianto==null && other.getRicoveroImpianto()==null) || 
             (this.ricoveroImpianto!=null &&
              this.ricoveroImpianto.equals(other.getRicoveroImpianto()))) &&
            ((this.rifEntrata==null && other.getRifEntrata()==null) || 
             (this.rifEntrata!=null &&
              this.rifEntrata.equals(other.getRifEntrata()))) &&
            ((this.rifUscitaRecExt==null && other.getRifUscitaRecExt()==null) || 
             (this.rifUscitaRecExt!=null &&
              this.rifUscitaRecExt.equals(other.getRifUscitaRecExt()))) &&
            ((this.rifUscitaRecInt==null && other.getRifUscitaRecInt()==null) || 
             (this.rifUscitaRecInt!=null &&
              this.rifUscitaRecInt.equals(other.getRifUscitaRecInt()))) &&
            ((this.rifUscitaSmaExt==null && other.getRifUscitaSmaExt()==null) || 
             (this.rifUscitaSmaExt!=null &&
              this.rifUscitaSmaExt.equals(other.getRifUscitaSmaExt()))) &&
            ((this.rifUscitaSmaInt==null && other.getRifUscitaSmaInt()==null) || 
             (this.rifUscitaSmaInt!=null &&
              this.rifUscitaSmaInt.equals(other.getRifUscitaSmaInt()))) &&
            ((this.rifiutiProdottiIgrMobiles==null && other.getRifiutiProdottiIgrMobiles()==null) || 
             (this.rifiutiProdottiIgrMobiles!=null &&
              java.util.Arrays.equals(this.rifiutiProdottiIgrMobiles, other.getRifiutiProdottiIgrMobiles()))) &&
            ((this.tipiTrattamento==null && other.getTipiTrattamento()==null) || 
             (this.tipiTrattamento!=null &&
              this.tipiTrattamento.equals(other.getTipiTrattamento()))) &&
            ((this.umCgmrnp==null && other.getUmCgmrnp()==null) || 
             (this.umCgmrnp!=null &&
              this.umCgmrnp.equals(other.getUmCgmrnp()))) &&
            ((this.umCgmrp==null && other.getUmCgmrp()==null) || 
             (this.umCgmrp!=null &&
              this.umCgmrp.equals(other.getUmCgmrp()))) &&
            ((this.umCgprnp==null && other.getUmCgprnp()==null) || 
             (this.umCgprnp!=null &&
              this.umCgprnp.equals(other.getUmCgprnp()))) &&
            ((this.umCgprp==null && other.getUmCgprp()==null) || 
             (this.umCgprp!=null &&
              this.umCgprp.equals(other.getUmCgprp()))) &&
            ((this.umComrnp==null && other.getUmComrnp()==null) || 
             (this.umComrnp!=null &&
              this.umComrnp.equals(other.getUmComrnp()))) &&
            ((this.umComrp==null && other.getUmComrp()==null) || 
             (this.umComrp!=null &&
              this.umComrp.equals(other.getUmComrp())));
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
        if (getBilancio() != null) {
            _hashCode += getBilancio().hashCode();
        }
        if (getCapGiorMassimaRnp() != null) {
            _hashCode += getCapGiorMassimaRnp().hashCode();
        }
        if (getCapGiorMassimaRp() != null) {
            _hashCode += getCapGiorMassimaRp().hashCode();
        }
        if (getCapGiorPrevistaRnp() != null) {
            _hashCode += getCapGiorPrevistaRnp().hashCode();
        }
        if (getCapGiorPrevistaRp() != null) {
            _hashCode += getCapGiorPrevistaRp().hashCode();
        }
        if (getCapOraMassimaRnp() != null) {
            _hashCode += getCapOraMassimaRnp().hashCode();
        }
        if (getCapOraMassimaRp() != null) {
            _hashCode += getCapOraMassimaRp().hashCode();
        }
        if (getCapRecupero() != null) {
            _hashCode += getCapRecupero().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getLineeTecnologiche() != null) {
            _hashCode += getLineeTecnologiche().hashCode();
        }
        if (getMatEntrata() != null) {
            _hashCode += getMatEntrata().hashCode();
        }
        if (getPerditeProc() != null) {
            _hashCode += getPerditeProc().hashCode();
        }
        if (getPresidiAmbientali() != null) {
            _hashCode += getPresidiAmbientali().hashCode();
        }
        if (getProdUscita() != null) {
            _hashCode += getProdUscita().hashCode();
        }
        if (getProdottiFinitiIgrMobiles() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProdottiFinitiIgrMobiles());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProdottiFinitiIgrMobiles(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProdottiUscitaIgrMobiles() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProdottiUscitaIgrMobiles());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProdottiUscitaIgrMobiles(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRicoveroImpianto() != null) {
            _hashCode += getRicoveroImpianto().hashCode();
        }
        if (getRifEntrata() != null) {
            _hashCode += getRifEntrata().hashCode();
        }
        if (getRifUscitaRecExt() != null) {
            _hashCode += getRifUscitaRecExt().hashCode();
        }
        if (getRifUscitaRecInt() != null) {
            _hashCode += getRifUscitaRecInt().hashCode();
        }
        if (getRifUscitaSmaExt() != null) {
            _hashCode += getRifUscitaSmaExt().hashCode();
        }
        if (getRifUscitaSmaInt() != null) {
            _hashCode += getRifUscitaSmaInt().hashCode();
        }
        if (getRifiutiProdottiIgrMobiles() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRifiutiProdottiIgrMobiles());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRifiutiProdottiIgrMobiles(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTipiTrattamento() != null) {
            _hashCode += getTipiTrattamento().hashCode();
        }
        if (getUmCgmrnp() != null) {
            _hashCode += getUmCgmrnp().hashCode();
        }
        if (getUmCgmrp() != null) {
            _hashCode += getUmCgmrp().hashCode();
        }
        if (getUmCgprnp() != null) {
            _hashCode += getUmCgprnp().hashCode();
        }
        if (getUmCgprp() != null) {
            _hashCode += getUmCgprp().hashCode();
        }
        if (getUmComrnp() != null) {
            _hashCode += getUmComrnp().hashCode();
        }
        if (getUmComrp() != null) {
            _hashCode += getUmComrp().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostIgrMobile.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrMobile"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bilancio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bilancio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capGiorMassimaRnp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capGiorMassimaRnp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capGiorMassimaRp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capGiorMassimaRp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capGiorPrevistaRnp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capGiorPrevistaRnp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capGiorPrevistaRp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capGiorPrevistaRp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capOraMassimaRnp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capOraMassimaRnp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capOraMassimaRp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capOraMassimaRp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capRecupero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capRecupero"));
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
        elemField.setFieldName("lineeTecnologiche");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lineeTecnologiche"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matEntrata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "matEntrata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("perditeProc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "perditeProc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("presidiAmbientali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "presidiAmbientali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodUscita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodUscita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodottiFinitiIgrMobiles");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodottiFinitiIgrMobiles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodottiFinitiIgrMobile"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodottiUscitaIgrMobiles");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodottiUscitaIgrMobiles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodottiUscitaIgrMobile"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ricoveroImpianto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ricoveroImpianto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rifEntrata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rifEntrata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rifUscitaRecExt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rifUscitaRecExt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rifUscitaRecInt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rifUscitaRecInt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rifUscitaSmaExt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rifUscitaSmaExt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rifUscitaSmaInt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rifUscitaSmaInt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rifiutiProdottiIgrMobiles");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rifiutiProdottiIgrMobiles"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "rifiutiProdottiIgrMobile"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipiTrattamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipiTrattamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umCgmrnp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umCgmrnp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "igrQuantitaGiornoUm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umCgmrp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umCgmrp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "igrQuantitaGiornoUm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umCgprnp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umCgprnp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "igrQuantitaGiornoUm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umCgprp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umCgprp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "igrQuantitaGiornoUm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umComrnp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umComrnp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "igrQuantitaOraUm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umComrp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umComrp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "igrQuantitaOraUm"));
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
