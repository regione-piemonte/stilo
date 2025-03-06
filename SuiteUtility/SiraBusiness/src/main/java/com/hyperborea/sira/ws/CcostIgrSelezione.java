/**
 * CcostIgrSelezione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostIgrSelezione  implements java.io.Serializable {
    private java.lang.String abbattimentoPolveri;

    private java.lang.String areaTrattamento;

    private com.hyperborea.sira.ws.DestMaterialiRecuperati[] destMaterialiRecuperatis;

    private java.lang.String destinazioneScarti;

    private java.lang.Integer idCcost;

    private java.lang.String modalitaStoccaggio;

    private java.lang.Integer numeroLinee;

    private java.lang.Float potenzialitaLinea;

    private java.lang.String presidiAmbientali;

    private java.lang.Float superficiePavimentata;

    private com.hyperborea.sira.ws.TecnologieTrattamento[] tecnologieTrattamentos;

    private java.lang.String tipoRifiuti;

    private com.hyperborea.sira.ws.TrattamentiPostSelezione[] trattamentiPostSeleziones;

    public CcostIgrSelezione() {
    }

    public CcostIgrSelezione(
           java.lang.String abbattimentoPolveri,
           java.lang.String areaTrattamento,
           com.hyperborea.sira.ws.DestMaterialiRecuperati[] destMaterialiRecuperatis,
           java.lang.String destinazioneScarti,
           java.lang.Integer idCcost,
           java.lang.String modalitaStoccaggio,
           java.lang.Integer numeroLinee,
           java.lang.Float potenzialitaLinea,
           java.lang.String presidiAmbientali,
           java.lang.Float superficiePavimentata,
           com.hyperborea.sira.ws.TecnologieTrattamento[] tecnologieTrattamentos,
           java.lang.String tipoRifiuti,
           com.hyperborea.sira.ws.TrattamentiPostSelezione[] trattamentiPostSeleziones) {
           this.abbattimentoPolveri = abbattimentoPolveri;
           this.areaTrattamento = areaTrattamento;
           this.destMaterialiRecuperatis = destMaterialiRecuperatis;
           this.destinazioneScarti = destinazioneScarti;
           this.idCcost = idCcost;
           this.modalitaStoccaggio = modalitaStoccaggio;
           this.numeroLinee = numeroLinee;
           this.potenzialitaLinea = potenzialitaLinea;
           this.presidiAmbientali = presidiAmbientali;
           this.superficiePavimentata = superficiePavimentata;
           this.tecnologieTrattamentos = tecnologieTrattamentos;
           this.tipoRifiuti = tipoRifiuti;
           this.trattamentiPostSeleziones = trattamentiPostSeleziones;
    }


    /**
     * Gets the abbattimentoPolveri value for this CcostIgrSelezione.
     * 
     * @return abbattimentoPolveri
     */
    public java.lang.String getAbbattimentoPolveri() {
        return abbattimentoPolveri;
    }


    /**
     * Sets the abbattimentoPolveri value for this CcostIgrSelezione.
     * 
     * @param abbattimentoPolveri
     */
    public void setAbbattimentoPolveri(java.lang.String abbattimentoPolveri) {
        this.abbattimentoPolveri = abbattimentoPolveri;
    }


    /**
     * Gets the areaTrattamento value for this CcostIgrSelezione.
     * 
     * @return areaTrattamento
     */
    public java.lang.String getAreaTrattamento() {
        return areaTrattamento;
    }


    /**
     * Sets the areaTrattamento value for this CcostIgrSelezione.
     * 
     * @param areaTrattamento
     */
    public void setAreaTrattamento(java.lang.String areaTrattamento) {
        this.areaTrattamento = areaTrattamento;
    }


    /**
     * Gets the destMaterialiRecuperatis value for this CcostIgrSelezione.
     * 
     * @return destMaterialiRecuperatis
     */
    public com.hyperborea.sira.ws.DestMaterialiRecuperati[] getDestMaterialiRecuperatis() {
        return destMaterialiRecuperatis;
    }


    /**
     * Sets the destMaterialiRecuperatis value for this CcostIgrSelezione.
     * 
     * @param destMaterialiRecuperatis
     */
    public void setDestMaterialiRecuperatis(com.hyperborea.sira.ws.DestMaterialiRecuperati[] destMaterialiRecuperatis) {
        this.destMaterialiRecuperatis = destMaterialiRecuperatis;
    }

    public com.hyperborea.sira.ws.DestMaterialiRecuperati getDestMaterialiRecuperatis(int i) {
        return this.destMaterialiRecuperatis[i];
    }

    public void setDestMaterialiRecuperatis(int i, com.hyperborea.sira.ws.DestMaterialiRecuperati _value) {
        this.destMaterialiRecuperatis[i] = _value;
    }


    /**
     * Gets the destinazioneScarti value for this CcostIgrSelezione.
     * 
     * @return destinazioneScarti
     */
    public java.lang.String getDestinazioneScarti() {
        return destinazioneScarti;
    }


    /**
     * Sets the destinazioneScarti value for this CcostIgrSelezione.
     * 
     * @param destinazioneScarti
     */
    public void setDestinazioneScarti(java.lang.String destinazioneScarti) {
        this.destinazioneScarti = destinazioneScarti;
    }


    /**
     * Gets the idCcost value for this CcostIgrSelezione.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostIgrSelezione.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the modalitaStoccaggio value for this CcostIgrSelezione.
     * 
     * @return modalitaStoccaggio
     */
    public java.lang.String getModalitaStoccaggio() {
        return modalitaStoccaggio;
    }


    /**
     * Sets the modalitaStoccaggio value for this CcostIgrSelezione.
     * 
     * @param modalitaStoccaggio
     */
    public void setModalitaStoccaggio(java.lang.String modalitaStoccaggio) {
        this.modalitaStoccaggio = modalitaStoccaggio;
    }


    /**
     * Gets the numeroLinee value for this CcostIgrSelezione.
     * 
     * @return numeroLinee
     */
    public java.lang.Integer getNumeroLinee() {
        return numeroLinee;
    }


    /**
     * Sets the numeroLinee value for this CcostIgrSelezione.
     * 
     * @param numeroLinee
     */
    public void setNumeroLinee(java.lang.Integer numeroLinee) {
        this.numeroLinee = numeroLinee;
    }


    /**
     * Gets the potenzialitaLinea value for this CcostIgrSelezione.
     * 
     * @return potenzialitaLinea
     */
    public java.lang.Float getPotenzialitaLinea() {
        return potenzialitaLinea;
    }


    /**
     * Sets the potenzialitaLinea value for this CcostIgrSelezione.
     * 
     * @param potenzialitaLinea
     */
    public void setPotenzialitaLinea(java.lang.Float potenzialitaLinea) {
        this.potenzialitaLinea = potenzialitaLinea;
    }


    /**
     * Gets the presidiAmbientali value for this CcostIgrSelezione.
     * 
     * @return presidiAmbientali
     */
    public java.lang.String getPresidiAmbientali() {
        return presidiAmbientali;
    }


    /**
     * Sets the presidiAmbientali value for this CcostIgrSelezione.
     * 
     * @param presidiAmbientali
     */
    public void setPresidiAmbientali(java.lang.String presidiAmbientali) {
        this.presidiAmbientali = presidiAmbientali;
    }


    /**
     * Gets the superficiePavimentata value for this CcostIgrSelezione.
     * 
     * @return superficiePavimentata
     */
    public java.lang.Float getSuperficiePavimentata() {
        return superficiePavimentata;
    }


    /**
     * Sets the superficiePavimentata value for this CcostIgrSelezione.
     * 
     * @param superficiePavimentata
     */
    public void setSuperficiePavimentata(java.lang.Float superficiePavimentata) {
        this.superficiePavimentata = superficiePavimentata;
    }


    /**
     * Gets the tecnologieTrattamentos value for this CcostIgrSelezione.
     * 
     * @return tecnologieTrattamentos
     */
    public com.hyperborea.sira.ws.TecnologieTrattamento[] getTecnologieTrattamentos() {
        return tecnologieTrattamentos;
    }


    /**
     * Sets the tecnologieTrattamentos value for this CcostIgrSelezione.
     * 
     * @param tecnologieTrattamentos
     */
    public void setTecnologieTrattamentos(com.hyperborea.sira.ws.TecnologieTrattamento[] tecnologieTrattamentos) {
        this.tecnologieTrattamentos = tecnologieTrattamentos;
    }

    public com.hyperborea.sira.ws.TecnologieTrattamento getTecnologieTrattamentos(int i) {
        return this.tecnologieTrattamentos[i];
    }

    public void setTecnologieTrattamentos(int i, com.hyperborea.sira.ws.TecnologieTrattamento _value) {
        this.tecnologieTrattamentos[i] = _value;
    }


    /**
     * Gets the tipoRifiuti value for this CcostIgrSelezione.
     * 
     * @return tipoRifiuti
     */
    public java.lang.String getTipoRifiuti() {
        return tipoRifiuti;
    }


    /**
     * Sets the tipoRifiuti value for this CcostIgrSelezione.
     * 
     * @param tipoRifiuti
     */
    public void setTipoRifiuti(java.lang.String tipoRifiuti) {
        this.tipoRifiuti = tipoRifiuti;
    }


    /**
     * Gets the trattamentiPostSeleziones value for this CcostIgrSelezione.
     * 
     * @return trattamentiPostSeleziones
     */
    public com.hyperborea.sira.ws.TrattamentiPostSelezione[] getTrattamentiPostSeleziones() {
        return trattamentiPostSeleziones;
    }


    /**
     * Sets the trattamentiPostSeleziones value for this CcostIgrSelezione.
     * 
     * @param trattamentiPostSeleziones
     */
    public void setTrattamentiPostSeleziones(com.hyperborea.sira.ws.TrattamentiPostSelezione[] trattamentiPostSeleziones) {
        this.trattamentiPostSeleziones = trattamentiPostSeleziones;
    }

    public com.hyperborea.sira.ws.TrattamentiPostSelezione getTrattamentiPostSeleziones(int i) {
        return this.trattamentiPostSeleziones[i];
    }

    public void setTrattamentiPostSeleziones(int i, com.hyperborea.sira.ws.TrattamentiPostSelezione _value) {
        this.trattamentiPostSeleziones[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostIgrSelezione)) return false;
        CcostIgrSelezione other = (CcostIgrSelezione) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.abbattimentoPolveri==null && other.getAbbattimentoPolveri()==null) || 
             (this.abbattimentoPolveri!=null &&
              this.abbattimentoPolveri.equals(other.getAbbattimentoPolveri()))) &&
            ((this.areaTrattamento==null && other.getAreaTrattamento()==null) || 
             (this.areaTrattamento!=null &&
              this.areaTrattamento.equals(other.getAreaTrattamento()))) &&
            ((this.destMaterialiRecuperatis==null && other.getDestMaterialiRecuperatis()==null) || 
             (this.destMaterialiRecuperatis!=null &&
              java.util.Arrays.equals(this.destMaterialiRecuperatis, other.getDestMaterialiRecuperatis()))) &&
            ((this.destinazioneScarti==null && other.getDestinazioneScarti()==null) || 
             (this.destinazioneScarti!=null &&
              this.destinazioneScarti.equals(other.getDestinazioneScarti()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.modalitaStoccaggio==null && other.getModalitaStoccaggio()==null) || 
             (this.modalitaStoccaggio!=null &&
              this.modalitaStoccaggio.equals(other.getModalitaStoccaggio()))) &&
            ((this.numeroLinee==null && other.getNumeroLinee()==null) || 
             (this.numeroLinee!=null &&
              this.numeroLinee.equals(other.getNumeroLinee()))) &&
            ((this.potenzialitaLinea==null && other.getPotenzialitaLinea()==null) || 
             (this.potenzialitaLinea!=null &&
              this.potenzialitaLinea.equals(other.getPotenzialitaLinea()))) &&
            ((this.presidiAmbientali==null && other.getPresidiAmbientali()==null) || 
             (this.presidiAmbientali!=null &&
              this.presidiAmbientali.equals(other.getPresidiAmbientali()))) &&
            ((this.superficiePavimentata==null && other.getSuperficiePavimentata()==null) || 
             (this.superficiePavimentata!=null &&
              this.superficiePavimentata.equals(other.getSuperficiePavimentata()))) &&
            ((this.tecnologieTrattamentos==null && other.getTecnologieTrattamentos()==null) || 
             (this.tecnologieTrattamentos!=null &&
              java.util.Arrays.equals(this.tecnologieTrattamentos, other.getTecnologieTrattamentos()))) &&
            ((this.tipoRifiuti==null && other.getTipoRifiuti()==null) || 
             (this.tipoRifiuti!=null &&
              this.tipoRifiuti.equals(other.getTipoRifiuti()))) &&
            ((this.trattamentiPostSeleziones==null && other.getTrattamentiPostSeleziones()==null) || 
             (this.trattamentiPostSeleziones!=null &&
              java.util.Arrays.equals(this.trattamentiPostSeleziones, other.getTrattamentiPostSeleziones())));
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
        if (getAbbattimentoPolveri() != null) {
            _hashCode += getAbbattimentoPolveri().hashCode();
        }
        if (getAreaTrattamento() != null) {
            _hashCode += getAreaTrattamento().hashCode();
        }
        if (getDestMaterialiRecuperatis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDestMaterialiRecuperatis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDestMaterialiRecuperatis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDestinazioneScarti() != null) {
            _hashCode += getDestinazioneScarti().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getModalitaStoccaggio() != null) {
            _hashCode += getModalitaStoccaggio().hashCode();
        }
        if (getNumeroLinee() != null) {
            _hashCode += getNumeroLinee().hashCode();
        }
        if (getPotenzialitaLinea() != null) {
            _hashCode += getPotenzialitaLinea().hashCode();
        }
        if (getPresidiAmbientali() != null) {
            _hashCode += getPresidiAmbientali().hashCode();
        }
        if (getSuperficiePavimentata() != null) {
            _hashCode += getSuperficiePavimentata().hashCode();
        }
        if (getTecnologieTrattamentos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTecnologieTrattamentos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTecnologieTrattamentos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTipoRifiuti() != null) {
            _hashCode += getTipoRifiuti().hashCode();
        }
        if (getTrattamentiPostSeleziones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTrattamentiPostSeleziones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTrattamentiPostSeleziones(), i);
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
        new org.apache.axis.description.TypeDesc(CcostIgrSelezione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrSelezione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("abbattimentoPolveri");
        elemField.setXmlName(new javax.xml.namespace.QName("", "abbattimentoPolveri"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areaTrattamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "areaTrattamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destMaterialiRecuperatis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destMaterialiRecuperatis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "destMaterialiRecuperati"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinazioneScarti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destinazioneScarti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modalitaStoccaggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modalitaStoccaggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroLinee");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroLinee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzialitaLinea");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzialitaLinea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("presidiAmbientali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "presidiAmbientali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficiePavimentata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficiePavimentata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tecnologieTrattamentos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tecnologieTrattamentos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tecnologieTrattamento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoRifiuti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoRifiuti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trattamentiPostSeleziones");
        elemField.setXmlName(new javax.xml.namespace.QName("", "trattamentiPostSeleziones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "trattamentiPostSelezione"));
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
