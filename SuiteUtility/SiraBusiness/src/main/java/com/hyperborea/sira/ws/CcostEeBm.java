/**
 * CcostEeBm.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostEeBm  implements java.io.Serializable {
    private java.lang.String autoriz;

    private java.lang.Float capacita;

    private java.util.Calendar dataEntrataEsercizio;

    private java.util.Calendar dataFine;

    private java.lang.String destdigest;

    private java.lang.Integer flagaltrispagri;

    private java.lang.Integer flagaltrspindustria;

    private java.lang.Integer flagbiolegno;

    private java.lang.Integer flagbioliquida;

    private java.lang.Integer flagcereali;

    private java.lang.Integer flagfanghi;

    private java.lang.Integer flagforsu;

    private java.lang.Integer flagrifliquid;

    private java.lang.Integer flagrifsolidiindustr;

    private java.lang.Integer flagscartzoo;

    private java.lang.Integer flagsottoprodliq;

    private java.lang.Integer idCcost;

    private java.lang.String note;

    private java.lang.Float provcom;

    private java.lang.Float provcorta;

    private java.lang.Float provexcom;

    private java.lang.Float provnaz;

    private java.lang.Float supoccupata;

    private java.lang.String titoloAutorizzativo;

    private com.hyperborea.sira.ws.VocAlimentComb vocAliment;

    private com.hyperborea.sira.ws.VocBmaliment vocBmaliment;

    private com.hyperborea.sira.ws.VocBmimpazoto vocBmimpazoto;

    private com.hyperborea.sira.ws.VocBmtipoprod vocBmtipoprod;

    private com.hyperborea.sira.ws.VocBmumis vocBmumis;

    public CcostEeBm() {
    }

    public CcostEeBm(
           java.lang.String autoriz,
           java.lang.Float capacita,
           java.util.Calendar dataEntrataEsercizio,
           java.util.Calendar dataFine,
           java.lang.String destdigest,
           java.lang.Integer flagaltrispagri,
           java.lang.Integer flagaltrspindustria,
           java.lang.Integer flagbiolegno,
           java.lang.Integer flagbioliquida,
           java.lang.Integer flagcereali,
           java.lang.Integer flagfanghi,
           java.lang.Integer flagforsu,
           java.lang.Integer flagrifliquid,
           java.lang.Integer flagrifsolidiindustr,
           java.lang.Integer flagscartzoo,
           java.lang.Integer flagsottoprodliq,
           java.lang.Integer idCcost,
           java.lang.String note,
           java.lang.Float provcom,
           java.lang.Float provcorta,
           java.lang.Float provexcom,
           java.lang.Float provnaz,
           java.lang.Float supoccupata,
           java.lang.String titoloAutorizzativo,
           com.hyperborea.sira.ws.VocAlimentComb vocAliment,
           com.hyperborea.sira.ws.VocBmaliment vocBmaliment,
           com.hyperborea.sira.ws.VocBmimpazoto vocBmimpazoto,
           com.hyperborea.sira.ws.VocBmtipoprod vocBmtipoprod,
           com.hyperborea.sira.ws.VocBmumis vocBmumis) {
           this.autoriz = autoriz;
           this.capacita = capacita;
           this.dataEntrataEsercizio = dataEntrataEsercizio;
           this.dataFine = dataFine;
           this.destdigest = destdigest;
           this.flagaltrispagri = flagaltrispagri;
           this.flagaltrspindustria = flagaltrspindustria;
           this.flagbiolegno = flagbiolegno;
           this.flagbioliquida = flagbioliquida;
           this.flagcereali = flagcereali;
           this.flagfanghi = flagfanghi;
           this.flagforsu = flagforsu;
           this.flagrifliquid = flagrifliquid;
           this.flagrifsolidiindustr = flagrifsolidiindustr;
           this.flagscartzoo = flagscartzoo;
           this.flagsottoprodliq = flagsottoprodliq;
           this.idCcost = idCcost;
           this.note = note;
           this.provcom = provcom;
           this.provcorta = provcorta;
           this.provexcom = provexcom;
           this.provnaz = provnaz;
           this.supoccupata = supoccupata;
           this.titoloAutorizzativo = titoloAutorizzativo;
           this.vocAliment = vocAliment;
           this.vocBmaliment = vocBmaliment;
           this.vocBmimpazoto = vocBmimpazoto;
           this.vocBmtipoprod = vocBmtipoprod;
           this.vocBmumis = vocBmumis;
    }


    /**
     * Gets the autoriz value for this CcostEeBm.
     * 
     * @return autoriz
     */
    public java.lang.String getAutoriz() {
        return autoriz;
    }


    /**
     * Sets the autoriz value for this CcostEeBm.
     * 
     * @param autoriz
     */
    public void setAutoriz(java.lang.String autoriz) {
        this.autoriz = autoriz;
    }


    /**
     * Gets the capacita value for this CcostEeBm.
     * 
     * @return capacita
     */
    public java.lang.Float getCapacita() {
        return capacita;
    }


    /**
     * Sets the capacita value for this CcostEeBm.
     * 
     * @param capacita
     */
    public void setCapacita(java.lang.Float capacita) {
        this.capacita = capacita;
    }


    /**
     * Gets the dataEntrataEsercizio value for this CcostEeBm.
     * 
     * @return dataEntrataEsercizio
     */
    public java.util.Calendar getDataEntrataEsercizio() {
        return dataEntrataEsercizio;
    }


    /**
     * Sets the dataEntrataEsercizio value for this CcostEeBm.
     * 
     * @param dataEntrataEsercizio
     */
    public void setDataEntrataEsercizio(java.util.Calendar dataEntrataEsercizio) {
        this.dataEntrataEsercizio = dataEntrataEsercizio;
    }


    /**
     * Gets the dataFine value for this CcostEeBm.
     * 
     * @return dataFine
     */
    public java.util.Calendar getDataFine() {
        return dataFine;
    }


    /**
     * Sets the dataFine value for this CcostEeBm.
     * 
     * @param dataFine
     */
    public void setDataFine(java.util.Calendar dataFine) {
        this.dataFine = dataFine;
    }


    /**
     * Gets the destdigest value for this CcostEeBm.
     * 
     * @return destdigest
     */
    public java.lang.String getDestdigest() {
        return destdigest;
    }


    /**
     * Sets the destdigest value for this CcostEeBm.
     * 
     * @param destdigest
     */
    public void setDestdigest(java.lang.String destdigest) {
        this.destdigest = destdigest;
    }


    /**
     * Gets the flagaltrispagri value for this CcostEeBm.
     * 
     * @return flagaltrispagri
     */
    public java.lang.Integer getFlagaltrispagri() {
        return flagaltrispagri;
    }


    /**
     * Sets the flagaltrispagri value for this CcostEeBm.
     * 
     * @param flagaltrispagri
     */
    public void setFlagaltrispagri(java.lang.Integer flagaltrispagri) {
        this.flagaltrispagri = flagaltrispagri;
    }


    /**
     * Gets the flagaltrspindustria value for this CcostEeBm.
     * 
     * @return flagaltrspindustria
     */
    public java.lang.Integer getFlagaltrspindustria() {
        return flagaltrspindustria;
    }


    /**
     * Sets the flagaltrspindustria value for this CcostEeBm.
     * 
     * @param flagaltrspindustria
     */
    public void setFlagaltrspindustria(java.lang.Integer flagaltrspindustria) {
        this.flagaltrspindustria = flagaltrspindustria;
    }


    /**
     * Gets the flagbiolegno value for this CcostEeBm.
     * 
     * @return flagbiolegno
     */
    public java.lang.Integer getFlagbiolegno() {
        return flagbiolegno;
    }


    /**
     * Sets the flagbiolegno value for this CcostEeBm.
     * 
     * @param flagbiolegno
     */
    public void setFlagbiolegno(java.lang.Integer flagbiolegno) {
        this.flagbiolegno = flagbiolegno;
    }


    /**
     * Gets the flagbioliquida value for this CcostEeBm.
     * 
     * @return flagbioliquida
     */
    public java.lang.Integer getFlagbioliquida() {
        return flagbioliquida;
    }


    /**
     * Sets the flagbioliquida value for this CcostEeBm.
     * 
     * @param flagbioliquida
     */
    public void setFlagbioliquida(java.lang.Integer flagbioliquida) {
        this.flagbioliquida = flagbioliquida;
    }


    /**
     * Gets the flagcereali value for this CcostEeBm.
     * 
     * @return flagcereali
     */
    public java.lang.Integer getFlagcereali() {
        return flagcereali;
    }


    /**
     * Sets the flagcereali value for this CcostEeBm.
     * 
     * @param flagcereali
     */
    public void setFlagcereali(java.lang.Integer flagcereali) {
        this.flagcereali = flagcereali;
    }


    /**
     * Gets the flagfanghi value for this CcostEeBm.
     * 
     * @return flagfanghi
     */
    public java.lang.Integer getFlagfanghi() {
        return flagfanghi;
    }


    /**
     * Sets the flagfanghi value for this CcostEeBm.
     * 
     * @param flagfanghi
     */
    public void setFlagfanghi(java.lang.Integer flagfanghi) {
        this.flagfanghi = flagfanghi;
    }


    /**
     * Gets the flagforsu value for this CcostEeBm.
     * 
     * @return flagforsu
     */
    public java.lang.Integer getFlagforsu() {
        return flagforsu;
    }


    /**
     * Sets the flagforsu value for this CcostEeBm.
     * 
     * @param flagforsu
     */
    public void setFlagforsu(java.lang.Integer flagforsu) {
        this.flagforsu = flagforsu;
    }


    /**
     * Gets the flagrifliquid value for this CcostEeBm.
     * 
     * @return flagrifliquid
     */
    public java.lang.Integer getFlagrifliquid() {
        return flagrifliquid;
    }


    /**
     * Sets the flagrifliquid value for this CcostEeBm.
     * 
     * @param flagrifliquid
     */
    public void setFlagrifliquid(java.lang.Integer flagrifliquid) {
        this.flagrifliquid = flagrifliquid;
    }


    /**
     * Gets the flagrifsolidiindustr value for this CcostEeBm.
     * 
     * @return flagrifsolidiindustr
     */
    public java.lang.Integer getFlagrifsolidiindustr() {
        return flagrifsolidiindustr;
    }


    /**
     * Sets the flagrifsolidiindustr value for this CcostEeBm.
     * 
     * @param flagrifsolidiindustr
     */
    public void setFlagrifsolidiindustr(java.lang.Integer flagrifsolidiindustr) {
        this.flagrifsolidiindustr = flagrifsolidiindustr;
    }


    /**
     * Gets the flagscartzoo value for this CcostEeBm.
     * 
     * @return flagscartzoo
     */
    public java.lang.Integer getFlagscartzoo() {
        return flagscartzoo;
    }


    /**
     * Sets the flagscartzoo value for this CcostEeBm.
     * 
     * @param flagscartzoo
     */
    public void setFlagscartzoo(java.lang.Integer flagscartzoo) {
        this.flagscartzoo = flagscartzoo;
    }


    /**
     * Gets the flagsottoprodliq value for this CcostEeBm.
     * 
     * @return flagsottoprodliq
     */
    public java.lang.Integer getFlagsottoprodliq() {
        return flagsottoprodliq;
    }


    /**
     * Sets the flagsottoprodliq value for this CcostEeBm.
     * 
     * @param flagsottoprodliq
     */
    public void setFlagsottoprodliq(java.lang.Integer flagsottoprodliq) {
        this.flagsottoprodliq = flagsottoprodliq;
    }


    /**
     * Gets the idCcost value for this CcostEeBm.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostEeBm.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the note value for this CcostEeBm.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this CcostEeBm.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the provcom value for this CcostEeBm.
     * 
     * @return provcom
     */
    public java.lang.Float getProvcom() {
        return provcom;
    }


    /**
     * Sets the provcom value for this CcostEeBm.
     * 
     * @param provcom
     */
    public void setProvcom(java.lang.Float provcom) {
        this.provcom = provcom;
    }


    /**
     * Gets the provcorta value for this CcostEeBm.
     * 
     * @return provcorta
     */
    public java.lang.Float getProvcorta() {
        return provcorta;
    }


    /**
     * Sets the provcorta value for this CcostEeBm.
     * 
     * @param provcorta
     */
    public void setProvcorta(java.lang.Float provcorta) {
        this.provcorta = provcorta;
    }


    /**
     * Gets the provexcom value for this CcostEeBm.
     * 
     * @return provexcom
     */
    public java.lang.Float getProvexcom() {
        return provexcom;
    }


    /**
     * Sets the provexcom value for this CcostEeBm.
     * 
     * @param provexcom
     */
    public void setProvexcom(java.lang.Float provexcom) {
        this.provexcom = provexcom;
    }


    /**
     * Gets the provnaz value for this CcostEeBm.
     * 
     * @return provnaz
     */
    public java.lang.Float getProvnaz() {
        return provnaz;
    }


    /**
     * Sets the provnaz value for this CcostEeBm.
     * 
     * @param provnaz
     */
    public void setProvnaz(java.lang.Float provnaz) {
        this.provnaz = provnaz;
    }


    /**
     * Gets the supoccupata value for this CcostEeBm.
     * 
     * @return supoccupata
     */
    public java.lang.Float getSupoccupata() {
        return supoccupata;
    }


    /**
     * Sets the supoccupata value for this CcostEeBm.
     * 
     * @param supoccupata
     */
    public void setSupoccupata(java.lang.Float supoccupata) {
        this.supoccupata = supoccupata;
    }


    /**
     * Gets the titoloAutorizzativo value for this CcostEeBm.
     * 
     * @return titoloAutorizzativo
     */
    public java.lang.String getTitoloAutorizzativo() {
        return titoloAutorizzativo;
    }


    /**
     * Sets the titoloAutorizzativo value for this CcostEeBm.
     * 
     * @param titoloAutorizzativo
     */
    public void setTitoloAutorizzativo(java.lang.String titoloAutorizzativo) {
        this.titoloAutorizzativo = titoloAutorizzativo;
    }


    /**
     * Gets the vocAliment value for this CcostEeBm.
     * 
     * @return vocAliment
     */
    public com.hyperborea.sira.ws.VocAlimentComb getVocAliment() {
        return vocAliment;
    }


    /**
     * Sets the vocAliment value for this CcostEeBm.
     * 
     * @param vocAliment
     */
    public void setVocAliment(com.hyperborea.sira.ws.VocAlimentComb vocAliment) {
        this.vocAliment = vocAliment;
    }


    /**
     * Gets the vocBmaliment value for this CcostEeBm.
     * 
     * @return vocBmaliment
     */
    public com.hyperborea.sira.ws.VocBmaliment getVocBmaliment() {
        return vocBmaliment;
    }


    /**
     * Sets the vocBmaliment value for this CcostEeBm.
     * 
     * @param vocBmaliment
     */
    public void setVocBmaliment(com.hyperborea.sira.ws.VocBmaliment vocBmaliment) {
        this.vocBmaliment = vocBmaliment;
    }


    /**
     * Gets the vocBmimpazoto value for this CcostEeBm.
     * 
     * @return vocBmimpazoto
     */
    public com.hyperborea.sira.ws.VocBmimpazoto getVocBmimpazoto() {
        return vocBmimpazoto;
    }


    /**
     * Sets the vocBmimpazoto value for this CcostEeBm.
     * 
     * @param vocBmimpazoto
     */
    public void setVocBmimpazoto(com.hyperborea.sira.ws.VocBmimpazoto vocBmimpazoto) {
        this.vocBmimpazoto = vocBmimpazoto;
    }


    /**
     * Gets the vocBmtipoprod value for this CcostEeBm.
     * 
     * @return vocBmtipoprod
     */
    public com.hyperborea.sira.ws.VocBmtipoprod getVocBmtipoprod() {
        return vocBmtipoprod;
    }


    /**
     * Sets the vocBmtipoprod value for this CcostEeBm.
     * 
     * @param vocBmtipoprod
     */
    public void setVocBmtipoprod(com.hyperborea.sira.ws.VocBmtipoprod vocBmtipoprod) {
        this.vocBmtipoprod = vocBmtipoprod;
    }


    /**
     * Gets the vocBmumis value for this CcostEeBm.
     * 
     * @return vocBmumis
     */
    public com.hyperborea.sira.ws.VocBmumis getVocBmumis() {
        return vocBmumis;
    }


    /**
     * Sets the vocBmumis value for this CcostEeBm.
     * 
     * @param vocBmumis
     */
    public void setVocBmumis(com.hyperborea.sira.ws.VocBmumis vocBmumis) {
        this.vocBmumis = vocBmumis;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostEeBm)) return false;
        CcostEeBm other = (CcostEeBm) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.autoriz==null && other.getAutoriz()==null) || 
             (this.autoriz!=null &&
              this.autoriz.equals(other.getAutoriz()))) &&
            ((this.capacita==null && other.getCapacita()==null) || 
             (this.capacita!=null &&
              this.capacita.equals(other.getCapacita()))) &&
            ((this.dataEntrataEsercizio==null && other.getDataEntrataEsercizio()==null) || 
             (this.dataEntrataEsercizio!=null &&
              this.dataEntrataEsercizio.equals(other.getDataEntrataEsercizio()))) &&
            ((this.dataFine==null && other.getDataFine()==null) || 
             (this.dataFine!=null &&
              this.dataFine.equals(other.getDataFine()))) &&
            ((this.destdigest==null && other.getDestdigest()==null) || 
             (this.destdigest!=null &&
              this.destdigest.equals(other.getDestdigest()))) &&
            ((this.flagaltrispagri==null && other.getFlagaltrispagri()==null) || 
             (this.flagaltrispagri!=null &&
              this.flagaltrispagri.equals(other.getFlagaltrispagri()))) &&
            ((this.flagaltrspindustria==null && other.getFlagaltrspindustria()==null) || 
             (this.flagaltrspindustria!=null &&
              this.flagaltrspindustria.equals(other.getFlagaltrspindustria()))) &&
            ((this.flagbiolegno==null && other.getFlagbiolegno()==null) || 
             (this.flagbiolegno!=null &&
              this.flagbiolegno.equals(other.getFlagbiolegno()))) &&
            ((this.flagbioliquida==null && other.getFlagbioliquida()==null) || 
             (this.flagbioliquida!=null &&
              this.flagbioliquida.equals(other.getFlagbioliquida()))) &&
            ((this.flagcereali==null && other.getFlagcereali()==null) || 
             (this.flagcereali!=null &&
              this.flagcereali.equals(other.getFlagcereali()))) &&
            ((this.flagfanghi==null && other.getFlagfanghi()==null) || 
             (this.flagfanghi!=null &&
              this.flagfanghi.equals(other.getFlagfanghi()))) &&
            ((this.flagforsu==null && other.getFlagforsu()==null) || 
             (this.flagforsu!=null &&
              this.flagforsu.equals(other.getFlagforsu()))) &&
            ((this.flagrifliquid==null && other.getFlagrifliquid()==null) || 
             (this.flagrifliquid!=null &&
              this.flagrifliquid.equals(other.getFlagrifliquid()))) &&
            ((this.flagrifsolidiindustr==null && other.getFlagrifsolidiindustr()==null) || 
             (this.flagrifsolidiindustr!=null &&
              this.flagrifsolidiindustr.equals(other.getFlagrifsolidiindustr()))) &&
            ((this.flagscartzoo==null && other.getFlagscartzoo()==null) || 
             (this.flagscartzoo!=null &&
              this.flagscartzoo.equals(other.getFlagscartzoo()))) &&
            ((this.flagsottoprodliq==null && other.getFlagsottoprodliq()==null) || 
             (this.flagsottoprodliq!=null &&
              this.flagsottoprodliq.equals(other.getFlagsottoprodliq()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.provcom==null && other.getProvcom()==null) || 
             (this.provcom!=null &&
              this.provcom.equals(other.getProvcom()))) &&
            ((this.provcorta==null && other.getProvcorta()==null) || 
             (this.provcorta!=null &&
              this.provcorta.equals(other.getProvcorta()))) &&
            ((this.provexcom==null && other.getProvexcom()==null) || 
             (this.provexcom!=null &&
              this.provexcom.equals(other.getProvexcom()))) &&
            ((this.provnaz==null && other.getProvnaz()==null) || 
             (this.provnaz!=null &&
              this.provnaz.equals(other.getProvnaz()))) &&
            ((this.supoccupata==null && other.getSupoccupata()==null) || 
             (this.supoccupata!=null &&
              this.supoccupata.equals(other.getSupoccupata()))) &&
            ((this.titoloAutorizzativo==null && other.getTitoloAutorizzativo()==null) || 
             (this.titoloAutorizzativo!=null &&
              this.titoloAutorizzativo.equals(other.getTitoloAutorizzativo()))) &&
            ((this.vocAliment==null && other.getVocAliment()==null) || 
             (this.vocAliment!=null &&
              this.vocAliment.equals(other.getVocAliment()))) &&
            ((this.vocBmaliment==null && other.getVocBmaliment()==null) || 
             (this.vocBmaliment!=null &&
              this.vocBmaliment.equals(other.getVocBmaliment()))) &&
            ((this.vocBmimpazoto==null && other.getVocBmimpazoto()==null) || 
             (this.vocBmimpazoto!=null &&
              this.vocBmimpazoto.equals(other.getVocBmimpazoto()))) &&
            ((this.vocBmtipoprod==null && other.getVocBmtipoprod()==null) || 
             (this.vocBmtipoprod!=null &&
              this.vocBmtipoprod.equals(other.getVocBmtipoprod()))) &&
            ((this.vocBmumis==null && other.getVocBmumis()==null) || 
             (this.vocBmumis!=null &&
              this.vocBmumis.equals(other.getVocBmumis())));
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
        if (getAutoriz() != null) {
            _hashCode += getAutoriz().hashCode();
        }
        if (getCapacita() != null) {
            _hashCode += getCapacita().hashCode();
        }
        if (getDataEntrataEsercizio() != null) {
            _hashCode += getDataEntrataEsercizio().hashCode();
        }
        if (getDataFine() != null) {
            _hashCode += getDataFine().hashCode();
        }
        if (getDestdigest() != null) {
            _hashCode += getDestdigest().hashCode();
        }
        if (getFlagaltrispagri() != null) {
            _hashCode += getFlagaltrispagri().hashCode();
        }
        if (getFlagaltrspindustria() != null) {
            _hashCode += getFlagaltrspindustria().hashCode();
        }
        if (getFlagbiolegno() != null) {
            _hashCode += getFlagbiolegno().hashCode();
        }
        if (getFlagbioliquida() != null) {
            _hashCode += getFlagbioliquida().hashCode();
        }
        if (getFlagcereali() != null) {
            _hashCode += getFlagcereali().hashCode();
        }
        if (getFlagfanghi() != null) {
            _hashCode += getFlagfanghi().hashCode();
        }
        if (getFlagforsu() != null) {
            _hashCode += getFlagforsu().hashCode();
        }
        if (getFlagrifliquid() != null) {
            _hashCode += getFlagrifliquid().hashCode();
        }
        if (getFlagrifsolidiindustr() != null) {
            _hashCode += getFlagrifsolidiindustr().hashCode();
        }
        if (getFlagscartzoo() != null) {
            _hashCode += getFlagscartzoo().hashCode();
        }
        if (getFlagsottoprodliq() != null) {
            _hashCode += getFlagsottoprodliq().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getProvcom() != null) {
            _hashCode += getProvcom().hashCode();
        }
        if (getProvcorta() != null) {
            _hashCode += getProvcorta().hashCode();
        }
        if (getProvexcom() != null) {
            _hashCode += getProvexcom().hashCode();
        }
        if (getProvnaz() != null) {
            _hashCode += getProvnaz().hashCode();
        }
        if (getSupoccupata() != null) {
            _hashCode += getSupoccupata().hashCode();
        }
        if (getTitoloAutorizzativo() != null) {
            _hashCode += getTitoloAutorizzativo().hashCode();
        }
        if (getVocAliment() != null) {
            _hashCode += getVocAliment().hashCode();
        }
        if (getVocBmaliment() != null) {
            _hashCode += getVocBmaliment().hashCode();
        }
        if (getVocBmimpazoto() != null) {
            _hashCode += getVocBmimpazoto().hashCode();
        }
        if (getVocBmtipoprod() != null) {
            _hashCode += getVocBmtipoprod().hashCode();
        }
        if (getVocBmumis() != null) {
            _hashCode += getVocBmumis().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostEeBm.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostEeBm"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("autoriz");
        elemField.setXmlName(new javax.xml.namespace.QName("", "autoriz"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capacita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataEntrataEsercizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataEntrataEsercizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField.setFieldName("destdigest");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destdigest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagaltrispagri");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagaltrispagri"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagaltrspindustria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagaltrspindustria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagbiolegno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagbiolegno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagbioliquida");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagbioliquida"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagcereali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagcereali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagfanghi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagfanghi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagforsu");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagforsu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagrifliquid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagrifliquid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagrifsolidiindustr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagrifsolidiindustr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagscartzoo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagscartzoo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagsottoprodliq");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagsottoprodliq"));
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
        elemField.setFieldName("note");
        elemField.setXmlName(new javax.xml.namespace.QName("", "note"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provcom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provcom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provcorta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provcorta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provexcom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provexcom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provnaz");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provnaz"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supoccupata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "supoccupata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titoloAutorizzativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "titoloAutorizzativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAliment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAliment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAlimentComb"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocBmaliment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocBmaliment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocBmaliment"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocBmimpazoto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocBmimpazoto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocBmimpazoto"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocBmtipoprod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocBmtipoprod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocBmtipoprod"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocBmumis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocBmumis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocBmumis"));
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
