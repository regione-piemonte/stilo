/**
 * CcostSorgentiIdr.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostSorgentiIdr  implements java.io.Serializable {
    private com.hyperborea.sira.ws.SorgentiAffioramento affioramento;

    private java.lang.String codGeniocivile;

    private java.lang.String codSalvaguardia;

    private java.lang.Float coeffEsaurimento;

    private java.util.Calendar dataMisPortale;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.SorgentiOpera operaDiPresa;

    private java.lang.Float portataMax;

    private java.lang.Float portataMed;

    private java.lang.Float portataMin;

    private com.hyperborea.sira.ws.SorgentiRegime regime;

    private java.lang.String specOpPresa;

    private java.lang.Float tempMedia;

    private com.hyperborea.sira.ws.SorgentiTipoacqua tipoAcqua;

    public CcostSorgentiIdr() {
    }

    public CcostSorgentiIdr(
           com.hyperborea.sira.ws.SorgentiAffioramento affioramento,
           java.lang.String codGeniocivile,
           java.lang.String codSalvaguardia,
           java.lang.Float coeffEsaurimento,
           java.util.Calendar dataMisPortale,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.SorgentiOpera operaDiPresa,
           java.lang.Float portataMax,
           java.lang.Float portataMed,
           java.lang.Float portataMin,
           com.hyperborea.sira.ws.SorgentiRegime regime,
           java.lang.String specOpPresa,
           java.lang.Float tempMedia,
           com.hyperborea.sira.ws.SorgentiTipoacqua tipoAcqua) {
           this.affioramento = affioramento;
           this.codGeniocivile = codGeniocivile;
           this.codSalvaguardia = codSalvaguardia;
           this.coeffEsaurimento = coeffEsaurimento;
           this.dataMisPortale = dataMisPortale;
           this.idCcost = idCcost;
           this.operaDiPresa = operaDiPresa;
           this.portataMax = portataMax;
           this.portataMed = portataMed;
           this.portataMin = portataMin;
           this.regime = regime;
           this.specOpPresa = specOpPresa;
           this.tempMedia = tempMedia;
           this.tipoAcqua = tipoAcqua;
    }


    /**
     * Gets the affioramento value for this CcostSorgentiIdr.
     * 
     * @return affioramento
     */
    public com.hyperborea.sira.ws.SorgentiAffioramento getAffioramento() {
        return affioramento;
    }


    /**
     * Sets the affioramento value for this CcostSorgentiIdr.
     * 
     * @param affioramento
     */
    public void setAffioramento(com.hyperborea.sira.ws.SorgentiAffioramento affioramento) {
        this.affioramento = affioramento;
    }


    /**
     * Gets the codGeniocivile value for this CcostSorgentiIdr.
     * 
     * @return codGeniocivile
     */
    public java.lang.String getCodGeniocivile() {
        return codGeniocivile;
    }


    /**
     * Sets the codGeniocivile value for this CcostSorgentiIdr.
     * 
     * @param codGeniocivile
     */
    public void setCodGeniocivile(java.lang.String codGeniocivile) {
        this.codGeniocivile = codGeniocivile;
    }


    /**
     * Gets the codSalvaguardia value for this CcostSorgentiIdr.
     * 
     * @return codSalvaguardia
     */
    public java.lang.String getCodSalvaguardia() {
        return codSalvaguardia;
    }


    /**
     * Sets the codSalvaguardia value for this CcostSorgentiIdr.
     * 
     * @param codSalvaguardia
     */
    public void setCodSalvaguardia(java.lang.String codSalvaguardia) {
        this.codSalvaguardia = codSalvaguardia;
    }


    /**
     * Gets the coeffEsaurimento value for this CcostSorgentiIdr.
     * 
     * @return coeffEsaurimento
     */
    public java.lang.Float getCoeffEsaurimento() {
        return coeffEsaurimento;
    }


    /**
     * Sets the coeffEsaurimento value for this CcostSorgentiIdr.
     * 
     * @param coeffEsaurimento
     */
    public void setCoeffEsaurimento(java.lang.Float coeffEsaurimento) {
        this.coeffEsaurimento = coeffEsaurimento;
    }


    /**
     * Gets the dataMisPortale value for this CcostSorgentiIdr.
     * 
     * @return dataMisPortale
     */
    public java.util.Calendar getDataMisPortale() {
        return dataMisPortale;
    }


    /**
     * Sets the dataMisPortale value for this CcostSorgentiIdr.
     * 
     * @param dataMisPortale
     */
    public void setDataMisPortale(java.util.Calendar dataMisPortale) {
        this.dataMisPortale = dataMisPortale;
    }


    /**
     * Gets the idCcost value for this CcostSorgentiIdr.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostSorgentiIdr.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the operaDiPresa value for this CcostSorgentiIdr.
     * 
     * @return operaDiPresa
     */
    public com.hyperborea.sira.ws.SorgentiOpera getOperaDiPresa() {
        return operaDiPresa;
    }


    /**
     * Sets the operaDiPresa value for this CcostSorgentiIdr.
     * 
     * @param operaDiPresa
     */
    public void setOperaDiPresa(com.hyperborea.sira.ws.SorgentiOpera operaDiPresa) {
        this.operaDiPresa = operaDiPresa;
    }


    /**
     * Gets the portataMax value for this CcostSorgentiIdr.
     * 
     * @return portataMax
     */
    public java.lang.Float getPortataMax() {
        return portataMax;
    }


    /**
     * Sets the portataMax value for this CcostSorgentiIdr.
     * 
     * @param portataMax
     */
    public void setPortataMax(java.lang.Float portataMax) {
        this.portataMax = portataMax;
    }


    /**
     * Gets the portataMed value for this CcostSorgentiIdr.
     * 
     * @return portataMed
     */
    public java.lang.Float getPortataMed() {
        return portataMed;
    }


    /**
     * Sets the portataMed value for this CcostSorgentiIdr.
     * 
     * @param portataMed
     */
    public void setPortataMed(java.lang.Float portataMed) {
        this.portataMed = portataMed;
    }


    /**
     * Gets the portataMin value for this CcostSorgentiIdr.
     * 
     * @return portataMin
     */
    public java.lang.Float getPortataMin() {
        return portataMin;
    }


    /**
     * Sets the portataMin value for this CcostSorgentiIdr.
     * 
     * @param portataMin
     */
    public void setPortataMin(java.lang.Float portataMin) {
        this.portataMin = portataMin;
    }


    /**
     * Gets the regime value for this CcostSorgentiIdr.
     * 
     * @return regime
     */
    public com.hyperborea.sira.ws.SorgentiRegime getRegime() {
        return regime;
    }


    /**
     * Sets the regime value for this CcostSorgentiIdr.
     * 
     * @param regime
     */
    public void setRegime(com.hyperborea.sira.ws.SorgentiRegime regime) {
        this.regime = regime;
    }


    /**
     * Gets the specOpPresa value for this CcostSorgentiIdr.
     * 
     * @return specOpPresa
     */
    public java.lang.String getSpecOpPresa() {
        return specOpPresa;
    }


    /**
     * Sets the specOpPresa value for this CcostSorgentiIdr.
     * 
     * @param specOpPresa
     */
    public void setSpecOpPresa(java.lang.String specOpPresa) {
        this.specOpPresa = specOpPresa;
    }


    /**
     * Gets the tempMedia value for this CcostSorgentiIdr.
     * 
     * @return tempMedia
     */
    public java.lang.Float getTempMedia() {
        return tempMedia;
    }


    /**
     * Sets the tempMedia value for this CcostSorgentiIdr.
     * 
     * @param tempMedia
     */
    public void setTempMedia(java.lang.Float tempMedia) {
        this.tempMedia = tempMedia;
    }


    /**
     * Gets the tipoAcqua value for this CcostSorgentiIdr.
     * 
     * @return tipoAcqua
     */
    public com.hyperborea.sira.ws.SorgentiTipoacqua getTipoAcqua() {
        return tipoAcqua;
    }


    /**
     * Sets the tipoAcqua value for this CcostSorgentiIdr.
     * 
     * @param tipoAcqua
     */
    public void setTipoAcqua(com.hyperborea.sira.ws.SorgentiTipoacqua tipoAcqua) {
        this.tipoAcqua = tipoAcqua;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostSorgentiIdr)) return false;
        CcostSorgentiIdr other = (CcostSorgentiIdr) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.affioramento==null && other.getAffioramento()==null) || 
             (this.affioramento!=null &&
              this.affioramento.equals(other.getAffioramento()))) &&
            ((this.codGeniocivile==null && other.getCodGeniocivile()==null) || 
             (this.codGeniocivile!=null &&
              this.codGeniocivile.equals(other.getCodGeniocivile()))) &&
            ((this.codSalvaguardia==null && other.getCodSalvaguardia()==null) || 
             (this.codSalvaguardia!=null &&
              this.codSalvaguardia.equals(other.getCodSalvaguardia()))) &&
            ((this.coeffEsaurimento==null && other.getCoeffEsaurimento()==null) || 
             (this.coeffEsaurimento!=null &&
              this.coeffEsaurimento.equals(other.getCoeffEsaurimento()))) &&
            ((this.dataMisPortale==null && other.getDataMisPortale()==null) || 
             (this.dataMisPortale!=null &&
              this.dataMisPortale.equals(other.getDataMisPortale()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.operaDiPresa==null && other.getOperaDiPresa()==null) || 
             (this.operaDiPresa!=null &&
              this.operaDiPresa.equals(other.getOperaDiPresa()))) &&
            ((this.portataMax==null && other.getPortataMax()==null) || 
             (this.portataMax!=null &&
              this.portataMax.equals(other.getPortataMax()))) &&
            ((this.portataMed==null && other.getPortataMed()==null) || 
             (this.portataMed!=null &&
              this.portataMed.equals(other.getPortataMed()))) &&
            ((this.portataMin==null && other.getPortataMin()==null) || 
             (this.portataMin!=null &&
              this.portataMin.equals(other.getPortataMin()))) &&
            ((this.regime==null && other.getRegime()==null) || 
             (this.regime!=null &&
              this.regime.equals(other.getRegime()))) &&
            ((this.specOpPresa==null && other.getSpecOpPresa()==null) || 
             (this.specOpPresa!=null &&
              this.specOpPresa.equals(other.getSpecOpPresa()))) &&
            ((this.tempMedia==null && other.getTempMedia()==null) || 
             (this.tempMedia!=null &&
              this.tempMedia.equals(other.getTempMedia()))) &&
            ((this.tipoAcqua==null && other.getTipoAcqua()==null) || 
             (this.tipoAcqua!=null &&
              this.tipoAcqua.equals(other.getTipoAcqua())));
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
        if (getAffioramento() != null) {
            _hashCode += getAffioramento().hashCode();
        }
        if (getCodGeniocivile() != null) {
            _hashCode += getCodGeniocivile().hashCode();
        }
        if (getCodSalvaguardia() != null) {
            _hashCode += getCodSalvaguardia().hashCode();
        }
        if (getCoeffEsaurimento() != null) {
            _hashCode += getCoeffEsaurimento().hashCode();
        }
        if (getDataMisPortale() != null) {
            _hashCode += getDataMisPortale().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getOperaDiPresa() != null) {
            _hashCode += getOperaDiPresa().hashCode();
        }
        if (getPortataMax() != null) {
            _hashCode += getPortataMax().hashCode();
        }
        if (getPortataMed() != null) {
            _hashCode += getPortataMed().hashCode();
        }
        if (getPortataMin() != null) {
            _hashCode += getPortataMin().hashCode();
        }
        if (getRegime() != null) {
            _hashCode += getRegime().hashCode();
        }
        if (getSpecOpPresa() != null) {
            _hashCode += getSpecOpPresa().hashCode();
        }
        if (getTempMedia() != null) {
            _hashCode += getTempMedia().hashCode();
        }
        if (getTipoAcqua() != null) {
            _hashCode += getTipoAcqua().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostSorgentiIdr.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSorgentiIdr"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("affioramento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "affioramento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgentiAffioramento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codGeniocivile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codGeniocivile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codSalvaguardia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codSalvaguardia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coeffEsaurimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "coeffEsaurimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataMisPortale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataMisPortale"));
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
        elemField.setFieldName("operaDiPresa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "operaDiPresa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgentiOpera"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataMed");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataMed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portataMin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataMin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "regime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgentiRegime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specOpPresa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "specOpPresa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tempMedia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tempMedia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoAcqua");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoAcqua"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgentiTipoacqua"));
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
