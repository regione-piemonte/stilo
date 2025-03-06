/**
 * CcostEeIe.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostEeIe  implements java.io.Serializable {
    private java.lang.String codiceCensImp;

    private java.lang.String codicePod;

    private java.lang.String codiceRintracciabilita;

    private java.lang.String codiceSigraf;

    private java.util.Calendar dataConferma;

    private java.util.Calendar dataEntrataEsercizio;

    private java.util.Calendar dataFine;

    private java.lang.Integer flagconnrete;

    private java.lang.Integer flagpompaggio;

    private java.lang.Integer idCcost;

    private java.lang.String note;

    private java.lang.Integer nropratica;

    private java.lang.Float potenzaElettrica;

    private java.lang.Float potenzaMedia;

    private java.lang.Float prodElettrica;

    private com.hyperborea.sira.ws.Produzioniie[] produzioniie;

    private java.lang.Float supoccupata;

    private java.lang.String titoloAutorizzativo;

    private com.hyperborea.sira.ws.VocTipContrattIe vocTipContrattIe;

    private com.hyperborea.sira.ws.VocTipImp vocTipImp;

    private com.hyperborea.sira.ws.Consumiie[] consumiie;

    public CcostEeIe() {
    }

    public CcostEeIe(
           java.lang.String codiceCensImp,
           java.lang.String codicePod,
           java.lang.String codiceRintracciabilita,
           java.lang.String codiceSigraf,
           java.util.Calendar dataConferma,
           java.util.Calendar dataEntrataEsercizio,
           java.util.Calendar dataFine,
           java.lang.Integer flagconnrete,
           java.lang.Integer flagpompaggio,
           java.lang.Integer idCcost,
           java.lang.String note,
           java.lang.Integer nropratica,
           java.lang.Float potenzaElettrica,
           java.lang.Float potenzaMedia,
           java.lang.Float prodElettrica,
           com.hyperborea.sira.ws.Produzioniie[] produzioniie,
           java.lang.Float supoccupata,
           java.lang.String titoloAutorizzativo,
           com.hyperborea.sira.ws.VocTipContrattIe vocTipContrattIe,
           com.hyperborea.sira.ws.VocTipImp vocTipImp,
           com.hyperborea.sira.ws.Consumiie[] consumiie) {
           this.codiceCensImp = codiceCensImp;
           this.codicePod = codicePod;
           this.codiceRintracciabilita = codiceRintracciabilita;
           this.codiceSigraf = codiceSigraf;
           this.dataConferma = dataConferma;
           this.dataEntrataEsercizio = dataEntrataEsercizio;
           this.dataFine = dataFine;
           this.flagconnrete = flagconnrete;
           this.flagpompaggio = flagpompaggio;
           this.idCcost = idCcost;
           this.note = note;
           this.nropratica = nropratica;
           this.potenzaElettrica = potenzaElettrica;
           this.potenzaMedia = potenzaMedia;
           this.prodElettrica = prodElettrica;
           this.produzioniie = produzioniie;
           this.supoccupata = supoccupata;
           this.titoloAutorizzativo = titoloAutorizzativo;
           this.vocTipContrattIe = vocTipContrattIe;
           this.vocTipImp = vocTipImp;
           this.consumiie = consumiie;
    }


    /**
     * Gets the codiceCensImp value for this CcostEeIe.
     * 
     * @return codiceCensImp
     */
    public java.lang.String getCodiceCensImp() {
        return codiceCensImp;
    }


    /**
     * Sets the codiceCensImp value for this CcostEeIe.
     * 
     * @param codiceCensImp
     */
    public void setCodiceCensImp(java.lang.String codiceCensImp) {
        this.codiceCensImp = codiceCensImp;
    }


    /**
     * Gets the codicePod value for this CcostEeIe.
     * 
     * @return codicePod
     */
    public java.lang.String getCodicePod() {
        return codicePod;
    }


    /**
     * Sets the codicePod value for this CcostEeIe.
     * 
     * @param codicePod
     */
    public void setCodicePod(java.lang.String codicePod) {
        this.codicePod = codicePod;
    }


    /**
     * Gets the codiceRintracciabilita value for this CcostEeIe.
     * 
     * @return codiceRintracciabilita
     */
    public java.lang.String getCodiceRintracciabilita() {
        return codiceRintracciabilita;
    }


    /**
     * Sets the codiceRintracciabilita value for this CcostEeIe.
     * 
     * @param codiceRintracciabilita
     */
    public void setCodiceRintracciabilita(java.lang.String codiceRintracciabilita) {
        this.codiceRintracciabilita = codiceRintracciabilita;
    }


    /**
     * Gets the codiceSigraf value for this CcostEeIe.
     * 
     * @return codiceSigraf
     */
    public java.lang.String getCodiceSigraf() {
        return codiceSigraf;
    }


    /**
     * Sets the codiceSigraf value for this CcostEeIe.
     * 
     * @param codiceSigraf
     */
    public void setCodiceSigraf(java.lang.String codiceSigraf) {
        this.codiceSigraf = codiceSigraf;
    }


    /**
     * Gets the dataConferma value for this CcostEeIe.
     * 
     * @return dataConferma
     */
    public java.util.Calendar getDataConferma() {
        return dataConferma;
    }


    /**
     * Sets the dataConferma value for this CcostEeIe.
     * 
     * @param dataConferma
     */
    public void setDataConferma(java.util.Calendar dataConferma) {
        this.dataConferma = dataConferma;
    }


    /**
     * Gets the dataEntrataEsercizio value for this CcostEeIe.
     * 
     * @return dataEntrataEsercizio
     */
    public java.util.Calendar getDataEntrataEsercizio() {
        return dataEntrataEsercizio;
    }


    /**
     * Sets the dataEntrataEsercizio value for this CcostEeIe.
     * 
     * @param dataEntrataEsercizio
     */
    public void setDataEntrataEsercizio(java.util.Calendar dataEntrataEsercizio) {
        this.dataEntrataEsercizio = dataEntrataEsercizio;
    }


    /**
     * Gets the dataFine value for this CcostEeIe.
     * 
     * @return dataFine
     */
    public java.util.Calendar getDataFine() {
        return dataFine;
    }


    /**
     * Sets the dataFine value for this CcostEeIe.
     * 
     * @param dataFine
     */
    public void setDataFine(java.util.Calendar dataFine) {
        this.dataFine = dataFine;
    }


    /**
     * Gets the flagconnrete value for this CcostEeIe.
     * 
     * @return flagconnrete
     */
    public java.lang.Integer getFlagconnrete() {
        return flagconnrete;
    }


    /**
     * Sets the flagconnrete value for this CcostEeIe.
     * 
     * @param flagconnrete
     */
    public void setFlagconnrete(java.lang.Integer flagconnrete) {
        this.flagconnrete = flagconnrete;
    }


    /**
     * Gets the flagpompaggio value for this CcostEeIe.
     * 
     * @return flagpompaggio
     */
    public java.lang.Integer getFlagpompaggio() {
        return flagpompaggio;
    }


    /**
     * Sets the flagpompaggio value for this CcostEeIe.
     * 
     * @param flagpompaggio
     */
    public void setFlagpompaggio(java.lang.Integer flagpompaggio) {
        this.flagpompaggio = flagpompaggio;
    }


    /**
     * Gets the idCcost value for this CcostEeIe.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostEeIe.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the note value for this CcostEeIe.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this CcostEeIe.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the nropratica value for this CcostEeIe.
     * 
     * @return nropratica
     */
    public java.lang.Integer getNropratica() {
        return nropratica;
    }


    /**
     * Sets the nropratica value for this CcostEeIe.
     * 
     * @param nropratica
     */
    public void setNropratica(java.lang.Integer nropratica) {
        this.nropratica = nropratica;
    }


    /**
     * Gets the potenzaElettrica value for this CcostEeIe.
     * 
     * @return potenzaElettrica
     */
    public java.lang.Float getPotenzaElettrica() {
        return potenzaElettrica;
    }


    /**
     * Sets the potenzaElettrica value for this CcostEeIe.
     * 
     * @param potenzaElettrica
     */
    public void setPotenzaElettrica(java.lang.Float potenzaElettrica) {
        this.potenzaElettrica = potenzaElettrica;
    }


    /**
     * Gets the potenzaMedia value for this CcostEeIe.
     * 
     * @return potenzaMedia
     */
    public java.lang.Float getPotenzaMedia() {
        return potenzaMedia;
    }


    /**
     * Sets the potenzaMedia value for this CcostEeIe.
     * 
     * @param potenzaMedia
     */
    public void setPotenzaMedia(java.lang.Float potenzaMedia) {
        this.potenzaMedia = potenzaMedia;
    }


    /**
     * Gets the prodElettrica value for this CcostEeIe.
     * 
     * @return prodElettrica
     */
    public java.lang.Float getProdElettrica() {
        return prodElettrica;
    }


    /**
     * Sets the prodElettrica value for this CcostEeIe.
     * 
     * @param prodElettrica
     */
    public void setProdElettrica(java.lang.Float prodElettrica) {
        this.prodElettrica = prodElettrica;
    }


    /**
     * Gets the produzioniie value for this CcostEeIe.
     * 
     * @return produzioniie
     */
    public com.hyperborea.sira.ws.Produzioniie[] getProduzioniie() {
        return produzioniie;
    }


    /**
     * Sets the produzioniie value for this CcostEeIe.
     * 
     * @param produzioniie
     */
    public void setProduzioniie(com.hyperborea.sira.ws.Produzioniie[] produzioniie) {
        this.produzioniie = produzioniie;
    }

    public com.hyperborea.sira.ws.Produzioniie getProduzioniie(int i) {
        return this.produzioniie[i];
    }

    public void setProduzioniie(int i, com.hyperborea.sira.ws.Produzioniie _value) {
        this.produzioniie[i] = _value;
    }


    /**
     * Gets the supoccupata value for this CcostEeIe.
     * 
     * @return supoccupata
     */
    public java.lang.Float getSupoccupata() {
        return supoccupata;
    }


    /**
     * Sets the supoccupata value for this CcostEeIe.
     * 
     * @param supoccupata
     */
    public void setSupoccupata(java.lang.Float supoccupata) {
        this.supoccupata = supoccupata;
    }


    /**
     * Gets the titoloAutorizzativo value for this CcostEeIe.
     * 
     * @return titoloAutorizzativo
     */
    public java.lang.String getTitoloAutorizzativo() {
        return titoloAutorizzativo;
    }


    /**
     * Sets the titoloAutorizzativo value for this CcostEeIe.
     * 
     * @param titoloAutorizzativo
     */
    public void setTitoloAutorizzativo(java.lang.String titoloAutorizzativo) {
        this.titoloAutorizzativo = titoloAutorizzativo;
    }


    /**
     * Gets the vocTipContrattIe value for this CcostEeIe.
     * 
     * @return vocTipContrattIe
     */
    public com.hyperborea.sira.ws.VocTipContrattIe getVocTipContrattIe() {
        return vocTipContrattIe;
    }


    /**
     * Sets the vocTipContrattIe value for this CcostEeIe.
     * 
     * @param vocTipContrattIe
     */
    public void setVocTipContrattIe(com.hyperborea.sira.ws.VocTipContrattIe vocTipContrattIe) {
        this.vocTipContrattIe = vocTipContrattIe;
    }


    /**
     * Gets the vocTipImp value for this CcostEeIe.
     * 
     * @return vocTipImp
     */
    public com.hyperborea.sira.ws.VocTipImp getVocTipImp() {
        return vocTipImp;
    }


    /**
     * Sets the vocTipImp value for this CcostEeIe.
     * 
     * @param vocTipImp
     */
    public void setVocTipImp(com.hyperborea.sira.ws.VocTipImp vocTipImp) {
        this.vocTipImp = vocTipImp;
    }


    /**
     * Gets the consumiie value for this CcostEeIe.
     * 
     * @return consumiie
     */
    public com.hyperborea.sira.ws.Consumiie[] getConsumiie() {
        return consumiie;
    }


    /**
     * Sets the consumiie value for this CcostEeIe.
     * 
     * @param consumiie
     */
    public void setConsumiie(com.hyperborea.sira.ws.Consumiie[] consumiie) {
        this.consumiie = consumiie;
    }

    public com.hyperborea.sira.ws.Consumiie getConsumiie(int i) {
        return this.consumiie[i];
    }

    public void setConsumiie(int i, com.hyperborea.sira.ws.Consumiie _value) {
        this.consumiie[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostEeIe)) return false;
        CcostEeIe other = (CcostEeIe) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
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
            ((this.flagpompaggio==null && other.getFlagpompaggio()==null) || 
             (this.flagpompaggio!=null &&
              this.flagpompaggio.equals(other.getFlagpompaggio()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.nropratica==null && other.getNropratica()==null) || 
             (this.nropratica!=null &&
              this.nropratica.equals(other.getNropratica()))) &&
            ((this.potenzaElettrica==null && other.getPotenzaElettrica()==null) || 
             (this.potenzaElettrica!=null &&
              this.potenzaElettrica.equals(other.getPotenzaElettrica()))) &&
            ((this.potenzaMedia==null && other.getPotenzaMedia()==null) || 
             (this.potenzaMedia!=null &&
              this.potenzaMedia.equals(other.getPotenzaMedia()))) &&
            ((this.prodElettrica==null && other.getProdElettrica()==null) || 
             (this.prodElettrica!=null &&
              this.prodElettrica.equals(other.getProdElettrica()))) &&
            ((this.produzioniie==null && other.getProduzioniie()==null) || 
             (this.produzioniie!=null &&
              java.util.Arrays.equals(this.produzioniie, other.getProduzioniie()))) &&
            ((this.supoccupata==null && other.getSupoccupata()==null) || 
             (this.supoccupata!=null &&
              this.supoccupata.equals(other.getSupoccupata()))) &&
            ((this.titoloAutorizzativo==null && other.getTitoloAutorizzativo()==null) || 
             (this.titoloAutorizzativo!=null &&
              this.titoloAutorizzativo.equals(other.getTitoloAutorizzativo()))) &&
            ((this.vocTipContrattIe==null && other.getVocTipContrattIe()==null) || 
             (this.vocTipContrattIe!=null &&
              this.vocTipContrattIe.equals(other.getVocTipContrattIe()))) &&
            ((this.vocTipImp==null && other.getVocTipImp()==null) || 
             (this.vocTipImp!=null &&
              this.vocTipImp.equals(other.getVocTipImp()))) &&
            ((this.consumiie==null && other.getConsumiie()==null) || 
             (this.consumiie!=null &&
              java.util.Arrays.equals(this.consumiie, other.getConsumiie())));
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
        if (getFlagpompaggio() != null) {
            _hashCode += getFlagpompaggio().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getNropratica() != null) {
            _hashCode += getNropratica().hashCode();
        }
        if (getPotenzaElettrica() != null) {
            _hashCode += getPotenzaElettrica().hashCode();
        }
        if (getPotenzaMedia() != null) {
            _hashCode += getPotenzaMedia().hashCode();
        }
        if (getProdElettrica() != null) {
            _hashCode += getProdElettrica().hashCode();
        }
        if (getProduzioniie() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProduzioniie());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProduzioniie(), i);
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
        if (getVocTipContrattIe() != null) {
            _hashCode += getVocTipContrattIe().hashCode();
        }
        if (getVocTipImp() != null) {
            _hashCode += getVocTipImp().hashCode();
        }
        if (getConsumiie() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConsumiie());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConsumiie(), i);
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
        new org.apache.axis.description.TypeDesc(CcostEeIe.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostEeIe"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("flagpompaggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagpompaggio"));
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
        elemField.setFieldName("nropratica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nropratica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("potenzaMedia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzaMedia"));
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
        elemField.setFieldName("produzioniie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "produzioniie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "produzioniie"));
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
        elemField.setFieldName("vocTipContrattIe");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipContrattIe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipContrattIe"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipImp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipImp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipImp"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumiie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consumiie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumiie"));
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
