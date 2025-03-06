/**
 * CcostLa.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostLa  implements java.io.Serializable {
    private com.hyperborea.sira.ws.ApprovvAntincendio approvvAntincendio;

    private java.lang.Integer flagserbatoio;

    private java.lang.Integer idCcost;

    private java.lang.String idCorpoIdrico;

    private java.lang.String idsiramigra;

    private java.lang.Float profondita;

    private java.lang.Float superficie;

    private java.lang.Float superficiespecchio;

    private java.lang.String tipologia;

    public CcostLa() {
    }

    public CcostLa(
           com.hyperborea.sira.ws.ApprovvAntincendio approvvAntincendio,
           java.lang.Integer flagserbatoio,
           java.lang.Integer idCcost,
           java.lang.String idCorpoIdrico,
           java.lang.String idsiramigra,
           java.lang.Float profondita,
           java.lang.Float superficie,
           java.lang.Float superficiespecchio,
           java.lang.String tipologia) {
           this.approvvAntincendio = approvvAntincendio;
           this.flagserbatoio = flagserbatoio;
           this.idCcost = idCcost;
           this.idCorpoIdrico = idCorpoIdrico;
           this.idsiramigra = idsiramigra;
           this.profondita = profondita;
           this.superficie = superficie;
           this.superficiespecchio = superficiespecchio;
           this.tipologia = tipologia;
    }


    /**
     * Gets the approvvAntincendio value for this CcostLa.
     * 
     * @return approvvAntincendio
     */
    public com.hyperborea.sira.ws.ApprovvAntincendio getApprovvAntincendio() {
        return approvvAntincendio;
    }


    /**
     * Sets the approvvAntincendio value for this CcostLa.
     * 
     * @param approvvAntincendio
     */
    public void setApprovvAntincendio(com.hyperborea.sira.ws.ApprovvAntincendio approvvAntincendio) {
        this.approvvAntincendio = approvvAntincendio;
    }


    /**
     * Gets the flagserbatoio value for this CcostLa.
     * 
     * @return flagserbatoio
     */
    public java.lang.Integer getFlagserbatoio() {
        return flagserbatoio;
    }


    /**
     * Sets the flagserbatoio value for this CcostLa.
     * 
     * @param flagserbatoio
     */
    public void setFlagserbatoio(java.lang.Integer flagserbatoio) {
        this.flagserbatoio = flagserbatoio;
    }


    /**
     * Gets the idCcost value for this CcostLa.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostLa.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the idCorpoIdrico value for this CcostLa.
     * 
     * @return idCorpoIdrico
     */
    public java.lang.String getIdCorpoIdrico() {
        return idCorpoIdrico;
    }


    /**
     * Sets the idCorpoIdrico value for this CcostLa.
     * 
     * @param idCorpoIdrico
     */
    public void setIdCorpoIdrico(java.lang.String idCorpoIdrico) {
        this.idCorpoIdrico = idCorpoIdrico;
    }


    /**
     * Gets the idsiramigra value for this CcostLa.
     * 
     * @return idsiramigra
     */
    public java.lang.String getIdsiramigra() {
        return idsiramigra;
    }


    /**
     * Sets the idsiramigra value for this CcostLa.
     * 
     * @param idsiramigra
     */
    public void setIdsiramigra(java.lang.String idsiramigra) {
        this.idsiramigra = idsiramigra;
    }


    /**
     * Gets the profondita value for this CcostLa.
     * 
     * @return profondita
     */
    public java.lang.Float getProfondita() {
        return profondita;
    }


    /**
     * Sets the profondita value for this CcostLa.
     * 
     * @param profondita
     */
    public void setProfondita(java.lang.Float profondita) {
        this.profondita = profondita;
    }


    /**
     * Gets the superficie value for this CcostLa.
     * 
     * @return superficie
     */
    public java.lang.Float getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this CcostLa.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Float superficie) {
        this.superficie = superficie;
    }


    /**
     * Gets the superficiespecchio value for this CcostLa.
     * 
     * @return superficiespecchio
     */
    public java.lang.Float getSuperficiespecchio() {
        return superficiespecchio;
    }


    /**
     * Sets the superficiespecchio value for this CcostLa.
     * 
     * @param superficiespecchio
     */
    public void setSuperficiespecchio(java.lang.Float superficiespecchio) {
        this.superficiespecchio = superficiespecchio;
    }


    /**
     * Gets the tipologia value for this CcostLa.
     * 
     * @return tipologia
     */
    public java.lang.String getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this CcostLa.
     * 
     * @param tipologia
     */
    public void setTipologia(java.lang.String tipologia) {
        this.tipologia = tipologia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostLa)) return false;
        CcostLa other = (CcostLa) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.approvvAntincendio==null && other.getApprovvAntincendio()==null) || 
             (this.approvvAntincendio!=null &&
              this.approvvAntincendio.equals(other.getApprovvAntincendio()))) &&
            ((this.flagserbatoio==null && other.getFlagserbatoio()==null) || 
             (this.flagserbatoio!=null &&
              this.flagserbatoio.equals(other.getFlagserbatoio()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.idCorpoIdrico==null && other.getIdCorpoIdrico()==null) || 
             (this.idCorpoIdrico!=null &&
              this.idCorpoIdrico.equals(other.getIdCorpoIdrico()))) &&
            ((this.idsiramigra==null && other.getIdsiramigra()==null) || 
             (this.idsiramigra!=null &&
              this.idsiramigra.equals(other.getIdsiramigra()))) &&
            ((this.profondita==null && other.getProfondita()==null) || 
             (this.profondita!=null &&
              this.profondita.equals(other.getProfondita()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie()))) &&
            ((this.superficiespecchio==null && other.getSuperficiespecchio()==null) || 
             (this.superficiespecchio!=null &&
              this.superficiespecchio.equals(other.getSuperficiespecchio()))) &&
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
        if (getApprovvAntincendio() != null) {
            _hashCode += getApprovvAntincendio().hashCode();
        }
        if (getFlagserbatoio() != null) {
            _hashCode += getFlagserbatoio().hashCode();
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
        if (getProfondita() != null) {
            _hashCode += getProfondita().hashCode();
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        if (getSuperficiespecchio() != null) {
            _hashCode += getSuperficiespecchio().hashCode();
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostLa.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostLa"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("approvvAntincendio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "approvvAntincendio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "approvvAntincendio"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagserbatoio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagserbatoio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("profondita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profondita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficiespecchio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficiespecchio"));
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
