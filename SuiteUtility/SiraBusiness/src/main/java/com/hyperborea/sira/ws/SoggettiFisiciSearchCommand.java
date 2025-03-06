/**
 * SoggettiFisiciSearchCommand.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class SoggettiFisiciSearchCommand  implements java.io.Serializable {
    private java.lang.String codiceFiscale;

    private java.lang.String cognome;

    private java.lang.String comune;

    private com.hyperborea.sira.ws.ComuniItalia comuniItalia;

    private java.lang.String idFunzioneSf;

    private java.lang.String nome;

    private java.lang.String nomeCognome;

    private java.lang.Integer pageSize;

    private java.lang.String provincia;

    public SoggettiFisiciSearchCommand() {
    }

    public SoggettiFisiciSearchCommand(
           java.lang.String codiceFiscale,
           java.lang.String cognome,
           java.lang.String comune,
           com.hyperborea.sira.ws.ComuniItalia comuniItalia,
           java.lang.String idFunzioneSf,
           java.lang.String nome,
           java.lang.String nomeCognome,
           java.lang.Integer pageSize,
           java.lang.String provincia) {
           this.codiceFiscale = codiceFiscale;
           this.cognome = cognome;
           this.comune = comune;
           this.comuniItalia = comuniItalia;
           this.idFunzioneSf = idFunzioneSf;
           this.nome = nome;
           this.nomeCognome = nomeCognome;
           this.pageSize = pageSize;
           this.provincia = provincia;
    }


    /**
     * Gets the codiceFiscale value for this SoggettiFisiciSearchCommand.
     * 
     * @return codiceFiscale
     */
    public java.lang.String getCodiceFiscale() {
        return codiceFiscale;
    }


    /**
     * Sets the codiceFiscale value for this SoggettiFisiciSearchCommand.
     * 
     * @param codiceFiscale
     */
    public void setCodiceFiscale(java.lang.String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }


    /**
     * Gets the cognome value for this SoggettiFisiciSearchCommand.
     * 
     * @return cognome
     */
    public java.lang.String getCognome() {
        return cognome;
    }


    /**
     * Sets the cognome value for this SoggettiFisiciSearchCommand.
     * 
     * @param cognome
     */
    public void setCognome(java.lang.String cognome) {
        this.cognome = cognome;
    }


    /**
     * Gets the comune value for this SoggettiFisiciSearchCommand.
     * 
     * @return comune
     */
    public java.lang.String getComune() {
        return comune;
    }


    /**
     * Sets the comune value for this SoggettiFisiciSearchCommand.
     * 
     * @param comune
     */
    public void setComune(java.lang.String comune) {
        this.comune = comune;
    }


    /**
     * Gets the comuniItalia value for this SoggettiFisiciSearchCommand.
     * 
     * @return comuniItalia
     */
    public com.hyperborea.sira.ws.ComuniItalia getComuniItalia() {
        return comuniItalia;
    }


    /**
     * Sets the comuniItalia value for this SoggettiFisiciSearchCommand.
     * 
     * @param comuniItalia
     */
    public void setComuniItalia(com.hyperborea.sira.ws.ComuniItalia comuniItalia) {
        this.comuniItalia = comuniItalia;
    }


    /**
     * Gets the idFunzioneSf value for this SoggettiFisiciSearchCommand.
     * 
     * @return idFunzioneSf
     */
    public java.lang.String getIdFunzioneSf() {
        return idFunzioneSf;
    }


    /**
     * Sets the idFunzioneSf value for this SoggettiFisiciSearchCommand.
     * 
     * @param idFunzioneSf
     */
    public void setIdFunzioneSf(java.lang.String idFunzioneSf) {
        this.idFunzioneSf = idFunzioneSf;
    }


    /**
     * Gets the nome value for this SoggettiFisiciSearchCommand.
     * 
     * @return nome
     */
    public java.lang.String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this SoggettiFisiciSearchCommand.
     * 
     * @param nome
     */
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }


    /**
     * Gets the nomeCognome value for this SoggettiFisiciSearchCommand.
     * 
     * @return nomeCognome
     */
    public java.lang.String getNomeCognome() {
        return nomeCognome;
    }


    /**
     * Sets the nomeCognome value for this SoggettiFisiciSearchCommand.
     * 
     * @param nomeCognome
     */
    public void setNomeCognome(java.lang.String nomeCognome) {
        this.nomeCognome = nomeCognome;
    }


    /**
     * Gets the pageSize value for this SoggettiFisiciSearchCommand.
     * 
     * @return pageSize
     */
    public java.lang.Integer getPageSize() {
        return pageSize;
    }


    /**
     * Sets the pageSize value for this SoggettiFisiciSearchCommand.
     * 
     * @param pageSize
     */
    public void setPageSize(java.lang.Integer pageSize) {
        this.pageSize = pageSize;
    }


    /**
     * Gets the provincia value for this SoggettiFisiciSearchCommand.
     * 
     * @return provincia
     */
    public java.lang.String getProvincia() {
        return provincia;
    }


    /**
     * Sets the provincia value for this SoggettiFisiciSearchCommand.
     * 
     * @param provincia
     */
    public void setProvincia(java.lang.String provincia) {
        this.provincia = provincia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SoggettiFisiciSearchCommand)) return false;
        SoggettiFisiciSearchCommand other = (SoggettiFisiciSearchCommand) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceFiscale==null && other.getCodiceFiscale()==null) || 
             (this.codiceFiscale!=null &&
              this.codiceFiscale.equals(other.getCodiceFiscale()))) &&
            ((this.cognome==null && other.getCognome()==null) || 
             (this.cognome!=null &&
              this.cognome.equals(other.getCognome()))) &&
            ((this.comune==null && other.getComune()==null) || 
             (this.comune!=null &&
              this.comune.equals(other.getComune()))) &&
            ((this.comuniItalia==null && other.getComuniItalia()==null) || 
             (this.comuniItalia!=null &&
              this.comuniItalia.equals(other.getComuniItalia()))) &&
            ((this.idFunzioneSf==null && other.getIdFunzioneSf()==null) || 
             (this.idFunzioneSf!=null &&
              this.idFunzioneSf.equals(other.getIdFunzioneSf()))) &&
            ((this.nome==null && other.getNome()==null) || 
             (this.nome!=null &&
              this.nome.equals(other.getNome()))) &&
            ((this.nomeCognome==null && other.getNomeCognome()==null) || 
             (this.nomeCognome!=null &&
              this.nomeCognome.equals(other.getNomeCognome()))) &&
            ((this.pageSize==null && other.getPageSize()==null) || 
             (this.pageSize!=null &&
              this.pageSize.equals(other.getPageSize()))) &&
            ((this.provincia==null && other.getProvincia()==null) || 
             (this.provincia!=null &&
              this.provincia.equals(other.getProvincia())));
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
        if (getCodiceFiscale() != null) {
            _hashCode += getCodiceFiscale().hashCode();
        }
        if (getCognome() != null) {
            _hashCode += getCognome().hashCode();
        }
        if (getComune() != null) {
            _hashCode += getComune().hashCode();
        }
        if (getComuniItalia() != null) {
            _hashCode += getComuniItalia().hashCode();
        }
        if (getIdFunzioneSf() != null) {
            _hashCode += getIdFunzioneSf().hashCode();
        }
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        if (getNomeCognome() != null) {
            _hashCode += getNomeCognome().hashCode();
        }
        if (getPageSize() != null) {
            _hashCode += getPageSize().hashCode();
        }
        if (getProvincia() != null) {
            _hashCode += getProvincia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SoggettiFisiciSearchCommand.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "soggettiFisiciSearchCommand"));
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
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comune");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comuniItalia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comuniItalia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "comuniItalia"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFunzioneSf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idFunzioneSf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeCognome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeCognome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageSize");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pageSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provincia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provincia"));
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
