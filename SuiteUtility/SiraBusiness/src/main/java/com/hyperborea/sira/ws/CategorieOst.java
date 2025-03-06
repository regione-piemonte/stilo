/**
 * CategorieOst.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CategorieOst  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer codice;

    private java.lang.String descrizione;

    private org.apache.axis.types.UnsignedShort tipo;

    private java.lang.Integer tipoCategoria;

    private java.lang.String tipoCategoriaLabel;

    private java.lang.String tipoLabel;

    public CategorieOst() {
    }

    public CategorieOst(
           java.lang.Integer codice,
           java.lang.String descrizione,
           org.apache.axis.types.UnsignedShort tipo,
           java.lang.Integer tipoCategoria,
           java.lang.String tipoCategoriaLabel,
           java.lang.String tipoLabel) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.tipo = tipo;
        this.tipoCategoria = tipoCategoria;
        this.tipoCategoriaLabel = tipoCategoriaLabel;
        this.tipoLabel = tipoLabel;
    }


    /**
     * Gets the codice value for this CategorieOst.
     * 
     * @return codice
     */
    public java.lang.Integer getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this CategorieOst.
     * 
     * @param codice
     */
    public void setCodice(java.lang.Integer codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this CategorieOst.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this CategorieOst.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the tipo value for this CategorieOst.
     * 
     * @return tipo
     */
    public org.apache.axis.types.UnsignedShort getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this CategorieOst.
     * 
     * @param tipo
     */
    public void setTipo(org.apache.axis.types.UnsignedShort tipo) {
        this.tipo = tipo;
    }


    /**
     * Gets the tipoCategoria value for this CategorieOst.
     * 
     * @return tipoCategoria
     */
    public java.lang.Integer getTipoCategoria() {
        return tipoCategoria;
    }


    /**
     * Sets the tipoCategoria value for this CategorieOst.
     * 
     * @param tipoCategoria
     */
    public void setTipoCategoria(java.lang.Integer tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }


    /**
     * Gets the tipoCategoriaLabel value for this CategorieOst.
     * 
     * @return tipoCategoriaLabel
     */
    public java.lang.String getTipoCategoriaLabel() {
        return tipoCategoriaLabel;
    }


    /**
     * Sets the tipoCategoriaLabel value for this CategorieOst.
     * 
     * @param tipoCategoriaLabel
     */
    public void setTipoCategoriaLabel(java.lang.String tipoCategoriaLabel) {
        this.tipoCategoriaLabel = tipoCategoriaLabel;
    }


    /**
     * Gets the tipoLabel value for this CategorieOst.
     * 
     * @return tipoLabel
     */
    public java.lang.String getTipoLabel() {
        return tipoLabel;
    }


    /**
     * Sets the tipoLabel value for this CategorieOst.
     * 
     * @param tipoLabel
     */
    public void setTipoLabel(java.lang.String tipoLabel) {
        this.tipoLabel = tipoLabel;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CategorieOst)) return false;
        CategorieOst other = (CategorieOst) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.tipo==null && other.getTipo()==null) || 
             (this.tipo!=null &&
              this.tipo.equals(other.getTipo()))) &&
            ((this.tipoCategoria==null && other.getTipoCategoria()==null) || 
             (this.tipoCategoria!=null &&
              this.tipoCategoria.equals(other.getTipoCategoria()))) &&
            ((this.tipoCategoriaLabel==null && other.getTipoCategoriaLabel()==null) || 
             (this.tipoCategoriaLabel!=null &&
              this.tipoCategoriaLabel.equals(other.getTipoCategoriaLabel()))) &&
            ((this.tipoLabel==null && other.getTipoLabel()==null) || 
             (this.tipoLabel!=null &&
              this.tipoLabel.equals(other.getTipoLabel())));
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
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getTipo() != null) {
            _hashCode += getTipo().hashCode();
        }
        if (getTipoCategoria() != null) {
            _hashCode += getTipoCategoria().hashCode();
        }
        if (getTipoCategoriaLabel() != null) {
            _hashCode += getTipoCategoriaLabel().hashCode();
        }
        if (getTipoLabel() != null) {
            _hashCode += getTipoLabel().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CategorieOst.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "categorieOst"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoCategoria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoCategoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoCategoriaLabel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoCategoriaLabel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoLabel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoLabel"));
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
