/**
 * Personale.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Personale  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String codiceFiscale;

    private java.lang.String cognome;

    private java.lang.String EMail;

    private com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGoverno;

    private java.lang.String idPersona;

    private java.lang.String nome;

    private com.hyperborea.sira.ws.PersonaleAttivita[] personaleAttivitas;

    private com.hyperborea.sira.ws.PersonaleUo[] personaleUos;

    private com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministratives;

    private java.lang.String telefono;

    public Personale() {
    }

    public Personale(
           java.lang.String codiceFiscale,
           java.lang.String cognome,
           java.lang.String EMail,
           com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGoverno,
           java.lang.String idPersona,
           java.lang.String nome,
           com.hyperborea.sira.ws.PersonaleAttivita[] personaleAttivitas,
           com.hyperborea.sira.ws.PersonaleUo[] personaleUos,
           com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministratives,
           java.lang.String telefono) {
        this.codiceFiscale = codiceFiscale;
        this.cognome = cognome;
        this.EMail = EMail;
        this.entiControlloGoverno = entiControlloGoverno;
        this.idPersona = idPersona;
        this.nome = nome;
        this.personaleAttivitas = personaleAttivitas;
        this.personaleUos = personaleUos;
        this.praticheAmministratives = praticheAmministratives;
        this.telefono = telefono;
    }


    /**
     * Gets the codiceFiscale value for this Personale.
     * 
     * @return codiceFiscale
     */
    public java.lang.String getCodiceFiscale() {
        return codiceFiscale;
    }


    /**
     * Sets the codiceFiscale value for this Personale.
     * 
     * @param codiceFiscale
     */
    public void setCodiceFiscale(java.lang.String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }


    /**
     * Gets the cognome value for this Personale.
     * 
     * @return cognome
     */
    public java.lang.String getCognome() {
        return cognome;
    }


    /**
     * Sets the cognome value for this Personale.
     * 
     * @param cognome
     */
    public void setCognome(java.lang.String cognome) {
        this.cognome = cognome;
    }


    /**
     * Gets the EMail value for this Personale.
     * 
     * @return EMail
     */
    public java.lang.String getEMail() {
        return EMail;
    }


    /**
     * Sets the EMail value for this Personale.
     * 
     * @param EMail
     */
    public void setEMail(java.lang.String EMail) {
        this.EMail = EMail;
    }


    /**
     * Gets the entiControlloGoverno value for this Personale.
     * 
     * @return entiControlloGoverno
     */
    public com.hyperborea.sira.ws.EntiControlloGoverno getEntiControlloGoverno() {
        return entiControlloGoverno;
    }


    /**
     * Sets the entiControlloGoverno value for this Personale.
     * 
     * @param entiControlloGoverno
     */
    public void setEntiControlloGoverno(com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGoverno) {
        this.entiControlloGoverno = entiControlloGoverno;
    }


    /**
     * Gets the idPersona value for this Personale.
     * 
     * @return idPersona
     */
    public java.lang.String getIdPersona() {
        return idPersona;
    }


    /**
     * Sets the idPersona value for this Personale.
     * 
     * @param idPersona
     */
    public void setIdPersona(java.lang.String idPersona) {
        this.idPersona = idPersona;
    }


    /**
     * Gets the nome value for this Personale.
     * 
     * @return nome
     */
    public java.lang.String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this Personale.
     * 
     * @param nome
     */
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }


    /**
     * Gets the personaleAttivitas value for this Personale.
     * 
     * @return personaleAttivitas
     */
    public com.hyperborea.sira.ws.PersonaleAttivita[] getPersonaleAttivitas() {
        return personaleAttivitas;
    }


    /**
     * Sets the personaleAttivitas value for this Personale.
     * 
     * @param personaleAttivitas
     */
    public void setPersonaleAttivitas(com.hyperborea.sira.ws.PersonaleAttivita[] personaleAttivitas) {
        this.personaleAttivitas = personaleAttivitas;
    }

    public com.hyperborea.sira.ws.PersonaleAttivita getPersonaleAttivitas(int i) {
        return this.personaleAttivitas[i];
    }

    public void setPersonaleAttivitas(int i, com.hyperborea.sira.ws.PersonaleAttivita _value) {
        this.personaleAttivitas[i] = _value;
    }


    /**
     * Gets the personaleUos value for this Personale.
     * 
     * @return personaleUos
     */
    public com.hyperborea.sira.ws.PersonaleUo[] getPersonaleUos() {
        return personaleUos;
    }


    /**
     * Sets the personaleUos value for this Personale.
     * 
     * @param personaleUos
     */
    public void setPersonaleUos(com.hyperborea.sira.ws.PersonaleUo[] personaleUos) {
        this.personaleUos = personaleUos;
    }

    public com.hyperborea.sira.ws.PersonaleUo getPersonaleUos(int i) {
        return this.personaleUos[i];
    }

    public void setPersonaleUos(int i, com.hyperborea.sira.ws.PersonaleUo _value) {
        this.personaleUos[i] = _value;
    }


    /**
     * Gets the praticheAmministratives value for this Personale.
     * 
     * @return praticheAmministratives
     */
    public com.hyperborea.sira.ws.PraticheAmministrative[] getPraticheAmministratives() {
        return praticheAmministratives;
    }


    /**
     * Sets the praticheAmministratives value for this Personale.
     * 
     * @param praticheAmministratives
     */
    public void setPraticheAmministratives(com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministratives) {
        this.praticheAmministratives = praticheAmministratives;
    }

    public com.hyperborea.sira.ws.PraticheAmministrative getPraticheAmministratives(int i) {
        return this.praticheAmministratives[i];
    }

    public void setPraticheAmministratives(int i, com.hyperborea.sira.ws.PraticheAmministrative _value) {
        this.praticheAmministratives[i] = _value;
    }


    /**
     * Gets the telefono value for this Personale.
     * 
     * @return telefono
     */
    public java.lang.String getTelefono() {
        return telefono;
    }


    /**
     * Sets the telefono value for this Personale.
     * 
     * @param telefono
     */
    public void setTelefono(java.lang.String telefono) {
        this.telefono = telefono;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Personale)) return false;
        Personale other = (Personale) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codiceFiscale==null && other.getCodiceFiscale()==null) || 
             (this.codiceFiscale!=null &&
              this.codiceFiscale.equals(other.getCodiceFiscale()))) &&
            ((this.cognome==null && other.getCognome()==null) || 
             (this.cognome!=null &&
              this.cognome.equals(other.getCognome()))) &&
            ((this.EMail==null && other.getEMail()==null) || 
             (this.EMail!=null &&
              this.EMail.equals(other.getEMail()))) &&
            ((this.entiControlloGoverno==null && other.getEntiControlloGoverno()==null) || 
             (this.entiControlloGoverno!=null &&
              this.entiControlloGoverno.equals(other.getEntiControlloGoverno()))) &&
            ((this.idPersona==null && other.getIdPersona()==null) || 
             (this.idPersona!=null &&
              this.idPersona.equals(other.getIdPersona()))) &&
            ((this.nome==null && other.getNome()==null) || 
             (this.nome!=null &&
              this.nome.equals(other.getNome()))) &&
            ((this.personaleAttivitas==null && other.getPersonaleAttivitas()==null) || 
             (this.personaleAttivitas!=null &&
              java.util.Arrays.equals(this.personaleAttivitas, other.getPersonaleAttivitas()))) &&
            ((this.personaleUos==null && other.getPersonaleUos()==null) || 
             (this.personaleUos!=null &&
              java.util.Arrays.equals(this.personaleUos, other.getPersonaleUos()))) &&
            ((this.praticheAmministratives==null && other.getPraticheAmministratives()==null) || 
             (this.praticheAmministratives!=null &&
              java.util.Arrays.equals(this.praticheAmministratives, other.getPraticheAmministratives()))) &&
            ((this.telefono==null && other.getTelefono()==null) || 
             (this.telefono!=null &&
              this.telefono.equals(other.getTelefono())));
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
        if (getCodiceFiscale() != null) {
            _hashCode += getCodiceFiscale().hashCode();
        }
        if (getCognome() != null) {
            _hashCode += getCognome().hashCode();
        }
        if (getEMail() != null) {
            _hashCode += getEMail().hashCode();
        }
        if (getEntiControlloGoverno() != null) {
            _hashCode += getEntiControlloGoverno().hashCode();
        }
        if (getIdPersona() != null) {
            _hashCode += getIdPersona().hashCode();
        }
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        if (getPersonaleAttivitas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPersonaleAttivitas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPersonaleAttivitas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPersonaleUos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPersonaleUos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPersonaleUos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPraticheAmministratives() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPraticheAmministratives());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPraticheAmministratives(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTelefono() != null) {
            _hashCode += getTelefono().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Personale.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "personale"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceFiscale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cognome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cognome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EMail");
        elemField.setXmlName(new javax.xml.namespace.QName("", "EMail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entiControlloGoverno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entiControlloGoverno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "entiControlloGoverno"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPersona");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idPersona"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personaleAttivitas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "personaleAttivitas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "personaleAttivita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personaleUos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "personaleUos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "personaleUo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("praticheAmministratives");
        elemField.setXmlName(new javax.xml.namespace.QName("", "praticheAmministratives"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "praticheAmministrative"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("telefono");
        elemField.setXmlName(new javax.xml.namespace.QName("", "telefono"));
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
