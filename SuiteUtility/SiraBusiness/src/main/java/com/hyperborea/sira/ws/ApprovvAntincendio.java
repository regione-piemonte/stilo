/**
 * ApprovvAntincendio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ApprovvAntincendio  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Float capacita;

    private java.lang.String cop;

    private java.lang.Float dimensioni;

    private java.lang.Integer idApprovvAntincendio;

    private java.lang.String tipoUtilizzo;

    public ApprovvAntincendio() {
    }

    public ApprovvAntincendio(
           java.lang.Float capacita,
           java.lang.String cop,
           java.lang.Float dimensioni,
           java.lang.Integer idApprovvAntincendio,
           java.lang.String tipoUtilizzo) {
        this.capacita = capacita;
        this.cop = cop;
        this.dimensioni = dimensioni;
        this.idApprovvAntincendio = idApprovvAntincendio;
        this.tipoUtilizzo = tipoUtilizzo;
    }


    /**
     * Gets the capacita value for this ApprovvAntincendio.
     * 
     * @return capacita
     */
    public java.lang.Float getCapacita() {
        return capacita;
    }


    /**
     * Sets the capacita value for this ApprovvAntincendio.
     * 
     * @param capacita
     */
    public void setCapacita(java.lang.Float capacita) {
        this.capacita = capacita;
    }


    /**
     * Gets the cop value for this ApprovvAntincendio.
     * 
     * @return cop
     */
    public java.lang.String getCop() {
        return cop;
    }


    /**
     * Sets the cop value for this ApprovvAntincendio.
     * 
     * @param cop
     */
    public void setCop(java.lang.String cop) {
        this.cop = cop;
    }


    /**
     * Gets the dimensioni value for this ApprovvAntincendio.
     * 
     * @return dimensioni
     */
    public java.lang.Float getDimensioni() {
        return dimensioni;
    }


    /**
     * Sets the dimensioni value for this ApprovvAntincendio.
     * 
     * @param dimensioni
     */
    public void setDimensioni(java.lang.Float dimensioni) {
        this.dimensioni = dimensioni;
    }


    /**
     * Gets the idApprovvAntincendio value for this ApprovvAntincendio.
     * 
     * @return idApprovvAntincendio
     */
    public java.lang.Integer getIdApprovvAntincendio() {
        return idApprovvAntincendio;
    }


    /**
     * Sets the idApprovvAntincendio value for this ApprovvAntincendio.
     * 
     * @param idApprovvAntincendio
     */
    public void setIdApprovvAntincendio(java.lang.Integer idApprovvAntincendio) {
        this.idApprovvAntincendio = idApprovvAntincendio;
    }


    /**
     * Gets the tipoUtilizzo value for this ApprovvAntincendio.
     * 
     * @return tipoUtilizzo
     */
    public java.lang.String getTipoUtilizzo() {
        return tipoUtilizzo;
    }


    /**
     * Sets the tipoUtilizzo value for this ApprovvAntincendio.
     * 
     * @param tipoUtilizzo
     */
    public void setTipoUtilizzo(java.lang.String tipoUtilizzo) {
        this.tipoUtilizzo = tipoUtilizzo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ApprovvAntincendio)) return false;
        ApprovvAntincendio other = (ApprovvAntincendio) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.capacita==null && other.getCapacita()==null) || 
             (this.capacita!=null &&
              this.capacita.equals(other.getCapacita()))) &&
            ((this.cop==null && other.getCop()==null) || 
             (this.cop!=null &&
              this.cop.equals(other.getCop()))) &&
            ((this.dimensioni==null && other.getDimensioni()==null) || 
             (this.dimensioni!=null &&
              this.dimensioni.equals(other.getDimensioni()))) &&
            ((this.idApprovvAntincendio==null && other.getIdApprovvAntincendio()==null) || 
             (this.idApprovvAntincendio!=null &&
              this.idApprovvAntincendio.equals(other.getIdApprovvAntincendio()))) &&
            ((this.tipoUtilizzo==null && other.getTipoUtilizzo()==null) || 
             (this.tipoUtilizzo!=null &&
              this.tipoUtilizzo.equals(other.getTipoUtilizzo())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getCapacita() != null) {
            _hashCode += getCapacita().hashCode();
        }
        if (getCop() != null) {
            _hashCode += getCop().hashCode();
        }
        if (getDimensioni() != null) {
            _hashCode += getDimensioni().hashCode();
        }
        if (getIdApprovvAntincendio() != null) {
            _hashCode += getIdApprovvAntincendio().hashCode();
        }
        if (getTipoUtilizzo() != null) {
            _hashCode += getTipoUtilizzo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ApprovvAntincendio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "approvvAntincendio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capacita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cop");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cop"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dimensioni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dimensioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idApprovvAntincendio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idApprovvAntincendio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoUtilizzo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoUtilizzo"));
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
