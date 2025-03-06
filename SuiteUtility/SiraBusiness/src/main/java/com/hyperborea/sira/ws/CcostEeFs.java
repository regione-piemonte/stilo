/**
 * CcostEeFs.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostEeFs  implements java.io.Serializable {
    private java.lang.Float area;

    private java.lang.Float capacita;

    private java.lang.String codiceCensImp;

    private java.lang.String codicePod;

    private java.lang.String codiceRintracciabilita;

    private java.lang.String codiceSigraf;

    private com.hyperborea.sira.ws.ConsProd[] consProds;

    private java.util.Calendar dataConferma;

    private java.util.Calendar dataEntrataEsercizio;

    private java.util.Calendar dataFine;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.Ingressi[] ingressis;

    private java.lang.String note;

    private java.lang.String titoloAutorizzativo;

    public CcostEeFs() {
    }

    public CcostEeFs(
           java.lang.Float area,
           java.lang.Float capacita,
           java.lang.String codiceCensImp,
           java.lang.String codicePod,
           java.lang.String codiceRintracciabilita,
           java.lang.String codiceSigraf,
           com.hyperborea.sira.ws.ConsProd[] consProds,
           java.util.Calendar dataConferma,
           java.util.Calendar dataEntrataEsercizio,
           java.util.Calendar dataFine,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.Ingressi[] ingressis,
           java.lang.String note,
           java.lang.String titoloAutorizzativo) {
           this.area = area;
           this.capacita = capacita;
           this.codiceCensImp = codiceCensImp;
           this.codicePod = codicePod;
           this.codiceRintracciabilita = codiceRintracciabilita;
           this.codiceSigraf = codiceSigraf;
           this.consProds = consProds;
           this.dataConferma = dataConferma;
           this.dataEntrataEsercizio = dataEntrataEsercizio;
           this.dataFine = dataFine;
           this.idCcost = idCcost;
           this.ingressis = ingressis;
           this.note = note;
           this.titoloAutorizzativo = titoloAutorizzativo;
    }


    /**
     * Gets the area value for this CcostEeFs.
     * 
     * @return area
     */
    public java.lang.Float getArea() {
        return area;
    }


    /**
     * Sets the area value for this CcostEeFs.
     * 
     * @param area
     */
    public void setArea(java.lang.Float area) {
        this.area = area;
    }


    /**
     * Gets the capacita value for this CcostEeFs.
     * 
     * @return capacita
     */
    public java.lang.Float getCapacita() {
        return capacita;
    }


    /**
     * Sets the capacita value for this CcostEeFs.
     * 
     * @param capacita
     */
    public void setCapacita(java.lang.Float capacita) {
        this.capacita = capacita;
    }


    /**
     * Gets the codiceCensImp value for this CcostEeFs.
     * 
     * @return codiceCensImp
     */
    public java.lang.String getCodiceCensImp() {
        return codiceCensImp;
    }


    /**
     * Sets the codiceCensImp value for this CcostEeFs.
     * 
     * @param codiceCensImp
     */
    public void setCodiceCensImp(java.lang.String codiceCensImp) {
        this.codiceCensImp = codiceCensImp;
    }


    /**
     * Gets the codicePod value for this CcostEeFs.
     * 
     * @return codicePod
     */
    public java.lang.String getCodicePod() {
        return codicePod;
    }


    /**
     * Sets the codicePod value for this CcostEeFs.
     * 
     * @param codicePod
     */
    public void setCodicePod(java.lang.String codicePod) {
        this.codicePod = codicePod;
    }


    /**
     * Gets the codiceRintracciabilita value for this CcostEeFs.
     * 
     * @return codiceRintracciabilita
     */
    public java.lang.String getCodiceRintracciabilita() {
        return codiceRintracciabilita;
    }


    /**
     * Sets the codiceRintracciabilita value for this CcostEeFs.
     * 
     * @param codiceRintracciabilita
     */
    public void setCodiceRintracciabilita(java.lang.String codiceRintracciabilita) {
        this.codiceRintracciabilita = codiceRintracciabilita;
    }


    /**
     * Gets the codiceSigraf value for this CcostEeFs.
     * 
     * @return codiceSigraf
     */
    public java.lang.String getCodiceSigraf() {
        return codiceSigraf;
    }


    /**
     * Sets the codiceSigraf value for this CcostEeFs.
     * 
     * @param codiceSigraf
     */
    public void setCodiceSigraf(java.lang.String codiceSigraf) {
        this.codiceSigraf = codiceSigraf;
    }


    /**
     * Gets the consProds value for this CcostEeFs.
     * 
     * @return consProds
     */
    public com.hyperborea.sira.ws.ConsProd[] getConsProds() {
        return consProds;
    }


    /**
     * Sets the consProds value for this CcostEeFs.
     * 
     * @param consProds
     */
    public void setConsProds(com.hyperborea.sira.ws.ConsProd[] consProds) {
        this.consProds = consProds;
    }

    public com.hyperborea.sira.ws.ConsProd getConsProds(int i) {
        return this.consProds[i];
    }

    public void setConsProds(int i, com.hyperborea.sira.ws.ConsProd _value) {
        this.consProds[i] = _value;
    }


    /**
     * Gets the dataConferma value for this CcostEeFs.
     * 
     * @return dataConferma
     */
    public java.util.Calendar getDataConferma() {
        return dataConferma;
    }


    /**
     * Sets the dataConferma value for this CcostEeFs.
     * 
     * @param dataConferma
     */
    public void setDataConferma(java.util.Calendar dataConferma) {
        this.dataConferma = dataConferma;
    }


    /**
     * Gets the dataEntrataEsercizio value for this CcostEeFs.
     * 
     * @return dataEntrataEsercizio
     */
    public java.util.Calendar getDataEntrataEsercizio() {
        return dataEntrataEsercizio;
    }


    /**
     * Sets the dataEntrataEsercizio value for this CcostEeFs.
     * 
     * @param dataEntrataEsercizio
     */
    public void setDataEntrataEsercizio(java.util.Calendar dataEntrataEsercizio) {
        this.dataEntrataEsercizio = dataEntrataEsercizio;
    }


    /**
     * Gets the dataFine value for this CcostEeFs.
     * 
     * @return dataFine
     */
    public java.util.Calendar getDataFine() {
        return dataFine;
    }


    /**
     * Sets the dataFine value for this CcostEeFs.
     * 
     * @param dataFine
     */
    public void setDataFine(java.util.Calendar dataFine) {
        this.dataFine = dataFine;
    }


    /**
     * Gets the idCcost value for this CcostEeFs.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostEeFs.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the ingressis value for this CcostEeFs.
     * 
     * @return ingressis
     */
    public com.hyperborea.sira.ws.Ingressi[] getIngressis() {
        return ingressis;
    }


    /**
     * Sets the ingressis value for this CcostEeFs.
     * 
     * @param ingressis
     */
    public void setIngressis(com.hyperborea.sira.ws.Ingressi[] ingressis) {
        this.ingressis = ingressis;
    }

    public com.hyperborea.sira.ws.Ingressi getIngressis(int i) {
        return this.ingressis[i];
    }

    public void setIngressis(int i, com.hyperborea.sira.ws.Ingressi _value) {
        this.ingressis[i] = _value;
    }


    /**
     * Gets the note value for this CcostEeFs.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this CcostEeFs.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the titoloAutorizzativo value for this CcostEeFs.
     * 
     * @return titoloAutorizzativo
     */
    public java.lang.String getTitoloAutorizzativo() {
        return titoloAutorizzativo;
    }


    /**
     * Sets the titoloAutorizzativo value for this CcostEeFs.
     * 
     * @param titoloAutorizzativo
     */
    public void setTitoloAutorizzativo(java.lang.String titoloAutorizzativo) {
        this.titoloAutorizzativo = titoloAutorizzativo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostEeFs)) return false;
        CcostEeFs other = (CcostEeFs) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.area==null && other.getArea()==null) || 
             (this.area!=null &&
              this.area.equals(other.getArea()))) &&
            ((this.capacita==null && other.getCapacita()==null) || 
             (this.capacita!=null &&
              this.capacita.equals(other.getCapacita()))) &&
            ((this.codiceCensImp==null && other.getCodiceCensImp()==null) || 
             (this.codiceCensImp!=null &&
              this.codiceCensImp.equals(other.getCodiceCensImp()))) &&
            ((this.codicePod==null && other.getCodicePod()==null) || 
             (this.codicePod!=null &&
              this.codicePod.equals(other.getCodicePod()))) &&
            ((this.codiceRintracciabilita==null && other.getCodiceRintracciabilita()==null) || 
             (this.codiceRintracciabilita!=null &&
              this.codiceRintracciabilita.equals(other.getCodiceRintracciabilita()))) &&
            ((this.codiceSigraf==null && other.getCodiceSigraf()==null) || 
             (this.codiceSigraf!=null &&
              this.codiceSigraf.equals(other.getCodiceSigraf()))) &&
            ((this.consProds==null && other.getConsProds()==null) || 
             (this.consProds!=null &&
              java.util.Arrays.equals(this.consProds, other.getConsProds()))) &&
            ((this.dataConferma==null && other.getDataConferma()==null) || 
             (this.dataConferma!=null &&
              this.dataConferma.equals(other.getDataConferma()))) &&
            ((this.dataEntrataEsercizio==null && other.getDataEntrataEsercizio()==null) || 
             (this.dataEntrataEsercizio!=null &&
              this.dataEntrataEsercizio.equals(other.getDataEntrataEsercizio()))) &&
            ((this.dataFine==null && other.getDataFine()==null) || 
             (this.dataFine!=null &&
              this.dataFine.equals(other.getDataFine()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.ingressis==null && other.getIngressis()==null) || 
             (this.ingressis!=null &&
              java.util.Arrays.equals(this.ingressis, other.getIngressis()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.titoloAutorizzativo==null && other.getTitoloAutorizzativo()==null) || 
             (this.titoloAutorizzativo!=null &&
              this.titoloAutorizzativo.equals(other.getTitoloAutorizzativo())));
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
        if (getArea() != null) {
            _hashCode += getArea().hashCode();
        }
        if (getCapacita() != null) {
            _hashCode += getCapacita().hashCode();
        }
        if (getCodiceCensImp() != null) {
            _hashCode += getCodiceCensImp().hashCode();
        }
        if (getCodicePod() != null) {
            _hashCode += getCodicePod().hashCode();
        }
        if (getCodiceRintracciabilita() != null) {
            _hashCode += getCodiceRintracciabilita().hashCode();
        }
        if (getCodiceSigraf() != null) {
            _hashCode += getCodiceSigraf().hashCode();
        }
        if (getConsProds() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConsProds());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConsProds(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDataConferma() != null) {
            _hashCode += getDataConferma().hashCode();
        }
        if (getDataEntrataEsercizio() != null) {
            _hashCode += getDataEntrataEsercizio().hashCode();
        }
        if (getDataFine() != null) {
            _hashCode += getDataFine().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getIngressis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIngressis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIngressis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getTitoloAutorizzativo() != null) {
            _hashCode += getTitoloAutorizzativo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostEeFs.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostEeFs"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("area");
        elemField.setXmlName(new javax.xml.namespace.QName("", "area"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capacita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceCensImp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceCensImp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codicePod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codicePod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceRintracciabilita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceRintracciabilita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceSigraf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceSigraf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consProds");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consProds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consProd"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataConferma");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataConferma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataEntrataEsercizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataEntrataEsercizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataFine"));
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
        elemField.setFieldName("ingressis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ingressis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ingressi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("note");
        elemField.setXmlName(new javax.xml.namespace.QName("", "note"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titoloAutorizzativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "titoloAutorizzativo"));
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
