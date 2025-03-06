/**
 * Canali.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Canali  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Long codCanale;

    private java.lang.Long estremi;

    private java.lang.Float estremoinfMhz;

    private java.lang.Float estremosupMhz;

    private java.lang.Integer idCanale;

    private java.lang.String polarizzazione;

    private java.lang.Float potenzaCanaleW;

    private com.hyperborea.sira.ws.TipiAntenne tipiAntenne;

    public Canali() {
    }

    public Canali(
           java.lang.Long codCanale,
           java.lang.Long estremi,
           java.lang.Float estremoinfMhz,
           java.lang.Float estremosupMhz,
           java.lang.Integer idCanale,
           java.lang.String polarizzazione,
           java.lang.Float potenzaCanaleW,
           com.hyperborea.sira.ws.TipiAntenne tipiAntenne) {
        this.codCanale = codCanale;
        this.estremi = estremi;
        this.estremoinfMhz = estremoinfMhz;
        this.estremosupMhz = estremosupMhz;
        this.idCanale = idCanale;
        this.polarizzazione = polarizzazione;
        this.potenzaCanaleW = potenzaCanaleW;
        this.tipiAntenne = tipiAntenne;
    }


    /**
     * Gets the codCanale value for this Canali.
     * 
     * @return codCanale
     */
    public java.lang.Long getCodCanale() {
        return codCanale;
    }


    /**
     * Sets the codCanale value for this Canali.
     * 
     * @param codCanale
     */
    public void setCodCanale(java.lang.Long codCanale) {
        this.codCanale = codCanale;
    }


    /**
     * Gets the estremi value for this Canali.
     * 
     * @return estremi
     */
    public java.lang.Long getEstremi() {
        return estremi;
    }


    /**
     * Sets the estremi value for this Canali.
     * 
     * @param estremi
     */
    public void setEstremi(java.lang.Long estremi) {
        this.estremi = estremi;
    }


    /**
     * Gets the estremoinfMhz value for this Canali.
     * 
     * @return estremoinfMhz
     */
    public java.lang.Float getEstremoinfMhz() {
        return estremoinfMhz;
    }


    /**
     * Sets the estremoinfMhz value for this Canali.
     * 
     * @param estremoinfMhz
     */
    public void setEstremoinfMhz(java.lang.Float estremoinfMhz) {
        this.estremoinfMhz = estremoinfMhz;
    }


    /**
     * Gets the estremosupMhz value for this Canali.
     * 
     * @return estremosupMhz
     */
    public java.lang.Float getEstremosupMhz() {
        return estremosupMhz;
    }


    /**
     * Sets the estremosupMhz value for this Canali.
     * 
     * @param estremosupMhz
     */
    public void setEstremosupMhz(java.lang.Float estremosupMhz) {
        this.estremosupMhz = estremosupMhz;
    }


    /**
     * Gets the idCanale value for this Canali.
     * 
     * @return idCanale
     */
    public java.lang.Integer getIdCanale() {
        return idCanale;
    }


    /**
     * Sets the idCanale value for this Canali.
     * 
     * @param idCanale
     */
    public void setIdCanale(java.lang.Integer idCanale) {
        this.idCanale = idCanale;
    }


    /**
     * Gets the polarizzazione value for this Canali.
     * 
     * @return polarizzazione
     */
    public java.lang.String getPolarizzazione() {
        return polarizzazione;
    }


    /**
     * Sets the polarizzazione value for this Canali.
     * 
     * @param polarizzazione
     */
    public void setPolarizzazione(java.lang.String polarizzazione) {
        this.polarizzazione = polarizzazione;
    }


    /**
     * Gets the potenzaCanaleW value for this Canali.
     * 
     * @return potenzaCanaleW
     */
    public java.lang.Float getPotenzaCanaleW() {
        return potenzaCanaleW;
    }


    /**
     * Sets the potenzaCanaleW value for this Canali.
     * 
     * @param potenzaCanaleW
     */
    public void setPotenzaCanaleW(java.lang.Float potenzaCanaleW) {
        this.potenzaCanaleW = potenzaCanaleW;
    }


    /**
     * Gets the tipiAntenne value for this Canali.
     * 
     * @return tipiAntenne
     */
    public com.hyperborea.sira.ws.TipiAntenne getTipiAntenne() {
        return tipiAntenne;
    }


    /**
     * Sets the tipiAntenne value for this Canali.
     * 
     * @param tipiAntenne
     */
    public void setTipiAntenne(com.hyperborea.sira.ws.TipiAntenne tipiAntenne) {
        this.tipiAntenne = tipiAntenne;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Canali)) return false;
        Canali other = (Canali) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codCanale==null && other.getCodCanale()==null) || 
             (this.codCanale!=null &&
              this.codCanale.equals(other.getCodCanale()))) &&
            ((this.estremi==null && other.getEstremi()==null) || 
             (this.estremi!=null &&
              this.estremi.equals(other.getEstremi()))) &&
            ((this.estremoinfMhz==null && other.getEstremoinfMhz()==null) || 
             (this.estremoinfMhz!=null &&
              this.estremoinfMhz.equals(other.getEstremoinfMhz()))) &&
            ((this.estremosupMhz==null && other.getEstremosupMhz()==null) || 
             (this.estremosupMhz!=null &&
              this.estremosupMhz.equals(other.getEstremosupMhz()))) &&
            ((this.idCanale==null && other.getIdCanale()==null) || 
             (this.idCanale!=null &&
              this.idCanale.equals(other.getIdCanale()))) &&
            ((this.polarizzazione==null && other.getPolarizzazione()==null) || 
             (this.polarizzazione!=null &&
              this.polarizzazione.equals(other.getPolarizzazione()))) &&
            ((this.potenzaCanaleW==null && other.getPotenzaCanaleW()==null) || 
             (this.potenzaCanaleW!=null &&
              this.potenzaCanaleW.equals(other.getPotenzaCanaleW()))) &&
            ((this.tipiAntenne==null && other.getTipiAntenne()==null) || 
             (this.tipiAntenne!=null &&
              this.tipiAntenne.equals(other.getTipiAntenne())));
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
        if (getCodCanale() != null) {
            _hashCode += getCodCanale().hashCode();
        }
        if (getEstremi() != null) {
            _hashCode += getEstremi().hashCode();
        }
        if (getEstremoinfMhz() != null) {
            _hashCode += getEstremoinfMhz().hashCode();
        }
        if (getEstremosupMhz() != null) {
            _hashCode += getEstremosupMhz().hashCode();
        }
        if (getIdCanale() != null) {
            _hashCode += getIdCanale().hashCode();
        }
        if (getPolarizzazione() != null) {
            _hashCode += getPolarizzazione().hashCode();
        }
        if (getPotenzaCanaleW() != null) {
            _hashCode += getPotenzaCanaleW().hashCode();
        }
        if (getTipiAntenne() != null) {
            _hashCode += getTipiAntenne().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Canali.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "canali"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codCanale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codCanale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estremi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estremi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estremoinfMhz");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estremoinfMhz"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estremosupMhz");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estremosupMhz"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCanale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCanale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("polarizzazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "polarizzazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzaCanaleW");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzaCanaleW"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipiAntenne");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipiAntenne"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiAntenne"));
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
