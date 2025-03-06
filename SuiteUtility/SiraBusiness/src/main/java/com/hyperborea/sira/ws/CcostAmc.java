/**
 * CcostAmc.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostAmc  implements java.io.Serializable {
    private java.lang.String descrizionefondale;

    private java.lang.Integer idCcost;

    private java.lang.String idCorpoIdrico;

    private java.lang.String idmigrasira;

    private java.lang.Float lunghezza;

    private java.lang.String tipologia;

    private java.lang.Integer zonacontrollo;

    private java.lang.Integer zonafortiimmissioni;

    public CcostAmc() {
    }

    public CcostAmc(
           java.lang.String descrizionefondale,
           java.lang.Integer idCcost,
           java.lang.String idCorpoIdrico,
           java.lang.String idmigrasira,
           java.lang.Float lunghezza,
           java.lang.String tipologia,
           java.lang.Integer zonacontrollo,
           java.lang.Integer zonafortiimmissioni) {
           this.descrizionefondale = descrizionefondale;
           this.idCcost = idCcost;
           this.idCorpoIdrico = idCorpoIdrico;
           this.idmigrasira = idmigrasira;
           this.lunghezza = lunghezza;
           this.tipologia = tipologia;
           this.zonacontrollo = zonacontrollo;
           this.zonafortiimmissioni = zonafortiimmissioni;
    }


    /**
     * Gets the descrizionefondale value for this CcostAmc.
     * 
     * @return descrizionefondale
     */
    public java.lang.String getDescrizionefondale() {
        return descrizionefondale;
    }


    /**
     * Sets the descrizionefondale value for this CcostAmc.
     * 
     * @param descrizionefondale
     */
    public void setDescrizionefondale(java.lang.String descrizionefondale) {
        this.descrizionefondale = descrizionefondale;
    }


    /**
     * Gets the idCcost value for this CcostAmc.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostAmc.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the idCorpoIdrico value for this CcostAmc.
     * 
     * @return idCorpoIdrico
     */
    public java.lang.String getIdCorpoIdrico() {
        return idCorpoIdrico;
    }


    /**
     * Sets the idCorpoIdrico value for this CcostAmc.
     * 
     * @param idCorpoIdrico
     */
    public void setIdCorpoIdrico(java.lang.String idCorpoIdrico) {
        this.idCorpoIdrico = idCorpoIdrico;
    }


    /**
     * Gets the idmigrasira value for this CcostAmc.
     * 
     * @return idmigrasira
     */
    public java.lang.String getIdmigrasira() {
        return idmigrasira;
    }


    /**
     * Sets the idmigrasira value for this CcostAmc.
     * 
     * @param idmigrasira
     */
    public void setIdmigrasira(java.lang.String idmigrasira) {
        this.idmigrasira = idmigrasira;
    }


    /**
     * Gets the lunghezza value for this CcostAmc.
     * 
     * @return lunghezza
     */
    public java.lang.Float getLunghezza() {
        return lunghezza;
    }


    /**
     * Sets the lunghezza value for this CcostAmc.
     * 
     * @param lunghezza
     */
    public void setLunghezza(java.lang.Float lunghezza) {
        this.lunghezza = lunghezza;
    }


    /**
     * Gets the tipologia value for this CcostAmc.
     * 
     * @return tipologia
     */
    public java.lang.String getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this CcostAmc.
     * 
     * @param tipologia
     */
    public void setTipologia(java.lang.String tipologia) {
        this.tipologia = tipologia;
    }


    /**
     * Gets the zonacontrollo value for this CcostAmc.
     * 
     * @return zonacontrollo
     */
    public java.lang.Integer getZonacontrollo() {
        return zonacontrollo;
    }


    /**
     * Sets the zonacontrollo value for this CcostAmc.
     * 
     * @param zonacontrollo
     */
    public void setZonacontrollo(java.lang.Integer zonacontrollo) {
        this.zonacontrollo = zonacontrollo;
    }


    /**
     * Gets the zonafortiimmissioni value for this CcostAmc.
     * 
     * @return zonafortiimmissioni
     */
    public java.lang.Integer getZonafortiimmissioni() {
        return zonafortiimmissioni;
    }


    /**
     * Sets the zonafortiimmissioni value for this CcostAmc.
     * 
     * @param zonafortiimmissioni
     */
    public void setZonafortiimmissioni(java.lang.Integer zonafortiimmissioni) {
        this.zonafortiimmissioni = zonafortiimmissioni;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostAmc)) return false;
        CcostAmc other = (CcostAmc) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.descrizionefondale==null && other.getDescrizionefondale()==null) || 
             (this.descrizionefondale!=null &&
              this.descrizionefondale.equals(other.getDescrizionefondale()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.idCorpoIdrico==null && other.getIdCorpoIdrico()==null) || 
             (this.idCorpoIdrico!=null &&
              this.idCorpoIdrico.equals(other.getIdCorpoIdrico()))) &&
            ((this.idmigrasira==null && other.getIdmigrasira()==null) || 
             (this.idmigrasira!=null &&
              this.idmigrasira.equals(other.getIdmigrasira()))) &&
            ((this.lunghezza==null && other.getLunghezza()==null) || 
             (this.lunghezza!=null &&
              this.lunghezza.equals(other.getLunghezza()))) &&
            ((this.tipologia==null && other.getTipologia()==null) || 
             (this.tipologia!=null &&
              this.tipologia.equals(other.getTipologia()))) &&
            ((this.zonacontrollo==null && other.getZonacontrollo()==null) || 
             (this.zonacontrollo!=null &&
              this.zonacontrollo.equals(other.getZonacontrollo()))) &&
            ((this.zonafortiimmissioni==null && other.getZonafortiimmissioni()==null) || 
             (this.zonafortiimmissioni!=null &&
              this.zonafortiimmissioni.equals(other.getZonafortiimmissioni())));
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
        if (getDescrizionefondale() != null) {
            _hashCode += getDescrizionefondale().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getIdCorpoIdrico() != null) {
            _hashCode += getIdCorpoIdrico().hashCode();
        }
        if (getIdmigrasira() != null) {
            _hashCode += getIdmigrasira().hashCode();
        }
        if (getLunghezza() != null) {
            _hashCode += getLunghezza().hashCode();
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        if (getZonacontrollo() != null) {
            _hashCode += getZonacontrollo().hashCode();
        }
        if (getZonafortiimmissioni() != null) {
            _hashCode += getZonafortiimmissioni().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostAmc.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAmc"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizionefondale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizionefondale"));
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
        elemField.setFieldName("idmigrasira");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idmigrasira"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lunghezza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lunghezza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zonacontrollo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "zonacontrollo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zonafortiimmissioni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "zonafortiimmissioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
