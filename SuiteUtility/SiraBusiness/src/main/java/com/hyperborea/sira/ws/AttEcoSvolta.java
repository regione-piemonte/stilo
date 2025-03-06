/**
 * AttEcoSvolta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class AttEcoSvolta  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String ago;

    private java.lang.String apr;

    private com.hyperborea.sira.ws.AttTecConnessa[] attTecConnessas;

    private com.hyperborea.sira.ws.CapacitaProduttiva[] capacitaProduttivas;

    private com.hyperborea.sira.ws.CategoriaIppc categoriaIppc;

    private com.hyperborea.sira.ws.CategoriaNace categoriaNace;

    private com.hyperborea.sira.ws.CategoriaNose categoriaNose;

    private java.util.Calendar dataCessazione;

    private java.util.Calendar dataInizio;

    private java.lang.String descrizione;

    private java.lang.String dic;

    private com.hyperborea.sira.ws.FasiAttEco[] fasiAttEcos;

    private java.lang.String feb;

    private java.lang.String gen;

    private java.lang.String giu;

    private java.lang.Integer idAttEcoSvolta;

    private java.lang.String lug;

    private java.lang.String mag;

    private java.lang.String mar;

    private java.lang.String note;

    private java.lang.String nov;

    private java.lang.Integer numeroAddetti;

    private java.lang.String ott;

    private java.lang.String periodicitaAttiva;

    private java.lang.String sett;

    private com.hyperborea.sira.ws.VocTipologiaRIR vocTipologiaRIR;

    public AttEcoSvolta() {
    }

    public AttEcoSvolta(
           java.lang.String ago,
           java.lang.String apr,
           com.hyperborea.sira.ws.AttTecConnessa[] attTecConnessas,
           com.hyperborea.sira.ws.CapacitaProduttiva[] capacitaProduttivas,
           com.hyperborea.sira.ws.CategoriaIppc categoriaIppc,
           com.hyperborea.sira.ws.CategoriaNace categoriaNace,
           com.hyperborea.sira.ws.CategoriaNose categoriaNose,
           java.util.Calendar dataCessazione,
           java.util.Calendar dataInizio,
           java.lang.String descrizione,
           java.lang.String dic,
           com.hyperborea.sira.ws.FasiAttEco[] fasiAttEcos,
           java.lang.String feb,
           java.lang.String gen,
           java.lang.String giu,
           java.lang.Integer idAttEcoSvolta,
           java.lang.String lug,
           java.lang.String mag,
           java.lang.String mar,
           java.lang.String note,
           java.lang.String nov,
           java.lang.Integer numeroAddetti,
           java.lang.String ott,
           java.lang.String periodicitaAttiva,
           java.lang.String sett,
           com.hyperborea.sira.ws.VocTipologiaRIR vocTipologiaRIR) {
        this.ago = ago;
        this.apr = apr;
        this.attTecConnessas = attTecConnessas;
        this.capacitaProduttivas = capacitaProduttivas;
        this.categoriaIppc = categoriaIppc;
        this.categoriaNace = categoriaNace;
        this.categoriaNose = categoriaNose;
        this.dataCessazione = dataCessazione;
        this.dataInizio = dataInizio;
        this.descrizione = descrizione;
        this.dic = dic;
        this.fasiAttEcos = fasiAttEcos;
        this.feb = feb;
        this.gen = gen;
        this.giu = giu;
        this.idAttEcoSvolta = idAttEcoSvolta;
        this.lug = lug;
        this.mag = mag;
        this.mar = mar;
        this.note = note;
        this.nov = nov;
        this.numeroAddetti = numeroAddetti;
        this.ott = ott;
        this.periodicitaAttiva = periodicitaAttiva;
        this.sett = sett;
        this.vocTipologiaRIR = vocTipologiaRIR;
    }


    /**
     * Gets the ago value for this AttEcoSvolta.
     * 
     * @return ago
     */
    public java.lang.String getAgo() {
        return ago;
    }


    /**
     * Sets the ago value for this AttEcoSvolta.
     * 
     * @param ago
     */
    public void setAgo(java.lang.String ago) {
        this.ago = ago;
    }


    /**
     * Gets the apr value for this AttEcoSvolta.
     * 
     * @return apr
     */
    public java.lang.String getApr() {
        return apr;
    }


    /**
     * Sets the apr value for this AttEcoSvolta.
     * 
     * @param apr
     */
    public void setApr(java.lang.String apr) {
        this.apr = apr;
    }


    /**
     * Gets the attTecConnessas value for this AttEcoSvolta.
     * 
     * @return attTecConnessas
     */
    public com.hyperborea.sira.ws.AttTecConnessa[] getAttTecConnessas() {
        return attTecConnessas;
    }


    /**
     * Sets the attTecConnessas value for this AttEcoSvolta.
     * 
     * @param attTecConnessas
     */
    public void setAttTecConnessas(com.hyperborea.sira.ws.AttTecConnessa[] attTecConnessas) {
        this.attTecConnessas = attTecConnessas;
    }

    public com.hyperborea.sira.ws.AttTecConnessa getAttTecConnessas(int i) {
        return this.attTecConnessas[i];
    }

    public void setAttTecConnessas(int i, com.hyperborea.sira.ws.AttTecConnessa _value) {
        this.attTecConnessas[i] = _value;
    }


    /**
     * Gets the capacitaProduttivas value for this AttEcoSvolta.
     * 
     * @return capacitaProduttivas
     */
    public com.hyperborea.sira.ws.CapacitaProduttiva[] getCapacitaProduttivas() {
        return capacitaProduttivas;
    }


    /**
     * Sets the capacitaProduttivas value for this AttEcoSvolta.
     * 
     * @param capacitaProduttivas
     */
    public void setCapacitaProduttivas(com.hyperborea.sira.ws.CapacitaProduttiva[] capacitaProduttivas) {
        this.capacitaProduttivas = capacitaProduttivas;
    }

    public com.hyperborea.sira.ws.CapacitaProduttiva getCapacitaProduttivas(int i) {
        return this.capacitaProduttivas[i];
    }

    public void setCapacitaProduttivas(int i, com.hyperborea.sira.ws.CapacitaProduttiva _value) {
        this.capacitaProduttivas[i] = _value;
    }


    /**
     * Gets the categoriaIppc value for this AttEcoSvolta.
     * 
     * @return categoriaIppc
     */
    public com.hyperborea.sira.ws.CategoriaIppc getCategoriaIppc() {
        return categoriaIppc;
    }


    /**
     * Sets the categoriaIppc value for this AttEcoSvolta.
     * 
     * @param categoriaIppc
     */
    public void setCategoriaIppc(com.hyperborea.sira.ws.CategoriaIppc categoriaIppc) {
        this.categoriaIppc = categoriaIppc;
    }


    /**
     * Gets the categoriaNace value for this AttEcoSvolta.
     * 
     * @return categoriaNace
     */
    public com.hyperborea.sira.ws.CategoriaNace getCategoriaNace() {
        return categoriaNace;
    }


    /**
     * Sets the categoriaNace value for this AttEcoSvolta.
     * 
     * @param categoriaNace
     */
    public void setCategoriaNace(com.hyperborea.sira.ws.CategoriaNace categoriaNace) {
        this.categoriaNace = categoriaNace;
    }


    /**
     * Gets the categoriaNose value for this AttEcoSvolta.
     * 
     * @return categoriaNose
     */
    public com.hyperborea.sira.ws.CategoriaNose getCategoriaNose() {
        return categoriaNose;
    }


    /**
     * Sets the categoriaNose value for this AttEcoSvolta.
     * 
     * @param categoriaNose
     */
    public void setCategoriaNose(com.hyperborea.sira.ws.CategoriaNose categoriaNose) {
        this.categoriaNose = categoriaNose;
    }


    /**
     * Gets the dataCessazione value for this AttEcoSvolta.
     * 
     * @return dataCessazione
     */
    public java.util.Calendar getDataCessazione() {
        return dataCessazione;
    }


    /**
     * Sets the dataCessazione value for this AttEcoSvolta.
     * 
     * @param dataCessazione
     */
    public void setDataCessazione(java.util.Calendar dataCessazione) {
        this.dataCessazione = dataCessazione;
    }


    /**
     * Gets the dataInizio value for this AttEcoSvolta.
     * 
     * @return dataInizio
     */
    public java.util.Calendar getDataInizio() {
        return dataInizio;
    }


    /**
     * Sets the dataInizio value for this AttEcoSvolta.
     * 
     * @param dataInizio
     */
    public void setDataInizio(java.util.Calendar dataInizio) {
        this.dataInizio = dataInizio;
    }


    /**
     * Gets the descrizione value for this AttEcoSvolta.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this AttEcoSvolta.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the dic value for this AttEcoSvolta.
     * 
     * @return dic
     */
    public java.lang.String getDic() {
        return dic;
    }


    /**
     * Sets the dic value for this AttEcoSvolta.
     * 
     * @param dic
     */
    public void setDic(java.lang.String dic) {
        this.dic = dic;
    }


    /**
     * Gets the fasiAttEcos value for this AttEcoSvolta.
     * 
     * @return fasiAttEcos
     */
    public com.hyperborea.sira.ws.FasiAttEco[] getFasiAttEcos() {
        return fasiAttEcos;
    }


    /**
     * Sets the fasiAttEcos value for this AttEcoSvolta.
     * 
     * @param fasiAttEcos
     */
    public void setFasiAttEcos(com.hyperborea.sira.ws.FasiAttEco[] fasiAttEcos) {
        this.fasiAttEcos = fasiAttEcos;
    }

    public com.hyperborea.sira.ws.FasiAttEco getFasiAttEcos(int i) {
        return this.fasiAttEcos[i];
    }

    public void setFasiAttEcos(int i, com.hyperborea.sira.ws.FasiAttEco _value) {
        this.fasiAttEcos[i] = _value;
    }


    /**
     * Gets the feb value for this AttEcoSvolta.
     * 
     * @return feb
     */
    public java.lang.String getFeb() {
        return feb;
    }


    /**
     * Sets the feb value for this AttEcoSvolta.
     * 
     * @param feb
     */
    public void setFeb(java.lang.String feb) {
        this.feb = feb;
    }


    /**
     * Gets the gen value for this AttEcoSvolta.
     * 
     * @return gen
     */
    public java.lang.String getGen() {
        return gen;
    }


    /**
     * Sets the gen value for this AttEcoSvolta.
     * 
     * @param gen
     */
    public void setGen(java.lang.String gen) {
        this.gen = gen;
    }


    /**
     * Gets the giu value for this AttEcoSvolta.
     * 
     * @return giu
     */
    public java.lang.String getGiu() {
        return giu;
    }


    /**
     * Sets the giu value for this AttEcoSvolta.
     * 
     * @param giu
     */
    public void setGiu(java.lang.String giu) {
        this.giu = giu;
    }


    /**
     * Gets the idAttEcoSvolta value for this AttEcoSvolta.
     * 
     * @return idAttEcoSvolta
     */
    public java.lang.Integer getIdAttEcoSvolta() {
        return idAttEcoSvolta;
    }


    /**
     * Sets the idAttEcoSvolta value for this AttEcoSvolta.
     * 
     * @param idAttEcoSvolta
     */
    public void setIdAttEcoSvolta(java.lang.Integer idAttEcoSvolta) {
        this.idAttEcoSvolta = idAttEcoSvolta;
    }


    /**
     * Gets the lug value for this AttEcoSvolta.
     * 
     * @return lug
     */
    public java.lang.String getLug() {
        return lug;
    }


    /**
     * Sets the lug value for this AttEcoSvolta.
     * 
     * @param lug
     */
    public void setLug(java.lang.String lug) {
        this.lug = lug;
    }


    /**
     * Gets the mag value for this AttEcoSvolta.
     * 
     * @return mag
     */
    public java.lang.String getMag() {
        return mag;
    }


    /**
     * Sets the mag value for this AttEcoSvolta.
     * 
     * @param mag
     */
    public void setMag(java.lang.String mag) {
        this.mag = mag;
    }


    /**
     * Gets the mar value for this AttEcoSvolta.
     * 
     * @return mar
     */
    public java.lang.String getMar() {
        return mar;
    }


    /**
     * Sets the mar value for this AttEcoSvolta.
     * 
     * @param mar
     */
    public void setMar(java.lang.String mar) {
        this.mar = mar;
    }


    /**
     * Gets the note value for this AttEcoSvolta.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this AttEcoSvolta.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the nov value for this AttEcoSvolta.
     * 
     * @return nov
     */
    public java.lang.String getNov() {
        return nov;
    }


    /**
     * Sets the nov value for this AttEcoSvolta.
     * 
     * @param nov
     */
    public void setNov(java.lang.String nov) {
        this.nov = nov;
    }


    /**
     * Gets the numeroAddetti value for this AttEcoSvolta.
     * 
     * @return numeroAddetti
     */
    public java.lang.Integer getNumeroAddetti() {
        return numeroAddetti;
    }


    /**
     * Sets the numeroAddetti value for this AttEcoSvolta.
     * 
     * @param numeroAddetti
     */
    public void setNumeroAddetti(java.lang.Integer numeroAddetti) {
        this.numeroAddetti = numeroAddetti;
    }


    /**
     * Gets the ott value for this AttEcoSvolta.
     * 
     * @return ott
     */
    public java.lang.String getOtt() {
        return ott;
    }


    /**
     * Sets the ott value for this AttEcoSvolta.
     * 
     * @param ott
     */
    public void setOtt(java.lang.String ott) {
        this.ott = ott;
    }


    /**
     * Gets the periodicitaAttiva value for this AttEcoSvolta.
     * 
     * @return periodicitaAttiva
     */
    public java.lang.String getPeriodicitaAttiva() {
        return periodicitaAttiva;
    }


    /**
     * Sets the periodicitaAttiva value for this AttEcoSvolta.
     * 
     * @param periodicitaAttiva
     */
    public void setPeriodicitaAttiva(java.lang.String periodicitaAttiva) {
        this.periodicitaAttiva = periodicitaAttiva;
    }


    /**
     * Gets the sett value for this AttEcoSvolta.
     * 
     * @return sett
     */
    public java.lang.String getSett() {
        return sett;
    }


    /**
     * Sets the sett value for this AttEcoSvolta.
     * 
     * @param sett
     */
    public void setSett(java.lang.String sett) {
        this.sett = sett;
    }


    /**
     * Gets the vocTipologiaRIR value for this AttEcoSvolta.
     * 
     * @return vocTipologiaRIR
     */
    public com.hyperborea.sira.ws.VocTipologiaRIR getVocTipologiaRIR() {
        return vocTipologiaRIR;
    }


    /**
     * Sets the vocTipologiaRIR value for this AttEcoSvolta.
     * 
     * @param vocTipologiaRIR
     */
    public void setVocTipologiaRIR(com.hyperborea.sira.ws.VocTipologiaRIR vocTipologiaRIR) {
        this.vocTipologiaRIR = vocTipologiaRIR;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AttEcoSvolta)) return false;
        AttEcoSvolta other = (AttEcoSvolta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.ago==null && other.getAgo()==null) || 
             (this.ago!=null &&
              this.ago.equals(other.getAgo()))) &&
            ((this.apr==null && other.getApr()==null) || 
             (this.apr!=null &&
              this.apr.equals(other.getApr()))) &&
            ((this.attTecConnessas==null && other.getAttTecConnessas()==null) || 
             (this.attTecConnessas!=null &&
              java.util.Arrays.equals(this.attTecConnessas, other.getAttTecConnessas()))) &&
            ((this.capacitaProduttivas==null && other.getCapacitaProduttivas()==null) || 
             (this.capacitaProduttivas!=null &&
              java.util.Arrays.equals(this.capacitaProduttivas, other.getCapacitaProduttivas()))) &&
            ((this.categoriaIppc==null && other.getCategoriaIppc()==null) || 
             (this.categoriaIppc!=null &&
              this.categoriaIppc.equals(other.getCategoriaIppc()))) &&
            ((this.categoriaNace==null && other.getCategoriaNace()==null) || 
             (this.categoriaNace!=null &&
              this.categoriaNace.equals(other.getCategoriaNace()))) &&
            ((this.categoriaNose==null && other.getCategoriaNose()==null) || 
             (this.categoriaNose!=null &&
              this.categoriaNose.equals(other.getCategoriaNose()))) &&
            ((this.dataCessazione==null && other.getDataCessazione()==null) || 
             (this.dataCessazione!=null &&
              this.dataCessazione.equals(other.getDataCessazione()))) &&
            ((this.dataInizio==null && other.getDataInizio()==null) || 
             (this.dataInizio!=null &&
              this.dataInizio.equals(other.getDataInizio()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.dic==null && other.getDic()==null) || 
             (this.dic!=null &&
              this.dic.equals(other.getDic()))) &&
            ((this.fasiAttEcos==null && other.getFasiAttEcos()==null) || 
             (this.fasiAttEcos!=null &&
              java.util.Arrays.equals(this.fasiAttEcos, other.getFasiAttEcos()))) &&
            ((this.feb==null && other.getFeb()==null) || 
             (this.feb!=null &&
              this.feb.equals(other.getFeb()))) &&
            ((this.gen==null && other.getGen()==null) || 
             (this.gen!=null &&
              this.gen.equals(other.getGen()))) &&
            ((this.giu==null && other.getGiu()==null) || 
             (this.giu!=null &&
              this.giu.equals(other.getGiu()))) &&
            ((this.idAttEcoSvolta==null && other.getIdAttEcoSvolta()==null) || 
             (this.idAttEcoSvolta!=null &&
              this.idAttEcoSvolta.equals(other.getIdAttEcoSvolta()))) &&
            ((this.lug==null && other.getLug()==null) || 
             (this.lug!=null &&
              this.lug.equals(other.getLug()))) &&
            ((this.mag==null && other.getMag()==null) || 
             (this.mag!=null &&
              this.mag.equals(other.getMag()))) &&
            ((this.mar==null && other.getMar()==null) || 
             (this.mar!=null &&
              this.mar.equals(other.getMar()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.nov==null && other.getNov()==null) || 
             (this.nov!=null &&
              this.nov.equals(other.getNov()))) &&
            ((this.numeroAddetti==null && other.getNumeroAddetti()==null) || 
             (this.numeroAddetti!=null &&
              this.numeroAddetti.equals(other.getNumeroAddetti()))) &&
            ((this.ott==null && other.getOtt()==null) || 
             (this.ott!=null &&
              this.ott.equals(other.getOtt()))) &&
            ((this.periodicitaAttiva==null && other.getPeriodicitaAttiva()==null) || 
             (this.periodicitaAttiva!=null &&
              this.periodicitaAttiva.equals(other.getPeriodicitaAttiva()))) &&
            ((this.sett==null && other.getSett()==null) || 
             (this.sett!=null &&
              this.sett.equals(other.getSett()))) &&
            ((this.vocTipologiaRIR==null && other.getVocTipologiaRIR()==null) || 
             (this.vocTipologiaRIR!=null &&
              this.vocTipologiaRIR.equals(other.getVocTipologiaRIR())));
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
        if (getAgo() != null) {
            _hashCode += getAgo().hashCode();
        }
        if (getApr() != null) {
            _hashCode += getApr().hashCode();
        }
        if (getAttTecConnessas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAttTecConnessas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAttTecConnessas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCapacitaProduttivas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCapacitaProduttivas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCapacitaProduttivas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCategoriaIppc() != null) {
            _hashCode += getCategoriaIppc().hashCode();
        }
        if (getCategoriaNace() != null) {
            _hashCode += getCategoriaNace().hashCode();
        }
        if (getCategoriaNose() != null) {
            _hashCode += getCategoriaNose().hashCode();
        }
        if (getDataCessazione() != null) {
            _hashCode += getDataCessazione().hashCode();
        }
        if (getDataInizio() != null) {
            _hashCode += getDataInizio().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getDic() != null) {
            _hashCode += getDic().hashCode();
        }
        if (getFasiAttEcos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFasiAttEcos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFasiAttEcos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFeb() != null) {
            _hashCode += getFeb().hashCode();
        }
        if (getGen() != null) {
            _hashCode += getGen().hashCode();
        }
        if (getGiu() != null) {
            _hashCode += getGiu().hashCode();
        }
        if (getIdAttEcoSvolta() != null) {
            _hashCode += getIdAttEcoSvolta().hashCode();
        }
        if (getLug() != null) {
            _hashCode += getLug().hashCode();
        }
        if (getMag() != null) {
            _hashCode += getMag().hashCode();
        }
        if (getMar() != null) {
            _hashCode += getMar().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getNov() != null) {
            _hashCode += getNov().hashCode();
        }
        if (getNumeroAddetti() != null) {
            _hashCode += getNumeroAddetti().hashCode();
        }
        if (getOtt() != null) {
            _hashCode += getOtt().hashCode();
        }
        if (getPeriodicitaAttiva() != null) {
            _hashCode += getPeriodicitaAttiva().hashCode();
        }
        if (getSett() != null) {
            _hashCode += getSett().hashCode();
        }
        if (getVocTipologiaRIR() != null) {
            _hashCode += getVocTipologiaRIR().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AttEcoSvolta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attEcoSvolta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("apr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "apr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attTecConnessas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attTecConnessas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attTecConnessa"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacitaProduttivas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capacitaProduttivas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "capacitaProduttiva"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoriaIppc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "categoriaIppc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "categoriaIppc"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoriaNace");
        elemField.setXmlName(new javax.xml.namespace.QName("", "categoriaNace"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "categoriaNace"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoriaNose");
        elemField.setXmlName(new javax.xml.namespace.QName("", "categoriaNose"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "categoriaNose"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataCessazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataCessazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dic");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dic"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fasiAttEcos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fasiAttEcos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "fasiAttEco"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("feb");
        elemField.setXmlName(new javax.xml.namespace.QName("", "feb"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gen");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("giu");
        elemField.setXmlName(new javax.xml.namespace.QName("", "giu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAttEcoSvolta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idAttEcoSvolta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lug");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lug"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mar");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mar"));
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
        elemField.setFieldName("nov");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nov"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroAddetti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroAddetti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ott");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ott"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodicitaAttiva");
        elemField.setXmlName(new javax.xml.namespace.QName("", "periodicitaAttiva"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sett");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sett"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaRIR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaRIR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaRIR"));
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
