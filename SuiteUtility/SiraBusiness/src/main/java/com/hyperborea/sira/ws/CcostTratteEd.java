/**
 * CcostTratteEd.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostTratteEd  implements java.io.Serializable {
    private java.lang.Float correnteMax;

    private java.lang.Float correnteRottura;

    private java.lang.Float fasciaRispetto;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.MaterialiConduttori materialiConduttori;

    private int progrTronco;

    private com.hyperborea.sira.ws.TipiTratteEd tipiTratteEd;

    public CcostTratteEd() {
    }

    public CcostTratteEd(
           java.lang.Float correnteMax,
           java.lang.Float correnteRottura,
           java.lang.Float fasciaRispetto,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.MaterialiConduttori materialiConduttori,
           int progrTronco,
           com.hyperborea.sira.ws.TipiTratteEd tipiTratteEd) {
           this.correnteMax = correnteMax;
           this.correnteRottura = correnteRottura;
           this.fasciaRispetto = fasciaRispetto;
           this.idCcost = idCcost;
           this.materialiConduttori = materialiConduttori;
           this.progrTronco = progrTronco;
           this.tipiTratteEd = tipiTratteEd;
    }


    /**
     * Gets the correnteMax value for this CcostTratteEd.
     * 
     * @return correnteMax
     */
    public java.lang.Float getCorrenteMax() {
        return correnteMax;
    }


    /**
     * Sets the correnteMax value for this CcostTratteEd.
     * 
     * @param correnteMax
     */
    public void setCorrenteMax(java.lang.Float correnteMax) {
        this.correnteMax = correnteMax;
    }


    /**
     * Gets the correnteRottura value for this CcostTratteEd.
     * 
     * @return correnteRottura
     */
    public java.lang.Float getCorrenteRottura() {
        return correnteRottura;
    }


    /**
     * Sets the correnteRottura value for this CcostTratteEd.
     * 
     * @param correnteRottura
     */
    public void setCorrenteRottura(java.lang.Float correnteRottura) {
        this.correnteRottura = correnteRottura;
    }


    /**
     * Gets the fasciaRispetto value for this CcostTratteEd.
     * 
     * @return fasciaRispetto
     */
    public java.lang.Float getFasciaRispetto() {
        return fasciaRispetto;
    }


    /**
     * Sets the fasciaRispetto value for this CcostTratteEd.
     * 
     * @param fasciaRispetto
     */
    public void setFasciaRispetto(java.lang.Float fasciaRispetto) {
        this.fasciaRispetto = fasciaRispetto;
    }


    /**
     * Gets the idCcost value for this CcostTratteEd.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostTratteEd.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the materialiConduttori value for this CcostTratteEd.
     * 
     * @return materialiConduttori
     */
    public com.hyperborea.sira.ws.MaterialiConduttori getMaterialiConduttori() {
        return materialiConduttori;
    }


    /**
     * Sets the materialiConduttori value for this CcostTratteEd.
     * 
     * @param materialiConduttori
     */
    public void setMaterialiConduttori(com.hyperborea.sira.ws.MaterialiConduttori materialiConduttori) {
        this.materialiConduttori = materialiConduttori;
    }


    /**
     * Gets the progrTronco value for this CcostTratteEd.
     * 
     * @return progrTronco
     */
    public int getProgrTronco() {
        return progrTronco;
    }


    /**
     * Sets the progrTronco value for this CcostTratteEd.
     * 
     * @param progrTronco
     */
    public void setProgrTronco(int progrTronco) {
        this.progrTronco = progrTronco;
    }


    /**
     * Gets the tipiTratteEd value for this CcostTratteEd.
     * 
     * @return tipiTratteEd
     */
    public com.hyperborea.sira.ws.TipiTratteEd getTipiTratteEd() {
        return tipiTratteEd;
    }


    /**
     * Sets the tipiTratteEd value for this CcostTratteEd.
     * 
     * @param tipiTratteEd
     */
    public void setTipiTratteEd(com.hyperborea.sira.ws.TipiTratteEd tipiTratteEd) {
        this.tipiTratteEd = tipiTratteEd;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostTratteEd)) return false;
        CcostTratteEd other = (CcostTratteEd) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.correnteMax==null && other.getCorrenteMax()==null) || 
             (this.correnteMax!=null &&
              this.correnteMax.equals(other.getCorrenteMax()))) &&
            ((this.correnteRottura==null && other.getCorrenteRottura()==null) || 
             (this.correnteRottura!=null &&
              this.correnteRottura.equals(other.getCorrenteRottura()))) &&
            ((this.fasciaRispetto==null && other.getFasciaRispetto()==null) || 
             (this.fasciaRispetto!=null &&
              this.fasciaRispetto.equals(other.getFasciaRispetto()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.materialiConduttori==null && other.getMaterialiConduttori()==null) || 
             (this.materialiConduttori!=null &&
              this.materialiConduttori.equals(other.getMaterialiConduttori()))) &&
            this.progrTronco == other.getProgrTronco() &&
            ((this.tipiTratteEd==null && other.getTipiTratteEd()==null) || 
             (this.tipiTratteEd!=null &&
              this.tipiTratteEd.equals(other.getTipiTratteEd())));
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
        if (getCorrenteMax() != null) {
            _hashCode += getCorrenteMax().hashCode();
        }
        if (getCorrenteRottura() != null) {
            _hashCode += getCorrenteRottura().hashCode();
        }
        if (getFasciaRispetto() != null) {
            _hashCode += getFasciaRispetto().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getMaterialiConduttori() != null) {
            _hashCode += getMaterialiConduttori().hashCode();
        }
        _hashCode += getProgrTronco();
        if (getTipiTratteEd() != null) {
            _hashCode += getTipiTratteEd().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostTratteEd.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostTratteEd"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("correnteMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "correnteMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("correnteRottura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "correnteRottura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fasciaRispetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fasciaRispetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
        elemField.setFieldName("materialiConduttori");
        elemField.setXmlName(new javax.xml.namespace.QName("", "materialiConduttori"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "materialiConduttori"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("progrTronco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "progrTronco"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipiTratteEd");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipiTratteEd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiTratteEd"));
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
