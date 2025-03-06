/**
 * Attivita.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Attivita  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.Campione[] campiones;

    private java.util.Calendar dataFine;

    private java.util.Calendar dataFineEffettiva;

    private java.util.Calendar dataInizio;

    private java.util.Calendar dataInizioEffettiva;

    private java.util.Calendar dataVerbale;

    private java.lang.String fuoriSede;

    private java.lang.Integer idAttivita;

    private com.hyperborea.sira.ws.MisureAgentifisici[] misureAgentifisicis;

    private java.lang.String note;

    private com.hyperborea.sira.ws.PaAttivita[] paAttivitas;

    private com.hyperborea.sira.ws.PersonaleAttivita[] personaleAttivitas;

    private com.hyperborea.sira.ws.TipologieAttivita tipologieAttivita;

    private java.lang.String verbaleNr;

    public Attivita() {
    }

    public Attivita(
           com.hyperborea.sira.ws.Campione[] campiones,
           java.util.Calendar dataFine,
           java.util.Calendar dataFineEffettiva,
           java.util.Calendar dataInizio,
           java.util.Calendar dataInizioEffettiva,
           java.util.Calendar dataVerbale,
           java.lang.String fuoriSede,
           java.lang.Integer idAttivita,
           com.hyperborea.sira.ws.MisureAgentifisici[] misureAgentifisicis,
           java.lang.String note,
           com.hyperborea.sira.ws.PaAttivita[] paAttivitas,
           com.hyperborea.sira.ws.PersonaleAttivita[] personaleAttivitas,
           com.hyperborea.sira.ws.TipologieAttivita tipologieAttivita,
           java.lang.String verbaleNr) {
        this.campiones = campiones;
        this.dataFine = dataFine;
        this.dataFineEffettiva = dataFineEffettiva;
        this.dataInizio = dataInizio;
        this.dataInizioEffettiva = dataInizioEffettiva;
        this.dataVerbale = dataVerbale;
        this.fuoriSede = fuoriSede;
        this.idAttivita = idAttivita;
        this.misureAgentifisicis = misureAgentifisicis;
        this.note = note;
        this.paAttivitas = paAttivitas;
        this.personaleAttivitas = personaleAttivitas;
        this.tipologieAttivita = tipologieAttivita;
        this.verbaleNr = verbaleNr;
    }


    /**
     * Gets the campiones value for this Attivita.
     * 
     * @return campiones
     */
    public com.hyperborea.sira.ws.Campione[] getCampiones() {
        return campiones;
    }


    /**
     * Sets the campiones value for this Attivita.
     * 
     * @param campiones
     */
    public void setCampiones(com.hyperborea.sira.ws.Campione[] campiones) {
        this.campiones = campiones;
    }

    public com.hyperborea.sira.ws.Campione getCampiones(int i) {
        return this.campiones[i];
    }

    public void setCampiones(int i, com.hyperborea.sira.ws.Campione _value) {
        this.campiones[i] = _value;
    }


    /**
     * Gets the dataFine value for this Attivita.
     * 
     * @return dataFine
     */
    public java.util.Calendar getDataFine() {
        return dataFine;
    }


    /**
     * Sets the dataFine value for this Attivita.
     * 
     * @param dataFine
     */
    public void setDataFine(java.util.Calendar dataFine) {
        this.dataFine = dataFine;
    }


    /**
     * Gets the dataFineEffettiva value for this Attivita.
     * 
     * @return dataFineEffettiva
     */
    public java.util.Calendar getDataFineEffettiva() {
        return dataFineEffettiva;
    }


    /**
     * Sets the dataFineEffettiva value for this Attivita.
     * 
     * @param dataFineEffettiva
     */
    public void setDataFineEffettiva(java.util.Calendar dataFineEffettiva) {
        this.dataFineEffettiva = dataFineEffettiva;
    }


    /**
     * Gets the dataInizio value for this Attivita.
     * 
     * @return dataInizio
     */
    public java.util.Calendar getDataInizio() {
        return dataInizio;
    }


    /**
     * Sets the dataInizio value for this Attivita.
     * 
     * @param dataInizio
     */
    public void setDataInizio(java.util.Calendar dataInizio) {
        this.dataInizio = dataInizio;
    }


    /**
     * Gets the dataInizioEffettiva value for this Attivita.
     * 
     * @return dataInizioEffettiva
     */
    public java.util.Calendar getDataInizioEffettiva() {
        return dataInizioEffettiva;
    }


    /**
     * Sets the dataInizioEffettiva value for this Attivita.
     * 
     * @param dataInizioEffettiva
     */
    public void setDataInizioEffettiva(java.util.Calendar dataInizioEffettiva) {
        this.dataInizioEffettiva = dataInizioEffettiva;
    }


    /**
     * Gets the dataVerbale value for this Attivita.
     * 
     * @return dataVerbale
     */
    public java.util.Calendar getDataVerbale() {
        return dataVerbale;
    }


    /**
     * Sets the dataVerbale value for this Attivita.
     * 
     * @param dataVerbale
     */
    public void setDataVerbale(java.util.Calendar dataVerbale) {
        this.dataVerbale = dataVerbale;
    }


    /**
     * Gets the fuoriSede value for this Attivita.
     * 
     * @return fuoriSede
     */
    public java.lang.String getFuoriSede() {
        return fuoriSede;
    }


    /**
     * Sets the fuoriSede value for this Attivita.
     * 
     * @param fuoriSede
     */
    public void setFuoriSede(java.lang.String fuoriSede) {
        this.fuoriSede = fuoriSede;
    }


    /**
     * Gets the idAttivita value for this Attivita.
     * 
     * @return idAttivita
     */
    public java.lang.Integer getIdAttivita() {
        return idAttivita;
    }


    /**
     * Sets the idAttivita value for this Attivita.
     * 
     * @param idAttivita
     */
    public void setIdAttivita(java.lang.Integer idAttivita) {
        this.idAttivita = idAttivita;
    }


    /**
     * Gets the misureAgentifisicis value for this Attivita.
     * 
     * @return misureAgentifisicis
     */
    public com.hyperborea.sira.ws.MisureAgentifisici[] getMisureAgentifisicis() {
        return misureAgentifisicis;
    }


    /**
     * Sets the misureAgentifisicis value for this Attivita.
     * 
     * @param misureAgentifisicis
     */
    public void setMisureAgentifisicis(com.hyperborea.sira.ws.MisureAgentifisici[] misureAgentifisicis) {
        this.misureAgentifisicis = misureAgentifisicis;
    }

    public com.hyperborea.sira.ws.MisureAgentifisici getMisureAgentifisicis(int i) {
        return this.misureAgentifisicis[i];
    }

    public void setMisureAgentifisicis(int i, com.hyperborea.sira.ws.MisureAgentifisici _value) {
        this.misureAgentifisicis[i] = _value;
    }


    /**
     * Gets the note value for this Attivita.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this Attivita.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the paAttivitas value for this Attivita.
     * 
     * @return paAttivitas
     */
    public com.hyperborea.sira.ws.PaAttivita[] getPaAttivitas() {
        return paAttivitas;
    }


    /**
     * Sets the paAttivitas value for this Attivita.
     * 
     * @param paAttivitas
     */
    public void setPaAttivitas(com.hyperborea.sira.ws.PaAttivita[] paAttivitas) {
        this.paAttivitas = paAttivitas;
    }

    public com.hyperborea.sira.ws.PaAttivita getPaAttivitas(int i) {
        return this.paAttivitas[i];
    }

    public void setPaAttivitas(int i, com.hyperborea.sira.ws.PaAttivita _value) {
        this.paAttivitas[i] = _value;
    }


    /**
     * Gets the personaleAttivitas value for this Attivita.
     * 
     * @return personaleAttivitas
     */
    public com.hyperborea.sira.ws.PersonaleAttivita[] getPersonaleAttivitas() {
        return personaleAttivitas;
    }


    /**
     * Sets the personaleAttivitas value for this Attivita.
     * 
     * @param personaleAttivitas
     */
    public void setPersonaleAttivitas(com.hyperborea.sira.ws.PersonaleAttivita[] personaleAttivitas) {
        this.personaleAttivitas = personaleAttivitas;
    }

    public com.hyperborea.sira.ws.PersonaleAttivita getPersonaleAttivitas(int i) {
        return this.personaleAttivitas[i];
    }

    public void setPersonaleAttivitas(int i, com.hyperborea.sira.ws.PersonaleAttivita _value) {
        this.personaleAttivitas[i] = _value;
    }


    /**
     * Gets the tipologieAttivita value for this Attivita.
     * 
     * @return tipologieAttivita
     */
    public com.hyperborea.sira.ws.TipologieAttivita getTipologieAttivita() {
        return tipologieAttivita;
    }


    /**
     * Sets the tipologieAttivita value for this Attivita.
     * 
     * @param tipologieAttivita
     */
    public void setTipologieAttivita(com.hyperborea.sira.ws.TipologieAttivita tipologieAttivita) {
        this.tipologieAttivita = tipologieAttivita;
    }


    /**
     * Gets the verbaleNr value for this Attivita.
     * 
     * @return verbaleNr
     */
    public java.lang.String getVerbaleNr() {
        return verbaleNr;
    }


    /**
     * Sets the verbaleNr value for this Attivita.
     * 
     * @param verbaleNr
     */
    public void setVerbaleNr(java.lang.String verbaleNr) {
        this.verbaleNr = verbaleNr;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Attivita)) return false;
        Attivita other = (Attivita) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.campiones==null && other.getCampiones()==null) || 
             (this.campiones!=null &&
              java.util.Arrays.equals(this.campiones, other.getCampiones()))) &&
            ((this.dataFine==null && other.getDataFine()==null) || 
             (this.dataFine!=null &&
              this.dataFine.equals(other.getDataFine()))) &&
            ((this.dataFineEffettiva==null && other.getDataFineEffettiva()==null) || 
             (this.dataFineEffettiva!=null &&
              this.dataFineEffettiva.equals(other.getDataFineEffettiva()))) &&
            ((this.dataInizio==null && other.getDataInizio()==null) || 
             (this.dataInizio!=null &&
              this.dataInizio.equals(other.getDataInizio()))) &&
            ((this.dataInizioEffettiva==null && other.getDataInizioEffettiva()==null) || 
             (this.dataInizioEffettiva!=null &&
              this.dataInizioEffettiva.equals(other.getDataInizioEffettiva()))) &&
            ((this.dataVerbale==null && other.getDataVerbale()==null) || 
             (this.dataVerbale!=null &&
              this.dataVerbale.equals(other.getDataVerbale()))) &&
            ((this.fuoriSede==null && other.getFuoriSede()==null) || 
             (this.fuoriSede!=null &&
              this.fuoriSede.equals(other.getFuoriSede()))) &&
            ((this.idAttivita==null && other.getIdAttivita()==null) || 
             (this.idAttivita!=null &&
              this.idAttivita.equals(other.getIdAttivita()))) &&
            ((this.misureAgentifisicis==null && other.getMisureAgentifisicis()==null) || 
             (this.misureAgentifisicis!=null &&
              java.util.Arrays.equals(this.misureAgentifisicis, other.getMisureAgentifisicis()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.paAttivitas==null && other.getPaAttivitas()==null) || 
             (this.paAttivitas!=null &&
              java.util.Arrays.equals(this.paAttivitas, other.getPaAttivitas()))) &&
            ((this.personaleAttivitas==null && other.getPersonaleAttivitas()==null) || 
             (this.personaleAttivitas!=null &&
              java.util.Arrays.equals(this.personaleAttivitas, other.getPersonaleAttivitas()))) &&
            ((this.tipologieAttivita==null && other.getTipologieAttivita()==null) || 
             (this.tipologieAttivita!=null &&
              this.tipologieAttivita.equals(other.getTipologieAttivita()))) &&
            ((this.verbaleNr==null && other.getVerbaleNr()==null) || 
             (this.verbaleNr!=null &&
              this.verbaleNr.equals(other.getVerbaleNr())));
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
        if (getCampiones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCampiones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCampiones(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDataFine() != null) {
            _hashCode += getDataFine().hashCode();
        }
        if (getDataFineEffettiva() != null) {
            _hashCode += getDataFineEffettiva().hashCode();
        }
        if (getDataInizio() != null) {
            _hashCode += getDataInizio().hashCode();
        }
        if (getDataInizioEffettiva() != null) {
            _hashCode += getDataInizioEffettiva().hashCode();
        }
        if (getDataVerbale() != null) {
            _hashCode += getDataVerbale().hashCode();
        }
        if (getFuoriSede() != null) {
            _hashCode += getFuoriSede().hashCode();
        }
        if (getIdAttivita() != null) {
            _hashCode += getIdAttivita().hashCode();
        }
        if (getMisureAgentifisicis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMisureAgentifisicis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMisureAgentifisicis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getPaAttivitas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPaAttivitas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPaAttivitas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPersonaleAttivitas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPersonaleAttivitas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPersonaleAttivitas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTipologieAttivita() != null) {
            _hashCode += getTipologieAttivita().hashCode();
        }
        if (getVerbaleNr() != null) {
            _hashCode += getVerbaleNr().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Attivita.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attivita"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campiones");
        elemField.setXmlName(new javax.xml.namespace.QName("", "campiones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "campione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataFine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFineEffettiva");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataFineEffettiva"));
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
        elemField.setFieldName("dataInizioEffettiva");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInizioEffettiva"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataVerbale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataVerbale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fuoriSede");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fuoriSede"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAttivita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idAttivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("misureAgentifisicis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "misureAgentifisicis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "misureAgentifisici"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("note");
        elemField.setXmlName(new javax.xml.namespace.QName("", "note"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paAttivitas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paAttivitas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "paAttivita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personaleAttivitas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "personaleAttivitas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "personaleAttivita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologieAttivita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologieAttivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieAttivita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("verbaleNr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "verbaleNr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
