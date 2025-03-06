/**
 * CcostAreaEstrattiva.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostAreaEstrattiva  implements java.io.Serializable {
    private com.hyperborea.sira.ws.AeEnergiaConsumi[] aeEnergiaConsumis;

    private com.hyperborea.sira.ws.AeMaterieConsumi[] aeMaterieConsumis;

    private com.hyperborea.sira.ws.CcostCava ccostCava;

    private com.hyperborea.sira.ws.CcostMiniera ccostMiniera;

    private java.lang.String contestoGeologico;

    private java.lang.Float dimensioneSuperficie;

    private java.lang.Integer durataColtivazione;

    private com.hyperborea.sira.ws.EmissioniInquinantiAe[] emissioniInquinantiAes;

    private java.lang.String faseRipristino;

    private java.lang.Integer idCcost;

    private java.lang.String pianoColtivazione;

    private java.lang.Float produzioneAnnua;

    private java.lang.Float resa;

    public CcostAreaEstrattiva() {
    }

    public CcostAreaEstrattiva(
           com.hyperborea.sira.ws.AeEnergiaConsumi[] aeEnergiaConsumis,
           com.hyperborea.sira.ws.AeMaterieConsumi[] aeMaterieConsumis,
           com.hyperborea.sira.ws.CcostCava ccostCava,
           com.hyperborea.sira.ws.CcostMiniera ccostMiniera,
           java.lang.String contestoGeologico,
           java.lang.Float dimensioneSuperficie,
           java.lang.Integer durataColtivazione,
           com.hyperborea.sira.ws.EmissioniInquinantiAe[] emissioniInquinantiAes,
           java.lang.String faseRipristino,
           java.lang.Integer idCcost,
           java.lang.String pianoColtivazione,
           java.lang.Float produzioneAnnua,
           java.lang.Float resa) {
           this.aeEnergiaConsumis = aeEnergiaConsumis;
           this.aeMaterieConsumis = aeMaterieConsumis;
           this.ccostCava = ccostCava;
           this.ccostMiniera = ccostMiniera;
           this.contestoGeologico = contestoGeologico;
           this.dimensioneSuperficie = dimensioneSuperficie;
           this.durataColtivazione = durataColtivazione;
           this.emissioniInquinantiAes = emissioniInquinantiAes;
           this.faseRipristino = faseRipristino;
           this.idCcost = idCcost;
           this.pianoColtivazione = pianoColtivazione;
           this.produzioneAnnua = produzioneAnnua;
           this.resa = resa;
    }


    /**
     * Gets the aeEnergiaConsumis value for this CcostAreaEstrattiva.
     * 
     * @return aeEnergiaConsumis
     */
    public com.hyperborea.sira.ws.AeEnergiaConsumi[] getAeEnergiaConsumis() {
        return aeEnergiaConsumis;
    }


    /**
     * Sets the aeEnergiaConsumis value for this CcostAreaEstrattiva.
     * 
     * @param aeEnergiaConsumis
     */
    public void setAeEnergiaConsumis(com.hyperborea.sira.ws.AeEnergiaConsumi[] aeEnergiaConsumis) {
        this.aeEnergiaConsumis = aeEnergiaConsumis;
    }

    public com.hyperborea.sira.ws.AeEnergiaConsumi getAeEnergiaConsumis(int i) {
        return this.aeEnergiaConsumis[i];
    }

    public void setAeEnergiaConsumis(int i, com.hyperborea.sira.ws.AeEnergiaConsumi _value) {
        this.aeEnergiaConsumis[i] = _value;
    }


    /**
     * Gets the aeMaterieConsumis value for this CcostAreaEstrattiva.
     * 
     * @return aeMaterieConsumis
     */
    public com.hyperborea.sira.ws.AeMaterieConsumi[] getAeMaterieConsumis() {
        return aeMaterieConsumis;
    }


    /**
     * Sets the aeMaterieConsumis value for this CcostAreaEstrattiva.
     * 
     * @param aeMaterieConsumis
     */
    public void setAeMaterieConsumis(com.hyperborea.sira.ws.AeMaterieConsumi[] aeMaterieConsumis) {
        this.aeMaterieConsumis = aeMaterieConsumis;
    }

    public com.hyperborea.sira.ws.AeMaterieConsumi getAeMaterieConsumis(int i) {
        return this.aeMaterieConsumis[i];
    }

    public void setAeMaterieConsumis(int i, com.hyperborea.sira.ws.AeMaterieConsumi _value) {
        this.aeMaterieConsumis[i] = _value;
    }


    /**
     * Gets the ccostCava value for this CcostAreaEstrattiva.
     * 
     * @return ccostCava
     */
    public com.hyperborea.sira.ws.CcostCava getCcostCava() {
        return ccostCava;
    }


    /**
     * Sets the ccostCava value for this CcostAreaEstrattiva.
     * 
     * @param ccostCava
     */
    public void setCcostCava(com.hyperborea.sira.ws.CcostCava ccostCava) {
        this.ccostCava = ccostCava;
    }


    /**
     * Gets the ccostMiniera value for this CcostAreaEstrattiva.
     * 
     * @return ccostMiniera
     */
    public com.hyperborea.sira.ws.CcostMiniera getCcostMiniera() {
        return ccostMiniera;
    }


    /**
     * Sets the ccostMiniera value for this CcostAreaEstrattiva.
     * 
     * @param ccostMiniera
     */
    public void setCcostMiniera(com.hyperborea.sira.ws.CcostMiniera ccostMiniera) {
        this.ccostMiniera = ccostMiniera;
    }


    /**
     * Gets the contestoGeologico value for this CcostAreaEstrattiva.
     * 
     * @return contestoGeologico
     */
    public java.lang.String getContestoGeologico() {
        return contestoGeologico;
    }


    /**
     * Sets the contestoGeologico value for this CcostAreaEstrattiva.
     * 
     * @param contestoGeologico
     */
    public void setContestoGeologico(java.lang.String contestoGeologico) {
        this.contestoGeologico = contestoGeologico;
    }


    /**
     * Gets the dimensioneSuperficie value for this CcostAreaEstrattiva.
     * 
     * @return dimensioneSuperficie
     */
    public java.lang.Float getDimensioneSuperficie() {
        return dimensioneSuperficie;
    }


    /**
     * Sets the dimensioneSuperficie value for this CcostAreaEstrattiva.
     * 
     * @param dimensioneSuperficie
     */
    public void setDimensioneSuperficie(java.lang.Float dimensioneSuperficie) {
        this.dimensioneSuperficie = dimensioneSuperficie;
    }


    /**
     * Gets the durataColtivazione value for this CcostAreaEstrattiva.
     * 
     * @return durataColtivazione
     */
    public java.lang.Integer getDurataColtivazione() {
        return durataColtivazione;
    }


    /**
     * Sets the durataColtivazione value for this CcostAreaEstrattiva.
     * 
     * @param durataColtivazione
     */
    public void setDurataColtivazione(java.lang.Integer durataColtivazione) {
        this.durataColtivazione = durataColtivazione;
    }


    /**
     * Gets the emissioniInquinantiAes value for this CcostAreaEstrattiva.
     * 
     * @return emissioniInquinantiAes
     */
    public com.hyperborea.sira.ws.EmissioniInquinantiAe[] getEmissioniInquinantiAes() {
        return emissioniInquinantiAes;
    }


    /**
     * Sets the emissioniInquinantiAes value for this CcostAreaEstrattiva.
     * 
     * @param emissioniInquinantiAes
     */
    public void setEmissioniInquinantiAes(com.hyperborea.sira.ws.EmissioniInquinantiAe[] emissioniInquinantiAes) {
        this.emissioniInquinantiAes = emissioniInquinantiAes;
    }

    public com.hyperborea.sira.ws.EmissioniInquinantiAe getEmissioniInquinantiAes(int i) {
        return this.emissioniInquinantiAes[i];
    }

    public void setEmissioniInquinantiAes(int i, com.hyperborea.sira.ws.EmissioniInquinantiAe _value) {
        this.emissioniInquinantiAes[i] = _value;
    }


    /**
     * Gets the faseRipristino value for this CcostAreaEstrattiva.
     * 
     * @return faseRipristino
     */
    public java.lang.String getFaseRipristino() {
        return faseRipristino;
    }


    /**
     * Sets the faseRipristino value for this CcostAreaEstrattiva.
     * 
     * @param faseRipristino
     */
    public void setFaseRipristino(java.lang.String faseRipristino) {
        this.faseRipristino = faseRipristino;
    }


    /**
     * Gets the idCcost value for this CcostAreaEstrattiva.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostAreaEstrattiva.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the pianoColtivazione value for this CcostAreaEstrattiva.
     * 
     * @return pianoColtivazione
     */
    public java.lang.String getPianoColtivazione() {
        return pianoColtivazione;
    }


    /**
     * Sets the pianoColtivazione value for this CcostAreaEstrattiva.
     * 
     * @param pianoColtivazione
     */
    public void setPianoColtivazione(java.lang.String pianoColtivazione) {
        this.pianoColtivazione = pianoColtivazione;
    }


    /**
     * Gets the produzioneAnnua value for this CcostAreaEstrattiva.
     * 
     * @return produzioneAnnua
     */
    public java.lang.Float getProduzioneAnnua() {
        return produzioneAnnua;
    }


    /**
     * Sets the produzioneAnnua value for this CcostAreaEstrattiva.
     * 
     * @param produzioneAnnua
     */
    public void setProduzioneAnnua(java.lang.Float produzioneAnnua) {
        this.produzioneAnnua = produzioneAnnua;
    }


    /**
     * Gets the resa value for this CcostAreaEstrattiva.
     * 
     * @return resa
     */
    public java.lang.Float getResa() {
        return resa;
    }


    /**
     * Sets the resa value for this CcostAreaEstrattiva.
     * 
     * @param resa
     */
    public void setResa(java.lang.Float resa) {
        this.resa = resa;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostAreaEstrattiva)) return false;
        CcostAreaEstrattiva other = (CcostAreaEstrattiva) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.aeEnergiaConsumis==null && other.getAeEnergiaConsumis()==null) || 
             (this.aeEnergiaConsumis!=null &&
              java.util.Arrays.equals(this.aeEnergiaConsumis, other.getAeEnergiaConsumis()))) &&
            ((this.aeMaterieConsumis==null && other.getAeMaterieConsumis()==null) || 
             (this.aeMaterieConsumis!=null &&
              java.util.Arrays.equals(this.aeMaterieConsumis, other.getAeMaterieConsumis()))) &&
            ((this.ccostCava==null && other.getCcostCava()==null) || 
             (this.ccostCava!=null &&
              this.ccostCava.equals(other.getCcostCava()))) &&
            ((this.ccostMiniera==null && other.getCcostMiniera()==null) || 
             (this.ccostMiniera!=null &&
              this.ccostMiniera.equals(other.getCcostMiniera()))) &&
            ((this.contestoGeologico==null && other.getContestoGeologico()==null) || 
             (this.contestoGeologico!=null &&
              this.contestoGeologico.equals(other.getContestoGeologico()))) &&
            ((this.dimensioneSuperficie==null && other.getDimensioneSuperficie()==null) || 
             (this.dimensioneSuperficie!=null &&
              this.dimensioneSuperficie.equals(other.getDimensioneSuperficie()))) &&
            ((this.durataColtivazione==null && other.getDurataColtivazione()==null) || 
             (this.durataColtivazione!=null &&
              this.durataColtivazione.equals(other.getDurataColtivazione()))) &&
            ((this.emissioniInquinantiAes==null && other.getEmissioniInquinantiAes()==null) || 
             (this.emissioniInquinantiAes!=null &&
              java.util.Arrays.equals(this.emissioniInquinantiAes, other.getEmissioniInquinantiAes()))) &&
            ((this.faseRipristino==null && other.getFaseRipristino()==null) || 
             (this.faseRipristino!=null &&
              this.faseRipristino.equals(other.getFaseRipristino()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.pianoColtivazione==null && other.getPianoColtivazione()==null) || 
             (this.pianoColtivazione!=null &&
              this.pianoColtivazione.equals(other.getPianoColtivazione()))) &&
            ((this.produzioneAnnua==null && other.getProduzioneAnnua()==null) || 
             (this.produzioneAnnua!=null &&
              this.produzioneAnnua.equals(other.getProduzioneAnnua()))) &&
            ((this.resa==null && other.getResa()==null) || 
             (this.resa!=null &&
              this.resa.equals(other.getResa())));
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
        if (getAeEnergiaConsumis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAeEnergiaConsumis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAeEnergiaConsumis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAeMaterieConsumis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAeMaterieConsumis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAeMaterieConsumis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCcostCava() != null) {
            _hashCode += getCcostCava().hashCode();
        }
        if (getCcostMiniera() != null) {
            _hashCode += getCcostMiniera().hashCode();
        }
        if (getContestoGeologico() != null) {
            _hashCode += getContestoGeologico().hashCode();
        }
        if (getDimensioneSuperficie() != null) {
            _hashCode += getDimensioneSuperficie().hashCode();
        }
        if (getDurataColtivazione() != null) {
            _hashCode += getDurataColtivazione().hashCode();
        }
        if (getEmissioniInquinantiAes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEmissioniInquinantiAes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEmissioniInquinantiAes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFaseRipristino() != null) {
            _hashCode += getFaseRipristino().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getPianoColtivazione() != null) {
            _hashCode += getPianoColtivazione().hashCode();
        }
        if (getProduzioneAnnua() != null) {
            _hashCode += getProduzioneAnnua().hashCode();
        }
        if (getResa() != null) {
            _hashCode += getResa().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostAreaEstrattiva.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAreaEstrattiva"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("aeEnergiaConsumis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "aeEnergiaConsumis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "aeEnergiaConsumi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("aeMaterieConsumis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "aeMaterieConsumis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "aeMaterieConsumi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostCava");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostCava"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCava"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostMiniera");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostMiniera"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostMiniera"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contestoGeologico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contestoGeologico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dimensioneSuperficie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dimensioneSuperficie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("durataColtivazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "durataColtivazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emissioniInquinantiAes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emissioniInquinantiAes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "emissioniInquinantiAe"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faseRipristino");
        elemField.setXmlName(new javax.xml.namespace.QName("", "faseRipristino"));
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
        elemField.setFieldName("pianoColtivazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pianoColtivazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("produzioneAnnua");
        elemField.setXmlName(new javax.xml.namespace.QName("", "produzioneAnnua"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resa"));
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
