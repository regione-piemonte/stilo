/**
 * CcostCentroFaunaEf.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostCentroFaunaEf  implements java.io.Serializable {
    private java.lang.Integer allevamento;

    private java.lang.String codice;

    private java.lang.String descrizione;

    private java.lang.Integer educazioneAmbientale;

    private java.lang.Integer idCcost;

    private java.lang.Integer recuperoSanitario;

    private java.lang.String servizioTerritoriale;

    private java.lang.Integer sperimentazione;

    private java.lang.String tipologia;

    public CcostCentroFaunaEf() {
    }

    public CcostCentroFaunaEf(
           java.lang.Integer allevamento,
           java.lang.String codice,
           java.lang.String descrizione,
           java.lang.Integer educazioneAmbientale,
           java.lang.Integer idCcost,
           java.lang.Integer recuperoSanitario,
           java.lang.String servizioTerritoriale,
           java.lang.Integer sperimentazione,
           java.lang.String tipologia) {
           this.allevamento = allevamento;
           this.codice = codice;
           this.descrizione = descrizione;
           this.educazioneAmbientale = educazioneAmbientale;
           this.idCcost = idCcost;
           this.recuperoSanitario = recuperoSanitario;
           this.servizioTerritoriale = servizioTerritoriale;
           this.sperimentazione = sperimentazione;
           this.tipologia = tipologia;
    }


    /**
     * Gets the allevamento value for this CcostCentroFaunaEf.
     * 
     * @return allevamento
     */
    public java.lang.Integer getAllevamento() {
        return allevamento;
    }


    /**
     * Sets the allevamento value for this CcostCentroFaunaEf.
     * 
     * @param allevamento
     */
    public void setAllevamento(java.lang.Integer allevamento) {
        this.allevamento = allevamento;
    }


    /**
     * Gets the codice value for this CcostCentroFaunaEf.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this CcostCentroFaunaEf.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this CcostCentroFaunaEf.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this CcostCentroFaunaEf.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the educazioneAmbientale value for this CcostCentroFaunaEf.
     * 
     * @return educazioneAmbientale
     */
    public java.lang.Integer getEducazioneAmbientale() {
        return educazioneAmbientale;
    }


    /**
     * Sets the educazioneAmbientale value for this CcostCentroFaunaEf.
     * 
     * @param educazioneAmbientale
     */
    public void setEducazioneAmbientale(java.lang.Integer educazioneAmbientale) {
        this.educazioneAmbientale = educazioneAmbientale;
    }


    /**
     * Gets the idCcost value for this CcostCentroFaunaEf.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostCentroFaunaEf.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the recuperoSanitario value for this CcostCentroFaunaEf.
     * 
     * @return recuperoSanitario
     */
    public java.lang.Integer getRecuperoSanitario() {
        return recuperoSanitario;
    }


    /**
     * Sets the recuperoSanitario value for this CcostCentroFaunaEf.
     * 
     * @param recuperoSanitario
     */
    public void setRecuperoSanitario(java.lang.Integer recuperoSanitario) {
        this.recuperoSanitario = recuperoSanitario;
    }


    /**
     * Gets the servizioTerritoriale value for this CcostCentroFaunaEf.
     * 
     * @return servizioTerritoriale
     */
    public java.lang.String getServizioTerritoriale() {
        return servizioTerritoriale;
    }


    /**
     * Sets the servizioTerritoriale value for this CcostCentroFaunaEf.
     * 
     * @param servizioTerritoriale
     */
    public void setServizioTerritoriale(java.lang.String servizioTerritoriale) {
        this.servizioTerritoriale = servizioTerritoriale;
    }


    /**
     * Gets the sperimentazione value for this CcostCentroFaunaEf.
     * 
     * @return sperimentazione
     */
    public java.lang.Integer getSperimentazione() {
        return sperimentazione;
    }


    /**
     * Sets the sperimentazione value for this CcostCentroFaunaEf.
     * 
     * @param sperimentazione
     */
    public void setSperimentazione(java.lang.Integer sperimentazione) {
        this.sperimentazione = sperimentazione;
    }


    /**
     * Gets the tipologia value for this CcostCentroFaunaEf.
     * 
     * @return tipologia
     */
    public java.lang.String getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this CcostCentroFaunaEf.
     * 
     * @param tipologia
     */
    public void setTipologia(java.lang.String tipologia) {
        this.tipologia = tipologia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostCentroFaunaEf)) return false;
        CcostCentroFaunaEf other = (CcostCentroFaunaEf) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.allevamento==null && other.getAllevamento()==null) || 
             (this.allevamento!=null &&
              this.allevamento.equals(other.getAllevamento()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.educazioneAmbientale==null && other.getEducazioneAmbientale()==null) || 
             (this.educazioneAmbientale!=null &&
              this.educazioneAmbientale.equals(other.getEducazioneAmbientale()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.recuperoSanitario==null && other.getRecuperoSanitario()==null) || 
             (this.recuperoSanitario!=null &&
              this.recuperoSanitario.equals(other.getRecuperoSanitario()))) &&
            ((this.servizioTerritoriale==null && other.getServizioTerritoriale()==null) || 
             (this.servizioTerritoriale!=null &&
              this.servizioTerritoriale.equals(other.getServizioTerritoriale()))) &&
            ((this.sperimentazione==null && other.getSperimentazione()==null) || 
             (this.sperimentazione!=null &&
              this.sperimentazione.equals(other.getSperimentazione()))) &&
            ((this.tipologia==null && other.getTipologia()==null) || 
             (this.tipologia!=null &&
              this.tipologia.equals(other.getTipologia())));
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
        if (getAllevamento() != null) {
            _hashCode += getAllevamento().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getEducazioneAmbientale() != null) {
            _hashCode += getEducazioneAmbientale().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getRecuperoSanitario() != null) {
            _hashCode += getRecuperoSanitario().hashCode();
        }
        if (getServizioTerritoriale() != null) {
            _hashCode += getServizioTerritoriale().hashCode();
        }
        if (getSperimentazione() != null) {
            _hashCode += getSperimentazione().hashCode();
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostCentroFaunaEf.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCentroFaunaEf"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allevamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "allevamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("educazioneAmbientale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "educazioneAmbientale"));
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
        elemField.setFieldName("recuperoSanitario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "recuperoSanitario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servizioTerritoriale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servizioTerritoriale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sperimentazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sperimentazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologia"));
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
