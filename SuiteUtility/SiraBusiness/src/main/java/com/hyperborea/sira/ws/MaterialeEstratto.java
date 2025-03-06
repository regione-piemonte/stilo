/**
 * MaterialeEstratto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class MaterialeEstratto  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer anno;

    private java.lang.String caratteristicheMineralogiche;

    private java.lang.Float cubaturaProduzione;

    private java.lang.Float cubaturaResiduo;

    private java.lang.Float cubaturaScarto;

    private java.lang.String destinazioneUso;

    private java.lang.Integer idMateriale;

    private java.lang.Float massaResiduo;

    private java.lang.String materialeLitologico;

    public MaterialeEstratto() {
    }

    public MaterialeEstratto(
           java.lang.Integer anno,
           java.lang.String caratteristicheMineralogiche,
           java.lang.Float cubaturaProduzione,
           java.lang.Float cubaturaResiduo,
           java.lang.Float cubaturaScarto,
           java.lang.String destinazioneUso,
           java.lang.Integer idMateriale,
           java.lang.Float massaResiduo,
           java.lang.String materialeLitologico) {
        this.anno = anno;
        this.caratteristicheMineralogiche = caratteristicheMineralogiche;
        this.cubaturaProduzione = cubaturaProduzione;
        this.cubaturaResiduo = cubaturaResiduo;
        this.cubaturaScarto = cubaturaScarto;
        this.destinazioneUso = destinazioneUso;
        this.idMateriale = idMateriale;
        this.massaResiduo = massaResiduo;
        this.materialeLitologico = materialeLitologico;
    }


    /**
     * Gets the anno value for this MaterialeEstratto.
     * 
     * @return anno
     */
    public java.lang.Integer getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this MaterialeEstratto.
     * 
     * @param anno
     */
    public void setAnno(java.lang.Integer anno) {
        this.anno = anno;
    }


    /**
     * Gets the caratteristicheMineralogiche value for this MaterialeEstratto.
     * 
     * @return caratteristicheMineralogiche
     */
    public java.lang.String getCaratteristicheMineralogiche() {
        return caratteristicheMineralogiche;
    }


    /**
     * Sets the caratteristicheMineralogiche value for this MaterialeEstratto.
     * 
     * @param caratteristicheMineralogiche
     */
    public void setCaratteristicheMineralogiche(java.lang.String caratteristicheMineralogiche) {
        this.caratteristicheMineralogiche = caratteristicheMineralogiche;
    }


    /**
     * Gets the cubaturaProduzione value for this MaterialeEstratto.
     * 
     * @return cubaturaProduzione
     */
    public java.lang.Float getCubaturaProduzione() {
        return cubaturaProduzione;
    }


    /**
     * Sets the cubaturaProduzione value for this MaterialeEstratto.
     * 
     * @param cubaturaProduzione
     */
    public void setCubaturaProduzione(java.lang.Float cubaturaProduzione) {
        this.cubaturaProduzione = cubaturaProduzione;
    }


    /**
     * Gets the cubaturaResiduo value for this MaterialeEstratto.
     * 
     * @return cubaturaResiduo
     */
    public java.lang.Float getCubaturaResiduo() {
        return cubaturaResiduo;
    }


    /**
     * Sets the cubaturaResiduo value for this MaterialeEstratto.
     * 
     * @param cubaturaResiduo
     */
    public void setCubaturaResiduo(java.lang.Float cubaturaResiduo) {
        this.cubaturaResiduo = cubaturaResiduo;
    }


    /**
     * Gets the cubaturaScarto value for this MaterialeEstratto.
     * 
     * @return cubaturaScarto
     */
    public java.lang.Float getCubaturaScarto() {
        return cubaturaScarto;
    }


    /**
     * Sets the cubaturaScarto value for this MaterialeEstratto.
     * 
     * @param cubaturaScarto
     */
    public void setCubaturaScarto(java.lang.Float cubaturaScarto) {
        this.cubaturaScarto = cubaturaScarto;
    }


    /**
     * Gets the destinazioneUso value for this MaterialeEstratto.
     * 
     * @return destinazioneUso
     */
    public java.lang.String getDestinazioneUso() {
        return destinazioneUso;
    }


    /**
     * Sets the destinazioneUso value for this MaterialeEstratto.
     * 
     * @param destinazioneUso
     */
    public void setDestinazioneUso(java.lang.String destinazioneUso) {
        this.destinazioneUso = destinazioneUso;
    }


    /**
     * Gets the idMateriale value for this MaterialeEstratto.
     * 
     * @return idMateriale
     */
    public java.lang.Integer getIdMateriale() {
        return idMateriale;
    }


    /**
     * Sets the idMateriale value for this MaterialeEstratto.
     * 
     * @param idMateriale
     */
    public void setIdMateriale(java.lang.Integer idMateriale) {
        this.idMateriale = idMateriale;
    }


    /**
     * Gets the massaResiduo value for this MaterialeEstratto.
     * 
     * @return massaResiduo
     */
    public java.lang.Float getMassaResiduo() {
        return massaResiduo;
    }


    /**
     * Sets the massaResiduo value for this MaterialeEstratto.
     * 
     * @param massaResiduo
     */
    public void setMassaResiduo(java.lang.Float massaResiduo) {
        this.massaResiduo = massaResiduo;
    }


    /**
     * Gets the materialeLitologico value for this MaterialeEstratto.
     * 
     * @return materialeLitologico
     */
    public java.lang.String getMaterialeLitologico() {
        return materialeLitologico;
    }


    /**
     * Sets the materialeLitologico value for this MaterialeEstratto.
     * 
     * @param materialeLitologico
     */
    public void setMaterialeLitologico(java.lang.String materialeLitologico) {
        this.materialeLitologico = materialeLitologico;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MaterialeEstratto)) return false;
        MaterialeEstratto other = (MaterialeEstratto) obj;
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
            ((this.caratteristicheMineralogiche==null && other.getCaratteristicheMineralogiche()==null) || 
             (this.caratteristicheMineralogiche!=null &&
              this.caratteristicheMineralogiche.equals(other.getCaratteristicheMineralogiche()))) &&
            ((this.cubaturaProduzione==null && other.getCubaturaProduzione()==null) || 
             (this.cubaturaProduzione!=null &&
              this.cubaturaProduzione.equals(other.getCubaturaProduzione()))) &&
            ((this.cubaturaResiduo==null && other.getCubaturaResiduo()==null) || 
             (this.cubaturaResiduo!=null &&
              this.cubaturaResiduo.equals(other.getCubaturaResiduo()))) &&
            ((this.cubaturaScarto==null && other.getCubaturaScarto()==null) || 
             (this.cubaturaScarto!=null &&
              this.cubaturaScarto.equals(other.getCubaturaScarto()))) &&
            ((this.destinazioneUso==null && other.getDestinazioneUso()==null) || 
             (this.destinazioneUso!=null &&
              this.destinazioneUso.equals(other.getDestinazioneUso()))) &&
            ((this.idMateriale==null && other.getIdMateriale()==null) || 
             (this.idMateriale!=null &&
              this.idMateriale.equals(other.getIdMateriale()))) &&
            ((this.massaResiduo==null && other.getMassaResiduo()==null) || 
             (this.massaResiduo!=null &&
              this.massaResiduo.equals(other.getMassaResiduo()))) &&
            ((this.materialeLitologico==null && other.getMaterialeLitologico()==null) || 
             (this.materialeLitologico!=null &&
              this.materialeLitologico.equals(other.getMaterialeLitologico())));
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
        if (getCaratteristicheMineralogiche() != null) {
            _hashCode += getCaratteristicheMineralogiche().hashCode();
        }
        if (getCubaturaProduzione() != null) {
            _hashCode += getCubaturaProduzione().hashCode();
        }
        if (getCubaturaResiduo() != null) {
            _hashCode += getCubaturaResiduo().hashCode();
        }
        if (getCubaturaScarto() != null) {
            _hashCode += getCubaturaScarto().hashCode();
        }
        if (getDestinazioneUso() != null) {
            _hashCode += getDestinazioneUso().hashCode();
        }
        if (getIdMateriale() != null) {
            _hashCode += getIdMateriale().hashCode();
        }
        if (getMassaResiduo() != null) {
            _hashCode += getMassaResiduo().hashCode();
        }
        if (getMaterialeLitologico() != null) {
            _hashCode += getMaterialeLitologico().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MaterialeEstratto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "materialeEstratto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caratteristicheMineralogiche");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caratteristicheMineralogiche"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cubaturaProduzione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cubaturaProduzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cubaturaResiduo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cubaturaResiduo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cubaturaScarto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cubaturaScarto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinazioneUso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destinazioneUso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMateriale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idMateriale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("massaResiduo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "massaResiduo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("materialeLitologico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "materialeLitologico"));
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
