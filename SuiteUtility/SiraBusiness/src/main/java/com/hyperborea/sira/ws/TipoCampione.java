/**
 * TipoCampione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class TipoCampione  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer codice;

    private java.lang.String descrizione;

    private com.hyperborea.sira.ws.ProfiliProva[] profiliProvas;

    private com.hyperborea.sira.ws.TemiAmbientali temiAmbientali;

    private com.hyperborea.sira.ws.TipiMisure[] tipiMisures;

    public TipoCampione() {
    }

    public TipoCampione(
           java.lang.Integer codice,
           java.lang.String descrizione,
           com.hyperborea.sira.ws.ProfiliProva[] profiliProvas,
           com.hyperborea.sira.ws.TemiAmbientali temiAmbientali,
           com.hyperborea.sira.ws.TipiMisure[] tipiMisures) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.profiliProvas = profiliProvas;
        this.temiAmbientali = temiAmbientali;
        this.tipiMisures = tipiMisures;
    }


    /**
     * Gets the codice value for this TipoCampione.
     * 
     * @return codice
     */
    public java.lang.Integer getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this TipoCampione.
     * 
     * @param codice
     */
    public void setCodice(java.lang.Integer codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this TipoCampione.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this TipoCampione.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the profiliProvas value for this TipoCampione.
     * 
     * @return profiliProvas
     */
    public com.hyperborea.sira.ws.ProfiliProva[] getProfiliProvas() {
        return profiliProvas;
    }


    /**
     * Sets the profiliProvas value for this TipoCampione.
     * 
     * @param profiliProvas
     */
    public void setProfiliProvas(com.hyperborea.sira.ws.ProfiliProva[] profiliProvas) {
        this.profiliProvas = profiliProvas;
    }

    public com.hyperborea.sira.ws.ProfiliProva getProfiliProvas(int i) {
        return this.profiliProvas[i];
    }

    public void setProfiliProvas(int i, com.hyperborea.sira.ws.ProfiliProva _value) {
        this.profiliProvas[i] = _value;
    }


    /**
     * Gets the temiAmbientali value for this TipoCampione.
     * 
     * @return temiAmbientali
     */
    public com.hyperborea.sira.ws.TemiAmbientali getTemiAmbientali() {
        return temiAmbientali;
    }


    /**
     * Sets the temiAmbientali value for this TipoCampione.
     * 
     * @param temiAmbientali
     */
    public void setTemiAmbientali(com.hyperborea.sira.ws.TemiAmbientali temiAmbientali) {
        this.temiAmbientali = temiAmbientali;
    }


    /**
     * Gets the tipiMisures value for this TipoCampione.
     * 
     * @return tipiMisures
     */
    public com.hyperborea.sira.ws.TipiMisure[] getTipiMisures() {
        return tipiMisures;
    }


    /**
     * Sets the tipiMisures value for this TipoCampione.
     * 
     * @param tipiMisures
     */
    public void setTipiMisures(com.hyperborea.sira.ws.TipiMisure[] tipiMisures) {
        this.tipiMisures = tipiMisures;
    }

    public com.hyperborea.sira.ws.TipiMisure getTipiMisures(int i) {
        return this.tipiMisures[i];
    }

    public void setTipiMisures(int i, com.hyperborea.sira.ws.TipiMisure _value) {
        this.tipiMisures[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipoCampione)) return false;
        TipoCampione other = (TipoCampione) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.profiliProvas==null && other.getProfiliProvas()==null) || 
             (this.profiliProvas!=null &&
              java.util.Arrays.equals(this.profiliProvas, other.getProfiliProvas()))) &&
            ((this.temiAmbientali==null && other.getTemiAmbientali()==null) || 
             (this.temiAmbientali!=null &&
              this.temiAmbientali.equals(other.getTemiAmbientali()))) &&
            ((this.tipiMisures==null && other.getTipiMisures()==null) || 
             (this.tipiMisures!=null &&
              java.util.Arrays.equals(this.tipiMisures, other.getTipiMisures())));
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
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getProfiliProvas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProfiliProvas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProfiliProvas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTemiAmbientali() != null) {
            _hashCode += getTemiAmbientali().hashCode();
        }
        if (getTipiMisures() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTipiMisures());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTipiMisures(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipoCampione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipoCampione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profiliProvas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profiliProvas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "profiliProva"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("temiAmbientali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "temiAmbientali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "temiAmbientali"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipiMisures");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipiMisures"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiMisure"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
