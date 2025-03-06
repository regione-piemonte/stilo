/**
 * CcostSitoSpandimentoEz.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostSitoSpandimentoEz  implements java.io.Serializable {
    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.ModuloA1A2SitoSpandEz[] moduloA1A2SitoSpandEz;

    private java.lang.Integer numAppezzamento;

    private java.lang.Float superficieSau;

    private java.lang.Float superficieSauAcque;

    private java.lang.Float superficieSauEffluenti;

    private java.lang.Float superficieTotale;

    private java.lang.String zonaVulnerabile;

    public CcostSitoSpandimentoEz() {
    }

    public CcostSitoSpandimentoEz(
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.ModuloA1A2SitoSpandEz[] moduloA1A2SitoSpandEz,
           java.lang.Integer numAppezzamento,
           java.lang.Float superficieSau,
           java.lang.Float superficieSauAcque,
           java.lang.Float superficieSauEffluenti,
           java.lang.Float superficieTotale,
           java.lang.String zonaVulnerabile) {
           this.idCcost = idCcost;
           this.moduloA1A2SitoSpandEz = moduloA1A2SitoSpandEz;
           this.numAppezzamento = numAppezzamento;
           this.superficieSau = superficieSau;
           this.superficieSauAcque = superficieSauAcque;
           this.superficieSauEffluenti = superficieSauEffluenti;
           this.superficieTotale = superficieTotale;
           this.zonaVulnerabile = zonaVulnerabile;
    }


    /**
     * Gets the idCcost value for this CcostSitoSpandimentoEz.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostSitoSpandimentoEz.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the moduloA1A2SitoSpandEz value for this CcostSitoSpandimentoEz.
     * 
     * @return moduloA1A2SitoSpandEz
     */
    public com.hyperborea.sira.ws.ModuloA1A2SitoSpandEz[] getModuloA1A2SitoSpandEz() {
        return moduloA1A2SitoSpandEz;
    }


    /**
     * Sets the moduloA1A2SitoSpandEz value for this CcostSitoSpandimentoEz.
     * 
     * @param moduloA1A2SitoSpandEz
     */
    public void setModuloA1A2SitoSpandEz(com.hyperborea.sira.ws.ModuloA1A2SitoSpandEz[] moduloA1A2SitoSpandEz) {
        this.moduloA1A2SitoSpandEz = moduloA1A2SitoSpandEz;
    }

    public com.hyperborea.sira.ws.ModuloA1A2SitoSpandEz getModuloA1A2SitoSpandEz(int i) {
        return this.moduloA1A2SitoSpandEz[i];
    }

    public void setModuloA1A2SitoSpandEz(int i, com.hyperborea.sira.ws.ModuloA1A2SitoSpandEz _value) {
        this.moduloA1A2SitoSpandEz[i] = _value;
    }


    /**
     * Gets the numAppezzamento value for this CcostSitoSpandimentoEz.
     * 
     * @return numAppezzamento
     */
    public java.lang.Integer getNumAppezzamento() {
        return numAppezzamento;
    }


    /**
     * Sets the numAppezzamento value for this CcostSitoSpandimentoEz.
     * 
     * @param numAppezzamento
     */
    public void setNumAppezzamento(java.lang.Integer numAppezzamento) {
        this.numAppezzamento = numAppezzamento;
    }


    /**
     * Gets the superficieSau value for this CcostSitoSpandimentoEz.
     * 
     * @return superficieSau
     */
    public java.lang.Float getSuperficieSau() {
        return superficieSau;
    }


    /**
     * Sets the superficieSau value for this CcostSitoSpandimentoEz.
     * 
     * @param superficieSau
     */
    public void setSuperficieSau(java.lang.Float superficieSau) {
        this.superficieSau = superficieSau;
    }


    /**
     * Gets the superficieSauAcque value for this CcostSitoSpandimentoEz.
     * 
     * @return superficieSauAcque
     */
    public java.lang.Float getSuperficieSauAcque() {
        return superficieSauAcque;
    }


    /**
     * Sets the superficieSauAcque value for this CcostSitoSpandimentoEz.
     * 
     * @param superficieSauAcque
     */
    public void setSuperficieSauAcque(java.lang.Float superficieSauAcque) {
        this.superficieSauAcque = superficieSauAcque;
    }


    /**
     * Gets the superficieSauEffluenti value for this CcostSitoSpandimentoEz.
     * 
     * @return superficieSauEffluenti
     */
    public java.lang.Float getSuperficieSauEffluenti() {
        return superficieSauEffluenti;
    }


    /**
     * Sets the superficieSauEffluenti value for this CcostSitoSpandimentoEz.
     * 
     * @param superficieSauEffluenti
     */
    public void setSuperficieSauEffluenti(java.lang.Float superficieSauEffluenti) {
        this.superficieSauEffluenti = superficieSauEffluenti;
    }


    /**
     * Gets the superficieTotale value for this CcostSitoSpandimentoEz.
     * 
     * @return superficieTotale
     */
    public java.lang.Float getSuperficieTotale() {
        return superficieTotale;
    }


    /**
     * Sets the superficieTotale value for this CcostSitoSpandimentoEz.
     * 
     * @param superficieTotale
     */
    public void setSuperficieTotale(java.lang.Float superficieTotale) {
        this.superficieTotale = superficieTotale;
    }


    /**
     * Gets the zonaVulnerabile value for this CcostSitoSpandimentoEz.
     * 
     * @return zonaVulnerabile
     */
    public java.lang.String getZonaVulnerabile() {
        return zonaVulnerabile;
    }


    /**
     * Sets the zonaVulnerabile value for this CcostSitoSpandimentoEz.
     * 
     * @param zonaVulnerabile
     */
    public void setZonaVulnerabile(java.lang.String zonaVulnerabile) {
        this.zonaVulnerabile = zonaVulnerabile;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostSitoSpandimentoEz)) return false;
        CcostSitoSpandimentoEz other = (CcostSitoSpandimentoEz) obj;
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
            ((this.moduloA1A2SitoSpandEz==null && other.getModuloA1A2SitoSpandEz()==null) || 
             (this.moduloA1A2SitoSpandEz!=null &&
              java.util.Arrays.equals(this.moduloA1A2SitoSpandEz, other.getModuloA1A2SitoSpandEz()))) &&
            ((this.numAppezzamento==null && other.getNumAppezzamento()==null) || 
             (this.numAppezzamento!=null &&
              this.numAppezzamento.equals(other.getNumAppezzamento()))) &&
            ((this.superficieSau==null && other.getSuperficieSau()==null) || 
             (this.superficieSau!=null &&
              this.superficieSau.equals(other.getSuperficieSau()))) &&
            ((this.superficieSauAcque==null && other.getSuperficieSauAcque()==null) || 
             (this.superficieSauAcque!=null &&
              this.superficieSauAcque.equals(other.getSuperficieSauAcque()))) &&
            ((this.superficieSauEffluenti==null && other.getSuperficieSauEffluenti()==null) || 
             (this.superficieSauEffluenti!=null &&
              this.superficieSauEffluenti.equals(other.getSuperficieSauEffluenti()))) &&
            ((this.superficieTotale==null && other.getSuperficieTotale()==null) || 
             (this.superficieTotale!=null &&
              this.superficieTotale.equals(other.getSuperficieTotale()))) &&
            ((this.zonaVulnerabile==null && other.getZonaVulnerabile()==null) || 
             (this.zonaVulnerabile!=null &&
              this.zonaVulnerabile.equals(other.getZonaVulnerabile())));
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
        if (getModuloA1A2SitoSpandEz() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getModuloA1A2SitoSpandEz());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getModuloA1A2SitoSpandEz(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNumAppezzamento() != null) {
            _hashCode += getNumAppezzamento().hashCode();
        }
        if (getSuperficieSau() != null) {
            _hashCode += getSuperficieSau().hashCode();
        }
        if (getSuperficieSauAcque() != null) {
            _hashCode += getSuperficieSauAcque().hashCode();
        }
        if (getSuperficieSauEffluenti() != null) {
            _hashCode += getSuperficieSauEffluenti().hashCode();
        }
        if (getSuperficieTotale() != null) {
            _hashCode += getSuperficieTotale().hashCode();
        }
        if (getZonaVulnerabile() != null) {
            _hashCode += getZonaVulnerabile().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostSitoSpandimentoEz.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSitoSpandimentoEz"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("moduloA1A2SitoSpandEz");
        elemField.setXmlName(new javax.xml.namespace.QName("", "moduloA1a2SitoSpandEz"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "moduloA1A2SitoSpandEz"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numAppezzamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numAppezzamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieSau");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieSau"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieSauAcque");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieSauAcque"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieSauEffluenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieSauEffluenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieTotale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieTotale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zonaVulnerabile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "zonaVulnerabile"));
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
