/**
 * Misure.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Misure  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idMisura;

    private java.lang.Float incertezza;

    private com.hyperborea.sira.ws.MisuraAnalitica misuraAnalitica;

    private java.util.Calendar tempoFine;

    private java.util.Calendar tempoInizio;

    private java.lang.Float valoreNum;

    private java.lang.String valoreStr;

    private com.hyperborea.sira.ws.WsTipiMisureRef wsTipiMisureRef;

    public Misure() {
    }

    public Misure(
           java.lang.Integer idMisura,
           java.lang.Float incertezza,
           com.hyperborea.sira.ws.MisuraAnalitica misuraAnalitica,
           java.util.Calendar tempoFine,
           java.util.Calendar tempoInizio,
           java.lang.Float valoreNum,
           java.lang.String valoreStr,
           com.hyperborea.sira.ws.WsTipiMisureRef wsTipiMisureRef) {
        this.idMisura = idMisura;
        this.incertezza = incertezza;
        this.misuraAnalitica = misuraAnalitica;
        this.tempoFine = tempoFine;
        this.tempoInizio = tempoInizio;
        this.valoreNum = valoreNum;
        this.valoreStr = valoreStr;
        this.wsTipiMisureRef = wsTipiMisureRef;
    }


    /**
     * Gets the idMisura value for this Misure.
     * 
     * @return idMisura
     */
    public java.lang.Integer getIdMisura() {
        return idMisura;
    }


    /**
     * Sets the idMisura value for this Misure.
     * 
     * @param idMisura
     */
    public void setIdMisura(java.lang.Integer idMisura) {
        this.idMisura = idMisura;
    }


    /**
     * Gets the incertezza value for this Misure.
     * 
     * @return incertezza
     */
    public java.lang.Float getIncertezza() {
        return incertezza;
    }


    /**
     * Sets the incertezza value for this Misure.
     * 
     * @param incertezza
     */
    public void setIncertezza(java.lang.Float incertezza) {
        this.incertezza = incertezza;
    }


    /**
     * Gets the misuraAnalitica value for this Misure.
     * 
     * @return misuraAnalitica
     */
    public com.hyperborea.sira.ws.MisuraAnalitica getMisuraAnalitica() {
        return misuraAnalitica;
    }


    /**
     * Sets the misuraAnalitica value for this Misure.
     * 
     * @param misuraAnalitica
     */
    public void setMisuraAnalitica(com.hyperborea.sira.ws.MisuraAnalitica misuraAnalitica) {
        this.misuraAnalitica = misuraAnalitica;
    }


    /**
     * Gets the tempoFine value for this Misure.
     * 
     * @return tempoFine
     */
    public java.util.Calendar getTempoFine() {
        return tempoFine;
    }


    /**
     * Sets the tempoFine value for this Misure.
     * 
     * @param tempoFine
     */
    public void setTempoFine(java.util.Calendar tempoFine) {
        this.tempoFine = tempoFine;
    }


    /**
     * Gets the tempoInizio value for this Misure.
     * 
     * @return tempoInizio
     */
    public java.util.Calendar getTempoInizio() {
        return tempoInizio;
    }


    /**
     * Sets the tempoInizio value for this Misure.
     * 
     * @param tempoInizio
     */
    public void setTempoInizio(java.util.Calendar tempoInizio) {
        this.tempoInizio = tempoInizio;
    }


    /**
     * Gets the valoreNum value for this Misure.
     * 
     * @return valoreNum
     */
    public java.lang.Float getValoreNum() {
        return valoreNum;
    }


    /**
     * Sets the valoreNum value for this Misure.
     * 
     * @param valoreNum
     */
    public void setValoreNum(java.lang.Float valoreNum) {
        this.valoreNum = valoreNum;
    }


    /**
     * Gets the valoreStr value for this Misure.
     * 
     * @return valoreStr
     */
    public java.lang.String getValoreStr() {
        return valoreStr;
    }


    /**
     * Sets the valoreStr value for this Misure.
     * 
     * @param valoreStr
     */
    public void setValoreStr(java.lang.String valoreStr) {
        this.valoreStr = valoreStr;
    }


    /**
     * Gets the wsTipiMisureRef value for this Misure.
     * 
     * @return wsTipiMisureRef
     */
    public com.hyperborea.sira.ws.WsTipiMisureRef getWsTipiMisureRef() {
        return wsTipiMisureRef;
    }


    /**
     * Sets the wsTipiMisureRef value for this Misure.
     * 
     * @param wsTipiMisureRef
     */
    public void setWsTipiMisureRef(com.hyperborea.sira.ws.WsTipiMisureRef wsTipiMisureRef) {
        this.wsTipiMisureRef = wsTipiMisureRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Misure)) return false;
        Misure other = (Misure) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idMisura==null && other.getIdMisura()==null) || 
             (this.idMisura!=null &&
              this.idMisura.equals(other.getIdMisura()))) &&
            ((this.incertezza==null && other.getIncertezza()==null) || 
             (this.incertezza!=null &&
              this.incertezza.equals(other.getIncertezza()))) &&
            ((this.misuraAnalitica==null && other.getMisuraAnalitica()==null) || 
             (this.misuraAnalitica!=null &&
              this.misuraAnalitica.equals(other.getMisuraAnalitica()))) &&
            ((this.tempoFine==null && other.getTempoFine()==null) || 
             (this.tempoFine!=null &&
              this.tempoFine.equals(other.getTempoFine()))) &&
            ((this.tempoInizio==null && other.getTempoInizio()==null) || 
             (this.tempoInizio!=null &&
              this.tempoInizio.equals(other.getTempoInizio()))) &&
            ((this.valoreNum==null && other.getValoreNum()==null) || 
             (this.valoreNum!=null &&
              this.valoreNum.equals(other.getValoreNum()))) &&
            ((this.valoreStr==null && other.getValoreStr()==null) || 
             (this.valoreStr!=null &&
              this.valoreStr.equals(other.getValoreStr()))) &&
            ((this.wsTipiMisureRef==null && other.getWsTipiMisureRef()==null) || 
             (this.wsTipiMisureRef!=null &&
              this.wsTipiMisureRef.equals(other.getWsTipiMisureRef())));
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
        if (getIdMisura() != null) {
            _hashCode += getIdMisura().hashCode();
        }
        if (getIncertezza() != null) {
            _hashCode += getIncertezza().hashCode();
        }
        if (getMisuraAnalitica() != null) {
            _hashCode += getMisuraAnalitica().hashCode();
        }
        if (getTempoFine() != null) {
            _hashCode += getTempoFine().hashCode();
        }
        if (getTempoInizio() != null) {
            _hashCode += getTempoInizio().hashCode();
        }
        if (getValoreNum() != null) {
            _hashCode += getValoreNum().hashCode();
        }
        if (getValoreStr() != null) {
            _hashCode += getValoreStr().hashCode();
        }
        if (getWsTipiMisureRef() != null) {
            _hashCode += getWsTipiMisureRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Misure.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "misure"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMisura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idMisura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incertezza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "incertezza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("misuraAnalitica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "misuraAnalitica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "misuraAnalitica"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tempoFine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tempoFine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tempoInizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tempoInizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valoreNum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valoreNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valoreStr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valoreStr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsTipiMisureRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsTipiMisureRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTipiMisureRef"));
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
