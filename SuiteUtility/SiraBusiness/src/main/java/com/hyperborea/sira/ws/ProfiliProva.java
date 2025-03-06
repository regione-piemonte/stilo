/**
 * ProfiliProva.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProfiliProva  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String denominazione;

    private java.lang.Integer idPp;

    private com.hyperborea.sira.ws.TipimisureProfiliprova[] tipimisureProfiliprovas;

    public ProfiliProva() {
    }

    public ProfiliProva(
           java.lang.String denominazione,
           java.lang.Integer idPp,
           com.hyperborea.sira.ws.TipimisureProfiliprova[] tipimisureProfiliprovas) {
        this.denominazione = denominazione;
        this.idPp = idPp;
        this.tipimisureProfiliprovas = tipimisureProfiliprovas;
    }


    /**
     * Gets the denominazione value for this ProfiliProva.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this ProfiliProva.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the idPp value for this ProfiliProva.
     * 
     * @return idPp
     */
    public java.lang.Integer getIdPp() {
        return idPp;
    }


    /**
     * Sets the idPp value for this ProfiliProva.
     * 
     * @param idPp
     */
    public void setIdPp(java.lang.Integer idPp) {
        this.idPp = idPp;
    }


    /**
     * Gets the tipimisureProfiliprovas value for this ProfiliProva.
     * 
     * @return tipimisureProfiliprovas
     */
    public com.hyperborea.sira.ws.TipimisureProfiliprova[] getTipimisureProfiliprovas() {
        return tipimisureProfiliprovas;
    }


    /**
     * Sets the tipimisureProfiliprovas value for this ProfiliProva.
     * 
     * @param tipimisureProfiliprovas
     */
    public void setTipimisureProfiliprovas(com.hyperborea.sira.ws.TipimisureProfiliprova[] tipimisureProfiliprovas) {
        this.tipimisureProfiliprovas = tipimisureProfiliprovas;
    }

    public com.hyperborea.sira.ws.TipimisureProfiliprova getTipimisureProfiliprovas(int i) {
        return this.tipimisureProfiliprovas[i];
    }

    public void setTipimisureProfiliprovas(int i, com.hyperborea.sira.ws.TipimisureProfiliprova _value) {
        this.tipimisureProfiliprovas[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProfiliProva)) return false;
        ProfiliProva other = (ProfiliProva) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.denominazione==null && other.getDenominazione()==null) || 
             (this.denominazione!=null &&
              this.denominazione.equals(other.getDenominazione()))) &&
            ((this.idPp==null && other.getIdPp()==null) || 
             (this.idPp!=null &&
              this.idPp.equals(other.getIdPp()))) &&
            ((this.tipimisureProfiliprovas==null && other.getTipimisureProfiliprovas()==null) || 
             (this.tipimisureProfiliprovas!=null &&
              java.util.Arrays.equals(this.tipimisureProfiliprovas, other.getTipimisureProfiliprovas())));
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
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
        }
        if (getIdPp() != null) {
            _hashCode += getIdPp().hashCode();
        }
        if (getTipimisureProfiliprovas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTipimisureProfiliprovas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTipimisureProfiliprovas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProfiliProva.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "profiliProva"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idPp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipimisureProfiliprovas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipimisureProfiliprovas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipimisureProfiliprova"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
