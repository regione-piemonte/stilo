/**
 * AreeStoccRifiuti.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class AreeStoccRifiuti  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Float capacita;

    private java.lang.String caratteristiche;

    private java.lang.String codiceIdentificazione;

    private java.lang.Integer idAreaStoccRifiuti;

    private java.lang.String identificazione;

    private java.lang.Float superficie;

    private java.lang.String tipologiaRifiuti;

    private java.lang.String umCapacita;

    private java.lang.Integer wsInternalRef;

    public AreeStoccRifiuti() {
    }

    public AreeStoccRifiuti(
           java.lang.Float capacita,
           java.lang.String caratteristiche,
           java.lang.String codiceIdentificazione,
           java.lang.Integer idAreaStoccRifiuti,
           java.lang.String identificazione,
           java.lang.Float superficie,
           java.lang.String tipologiaRifiuti,
           java.lang.String umCapacita,
           java.lang.Integer wsInternalRef) {
        this.capacita = capacita;
        this.caratteristiche = caratteristiche;
        this.codiceIdentificazione = codiceIdentificazione;
        this.idAreaStoccRifiuti = idAreaStoccRifiuti;
        this.identificazione = identificazione;
        this.superficie = superficie;
        this.tipologiaRifiuti = tipologiaRifiuti;
        this.umCapacita = umCapacita;
        this.wsInternalRef = wsInternalRef;
    }


    /**
     * Gets the capacita value for this AreeStoccRifiuti.
     * 
     * @return capacita
     */
    public java.lang.Float getCapacita() {
        return capacita;
    }


    /**
     * Sets the capacita value for this AreeStoccRifiuti.
     * 
     * @param capacita
     */
    public void setCapacita(java.lang.Float capacita) {
        this.capacita = capacita;
    }


    /**
     * Gets the caratteristiche value for this AreeStoccRifiuti.
     * 
     * @return caratteristiche
     */
    public java.lang.String getCaratteristiche() {
        return caratteristiche;
    }


    /**
     * Sets the caratteristiche value for this AreeStoccRifiuti.
     * 
     * @param caratteristiche
     */
    public void setCaratteristiche(java.lang.String caratteristiche) {
        this.caratteristiche = caratteristiche;
    }


    /**
     * Gets the codiceIdentificazione value for this AreeStoccRifiuti.
     * 
     * @return codiceIdentificazione
     */
    public java.lang.String getCodiceIdentificazione() {
        return codiceIdentificazione;
    }


    /**
     * Sets the codiceIdentificazione value for this AreeStoccRifiuti.
     * 
     * @param codiceIdentificazione
     */
    public void setCodiceIdentificazione(java.lang.String codiceIdentificazione) {
        this.codiceIdentificazione = codiceIdentificazione;
    }


    /**
     * Gets the idAreaStoccRifiuti value for this AreeStoccRifiuti.
     * 
     * @return idAreaStoccRifiuti
     */
    public java.lang.Integer getIdAreaStoccRifiuti() {
        return idAreaStoccRifiuti;
    }


    /**
     * Sets the idAreaStoccRifiuti value for this AreeStoccRifiuti.
     * 
     * @param idAreaStoccRifiuti
     */
    public void setIdAreaStoccRifiuti(java.lang.Integer idAreaStoccRifiuti) {
        this.idAreaStoccRifiuti = idAreaStoccRifiuti;
    }


    /**
     * Gets the identificazione value for this AreeStoccRifiuti.
     * 
     * @return identificazione
     */
    public java.lang.String getIdentificazione() {
        return identificazione;
    }


    /**
     * Sets the identificazione value for this AreeStoccRifiuti.
     * 
     * @param identificazione
     */
    public void setIdentificazione(java.lang.String identificazione) {
        this.identificazione = identificazione;
    }


    /**
     * Gets the superficie value for this AreeStoccRifiuti.
     * 
     * @return superficie
     */
    public java.lang.Float getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this AreeStoccRifiuti.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Float superficie) {
        this.superficie = superficie;
    }


    /**
     * Gets the tipologiaRifiuti value for this AreeStoccRifiuti.
     * 
     * @return tipologiaRifiuti
     */
    public java.lang.String getTipologiaRifiuti() {
        return tipologiaRifiuti;
    }


    /**
     * Sets the tipologiaRifiuti value for this AreeStoccRifiuti.
     * 
     * @param tipologiaRifiuti
     */
    public void setTipologiaRifiuti(java.lang.String tipologiaRifiuti) {
        this.tipologiaRifiuti = tipologiaRifiuti;
    }


    /**
     * Gets the umCapacita value for this AreeStoccRifiuti.
     * 
     * @return umCapacita
     */
    public java.lang.String getUmCapacita() {
        return umCapacita;
    }


    /**
     * Sets the umCapacita value for this AreeStoccRifiuti.
     * 
     * @param umCapacita
     */
    public void setUmCapacita(java.lang.String umCapacita) {
        this.umCapacita = umCapacita;
    }


    /**
     * Gets the wsInternalRef value for this AreeStoccRifiuti.
     * 
     * @return wsInternalRef
     */
    public java.lang.Integer getWsInternalRef() {
        return wsInternalRef;
    }


    /**
     * Sets the wsInternalRef value for this AreeStoccRifiuti.
     * 
     * @param wsInternalRef
     */
    public void setWsInternalRef(java.lang.Integer wsInternalRef) {
        this.wsInternalRef = wsInternalRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AreeStoccRifiuti)) return false;
        AreeStoccRifiuti other = (AreeStoccRifiuti) obj;
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
            ((this.caratteristiche==null && other.getCaratteristiche()==null) || 
             (this.caratteristiche!=null &&
              this.caratteristiche.equals(other.getCaratteristiche()))) &&
            ((this.codiceIdentificazione==null && other.getCodiceIdentificazione()==null) || 
             (this.codiceIdentificazione!=null &&
              this.codiceIdentificazione.equals(other.getCodiceIdentificazione()))) &&
            ((this.idAreaStoccRifiuti==null && other.getIdAreaStoccRifiuti()==null) || 
             (this.idAreaStoccRifiuti!=null &&
              this.idAreaStoccRifiuti.equals(other.getIdAreaStoccRifiuti()))) &&
            ((this.identificazione==null && other.getIdentificazione()==null) || 
             (this.identificazione!=null &&
              this.identificazione.equals(other.getIdentificazione()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie()))) &&
            ((this.tipologiaRifiuti==null && other.getTipologiaRifiuti()==null) || 
             (this.tipologiaRifiuti!=null &&
              this.tipologiaRifiuti.equals(other.getTipologiaRifiuti()))) &&
            ((this.umCapacita==null && other.getUmCapacita()==null) || 
             (this.umCapacita!=null &&
              this.umCapacita.equals(other.getUmCapacita()))) &&
            ((this.wsInternalRef==null && other.getWsInternalRef()==null) || 
             (this.wsInternalRef!=null &&
              this.wsInternalRef.equals(other.getWsInternalRef())));
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
        if (getCaratteristiche() != null) {
            _hashCode += getCaratteristiche().hashCode();
        }
        if (getCodiceIdentificazione() != null) {
            _hashCode += getCodiceIdentificazione().hashCode();
        }
        if (getIdAreaStoccRifiuti() != null) {
            _hashCode += getIdAreaStoccRifiuti().hashCode();
        }
        if (getIdentificazione() != null) {
            _hashCode += getIdentificazione().hashCode();
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        if (getTipologiaRifiuti() != null) {
            _hashCode += getTipologiaRifiuti().hashCode();
        }
        if (getUmCapacita() != null) {
            _hashCode += getUmCapacita().hashCode();
        }
        if (getWsInternalRef() != null) {
            _hashCode += getWsInternalRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AreeStoccRifiuti.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "areeStoccRifiuti"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capacita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caratteristiche");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caratteristiche"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceIdentificazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceIdentificazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAreaStoccRifiuti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idAreaStoccRifiuti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologiaRifiuti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologiaRifiuti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsInternalRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsInternalRef"));
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
