/**
 * GruppoRifiutiGestiti.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class GruppoRifiutiGestiti  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String carattOrganolettiche;

    private java.lang.String gruppoUnitaMisura;

    private java.lang.Integer idGruppoRifiuti;

    private java.lang.String note;

    private java.lang.Float quantitaGruppo;

    private com.hyperborea.sira.ws.RifiutiGestiti[] rifiutiGestitis;

    private java.lang.String statoFisico;

    public GruppoRifiutiGestiti() {
    }

    public GruppoRifiutiGestiti(
           java.lang.String carattOrganolettiche,
           java.lang.String gruppoUnitaMisura,
           java.lang.Integer idGruppoRifiuti,
           java.lang.String note,
           java.lang.Float quantitaGruppo,
           com.hyperborea.sira.ws.RifiutiGestiti[] rifiutiGestitis,
           java.lang.String statoFisico) {
        this.carattOrganolettiche = carattOrganolettiche;
        this.gruppoUnitaMisura = gruppoUnitaMisura;
        this.idGruppoRifiuti = idGruppoRifiuti;
        this.note = note;
        this.quantitaGruppo = quantitaGruppo;
        this.rifiutiGestitis = rifiutiGestitis;
        this.statoFisico = statoFisico;
    }


    /**
     * Gets the carattOrganolettiche value for this GruppoRifiutiGestiti.
     * 
     * @return carattOrganolettiche
     */
    public java.lang.String getCarattOrganolettiche() {
        return carattOrganolettiche;
    }


    /**
     * Sets the carattOrganolettiche value for this GruppoRifiutiGestiti.
     * 
     * @param carattOrganolettiche
     */
    public void setCarattOrganolettiche(java.lang.String carattOrganolettiche) {
        this.carattOrganolettiche = carattOrganolettiche;
    }


    /**
     * Gets the gruppoUnitaMisura value for this GruppoRifiutiGestiti.
     * 
     * @return gruppoUnitaMisura
     */
    public java.lang.String getGruppoUnitaMisura() {
        return gruppoUnitaMisura;
    }


    /**
     * Sets the gruppoUnitaMisura value for this GruppoRifiutiGestiti.
     * 
     * @param gruppoUnitaMisura
     */
    public void setGruppoUnitaMisura(java.lang.String gruppoUnitaMisura) {
        this.gruppoUnitaMisura = gruppoUnitaMisura;
    }


    /**
     * Gets the idGruppoRifiuti value for this GruppoRifiutiGestiti.
     * 
     * @return idGruppoRifiuti
     */
    public java.lang.Integer getIdGruppoRifiuti() {
        return idGruppoRifiuti;
    }


    /**
     * Sets the idGruppoRifiuti value for this GruppoRifiutiGestiti.
     * 
     * @param idGruppoRifiuti
     */
    public void setIdGruppoRifiuti(java.lang.Integer idGruppoRifiuti) {
        this.idGruppoRifiuti = idGruppoRifiuti;
    }


    /**
     * Gets the note value for this GruppoRifiutiGestiti.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this GruppoRifiutiGestiti.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the quantitaGruppo value for this GruppoRifiutiGestiti.
     * 
     * @return quantitaGruppo
     */
    public java.lang.Float getQuantitaGruppo() {
        return quantitaGruppo;
    }


    /**
     * Sets the quantitaGruppo value for this GruppoRifiutiGestiti.
     * 
     * @param quantitaGruppo
     */
    public void setQuantitaGruppo(java.lang.Float quantitaGruppo) {
        this.quantitaGruppo = quantitaGruppo;
    }


    /**
     * Gets the rifiutiGestitis value for this GruppoRifiutiGestiti.
     * 
     * @return rifiutiGestitis
     */
    public com.hyperborea.sira.ws.RifiutiGestiti[] getRifiutiGestitis() {
        return rifiutiGestitis;
    }


    /**
     * Sets the rifiutiGestitis value for this GruppoRifiutiGestiti.
     * 
     * @param rifiutiGestitis
     */
    public void setRifiutiGestitis(com.hyperborea.sira.ws.RifiutiGestiti[] rifiutiGestitis) {
        this.rifiutiGestitis = rifiutiGestitis;
    }

    public com.hyperborea.sira.ws.RifiutiGestiti getRifiutiGestitis(int i) {
        return this.rifiutiGestitis[i];
    }

    public void setRifiutiGestitis(int i, com.hyperborea.sira.ws.RifiutiGestiti _value) {
        this.rifiutiGestitis[i] = _value;
    }


    /**
     * Gets the statoFisico value for this GruppoRifiutiGestiti.
     * 
     * @return statoFisico
     */
    public java.lang.String getStatoFisico() {
        return statoFisico;
    }


    /**
     * Sets the statoFisico value for this GruppoRifiutiGestiti.
     * 
     * @param statoFisico
     */
    public void setStatoFisico(java.lang.String statoFisico) {
        this.statoFisico = statoFisico;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GruppoRifiutiGestiti)) return false;
        GruppoRifiutiGestiti other = (GruppoRifiutiGestiti) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.carattOrganolettiche==null && other.getCarattOrganolettiche()==null) || 
             (this.carattOrganolettiche!=null &&
              this.carattOrganolettiche.equals(other.getCarattOrganolettiche()))) &&
            ((this.gruppoUnitaMisura==null && other.getGruppoUnitaMisura()==null) || 
             (this.gruppoUnitaMisura!=null &&
              this.gruppoUnitaMisura.equals(other.getGruppoUnitaMisura()))) &&
            ((this.idGruppoRifiuti==null && other.getIdGruppoRifiuti()==null) || 
             (this.idGruppoRifiuti!=null &&
              this.idGruppoRifiuti.equals(other.getIdGruppoRifiuti()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.quantitaGruppo==null && other.getQuantitaGruppo()==null) || 
             (this.quantitaGruppo!=null &&
              this.quantitaGruppo.equals(other.getQuantitaGruppo()))) &&
            ((this.rifiutiGestitis==null && other.getRifiutiGestitis()==null) || 
             (this.rifiutiGestitis!=null &&
              java.util.Arrays.equals(this.rifiutiGestitis, other.getRifiutiGestitis()))) &&
            ((this.statoFisico==null && other.getStatoFisico()==null) || 
             (this.statoFisico!=null &&
              this.statoFisico.equals(other.getStatoFisico())));
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
        if (getCarattOrganolettiche() != null) {
            _hashCode += getCarattOrganolettiche().hashCode();
        }
        if (getGruppoUnitaMisura() != null) {
            _hashCode += getGruppoUnitaMisura().hashCode();
        }
        if (getIdGruppoRifiuti() != null) {
            _hashCode += getIdGruppoRifiuti().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getQuantitaGruppo() != null) {
            _hashCode += getQuantitaGruppo().hashCode();
        }
        if (getRifiutiGestitis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRifiutiGestitis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRifiutiGestitis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStatoFisico() != null) {
            _hashCode += getStatoFisico().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GruppoRifiutiGestiti.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "gruppoRifiutiGestiti"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("carattOrganolettiche");
        elemField.setXmlName(new javax.xml.namespace.QName("", "carattOrganolettiche"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gruppoUnitaMisura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gruppoUnitaMisura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idGruppoRifiuti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idGruppoRifiuti"));
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
        elemField.setFieldName("quantitaGruppo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quantitaGruppo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rifiutiGestitis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rifiutiGestitis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "rifiutiGestiti"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoFisico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statoFisico"));
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
