/**
 * SezioniSchedaMon.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class SezioniSchedaMon  implements java.io.Serializable {
    private java.lang.String codVista;

    private java.lang.Integer idSezione;

    private java.math.BigInteger ordinePagina;

    private java.lang.String titolo;

    private java.math.BigInteger visibile;

    public SezioniSchedaMon() {
    }

    public SezioniSchedaMon(
           java.lang.String codVista,
           java.lang.Integer idSezione,
           java.math.BigInteger ordinePagina,
           java.lang.String titolo,
           java.math.BigInteger visibile) {
           this.codVista = codVista;
           this.idSezione = idSezione;
           this.ordinePagina = ordinePagina;
           this.titolo = titolo;
           this.visibile = visibile;
    }


    /**
     * Gets the codVista value for this SezioniSchedaMon.
     * 
     * @return codVista
     */
    public java.lang.String getCodVista() {
        return codVista;
    }


    /**
     * Sets the codVista value for this SezioniSchedaMon.
     * 
     * @param codVista
     */
    public void setCodVista(java.lang.String codVista) {
        this.codVista = codVista;
    }


    /**
     * Gets the idSezione value for this SezioniSchedaMon.
     * 
     * @return idSezione
     */
    public java.lang.Integer getIdSezione() {
        return idSezione;
    }


    /**
     * Sets the idSezione value for this SezioniSchedaMon.
     * 
     * @param idSezione
     */
    public void setIdSezione(java.lang.Integer idSezione) {
        this.idSezione = idSezione;
    }


    /**
     * Gets the ordinePagina value for this SezioniSchedaMon.
     * 
     * @return ordinePagina
     */
    public java.math.BigInteger getOrdinePagina() {
        return ordinePagina;
    }


    /**
     * Sets the ordinePagina value for this SezioniSchedaMon.
     * 
     * @param ordinePagina
     */
    public void setOrdinePagina(java.math.BigInteger ordinePagina) {
        this.ordinePagina = ordinePagina;
    }


    /**
     * Gets the titolo value for this SezioniSchedaMon.
     * 
     * @return titolo
     */
    public java.lang.String getTitolo() {
        return titolo;
    }


    /**
     * Sets the titolo value for this SezioniSchedaMon.
     * 
     * @param titolo
     */
    public void setTitolo(java.lang.String titolo) {
        this.titolo = titolo;
    }


    /**
     * Gets the visibile value for this SezioniSchedaMon.
     * 
     * @return visibile
     */
    public java.math.BigInteger getVisibile() {
        return visibile;
    }


    /**
     * Sets the visibile value for this SezioniSchedaMon.
     * 
     * @param visibile
     */
    public void setVisibile(java.math.BigInteger visibile) {
        this.visibile = visibile;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SezioniSchedaMon)) return false;
        SezioniSchedaMon other = (SezioniSchedaMon) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codVista==null && other.getCodVista()==null) || 
             (this.codVista!=null &&
              this.codVista.equals(other.getCodVista()))) &&
            ((this.idSezione==null && other.getIdSezione()==null) || 
             (this.idSezione!=null &&
              this.idSezione.equals(other.getIdSezione()))) &&
            ((this.ordinePagina==null && other.getOrdinePagina()==null) || 
             (this.ordinePagina!=null &&
              this.ordinePagina.equals(other.getOrdinePagina()))) &&
            ((this.titolo==null && other.getTitolo()==null) || 
             (this.titolo!=null &&
              this.titolo.equals(other.getTitolo()))) &&
            ((this.visibile==null && other.getVisibile()==null) || 
             (this.visibile!=null &&
              this.visibile.equals(other.getVisibile())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCodVista() != null) {
            _hashCode += getCodVista().hashCode();
        }
        if (getIdSezione() != null) {
            _hashCode += getIdSezione().hashCode();
        }
        if (getOrdinePagina() != null) {
            _hashCode += getOrdinePagina().hashCode();
        }
        if (getTitolo() != null) {
            _hashCode += getTitolo().hashCode();
        }
        if (getVisibile() != null) {
            _hashCode += getVisibile().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SezioniSchedaMon.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sezioniSchedaMon"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codVista");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codVista"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSezione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idSezione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ordinePagina");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ordinePagina"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "titolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("visibile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "visibile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
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
