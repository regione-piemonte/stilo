/**
 * CcostFasciaParafuoco.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostFasciaParafuoco  implements java.io.Serializable {
    private java.lang.Integer annoUltimoIntervento;

    private java.lang.String cfva;

    private java.lang.String cop;

    private java.lang.Integer idCcost;

    private java.lang.Float larghezza;

    private java.lang.Float lunghezza;

    private java.lang.String tipoUltimoIntervento;

    private com.hyperborea.sira.ws.VocTipologiaFasciaParafuoco vocTipologiaFasciaParafuoco;

    private com.hyperborea.sira.ws.VocTitoloGestione vocTitoloGestione;

    public CcostFasciaParafuoco() {
    }

    public CcostFasciaParafuoco(
           java.lang.Integer annoUltimoIntervento,
           java.lang.String cfva,
           java.lang.String cop,
           java.lang.Integer idCcost,
           java.lang.Float larghezza,
           java.lang.Float lunghezza,
           java.lang.String tipoUltimoIntervento,
           com.hyperborea.sira.ws.VocTipologiaFasciaParafuoco vocTipologiaFasciaParafuoco,
           com.hyperborea.sira.ws.VocTitoloGestione vocTitoloGestione) {
           this.annoUltimoIntervento = annoUltimoIntervento;
           this.cfva = cfva;
           this.cop = cop;
           this.idCcost = idCcost;
           this.larghezza = larghezza;
           this.lunghezza = lunghezza;
           this.tipoUltimoIntervento = tipoUltimoIntervento;
           this.vocTipologiaFasciaParafuoco = vocTipologiaFasciaParafuoco;
           this.vocTitoloGestione = vocTitoloGestione;
    }


    /**
     * Gets the annoUltimoIntervento value for this CcostFasciaParafuoco.
     * 
     * @return annoUltimoIntervento
     */
    public java.lang.Integer getAnnoUltimoIntervento() {
        return annoUltimoIntervento;
    }


    /**
     * Sets the annoUltimoIntervento value for this CcostFasciaParafuoco.
     * 
     * @param annoUltimoIntervento
     */
    public void setAnnoUltimoIntervento(java.lang.Integer annoUltimoIntervento) {
        this.annoUltimoIntervento = annoUltimoIntervento;
    }


    /**
     * Gets the cfva value for this CcostFasciaParafuoco.
     * 
     * @return cfva
     */
    public java.lang.String getCfva() {
        return cfva;
    }


    /**
     * Sets the cfva value for this CcostFasciaParafuoco.
     * 
     * @param cfva
     */
    public void setCfva(java.lang.String cfva) {
        this.cfva = cfva;
    }


    /**
     * Gets the cop value for this CcostFasciaParafuoco.
     * 
     * @return cop
     */
    public java.lang.String getCop() {
        return cop;
    }


    /**
     * Sets the cop value for this CcostFasciaParafuoco.
     * 
     * @param cop
     */
    public void setCop(java.lang.String cop) {
        this.cop = cop;
    }


    /**
     * Gets the idCcost value for this CcostFasciaParafuoco.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostFasciaParafuoco.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the larghezza value for this CcostFasciaParafuoco.
     * 
     * @return larghezza
     */
    public java.lang.Float getLarghezza() {
        return larghezza;
    }


    /**
     * Sets the larghezza value for this CcostFasciaParafuoco.
     * 
     * @param larghezza
     */
    public void setLarghezza(java.lang.Float larghezza) {
        this.larghezza = larghezza;
    }


    /**
     * Gets the lunghezza value for this CcostFasciaParafuoco.
     * 
     * @return lunghezza
     */
    public java.lang.Float getLunghezza() {
        return lunghezza;
    }


    /**
     * Sets the lunghezza value for this CcostFasciaParafuoco.
     * 
     * @param lunghezza
     */
    public void setLunghezza(java.lang.Float lunghezza) {
        this.lunghezza = lunghezza;
    }


    /**
     * Gets the tipoUltimoIntervento value for this CcostFasciaParafuoco.
     * 
     * @return tipoUltimoIntervento
     */
    public java.lang.String getTipoUltimoIntervento() {
        return tipoUltimoIntervento;
    }


    /**
     * Sets the tipoUltimoIntervento value for this CcostFasciaParafuoco.
     * 
     * @param tipoUltimoIntervento
     */
    public void setTipoUltimoIntervento(java.lang.String tipoUltimoIntervento) {
        this.tipoUltimoIntervento = tipoUltimoIntervento;
    }


    /**
     * Gets the vocTipologiaFasciaParafuoco value for this CcostFasciaParafuoco.
     * 
     * @return vocTipologiaFasciaParafuoco
     */
    public com.hyperborea.sira.ws.VocTipologiaFasciaParafuoco getVocTipologiaFasciaParafuoco() {
        return vocTipologiaFasciaParafuoco;
    }


    /**
     * Sets the vocTipologiaFasciaParafuoco value for this CcostFasciaParafuoco.
     * 
     * @param vocTipologiaFasciaParafuoco
     */
    public void setVocTipologiaFasciaParafuoco(com.hyperborea.sira.ws.VocTipologiaFasciaParafuoco vocTipologiaFasciaParafuoco) {
        this.vocTipologiaFasciaParafuoco = vocTipologiaFasciaParafuoco;
    }


    /**
     * Gets the vocTitoloGestione value for this CcostFasciaParafuoco.
     * 
     * @return vocTitoloGestione
     */
    public com.hyperborea.sira.ws.VocTitoloGestione getVocTitoloGestione() {
        return vocTitoloGestione;
    }


    /**
     * Sets the vocTitoloGestione value for this CcostFasciaParafuoco.
     * 
     * @param vocTitoloGestione
     */
    public void setVocTitoloGestione(com.hyperborea.sira.ws.VocTitoloGestione vocTitoloGestione) {
        this.vocTitoloGestione = vocTitoloGestione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostFasciaParafuoco)) return false;
        CcostFasciaParafuoco other = (CcostFasciaParafuoco) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.annoUltimoIntervento==null && other.getAnnoUltimoIntervento()==null) || 
             (this.annoUltimoIntervento!=null &&
              this.annoUltimoIntervento.equals(other.getAnnoUltimoIntervento()))) &&
            ((this.cfva==null && other.getCfva()==null) || 
             (this.cfva!=null &&
              this.cfva.equals(other.getCfva()))) &&
            ((this.cop==null && other.getCop()==null) || 
             (this.cop!=null &&
              this.cop.equals(other.getCop()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.larghezza==null && other.getLarghezza()==null) || 
             (this.larghezza!=null &&
              this.larghezza.equals(other.getLarghezza()))) &&
            ((this.lunghezza==null && other.getLunghezza()==null) || 
             (this.lunghezza!=null &&
              this.lunghezza.equals(other.getLunghezza()))) &&
            ((this.tipoUltimoIntervento==null && other.getTipoUltimoIntervento()==null) || 
             (this.tipoUltimoIntervento!=null &&
              this.tipoUltimoIntervento.equals(other.getTipoUltimoIntervento()))) &&
            ((this.vocTipologiaFasciaParafuoco==null && other.getVocTipologiaFasciaParafuoco()==null) || 
             (this.vocTipologiaFasciaParafuoco!=null &&
              this.vocTipologiaFasciaParafuoco.equals(other.getVocTipologiaFasciaParafuoco()))) &&
            ((this.vocTitoloGestione==null && other.getVocTitoloGestione()==null) || 
             (this.vocTitoloGestione!=null &&
              this.vocTitoloGestione.equals(other.getVocTitoloGestione())));
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
        if (getAnnoUltimoIntervento() != null) {
            _hashCode += getAnnoUltimoIntervento().hashCode();
        }
        if (getCfva() != null) {
            _hashCode += getCfva().hashCode();
        }
        if (getCop() != null) {
            _hashCode += getCop().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getLarghezza() != null) {
            _hashCode += getLarghezza().hashCode();
        }
        if (getLunghezza() != null) {
            _hashCode += getLunghezza().hashCode();
        }
        if (getTipoUltimoIntervento() != null) {
            _hashCode += getTipoUltimoIntervento().hashCode();
        }
        if (getVocTipologiaFasciaParafuoco() != null) {
            _hashCode += getVocTipologiaFasciaParafuoco().hashCode();
        }
        if (getVocTitoloGestione() != null) {
            _hashCode += getVocTitoloGestione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostFasciaParafuoco.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostFasciaParafuoco"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoUltimoIntervento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoUltimoIntervento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cfva");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cfva"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cop");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cop"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("larghezza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "larghezza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lunghezza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lunghezza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoUltimoIntervento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoUltimoIntervento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaFasciaParafuoco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaFasciaParafuoco"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaFasciaParafuoco"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTitoloGestione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTitoloGestione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTitoloGestione"));
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
