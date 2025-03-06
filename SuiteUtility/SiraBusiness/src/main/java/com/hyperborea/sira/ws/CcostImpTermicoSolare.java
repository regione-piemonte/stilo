/**
 * CcostImpTermicoSolare.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostImpTermicoSolare  implements java.io.Serializable {
    private java.lang.Integer idCcost;

    private java.lang.Integer impiantoAInseguimento;

    private java.lang.Float superficieCaptante;

    private java.lang.Float superficieSuolo;

    private com.hyperborea.sira.ws.VocIntegrazione vocIntegrazione;

    private com.hyperborea.sira.ws.VocPosizioneImpianto vocPosizioneImpianto;

    private com.hyperborea.sira.ws.VocTipologiaPannelli vocTipologiaPannelli;

    public CcostImpTermicoSolare() {
    }

    public CcostImpTermicoSolare(
           java.lang.Integer idCcost,
           java.lang.Integer impiantoAInseguimento,
           java.lang.Float superficieCaptante,
           java.lang.Float superficieSuolo,
           com.hyperborea.sira.ws.VocIntegrazione vocIntegrazione,
           com.hyperborea.sira.ws.VocPosizioneImpianto vocPosizioneImpianto,
           com.hyperborea.sira.ws.VocTipologiaPannelli vocTipologiaPannelli) {
           this.idCcost = idCcost;
           this.impiantoAInseguimento = impiantoAInseguimento;
           this.superficieCaptante = superficieCaptante;
           this.superficieSuolo = superficieSuolo;
           this.vocIntegrazione = vocIntegrazione;
           this.vocPosizioneImpianto = vocPosizioneImpianto;
           this.vocTipologiaPannelli = vocTipologiaPannelli;
    }


    /**
     * Gets the idCcost value for this CcostImpTermicoSolare.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostImpTermicoSolare.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the impiantoAInseguimento value for this CcostImpTermicoSolare.
     * 
     * @return impiantoAInseguimento
     */
    public java.lang.Integer getImpiantoAInseguimento() {
        return impiantoAInseguimento;
    }


    /**
     * Sets the impiantoAInseguimento value for this CcostImpTermicoSolare.
     * 
     * @param impiantoAInseguimento
     */
    public void setImpiantoAInseguimento(java.lang.Integer impiantoAInseguimento) {
        this.impiantoAInseguimento = impiantoAInseguimento;
    }


    /**
     * Gets the superficieCaptante value for this CcostImpTermicoSolare.
     * 
     * @return superficieCaptante
     */
    public java.lang.Float getSuperficieCaptante() {
        return superficieCaptante;
    }


    /**
     * Sets the superficieCaptante value for this CcostImpTermicoSolare.
     * 
     * @param superficieCaptante
     */
    public void setSuperficieCaptante(java.lang.Float superficieCaptante) {
        this.superficieCaptante = superficieCaptante;
    }


    /**
     * Gets the superficieSuolo value for this CcostImpTermicoSolare.
     * 
     * @return superficieSuolo
     */
    public java.lang.Float getSuperficieSuolo() {
        return superficieSuolo;
    }


    /**
     * Sets the superficieSuolo value for this CcostImpTermicoSolare.
     * 
     * @param superficieSuolo
     */
    public void setSuperficieSuolo(java.lang.Float superficieSuolo) {
        this.superficieSuolo = superficieSuolo;
    }


    /**
     * Gets the vocIntegrazione value for this CcostImpTermicoSolare.
     * 
     * @return vocIntegrazione
     */
    public com.hyperborea.sira.ws.VocIntegrazione getVocIntegrazione() {
        return vocIntegrazione;
    }


    /**
     * Sets the vocIntegrazione value for this CcostImpTermicoSolare.
     * 
     * @param vocIntegrazione
     */
    public void setVocIntegrazione(com.hyperborea.sira.ws.VocIntegrazione vocIntegrazione) {
        this.vocIntegrazione = vocIntegrazione;
    }


    /**
     * Gets the vocPosizioneImpianto value for this CcostImpTermicoSolare.
     * 
     * @return vocPosizioneImpianto
     */
    public com.hyperborea.sira.ws.VocPosizioneImpianto getVocPosizioneImpianto() {
        return vocPosizioneImpianto;
    }


    /**
     * Sets the vocPosizioneImpianto value for this CcostImpTermicoSolare.
     * 
     * @param vocPosizioneImpianto
     */
    public void setVocPosizioneImpianto(com.hyperborea.sira.ws.VocPosizioneImpianto vocPosizioneImpianto) {
        this.vocPosizioneImpianto = vocPosizioneImpianto;
    }


    /**
     * Gets the vocTipologiaPannelli value for this CcostImpTermicoSolare.
     * 
     * @return vocTipologiaPannelli
     */
    public com.hyperborea.sira.ws.VocTipologiaPannelli getVocTipologiaPannelli() {
        return vocTipologiaPannelli;
    }


    /**
     * Sets the vocTipologiaPannelli value for this CcostImpTermicoSolare.
     * 
     * @param vocTipologiaPannelli
     */
    public void setVocTipologiaPannelli(com.hyperborea.sira.ws.VocTipologiaPannelli vocTipologiaPannelli) {
        this.vocTipologiaPannelli = vocTipologiaPannelli;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostImpTermicoSolare)) return false;
        CcostImpTermicoSolare other = (CcostImpTermicoSolare) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.impiantoAInseguimento==null && other.getImpiantoAInseguimento()==null) || 
             (this.impiantoAInseguimento!=null &&
              this.impiantoAInseguimento.equals(other.getImpiantoAInseguimento()))) &&
            ((this.superficieCaptante==null && other.getSuperficieCaptante()==null) || 
             (this.superficieCaptante!=null &&
              this.superficieCaptante.equals(other.getSuperficieCaptante()))) &&
            ((this.superficieSuolo==null && other.getSuperficieSuolo()==null) || 
             (this.superficieSuolo!=null &&
              this.superficieSuolo.equals(other.getSuperficieSuolo()))) &&
            ((this.vocIntegrazione==null && other.getVocIntegrazione()==null) || 
             (this.vocIntegrazione!=null &&
              this.vocIntegrazione.equals(other.getVocIntegrazione()))) &&
            ((this.vocPosizioneImpianto==null && other.getVocPosizioneImpianto()==null) || 
             (this.vocPosizioneImpianto!=null &&
              this.vocPosizioneImpianto.equals(other.getVocPosizioneImpianto()))) &&
            ((this.vocTipologiaPannelli==null && other.getVocTipologiaPannelli()==null) || 
             (this.vocTipologiaPannelli!=null &&
              this.vocTipologiaPannelli.equals(other.getVocTipologiaPannelli())));
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
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getImpiantoAInseguimento() != null) {
            _hashCode += getImpiantoAInseguimento().hashCode();
        }
        if (getSuperficieCaptante() != null) {
            _hashCode += getSuperficieCaptante().hashCode();
        }
        if (getSuperficieSuolo() != null) {
            _hashCode += getSuperficieSuolo().hashCode();
        }
        if (getVocIntegrazione() != null) {
            _hashCode += getVocIntegrazione().hashCode();
        }
        if (getVocPosizioneImpianto() != null) {
            _hashCode += getVocPosizioneImpianto().hashCode();
        }
        if (getVocTipologiaPannelli() != null) {
            _hashCode += getVocTipologiaPannelli().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostImpTermicoSolare.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpTermicoSolare"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("impiantoAInseguimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "impiantoAInseguimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieCaptante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieCaptante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieSuolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieSuolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocIntegrazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocIntegrazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocIntegrazione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocPosizioneImpianto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocPosizioneImpianto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocPosizioneImpianto"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaPannelli");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaPannelli"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaPannelli"));
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
