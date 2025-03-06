/**
 * ProgettiRicercaForestale.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProgettiRicercaForestale  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.util.Calendar dataFine;

    private java.util.Calendar dataInizio;

    private java.lang.String denominazione;

    private java.lang.String ente;

    private java.lang.String finanziamento;

    private java.lang.Integer idProgetto;

    private java.lang.String partecipanti;

    public ProgettiRicercaForestale() {
    }

    public ProgettiRicercaForestale(
           java.util.Calendar dataFine,
           java.util.Calendar dataInizio,
           java.lang.String denominazione,
           java.lang.String ente,
           java.lang.String finanziamento,
           java.lang.Integer idProgetto,
           java.lang.String partecipanti) {
        this.dataFine = dataFine;
        this.dataInizio = dataInizio;
        this.denominazione = denominazione;
        this.ente = ente;
        this.finanziamento = finanziamento;
        this.idProgetto = idProgetto;
        this.partecipanti = partecipanti;
    }


    /**
     * Gets the dataFine value for this ProgettiRicercaForestale.
     * 
     * @return dataFine
     */
    public java.util.Calendar getDataFine() {
        return dataFine;
    }


    /**
     * Sets the dataFine value for this ProgettiRicercaForestale.
     * 
     * @param dataFine
     */
    public void setDataFine(java.util.Calendar dataFine) {
        this.dataFine = dataFine;
    }


    /**
     * Gets the dataInizio value for this ProgettiRicercaForestale.
     * 
     * @return dataInizio
     */
    public java.util.Calendar getDataInizio() {
        return dataInizio;
    }


    /**
     * Sets the dataInizio value for this ProgettiRicercaForestale.
     * 
     * @param dataInizio
     */
    public void setDataInizio(java.util.Calendar dataInizio) {
        this.dataInizio = dataInizio;
    }


    /**
     * Gets the denominazione value for this ProgettiRicercaForestale.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this ProgettiRicercaForestale.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the ente value for this ProgettiRicercaForestale.
     * 
     * @return ente
     */
    public java.lang.String getEnte() {
        return ente;
    }


    /**
     * Sets the ente value for this ProgettiRicercaForestale.
     * 
     * @param ente
     */
    public void setEnte(java.lang.String ente) {
        this.ente = ente;
    }


    /**
     * Gets the finanziamento value for this ProgettiRicercaForestale.
     * 
     * @return finanziamento
     */
    public java.lang.String getFinanziamento() {
        return finanziamento;
    }


    /**
     * Sets the finanziamento value for this ProgettiRicercaForestale.
     * 
     * @param finanziamento
     */
    public void setFinanziamento(java.lang.String finanziamento) {
        this.finanziamento = finanziamento;
    }


    /**
     * Gets the idProgetto value for this ProgettiRicercaForestale.
     * 
     * @return idProgetto
     */
    public java.lang.Integer getIdProgetto() {
        return idProgetto;
    }


    /**
     * Sets the idProgetto value for this ProgettiRicercaForestale.
     * 
     * @param idProgetto
     */
    public void setIdProgetto(java.lang.Integer idProgetto) {
        this.idProgetto = idProgetto;
    }


    /**
     * Gets the partecipanti value for this ProgettiRicercaForestale.
     * 
     * @return partecipanti
     */
    public java.lang.String getPartecipanti() {
        return partecipanti;
    }


    /**
     * Sets the partecipanti value for this ProgettiRicercaForestale.
     * 
     * @param partecipanti
     */
    public void setPartecipanti(java.lang.String partecipanti) {
        this.partecipanti = partecipanti;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProgettiRicercaForestale)) return false;
        ProgettiRicercaForestale other = (ProgettiRicercaForestale) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.dataFine==null && other.getDataFine()==null) || 
             (this.dataFine!=null &&
              this.dataFine.equals(other.getDataFine()))) &&
            ((this.dataInizio==null && other.getDataInizio()==null) || 
             (this.dataInizio!=null &&
              this.dataInizio.equals(other.getDataInizio()))) &&
            ((this.denominazione==null && other.getDenominazione()==null) || 
             (this.denominazione!=null &&
              this.denominazione.equals(other.getDenominazione()))) &&
            ((this.ente==null && other.getEnte()==null) || 
             (this.ente!=null &&
              this.ente.equals(other.getEnte()))) &&
            ((this.finanziamento==null && other.getFinanziamento()==null) || 
             (this.finanziamento!=null &&
              this.finanziamento.equals(other.getFinanziamento()))) &&
            ((this.idProgetto==null && other.getIdProgetto()==null) || 
             (this.idProgetto!=null &&
              this.idProgetto.equals(other.getIdProgetto()))) &&
            ((this.partecipanti==null && other.getPartecipanti()==null) || 
             (this.partecipanti!=null &&
              this.partecipanti.equals(other.getPartecipanti())));
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
        if (getDataFine() != null) {
            _hashCode += getDataFine().hashCode();
        }
        if (getDataInizio() != null) {
            _hashCode += getDataInizio().hashCode();
        }
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
        }
        if (getEnte() != null) {
            _hashCode += getEnte().hashCode();
        }
        if (getFinanziamento() != null) {
            _hashCode += getFinanziamento().hashCode();
        }
        if (getIdProgetto() != null) {
            _hashCode += getIdProgetto().hashCode();
        }
        if (getPartecipanti() != null) {
            _hashCode += getPartecipanti().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProgettiRicercaForestale.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "progettiRicercaForestale"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataFine"));
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
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finanziamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "finanziamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idProgetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idProgetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partecipanti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "partecipanti"));
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
