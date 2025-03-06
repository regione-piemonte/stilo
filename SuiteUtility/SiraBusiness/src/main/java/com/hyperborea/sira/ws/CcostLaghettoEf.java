/**
 * CcostLaghettoEf.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostLaghettoEf  implements java.io.Serializable {
    private com.hyperborea.sira.ws.ApprovvAntincendio approvvAntincendio;

    private java.lang.Float capacita;

    private java.lang.String codice;

    private java.lang.Integer idCcost;

    private java.lang.String servizioTerritoriale;

    private java.lang.Float superficie;

    public CcostLaghettoEf() {
    }

    public CcostLaghettoEf(
           com.hyperborea.sira.ws.ApprovvAntincendio approvvAntincendio,
           java.lang.Float capacita,
           java.lang.String codice,
           java.lang.Integer idCcost,
           java.lang.String servizioTerritoriale,
           java.lang.Float superficie) {
           this.approvvAntincendio = approvvAntincendio;
           this.capacita = capacita;
           this.codice = codice;
           this.idCcost = idCcost;
           this.servizioTerritoriale = servizioTerritoriale;
           this.superficie = superficie;
    }


    /**
     * Gets the approvvAntincendio value for this CcostLaghettoEf.
     * 
     * @return approvvAntincendio
     */
    public com.hyperborea.sira.ws.ApprovvAntincendio getApprovvAntincendio() {
        return approvvAntincendio;
    }


    /**
     * Sets the approvvAntincendio value for this CcostLaghettoEf.
     * 
     * @param approvvAntincendio
     */
    public void setApprovvAntincendio(com.hyperborea.sira.ws.ApprovvAntincendio approvvAntincendio) {
        this.approvvAntincendio = approvvAntincendio;
    }


    /**
     * Gets the capacita value for this CcostLaghettoEf.
     * 
     * @return capacita
     */
    public java.lang.Float getCapacita() {
        return capacita;
    }


    /**
     * Sets the capacita value for this CcostLaghettoEf.
     * 
     * @param capacita
     */
    public void setCapacita(java.lang.Float capacita) {
        this.capacita = capacita;
    }


    /**
     * Gets the codice value for this CcostLaghettoEf.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this CcostLaghettoEf.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the idCcost value for this CcostLaghettoEf.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostLaghettoEf.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the servizioTerritoriale value for this CcostLaghettoEf.
     * 
     * @return servizioTerritoriale
     */
    public java.lang.String getServizioTerritoriale() {
        return servizioTerritoriale;
    }


    /**
     * Sets the servizioTerritoriale value for this CcostLaghettoEf.
     * 
     * @param servizioTerritoriale
     */
    public void setServizioTerritoriale(java.lang.String servizioTerritoriale) {
        this.servizioTerritoriale = servizioTerritoriale;
    }


    /**
     * Gets the superficie value for this CcostLaghettoEf.
     * 
     * @return superficie
     */
    public java.lang.Float getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this CcostLaghettoEf.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Float superficie) {
        this.superficie = superficie;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostLaghettoEf)) return false;
        CcostLaghettoEf other = (CcostLaghettoEf) obj;
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
            ((this.capacita==null && other.getCapacita()==null) || 
             (this.capacita!=null &&
              this.capacita.equals(other.getCapacita()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.servizioTerritoriale==null && other.getServizioTerritoriale()==null) || 
             (this.servizioTerritoriale!=null &&
              this.servizioTerritoriale.equals(other.getServizioTerritoriale()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie())));
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
        if (getCapacita() != null) {
            _hashCode += getCapacita().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getServizioTerritoriale() != null) {
            _hashCode += getServizioTerritoriale().hashCode();
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostLaghettoEf.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostLaghettoEf"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("approvvAntincendio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "approvvAntincendio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "approvvAntincendio"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capacita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
        elemField.setFieldName("servizioTerritoriale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servizioTerritoriale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
