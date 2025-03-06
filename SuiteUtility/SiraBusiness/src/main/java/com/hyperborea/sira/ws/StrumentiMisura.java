/**
 * StrumentiMisura.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class StrumentiMisura  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst;

    private java.util.Calendar dataInstallazione;

    private java.util.Calendar dataRimozione;

    private java.lang.Integer idStrumento;

    private com.hyperborea.sira.ws.MisureStrumentali[] misureStrumentalis;

    private com.hyperborea.sira.ws.TipiStrumentimisura tipiStrumentimisura;

    public StrumentiMisura() {
    }

    public StrumentiMisura(
           com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst,
           java.util.Calendar dataInstallazione,
           java.util.Calendar dataRimozione,
           java.lang.Integer idStrumento,
           com.hyperborea.sira.ws.MisureStrumentali[] misureStrumentalis,
           com.hyperborea.sira.ws.TipiStrumentimisura tipiStrumentimisura) {
        this.caratterizzazioniOst = caratterizzazioniOst;
        this.dataInstallazione = dataInstallazione;
        this.dataRimozione = dataRimozione;
        this.idStrumento = idStrumento;
        this.misureStrumentalis = misureStrumentalis;
        this.tipiStrumentimisura = tipiStrumentimisura;
    }


    /**
     * Gets the caratterizzazioniOst value for this StrumentiMisura.
     * 
     * @return caratterizzazioniOst
     */
    public com.hyperborea.sira.ws.CaratterizzazioniOst getCaratterizzazioniOst() {
        return caratterizzazioniOst;
    }


    /**
     * Sets the caratterizzazioniOst value for this StrumentiMisura.
     * 
     * @param caratterizzazioniOst
     */
    public void setCaratterizzazioniOst(com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst) {
        this.caratterizzazioniOst = caratterizzazioniOst;
    }


    /**
     * Gets the dataInstallazione value for this StrumentiMisura.
     * 
     * @return dataInstallazione
     */
    public java.util.Calendar getDataInstallazione() {
        return dataInstallazione;
    }


    /**
     * Sets the dataInstallazione value for this StrumentiMisura.
     * 
     * @param dataInstallazione
     */
    public void setDataInstallazione(java.util.Calendar dataInstallazione) {
        this.dataInstallazione = dataInstallazione;
    }


    /**
     * Gets the dataRimozione value for this StrumentiMisura.
     * 
     * @return dataRimozione
     */
    public java.util.Calendar getDataRimozione() {
        return dataRimozione;
    }


    /**
     * Sets the dataRimozione value for this StrumentiMisura.
     * 
     * @param dataRimozione
     */
    public void setDataRimozione(java.util.Calendar dataRimozione) {
        this.dataRimozione = dataRimozione;
    }


    /**
     * Gets the idStrumento value for this StrumentiMisura.
     * 
     * @return idStrumento
     */
    public java.lang.Integer getIdStrumento() {
        return idStrumento;
    }


    /**
     * Sets the idStrumento value for this StrumentiMisura.
     * 
     * @param idStrumento
     */
    public void setIdStrumento(java.lang.Integer idStrumento) {
        this.idStrumento = idStrumento;
    }


    /**
     * Gets the misureStrumentalis value for this StrumentiMisura.
     * 
     * @return misureStrumentalis
     */
    public com.hyperborea.sira.ws.MisureStrumentali[] getMisureStrumentalis() {
        return misureStrumentalis;
    }


    /**
     * Sets the misureStrumentalis value for this StrumentiMisura.
     * 
     * @param misureStrumentalis
     */
    public void setMisureStrumentalis(com.hyperborea.sira.ws.MisureStrumentali[] misureStrumentalis) {
        this.misureStrumentalis = misureStrumentalis;
    }

    public com.hyperborea.sira.ws.MisureStrumentali getMisureStrumentalis(int i) {
        return this.misureStrumentalis[i];
    }

    public void setMisureStrumentalis(int i, com.hyperborea.sira.ws.MisureStrumentali _value) {
        this.misureStrumentalis[i] = _value;
    }


    /**
     * Gets the tipiStrumentimisura value for this StrumentiMisura.
     * 
     * @return tipiStrumentimisura
     */
    public com.hyperborea.sira.ws.TipiStrumentimisura getTipiStrumentimisura() {
        return tipiStrumentimisura;
    }


    /**
     * Sets the tipiStrumentimisura value for this StrumentiMisura.
     * 
     * @param tipiStrumentimisura
     */
    public void setTipiStrumentimisura(com.hyperborea.sira.ws.TipiStrumentimisura tipiStrumentimisura) {
        this.tipiStrumentimisura = tipiStrumentimisura;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StrumentiMisura)) return false;
        StrumentiMisura other = (StrumentiMisura) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.caratterizzazioniOst==null && other.getCaratterizzazioniOst()==null) || 
             (this.caratterizzazioniOst!=null &&
              this.caratterizzazioniOst.equals(other.getCaratterizzazioniOst()))) &&
            ((this.dataInstallazione==null && other.getDataInstallazione()==null) || 
             (this.dataInstallazione!=null &&
              this.dataInstallazione.equals(other.getDataInstallazione()))) &&
            ((this.dataRimozione==null && other.getDataRimozione()==null) || 
             (this.dataRimozione!=null &&
              this.dataRimozione.equals(other.getDataRimozione()))) &&
            ((this.idStrumento==null && other.getIdStrumento()==null) || 
             (this.idStrumento!=null &&
              this.idStrumento.equals(other.getIdStrumento()))) &&
            ((this.misureStrumentalis==null && other.getMisureStrumentalis()==null) || 
             (this.misureStrumentalis!=null &&
              java.util.Arrays.equals(this.misureStrumentalis, other.getMisureStrumentalis()))) &&
            ((this.tipiStrumentimisura==null && other.getTipiStrumentimisura()==null) || 
             (this.tipiStrumentimisura!=null &&
              this.tipiStrumentimisura.equals(other.getTipiStrumentimisura())));
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
        if (getCaratterizzazioniOst() != null) {
            _hashCode += getCaratterizzazioniOst().hashCode();
        }
        if (getDataInstallazione() != null) {
            _hashCode += getDataInstallazione().hashCode();
        }
        if (getDataRimozione() != null) {
            _hashCode += getDataRimozione().hashCode();
        }
        if (getIdStrumento() != null) {
            _hashCode += getIdStrumento().hashCode();
        }
        if (getMisureStrumentalis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMisureStrumentalis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMisureStrumentalis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTipiStrumentimisura() != null) {
            _hashCode += getTipiStrumentimisura().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StrumentiMisura.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "strumentiMisura"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caratterizzazioniOst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caratterizzazioniOst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "caratterizzazioniOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInstallazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInstallazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataRimozione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataRimozione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idStrumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idStrumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("misureStrumentalis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "misureStrumentalis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "misureStrumentali"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipiStrumentimisura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipiStrumentimisura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiStrumentimisura"));
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
