/**
 * CcostEeEo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostEeEo  implements java.io.Serializable {
    private java.lang.Float area;

    private java.lang.String codiceCensImp;

    private java.lang.String codicePod;

    private java.lang.String codiceRintracciabilita;

    private java.lang.String codiceSigraf;

    private java.util.Calendar dataConferma;

    private java.util.Calendar dataEntrataEsercizio;

    private java.util.Calendar dataFine;

    private java.lang.Integer flagconnrete;

    private java.lang.Integer idCcost;

    private java.lang.String note;

    private java.lang.Integer nroaerogen;

    private java.lang.Float potenzaElettrica;

    private java.lang.Float prodElettrica;

    private com.hyperborea.sira.ws.Produzionieo[] produzionieo;

    private java.lang.Float supoccupata;

    private java.lang.String titoloAutorizzativo;

    private com.hyperborea.sira.ws.VocTipAerogen vocTipAerogen;

    private com.hyperborea.sira.ws.VocTipContrattEo vocTipContrattEo;

    private com.hyperborea.sira.ws.Consumieo[] consumieo;

    public CcostEeEo() {
    }

    public CcostEeEo(
           java.lang.Float area,
           java.lang.String codiceCensImp,
           java.lang.String codicePod,
           java.lang.String codiceRintracciabilita,
           java.lang.String codiceSigraf,
           java.util.Calendar dataConferma,
           java.util.Calendar dataEntrataEsercizio,
           java.util.Calendar dataFine,
           java.lang.Integer flagconnrete,
           java.lang.Integer idCcost,
           java.lang.String note,
           java.lang.Integer nroaerogen,
           java.lang.Float potenzaElettrica,
           java.lang.Float prodElettrica,
           com.hyperborea.sira.ws.Produzionieo[] produzionieo,
           java.lang.Float supoccupata,
           java.lang.String titoloAutorizzativo,
           com.hyperborea.sira.ws.VocTipAerogen vocTipAerogen,
           com.hyperborea.sira.ws.VocTipContrattEo vocTipContrattEo,
           com.hyperborea.sira.ws.Consumieo[] consumieo) {
           this.area = area;
           this.codiceCensImp = codiceCensImp;
           this.codicePod = codicePod;
           this.codiceRintracciabilita = codiceRintracciabilita;
           this.codiceSigraf = codiceSigraf;
           this.dataConferma = dataConferma;
           this.dataEntrataEsercizio = dataEntrataEsercizio;
           this.dataFine = dataFine;
           this.flagconnrete = flagconnrete;
           this.idCcost = idCcost;
           this.note = note;
           this.nroaerogen = nroaerogen;
           this.potenzaElettrica = potenzaElettrica;
           this.prodElettrica = prodElettrica;
           this.produzionieo = produzionieo;
           this.supoccupata = supoccupata;
           this.titoloAutorizzativo = titoloAutorizzativo;
           this.vocTipAerogen = vocTipAerogen;
           this.vocTipContrattEo = vocTipContrattEo;
           this.consumieo = consumieo;
    }


    /**
     * Gets the area value for this CcostEeEo.
     * 
     * @return area
     */
    public java.lang.Float getArea() {
        return area;
    }


    /**
     * Sets the area value for this CcostEeEo.
     * 
     * @param area
     */
    public void setArea(java.lang.Float area) {
        this.area = area;
    }


    /**
     * Gets the codiceCensImp value for this CcostEeEo.
     * 
     * @return codiceCensImp
     */
    public java.lang.String getCodiceCensImp() {
        return codiceCensImp;
    }


    /**
     * Sets the codiceCensImp value for this CcostEeEo.
     * 
     * @param codiceCensImp
     */
    public void setCodiceCensImp(java.lang.String codiceCensImp) {
        this.codiceCensImp = codiceCensImp;
    }


    /**
     * Gets the codicePod value for this CcostEeEo.
     * 
     * @return codicePod
     */
    public java.lang.String getCodicePod() {
        return codicePod;
    }


    /**
     * Sets the codicePod value for this CcostEeEo.
     * 
     * @param codicePod
     */
    public void setCodicePod(java.lang.String codicePod) {
        this.codicePod = codicePod;
    }


    /**
     * Gets the codiceRintracciabilita value for this CcostEeEo.
     * 
     * @return codiceRintracciabilita
     */
    public java.lang.String getCodiceRintracciabilita() {
        return codiceRintracciabilita;
    }


    /**
     * Sets the codiceRintracciabilita value for this CcostEeEo.
     * 
     * @param codiceRintracciabilita
     */
    public void setCodiceRintracciabilita(java.lang.String codiceRintracciabilita) {
        this.codiceRintracciabilita = codiceRintracciabilita;
    }


    /**
     * Gets the codiceSigraf value for this CcostEeEo.
     * 
     * @return codiceSigraf
     */
    public java.lang.String getCodiceSigraf() {
        return codiceSigraf;
    }


    /**
     * Sets the codiceSigraf value for this CcostEeEo.
     * 
     * @param codiceSigraf
     */
    public void setCodiceSigraf(java.lang.String codiceSigraf) {
        this.codiceSigraf = codiceSigraf;
    }


    /**
     * Gets the dataConferma value for this CcostEeEo.
     * 
     * @return dataConferma
     */
    public java.util.Calendar getDataConferma() {
        return dataConferma;
    }


    /**
     * Sets the dataConferma value for this CcostEeEo.
     * 
     * @param dataConferma
     */
    public void setDataConferma(java.util.Calendar dataConferma) {
        this.dataConferma = dataConferma;
    }


    /**
     * Gets the dataEntrataEsercizio value for this CcostEeEo.
     * 
     * @return dataEntrataEsercizio
     */
    public java.util.Calendar getDataEntrataEsercizio() {
        return dataEntrataEsercizio;
    }


    /**
     * Sets the dataEntrataEsercizio value for this CcostEeEo.
     * 
     * @param dataEntrataEsercizio
     */
    public void setDataEntrataEsercizio(java.util.Calendar dataEntrataEsercizio) {
        this.dataEntrataEsercizio = dataEntrataEsercizio;
    }


    /**
     * Gets the dataFine value for this CcostEeEo.
     * 
     * @return dataFine
     */
    public java.util.Calendar getDataFine() {
        return dataFine;
    }


    /**
     * Sets the dataFine value for this CcostEeEo.
     * 
     * @param dataFine
     */
    public void setDataFine(java.util.Calendar dataFine) {
        this.dataFine = dataFine;
    }


    /**
     * Gets the flagconnrete value for this CcostEeEo.
     * 
     * @return flagconnrete
     */
    public java.lang.Integer getFlagconnrete() {
        return flagconnrete;
    }


    /**
     * Sets the flagconnrete value for this CcostEeEo.
     * 
     * @param flagconnrete
     */
    public void setFlagconnrete(java.lang.Integer flagconnrete) {
        this.flagconnrete = flagconnrete;
    }


    /**
     * Gets the idCcost value for this CcostEeEo.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostEeEo.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the note value for this CcostEeEo.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this CcostEeEo.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the nroaerogen value for this CcostEeEo.
     * 
     * @return nroaerogen
     */
    public java.lang.Integer getNroaerogen() {
        return nroaerogen;
    }


    /**
     * Sets the nroaerogen value for this CcostEeEo.
     * 
     * @param nroaerogen
     */
    public void setNroaerogen(java.lang.Integer nroaerogen) {
        this.nroaerogen = nroaerogen;
    }


    /**
     * Gets the potenzaElettrica value for this CcostEeEo.
     * 
     * @return potenzaElettrica
     */
    public java.lang.Float getPotenzaElettrica() {
        return potenzaElettrica;
    }


    /**
     * Sets the potenzaElettrica value for this CcostEeEo.
     * 
     * @param potenzaElettrica
     */
    public void setPotenzaElettrica(java.lang.Float potenzaElettrica) {
        this.potenzaElettrica = potenzaElettrica;
    }


    /**
     * Gets the prodElettrica value for this CcostEeEo.
     * 
     * @return prodElettrica
     */
    public java.lang.Float getProdElettrica() {
        return prodElettrica;
    }


    /**
     * Sets the prodElettrica value for this CcostEeEo.
     * 
     * @param prodElettrica
     */
    public void setProdElettrica(java.lang.Float prodElettrica) {
        this.prodElettrica = prodElettrica;
    }


    /**
     * Gets the produzionieo value for this CcostEeEo.
     * 
     * @return produzionieo
     */
    public com.hyperborea.sira.ws.Produzionieo[] getProduzionieo() {
        return produzionieo;
    }


    /**
     * Sets the produzionieo value for this CcostEeEo.
     * 
     * @param produzionieo
     */
    public void setProduzionieo(com.hyperborea.sira.ws.Produzionieo[] produzionieo) {
        this.produzionieo = produzionieo;
    }

    public com.hyperborea.sira.ws.Produzionieo getProduzionieo(int i) {
        return this.produzionieo[i];
    }

    public void setProduzionieo(int i, com.hyperborea.sira.ws.Produzionieo _value) {
        this.produzionieo[i] = _value;
    }


    /**
     * Gets the supoccupata value for this CcostEeEo.
     * 
     * @return supoccupata
     */
    public java.lang.Float getSupoccupata() {
        return supoccupata;
    }


    /**
     * Sets the supoccupata value for this CcostEeEo.
     * 
     * @param supoccupata
     */
    public void setSupoccupata(java.lang.Float supoccupata) {
        this.supoccupata = supoccupata;
    }


    /**
     * Gets the titoloAutorizzativo value for this CcostEeEo.
     * 
     * @return titoloAutorizzativo
     */
    public java.lang.String getTitoloAutorizzativo() {
        return titoloAutorizzativo;
    }


    /**
     * Sets the titoloAutorizzativo value for this CcostEeEo.
     * 
     * @param titoloAutorizzativo
     */
    public void setTitoloAutorizzativo(java.lang.String titoloAutorizzativo) {
        this.titoloAutorizzativo = titoloAutorizzativo;
    }


    /**
     * Gets the vocTipAerogen value for this CcostEeEo.
     * 
     * @return vocTipAerogen
     */
    public com.hyperborea.sira.ws.VocTipAerogen getVocTipAerogen() {
        return vocTipAerogen;
    }


    /**
     * Sets the vocTipAerogen value for this CcostEeEo.
     * 
     * @param vocTipAerogen
     */
    public void setVocTipAerogen(com.hyperborea.sira.ws.VocTipAerogen vocTipAerogen) {
        this.vocTipAerogen = vocTipAerogen;
    }


    /**
     * Gets the vocTipContrattEo value for this CcostEeEo.
     * 
     * @return vocTipContrattEo
     */
    public com.hyperborea.sira.ws.VocTipContrattEo getVocTipContrattEo() {
        return vocTipContrattEo;
    }


    /**
     * Sets the vocTipContrattEo value for this CcostEeEo.
     * 
     * @param vocTipContrattEo
     */
    public void setVocTipContrattEo(com.hyperborea.sira.ws.VocTipContrattEo vocTipContrattEo) {
        this.vocTipContrattEo = vocTipContrattEo;
    }


    /**
     * Gets the consumieo value for this CcostEeEo.
     * 
     * @return consumieo
     */
    public com.hyperborea.sira.ws.Consumieo[] getConsumieo() {
        return consumieo;
    }


    /**
     * Sets the consumieo value for this CcostEeEo.
     * 
     * @param consumieo
     */
    public void setConsumieo(com.hyperborea.sira.ws.Consumieo[] consumieo) {
        this.consumieo = consumieo;
    }

    public com.hyperborea.sira.ws.Consumieo getConsumieo(int i) {
        return this.consumieo[i];
    }

    public void setConsumieo(int i, com.hyperborea.sira.ws.Consumieo _value) {
        this.consumieo[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostEeEo)) return false;
        CcostEeEo other = (CcostEeEo) obj;
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
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.nroaerogen==null && other.getNroaerogen()==null) || 
             (this.nroaerogen!=null &&
              this.nroaerogen.equals(other.getNroaerogen()))) &&
            ((this.potenzaElettrica==null && other.getPotenzaElettrica()==null) || 
             (this.potenzaElettrica!=null &&
              this.potenzaElettrica.equals(other.getPotenzaElettrica()))) &&
            ((this.prodElettrica==null && other.getProdElettrica()==null) || 
             (this.prodElettrica!=null &&
              this.prodElettrica.equals(other.getProdElettrica()))) &&
            ((this.produzionieo==null && other.getProduzionieo()==null) || 
             (this.produzionieo!=null &&
              java.util.Arrays.equals(this.produzionieo, other.getProduzionieo()))) &&
            ((this.supoccupata==null && other.getSupoccupata()==null) || 
             (this.supoccupata!=null &&
              this.supoccupata.equals(other.getSupoccupata()))) &&
            ((this.titoloAutorizzativo==null && other.getTitoloAutorizzativo()==null) || 
             (this.titoloAutorizzativo!=null &&
              this.titoloAutorizzativo.equals(other.getTitoloAutorizzativo()))) &&
            ((this.vocTipAerogen==null && other.getVocTipAerogen()==null) || 
             (this.vocTipAerogen!=null &&
              this.vocTipAerogen.equals(other.getVocTipAerogen()))) &&
            ((this.vocTipContrattEo==null && other.getVocTipContrattEo()==null) || 
             (this.vocTipContrattEo!=null &&
              this.vocTipContrattEo.equals(other.getVocTipContrattEo()))) &&
            ((this.consumieo==null && other.getConsumieo()==null) || 
             (this.consumieo!=null &&
              java.util.Arrays.equals(this.consumieo, other.getConsumieo())));
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
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getNroaerogen() != null) {
            _hashCode += getNroaerogen().hashCode();
        }
        if (getPotenzaElettrica() != null) {
            _hashCode += getPotenzaElettrica().hashCode();
        }
        if (getProdElettrica() != null) {
            _hashCode += getProdElettrica().hashCode();
        }
        if (getProduzionieo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProduzionieo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProduzionieo(), i);
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
        if (getVocTipAerogen() != null) {
            _hashCode += getVocTipAerogen().hashCode();
        }
        if (getVocTipContrattEo() != null) {
            _hashCode += getVocTipContrattEo().hashCode();
        }
        if (getConsumieo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConsumieo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConsumieo(), i);
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
        new org.apache.axis.description.TypeDesc(CcostEeEo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostEeEo"));
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
        elemField.setFieldName("nroaerogen");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nroaerogen"));
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
        elemField.setFieldName("prodElettrica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodElettrica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("produzionieo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "produzionieo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "produzionieo"));
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
        elemField.setFieldName("vocTipAerogen");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipAerogen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipAerogen"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipContrattEo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipContrattEo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipContrattEo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumieo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consumieo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumieo"));
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
