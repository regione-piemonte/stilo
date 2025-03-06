/**
 * Bonifiche.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Bonifiche  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CarattIntervento[] carattInterventos;

    private java.lang.Float costoIntervento;

    private java.util.Calendar dataSvincoloGaranzia;

    private java.lang.Float durataIntervento;

    private java.lang.Integer garanzia;

    private java.lang.String garanziaPerFasi;

    private java.lang.Integer idBonifica;

    private java.lang.String note;

    private java.lang.Integer numFasi;

    private java.lang.Integer perFasi;

    private java.lang.Float percentualeGaranzia;

    private java.lang.String tipoFasi;

    private java.lang.String tipoIntervento;

    private com.hyperborea.sira.ws.TecnologieUtilizzate[] tipologieUtilizates;

    public Bonifiche() {
    }

    public Bonifiche(
           com.hyperborea.sira.ws.CarattIntervento[] carattInterventos,
           java.lang.Float costoIntervento,
           java.util.Calendar dataSvincoloGaranzia,
           java.lang.Float durataIntervento,
           java.lang.Integer garanzia,
           java.lang.String garanziaPerFasi,
           java.lang.Integer idBonifica,
           java.lang.String note,
           java.lang.Integer numFasi,
           java.lang.Integer perFasi,
           java.lang.Float percentualeGaranzia,
           java.lang.String tipoFasi,
           java.lang.String tipoIntervento,
           com.hyperborea.sira.ws.TecnologieUtilizzate[] tipologieUtilizates) {
        this.carattInterventos = carattInterventos;
        this.costoIntervento = costoIntervento;
        this.dataSvincoloGaranzia = dataSvincoloGaranzia;
        this.durataIntervento = durataIntervento;
        this.garanzia = garanzia;
        this.garanziaPerFasi = garanziaPerFasi;
        this.idBonifica = idBonifica;
        this.note = note;
        this.numFasi = numFasi;
        this.perFasi = perFasi;
        this.percentualeGaranzia = percentualeGaranzia;
        this.tipoFasi = tipoFasi;
        this.tipoIntervento = tipoIntervento;
        this.tipologieUtilizates = tipologieUtilizates;
    }


    /**
     * Gets the carattInterventos value for this Bonifiche.
     * 
     * @return carattInterventos
     */
    public com.hyperborea.sira.ws.CarattIntervento[] getCarattInterventos() {
        return carattInterventos;
    }


    /**
     * Sets the carattInterventos value for this Bonifiche.
     * 
     * @param carattInterventos
     */
    public void setCarattInterventos(com.hyperborea.sira.ws.CarattIntervento[] carattInterventos) {
        this.carattInterventos = carattInterventos;
    }

    public com.hyperborea.sira.ws.CarattIntervento getCarattInterventos(int i) {
        return this.carattInterventos[i];
    }

    public void setCarattInterventos(int i, com.hyperborea.sira.ws.CarattIntervento _value) {
        this.carattInterventos[i] = _value;
    }


    /**
     * Gets the costoIntervento value for this Bonifiche.
     * 
     * @return costoIntervento
     */
    public java.lang.Float getCostoIntervento() {
        return costoIntervento;
    }


    /**
     * Sets the costoIntervento value for this Bonifiche.
     * 
     * @param costoIntervento
     */
    public void setCostoIntervento(java.lang.Float costoIntervento) {
        this.costoIntervento = costoIntervento;
    }


    /**
     * Gets the dataSvincoloGaranzia value for this Bonifiche.
     * 
     * @return dataSvincoloGaranzia
     */
    public java.util.Calendar getDataSvincoloGaranzia() {
        return dataSvincoloGaranzia;
    }


    /**
     * Sets the dataSvincoloGaranzia value for this Bonifiche.
     * 
     * @param dataSvincoloGaranzia
     */
    public void setDataSvincoloGaranzia(java.util.Calendar dataSvincoloGaranzia) {
        this.dataSvincoloGaranzia = dataSvincoloGaranzia;
    }


    /**
     * Gets the durataIntervento value for this Bonifiche.
     * 
     * @return durataIntervento
     */
    public java.lang.Float getDurataIntervento() {
        return durataIntervento;
    }


    /**
     * Sets the durataIntervento value for this Bonifiche.
     * 
     * @param durataIntervento
     */
    public void setDurataIntervento(java.lang.Float durataIntervento) {
        this.durataIntervento = durataIntervento;
    }


    /**
     * Gets the garanzia value for this Bonifiche.
     * 
     * @return garanzia
     */
    public java.lang.Integer getGaranzia() {
        return garanzia;
    }


    /**
     * Sets the garanzia value for this Bonifiche.
     * 
     * @param garanzia
     */
    public void setGaranzia(java.lang.Integer garanzia) {
        this.garanzia = garanzia;
    }


    /**
     * Gets the garanziaPerFasi value for this Bonifiche.
     * 
     * @return garanziaPerFasi
     */
    public java.lang.String getGaranziaPerFasi() {
        return garanziaPerFasi;
    }


    /**
     * Sets the garanziaPerFasi value for this Bonifiche.
     * 
     * @param garanziaPerFasi
     */
    public void setGaranziaPerFasi(java.lang.String garanziaPerFasi) {
        this.garanziaPerFasi = garanziaPerFasi;
    }


    /**
     * Gets the idBonifica value for this Bonifiche.
     * 
     * @return idBonifica
     */
    public java.lang.Integer getIdBonifica() {
        return idBonifica;
    }


    /**
     * Sets the idBonifica value for this Bonifiche.
     * 
     * @param idBonifica
     */
    public void setIdBonifica(java.lang.Integer idBonifica) {
        this.idBonifica = idBonifica;
    }


    /**
     * Gets the note value for this Bonifiche.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this Bonifiche.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the numFasi value for this Bonifiche.
     * 
     * @return numFasi
     */
    public java.lang.Integer getNumFasi() {
        return numFasi;
    }


    /**
     * Sets the numFasi value for this Bonifiche.
     * 
     * @param numFasi
     */
    public void setNumFasi(java.lang.Integer numFasi) {
        this.numFasi = numFasi;
    }


    /**
     * Gets the perFasi value for this Bonifiche.
     * 
     * @return perFasi
     */
    public java.lang.Integer getPerFasi() {
        return perFasi;
    }


    /**
     * Sets the perFasi value for this Bonifiche.
     * 
     * @param perFasi
     */
    public void setPerFasi(java.lang.Integer perFasi) {
        this.perFasi = perFasi;
    }


    /**
     * Gets the percentualeGaranzia value for this Bonifiche.
     * 
     * @return percentualeGaranzia
     */
    public java.lang.Float getPercentualeGaranzia() {
        return percentualeGaranzia;
    }


    /**
     * Sets the percentualeGaranzia value for this Bonifiche.
     * 
     * @param percentualeGaranzia
     */
    public void setPercentualeGaranzia(java.lang.Float percentualeGaranzia) {
        this.percentualeGaranzia = percentualeGaranzia;
    }


    /**
     * Gets the tipoFasi value for this Bonifiche.
     * 
     * @return tipoFasi
     */
    public java.lang.String getTipoFasi() {
        return tipoFasi;
    }


    /**
     * Sets the tipoFasi value for this Bonifiche.
     * 
     * @param tipoFasi
     */
    public void setTipoFasi(java.lang.String tipoFasi) {
        this.tipoFasi = tipoFasi;
    }


    /**
     * Gets the tipoIntervento value for this Bonifiche.
     * 
     * @return tipoIntervento
     */
    public java.lang.String getTipoIntervento() {
        return tipoIntervento;
    }


    /**
     * Sets the tipoIntervento value for this Bonifiche.
     * 
     * @param tipoIntervento
     */
    public void setTipoIntervento(java.lang.String tipoIntervento) {
        this.tipoIntervento = tipoIntervento;
    }


    /**
     * Gets the tipologieUtilizates value for this Bonifiche.
     * 
     * @return tipologieUtilizates
     */
    public com.hyperborea.sira.ws.TecnologieUtilizzate[] getTipologieUtilizates() {
        return tipologieUtilizates;
    }


    /**
     * Sets the tipologieUtilizates value for this Bonifiche.
     * 
     * @param tipologieUtilizates
     */
    public void setTipologieUtilizates(com.hyperborea.sira.ws.TecnologieUtilizzate[] tipologieUtilizates) {
        this.tipologieUtilizates = tipologieUtilizates;
    }

    public com.hyperborea.sira.ws.TecnologieUtilizzate getTipologieUtilizates(int i) {
        return this.tipologieUtilizates[i];
    }

    public void setTipologieUtilizates(int i, com.hyperborea.sira.ws.TecnologieUtilizzate _value) {
        this.tipologieUtilizates[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Bonifiche)) return false;
        Bonifiche other = (Bonifiche) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.carattInterventos==null && other.getCarattInterventos()==null) || 
             (this.carattInterventos!=null &&
              java.util.Arrays.equals(this.carattInterventos, other.getCarattInterventos()))) &&
            ((this.costoIntervento==null && other.getCostoIntervento()==null) || 
             (this.costoIntervento!=null &&
              this.costoIntervento.equals(other.getCostoIntervento()))) &&
            ((this.dataSvincoloGaranzia==null && other.getDataSvincoloGaranzia()==null) || 
             (this.dataSvincoloGaranzia!=null &&
              this.dataSvincoloGaranzia.equals(other.getDataSvincoloGaranzia()))) &&
            ((this.durataIntervento==null && other.getDurataIntervento()==null) || 
             (this.durataIntervento!=null &&
              this.durataIntervento.equals(other.getDurataIntervento()))) &&
            ((this.garanzia==null && other.getGaranzia()==null) || 
             (this.garanzia!=null &&
              this.garanzia.equals(other.getGaranzia()))) &&
            ((this.garanziaPerFasi==null && other.getGaranziaPerFasi()==null) || 
             (this.garanziaPerFasi!=null &&
              this.garanziaPerFasi.equals(other.getGaranziaPerFasi()))) &&
            ((this.idBonifica==null && other.getIdBonifica()==null) || 
             (this.idBonifica!=null &&
              this.idBonifica.equals(other.getIdBonifica()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.numFasi==null && other.getNumFasi()==null) || 
             (this.numFasi!=null &&
              this.numFasi.equals(other.getNumFasi()))) &&
            ((this.perFasi==null && other.getPerFasi()==null) || 
             (this.perFasi!=null &&
              this.perFasi.equals(other.getPerFasi()))) &&
            ((this.percentualeGaranzia==null && other.getPercentualeGaranzia()==null) || 
             (this.percentualeGaranzia!=null &&
              this.percentualeGaranzia.equals(other.getPercentualeGaranzia()))) &&
            ((this.tipoFasi==null && other.getTipoFasi()==null) || 
             (this.tipoFasi!=null &&
              this.tipoFasi.equals(other.getTipoFasi()))) &&
            ((this.tipoIntervento==null && other.getTipoIntervento()==null) || 
             (this.tipoIntervento!=null &&
              this.tipoIntervento.equals(other.getTipoIntervento()))) &&
            ((this.tipologieUtilizates==null && other.getTipologieUtilizates()==null) || 
             (this.tipologieUtilizates!=null &&
              java.util.Arrays.equals(this.tipologieUtilizates, other.getTipologieUtilizates())));
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
        if (getCarattInterventos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCarattInterventos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCarattInterventos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCostoIntervento() != null) {
            _hashCode += getCostoIntervento().hashCode();
        }
        if (getDataSvincoloGaranzia() != null) {
            _hashCode += getDataSvincoloGaranzia().hashCode();
        }
        if (getDurataIntervento() != null) {
            _hashCode += getDurataIntervento().hashCode();
        }
        if (getGaranzia() != null) {
            _hashCode += getGaranzia().hashCode();
        }
        if (getGaranziaPerFasi() != null) {
            _hashCode += getGaranziaPerFasi().hashCode();
        }
        if (getIdBonifica() != null) {
            _hashCode += getIdBonifica().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getNumFasi() != null) {
            _hashCode += getNumFasi().hashCode();
        }
        if (getPerFasi() != null) {
            _hashCode += getPerFasi().hashCode();
        }
        if (getPercentualeGaranzia() != null) {
            _hashCode += getPercentualeGaranzia().hashCode();
        }
        if (getTipoFasi() != null) {
            _hashCode += getTipoFasi().hashCode();
        }
        if (getTipoIntervento() != null) {
            _hashCode += getTipoIntervento().hashCode();
        }
        if (getTipologieUtilizates() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTipologieUtilizates());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTipologieUtilizates(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Bonifiche.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "bonifiche"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("carattInterventos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "carattInterventos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "carattIntervento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("costoIntervento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "costoIntervento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataSvincoloGaranzia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataSvincoloGaranzia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("durataIntervento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "durataIntervento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("garanzia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "garanzia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("garanziaPerFasi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "garanziaPerFasi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idBonifica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idBonifica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("numFasi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numFasi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("perFasi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "perFasi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentualeGaranzia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "percentualeGaranzia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoFasi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoFasi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoIntervento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoIntervento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologieUtilizates");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologieUtilizates"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tecnologieUtilizzate"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
