/**
 * SorgRumore.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class SorgRumore  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Float capacitaAbbattimento;

    private java.lang.String descrizione;

    private java.lang.Integer idSorgRumore;

    private java.lang.String localizzazione;

    private java.lang.Float presSonoraGiorno;

    private java.lang.Float presSonoraNotte;

    private java.lang.String sistemiContenimento;

    public SorgRumore() {
    }

    public SorgRumore(
           java.lang.Float capacitaAbbattimento,
           java.lang.String descrizione,
           java.lang.Integer idSorgRumore,
           java.lang.String localizzazione,
           java.lang.Float presSonoraGiorno,
           java.lang.Float presSonoraNotte,
           java.lang.String sistemiContenimento) {
        this.capacitaAbbattimento = capacitaAbbattimento;
        this.descrizione = descrizione;
        this.idSorgRumore = idSorgRumore;
        this.localizzazione = localizzazione;
        this.presSonoraGiorno = presSonoraGiorno;
        this.presSonoraNotte = presSonoraNotte;
        this.sistemiContenimento = sistemiContenimento;
    }


    /**
     * Gets the capacitaAbbattimento value for this SorgRumore.
     * 
     * @return capacitaAbbattimento
     */
    public java.lang.Float getCapacitaAbbattimento() {
        return capacitaAbbattimento;
    }


    /**
     * Sets the capacitaAbbattimento value for this SorgRumore.
     * 
     * @param capacitaAbbattimento
     */
    public void setCapacitaAbbattimento(java.lang.Float capacitaAbbattimento) {
        this.capacitaAbbattimento = capacitaAbbattimento;
    }


    /**
     * Gets the descrizione value for this SorgRumore.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this SorgRumore.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idSorgRumore value for this SorgRumore.
     * 
     * @return idSorgRumore
     */
    public java.lang.Integer getIdSorgRumore() {
        return idSorgRumore;
    }


    /**
     * Sets the idSorgRumore value for this SorgRumore.
     * 
     * @param idSorgRumore
     */
    public void setIdSorgRumore(java.lang.Integer idSorgRumore) {
        this.idSorgRumore = idSorgRumore;
    }


    /**
     * Gets the localizzazione value for this SorgRumore.
     * 
     * @return localizzazione
     */
    public java.lang.String getLocalizzazione() {
        return localizzazione;
    }


    /**
     * Sets the localizzazione value for this SorgRumore.
     * 
     * @param localizzazione
     */
    public void setLocalizzazione(java.lang.String localizzazione) {
        this.localizzazione = localizzazione;
    }


    /**
     * Gets the presSonoraGiorno value for this SorgRumore.
     * 
     * @return presSonoraGiorno
     */
    public java.lang.Float getPresSonoraGiorno() {
        return presSonoraGiorno;
    }


    /**
     * Sets the presSonoraGiorno value for this SorgRumore.
     * 
     * @param presSonoraGiorno
     */
    public void setPresSonoraGiorno(java.lang.Float presSonoraGiorno) {
        this.presSonoraGiorno = presSonoraGiorno;
    }


    /**
     * Gets the presSonoraNotte value for this SorgRumore.
     * 
     * @return presSonoraNotte
     */
    public java.lang.Float getPresSonoraNotte() {
        return presSonoraNotte;
    }


    /**
     * Sets the presSonoraNotte value for this SorgRumore.
     * 
     * @param presSonoraNotte
     */
    public void setPresSonoraNotte(java.lang.Float presSonoraNotte) {
        this.presSonoraNotte = presSonoraNotte;
    }


    /**
     * Gets the sistemiContenimento value for this SorgRumore.
     * 
     * @return sistemiContenimento
     */
    public java.lang.String getSistemiContenimento() {
        return sistemiContenimento;
    }


    /**
     * Sets the sistemiContenimento value for this SorgRumore.
     * 
     * @param sistemiContenimento
     */
    public void setSistemiContenimento(java.lang.String sistemiContenimento) {
        this.sistemiContenimento = sistemiContenimento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SorgRumore)) return false;
        SorgRumore other = (SorgRumore) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.capacitaAbbattimento==null && other.getCapacitaAbbattimento()==null) || 
             (this.capacitaAbbattimento!=null &&
              this.capacitaAbbattimento.equals(other.getCapacitaAbbattimento()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.idSorgRumore==null && other.getIdSorgRumore()==null) || 
             (this.idSorgRumore!=null &&
              this.idSorgRumore.equals(other.getIdSorgRumore()))) &&
            ((this.localizzazione==null && other.getLocalizzazione()==null) || 
             (this.localizzazione!=null &&
              this.localizzazione.equals(other.getLocalizzazione()))) &&
            ((this.presSonoraGiorno==null && other.getPresSonoraGiorno()==null) || 
             (this.presSonoraGiorno!=null &&
              this.presSonoraGiorno.equals(other.getPresSonoraGiorno()))) &&
            ((this.presSonoraNotte==null && other.getPresSonoraNotte()==null) || 
             (this.presSonoraNotte!=null &&
              this.presSonoraNotte.equals(other.getPresSonoraNotte()))) &&
            ((this.sistemiContenimento==null && other.getSistemiContenimento()==null) || 
             (this.sistemiContenimento!=null &&
              this.sistemiContenimento.equals(other.getSistemiContenimento())));
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
        if (getCapacitaAbbattimento() != null) {
            _hashCode += getCapacitaAbbattimento().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getIdSorgRumore() != null) {
            _hashCode += getIdSorgRumore().hashCode();
        }
        if (getLocalizzazione() != null) {
            _hashCode += getLocalizzazione().hashCode();
        }
        if (getPresSonoraGiorno() != null) {
            _hashCode += getPresSonoraGiorno().hashCode();
        }
        if (getPresSonoraNotte() != null) {
            _hashCode += getPresSonoraNotte().hashCode();
        }
        if (getSistemiContenimento() != null) {
            _hashCode += getSistemiContenimento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SorgRumore.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgRumore"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacitaAbbattimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capacitaAbbattimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
        elemField.setFieldName("idSorgRumore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idSorgRumore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localizzazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "localizzazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("presSonoraGiorno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "presSonoraGiorno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("presSonoraNotte");
        elemField.setXmlName(new javax.xml.namespace.QName("", "presSonoraNotte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sistemiContenimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sistemiContenimento"));
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
