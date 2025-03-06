/**
 * CcostSorgLumArtificiale.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostSorgLumArtificiale  implements java.io.Serializable {
    private java.lang.Float coefficienteUtilizzazione;

    private java.lang.Float durataVitaLampada;

    private java.lang.Float efficienteLuminosaLampada;

    private java.lang.Float flussoLuminoso;

    private java.lang.Integer idCcost;

    private java.lang.Float illuminamentoLampada;

    private java.lang.Integer intensitaLuminosa;

    private java.lang.Float interdistanzaMedia;

    private java.lang.Integer irc;

    private java.lang.Float luminanza;

    private java.lang.Float potenzaMediaLampada;

    private java.lang.Integer puntiLuceTotali;

    private com.hyperborea.sira.ws.PuntiLuce[] puntiLuces;

    private java.lang.Float temperaturaColore;

    private java.lang.String tipologiaApparecchio;

    private com.hyperborea.sira.ws.VocClassificazioneStrada vocClassificazioneStrada;

    private com.hyperborea.sira.ws.VocTipoSorgentePrimaria vocTipoSorgentePrimaria;

    private com.hyperborea.sira.ws.VocTipologiaLampade vocTipologiaLampade;

    private com.hyperborea.sira.ws.VocTipologiaSorgenteLuce vocTipologiaSorgenteLuce;

    private java.lang.Integer nAppServiti;

    public CcostSorgLumArtificiale() {
    }

    public CcostSorgLumArtificiale(
           java.lang.Float coefficienteUtilizzazione,
           java.lang.Float durataVitaLampada,
           java.lang.Float efficienteLuminosaLampada,
           java.lang.Float flussoLuminoso,
           java.lang.Integer idCcost,
           java.lang.Float illuminamentoLampada,
           java.lang.Integer intensitaLuminosa,
           java.lang.Float interdistanzaMedia,
           java.lang.Integer irc,
           java.lang.Float luminanza,
           java.lang.Float potenzaMediaLampada,
           java.lang.Integer puntiLuceTotali,
           com.hyperborea.sira.ws.PuntiLuce[] puntiLuces,
           java.lang.Float temperaturaColore,
           java.lang.String tipologiaApparecchio,
           com.hyperborea.sira.ws.VocClassificazioneStrada vocClassificazioneStrada,
           com.hyperborea.sira.ws.VocTipoSorgentePrimaria vocTipoSorgentePrimaria,
           com.hyperborea.sira.ws.VocTipologiaLampade vocTipologiaLampade,
           com.hyperborea.sira.ws.VocTipologiaSorgenteLuce vocTipologiaSorgenteLuce,
           java.lang.Integer nAppServiti) {
           this.coefficienteUtilizzazione = coefficienteUtilizzazione;
           this.durataVitaLampada = durataVitaLampada;
           this.efficienteLuminosaLampada = efficienteLuminosaLampada;
           this.flussoLuminoso = flussoLuminoso;
           this.idCcost = idCcost;
           this.illuminamentoLampada = illuminamentoLampada;
           this.intensitaLuminosa = intensitaLuminosa;
           this.interdistanzaMedia = interdistanzaMedia;
           this.irc = irc;
           this.luminanza = luminanza;
           this.potenzaMediaLampada = potenzaMediaLampada;
           this.puntiLuceTotali = puntiLuceTotali;
           this.puntiLuces = puntiLuces;
           this.temperaturaColore = temperaturaColore;
           this.tipologiaApparecchio = tipologiaApparecchio;
           this.vocClassificazioneStrada = vocClassificazioneStrada;
           this.vocTipoSorgentePrimaria = vocTipoSorgentePrimaria;
           this.vocTipologiaLampade = vocTipologiaLampade;
           this.vocTipologiaSorgenteLuce = vocTipologiaSorgenteLuce;
           this.nAppServiti = nAppServiti;
    }


    /**
     * Gets the coefficienteUtilizzazione value for this CcostSorgLumArtificiale.
     * 
     * @return coefficienteUtilizzazione
     */
    public java.lang.Float getCoefficienteUtilizzazione() {
        return coefficienteUtilizzazione;
    }


    /**
     * Sets the coefficienteUtilizzazione value for this CcostSorgLumArtificiale.
     * 
     * @param coefficienteUtilizzazione
     */
    public void setCoefficienteUtilizzazione(java.lang.Float coefficienteUtilizzazione) {
        this.coefficienteUtilizzazione = coefficienteUtilizzazione;
    }


    /**
     * Gets the durataVitaLampada value for this CcostSorgLumArtificiale.
     * 
     * @return durataVitaLampada
     */
    public java.lang.Float getDurataVitaLampada() {
        return durataVitaLampada;
    }


    /**
     * Sets the durataVitaLampada value for this CcostSorgLumArtificiale.
     * 
     * @param durataVitaLampada
     */
    public void setDurataVitaLampada(java.lang.Float durataVitaLampada) {
        this.durataVitaLampada = durataVitaLampada;
    }


    /**
     * Gets the efficienteLuminosaLampada value for this CcostSorgLumArtificiale.
     * 
     * @return efficienteLuminosaLampada
     */
    public java.lang.Float getEfficienteLuminosaLampada() {
        return efficienteLuminosaLampada;
    }


    /**
     * Sets the efficienteLuminosaLampada value for this CcostSorgLumArtificiale.
     * 
     * @param efficienteLuminosaLampada
     */
    public void setEfficienteLuminosaLampada(java.lang.Float efficienteLuminosaLampada) {
        this.efficienteLuminosaLampada = efficienteLuminosaLampada;
    }


    /**
     * Gets the flussoLuminoso value for this CcostSorgLumArtificiale.
     * 
     * @return flussoLuminoso
     */
    public java.lang.Float getFlussoLuminoso() {
        return flussoLuminoso;
    }


    /**
     * Sets the flussoLuminoso value for this CcostSorgLumArtificiale.
     * 
     * @param flussoLuminoso
     */
    public void setFlussoLuminoso(java.lang.Float flussoLuminoso) {
        this.flussoLuminoso = flussoLuminoso;
    }


    /**
     * Gets the idCcost value for this CcostSorgLumArtificiale.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostSorgLumArtificiale.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the illuminamentoLampada value for this CcostSorgLumArtificiale.
     * 
     * @return illuminamentoLampada
     */
    public java.lang.Float getIlluminamentoLampada() {
        return illuminamentoLampada;
    }


    /**
     * Sets the illuminamentoLampada value for this CcostSorgLumArtificiale.
     * 
     * @param illuminamentoLampada
     */
    public void setIlluminamentoLampada(java.lang.Float illuminamentoLampada) {
        this.illuminamentoLampada = illuminamentoLampada;
    }


    /**
     * Gets the intensitaLuminosa value for this CcostSorgLumArtificiale.
     * 
     * @return intensitaLuminosa
     */
    public java.lang.Integer getIntensitaLuminosa() {
        return intensitaLuminosa;
    }


    /**
     * Sets the intensitaLuminosa value for this CcostSorgLumArtificiale.
     * 
     * @param intensitaLuminosa
     */
    public void setIntensitaLuminosa(java.lang.Integer intensitaLuminosa) {
        this.intensitaLuminosa = intensitaLuminosa;
    }


    /**
     * Gets the interdistanzaMedia value for this CcostSorgLumArtificiale.
     * 
     * @return interdistanzaMedia
     */
    public java.lang.Float getInterdistanzaMedia() {
        return interdistanzaMedia;
    }


    /**
     * Sets the interdistanzaMedia value for this CcostSorgLumArtificiale.
     * 
     * @param interdistanzaMedia
     */
    public void setInterdistanzaMedia(java.lang.Float interdistanzaMedia) {
        this.interdistanzaMedia = interdistanzaMedia;
    }


    /**
     * Gets the irc value for this CcostSorgLumArtificiale.
     * 
     * @return irc
     */
    public java.lang.Integer getIrc() {
        return irc;
    }


    /**
     * Sets the irc value for this CcostSorgLumArtificiale.
     * 
     * @param irc
     */
    public void setIrc(java.lang.Integer irc) {
        this.irc = irc;
    }


    /**
     * Gets the luminanza value for this CcostSorgLumArtificiale.
     * 
     * @return luminanza
     */
    public java.lang.Float getLuminanza() {
        return luminanza;
    }


    /**
     * Sets the luminanza value for this CcostSorgLumArtificiale.
     * 
     * @param luminanza
     */
    public void setLuminanza(java.lang.Float luminanza) {
        this.luminanza = luminanza;
    }


    /**
     * Gets the potenzaMediaLampada value for this CcostSorgLumArtificiale.
     * 
     * @return potenzaMediaLampada
     */
    public java.lang.Float getPotenzaMediaLampada() {
        return potenzaMediaLampada;
    }


    /**
     * Sets the potenzaMediaLampada value for this CcostSorgLumArtificiale.
     * 
     * @param potenzaMediaLampada
     */
    public void setPotenzaMediaLampada(java.lang.Float potenzaMediaLampada) {
        this.potenzaMediaLampada = potenzaMediaLampada;
    }


    /**
     * Gets the puntiLuceTotali value for this CcostSorgLumArtificiale.
     * 
     * @return puntiLuceTotali
     */
    public java.lang.Integer getPuntiLuceTotali() {
        return puntiLuceTotali;
    }


    /**
     * Sets the puntiLuceTotali value for this CcostSorgLumArtificiale.
     * 
     * @param puntiLuceTotali
     */
    public void setPuntiLuceTotali(java.lang.Integer puntiLuceTotali) {
        this.puntiLuceTotali = puntiLuceTotali;
    }


    /**
     * Gets the puntiLuces value for this CcostSorgLumArtificiale.
     * 
     * @return puntiLuces
     */
    public com.hyperborea.sira.ws.PuntiLuce[] getPuntiLuces() {
        return puntiLuces;
    }


    /**
     * Sets the puntiLuces value for this CcostSorgLumArtificiale.
     * 
     * @param puntiLuces
     */
    public void setPuntiLuces(com.hyperborea.sira.ws.PuntiLuce[] puntiLuces) {
        this.puntiLuces = puntiLuces;
    }

    public com.hyperborea.sira.ws.PuntiLuce getPuntiLuces(int i) {
        return this.puntiLuces[i];
    }

    public void setPuntiLuces(int i, com.hyperborea.sira.ws.PuntiLuce _value) {
        this.puntiLuces[i] = _value;
    }


    /**
     * Gets the temperaturaColore value for this CcostSorgLumArtificiale.
     * 
     * @return temperaturaColore
     */
    public java.lang.Float getTemperaturaColore() {
        return temperaturaColore;
    }


    /**
     * Sets the temperaturaColore value for this CcostSorgLumArtificiale.
     * 
     * @param temperaturaColore
     */
    public void setTemperaturaColore(java.lang.Float temperaturaColore) {
        this.temperaturaColore = temperaturaColore;
    }


    /**
     * Gets the tipologiaApparecchio value for this CcostSorgLumArtificiale.
     * 
     * @return tipologiaApparecchio
     */
    public java.lang.String getTipologiaApparecchio() {
        return tipologiaApparecchio;
    }


    /**
     * Sets the tipologiaApparecchio value for this CcostSorgLumArtificiale.
     * 
     * @param tipologiaApparecchio
     */
    public void setTipologiaApparecchio(java.lang.String tipologiaApparecchio) {
        this.tipologiaApparecchio = tipologiaApparecchio;
    }


    /**
     * Gets the vocClassificazioneStrada value for this CcostSorgLumArtificiale.
     * 
     * @return vocClassificazioneStrada
     */
    public com.hyperborea.sira.ws.VocClassificazioneStrada getVocClassificazioneStrada() {
        return vocClassificazioneStrada;
    }


    /**
     * Sets the vocClassificazioneStrada value for this CcostSorgLumArtificiale.
     * 
     * @param vocClassificazioneStrada
     */
    public void setVocClassificazioneStrada(com.hyperborea.sira.ws.VocClassificazioneStrada vocClassificazioneStrada) {
        this.vocClassificazioneStrada = vocClassificazioneStrada;
    }


    /**
     * Gets the vocTipoSorgentePrimaria value for this CcostSorgLumArtificiale.
     * 
     * @return vocTipoSorgentePrimaria
     */
    public com.hyperborea.sira.ws.VocTipoSorgentePrimaria getVocTipoSorgentePrimaria() {
        return vocTipoSorgentePrimaria;
    }


    /**
     * Sets the vocTipoSorgentePrimaria value for this CcostSorgLumArtificiale.
     * 
     * @param vocTipoSorgentePrimaria
     */
    public void setVocTipoSorgentePrimaria(com.hyperborea.sira.ws.VocTipoSorgentePrimaria vocTipoSorgentePrimaria) {
        this.vocTipoSorgentePrimaria = vocTipoSorgentePrimaria;
    }


    /**
     * Gets the vocTipologiaLampade value for this CcostSorgLumArtificiale.
     * 
     * @return vocTipologiaLampade
     */
    public com.hyperborea.sira.ws.VocTipologiaLampade getVocTipologiaLampade() {
        return vocTipologiaLampade;
    }


    /**
     * Sets the vocTipologiaLampade value for this CcostSorgLumArtificiale.
     * 
     * @param vocTipologiaLampade
     */
    public void setVocTipologiaLampade(com.hyperborea.sira.ws.VocTipologiaLampade vocTipologiaLampade) {
        this.vocTipologiaLampade = vocTipologiaLampade;
    }


    /**
     * Gets the vocTipologiaSorgenteLuce value for this CcostSorgLumArtificiale.
     * 
     * @return vocTipologiaSorgenteLuce
     */
    public com.hyperborea.sira.ws.VocTipologiaSorgenteLuce getVocTipologiaSorgenteLuce() {
        return vocTipologiaSorgenteLuce;
    }


    /**
     * Sets the vocTipologiaSorgenteLuce value for this CcostSorgLumArtificiale.
     * 
     * @param vocTipologiaSorgenteLuce
     */
    public void setVocTipologiaSorgenteLuce(com.hyperborea.sira.ws.VocTipologiaSorgenteLuce vocTipologiaSorgenteLuce) {
        this.vocTipologiaSorgenteLuce = vocTipologiaSorgenteLuce;
    }


    /**
     * Gets the nAppServiti value for this CcostSorgLumArtificiale.
     * 
     * @return nAppServiti
     */
    public java.lang.Integer getNAppServiti() {
        return nAppServiti;
    }


    /**
     * Sets the nAppServiti value for this CcostSorgLumArtificiale.
     * 
     * @param nAppServiti
     */
    public void setNAppServiti(java.lang.Integer nAppServiti) {
        this.nAppServiti = nAppServiti;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostSorgLumArtificiale)) return false;
        CcostSorgLumArtificiale other = (CcostSorgLumArtificiale) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.coefficienteUtilizzazione==null && other.getCoefficienteUtilizzazione()==null) || 
             (this.coefficienteUtilizzazione!=null &&
              this.coefficienteUtilizzazione.equals(other.getCoefficienteUtilizzazione()))) &&
            ((this.durataVitaLampada==null && other.getDurataVitaLampada()==null) || 
             (this.durataVitaLampada!=null &&
              this.durataVitaLampada.equals(other.getDurataVitaLampada()))) &&
            ((this.efficienteLuminosaLampada==null && other.getEfficienteLuminosaLampada()==null) || 
             (this.efficienteLuminosaLampada!=null &&
              this.efficienteLuminosaLampada.equals(other.getEfficienteLuminosaLampada()))) &&
            ((this.flussoLuminoso==null && other.getFlussoLuminoso()==null) || 
             (this.flussoLuminoso!=null &&
              this.flussoLuminoso.equals(other.getFlussoLuminoso()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.illuminamentoLampada==null && other.getIlluminamentoLampada()==null) || 
             (this.illuminamentoLampada!=null &&
              this.illuminamentoLampada.equals(other.getIlluminamentoLampada()))) &&
            ((this.intensitaLuminosa==null && other.getIntensitaLuminosa()==null) || 
             (this.intensitaLuminosa!=null &&
              this.intensitaLuminosa.equals(other.getIntensitaLuminosa()))) &&
            ((this.interdistanzaMedia==null && other.getInterdistanzaMedia()==null) || 
             (this.interdistanzaMedia!=null &&
              this.interdistanzaMedia.equals(other.getInterdistanzaMedia()))) &&
            ((this.irc==null && other.getIrc()==null) || 
             (this.irc!=null &&
              this.irc.equals(other.getIrc()))) &&
            ((this.luminanza==null && other.getLuminanza()==null) || 
             (this.luminanza!=null &&
              this.luminanza.equals(other.getLuminanza()))) &&
            ((this.potenzaMediaLampada==null && other.getPotenzaMediaLampada()==null) || 
             (this.potenzaMediaLampada!=null &&
              this.potenzaMediaLampada.equals(other.getPotenzaMediaLampada()))) &&
            ((this.puntiLuceTotali==null && other.getPuntiLuceTotali()==null) || 
             (this.puntiLuceTotali!=null &&
              this.puntiLuceTotali.equals(other.getPuntiLuceTotali()))) &&
            ((this.puntiLuces==null && other.getPuntiLuces()==null) || 
             (this.puntiLuces!=null &&
              java.util.Arrays.equals(this.puntiLuces, other.getPuntiLuces()))) &&
            ((this.temperaturaColore==null && other.getTemperaturaColore()==null) || 
             (this.temperaturaColore!=null &&
              this.temperaturaColore.equals(other.getTemperaturaColore()))) &&
            ((this.tipologiaApparecchio==null && other.getTipologiaApparecchio()==null) || 
             (this.tipologiaApparecchio!=null &&
              this.tipologiaApparecchio.equals(other.getTipologiaApparecchio()))) &&
            ((this.vocClassificazioneStrada==null && other.getVocClassificazioneStrada()==null) || 
             (this.vocClassificazioneStrada!=null &&
              this.vocClassificazioneStrada.equals(other.getVocClassificazioneStrada()))) &&
            ((this.vocTipoSorgentePrimaria==null && other.getVocTipoSorgentePrimaria()==null) || 
             (this.vocTipoSorgentePrimaria!=null &&
              this.vocTipoSorgentePrimaria.equals(other.getVocTipoSorgentePrimaria()))) &&
            ((this.vocTipologiaLampade==null && other.getVocTipologiaLampade()==null) || 
             (this.vocTipologiaLampade!=null &&
              this.vocTipologiaLampade.equals(other.getVocTipologiaLampade()))) &&
            ((this.vocTipologiaSorgenteLuce==null && other.getVocTipologiaSorgenteLuce()==null) || 
             (this.vocTipologiaSorgenteLuce!=null &&
              this.vocTipologiaSorgenteLuce.equals(other.getVocTipologiaSorgenteLuce()))) &&
            ((this.nAppServiti==null && other.getNAppServiti()==null) || 
             (this.nAppServiti!=null &&
              this.nAppServiti.equals(other.getNAppServiti())));
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
        if (getCoefficienteUtilizzazione() != null) {
            _hashCode += getCoefficienteUtilizzazione().hashCode();
        }
        if (getDurataVitaLampada() != null) {
            _hashCode += getDurataVitaLampada().hashCode();
        }
        if (getEfficienteLuminosaLampada() != null) {
            _hashCode += getEfficienteLuminosaLampada().hashCode();
        }
        if (getFlussoLuminoso() != null) {
            _hashCode += getFlussoLuminoso().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getIlluminamentoLampada() != null) {
            _hashCode += getIlluminamentoLampada().hashCode();
        }
        if (getIntensitaLuminosa() != null) {
            _hashCode += getIntensitaLuminosa().hashCode();
        }
        if (getInterdistanzaMedia() != null) {
            _hashCode += getInterdistanzaMedia().hashCode();
        }
        if (getIrc() != null) {
            _hashCode += getIrc().hashCode();
        }
        if (getLuminanza() != null) {
            _hashCode += getLuminanza().hashCode();
        }
        if (getPotenzaMediaLampada() != null) {
            _hashCode += getPotenzaMediaLampada().hashCode();
        }
        if (getPuntiLuceTotali() != null) {
            _hashCode += getPuntiLuceTotali().hashCode();
        }
        if (getPuntiLuces() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPuntiLuces());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPuntiLuces(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTemperaturaColore() != null) {
            _hashCode += getTemperaturaColore().hashCode();
        }
        if (getTipologiaApparecchio() != null) {
            _hashCode += getTipologiaApparecchio().hashCode();
        }
        if (getVocClassificazioneStrada() != null) {
            _hashCode += getVocClassificazioneStrada().hashCode();
        }
        if (getVocTipoSorgentePrimaria() != null) {
            _hashCode += getVocTipoSorgentePrimaria().hashCode();
        }
        if (getVocTipologiaLampade() != null) {
            _hashCode += getVocTipologiaLampade().hashCode();
        }
        if (getVocTipologiaSorgenteLuce() != null) {
            _hashCode += getVocTipologiaSorgenteLuce().hashCode();
        }
        if (getNAppServiti() != null) {
            _hashCode += getNAppServiti().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostSorgLumArtificiale.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSorgLumArtificiale"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coefficienteUtilizzazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "coefficienteUtilizzazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("durataVitaLampada");
        elemField.setXmlName(new javax.xml.namespace.QName("", "durataVitaLampada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("efficienteLuminosaLampada");
        elemField.setXmlName(new javax.xml.namespace.QName("", "efficienteLuminosaLampada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flussoLuminoso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flussoLuminoso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
        elemField.setFieldName("illuminamentoLampada");
        elemField.setXmlName(new javax.xml.namespace.QName("", "illuminamentoLampada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("intensitaLuminosa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "intensitaLuminosa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interdistanzaMedia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "interdistanzaMedia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("irc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "irc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("luminanza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "luminanza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzaMediaLampada");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzaMediaLampada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("puntiLuceTotali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "puntiLuceTotali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("puntiLuces");
        elemField.setXmlName(new javax.xml.namespace.QName("", "puntiLuces"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "puntiLuce"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("temperaturaColore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "temperaturaColore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologiaApparecchio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologiaApparecchio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocClassificazioneStrada");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocClassificazioneStrada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClassificazioneStrada"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoSorgentePrimaria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoSorgentePrimaria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoSorgentePrimaria"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaLampade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaLampade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaLampade"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaSorgenteLuce");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaSorgenteLuce"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaSorgenteLuce"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NAppServiti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nAppServiti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
