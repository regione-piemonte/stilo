/**
 * SorgAcuRicettori.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class SorgAcuRicettori  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String barriereAcustiche;

    private java.lang.Float distanza;

    private java.lang.Integer idRicettore;

    private java.lang.Float valRumoreStimato;

    private com.hyperborea.sira.ws.VocTipologiaRicettori vocTipoRicettori;

    public SorgAcuRicettori() {
    }

    public SorgAcuRicettori(
           java.lang.String barriereAcustiche,
           java.lang.Float distanza,
           java.lang.Integer idRicettore,
           java.lang.Float valRumoreStimato,
           com.hyperborea.sira.ws.VocTipologiaRicettori vocTipoRicettori) {
        this.barriereAcustiche = barriereAcustiche;
        this.distanza = distanza;
        this.idRicettore = idRicettore;
        this.valRumoreStimato = valRumoreStimato;
        this.vocTipoRicettori = vocTipoRicettori;
    }


    /**
     * Gets the barriereAcustiche value for this SorgAcuRicettori.
     * 
     * @return barriereAcustiche
     */
    public java.lang.String getBarriereAcustiche() {
        return barriereAcustiche;
    }


    /**
     * Sets the barriereAcustiche value for this SorgAcuRicettori.
     * 
     * @param barriereAcustiche
     */
    public void setBarriereAcustiche(java.lang.String barriereAcustiche) {
        this.barriereAcustiche = barriereAcustiche;
    }


    /**
     * Gets the distanza value for this SorgAcuRicettori.
     * 
     * @return distanza
     */
    public java.lang.Float getDistanza() {
        return distanza;
    }


    /**
     * Sets the distanza value for this SorgAcuRicettori.
     * 
     * @param distanza
     */
    public void setDistanza(java.lang.Float distanza) {
        this.distanza = distanza;
    }


    /**
     * Gets the idRicettore value for this SorgAcuRicettori.
     * 
     * @return idRicettore
     */
    public java.lang.Integer getIdRicettore() {
        return idRicettore;
    }


    /**
     * Sets the idRicettore value for this SorgAcuRicettori.
     * 
     * @param idRicettore
     */
    public void setIdRicettore(java.lang.Integer idRicettore) {
        this.idRicettore = idRicettore;
    }


    /**
     * Gets the valRumoreStimato value for this SorgAcuRicettori.
     * 
     * @return valRumoreStimato
     */
    public java.lang.Float getValRumoreStimato() {
        return valRumoreStimato;
    }


    /**
     * Sets the valRumoreStimato value for this SorgAcuRicettori.
     * 
     * @param valRumoreStimato
     */
    public void setValRumoreStimato(java.lang.Float valRumoreStimato) {
        this.valRumoreStimato = valRumoreStimato;
    }


    /**
     * Gets the vocTipoRicettori value for this SorgAcuRicettori.
     * 
     * @return vocTipoRicettori
     */
    public com.hyperborea.sira.ws.VocTipologiaRicettori getVocTipoRicettori() {
        return vocTipoRicettori;
    }


    /**
     * Sets the vocTipoRicettori value for this SorgAcuRicettori.
     * 
     * @param vocTipoRicettori
     */
    public void setVocTipoRicettori(com.hyperborea.sira.ws.VocTipologiaRicettori vocTipoRicettori) {
        this.vocTipoRicettori = vocTipoRicettori;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SorgAcuRicettori)) return false;
        SorgAcuRicettori other = (SorgAcuRicettori) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.barriereAcustiche==null && other.getBarriereAcustiche()==null) || 
             (this.barriereAcustiche!=null &&
              this.barriereAcustiche.equals(other.getBarriereAcustiche()))) &&
            ((this.distanza==null && other.getDistanza()==null) || 
             (this.distanza!=null &&
              this.distanza.equals(other.getDistanza()))) &&
            ((this.idRicettore==null && other.getIdRicettore()==null) || 
             (this.idRicettore!=null &&
              this.idRicettore.equals(other.getIdRicettore()))) &&
            ((this.valRumoreStimato==null && other.getValRumoreStimato()==null) || 
             (this.valRumoreStimato!=null &&
              this.valRumoreStimato.equals(other.getValRumoreStimato()))) &&
            ((this.vocTipoRicettori==null && other.getVocTipoRicettori()==null) || 
             (this.vocTipoRicettori!=null &&
              this.vocTipoRicettori.equals(other.getVocTipoRicettori())));
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
        if (getBarriereAcustiche() != null) {
            _hashCode += getBarriereAcustiche().hashCode();
        }
        if (getDistanza() != null) {
            _hashCode += getDistanza().hashCode();
        }
        if (getIdRicettore() != null) {
            _hashCode += getIdRicettore().hashCode();
        }
        if (getValRumoreStimato() != null) {
            _hashCode += getValRumoreStimato().hashCode();
        }
        if (getVocTipoRicettori() != null) {
            _hashCode += getVocTipoRicettori().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SorgAcuRicettori.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgAcuRicettori"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("barriereAcustiche");
        elemField.setXmlName(new javax.xml.namespace.QName("", "barriereAcustiche"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("distanza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "distanza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idRicettore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idRicettore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valRumoreStimato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valRumoreStimato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoRicettori");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoRicettori"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaRicettori"));
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
