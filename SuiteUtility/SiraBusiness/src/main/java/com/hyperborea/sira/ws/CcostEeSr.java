/**
 * CcostEeSr.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostEeSr  implements java.io.Serializable {
    private java.lang.Float area;

    private java.lang.String codiceCensImp;

    private java.lang.String codicePod;

    private java.lang.String codiceRintracciabilita;

    private java.lang.String codiceSigraf;

    private com.hyperborea.sira.ws.Consumisr[] consumisr;

    private java.util.Calendar dataConferma;

    private java.util.Calendar dataEntrataEsercizio;

    private java.util.Calendar dataFine;

    private java.lang.Float eneNoAutocons;

    private java.lang.Integer flagconnrete;

    private java.lang.Integer flagconntele;

    private java.lang.Integer idCcost;

    private java.lang.String note;

    private java.lang.Float potenzaElettrica;

    private java.lang.Float potenzaTermica;

    private java.lang.Float potenzaTermicaUtile;

    private java.lang.Float prodElettrica;

    private java.lang.Float prodTermica;

    private com.hyperborea.sira.ws.Produzionisr[] produzionisr;

    private java.lang.Float supoccupata;

    private java.lang.String titoloAutorizzativo;

    private com.hyperborea.sira.ws.VocImpIns vocImpIns;

    private com.hyperborea.sira.ws.VocTipContratt vocTipContratt;

    private com.hyperborea.sira.ws.VocTipPannelli vocTipPannelli;

    public CcostEeSr() {
    }

    public CcostEeSr(
           java.lang.Float area,
           java.lang.String codiceCensImp,
           java.lang.String codicePod,
           java.lang.String codiceRintracciabilita,
           java.lang.String codiceSigraf,
           com.hyperborea.sira.ws.Consumisr[] consumisr,
           java.util.Calendar dataConferma,
           java.util.Calendar dataEntrataEsercizio,
           java.util.Calendar dataFine,
           java.lang.Float eneNoAutocons,
           java.lang.Integer flagconnrete,
           java.lang.Integer flagconntele,
           java.lang.Integer idCcost,
           java.lang.String note,
           java.lang.Float potenzaElettrica,
           java.lang.Float potenzaTermica,
           java.lang.Float potenzaTermicaUtile,
           java.lang.Float prodElettrica,
           java.lang.Float prodTermica,
           com.hyperborea.sira.ws.Produzionisr[] produzionisr,
           java.lang.Float supoccupata,
           java.lang.String titoloAutorizzativo,
           com.hyperborea.sira.ws.VocImpIns vocImpIns,
           com.hyperborea.sira.ws.VocTipContratt vocTipContratt,
           com.hyperborea.sira.ws.VocTipPannelli vocTipPannelli) {
           this.area = area;
           this.codiceCensImp = codiceCensImp;
           this.codicePod = codicePod;
           this.codiceRintracciabilita = codiceRintracciabilita;
           this.codiceSigraf = codiceSigraf;
           this.consumisr = consumisr;
           this.dataConferma = dataConferma;
           this.dataEntrataEsercizio = dataEntrataEsercizio;
           this.dataFine = dataFine;
           this.eneNoAutocons = eneNoAutocons;
           this.flagconnrete = flagconnrete;
           this.flagconntele = flagconntele;
           this.idCcost = idCcost;
           this.note = note;
           this.potenzaElettrica = potenzaElettrica;
           this.potenzaTermica = potenzaTermica;
           this.potenzaTermicaUtile = potenzaTermicaUtile;
           this.prodElettrica = prodElettrica;
           this.prodTermica = prodTermica;
           this.produzionisr = produzionisr;
           this.supoccupata = supoccupata;
           this.titoloAutorizzativo = titoloAutorizzativo;
           this.vocImpIns = vocImpIns;
           this.vocTipContratt = vocTipContratt;
           this.vocTipPannelli = vocTipPannelli;
    }


    /**
     * Gets the area value for this CcostEeSr.
     * 
     * @return area
     */
    public java.lang.Float getArea() {
        return area;
    }


    /**
     * Sets the area value for this CcostEeSr.
     * 
     * @param area
     */
    public void setArea(java.lang.Float area) {
        this.area = area;
    }


    /**
     * Gets the codiceCensImp value for this CcostEeSr.
     * 
     * @return codiceCensImp
     */
    public java.lang.String getCodiceCensImp() {
        return codiceCensImp;
    }


    /**
     * Sets the codiceCensImp value for this CcostEeSr.
     * 
     * @param codiceCensImp
     */
    public void setCodiceCensImp(java.lang.String codiceCensImp) {
        this.codiceCensImp = codiceCensImp;
    }


    /**
     * Gets the codicePod value for this CcostEeSr.
     * 
     * @return codicePod
     */
    public java.lang.String getCodicePod() {
        return codicePod;
    }


    /**
     * Sets the codicePod value for this CcostEeSr.
     * 
     * @param codicePod
     */
    public void setCodicePod(java.lang.String codicePod) {
        this.codicePod = codicePod;
    }


    /**
     * Gets the codiceRintracciabilita value for this CcostEeSr.
     * 
     * @return codiceRintracciabilita
     */
    public java.lang.String getCodiceRintracciabilita() {
        return codiceRintracciabilita;
    }


    /**
     * Sets the codiceRintracciabilita value for this CcostEeSr.
     * 
     * @param codiceRintracciabilita
     */
    public void setCodiceRintracciabilita(java.lang.String codiceRintracciabilita) {
        this.codiceRintracciabilita = codiceRintracciabilita;
    }


    /**
     * Gets the codiceSigraf value for this CcostEeSr.
     * 
     * @return codiceSigraf
     */
    public java.lang.String getCodiceSigraf() {
        return codiceSigraf;
    }


    /**
     * Sets the codiceSigraf value for this CcostEeSr.
     * 
     * @param codiceSigraf
     */
    public void setCodiceSigraf(java.lang.String codiceSigraf) {
        this.codiceSigraf = codiceSigraf;
    }


    /**
     * Gets the consumisr value for this CcostEeSr.
     * 
     * @return consumisr
     */
    public com.hyperborea.sira.ws.Consumisr[] getConsumisr() {
        return consumisr;
    }


    /**
     * Sets the consumisr value for this CcostEeSr.
     * 
     * @param consumisr
     */
    public void setConsumisr(com.hyperborea.sira.ws.Consumisr[] consumisr) {
        this.consumisr = consumisr;
    }

    public com.hyperborea.sira.ws.Consumisr getConsumisr(int i) {
        return this.consumisr[i];
    }

    public void setConsumisr(int i, com.hyperborea.sira.ws.Consumisr _value) {
        this.consumisr[i] = _value;
    }


    /**
     * Gets the dataConferma value for this CcostEeSr.
     * 
     * @return dataConferma
     */
    public java.util.Calendar getDataConferma() {
        return dataConferma;
    }


    /**
     * Sets the dataConferma value for this CcostEeSr.
     * 
     * @param dataConferma
     */
    public void setDataConferma(java.util.Calendar dataConferma) {
        this.dataConferma = dataConferma;
    }


    /**
     * Gets the dataEntrataEsercizio value for this CcostEeSr.
     * 
     * @return dataEntrataEsercizio
     */
    public java.util.Calendar getDataEntrataEsercizio() {
        return dataEntrataEsercizio;
    }


    /**
     * Sets the dataEntrataEsercizio value for this CcostEeSr.
     * 
     * @param dataEntrataEsercizio
     */
    public void setDataEntrataEsercizio(java.util.Calendar dataEntrataEsercizio) {
        this.dataEntrataEsercizio = dataEntrataEsercizio;
    }


    /**
     * Gets the dataFine value for this CcostEeSr.
     * 
     * @return dataFine
     */
    public java.util.Calendar getDataFine() {
        return dataFine;
    }


    /**
     * Sets the dataFine value for this CcostEeSr.
     * 
     * @param dataFine
     */
    public void setDataFine(java.util.Calendar dataFine) {
        this.dataFine = dataFine;
    }


    /**
     * Gets the eneNoAutocons value for this CcostEeSr.
     * 
     * @return eneNoAutocons
     */
    public java.lang.Float getEneNoAutocons() {
        return eneNoAutocons;
    }


    /**
     * Sets the eneNoAutocons value for this CcostEeSr.
     * 
     * @param eneNoAutocons
     */
    public void setEneNoAutocons(java.lang.Float eneNoAutocons) {
        this.eneNoAutocons = eneNoAutocons;
    }


    /**
     * Gets the flagconnrete value for this CcostEeSr.
     * 
     * @return flagconnrete
     */
    public java.lang.Integer getFlagconnrete() {
        return flagconnrete;
    }


    /**
     * Sets the flagconnrete value for this CcostEeSr.
     * 
     * @param flagconnrete
     */
    public void setFlagconnrete(java.lang.Integer flagconnrete) {
        this.flagconnrete = flagconnrete;
    }


    /**
     * Gets the flagconntele value for this CcostEeSr.
     * 
     * @return flagconntele
     */
    public java.lang.Integer getFlagconntele() {
        return flagconntele;
    }


    /**
     * Sets the flagconntele value for this CcostEeSr.
     * 
     * @param flagconntele
     */
    public void setFlagconntele(java.lang.Integer flagconntele) {
        this.flagconntele = flagconntele;
    }


    /**
     * Gets the idCcost value for this CcostEeSr.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostEeSr.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the note value for this CcostEeSr.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this CcostEeSr.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the potenzaElettrica value for this CcostEeSr.
     * 
     * @return potenzaElettrica
     */
    public java.lang.Float getPotenzaElettrica() {
        return potenzaElettrica;
    }


    /**
     * Sets the potenzaElettrica value for this CcostEeSr.
     * 
     * @param potenzaElettrica
     */
    public void setPotenzaElettrica(java.lang.Float potenzaElettrica) {
        this.potenzaElettrica = potenzaElettrica;
    }


    /**
     * Gets the potenzaTermica value for this CcostEeSr.
     * 
     * @return potenzaTermica
     */
    public java.lang.Float getPotenzaTermica() {
        return potenzaTermica;
    }


    /**
     * Sets the potenzaTermica value for this CcostEeSr.
     * 
     * @param potenzaTermica
     */
    public void setPotenzaTermica(java.lang.Float potenzaTermica) {
        this.potenzaTermica = potenzaTermica;
    }


    /**
     * Gets the potenzaTermicaUtile value for this CcostEeSr.
     * 
     * @return potenzaTermicaUtile
     */
    public java.lang.Float getPotenzaTermicaUtile() {
        return potenzaTermicaUtile;
    }


    /**
     * Sets the potenzaTermicaUtile value for this CcostEeSr.
     * 
     * @param potenzaTermicaUtile
     */
    public void setPotenzaTermicaUtile(java.lang.Float potenzaTermicaUtile) {
        this.potenzaTermicaUtile = potenzaTermicaUtile;
    }


    /**
     * Gets the prodElettrica value for this CcostEeSr.
     * 
     * @return prodElettrica
     */
    public java.lang.Float getProdElettrica() {
        return prodElettrica;
    }


    /**
     * Sets the prodElettrica value for this CcostEeSr.
     * 
     * @param prodElettrica
     */
    public void setProdElettrica(java.lang.Float prodElettrica) {
        this.prodElettrica = prodElettrica;
    }


    /**
     * Gets the prodTermica value for this CcostEeSr.
     * 
     * @return prodTermica
     */
    public java.lang.Float getProdTermica() {
        return prodTermica;
    }


    /**
     * Sets the prodTermica value for this CcostEeSr.
     * 
     * @param prodTermica
     */
    public void setProdTermica(java.lang.Float prodTermica) {
        this.prodTermica = prodTermica;
    }


    /**
     * Gets the produzionisr value for this CcostEeSr.
     * 
     * @return produzionisr
     */
    public com.hyperborea.sira.ws.Produzionisr[] getProduzionisr() {
        return produzionisr;
    }


    /**
     * Sets the produzionisr value for this CcostEeSr.
     * 
     * @param produzionisr
     */
    public void setProduzionisr(com.hyperborea.sira.ws.Produzionisr[] produzionisr) {
        this.produzionisr = produzionisr;
    }

    public com.hyperborea.sira.ws.Produzionisr getProduzionisr(int i) {
        return this.produzionisr[i];
    }

    public void setProduzionisr(int i, com.hyperborea.sira.ws.Produzionisr _value) {
        this.produzionisr[i] = _value;
    }


    /**
     * Gets the supoccupata value for this CcostEeSr.
     * 
     * @return supoccupata
     */
    public java.lang.Float getSupoccupata() {
        return supoccupata;
    }


    /**
     * Sets the supoccupata value for this CcostEeSr.
     * 
     * @param supoccupata
     */
    public void setSupoccupata(java.lang.Float supoccupata) {
        this.supoccupata = supoccupata;
    }


    /**
     * Gets the titoloAutorizzativo value for this CcostEeSr.
     * 
     * @return titoloAutorizzativo
     */
    public java.lang.String getTitoloAutorizzativo() {
        return titoloAutorizzativo;
    }


    /**
     * Sets the titoloAutorizzativo value for this CcostEeSr.
     * 
     * @param titoloAutorizzativo
     */
    public void setTitoloAutorizzativo(java.lang.String titoloAutorizzativo) {
        this.titoloAutorizzativo = titoloAutorizzativo;
    }


    /**
     * Gets the vocImpIns value for this CcostEeSr.
     * 
     * @return vocImpIns
     */
    public com.hyperborea.sira.ws.VocImpIns getVocImpIns() {
        return vocImpIns;
    }


    /**
     * Sets the vocImpIns value for this CcostEeSr.
     * 
     * @param vocImpIns
     */
    public void setVocImpIns(com.hyperborea.sira.ws.VocImpIns vocImpIns) {
        this.vocImpIns = vocImpIns;
    }


    /**
     * Gets the vocTipContratt value for this CcostEeSr.
     * 
     * @return vocTipContratt
     */
    public com.hyperborea.sira.ws.VocTipContratt getVocTipContratt() {
        return vocTipContratt;
    }


    /**
     * Sets the vocTipContratt value for this CcostEeSr.
     * 
     * @param vocTipContratt
     */
    public void setVocTipContratt(com.hyperborea.sira.ws.VocTipContratt vocTipContratt) {
        this.vocTipContratt = vocTipContratt;
    }


    /**
     * Gets the vocTipPannelli value for this CcostEeSr.
     * 
     * @return vocTipPannelli
     */
    public com.hyperborea.sira.ws.VocTipPannelli getVocTipPannelli() {
        return vocTipPannelli;
    }


    /**
     * Sets the vocTipPannelli value for this CcostEeSr.
     * 
     * @param vocTipPannelli
     */
    public void setVocTipPannelli(com.hyperborea.sira.ws.VocTipPannelli vocTipPannelli) {
        this.vocTipPannelli = vocTipPannelli;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostEeSr)) return false;
        CcostEeSr other = (CcostEeSr) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.area==null && other.getArea()==null) || 
             (this.area!=null &&
              this.area.equals(other.getArea()))) &&
            ((this.codiceCensImp==null && other.getCodiceCensImp()==null) || 
             (this.codiceCensImp!=null &&
              this.codiceCensImp.equals(other.getCodiceCensImp()))) &&
            ((this.codicePod==null && other.getCodicePod()==null) || 
             (this.codicePod!=null &&
              this.codicePod.equals(other.getCodicePod()))) &&
            ((this.codiceRintracciabilita==null && other.getCodiceRintracciabilita()==null) || 
             (this.codiceRintracciabilita!=null &&
              this.codiceRintracciabilita.equals(other.getCodiceRintracciabilita()))) &&
            ((this.codiceSigraf==null && other.getCodiceSigraf()==null) || 
             (this.codiceSigraf!=null &&
              this.codiceSigraf.equals(other.getCodiceSigraf()))) &&
            ((this.consumisr==null && other.getConsumisr()==null) || 
             (this.consumisr!=null &&
              java.util.Arrays.equals(this.consumisr, other.getConsumisr()))) &&
            ((this.dataConferma==null && other.getDataConferma()==null) || 
             (this.dataConferma!=null &&
              this.dataConferma.equals(other.getDataConferma()))) &&
            ((this.dataEntrataEsercizio==null && other.getDataEntrataEsercizio()==null) || 
             (this.dataEntrataEsercizio!=null &&
              this.dataEntrataEsercizio.equals(other.getDataEntrataEsercizio()))) &&
            ((this.dataFine==null && other.getDataFine()==null) || 
             (this.dataFine!=null &&
              this.dataFine.equals(other.getDataFine()))) &&
            ((this.eneNoAutocons==null && other.getEneNoAutocons()==null) || 
             (this.eneNoAutocons!=null &&
              this.eneNoAutocons.equals(other.getEneNoAutocons()))) &&
            ((this.flagconnrete==null && other.getFlagconnrete()==null) || 
             (this.flagconnrete!=null &&
              this.flagconnrete.equals(other.getFlagconnrete()))) &&
            ((this.flagconntele==null && other.getFlagconntele()==null) || 
             (this.flagconntele!=null &&
              this.flagconntele.equals(other.getFlagconntele()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.potenzaElettrica==null && other.getPotenzaElettrica()==null) || 
             (this.potenzaElettrica!=null &&
              this.potenzaElettrica.equals(other.getPotenzaElettrica()))) &&
            ((this.potenzaTermica==null && other.getPotenzaTermica()==null) || 
             (this.potenzaTermica!=null &&
              this.potenzaTermica.equals(other.getPotenzaTermica()))) &&
            ((this.potenzaTermicaUtile==null && other.getPotenzaTermicaUtile()==null) || 
             (this.potenzaTermicaUtile!=null &&
              this.potenzaTermicaUtile.equals(other.getPotenzaTermicaUtile()))) &&
            ((this.prodElettrica==null && other.getProdElettrica()==null) || 
             (this.prodElettrica!=null &&
              this.prodElettrica.equals(other.getProdElettrica()))) &&
            ((this.prodTermica==null && other.getProdTermica()==null) || 
             (this.prodTermica!=null &&
              this.prodTermica.equals(other.getProdTermica()))) &&
            ((this.produzionisr==null && other.getProduzionisr()==null) || 
             (this.produzionisr!=null &&
              java.util.Arrays.equals(this.produzionisr, other.getProduzionisr()))) &&
            ((this.supoccupata==null && other.getSupoccupata()==null) || 
             (this.supoccupata!=null &&
              this.supoccupata.equals(other.getSupoccupata()))) &&
            ((this.titoloAutorizzativo==null && other.getTitoloAutorizzativo()==null) || 
             (this.titoloAutorizzativo!=null &&
              this.titoloAutorizzativo.equals(other.getTitoloAutorizzativo()))) &&
            ((this.vocImpIns==null && other.getVocImpIns()==null) || 
             (this.vocImpIns!=null &&
              this.vocImpIns.equals(other.getVocImpIns()))) &&
            ((this.vocTipContratt==null && other.getVocTipContratt()==null) || 
             (this.vocTipContratt!=null &&
              this.vocTipContratt.equals(other.getVocTipContratt()))) &&
            ((this.vocTipPannelli==null && other.getVocTipPannelli()==null) || 
             (this.vocTipPannelli!=null &&
              this.vocTipPannelli.equals(other.getVocTipPannelli())));
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
        if (getArea() != null) {
            _hashCode += getArea().hashCode();
        }
        if (getCodiceCensImp() != null) {
            _hashCode += getCodiceCensImp().hashCode();
        }
        if (getCodicePod() != null) {
            _hashCode += getCodicePod().hashCode();
        }
        if (getCodiceRintracciabilita() != null) {
            _hashCode += getCodiceRintracciabilita().hashCode();
        }
        if (getCodiceSigraf() != null) {
            _hashCode += getCodiceSigraf().hashCode();
        }
        if (getConsumisr() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConsumisr());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConsumisr(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDataConferma() != null) {
            _hashCode += getDataConferma().hashCode();
        }
        if (getDataEntrataEsercizio() != null) {
            _hashCode += getDataEntrataEsercizio().hashCode();
        }
        if (getDataFine() != null) {
            _hashCode += getDataFine().hashCode();
        }
        if (getEneNoAutocons() != null) {
            _hashCode += getEneNoAutocons().hashCode();
        }
        if (getFlagconnrete() != null) {
            _hashCode += getFlagconnrete().hashCode();
        }
        if (getFlagconntele() != null) {
            _hashCode += getFlagconntele().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getPotenzaElettrica() != null) {
            _hashCode += getPotenzaElettrica().hashCode();
        }
        if (getPotenzaTermica() != null) {
            _hashCode += getPotenzaTermica().hashCode();
        }
        if (getPotenzaTermicaUtile() != null) {
            _hashCode += getPotenzaTermicaUtile().hashCode();
        }
        if (getProdElettrica() != null) {
            _hashCode += getProdElettrica().hashCode();
        }
        if (getProdTermica() != null) {
            _hashCode += getProdTermica().hashCode();
        }
        if (getProduzionisr() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProduzionisr());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProduzionisr(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSupoccupata() != null) {
            _hashCode += getSupoccupata().hashCode();
        }
        if (getTitoloAutorizzativo() != null) {
            _hashCode += getTitoloAutorizzativo().hashCode();
        }
        if (getVocImpIns() != null) {
            _hashCode += getVocImpIns().hashCode();
        }
        if (getVocTipContratt() != null) {
            _hashCode += getVocTipContratt().hashCode();
        }
        if (getVocTipPannelli() != null) {
            _hashCode += getVocTipPannelli().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostEeSr.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostEeSr"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("area");
        elemField.setXmlName(new javax.xml.namespace.QName("", "area"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceCensImp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceCensImp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codicePod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codicePod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceRintracciabilita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceRintracciabilita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceSigraf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceSigraf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumisr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consumisr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumisr"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataConferma");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataConferma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField.setFieldName("eneNoAutocons");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eneNoAutocons"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagconnrete");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagconnrete"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagconntele");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagconntele"));
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
        elemField.setFieldName("potenzaElettrica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzaElettrica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzaTermica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzaTermica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzaTermicaUtile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzaTermicaUtile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodElettrica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodElettrica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodTermica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodTermica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("produzionisr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "produzionisr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "produzionisr"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
        elemField.setFieldName("vocImpIns");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocImpIns"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocImpIns"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipContratt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipContratt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipContratt"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipPannelli");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipPannelli"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipPannelli"));
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
