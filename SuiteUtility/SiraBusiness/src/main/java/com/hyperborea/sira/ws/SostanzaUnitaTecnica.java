/**
 * SostanzaUnitaTecnica.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class SostanzaUnitaTecnica  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.EventoIncidentaleRir[] eventoIncidentaleRirs;

    private java.lang.Integer idSostanza;

    private java.lang.Float quantita;

    private com.hyperborea.sira.ws.ScenarioIncidentaleRir[] scenarioIncidentaleRirs;

    private com.hyperborea.sira.ws.SostanzaRir sostanzaRir;

    private com.hyperborea.sira.ws.VocTipoQuantita vocTipoQuantita;

    public SostanzaUnitaTecnica() {
    }

    public SostanzaUnitaTecnica(
           com.hyperborea.sira.ws.EventoIncidentaleRir[] eventoIncidentaleRirs,
           java.lang.Integer idSostanza,
           java.lang.Float quantita,
           com.hyperborea.sira.ws.ScenarioIncidentaleRir[] scenarioIncidentaleRirs,
           com.hyperborea.sira.ws.SostanzaRir sostanzaRir,
           com.hyperborea.sira.ws.VocTipoQuantita vocTipoQuantita) {
        this.eventoIncidentaleRirs = eventoIncidentaleRirs;
        this.idSostanza = idSostanza;
        this.quantita = quantita;
        this.scenarioIncidentaleRirs = scenarioIncidentaleRirs;
        this.sostanzaRir = sostanzaRir;
        this.vocTipoQuantita = vocTipoQuantita;
    }


    /**
     * Gets the eventoIncidentaleRirs value for this SostanzaUnitaTecnica.
     * 
     * @return eventoIncidentaleRirs
     */
    public com.hyperborea.sira.ws.EventoIncidentaleRir[] getEventoIncidentaleRirs() {
        return eventoIncidentaleRirs;
    }


    /**
     * Sets the eventoIncidentaleRirs value for this SostanzaUnitaTecnica.
     * 
     * @param eventoIncidentaleRirs
     */
    public void setEventoIncidentaleRirs(com.hyperborea.sira.ws.EventoIncidentaleRir[] eventoIncidentaleRirs) {
        this.eventoIncidentaleRirs = eventoIncidentaleRirs;
    }

    public com.hyperborea.sira.ws.EventoIncidentaleRir getEventoIncidentaleRirs(int i) {
        return this.eventoIncidentaleRirs[i];
    }

    public void setEventoIncidentaleRirs(int i, com.hyperborea.sira.ws.EventoIncidentaleRir _value) {
        this.eventoIncidentaleRirs[i] = _value;
    }


    /**
     * Gets the idSostanza value for this SostanzaUnitaTecnica.
     * 
     * @return idSostanza
     */
    public java.lang.Integer getIdSostanza() {
        return idSostanza;
    }


    /**
     * Sets the idSostanza value for this SostanzaUnitaTecnica.
     * 
     * @param idSostanza
     */
    public void setIdSostanza(java.lang.Integer idSostanza) {
        this.idSostanza = idSostanza;
    }


    /**
     * Gets the quantita value for this SostanzaUnitaTecnica.
     * 
     * @return quantita
     */
    public java.lang.Float getQuantita() {
        return quantita;
    }


    /**
     * Sets the quantita value for this SostanzaUnitaTecnica.
     * 
     * @param quantita
     */
    public void setQuantita(java.lang.Float quantita) {
        this.quantita = quantita;
    }


    /**
     * Gets the scenarioIncidentaleRirs value for this SostanzaUnitaTecnica.
     * 
     * @return scenarioIncidentaleRirs
     */
    public com.hyperborea.sira.ws.ScenarioIncidentaleRir[] getScenarioIncidentaleRirs() {
        return scenarioIncidentaleRirs;
    }


    /**
     * Sets the scenarioIncidentaleRirs value for this SostanzaUnitaTecnica.
     * 
     * @param scenarioIncidentaleRirs
     */
    public void setScenarioIncidentaleRirs(com.hyperborea.sira.ws.ScenarioIncidentaleRir[] scenarioIncidentaleRirs) {
        this.scenarioIncidentaleRirs = scenarioIncidentaleRirs;
    }

    public com.hyperborea.sira.ws.ScenarioIncidentaleRir getScenarioIncidentaleRirs(int i) {
        return this.scenarioIncidentaleRirs[i];
    }

    public void setScenarioIncidentaleRirs(int i, com.hyperborea.sira.ws.ScenarioIncidentaleRir _value) {
        this.scenarioIncidentaleRirs[i] = _value;
    }


    /**
     * Gets the sostanzaRir value for this SostanzaUnitaTecnica.
     * 
     * @return sostanzaRir
     */
    public com.hyperborea.sira.ws.SostanzaRir getSostanzaRir() {
        return sostanzaRir;
    }


    /**
     * Sets the sostanzaRir value for this SostanzaUnitaTecnica.
     * 
     * @param sostanzaRir
     */
    public void setSostanzaRir(com.hyperborea.sira.ws.SostanzaRir sostanzaRir) {
        this.sostanzaRir = sostanzaRir;
    }


    /**
     * Gets the vocTipoQuantita value for this SostanzaUnitaTecnica.
     * 
     * @return vocTipoQuantita
     */
    public com.hyperborea.sira.ws.VocTipoQuantita getVocTipoQuantita() {
        return vocTipoQuantita;
    }


    /**
     * Sets the vocTipoQuantita value for this SostanzaUnitaTecnica.
     * 
     * @param vocTipoQuantita
     */
    public void setVocTipoQuantita(com.hyperborea.sira.ws.VocTipoQuantita vocTipoQuantita) {
        this.vocTipoQuantita = vocTipoQuantita;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SostanzaUnitaTecnica)) return false;
        SostanzaUnitaTecnica other = (SostanzaUnitaTecnica) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.eventoIncidentaleRirs==null && other.getEventoIncidentaleRirs()==null) || 
             (this.eventoIncidentaleRirs!=null &&
              java.util.Arrays.equals(this.eventoIncidentaleRirs, other.getEventoIncidentaleRirs()))) &&
            ((this.idSostanza==null && other.getIdSostanza()==null) || 
             (this.idSostanza!=null &&
              this.idSostanza.equals(other.getIdSostanza()))) &&
            ((this.quantita==null && other.getQuantita()==null) || 
             (this.quantita!=null &&
              this.quantita.equals(other.getQuantita()))) &&
            ((this.scenarioIncidentaleRirs==null && other.getScenarioIncidentaleRirs()==null) || 
             (this.scenarioIncidentaleRirs!=null &&
              java.util.Arrays.equals(this.scenarioIncidentaleRirs, other.getScenarioIncidentaleRirs()))) &&
            ((this.sostanzaRir==null && other.getSostanzaRir()==null) || 
             (this.sostanzaRir!=null &&
              this.sostanzaRir.equals(other.getSostanzaRir()))) &&
            ((this.vocTipoQuantita==null && other.getVocTipoQuantita()==null) || 
             (this.vocTipoQuantita!=null &&
              this.vocTipoQuantita.equals(other.getVocTipoQuantita())));
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
        if (getEventoIncidentaleRirs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEventoIncidentaleRirs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEventoIncidentaleRirs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIdSostanza() != null) {
            _hashCode += getIdSostanza().hashCode();
        }
        if (getQuantita() != null) {
            _hashCode += getQuantita().hashCode();
        }
        if (getScenarioIncidentaleRirs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getScenarioIncidentaleRirs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getScenarioIncidentaleRirs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSostanzaRir() != null) {
            _hashCode += getSostanzaRir().hashCode();
        }
        if (getVocTipoQuantita() != null) {
            _hashCode += getVocTipoQuantita().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SostanzaUnitaTecnica.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostanzaUnitaTecnica"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventoIncidentaleRirs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eventoIncidentaleRirs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "eventoIncidentaleRir"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSostanza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idSostanza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quantita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenarioIncidentaleRirs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scenarioIncidentaleRirs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "scenarioIncidentaleRir"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sostanzaRir");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sostanzaRir"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostanzaRir"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoQuantita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoQuantita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoQuantita"));
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
