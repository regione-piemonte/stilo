/**
 * CcostMiniera.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostMiniera  implements java.io.Serializable {
    private java.lang.Integer annoAvvio;

    private java.util.Calendar dataDecorrenza;

    private java.util.Calendar dataPrimoDecreto;

    private java.util.Calendar dataRinuncia;

    private java.util.Calendar dataScadenza;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.MinieraAcqueProduzione[] minieraAcqueProduziones;

    private com.hyperborea.sira.ws.MinieraComuni[] minieraComunis;

    private com.hyperborea.sira.ws.MinieraMinerali[] minieraMineralis;

    private com.hyperborea.sira.ws.MinieraTavoletteIgm[] minieraTavoletteIgms;

    private java.lang.Float quantitaConcessaMc;

    private java.lang.Float quantitaConcessaT;

    private java.lang.Float superficieAccordata;

    private java.lang.String tipoColtivazione;

    private com.hyperborea.sira.ws.VocTipologiaTitoloMinerario vocTipologiaTitoloMinerario;

    private java.lang.String nRegistroStorico;

    public CcostMiniera() {
    }

    public CcostMiniera(
           java.lang.Integer annoAvvio,
           java.util.Calendar dataDecorrenza,
           java.util.Calendar dataPrimoDecreto,
           java.util.Calendar dataRinuncia,
           java.util.Calendar dataScadenza,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.MinieraAcqueProduzione[] minieraAcqueProduziones,
           com.hyperborea.sira.ws.MinieraComuni[] minieraComunis,
           com.hyperborea.sira.ws.MinieraMinerali[] minieraMineralis,
           com.hyperborea.sira.ws.MinieraTavoletteIgm[] minieraTavoletteIgms,
           java.lang.Float quantitaConcessaMc,
           java.lang.Float quantitaConcessaT,
           java.lang.Float superficieAccordata,
           java.lang.String tipoColtivazione,
           com.hyperborea.sira.ws.VocTipologiaTitoloMinerario vocTipologiaTitoloMinerario,
           java.lang.String nRegistroStorico) {
           this.annoAvvio = annoAvvio;
           this.dataDecorrenza = dataDecorrenza;
           this.dataPrimoDecreto = dataPrimoDecreto;
           this.dataRinuncia = dataRinuncia;
           this.dataScadenza = dataScadenza;
           this.idCcost = idCcost;
           this.minieraAcqueProduziones = minieraAcqueProduziones;
           this.minieraComunis = minieraComunis;
           this.minieraMineralis = minieraMineralis;
           this.minieraTavoletteIgms = minieraTavoletteIgms;
           this.quantitaConcessaMc = quantitaConcessaMc;
           this.quantitaConcessaT = quantitaConcessaT;
           this.superficieAccordata = superficieAccordata;
           this.tipoColtivazione = tipoColtivazione;
           this.vocTipologiaTitoloMinerario = vocTipologiaTitoloMinerario;
           this.nRegistroStorico = nRegistroStorico;
    }


    /**
     * Gets the annoAvvio value for this CcostMiniera.
     * 
     * @return annoAvvio
     */
    public java.lang.Integer getAnnoAvvio() {
        return annoAvvio;
    }


    /**
     * Sets the annoAvvio value for this CcostMiniera.
     * 
     * @param annoAvvio
     */
    public void setAnnoAvvio(java.lang.Integer annoAvvio) {
        this.annoAvvio = annoAvvio;
    }


    /**
     * Gets the dataDecorrenza value for this CcostMiniera.
     * 
     * @return dataDecorrenza
     */
    public java.util.Calendar getDataDecorrenza() {
        return dataDecorrenza;
    }


    /**
     * Sets the dataDecorrenza value for this CcostMiniera.
     * 
     * @param dataDecorrenza
     */
    public void setDataDecorrenza(java.util.Calendar dataDecorrenza) {
        this.dataDecorrenza = dataDecorrenza;
    }


    /**
     * Gets the dataPrimoDecreto value for this CcostMiniera.
     * 
     * @return dataPrimoDecreto
     */
    public java.util.Calendar getDataPrimoDecreto() {
        return dataPrimoDecreto;
    }


    /**
     * Sets the dataPrimoDecreto value for this CcostMiniera.
     * 
     * @param dataPrimoDecreto
     */
    public void setDataPrimoDecreto(java.util.Calendar dataPrimoDecreto) {
        this.dataPrimoDecreto = dataPrimoDecreto;
    }


    /**
     * Gets the dataRinuncia value for this CcostMiniera.
     * 
     * @return dataRinuncia
     */
    public java.util.Calendar getDataRinuncia() {
        return dataRinuncia;
    }


    /**
     * Sets the dataRinuncia value for this CcostMiniera.
     * 
     * @param dataRinuncia
     */
    public void setDataRinuncia(java.util.Calendar dataRinuncia) {
        this.dataRinuncia = dataRinuncia;
    }


    /**
     * Gets the dataScadenza value for this CcostMiniera.
     * 
     * @return dataScadenza
     */
    public java.util.Calendar getDataScadenza() {
        return dataScadenza;
    }


    /**
     * Sets the dataScadenza value for this CcostMiniera.
     * 
     * @param dataScadenza
     */
    public void setDataScadenza(java.util.Calendar dataScadenza) {
        this.dataScadenza = dataScadenza;
    }


    /**
     * Gets the idCcost value for this CcostMiniera.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostMiniera.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the minieraAcqueProduziones value for this CcostMiniera.
     * 
     * @return minieraAcqueProduziones
     */
    public com.hyperborea.sira.ws.MinieraAcqueProduzione[] getMinieraAcqueProduziones() {
        return minieraAcqueProduziones;
    }


    /**
     * Sets the minieraAcqueProduziones value for this CcostMiniera.
     * 
     * @param minieraAcqueProduziones
     */
    public void setMinieraAcqueProduziones(com.hyperborea.sira.ws.MinieraAcqueProduzione[] minieraAcqueProduziones) {
        this.minieraAcqueProduziones = minieraAcqueProduziones;
    }

    public com.hyperborea.sira.ws.MinieraAcqueProduzione getMinieraAcqueProduziones(int i) {
        return this.minieraAcqueProduziones[i];
    }

    public void setMinieraAcqueProduziones(int i, com.hyperborea.sira.ws.MinieraAcqueProduzione _value) {
        this.minieraAcqueProduziones[i] = _value;
    }


    /**
     * Gets the minieraComunis value for this CcostMiniera.
     * 
     * @return minieraComunis
     */
    public com.hyperborea.sira.ws.MinieraComuni[] getMinieraComunis() {
        return minieraComunis;
    }


    /**
     * Sets the minieraComunis value for this CcostMiniera.
     * 
     * @param minieraComunis
     */
    public void setMinieraComunis(com.hyperborea.sira.ws.MinieraComuni[] minieraComunis) {
        this.minieraComunis = minieraComunis;
    }

    public com.hyperborea.sira.ws.MinieraComuni getMinieraComunis(int i) {
        return this.minieraComunis[i];
    }

    public void setMinieraComunis(int i, com.hyperborea.sira.ws.MinieraComuni _value) {
        this.minieraComunis[i] = _value;
    }


    /**
     * Gets the minieraMineralis value for this CcostMiniera.
     * 
     * @return minieraMineralis
     */
    public com.hyperborea.sira.ws.MinieraMinerali[] getMinieraMineralis() {
        return minieraMineralis;
    }


    /**
     * Sets the minieraMineralis value for this CcostMiniera.
     * 
     * @param minieraMineralis
     */
    public void setMinieraMineralis(com.hyperborea.sira.ws.MinieraMinerali[] minieraMineralis) {
        this.minieraMineralis = minieraMineralis;
    }

    public com.hyperborea.sira.ws.MinieraMinerali getMinieraMineralis(int i) {
        return this.minieraMineralis[i];
    }

    public void setMinieraMineralis(int i, com.hyperborea.sira.ws.MinieraMinerali _value) {
        this.minieraMineralis[i] = _value;
    }


    /**
     * Gets the minieraTavoletteIgms value for this CcostMiniera.
     * 
     * @return minieraTavoletteIgms
     */
    public com.hyperborea.sira.ws.MinieraTavoletteIgm[] getMinieraTavoletteIgms() {
        return minieraTavoletteIgms;
    }


    /**
     * Sets the minieraTavoletteIgms value for this CcostMiniera.
     * 
     * @param minieraTavoletteIgms
     */
    public void setMinieraTavoletteIgms(com.hyperborea.sira.ws.MinieraTavoletteIgm[] minieraTavoletteIgms) {
        this.minieraTavoletteIgms = minieraTavoletteIgms;
    }

    public com.hyperborea.sira.ws.MinieraTavoletteIgm getMinieraTavoletteIgms(int i) {
        return this.minieraTavoletteIgms[i];
    }

    public void setMinieraTavoletteIgms(int i, com.hyperborea.sira.ws.MinieraTavoletteIgm _value) {
        this.minieraTavoletteIgms[i] = _value;
    }


    /**
     * Gets the quantitaConcessaMc value for this CcostMiniera.
     * 
     * @return quantitaConcessaMc
     */
    public java.lang.Float getQuantitaConcessaMc() {
        return quantitaConcessaMc;
    }


    /**
     * Sets the quantitaConcessaMc value for this CcostMiniera.
     * 
     * @param quantitaConcessaMc
     */
    public void setQuantitaConcessaMc(java.lang.Float quantitaConcessaMc) {
        this.quantitaConcessaMc = quantitaConcessaMc;
    }


    /**
     * Gets the quantitaConcessaT value for this CcostMiniera.
     * 
     * @return quantitaConcessaT
     */
    public java.lang.Float getQuantitaConcessaT() {
        return quantitaConcessaT;
    }


    /**
     * Sets the quantitaConcessaT value for this CcostMiniera.
     * 
     * @param quantitaConcessaT
     */
    public void setQuantitaConcessaT(java.lang.Float quantitaConcessaT) {
        this.quantitaConcessaT = quantitaConcessaT;
    }


    /**
     * Gets the superficieAccordata value for this CcostMiniera.
     * 
     * @return superficieAccordata
     */
    public java.lang.Float getSuperficieAccordata() {
        return superficieAccordata;
    }


    /**
     * Sets the superficieAccordata value for this CcostMiniera.
     * 
     * @param superficieAccordata
     */
    public void setSuperficieAccordata(java.lang.Float superficieAccordata) {
        this.superficieAccordata = superficieAccordata;
    }


    /**
     * Gets the tipoColtivazione value for this CcostMiniera.
     * 
     * @return tipoColtivazione
     */
    public java.lang.String getTipoColtivazione() {
        return tipoColtivazione;
    }


    /**
     * Sets the tipoColtivazione value for this CcostMiniera.
     * 
     * @param tipoColtivazione
     */
    public void setTipoColtivazione(java.lang.String tipoColtivazione) {
        this.tipoColtivazione = tipoColtivazione;
    }


    /**
     * Gets the vocTipologiaTitoloMinerario value for this CcostMiniera.
     * 
     * @return vocTipologiaTitoloMinerario
     */
    public com.hyperborea.sira.ws.VocTipologiaTitoloMinerario getVocTipologiaTitoloMinerario() {
        return vocTipologiaTitoloMinerario;
    }


    /**
     * Sets the vocTipologiaTitoloMinerario value for this CcostMiniera.
     * 
     * @param vocTipologiaTitoloMinerario
     */
    public void setVocTipologiaTitoloMinerario(com.hyperborea.sira.ws.VocTipologiaTitoloMinerario vocTipologiaTitoloMinerario) {
        this.vocTipologiaTitoloMinerario = vocTipologiaTitoloMinerario;
    }


    /**
     * Gets the nRegistroStorico value for this CcostMiniera.
     * 
     * @return nRegistroStorico
     */
    public java.lang.String getNRegistroStorico() {
        return nRegistroStorico;
    }


    /**
     * Sets the nRegistroStorico value for this CcostMiniera.
     * 
     * @param nRegistroStorico
     */
    public void setNRegistroStorico(java.lang.String nRegistroStorico) {
        this.nRegistroStorico = nRegistroStorico;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostMiniera)) return false;
        CcostMiniera other = (CcostMiniera) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.annoAvvio==null && other.getAnnoAvvio()==null) || 
             (this.annoAvvio!=null &&
              this.annoAvvio.equals(other.getAnnoAvvio()))) &&
            ((this.dataDecorrenza==null && other.getDataDecorrenza()==null) || 
             (this.dataDecorrenza!=null &&
              this.dataDecorrenza.equals(other.getDataDecorrenza()))) &&
            ((this.dataPrimoDecreto==null && other.getDataPrimoDecreto()==null) || 
             (this.dataPrimoDecreto!=null &&
              this.dataPrimoDecreto.equals(other.getDataPrimoDecreto()))) &&
            ((this.dataRinuncia==null && other.getDataRinuncia()==null) || 
             (this.dataRinuncia!=null &&
              this.dataRinuncia.equals(other.getDataRinuncia()))) &&
            ((this.dataScadenza==null && other.getDataScadenza()==null) || 
             (this.dataScadenza!=null &&
              this.dataScadenza.equals(other.getDataScadenza()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.minieraAcqueProduziones==null && other.getMinieraAcqueProduziones()==null) || 
             (this.minieraAcqueProduziones!=null &&
              java.util.Arrays.equals(this.minieraAcqueProduziones, other.getMinieraAcqueProduziones()))) &&
            ((this.minieraComunis==null && other.getMinieraComunis()==null) || 
             (this.minieraComunis!=null &&
              java.util.Arrays.equals(this.minieraComunis, other.getMinieraComunis()))) &&
            ((this.minieraMineralis==null && other.getMinieraMineralis()==null) || 
             (this.minieraMineralis!=null &&
              java.util.Arrays.equals(this.minieraMineralis, other.getMinieraMineralis()))) &&
            ((this.minieraTavoletteIgms==null && other.getMinieraTavoletteIgms()==null) || 
             (this.minieraTavoletteIgms!=null &&
              java.util.Arrays.equals(this.minieraTavoletteIgms, other.getMinieraTavoletteIgms()))) &&
            ((this.quantitaConcessaMc==null && other.getQuantitaConcessaMc()==null) || 
             (this.quantitaConcessaMc!=null &&
              this.quantitaConcessaMc.equals(other.getQuantitaConcessaMc()))) &&
            ((this.quantitaConcessaT==null && other.getQuantitaConcessaT()==null) || 
             (this.quantitaConcessaT!=null &&
              this.quantitaConcessaT.equals(other.getQuantitaConcessaT()))) &&
            ((this.superficieAccordata==null && other.getSuperficieAccordata()==null) || 
             (this.superficieAccordata!=null &&
              this.superficieAccordata.equals(other.getSuperficieAccordata()))) &&
            ((this.tipoColtivazione==null && other.getTipoColtivazione()==null) || 
             (this.tipoColtivazione!=null &&
              this.tipoColtivazione.equals(other.getTipoColtivazione()))) &&
            ((this.vocTipologiaTitoloMinerario==null && other.getVocTipologiaTitoloMinerario()==null) || 
             (this.vocTipologiaTitoloMinerario!=null &&
              this.vocTipologiaTitoloMinerario.equals(other.getVocTipologiaTitoloMinerario()))) &&
            ((this.nRegistroStorico==null && other.getNRegistroStorico()==null) || 
             (this.nRegistroStorico!=null &&
              this.nRegistroStorico.equals(other.getNRegistroStorico())));
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
        if (getAnnoAvvio() != null) {
            _hashCode += getAnnoAvvio().hashCode();
        }
        if (getDataDecorrenza() != null) {
            _hashCode += getDataDecorrenza().hashCode();
        }
        if (getDataPrimoDecreto() != null) {
            _hashCode += getDataPrimoDecreto().hashCode();
        }
        if (getDataRinuncia() != null) {
            _hashCode += getDataRinuncia().hashCode();
        }
        if (getDataScadenza() != null) {
            _hashCode += getDataScadenza().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getMinieraAcqueProduziones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMinieraAcqueProduziones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMinieraAcqueProduziones(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMinieraComunis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMinieraComunis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMinieraComunis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMinieraMineralis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMinieraMineralis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMinieraMineralis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMinieraTavoletteIgms() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMinieraTavoletteIgms());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMinieraTavoletteIgms(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getQuantitaConcessaMc() != null) {
            _hashCode += getQuantitaConcessaMc().hashCode();
        }
        if (getQuantitaConcessaT() != null) {
            _hashCode += getQuantitaConcessaT().hashCode();
        }
        if (getSuperficieAccordata() != null) {
            _hashCode += getSuperficieAccordata().hashCode();
        }
        if (getTipoColtivazione() != null) {
            _hashCode += getTipoColtivazione().hashCode();
        }
        if (getVocTipologiaTitoloMinerario() != null) {
            _hashCode += getVocTipologiaTitoloMinerario().hashCode();
        }
        if (getNRegistroStorico() != null) {
            _hashCode += getNRegistroStorico().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostMiniera.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostMiniera"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoAvvio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoAvvio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataDecorrenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataDecorrenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataPrimoDecreto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataPrimoDecreto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataRinuncia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataRinuncia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataScadenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataScadenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField.setFieldName("minieraAcqueProduziones");
        elemField.setXmlName(new javax.xml.namespace.QName("", "minieraAcqueProduziones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "minieraAcqueProduzione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minieraComunis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "minieraComunis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "minieraComuni"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minieraMineralis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "minieraMineralis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "minieraMinerali"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minieraTavoletteIgms");
        elemField.setXmlName(new javax.xml.namespace.QName("", "minieraTavoletteIgms"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "minieraTavoletteIgm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantitaConcessaMc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quantitaConcessaMc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantitaConcessaT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quantitaConcessaT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieAccordata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieAccordata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoColtivazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoColtivazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaTitoloMinerario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaTitoloMinerario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaTitoloMinerario"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NRegistroStorico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nRegistroStorico"));
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
