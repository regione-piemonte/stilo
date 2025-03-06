/**
 * CcostActr.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostActr  implements java.io.Serializable {
    private java.lang.Float areabacinoscolante;

    private java.lang.Integer areasoggettaasit;

    private java.lang.String caratteristicheidrologiche;

    private java.lang.Integer idCcost;

    private java.lang.String idCorpoIdrico;

    private java.lang.String idsiramigra;

    private java.lang.String strutturafisica;

    private java.lang.String tipologia;

    public CcostActr() {
    }

    public CcostActr(
           java.lang.Float areabacinoscolante,
           java.lang.Integer areasoggettaasit,
           java.lang.String caratteristicheidrologiche,
           java.lang.Integer idCcost,
           java.lang.String idCorpoIdrico,
           java.lang.String idsiramigra,
           java.lang.String strutturafisica,
           java.lang.String tipologia) {
           this.areabacinoscolante = areabacinoscolante;
           this.areasoggettaasit = areasoggettaasit;
           this.caratteristicheidrologiche = caratteristicheidrologiche;
           this.idCcost = idCcost;
           this.idCorpoIdrico = idCorpoIdrico;
           this.idsiramigra = idsiramigra;
           this.strutturafisica = strutturafisica;
           this.tipologia = tipologia;
    }


    /**
     * Gets the areabacinoscolante value for this CcostActr.
     * 
     * @return areabacinoscolante
     */
    public java.lang.Float getAreabacinoscolante() {
        return areabacinoscolante;
    }


    /**
     * Sets the areabacinoscolante value for this CcostActr.
     * 
     * @param areabacinoscolante
     */
    public void setAreabacinoscolante(java.lang.Float areabacinoscolante) {
        this.areabacinoscolante = areabacinoscolante;
    }


    /**
     * Gets the areasoggettaasit value for this CcostActr.
     * 
     * @return areasoggettaasit
     */
    public java.lang.Integer getAreasoggettaasit() {
        return areasoggettaasit;
    }


    /**
     * Sets the areasoggettaasit value for this CcostActr.
     * 
     * @param areasoggettaasit
     */
    public void setAreasoggettaasit(java.lang.Integer areasoggettaasit) {
        this.areasoggettaasit = areasoggettaasit;
    }


    /**
     * Gets the caratteristicheidrologiche value for this CcostActr.
     * 
     * @return caratteristicheidrologiche
     */
    public java.lang.String getCaratteristicheidrologiche() {
        return caratteristicheidrologiche;
    }


    /**
     * Sets the caratteristicheidrologiche value for this CcostActr.
     * 
     * @param caratteristicheidrologiche
     */
    public void setCaratteristicheidrologiche(java.lang.String caratteristicheidrologiche) {
        this.caratteristicheidrologiche = caratteristicheidrologiche;
    }


    /**
     * Gets the idCcost value for this CcostActr.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostActr.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the idCorpoIdrico value for this CcostActr.
     * 
     * @return idCorpoIdrico
     */
    public java.lang.String getIdCorpoIdrico() {
        return idCorpoIdrico;
    }


    /**
     * Sets the idCorpoIdrico value for this CcostActr.
     * 
     * @param idCorpoIdrico
     */
    public void setIdCorpoIdrico(java.lang.String idCorpoIdrico) {
        this.idCorpoIdrico = idCorpoIdrico;
    }


    /**
     * Gets the idsiramigra value for this CcostActr.
     * 
     * @return idsiramigra
     */
    public java.lang.String getIdsiramigra() {
        return idsiramigra;
    }


    /**
     * Sets the idsiramigra value for this CcostActr.
     * 
     * @param idsiramigra
     */
    public void setIdsiramigra(java.lang.String idsiramigra) {
        this.idsiramigra = idsiramigra;
    }


    /**
     * Gets the strutturafisica value for this CcostActr.
     * 
     * @return strutturafisica
     */
    public java.lang.String getStrutturafisica() {
        return strutturafisica;
    }


    /**
     * Sets the strutturafisica value for this CcostActr.
     * 
     * @param strutturafisica
     */
    public void setStrutturafisica(java.lang.String strutturafisica) {
        this.strutturafisica = strutturafisica;
    }


    /**
     * Gets the tipologia value for this CcostActr.
     * 
     * @return tipologia
     */
    public java.lang.String getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this CcostActr.
     * 
     * @param tipologia
     */
    public void setTipologia(java.lang.String tipologia) {
        this.tipologia = tipologia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostActr)) return false;
        CcostActr other = (CcostActr) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.areabacinoscolante==null && other.getAreabacinoscolante()==null) || 
             (this.areabacinoscolante!=null &&
              this.areabacinoscolante.equals(other.getAreabacinoscolante()))) &&
            ((this.areasoggettaasit==null && other.getAreasoggettaasit()==null) || 
             (this.areasoggettaasit!=null &&
              this.areasoggettaasit.equals(other.getAreasoggettaasit()))) &&
            ((this.caratteristicheidrologiche==null && other.getCaratteristicheidrologiche()==null) || 
             (this.caratteristicheidrologiche!=null &&
              this.caratteristicheidrologiche.equals(other.getCaratteristicheidrologiche()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.idCorpoIdrico==null && other.getIdCorpoIdrico()==null) || 
             (this.idCorpoIdrico!=null &&
              this.idCorpoIdrico.equals(other.getIdCorpoIdrico()))) &&
            ((this.idsiramigra==null && other.getIdsiramigra()==null) || 
             (this.idsiramigra!=null &&
              this.idsiramigra.equals(other.getIdsiramigra()))) &&
            ((this.strutturafisica==null && other.getStrutturafisica()==null) || 
             (this.strutturafisica!=null &&
              this.strutturafisica.equals(other.getStrutturafisica()))) &&
            ((this.tipologia==null && other.getTipologia()==null) || 
             (this.tipologia!=null &&
              this.tipologia.equals(other.getTipologia())));
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
        if (getAreabacinoscolante() != null) {
            _hashCode += getAreabacinoscolante().hashCode();
        }
        if (getAreasoggettaasit() != null) {
            _hashCode += getAreasoggettaasit().hashCode();
        }
        if (getCaratteristicheidrologiche() != null) {
            _hashCode += getCaratteristicheidrologiche().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getIdCorpoIdrico() != null) {
            _hashCode += getIdCorpoIdrico().hashCode();
        }
        if (getIdsiramigra() != null) {
            _hashCode += getIdsiramigra().hashCode();
        }
        if (getStrutturafisica() != null) {
            _hashCode += getStrutturafisica().hashCode();
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostActr.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostActr"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areabacinoscolante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "areabacinoscolante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areasoggettaasit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "areasoggettaasit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caratteristicheidrologiche");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caratteristicheidrologiche"));
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
        elemField.setFieldName("idCorpoIdrico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCorpoIdrico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idsiramigra");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idsiramigra"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strutturafisica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "strutturafisica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologia"));
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
