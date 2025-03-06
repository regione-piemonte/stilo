/**
 * CcostIgrAutodemolizione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostIgrAutodemolizione  implements java.io.Serializable {
    private java.lang.String destPartiSmontate;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.ModStocRifLiquidiAutodem[] modStocRifLiquidiAutodems;

    private com.hyperborea.sira.ws.ModStocRifNonPerAutodem[] modStocRifNonPerAutodems;

    private com.hyperborea.sira.ws.ModStocRifPerAutodem[] modStocRifPerAutodems;

    private java.lang.String operazioni;

    private java.lang.Float potenzialitaBonificati;

    private java.lang.Float potenzialitaNonBonificati;

    private java.lang.Float potenzialitaStoccaggio;

    private com.hyperborea.sira.ws.SettoriIgrAutodemolizione[] settoriIgrAutodemoliziones;

    private java.lang.Float supStoccContaminate;

    private java.lang.Float superficiePavimentata;

    private java.lang.Float superficieTotale;

    private java.lang.String tipoRifiuti;

    public CcostIgrAutodemolizione() {
    }

    public CcostIgrAutodemolizione(
           java.lang.String destPartiSmontate,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.ModStocRifLiquidiAutodem[] modStocRifLiquidiAutodems,
           com.hyperborea.sira.ws.ModStocRifNonPerAutodem[] modStocRifNonPerAutodems,
           com.hyperborea.sira.ws.ModStocRifPerAutodem[] modStocRifPerAutodems,
           java.lang.String operazioni,
           java.lang.Float potenzialitaBonificati,
           java.lang.Float potenzialitaNonBonificati,
           java.lang.Float potenzialitaStoccaggio,
           com.hyperborea.sira.ws.SettoriIgrAutodemolizione[] settoriIgrAutodemoliziones,
           java.lang.Float supStoccContaminate,
           java.lang.Float superficiePavimentata,
           java.lang.Float superficieTotale,
           java.lang.String tipoRifiuti) {
           this.destPartiSmontate = destPartiSmontate;
           this.idCcost = idCcost;
           this.modStocRifLiquidiAutodems = modStocRifLiquidiAutodems;
           this.modStocRifNonPerAutodems = modStocRifNonPerAutodems;
           this.modStocRifPerAutodems = modStocRifPerAutodems;
           this.operazioni = operazioni;
           this.potenzialitaBonificati = potenzialitaBonificati;
           this.potenzialitaNonBonificati = potenzialitaNonBonificati;
           this.potenzialitaStoccaggio = potenzialitaStoccaggio;
           this.settoriIgrAutodemoliziones = settoriIgrAutodemoliziones;
           this.supStoccContaminate = supStoccContaminate;
           this.superficiePavimentata = superficiePavimentata;
           this.superficieTotale = superficieTotale;
           this.tipoRifiuti = tipoRifiuti;
    }


    /**
     * Gets the destPartiSmontate value for this CcostIgrAutodemolizione.
     * 
     * @return destPartiSmontate
     */
    public java.lang.String getDestPartiSmontate() {
        return destPartiSmontate;
    }


    /**
     * Sets the destPartiSmontate value for this CcostIgrAutodemolizione.
     * 
     * @param destPartiSmontate
     */
    public void setDestPartiSmontate(java.lang.String destPartiSmontate) {
        this.destPartiSmontate = destPartiSmontate;
    }


    /**
     * Gets the idCcost value for this CcostIgrAutodemolizione.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostIgrAutodemolizione.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the modStocRifLiquidiAutodems value for this CcostIgrAutodemolizione.
     * 
     * @return modStocRifLiquidiAutodems
     */
    public com.hyperborea.sira.ws.ModStocRifLiquidiAutodem[] getModStocRifLiquidiAutodems() {
        return modStocRifLiquidiAutodems;
    }


    /**
     * Sets the modStocRifLiquidiAutodems value for this CcostIgrAutodemolizione.
     * 
     * @param modStocRifLiquidiAutodems
     */
    public void setModStocRifLiquidiAutodems(com.hyperborea.sira.ws.ModStocRifLiquidiAutodem[] modStocRifLiquidiAutodems) {
        this.modStocRifLiquidiAutodems = modStocRifLiquidiAutodems;
    }

    public com.hyperborea.sira.ws.ModStocRifLiquidiAutodem getModStocRifLiquidiAutodems(int i) {
        return this.modStocRifLiquidiAutodems[i];
    }

    public void setModStocRifLiquidiAutodems(int i, com.hyperborea.sira.ws.ModStocRifLiquidiAutodem _value) {
        this.modStocRifLiquidiAutodems[i] = _value;
    }


    /**
     * Gets the modStocRifNonPerAutodems value for this CcostIgrAutodemolizione.
     * 
     * @return modStocRifNonPerAutodems
     */
    public com.hyperborea.sira.ws.ModStocRifNonPerAutodem[] getModStocRifNonPerAutodems() {
        return modStocRifNonPerAutodems;
    }


    /**
     * Sets the modStocRifNonPerAutodems value for this CcostIgrAutodemolizione.
     * 
     * @param modStocRifNonPerAutodems
     */
    public void setModStocRifNonPerAutodems(com.hyperborea.sira.ws.ModStocRifNonPerAutodem[] modStocRifNonPerAutodems) {
        this.modStocRifNonPerAutodems = modStocRifNonPerAutodems;
    }

    public com.hyperborea.sira.ws.ModStocRifNonPerAutodem getModStocRifNonPerAutodems(int i) {
        return this.modStocRifNonPerAutodems[i];
    }

    public void setModStocRifNonPerAutodems(int i, com.hyperborea.sira.ws.ModStocRifNonPerAutodem _value) {
        this.modStocRifNonPerAutodems[i] = _value;
    }


    /**
     * Gets the modStocRifPerAutodems value for this CcostIgrAutodemolizione.
     * 
     * @return modStocRifPerAutodems
     */
    public com.hyperborea.sira.ws.ModStocRifPerAutodem[] getModStocRifPerAutodems() {
        return modStocRifPerAutodems;
    }


    /**
     * Sets the modStocRifPerAutodems value for this CcostIgrAutodemolizione.
     * 
     * @param modStocRifPerAutodems
     */
    public void setModStocRifPerAutodems(com.hyperborea.sira.ws.ModStocRifPerAutodem[] modStocRifPerAutodems) {
        this.modStocRifPerAutodems = modStocRifPerAutodems;
    }

    public com.hyperborea.sira.ws.ModStocRifPerAutodem getModStocRifPerAutodems(int i) {
        return this.modStocRifPerAutodems[i];
    }

    public void setModStocRifPerAutodems(int i, com.hyperborea.sira.ws.ModStocRifPerAutodem _value) {
        this.modStocRifPerAutodems[i] = _value;
    }


    /**
     * Gets the operazioni value for this CcostIgrAutodemolizione.
     * 
     * @return operazioni
     */
    public java.lang.String getOperazioni() {
        return operazioni;
    }


    /**
     * Sets the operazioni value for this CcostIgrAutodemolizione.
     * 
     * @param operazioni
     */
    public void setOperazioni(java.lang.String operazioni) {
        this.operazioni = operazioni;
    }


    /**
     * Gets the potenzialitaBonificati value for this CcostIgrAutodemolizione.
     * 
     * @return potenzialitaBonificati
     */
    public java.lang.Float getPotenzialitaBonificati() {
        return potenzialitaBonificati;
    }


    /**
     * Sets the potenzialitaBonificati value for this CcostIgrAutodemolizione.
     * 
     * @param potenzialitaBonificati
     */
    public void setPotenzialitaBonificati(java.lang.Float potenzialitaBonificati) {
        this.potenzialitaBonificati = potenzialitaBonificati;
    }


    /**
     * Gets the potenzialitaNonBonificati value for this CcostIgrAutodemolizione.
     * 
     * @return potenzialitaNonBonificati
     */
    public java.lang.Float getPotenzialitaNonBonificati() {
        return potenzialitaNonBonificati;
    }


    /**
     * Sets the potenzialitaNonBonificati value for this CcostIgrAutodemolizione.
     * 
     * @param potenzialitaNonBonificati
     */
    public void setPotenzialitaNonBonificati(java.lang.Float potenzialitaNonBonificati) {
        this.potenzialitaNonBonificati = potenzialitaNonBonificati;
    }


    /**
     * Gets the potenzialitaStoccaggio value for this CcostIgrAutodemolizione.
     * 
     * @return potenzialitaStoccaggio
     */
    public java.lang.Float getPotenzialitaStoccaggio() {
        return potenzialitaStoccaggio;
    }


    /**
     * Sets the potenzialitaStoccaggio value for this CcostIgrAutodemolizione.
     * 
     * @param potenzialitaStoccaggio
     */
    public void setPotenzialitaStoccaggio(java.lang.Float potenzialitaStoccaggio) {
        this.potenzialitaStoccaggio = potenzialitaStoccaggio;
    }


    /**
     * Gets the settoriIgrAutodemoliziones value for this CcostIgrAutodemolizione.
     * 
     * @return settoriIgrAutodemoliziones
     */
    public com.hyperborea.sira.ws.SettoriIgrAutodemolizione[] getSettoriIgrAutodemoliziones() {
        return settoriIgrAutodemoliziones;
    }


    /**
     * Sets the settoriIgrAutodemoliziones value for this CcostIgrAutodemolizione.
     * 
     * @param settoriIgrAutodemoliziones
     */
    public void setSettoriIgrAutodemoliziones(com.hyperborea.sira.ws.SettoriIgrAutodemolizione[] settoriIgrAutodemoliziones) {
        this.settoriIgrAutodemoliziones = settoriIgrAutodemoliziones;
    }

    public com.hyperborea.sira.ws.SettoriIgrAutodemolizione getSettoriIgrAutodemoliziones(int i) {
        return this.settoriIgrAutodemoliziones[i];
    }

    public void setSettoriIgrAutodemoliziones(int i, com.hyperborea.sira.ws.SettoriIgrAutodemolizione _value) {
        this.settoriIgrAutodemoliziones[i] = _value;
    }


    /**
     * Gets the supStoccContaminate value for this CcostIgrAutodemolizione.
     * 
     * @return supStoccContaminate
     */
    public java.lang.Float getSupStoccContaminate() {
        return supStoccContaminate;
    }


    /**
     * Sets the supStoccContaminate value for this CcostIgrAutodemolizione.
     * 
     * @param supStoccContaminate
     */
    public void setSupStoccContaminate(java.lang.Float supStoccContaminate) {
        this.supStoccContaminate = supStoccContaminate;
    }


    /**
     * Gets the superficiePavimentata value for this CcostIgrAutodemolizione.
     * 
     * @return superficiePavimentata
     */
    public java.lang.Float getSuperficiePavimentata() {
        return superficiePavimentata;
    }


    /**
     * Sets the superficiePavimentata value for this CcostIgrAutodemolizione.
     * 
     * @param superficiePavimentata
     */
    public void setSuperficiePavimentata(java.lang.Float superficiePavimentata) {
        this.superficiePavimentata = superficiePavimentata;
    }


    /**
     * Gets the superficieTotale value for this CcostIgrAutodemolizione.
     * 
     * @return superficieTotale
     */
    public java.lang.Float getSuperficieTotale() {
        return superficieTotale;
    }


    /**
     * Sets the superficieTotale value for this CcostIgrAutodemolizione.
     * 
     * @param superficieTotale
     */
    public void setSuperficieTotale(java.lang.Float superficieTotale) {
        this.superficieTotale = superficieTotale;
    }


    /**
     * Gets the tipoRifiuti value for this CcostIgrAutodemolizione.
     * 
     * @return tipoRifiuti
     */
    public java.lang.String getTipoRifiuti() {
        return tipoRifiuti;
    }


    /**
     * Sets the tipoRifiuti value for this CcostIgrAutodemolizione.
     * 
     * @param tipoRifiuti
     */
    public void setTipoRifiuti(java.lang.String tipoRifiuti) {
        this.tipoRifiuti = tipoRifiuti;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostIgrAutodemolizione)) return false;
        CcostIgrAutodemolizione other = (CcostIgrAutodemolizione) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.destPartiSmontate==null && other.getDestPartiSmontate()==null) || 
             (this.destPartiSmontate!=null &&
              this.destPartiSmontate.equals(other.getDestPartiSmontate()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.modStocRifLiquidiAutodems==null && other.getModStocRifLiquidiAutodems()==null) || 
             (this.modStocRifLiquidiAutodems!=null &&
              java.util.Arrays.equals(this.modStocRifLiquidiAutodems, other.getModStocRifLiquidiAutodems()))) &&
            ((this.modStocRifNonPerAutodems==null && other.getModStocRifNonPerAutodems()==null) || 
             (this.modStocRifNonPerAutodems!=null &&
              java.util.Arrays.equals(this.modStocRifNonPerAutodems, other.getModStocRifNonPerAutodems()))) &&
            ((this.modStocRifPerAutodems==null && other.getModStocRifPerAutodems()==null) || 
             (this.modStocRifPerAutodems!=null &&
              java.util.Arrays.equals(this.modStocRifPerAutodems, other.getModStocRifPerAutodems()))) &&
            ((this.operazioni==null && other.getOperazioni()==null) || 
             (this.operazioni!=null &&
              this.operazioni.equals(other.getOperazioni()))) &&
            ((this.potenzialitaBonificati==null && other.getPotenzialitaBonificati()==null) || 
             (this.potenzialitaBonificati!=null &&
              this.potenzialitaBonificati.equals(other.getPotenzialitaBonificati()))) &&
            ((this.potenzialitaNonBonificati==null && other.getPotenzialitaNonBonificati()==null) || 
             (this.potenzialitaNonBonificati!=null &&
              this.potenzialitaNonBonificati.equals(other.getPotenzialitaNonBonificati()))) &&
            ((this.potenzialitaStoccaggio==null && other.getPotenzialitaStoccaggio()==null) || 
             (this.potenzialitaStoccaggio!=null &&
              this.potenzialitaStoccaggio.equals(other.getPotenzialitaStoccaggio()))) &&
            ((this.settoriIgrAutodemoliziones==null && other.getSettoriIgrAutodemoliziones()==null) || 
             (this.settoriIgrAutodemoliziones!=null &&
              java.util.Arrays.equals(this.settoriIgrAutodemoliziones, other.getSettoriIgrAutodemoliziones()))) &&
            ((this.supStoccContaminate==null && other.getSupStoccContaminate()==null) || 
             (this.supStoccContaminate!=null &&
              this.supStoccContaminate.equals(other.getSupStoccContaminate()))) &&
            ((this.superficiePavimentata==null && other.getSuperficiePavimentata()==null) || 
             (this.superficiePavimentata!=null &&
              this.superficiePavimentata.equals(other.getSuperficiePavimentata()))) &&
            ((this.superficieTotale==null && other.getSuperficieTotale()==null) || 
             (this.superficieTotale!=null &&
              this.superficieTotale.equals(other.getSuperficieTotale()))) &&
            ((this.tipoRifiuti==null && other.getTipoRifiuti()==null) || 
             (this.tipoRifiuti!=null &&
              this.tipoRifiuti.equals(other.getTipoRifiuti())));
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
        if (getDestPartiSmontate() != null) {
            _hashCode += getDestPartiSmontate().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getModStocRifLiquidiAutodems() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getModStocRifLiquidiAutodems());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getModStocRifLiquidiAutodems(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getModStocRifNonPerAutodems() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getModStocRifNonPerAutodems());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getModStocRifNonPerAutodems(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getModStocRifPerAutodems() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getModStocRifPerAutodems());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getModStocRifPerAutodems(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getOperazioni() != null) {
            _hashCode += getOperazioni().hashCode();
        }
        if (getPotenzialitaBonificati() != null) {
            _hashCode += getPotenzialitaBonificati().hashCode();
        }
        if (getPotenzialitaNonBonificati() != null) {
            _hashCode += getPotenzialitaNonBonificati().hashCode();
        }
        if (getPotenzialitaStoccaggio() != null) {
            _hashCode += getPotenzialitaStoccaggio().hashCode();
        }
        if (getSettoriIgrAutodemoliziones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSettoriIgrAutodemoliziones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSettoriIgrAutodemoliziones(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSupStoccContaminate() != null) {
            _hashCode += getSupStoccContaminate().hashCode();
        }
        if (getSuperficiePavimentata() != null) {
            _hashCode += getSuperficiePavimentata().hashCode();
        }
        if (getSuperficieTotale() != null) {
            _hashCode += getSuperficieTotale().hashCode();
        }
        if (getTipoRifiuti() != null) {
            _hashCode += getTipoRifiuti().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostIgrAutodemolizione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrAutodemolizione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destPartiSmontate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destPartiSmontate"));
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
        elemField.setFieldName("modStocRifLiquidiAutodems");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modStocRifLiquidiAutodems"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifLiquidiAutodem"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modStocRifNonPerAutodems");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modStocRifNonPerAutodems"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifNonPerAutodem"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modStocRifPerAutodems");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modStocRifPerAutodems"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifPerAutodem"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operazioni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "operazioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzialitaBonificati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzialitaBonificati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzialitaNonBonificati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzialitaNonBonificati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzialitaStoccaggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzialitaStoccaggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("settoriIgrAutodemoliziones");
        elemField.setXmlName(new javax.xml.namespace.QName("", "settoriIgrAutodemoliziones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "settoriIgrAutodemolizione"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supStoccContaminate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "supStoccContaminate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
        elemField.setFieldName("superficieTotale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieTotale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoRifiuti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoRifiuti"));
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
