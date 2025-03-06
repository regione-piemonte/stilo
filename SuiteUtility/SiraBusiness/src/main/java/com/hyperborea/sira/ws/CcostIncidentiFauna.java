/**
 * CcostIncidentiFauna.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostIncidentiFauna  implements java.io.Serializable {
    private java.lang.String danno;

    private java.util.Calendar dataIncidente;

    private java.lang.Integer idCcost;

    private java.lang.Float importoCorrisposto;

    private java.lang.Float importoPresunto;

    private java.lang.Float km;

    private java.lang.String nomeStrada;

    private java.lang.String noteEsemplare;

    private java.lang.String oraIncidente;

    private java.lang.Integer presenzaCartelli;

    private com.hyperborea.sira.ws.Specie specie;

    private com.hyperborea.sira.ws.VocDestino vocDestino;

    private com.hyperborea.sira.ws.VocTipologiaDiStrada vocTipologiaDiStrada;

    public CcostIncidentiFauna() {
    }

    public CcostIncidentiFauna(
           java.lang.String danno,
           java.util.Calendar dataIncidente,
           java.lang.Integer idCcost,
           java.lang.Float importoCorrisposto,
           java.lang.Float importoPresunto,
           java.lang.Float km,
           java.lang.String nomeStrada,
           java.lang.String noteEsemplare,
           java.lang.String oraIncidente,
           java.lang.Integer presenzaCartelli,
           com.hyperborea.sira.ws.Specie specie,
           com.hyperborea.sira.ws.VocDestino vocDestino,
           com.hyperborea.sira.ws.VocTipologiaDiStrada vocTipologiaDiStrada) {
           this.danno = danno;
           this.dataIncidente = dataIncidente;
           this.idCcost = idCcost;
           this.importoCorrisposto = importoCorrisposto;
           this.importoPresunto = importoPresunto;
           this.km = km;
           this.nomeStrada = nomeStrada;
           this.noteEsemplare = noteEsemplare;
           this.oraIncidente = oraIncidente;
           this.presenzaCartelli = presenzaCartelli;
           this.specie = specie;
           this.vocDestino = vocDestino;
           this.vocTipologiaDiStrada = vocTipologiaDiStrada;
    }


    /**
     * Gets the danno value for this CcostIncidentiFauna.
     * 
     * @return danno
     */
    public java.lang.String getDanno() {
        return danno;
    }


    /**
     * Sets the danno value for this CcostIncidentiFauna.
     * 
     * @param danno
     */
    public void setDanno(java.lang.String danno) {
        this.danno = danno;
    }


    /**
     * Gets the dataIncidente value for this CcostIncidentiFauna.
     * 
     * @return dataIncidente
     */
    public java.util.Calendar getDataIncidente() {
        return dataIncidente;
    }


    /**
     * Sets the dataIncidente value for this CcostIncidentiFauna.
     * 
     * @param dataIncidente
     */
    public void setDataIncidente(java.util.Calendar dataIncidente) {
        this.dataIncidente = dataIncidente;
    }


    /**
     * Gets the idCcost value for this CcostIncidentiFauna.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostIncidentiFauna.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the importoCorrisposto value for this CcostIncidentiFauna.
     * 
     * @return importoCorrisposto
     */
    public java.lang.Float getImportoCorrisposto() {
        return importoCorrisposto;
    }


    /**
     * Sets the importoCorrisposto value for this CcostIncidentiFauna.
     * 
     * @param importoCorrisposto
     */
    public void setImportoCorrisposto(java.lang.Float importoCorrisposto) {
        this.importoCorrisposto = importoCorrisposto;
    }


    /**
     * Gets the importoPresunto value for this CcostIncidentiFauna.
     * 
     * @return importoPresunto
     */
    public java.lang.Float getImportoPresunto() {
        return importoPresunto;
    }


    /**
     * Sets the importoPresunto value for this CcostIncidentiFauna.
     * 
     * @param importoPresunto
     */
    public void setImportoPresunto(java.lang.Float importoPresunto) {
        this.importoPresunto = importoPresunto;
    }


    /**
     * Gets the km value for this CcostIncidentiFauna.
     * 
     * @return km
     */
    public java.lang.Float getKm() {
        return km;
    }


    /**
     * Sets the km value for this CcostIncidentiFauna.
     * 
     * @param km
     */
    public void setKm(java.lang.Float km) {
        this.km = km;
    }


    /**
     * Gets the nomeStrada value for this CcostIncidentiFauna.
     * 
     * @return nomeStrada
     */
    public java.lang.String getNomeStrada() {
        return nomeStrada;
    }


    /**
     * Sets the nomeStrada value for this CcostIncidentiFauna.
     * 
     * @param nomeStrada
     */
    public void setNomeStrada(java.lang.String nomeStrada) {
        this.nomeStrada = nomeStrada;
    }


    /**
     * Gets the noteEsemplare value for this CcostIncidentiFauna.
     * 
     * @return noteEsemplare
     */
    public java.lang.String getNoteEsemplare() {
        return noteEsemplare;
    }


    /**
     * Sets the noteEsemplare value for this CcostIncidentiFauna.
     * 
     * @param noteEsemplare
     */
    public void setNoteEsemplare(java.lang.String noteEsemplare) {
        this.noteEsemplare = noteEsemplare;
    }


    /**
     * Gets the oraIncidente value for this CcostIncidentiFauna.
     * 
     * @return oraIncidente
     */
    public java.lang.String getOraIncidente() {
        return oraIncidente;
    }


    /**
     * Sets the oraIncidente value for this CcostIncidentiFauna.
     * 
     * @param oraIncidente
     */
    public void setOraIncidente(java.lang.String oraIncidente) {
        this.oraIncidente = oraIncidente;
    }


    /**
     * Gets the presenzaCartelli value for this CcostIncidentiFauna.
     * 
     * @return presenzaCartelli
     */
    public java.lang.Integer getPresenzaCartelli() {
        return presenzaCartelli;
    }


    /**
     * Sets the presenzaCartelli value for this CcostIncidentiFauna.
     * 
     * @param presenzaCartelli
     */
    public void setPresenzaCartelli(java.lang.Integer presenzaCartelli) {
        this.presenzaCartelli = presenzaCartelli;
    }


    /**
     * Gets the specie value for this CcostIncidentiFauna.
     * 
     * @return specie
     */
    public com.hyperborea.sira.ws.Specie getSpecie() {
        return specie;
    }


    /**
     * Sets the specie value for this CcostIncidentiFauna.
     * 
     * @param specie
     */
    public void setSpecie(com.hyperborea.sira.ws.Specie specie) {
        this.specie = specie;
    }


    /**
     * Gets the vocDestino value for this CcostIncidentiFauna.
     * 
     * @return vocDestino
     */
    public com.hyperborea.sira.ws.VocDestino getVocDestino() {
        return vocDestino;
    }


    /**
     * Sets the vocDestino value for this CcostIncidentiFauna.
     * 
     * @param vocDestino
     */
    public void setVocDestino(com.hyperborea.sira.ws.VocDestino vocDestino) {
        this.vocDestino = vocDestino;
    }


    /**
     * Gets the vocTipologiaDiStrada value for this CcostIncidentiFauna.
     * 
     * @return vocTipologiaDiStrada
     */
    public com.hyperborea.sira.ws.VocTipologiaDiStrada getVocTipologiaDiStrada() {
        return vocTipologiaDiStrada;
    }


    /**
     * Sets the vocTipologiaDiStrada value for this CcostIncidentiFauna.
     * 
     * @param vocTipologiaDiStrada
     */
    public void setVocTipologiaDiStrada(com.hyperborea.sira.ws.VocTipologiaDiStrada vocTipologiaDiStrada) {
        this.vocTipologiaDiStrada = vocTipologiaDiStrada;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostIncidentiFauna)) return false;
        CcostIncidentiFauna other = (CcostIncidentiFauna) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.danno==null && other.getDanno()==null) || 
             (this.danno!=null &&
              this.danno.equals(other.getDanno()))) &&
            ((this.dataIncidente==null && other.getDataIncidente()==null) || 
             (this.dataIncidente!=null &&
              this.dataIncidente.equals(other.getDataIncidente()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.importoCorrisposto==null && other.getImportoCorrisposto()==null) || 
             (this.importoCorrisposto!=null &&
              this.importoCorrisposto.equals(other.getImportoCorrisposto()))) &&
            ((this.importoPresunto==null && other.getImportoPresunto()==null) || 
             (this.importoPresunto!=null &&
              this.importoPresunto.equals(other.getImportoPresunto()))) &&
            ((this.km==null && other.getKm()==null) || 
             (this.km!=null &&
              this.km.equals(other.getKm()))) &&
            ((this.nomeStrada==null && other.getNomeStrada()==null) || 
             (this.nomeStrada!=null &&
              this.nomeStrada.equals(other.getNomeStrada()))) &&
            ((this.noteEsemplare==null && other.getNoteEsemplare()==null) || 
             (this.noteEsemplare!=null &&
              this.noteEsemplare.equals(other.getNoteEsemplare()))) &&
            ((this.oraIncidente==null && other.getOraIncidente()==null) || 
             (this.oraIncidente!=null &&
              this.oraIncidente.equals(other.getOraIncidente()))) &&
            ((this.presenzaCartelli==null && other.getPresenzaCartelli()==null) || 
             (this.presenzaCartelli!=null &&
              this.presenzaCartelli.equals(other.getPresenzaCartelli()))) &&
            ((this.specie==null && other.getSpecie()==null) || 
             (this.specie!=null &&
              this.specie.equals(other.getSpecie()))) &&
            ((this.vocDestino==null && other.getVocDestino()==null) || 
             (this.vocDestino!=null &&
              this.vocDestino.equals(other.getVocDestino()))) &&
            ((this.vocTipologiaDiStrada==null && other.getVocTipologiaDiStrada()==null) || 
             (this.vocTipologiaDiStrada!=null &&
              this.vocTipologiaDiStrada.equals(other.getVocTipologiaDiStrada())));
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
        if (getDanno() != null) {
            _hashCode += getDanno().hashCode();
        }
        if (getDataIncidente() != null) {
            _hashCode += getDataIncidente().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getImportoCorrisposto() != null) {
            _hashCode += getImportoCorrisposto().hashCode();
        }
        if (getImportoPresunto() != null) {
            _hashCode += getImportoPresunto().hashCode();
        }
        if (getKm() != null) {
            _hashCode += getKm().hashCode();
        }
        if (getNomeStrada() != null) {
            _hashCode += getNomeStrada().hashCode();
        }
        if (getNoteEsemplare() != null) {
            _hashCode += getNoteEsemplare().hashCode();
        }
        if (getOraIncidente() != null) {
            _hashCode += getOraIncidente().hashCode();
        }
        if (getPresenzaCartelli() != null) {
            _hashCode += getPresenzaCartelli().hashCode();
        }
        if (getSpecie() != null) {
            _hashCode += getSpecie().hashCode();
        }
        if (getVocDestino() != null) {
            _hashCode += getVocDestino().hashCode();
        }
        if (getVocTipologiaDiStrada() != null) {
            _hashCode += getVocTipologiaDiStrada().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostIncidentiFauna.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIncidentiFauna"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("danno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "danno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataIncidente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataIncidente"));
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
        elemField.setFieldName("importoCorrisposto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importoCorrisposto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importoPresunto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importoPresunto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("km");
        elemField.setXmlName(new javax.xml.namespace.QName("", "km"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeStrada");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeStrada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noteEsemplare");
        elemField.setXmlName(new javax.xml.namespace.QName("", "noteEsemplare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oraIncidente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oraIncidente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("presenzaCartelli");
        elemField.setXmlName(new javax.xml.namespace.QName("", "presenzaCartelli"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "specie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "specie"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocDestino");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocDestino"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocDestino"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaDiStrada");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaDiStrada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaDiStrada"));
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
