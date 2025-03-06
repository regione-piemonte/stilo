/**
 * PuntiLuce.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class PuntiLuce  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idPuntoLuce;

    private java.lang.String modelloOttica;

    private java.lang.Float potenzaLampada;

    private java.lang.Float potenzaLampadaPre;

    private java.lang.Float riduzioneFlussoLuminoso;

    private com.hyperborea.sira.ws.VocTipologiaLampade vocTipologiaLampade;

    public PuntiLuce() {
    }

    public PuntiLuce(
           java.lang.Integer idPuntoLuce,
           java.lang.String modelloOttica,
           java.lang.Float potenzaLampada,
           java.lang.Float potenzaLampadaPre,
           java.lang.Float riduzioneFlussoLuminoso,
           com.hyperborea.sira.ws.VocTipologiaLampade vocTipologiaLampade) {
        this.idPuntoLuce = idPuntoLuce;
        this.modelloOttica = modelloOttica;
        this.potenzaLampada = potenzaLampada;
        this.potenzaLampadaPre = potenzaLampadaPre;
        this.riduzioneFlussoLuminoso = riduzioneFlussoLuminoso;
        this.vocTipologiaLampade = vocTipologiaLampade;
    }


    /**
     * Gets the idPuntoLuce value for this PuntiLuce.
     * 
     * @return idPuntoLuce
     */
    public java.lang.Integer getIdPuntoLuce() {
        return idPuntoLuce;
    }


    /**
     * Sets the idPuntoLuce value for this PuntiLuce.
     * 
     * @param idPuntoLuce
     */
    public void setIdPuntoLuce(java.lang.Integer idPuntoLuce) {
        this.idPuntoLuce = idPuntoLuce;
    }


    /**
     * Gets the modelloOttica value for this PuntiLuce.
     * 
     * @return modelloOttica
     */
    public java.lang.String getModelloOttica() {
        return modelloOttica;
    }


    /**
     * Sets the modelloOttica value for this PuntiLuce.
     * 
     * @param modelloOttica
     */
    public void setModelloOttica(java.lang.String modelloOttica) {
        this.modelloOttica = modelloOttica;
    }


    /**
     * Gets the potenzaLampada value for this PuntiLuce.
     * 
     * @return potenzaLampada
     */
    public java.lang.Float getPotenzaLampada() {
        return potenzaLampada;
    }


    /**
     * Sets the potenzaLampada value for this PuntiLuce.
     * 
     * @param potenzaLampada
     */
    public void setPotenzaLampada(java.lang.Float potenzaLampada) {
        this.potenzaLampada = potenzaLampada;
    }


    /**
     * Gets the potenzaLampadaPre value for this PuntiLuce.
     * 
     * @return potenzaLampadaPre
     */
    public java.lang.Float getPotenzaLampadaPre() {
        return potenzaLampadaPre;
    }


    /**
     * Sets the potenzaLampadaPre value for this PuntiLuce.
     * 
     * @param potenzaLampadaPre
     */
    public void setPotenzaLampadaPre(java.lang.Float potenzaLampadaPre) {
        this.potenzaLampadaPre = potenzaLampadaPre;
    }


    /**
     * Gets the riduzioneFlussoLuminoso value for this PuntiLuce.
     * 
     * @return riduzioneFlussoLuminoso
     */
    public java.lang.Float getRiduzioneFlussoLuminoso() {
        return riduzioneFlussoLuminoso;
    }


    /**
     * Sets the riduzioneFlussoLuminoso value for this PuntiLuce.
     * 
     * @param riduzioneFlussoLuminoso
     */
    public void setRiduzioneFlussoLuminoso(java.lang.Float riduzioneFlussoLuminoso) {
        this.riduzioneFlussoLuminoso = riduzioneFlussoLuminoso;
    }


    /**
     * Gets the vocTipologiaLampade value for this PuntiLuce.
     * 
     * @return vocTipologiaLampade
     */
    public com.hyperborea.sira.ws.VocTipologiaLampade getVocTipologiaLampade() {
        return vocTipologiaLampade;
    }


    /**
     * Sets the vocTipologiaLampade value for this PuntiLuce.
     * 
     * @param vocTipologiaLampade
     */
    public void setVocTipologiaLampade(com.hyperborea.sira.ws.VocTipologiaLampade vocTipologiaLampade) {
        this.vocTipologiaLampade = vocTipologiaLampade;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PuntiLuce)) return false;
        PuntiLuce other = (PuntiLuce) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idPuntoLuce==null && other.getIdPuntoLuce()==null) || 
             (this.idPuntoLuce!=null &&
              this.idPuntoLuce.equals(other.getIdPuntoLuce()))) &&
            ((this.modelloOttica==null && other.getModelloOttica()==null) || 
             (this.modelloOttica!=null &&
              this.modelloOttica.equals(other.getModelloOttica()))) &&
            ((this.potenzaLampada==null && other.getPotenzaLampada()==null) || 
             (this.potenzaLampada!=null &&
              this.potenzaLampada.equals(other.getPotenzaLampada()))) &&
            ((this.potenzaLampadaPre==null && other.getPotenzaLampadaPre()==null) || 
             (this.potenzaLampadaPre!=null &&
              this.potenzaLampadaPre.equals(other.getPotenzaLampadaPre()))) &&
            ((this.riduzioneFlussoLuminoso==null && other.getRiduzioneFlussoLuminoso()==null) || 
             (this.riduzioneFlussoLuminoso!=null &&
              this.riduzioneFlussoLuminoso.equals(other.getRiduzioneFlussoLuminoso()))) &&
            ((this.vocTipologiaLampade==null && other.getVocTipologiaLampade()==null) || 
             (this.vocTipologiaLampade!=null &&
              this.vocTipologiaLampade.equals(other.getVocTipologiaLampade())));
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
        if (getIdPuntoLuce() != null) {
            _hashCode += getIdPuntoLuce().hashCode();
        }
        if (getModelloOttica() != null) {
            _hashCode += getModelloOttica().hashCode();
        }
        if (getPotenzaLampada() != null) {
            _hashCode += getPotenzaLampada().hashCode();
        }
        if (getPotenzaLampadaPre() != null) {
            _hashCode += getPotenzaLampadaPre().hashCode();
        }
        if (getRiduzioneFlussoLuminoso() != null) {
            _hashCode += getRiduzioneFlussoLuminoso().hashCode();
        }
        if (getVocTipologiaLampade() != null) {
            _hashCode += getVocTipologiaLampade().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PuntiLuce.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "puntiLuce"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPuntoLuce");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idPuntoLuce"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modelloOttica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modelloOttica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzaLampada");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzaLampada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzaLampadaPre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzaLampadaPre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riduzioneFlussoLuminoso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riduzioneFlussoLuminoso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaLampade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaLampade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaLampade"));
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
