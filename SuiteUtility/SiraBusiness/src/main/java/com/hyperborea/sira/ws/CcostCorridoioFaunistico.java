/**
 * CcostCorridoioFaunistico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostCorridoioFaunistico  implements java.io.Serializable {
    private java.lang.String tipoSito;

    private java.lang.Float altezzaMax;

    private java.lang.Float altezzaMedia;

    private java.lang.Float altezzaMin;

    private java.lang.Float area;

    private java.lang.String caratteristicheFisiche;

    private java.lang.String codice;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.VocRegioneBiogeografica vocRegioneBiogeografica;

    public CcostCorridoioFaunistico() {
    }

    public CcostCorridoioFaunistico(
           java.lang.String tipoSito,
           java.lang.Float altezzaMax,
           java.lang.Float altezzaMedia,
           java.lang.Float altezzaMin,
           java.lang.Float area,
           java.lang.String caratteristicheFisiche,
           java.lang.String codice,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.VocRegioneBiogeografica vocRegioneBiogeografica) {
           this.tipoSito = tipoSito;
           this.altezzaMax = altezzaMax;
           this.altezzaMedia = altezzaMedia;
           this.altezzaMin = altezzaMin;
           this.area = area;
           this.caratteristicheFisiche = caratteristicheFisiche;
           this.codice = codice;
           this.idCcost = idCcost;
           this.vocRegioneBiogeografica = vocRegioneBiogeografica;
    }


    /**
     * Gets the tipoSito value for this CcostCorridoioFaunistico.
     * 
     * @return tipoSito
     */
    public java.lang.String getTipoSito() {
        return tipoSito;
    }


    /**
     * Sets the tipoSito value for this CcostCorridoioFaunistico.
     * 
     * @param tipoSito
     */
    public void setTipoSito(java.lang.String tipoSito) {
        this.tipoSito = tipoSito;
    }


    /**
     * Gets the altezzaMax value for this CcostCorridoioFaunistico.
     * 
     * @return altezzaMax
     */
    public java.lang.Float getAltezzaMax() {
        return altezzaMax;
    }


    /**
     * Sets the altezzaMax value for this CcostCorridoioFaunistico.
     * 
     * @param altezzaMax
     */
    public void setAltezzaMax(java.lang.Float altezzaMax) {
        this.altezzaMax = altezzaMax;
    }


    /**
     * Gets the altezzaMedia value for this CcostCorridoioFaunistico.
     * 
     * @return altezzaMedia
     */
    public java.lang.Float getAltezzaMedia() {
        return altezzaMedia;
    }


    /**
     * Sets the altezzaMedia value for this CcostCorridoioFaunistico.
     * 
     * @param altezzaMedia
     */
    public void setAltezzaMedia(java.lang.Float altezzaMedia) {
        this.altezzaMedia = altezzaMedia;
    }


    /**
     * Gets the altezzaMin value for this CcostCorridoioFaunistico.
     * 
     * @return altezzaMin
     */
    public java.lang.Float getAltezzaMin() {
        return altezzaMin;
    }


    /**
     * Sets the altezzaMin value for this CcostCorridoioFaunistico.
     * 
     * @param altezzaMin
     */
    public void setAltezzaMin(java.lang.Float altezzaMin) {
        this.altezzaMin = altezzaMin;
    }


    /**
     * Gets the area value for this CcostCorridoioFaunistico.
     * 
     * @return area
     */
    public java.lang.Float getArea() {
        return area;
    }


    /**
     * Sets the area value for this CcostCorridoioFaunistico.
     * 
     * @param area
     */
    public void setArea(java.lang.Float area) {
        this.area = area;
    }


    /**
     * Gets the caratteristicheFisiche value for this CcostCorridoioFaunistico.
     * 
     * @return caratteristicheFisiche
     */
    public java.lang.String getCaratteristicheFisiche() {
        return caratteristicheFisiche;
    }


    /**
     * Sets the caratteristicheFisiche value for this CcostCorridoioFaunistico.
     * 
     * @param caratteristicheFisiche
     */
    public void setCaratteristicheFisiche(java.lang.String caratteristicheFisiche) {
        this.caratteristicheFisiche = caratteristicheFisiche;
    }


    /**
     * Gets the codice value for this CcostCorridoioFaunistico.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this CcostCorridoioFaunistico.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the idCcost value for this CcostCorridoioFaunistico.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostCorridoioFaunistico.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the vocRegioneBiogeografica value for this CcostCorridoioFaunistico.
     * 
     * @return vocRegioneBiogeografica
     */
    public com.hyperborea.sira.ws.VocRegioneBiogeografica getVocRegioneBiogeografica() {
        return vocRegioneBiogeografica;
    }


    /**
     * Sets the vocRegioneBiogeografica value for this CcostCorridoioFaunistico.
     * 
     * @param vocRegioneBiogeografica
     */
    public void setVocRegioneBiogeografica(com.hyperborea.sira.ws.VocRegioneBiogeografica vocRegioneBiogeografica) {
        this.vocRegioneBiogeografica = vocRegioneBiogeografica;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostCorridoioFaunistico)) return false;
        CcostCorridoioFaunistico other = (CcostCorridoioFaunistico) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.tipoSito==null && other.getTipoSito()==null) || 
             (this.tipoSito!=null &&
              this.tipoSito.equals(other.getTipoSito()))) &&
            ((this.altezzaMax==null && other.getAltezzaMax()==null) || 
             (this.altezzaMax!=null &&
              this.altezzaMax.equals(other.getAltezzaMax()))) &&
            ((this.altezzaMedia==null && other.getAltezzaMedia()==null) || 
             (this.altezzaMedia!=null &&
              this.altezzaMedia.equals(other.getAltezzaMedia()))) &&
            ((this.altezzaMin==null && other.getAltezzaMin()==null) || 
             (this.altezzaMin!=null &&
              this.altezzaMin.equals(other.getAltezzaMin()))) &&
            ((this.area==null && other.getArea()==null) || 
             (this.area!=null &&
              this.area.equals(other.getArea()))) &&
            ((this.caratteristicheFisiche==null && other.getCaratteristicheFisiche()==null) || 
             (this.caratteristicheFisiche!=null &&
              this.caratteristicheFisiche.equals(other.getCaratteristicheFisiche()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.vocRegioneBiogeografica==null && other.getVocRegioneBiogeografica()==null) || 
             (this.vocRegioneBiogeografica!=null &&
              this.vocRegioneBiogeografica.equals(other.getVocRegioneBiogeografica())));
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
        if (getTipoSito() != null) {
            _hashCode += getTipoSito().hashCode();
        }
        if (getAltezzaMax() != null) {
            _hashCode += getAltezzaMax().hashCode();
        }
        if (getAltezzaMedia() != null) {
            _hashCode += getAltezzaMedia().hashCode();
        }
        if (getAltezzaMin() != null) {
            _hashCode += getAltezzaMin().hashCode();
        }
        if (getArea() != null) {
            _hashCode += getArea().hashCode();
        }
        if (getCaratteristicheFisiche() != null) {
            _hashCode += getCaratteristicheFisiche().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getVocRegioneBiogeografica() != null) {
            _hashCode += getVocRegioneBiogeografica().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostCorridoioFaunistico.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCorridoioFaunistico"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoSito");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoSito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altezzaMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "altezzaMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altezzaMedia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "altezzaMedia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altezzaMin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "altezzaMin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("area");
        elemField.setXmlName(new javax.xml.namespace.QName("", "area"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caratteristicheFisiche");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caratteristicheFisiche"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
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
        elemField.setFieldName("vocRegioneBiogeografica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocRegioneBiogeografica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocRegioneBiogeografica"));
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
