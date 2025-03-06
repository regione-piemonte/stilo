/**
 * StoccaggioFanghi.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class StoccaggioFanghi  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.util.Calendar dataAut;

    private java.lang.String descrizione;

    private java.lang.String enteAut;

    private java.lang.Integer idStoccaggioFanghi;

    private java.lang.String numeroAut;

    private java.lang.String titolareAut;

    private java.lang.Integer wsCcostRef;

    public StoccaggioFanghi() {
    }

    public StoccaggioFanghi(
           java.util.Calendar dataAut,
           java.lang.String descrizione,
           java.lang.String enteAut,
           java.lang.Integer idStoccaggioFanghi,
           java.lang.String numeroAut,
           java.lang.String titolareAut,
           java.lang.Integer wsCcostRef) {
        this.dataAut = dataAut;
        this.descrizione = descrizione;
        this.enteAut = enteAut;
        this.idStoccaggioFanghi = idStoccaggioFanghi;
        this.numeroAut = numeroAut;
        this.titolareAut = titolareAut;
        this.wsCcostRef = wsCcostRef;
    }


    /**
     * Gets the dataAut value for this StoccaggioFanghi.
     * 
     * @return dataAut
     */
    public java.util.Calendar getDataAut() {
        return dataAut;
    }


    /**
     * Sets the dataAut value for this StoccaggioFanghi.
     * 
     * @param dataAut
     */
    public void setDataAut(java.util.Calendar dataAut) {
        this.dataAut = dataAut;
    }


    /**
     * Gets the descrizione value for this StoccaggioFanghi.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this StoccaggioFanghi.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the enteAut value for this StoccaggioFanghi.
     * 
     * @return enteAut
     */
    public java.lang.String getEnteAut() {
        return enteAut;
    }


    /**
     * Sets the enteAut value for this StoccaggioFanghi.
     * 
     * @param enteAut
     */
    public void setEnteAut(java.lang.String enteAut) {
        this.enteAut = enteAut;
    }


    /**
     * Gets the idStoccaggioFanghi value for this StoccaggioFanghi.
     * 
     * @return idStoccaggioFanghi
     */
    public java.lang.Integer getIdStoccaggioFanghi() {
        return idStoccaggioFanghi;
    }


    /**
     * Sets the idStoccaggioFanghi value for this StoccaggioFanghi.
     * 
     * @param idStoccaggioFanghi
     */
    public void setIdStoccaggioFanghi(java.lang.Integer idStoccaggioFanghi) {
        this.idStoccaggioFanghi = idStoccaggioFanghi;
    }


    /**
     * Gets the numeroAut value for this StoccaggioFanghi.
     * 
     * @return numeroAut
     */
    public java.lang.String getNumeroAut() {
        return numeroAut;
    }


    /**
     * Sets the numeroAut value for this StoccaggioFanghi.
     * 
     * @param numeroAut
     */
    public void setNumeroAut(java.lang.String numeroAut) {
        this.numeroAut = numeroAut;
    }


    /**
     * Gets the titolareAut value for this StoccaggioFanghi.
     * 
     * @return titolareAut
     */
    public java.lang.String getTitolareAut() {
        return titolareAut;
    }


    /**
     * Sets the titolareAut value for this StoccaggioFanghi.
     * 
     * @param titolareAut
     */
    public void setTitolareAut(java.lang.String titolareAut) {
        this.titolareAut = titolareAut;
    }


    /**
     * Gets the wsCcostRef value for this StoccaggioFanghi.
     * 
     * @return wsCcostRef
     */
    public java.lang.Integer getWsCcostRef() {
        return wsCcostRef;
    }


    /**
     * Sets the wsCcostRef value for this StoccaggioFanghi.
     * 
     * @param wsCcostRef
     */
    public void setWsCcostRef(java.lang.Integer wsCcostRef) {
        this.wsCcostRef = wsCcostRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StoccaggioFanghi)) return false;
        StoccaggioFanghi other = (StoccaggioFanghi) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.dataAut==null && other.getDataAut()==null) || 
             (this.dataAut!=null &&
              this.dataAut.equals(other.getDataAut()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.enteAut==null && other.getEnteAut()==null) || 
             (this.enteAut!=null &&
              this.enteAut.equals(other.getEnteAut()))) &&
            ((this.idStoccaggioFanghi==null && other.getIdStoccaggioFanghi()==null) || 
             (this.idStoccaggioFanghi!=null &&
              this.idStoccaggioFanghi.equals(other.getIdStoccaggioFanghi()))) &&
            ((this.numeroAut==null && other.getNumeroAut()==null) || 
             (this.numeroAut!=null &&
              this.numeroAut.equals(other.getNumeroAut()))) &&
            ((this.titolareAut==null && other.getTitolareAut()==null) || 
             (this.titolareAut!=null &&
              this.titolareAut.equals(other.getTitolareAut()))) &&
            ((this.wsCcostRef==null && other.getWsCcostRef()==null) || 
             (this.wsCcostRef!=null &&
              this.wsCcostRef.equals(other.getWsCcostRef())));
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
        if (getDataAut() != null) {
            _hashCode += getDataAut().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getEnteAut() != null) {
            _hashCode += getEnteAut().hashCode();
        }
        if (getIdStoccaggioFanghi() != null) {
            _hashCode += getIdStoccaggioFanghi().hashCode();
        }
        if (getNumeroAut() != null) {
            _hashCode += getNumeroAut().hashCode();
        }
        if (getTitolareAut() != null) {
            _hashCode += getTitolareAut().hashCode();
        }
        if (getWsCcostRef() != null) {
            _hashCode += getWsCcostRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StoccaggioFanghi.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "stoccaggioFanghi"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataAut");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataAut"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enteAut");
        elemField.setXmlName(new javax.xml.namespace.QName("", "enteAut"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idStoccaggioFanghi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idStoccaggioFanghi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroAut");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroAut"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titolareAut");
        elemField.setXmlName(new javax.xml.namespace.QName("", "titolareAut"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsCcostRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsCcostRef"));
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
