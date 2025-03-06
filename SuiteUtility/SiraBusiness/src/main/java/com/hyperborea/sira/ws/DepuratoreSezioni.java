/**
 * DepuratoreSezioni.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class DepuratoreSezioni  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.DepuratoreSezioniParametri[] depuratoreSezioniParametris;

    private java.lang.Integer idDepuratoreSezioni;

    private java.lang.Integer unitaPreesistenti;

    private java.lang.Integer unitaPreviste;

    private com.hyperborea.sira.ws.VocDepTipologieSezioni vocDepTipologieSezioni;

    public DepuratoreSezioni() {
    }

    public DepuratoreSezioni(
           com.hyperborea.sira.ws.DepuratoreSezioniParametri[] depuratoreSezioniParametris,
           java.lang.Integer idDepuratoreSezioni,
           java.lang.Integer unitaPreesistenti,
           java.lang.Integer unitaPreviste,
           com.hyperborea.sira.ws.VocDepTipologieSezioni vocDepTipologieSezioni) {
        this.depuratoreSezioniParametris = depuratoreSezioniParametris;
        this.idDepuratoreSezioni = idDepuratoreSezioni;
        this.unitaPreesistenti = unitaPreesistenti;
        this.unitaPreviste = unitaPreviste;
        this.vocDepTipologieSezioni = vocDepTipologieSezioni;
    }


    /**
     * Gets the depuratoreSezioniParametris value for this DepuratoreSezioni.
     * 
     * @return depuratoreSezioniParametris
     */
    public com.hyperborea.sira.ws.DepuratoreSezioniParametri[] getDepuratoreSezioniParametris() {
        return depuratoreSezioniParametris;
    }


    /**
     * Sets the depuratoreSezioniParametris value for this DepuratoreSezioni.
     * 
     * @param depuratoreSezioniParametris
     */
    public void setDepuratoreSezioniParametris(com.hyperborea.sira.ws.DepuratoreSezioniParametri[] depuratoreSezioniParametris) {
        this.depuratoreSezioniParametris = depuratoreSezioniParametris;
    }

    public com.hyperborea.sira.ws.DepuratoreSezioniParametri getDepuratoreSezioniParametris(int i) {
        return this.depuratoreSezioniParametris[i];
    }

    public void setDepuratoreSezioniParametris(int i, com.hyperborea.sira.ws.DepuratoreSezioniParametri _value) {
        this.depuratoreSezioniParametris[i] = _value;
    }


    /**
     * Gets the idDepuratoreSezioni value for this DepuratoreSezioni.
     * 
     * @return idDepuratoreSezioni
     */
    public java.lang.Integer getIdDepuratoreSezioni() {
        return idDepuratoreSezioni;
    }


    /**
     * Sets the idDepuratoreSezioni value for this DepuratoreSezioni.
     * 
     * @param idDepuratoreSezioni
     */
    public void setIdDepuratoreSezioni(java.lang.Integer idDepuratoreSezioni) {
        this.idDepuratoreSezioni = idDepuratoreSezioni;
    }


    /**
     * Gets the unitaPreesistenti value for this DepuratoreSezioni.
     * 
     * @return unitaPreesistenti
     */
    public java.lang.Integer getUnitaPreesistenti() {
        return unitaPreesistenti;
    }


    /**
     * Sets the unitaPreesistenti value for this DepuratoreSezioni.
     * 
     * @param unitaPreesistenti
     */
    public void setUnitaPreesistenti(java.lang.Integer unitaPreesistenti) {
        this.unitaPreesistenti = unitaPreesistenti;
    }


    /**
     * Gets the unitaPreviste value for this DepuratoreSezioni.
     * 
     * @return unitaPreviste
     */
    public java.lang.Integer getUnitaPreviste() {
        return unitaPreviste;
    }


    /**
     * Sets the unitaPreviste value for this DepuratoreSezioni.
     * 
     * @param unitaPreviste
     */
    public void setUnitaPreviste(java.lang.Integer unitaPreviste) {
        this.unitaPreviste = unitaPreviste;
    }


    /**
     * Gets the vocDepTipologieSezioni value for this DepuratoreSezioni.
     * 
     * @return vocDepTipologieSezioni
     */
    public com.hyperborea.sira.ws.VocDepTipologieSezioni getVocDepTipologieSezioni() {
        return vocDepTipologieSezioni;
    }


    /**
     * Sets the vocDepTipologieSezioni value for this DepuratoreSezioni.
     * 
     * @param vocDepTipologieSezioni
     */
    public void setVocDepTipologieSezioni(com.hyperborea.sira.ws.VocDepTipologieSezioni vocDepTipologieSezioni) {
        this.vocDepTipologieSezioni = vocDepTipologieSezioni;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepuratoreSezioni)) return false;
        DepuratoreSezioni other = (DepuratoreSezioni) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.depuratoreSezioniParametris==null && other.getDepuratoreSezioniParametris()==null) || 
             (this.depuratoreSezioniParametris!=null &&
              java.util.Arrays.equals(this.depuratoreSezioniParametris, other.getDepuratoreSezioniParametris()))) &&
            ((this.idDepuratoreSezioni==null && other.getIdDepuratoreSezioni()==null) || 
             (this.idDepuratoreSezioni!=null &&
              this.idDepuratoreSezioni.equals(other.getIdDepuratoreSezioni()))) &&
            ((this.unitaPreesistenti==null && other.getUnitaPreesistenti()==null) || 
             (this.unitaPreesistenti!=null &&
              this.unitaPreesistenti.equals(other.getUnitaPreesistenti()))) &&
            ((this.unitaPreviste==null && other.getUnitaPreviste()==null) || 
             (this.unitaPreviste!=null &&
              this.unitaPreviste.equals(other.getUnitaPreviste()))) &&
            ((this.vocDepTipologieSezioni==null && other.getVocDepTipologieSezioni()==null) || 
             (this.vocDepTipologieSezioni!=null &&
              this.vocDepTipologieSezioni.equals(other.getVocDepTipologieSezioni())));
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
        if (getDepuratoreSezioniParametris() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDepuratoreSezioniParametris());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDepuratoreSezioniParametris(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIdDepuratoreSezioni() != null) {
            _hashCode += getIdDepuratoreSezioni().hashCode();
        }
        if (getUnitaPreesistenti() != null) {
            _hashCode += getUnitaPreesistenti().hashCode();
        }
        if (getUnitaPreviste() != null) {
            _hashCode += getUnitaPreviste().hashCode();
        }
        if (getVocDepTipologieSezioni() != null) {
            _hashCode += getVocDepTipologieSezioni().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepuratoreSezioni.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "depuratoreSezioni"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depuratoreSezioniParametris");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depuratoreSezioniParametris"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "depuratoreSezioniParametri"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDepuratoreSezioni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDepuratoreSezioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitaPreesistenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unitaPreesistenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitaPreviste");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unitaPreviste"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocDepTipologieSezioni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocDepTipologieSezioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocDepTipologieSezioni"));
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
