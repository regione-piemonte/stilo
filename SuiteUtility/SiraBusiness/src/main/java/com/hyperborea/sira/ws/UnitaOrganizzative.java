/**
 * UnitaOrganizzative.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class UnitaOrganizzative  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String denominazioneUo;

    private com.hyperborea.sira.ws.EcgUo[] ecgUos;

    private java.lang.String emailUo;

    private java.lang.String idUo;

    private com.hyperborea.sira.ws.PaUo[] paUos;

    private java.lang.String telefonoUo;

    private com.hyperborea.sira.ws.Utenti[] utentis;

    public UnitaOrganizzative() {
    }

    public UnitaOrganizzative(
           java.lang.String denominazioneUo,
           com.hyperborea.sira.ws.EcgUo[] ecgUos,
           java.lang.String emailUo,
           java.lang.String idUo,
           com.hyperborea.sira.ws.PaUo[] paUos,
           java.lang.String telefonoUo,
           com.hyperborea.sira.ws.Utenti[] utentis) {
        this.denominazioneUo = denominazioneUo;
        this.ecgUos = ecgUos;
        this.emailUo = emailUo;
        this.idUo = idUo;
        this.paUos = paUos;
        this.telefonoUo = telefonoUo;
        this.utentis = utentis;
    }


    /**
     * Gets the denominazioneUo value for this UnitaOrganizzative.
     * 
     * @return denominazioneUo
     */
    public java.lang.String getDenominazioneUo() {
        return denominazioneUo;
    }


    /**
     * Sets the denominazioneUo value for this UnitaOrganizzative.
     * 
     * @param denominazioneUo
     */
    public void setDenominazioneUo(java.lang.String denominazioneUo) {
        this.denominazioneUo = denominazioneUo;
    }


    /**
     * Gets the ecgUos value for this UnitaOrganizzative.
     * 
     * @return ecgUos
     */
    public com.hyperborea.sira.ws.EcgUo[] getEcgUos() {
        return ecgUos;
    }


    /**
     * Sets the ecgUos value for this UnitaOrganizzative.
     * 
     * @param ecgUos
     */
    public void setEcgUos(com.hyperborea.sira.ws.EcgUo[] ecgUos) {
        this.ecgUos = ecgUos;
    }

    public com.hyperborea.sira.ws.EcgUo getEcgUos(int i) {
        return this.ecgUos[i];
    }

    public void setEcgUos(int i, com.hyperborea.sira.ws.EcgUo _value) {
        this.ecgUos[i] = _value;
    }


    /**
     * Gets the emailUo value for this UnitaOrganizzative.
     * 
     * @return emailUo
     */
    public java.lang.String getEmailUo() {
        return emailUo;
    }


    /**
     * Sets the emailUo value for this UnitaOrganizzative.
     * 
     * @param emailUo
     */
    public void setEmailUo(java.lang.String emailUo) {
        this.emailUo = emailUo;
    }


    /**
     * Gets the idUo value for this UnitaOrganizzative.
     * 
     * @return idUo
     */
    public java.lang.String getIdUo() {
        return idUo;
    }


    /**
     * Sets the idUo value for this UnitaOrganizzative.
     * 
     * @param idUo
     */
    public void setIdUo(java.lang.String idUo) {
        this.idUo = idUo;
    }


    /**
     * Gets the paUos value for this UnitaOrganizzative.
     * 
     * @return paUos
     */
    public com.hyperborea.sira.ws.PaUo[] getPaUos() {
        return paUos;
    }


    /**
     * Sets the paUos value for this UnitaOrganizzative.
     * 
     * @param paUos
     */
    public void setPaUos(com.hyperborea.sira.ws.PaUo[] paUos) {
        this.paUos = paUos;
    }

    public com.hyperborea.sira.ws.PaUo getPaUos(int i) {
        return this.paUos[i];
    }

    public void setPaUos(int i, com.hyperborea.sira.ws.PaUo _value) {
        this.paUos[i] = _value;
    }


    /**
     * Gets the telefonoUo value for this UnitaOrganizzative.
     * 
     * @return telefonoUo
     */
    public java.lang.String getTelefonoUo() {
        return telefonoUo;
    }


    /**
     * Sets the telefonoUo value for this UnitaOrganizzative.
     * 
     * @param telefonoUo
     */
    public void setTelefonoUo(java.lang.String telefonoUo) {
        this.telefonoUo = telefonoUo;
    }


    /**
     * Gets the utentis value for this UnitaOrganizzative.
     * 
     * @return utentis
     */
    public com.hyperborea.sira.ws.Utenti[] getUtentis() {
        return utentis;
    }


    /**
     * Sets the utentis value for this UnitaOrganizzative.
     * 
     * @param utentis
     */
    public void setUtentis(com.hyperborea.sira.ws.Utenti[] utentis) {
        this.utentis = utentis;
    }

    public com.hyperborea.sira.ws.Utenti getUtentis(int i) {
        return this.utentis[i];
    }

    public void setUtentis(int i, com.hyperborea.sira.ws.Utenti _value) {
        this.utentis[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UnitaOrganizzative)) return false;
        UnitaOrganizzative other = (UnitaOrganizzative) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.denominazioneUo==null && other.getDenominazioneUo()==null) || 
             (this.denominazioneUo!=null &&
              this.denominazioneUo.equals(other.getDenominazioneUo()))) &&
            ((this.ecgUos==null && other.getEcgUos()==null) || 
             (this.ecgUos!=null &&
              java.util.Arrays.equals(this.ecgUos, other.getEcgUos()))) &&
            ((this.emailUo==null && other.getEmailUo()==null) || 
             (this.emailUo!=null &&
              this.emailUo.equals(other.getEmailUo()))) &&
            ((this.idUo==null && other.getIdUo()==null) || 
             (this.idUo!=null &&
              this.idUo.equals(other.getIdUo()))) &&
            ((this.paUos==null && other.getPaUos()==null) || 
             (this.paUos!=null &&
              java.util.Arrays.equals(this.paUos, other.getPaUos()))) &&
            ((this.telefonoUo==null && other.getTelefonoUo()==null) || 
             (this.telefonoUo!=null &&
              this.telefonoUo.equals(other.getTelefonoUo()))) &&
            ((this.utentis==null && other.getUtentis()==null) || 
             (this.utentis!=null &&
              java.util.Arrays.equals(this.utentis, other.getUtentis())));
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
        if (getDenominazioneUo() != null) {
            _hashCode += getDenominazioneUo().hashCode();
        }
        if (getEcgUos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEcgUos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEcgUos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEmailUo() != null) {
            _hashCode += getEmailUo().hashCode();
        }
        if (getIdUo() != null) {
            _hashCode += getIdUo().hashCode();
        }
        if (getPaUos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPaUos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPaUos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTelefonoUo() != null) {
            _hashCode += getTelefonoUo().hashCode();
        }
        if (getUtentis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUtentis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUtentis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UnitaOrganizzative.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "unitaOrganizzative"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazioneUo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazioneUo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ecgUos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ecgUos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ecgUo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailUo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emailUo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idUo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idUo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paUos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paUos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "paUo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("telefonoUo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "telefonoUo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utentis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utentis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utenti"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
