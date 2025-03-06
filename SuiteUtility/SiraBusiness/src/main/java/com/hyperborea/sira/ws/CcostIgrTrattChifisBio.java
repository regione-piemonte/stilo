/**
 * CcostIgrTrattChifisBio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostIgrTrattChifisBio  implements java.io.Serializable {
    private java.lang.String destinazioneFanghi;

    private java.lang.Integer idCcost;

    private java.lang.String operazioniAccessorie;

    private java.lang.String pretrattamento;

    private java.lang.String tipoImpianto;

    private java.lang.String tipoRifiuti;

    private java.lang.String tipoTrattamento;

    private java.lang.String trattamentoFanghi;

    public CcostIgrTrattChifisBio() {
    }

    public CcostIgrTrattChifisBio(
           java.lang.String destinazioneFanghi,
           java.lang.Integer idCcost,
           java.lang.String operazioniAccessorie,
           java.lang.String pretrattamento,
           java.lang.String tipoImpianto,
           java.lang.String tipoRifiuti,
           java.lang.String tipoTrattamento,
           java.lang.String trattamentoFanghi) {
           this.destinazioneFanghi = destinazioneFanghi;
           this.idCcost = idCcost;
           this.operazioniAccessorie = operazioniAccessorie;
           this.pretrattamento = pretrattamento;
           this.tipoImpianto = tipoImpianto;
           this.tipoRifiuti = tipoRifiuti;
           this.tipoTrattamento = tipoTrattamento;
           this.trattamentoFanghi = trattamentoFanghi;
    }


    /**
     * Gets the destinazioneFanghi value for this CcostIgrTrattChifisBio.
     * 
     * @return destinazioneFanghi
     */
    public java.lang.String getDestinazioneFanghi() {
        return destinazioneFanghi;
    }


    /**
     * Sets the destinazioneFanghi value for this CcostIgrTrattChifisBio.
     * 
     * @param destinazioneFanghi
     */
    public void setDestinazioneFanghi(java.lang.String destinazioneFanghi) {
        this.destinazioneFanghi = destinazioneFanghi;
    }


    /**
     * Gets the idCcost value for this CcostIgrTrattChifisBio.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostIgrTrattChifisBio.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the operazioniAccessorie value for this CcostIgrTrattChifisBio.
     * 
     * @return operazioniAccessorie
     */
    public java.lang.String getOperazioniAccessorie() {
        return operazioniAccessorie;
    }


    /**
     * Sets the operazioniAccessorie value for this CcostIgrTrattChifisBio.
     * 
     * @param operazioniAccessorie
     */
    public void setOperazioniAccessorie(java.lang.String operazioniAccessorie) {
        this.operazioniAccessorie = operazioniAccessorie;
    }


    /**
     * Gets the pretrattamento value for this CcostIgrTrattChifisBio.
     * 
     * @return pretrattamento
     */
    public java.lang.String getPretrattamento() {
        return pretrattamento;
    }


    /**
     * Sets the pretrattamento value for this CcostIgrTrattChifisBio.
     * 
     * @param pretrattamento
     */
    public void setPretrattamento(java.lang.String pretrattamento) {
        this.pretrattamento = pretrattamento;
    }


    /**
     * Gets the tipoImpianto value for this CcostIgrTrattChifisBio.
     * 
     * @return tipoImpianto
     */
    public java.lang.String getTipoImpianto() {
        return tipoImpianto;
    }


    /**
     * Sets the tipoImpianto value for this CcostIgrTrattChifisBio.
     * 
     * @param tipoImpianto
     */
    public void setTipoImpianto(java.lang.String tipoImpianto) {
        this.tipoImpianto = tipoImpianto;
    }


    /**
     * Gets the tipoRifiuti value for this CcostIgrTrattChifisBio.
     * 
     * @return tipoRifiuti
     */
    public java.lang.String getTipoRifiuti() {
        return tipoRifiuti;
    }


    /**
     * Sets the tipoRifiuti value for this CcostIgrTrattChifisBio.
     * 
     * @param tipoRifiuti
     */
    public void setTipoRifiuti(java.lang.String tipoRifiuti) {
        this.tipoRifiuti = tipoRifiuti;
    }


    /**
     * Gets the tipoTrattamento value for this CcostIgrTrattChifisBio.
     * 
     * @return tipoTrattamento
     */
    public java.lang.String getTipoTrattamento() {
        return tipoTrattamento;
    }


    /**
     * Sets the tipoTrattamento value for this CcostIgrTrattChifisBio.
     * 
     * @param tipoTrattamento
     */
    public void setTipoTrattamento(java.lang.String tipoTrattamento) {
        this.tipoTrattamento = tipoTrattamento;
    }


    /**
     * Gets the trattamentoFanghi value for this CcostIgrTrattChifisBio.
     * 
     * @return trattamentoFanghi
     */
    public java.lang.String getTrattamentoFanghi() {
        return trattamentoFanghi;
    }


    /**
     * Sets the trattamentoFanghi value for this CcostIgrTrattChifisBio.
     * 
     * @param trattamentoFanghi
     */
    public void setTrattamentoFanghi(java.lang.String trattamentoFanghi) {
        this.trattamentoFanghi = trattamentoFanghi;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostIgrTrattChifisBio)) return false;
        CcostIgrTrattChifisBio other = (CcostIgrTrattChifisBio) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.destinazioneFanghi==null && other.getDestinazioneFanghi()==null) || 
             (this.destinazioneFanghi!=null &&
              this.destinazioneFanghi.equals(other.getDestinazioneFanghi()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.operazioniAccessorie==null && other.getOperazioniAccessorie()==null) || 
             (this.operazioniAccessorie!=null &&
              this.operazioniAccessorie.equals(other.getOperazioniAccessorie()))) &&
            ((this.pretrattamento==null && other.getPretrattamento()==null) || 
             (this.pretrattamento!=null &&
              this.pretrattamento.equals(other.getPretrattamento()))) &&
            ((this.tipoImpianto==null && other.getTipoImpianto()==null) || 
             (this.tipoImpianto!=null &&
              this.tipoImpianto.equals(other.getTipoImpianto()))) &&
            ((this.tipoRifiuti==null && other.getTipoRifiuti()==null) || 
             (this.tipoRifiuti!=null &&
              this.tipoRifiuti.equals(other.getTipoRifiuti()))) &&
            ((this.tipoTrattamento==null && other.getTipoTrattamento()==null) || 
             (this.tipoTrattamento!=null &&
              this.tipoTrattamento.equals(other.getTipoTrattamento()))) &&
            ((this.trattamentoFanghi==null && other.getTrattamentoFanghi()==null) || 
             (this.trattamentoFanghi!=null &&
              this.trattamentoFanghi.equals(other.getTrattamentoFanghi())));
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
        if (getDestinazioneFanghi() != null) {
            _hashCode += getDestinazioneFanghi().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getOperazioniAccessorie() != null) {
            _hashCode += getOperazioniAccessorie().hashCode();
        }
        if (getPretrattamento() != null) {
            _hashCode += getPretrattamento().hashCode();
        }
        if (getTipoImpianto() != null) {
            _hashCode += getTipoImpianto().hashCode();
        }
        if (getTipoRifiuti() != null) {
            _hashCode += getTipoRifiuti().hashCode();
        }
        if (getTipoTrattamento() != null) {
            _hashCode += getTipoTrattamento().hashCode();
        }
        if (getTrattamentoFanghi() != null) {
            _hashCode += getTrattamentoFanghi().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostIgrTrattChifisBio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrTrattChifisBio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinazioneFanghi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destinazioneFanghi"));
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
        elemField.setFieldName("operazioniAccessorie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "operazioniAccessorie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pretrattamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pretrattamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoImpianto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoImpianto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoRifiuti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoRifiuti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoTrattamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoTrattamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trattamentoFanghi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "trattamentoFanghi"));
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
