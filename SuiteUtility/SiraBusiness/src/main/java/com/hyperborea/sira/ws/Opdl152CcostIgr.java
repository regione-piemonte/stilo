/**
 * Opdl152CcostIgr.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Opdl152CcostIgr  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.util.Calendar dataEsercizio;

    private java.util.Calendar dataRegime;

    private java.lang.String descrizioneOperazione;

    private com.hyperborea.sira.ws.DmOperazRecupero dmOperazRecupero;

    private com.hyperborea.sira.ws.GruppoRifiutiGestiti[] gruppoRifiutiGestitis;

    private java.lang.Integer idOpCcost;

    private com.hyperborea.sira.ws.OpDl15206AllBc opDl15206AllBc;

    private com.hyperborea.sira.ws.Opdl152Puntodm opdl152Puntodm;

    private com.hyperborea.sira.ws.PuntoDm puntoDm;

    private java.lang.String riservaR13;

    private java.lang.String subattivitaRecupero;

    public Opdl152CcostIgr() {
    }

    public Opdl152CcostIgr(
           java.util.Calendar dataEsercizio,
           java.util.Calendar dataRegime,
           java.lang.String descrizioneOperazione,
           com.hyperborea.sira.ws.DmOperazRecupero dmOperazRecupero,
           com.hyperborea.sira.ws.GruppoRifiutiGestiti[] gruppoRifiutiGestitis,
           java.lang.Integer idOpCcost,
           com.hyperborea.sira.ws.OpDl15206AllBc opDl15206AllBc,
           com.hyperborea.sira.ws.Opdl152Puntodm opdl152Puntodm,
           com.hyperborea.sira.ws.PuntoDm puntoDm,
           java.lang.String riservaR13,
           java.lang.String subattivitaRecupero) {
        this.dataEsercizio = dataEsercizio;
        this.dataRegime = dataRegime;
        this.descrizioneOperazione = descrizioneOperazione;
        this.dmOperazRecupero = dmOperazRecupero;
        this.gruppoRifiutiGestitis = gruppoRifiutiGestitis;
        this.idOpCcost = idOpCcost;
        this.opDl15206AllBc = opDl15206AllBc;
        this.opdl152Puntodm = opdl152Puntodm;
        this.puntoDm = puntoDm;
        this.riservaR13 = riservaR13;
        this.subattivitaRecupero = subattivitaRecupero;
    }


    /**
     * Gets the dataEsercizio value for this Opdl152CcostIgr.
     * 
     * @return dataEsercizio
     */
    public java.util.Calendar getDataEsercizio() {
        return dataEsercizio;
    }


    /**
     * Sets the dataEsercizio value for this Opdl152CcostIgr.
     * 
     * @param dataEsercizio
     */
    public void setDataEsercizio(java.util.Calendar dataEsercizio) {
        this.dataEsercizio = dataEsercizio;
    }


    /**
     * Gets the dataRegime value for this Opdl152CcostIgr.
     * 
     * @return dataRegime
     */
    public java.util.Calendar getDataRegime() {
        return dataRegime;
    }


    /**
     * Sets the dataRegime value for this Opdl152CcostIgr.
     * 
     * @param dataRegime
     */
    public void setDataRegime(java.util.Calendar dataRegime) {
        this.dataRegime = dataRegime;
    }


    /**
     * Gets the descrizioneOperazione value for this Opdl152CcostIgr.
     * 
     * @return descrizioneOperazione
     */
    public java.lang.String getDescrizioneOperazione() {
        return descrizioneOperazione;
    }


    /**
     * Sets the descrizioneOperazione value for this Opdl152CcostIgr.
     * 
     * @param descrizioneOperazione
     */
    public void setDescrizioneOperazione(java.lang.String descrizioneOperazione) {
        this.descrizioneOperazione = descrizioneOperazione;
    }


    /**
     * Gets the dmOperazRecupero value for this Opdl152CcostIgr.
     * 
     * @return dmOperazRecupero
     */
    public com.hyperborea.sira.ws.DmOperazRecupero getDmOperazRecupero() {
        return dmOperazRecupero;
    }


    /**
     * Sets the dmOperazRecupero value for this Opdl152CcostIgr.
     * 
     * @param dmOperazRecupero
     */
    public void setDmOperazRecupero(com.hyperborea.sira.ws.DmOperazRecupero dmOperazRecupero) {
        this.dmOperazRecupero = dmOperazRecupero;
    }


    /**
     * Gets the gruppoRifiutiGestitis value for this Opdl152CcostIgr.
     * 
     * @return gruppoRifiutiGestitis
     */
    public com.hyperborea.sira.ws.GruppoRifiutiGestiti[] getGruppoRifiutiGestitis() {
        return gruppoRifiutiGestitis;
    }


    /**
     * Sets the gruppoRifiutiGestitis value for this Opdl152CcostIgr.
     * 
     * @param gruppoRifiutiGestitis
     */
    public void setGruppoRifiutiGestitis(com.hyperborea.sira.ws.GruppoRifiutiGestiti[] gruppoRifiutiGestitis) {
        this.gruppoRifiutiGestitis = gruppoRifiutiGestitis;
    }

    public com.hyperborea.sira.ws.GruppoRifiutiGestiti getGruppoRifiutiGestitis(int i) {
        return this.gruppoRifiutiGestitis[i];
    }

    public void setGruppoRifiutiGestitis(int i, com.hyperborea.sira.ws.GruppoRifiutiGestiti _value) {
        this.gruppoRifiutiGestitis[i] = _value;
    }


    /**
     * Gets the idOpCcost value for this Opdl152CcostIgr.
     * 
     * @return idOpCcost
     */
    public java.lang.Integer getIdOpCcost() {
        return idOpCcost;
    }


    /**
     * Sets the idOpCcost value for this Opdl152CcostIgr.
     * 
     * @param idOpCcost
     */
    public void setIdOpCcost(java.lang.Integer idOpCcost) {
        this.idOpCcost = idOpCcost;
    }


    /**
     * Gets the opDl15206AllBc value for this Opdl152CcostIgr.
     * 
     * @return opDl15206AllBc
     */
    public com.hyperborea.sira.ws.OpDl15206AllBc getOpDl15206AllBc() {
        return opDl15206AllBc;
    }


    /**
     * Sets the opDl15206AllBc value for this Opdl152CcostIgr.
     * 
     * @param opDl15206AllBc
     */
    public void setOpDl15206AllBc(com.hyperborea.sira.ws.OpDl15206AllBc opDl15206AllBc) {
        this.opDl15206AllBc = opDl15206AllBc;
    }


    /**
     * Gets the opdl152Puntodm value for this Opdl152CcostIgr.
     * 
     * @return opdl152Puntodm
     */
    public com.hyperborea.sira.ws.Opdl152Puntodm getOpdl152Puntodm() {
        return opdl152Puntodm;
    }


    /**
     * Sets the opdl152Puntodm value for this Opdl152CcostIgr.
     * 
     * @param opdl152Puntodm
     */
    public void setOpdl152Puntodm(com.hyperborea.sira.ws.Opdl152Puntodm opdl152Puntodm) {
        this.opdl152Puntodm = opdl152Puntodm;
    }


    /**
     * Gets the puntoDm value for this Opdl152CcostIgr.
     * 
     * @return puntoDm
     */
    public com.hyperborea.sira.ws.PuntoDm getPuntoDm() {
        return puntoDm;
    }


    /**
     * Sets the puntoDm value for this Opdl152CcostIgr.
     * 
     * @param puntoDm
     */
    public void setPuntoDm(com.hyperborea.sira.ws.PuntoDm puntoDm) {
        this.puntoDm = puntoDm;
    }


    /**
     * Gets the riservaR13 value for this Opdl152CcostIgr.
     * 
     * @return riservaR13
     */
    public java.lang.String getRiservaR13() {
        return riservaR13;
    }


    /**
     * Sets the riservaR13 value for this Opdl152CcostIgr.
     * 
     * @param riservaR13
     */
    public void setRiservaR13(java.lang.String riservaR13) {
        this.riservaR13 = riservaR13;
    }


    /**
     * Gets the subattivitaRecupero value for this Opdl152CcostIgr.
     * 
     * @return subattivitaRecupero
     */
    public java.lang.String getSubattivitaRecupero() {
        return subattivitaRecupero;
    }


    /**
     * Sets the subattivitaRecupero value for this Opdl152CcostIgr.
     * 
     * @param subattivitaRecupero
     */
    public void setSubattivitaRecupero(java.lang.String subattivitaRecupero) {
        this.subattivitaRecupero = subattivitaRecupero;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Opdl152CcostIgr)) return false;
        Opdl152CcostIgr other = (Opdl152CcostIgr) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.dataEsercizio==null && other.getDataEsercizio()==null) || 
             (this.dataEsercizio!=null &&
              this.dataEsercizio.equals(other.getDataEsercizio()))) &&
            ((this.dataRegime==null && other.getDataRegime()==null) || 
             (this.dataRegime!=null &&
              this.dataRegime.equals(other.getDataRegime()))) &&
            ((this.descrizioneOperazione==null && other.getDescrizioneOperazione()==null) || 
             (this.descrizioneOperazione!=null &&
              this.descrizioneOperazione.equals(other.getDescrizioneOperazione()))) &&
            ((this.dmOperazRecupero==null && other.getDmOperazRecupero()==null) || 
             (this.dmOperazRecupero!=null &&
              this.dmOperazRecupero.equals(other.getDmOperazRecupero()))) &&
            ((this.gruppoRifiutiGestitis==null && other.getGruppoRifiutiGestitis()==null) || 
             (this.gruppoRifiutiGestitis!=null &&
              java.util.Arrays.equals(this.gruppoRifiutiGestitis, other.getGruppoRifiutiGestitis()))) &&
            ((this.idOpCcost==null && other.getIdOpCcost()==null) || 
             (this.idOpCcost!=null &&
              this.idOpCcost.equals(other.getIdOpCcost()))) &&
            ((this.opDl15206AllBc==null && other.getOpDl15206AllBc()==null) || 
             (this.opDl15206AllBc!=null &&
              this.opDl15206AllBc.equals(other.getOpDl15206AllBc()))) &&
            ((this.opdl152Puntodm==null && other.getOpdl152Puntodm()==null) || 
             (this.opdl152Puntodm!=null &&
              this.opdl152Puntodm.equals(other.getOpdl152Puntodm()))) &&
            ((this.puntoDm==null && other.getPuntoDm()==null) || 
             (this.puntoDm!=null &&
              this.puntoDm.equals(other.getPuntoDm()))) &&
            ((this.riservaR13==null && other.getRiservaR13()==null) || 
             (this.riservaR13!=null &&
              this.riservaR13.equals(other.getRiservaR13()))) &&
            ((this.subattivitaRecupero==null && other.getSubattivitaRecupero()==null) || 
             (this.subattivitaRecupero!=null &&
              this.subattivitaRecupero.equals(other.getSubattivitaRecupero())));
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
        if (getDataEsercizio() != null) {
            _hashCode += getDataEsercizio().hashCode();
        }
        if (getDataRegime() != null) {
            _hashCode += getDataRegime().hashCode();
        }
        if (getDescrizioneOperazione() != null) {
            _hashCode += getDescrizioneOperazione().hashCode();
        }
        if (getDmOperazRecupero() != null) {
            _hashCode += getDmOperazRecupero().hashCode();
        }
        if (getGruppoRifiutiGestitis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGruppoRifiutiGestitis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGruppoRifiutiGestitis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIdOpCcost() != null) {
            _hashCode += getIdOpCcost().hashCode();
        }
        if (getOpDl15206AllBc() != null) {
            _hashCode += getOpDl15206AllBc().hashCode();
        }
        if (getOpdl152Puntodm() != null) {
            _hashCode += getOpdl152Puntodm().hashCode();
        }
        if (getPuntoDm() != null) {
            _hashCode += getPuntoDm().hashCode();
        }
        if (getRiservaR13() != null) {
            _hashCode += getRiservaR13().hashCode();
        }
        if (getSubattivitaRecupero() != null) {
            _hashCode += getSubattivitaRecupero().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Opdl152CcostIgr.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "opdl152CcostIgr"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataEsercizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataEsercizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataRegime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataRegime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneOperazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneOperazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dmOperazRecupero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dmOperazRecupero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "dmOperazRecupero"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gruppoRifiutiGestitis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gruppoRifiutiGestitis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "gruppoRifiutiGestiti"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idOpCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idOpCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opDl15206AllBc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opDl15206AllBc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "opDl15206AllBc"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opdl152Puntodm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opdl152Puntodm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "opdl152Puntodm"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("puntoDm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "puntoDm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "puntoDm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riservaR13");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riservaR13"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subattivitaRecupero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subattivitaRecupero"));
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
