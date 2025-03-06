/**
 * Campione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Campione  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.util.Calendar dataAnalisi;

    private java.lang.String descrizione;

    private java.lang.Integer idCampione;

    private java.lang.String noteCampionamento;

    private java.lang.String noteConfezionamento;

    private java.lang.String noteDiConservazione;

    private java.lang.String noteTrasporto;

    private java.lang.String numRegistro;

    private java.lang.Integer numeroProgressivo;

    private java.util.Calendar oraPrelievo;

    private java.lang.Float profondita;

    private java.lang.Float profonditaFinale;

    private com.hyperborea.sira.ws.RapportiProva[] rapportiProvas;

    private java.lang.Integer suppletiva;

    private com.hyperborea.sira.ws.WsAttivitaEsternaRef wsAttivitaEsternaRef;

    private com.hyperborea.sira.ws.WsCcostRef wsCcostRef;

    private com.hyperborea.sira.ws.WsTipoCampioneRef wsTipoCampioneRef;

    public Campione() {
    }

    public Campione(
           java.util.Calendar dataAnalisi,
           java.lang.String descrizione,
           java.lang.Integer idCampione,
           java.lang.String noteCampionamento,
           java.lang.String noteConfezionamento,
           java.lang.String noteDiConservazione,
           java.lang.String noteTrasporto,
           java.lang.String numRegistro,
           java.lang.Integer numeroProgressivo,
           java.util.Calendar oraPrelievo,
           java.lang.Float profondita,
           java.lang.Float profonditaFinale,
           com.hyperborea.sira.ws.RapportiProva[] rapportiProvas,
           java.lang.Integer suppletiva,
           com.hyperborea.sira.ws.WsAttivitaEsternaRef wsAttivitaEsternaRef,
           com.hyperborea.sira.ws.WsCcostRef wsCcostRef,
           com.hyperborea.sira.ws.WsTipoCampioneRef wsTipoCampioneRef) {
        this.dataAnalisi = dataAnalisi;
        this.descrizione = descrizione;
        this.idCampione = idCampione;
        this.noteCampionamento = noteCampionamento;
        this.noteConfezionamento = noteConfezionamento;
        this.noteDiConservazione = noteDiConservazione;
        this.noteTrasporto = noteTrasporto;
        this.numRegistro = numRegistro;
        this.numeroProgressivo = numeroProgressivo;
        this.oraPrelievo = oraPrelievo;
        this.profondita = profondita;
        this.profonditaFinale = profonditaFinale;
        this.rapportiProvas = rapportiProvas;
        this.suppletiva = suppletiva;
        this.wsAttivitaEsternaRef = wsAttivitaEsternaRef;
        this.wsCcostRef = wsCcostRef;
        this.wsTipoCampioneRef = wsTipoCampioneRef;
    }


    /**
     * Gets the dataAnalisi value for this Campione.
     * 
     * @return dataAnalisi
     */
    public java.util.Calendar getDataAnalisi() {
        return dataAnalisi;
    }


    /**
     * Sets the dataAnalisi value for this Campione.
     * 
     * @param dataAnalisi
     */
    public void setDataAnalisi(java.util.Calendar dataAnalisi) {
        this.dataAnalisi = dataAnalisi;
    }


    /**
     * Gets the descrizione value for this Campione.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this Campione.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idCampione value for this Campione.
     * 
     * @return idCampione
     */
    public java.lang.Integer getIdCampione() {
        return idCampione;
    }


    /**
     * Sets the idCampione value for this Campione.
     * 
     * @param idCampione
     */
    public void setIdCampione(java.lang.Integer idCampione) {
        this.idCampione = idCampione;
    }


    /**
     * Gets the noteCampionamento value for this Campione.
     * 
     * @return noteCampionamento
     */
    public java.lang.String getNoteCampionamento() {
        return noteCampionamento;
    }


    /**
     * Sets the noteCampionamento value for this Campione.
     * 
     * @param noteCampionamento
     */
    public void setNoteCampionamento(java.lang.String noteCampionamento) {
        this.noteCampionamento = noteCampionamento;
    }


    /**
     * Gets the noteConfezionamento value for this Campione.
     * 
     * @return noteConfezionamento
     */
    public java.lang.String getNoteConfezionamento() {
        return noteConfezionamento;
    }


    /**
     * Sets the noteConfezionamento value for this Campione.
     * 
     * @param noteConfezionamento
     */
    public void setNoteConfezionamento(java.lang.String noteConfezionamento) {
        this.noteConfezionamento = noteConfezionamento;
    }


    /**
     * Gets the noteDiConservazione value for this Campione.
     * 
     * @return noteDiConservazione
     */
    public java.lang.String getNoteDiConservazione() {
        return noteDiConservazione;
    }


    /**
     * Sets the noteDiConservazione value for this Campione.
     * 
     * @param noteDiConservazione
     */
    public void setNoteDiConservazione(java.lang.String noteDiConservazione) {
        this.noteDiConservazione = noteDiConservazione;
    }


    /**
     * Gets the noteTrasporto value for this Campione.
     * 
     * @return noteTrasporto
     */
    public java.lang.String getNoteTrasporto() {
        return noteTrasporto;
    }


    /**
     * Sets the noteTrasporto value for this Campione.
     * 
     * @param noteTrasporto
     */
    public void setNoteTrasporto(java.lang.String noteTrasporto) {
        this.noteTrasporto = noteTrasporto;
    }


    /**
     * Gets the numRegistro value for this Campione.
     * 
     * @return numRegistro
     */
    public java.lang.String getNumRegistro() {
        return numRegistro;
    }


    /**
     * Sets the numRegistro value for this Campione.
     * 
     * @param numRegistro
     */
    public void setNumRegistro(java.lang.String numRegistro) {
        this.numRegistro = numRegistro;
    }


    /**
     * Gets the numeroProgressivo value for this Campione.
     * 
     * @return numeroProgressivo
     */
    public java.lang.Integer getNumeroProgressivo() {
        return numeroProgressivo;
    }


    /**
     * Sets the numeroProgressivo value for this Campione.
     * 
     * @param numeroProgressivo
     */
    public void setNumeroProgressivo(java.lang.Integer numeroProgressivo) {
        this.numeroProgressivo = numeroProgressivo;
    }


    /**
     * Gets the oraPrelievo value for this Campione.
     * 
     * @return oraPrelievo
     */
    public java.util.Calendar getOraPrelievo() {
        return oraPrelievo;
    }


    /**
     * Sets the oraPrelievo value for this Campione.
     * 
     * @param oraPrelievo
     */
    public void setOraPrelievo(java.util.Calendar oraPrelievo) {
        this.oraPrelievo = oraPrelievo;
    }


    /**
     * Gets the profondita value for this Campione.
     * 
     * @return profondita
     */
    public java.lang.Float getProfondita() {
        return profondita;
    }


    /**
     * Sets the profondita value for this Campione.
     * 
     * @param profondita
     */
    public void setProfondita(java.lang.Float profondita) {
        this.profondita = profondita;
    }


    /**
     * Gets the profonditaFinale value for this Campione.
     * 
     * @return profonditaFinale
     */
    public java.lang.Float getProfonditaFinale() {
        return profonditaFinale;
    }


    /**
     * Sets the profonditaFinale value for this Campione.
     * 
     * @param profonditaFinale
     */
    public void setProfonditaFinale(java.lang.Float profonditaFinale) {
        this.profonditaFinale = profonditaFinale;
    }


    /**
     * Gets the rapportiProvas value for this Campione.
     * 
     * @return rapportiProvas
     */
    public com.hyperborea.sira.ws.RapportiProva[] getRapportiProvas() {
        return rapportiProvas;
    }


    /**
     * Sets the rapportiProvas value for this Campione.
     * 
     * @param rapportiProvas
     */
    public void setRapportiProvas(com.hyperborea.sira.ws.RapportiProva[] rapportiProvas) {
        this.rapportiProvas = rapportiProvas;
    }

    public com.hyperborea.sira.ws.RapportiProva getRapportiProvas(int i) {
        return this.rapportiProvas[i];
    }

    public void setRapportiProvas(int i, com.hyperborea.sira.ws.RapportiProva _value) {
        this.rapportiProvas[i] = _value;
    }


    /**
     * Gets the suppletiva value for this Campione.
     * 
     * @return suppletiva
     */
    public java.lang.Integer getSuppletiva() {
        return suppletiva;
    }


    /**
     * Sets the suppletiva value for this Campione.
     * 
     * @param suppletiva
     */
    public void setSuppletiva(java.lang.Integer suppletiva) {
        this.suppletiva = suppletiva;
    }


    /**
     * Gets the wsAttivitaEsternaRef value for this Campione.
     * 
     * @return wsAttivitaEsternaRef
     */
    public com.hyperborea.sira.ws.WsAttivitaEsternaRef getWsAttivitaEsternaRef() {
        return wsAttivitaEsternaRef;
    }


    /**
     * Sets the wsAttivitaEsternaRef value for this Campione.
     * 
     * @param wsAttivitaEsternaRef
     */
    public void setWsAttivitaEsternaRef(com.hyperborea.sira.ws.WsAttivitaEsternaRef wsAttivitaEsternaRef) {
        this.wsAttivitaEsternaRef = wsAttivitaEsternaRef;
    }


    /**
     * Gets the wsCcostRef value for this Campione.
     * 
     * @return wsCcostRef
     */
    public com.hyperborea.sira.ws.WsCcostRef getWsCcostRef() {
        return wsCcostRef;
    }


    /**
     * Sets the wsCcostRef value for this Campione.
     * 
     * @param wsCcostRef
     */
    public void setWsCcostRef(com.hyperborea.sira.ws.WsCcostRef wsCcostRef) {
        this.wsCcostRef = wsCcostRef;
    }


    /**
     * Gets the wsTipoCampioneRef value for this Campione.
     * 
     * @return wsTipoCampioneRef
     */
    public com.hyperborea.sira.ws.WsTipoCampioneRef getWsTipoCampioneRef() {
        return wsTipoCampioneRef;
    }


    /**
     * Sets the wsTipoCampioneRef value for this Campione.
     * 
     * @param wsTipoCampioneRef
     */
    public void setWsTipoCampioneRef(com.hyperborea.sira.ws.WsTipoCampioneRef wsTipoCampioneRef) {
        this.wsTipoCampioneRef = wsTipoCampioneRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Campione)) return false;
        Campione other = (Campione) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.dataAnalisi==null && other.getDataAnalisi()==null) || 
             (this.dataAnalisi!=null &&
              this.dataAnalisi.equals(other.getDataAnalisi()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.idCampione==null && other.getIdCampione()==null) || 
             (this.idCampione!=null &&
              this.idCampione.equals(other.getIdCampione()))) &&
            ((this.noteCampionamento==null && other.getNoteCampionamento()==null) || 
             (this.noteCampionamento!=null &&
              this.noteCampionamento.equals(other.getNoteCampionamento()))) &&
            ((this.noteConfezionamento==null && other.getNoteConfezionamento()==null) || 
             (this.noteConfezionamento!=null &&
              this.noteConfezionamento.equals(other.getNoteConfezionamento()))) &&
            ((this.noteDiConservazione==null && other.getNoteDiConservazione()==null) || 
             (this.noteDiConservazione!=null &&
              this.noteDiConservazione.equals(other.getNoteDiConservazione()))) &&
            ((this.noteTrasporto==null && other.getNoteTrasporto()==null) || 
             (this.noteTrasporto!=null &&
              this.noteTrasporto.equals(other.getNoteTrasporto()))) &&
            ((this.numRegistro==null && other.getNumRegistro()==null) || 
             (this.numRegistro!=null &&
              this.numRegistro.equals(other.getNumRegistro()))) &&
            ((this.numeroProgressivo==null && other.getNumeroProgressivo()==null) || 
             (this.numeroProgressivo!=null &&
              this.numeroProgressivo.equals(other.getNumeroProgressivo()))) &&
            ((this.oraPrelievo==null && other.getOraPrelievo()==null) || 
             (this.oraPrelievo!=null &&
              this.oraPrelievo.equals(other.getOraPrelievo()))) &&
            ((this.profondita==null && other.getProfondita()==null) || 
             (this.profondita!=null &&
              this.profondita.equals(other.getProfondita()))) &&
            ((this.profonditaFinale==null && other.getProfonditaFinale()==null) || 
             (this.profonditaFinale!=null &&
              this.profonditaFinale.equals(other.getProfonditaFinale()))) &&
            ((this.rapportiProvas==null && other.getRapportiProvas()==null) || 
             (this.rapportiProvas!=null &&
              java.util.Arrays.equals(this.rapportiProvas, other.getRapportiProvas()))) &&
            ((this.suppletiva==null && other.getSuppletiva()==null) || 
             (this.suppletiva!=null &&
              this.suppletiva.equals(other.getSuppletiva()))) &&
            ((this.wsAttivitaEsternaRef==null && other.getWsAttivitaEsternaRef()==null) || 
             (this.wsAttivitaEsternaRef!=null &&
              this.wsAttivitaEsternaRef.equals(other.getWsAttivitaEsternaRef()))) &&
            ((this.wsCcostRef==null && other.getWsCcostRef()==null) || 
             (this.wsCcostRef!=null &&
              this.wsCcostRef.equals(other.getWsCcostRef()))) &&
            ((this.wsTipoCampioneRef==null && other.getWsTipoCampioneRef()==null) || 
             (this.wsTipoCampioneRef!=null &&
              this.wsTipoCampioneRef.equals(other.getWsTipoCampioneRef())));
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
        if (getDataAnalisi() != null) {
            _hashCode += getDataAnalisi().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getIdCampione() != null) {
            _hashCode += getIdCampione().hashCode();
        }
        if (getNoteCampionamento() != null) {
            _hashCode += getNoteCampionamento().hashCode();
        }
        if (getNoteConfezionamento() != null) {
            _hashCode += getNoteConfezionamento().hashCode();
        }
        if (getNoteDiConservazione() != null) {
            _hashCode += getNoteDiConservazione().hashCode();
        }
        if (getNoteTrasporto() != null) {
            _hashCode += getNoteTrasporto().hashCode();
        }
        if (getNumRegistro() != null) {
            _hashCode += getNumRegistro().hashCode();
        }
        if (getNumeroProgressivo() != null) {
            _hashCode += getNumeroProgressivo().hashCode();
        }
        if (getOraPrelievo() != null) {
            _hashCode += getOraPrelievo().hashCode();
        }
        if (getProfondita() != null) {
            _hashCode += getProfondita().hashCode();
        }
        if (getProfonditaFinale() != null) {
            _hashCode += getProfonditaFinale().hashCode();
        }
        if (getRapportiProvas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRapportiProvas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRapportiProvas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSuppletiva() != null) {
            _hashCode += getSuppletiva().hashCode();
        }
        if (getWsAttivitaEsternaRef() != null) {
            _hashCode += getWsAttivitaEsternaRef().hashCode();
        }
        if (getWsCcostRef() != null) {
            _hashCode += getWsCcostRef().hashCode();
        }
        if (getWsTipoCampioneRef() != null) {
            _hashCode += getWsTipoCampioneRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Campione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "campione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataAnalisi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataAnalisi"));
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
        elemField.setFieldName("idCampione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCampione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noteCampionamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "noteCampionamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noteConfezionamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "noteConfezionamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noteDiConservazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "noteDiConservazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noteTrasporto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "noteTrasporto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroProgressivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroProgressivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oraPrelievo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oraPrelievo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profondita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profondita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profonditaFinale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profonditaFinale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rapportiProvas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rapportiProvas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "rapportiProva"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suppletiva");
        elemField.setXmlName(new javax.xml.namespace.QName("", "suppletiva"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsAttivitaEsternaRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsAttivitaEsternaRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsAttivitaEsternaRef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsCcostRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsCcostRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCcostRef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsTipoCampioneRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsTipoCampioneRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTipoCampioneRef"));
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
