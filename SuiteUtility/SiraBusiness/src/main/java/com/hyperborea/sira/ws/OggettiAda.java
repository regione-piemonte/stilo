/**
 * OggettiAda.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class OggettiAda  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.AttiDisposizioniAmm[] attiDisposizioniAmms;

    private int attivo;

    private int blocco;

    private java.lang.Integer codice;

    private java.lang.String descrizione;

    private int durata;

    private com.hyperborea.sira.ws.TemiAmbientali temiAmbientali;

    public OggettiAda() {
    }

    public OggettiAda(
           com.hyperborea.sira.ws.AttiDisposizioniAmm[] attiDisposizioniAmms,
           int attivo,
           int blocco,
           java.lang.Integer codice,
           java.lang.String descrizione,
           int durata,
           com.hyperborea.sira.ws.TemiAmbientali temiAmbientali) {
        this.attiDisposizioniAmms = attiDisposizioniAmms;
        this.attivo = attivo;
        this.blocco = blocco;
        this.codice = codice;
        this.descrizione = descrizione;
        this.durata = durata;
        this.temiAmbientali = temiAmbientali;
    }


    /**
     * Gets the attiDisposizioniAmms value for this OggettiAda.
     * 
     * @return attiDisposizioniAmms
     */
    public com.hyperborea.sira.ws.AttiDisposizioniAmm[] getAttiDisposizioniAmms() {
        return attiDisposizioniAmms;
    }


    /**
     * Sets the attiDisposizioniAmms value for this OggettiAda.
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
     * Gets the attivo value for this OggettiAda.
     * 
     * @return attivo
     */
    public int getAttivo() {
        return attivo;
    }


    /**
     * Sets the attivo value for this OggettiAda.
     * 
     * @param attivo
     */
    public void setAttivo(int attivo) {
        this.attivo = attivo;
    }


    /**
     * Gets the blocco value for this OggettiAda.
     * 
     * @return blocco
     */
    public int getBlocco() {
        return blocco;
    }


    /**
     * Sets the blocco value for this OggettiAda.
     * 
     * @param blocco
     */
    public void setBlocco(int blocco) {
        this.blocco = blocco;
    }


    /**
     * Gets the codice value for this OggettiAda.
     * 
     * @return codice
     */
    public java.lang.Integer getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this OggettiAda.
     * 
     * @param codice
     */
    public void setCodice(java.lang.Integer codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this OggettiAda.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this OggettiAda.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the durata value for this OggettiAda.
     * 
     * @return durata
     */
    public int getDurata() {
        return durata;
    }


    /**
     * Sets the durata value for this OggettiAda.
     * 
     * @param durata
     */
    public void setDurata(int durata) {
        this.durata = durata;
    }


    /**
     * Gets the temiAmbientali value for this OggettiAda.
     * 
     * @return temiAmbientali
     */
    public com.hyperborea.sira.ws.TemiAmbientali getTemiAmbientali() {
        return temiAmbientali;
    }


    /**
     * Sets the temiAmbientali value for this OggettiAda.
     * 
     * @param temiAmbientali
     */
    public void setTemiAmbientali(com.hyperborea.sira.ws.TemiAmbientali temiAmbientali) {
        this.temiAmbientali = temiAmbientali;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OggettiAda)) return false;
        OggettiAda other = (OggettiAda) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.attiDisposizioniAmms==null && other.getAttiDisposizioniAmms()==null) || 
             (this.attiDisposizioniAmms!=null &&
              java.util.Arrays.equals(this.attiDisposizioniAmms, other.getAttiDisposizioniAmms()))) &&
            this.attivo == other.getAttivo() &&
            this.blocco == other.getBlocco() &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            this.durata == other.getDurata() &&
            ((this.temiAmbientali==null && other.getTemiAmbientali()==null) || 
             (this.temiAmbientali!=null &&
              this.temiAmbientali.equals(other.getTemiAmbientali())));
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
        _hashCode += getAttivo();
        _hashCode += getBlocco();
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        _hashCode += getDurata();
        if (getTemiAmbientali() != null) {
            _hashCode += getTemiAmbientali().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OggettiAda.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "oggettiAda"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attiDisposizioniAmms");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attiDisposizioniAmms"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attiDisposizioniAmm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blocco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "blocco"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("durata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "durata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("temiAmbientali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "temiAmbientali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "temiAmbientali"));
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
