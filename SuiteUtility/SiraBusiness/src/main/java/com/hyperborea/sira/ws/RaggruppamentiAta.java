/**
 * RaggruppamentiAta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class RaggruppamentiAta  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.AreeTematicheAmbientali areeTematicheAmbientali;

    private int attivo;

    private int blocco;

    private java.lang.Integer codice;

    private java.lang.String descrizione;

    private com.hyperborea.sira.ws.TipologieMotivazioni[] tipologieMotivazionis;

    public RaggruppamentiAta() {
    }

    public RaggruppamentiAta(
           com.hyperborea.sira.ws.AreeTematicheAmbientali areeTematicheAmbientali,
           int attivo,
           int blocco,
           java.lang.Integer codice,
           java.lang.String descrizione,
           com.hyperborea.sira.ws.TipologieMotivazioni[] tipologieMotivazionis) {
        this.areeTematicheAmbientali = areeTematicheAmbientali;
        this.attivo = attivo;
        this.blocco = blocco;
        this.codice = codice;
        this.descrizione = descrizione;
        this.tipologieMotivazionis = tipologieMotivazionis;
    }


    /**
     * Gets the areeTematicheAmbientali value for this RaggruppamentiAta.
     * 
     * @return areeTematicheAmbientali
     */
    public com.hyperborea.sira.ws.AreeTematicheAmbientali getAreeTematicheAmbientali() {
        return areeTematicheAmbientali;
    }


    /**
     * Sets the areeTematicheAmbientali value for this RaggruppamentiAta.
     * 
     * @param areeTematicheAmbientali
     */
    public void setAreeTematicheAmbientali(com.hyperborea.sira.ws.AreeTematicheAmbientali areeTematicheAmbientali) {
        this.areeTematicheAmbientali = areeTematicheAmbientali;
    }


    /**
     * Gets the attivo value for this RaggruppamentiAta.
     * 
     * @return attivo
     */
    public int getAttivo() {
        return attivo;
    }


    /**
     * Sets the attivo value for this RaggruppamentiAta.
     * 
     * @param attivo
     */
    public void setAttivo(int attivo) {
        this.attivo = attivo;
    }


    /**
     * Gets the blocco value for this RaggruppamentiAta.
     * 
     * @return blocco
     */
    public int getBlocco() {
        return blocco;
    }


    /**
     * Sets the blocco value for this RaggruppamentiAta.
     * 
     * @param blocco
     */
    public void setBlocco(int blocco) {
        this.blocco = blocco;
    }


    /**
     * Gets the codice value for this RaggruppamentiAta.
     * 
     * @return codice
     */
    public java.lang.Integer getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this RaggruppamentiAta.
     * 
     * @param codice
     */
    public void setCodice(java.lang.Integer codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this RaggruppamentiAta.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this RaggruppamentiAta.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the tipologieMotivazionis value for this RaggruppamentiAta.
     * 
     * @return tipologieMotivazionis
     */
    public com.hyperborea.sira.ws.TipologieMotivazioni[] getTipologieMotivazionis() {
        return tipologieMotivazionis;
    }


    /**
     * Sets the tipologieMotivazionis value for this RaggruppamentiAta.
     * 
     * @param tipologieMotivazionis
     */
    public void setTipologieMotivazionis(com.hyperborea.sira.ws.TipologieMotivazioni[] tipologieMotivazionis) {
        this.tipologieMotivazionis = tipologieMotivazionis;
    }

    public com.hyperborea.sira.ws.TipologieMotivazioni getTipologieMotivazionis(int i) {
        return this.tipologieMotivazionis[i];
    }

    public void setTipologieMotivazionis(int i, com.hyperborea.sira.ws.TipologieMotivazioni _value) {
        this.tipologieMotivazionis[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RaggruppamentiAta)) return false;
        RaggruppamentiAta other = (RaggruppamentiAta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.areeTematicheAmbientali==null && other.getAreeTematicheAmbientali()==null) || 
             (this.areeTematicheAmbientali!=null &&
              this.areeTematicheAmbientali.equals(other.getAreeTematicheAmbientali()))) &&
            this.attivo == other.getAttivo() &&
            this.blocco == other.getBlocco() &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.tipologieMotivazionis==null && other.getTipologieMotivazionis()==null) || 
             (this.tipologieMotivazionis!=null &&
              java.util.Arrays.equals(this.tipologieMotivazionis, other.getTipologieMotivazionis())));
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
        if (getAreeTematicheAmbientali() != null) {
            _hashCode += getAreeTematicheAmbientali().hashCode();
        }
        _hashCode += getAttivo();
        _hashCode += getBlocco();
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getTipologieMotivazionis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTipologieMotivazionis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTipologieMotivazionis(), i);
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
        new org.apache.axis.description.TypeDesc(RaggruppamentiAta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "raggruppamentiAta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areeTematicheAmbientali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "areeTematicheAmbientali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "areeTematicheAmbientali"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologieMotivazionis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologieMotivazionis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieMotivazioni"));
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
