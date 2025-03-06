/**
 * ProfiliUtente.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProfiliUtente  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String descrizione;

    private java.lang.Integer idProfilo;

    private com.hyperborea.sira.ws.Permessi[] permessis;

    private com.hyperborea.sira.ws.Utenti[] utentis;

    public ProfiliUtente() {
    }

    public ProfiliUtente(
           java.lang.String descrizione,
           java.lang.Integer idProfilo,
           com.hyperborea.sira.ws.Permessi[] permessis,
           com.hyperborea.sira.ws.Utenti[] utentis) {
        this.descrizione = descrizione;
        this.idProfilo = idProfilo;
        this.permessis = permessis;
        this.utentis = utentis;
    }


    /**
     * Gets the descrizione value for this ProfiliUtente.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this ProfiliUtente.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idProfilo value for this ProfiliUtente.
     * 
     * @return idProfilo
     */
    public java.lang.Integer getIdProfilo() {
        return idProfilo;
    }


    /**
     * Sets the idProfilo value for this ProfiliUtente.
     * 
     * @param idProfilo
     */
    public void setIdProfilo(java.lang.Integer idProfilo) {
        this.idProfilo = idProfilo;
    }


    /**
     * Gets the permessis value for this ProfiliUtente.
     * 
     * @return permessis
     */
    public com.hyperborea.sira.ws.Permessi[] getPermessis() {
        return permessis;
    }


    /**
     * Sets the permessis value for this ProfiliUtente.
     * 
     * @param permessis
     */
    public void setPermessis(com.hyperborea.sira.ws.Permessi[] permessis) {
        this.permessis = permessis;
    }

    public com.hyperborea.sira.ws.Permessi getPermessis(int i) {
        return this.permessis[i];
    }

    public void setPermessis(int i, com.hyperborea.sira.ws.Permessi _value) {
        this.permessis[i] = _value;
    }


    /**
     * Gets the utentis value for this ProfiliUtente.
     * 
     * @return utentis
     */
    public com.hyperborea.sira.ws.Utenti[] getUtentis() {
        return utentis;
    }


    /**
     * Sets the utentis value for this ProfiliUtente.
     * 
     * @param utentis
     */
    public void setUtentis(com.hyperborea.sira.ws.Utenti[] utentis) {
        this.utentis = utentis;
    }

    public com.hyperborea.sira.ws.Utenti getUtentis(int i) {
        return this.utentis[i];
    }

    public void setUtentis(int i, com.hyperborea.sira.ws.Utenti _value) {
        this.utentis[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProfiliUtente)) return false;
        ProfiliUtente other = (ProfiliUtente) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.idProfilo==null && other.getIdProfilo()==null) || 
             (this.idProfilo!=null &&
              this.idProfilo.equals(other.getIdProfilo()))) &&
            ((this.permessis==null && other.getPermessis()==null) || 
             (this.permessis!=null &&
              java.util.Arrays.equals(this.permessis, other.getPermessis()))) &&
            ((this.utentis==null && other.getUtentis()==null) || 
             (this.utentis!=null &&
              java.util.Arrays.equals(this.utentis, other.getUtentis())));
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
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getIdProfilo() != null) {
            _hashCode += getIdProfilo().hashCode();
        }
        if (getPermessis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPermessis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPermessis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUtentis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUtentis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUtentis(), i);
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
        new org.apache.axis.description.TypeDesc(ProfiliUtente.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "profiliUtente"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idProfilo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idProfilo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("permessis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "permessis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "permessi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utentis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utentis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utenti"));
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
