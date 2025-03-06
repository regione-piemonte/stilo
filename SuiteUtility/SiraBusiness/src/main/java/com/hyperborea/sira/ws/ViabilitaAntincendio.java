/**
 * ViabilitaAntincendio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ViabilitaAntincendio  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer atterraggio;

    private java.lang.String cfva;

    private java.lang.String cop;

    private java.lang.Integer idCcost;

    private java.lang.Integer passaggioMezzi;

    private java.lang.Integer piazzuola;

    private java.lang.Integer sistemi;

    private java.lang.String tipologieMezzi;

    public ViabilitaAntincendio() {
    }

    public ViabilitaAntincendio(
           java.lang.Integer atterraggio,
           java.lang.String cfva,
           java.lang.String cop,
           java.lang.Integer idCcost,
           java.lang.Integer passaggioMezzi,
           java.lang.Integer piazzuola,
           java.lang.Integer sistemi,
           java.lang.String tipologieMezzi) {
        this.atterraggio = atterraggio;
        this.cfva = cfva;
        this.cop = cop;
        this.idCcost = idCcost;
        this.passaggioMezzi = passaggioMezzi;
        this.piazzuola = piazzuola;
        this.sistemi = sistemi;
        this.tipologieMezzi = tipologieMezzi;
    }


    /**
     * Gets the atterraggio value for this ViabilitaAntincendio.
     * 
     * @return atterraggio
     */
    public java.lang.Integer getAtterraggio() {
        return atterraggio;
    }


    /**
     * Sets the atterraggio value for this ViabilitaAntincendio.
     * 
     * @param atterraggio
     */
    public void setAtterraggio(java.lang.Integer atterraggio) {
        this.atterraggio = atterraggio;
    }


    /**
     * Gets the cfva value for this ViabilitaAntincendio.
     * 
     * @return cfva
     */
    public java.lang.String getCfva() {
        return cfva;
    }


    /**
     * Sets the cfva value for this ViabilitaAntincendio.
     * 
     * @param cfva
     */
    public void setCfva(java.lang.String cfva) {
        this.cfva = cfva;
    }


    /**
     * Gets the cop value for this ViabilitaAntincendio.
     * 
     * @return cop
     */
    public java.lang.String getCop() {
        return cop;
    }


    /**
     * Sets the cop value for this ViabilitaAntincendio.
     * 
     * @param cop
     */
    public void setCop(java.lang.String cop) {
        this.cop = cop;
    }


    /**
     * Gets the idCcost value for this ViabilitaAntincendio.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this ViabilitaAntincendio.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the passaggioMezzi value for this ViabilitaAntincendio.
     * 
     * @return passaggioMezzi
     */
    public java.lang.Integer getPassaggioMezzi() {
        return passaggioMezzi;
    }


    /**
     * Sets the passaggioMezzi value for this ViabilitaAntincendio.
     * 
     * @param passaggioMezzi
     */
    public void setPassaggioMezzi(java.lang.Integer passaggioMezzi) {
        this.passaggioMezzi = passaggioMezzi;
    }


    /**
     * Gets the piazzuola value for this ViabilitaAntincendio.
     * 
     * @return piazzuola
     */
    public java.lang.Integer getPiazzuola() {
        return piazzuola;
    }


    /**
     * Sets the piazzuola value for this ViabilitaAntincendio.
     * 
     * @param piazzuola
     */
    public void setPiazzuola(java.lang.Integer piazzuola) {
        this.piazzuola = piazzuola;
    }


    /**
     * Gets the sistemi value for this ViabilitaAntincendio.
     * 
     * @return sistemi
     */
    public java.lang.Integer getSistemi() {
        return sistemi;
    }


    /**
     * Sets the sistemi value for this ViabilitaAntincendio.
     * 
     * @param sistemi
     */
    public void setSistemi(java.lang.Integer sistemi) {
        this.sistemi = sistemi;
    }


    /**
     * Gets the tipologieMezzi value for this ViabilitaAntincendio.
     * 
     * @return tipologieMezzi
     */
    public java.lang.String getTipologieMezzi() {
        return tipologieMezzi;
    }


    /**
     * Sets the tipologieMezzi value for this ViabilitaAntincendio.
     * 
     * @param tipologieMezzi
     */
    public void setTipologieMezzi(java.lang.String tipologieMezzi) {
        this.tipologieMezzi = tipologieMezzi;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ViabilitaAntincendio)) return false;
        ViabilitaAntincendio other = (ViabilitaAntincendio) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.atterraggio==null && other.getAtterraggio()==null) || 
             (this.atterraggio!=null &&
              this.atterraggio.equals(other.getAtterraggio()))) &&
            ((this.cfva==null && other.getCfva()==null) || 
             (this.cfva!=null &&
              this.cfva.equals(other.getCfva()))) &&
            ((this.cop==null && other.getCop()==null) || 
             (this.cop!=null &&
              this.cop.equals(other.getCop()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.passaggioMezzi==null && other.getPassaggioMezzi()==null) || 
             (this.passaggioMezzi!=null &&
              this.passaggioMezzi.equals(other.getPassaggioMezzi()))) &&
            ((this.piazzuola==null && other.getPiazzuola()==null) || 
             (this.piazzuola!=null &&
              this.piazzuola.equals(other.getPiazzuola()))) &&
            ((this.sistemi==null && other.getSistemi()==null) || 
             (this.sistemi!=null &&
              this.sistemi.equals(other.getSistemi()))) &&
            ((this.tipologieMezzi==null && other.getTipologieMezzi()==null) || 
             (this.tipologieMezzi!=null &&
              this.tipologieMezzi.equals(other.getTipologieMezzi())));
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
        if (getAtterraggio() != null) {
            _hashCode += getAtterraggio().hashCode();
        }
        if (getCfva() != null) {
            _hashCode += getCfva().hashCode();
        }
        if (getCop() != null) {
            _hashCode += getCop().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getPassaggioMezzi() != null) {
            _hashCode += getPassaggioMezzi().hashCode();
        }
        if (getPiazzuola() != null) {
            _hashCode += getPiazzuola().hashCode();
        }
        if (getSistemi() != null) {
            _hashCode += getSistemi().hashCode();
        }
        if (getTipologieMezzi() != null) {
            _hashCode += getTipologieMezzi().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ViabilitaAntincendio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "viabilitaAntincendio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atterraggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "atterraggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cfva");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cfva"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passaggioMezzi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "passaggioMezzi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("piazzuola");
        elemField.setXmlName(new javax.xml.namespace.QName("", "piazzuola"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sistemi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sistemi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologieMezzi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologieMezzi"));
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
