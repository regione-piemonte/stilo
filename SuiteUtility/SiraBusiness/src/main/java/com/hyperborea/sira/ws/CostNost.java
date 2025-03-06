/**
 * CostNost.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CostNost  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CategorieOst categorieOst;

    private com.hyperborea.sira.ws.CostNostId id;

    private com.hyperborea.sira.ws.NatureOst natureOst;

    private com.hyperborea.sira.ws.StatioperativiCostNost[] statioperativiCostNosts;

    private com.hyperborea.sira.ws.TipirostCostNost[] tipoRelazioneOstAsParent;

    private com.hyperborea.sira.ws.TipirostCostNost[] tipoRelazioneOstAsChild;

    private java.lang.String tipoGeometria;

    private java.lang.String visibile;

    public CostNost() {
    }

    public CostNost(
           com.hyperborea.sira.ws.CategorieOst categorieOst,
           com.hyperborea.sira.ws.CostNostId id,
           com.hyperborea.sira.ws.NatureOst natureOst,
           com.hyperborea.sira.ws.StatioperativiCostNost[] statioperativiCostNosts,
           com.hyperborea.sira.ws.TipirostCostNost[] tipoRelazioneOstAsParent,
           com.hyperborea.sira.ws.TipirostCostNost[] tipoRelazioneOstAsChild,
           java.lang.String tipoGeometria,
           java.lang.String visibile) {
        this.categorieOst = categorieOst;
        this.id = id;
        this.natureOst = natureOst;
        this.statioperativiCostNosts = statioperativiCostNosts;
        this.tipoRelazioneOstAsParent = tipoRelazioneOstAsParent;
        this.tipoRelazioneOstAsChild = tipoRelazioneOstAsChild;
        this.tipoGeometria = tipoGeometria;
        this.visibile = visibile;
    }


    /**
     * Gets the categorieOst value for this CostNost.
     * 
     * @return categorieOst
     */
    public com.hyperborea.sira.ws.CategorieOst getCategorieOst() {
        return categorieOst;
    }


    /**
     * Sets the categorieOst value for this CostNost.
     * 
     * @param categorieOst
     */
    public void setCategorieOst(com.hyperborea.sira.ws.CategorieOst categorieOst) {
        this.categorieOst = categorieOst;
    }


    /**
     * Gets the id value for this CostNost.
     * 
     * @return id
     */
    public com.hyperborea.sira.ws.CostNostId getId() {
        return id;
    }


    /**
     * Sets the id value for this CostNost.
     * 
     * @param id
     */
    public void setId(com.hyperborea.sira.ws.CostNostId id) {
        this.id = id;
    }


    /**
     * Gets the natureOst value for this CostNost.
     * 
     * @return natureOst
     */
    public com.hyperborea.sira.ws.NatureOst getNatureOst() {
        return natureOst;
    }


    /**
     * Sets the natureOst value for this CostNost.
     * 
     * @param natureOst
     */
    public void setNatureOst(com.hyperborea.sira.ws.NatureOst natureOst) {
        this.natureOst = natureOst;
    }


    /**
     * Gets the statioperativiCostNosts value for this CostNost.
     * 
     * @return statioperativiCostNosts
     */
    public com.hyperborea.sira.ws.StatioperativiCostNost[] getStatioperativiCostNosts() {
        return statioperativiCostNosts;
    }


    /**
     * Sets the statioperativiCostNosts value for this CostNost.
     * 
     * @param statioperativiCostNosts
     */
    public void setStatioperativiCostNosts(com.hyperborea.sira.ws.StatioperativiCostNost[] statioperativiCostNosts) {
        this.statioperativiCostNosts = statioperativiCostNosts;
    }

    public com.hyperborea.sira.ws.StatioperativiCostNost getStatioperativiCostNosts(int i) {
        return this.statioperativiCostNosts[i];
    }

    public void setStatioperativiCostNosts(int i, com.hyperborea.sira.ws.StatioperativiCostNost _value) {
        this.statioperativiCostNosts[i] = _value;
    }


    /**
     * Gets the tipoRelazioneOstAsParent value for this CostNost.
     * 
     * @return tipoRelazioneOstAsParent
     */
    public com.hyperborea.sira.ws.TipirostCostNost[] getTipoRelazioneOstAsParent() {
        return tipoRelazioneOstAsParent;
    }


    /**
     * Sets the tipoRelazioneOstAsParent value for this CostNost.
     * 
     * @param tipoRelazioneOstAsParent
     */
    public void setTipoRelazioneOstAsParent(com.hyperborea.sira.ws.TipirostCostNost[] tipoRelazioneOstAsParent) {
        this.tipoRelazioneOstAsParent = tipoRelazioneOstAsParent;
    }

    public com.hyperborea.sira.ws.TipirostCostNost getTipoRelazioneOstAsParent(int i) {
        return this.tipoRelazioneOstAsParent[i];
    }

    public void setTipoRelazioneOstAsParent(int i, com.hyperborea.sira.ws.TipirostCostNost _value) {
        this.tipoRelazioneOstAsParent[i] = _value;
    }


    /**
     * Gets the tipoRelazioneOstAsChild value for this CostNost.
     * 
     * @return tipoRelazioneOstAsChild
     */
    public com.hyperborea.sira.ws.TipirostCostNost[] getTipoRelazioneOstAsChild() {
        return tipoRelazioneOstAsChild;
    }


    /**
     * Sets the tipoRelazioneOstAsChild value for this CostNost.
     * 
     * @param tipoRelazioneOstAsChild
     */
    public void setTipoRelazioneOstAsChild(com.hyperborea.sira.ws.TipirostCostNost[] tipoRelazioneOstAsChild) {
        this.tipoRelazioneOstAsChild = tipoRelazioneOstAsChild;
    }

    public com.hyperborea.sira.ws.TipirostCostNost getTipoRelazioneOstAsChild(int i) {
        return this.tipoRelazioneOstAsChild[i];
    }

    public void setTipoRelazioneOstAsChild(int i, com.hyperborea.sira.ws.TipirostCostNost _value) {
        this.tipoRelazioneOstAsChild[i] = _value;
    }


    /**
     * Gets the tipoGeometria value for this CostNost.
     * 
     * @return tipoGeometria
     */
    public java.lang.String getTipoGeometria() {
        return tipoGeometria;
    }


    /**
     * Sets the tipoGeometria value for this CostNost.
     * 
     * @param tipoGeometria
     */
    public void setTipoGeometria(java.lang.String tipoGeometria) {
        this.tipoGeometria = tipoGeometria;
    }


    /**
     * Gets the visibile value for this CostNost.
     * 
     * @return visibile
     */
    public java.lang.String getVisibile() {
        return visibile;
    }


    /**
     * Sets the visibile value for this CostNost.
     * 
     * @param visibile
     */
    public void setVisibile(java.lang.String visibile) {
        this.visibile = visibile;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CostNost)) return false;
        CostNost other = (CostNost) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.categorieOst==null && other.getCategorieOst()==null) || 
             (this.categorieOst!=null &&
              this.categorieOst.equals(other.getCategorieOst()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.natureOst==null && other.getNatureOst()==null) || 
             (this.natureOst!=null &&
              this.natureOst.equals(other.getNatureOst()))) &&
            ((this.statioperativiCostNosts==null && other.getStatioperativiCostNosts()==null) || 
             (this.statioperativiCostNosts!=null &&
              java.util.Arrays.equals(this.statioperativiCostNosts, other.getStatioperativiCostNosts()))) &&
            ((this.tipoRelazioneOstAsParent==null && other.getTipoRelazioneOstAsParent()==null) || 
             (this.tipoRelazioneOstAsParent!=null &&
              java.util.Arrays.equals(this.tipoRelazioneOstAsParent, other.getTipoRelazioneOstAsParent()))) &&
            ((this.tipoRelazioneOstAsChild==null && other.getTipoRelazioneOstAsChild()==null) || 
             (this.tipoRelazioneOstAsChild!=null &&
              java.util.Arrays.equals(this.tipoRelazioneOstAsChild, other.getTipoRelazioneOstAsChild()))) &&
            ((this.tipoGeometria==null && other.getTipoGeometria()==null) || 
             (this.tipoGeometria!=null &&
              this.tipoGeometria.equals(other.getTipoGeometria()))) &&
            ((this.visibile==null && other.getVisibile()==null) || 
             (this.visibile!=null &&
              this.visibile.equals(other.getVisibile())));
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
        if (getCategorieOst() != null) {
            _hashCode += getCategorieOst().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getNatureOst() != null) {
            _hashCode += getNatureOst().hashCode();
        }
        if (getStatioperativiCostNosts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStatioperativiCostNosts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStatioperativiCostNosts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTipoRelazioneOstAsParent() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTipoRelazioneOstAsParent());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTipoRelazioneOstAsParent(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTipoRelazioneOstAsChild() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTipoRelazioneOstAsChild());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTipoRelazioneOstAsChild(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTipoGeometria() != null) {
            _hashCode += getTipoGeometria().hashCode();
        }
        if (getVisibile() != null) {
            _hashCode += getVisibile().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CostNost.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "costNost"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categorieOst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "categorieOst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "categorieOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "costNostId"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("natureOst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "natureOst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "natureOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statioperativiCostNosts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statioperativiCostNosts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "statioperativiCostNost"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoRelazioneOstAsParent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoRelazioneOstAsParent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipirostCostNost"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoRelazioneOstAsChild");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoRelazioneOstAsChild"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipirostCostNost"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoGeometria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoGeometria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("visibile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "visibile"));
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
