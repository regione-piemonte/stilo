/**
 * CcostEeFv.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostEeFv  implements java.io.Serializable {
    private java.lang.Float area;

    private java.lang.String codiceCensImp;

    private java.lang.String codicePod;

    private java.lang.String codiceRintracciabilita;

    private java.lang.String codiceSigraf;

    private java.util.Calendar dataConferma;

    private java.util.Calendar dataEntrataEsercizio;

    private java.util.Calendar dataFine;

    private java.lang.Integer flagconnrete;

    private java.lang.Integer flaginseguimento;

    private java.lang.Integer idCcost;

    private java.lang.String note;

    private java.lang.Float potenzaElettrica;

    private java.lang.Float prodElettrica;

    private com.hyperborea.sira.ws.Produzionifv[] produzionifv;

    private java.lang.Float supoccupata;

    private java.lang.String titoloAutorizzativo;

    private com.hyperborea.sira.ws.VocImpIns vocImpIns;

    private com.hyperborea.sira.ws.VocIntArch vocIntArch;

    private com.hyperborea.sira.ws.VocPosizione vocPosizione;

    private com.hyperborea.sira.ws.VocTipContrattFv vocTipContrattFv;

    private com.hyperborea.sira.ws.VocTipPannelliFv vocTipPannelliFv;

    private com.hyperborea.sira.ws.Consumifv[] consumifv;

    public CcostEeFv() {
    }

    public CcostEeFv(
           java.lang.Float area,
           java.lang.String codiceCensImp,
           java.lang.String codicePod,
           java.lang.String codiceRintracciabilita,
           java.lang.String codiceSigraf,
           java.util.Calendar dataConferma,
           java.util.Calendar dataEntrataEsercizio,
           java.util.Calendar dataFine,
           java.lang.Integer flagconnrete,
           java.lang.Integer flaginseguimento,
           java.lang.Integer idCcost,
           java.lang.String note,
           java.lang.Float potenzaElettrica,
           java.lang.Float prodElettrica,
           com.hyperborea.sira.ws.Produzionifv[] produzionifv,
           java.lang.Float supoccupata,
           java.lang.String titoloAutorizzativo,
           com.hyperborea.sira.ws.VocImpIns vocImpIns,
           com.hyperborea.sira.ws.VocIntArch vocIntArch,
           com.hyperborea.sira.ws.VocPosizione vocPosizione,
           com.hyperborea.sira.ws.VocTipContrattFv vocTipContrattFv,
           com.hyperborea.sira.ws.VocTipPannelliFv vocTipPannelliFv,
           com.hyperborea.sira.ws.Consumifv[] consumifv) {
           this.area = area;
           this.codiceCensImp = codiceCensImp;
           this.codicePod = codicePod;
           this.codiceRintracciabilita = codiceRintracciabilita;
           this.codiceSigraf = codiceSigraf;
           this.dataConferma = dataConferma;
           this.dataEntrataEsercizio = dataEntrataEsercizio;
           this.dataFine = dataFine;
           this.flagconnrete = flagconnrete;
           this.flaginseguimento = flaginseguimento;
           this.idCcost = idCcost;
           this.note = note;
           this.potenzaElettrica = potenzaElettrica;
           this.prodElettrica = prodElettrica;
           this.produzionifv = produzionifv;
           this.supoccupata = supoccupata;
           this.titoloAutorizzativo = titoloAutorizzativo;
           this.vocImpIns = vocImpIns;
           this.vocIntArch = vocIntArch;
           this.vocPosizione = vocPosizione;
           this.vocTipContrattFv = vocTipContrattFv;
           this.vocTipPannelliFv = vocTipPannelliFv;
           this.consumifv = consumifv;
    }


    /**
     * Gets the area value for this CcostEeFv.
     * 
     * @return area
     */
    public java.lang.Float getArea() {
        return area;
    }


    /**
     * Sets the area value for this CcostEeFv.
     * 
     * @param area
     */
    public void setArea(java.lang.Float area) {
        this.area = area;
    }


    /**
     * Gets the codiceCensImp value for this CcostEeFv.
     * 
     * @return codiceCensImp
     */
    public java.lang.String getCodiceCensImp() {
        return codiceCensImp;
    }


    /**
     * Sets the codiceCensImp value for this CcostEeFv.
     * 
     * @param codiceCensImp
     */
    public void setCodiceCensImp(java.lang.String codiceCensImp) {
        this.codiceCensImp = codiceCensImp;
    }


    /**
     * Gets the codicePod value for this CcostEeFv.
     * 
     * @return codicePod
     */
    public java.lang.String getCodicePod() {
        return codicePod;
    }


    /**
     * Sets the codicePod value for this CcostEeFv.
     * 
     * @param codicePod
     */
    public void setCodicePod(java.lang.String codicePod) {
        this.codicePod = codicePod;
    }


    /**
     * Gets the codiceRintracciabilita value for this CcostEeFv.
     * 
     * @return codiceRintracciabilita
     */
    public java.lang.String getCodiceRintracciabilita() {
        return codiceRintracciabilita;
    }


    /**
     * Sets the codiceRintracciabilita value for this CcostEeFv.
     * 
     * @param codiceRintracciabilita
     */
    public void setCodiceRintracciabilita(java.lang.String codiceRintracciabilita) {
        this.codiceRintracciabilita = codiceRintracciabilita;
    }


    /**
     * Gets the codiceSigraf value for this CcostEeFv.
     * 
     * @return codiceSigraf
     */
    public java.lang.String getCodiceSigraf() {
        return codiceSigraf;
    }


    /**
     * Sets the codiceSigraf value for this CcostEeFv.
     * 
     * @param codiceSigraf
     */
    public void setCodiceSigraf(java.lang.String codiceSigraf) {
        this.codiceSigraf = codiceSigraf;
    }


    /**
     * Gets the dataConferma value for this CcostEeFv.
     * 
     * @return dataConferma
     */
    public java.util.Calendar getDataConferma() {
        return dataConferma;
    }


    /**
     * Sets the dataConferma value for this CcostEeFv.
     * 
     * @param dataConferma
     */
    public void setDataConferma(java.util.Calendar dataConferma) {
        this.dataConferma = dataConferma;
    }


    /**
     * Gets the dataEntrataEsercizio value for this CcostEeFv.
     * 
     * @return dataEntrataEsercizio
     */
    public java.util.Calendar getDataEntrataEsercizio() {
        return dataEntrataEsercizio;
    }


    /**
     * Sets the dataEntrataEsercizio value for this CcostEeFv.
     * 
     * @param dataEntrataEsercizio
     */
    public void setDataEntrataEsercizio(java.util.Calendar dataEntrataEsercizio) {
        this.dataEntrataEsercizio = dataEntrataEsercizio;
    }


    /**
     * Gets the dataFine value for this CcostEeFv.
     * 
     * @return dataFine
     */
    public java.util.Calendar getDataFine() {
        return dataFine;
    }


    /**
     * Sets the dataFine value for this CcostEeFv.
     * 
     * @param dataFine
     */
    public void setDataFine(java.util.Calendar dataFine) {
        this.dataFine = dataFine;
    }


    /**
     * Gets the flagconnrete value for this CcostEeFv.
     * 
     * @return flagconnrete
     */
    public java.lang.Integer getFlagconnrete() {
        return flagconnrete;
    }


    /**
     * Sets the flagconnrete value for this CcostEeFv.
     * 
     * @param flagconnrete
     */
    public void setFlagconnrete(java.lang.Integer flagconnrete) {
        this.flagconnrete = flagconnrete;
    }


    /**
     * Gets the flaginseguimento value for this CcostEeFv.
     * 
     * @return flaginseguimento
     */
    public java.lang.Integer getFlaginseguimento() {
        return flaginseguimento;
    }


    /**
     * Sets the flaginseguimento value for this CcostEeFv.
     * 
     * @param flaginseguimento
     */
    public void setFlaginseguimento(java.lang.Integer flaginseguimento) {
        this.flaginseguimento = flaginseguimento;
    }


    /**
     * Gets the idCcost value for this CcostEeFv.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostEeFv.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the note value for this CcostEeFv.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this CcostEeFv.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the potenzaElettrica value for this CcostEeFv.
     * 
     * @return potenzaElettrica
     */
    public java.lang.Float getPotenzaElettrica() {
        return potenzaElettrica;
    }


    /**
     * Sets the potenzaElettrica value for this CcostEeFv.
     * 
     * @param potenzaElettrica
     */
    public void setPotenzaElettrica(java.lang.Float potenzaElettrica) {
        this.potenzaElettrica = potenzaElettrica;
    }


    /**
     * Gets the prodElettrica value for this CcostEeFv.
     * 
     * @return prodElettrica
     */
    public java.lang.Float getProdElettrica() {
        return prodElettrica;
    }


    /**
     * Sets the prodElettrica value for this CcostEeFv.
     * 
     * @param prodElettrica
     */
    public void setProdElettrica(java.lang.Float prodElettrica) {
        this.prodElettrica = prodElettrica;
    }


    /**
     * Gets the produzionifv value for this CcostEeFv.
     * 
     * @return produzionifv
     */
    public com.hyperborea.sira.ws.Produzionifv[] getProduzionifv() {
        return produzionifv;
    }


    /**
     * Sets the produzionifv value for this CcostEeFv.
     * 
     * @param produzionifv
     */
    public void setProduzionifv(com.hyperborea.sira.ws.Produzionifv[] produzionifv) {
        this.produzionifv = produzionifv;
    }

    public com.hyperborea.sira.ws.Produzionifv getProduzionifv(int i) {
        return this.produzionifv[i];
    }

    public void setProduzionifv(int i, com.hyperborea.sira.ws.Produzionifv _value) {
        this.produzionifv[i] = _value;
    }


    /**
     * Gets the supoccupata value for this CcostEeFv.
     * 
     * @return supoccupata
     */
    public java.lang.Float getSupoccupata() {
        return supoccupata;
    }


    /**
     * Sets the supoccupata value for this CcostEeFv.
     * 
     * @param supoccupata
     */
    public void setSupoccupata(java.lang.Float supoccupata) {
        this.supoccupata = supoccupata;
    }


    /**
     * Gets the titoloAutorizzativo value for this CcostEeFv.
     * 
     * @return titoloAutorizzativo
     */
    public java.lang.String getTitoloAutorizzativo() {
        return titoloAutorizzativo;
    }


    /**
     * Sets the titoloAutorizzativo value for this CcostEeFv.
     * 
     * @param titoloAutorizzativo
     */
    public void setTitoloAutorizzativo(java.lang.String titoloAutorizzativo) {
        this.titoloAutorizzativo = titoloAutorizzativo;
    }


    /**
     * Gets the vocImpIns value for this CcostEeFv.
     * 
     * @return vocImpIns
     */
    public com.hyperborea.sira.ws.VocImpIns getVocImpIns() {
        return vocImpIns;
    }


    /**
     * Sets the vocImpIns value for this CcostEeFv.
     * 
     * @param vocImpIns
     */
    public void setVocImpIns(com.hyperborea.sira.ws.VocImpIns vocImpIns) {
        this.vocImpIns = vocImpIns;
    }


    /**
     * Gets the vocIntArch value for this CcostEeFv.
     * 
     * @return vocIntArch
     */
    public com.hyperborea.sira.ws.VocIntArch getVocIntArch() {
        return vocIntArch;
    }


    /**
     * Sets the vocIntArch value for this CcostEeFv.
     * 
     * @param vocIntArch
     */
    public void setVocIntArch(com.hyperborea.sira.ws.VocIntArch vocIntArch) {
        this.vocIntArch = vocIntArch;
    }


    /**
     * Gets the vocPosizione value for this CcostEeFv.
     * 
     * @return vocPosizione
     */
    public com.hyperborea.sira.ws.VocPosizione getVocPosizione() {
        return vocPosizione;
    }


    /**
     * Sets the vocPosizione value for this CcostEeFv.
     * 
     * @param vocPosizione
     */
    public void setVocPosizione(com.hyperborea.sira.ws.VocPosizione vocPosizione) {
        this.vocPosizione = vocPosizione;
    }


    /**
     * Gets the vocTipContrattFv value for this CcostEeFv.
     * 
     * @return vocTipContrattFv
     */
    public com.hyperborea.sira.ws.VocTipContrattFv getVocTipContrattFv() {
        return vocTipContrattFv;
    }


    /**
     * Sets the vocTipContrattFv value for this CcostEeFv.
     * 
     * @param vocTipContrattFv
     */
    public void setVocTipContrattFv(com.hyperborea.sira.ws.VocTipContrattFv vocTipContrattFv) {
        this.vocTipContrattFv = vocTipContrattFv;
    }


    /**
     * Gets the vocTipPannelliFv value for this CcostEeFv.
     * 
     * @return vocTipPannelliFv
     */
    public com.hyperborea.sira.ws.VocTipPannelliFv getVocTipPannelliFv() {
        return vocTipPannelliFv;
    }


    /**
     * Sets the vocTipPannelliFv value for this CcostEeFv.
     * 
     * @param vocTipPannelliFv
     */
    public void setVocTipPannelliFv(com.hyperborea.sira.ws.VocTipPannelliFv vocTipPannelliFv) {
        this.vocTipPannelliFv = vocTipPannelliFv;
    }


    /**
     * Gets the consumifv value for this CcostEeFv.
     * 
     * @return consumifv
     */
    public com.hyperborea.sira.ws.Consumifv[] getConsumifv() {
        return consumifv;
    }


    /**
     * Sets the consumifv value for this CcostEeFv.
     * 
     * @param consumifv
     */
    public void setConsumifv(com.hyperborea.sira.ws.Consumifv[] consumifv) {
        this.consumifv = consumifv;
    }

    public com.hyperborea.sira.ws.Consumifv getConsumifv(int i) {
        return this.consumifv[i];
    }

    public void setConsumifv(int i, com.hyperborea.sira.ws.Consumifv _value) {
        this.consumifv[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostEeFv)) return false;
        CcostEeFv other = (CcostEeFv) obj;
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
            ((this.dataConferma==null && other.getDataConferma()==null) || 
             (this.dataConferma!=null &&
              this.dataConferma.equals(other.getDataConferma()))) &&
            ((this.dataEntrataEsercizio==null && other.getDataEntrataEsercizio()==null) || 
             (this.dataEntrataEsercizio!=null &&
              this.dataEntrataEsercizio.equals(other.getDataEntrataEsercizio()))) &&
            ((this.dataFine==null && other.getDataFine()==null) || 
             (this.dataFine!=null &&
              this.dataFine.equals(other.getDataFine()))) &&
            ((this.flagconnrete==null && other.getFlagconnrete()==null) || 
             (this.flagconnrete!=null &&
              this.flagconnrete.equals(other.getFlagconnrete()))) &&
            ((this.flaginseguimento==null && other.getFlaginseguimento()==null) || 
             (this.flaginseguimento!=null &&
              this.flaginseguimento.equals(other.getFlaginseguimento()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.potenzaElettrica==null && other.getPotenzaElettrica()==null) || 
             (this.potenzaElettrica!=null &&
              this.potenzaElettrica.equals(other.getPotenzaElettrica()))) &&
            ((this.prodElettrica==null && other.getProdElettrica()==null) || 
             (this.prodElettrica!=null &&
              this.prodElettrica.equals(other.getProdElettrica()))) &&
            ((this.produzionifv==null && other.getProduzionifv()==null) || 
             (this.produzionifv!=null &&
              java.util.Arrays.equals(this.produzionifv, other.getProduzionifv()))) &&
            ((this.supoccupata==null && other.getSupoccupata()==null) || 
             (this.supoccupata!=null &&
              this.supoccupata.equals(other.getSupoccupata()))) &&
            ((this.titoloAutorizzativo==null && other.getTitoloAutorizzativo()==null) || 
             (this.titoloAutorizzativo!=null &&
              this.titoloAutorizzativo.equals(other.getTitoloAutorizzativo()))) &&
            ((this.vocImpIns==null && other.getVocImpIns()==null) || 
             (this.vocImpIns!=null &&
              this.vocImpIns.equals(other.getVocImpIns()))) &&
            ((this.vocIntArch==null && other.getVocIntArch()==null) || 
             (this.vocIntArch!=null &&
              this.vocIntArch.equals(other.getVocIntArch()))) &&
            ((this.vocPosizione==null && other.getVocPosizione()==null) || 
             (this.vocPosizione!=null &&
              this.vocPosizione.equals(other.getVocPosizione()))) &&
            ((this.vocTipContrattFv==null && other.getVocTipContrattFv()==null) || 
             (this.vocTipContrattFv!=null &&
              this.vocTipContrattFv.equals(other.getVocTipContrattFv()))) &&
            ((this.vocTipPannelliFv==null && other.getVocTipPannelliFv()==null) || 
             (this.vocTipPannelliFv!=null &&
              this.vocTipPannelliFv.equals(other.getVocTipPannelliFv()))) &&
            ((this.consumifv==null && other.getConsumifv()==null) || 
             (this.consumifv!=null &&
              java.util.Arrays.equals(this.consumifv, other.getConsumifv())));
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
        if (getDataConferma() != null) {
            _hashCode += getDataConferma().hashCode();
        }
        if (getDataEntrataEsercizio() != null) {
            _hashCode += getDataEntrataEsercizio().hashCode();
        }
        if (getDataFine() != null) {
            _hashCode += getDataFine().hashCode();
        }
        if (getFlagconnrete() != null) {
            _hashCode += getFlagconnrete().hashCode();
        }
        if (getFlaginseguimento() != null) {
            _hashCode += getFlaginseguimento().hashCode();
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
        if (getProdElettrica() != null) {
            _hashCode += getProdElettrica().hashCode();
        }
        if (getProduzionifv() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProduzionifv());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProduzionifv(), i);
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
        if (getVocIntArch() != null) {
            _hashCode += getVocIntArch().hashCode();
        }
        if (getVocPosizione() != null) {
            _hashCode += getVocPosizione().hashCode();
        }
        if (getVocTipContrattFv() != null) {
            _hashCode += getVocTipContrattFv().hashCode();
        }
        if (getVocTipPannelliFv() != null) {
            _hashCode += getVocTipPannelliFv().hashCode();
        }
        if (getConsumifv() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConsumifv());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConsumifv(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostEeFv.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostEeFv"));
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
        elemField.setFieldName("flagconnrete");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagconnrete"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flaginseguimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flaginseguimento"));
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
        elemField.setFieldName("prodElettrica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodElettrica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("produzionifv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "produzionifv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "produzionifv"));
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
        elemField.setFieldName("vocIntArch");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocIntArch"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocIntArch"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocPosizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocPosizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocPosizione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipContrattFv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipContrattFv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipContrattFv"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipPannelliFv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipPannelliFv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipPannelliFv"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumifv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consumifv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumifv"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
