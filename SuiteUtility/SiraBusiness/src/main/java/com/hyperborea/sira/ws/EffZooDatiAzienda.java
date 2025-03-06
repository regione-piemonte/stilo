/**
 * EffZooDatiAzienda.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class EffZooDatiAzienda  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String codiceAsl;

    private java.lang.String cuaa;

    private java.lang.Integer idCcost;

    private java.lang.String ordinamentoColt;

    private com.hyperborea.sira.ws.ProdEffZooTabellaA[] prodEffZooTabellaAs;

    private com.hyperborea.sira.ws.ProdEffZooTabellaB[] prodEffZooTabellaBs;

    private com.hyperborea.sira.ws.ProdEffZooTabellaC prodEffZooTabellaC;

    private com.hyperborea.sira.ws.ProdEffZooTabellaD prodEffZooTabellaD;

    private com.hyperborea.sira.ws.ProdEffZooTabellaE prodEffZooTabellaE;

    private com.hyperborea.sira.ws.ProdEffZooTabellaF prodEffZooTabellaF;

    private com.hyperborea.sira.ws.ProdEffZooTabellaG[] prodEffZooTabellaGs;

    private com.hyperborea.sira.ws.ProdEffZooTabellaH[] prodEffZooTabellaHs;

    private com.hyperborea.sira.ws.ProdEffZooTabellaI[] prodEffZooTabellaIs;

    private com.hyperborea.sira.ws.ProdEffZooTabellaJ[] prodEffZooTabellaJs;

    private com.hyperborea.sira.ws.ProdEffZooTabellaK[] prodEffZooTabellaKs;

    private java.lang.String pua;

    private java.lang.String rimozioneDeiezioni;

    private java.lang.Float sauZo;

    private java.lang.Float sauZoEffluenti;

    private java.lang.Float sauZoReflui;

    private java.lang.Float sauZvReflui;

    private java.lang.Float supProduttivaZo;

    private java.lang.Float supProduttivaZv;

    private java.lang.Float supTotale;

    private java.lang.String tecnicheDistribuzione;

    private java.lang.String tipoAlimentazione;

    private com.hyperborea.sira.ws.UtilEffZooTabellaN[] utilEffZooTabellaNs;

    private com.hyperborea.sira.ws.UtilEffZooTabellaO[] utilEffZooTabellaOs;

    private com.hyperborea.sira.ws.UtilEffZooTabellaP[] utilEffZooTabellaPs;

    private com.hyperborea.sira.ws.UtilEffZooTabellaR[] utilEffZooTabellaRs;

    private com.hyperborea.sira.ws.UtilEffZooTabellaS[] utilEffZooTabellaSs;

    private com.hyperborea.sira.ws.UtilEffZooTabellaT[] utilEffZooTabellaTs;

    private com.hyperborea.sira.ws.UtilEffZooTabellaU[] utilEffZooTabellaUs;

    private com.hyperborea.sira.ws.VocProduzioneUtilizzo vocProduzioneUtilizzo;

    private com.hyperborea.sira.ws.VocQuantitativo vocQuantitativo;

    private com.hyperborea.sira.ws.VocZona vocZona;

    public EffZooDatiAzienda() {
    }

    public EffZooDatiAzienda(
           java.lang.String codiceAsl,
           java.lang.String cuaa,
           java.lang.Integer idCcost,
           java.lang.String ordinamentoColt,
           com.hyperborea.sira.ws.ProdEffZooTabellaA[] prodEffZooTabellaAs,
           com.hyperborea.sira.ws.ProdEffZooTabellaB[] prodEffZooTabellaBs,
           com.hyperborea.sira.ws.ProdEffZooTabellaC prodEffZooTabellaC,
           com.hyperborea.sira.ws.ProdEffZooTabellaD prodEffZooTabellaD,
           com.hyperborea.sira.ws.ProdEffZooTabellaE prodEffZooTabellaE,
           com.hyperborea.sira.ws.ProdEffZooTabellaF prodEffZooTabellaF,
           com.hyperborea.sira.ws.ProdEffZooTabellaG[] prodEffZooTabellaGs,
           com.hyperborea.sira.ws.ProdEffZooTabellaH[] prodEffZooTabellaHs,
           com.hyperborea.sira.ws.ProdEffZooTabellaI[] prodEffZooTabellaIs,
           com.hyperborea.sira.ws.ProdEffZooTabellaJ[] prodEffZooTabellaJs,
           com.hyperborea.sira.ws.ProdEffZooTabellaK[] prodEffZooTabellaKs,
           java.lang.String pua,
           java.lang.String rimozioneDeiezioni,
           java.lang.Float sauZo,
           java.lang.Float sauZoEffluenti,
           java.lang.Float sauZoReflui,
           java.lang.Float sauZvReflui,
           java.lang.Float supProduttivaZo,
           java.lang.Float supProduttivaZv,
           java.lang.Float supTotale,
           java.lang.String tecnicheDistribuzione,
           java.lang.String tipoAlimentazione,
           com.hyperborea.sira.ws.UtilEffZooTabellaN[] utilEffZooTabellaNs,
           com.hyperborea.sira.ws.UtilEffZooTabellaO[] utilEffZooTabellaOs,
           com.hyperborea.sira.ws.UtilEffZooTabellaP[] utilEffZooTabellaPs,
           com.hyperborea.sira.ws.UtilEffZooTabellaR[] utilEffZooTabellaRs,
           com.hyperborea.sira.ws.UtilEffZooTabellaS[] utilEffZooTabellaSs,
           com.hyperborea.sira.ws.UtilEffZooTabellaT[] utilEffZooTabellaTs,
           com.hyperborea.sira.ws.UtilEffZooTabellaU[] utilEffZooTabellaUs,
           com.hyperborea.sira.ws.VocProduzioneUtilizzo vocProduzioneUtilizzo,
           com.hyperborea.sira.ws.VocQuantitativo vocQuantitativo,
           com.hyperborea.sira.ws.VocZona vocZona) {
        this.codiceAsl = codiceAsl;
        this.cuaa = cuaa;
        this.idCcost = idCcost;
        this.ordinamentoColt = ordinamentoColt;
        this.prodEffZooTabellaAs = prodEffZooTabellaAs;
        this.prodEffZooTabellaBs = prodEffZooTabellaBs;
        this.prodEffZooTabellaC = prodEffZooTabellaC;
        this.prodEffZooTabellaD = prodEffZooTabellaD;
        this.prodEffZooTabellaE = prodEffZooTabellaE;
        this.prodEffZooTabellaF = prodEffZooTabellaF;
        this.prodEffZooTabellaGs = prodEffZooTabellaGs;
        this.prodEffZooTabellaHs = prodEffZooTabellaHs;
        this.prodEffZooTabellaIs = prodEffZooTabellaIs;
        this.prodEffZooTabellaJs = prodEffZooTabellaJs;
        this.prodEffZooTabellaKs = prodEffZooTabellaKs;
        this.pua = pua;
        this.rimozioneDeiezioni = rimozioneDeiezioni;
        this.sauZo = sauZo;
        this.sauZoEffluenti = sauZoEffluenti;
        this.sauZoReflui = sauZoReflui;
        this.sauZvReflui = sauZvReflui;
        this.supProduttivaZo = supProduttivaZo;
        this.supProduttivaZv = supProduttivaZv;
        this.supTotale = supTotale;
        this.tecnicheDistribuzione = tecnicheDistribuzione;
        this.tipoAlimentazione = tipoAlimentazione;
        this.utilEffZooTabellaNs = utilEffZooTabellaNs;
        this.utilEffZooTabellaOs = utilEffZooTabellaOs;
        this.utilEffZooTabellaPs = utilEffZooTabellaPs;
        this.utilEffZooTabellaRs = utilEffZooTabellaRs;
        this.utilEffZooTabellaSs = utilEffZooTabellaSs;
        this.utilEffZooTabellaTs = utilEffZooTabellaTs;
        this.utilEffZooTabellaUs = utilEffZooTabellaUs;
        this.vocProduzioneUtilizzo = vocProduzioneUtilizzo;
        this.vocQuantitativo = vocQuantitativo;
        this.vocZona = vocZona;
    }


    /**
     * Gets the codiceAsl value for this EffZooDatiAzienda.
     * 
     * @return codiceAsl
     */
    public java.lang.String getCodiceAsl() {
        return codiceAsl;
    }


    /**
     * Sets the codiceAsl value for this EffZooDatiAzienda.
     * 
     * @param codiceAsl
     */
    public void setCodiceAsl(java.lang.String codiceAsl) {
        this.codiceAsl = codiceAsl;
    }


    /**
     * Gets the cuaa value for this EffZooDatiAzienda.
     * 
     * @return cuaa
     */
    public java.lang.String getCuaa() {
        return cuaa;
    }


    /**
     * Sets the cuaa value for this EffZooDatiAzienda.
     * 
     * @param cuaa
     */
    public void setCuaa(java.lang.String cuaa) {
        this.cuaa = cuaa;
    }


    /**
     * Gets the idCcost value for this EffZooDatiAzienda.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this EffZooDatiAzienda.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the ordinamentoColt value for this EffZooDatiAzienda.
     * 
     * @return ordinamentoColt
     */
    public java.lang.String getOrdinamentoColt() {
        return ordinamentoColt;
    }


    /**
     * Sets the ordinamentoColt value for this EffZooDatiAzienda.
     * 
     * @param ordinamentoColt
     */
    public void setOrdinamentoColt(java.lang.String ordinamentoColt) {
        this.ordinamentoColt = ordinamentoColt;
    }


    /**
     * Gets the prodEffZooTabellaAs value for this EffZooDatiAzienda.
     * 
     * @return prodEffZooTabellaAs
     */
    public com.hyperborea.sira.ws.ProdEffZooTabellaA[] getProdEffZooTabellaAs() {
        return prodEffZooTabellaAs;
    }


    /**
     * Sets the prodEffZooTabellaAs value for this EffZooDatiAzienda.
     * 
     * @param prodEffZooTabellaAs
     */
    public void setProdEffZooTabellaAs(com.hyperborea.sira.ws.ProdEffZooTabellaA[] prodEffZooTabellaAs) {
        this.prodEffZooTabellaAs = prodEffZooTabellaAs;
    }

    public com.hyperborea.sira.ws.ProdEffZooTabellaA getProdEffZooTabellaAs(int i) {
        return this.prodEffZooTabellaAs[i];
    }

    public void setProdEffZooTabellaAs(int i, com.hyperborea.sira.ws.ProdEffZooTabellaA _value) {
        this.prodEffZooTabellaAs[i] = _value;
    }


    /**
     * Gets the prodEffZooTabellaBs value for this EffZooDatiAzienda.
     * 
     * @return prodEffZooTabellaBs
     */
    public com.hyperborea.sira.ws.ProdEffZooTabellaB[] getProdEffZooTabellaBs() {
        return prodEffZooTabellaBs;
    }


    /**
     * Sets the prodEffZooTabellaBs value for this EffZooDatiAzienda.
     * 
     * @param prodEffZooTabellaBs
     */
    public void setProdEffZooTabellaBs(com.hyperborea.sira.ws.ProdEffZooTabellaB[] prodEffZooTabellaBs) {
        this.prodEffZooTabellaBs = prodEffZooTabellaBs;
    }

    public com.hyperborea.sira.ws.ProdEffZooTabellaB getProdEffZooTabellaBs(int i) {
        return this.prodEffZooTabellaBs[i];
    }

    public void setProdEffZooTabellaBs(int i, com.hyperborea.sira.ws.ProdEffZooTabellaB _value) {
        this.prodEffZooTabellaBs[i] = _value;
    }


    /**
     * Gets the prodEffZooTabellaC value for this EffZooDatiAzienda.
     * 
     * @return prodEffZooTabellaC
     */
    public com.hyperborea.sira.ws.ProdEffZooTabellaC getProdEffZooTabellaC() {
        return prodEffZooTabellaC;
    }


    /**
     * Sets the prodEffZooTabellaC value for this EffZooDatiAzienda.
     * 
     * @param prodEffZooTabellaC
     */
    public void setProdEffZooTabellaC(com.hyperborea.sira.ws.ProdEffZooTabellaC prodEffZooTabellaC) {
        this.prodEffZooTabellaC = prodEffZooTabellaC;
    }


    /**
     * Gets the prodEffZooTabellaD value for this EffZooDatiAzienda.
     * 
     * @return prodEffZooTabellaD
     */
    public com.hyperborea.sira.ws.ProdEffZooTabellaD getProdEffZooTabellaD() {
        return prodEffZooTabellaD;
    }


    /**
     * Sets the prodEffZooTabellaD value for this EffZooDatiAzienda.
     * 
     * @param prodEffZooTabellaD
     */
    public void setProdEffZooTabellaD(com.hyperborea.sira.ws.ProdEffZooTabellaD prodEffZooTabellaD) {
        this.prodEffZooTabellaD = prodEffZooTabellaD;
    }


    /**
     * Gets the prodEffZooTabellaE value for this EffZooDatiAzienda.
     * 
     * @return prodEffZooTabellaE
     */
    public com.hyperborea.sira.ws.ProdEffZooTabellaE getProdEffZooTabellaE() {
        return prodEffZooTabellaE;
    }


    /**
     * Sets the prodEffZooTabellaE value for this EffZooDatiAzienda.
     * 
     * @param prodEffZooTabellaE
     */
    public void setProdEffZooTabellaE(com.hyperborea.sira.ws.ProdEffZooTabellaE prodEffZooTabellaE) {
        this.prodEffZooTabellaE = prodEffZooTabellaE;
    }


    /**
     * Gets the prodEffZooTabellaF value for this EffZooDatiAzienda.
     * 
     * @return prodEffZooTabellaF
     */
    public com.hyperborea.sira.ws.ProdEffZooTabellaF getProdEffZooTabellaF() {
        return prodEffZooTabellaF;
    }


    /**
     * Sets the prodEffZooTabellaF value for this EffZooDatiAzienda.
     * 
     * @param prodEffZooTabellaF
     */
    public void setProdEffZooTabellaF(com.hyperborea.sira.ws.ProdEffZooTabellaF prodEffZooTabellaF) {
        this.prodEffZooTabellaF = prodEffZooTabellaF;
    }


    /**
     * Gets the prodEffZooTabellaGs value for this EffZooDatiAzienda.
     * 
     * @return prodEffZooTabellaGs
     */
    public com.hyperborea.sira.ws.ProdEffZooTabellaG[] getProdEffZooTabellaGs() {
        return prodEffZooTabellaGs;
    }


    /**
     * Sets the prodEffZooTabellaGs value for this EffZooDatiAzienda.
     * 
     * @param prodEffZooTabellaGs
     */
    public void setProdEffZooTabellaGs(com.hyperborea.sira.ws.ProdEffZooTabellaG[] prodEffZooTabellaGs) {
        this.prodEffZooTabellaGs = prodEffZooTabellaGs;
    }

    public com.hyperborea.sira.ws.ProdEffZooTabellaG getProdEffZooTabellaGs(int i) {
        return this.prodEffZooTabellaGs[i];
    }

    public void setProdEffZooTabellaGs(int i, com.hyperborea.sira.ws.ProdEffZooTabellaG _value) {
        this.prodEffZooTabellaGs[i] = _value;
    }


    /**
     * Gets the prodEffZooTabellaHs value for this EffZooDatiAzienda.
     * 
     * @return prodEffZooTabellaHs
     */
    public com.hyperborea.sira.ws.ProdEffZooTabellaH[] getProdEffZooTabellaHs() {
        return prodEffZooTabellaHs;
    }


    /**
     * Sets the prodEffZooTabellaHs value for this EffZooDatiAzienda.
     * 
     * @param prodEffZooTabellaHs
     */
    public void setProdEffZooTabellaHs(com.hyperborea.sira.ws.ProdEffZooTabellaH[] prodEffZooTabellaHs) {
        this.prodEffZooTabellaHs = prodEffZooTabellaHs;
    }

    public com.hyperborea.sira.ws.ProdEffZooTabellaH getProdEffZooTabellaHs(int i) {
        return this.prodEffZooTabellaHs[i];
    }

    public void setProdEffZooTabellaHs(int i, com.hyperborea.sira.ws.ProdEffZooTabellaH _value) {
        this.prodEffZooTabellaHs[i] = _value;
    }


    /**
     * Gets the prodEffZooTabellaIs value for this EffZooDatiAzienda.
     * 
     * @return prodEffZooTabellaIs
     */
    public com.hyperborea.sira.ws.ProdEffZooTabellaI[] getProdEffZooTabellaIs() {
        return prodEffZooTabellaIs;
    }


    /**
     * Sets the prodEffZooTabellaIs value for this EffZooDatiAzienda.
     * 
     * @param prodEffZooTabellaIs
     */
    public void setProdEffZooTabellaIs(com.hyperborea.sira.ws.ProdEffZooTabellaI[] prodEffZooTabellaIs) {
        this.prodEffZooTabellaIs = prodEffZooTabellaIs;
    }

    public com.hyperborea.sira.ws.ProdEffZooTabellaI getProdEffZooTabellaIs(int i) {
        return this.prodEffZooTabellaIs[i];
    }

    public void setProdEffZooTabellaIs(int i, com.hyperborea.sira.ws.ProdEffZooTabellaI _value) {
        this.prodEffZooTabellaIs[i] = _value;
    }


    /**
     * Gets the prodEffZooTabellaJs value for this EffZooDatiAzienda.
     * 
     * @return prodEffZooTabellaJs
     */
    public com.hyperborea.sira.ws.ProdEffZooTabellaJ[] getProdEffZooTabellaJs() {
        return prodEffZooTabellaJs;
    }


    /**
     * Sets the prodEffZooTabellaJs value for this EffZooDatiAzienda.
     * 
     * @param prodEffZooTabellaJs
     */
    public void setProdEffZooTabellaJs(com.hyperborea.sira.ws.ProdEffZooTabellaJ[] prodEffZooTabellaJs) {
        this.prodEffZooTabellaJs = prodEffZooTabellaJs;
    }

    public com.hyperborea.sira.ws.ProdEffZooTabellaJ getProdEffZooTabellaJs(int i) {
        return this.prodEffZooTabellaJs[i];
    }

    public void setProdEffZooTabellaJs(int i, com.hyperborea.sira.ws.ProdEffZooTabellaJ _value) {
        this.prodEffZooTabellaJs[i] = _value;
    }


    /**
     * Gets the prodEffZooTabellaKs value for this EffZooDatiAzienda.
     * 
     * @return prodEffZooTabellaKs
     */
    public com.hyperborea.sira.ws.ProdEffZooTabellaK[] getProdEffZooTabellaKs() {
        return prodEffZooTabellaKs;
    }


    /**
     * Sets the prodEffZooTabellaKs value for this EffZooDatiAzienda.
     * 
     * @param prodEffZooTabellaKs
     */
    public void setProdEffZooTabellaKs(com.hyperborea.sira.ws.ProdEffZooTabellaK[] prodEffZooTabellaKs) {
        this.prodEffZooTabellaKs = prodEffZooTabellaKs;
    }

    public com.hyperborea.sira.ws.ProdEffZooTabellaK getProdEffZooTabellaKs(int i) {
        return this.prodEffZooTabellaKs[i];
    }

    public void setProdEffZooTabellaKs(int i, com.hyperborea.sira.ws.ProdEffZooTabellaK _value) {
        this.prodEffZooTabellaKs[i] = _value;
    }


    /**
     * Gets the pua value for this EffZooDatiAzienda.
     * 
     * @return pua
     */
    public java.lang.String getPua() {
        return pua;
    }


    /**
     * Sets the pua value for this EffZooDatiAzienda.
     * 
     * @param pua
     */
    public void setPua(java.lang.String pua) {
        this.pua = pua;
    }


    /**
     * Gets the rimozioneDeiezioni value for this EffZooDatiAzienda.
     * 
     * @return rimozioneDeiezioni
     */
    public java.lang.String getRimozioneDeiezioni() {
        return rimozioneDeiezioni;
    }


    /**
     * Sets the rimozioneDeiezioni value for this EffZooDatiAzienda.
     * 
     * @param rimozioneDeiezioni
     */
    public void setRimozioneDeiezioni(java.lang.String rimozioneDeiezioni) {
        this.rimozioneDeiezioni = rimozioneDeiezioni;
    }


    /**
     * Gets the sauZo value for this EffZooDatiAzienda.
     * 
     * @return sauZo
     */
    public java.lang.Float getSauZo() {
        return sauZo;
    }


    /**
     * Sets the sauZo value for this EffZooDatiAzienda.
     * 
     * @param sauZo
     */
    public void setSauZo(java.lang.Float sauZo) {
        this.sauZo = sauZo;
    }


    /**
     * Gets the sauZoEffluenti value for this EffZooDatiAzienda.
     * 
     * @return sauZoEffluenti
     */
    public java.lang.Float getSauZoEffluenti() {
        return sauZoEffluenti;
    }


    /**
     * Sets the sauZoEffluenti value for this EffZooDatiAzienda.
     * 
     * @param sauZoEffluenti
     */
    public void setSauZoEffluenti(java.lang.Float sauZoEffluenti) {
        this.sauZoEffluenti = sauZoEffluenti;
    }


    /**
     * Gets the sauZoReflui value for this EffZooDatiAzienda.
     * 
     * @return sauZoReflui
     */
    public java.lang.Float getSauZoReflui() {
        return sauZoReflui;
    }


    /**
     * Sets the sauZoReflui value for this EffZooDatiAzienda.
     * 
     * @param sauZoReflui
     */
    public void setSauZoReflui(java.lang.Float sauZoReflui) {
        this.sauZoReflui = sauZoReflui;
    }


    /**
     * Gets the sauZvReflui value for this EffZooDatiAzienda.
     * 
     * @return sauZvReflui
     */
    public java.lang.Float getSauZvReflui() {
        return sauZvReflui;
    }


    /**
     * Sets the sauZvReflui value for this EffZooDatiAzienda.
     * 
     * @param sauZvReflui
     */
    public void setSauZvReflui(java.lang.Float sauZvReflui) {
        this.sauZvReflui = sauZvReflui;
    }


    /**
     * Gets the supProduttivaZo value for this EffZooDatiAzienda.
     * 
     * @return supProduttivaZo
     */
    public java.lang.Float getSupProduttivaZo() {
        return supProduttivaZo;
    }


    /**
     * Sets the supProduttivaZo value for this EffZooDatiAzienda.
     * 
     * @param supProduttivaZo
     */
    public void setSupProduttivaZo(java.lang.Float supProduttivaZo) {
        this.supProduttivaZo = supProduttivaZo;
    }


    /**
     * Gets the supProduttivaZv value for this EffZooDatiAzienda.
     * 
     * @return supProduttivaZv
     */
    public java.lang.Float getSupProduttivaZv() {
        return supProduttivaZv;
    }


    /**
     * Sets the supProduttivaZv value for this EffZooDatiAzienda.
     * 
     * @param supProduttivaZv
     */
    public void setSupProduttivaZv(java.lang.Float supProduttivaZv) {
        this.supProduttivaZv = supProduttivaZv;
    }


    /**
     * Gets the supTotale value for this EffZooDatiAzienda.
     * 
     * @return supTotale
     */
    public java.lang.Float getSupTotale() {
        return supTotale;
    }


    /**
     * Sets the supTotale value for this EffZooDatiAzienda.
     * 
     * @param supTotale
     */
    public void setSupTotale(java.lang.Float supTotale) {
        this.supTotale = supTotale;
    }


    /**
     * Gets the tecnicheDistribuzione value for this EffZooDatiAzienda.
     * 
     * @return tecnicheDistribuzione
     */
    public java.lang.String getTecnicheDistribuzione() {
        return tecnicheDistribuzione;
    }


    /**
     * Sets the tecnicheDistribuzione value for this EffZooDatiAzienda.
     * 
     * @param tecnicheDistribuzione
     */
    public void setTecnicheDistribuzione(java.lang.String tecnicheDistribuzione) {
        this.tecnicheDistribuzione = tecnicheDistribuzione;
    }


    /**
     * Gets the tipoAlimentazione value for this EffZooDatiAzienda.
     * 
     * @return tipoAlimentazione
     */
    public java.lang.String getTipoAlimentazione() {
        return tipoAlimentazione;
    }


    /**
     * Sets the tipoAlimentazione value for this EffZooDatiAzienda.
     * 
     * @param tipoAlimentazione
     */
    public void setTipoAlimentazione(java.lang.String tipoAlimentazione) {
        this.tipoAlimentazione = tipoAlimentazione;
    }


    /**
     * Gets the utilEffZooTabellaNs value for this EffZooDatiAzienda.
     * 
     * @return utilEffZooTabellaNs
     */
    public com.hyperborea.sira.ws.UtilEffZooTabellaN[] getUtilEffZooTabellaNs() {
        return utilEffZooTabellaNs;
    }


    /**
     * Sets the utilEffZooTabellaNs value for this EffZooDatiAzienda.
     * 
     * @param utilEffZooTabellaNs
     */
    public void setUtilEffZooTabellaNs(com.hyperborea.sira.ws.UtilEffZooTabellaN[] utilEffZooTabellaNs) {
        this.utilEffZooTabellaNs = utilEffZooTabellaNs;
    }

    public com.hyperborea.sira.ws.UtilEffZooTabellaN getUtilEffZooTabellaNs(int i) {
        return this.utilEffZooTabellaNs[i];
    }

    public void setUtilEffZooTabellaNs(int i, com.hyperborea.sira.ws.UtilEffZooTabellaN _value) {
        this.utilEffZooTabellaNs[i] = _value;
    }


    /**
     * Gets the utilEffZooTabellaOs value for this EffZooDatiAzienda.
     * 
     * @return utilEffZooTabellaOs
     */
    public com.hyperborea.sira.ws.UtilEffZooTabellaO[] getUtilEffZooTabellaOs() {
        return utilEffZooTabellaOs;
    }


    /**
     * Sets the utilEffZooTabellaOs value for this EffZooDatiAzienda.
     * 
     * @param utilEffZooTabellaOs
     */
    public void setUtilEffZooTabellaOs(com.hyperborea.sira.ws.UtilEffZooTabellaO[] utilEffZooTabellaOs) {
        this.utilEffZooTabellaOs = utilEffZooTabellaOs;
    }

    public com.hyperborea.sira.ws.UtilEffZooTabellaO getUtilEffZooTabellaOs(int i) {
        return this.utilEffZooTabellaOs[i];
    }

    public void setUtilEffZooTabellaOs(int i, com.hyperborea.sira.ws.UtilEffZooTabellaO _value) {
        this.utilEffZooTabellaOs[i] = _value;
    }


    /**
     * Gets the utilEffZooTabellaPs value for this EffZooDatiAzienda.
     * 
     * @return utilEffZooTabellaPs
     */
    public com.hyperborea.sira.ws.UtilEffZooTabellaP[] getUtilEffZooTabellaPs() {
        return utilEffZooTabellaPs;
    }


    /**
     * Sets the utilEffZooTabellaPs value for this EffZooDatiAzienda.
     * 
     * @param utilEffZooTabellaPs
     */
    public void setUtilEffZooTabellaPs(com.hyperborea.sira.ws.UtilEffZooTabellaP[] utilEffZooTabellaPs) {
        this.utilEffZooTabellaPs = utilEffZooTabellaPs;
    }

    public com.hyperborea.sira.ws.UtilEffZooTabellaP getUtilEffZooTabellaPs(int i) {
        return this.utilEffZooTabellaPs[i];
    }

    public void setUtilEffZooTabellaPs(int i, com.hyperborea.sira.ws.UtilEffZooTabellaP _value) {
        this.utilEffZooTabellaPs[i] = _value;
    }


    /**
     * Gets the utilEffZooTabellaRs value for this EffZooDatiAzienda.
     * 
     * @return utilEffZooTabellaRs
     */
    public com.hyperborea.sira.ws.UtilEffZooTabellaR[] getUtilEffZooTabellaRs() {
        return utilEffZooTabellaRs;
    }


    /**
     * Sets the utilEffZooTabellaRs value for this EffZooDatiAzienda.
     * 
     * @param utilEffZooTabellaRs
     */
    public void setUtilEffZooTabellaRs(com.hyperborea.sira.ws.UtilEffZooTabellaR[] utilEffZooTabellaRs) {
        this.utilEffZooTabellaRs = utilEffZooTabellaRs;
    }

    public com.hyperborea.sira.ws.UtilEffZooTabellaR getUtilEffZooTabellaRs(int i) {
        return this.utilEffZooTabellaRs[i];
    }

    public void setUtilEffZooTabellaRs(int i, com.hyperborea.sira.ws.UtilEffZooTabellaR _value) {
        this.utilEffZooTabellaRs[i] = _value;
    }


    /**
     * Gets the utilEffZooTabellaSs value for this EffZooDatiAzienda.
     * 
     * @return utilEffZooTabellaSs
     */
    public com.hyperborea.sira.ws.UtilEffZooTabellaS[] getUtilEffZooTabellaSs() {
        return utilEffZooTabellaSs;
    }


    /**
     * Sets the utilEffZooTabellaSs value for this EffZooDatiAzienda.
     * 
     * @param utilEffZooTabellaSs
     */
    public void setUtilEffZooTabellaSs(com.hyperborea.sira.ws.UtilEffZooTabellaS[] utilEffZooTabellaSs) {
        this.utilEffZooTabellaSs = utilEffZooTabellaSs;
    }

    public com.hyperborea.sira.ws.UtilEffZooTabellaS getUtilEffZooTabellaSs(int i) {
        return this.utilEffZooTabellaSs[i];
    }

    public void setUtilEffZooTabellaSs(int i, com.hyperborea.sira.ws.UtilEffZooTabellaS _value) {
        this.utilEffZooTabellaSs[i] = _value;
    }


    /**
     * Gets the utilEffZooTabellaTs value for this EffZooDatiAzienda.
     * 
     * @return utilEffZooTabellaTs
     */
    public com.hyperborea.sira.ws.UtilEffZooTabellaT[] getUtilEffZooTabellaTs() {
        return utilEffZooTabellaTs;
    }


    /**
     * Sets the utilEffZooTabellaTs value for this EffZooDatiAzienda.
     * 
     * @param utilEffZooTabellaTs
     */
    public void setUtilEffZooTabellaTs(com.hyperborea.sira.ws.UtilEffZooTabellaT[] utilEffZooTabellaTs) {
        this.utilEffZooTabellaTs = utilEffZooTabellaTs;
    }

    public com.hyperborea.sira.ws.UtilEffZooTabellaT getUtilEffZooTabellaTs(int i) {
        return this.utilEffZooTabellaTs[i];
    }

    public void setUtilEffZooTabellaTs(int i, com.hyperborea.sira.ws.UtilEffZooTabellaT _value) {
        this.utilEffZooTabellaTs[i] = _value;
    }


    /**
     * Gets the utilEffZooTabellaUs value for this EffZooDatiAzienda.
     * 
     * @return utilEffZooTabellaUs
     */
    public com.hyperborea.sira.ws.UtilEffZooTabellaU[] getUtilEffZooTabellaUs() {
        return utilEffZooTabellaUs;
    }


    /**
     * Sets the utilEffZooTabellaUs value for this EffZooDatiAzienda.
     * 
     * @param utilEffZooTabellaUs
     */
    public void setUtilEffZooTabellaUs(com.hyperborea.sira.ws.UtilEffZooTabellaU[] utilEffZooTabellaUs) {
        this.utilEffZooTabellaUs = utilEffZooTabellaUs;
    }

    public com.hyperborea.sira.ws.UtilEffZooTabellaU getUtilEffZooTabellaUs(int i) {
        return this.utilEffZooTabellaUs[i];
    }

    public void setUtilEffZooTabellaUs(int i, com.hyperborea.sira.ws.UtilEffZooTabellaU _value) {
        this.utilEffZooTabellaUs[i] = _value;
    }


    /**
     * Gets the vocProduzioneUtilizzo value for this EffZooDatiAzienda.
     * 
     * @return vocProduzioneUtilizzo
     */
    public com.hyperborea.sira.ws.VocProduzioneUtilizzo getVocProduzioneUtilizzo() {
        return vocProduzioneUtilizzo;
    }


    /**
     * Sets the vocProduzioneUtilizzo value for this EffZooDatiAzienda.
     * 
     * @param vocProduzioneUtilizzo
     */
    public void setVocProduzioneUtilizzo(com.hyperborea.sira.ws.VocProduzioneUtilizzo vocProduzioneUtilizzo) {
        this.vocProduzioneUtilizzo = vocProduzioneUtilizzo;
    }


    /**
     * Gets the vocQuantitativo value for this EffZooDatiAzienda.
     * 
     * @return vocQuantitativo
     */
    public com.hyperborea.sira.ws.VocQuantitativo getVocQuantitativo() {
        return vocQuantitativo;
    }


    /**
     * Sets the vocQuantitativo value for this EffZooDatiAzienda.
     * 
     * @param vocQuantitativo
     */
    public void setVocQuantitativo(com.hyperborea.sira.ws.VocQuantitativo vocQuantitativo) {
        this.vocQuantitativo = vocQuantitativo;
    }


    /**
     * Gets the vocZona value for this EffZooDatiAzienda.
     * 
     * @return vocZona
     */
    public com.hyperborea.sira.ws.VocZona getVocZona() {
        return vocZona;
    }


    /**
     * Sets the vocZona value for this EffZooDatiAzienda.
     * 
     * @param vocZona
     */
    public void setVocZona(com.hyperborea.sira.ws.VocZona vocZona) {
        this.vocZona = vocZona;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EffZooDatiAzienda)) return false;
        EffZooDatiAzienda other = (EffZooDatiAzienda) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codiceAsl==null && other.getCodiceAsl()==null) || 
             (this.codiceAsl!=null &&
              this.codiceAsl.equals(other.getCodiceAsl()))) &&
            ((this.cuaa==null && other.getCuaa()==null) || 
             (this.cuaa!=null &&
              this.cuaa.equals(other.getCuaa()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.ordinamentoColt==null && other.getOrdinamentoColt()==null) || 
             (this.ordinamentoColt!=null &&
              this.ordinamentoColt.equals(other.getOrdinamentoColt()))) &&
            ((this.prodEffZooTabellaAs==null && other.getProdEffZooTabellaAs()==null) || 
             (this.prodEffZooTabellaAs!=null &&
              java.util.Arrays.equals(this.prodEffZooTabellaAs, other.getProdEffZooTabellaAs()))) &&
            ((this.prodEffZooTabellaBs==null && other.getProdEffZooTabellaBs()==null) || 
             (this.prodEffZooTabellaBs!=null &&
              java.util.Arrays.equals(this.prodEffZooTabellaBs, other.getProdEffZooTabellaBs()))) &&
            ((this.prodEffZooTabellaC==null && other.getProdEffZooTabellaC()==null) || 
             (this.prodEffZooTabellaC!=null &&
              this.prodEffZooTabellaC.equals(other.getProdEffZooTabellaC()))) &&
            ((this.prodEffZooTabellaD==null && other.getProdEffZooTabellaD()==null) || 
             (this.prodEffZooTabellaD!=null &&
              this.prodEffZooTabellaD.equals(other.getProdEffZooTabellaD()))) &&
            ((this.prodEffZooTabellaE==null && other.getProdEffZooTabellaE()==null) || 
             (this.prodEffZooTabellaE!=null &&
              this.prodEffZooTabellaE.equals(other.getProdEffZooTabellaE()))) &&
            ((this.prodEffZooTabellaF==null && other.getProdEffZooTabellaF()==null) || 
             (this.prodEffZooTabellaF!=null &&
              this.prodEffZooTabellaF.equals(other.getProdEffZooTabellaF()))) &&
            ((this.prodEffZooTabellaGs==null && other.getProdEffZooTabellaGs()==null) || 
             (this.prodEffZooTabellaGs!=null &&
              java.util.Arrays.equals(this.prodEffZooTabellaGs, other.getProdEffZooTabellaGs()))) &&
            ((this.prodEffZooTabellaHs==null && other.getProdEffZooTabellaHs()==null) || 
             (this.prodEffZooTabellaHs!=null &&
              java.util.Arrays.equals(this.prodEffZooTabellaHs, other.getProdEffZooTabellaHs()))) &&
            ((this.prodEffZooTabellaIs==null && other.getProdEffZooTabellaIs()==null) || 
             (this.prodEffZooTabellaIs!=null &&
              java.util.Arrays.equals(this.prodEffZooTabellaIs, other.getProdEffZooTabellaIs()))) &&
            ((this.prodEffZooTabellaJs==null && other.getProdEffZooTabellaJs()==null) || 
             (this.prodEffZooTabellaJs!=null &&
              java.util.Arrays.equals(this.prodEffZooTabellaJs, other.getProdEffZooTabellaJs()))) &&
            ((this.prodEffZooTabellaKs==null && other.getProdEffZooTabellaKs()==null) || 
             (this.prodEffZooTabellaKs!=null &&
              java.util.Arrays.equals(this.prodEffZooTabellaKs, other.getProdEffZooTabellaKs()))) &&
            ((this.pua==null && other.getPua()==null) || 
             (this.pua!=null &&
              this.pua.equals(other.getPua()))) &&
            ((this.rimozioneDeiezioni==null && other.getRimozioneDeiezioni()==null) || 
             (this.rimozioneDeiezioni!=null &&
              this.rimozioneDeiezioni.equals(other.getRimozioneDeiezioni()))) &&
            ((this.sauZo==null && other.getSauZo()==null) || 
             (this.sauZo!=null &&
              this.sauZo.equals(other.getSauZo()))) &&
            ((this.sauZoEffluenti==null && other.getSauZoEffluenti()==null) || 
             (this.sauZoEffluenti!=null &&
              this.sauZoEffluenti.equals(other.getSauZoEffluenti()))) &&
            ((this.sauZoReflui==null && other.getSauZoReflui()==null) || 
             (this.sauZoReflui!=null &&
              this.sauZoReflui.equals(other.getSauZoReflui()))) &&
            ((this.sauZvReflui==null && other.getSauZvReflui()==null) || 
             (this.sauZvReflui!=null &&
              this.sauZvReflui.equals(other.getSauZvReflui()))) &&
            ((this.supProduttivaZo==null && other.getSupProduttivaZo()==null) || 
             (this.supProduttivaZo!=null &&
              this.supProduttivaZo.equals(other.getSupProduttivaZo()))) &&
            ((this.supProduttivaZv==null && other.getSupProduttivaZv()==null) || 
             (this.supProduttivaZv!=null &&
              this.supProduttivaZv.equals(other.getSupProduttivaZv()))) &&
            ((this.supTotale==null && other.getSupTotale()==null) || 
             (this.supTotale!=null &&
              this.supTotale.equals(other.getSupTotale()))) &&
            ((this.tecnicheDistribuzione==null && other.getTecnicheDistribuzione()==null) || 
             (this.tecnicheDistribuzione!=null &&
              this.tecnicheDistribuzione.equals(other.getTecnicheDistribuzione()))) &&
            ((this.tipoAlimentazione==null && other.getTipoAlimentazione()==null) || 
             (this.tipoAlimentazione!=null &&
              this.tipoAlimentazione.equals(other.getTipoAlimentazione()))) &&
            ((this.utilEffZooTabellaNs==null && other.getUtilEffZooTabellaNs()==null) || 
             (this.utilEffZooTabellaNs!=null &&
              java.util.Arrays.equals(this.utilEffZooTabellaNs, other.getUtilEffZooTabellaNs()))) &&
            ((this.utilEffZooTabellaOs==null && other.getUtilEffZooTabellaOs()==null) || 
             (this.utilEffZooTabellaOs!=null &&
              java.util.Arrays.equals(this.utilEffZooTabellaOs, other.getUtilEffZooTabellaOs()))) &&
            ((this.utilEffZooTabellaPs==null && other.getUtilEffZooTabellaPs()==null) || 
             (this.utilEffZooTabellaPs!=null &&
              java.util.Arrays.equals(this.utilEffZooTabellaPs, other.getUtilEffZooTabellaPs()))) &&
            ((this.utilEffZooTabellaRs==null && other.getUtilEffZooTabellaRs()==null) || 
             (this.utilEffZooTabellaRs!=null &&
              java.util.Arrays.equals(this.utilEffZooTabellaRs, other.getUtilEffZooTabellaRs()))) &&
            ((this.utilEffZooTabellaSs==null && other.getUtilEffZooTabellaSs()==null) || 
             (this.utilEffZooTabellaSs!=null &&
              java.util.Arrays.equals(this.utilEffZooTabellaSs, other.getUtilEffZooTabellaSs()))) &&
            ((this.utilEffZooTabellaTs==null && other.getUtilEffZooTabellaTs()==null) || 
             (this.utilEffZooTabellaTs!=null &&
              java.util.Arrays.equals(this.utilEffZooTabellaTs, other.getUtilEffZooTabellaTs()))) &&
            ((this.utilEffZooTabellaUs==null && other.getUtilEffZooTabellaUs()==null) || 
             (this.utilEffZooTabellaUs!=null &&
              java.util.Arrays.equals(this.utilEffZooTabellaUs, other.getUtilEffZooTabellaUs()))) &&
            ((this.vocProduzioneUtilizzo==null && other.getVocProduzioneUtilizzo()==null) || 
             (this.vocProduzioneUtilizzo!=null &&
              this.vocProduzioneUtilizzo.equals(other.getVocProduzioneUtilizzo()))) &&
            ((this.vocQuantitativo==null && other.getVocQuantitativo()==null) || 
             (this.vocQuantitativo!=null &&
              this.vocQuantitativo.equals(other.getVocQuantitativo()))) &&
            ((this.vocZona==null && other.getVocZona()==null) || 
             (this.vocZona!=null &&
              this.vocZona.equals(other.getVocZona())));
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
        if (getCodiceAsl() != null) {
            _hashCode += getCodiceAsl().hashCode();
        }
        if (getCuaa() != null) {
            _hashCode += getCuaa().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getOrdinamentoColt() != null) {
            _hashCode += getOrdinamentoColt().hashCode();
        }
        if (getProdEffZooTabellaAs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProdEffZooTabellaAs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProdEffZooTabellaAs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProdEffZooTabellaBs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProdEffZooTabellaBs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProdEffZooTabellaBs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProdEffZooTabellaC() != null) {
            _hashCode += getProdEffZooTabellaC().hashCode();
        }
        if (getProdEffZooTabellaD() != null) {
            _hashCode += getProdEffZooTabellaD().hashCode();
        }
        if (getProdEffZooTabellaE() != null) {
            _hashCode += getProdEffZooTabellaE().hashCode();
        }
        if (getProdEffZooTabellaF() != null) {
            _hashCode += getProdEffZooTabellaF().hashCode();
        }
        if (getProdEffZooTabellaGs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProdEffZooTabellaGs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProdEffZooTabellaGs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProdEffZooTabellaHs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProdEffZooTabellaHs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProdEffZooTabellaHs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProdEffZooTabellaIs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProdEffZooTabellaIs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProdEffZooTabellaIs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProdEffZooTabellaJs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProdEffZooTabellaJs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProdEffZooTabellaJs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProdEffZooTabellaKs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProdEffZooTabellaKs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProdEffZooTabellaKs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPua() != null) {
            _hashCode += getPua().hashCode();
        }
        if (getRimozioneDeiezioni() != null) {
            _hashCode += getRimozioneDeiezioni().hashCode();
        }
        if (getSauZo() != null) {
            _hashCode += getSauZo().hashCode();
        }
        if (getSauZoEffluenti() != null) {
            _hashCode += getSauZoEffluenti().hashCode();
        }
        if (getSauZoReflui() != null) {
            _hashCode += getSauZoReflui().hashCode();
        }
        if (getSauZvReflui() != null) {
            _hashCode += getSauZvReflui().hashCode();
        }
        if (getSupProduttivaZo() != null) {
            _hashCode += getSupProduttivaZo().hashCode();
        }
        if (getSupProduttivaZv() != null) {
            _hashCode += getSupProduttivaZv().hashCode();
        }
        if (getSupTotale() != null) {
            _hashCode += getSupTotale().hashCode();
        }
        if (getTecnicheDistribuzione() != null) {
            _hashCode += getTecnicheDistribuzione().hashCode();
        }
        if (getTipoAlimentazione() != null) {
            _hashCode += getTipoAlimentazione().hashCode();
        }
        if (getUtilEffZooTabellaNs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUtilEffZooTabellaNs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUtilEffZooTabellaNs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUtilEffZooTabellaOs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUtilEffZooTabellaOs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUtilEffZooTabellaOs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUtilEffZooTabellaPs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUtilEffZooTabellaPs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUtilEffZooTabellaPs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUtilEffZooTabellaRs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUtilEffZooTabellaRs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUtilEffZooTabellaRs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUtilEffZooTabellaSs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUtilEffZooTabellaSs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUtilEffZooTabellaSs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUtilEffZooTabellaTs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUtilEffZooTabellaTs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUtilEffZooTabellaTs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUtilEffZooTabellaUs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUtilEffZooTabellaUs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUtilEffZooTabellaUs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVocProduzioneUtilizzo() != null) {
            _hashCode += getVocProduzioneUtilizzo().hashCode();
        }
        if (getVocQuantitativo() != null) {
            _hashCode += getVocQuantitativo().hashCode();
        }
        if (getVocZona() != null) {
            _hashCode += getVocZona().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EffZooDatiAzienda.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "effZooDatiAzienda"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceAsl");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceAsl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cuaa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cuaa"));
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
        elemField.setFieldName("ordinamentoColt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ordinamentoColt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodEffZooTabellaAs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooTabellaAs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaA"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodEffZooTabellaBs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooTabellaBs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaB"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodEffZooTabellaC");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooTabellaC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaC"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodEffZooTabellaD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooTabellaD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaD"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodEffZooTabellaE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooTabellaE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaE"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodEffZooTabellaF");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooTabellaF"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaF"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodEffZooTabellaGs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooTabellaGs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaG"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodEffZooTabellaHs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooTabellaHs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaH"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodEffZooTabellaIs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooTabellaIs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaI"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodEffZooTabellaJs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooTabellaJs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaJ"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodEffZooTabellaKs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooTabellaKs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaK"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pua");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pua"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rimozioneDeiezioni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rimozioneDeiezioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sauZo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sauZo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sauZoEffluenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sauZoEffluenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sauZoReflui");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sauZoReflui"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sauZvReflui");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sauZvReflui"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supProduttivaZo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "supProduttivaZo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supProduttivaZv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "supProduttivaZv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supTotale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "supTotale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tecnicheDistribuzione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tecnicheDistribuzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoAlimentazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoAlimentazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utilEffZooTabellaNs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilEffZooTabellaNs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaN"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utilEffZooTabellaOs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilEffZooTabellaOs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utilEffZooTabellaPs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilEffZooTabellaPs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaP"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utilEffZooTabellaRs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilEffZooTabellaRs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaR"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utilEffZooTabellaSs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilEffZooTabellaSs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaS"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utilEffZooTabellaTs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilEffZooTabellaTs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaT"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utilEffZooTabellaUs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilEffZooTabellaUs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaU"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocProduzioneUtilizzo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocProduzioneUtilizzo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocProduzioneUtilizzo"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocQuantitativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocQuantitativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocQuantitativo"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocZona");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocZona"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocZona"));
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
