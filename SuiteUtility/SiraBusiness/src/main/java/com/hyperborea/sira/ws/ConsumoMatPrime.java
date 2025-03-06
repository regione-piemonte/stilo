/**
 * ConsumoMatPrime.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ConsumoMatPrime  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer anno;

    private com.hyperborea.sira.ws.CcostUnitaLocali ccostUnitaLocali;

    private java.lang.Float consumoAnnuo;

    private java.lang.Float consumoAnnuoAnnoRif;

    private java.lang.String descrizione;

    private java.lang.Integer idMatPrima;

    private java.lang.String produttore;

    private java.lang.String schedaTecnica;

    private com.hyperborea.sira.ws.SostanzePericolose[] sostanzePericoloses;

    private com.hyperborea.sira.ws.VocStatoFisico statoFisico;

    private com.hyperborea.sira.ws.VocTipoMateriaPrima tipo;

    private com.hyperborea.sira.ws.UnitaDiMisuraCia umConsumoAnnuo;

    private java.lang.Integer wsInternalRef;

    public ConsumoMatPrime() {
    }

    public ConsumoMatPrime(
           java.lang.Integer anno,
           com.hyperborea.sira.ws.CcostUnitaLocali ccostUnitaLocali,
           java.lang.Float consumoAnnuo,
           java.lang.Float consumoAnnuoAnnoRif,
           java.lang.String descrizione,
           java.lang.Integer idMatPrima,
           java.lang.String produttore,
           java.lang.String schedaTecnica,
           com.hyperborea.sira.ws.SostanzePericolose[] sostanzePericoloses,
           com.hyperborea.sira.ws.VocStatoFisico statoFisico,
           com.hyperborea.sira.ws.VocTipoMateriaPrima tipo,
           com.hyperborea.sira.ws.UnitaDiMisuraCia umConsumoAnnuo,
           java.lang.Integer wsInternalRef) {
        this.anno = anno;
        this.ccostUnitaLocali = ccostUnitaLocali;
        this.consumoAnnuo = consumoAnnuo;
        this.consumoAnnuoAnnoRif = consumoAnnuoAnnoRif;
        this.descrizione = descrizione;
        this.idMatPrima = idMatPrima;
        this.produttore = produttore;
        this.schedaTecnica = schedaTecnica;
        this.sostanzePericoloses = sostanzePericoloses;
        this.statoFisico = statoFisico;
        this.tipo = tipo;
        this.umConsumoAnnuo = umConsumoAnnuo;
        this.wsInternalRef = wsInternalRef;
    }


    /**
     * Gets the anno value for this ConsumoMatPrime.
     * 
     * @return anno
     */
    public java.lang.Integer getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this ConsumoMatPrime.
     * 
     * @param anno
     */
    public void setAnno(java.lang.Integer anno) {
        this.anno = anno;
    }


    /**
     * Gets the ccostUnitaLocali value for this ConsumoMatPrime.
     * 
     * @return ccostUnitaLocali
     */
    public com.hyperborea.sira.ws.CcostUnitaLocali getCcostUnitaLocali() {
        return ccostUnitaLocali;
    }


    /**
     * Sets the ccostUnitaLocali value for this ConsumoMatPrime.
     * 
     * @param ccostUnitaLocali
     */
    public void setCcostUnitaLocali(com.hyperborea.sira.ws.CcostUnitaLocali ccostUnitaLocali) {
        this.ccostUnitaLocali = ccostUnitaLocali;
    }


    /**
     * Gets the consumoAnnuo value for this ConsumoMatPrime.
     * 
     * @return consumoAnnuo
     */
    public java.lang.Float getConsumoAnnuo() {
        return consumoAnnuo;
    }


    /**
     * Sets the consumoAnnuo value for this ConsumoMatPrime.
     * 
     * @param consumoAnnuo
     */
    public void setConsumoAnnuo(java.lang.Float consumoAnnuo) {
        this.consumoAnnuo = consumoAnnuo;
    }


    /**
     * Gets the consumoAnnuoAnnoRif value for this ConsumoMatPrime.
     * 
     * @return consumoAnnuoAnnoRif
     */
    public java.lang.Float getConsumoAnnuoAnnoRif() {
        return consumoAnnuoAnnoRif;
    }


    /**
     * Sets the consumoAnnuoAnnoRif value for this ConsumoMatPrime.
     * 
     * @param consumoAnnuoAnnoRif
     */
    public void setConsumoAnnuoAnnoRif(java.lang.Float consumoAnnuoAnnoRif) {
        this.consumoAnnuoAnnoRif = consumoAnnuoAnnoRif;
    }


    /**
     * Gets the descrizione value for this ConsumoMatPrime.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this ConsumoMatPrime.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idMatPrima value for this ConsumoMatPrime.
     * 
     * @return idMatPrima
     */
    public java.lang.Integer getIdMatPrima() {
        return idMatPrima;
    }


    /**
     * Sets the idMatPrima value for this ConsumoMatPrime.
     * 
     * @param idMatPrima
     */
    public void setIdMatPrima(java.lang.Integer idMatPrima) {
        this.idMatPrima = idMatPrima;
    }


    /**
     * Gets the produttore value for this ConsumoMatPrime.
     * 
     * @return produttore
     */
    public java.lang.String getProduttore() {
        return produttore;
    }


    /**
     * Sets the produttore value for this ConsumoMatPrime.
     * 
     * @param produttore
     */
    public void setProduttore(java.lang.String produttore) {
        this.produttore = produttore;
    }


    /**
     * Gets the schedaTecnica value for this ConsumoMatPrime.
     * 
     * @return schedaTecnica
     */
    public java.lang.String getSchedaTecnica() {
        return schedaTecnica;
    }


    /**
     * Sets the schedaTecnica value for this ConsumoMatPrime.
     * 
     * @param schedaTecnica
     */
    public void setSchedaTecnica(java.lang.String schedaTecnica) {
        this.schedaTecnica = schedaTecnica;
    }


    /**
     * Gets the sostanzePericoloses value for this ConsumoMatPrime.
     * 
     * @return sostanzePericoloses
     */
    public com.hyperborea.sira.ws.SostanzePericolose[] getSostanzePericoloses() {
        return sostanzePericoloses;
    }


    /**
     * Sets the sostanzePericoloses value for this ConsumoMatPrime.
     * 
     * @param sostanzePericoloses
     */
    public void setSostanzePericoloses(com.hyperborea.sira.ws.SostanzePericolose[] sostanzePericoloses) {
        this.sostanzePericoloses = sostanzePericoloses;
    }

    public com.hyperborea.sira.ws.SostanzePericolose getSostanzePericoloses(int i) {
        return this.sostanzePericoloses[i];
    }

    public void setSostanzePericoloses(int i, com.hyperborea.sira.ws.SostanzePericolose _value) {
        this.sostanzePericoloses[i] = _value;
    }


    /**
     * Gets the statoFisico value for this ConsumoMatPrime.
     * 
     * @return statoFisico
     */
    public com.hyperborea.sira.ws.VocStatoFisico getStatoFisico() {
        return statoFisico;
    }


    /**
     * Sets the statoFisico value for this ConsumoMatPrime.
     * 
     * @param statoFisico
     */
    public void setStatoFisico(com.hyperborea.sira.ws.VocStatoFisico statoFisico) {
        this.statoFisico = statoFisico;
    }


    /**
     * Gets the tipo value for this ConsumoMatPrime.
     * 
     * @return tipo
     */
    public com.hyperborea.sira.ws.VocTipoMateriaPrima getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this ConsumoMatPrime.
     * 
     * @param tipo
     */
    public void setTipo(com.hyperborea.sira.ws.VocTipoMateriaPrima tipo) {
        this.tipo = tipo;
    }


    /**
     * Gets the umConsumoAnnuo value for this ConsumoMatPrime.
     * 
     * @return umConsumoAnnuo
     */
    public com.hyperborea.sira.ws.UnitaDiMisuraCia getUmConsumoAnnuo() {
        return umConsumoAnnuo;
    }


    /**
     * Sets the umConsumoAnnuo value for this ConsumoMatPrime.
     * 
     * @param umConsumoAnnuo
     */
    public void setUmConsumoAnnuo(com.hyperborea.sira.ws.UnitaDiMisuraCia umConsumoAnnuo) {
        this.umConsumoAnnuo = umConsumoAnnuo;
    }


    /**
     * Gets the wsInternalRef value for this ConsumoMatPrime.
     * 
     * @return wsInternalRef
     */
    public java.lang.Integer getWsInternalRef() {
        return wsInternalRef;
    }


    /**
     * Sets the wsInternalRef value for this ConsumoMatPrime.
     * 
     * @param wsInternalRef
     */
    public void setWsInternalRef(java.lang.Integer wsInternalRef) {
        this.wsInternalRef = wsInternalRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsumoMatPrime)) return false;
        ConsumoMatPrime other = (ConsumoMatPrime) obj;
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
            ((this.consumoAnnuo==null && other.getConsumoAnnuo()==null) || 
             (this.consumoAnnuo!=null &&
              this.consumoAnnuo.equals(other.getConsumoAnnuo()))) &&
            ((this.consumoAnnuoAnnoRif==null && other.getConsumoAnnuoAnnoRif()==null) || 
             (this.consumoAnnuoAnnoRif!=null &&
              this.consumoAnnuoAnnoRif.equals(other.getConsumoAnnuoAnnoRif()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.idMatPrima==null && other.getIdMatPrima()==null) || 
             (this.idMatPrima!=null &&
              this.idMatPrima.equals(other.getIdMatPrima()))) &&
            ((this.produttore==null && other.getProduttore()==null) || 
             (this.produttore!=null &&
              this.produttore.equals(other.getProduttore()))) &&
            ((this.schedaTecnica==null && other.getSchedaTecnica()==null) || 
             (this.schedaTecnica!=null &&
              this.schedaTecnica.equals(other.getSchedaTecnica()))) &&
            ((this.sostanzePericoloses==null && other.getSostanzePericoloses()==null) || 
             (this.sostanzePericoloses!=null &&
              java.util.Arrays.equals(this.sostanzePericoloses, other.getSostanzePericoloses()))) &&
            ((this.statoFisico==null && other.getStatoFisico()==null) || 
             (this.statoFisico!=null &&
              this.statoFisico.equals(other.getStatoFisico()))) &&
            ((this.tipo==null && other.getTipo()==null) || 
             (this.tipo!=null &&
              this.tipo.equals(other.getTipo()))) &&
            ((this.umConsumoAnnuo==null && other.getUmConsumoAnnuo()==null) || 
             (this.umConsumoAnnuo!=null &&
              this.umConsumoAnnuo.equals(other.getUmConsumoAnnuo()))) &&
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
        if (getConsumoAnnuo() != null) {
            _hashCode += getConsumoAnnuo().hashCode();
        }
        if (getConsumoAnnuoAnnoRif() != null) {
            _hashCode += getConsumoAnnuoAnnoRif().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getIdMatPrima() != null) {
            _hashCode += getIdMatPrima().hashCode();
        }
        if (getProduttore() != null) {
            _hashCode += getProduttore().hashCode();
        }
        if (getSchedaTecnica() != null) {
            _hashCode += getSchedaTecnica().hashCode();
        }
        if (getSostanzePericoloses() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSostanzePericoloses());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSostanzePericoloses(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStatoFisico() != null) {
            _hashCode += getStatoFisico().hashCode();
        }
        if (getTipo() != null) {
            _hashCode += getTipo().hashCode();
        }
        if (getUmConsumoAnnuo() != null) {
            _hashCode += getUmConsumoAnnuo().hashCode();
        }
        if (getWsInternalRef() != null) {
            _hashCode += getWsInternalRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsumoMatPrime.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumoMatPrime"));
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
        elemField.setFieldName("consumoAnnuo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consumoAnnuo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumoAnnuoAnnoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consumoAnnuoAnnoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMatPrima");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idMatPrima"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("produttore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "produttore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("schedaTecnica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "schedaTecnica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sostanzePericoloses");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sostanzePericoloses"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostanzePericolose"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoFisico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statoFisico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocStatoFisico"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoMateriaPrima"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umConsumoAnnuo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umConsumoAnnuo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "unitaDiMisuraCia"));
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
