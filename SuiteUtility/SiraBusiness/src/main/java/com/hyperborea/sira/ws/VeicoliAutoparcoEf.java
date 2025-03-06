/**
 * VeicoliAutoparcoEf.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class VeicoliAutoparcoEf  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idVeicolo;

    private java.lang.String noteRicovero;

    private java.lang.Integer ricoveroCapacita;

    private java.lang.Integer ricoveroEffettivo;

    private com.hyperborea.sira.ws.VocEfTipologiaVeicolo vocEfTipologiaVeicolo;

    public VeicoliAutoparcoEf() {
    }

    public VeicoliAutoparcoEf(
           java.lang.Integer idVeicolo,
           java.lang.String noteRicovero,
           java.lang.Integer ricoveroCapacita,
           java.lang.Integer ricoveroEffettivo,
           com.hyperborea.sira.ws.VocEfTipologiaVeicolo vocEfTipologiaVeicolo) {
        this.idVeicolo = idVeicolo;
        this.noteRicovero = noteRicovero;
        this.ricoveroCapacita = ricoveroCapacita;
        this.ricoveroEffettivo = ricoveroEffettivo;
        this.vocEfTipologiaVeicolo = vocEfTipologiaVeicolo;
    }


    /**
     * Gets the idVeicolo value for this VeicoliAutoparcoEf.
     * 
     * @return idVeicolo
     */
    public java.lang.Integer getIdVeicolo() {
        return idVeicolo;
    }


    /**
     * Sets the idVeicolo value for this VeicoliAutoparcoEf.
     * 
     * @param idVeicolo
     */
    public void setIdVeicolo(java.lang.Integer idVeicolo) {
        this.idVeicolo = idVeicolo;
    }


    /**
     * Gets the noteRicovero value for this VeicoliAutoparcoEf.
     * 
     * @return noteRicovero
     */
    public java.lang.String getNoteRicovero() {
        return noteRicovero;
    }


    /**
     * Sets the noteRicovero value for this VeicoliAutoparcoEf.
     * 
     * @param noteRicovero
     */
    public void setNoteRicovero(java.lang.String noteRicovero) {
        this.noteRicovero = noteRicovero;
    }


    /**
     * Gets the ricoveroCapacita value for this VeicoliAutoparcoEf.
     * 
     * @return ricoveroCapacita
     */
    public java.lang.Integer getRicoveroCapacita() {
        return ricoveroCapacita;
    }


    /**
     * Sets the ricoveroCapacita value for this VeicoliAutoparcoEf.
     * 
     * @param ricoveroCapacita
     */
    public void setRicoveroCapacita(java.lang.Integer ricoveroCapacita) {
        this.ricoveroCapacita = ricoveroCapacita;
    }


    /**
     * Gets the ricoveroEffettivo value for this VeicoliAutoparcoEf.
     * 
     * @return ricoveroEffettivo
     */
    public java.lang.Integer getRicoveroEffettivo() {
        return ricoveroEffettivo;
    }


    /**
     * Sets the ricoveroEffettivo value for this VeicoliAutoparcoEf.
     * 
     * @param ricoveroEffettivo
     */
    public void setRicoveroEffettivo(java.lang.Integer ricoveroEffettivo) {
        this.ricoveroEffettivo = ricoveroEffettivo;
    }


    /**
     * Gets the vocEfTipologiaVeicolo value for this VeicoliAutoparcoEf.
     * 
     * @return vocEfTipologiaVeicolo
     */
    public com.hyperborea.sira.ws.VocEfTipologiaVeicolo getVocEfTipologiaVeicolo() {
        return vocEfTipologiaVeicolo;
    }


    /**
     * Sets the vocEfTipologiaVeicolo value for this VeicoliAutoparcoEf.
     * 
     * @param vocEfTipologiaVeicolo
     */
    public void setVocEfTipologiaVeicolo(com.hyperborea.sira.ws.VocEfTipologiaVeicolo vocEfTipologiaVeicolo) {
        this.vocEfTipologiaVeicolo = vocEfTipologiaVeicolo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VeicoliAutoparcoEf)) return false;
        VeicoliAutoparcoEf other = (VeicoliAutoparcoEf) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idVeicolo==null && other.getIdVeicolo()==null) || 
             (this.idVeicolo!=null &&
              this.idVeicolo.equals(other.getIdVeicolo()))) &&
            ((this.noteRicovero==null && other.getNoteRicovero()==null) || 
             (this.noteRicovero!=null &&
              this.noteRicovero.equals(other.getNoteRicovero()))) &&
            ((this.ricoveroCapacita==null && other.getRicoveroCapacita()==null) || 
             (this.ricoveroCapacita!=null &&
              this.ricoveroCapacita.equals(other.getRicoveroCapacita()))) &&
            ((this.ricoveroEffettivo==null && other.getRicoveroEffettivo()==null) || 
             (this.ricoveroEffettivo!=null &&
              this.ricoveroEffettivo.equals(other.getRicoveroEffettivo()))) &&
            ((this.vocEfTipologiaVeicolo==null && other.getVocEfTipologiaVeicolo()==null) || 
             (this.vocEfTipologiaVeicolo!=null &&
              this.vocEfTipologiaVeicolo.equals(other.getVocEfTipologiaVeicolo())));
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
        if (getIdVeicolo() != null) {
            _hashCode += getIdVeicolo().hashCode();
        }
        if (getNoteRicovero() != null) {
            _hashCode += getNoteRicovero().hashCode();
        }
        if (getRicoveroCapacita() != null) {
            _hashCode += getRicoveroCapacita().hashCode();
        }
        if (getRicoveroEffettivo() != null) {
            _hashCode += getRicoveroEffettivo().hashCode();
        }
        if (getVocEfTipologiaVeicolo() != null) {
            _hashCode += getVocEfTipologiaVeicolo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VeicoliAutoparcoEf.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "veicoliAutoparcoEf"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idVeicolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idVeicolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noteRicovero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "noteRicovero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ricoveroCapacita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ricoveroCapacita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ricoveroEffettivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ricoveroEffettivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfTipologiaVeicolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfTipologiaVeicolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipologiaVeicolo"));
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
