/**
 * CcostSorgRadIonizzanti.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostSorgRadIonizzanti  implements java.io.Serializable {
    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.IsotopiSorgRadioattivita[] isotopiSorgRadioattivitas;

    private com.hyperborea.sira.ws.MisurePreventiveSicurezza[] misurePreventiveSicurezzas;

    private com.hyperborea.sira.ws.RadiazioniSorgRadioattiva[] radiazioniSorgRadioattivas;

    private java.lang.String valDoseAmbientale;

    private java.lang.String valDoseIndividuale;

    private com.hyperborea.sira.ws.VocStatoFisico vocStatoFisico;

    private com.hyperborea.sira.ws.VocStatoSostRadioattiva vocStatoSostRadioattiva;

    private com.hyperborea.sira.ws.VocTipologiaSorgente vocTipologiaSorgente;

    private com.hyperborea.sira.ws.VocUtilizzo vocUtilizzo;

    public CcostSorgRadIonizzanti() {
    }

    public CcostSorgRadIonizzanti(
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.IsotopiSorgRadioattivita[] isotopiSorgRadioattivitas,
           com.hyperborea.sira.ws.MisurePreventiveSicurezza[] misurePreventiveSicurezzas,
           com.hyperborea.sira.ws.RadiazioniSorgRadioattiva[] radiazioniSorgRadioattivas,
           java.lang.String valDoseAmbientale,
           java.lang.String valDoseIndividuale,
           com.hyperborea.sira.ws.VocStatoFisico vocStatoFisico,
           com.hyperborea.sira.ws.VocStatoSostRadioattiva vocStatoSostRadioattiva,
           com.hyperborea.sira.ws.VocTipologiaSorgente vocTipologiaSorgente,
           com.hyperborea.sira.ws.VocUtilizzo vocUtilizzo) {
           this.idCcost = idCcost;
           this.isotopiSorgRadioattivitas = isotopiSorgRadioattivitas;
           this.misurePreventiveSicurezzas = misurePreventiveSicurezzas;
           this.radiazioniSorgRadioattivas = radiazioniSorgRadioattivas;
           this.valDoseAmbientale = valDoseAmbientale;
           this.valDoseIndividuale = valDoseIndividuale;
           this.vocStatoFisico = vocStatoFisico;
           this.vocStatoSostRadioattiva = vocStatoSostRadioattiva;
           this.vocTipologiaSorgente = vocTipologiaSorgente;
           this.vocUtilizzo = vocUtilizzo;
    }


    /**
     * Gets the idCcost value for this CcostSorgRadIonizzanti.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostSorgRadIonizzanti.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the isotopiSorgRadioattivitas value for this CcostSorgRadIonizzanti.
     * 
     * @return isotopiSorgRadioattivitas
     */
    public com.hyperborea.sira.ws.IsotopiSorgRadioattivita[] getIsotopiSorgRadioattivitas() {
        return isotopiSorgRadioattivitas;
    }


    /**
     * Sets the isotopiSorgRadioattivitas value for this CcostSorgRadIonizzanti.
     * 
     * @param isotopiSorgRadioattivitas
     */
    public void setIsotopiSorgRadioattivitas(com.hyperborea.sira.ws.IsotopiSorgRadioattivita[] isotopiSorgRadioattivitas) {
        this.isotopiSorgRadioattivitas = isotopiSorgRadioattivitas;
    }

    public com.hyperborea.sira.ws.IsotopiSorgRadioattivita getIsotopiSorgRadioattivitas(int i) {
        return this.isotopiSorgRadioattivitas[i];
    }

    public void setIsotopiSorgRadioattivitas(int i, com.hyperborea.sira.ws.IsotopiSorgRadioattivita _value) {
        this.isotopiSorgRadioattivitas[i] = _value;
    }


    /**
     * Gets the misurePreventiveSicurezzas value for this CcostSorgRadIonizzanti.
     * 
     * @return misurePreventiveSicurezzas
     */
    public com.hyperborea.sira.ws.MisurePreventiveSicurezza[] getMisurePreventiveSicurezzas() {
        return misurePreventiveSicurezzas;
    }


    /**
     * Sets the misurePreventiveSicurezzas value for this CcostSorgRadIonizzanti.
     * 
     * @param misurePreventiveSicurezzas
     */
    public void setMisurePreventiveSicurezzas(com.hyperborea.sira.ws.MisurePreventiveSicurezza[] misurePreventiveSicurezzas) {
        this.misurePreventiveSicurezzas = misurePreventiveSicurezzas;
    }

    public com.hyperborea.sira.ws.MisurePreventiveSicurezza getMisurePreventiveSicurezzas(int i) {
        return this.misurePreventiveSicurezzas[i];
    }

    public void setMisurePreventiveSicurezzas(int i, com.hyperborea.sira.ws.MisurePreventiveSicurezza _value) {
        this.misurePreventiveSicurezzas[i] = _value;
    }


    /**
     * Gets the radiazioniSorgRadioattivas value for this CcostSorgRadIonizzanti.
     * 
     * @return radiazioniSorgRadioattivas
     */
    public com.hyperborea.sira.ws.RadiazioniSorgRadioattiva[] getRadiazioniSorgRadioattivas() {
        return radiazioniSorgRadioattivas;
    }


    /**
     * Sets the radiazioniSorgRadioattivas value for this CcostSorgRadIonizzanti.
     * 
     * @param radiazioniSorgRadioattivas
     */
    public void setRadiazioniSorgRadioattivas(com.hyperborea.sira.ws.RadiazioniSorgRadioattiva[] radiazioniSorgRadioattivas) {
        this.radiazioniSorgRadioattivas = radiazioniSorgRadioattivas;
    }

    public com.hyperborea.sira.ws.RadiazioniSorgRadioattiva getRadiazioniSorgRadioattivas(int i) {
        return this.radiazioniSorgRadioattivas[i];
    }

    public void setRadiazioniSorgRadioattivas(int i, com.hyperborea.sira.ws.RadiazioniSorgRadioattiva _value) {
        this.radiazioniSorgRadioattivas[i] = _value;
    }


    /**
     * Gets the valDoseAmbientale value for this CcostSorgRadIonizzanti.
     * 
     * @return valDoseAmbientale
     */
    public java.lang.String getValDoseAmbientale() {
        return valDoseAmbientale;
    }


    /**
     * Sets the valDoseAmbientale value for this CcostSorgRadIonizzanti.
     * 
     * @param valDoseAmbientale
     */
    public void setValDoseAmbientale(java.lang.String valDoseAmbientale) {
        this.valDoseAmbientale = valDoseAmbientale;
    }


    /**
     * Gets the valDoseIndividuale value for this CcostSorgRadIonizzanti.
     * 
     * @return valDoseIndividuale
     */
    public java.lang.String getValDoseIndividuale() {
        return valDoseIndividuale;
    }


    /**
     * Sets the valDoseIndividuale value for this CcostSorgRadIonizzanti.
     * 
     * @param valDoseIndividuale
     */
    public void setValDoseIndividuale(java.lang.String valDoseIndividuale) {
        this.valDoseIndividuale = valDoseIndividuale;
    }


    /**
     * Gets the vocStatoFisico value for this CcostSorgRadIonizzanti.
     * 
     * @return vocStatoFisico
     */
    public com.hyperborea.sira.ws.VocStatoFisico getVocStatoFisico() {
        return vocStatoFisico;
    }


    /**
     * Sets the vocStatoFisico value for this CcostSorgRadIonizzanti.
     * 
     * @param vocStatoFisico
     */
    public void setVocStatoFisico(com.hyperborea.sira.ws.VocStatoFisico vocStatoFisico) {
        this.vocStatoFisico = vocStatoFisico;
    }


    /**
     * Gets the vocStatoSostRadioattiva value for this CcostSorgRadIonizzanti.
     * 
     * @return vocStatoSostRadioattiva
     */
    public com.hyperborea.sira.ws.VocStatoSostRadioattiva getVocStatoSostRadioattiva() {
        return vocStatoSostRadioattiva;
    }


    /**
     * Sets the vocStatoSostRadioattiva value for this CcostSorgRadIonizzanti.
     * 
     * @param vocStatoSostRadioattiva
     */
    public void setVocStatoSostRadioattiva(com.hyperborea.sira.ws.VocStatoSostRadioattiva vocStatoSostRadioattiva) {
        this.vocStatoSostRadioattiva = vocStatoSostRadioattiva;
    }


    /**
     * Gets the vocTipologiaSorgente value for this CcostSorgRadIonizzanti.
     * 
     * @return vocTipologiaSorgente
     */
    public com.hyperborea.sira.ws.VocTipologiaSorgente getVocTipologiaSorgente() {
        return vocTipologiaSorgente;
    }


    /**
     * Sets the vocTipologiaSorgente value for this CcostSorgRadIonizzanti.
     * 
     * @param vocTipologiaSorgente
     */
    public void setVocTipologiaSorgente(com.hyperborea.sira.ws.VocTipologiaSorgente vocTipologiaSorgente) {
        this.vocTipologiaSorgente = vocTipologiaSorgente;
    }


    /**
     * Gets the vocUtilizzo value for this CcostSorgRadIonizzanti.
     * 
     * @return vocUtilizzo
     */
    public com.hyperborea.sira.ws.VocUtilizzo getVocUtilizzo() {
        return vocUtilizzo;
    }


    /**
     * Sets the vocUtilizzo value for this CcostSorgRadIonizzanti.
     * 
     * @param vocUtilizzo
     */
    public void setVocUtilizzo(com.hyperborea.sira.ws.VocUtilizzo vocUtilizzo) {
        this.vocUtilizzo = vocUtilizzo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostSorgRadIonizzanti)) return false;
        CcostSorgRadIonizzanti other = (CcostSorgRadIonizzanti) obj;
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
            ((this.isotopiSorgRadioattivitas==null && other.getIsotopiSorgRadioattivitas()==null) || 
             (this.isotopiSorgRadioattivitas!=null &&
              java.util.Arrays.equals(this.isotopiSorgRadioattivitas, other.getIsotopiSorgRadioattivitas()))) &&
            ((this.misurePreventiveSicurezzas==null && other.getMisurePreventiveSicurezzas()==null) || 
             (this.misurePreventiveSicurezzas!=null &&
              java.util.Arrays.equals(this.misurePreventiveSicurezzas, other.getMisurePreventiveSicurezzas()))) &&
            ((this.radiazioniSorgRadioattivas==null && other.getRadiazioniSorgRadioattivas()==null) || 
             (this.radiazioniSorgRadioattivas!=null &&
              java.util.Arrays.equals(this.radiazioniSorgRadioattivas, other.getRadiazioniSorgRadioattivas()))) &&
            ((this.valDoseAmbientale==null && other.getValDoseAmbientale()==null) || 
             (this.valDoseAmbientale!=null &&
              this.valDoseAmbientale.equals(other.getValDoseAmbientale()))) &&
            ((this.valDoseIndividuale==null && other.getValDoseIndividuale()==null) || 
             (this.valDoseIndividuale!=null &&
              this.valDoseIndividuale.equals(other.getValDoseIndividuale()))) &&
            ((this.vocStatoFisico==null && other.getVocStatoFisico()==null) || 
             (this.vocStatoFisico!=null &&
              this.vocStatoFisico.equals(other.getVocStatoFisico()))) &&
            ((this.vocStatoSostRadioattiva==null && other.getVocStatoSostRadioattiva()==null) || 
             (this.vocStatoSostRadioattiva!=null &&
              this.vocStatoSostRadioattiva.equals(other.getVocStatoSostRadioattiva()))) &&
            ((this.vocTipologiaSorgente==null && other.getVocTipologiaSorgente()==null) || 
             (this.vocTipologiaSorgente!=null &&
              this.vocTipologiaSorgente.equals(other.getVocTipologiaSorgente()))) &&
            ((this.vocUtilizzo==null && other.getVocUtilizzo()==null) || 
             (this.vocUtilizzo!=null &&
              this.vocUtilizzo.equals(other.getVocUtilizzo())));
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
        if (getIsotopiSorgRadioattivitas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIsotopiSorgRadioattivitas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIsotopiSorgRadioattivitas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMisurePreventiveSicurezzas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMisurePreventiveSicurezzas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMisurePreventiveSicurezzas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRadiazioniSorgRadioattivas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRadiazioniSorgRadioattivas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRadiazioniSorgRadioattivas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getValDoseAmbientale() != null) {
            _hashCode += getValDoseAmbientale().hashCode();
        }
        if (getValDoseIndividuale() != null) {
            _hashCode += getValDoseIndividuale().hashCode();
        }
        if (getVocStatoFisico() != null) {
            _hashCode += getVocStatoFisico().hashCode();
        }
        if (getVocStatoSostRadioattiva() != null) {
            _hashCode += getVocStatoSostRadioattiva().hashCode();
        }
        if (getVocTipologiaSorgente() != null) {
            _hashCode += getVocTipologiaSorgente().hashCode();
        }
        if (getVocUtilizzo() != null) {
            _hashCode += getVocUtilizzo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostSorgRadIonizzanti.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSorgRadIonizzanti"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isotopiSorgRadioattivitas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "isotopiSorgRadioattivitas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "isotopiSorgRadioattivita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("misurePreventiveSicurezzas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "misurePreventiveSicurezzas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "misurePreventiveSicurezza"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("radiazioniSorgRadioattivas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "radiazioniSorgRadioattivas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "radiazioniSorgRadioattiva"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valDoseAmbientale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valDoseAmbientale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valDoseIndividuale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valDoseIndividuale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocStatoFisico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocStatoFisico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocStatoFisico"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocStatoSostRadioattiva");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocStatoSostRadioattiva"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocStatoSostRadioattiva"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaSorgente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaSorgente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaSorgente"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocUtilizzo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocUtilizzo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocUtilizzo"));
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
