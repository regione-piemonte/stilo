/**
 * VocAmQuantitaCompatto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class VocAmQuantitaCompatto  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer attivo;

    private java.lang.Integer blocco;

    private java.lang.Integer codice;

    private java.lang.String descrizione;

    private java.lang.Float punteggio;

    public VocAmQuantitaCompatto() {
    }

    public VocAmQuantitaCompatto(
           java.lang.Integer attivo,
           java.lang.Integer blocco,
           java.lang.Integer codice,
           java.lang.String descrizione,
           java.lang.Float punteggio) {
        this.attivo = attivo;
        this.blocco = blocco;
        this.codice = codice;
        this.descrizione = descrizione;
        this.punteggio = punteggio;
    }


    /**
     * Gets the attivo value for this VocAmQuantitaCompatto.
     * 
     * @return attivo
     */
    public java.lang.Integer getAttivo() {
        return attivo;
    }


    /**
     * Sets the attivo value for this VocAmQuantitaCompatto.
     * 
     * @param attivo
     */
    public void setAttivo(java.lang.Integer attivo) {
        this.attivo = attivo;
    }


    /**
     * Gets the blocco value for this VocAmQuantitaCompatto.
     * 
     * @return blocco
     */
    public java.lang.Integer getBlocco() {
        return blocco;
    }


    /**
     * Sets the blocco value for this VocAmQuantitaCompatto.
     * 
     * @param blocco
     */
    public void setBlocco(java.lang.Integer blocco) {
        this.blocco = blocco;
    }


    /**
     * Gets the codice value for this VocAmQuantitaCompatto.
     * 
     * @return codice
     */
    public java.lang.Integer getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this VocAmQuantitaCompatto.
     * 
     * @param codice
     */
    public void setCodice(java.lang.Integer codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this VocAmQuantitaCompatto.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this VocAmQuantitaCompatto.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the punteggio value for this VocAmQuantitaCompatto.
     * 
     * @return punteggio
     */
    public java.lang.Float getPunteggio() {
        return punteggio;
    }


    /**
     * Sets the punteggio value for this VocAmQuantitaCompatto.
     * 
     * @param punteggio
     */
    public void setPunteggio(java.lang.Float punteggio) {
        this.punteggio = punteggio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VocAmQuantitaCompatto)) return false;
        VocAmQuantitaCompatto other = (VocAmQuantitaCompatto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.attivo==null && other.getAttivo()==null) || 
             (this.attivo!=null &&
              this.attivo.equals(other.getAttivo()))) &&
            ((this.blocco==null && other.getBlocco()==null) || 
             (this.blocco!=null &&
              this.blocco.equals(other.getBlocco()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.punteggio==null && other.getPunteggio()==null) || 
             (this.punteggio!=null &&
              this.punteggio.equals(other.getPunteggio())));
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
        if (getAttivo() != null) {
            _hashCode += getAttivo().hashCode();
        }
        if (getBlocco() != null) {
            _hashCode += getBlocco().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getPunteggio() != null) {
            _hashCode += getPunteggio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VocAmQuantitaCompatto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmQuantitaCompatto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blocco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "blocco"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
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
        elemField.setFieldName("punteggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "punteggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
