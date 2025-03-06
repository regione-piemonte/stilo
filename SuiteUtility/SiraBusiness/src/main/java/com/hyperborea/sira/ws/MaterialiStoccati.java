/**
 * MaterialiStoccati.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class MaterialiStoccati  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private float capacita;

    private java.lang.Integer idMateriale;

    private java.lang.String materiale;

    private java.lang.String modalita;

    private java.lang.String umCapacita;

    public MaterialiStoccati() {
    }

    public MaterialiStoccati(
           float capacita,
           java.lang.Integer idMateriale,
           java.lang.String materiale,
           java.lang.String modalita,
           java.lang.String umCapacita) {
        this.capacita = capacita;
        this.idMateriale = idMateriale;
        this.materiale = materiale;
        this.modalita = modalita;
        this.umCapacita = umCapacita;
    }


    /**
     * Gets the capacita value for this MaterialiStoccati.
     * 
     * @return capacita
     */
    public float getCapacita() {
        return capacita;
    }


    /**
     * Sets the capacita value for this MaterialiStoccati.
     * 
     * @param capacita
     */
    public void setCapacita(float capacita) {
        this.capacita = capacita;
    }


    /**
     * Gets the idMateriale value for this MaterialiStoccati.
     * 
     * @return idMateriale
     */
    public java.lang.Integer getIdMateriale() {
        return idMateriale;
    }


    /**
     * Sets the idMateriale value for this MaterialiStoccati.
     * 
     * @param idMateriale
     */
    public void setIdMateriale(java.lang.Integer idMateriale) {
        this.idMateriale = idMateriale;
    }


    /**
     * Gets the materiale value for this MaterialiStoccati.
     * 
     * @return materiale
     */
    public java.lang.String getMateriale() {
        return materiale;
    }


    /**
     * Sets the materiale value for this MaterialiStoccati.
     * 
     * @param materiale
     */
    public void setMateriale(java.lang.String materiale) {
        this.materiale = materiale;
    }


    /**
     * Gets the modalita value for this MaterialiStoccati.
     * 
     * @return modalita
     */
    public java.lang.String getModalita() {
        return modalita;
    }


    /**
     * Sets the modalita value for this MaterialiStoccati.
     * 
     * @param modalita
     */
    public void setModalita(java.lang.String modalita) {
        this.modalita = modalita;
    }


    /**
     * Gets the umCapacita value for this MaterialiStoccati.
     * 
     * @return umCapacita
     */
    public java.lang.String getUmCapacita() {
        return umCapacita;
    }


    /**
     * Sets the umCapacita value for this MaterialiStoccati.
     * 
     * @param umCapacita
     */
    public void setUmCapacita(java.lang.String umCapacita) {
        this.umCapacita = umCapacita;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MaterialiStoccati)) return false;
        MaterialiStoccati other = (MaterialiStoccati) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.capacita == other.getCapacita() &&
            ((this.idMateriale==null && other.getIdMateriale()==null) || 
             (this.idMateriale!=null &&
              this.idMateriale.equals(other.getIdMateriale()))) &&
            ((this.materiale==null && other.getMateriale()==null) || 
             (this.materiale!=null &&
              this.materiale.equals(other.getMateriale()))) &&
            ((this.modalita==null && other.getModalita()==null) || 
             (this.modalita!=null &&
              this.modalita.equals(other.getModalita()))) &&
            ((this.umCapacita==null && other.getUmCapacita()==null) || 
             (this.umCapacita!=null &&
              this.umCapacita.equals(other.getUmCapacita())));
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
        _hashCode += new Float(getCapacita()).hashCode();
        if (getIdMateriale() != null) {
            _hashCode += getIdMateriale().hashCode();
        }
        if (getMateriale() != null) {
            _hashCode += getMateriale().hashCode();
        }
        if (getModalita() != null) {
            _hashCode += getModalita().hashCode();
        }
        if (getUmCapacita() != null) {
            _hashCode += getUmCapacita().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MaterialiStoccati.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "materialiStoccati"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capacita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMateriale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idMateriale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("materiale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "materiale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modalita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modalita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umCapacita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umCapacita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
