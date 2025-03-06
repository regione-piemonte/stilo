/**
 * CcostSondaggiGeo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostSondaggiGeo  implements java.io.Serializable {
    private com.hyperborea.sira.ws.ColLitostratigrafica[] colLitostratigraficas;

    private java.lang.Integer idCcost;

    private java.lang.String motivoEsec;

    private java.lang.Float profondita;

    private java.lang.Float quota;

    private java.lang.String tipoPerf;

    public CcostSondaggiGeo() {
    }

    public CcostSondaggiGeo(
           com.hyperborea.sira.ws.ColLitostratigrafica[] colLitostratigraficas,
           java.lang.Integer idCcost,
           java.lang.String motivoEsec,
           java.lang.Float profondita,
           java.lang.Float quota,
           java.lang.String tipoPerf) {
           this.colLitostratigraficas = colLitostratigraficas;
           this.idCcost = idCcost;
           this.motivoEsec = motivoEsec;
           this.profondita = profondita;
           this.quota = quota;
           this.tipoPerf = tipoPerf;
    }


    /**
     * Gets the colLitostratigraficas value for this CcostSondaggiGeo.
     * 
     * @return colLitostratigraficas
     */
    public com.hyperborea.sira.ws.ColLitostratigrafica[] getColLitostratigraficas() {
        return colLitostratigraficas;
    }


    /**
     * Sets the colLitostratigraficas value for this CcostSondaggiGeo.
     * 
     * @param colLitostratigraficas
     */
    public void setColLitostratigraficas(com.hyperborea.sira.ws.ColLitostratigrafica[] colLitostratigraficas) {
        this.colLitostratigraficas = colLitostratigraficas;
    }

    public com.hyperborea.sira.ws.ColLitostratigrafica getColLitostratigraficas(int i) {
        return this.colLitostratigraficas[i];
    }

    public void setColLitostratigraficas(int i, com.hyperborea.sira.ws.ColLitostratigrafica _value) {
        this.colLitostratigraficas[i] = _value;
    }


    /**
     * Gets the idCcost value for this CcostSondaggiGeo.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostSondaggiGeo.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the motivoEsec value for this CcostSondaggiGeo.
     * 
     * @return motivoEsec
     */
    public java.lang.String getMotivoEsec() {
        return motivoEsec;
    }


    /**
     * Sets the motivoEsec value for this CcostSondaggiGeo.
     * 
     * @param motivoEsec
     */
    public void setMotivoEsec(java.lang.String motivoEsec) {
        this.motivoEsec = motivoEsec;
    }


    /**
     * Gets the profondita value for this CcostSondaggiGeo.
     * 
     * @return profondita
     */
    public java.lang.Float getProfondita() {
        return profondita;
    }


    /**
     * Sets the profondita value for this CcostSondaggiGeo.
     * 
     * @param profondita
     */
    public void setProfondita(java.lang.Float profondita) {
        this.profondita = profondita;
    }


    /**
     * Gets the quota value for this CcostSondaggiGeo.
     * 
     * @return quota
     */
    public java.lang.Float getQuota() {
        return quota;
    }


    /**
     * Sets the quota value for this CcostSondaggiGeo.
     * 
     * @param quota
     */
    public void setQuota(java.lang.Float quota) {
        this.quota = quota;
    }


    /**
     * Gets the tipoPerf value for this CcostSondaggiGeo.
     * 
     * @return tipoPerf
     */
    public java.lang.String getTipoPerf() {
        return tipoPerf;
    }


    /**
     * Sets the tipoPerf value for this CcostSondaggiGeo.
     * 
     * @param tipoPerf
     */
    public void setTipoPerf(java.lang.String tipoPerf) {
        this.tipoPerf = tipoPerf;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostSondaggiGeo)) return false;
        CcostSondaggiGeo other = (CcostSondaggiGeo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.colLitostratigraficas==null && other.getColLitostratigraficas()==null) || 
             (this.colLitostratigraficas!=null &&
              java.util.Arrays.equals(this.colLitostratigraficas, other.getColLitostratigraficas()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.motivoEsec==null && other.getMotivoEsec()==null) || 
             (this.motivoEsec!=null &&
              this.motivoEsec.equals(other.getMotivoEsec()))) &&
            ((this.profondita==null && other.getProfondita()==null) || 
             (this.profondita!=null &&
              this.profondita.equals(other.getProfondita()))) &&
            ((this.quota==null && other.getQuota()==null) || 
             (this.quota!=null &&
              this.quota.equals(other.getQuota()))) &&
            ((this.tipoPerf==null && other.getTipoPerf()==null) || 
             (this.tipoPerf!=null &&
              this.tipoPerf.equals(other.getTipoPerf())));
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
        if (getColLitostratigraficas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getColLitostratigraficas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getColLitostratigraficas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getMotivoEsec() != null) {
            _hashCode += getMotivoEsec().hashCode();
        }
        if (getProfondita() != null) {
            _hashCode += getProfondita().hashCode();
        }
        if (getQuota() != null) {
            _hashCode += getQuota().hashCode();
        }
        if (getTipoPerf() != null) {
            _hashCode += getTipoPerf().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostSondaggiGeo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSondaggiGeo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("colLitostratigraficas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "colLitostratigraficas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "colLitostratigrafica"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("motivoEsec");
        elemField.setXmlName(new javax.xml.namespace.QName("", "motivoEsec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profondita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profondita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoPerf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoPerf"));
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
