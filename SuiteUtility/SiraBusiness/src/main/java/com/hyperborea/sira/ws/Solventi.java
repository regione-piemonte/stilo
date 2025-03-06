/**
 * Solventi.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Solventi  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idSolventi;

    private java.lang.String nomeSolvente;

    private java.lang.Float temperaturaSolubilita;

    private java.lang.Float valoreSolubilita;

    public Solventi() {
    }

    public Solventi(
           java.lang.Integer idSolventi,
           java.lang.String nomeSolvente,
           java.lang.Float temperaturaSolubilita,
           java.lang.Float valoreSolubilita) {
        this.idSolventi = idSolventi;
        this.nomeSolvente = nomeSolvente;
        this.temperaturaSolubilita = temperaturaSolubilita;
        this.valoreSolubilita = valoreSolubilita;
    }


    /**
     * Gets the idSolventi value for this Solventi.
     * 
     * @return idSolventi
     */
    public java.lang.Integer getIdSolventi() {
        return idSolventi;
    }


    /**
     * Sets the idSolventi value for this Solventi.
     * 
     * @param idSolventi
     */
    public void setIdSolventi(java.lang.Integer idSolventi) {
        this.idSolventi = idSolventi;
    }


    /**
     * Gets the nomeSolvente value for this Solventi.
     * 
     * @return nomeSolvente
     */
    public java.lang.String getNomeSolvente() {
        return nomeSolvente;
    }


    /**
     * Sets the nomeSolvente value for this Solventi.
     * 
     * @param nomeSolvente
     */
    public void setNomeSolvente(java.lang.String nomeSolvente) {
        this.nomeSolvente = nomeSolvente;
    }


    /**
     * Gets the temperaturaSolubilita value for this Solventi.
     * 
     * @return temperaturaSolubilita
     */
    public java.lang.Float getTemperaturaSolubilita() {
        return temperaturaSolubilita;
    }


    /**
     * Sets the temperaturaSolubilita value for this Solventi.
     * 
     * @param temperaturaSolubilita
     */
    public void setTemperaturaSolubilita(java.lang.Float temperaturaSolubilita) {
        this.temperaturaSolubilita = temperaturaSolubilita;
    }


    /**
     * Gets the valoreSolubilita value for this Solventi.
     * 
     * @return valoreSolubilita
     */
    public java.lang.Float getValoreSolubilita() {
        return valoreSolubilita;
    }


    /**
     * Sets the valoreSolubilita value for this Solventi.
     * 
     * @param valoreSolubilita
     */
    public void setValoreSolubilita(java.lang.Float valoreSolubilita) {
        this.valoreSolubilita = valoreSolubilita;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Solventi)) return false;
        Solventi other = (Solventi) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idSolventi==null && other.getIdSolventi()==null) || 
             (this.idSolventi!=null &&
              this.idSolventi.equals(other.getIdSolventi()))) &&
            ((this.nomeSolvente==null && other.getNomeSolvente()==null) || 
             (this.nomeSolvente!=null &&
              this.nomeSolvente.equals(other.getNomeSolvente()))) &&
            ((this.temperaturaSolubilita==null && other.getTemperaturaSolubilita()==null) || 
             (this.temperaturaSolubilita!=null &&
              this.temperaturaSolubilita.equals(other.getTemperaturaSolubilita()))) &&
            ((this.valoreSolubilita==null && other.getValoreSolubilita()==null) || 
             (this.valoreSolubilita!=null &&
              this.valoreSolubilita.equals(other.getValoreSolubilita())));
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
        if (getIdSolventi() != null) {
            _hashCode += getIdSolventi().hashCode();
        }
        if (getNomeSolvente() != null) {
            _hashCode += getNomeSolvente().hashCode();
        }
        if (getTemperaturaSolubilita() != null) {
            _hashCode += getTemperaturaSolubilita().hashCode();
        }
        if (getValoreSolubilita() != null) {
            _hashCode += getValoreSolubilita().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Solventi.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "solventi"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSolventi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idSolventi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeSolvente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeSolvente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("temperaturaSolubilita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "temperaturaSolubilita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valoreSolubilita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valoreSolubilita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
