/**
 * CcostSitiRc.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostSitiRc  implements java.io.Serializable {
    private java.lang.String accessibilitaSito;

    private java.lang.String codFacoltativo;

    private java.util.Calendar dataConc;

    private java.lang.Integer idCcost;

    private java.lang.String limitazioniAccesso;

    private java.lang.String note;

    private java.lang.String numeroConc;

    private java.lang.String schemi;

    private java.lang.String tipoConc;

    private com.hyperborea.sira.ws.SitoTipologia tipologia;

    public CcostSitiRc() {
    }

    public CcostSitiRc(
           java.lang.String accessibilitaSito,
           java.lang.String codFacoltativo,
           java.util.Calendar dataConc,
           java.lang.Integer idCcost,
           java.lang.String limitazioniAccesso,
           java.lang.String note,
           java.lang.String numeroConc,
           java.lang.String schemi,
           java.lang.String tipoConc,
           com.hyperborea.sira.ws.SitoTipologia tipologia) {
           this.accessibilitaSito = accessibilitaSito;
           this.codFacoltativo = codFacoltativo;
           this.dataConc = dataConc;
           this.idCcost = idCcost;
           this.limitazioniAccesso = limitazioniAccesso;
           this.note = note;
           this.numeroConc = numeroConc;
           this.schemi = schemi;
           this.tipoConc = tipoConc;
           this.tipologia = tipologia;
    }


    /**
     * Gets the accessibilitaSito value for this CcostSitiRc.
     * 
     * @return accessibilitaSito
     */
    public java.lang.String getAccessibilitaSito() {
        return accessibilitaSito;
    }


    /**
     * Sets the accessibilitaSito value for this CcostSitiRc.
     * 
     * @param accessibilitaSito
     */
    public void setAccessibilitaSito(java.lang.String accessibilitaSito) {
        this.accessibilitaSito = accessibilitaSito;
    }


    /**
     * Gets the codFacoltativo value for this CcostSitiRc.
     * 
     * @return codFacoltativo
     */
    public java.lang.String getCodFacoltativo() {
        return codFacoltativo;
    }


    /**
     * Sets the codFacoltativo value for this CcostSitiRc.
     * 
     * @param codFacoltativo
     */
    public void setCodFacoltativo(java.lang.String codFacoltativo) {
        this.codFacoltativo = codFacoltativo;
    }


    /**
     * Gets the dataConc value for this CcostSitiRc.
     * 
     * @return dataConc
     */
    public java.util.Calendar getDataConc() {
        return dataConc;
    }


    /**
     * Sets the dataConc value for this CcostSitiRc.
     * 
     * @param dataConc
     */
    public void setDataConc(java.util.Calendar dataConc) {
        this.dataConc = dataConc;
    }


    /**
     * Gets the idCcost value for this CcostSitiRc.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostSitiRc.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the limitazioniAccesso value for this CcostSitiRc.
     * 
     * @return limitazioniAccesso
     */
    public java.lang.String getLimitazioniAccesso() {
        return limitazioniAccesso;
    }


    /**
     * Sets the limitazioniAccesso value for this CcostSitiRc.
     * 
     * @param limitazioniAccesso
     */
    public void setLimitazioniAccesso(java.lang.String limitazioniAccesso) {
        this.limitazioniAccesso = limitazioniAccesso;
    }


    /**
     * Gets the note value for this CcostSitiRc.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this CcostSitiRc.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the numeroConc value for this CcostSitiRc.
     * 
     * @return numeroConc
     */
    public java.lang.String getNumeroConc() {
        return numeroConc;
    }


    /**
     * Sets the numeroConc value for this CcostSitiRc.
     * 
     * @param numeroConc
     */
    public void setNumeroConc(java.lang.String numeroConc) {
        this.numeroConc = numeroConc;
    }


    /**
     * Gets the schemi value for this CcostSitiRc.
     * 
     * @return schemi
     */
    public java.lang.String getSchemi() {
        return schemi;
    }


    /**
     * Sets the schemi value for this CcostSitiRc.
     * 
     * @param schemi
     */
    public void setSchemi(java.lang.String schemi) {
        this.schemi = schemi;
    }


    /**
     * Gets the tipoConc value for this CcostSitiRc.
     * 
     * @return tipoConc
     */
    public java.lang.String getTipoConc() {
        return tipoConc;
    }


    /**
     * Sets the tipoConc value for this CcostSitiRc.
     * 
     * @param tipoConc
     */
    public void setTipoConc(java.lang.String tipoConc) {
        this.tipoConc = tipoConc;
    }


    /**
     * Gets the tipologia value for this CcostSitiRc.
     * 
     * @return tipologia
     */
    public com.hyperborea.sira.ws.SitoTipologia getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this CcostSitiRc.
     * 
     * @param tipologia
     */
    public void setTipologia(com.hyperborea.sira.ws.SitoTipologia tipologia) {
        this.tipologia = tipologia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostSitiRc)) return false;
        CcostSitiRc other = (CcostSitiRc) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accessibilitaSito==null && other.getAccessibilitaSito()==null) || 
             (this.accessibilitaSito!=null &&
              this.accessibilitaSito.equals(other.getAccessibilitaSito()))) &&
            ((this.codFacoltativo==null && other.getCodFacoltativo()==null) || 
             (this.codFacoltativo!=null &&
              this.codFacoltativo.equals(other.getCodFacoltativo()))) &&
            ((this.dataConc==null && other.getDataConc()==null) || 
             (this.dataConc!=null &&
              this.dataConc.equals(other.getDataConc()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.limitazioniAccesso==null && other.getLimitazioniAccesso()==null) || 
             (this.limitazioniAccesso!=null &&
              this.limitazioniAccesso.equals(other.getLimitazioniAccesso()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.numeroConc==null && other.getNumeroConc()==null) || 
             (this.numeroConc!=null &&
              this.numeroConc.equals(other.getNumeroConc()))) &&
            ((this.schemi==null && other.getSchemi()==null) || 
             (this.schemi!=null &&
              this.schemi.equals(other.getSchemi()))) &&
            ((this.tipoConc==null && other.getTipoConc()==null) || 
             (this.tipoConc!=null &&
              this.tipoConc.equals(other.getTipoConc()))) &&
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
        int _hashCode = 1;
        if (getAccessibilitaSito() != null) {
            _hashCode += getAccessibilitaSito().hashCode();
        }
        if (getCodFacoltativo() != null) {
            _hashCode += getCodFacoltativo().hashCode();
        }
        if (getDataConc() != null) {
            _hashCode += getDataConc().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getLimitazioniAccesso() != null) {
            _hashCode += getLimitazioniAccesso().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getNumeroConc() != null) {
            _hashCode += getNumeroConc().hashCode();
        }
        if (getSchemi() != null) {
            _hashCode += getSchemi().hashCode();
        }
        if (getTipoConc() != null) {
            _hashCode += getTipoConc().hashCode();
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostSitiRc.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSitiRc"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessibilitaSito");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accessibilitaSito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codFacoltativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codFacoltativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataConc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataConc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField.setFieldName("limitazioniAccesso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limitazioniAccesso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("note");
        elemField.setXmlName(new javax.xml.namespace.QName("", "note"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroConc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroConc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schemi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "schemi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoConc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoConc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sitoTipologia"));
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
