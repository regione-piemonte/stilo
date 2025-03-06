/**
 * SostanzePericolose.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class SostanzePericolose  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String casNumber;

    private java.lang.String classePericolosita;

    private java.lang.String denominazione;

    private java.lang.String frasiR;

    private java.lang.String frasiS;

    private java.lang.Integer idSostanza;

    private java.lang.String percPeso;

    private org.apache.axis.types.UnsignedShort segnoPercPeso;

    public SostanzePericolose() {
    }

    public SostanzePericolose(
           java.lang.String casNumber,
           java.lang.String classePericolosita,
           java.lang.String denominazione,
           java.lang.String frasiR,
           java.lang.String frasiS,
           java.lang.Integer idSostanza,
           java.lang.String percPeso,
           org.apache.axis.types.UnsignedShort segnoPercPeso) {
        this.casNumber = casNumber;
        this.classePericolosita = classePericolosita;
        this.denominazione = denominazione;
        this.frasiR = frasiR;
        this.frasiS = frasiS;
        this.idSostanza = idSostanza;
        this.percPeso = percPeso;
        this.segnoPercPeso = segnoPercPeso;
    }


    /**
     * Gets the casNumber value for this SostanzePericolose.
     * 
     * @return casNumber
     */
    public java.lang.String getCasNumber() {
        return casNumber;
    }


    /**
     * Sets the casNumber value for this SostanzePericolose.
     * 
     * @param casNumber
     */
    public void setCasNumber(java.lang.String casNumber) {
        this.casNumber = casNumber;
    }


    /**
     * Gets the classePericolosita value for this SostanzePericolose.
     * 
     * @return classePericolosita
     */
    public java.lang.String getClassePericolosita() {
        return classePericolosita;
    }


    /**
     * Sets the classePericolosita value for this SostanzePericolose.
     * 
     * @param classePericolosita
     */
    public void setClassePericolosita(java.lang.String classePericolosita) {
        this.classePericolosita = classePericolosita;
    }


    /**
     * Gets the denominazione value for this SostanzePericolose.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this SostanzePericolose.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the frasiR value for this SostanzePericolose.
     * 
     * @return frasiR
     */
    public java.lang.String getFrasiR() {
        return frasiR;
    }


    /**
     * Sets the frasiR value for this SostanzePericolose.
     * 
     * @param frasiR
     */
    public void setFrasiR(java.lang.String frasiR) {
        this.frasiR = frasiR;
    }


    /**
     * Gets the frasiS value for this SostanzePericolose.
     * 
     * @return frasiS
     */
    public java.lang.String getFrasiS() {
        return frasiS;
    }


    /**
     * Sets the frasiS value for this SostanzePericolose.
     * 
     * @param frasiS
     */
    public void setFrasiS(java.lang.String frasiS) {
        this.frasiS = frasiS;
    }


    /**
     * Gets the idSostanza value for this SostanzePericolose.
     * 
     * @return idSostanza
     */
    public java.lang.Integer getIdSostanza() {
        return idSostanza;
    }


    /**
     * Sets the idSostanza value for this SostanzePericolose.
     * 
     * @param idSostanza
     */
    public void setIdSostanza(java.lang.Integer idSostanza) {
        this.idSostanza = idSostanza;
    }


    /**
     * Gets the percPeso value for this SostanzePericolose.
     * 
     * @return percPeso
     */
    public java.lang.String getPercPeso() {
        return percPeso;
    }


    /**
     * Sets the percPeso value for this SostanzePericolose.
     * 
     * @param percPeso
     */
    public void setPercPeso(java.lang.String percPeso) {
        this.percPeso = percPeso;
    }


    /**
     * Gets the segnoPercPeso value for this SostanzePericolose.
     * 
     * @return segnoPercPeso
     */
    public org.apache.axis.types.UnsignedShort getSegnoPercPeso() {
        return segnoPercPeso;
    }


    /**
     * Sets the segnoPercPeso value for this SostanzePericolose.
     * 
     * @param segnoPercPeso
     */
    public void setSegnoPercPeso(org.apache.axis.types.UnsignedShort segnoPercPeso) {
        this.segnoPercPeso = segnoPercPeso;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SostanzePericolose)) return false;
        SostanzePericolose other = (SostanzePericolose) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.casNumber==null && other.getCasNumber()==null) || 
             (this.casNumber!=null &&
              this.casNumber.equals(other.getCasNumber()))) &&
            ((this.classePericolosita==null && other.getClassePericolosita()==null) || 
             (this.classePericolosita!=null &&
              this.classePericolosita.equals(other.getClassePericolosita()))) &&
            ((this.denominazione==null && other.getDenominazione()==null) || 
             (this.denominazione!=null &&
              this.denominazione.equals(other.getDenominazione()))) &&
            ((this.frasiR==null && other.getFrasiR()==null) || 
             (this.frasiR!=null &&
              this.frasiR.equals(other.getFrasiR()))) &&
            ((this.frasiS==null && other.getFrasiS()==null) || 
             (this.frasiS!=null &&
              this.frasiS.equals(other.getFrasiS()))) &&
            ((this.idSostanza==null && other.getIdSostanza()==null) || 
             (this.idSostanza!=null &&
              this.idSostanza.equals(other.getIdSostanza()))) &&
            ((this.percPeso==null && other.getPercPeso()==null) || 
             (this.percPeso!=null &&
              this.percPeso.equals(other.getPercPeso()))) &&
            ((this.segnoPercPeso==null && other.getSegnoPercPeso()==null) || 
             (this.segnoPercPeso!=null &&
              this.segnoPercPeso.equals(other.getSegnoPercPeso())));
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
        if (getCasNumber() != null) {
            _hashCode += getCasNumber().hashCode();
        }
        if (getClassePericolosita() != null) {
            _hashCode += getClassePericolosita().hashCode();
        }
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
        }
        if (getFrasiR() != null) {
            _hashCode += getFrasiR().hashCode();
        }
        if (getFrasiS() != null) {
            _hashCode += getFrasiS().hashCode();
        }
        if (getIdSostanza() != null) {
            _hashCode += getIdSostanza().hashCode();
        }
        if (getPercPeso() != null) {
            _hashCode += getPercPeso().hashCode();
        }
        if (getSegnoPercPeso() != null) {
            _hashCode += getSegnoPercPeso().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SostanzePericolose.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostanzePericolose"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("casNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "casNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classePericolosita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "classePericolosita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("frasiR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "frasiR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("frasiS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "frasiS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSostanza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idSostanza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percPeso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "percPeso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("segnoPercPeso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "segnoPercPeso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
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
