/**
 * ConsumoEnergia.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ConsumoEnergia  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer anno;

    private com.hyperborea.sira.ws.CcostUnitaLocali ccostUnitaLocali;

    private java.lang.Float consumoElSpecif;

    private java.lang.Float consumoElSpecifAnnoRif;

    private java.lang.Float consumoTerSpecif;

    private java.lang.Float consumoTerSpecifAnnoRif;

    private java.lang.Float energiaElettrica;

    private java.lang.Float energiaElettricaAnnoRif;

    private java.lang.Float energiaTermica;

    private java.lang.Float energiaTermicaAnnoRif;

    private java.lang.Integer idConsEnergia;

    private java.lang.String prodottoPrincipale;

    private java.lang.Integer wsInternalRef;

    public ConsumoEnergia() {
    }

    public ConsumoEnergia(
           java.lang.Integer anno,
           com.hyperborea.sira.ws.CcostUnitaLocali ccostUnitaLocali,
           java.lang.Float consumoElSpecif,
           java.lang.Float consumoElSpecifAnnoRif,
           java.lang.Float consumoTerSpecif,
           java.lang.Float consumoTerSpecifAnnoRif,
           java.lang.Float energiaElettrica,
           java.lang.Float energiaElettricaAnnoRif,
           java.lang.Float energiaTermica,
           java.lang.Float energiaTermicaAnnoRif,
           java.lang.Integer idConsEnergia,
           java.lang.String prodottoPrincipale,
           java.lang.Integer wsInternalRef) {
        this.anno = anno;
        this.ccostUnitaLocali = ccostUnitaLocali;
        this.consumoElSpecif = consumoElSpecif;
        this.consumoElSpecifAnnoRif = consumoElSpecifAnnoRif;
        this.consumoTerSpecif = consumoTerSpecif;
        this.consumoTerSpecifAnnoRif = consumoTerSpecifAnnoRif;
        this.energiaElettrica = energiaElettrica;
        this.energiaElettricaAnnoRif = energiaElettricaAnnoRif;
        this.energiaTermica = energiaTermica;
        this.energiaTermicaAnnoRif = energiaTermicaAnnoRif;
        this.idConsEnergia = idConsEnergia;
        this.prodottoPrincipale = prodottoPrincipale;
        this.wsInternalRef = wsInternalRef;
    }


    /**
     * Gets the anno value for this ConsumoEnergia.
     * 
     * @return anno
     */
    public java.lang.Integer getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this ConsumoEnergia.
     * 
     * @param anno
     */
    public void setAnno(java.lang.Integer anno) {
        this.anno = anno;
    }


    /**
     * Gets the ccostUnitaLocali value for this ConsumoEnergia.
     * 
     * @return ccostUnitaLocali
     */
    public com.hyperborea.sira.ws.CcostUnitaLocali getCcostUnitaLocali() {
        return ccostUnitaLocali;
    }


    /**
     * Sets the ccostUnitaLocali value for this ConsumoEnergia.
     * 
     * @param ccostUnitaLocali
     */
    public void setCcostUnitaLocali(com.hyperborea.sira.ws.CcostUnitaLocali ccostUnitaLocali) {
        this.ccostUnitaLocali = ccostUnitaLocali;
    }


    /**
     * Gets the consumoElSpecif value for this ConsumoEnergia.
     * 
     * @return consumoElSpecif
     */
    public java.lang.Float getConsumoElSpecif() {
        return consumoElSpecif;
    }


    /**
     * Sets the consumoElSpecif value for this ConsumoEnergia.
     * 
     * @param consumoElSpecif
     */
    public void setConsumoElSpecif(java.lang.Float consumoElSpecif) {
        this.consumoElSpecif = consumoElSpecif;
    }


    /**
     * Gets the consumoElSpecifAnnoRif value for this ConsumoEnergia.
     * 
     * @return consumoElSpecifAnnoRif
     */
    public java.lang.Float getConsumoElSpecifAnnoRif() {
        return consumoElSpecifAnnoRif;
    }


    /**
     * Sets the consumoElSpecifAnnoRif value for this ConsumoEnergia.
     * 
     * @param consumoElSpecifAnnoRif
     */
    public void setConsumoElSpecifAnnoRif(java.lang.Float consumoElSpecifAnnoRif) {
        this.consumoElSpecifAnnoRif = consumoElSpecifAnnoRif;
    }


    /**
     * Gets the consumoTerSpecif value for this ConsumoEnergia.
     * 
     * @return consumoTerSpecif
     */
    public java.lang.Float getConsumoTerSpecif() {
        return consumoTerSpecif;
    }


    /**
     * Sets the consumoTerSpecif value for this ConsumoEnergia.
     * 
     * @param consumoTerSpecif
     */
    public void setConsumoTerSpecif(java.lang.Float consumoTerSpecif) {
        this.consumoTerSpecif = consumoTerSpecif;
    }


    /**
     * Gets the consumoTerSpecifAnnoRif value for this ConsumoEnergia.
     * 
     * @return consumoTerSpecifAnnoRif
     */
    public java.lang.Float getConsumoTerSpecifAnnoRif() {
        return consumoTerSpecifAnnoRif;
    }


    /**
     * Sets the consumoTerSpecifAnnoRif value for this ConsumoEnergia.
     * 
     * @param consumoTerSpecifAnnoRif
     */
    public void setConsumoTerSpecifAnnoRif(java.lang.Float consumoTerSpecifAnnoRif) {
        this.consumoTerSpecifAnnoRif = consumoTerSpecifAnnoRif;
    }


    /**
     * Gets the energiaElettrica value for this ConsumoEnergia.
     * 
     * @return energiaElettrica
     */
    public java.lang.Float getEnergiaElettrica() {
        return energiaElettrica;
    }


    /**
     * Sets the energiaElettrica value for this ConsumoEnergia.
     * 
     * @param energiaElettrica
     */
    public void setEnergiaElettrica(java.lang.Float energiaElettrica) {
        this.energiaElettrica = energiaElettrica;
    }


    /**
     * Gets the energiaElettricaAnnoRif value for this ConsumoEnergia.
     * 
     * @return energiaElettricaAnnoRif
     */
    public java.lang.Float getEnergiaElettricaAnnoRif() {
        return energiaElettricaAnnoRif;
    }


    /**
     * Sets the energiaElettricaAnnoRif value for this ConsumoEnergia.
     * 
     * @param energiaElettricaAnnoRif
     */
    public void setEnergiaElettricaAnnoRif(java.lang.Float energiaElettricaAnnoRif) {
        this.energiaElettricaAnnoRif = energiaElettricaAnnoRif;
    }


    /**
     * Gets the energiaTermica value for this ConsumoEnergia.
     * 
     * @return energiaTermica
     */
    public java.lang.Float getEnergiaTermica() {
        return energiaTermica;
    }


    /**
     * Sets the energiaTermica value for this ConsumoEnergia.
     * 
     * @param energiaTermica
     */
    public void setEnergiaTermica(java.lang.Float energiaTermica) {
        this.energiaTermica = energiaTermica;
    }


    /**
     * Gets the energiaTermicaAnnoRif value for this ConsumoEnergia.
     * 
     * @return energiaTermicaAnnoRif
     */
    public java.lang.Float getEnergiaTermicaAnnoRif() {
        return energiaTermicaAnnoRif;
    }


    /**
     * Sets the energiaTermicaAnnoRif value for this ConsumoEnergia.
     * 
     * @param energiaTermicaAnnoRif
     */
    public void setEnergiaTermicaAnnoRif(java.lang.Float energiaTermicaAnnoRif) {
        this.energiaTermicaAnnoRif = energiaTermicaAnnoRif;
    }


    /**
     * Gets the idConsEnergia value for this ConsumoEnergia.
     * 
     * @return idConsEnergia
     */
    public java.lang.Integer getIdConsEnergia() {
        return idConsEnergia;
    }


    /**
     * Sets the idConsEnergia value for this ConsumoEnergia.
     * 
     * @param idConsEnergia
     */
    public void setIdConsEnergia(java.lang.Integer idConsEnergia) {
        this.idConsEnergia = idConsEnergia;
    }


    /**
     * Gets the prodottoPrincipale value for this ConsumoEnergia.
     * 
     * @return prodottoPrincipale
     */
    public java.lang.String getProdottoPrincipale() {
        return prodottoPrincipale;
    }


    /**
     * Sets the prodottoPrincipale value for this ConsumoEnergia.
     * 
     * @param prodottoPrincipale
     */
    public void setProdottoPrincipale(java.lang.String prodottoPrincipale) {
        this.prodottoPrincipale = prodottoPrincipale;
    }


    /**
     * Gets the wsInternalRef value for this ConsumoEnergia.
     * 
     * @return wsInternalRef
     */
    public java.lang.Integer getWsInternalRef() {
        return wsInternalRef;
    }


    /**
     * Sets the wsInternalRef value for this ConsumoEnergia.
     * 
     * @param wsInternalRef
     */
    public void setWsInternalRef(java.lang.Integer wsInternalRef) {
        this.wsInternalRef = wsInternalRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsumoEnergia)) return false;
        ConsumoEnergia other = (ConsumoEnergia) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.anno==null && other.getAnno()==null) || 
             (this.anno!=null &&
              this.anno.equals(other.getAnno()))) &&
            ((this.ccostUnitaLocali==null && other.getCcostUnitaLocali()==null) || 
             (this.ccostUnitaLocali!=null &&
              this.ccostUnitaLocali.equals(other.getCcostUnitaLocali()))) &&
            ((this.consumoElSpecif==null && other.getConsumoElSpecif()==null) || 
             (this.consumoElSpecif!=null &&
              this.consumoElSpecif.equals(other.getConsumoElSpecif()))) &&
            ((this.consumoElSpecifAnnoRif==null && other.getConsumoElSpecifAnnoRif()==null) || 
             (this.consumoElSpecifAnnoRif!=null &&
              this.consumoElSpecifAnnoRif.equals(other.getConsumoElSpecifAnnoRif()))) &&
            ((this.consumoTerSpecif==null && other.getConsumoTerSpecif()==null) || 
             (this.consumoTerSpecif!=null &&
              this.consumoTerSpecif.equals(other.getConsumoTerSpecif()))) &&
            ((this.consumoTerSpecifAnnoRif==null && other.getConsumoTerSpecifAnnoRif()==null) || 
             (this.consumoTerSpecifAnnoRif!=null &&
              this.consumoTerSpecifAnnoRif.equals(other.getConsumoTerSpecifAnnoRif()))) &&
            ((this.energiaElettrica==null && other.getEnergiaElettrica()==null) || 
             (this.energiaElettrica!=null &&
              this.energiaElettrica.equals(other.getEnergiaElettrica()))) &&
            ((this.energiaElettricaAnnoRif==null && other.getEnergiaElettricaAnnoRif()==null) || 
             (this.energiaElettricaAnnoRif!=null &&
              this.energiaElettricaAnnoRif.equals(other.getEnergiaElettricaAnnoRif()))) &&
            ((this.energiaTermica==null && other.getEnergiaTermica()==null) || 
             (this.energiaTermica!=null &&
              this.energiaTermica.equals(other.getEnergiaTermica()))) &&
            ((this.energiaTermicaAnnoRif==null && other.getEnergiaTermicaAnnoRif()==null) || 
             (this.energiaTermicaAnnoRif!=null &&
              this.energiaTermicaAnnoRif.equals(other.getEnergiaTermicaAnnoRif()))) &&
            ((this.idConsEnergia==null && other.getIdConsEnergia()==null) || 
             (this.idConsEnergia!=null &&
              this.idConsEnergia.equals(other.getIdConsEnergia()))) &&
            ((this.prodottoPrincipale==null && other.getProdottoPrincipale()==null) || 
             (this.prodottoPrincipale!=null &&
              this.prodottoPrincipale.equals(other.getProdottoPrincipale()))) &&
            ((this.wsInternalRef==null && other.getWsInternalRef()==null) || 
             (this.wsInternalRef!=null &&
              this.wsInternalRef.equals(other.getWsInternalRef())));
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
        if (getAnno() != null) {
            _hashCode += getAnno().hashCode();
        }
        if (getCcostUnitaLocali() != null) {
            _hashCode += getCcostUnitaLocali().hashCode();
        }
        if (getConsumoElSpecif() != null) {
            _hashCode += getConsumoElSpecif().hashCode();
        }
        if (getConsumoElSpecifAnnoRif() != null) {
            _hashCode += getConsumoElSpecifAnnoRif().hashCode();
        }
        if (getConsumoTerSpecif() != null) {
            _hashCode += getConsumoTerSpecif().hashCode();
        }
        if (getConsumoTerSpecifAnnoRif() != null) {
            _hashCode += getConsumoTerSpecifAnnoRif().hashCode();
        }
        if (getEnergiaElettrica() != null) {
            _hashCode += getEnergiaElettrica().hashCode();
        }
        if (getEnergiaElettricaAnnoRif() != null) {
            _hashCode += getEnergiaElettricaAnnoRif().hashCode();
        }
        if (getEnergiaTermica() != null) {
            _hashCode += getEnergiaTermica().hashCode();
        }
        if (getEnergiaTermicaAnnoRif() != null) {
            _hashCode += getEnergiaTermicaAnnoRif().hashCode();
        }
        if (getIdConsEnergia() != null) {
            _hashCode += getIdConsEnergia().hashCode();
        }
        if (getProdottoPrincipale() != null) {
            _hashCode += getProdottoPrincipale().hashCode();
        }
        if (getWsInternalRef() != null) {
            _hashCode += getWsInternalRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsumoEnergia.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumoEnergia"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostUnitaLocali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostUnitaLocali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostUnitaLocali"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumoElSpecif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consumoElSpecif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumoElSpecifAnnoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consumoElSpecifAnnoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumoTerSpecif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consumoTerSpecif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumoTerSpecifAnnoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consumoTerSpecifAnnoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("energiaElettrica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "energiaElettrica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("energiaElettricaAnnoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "energiaElettricaAnnoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("energiaTermica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "energiaTermica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("energiaTermicaAnnoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "energiaTermicaAnnoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idConsEnergia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idConsEnergia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodottoPrincipale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodottoPrincipale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsInternalRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsInternalRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
