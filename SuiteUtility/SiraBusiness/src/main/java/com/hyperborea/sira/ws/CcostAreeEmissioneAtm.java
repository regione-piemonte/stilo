/**
 * CcostAreeEmissioneAtm.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostAreeEmissioneAtm  implements java.io.Serializable {
    private java.lang.String ambiente;

    private com.hyperborea.sira.ws.EmissioniInquinanteAtm[] emissioniInquinanteAtms;

    private java.lang.Float giorniAnni;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.VocImpiantoMobile impiantoMobile;

    private java.lang.String macchinari;

    private java.lang.Float mediaOre;

    private java.lang.String metereologiche;

    private java.lang.String modalitaContenimento;

    private java.lang.String monitoraggio;

    private java.lang.String prescrizioni;

    private com.hyperborea.sira.ws.VocTipologiaEmissione vocTipologiaEmissione;

    public CcostAreeEmissioneAtm() {
    }

    public CcostAreeEmissioneAtm(
           java.lang.String ambiente,
           com.hyperborea.sira.ws.EmissioniInquinanteAtm[] emissioniInquinanteAtms,
           java.lang.Float giorniAnni,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.VocImpiantoMobile impiantoMobile,
           java.lang.String macchinari,
           java.lang.Float mediaOre,
           java.lang.String metereologiche,
           java.lang.String modalitaContenimento,
           java.lang.String monitoraggio,
           java.lang.String prescrizioni,
           com.hyperborea.sira.ws.VocTipologiaEmissione vocTipologiaEmissione) {
           this.ambiente = ambiente;
           this.emissioniInquinanteAtms = emissioniInquinanteAtms;
           this.giorniAnni = giorniAnni;
           this.idCcost = idCcost;
           this.impiantoMobile = impiantoMobile;
           this.macchinari = macchinari;
           this.mediaOre = mediaOre;
           this.metereologiche = metereologiche;
           this.modalitaContenimento = modalitaContenimento;
           this.monitoraggio = monitoraggio;
           this.prescrizioni = prescrizioni;
           this.vocTipologiaEmissione = vocTipologiaEmissione;
    }


    /**
     * Gets the ambiente value for this CcostAreeEmissioneAtm.
     * 
     * @return ambiente
     */
    public java.lang.String getAmbiente() {
        return ambiente;
    }


    /**
     * Sets the ambiente value for this CcostAreeEmissioneAtm.
     * 
     * @param ambiente
     */
    public void setAmbiente(java.lang.String ambiente) {
        this.ambiente = ambiente;
    }


    /**
     * Gets the emissioniInquinanteAtms value for this CcostAreeEmissioneAtm.
     * 
     * @return emissioniInquinanteAtms
     */
    public com.hyperborea.sira.ws.EmissioniInquinanteAtm[] getEmissioniInquinanteAtms() {
        return emissioniInquinanteAtms;
    }


    /**
     * Sets the emissioniInquinanteAtms value for this CcostAreeEmissioneAtm.
     * 
     * @param emissioniInquinanteAtms
     */
    public void setEmissioniInquinanteAtms(com.hyperborea.sira.ws.EmissioniInquinanteAtm[] emissioniInquinanteAtms) {
        this.emissioniInquinanteAtms = emissioniInquinanteAtms;
    }

    public com.hyperborea.sira.ws.EmissioniInquinanteAtm getEmissioniInquinanteAtms(int i) {
        return this.emissioniInquinanteAtms[i];
    }

    public void setEmissioniInquinanteAtms(int i, com.hyperborea.sira.ws.EmissioniInquinanteAtm _value) {
        this.emissioniInquinanteAtms[i] = _value;
    }


    /**
     * Gets the giorniAnni value for this CcostAreeEmissioneAtm.
     * 
     * @return giorniAnni
     */
    public java.lang.Float getGiorniAnni() {
        return giorniAnni;
    }


    /**
     * Sets the giorniAnni value for this CcostAreeEmissioneAtm.
     * 
     * @param giorniAnni
     */
    public void setGiorniAnni(java.lang.Float giorniAnni) {
        this.giorniAnni = giorniAnni;
    }


    /**
     * Gets the idCcost value for this CcostAreeEmissioneAtm.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostAreeEmissioneAtm.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the impiantoMobile value for this CcostAreeEmissioneAtm.
     * 
     * @return impiantoMobile
     */
    public com.hyperborea.sira.ws.VocImpiantoMobile getImpiantoMobile() {
        return impiantoMobile;
    }


    /**
     * Sets the impiantoMobile value for this CcostAreeEmissioneAtm.
     * 
     * @param impiantoMobile
     */
    public void setImpiantoMobile(com.hyperborea.sira.ws.VocImpiantoMobile impiantoMobile) {
        this.impiantoMobile = impiantoMobile;
    }


    /**
     * Gets the macchinari value for this CcostAreeEmissioneAtm.
     * 
     * @return macchinari
     */
    public java.lang.String getMacchinari() {
        return macchinari;
    }


    /**
     * Sets the macchinari value for this CcostAreeEmissioneAtm.
     * 
     * @param macchinari
     */
    public void setMacchinari(java.lang.String macchinari) {
        this.macchinari = macchinari;
    }


    /**
     * Gets the mediaOre value for this CcostAreeEmissioneAtm.
     * 
     * @return mediaOre
     */
    public java.lang.Float getMediaOre() {
        return mediaOre;
    }


    /**
     * Sets the mediaOre value for this CcostAreeEmissioneAtm.
     * 
     * @param mediaOre
     */
    public void setMediaOre(java.lang.Float mediaOre) {
        this.mediaOre = mediaOre;
    }


    /**
     * Gets the metereologiche value for this CcostAreeEmissioneAtm.
     * 
     * @return metereologiche
     */
    public java.lang.String getMetereologiche() {
        return metereologiche;
    }


    /**
     * Sets the metereologiche value for this CcostAreeEmissioneAtm.
     * 
     * @param metereologiche
     */
    public void setMetereologiche(java.lang.String metereologiche) {
        this.metereologiche = metereologiche;
    }


    /**
     * Gets the modalitaContenimento value for this CcostAreeEmissioneAtm.
     * 
     * @return modalitaContenimento
     */
    public java.lang.String getModalitaContenimento() {
        return modalitaContenimento;
    }


    /**
     * Sets the modalitaContenimento value for this CcostAreeEmissioneAtm.
     * 
     * @param modalitaContenimento
     */
    public void setModalitaContenimento(java.lang.String modalitaContenimento) {
        this.modalitaContenimento = modalitaContenimento;
    }


    /**
     * Gets the monitoraggio value for this CcostAreeEmissioneAtm.
     * 
     * @return monitoraggio
     */
    public java.lang.String getMonitoraggio() {
        return monitoraggio;
    }


    /**
     * Sets the monitoraggio value for this CcostAreeEmissioneAtm.
     * 
     * @param monitoraggio
     */
    public void setMonitoraggio(java.lang.String monitoraggio) {
        this.monitoraggio = monitoraggio;
    }


    /**
     * Gets the prescrizioni value for this CcostAreeEmissioneAtm.
     * 
     * @return prescrizioni
     */
    public java.lang.String getPrescrizioni() {
        return prescrizioni;
    }


    /**
     * Sets the prescrizioni value for this CcostAreeEmissioneAtm.
     * 
     * @param prescrizioni
     */
    public void setPrescrizioni(java.lang.String prescrizioni) {
        this.prescrizioni = prescrizioni;
    }


    /**
     * Gets the vocTipologiaEmissione value for this CcostAreeEmissioneAtm.
     * 
     * @return vocTipologiaEmissione
     */
    public com.hyperborea.sira.ws.VocTipologiaEmissione getVocTipologiaEmissione() {
        return vocTipologiaEmissione;
    }


    /**
     * Sets the vocTipologiaEmissione value for this CcostAreeEmissioneAtm.
     * 
     * @param vocTipologiaEmissione
     */
    public void setVocTipologiaEmissione(com.hyperborea.sira.ws.VocTipologiaEmissione vocTipologiaEmissione) {
        this.vocTipologiaEmissione = vocTipologiaEmissione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostAreeEmissioneAtm)) return false;
        CcostAreeEmissioneAtm other = (CcostAreeEmissioneAtm) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ambiente==null && other.getAmbiente()==null) || 
             (this.ambiente!=null &&
              this.ambiente.equals(other.getAmbiente()))) &&
            ((this.emissioniInquinanteAtms==null && other.getEmissioniInquinanteAtms()==null) || 
             (this.emissioniInquinanteAtms!=null &&
              java.util.Arrays.equals(this.emissioniInquinanteAtms, other.getEmissioniInquinanteAtms()))) &&
            ((this.giorniAnni==null && other.getGiorniAnni()==null) || 
             (this.giorniAnni!=null &&
              this.giorniAnni.equals(other.getGiorniAnni()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.impiantoMobile==null && other.getImpiantoMobile()==null) || 
             (this.impiantoMobile!=null &&
              this.impiantoMobile.equals(other.getImpiantoMobile()))) &&
            ((this.macchinari==null && other.getMacchinari()==null) || 
             (this.macchinari!=null &&
              this.macchinari.equals(other.getMacchinari()))) &&
            ((this.mediaOre==null && other.getMediaOre()==null) || 
             (this.mediaOre!=null &&
              this.mediaOre.equals(other.getMediaOre()))) &&
            ((this.metereologiche==null && other.getMetereologiche()==null) || 
             (this.metereologiche!=null &&
              this.metereologiche.equals(other.getMetereologiche()))) &&
            ((this.modalitaContenimento==null && other.getModalitaContenimento()==null) || 
             (this.modalitaContenimento!=null &&
              this.modalitaContenimento.equals(other.getModalitaContenimento()))) &&
            ((this.monitoraggio==null && other.getMonitoraggio()==null) || 
             (this.monitoraggio!=null &&
              this.monitoraggio.equals(other.getMonitoraggio()))) &&
            ((this.prescrizioni==null && other.getPrescrizioni()==null) || 
             (this.prescrizioni!=null &&
              this.prescrizioni.equals(other.getPrescrizioni()))) &&
            ((this.vocTipologiaEmissione==null && other.getVocTipologiaEmissione()==null) || 
             (this.vocTipologiaEmissione!=null &&
              this.vocTipologiaEmissione.equals(other.getVocTipologiaEmissione())));
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
        if (getAmbiente() != null) {
            _hashCode += getAmbiente().hashCode();
        }
        if (getEmissioniInquinanteAtms() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEmissioniInquinanteAtms());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEmissioniInquinanteAtms(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGiorniAnni() != null) {
            _hashCode += getGiorniAnni().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getImpiantoMobile() != null) {
            _hashCode += getImpiantoMobile().hashCode();
        }
        if (getMacchinari() != null) {
            _hashCode += getMacchinari().hashCode();
        }
        if (getMediaOre() != null) {
            _hashCode += getMediaOre().hashCode();
        }
        if (getMetereologiche() != null) {
            _hashCode += getMetereologiche().hashCode();
        }
        if (getModalitaContenimento() != null) {
            _hashCode += getModalitaContenimento().hashCode();
        }
        if (getMonitoraggio() != null) {
            _hashCode += getMonitoraggio().hashCode();
        }
        if (getPrescrizioni() != null) {
            _hashCode += getPrescrizioni().hashCode();
        }
        if (getVocTipologiaEmissione() != null) {
            _hashCode += getVocTipologiaEmissione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostAreeEmissioneAtm.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAreeEmissioneAtm"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ambiente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ambiente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emissioniInquinanteAtms");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emissioniInquinanteAtms"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "emissioniInquinanteAtm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("giorniAnni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "giorniAnni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
        elemField.setFieldName("impiantoMobile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "impiantoMobile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocImpiantoMobile"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("macchinari");
        elemField.setXmlName(new javax.xml.namespace.QName("", "macchinari"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mediaOre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mediaOre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("metereologiche");
        elemField.setXmlName(new javax.xml.namespace.QName("", "metereologiche"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modalitaContenimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modalitaContenimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monitoraggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "monitoraggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prescrizioni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prescrizioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaEmissione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaEmissione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaEmissione"));
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
