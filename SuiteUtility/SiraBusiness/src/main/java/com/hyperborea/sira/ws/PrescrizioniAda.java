/**
 * PrescrizioniAda.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class PrescrizioniAda  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.AttiDisposizioniAmm attiDisposizioniAmm;

    private java.util.Calendar dataScadenza;

    private java.lang.String descrizione;

    private java.lang.Integer frequenzaAnnuale;

    private java.lang.Integer idPAda;

    private java.lang.String tipoAzione;

    public PrescrizioniAda() {
    }

    public PrescrizioniAda(
           com.hyperborea.sira.ws.AttiDisposizioniAmm attiDisposizioniAmm,
           java.util.Calendar dataScadenza,
           java.lang.String descrizione,
           java.lang.Integer frequenzaAnnuale,
           java.lang.Integer idPAda,
           java.lang.String tipoAzione) {
        this.attiDisposizioniAmm = attiDisposizioniAmm;
        this.dataScadenza = dataScadenza;
        this.descrizione = descrizione;
        this.frequenzaAnnuale = frequenzaAnnuale;
        this.idPAda = idPAda;
        this.tipoAzione = tipoAzione;
    }


    /**
     * Gets the attiDisposizioniAmm value for this PrescrizioniAda.
     * 
     * @return attiDisposizioniAmm
     */
    public com.hyperborea.sira.ws.AttiDisposizioniAmm getAttiDisposizioniAmm() {
        return attiDisposizioniAmm;
    }


    /**
     * Sets the attiDisposizioniAmm value for this PrescrizioniAda.
     * 
     * @param attiDisposizioniAmm
     */
    public void setAttiDisposizioniAmm(com.hyperborea.sira.ws.AttiDisposizioniAmm attiDisposizioniAmm) {
        this.attiDisposizioniAmm = attiDisposizioniAmm;
    }


    /**
     * Gets the dataScadenza value for this PrescrizioniAda.
     * 
     * @return dataScadenza
     */
    public java.util.Calendar getDataScadenza() {
        return dataScadenza;
    }


    /**
     * Sets the dataScadenza value for this PrescrizioniAda.
     * 
     * @param dataScadenza
     */
    public void setDataScadenza(java.util.Calendar dataScadenza) {
        this.dataScadenza = dataScadenza;
    }


    /**
     * Gets the descrizione value for this PrescrizioniAda.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this PrescrizioniAda.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the frequenzaAnnuale value for this PrescrizioniAda.
     * 
     * @return frequenzaAnnuale
     */
    public java.lang.Integer getFrequenzaAnnuale() {
        return frequenzaAnnuale;
    }


    /**
     * Sets the frequenzaAnnuale value for this PrescrizioniAda.
     * 
     * @param frequenzaAnnuale
     */
    public void setFrequenzaAnnuale(java.lang.Integer frequenzaAnnuale) {
        this.frequenzaAnnuale = frequenzaAnnuale;
    }


    /**
     * Gets the idPAda value for this PrescrizioniAda.
     * 
     * @return idPAda
     */
    public java.lang.Integer getIdPAda() {
        return idPAda;
    }


    /**
     * Sets the idPAda value for this PrescrizioniAda.
     * 
     * @param idPAda
     */
    public void setIdPAda(java.lang.Integer idPAda) {
        this.idPAda = idPAda;
    }


    /**
     * Gets the tipoAzione value for this PrescrizioniAda.
     * 
     * @return tipoAzione
     */
    public java.lang.String getTipoAzione() {
        return tipoAzione;
    }


    /**
     * Sets the tipoAzione value for this PrescrizioniAda.
     * 
     * @param tipoAzione
     */
    public void setTipoAzione(java.lang.String tipoAzione) {
        this.tipoAzione = tipoAzione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PrescrizioniAda)) return false;
        PrescrizioniAda other = (PrescrizioniAda) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.attiDisposizioniAmm==null && other.getAttiDisposizioniAmm()==null) || 
             (this.attiDisposizioniAmm!=null &&
              this.attiDisposizioniAmm.equals(other.getAttiDisposizioniAmm()))) &&
            ((this.dataScadenza==null && other.getDataScadenza()==null) || 
             (this.dataScadenza!=null &&
              this.dataScadenza.equals(other.getDataScadenza()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.frequenzaAnnuale==null && other.getFrequenzaAnnuale()==null) || 
             (this.frequenzaAnnuale!=null &&
              this.frequenzaAnnuale.equals(other.getFrequenzaAnnuale()))) &&
            ((this.idPAda==null && other.getIdPAda()==null) || 
             (this.idPAda!=null &&
              this.idPAda.equals(other.getIdPAda()))) &&
            ((this.tipoAzione==null && other.getTipoAzione()==null) || 
             (this.tipoAzione!=null &&
              this.tipoAzione.equals(other.getTipoAzione())));
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
        if (getAttiDisposizioniAmm() != null) {
            _hashCode += getAttiDisposizioniAmm().hashCode();
        }
        if (getDataScadenza() != null) {
            _hashCode += getDataScadenza().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getFrequenzaAnnuale() != null) {
            _hashCode += getFrequenzaAnnuale().hashCode();
        }
        if (getIdPAda() != null) {
            _hashCode += getIdPAda().hashCode();
        }
        if (getTipoAzione() != null) {
            _hashCode += getTipoAzione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PrescrizioniAda.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prescrizioniAda"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attiDisposizioniAmm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attiDisposizioniAmm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attiDisposizioniAmm"));
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
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("frequenzaAnnuale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "frequenzaAnnuale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPAda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idPAda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoAzione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoAzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
