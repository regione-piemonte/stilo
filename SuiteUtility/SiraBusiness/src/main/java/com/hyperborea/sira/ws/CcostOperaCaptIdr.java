/**
 * CcostOperaCaptIdr.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostOperaCaptIdr  implements java.io.Serializable {
    private com.hyperborea.sira.ws.ColLitostratigrafica[] colLitostratigraficas;

    private java.lang.Integer idCcost;

    private java.lang.Float portataMaxConcessa;

    private java.lang.String tipoOperaPresa;

    private java.lang.String tipoSbarramento;

    private java.lang.String usoRisorsaIdrica;

    private java.lang.Float volumeMaxAnnuoConcesso;

    private java.lang.Float volumeMedioAnnuo;

    public CcostOperaCaptIdr() {
    }

    public CcostOperaCaptIdr(
           com.hyperborea.sira.ws.ColLitostratigrafica[] colLitostratigraficas,
           java.lang.Integer idCcost,
           java.lang.Float portataMaxConcessa,
           java.lang.String tipoOperaPresa,
           java.lang.String tipoSbarramento,
           java.lang.String usoRisorsaIdrica,
           java.lang.Float volumeMaxAnnuoConcesso,
           java.lang.Float volumeMedioAnnuo) {
           this.colLitostratigraficas = colLitostratigraficas;
           this.idCcost = idCcost;
           this.portataMaxConcessa = portataMaxConcessa;
           this.tipoOperaPresa = tipoOperaPresa;
           this.tipoSbarramento = tipoSbarramento;
           this.usoRisorsaIdrica = usoRisorsaIdrica;
           this.volumeMaxAnnuoConcesso = volumeMaxAnnuoConcesso;
           this.volumeMedioAnnuo = volumeMedioAnnuo;
    }


    /**
     * Gets the colLitostratigraficas value for this CcostOperaCaptIdr.
     * 
     * @return colLitostratigraficas
     */
    public com.hyperborea.sira.ws.ColLitostratigrafica[] getColLitostratigraficas() {
        return colLitostratigraficas;
    }


    /**
     * Sets the colLitostratigraficas value for this CcostOperaCaptIdr.
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
     * Gets the idCcost value for this CcostOperaCaptIdr.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostOperaCaptIdr.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the portataMaxConcessa value for this CcostOperaCaptIdr.
     * 
     * @return portataMaxConcessa
     */
    public java.lang.Float getPortataMaxConcessa() {
        return portataMaxConcessa;
    }


    /**
     * Sets the portataMaxConcessa value for this CcostOperaCaptIdr.
     * 
     * @param portataMaxConcessa
     */
    public void setPortataMaxConcessa(java.lang.Float portataMaxConcessa) {
        this.portataMaxConcessa = portataMaxConcessa;
    }


    /**
     * Gets the tipoOperaPresa value for this CcostOperaCaptIdr.
     * 
     * @return tipoOperaPresa
     */
    public java.lang.String getTipoOperaPresa() {
        return tipoOperaPresa;
    }


    /**
     * Sets the tipoOperaPresa value for this CcostOperaCaptIdr.
     * 
     * @param tipoOperaPresa
     */
    public void setTipoOperaPresa(java.lang.String tipoOperaPresa) {
        this.tipoOperaPresa = tipoOperaPresa;
    }


    /**
     * Gets the tipoSbarramento value for this CcostOperaCaptIdr.
     * 
     * @return tipoSbarramento
     */
    public java.lang.String getTipoSbarramento() {
        return tipoSbarramento;
    }


    /**
     * Sets the tipoSbarramento value for this CcostOperaCaptIdr.
     * 
     * @param tipoSbarramento
     */
    public void setTipoSbarramento(java.lang.String tipoSbarramento) {
        this.tipoSbarramento = tipoSbarramento;
    }


    /**
     * Gets the usoRisorsaIdrica value for this CcostOperaCaptIdr.
     * 
     * @return usoRisorsaIdrica
     */
    public java.lang.String getUsoRisorsaIdrica() {
        return usoRisorsaIdrica;
    }


    /**
     * Sets the usoRisorsaIdrica value for this CcostOperaCaptIdr.
     * 
     * @param usoRisorsaIdrica
     */
    public void setUsoRisorsaIdrica(java.lang.String usoRisorsaIdrica) {
        this.usoRisorsaIdrica = usoRisorsaIdrica;
    }


    /**
     * Gets the volumeMaxAnnuoConcesso value for this CcostOperaCaptIdr.
     * 
     * @return volumeMaxAnnuoConcesso
     */
    public java.lang.Float getVolumeMaxAnnuoConcesso() {
        return volumeMaxAnnuoConcesso;
    }


    /**
     * Sets the volumeMaxAnnuoConcesso value for this CcostOperaCaptIdr.
     * 
     * @param volumeMaxAnnuoConcesso
     */
    public void setVolumeMaxAnnuoConcesso(java.lang.Float volumeMaxAnnuoConcesso) {
        this.volumeMaxAnnuoConcesso = volumeMaxAnnuoConcesso;
    }


    /**
     * Gets the volumeMedioAnnuo value for this CcostOperaCaptIdr.
     * 
     * @return volumeMedioAnnuo
     */
    public java.lang.Float getVolumeMedioAnnuo() {
        return volumeMedioAnnuo;
    }


    /**
     * Sets the volumeMedioAnnuo value for this CcostOperaCaptIdr.
     * 
     * @param volumeMedioAnnuo
     */
    public void setVolumeMedioAnnuo(java.lang.Float volumeMedioAnnuo) {
        this.volumeMedioAnnuo = volumeMedioAnnuo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostOperaCaptIdr)) return false;
        CcostOperaCaptIdr other = (CcostOperaCaptIdr) obj;
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
            ((this.portataMaxConcessa==null && other.getPortataMaxConcessa()==null) || 
             (this.portataMaxConcessa!=null &&
              this.portataMaxConcessa.equals(other.getPortataMaxConcessa()))) &&
            ((this.tipoOperaPresa==null && other.getTipoOperaPresa()==null) || 
             (this.tipoOperaPresa!=null &&
              this.tipoOperaPresa.equals(other.getTipoOperaPresa()))) &&
            ((this.tipoSbarramento==null && other.getTipoSbarramento()==null) || 
             (this.tipoSbarramento!=null &&
              this.tipoSbarramento.equals(other.getTipoSbarramento()))) &&
            ((this.usoRisorsaIdrica==null && other.getUsoRisorsaIdrica()==null) || 
             (this.usoRisorsaIdrica!=null &&
              this.usoRisorsaIdrica.equals(other.getUsoRisorsaIdrica()))) &&
            ((this.volumeMaxAnnuoConcesso==null && other.getVolumeMaxAnnuoConcesso()==null) || 
             (this.volumeMaxAnnuoConcesso!=null &&
              this.volumeMaxAnnuoConcesso.equals(other.getVolumeMaxAnnuoConcesso()))) &&
            ((this.volumeMedioAnnuo==null && other.getVolumeMedioAnnuo()==null) || 
             (this.volumeMedioAnnuo!=null &&
              this.volumeMedioAnnuo.equals(other.getVolumeMedioAnnuo())));
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
        if (getPortataMaxConcessa() != null) {
            _hashCode += getPortataMaxConcessa().hashCode();
        }
        if (getTipoOperaPresa() != null) {
            _hashCode += getTipoOperaPresa().hashCode();
        }
        if (getTipoSbarramento() != null) {
            _hashCode += getTipoSbarramento().hashCode();
        }
        if (getUsoRisorsaIdrica() != null) {
            _hashCode += getUsoRisorsaIdrica().hashCode();
        }
        if (getVolumeMaxAnnuoConcesso() != null) {
            _hashCode += getVolumeMaxAnnuoConcesso().hashCode();
        }
        if (getVolumeMedioAnnuo() != null) {
            _hashCode += getVolumeMedioAnnuo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostOperaCaptIdr.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostOperaCaptIdr"));
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
        elemField.setFieldName("portataMaxConcessa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portataMaxConcessa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoOperaPresa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoOperaPresa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoSbarramento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoSbarramento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usoRisorsaIdrica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "usoRisorsaIdrica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeMaxAnnuoConcesso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeMaxAnnuoConcesso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeMedioAnnuo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeMedioAnnuo"));
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
