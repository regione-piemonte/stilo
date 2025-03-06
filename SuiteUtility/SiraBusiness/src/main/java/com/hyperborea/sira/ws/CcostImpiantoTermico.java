/**
 * CcostImpiantoTermico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostImpiantoTermico  implements java.io.Serializable {
    private java.lang.Integer annoInstallazione;

    private java.lang.String dichiarazioneConformita;

    private java.lang.Integer idCcost;

    private java.lang.Integer librettoImpianto;

    private java.lang.Integer librettoUsoManutenzione;

    private java.lang.Integer perAcquaCalda;

    private java.lang.Integer perRiscaldamento;

    private java.lang.Integer periodicitaControllo;

    private java.lang.Float potenzaTermica;

    private com.hyperborea.sira.ws.ProveCombustioneImpTermici[] proveCombustioneImpTermicis;

    private java.lang.Float valoreSoglia;

    private com.hyperborea.sira.ws.VocTipoCaldaia vocTipoCaldaia;

    private com.hyperborea.sira.ws.VocTipoCombustibile vocTipoCombustibile;

    private com.hyperborea.sira.ws.VocTipoGeneratore vocTipoGeneratore;

    public CcostImpiantoTermico() {
    }

    public CcostImpiantoTermico(
           java.lang.Integer annoInstallazione,
           java.lang.String dichiarazioneConformita,
           java.lang.Integer idCcost,
           java.lang.Integer librettoImpianto,
           java.lang.Integer librettoUsoManutenzione,
           java.lang.Integer perAcquaCalda,
           java.lang.Integer perRiscaldamento,
           java.lang.Integer periodicitaControllo,
           java.lang.Float potenzaTermica,
           com.hyperborea.sira.ws.ProveCombustioneImpTermici[] proveCombustioneImpTermicis,
           java.lang.Float valoreSoglia,
           com.hyperborea.sira.ws.VocTipoCaldaia vocTipoCaldaia,
           com.hyperborea.sira.ws.VocTipoCombustibile vocTipoCombustibile,
           com.hyperborea.sira.ws.VocTipoGeneratore vocTipoGeneratore) {
           this.annoInstallazione = annoInstallazione;
           this.dichiarazioneConformita = dichiarazioneConformita;
           this.idCcost = idCcost;
           this.librettoImpianto = librettoImpianto;
           this.librettoUsoManutenzione = librettoUsoManutenzione;
           this.perAcquaCalda = perAcquaCalda;
           this.perRiscaldamento = perRiscaldamento;
           this.periodicitaControllo = periodicitaControllo;
           this.potenzaTermica = potenzaTermica;
           this.proveCombustioneImpTermicis = proveCombustioneImpTermicis;
           this.valoreSoglia = valoreSoglia;
           this.vocTipoCaldaia = vocTipoCaldaia;
           this.vocTipoCombustibile = vocTipoCombustibile;
           this.vocTipoGeneratore = vocTipoGeneratore;
    }


    /**
     * Gets the annoInstallazione value for this CcostImpiantoTermico.
     * 
     * @return annoInstallazione
     */
    public java.lang.Integer getAnnoInstallazione() {
        return annoInstallazione;
    }


    /**
     * Sets the annoInstallazione value for this CcostImpiantoTermico.
     * 
     * @param annoInstallazione
     */
    public void setAnnoInstallazione(java.lang.Integer annoInstallazione) {
        this.annoInstallazione = annoInstallazione;
    }


    /**
     * Gets the dichiarazioneConformita value for this CcostImpiantoTermico.
     * 
     * @return dichiarazioneConformita
     */
    public java.lang.String getDichiarazioneConformita() {
        return dichiarazioneConformita;
    }


    /**
     * Sets the dichiarazioneConformita value for this CcostImpiantoTermico.
     * 
     * @param dichiarazioneConformita
     */
    public void setDichiarazioneConformita(java.lang.String dichiarazioneConformita) {
        this.dichiarazioneConformita = dichiarazioneConformita;
    }


    /**
     * Gets the idCcost value for this CcostImpiantoTermico.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostImpiantoTermico.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the librettoImpianto value for this CcostImpiantoTermico.
     * 
     * @return librettoImpianto
     */
    public java.lang.Integer getLibrettoImpianto() {
        return librettoImpianto;
    }


    /**
     * Sets the librettoImpianto value for this CcostImpiantoTermico.
     * 
     * @param librettoImpianto
     */
    public void setLibrettoImpianto(java.lang.Integer librettoImpianto) {
        this.librettoImpianto = librettoImpianto;
    }


    /**
     * Gets the librettoUsoManutenzione value for this CcostImpiantoTermico.
     * 
     * @return librettoUsoManutenzione
     */
    public java.lang.Integer getLibrettoUsoManutenzione() {
        return librettoUsoManutenzione;
    }


    /**
     * Sets the librettoUsoManutenzione value for this CcostImpiantoTermico.
     * 
     * @param librettoUsoManutenzione
     */
    public void setLibrettoUsoManutenzione(java.lang.Integer librettoUsoManutenzione) {
        this.librettoUsoManutenzione = librettoUsoManutenzione;
    }


    /**
     * Gets the perAcquaCalda value for this CcostImpiantoTermico.
     * 
     * @return perAcquaCalda
     */
    public java.lang.Integer getPerAcquaCalda() {
        return perAcquaCalda;
    }


    /**
     * Sets the perAcquaCalda value for this CcostImpiantoTermico.
     * 
     * @param perAcquaCalda
     */
    public void setPerAcquaCalda(java.lang.Integer perAcquaCalda) {
        this.perAcquaCalda = perAcquaCalda;
    }


    /**
     * Gets the perRiscaldamento value for this CcostImpiantoTermico.
     * 
     * @return perRiscaldamento
     */
    public java.lang.Integer getPerRiscaldamento() {
        return perRiscaldamento;
    }


    /**
     * Sets the perRiscaldamento value for this CcostImpiantoTermico.
     * 
     * @param perRiscaldamento
     */
    public void setPerRiscaldamento(java.lang.Integer perRiscaldamento) {
        this.perRiscaldamento = perRiscaldamento;
    }


    /**
     * Gets the periodicitaControllo value for this CcostImpiantoTermico.
     * 
     * @return periodicitaControllo
     */
    public java.lang.Integer getPeriodicitaControllo() {
        return periodicitaControllo;
    }


    /**
     * Sets the periodicitaControllo value for this CcostImpiantoTermico.
     * 
     * @param periodicitaControllo
     */
    public void setPeriodicitaControllo(java.lang.Integer periodicitaControllo) {
        this.periodicitaControllo = periodicitaControllo;
    }


    /**
     * Gets the potenzaTermica value for this CcostImpiantoTermico.
     * 
     * @return potenzaTermica
     */
    public java.lang.Float getPotenzaTermica() {
        return potenzaTermica;
    }


    /**
     * Sets the potenzaTermica value for this CcostImpiantoTermico.
     * 
     * @param potenzaTermica
     */
    public void setPotenzaTermica(java.lang.Float potenzaTermica) {
        this.potenzaTermica = potenzaTermica;
    }


    /**
     * Gets the proveCombustioneImpTermicis value for this CcostImpiantoTermico.
     * 
     * @return proveCombustioneImpTermicis
     */
    public com.hyperborea.sira.ws.ProveCombustioneImpTermici[] getProveCombustioneImpTermicis() {
        return proveCombustioneImpTermicis;
    }


    /**
     * Sets the proveCombustioneImpTermicis value for this CcostImpiantoTermico.
     * 
     * @param proveCombustioneImpTermicis
     */
    public void setProveCombustioneImpTermicis(com.hyperborea.sira.ws.ProveCombustioneImpTermici[] proveCombustioneImpTermicis) {
        this.proveCombustioneImpTermicis = proveCombustioneImpTermicis;
    }

    public com.hyperborea.sira.ws.ProveCombustioneImpTermici getProveCombustioneImpTermicis(int i) {
        return this.proveCombustioneImpTermicis[i];
    }

    public void setProveCombustioneImpTermicis(int i, com.hyperborea.sira.ws.ProveCombustioneImpTermici _value) {
        this.proveCombustioneImpTermicis[i] = _value;
    }


    /**
     * Gets the valoreSoglia value for this CcostImpiantoTermico.
     * 
     * @return valoreSoglia
     */
    public java.lang.Float getValoreSoglia() {
        return valoreSoglia;
    }


    /**
     * Sets the valoreSoglia value for this CcostImpiantoTermico.
     * 
     * @param valoreSoglia
     */
    public void setValoreSoglia(java.lang.Float valoreSoglia) {
        this.valoreSoglia = valoreSoglia;
    }


    /**
     * Gets the vocTipoCaldaia value for this CcostImpiantoTermico.
     * 
     * @return vocTipoCaldaia
     */
    public com.hyperborea.sira.ws.VocTipoCaldaia getVocTipoCaldaia() {
        return vocTipoCaldaia;
    }


    /**
     * Sets the vocTipoCaldaia value for this CcostImpiantoTermico.
     * 
     * @param vocTipoCaldaia
     */
    public void setVocTipoCaldaia(com.hyperborea.sira.ws.VocTipoCaldaia vocTipoCaldaia) {
        this.vocTipoCaldaia = vocTipoCaldaia;
    }


    /**
     * Gets the vocTipoCombustibile value for this CcostImpiantoTermico.
     * 
     * @return vocTipoCombustibile
     */
    public com.hyperborea.sira.ws.VocTipoCombustibile getVocTipoCombustibile() {
        return vocTipoCombustibile;
    }


    /**
     * Sets the vocTipoCombustibile value for this CcostImpiantoTermico.
     * 
     * @param vocTipoCombustibile
     */
    public void setVocTipoCombustibile(com.hyperborea.sira.ws.VocTipoCombustibile vocTipoCombustibile) {
        this.vocTipoCombustibile = vocTipoCombustibile;
    }


    /**
     * Gets the vocTipoGeneratore value for this CcostImpiantoTermico.
     * 
     * @return vocTipoGeneratore
     */
    public com.hyperborea.sira.ws.VocTipoGeneratore getVocTipoGeneratore() {
        return vocTipoGeneratore;
    }


    /**
     * Sets the vocTipoGeneratore value for this CcostImpiantoTermico.
     * 
     * @param vocTipoGeneratore
     */
    public void setVocTipoGeneratore(com.hyperborea.sira.ws.VocTipoGeneratore vocTipoGeneratore) {
        this.vocTipoGeneratore = vocTipoGeneratore;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostImpiantoTermico)) return false;
        CcostImpiantoTermico other = (CcostImpiantoTermico) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.annoInstallazione==null && other.getAnnoInstallazione()==null) || 
             (this.annoInstallazione!=null &&
              this.annoInstallazione.equals(other.getAnnoInstallazione()))) &&
            ((this.dichiarazioneConformita==null && other.getDichiarazioneConformita()==null) || 
             (this.dichiarazioneConformita!=null &&
              this.dichiarazioneConformita.equals(other.getDichiarazioneConformita()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.librettoImpianto==null && other.getLibrettoImpianto()==null) || 
             (this.librettoImpianto!=null &&
              this.librettoImpianto.equals(other.getLibrettoImpianto()))) &&
            ((this.librettoUsoManutenzione==null && other.getLibrettoUsoManutenzione()==null) || 
             (this.librettoUsoManutenzione!=null &&
              this.librettoUsoManutenzione.equals(other.getLibrettoUsoManutenzione()))) &&
            ((this.perAcquaCalda==null && other.getPerAcquaCalda()==null) || 
             (this.perAcquaCalda!=null &&
              this.perAcquaCalda.equals(other.getPerAcquaCalda()))) &&
            ((this.perRiscaldamento==null && other.getPerRiscaldamento()==null) || 
             (this.perRiscaldamento!=null &&
              this.perRiscaldamento.equals(other.getPerRiscaldamento()))) &&
            ((this.periodicitaControllo==null && other.getPeriodicitaControllo()==null) || 
             (this.periodicitaControllo!=null &&
              this.periodicitaControllo.equals(other.getPeriodicitaControllo()))) &&
            ((this.potenzaTermica==null && other.getPotenzaTermica()==null) || 
             (this.potenzaTermica!=null &&
              this.potenzaTermica.equals(other.getPotenzaTermica()))) &&
            ((this.proveCombustioneImpTermicis==null && other.getProveCombustioneImpTermicis()==null) || 
             (this.proveCombustioneImpTermicis!=null &&
              java.util.Arrays.equals(this.proveCombustioneImpTermicis, other.getProveCombustioneImpTermicis()))) &&
            ((this.valoreSoglia==null && other.getValoreSoglia()==null) || 
             (this.valoreSoglia!=null &&
              this.valoreSoglia.equals(other.getValoreSoglia()))) &&
            ((this.vocTipoCaldaia==null && other.getVocTipoCaldaia()==null) || 
             (this.vocTipoCaldaia!=null &&
              this.vocTipoCaldaia.equals(other.getVocTipoCaldaia()))) &&
            ((this.vocTipoCombustibile==null && other.getVocTipoCombustibile()==null) || 
             (this.vocTipoCombustibile!=null &&
              this.vocTipoCombustibile.equals(other.getVocTipoCombustibile()))) &&
            ((this.vocTipoGeneratore==null && other.getVocTipoGeneratore()==null) || 
             (this.vocTipoGeneratore!=null &&
              this.vocTipoGeneratore.equals(other.getVocTipoGeneratore())));
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
        if (getAnnoInstallazione() != null) {
            _hashCode += getAnnoInstallazione().hashCode();
        }
        if (getDichiarazioneConformita() != null) {
            _hashCode += getDichiarazioneConformita().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getLibrettoImpianto() != null) {
            _hashCode += getLibrettoImpianto().hashCode();
        }
        if (getLibrettoUsoManutenzione() != null) {
            _hashCode += getLibrettoUsoManutenzione().hashCode();
        }
        if (getPerAcquaCalda() != null) {
            _hashCode += getPerAcquaCalda().hashCode();
        }
        if (getPerRiscaldamento() != null) {
            _hashCode += getPerRiscaldamento().hashCode();
        }
        if (getPeriodicitaControllo() != null) {
            _hashCode += getPeriodicitaControllo().hashCode();
        }
        if (getPotenzaTermica() != null) {
            _hashCode += getPotenzaTermica().hashCode();
        }
        if (getProveCombustioneImpTermicis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProveCombustioneImpTermicis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProveCombustioneImpTermicis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getValoreSoglia() != null) {
            _hashCode += getValoreSoglia().hashCode();
        }
        if (getVocTipoCaldaia() != null) {
            _hashCode += getVocTipoCaldaia().hashCode();
        }
        if (getVocTipoCombustibile() != null) {
            _hashCode += getVocTipoCombustibile().hashCode();
        }
        if (getVocTipoGeneratore() != null) {
            _hashCode += getVocTipoGeneratore().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostImpiantoTermico.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpiantoTermico"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoInstallazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoInstallazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dichiarazioneConformita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dichiarazioneConformita"));
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
        elemField.setFieldName("librettoImpianto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "librettoImpianto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("librettoUsoManutenzione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "librettoUsoManutenzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("perAcquaCalda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "perAcquaCalda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("perRiscaldamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "perRiscaldamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodicitaControllo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "periodicitaControllo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzaTermica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzaTermica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("proveCombustioneImpTermicis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "proveCombustioneImpTermicis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "proveCombustioneImpTermici"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valoreSoglia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valoreSoglia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoCaldaia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoCaldaia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoCaldaia"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoCombustibile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoCombustibile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoCombustibile"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoGeneratore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoGeneratore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoGeneratore"));
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
