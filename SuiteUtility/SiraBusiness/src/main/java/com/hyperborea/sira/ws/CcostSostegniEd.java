/**
 * CcostSostegniEd.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostSostegniEd  implements java.io.Serializable {
    private java.lang.Integer allacciamento;

    private com.hyperborea.sira.ws.Fasi[] fasis;

    private java.lang.Float hhCondBasso;

    private java.lang.Float hhGiunzione;

    private java.lang.Integer idCcost;

    private java.lang.Integer nnSostegno;

    private java.lang.Float orientamento;

    private com.hyperborea.sira.ws.TipiBaseSostegno tipiBaseSostegno;

    private com.hyperborea.sira.ws.TipiTestaSostegno tipiTestaSostegno;

    private com.hyperborea.sira.ws.SostegniEdTipoOrientamento tipoOrientamento;

    private com.hyperborea.sira.ws.SostegniEdTipoSostegno tipoSostegno;

    public CcostSostegniEd() {
    }

    public CcostSostegniEd(
           java.lang.Integer allacciamento,
           com.hyperborea.sira.ws.Fasi[] fasis,
           java.lang.Float hhCondBasso,
           java.lang.Float hhGiunzione,
           java.lang.Integer idCcost,
           java.lang.Integer nnSostegno,
           java.lang.Float orientamento,
           com.hyperborea.sira.ws.TipiBaseSostegno tipiBaseSostegno,
           com.hyperborea.sira.ws.TipiTestaSostegno tipiTestaSostegno,
           com.hyperborea.sira.ws.SostegniEdTipoOrientamento tipoOrientamento,
           com.hyperborea.sira.ws.SostegniEdTipoSostegno tipoSostegno) {
           this.allacciamento = allacciamento;
           this.fasis = fasis;
           this.hhCondBasso = hhCondBasso;
           this.hhGiunzione = hhGiunzione;
           this.idCcost = idCcost;
           this.nnSostegno = nnSostegno;
           this.orientamento = orientamento;
           this.tipiBaseSostegno = tipiBaseSostegno;
           this.tipiTestaSostegno = tipiTestaSostegno;
           this.tipoOrientamento = tipoOrientamento;
           this.tipoSostegno = tipoSostegno;
    }


    /**
     * Gets the allacciamento value for this CcostSostegniEd.
     * 
     * @return allacciamento
     */
    public java.lang.Integer getAllacciamento() {
        return allacciamento;
    }


    /**
     * Sets the allacciamento value for this CcostSostegniEd.
     * 
     * @param allacciamento
     */
    public void setAllacciamento(java.lang.Integer allacciamento) {
        this.allacciamento = allacciamento;
    }


    /**
     * Gets the fasis value for this CcostSostegniEd.
     * 
     * @return fasis
     */
    public com.hyperborea.sira.ws.Fasi[] getFasis() {
        return fasis;
    }


    /**
     * Sets the fasis value for this CcostSostegniEd.
     * 
     * @param fasis
     */
    public void setFasis(com.hyperborea.sira.ws.Fasi[] fasis) {
        this.fasis = fasis;
    }

    public com.hyperborea.sira.ws.Fasi getFasis(int i) {
        return this.fasis[i];
    }

    public void setFasis(int i, com.hyperborea.sira.ws.Fasi _value) {
        this.fasis[i] = _value;
    }


    /**
     * Gets the hhCondBasso value for this CcostSostegniEd.
     * 
     * @return hhCondBasso
     */
    public java.lang.Float getHhCondBasso() {
        return hhCondBasso;
    }


    /**
     * Sets the hhCondBasso value for this CcostSostegniEd.
     * 
     * @param hhCondBasso
     */
    public void setHhCondBasso(java.lang.Float hhCondBasso) {
        this.hhCondBasso = hhCondBasso;
    }


    /**
     * Gets the hhGiunzione value for this CcostSostegniEd.
     * 
     * @return hhGiunzione
     */
    public java.lang.Float getHhGiunzione() {
        return hhGiunzione;
    }


    /**
     * Sets the hhGiunzione value for this CcostSostegniEd.
     * 
     * @param hhGiunzione
     */
    public void setHhGiunzione(java.lang.Float hhGiunzione) {
        this.hhGiunzione = hhGiunzione;
    }


    /**
     * Gets the idCcost value for this CcostSostegniEd.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostSostegniEd.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the nnSostegno value for this CcostSostegniEd.
     * 
     * @return nnSostegno
     */
    public java.lang.Integer getNnSostegno() {
        return nnSostegno;
    }


    /**
     * Sets the nnSostegno value for this CcostSostegniEd.
     * 
     * @param nnSostegno
     */
    public void setNnSostegno(java.lang.Integer nnSostegno) {
        this.nnSostegno = nnSostegno;
    }


    /**
     * Gets the orientamento value for this CcostSostegniEd.
     * 
     * @return orientamento
     */
    public java.lang.Float getOrientamento() {
        return orientamento;
    }


    /**
     * Sets the orientamento value for this CcostSostegniEd.
     * 
     * @param orientamento
     */
    public void setOrientamento(java.lang.Float orientamento) {
        this.orientamento = orientamento;
    }


    /**
     * Gets the tipiBaseSostegno value for this CcostSostegniEd.
     * 
     * @return tipiBaseSostegno
     */
    public com.hyperborea.sira.ws.TipiBaseSostegno getTipiBaseSostegno() {
        return tipiBaseSostegno;
    }


    /**
     * Sets the tipiBaseSostegno value for this CcostSostegniEd.
     * 
     * @param tipiBaseSostegno
     */
    public void setTipiBaseSostegno(com.hyperborea.sira.ws.TipiBaseSostegno tipiBaseSostegno) {
        this.tipiBaseSostegno = tipiBaseSostegno;
    }


    /**
     * Gets the tipiTestaSostegno value for this CcostSostegniEd.
     * 
     * @return tipiTestaSostegno
     */
    public com.hyperborea.sira.ws.TipiTestaSostegno getTipiTestaSostegno() {
        return tipiTestaSostegno;
    }


    /**
     * Sets the tipiTestaSostegno value for this CcostSostegniEd.
     * 
     * @param tipiTestaSostegno
     */
    public void setTipiTestaSostegno(com.hyperborea.sira.ws.TipiTestaSostegno tipiTestaSostegno) {
        this.tipiTestaSostegno = tipiTestaSostegno;
    }


    /**
     * Gets the tipoOrientamento value for this CcostSostegniEd.
     * 
     * @return tipoOrientamento
     */
    public com.hyperborea.sira.ws.SostegniEdTipoOrientamento getTipoOrientamento() {
        return tipoOrientamento;
    }


    /**
     * Sets the tipoOrientamento value for this CcostSostegniEd.
     * 
     * @param tipoOrientamento
     */
    public void setTipoOrientamento(com.hyperborea.sira.ws.SostegniEdTipoOrientamento tipoOrientamento) {
        this.tipoOrientamento = tipoOrientamento;
    }


    /**
     * Gets the tipoSostegno value for this CcostSostegniEd.
     * 
     * @return tipoSostegno
     */
    public com.hyperborea.sira.ws.SostegniEdTipoSostegno getTipoSostegno() {
        return tipoSostegno;
    }


    /**
     * Sets the tipoSostegno value for this CcostSostegniEd.
     * 
     * @param tipoSostegno
     */
    public void setTipoSostegno(com.hyperborea.sira.ws.SostegniEdTipoSostegno tipoSostegno) {
        this.tipoSostegno = tipoSostegno;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostSostegniEd)) return false;
        CcostSostegniEd other = (CcostSostegniEd) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.allacciamento==null && other.getAllacciamento()==null) || 
             (this.allacciamento!=null &&
              this.allacciamento.equals(other.getAllacciamento()))) &&
            ((this.fasis==null && other.getFasis()==null) || 
             (this.fasis!=null &&
              java.util.Arrays.equals(this.fasis, other.getFasis()))) &&
            ((this.hhCondBasso==null && other.getHhCondBasso()==null) || 
             (this.hhCondBasso!=null &&
              this.hhCondBasso.equals(other.getHhCondBasso()))) &&
            ((this.hhGiunzione==null && other.getHhGiunzione()==null) || 
             (this.hhGiunzione!=null &&
              this.hhGiunzione.equals(other.getHhGiunzione()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.nnSostegno==null && other.getNnSostegno()==null) || 
             (this.nnSostegno!=null &&
              this.nnSostegno.equals(other.getNnSostegno()))) &&
            ((this.orientamento==null && other.getOrientamento()==null) || 
             (this.orientamento!=null &&
              this.orientamento.equals(other.getOrientamento()))) &&
            ((this.tipiBaseSostegno==null && other.getTipiBaseSostegno()==null) || 
             (this.tipiBaseSostegno!=null &&
              this.tipiBaseSostegno.equals(other.getTipiBaseSostegno()))) &&
            ((this.tipiTestaSostegno==null && other.getTipiTestaSostegno()==null) || 
             (this.tipiTestaSostegno!=null &&
              this.tipiTestaSostegno.equals(other.getTipiTestaSostegno()))) &&
            ((this.tipoOrientamento==null && other.getTipoOrientamento()==null) || 
             (this.tipoOrientamento!=null &&
              this.tipoOrientamento.equals(other.getTipoOrientamento()))) &&
            ((this.tipoSostegno==null && other.getTipoSostegno()==null) || 
             (this.tipoSostegno!=null &&
              this.tipoSostegno.equals(other.getTipoSostegno())));
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
        if (getAllacciamento() != null) {
            _hashCode += getAllacciamento().hashCode();
        }
        if (getFasis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFasis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFasis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getHhCondBasso() != null) {
            _hashCode += getHhCondBasso().hashCode();
        }
        if (getHhGiunzione() != null) {
            _hashCode += getHhGiunzione().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getNnSostegno() != null) {
            _hashCode += getNnSostegno().hashCode();
        }
        if (getOrientamento() != null) {
            _hashCode += getOrientamento().hashCode();
        }
        if (getTipiBaseSostegno() != null) {
            _hashCode += getTipiBaseSostegno().hashCode();
        }
        if (getTipiTestaSostegno() != null) {
            _hashCode += getTipiTestaSostegno().hashCode();
        }
        if (getTipoOrientamento() != null) {
            _hashCode += getTipoOrientamento().hashCode();
        }
        if (getTipoSostegno() != null) {
            _hashCode += getTipoSostegno().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostSostegniEd.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSostegniEd"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allacciamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "allacciamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fasis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fasis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "fasi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hhCondBasso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hhCondBasso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hhGiunzione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hhGiunzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
        elemField.setFieldName("nnSostegno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nnSostegno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orientamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orientamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipiBaseSostegno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipiBaseSostegno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiBaseSostegno"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipiTestaSostegno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipiTestaSostegno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiTestaSostegno"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoOrientamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoOrientamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostegniEdTipoOrientamento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoSostegno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoSostegno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostegniEdTipoSostegno"));
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
