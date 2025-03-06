/**
 * SorgOdore.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class SorgOdore  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String descrizione;

    private java.lang.String estensione;

    private java.lang.Integer idSorgOdore;

    private java.lang.String intensita;

    private java.lang.String localizzazione;

    private java.lang.String persistenza;

    private java.lang.String sistemiContenimento;

    private java.lang.String tipologia;

    public SorgOdore() {
    }

    public SorgOdore(
           java.lang.String descrizione,
           java.lang.String estensione,
           java.lang.Integer idSorgOdore,
           java.lang.String intensita,
           java.lang.String localizzazione,
           java.lang.String persistenza,
           java.lang.String sistemiContenimento,
           java.lang.String tipologia) {
        this.descrizione = descrizione;
        this.estensione = estensione;
        this.idSorgOdore = idSorgOdore;
        this.intensita = intensita;
        this.localizzazione = localizzazione;
        this.persistenza = persistenza;
        this.sistemiContenimento = sistemiContenimento;
        this.tipologia = tipologia;
    }


    /**
     * Gets the descrizione value for this SorgOdore.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this SorgOdore.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the estensione value for this SorgOdore.
     * 
     * @return estensione
     */
    public java.lang.String getEstensione() {
        return estensione;
    }


    /**
     * Sets the estensione value for this SorgOdore.
     * 
     * @param estensione
     */
    public void setEstensione(java.lang.String estensione) {
        this.estensione = estensione;
    }


    /**
     * Gets the idSorgOdore value for this SorgOdore.
     * 
     * @return idSorgOdore
     */
    public java.lang.Integer getIdSorgOdore() {
        return idSorgOdore;
    }


    /**
     * Sets the idSorgOdore value for this SorgOdore.
     * 
     * @param idSorgOdore
     */
    public void setIdSorgOdore(java.lang.Integer idSorgOdore) {
        this.idSorgOdore = idSorgOdore;
    }


    /**
     * Gets the intensita value for this SorgOdore.
     * 
     * @return intensita
     */
    public java.lang.String getIntensita() {
        return intensita;
    }


    /**
     * Sets the intensita value for this SorgOdore.
     * 
     * @param intensita
     */
    public void setIntensita(java.lang.String intensita) {
        this.intensita = intensita;
    }


    /**
     * Gets the localizzazione value for this SorgOdore.
     * 
     * @return localizzazione
     */
    public java.lang.String getLocalizzazione() {
        return localizzazione;
    }


    /**
     * Sets the localizzazione value for this SorgOdore.
     * 
     * @param localizzazione
     */
    public void setLocalizzazione(java.lang.String localizzazione) {
        this.localizzazione = localizzazione;
    }


    /**
     * Gets the persistenza value for this SorgOdore.
     * 
     * @return persistenza
     */
    public java.lang.String getPersistenza() {
        return persistenza;
    }


    /**
     * Sets the persistenza value for this SorgOdore.
     * 
     * @param persistenza
     */
    public void setPersistenza(java.lang.String persistenza) {
        this.persistenza = persistenza;
    }


    /**
     * Gets the sistemiContenimento value for this SorgOdore.
     * 
     * @return sistemiContenimento
     */
    public java.lang.String getSistemiContenimento() {
        return sistemiContenimento;
    }


    /**
     * Sets the sistemiContenimento value for this SorgOdore.
     * 
     * @param sistemiContenimento
     */
    public void setSistemiContenimento(java.lang.String sistemiContenimento) {
        this.sistemiContenimento = sistemiContenimento;
    }


    /**
     * Gets the tipologia value for this SorgOdore.
     * 
     * @return tipologia
     */
    public java.lang.String getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this SorgOdore.
     * 
     * @param tipologia
     */
    public void setTipologia(java.lang.String tipologia) {
        this.tipologia = tipologia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SorgOdore)) return false;
        SorgOdore other = (SorgOdore) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.estensione==null && other.getEstensione()==null) || 
             (this.estensione!=null &&
              this.estensione.equals(other.getEstensione()))) &&
            ((this.idSorgOdore==null && other.getIdSorgOdore()==null) || 
             (this.idSorgOdore!=null &&
              this.idSorgOdore.equals(other.getIdSorgOdore()))) &&
            ((this.intensita==null && other.getIntensita()==null) || 
             (this.intensita!=null &&
              this.intensita.equals(other.getIntensita()))) &&
            ((this.localizzazione==null && other.getLocalizzazione()==null) || 
             (this.localizzazione!=null &&
              this.localizzazione.equals(other.getLocalizzazione()))) &&
            ((this.persistenza==null && other.getPersistenza()==null) || 
             (this.persistenza!=null &&
              this.persistenza.equals(other.getPersistenza()))) &&
            ((this.sistemiContenimento==null && other.getSistemiContenimento()==null) || 
             (this.sistemiContenimento!=null &&
              this.sistemiContenimento.equals(other.getSistemiContenimento()))) &&
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
        int _hashCode = super.hashCode();
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getEstensione() != null) {
            _hashCode += getEstensione().hashCode();
        }
        if (getIdSorgOdore() != null) {
            _hashCode += getIdSorgOdore().hashCode();
        }
        if (getIntensita() != null) {
            _hashCode += getIntensita().hashCode();
        }
        if (getLocalizzazione() != null) {
            _hashCode += getLocalizzazione().hashCode();
        }
        if (getPersistenza() != null) {
            _hashCode += getPersistenza().hashCode();
        }
        if (getSistemiContenimento() != null) {
            _hashCode += getSistemiContenimento().hashCode();
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SorgOdore.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgOdore"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estensione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estensione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSorgOdore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idSorgOdore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("intensita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "intensita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localizzazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "localizzazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("persistenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "persistenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sistemiContenimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sistemiContenimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
