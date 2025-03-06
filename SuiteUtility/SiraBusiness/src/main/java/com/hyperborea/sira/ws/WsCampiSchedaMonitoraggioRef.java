/**
 * WsCampiSchedaMonitoraggioRef.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsCampiSchedaMonitoraggioRef  implements java.io.Serializable {
    private java.lang.Integer idCampo;

    private java.lang.String nomeCampo;

    private java.lang.String nomeVocabolario;

    private java.lang.String searchType;

    public WsCampiSchedaMonitoraggioRef() {
    }

    public WsCampiSchedaMonitoraggioRef(
           java.lang.Integer idCampo,
           java.lang.String nomeCampo,
           java.lang.String nomeVocabolario,
           java.lang.String searchType) {
           this.idCampo = idCampo;
           this.nomeCampo = nomeCampo;
           this.nomeVocabolario = nomeVocabolario;
           this.searchType = searchType;
    }


    /**
     * Gets the idCampo value for this WsCampiSchedaMonitoraggioRef.
     * 
     * @return idCampo
     */
    public java.lang.Integer getIdCampo() {
        return idCampo;
    }


    /**
     * Sets the idCampo value for this WsCampiSchedaMonitoraggioRef.
     * 
     * @param idCampo
     */
    public void setIdCampo(java.lang.Integer idCampo) {
        this.idCampo = idCampo;
    }


    /**
     * Gets the nomeCampo value for this WsCampiSchedaMonitoraggioRef.
     * 
     * @return nomeCampo
     */
    public java.lang.String getNomeCampo() {
        return nomeCampo;
    }


    /**
     * Sets the nomeCampo value for this WsCampiSchedaMonitoraggioRef.
     * 
     * @param nomeCampo
     */
    public void setNomeCampo(java.lang.String nomeCampo) {
        this.nomeCampo = nomeCampo;
    }


    /**
     * Gets the nomeVocabolario value for this WsCampiSchedaMonitoraggioRef.
     * 
     * @return nomeVocabolario
     */
    public java.lang.String getNomeVocabolario() {
        return nomeVocabolario;
    }


    /**
     * Sets the nomeVocabolario value for this WsCampiSchedaMonitoraggioRef.
     * 
     * @param nomeVocabolario
     */
    public void setNomeVocabolario(java.lang.String nomeVocabolario) {
        this.nomeVocabolario = nomeVocabolario;
    }


    /**
     * Gets the searchType value for this WsCampiSchedaMonitoraggioRef.
     * 
     * @return searchType
     */
    public java.lang.String getSearchType() {
        return searchType;
    }


    /**
     * Sets the searchType value for this WsCampiSchedaMonitoraggioRef.
     * 
     * @param searchType
     */
    public void setSearchType(java.lang.String searchType) {
        this.searchType = searchType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsCampiSchedaMonitoraggioRef)) return false;
        WsCampiSchedaMonitoraggioRef other = (WsCampiSchedaMonitoraggioRef) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idCampo==null && other.getIdCampo()==null) || 
             (this.idCampo!=null &&
              this.idCampo.equals(other.getIdCampo()))) &&
            ((this.nomeCampo==null && other.getNomeCampo()==null) || 
             (this.nomeCampo!=null &&
              this.nomeCampo.equals(other.getNomeCampo()))) &&
            ((this.nomeVocabolario==null && other.getNomeVocabolario()==null) || 
             (this.nomeVocabolario!=null &&
              this.nomeVocabolario.equals(other.getNomeVocabolario()))) &&
            ((this.searchType==null && other.getSearchType()==null) || 
             (this.searchType!=null &&
              this.searchType.equals(other.getSearchType())));
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
        if (getIdCampo() != null) {
            _hashCode += getIdCampo().hashCode();
        }
        if (getNomeCampo() != null) {
            _hashCode += getNomeCampo().hashCode();
        }
        if (getNomeVocabolario() != null) {
            _hashCode += getNomeVocabolario().hashCode();
        }
        if (getSearchType() != null) {
            _hashCode += getSearchType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsCampiSchedaMonitoraggioRef.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCampiSchedaMonitoraggioRef"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCampo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCampo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeCampo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeCampo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeVocabolario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeVocabolario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "searchType"));
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
