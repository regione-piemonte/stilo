/**
 * CcostStazioniSar.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostStazioniSar  implements java.io.Serializable {
    private java.lang.String classeMinisteriale;

    private java.lang.String codiceIcao;

    private java.lang.String codiceWmo;

    private java.lang.Integer idCcost;

    private java.lang.String selezioneNazionale;

    private java.lang.String tipoStazione;

    public CcostStazioniSar() {
    }

    public CcostStazioniSar(
           java.lang.String classeMinisteriale,
           java.lang.String codiceIcao,
           java.lang.String codiceWmo,
           java.lang.Integer idCcost,
           java.lang.String selezioneNazionale,
           java.lang.String tipoStazione) {
           this.classeMinisteriale = classeMinisteriale;
           this.codiceIcao = codiceIcao;
           this.codiceWmo = codiceWmo;
           this.idCcost = idCcost;
           this.selezioneNazionale = selezioneNazionale;
           this.tipoStazione = tipoStazione;
    }


    /**
     * Gets the classeMinisteriale value for this CcostStazioniSar.
     * 
     * @return classeMinisteriale
     */
    public java.lang.String getClasseMinisteriale() {
        return classeMinisteriale;
    }


    /**
     * Sets the classeMinisteriale value for this CcostStazioniSar.
     * 
     * @param classeMinisteriale
     */
    public void setClasseMinisteriale(java.lang.String classeMinisteriale) {
        this.classeMinisteriale = classeMinisteriale;
    }


    /**
     * Gets the codiceIcao value for this CcostStazioniSar.
     * 
     * @return codiceIcao
     */
    public java.lang.String getCodiceIcao() {
        return codiceIcao;
    }


    /**
     * Sets the codiceIcao value for this CcostStazioniSar.
     * 
     * @param codiceIcao
     */
    public void setCodiceIcao(java.lang.String codiceIcao) {
        this.codiceIcao = codiceIcao;
    }


    /**
     * Gets the codiceWmo value for this CcostStazioniSar.
     * 
     * @return codiceWmo
     */
    public java.lang.String getCodiceWmo() {
        return codiceWmo;
    }


    /**
     * Sets the codiceWmo value for this CcostStazioniSar.
     * 
     * @param codiceWmo
     */
    public void setCodiceWmo(java.lang.String codiceWmo) {
        this.codiceWmo = codiceWmo;
    }


    /**
     * Gets the idCcost value for this CcostStazioniSar.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostStazioniSar.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the selezioneNazionale value for this CcostStazioniSar.
     * 
     * @return selezioneNazionale
     */
    public java.lang.String getSelezioneNazionale() {
        return selezioneNazionale;
    }


    /**
     * Sets the selezioneNazionale value for this CcostStazioniSar.
     * 
     * @param selezioneNazionale
     */
    public void setSelezioneNazionale(java.lang.String selezioneNazionale) {
        this.selezioneNazionale = selezioneNazionale;
    }


    /**
     * Gets the tipoStazione value for this CcostStazioniSar.
     * 
     * @return tipoStazione
     */
    public java.lang.String getTipoStazione() {
        return tipoStazione;
    }


    /**
     * Sets the tipoStazione value for this CcostStazioniSar.
     * 
     * @param tipoStazione
     */
    public void setTipoStazione(java.lang.String tipoStazione) {
        this.tipoStazione = tipoStazione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostStazioniSar)) return false;
        CcostStazioniSar other = (CcostStazioniSar) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.classeMinisteriale==null && other.getClasseMinisteriale()==null) || 
             (this.classeMinisteriale!=null &&
              this.classeMinisteriale.equals(other.getClasseMinisteriale()))) &&
            ((this.codiceIcao==null && other.getCodiceIcao()==null) || 
             (this.codiceIcao!=null &&
              this.codiceIcao.equals(other.getCodiceIcao()))) &&
            ((this.codiceWmo==null && other.getCodiceWmo()==null) || 
             (this.codiceWmo!=null &&
              this.codiceWmo.equals(other.getCodiceWmo()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.selezioneNazionale==null && other.getSelezioneNazionale()==null) || 
             (this.selezioneNazionale!=null &&
              this.selezioneNazionale.equals(other.getSelezioneNazionale()))) &&
            ((this.tipoStazione==null && other.getTipoStazione()==null) || 
             (this.tipoStazione!=null &&
              this.tipoStazione.equals(other.getTipoStazione())));
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
        if (getClasseMinisteriale() != null) {
            _hashCode += getClasseMinisteriale().hashCode();
        }
        if (getCodiceIcao() != null) {
            _hashCode += getCodiceIcao().hashCode();
        }
        if (getCodiceWmo() != null) {
            _hashCode += getCodiceWmo().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getSelezioneNazionale() != null) {
            _hashCode += getSelezioneNazionale().hashCode();
        }
        if (getTipoStazione() != null) {
            _hashCode += getTipoStazione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostStazioniSar.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostStazioniSar"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classeMinisteriale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "classeMinisteriale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceIcao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceIcao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceWmo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceWmo"));
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
        elemField.setFieldName("selezioneNazionale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "selezioneNazionale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoStazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoStazione"));
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
