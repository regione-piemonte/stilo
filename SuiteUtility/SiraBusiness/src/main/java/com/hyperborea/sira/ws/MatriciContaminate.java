/**
 * MatriciContaminate.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class MatriciContaminate  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String descrizioneRilevatore;

    private com.hyperborea.sira.ws.ScFaseAccertamento faseAccertamento;

    private java.lang.Integer idMatrice;

    private com.hyperborea.sira.ws.ScTipoMatrice idTipoMatrice;

    private com.hyperborea.sira.ws.SostanzeRilevate[] sostanzeRilevates;

    private java.lang.String tipoRilevatore;

    public MatriciContaminate() {
    }

    public MatriciContaminate(
           java.lang.String descrizioneRilevatore,
           com.hyperborea.sira.ws.ScFaseAccertamento faseAccertamento,
           java.lang.Integer idMatrice,
           com.hyperborea.sira.ws.ScTipoMatrice idTipoMatrice,
           com.hyperborea.sira.ws.SostanzeRilevate[] sostanzeRilevates,
           java.lang.String tipoRilevatore) {
        this.descrizioneRilevatore = descrizioneRilevatore;
        this.faseAccertamento = faseAccertamento;
        this.idMatrice = idMatrice;
        this.idTipoMatrice = idTipoMatrice;
        this.sostanzeRilevates = sostanzeRilevates;
        this.tipoRilevatore = tipoRilevatore;
    }


    /**
     * Gets the descrizioneRilevatore value for this MatriciContaminate.
     * 
     * @return descrizioneRilevatore
     */
    public java.lang.String getDescrizioneRilevatore() {
        return descrizioneRilevatore;
    }


    /**
     * Sets the descrizioneRilevatore value for this MatriciContaminate.
     * 
     * @param descrizioneRilevatore
     */
    public void setDescrizioneRilevatore(java.lang.String descrizioneRilevatore) {
        this.descrizioneRilevatore = descrizioneRilevatore;
    }


    /**
     * Gets the faseAccertamento value for this MatriciContaminate.
     * 
     * @return faseAccertamento
     */
    public com.hyperborea.sira.ws.ScFaseAccertamento getFaseAccertamento() {
        return faseAccertamento;
    }


    /**
     * Sets the faseAccertamento value for this MatriciContaminate.
     * 
     * @param faseAccertamento
     */
    public void setFaseAccertamento(com.hyperborea.sira.ws.ScFaseAccertamento faseAccertamento) {
        this.faseAccertamento = faseAccertamento;
    }


    /**
     * Gets the idMatrice value for this MatriciContaminate.
     * 
     * @return idMatrice
     */
    public java.lang.Integer getIdMatrice() {
        return idMatrice;
    }


    /**
     * Sets the idMatrice value for this MatriciContaminate.
     * 
     * @param idMatrice
     */
    public void setIdMatrice(java.lang.Integer idMatrice) {
        this.idMatrice = idMatrice;
    }


    /**
     * Gets the idTipoMatrice value for this MatriciContaminate.
     * 
     * @return idTipoMatrice
     */
    public com.hyperborea.sira.ws.ScTipoMatrice getIdTipoMatrice() {
        return idTipoMatrice;
    }


    /**
     * Sets the idTipoMatrice value for this MatriciContaminate.
     * 
     * @param idTipoMatrice
     */
    public void setIdTipoMatrice(com.hyperborea.sira.ws.ScTipoMatrice idTipoMatrice) {
        this.idTipoMatrice = idTipoMatrice;
    }


    /**
     * Gets the sostanzeRilevates value for this MatriciContaminate.
     * 
     * @return sostanzeRilevates
     */
    public com.hyperborea.sira.ws.SostanzeRilevate[] getSostanzeRilevates() {
        return sostanzeRilevates;
    }


    /**
     * Sets the sostanzeRilevates value for this MatriciContaminate.
     * 
     * @param sostanzeRilevates
     */
    public void setSostanzeRilevates(com.hyperborea.sira.ws.SostanzeRilevate[] sostanzeRilevates) {
        this.sostanzeRilevates = sostanzeRilevates;
    }

    public com.hyperborea.sira.ws.SostanzeRilevate getSostanzeRilevates(int i) {
        return this.sostanzeRilevates[i];
    }

    public void setSostanzeRilevates(int i, com.hyperborea.sira.ws.SostanzeRilevate _value) {
        this.sostanzeRilevates[i] = _value;
    }


    /**
     * Gets the tipoRilevatore value for this MatriciContaminate.
     * 
     * @return tipoRilevatore
     */
    public java.lang.String getTipoRilevatore() {
        return tipoRilevatore;
    }


    /**
     * Sets the tipoRilevatore value for this MatriciContaminate.
     * 
     * @param tipoRilevatore
     */
    public void setTipoRilevatore(java.lang.String tipoRilevatore) {
        this.tipoRilevatore = tipoRilevatore;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MatriciContaminate)) return false;
        MatriciContaminate other = (MatriciContaminate) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.descrizioneRilevatore==null && other.getDescrizioneRilevatore()==null) || 
             (this.descrizioneRilevatore!=null &&
              this.descrizioneRilevatore.equals(other.getDescrizioneRilevatore()))) &&
            ((this.faseAccertamento==null && other.getFaseAccertamento()==null) || 
             (this.faseAccertamento!=null &&
              this.faseAccertamento.equals(other.getFaseAccertamento()))) &&
            ((this.idMatrice==null && other.getIdMatrice()==null) || 
             (this.idMatrice!=null &&
              this.idMatrice.equals(other.getIdMatrice()))) &&
            ((this.idTipoMatrice==null && other.getIdTipoMatrice()==null) || 
             (this.idTipoMatrice!=null &&
              this.idTipoMatrice.equals(other.getIdTipoMatrice()))) &&
            ((this.sostanzeRilevates==null && other.getSostanzeRilevates()==null) || 
             (this.sostanzeRilevates!=null &&
              java.util.Arrays.equals(this.sostanzeRilevates, other.getSostanzeRilevates()))) &&
            ((this.tipoRilevatore==null && other.getTipoRilevatore()==null) || 
             (this.tipoRilevatore!=null &&
              this.tipoRilevatore.equals(other.getTipoRilevatore())));
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
        if (getDescrizioneRilevatore() != null) {
            _hashCode += getDescrizioneRilevatore().hashCode();
        }
        if (getFaseAccertamento() != null) {
            _hashCode += getFaseAccertamento().hashCode();
        }
        if (getIdMatrice() != null) {
            _hashCode += getIdMatrice().hashCode();
        }
        if (getIdTipoMatrice() != null) {
            _hashCode += getIdTipoMatrice().hashCode();
        }
        if (getSostanzeRilevates() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSostanzeRilevates());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSostanzeRilevates(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTipoRilevatore() != null) {
            _hashCode += getTipoRilevatore().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MatriciContaminate.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "matriciContaminate"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneRilevatore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneRilevatore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faseAccertamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "faseAccertamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "scFaseAccertamento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMatrice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idMatrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTipoMatrice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTipoMatrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "scTipoMatrice"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sostanzeRilevates");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sostanzeRilevates"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostanzeRilevate"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoRilevatore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoRilevatore"));
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
