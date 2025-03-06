/**
 * DichiarazioniAmbientali.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class DichiarazioniAmbientali  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.Allegati[] allegatis;

    private java.lang.Integer anno;

    private com.hyperborea.sira.ws.AttiDisposizioniAmm[] attiDisposizioniAmms;

    private com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst;

    private com.hyperborea.sira.ws.CaratterizzazioniOst[] caratterizzazioniOsts;

    private org.apache.axis.types.UnsignedShort convalidata;

    private java.util.Calendar dataDia;

    private java.util.Calendar dataProt;

    private java.lang.String descrizione;

    private com.hyperborea.sira.ws.DiaOst[] diaOsts;

    private com.hyperborea.sira.ws.FasiProcedimenti fasiProcedimenti;

    private java.lang.Integer idDia;

    private com.hyperborea.sira.ws.NatureDia natureDia;

    private java.lang.String note;

    private java.lang.String numProt;

    private java.lang.Integer periodica;

    private com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministratives;

    private com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi;

    private com.hyperborea.sira.ws.SoggettiFisici soggettiFisici;

    public DichiarazioniAmbientali() {
    }

    public DichiarazioniAmbientali(
           com.hyperborea.sira.ws.Allegati[] allegatis,
           java.lang.Integer anno,
           com.hyperborea.sira.ws.AttiDisposizioniAmm[] attiDisposizioniAmms,
           com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst,
           com.hyperborea.sira.ws.CaratterizzazioniOst[] caratterizzazioniOsts,
           org.apache.axis.types.UnsignedShort convalidata,
           java.util.Calendar dataDia,
           java.util.Calendar dataProt,
           java.lang.String descrizione,
           com.hyperborea.sira.ws.DiaOst[] diaOsts,
           com.hyperborea.sira.ws.FasiProcedimenti fasiProcedimenti,
           java.lang.Integer idDia,
           com.hyperborea.sira.ws.NatureDia natureDia,
           java.lang.String note,
           java.lang.String numProt,
           java.lang.Integer periodica,
           com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministratives,
           com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi,
           com.hyperborea.sira.ws.SoggettiFisici soggettiFisici) {
        this.allegatis = allegatis;
        this.anno = anno;
        this.attiDisposizioniAmms = attiDisposizioniAmms;
        this.caratterizzazioniOst = caratterizzazioniOst;
        this.caratterizzazioniOsts = caratterizzazioniOsts;
        this.convalidata = convalidata;
        this.dataDia = dataDia;
        this.dataProt = dataProt;
        this.descrizione = descrizione;
        this.diaOsts = diaOsts;
        this.fasiProcedimenti = fasiProcedimenti;
        this.idDia = idDia;
        this.natureDia = natureDia;
        this.note = note;
        this.numProt = numProt;
        this.periodica = periodica;
        this.praticheAmministratives = praticheAmministratives;
        this.riferimentiNormativi = riferimentiNormativi;
        this.soggettiFisici = soggettiFisici;
    }


    /**
     * Gets the allegatis value for this DichiarazioniAmbientali.
     * 
     * @return allegatis
     */
    public com.hyperborea.sira.ws.Allegati[] getAllegatis() {
        return allegatis;
    }


    /**
     * Sets the allegatis value for this DichiarazioniAmbientali.
     * 
     * @param allegatis
     */
    public void setAllegatis(com.hyperborea.sira.ws.Allegati[] allegatis) {
        this.allegatis = allegatis;
    }

    public com.hyperborea.sira.ws.Allegati getAllegatis(int i) {
        return this.allegatis[i];
    }

    public void setAllegatis(int i, com.hyperborea.sira.ws.Allegati _value) {
        this.allegatis[i] = _value;
    }


    /**
     * Gets the anno value for this DichiarazioniAmbientali.
     * 
     * @return anno
     */
    public java.lang.Integer getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this DichiarazioniAmbientali.
     * 
     * @param anno
     */
    public void setAnno(java.lang.Integer anno) {
        this.anno = anno;
    }


    /**
     * Gets the attiDisposizioniAmms value for this DichiarazioniAmbientali.
     * 
     * @return attiDisposizioniAmms
     */
    public com.hyperborea.sira.ws.AttiDisposizioniAmm[] getAttiDisposizioniAmms() {
        return attiDisposizioniAmms;
    }


    /**
     * Sets the attiDisposizioniAmms value for this DichiarazioniAmbientali.
     * 
     * @param attiDisposizioniAmms
     */
    public void setAttiDisposizioniAmms(com.hyperborea.sira.ws.AttiDisposizioniAmm[] attiDisposizioniAmms) {
        this.attiDisposizioniAmms = attiDisposizioniAmms;
    }

    public com.hyperborea.sira.ws.AttiDisposizioniAmm getAttiDisposizioniAmms(int i) {
        return this.attiDisposizioniAmms[i];
    }

    public void setAttiDisposizioniAmms(int i, com.hyperborea.sira.ws.AttiDisposizioniAmm _value) {
        this.attiDisposizioniAmms[i] = _value;
    }


    /**
     * Gets the caratterizzazioniOst value for this DichiarazioniAmbientali.
     * 
     * @return caratterizzazioniOst
     */
    public com.hyperborea.sira.ws.CaratterizzazioniOst getCaratterizzazioniOst() {
        return caratterizzazioniOst;
    }


    /**
     * Sets the caratterizzazioniOst value for this DichiarazioniAmbientali.
     * 
     * @param caratterizzazioniOst
     */
    public void setCaratterizzazioniOst(com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst) {
        this.caratterizzazioniOst = caratterizzazioniOst;
    }


    /**
     * Gets the caratterizzazioniOsts value for this DichiarazioniAmbientali.
     * 
     * @return caratterizzazioniOsts
     */
    public com.hyperborea.sira.ws.CaratterizzazioniOst[] getCaratterizzazioniOsts() {
        return caratterizzazioniOsts;
    }


    /**
     * Sets the caratterizzazioniOsts value for this DichiarazioniAmbientali.
     * 
     * @param caratterizzazioniOsts
     */
    public void setCaratterizzazioniOsts(com.hyperborea.sira.ws.CaratterizzazioniOst[] caratterizzazioniOsts) {
        this.caratterizzazioniOsts = caratterizzazioniOsts;
    }

    public com.hyperborea.sira.ws.CaratterizzazioniOst getCaratterizzazioniOsts(int i) {
        return this.caratterizzazioniOsts[i];
    }

    public void setCaratterizzazioniOsts(int i, com.hyperborea.sira.ws.CaratterizzazioniOst _value) {
        this.caratterizzazioniOsts[i] = _value;
    }


    /**
     * Gets the convalidata value for this DichiarazioniAmbientali.
     * 
     * @return convalidata
     */
    public org.apache.axis.types.UnsignedShort getConvalidata() {
        return convalidata;
    }


    /**
     * Sets the convalidata value for this DichiarazioniAmbientali.
     * 
     * @param convalidata
     */
    public void setConvalidata(org.apache.axis.types.UnsignedShort convalidata) {
        this.convalidata = convalidata;
    }


    /**
     * Gets the dataDia value for this DichiarazioniAmbientali.
     * 
     * @return dataDia
     */
    public java.util.Calendar getDataDia() {
        return dataDia;
    }


    /**
     * Sets the dataDia value for this DichiarazioniAmbientali.
     * 
     * @param dataDia
     */
    public void setDataDia(java.util.Calendar dataDia) {
        this.dataDia = dataDia;
    }


    /**
     * Gets the dataProt value for this DichiarazioniAmbientali.
     * 
     * @return dataProt
     */
    public java.util.Calendar getDataProt() {
        return dataProt;
    }


    /**
     * Sets the dataProt value for this DichiarazioniAmbientali.
     * 
     * @param dataProt
     */
    public void setDataProt(java.util.Calendar dataProt) {
        this.dataProt = dataProt;
    }


    /**
     * Gets the descrizione value for this DichiarazioniAmbientali.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this DichiarazioniAmbientali.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the diaOsts value for this DichiarazioniAmbientali.
     * 
     * @return diaOsts
     */
    public com.hyperborea.sira.ws.DiaOst[] getDiaOsts() {
        return diaOsts;
    }


    /**
     * Sets the diaOsts value for this DichiarazioniAmbientali.
     * 
     * @param diaOsts
     */
    public void setDiaOsts(com.hyperborea.sira.ws.DiaOst[] diaOsts) {
        this.diaOsts = diaOsts;
    }

    public com.hyperborea.sira.ws.DiaOst getDiaOsts(int i) {
        return this.diaOsts[i];
    }

    public void setDiaOsts(int i, com.hyperborea.sira.ws.DiaOst _value) {
        this.diaOsts[i] = _value;
    }


    /**
     * Gets the fasiProcedimenti value for this DichiarazioniAmbientali.
     * 
     * @return fasiProcedimenti
     */
    public com.hyperborea.sira.ws.FasiProcedimenti getFasiProcedimenti() {
        return fasiProcedimenti;
    }


    /**
     * Sets the fasiProcedimenti value for this DichiarazioniAmbientali.
     * 
     * @param fasiProcedimenti
     */
    public void setFasiProcedimenti(com.hyperborea.sira.ws.FasiProcedimenti fasiProcedimenti) {
        this.fasiProcedimenti = fasiProcedimenti;
    }


    /**
     * Gets the idDia value for this DichiarazioniAmbientali.
     * 
     * @return idDia
     */
    public java.lang.Integer getIdDia() {
        return idDia;
    }


    /**
     * Sets the idDia value for this DichiarazioniAmbientali.
     * 
     * @param idDia
     */
    public void setIdDia(java.lang.Integer idDia) {
        this.idDia = idDia;
    }


    /**
     * Gets the natureDia value for this DichiarazioniAmbientali.
     * 
     * @return natureDia
     */
    public com.hyperborea.sira.ws.NatureDia getNatureDia() {
        return natureDia;
    }


    /**
     * Sets the natureDia value for this DichiarazioniAmbientali.
     * 
     * @param natureDia
     */
    public void setNatureDia(com.hyperborea.sira.ws.NatureDia natureDia) {
        this.natureDia = natureDia;
    }


    /**
     * Gets the note value for this DichiarazioniAmbientali.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this DichiarazioniAmbientali.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the numProt value for this DichiarazioniAmbientali.
     * 
     * @return numProt
     */
    public java.lang.String getNumProt() {
        return numProt;
    }


    /**
     * Sets the numProt value for this DichiarazioniAmbientali.
     * 
     * @param numProt
     */
    public void setNumProt(java.lang.String numProt) {
        this.numProt = numProt;
    }


    /**
     * Gets the periodica value for this DichiarazioniAmbientali.
     * 
     * @return periodica
     */
    public java.lang.Integer getPeriodica() {
        return periodica;
    }


    /**
     * Sets the periodica value for this DichiarazioniAmbientali.
     * 
     * @param periodica
     */
    public void setPeriodica(java.lang.Integer periodica) {
        this.periodica = periodica;
    }


    /**
     * Gets the praticheAmministratives value for this DichiarazioniAmbientali.
     * 
     * @return praticheAmministratives
     */
    public com.hyperborea.sira.ws.PraticheAmministrative[] getPraticheAmministratives() {
        return praticheAmministratives;
    }


    /**
     * Sets the praticheAmministratives value for this DichiarazioniAmbientali.
     * 
     * @param praticheAmministratives
     */
    public void setPraticheAmministratives(com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministratives) {
        this.praticheAmministratives = praticheAmministratives;
    }

    public com.hyperborea.sira.ws.PraticheAmministrative getPraticheAmministratives(int i) {
        return this.praticheAmministratives[i];
    }

    public void setPraticheAmministratives(int i, com.hyperborea.sira.ws.PraticheAmministrative _value) {
        this.praticheAmministratives[i] = _value;
    }


    /**
     * Gets the riferimentiNormativi value for this DichiarazioniAmbientali.
     * 
     * @return riferimentiNormativi
     */
    public com.hyperborea.sira.ws.RiferimentiNormativi getRiferimentiNormativi() {
        return riferimentiNormativi;
    }


    /**
     * Sets the riferimentiNormativi value for this DichiarazioniAmbientali.
     * 
     * @param riferimentiNormativi
     */
    public void setRiferimentiNormativi(com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi) {
        this.riferimentiNormativi = riferimentiNormativi;
    }


    /**
     * Gets the soggettiFisici value for this DichiarazioniAmbientali.
     * 
     * @return soggettiFisici
     */
    public com.hyperborea.sira.ws.SoggettiFisici getSoggettiFisici() {
        return soggettiFisici;
    }


    /**
     * Sets the soggettiFisici value for this DichiarazioniAmbientali.
     * 
     * @param soggettiFisici
     */
    public void setSoggettiFisici(com.hyperborea.sira.ws.SoggettiFisici soggettiFisici) {
        this.soggettiFisici = soggettiFisici;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DichiarazioniAmbientali)) return false;
        DichiarazioniAmbientali other = (DichiarazioniAmbientali) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.allegatis==null && other.getAllegatis()==null) || 
             (this.allegatis!=null &&
              java.util.Arrays.equals(this.allegatis, other.getAllegatis()))) &&
            ((this.anno==null && other.getAnno()==null) || 
             (this.anno!=null &&
              this.anno.equals(other.getAnno()))) &&
            ((this.attiDisposizioniAmms==null && other.getAttiDisposizioniAmms()==null) || 
             (this.attiDisposizioniAmms!=null &&
              java.util.Arrays.equals(this.attiDisposizioniAmms, other.getAttiDisposizioniAmms()))) &&
            ((this.caratterizzazioniOst==null && other.getCaratterizzazioniOst()==null) || 
             (this.caratterizzazioniOst!=null &&
              this.caratterizzazioniOst.equals(other.getCaratterizzazioniOst()))) &&
            ((this.caratterizzazioniOsts==null && other.getCaratterizzazioniOsts()==null) || 
             (this.caratterizzazioniOsts!=null &&
              java.util.Arrays.equals(this.caratterizzazioniOsts, other.getCaratterizzazioniOsts()))) &&
            ((this.convalidata==null && other.getConvalidata()==null) || 
             (this.convalidata!=null &&
              this.convalidata.equals(other.getConvalidata()))) &&
            ((this.dataDia==null && other.getDataDia()==null) || 
             (this.dataDia!=null &&
              this.dataDia.equals(other.getDataDia()))) &&
            ((this.dataProt==null && other.getDataProt()==null) || 
             (this.dataProt!=null &&
              this.dataProt.equals(other.getDataProt()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.diaOsts==null && other.getDiaOsts()==null) || 
             (this.diaOsts!=null &&
              java.util.Arrays.equals(this.diaOsts, other.getDiaOsts()))) &&
            ((this.fasiProcedimenti==null && other.getFasiProcedimenti()==null) || 
             (this.fasiProcedimenti!=null &&
              this.fasiProcedimenti.equals(other.getFasiProcedimenti()))) &&
            ((this.idDia==null && other.getIdDia()==null) || 
             (this.idDia!=null &&
              this.idDia.equals(other.getIdDia()))) &&
            ((this.natureDia==null && other.getNatureDia()==null) || 
             (this.natureDia!=null &&
              this.natureDia.equals(other.getNatureDia()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.numProt==null && other.getNumProt()==null) || 
             (this.numProt!=null &&
              this.numProt.equals(other.getNumProt()))) &&
            ((this.periodica==null && other.getPeriodica()==null) || 
             (this.periodica!=null &&
              this.periodica.equals(other.getPeriodica()))) &&
            ((this.praticheAmministratives==null && other.getPraticheAmministratives()==null) || 
             (this.praticheAmministratives!=null &&
              java.util.Arrays.equals(this.praticheAmministratives, other.getPraticheAmministratives()))) &&
            ((this.riferimentiNormativi==null && other.getRiferimentiNormativi()==null) || 
             (this.riferimentiNormativi!=null &&
              this.riferimentiNormativi.equals(other.getRiferimentiNormativi()))) &&
            ((this.soggettiFisici==null && other.getSoggettiFisici()==null) || 
             (this.soggettiFisici!=null &&
              this.soggettiFisici.equals(other.getSoggettiFisici())));
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
        if (getAllegatis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAllegatis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAllegatis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAnno() != null) {
            _hashCode += getAnno().hashCode();
        }
        if (getAttiDisposizioniAmms() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAttiDisposizioniAmms());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAttiDisposizioniAmms(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCaratterizzazioniOst() != null) {
            _hashCode += getCaratterizzazioniOst().hashCode();
        }
        if (getCaratterizzazioniOsts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCaratterizzazioniOsts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCaratterizzazioniOsts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getConvalidata() != null) {
            _hashCode += getConvalidata().hashCode();
        }
        if (getDataDia() != null) {
            _hashCode += getDataDia().hashCode();
        }
        if (getDataProt() != null) {
            _hashCode += getDataProt().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getDiaOsts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDiaOsts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDiaOsts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFasiProcedimenti() != null) {
            _hashCode += getFasiProcedimenti().hashCode();
        }
        if (getIdDia() != null) {
            _hashCode += getIdDia().hashCode();
        }
        if (getNatureDia() != null) {
            _hashCode += getNatureDia().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getNumProt() != null) {
            _hashCode += getNumProt().hashCode();
        }
        if (getPeriodica() != null) {
            _hashCode += getPeriodica().hashCode();
        }
        if (getPraticheAmministratives() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPraticheAmministratives());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPraticheAmministratives(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRiferimentiNormativi() != null) {
            _hashCode += getRiferimentiNormativi().hashCode();
        }
        if (getSoggettiFisici() != null) {
            _hashCode += getSoggettiFisici().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DichiarazioniAmbientali.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "dichiarazioniAmbientali"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allegatis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "allegatis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "allegati"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attiDisposizioniAmms");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attiDisposizioniAmms"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attiDisposizioniAmm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caratterizzazioniOst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caratterizzazioniOst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "caratterizzazioniOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caratterizzazioniOsts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caratterizzazioniOsts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "caratterizzazioniOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("convalidata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "convalidata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataDia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataDia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataProt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataProt"));
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
        elemField.setFieldName("diaOsts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "diaOsts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "diaOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fasiProcedimenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fasiProcedimenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "fasiProcedimenti"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("natureDia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "natureDia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "natureDia"));
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
        elemField.setFieldName("numProt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numProt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "periodica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("praticheAmministratives");
        elemField.setXmlName(new javax.xml.namespace.QName("", "praticheAmministratives"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "praticheAmministrative"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riferimentiNormativi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riferimentiNormativi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "riferimentiNormativi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soggettiFisici");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soggettiFisici"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "soggettiFisici"));
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
