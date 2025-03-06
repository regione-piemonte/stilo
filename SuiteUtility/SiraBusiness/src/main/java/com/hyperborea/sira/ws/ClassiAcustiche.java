/**
 * ClassiAcustiche.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ClassiAcustiche  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String denominazione;

    private java.lang.String descrizione;

    private java.lang.Integer idClasse;

    private float limEmDiurno;

    private float limEmNotturno;

    private float limImDiurno;

    private float limImNotturno;

    private float limQualDiurno;

    private float limQualNotturno;

    public ClassiAcustiche() {
    }

    public ClassiAcustiche(
           java.lang.String denominazione,
           java.lang.String descrizione,
           java.lang.Integer idClasse,
           float limEmDiurno,
           float limEmNotturno,
           float limImDiurno,
           float limImNotturno,
           float limQualDiurno,
           float limQualNotturno) {
        this.denominazione = denominazione;
        this.descrizione = descrizione;
        this.idClasse = idClasse;
        this.limEmDiurno = limEmDiurno;
        this.limEmNotturno = limEmNotturno;
        this.limImDiurno = limImDiurno;
        this.limImNotturno = limImNotturno;
        this.limQualDiurno = limQualDiurno;
        this.limQualNotturno = limQualNotturno;
    }


    /**
     * Gets the denominazione value for this ClassiAcustiche.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this ClassiAcustiche.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the descrizione value for this ClassiAcustiche.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this ClassiAcustiche.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idClasse value for this ClassiAcustiche.
     * 
     * @return idClasse
     */
    public java.lang.Integer getIdClasse() {
        return idClasse;
    }


    /**
     * Sets the idClasse value for this ClassiAcustiche.
     * 
     * @param idClasse
     */
    public void setIdClasse(java.lang.Integer idClasse) {
        this.idClasse = idClasse;
    }


    /**
     * Gets the limEmDiurno value for this ClassiAcustiche.
     * 
     * @return limEmDiurno
     */
    public float getLimEmDiurno() {
        return limEmDiurno;
    }


    /**
     * Sets the limEmDiurno value for this ClassiAcustiche.
     * 
     * @param limEmDiurno
     */
    public void setLimEmDiurno(float limEmDiurno) {
        this.limEmDiurno = limEmDiurno;
    }


    /**
     * Gets the limEmNotturno value for this ClassiAcustiche.
     * 
     * @return limEmNotturno
     */
    public float getLimEmNotturno() {
        return limEmNotturno;
    }


    /**
     * Sets the limEmNotturno value for this ClassiAcustiche.
     * 
     * @param limEmNotturno
     */
    public void setLimEmNotturno(float limEmNotturno) {
        this.limEmNotturno = limEmNotturno;
    }


    /**
     * Gets the limImDiurno value for this ClassiAcustiche.
     * 
     * @return limImDiurno
     */
    public float getLimImDiurno() {
        return limImDiurno;
    }


    /**
     * Sets the limImDiurno value for this ClassiAcustiche.
     * 
     * @param limImDiurno
     */
    public void setLimImDiurno(float limImDiurno) {
        this.limImDiurno = limImDiurno;
    }


    /**
     * Gets the limImNotturno value for this ClassiAcustiche.
     * 
     * @return limImNotturno
     */
    public float getLimImNotturno() {
        return limImNotturno;
    }


    /**
     * Sets the limImNotturno value for this ClassiAcustiche.
     * 
     * @param limImNotturno
     */
    public void setLimImNotturno(float limImNotturno) {
        this.limImNotturno = limImNotturno;
    }


    /**
     * Gets the limQualDiurno value for this ClassiAcustiche.
     * 
     * @return limQualDiurno
     */
    public float getLimQualDiurno() {
        return limQualDiurno;
    }


    /**
     * Sets the limQualDiurno value for this ClassiAcustiche.
     * 
     * @param limQualDiurno
     */
    public void setLimQualDiurno(float limQualDiurno) {
        this.limQualDiurno = limQualDiurno;
    }


    /**
     * Gets the limQualNotturno value for this ClassiAcustiche.
     * 
     * @return limQualNotturno
     */
    public float getLimQualNotturno() {
        return limQualNotturno;
    }


    /**
     * Sets the limQualNotturno value for this ClassiAcustiche.
     * 
     * @param limQualNotturno
     */
    public void setLimQualNotturno(float limQualNotturno) {
        this.limQualNotturno = limQualNotturno;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClassiAcustiche)) return false;
        ClassiAcustiche other = (ClassiAcustiche) obj;
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
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.idClasse==null && other.getIdClasse()==null) || 
             (this.idClasse!=null &&
              this.idClasse.equals(other.getIdClasse()))) &&
            this.limEmDiurno == other.getLimEmDiurno() &&
            this.limEmNotturno == other.getLimEmNotturno() &&
            this.limImDiurno == other.getLimImDiurno() &&
            this.limImNotturno == other.getLimImNotturno() &&
            this.limQualDiurno == other.getLimQualDiurno() &&
            this.limQualNotturno == other.getLimQualNotturno();
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
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getIdClasse() != null) {
            _hashCode += getIdClasse().hashCode();
        }
        _hashCode += new Float(getLimEmDiurno()).hashCode();
        _hashCode += new Float(getLimEmNotturno()).hashCode();
        _hashCode += new Float(getLimImDiurno()).hashCode();
        _hashCode += new Float(getLimImNotturno()).hashCode();
        _hashCode += new Float(getLimQualDiurno()).hashCode();
        _hashCode += new Float(getLimQualNotturno()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClassiAcustiche.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "classiAcustiche"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idClasse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idClasse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limEmDiurno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limEmDiurno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limEmNotturno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limEmNotturno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limImDiurno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limImDiurno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limImNotturno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limImNotturno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limQualDiurno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limQualDiurno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limQualNotturno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limQualNotturno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
