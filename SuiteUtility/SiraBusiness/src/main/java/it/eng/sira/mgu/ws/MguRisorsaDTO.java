/**
 * MguRisorsaDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.sira.mgu.ws;

public class MguRisorsaDTO  implements java.io.Serializable {
    private java.lang.String codice;

    private java.lang.String descrizione;

    private it.eng.sira.mgu.ws.MguRisorsaDTO[] risorse;

    private java.lang.String tipologia;

    public MguRisorsaDTO() {
    }

    public MguRisorsaDTO(
           java.lang.String codice,
           java.lang.String descrizione,
           it.eng.sira.mgu.ws.MguRisorsaDTO[] risorse,
           java.lang.String tipologia) {
           this.codice = codice;
           this.descrizione = descrizione;
           this.risorse = risorse;
           this.tipologia = tipologia;
    }


    /**
     * Gets the codice value for this MguRisorsaDTO.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this MguRisorsaDTO.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this MguRisorsaDTO.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this MguRisorsaDTO.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the risorse value for this MguRisorsaDTO.
     * 
     * @return risorse
     */
    public it.eng.sira.mgu.ws.MguRisorsaDTO[] getRisorse() {
        return risorse;
    }


    /**
     * Sets the risorse value for this MguRisorsaDTO.
     * 
     * @param risorse
     */
    public void setRisorse(it.eng.sira.mgu.ws.MguRisorsaDTO[] risorse) {
        this.risorse = risorse;
    }

    public it.eng.sira.mgu.ws.MguRisorsaDTO getRisorse(int i) {
        return this.risorse[i];
    }

    public void setRisorse(int i, it.eng.sira.mgu.ws.MguRisorsaDTO _value) {
        this.risorse[i] = _value;
    }


    /**
     * Gets the tipologia value for this MguRisorsaDTO.
     * 
     * @return tipologia
     */
    public java.lang.String getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this MguRisorsaDTO.
     * 
     * @param tipologia
     */
    public void setTipologia(java.lang.String tipologia) {
        this.tipologia = tipologia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MguRisorsaDTO)) return false;
        MguRisorsaDTO other = (MguRisorsaDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.risorse==null && other.getRisorse()==null) || 
             (this.risorse!=null &&
              java.util.Arrays.equals(this.risorse, other.getRisorse()))) &&
            ((this.tipologia==null && other.getTipologia()==null) || 
             (this.tipologia!=null &&
              this.tipologia.equals(other.getTipologia())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getRisorse() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRisorse());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRisorse(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MguRisorsaDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguRisorsaDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
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
        elemField.setFieldName("risorse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "risorse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguRisorsaDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologia"));
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
