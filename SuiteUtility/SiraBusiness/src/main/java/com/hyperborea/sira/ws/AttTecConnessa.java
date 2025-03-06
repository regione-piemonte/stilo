/**
 * AttTecConnessa.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class AttTecConnessa  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String datiDimensionali;

    private java.lang.String descrizione;

    private java.lang.Integer idAttTecCon;

    private java.lang.String note;

    private java.lang.String riferimento;

    private java.lang.String sigla;

    public AttTecConnessa() {
    }

    public AttTecConnessa(
           java.lang.String datiDimensionali,
           java.lang.String descrizione,
           java.lang.Integer idAttTecCon,
           java.lang.String note,
           java.lang.String riferimento,
           java.lang.String sigla) {
        this.datiDimensionali = datiDimensionali;
        this.descrizione = descrizione;
        this.idAttTecCon = idAttTecCon;
        this.note = note;
        this.riferimento = riferimento;
        this.sigla = sigla;
    }


    /**
     * Gets the datiDimensionali value for this AttTecConnessa.
     * 
     * @return datiDimensionali
     */
    public java.lang.String getDatiDimensionali() {
        return datiDimensionali;
    }


    /**
     * Sets the datiDimensionali value for this AttTecConnessa.
     * 
     * @param datiDimensionali
     */
    public void setDatiDimensionali(java.lang.String datiDimensionali) {
        this.datiDimensionali = datiDimensionali;
    }


    /**
     * Gets the descrizione value for this AttTecConnessa.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this AttTecConnessa.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idAttTecCon value for this AttTecConnessa.
     * 
     * @return idAttTecCon
     */
    public java.lang.Integer getIdAttTecCon() {
        return idAttTecCon;
    }


    /**
     * Sets the idAttTecCon value for this AttTecConnessa.
     * 
     * @param idAttTecCon
     */
    public void setIdAttTecCon(java.lang.Integer idAttTecCon) {
        this.idAttTecCon = idAttTecCon;
    }


    /**
     * Gets the note value for this AttTecConnessa.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this AttTecConnessa.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the riferimento value for this AttTecConnessa.
     * 
     * @return riferimento
     */
    public java.lang.String getRiferimento() {
        return riferimento;
    }


    /**
     * Sets the riferimento value for this AttTecConnessa.
     * 
     * @param riferimento
     */
    public void setRiferimento(java.lang.String riferimento) {
        this.riferimento = riferimento;
    }


    /**
     * Gets the sigla value for this AttTecConnessa.
     * 
     * @return sigla
     */
    public java.lang.String getSigla() {
        return sigla;
    }


    /**
     * Sets the sigla value for this AttTecConnessa.
     * 
     * @param sigla
     */
    public void setSigla(java.lang.String sigla) {
        this.sigla = sigla;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AttTecConnessa)) return false;
        AttTecConnessa other = (AttTecConnessa) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.datiDimensionali==null && other.getDatiDimensionali()==null) || 
             (this.datiDimensionali!=null &&
              this.datiDimensionali.equals(other.getDatiDimensionali()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.idAttTecCon==null && other.getIdAttTecCon()==null) || 
             (this.idAttTecCon!=null &&
              this.idAttTecCon.equals(other.getIdAttTecCon()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.riferimento==null && other.getRiferimento()==null) || 
             (this.riferimento!=null &&
              this.riferimento.equals(other.getRiferimento()))) &&
            ((this.sigla==null && other.getSigla()==null) || 
             (this.sigla!=null &&
              this.sigla.equals(other.getSigla())));
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
        if (getDatiDimensionali() != null) {
            _hashCode += getDatiDimensionali().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getIdAttTecCon() != null) {
            _hashCode += getIdAttTecCon().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getRiferimento() != null) {
            _hashCode += getRiferimento().hashCode();
        }
        if (getSigla() != null) {
            _hashCode += getSigla().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AttTecConnessa.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attTecConnessa"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datiDimensionali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datiDimensionali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("idAttTecCon");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idAttTecCon"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("note");
        elemField.setXmlName(new javax.xml.namespace.QName("", "note"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riferimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riferimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sigla");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sigla"));
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
