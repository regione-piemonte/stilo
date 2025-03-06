/**
 * AreeStoccMat.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class AreeStoccMat  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Float capacita;

    private java.lang.String codiceIdentificazione;

    private java.lang.Integer idAreaStoccMat;

    private java.lang.String identificazione;

    private com.hyperborea.sira.ws.MaterialiStoccati[] materialiStoccatis;

    private java.lang.Float superficie;

    private java.lang.String umCapacita;

    public AreeStoccMat() {
    }

    public AreeStoccMat(
           java.lang.Float capacita,
           java.lang.String codiceIdentificazione,
           java.lang.Integer idAreaStoccMat,
           java.lang.String identificazione,
           com.hyperborea.sira.ws.MaterialiStoccati[] materialiStoccatis,
           java.lang.Float superficie,
           java.lang.String umCapacita) {
        this.capacita = capacita;
        this.codiceIdentificazione = codiceIdentificazione;
        this.idAreaStoccMat = idAreaStoccMat;
        this.identificazione = identificazione;
        this.materialiStoccatis = materialiStoccatis;
        this.superficie = superficie;
        this.umCapacita = umCapacita;
    }


    /**
     * Gets the capacita value for this AreeStoccMat.
     * 
     * @return capacita
     */
    public java.lang.Float getCapacita() {
        return capacita;
    }


    /**
     * Sets the capacita value for this AreeStoccMat.
     * 
     * @param capacita
     */
    public void setCapacita(java.lang.Float capacita) {
        this.capacita = capacita;
    }


    /**
     * Gets the codiceIdentificazione value for this AreeStoccMat.
     * 
     * @return codiceIdentificazione
     */
    public java.lang.String getCodiceIdentificazione() {
        return codiceIdentificazione;
    }


    /**
     * Sets the codiceIdentificazione value for this AreeStoccMat.
     * 
     * @param codiceIdentificazione
     */
    public void setCodiceIdentificazione(java.lang.String codiceIdentificazione) {
        this.codiceIdentificazione = codiceIdentificazione;
    }


    /**
     * Gets the idAreaStoccMat value for this AreeStoccMat.
     * 
     * @return idAreaStoccMat
     */
    public java.lang.Integer getIdAreaStoccMat() {
        return idAreaStoccMat;
    }


    /**
     * Sets the idAreaStoccMat value for this AreeStoccMat.
     * 
     * @param idAreaStoccMat
     */
    public void setIdAreaStoccMat(java.lang.Integer idAreaStoccMat) {
        this.idAreaStoccMat = idAreaStoccMat;
    }


    /**
     * Gets the identificazione value for this AreeStoccMat.
     * 
     * @return identificazione
     */
    public java.lang.String getIdentificazione() {
        return identificazione;
    }


    /**
     * Sets the identificazione value for this AreeStoccMat.
     * 
     * @param identificazione
     */
    public void setIdentificazione(java.lang.String identificazione) {
        this.identificazione = identificazione;
    }


    /**
     * Gets the materialiStoccatis value for this AreeStoccMat.
     * 
     * @return materialiStoccatis
     */
    public com.hyperborea.sira.ws.MaterialiStoccati[] getMaterialiStoccatis() {
        return materialiStoccatis;
    }


    /**
     * Sets the materialiStoccatis value for this AreeStoccMat.
     * 
     * @param materialiStoccatis
     */
    public void setMaterialiStoccatis(com.hyperborea.sira.ws.MaterialiStoccati[] materialiStoccatis) {
        this.materialiStoccatis = materialiStoccatis;
    }

    public com.hyperborea.sira.ws.MaterialiStoccati getMaterialiStoccatis(int i) {
        return this.materialiStoccatis[i];
    }

    public void setMaterialiStoccatis(int i, com.hyperborea.sira.ws.MaterialiStoccati _value) {
        this.materialiStoccatis[i] = _value;
    }


    /**
     * Gets the superficie value for this AreeStoccMat.
     * 
     * @return superficie
     */
    public java.lang.Float getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this AreeStoccMat.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Float superficie) {
        this.superficie = superficie;
    }


    /**
     * Gets the umCapacita value for this AreeStoccMat.
     * 
     * @return umCapacita
     */
    public java.lang.String getUmCapacita() {
        return umCapacita;
    }


    /**
     * Sets the umCapacita value for this AreeStoccMat.
     * 
     * @param umCapacita
     */
    public void setUmCapacita(java.lang.String umCapacita) {
        this.umCapacita = umCapacita;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AreeStoccMat)) return false;
        AreeStoccMat other = (AreeStoccMat) obj;
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
            ((this.codiceIdentificazione==null && other.getCodiceIdentificazione()==null) || 
             (this.codiceIdentificazione!=null &&
              this.codiceIdentificazione.equals(other.getCodiceIdentificazione()))) &&
            ((this.idAreaStoccMat==null && other.getIdAreaStoccMat()==null) || 
             (this.idAreaStoccMat!=null &&
              this.idAreaStoccMat.equals(other.getIdAreaStoccMat()))) &&
            ((this.identificazione==null && other.getIdentificazione()==null) || 
             (this.identificazione!=null &&
              this.identificazione.equals(other.getIdentificazione()))) &&
            ((this.materialiStoccatis==null && other.getMaterialiStoccatis()==null) || 
             (this.materialiStoccatis!=null &&
              java.util.Arrays.equals(this.materialiStoccatis, other.getMaterialiStoccatis()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie()))) &&
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
        if (getCapacita() != null) {
            _hashCode += getCapacita().hashCode();
        }
        if (getCodiceIdentificazione() != null) {
            _hashCode += getCodiceIdentificazione().hashCode();
        }
        if (getIdAreaStoccMat() != null) {
            _hashCode += getIdAreaStoccMat().hashCode();
        }
        if (getIdentificazione() != null) {
            _hashCode += getIdentificazione().hashCode();
        }
        if (getMaterialiStoccatis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMaterialiStoccatis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMaterialiStoccatis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        if (getUmCapacita() != null) {
            _hashCode += getUmCapacita().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AreeStoccMat.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "areeStoccMat"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capacita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceIdentificazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceIdentificazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAreaStoccMat");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idAreaStoccMat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("materialiStoccatis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "materialiStoccatis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "materialiStoccati"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umCapacita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umCapacita"));
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
