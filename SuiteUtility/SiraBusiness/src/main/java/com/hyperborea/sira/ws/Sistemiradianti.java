/**
 * Sistemiradianti.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Sistemiradianti  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Float altezzacentroelM;

    private com.hyperborea.sira.ws.Canali[] canalis;

    private java.lang.Float durataImpulsi;

    private java.lang.Float estremoInf;

    private java.lang.Float estremoSup;

    private java.lang.Float guadagnoDbi;

    private java.lang.Integer idSysrad;

    private com.hyperborea.sira.ws.ImpiantiRcTiposervizio impiantiRcTiposervizio;

    private java.lang.String modoOriz;

    private java.lang.String modoVert;

    private java.lang.Integer numSistema;

    private java.lang.Float offsetX;

    private java.lang.Float offsetY;

    private com.hyperborea.sira.ws.Pannelli[] pannellis;

    private java.lang.Float periodoBrandeggio;

    private java.lang.Float periodoImpulsi;

    private java.lang.String polarizzazione;

    private java.lang.Integer portanti;

    private java.lang.Float potenzaeirpW;

    private java.lang.Float puntamentoDeg;

    private com.hyperborea.sira.ws.SisradMarchi[] sisradMarchi;

    private java.lang.Integer statoAttivazione;

    private java.lang.Float tiltComplessivo;

    private java.lang.Float tiltMeccDeg;

    private java.lang.Float tiltelettricoDeg;

    private com.hyperborea.sira.ws.TipiAntenne tipiAntenne;

    private com.hyperborea.sira.ws.TipiTrasmettitori tipiTrasmettitori;

    private java.lang.String tipoModulazione;

    private java.lang.Float velocitaAngolare;

    public Sistemiradianti() {
    }

    public Sistemiradianti(
           java.lang.Float altezzacentroelM,
           com.hyperborea.sira.ws.Canali[] canalis,
           java.lang.Float durataImpulsi,
           java.lang.Float estremoInf,
           java.lang.Float estremoSup,
           java.lang.Float guadagnoDbi,
           java.lang.Integer idSysrad,
           com.hyperborea.sira.ws.ImpiantiRcTiposervizio impiantiRcTiposervizio,
           java.lang.String modoOriz,
           java.lang.String modoVert,
           java.lang.Integer numSistema,
           java.lang.Float offsetX,
           java.lang.Float offsetY,
           com.hyperborea.sira.ws.Pannelli[] pannellis,
           java.lang.Float periodoBrandeggio,
           java.lang.Float periodoImpulsi,
           java.lang.String polarizzazione,
           java.lang.Integer portanti,
           java.lang.Float potenzaeirpW,
           java.lang.Float puntamentoDeg,
           com.hyperborea.sira.ws.SisradMarchi[] sisradMarchi,
           java.lang.Integer statoAttivazione,
           java.lang.Float tiltComplessivo,
           java.lang.Float tiltMeccDeg,
           java.lang.Float tiltelettricoDeg,
           com.hyperborea.sira.ws.TipiAntenne tipiAntenne,
           com.hyperborea.sira.ws.TipiTrasmettitori tipiTrasmettitori,
           java.lang.String tipoModulazione,
           java.lang.Float velocitaAngolare) {
        this.altezzacentroelM = altezzacentroelM;
        this.canalis = canalis;
        this.durataImpulsi = durataImpulsi;
        this.estremoInf = estremoInf;
        this.estremoSup = estremoSup;
        this.guadagnoDbi = guadagnoDbi;
        this.idSysrad = idSysrad;
        this.impiantiRcTiposervizio = impiantiRcTiposervizio;
        this.modoOriz = modoOriz;
        this.modoVert = modoVert;
        this.numSistema = numSistema;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.pannellis = pannellis;
        this.periodoBrandeggio = periodoBrandeggio;
        this.periodoImpulsi = periodoImpulsi;
        this.polarizzazione = polarizzazione;
        this.portanti = portanti;
        this.potenzaeirpW = potenzaeirpW;
        this.puntamentoDeg = puntamentoDeg;
        this.sisradMarchi = sisradMarchi;
        this.statoAttivazione = statoAttivazione;
        this.tiltComplessivo = tiltComplessivo;
        this.tiltMeccDeg = tiltMeccDeg;
        this.tiltelettricoDeg = tiltelettricoDeg;
        this.tipiAntenne = tipiAntenne;
        this.tipiTrasmettitori = tipiTrasmettitori;
        this.tipoModulazione = tipoModulazione;
        this.velocitaAngolare = velocitaAngolare;
    }


    /**
     * Gets the altezzacentroelM value for this Sistemiradianti.
     * 
     * @return altezzacentroelM
     */
    public java.lang.Float getAltezzacentroelM() {
        return altezzacentroelM;
    }


    /**
     * Sets the altezzacentroelM value for this Sistemiradianti.
     * 
     * @param altezzacentroelM
     */
    public void setAltezzacentroelM(java.lang.Float altezzacentroelM) {
        this.altezzacentroelM = altezzacentroelM;
    }


    /**
     * Gets the canalis value for this Sistemiradianti.
     * 
     * @return canalis
     */
    public com.hyperborea.sira.ws.Canali[] getCanalis() {
        return canalis;
    }


    /**
     * Sets the canalis value for this Sistemiradianti.
     * 
     * @param canalis
     */
    public void setCanalis(com.hyperborea.sira.ws.Canali[] canalis) {
        this.canalis = canalis;
    }

    public com.hyperborea.sira.ws.Canali getCanalis(int i) {
        return this.canalis[i];
    }

    public void setCanalis(int i, com.hyperborea.sira.ws.Canali _value) {
        this.canalis[i] = _value;
    }


    /**
     * Gets the durataImpulsi value for this Sistemiradianti.
     * 
     * @return durataImpulsi
     */
    public java.lang.Float getDurataImpulsi() {
        return durataImpulsi;
    }


    /**
     * Sets the durataImpulsi value for this Sistemiradianti.
     * 
     * @param durataImpulsi
     */
    public void setDurataImpulsi(java.lang.Float durataImpulsi) {
        this.durataImpulsi = durataImpulsi;
    }


    /**
     * Gets the estremoInf value for this Sistemiradianti.
     * 
     * @return estremoInf
     */
    public java.lang.Float getEstremoInf() {
        return estremoInf;
    }


    /**
     * Sets the estremoInf value for this Sistemiradianti.
     * 
     * @param estremoInf
     */
    public void setEstremoInf(java.lang.Float estremoInf) {
        this.estremoInf = estremoInf;
    }


    /**
     * Gets the estremoSup value for this Sistemiradianti.
     * 
     * @return estremoSup
     */
    public java.lang.Float getEstremoSup() {
        return estremoSup;
    }


    /**
     * Sets the estremoSup value for this Sistemiradianti.
     * 
     * @param estremoSup
     */
    public void setEstremoSup(java.lang.Float estremoSup) {
        this.estremoSup = estremoSup;
    }


    /**
     * Gets the guadagnoDbi value for this Sistemiradianti.
     * 
     * @return guadagnoDbi
     */
    public java.lang.Float getGuadagnoDbi() {
        return guadagnoDbi;
    }


    /**
     * Sets the guadagnoDbi value for this Sistemiradianti.
     * 
     * @param guadagnoDbi
     */
    public void setGuadagnoDbi(java.lang.Float guadagnoDbi) {
        this.guadagnoDbi = guadagnoDbi;
    }


    /**
     * Gets the idSysrad value for this Sistemiradianti.
     * 
     * @return idSysrad
     */
    public java.lang.Integer getIdSysrad() {
        return idSysrad;
    }


    /**
     * Sets the idSysrad value for this Sistemiradianti.
     * 
     * @param idSysrad
     */
    public void setIdSysrad(java.lang.Integer idSysrad) {
        this.idSysrad = idSysrad;
    }


    /**
     * Gets the impiantiRcTiposervizio value for this Sistemiradianti.
     * 
     * @return impiantiRcTiposervizio
     */
    public com.hyperborea.sira.ws.ImpiantiRcTiposervizio getImpiantiRcTiposervizio() {
        return impiantiRcTiposervizio;
    }


    /**
     * Sets the impiantiRcTiposervizio value for this Sistemiradianti.
     * 
     * @param impiantiRcTiposervizio
     */
    public void setImpiantiRcTiposervizio(com.hyperborea.sira.ws.ImpiantiRcTiposervizio impiantiRcTiposervizio) {
        this.impiantiRcTiposervizio = impiantiRcTiposervizio;
    }


    /**
     * Gets the modoOriz value for this Sistemiradianti.
     * 
     * @return modoOriz
     */
    public java.lang.String getModoOriz() {
        return modoOriz;
    }


    /**
     * Sets the modoOriz value for this Sistemiradianti.
     * 
     * @param modoOriz
     */
    public void setModoOriz(java.lang.String modoOriz) {
        this.modoOriz = modoOriz;
    }


    /**
     * Gets the modoVert value for this Sistemiradianti.
     * 
     * @return modoVert
     */
    public java.lang.String getModoVert() {
        return modoVert;
    }


    /**
     * Sets the modoVert value for this Sistemiradianti.
     * 
     * @param modoVert
     */
    public void setModoVert(java.lang.String modoVert) {
        this.modoVert = modoVert;
    }


    /**
     * Gets the numSistema value for this Sistemiradianti.
     * 
     * @return numSistema
     */
    public java.lang.Integer getNumSistema() {
        return numSistema;
    }


    /**
     * Sets the numSistema value for this Sistemiradianti.
     * 
     * @param numSistema
     */
    public void setNumSistema(java.lang.Integer numSistema) {
        this.numSistema = numSistema;
    }


    /**
     * Gets the offsetX value for this Sistemiradianti.
     * 
     * @return offsetX
     */
    public java.lang.Float getOffsetX() {
        return offsetX;
    }


    /**
     * Sets the offsetX value for this Sistemiradianti.
     * 
     * @param offsetX
     */
    public void setOffsetX(java.lang.Float offsetX) {
        this.offsetX = offsetX;
    }


    /**
     * Gets the offsetY value for this Sistemiradianti.
     * 
     * @return offsetY
     */
    public java.lang.Float getOffsetY() {
        return offsetY;
    }


    /**
     * Sets the offsetY value for this Sistemiradianti.
     * 
     * @param offsetY
     */
    public void setOffsetY(java.lang.Float offsetY) {
        this.offsetY = offsetY;
    }


    /**
     * Gets the pannellis value for this Sistemiradianti.
     * 
     * @return pannellis
     */
    public com.hyperborea.sira.ws.Pannelli[] getPannellis() {
        return pannellis;
    }


    /**
     * Sets the pannellis value for this Sistemiradianti.
     * 
     * @param pannellis
     */
    public void setPannellis(com.hyperborea.sira.ws.Pannelli[] pannellis) {
        this.pannellis = pannellis;
    }

    public com.hyperborea.sira.ws.Pannelli getPannellis(int i) {
        return this.pannellis[i];
    }

    public void setPannellis(int i, com.hyperborea.sira.ws.Pannelli _value) {
        this.pannellis[i] = _value;
    }


    /**
     * Gets the periodoBrandeggio value for this Sistemiradianti.
     * 
     * @return periodoBrandeggio
     */
    public java.lang.Float getPeriodoBrandeggio() {
        return periodoBrandeggio;
    }


    /**
     * Sets the periodoBrandeggio value for this Sistemiradianti.
     * 
     * @param periodoBrandeggio
     */
    public void setPeriodoBrandeggio(java.lang.Float periodoBrandeggio) {
        this.periodoBrandeggio = periodoBrandeggio;
    }


    /**
     * Gets the periodoImpulsi value for this Sistemiradianti.
     * 
     * @return periodoImpulsi
     */
    public java.lang.Float getPeriodoImpulsi() {
        return periodoImpulsi;
    }


    /**
     * Sets the periodoImpulsi value for this Sistemiradianti.
     * 
     * @param periodoImpulsi
     */
    public void setPeriodoImpulsi(java.lang.Float periodoImpulsi) {
        this.periodoImpulsi = periodoImpulsi;
    }


    /**
     * Gets the polarizzazione value for this Sistemiradianti.
     * 
     * @return polarizzazione
     */
    public java.lang.String getPolarizzazione() {
        return polarizzazione;
    }


    /**
     * Sets the polarizzazione value for this Sistemiradianti.
     * 
     * @param polarizzazione
     */
    public void setPolarizzazione(java.lang.String polarizzazione) {
        this.polarizzazione = polarizzazione;
    }


    /**
     * Gets the portanti value for this Sistemiradianti.
     * 
     * @return portanti
     */
    public java.lang.Integer getPortanti() {
        return portanti;
    }


    /**
     * Sets the portanti value for this Sistemiradianti.
     * 
     * @param portanti
     */
    public void setPortanti(java.lang.Integer portanti) {
        this.portanti = portanti;
    }


    /**
     * Gets the potenzaeirpW value for this Sistemiradianti.
     * 
     * @return potenzaeirpW
     */
    public java.lang.Float getPotenzaeirpW() {
        return potenzaeirpW;
    }


    /**
     * Sets the potenzaeirpW value for this Sistemiradianti.
     * 
     * @param potenzaeirpW
     */
    public void setPotenzaeirpW(java.lang.Float potenzaeirpW) {
        this.potenzaeirpW = potenzaeirpW;
    }


    /**
     * Gets the puntamentoDeg value for this Sistemiradianti.
     * 
     * @return puntamentoDeg
     */
    public java.lang.Float getPuntamentoDeg() {
        return puntamentoDeg;
    }


    /**
     * Sets the puntamentoDeg value for this Sistemiradianti.
     * 
     * @param puntamentoDeg
     */
    public void setPuntamentoDeg(java.lang.Float puntamentoDeg) {
        this.puntamentoDeg = puntamentoDeg;
    }


    /**
     * Gets the sisradMarchi value for this Sistemiradianti.
     * 
     * @return sisradMarchi
     */
    public com.hyperborea.sira.ws.SisradMarchi[] getSisradMarchi() {
        return sisradMarchi;
    }


    /**
     * Sets the sisradMarchi value for this Sistemiradianti.
     * 
     * @param sisradMarchi
     */
    public void setSisradMarchi(com.hyperborea.sira.ws.SisradMarchi[] sisradMarchi) {
        this.sisradMarchi = sisradMarchi;
    }

    public com.hyperborea.sira.ws.SisradMarchi getSisradMarchi(int i) {
        return this.sisradMarchi[i];
    }

    public void setSisradMarchi(int i, com.hyperborea.sira.ws.SisradMarchi _value) {
        this.sisradMarchi[i] = _value;
    }


    /**
     * Gets the statoAttivazione value for this Sistemiradianti.
     * 
     * @return statoAttivazione
     */
    public java.lang.Integer getStatoAttivazione() {
        return statoAttivazione;
    }


    /**
     * Sets the statoAttivazione value for this Sistemiradianti.
     * 
     * @param statoAttivazione
     */
    public void setStatoAttivazione(java.lang.Integer statoAttivazione) {
        this.statoAttivazione = statoAttivazione;
    }


    /**
     * Gets the tiltComplessivo value for this Sistemiradianti.
     * 
     * @return tiltComplessivo
     */
    public java.lang.Float getTiltComplessivo() {
        return tiltComplessivo;
    }


    /**
     * Sets the tiltComplessivo value for this Sistemiradianti.
     * 
     * @param tiltComplessivo
     */
    public void setTiltComplessivo(java.lang.Float tiltComplessivo) {
        this.tiltComplessivo = tiltComplessivo;
    }


    /**
     * Gets the tiltMeccDeg value for this Sistemiradianti.
     * 
     * @return tiltMeccDeg
     */
    public java.lang.Float getTiltMeccDeg() {
        return tiltMeccDeg;
    }


    /**
     * Sets the tiltMeccDeg value for this Sistemiradianti.
     * 
     * @param tiltMeccDeg
     */
    public void setTiltMeccDeg(java.lang.Float tiltMeccDeg) {
        this.tiltMeccDeg = tiltMeccDeg;
    }


    /**
     * Gets the tiltelettricoDeg value for this Sistemiradianti.
     * 
     * @return tiltelettricoDeg
     */
    public java.lang.Float getTiltelettricoDeg() {
        return tiltelettricoDeg;
    }


    /**
     * Sets the tiltelettricoDeg value for this Sistemiradianti.
     * 
     * @param tiltelettricoDeg
     */
    public void setTiltelettricoDeg(java.lang.Float tiltelettricoDeg) {
        this.tiltelettricoDeg = tiltelettricoDeg;
    }


    /**
     * Gets the tipiAntenne value for this Sistemiradianti.
     * 
     * @return tipiAntenne
     */
    public com.hyperborea.sira.ws.TipiAntenne getTipiAntenne() {
        return tipiAntenne;
    }


    /**
     * Sets the tipiAntenne value for this Sistemiradianti.
     * 
     * @param tipiAntenne
     */
    public void setTipiAntenne(com.hyperborea.sira.ws.TipiAntenne tipiAntenne) {
        this.tipiAntenne = tipiAntenne;
    }


    /**
     * Gets the tipiTrasmettitori value for this Sistemiradianti.
     * 
     * @return tipiTrasmettitori
     */
    public com.hyperborea.sira.ws.TipiTrasmettitori getTipiTrasmettitori() {
        return tipiTrasmettitori;
    }


    /**
     * Sets the tipiTrasmettitori value for this Sistemiradianti.
     * 
     * @param tipiTrasmettitori
     */
    public void setTipiTrasmettitori(com.hyperborea.sira.ws.TipiTrasmettitori tipiTrasmettitori) {
        this.tipiTrasmettitori = tipiTrasmettitori;
    }


    /**
     * Gets the tipoModulazione value for this Sistemiradianti.
     * 
     * @return tipoModulazione
     */
    public java.lang.String getTipoModulazione() {
        return tipoModulazione;
    }


    /**
     * Sets the tipoModulazione value for this Sistemiradianti.
     * 
     * @param tipoModulazione
     */
    public void setTipoModulazione(java.lang.String tipoModulazione) {
        this.tipoModulazione = tipoModulazione;
    }


    /**
     * Gets the velocitaAngolare value for this Sistemiradianti.
     * 
     * @return velocitaAngolare
     */
    public java.lang.Float getVelocitaAngolare() {
        return velocitaAngolare;
    }


    /**
     * Sets the velocitaAngolare value for this Sistemiradianti.
     * 
     * @param velocitaAngolare
     */
    public void setVelocitaAngolare(java.lang.Float velocitaAngolare) {
        this.velocitaAngolare = velocitaAngolare;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Sistemiradianti)) return false;
        Sistemiradianti other = (Sistemiradianti) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.altezzacentroelM==null && other.getAltezzacentroelM()==null) || 
             (this.altezzacentroelM!=null &&
              this.altezzacentroelM.equals(other.getAltezzacentroelM()))) &&
            ((this.canalis==null && other.getCanalis()==null) || 
             (this.canalis!=null &&
              java.util.Arrays.equals(this.canalis, other.getCanalis()))) &&
            ((this.durataImpulsi==null && other.getDurataImpulsi()==null) || 
             (this.durataImpulsi!=null &&
              this.durataImpulsi.equals(other.getDurataImpulsi()))) &&
            ((this.estremoInf==null && other.getEstremoInf()==null) || 
             (this.estremoInf!=null &&
              this.estremoInf.equals(other.getEstremoInf()))) &&
            ((this.estremoSup==null && other.getEstremoSup()==null) || 
             (this.estremoSup!=null &&
              this.estremoSup.equals(other.getEstremoSup()))) &&
            ((this.guadagnoDbi==null && other.getGuadagnoDbi()==null) || 
             (this.guadagnoDbi!=null &&
              this.guadagnoDbi.equals(other.getGuadagnoDbi()))) &&
            ((this.idSysrad==null && other.getIdSysrad()==null) || 
             (this.idSysrad!=null &&
              this.idSysrad.equals(other.getIdSysrad()))) &&
            ((this.impiantiRcTiposervizio==null && other.getImpiantiRcTiposervizio()==null) || 
             (this.impiantiRcTiposervizio!=null &&
              this.impiantiRcTiposervizio.equals(other.getImpiantiRcTiposervizio()))) &&
            ((this.modoOriz==null && other.getModoOriz()==null) || 
             (this.modoOriz!=null &&
              this.modoOriz.equals(other.getModoOriz()))) &&
            ((this.modoVert==null && other.getModoVert()==null) || 
             (this.modoVert!=null &&
              this.modoVert.equals(other.getModoVert()))) &&
            ((this.numSistema==null && other.getNumSistema()==null) || 
             (this.numSistema!=null &&
              this.numSistema.equals(other.getNumSistema()))) &&
            ((this.offsetX==null && other.getOffsetX()==null) || 
             (this.offsetX!=null &&
              this.offsetX.equals(other.getOffsetX()))) &&
            ((this.offsetY==null && other.getOffsetY()==null) || 
             (this.offsetY!=null &&
              this.offsetY.equals(other.getOffsetY()))) &&
            ((this.pannellis==null && other.getPannellis()==null) || 
             (this.pannellis!=null &&
              java.util.Arrays.equals(this.pannellis, other.getPannellis()))) &&
            ((this.periodoBrandeggio==null && other.getPeriodoBrandeggio()==null) || 
             (this.periodoBrandeggio!=null &&
              this.periodoBrandeggio.equals(other.getPeriodoBrandeggio()))) &&
            ((this.periodoImpulsi==null && other.getPeriodoImpulsi()==null) || 
             (this.periodoImpulsi!=null &&
              this.periodoImpulsi.equals(other.getPeriodoImpulsi()))) &&
            ((this.polarizzazione==null && other.getPolarizzazione()==null) || 
             (this.polarizzazione!=null &&
              this.polarizzazione.equals(other.getPolarizzazione()))) &&
            ((this.portanti==null && other.getPortanti()==null) || 
             (this.portanti!=null &&
              this.portanti.equals(other.getPortanti()))) &&
            ((this.potenzaeirpW==null && other.getPotenzaeirpW()==null) || 
             (this.potenzaeirpW!=null &&
              this.potenzaeirpW.equals(other.getPotenzaeirpW()))) &&
            ((this.puntamentoDeg==null && other.getPuntamentoDeg()==null) || 
             (this.puntamentoDeg!=null &&
              this.puntamentoDeg.equals(other.getPuntamentoDeg()))) &&
            ((this.sisradMarchi==null && other.getSisradMarchi()==null) || 
             (this.sisradMarchi!=null &&
              java.util.Arrays.equals(this.sisradMarchi, other.getSisradMarchi()))) &&
            ((this.statoAttivazione==null && other.getStatoAttivazione()==null) || 
             (this.statoAttivazione!=null &&
              this.statoAttivazione.equals(other.getStatoAttivazione()))) &&
            ((this.tiltComplessivo==null && other.getTiltComplessivo()==null) || 
             (this.tiltComplessivo!=null &&
              this.tiltComplessivo.equals(other.getTiltComplessivo()))) &&
            ((this.tiltMeccDeg==null && other.getTiltMeccDeg()==null) || 
             (this.tiltMeccDeg!=null &&
              this.tiltMeccDeg.equals(other.getTiltMeccDeg()))) &&
            ((this.tiltelettricoDeg==null && other.getTiltelettricoDeg()==null) || 
             (this.tiltelettricoDeg!=null &&
              this.tiltelettricoDeg.equals(other.getTiltelettricoDeg()))) &&
            ((this.tipiAntenne==null && other.getTipiAntenne()==null) || 
             (this.tipiAntenne!=null &&
              this.tipiAntenne.equals(other.getTipiAntenne()))) &&
            ((this.tipiTrasmettitori==null && other.getTipiTrasmettitori()==null) || 
             (this.tipiTrasmettitori!=null &&
              this.tipiTrasmettitori.equals(other.getTipiTrasmettitori()))) &&
            ((this.tipoModulazione==null && other.getTipoModulazione()==null) || 
             (this.tipoModulazione!=null &&
              this.tipoModulazione.equals(other.getTipoModulazione()))) &&
            ((this.velocitaAngolare==null && other.getVelocitaAngolare()==null) || 
             (this.velocitaAngolare!=null &&
              this.velocitaAngolare.equals(other.getVelocitaAngolare())));
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
        if (getAltezzacentroelM() != null) {
            _hashCode += getAltezzacentroelM().hashCode();
        }
        if (getCanalis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCanalis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCanalis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDurataImpulsi() != null) {
            _hashCode += getDurataImpulsi().hashCode();
        }
        if (getEstremoInf() != null) {
            _hashCode += getEstremoInf().hashCode();
        }
        if (getEstremoSup() != null) {
            _hashCode += getEstremoSup().hashCode();
        }
        if (getGuadagnoDbi() != null) {
            _hashCode += getGuadagnoDbi().hashCode();
        }
        if (getIdSysrad() != null) {
            _hashCode += getIdSysrad().hashCode();
        }
        if (getImpiantiRcTiposervizio() != null) {
            _hashCode += getImpiantiRcTiposervizio().hashCode();
        }
        if (getModoOriz() != null) {
            _hashCode += getModoOriz().hashCode();
        }
        if (getModoVert() != null) {
            _hashCode += getModoVert().hashCode();
        }
        if (getNumSistema() != null) {
            _hashCode += getNumSistema().hashCode();
        }
        if (getOffsetX() != null) {
            _hashCode += getOffsetX().hashCode();
        }
        if (getOffsetY() != null) {
            _hashCode += getOffsetY().hashCode();
        }
        if (getPannellis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPannellis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPannellis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPeriodoBrandeggio() != null) {
            _hashCode += getPeriodoBrandeggio().hashCode();
        }
        if (getPeriodoImpulsi() != null) {
            _hashCode += getPeriodoImpulsi().hashCode();
        }
        if (getPolarizzazione() != null) {
            _hashCode += getPolarizzazione().hashCode();
        }
        if (getPortanti() != null) {
            _hashCode += getPortanti().hashCode();
        }
        if (getPotenzaeirpW() != null) {
            _hashCode += getPotenzaeirpW().hashCode();
        }
        if (getPuntamentoDeg() != null) {
            _hashCode += getPuntamentoDeg().hashCode();
        }
        if (getSisradMarchi() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSisradMarchi());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSisradMarchi(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStatoAttivazione() != null) {
            _hashCode += getStatoAttivazione().hashCode();
        }
        if (getTiltComplessivo() != null) {
            _hashCode += getTiltComplessivo().hashCode();
        }
        if (getTiltMeccDeg() != null) {
            _hashCode += getTiltMeccDeg().hashCode();
        }
        if (getTiltelettricoDeg() != null) {
            _hashCode += getTiltelettricoDeg().hashCode();
        }
        if (getTipiAntenne() != null) {
            _hashCode += getTipiAntenne().hashCode();
        }
        if (getTipiTrasmettitori() != null) {
            _hashCode += getTipiTrasmettitori().hashCode();
        }
        if (getTipoModulazione() != null) {
            _hashCode += getTipoModulazione().hashCode();
        }
        if (getVelocitaAngolare() != null) {
            _hashCode += getVelocitaAngolare().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Sistemiradianti.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sistemiradianti"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altezzacentroelM");
        elemField.setXmlName(new javax.xml.namespace.QName("", "altezzacentroelM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("canalis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "canalis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "canali"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("durataImpulsi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "durataImpulsi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estremoInf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estremoInf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estremoSup");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estremoSup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("guadagnoDbi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "guadagnoDbi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSysrad");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idSysrad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("impiantiRcTiposervizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "impiantiRcTiposervizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "impiantiRcTiposervizio"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modoOriz");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modoOriz"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modoVert");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modoVert"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numSistema");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numSistema"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offsetX");
        elemField.setXmlName(new javax.xml.namespace.QName("", "offsetX"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offsetY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "offsetY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pannellis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pannellis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pannelli"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodoBrandeggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "periodoBrandeggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodoImpulsi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "periodoImpulsi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("polarizzazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "polarizzazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portanti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portanti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzaeirpW");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzaeirpW"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("puntamentoDeg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "puntamentoDeg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sisradMarchi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sisradMarchi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sisradMarchi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoAttivazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statoAttivazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tiltComplessivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tiltComplessivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tiltMeccDeg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tiltMeccDeg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tiltelettricoDeg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tiltelettricoDeg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipiAntenne");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipiAntenne"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiAntenne"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipiTrasmettitori");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipiTrasmettitori"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiTrasmettitori"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoModulazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoModulazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("velocitaAngolare");
        elemField.setXmlName(new javax.xml.namespace.QName("", "velocitaAngolare"));
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
