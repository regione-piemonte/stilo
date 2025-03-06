/**
 * CcostIgrTrattamentoRaee.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostIgrTrattamentoRaee  implements java.io.Serializable {
    private java.lang.String destPartiSmontate;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.ModStocRifLiqTratRaee[] modStocRifLiqTratRaees;

    private com.hyperborea.sira.ws.ModStocRifNonPerTratRaee[] modStocRifNonPerTratRaees;

    private com.hyperborea.sira.ws.ModStocRifPerTratRaee[] modStocRifPerTratRaees;

    private java.lang.String operazioni;

    private java.lang.Float potenzialitaImpianto;

    private java.lang.Float potenzialitaStoccaggio;

    private com.hyperborea.sira.ws.SettoriIgrTrattamentoRaee[] settoriIgrTrattamentoRaees;

    private java.lang.Float supStoccContaminate;

    private java.lang.Float superficiePavimentata;

    private java.lang.Float superficieTotale;

    private java.lang.String tipoRifiuti;

    public CcostIgrTrattamentoRaee() {
    }

    public CcostIgrTrattamentoRaee(
           java.lang.String destPartiSmontate,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.ModStocRifLiqTratRaee[] modStocRifLiqTratRaees,
           com.hyperborea.sira.ws.ModStocRifNonPerTratRaee[] modStocRifNonPerTratRaees,
           com.hyperborea.sira.ws.ModStocRifPerTratRaee[] modStocRifPerTratRaees,
           java.lang.String operazioni,
           java.lang.Float potenzialitaImpianto,
           java.lang.Float potenzialitaStoccaggio,
           com.hyperborea.sira.ws.SettoriIgrTrattamentoRaee[] settoriIgrTrattamentoRaees,
           java.lang.Float supStoccContaminate,
           java.lang.Float superficiePavimentata,
           java.lang.Float superficieTotale,
           java.lang.String tipoRifiuti) {
           this.destPartiSmontate = destPartiSmontate;
           this.idCcost = idCcost;
           this.modStocRifLiqTratRaees = modStocRifLiqTratRaees;
           this.modStocRifNonPerTratRaees = modStocRifNonPerTratRaees;
           this.modStocRifPerTratRaees = modStocRifPerTratRaees;
           this.operazioni = operazioni;
           this.potenzialitaImpianto = potenzialitaImpianto;
           this.potenzialitaStoccaggio = potenzialitaStoccaggio;
           this.settoriIgrTrattamentoRaees = settoriIgrTrattamentoRaees;
           this.supStoccContaminate = supStoccContaminate;
           this.superficiePavimentata = superficiePavimentata;
           this.superficieTotale = superficieTotale;
           this.tipoRifiuti = tipoRifiuti;
    }


    /**
     * Gets the destPartiSmontate value for this CcostIgrTrattamentoRaee.
     * 
     * @return destPartiSmontate
     */
    public java.lang.String getDestPartiSmontate() {
        return destPartiSmontate;
    }


    /**
     * Sets the destPartiSmontate value for this CcostIgrTrattamentoRaee.
     * 
     * @param destPartiSmontate
     */
    public void setDestPartiSmontate(java.lang.String destPartiSmontate) {
        this.destPartiSmontate = destPartiSmontate;
    }


    /**
     * Gets the idCcost value for this CcostIgrTrattamentoRaee.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostIgrTrattamentoRaee.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the modStocRifLiqTratRaees value for this CcostIgrTrattamentoRaee.
     * 
     * @return modStocRifLiqTratRaees
     */
    public com.hyperborea.sira.ws.ModStocRifLiqTratRaee[] getModStocRifLiqTratRaees() {
        return modStocRifLiqTratRaees;
    }


    /**
     * Sets the modStocRifLiqTratRaees value for this CcostIgrTrattamentoRaee.
     * 
     * @param modStocRifLiqTratRaees
     */
    public void setModStocRifLiqTratRaees(com.hyperborea.sira.ws.ModStocRifLiqTratRaee[] modStocRifLiqTratRaees) {
        this.modStocRifLiqTratRaees = modStocRifLiqTratRaees;
    }

    public com.hyperborea.sira.ws.ModStocRifLiqTratRaee getModStocRifLiqTratRaees(int i) {
        return this.modStocRifLiqTratRaees[i];
    }

    public void setModStocRifLiqTratRaees(int i, com.hyperborea.sira.ws.ModStocRifLiqTratRaee _value) {
        this.modStocRifLiqTratRaees[i] = _value;
    }


    /**
     * Gets the modStocRifNonPerTratRaees value for this CcostIgrTrattamentoRaee.
     * 
     * @return modStocRifNonPerTratRaees
     */
    public com.hyperborea.sira.ws.ModStocRifNonPerTratRaee[] getModStocRifNonPerTratRaees() {
        return modStocRifNonPerTratRaees;
    }


    /**
     * Sets the modStocRifNonPerTratRaees value for this CcostIgrTrattamentoRaee.
     * 
     * @param modStocRifNonPerTratRaees
     */
    public void setModStocRifNonPerTratRaees(com.hyperborea.sira.ws.ModStocRifNonPerTratRaee[] modStocRifNonPerTratRaees) {
        this.modStocRifNonPerTratRaees = modStocRifNonPerTratRaees;
    }

    public com.hyperborea.sira.ws.ModStocRifNonPerTratRaee getModStocRifNonPerTratRaees(int i) {
        return this.modStocRifNonPerTratRaees[i];
    }

    public void setModStocRifNonPerTratRaees(int i, com.hyperborea.sira.ws.ModStocRifNonPerTratRaee _value) {
        this.modStocRifNonPerTratRaees[i] = _value;
    }


    /**
     * Gets the modStocRifPerTratRaees value for this CcostIgrTrattamentoRaee.
     * 
     * @return modStocRifPerTratRaees
     */
    public com.hyperborea.sira.ws.ModStocRifPerTratRaee[] getModStocRifPerTratRaees() {
        return modStocRifPerTratRaees;
    }


    /**
     * Sets the modStocRifPerTratRaees value for this CcostIgrTrattamentoRaee.
     * 
     * @param modStocRifPerTratRaees
     */
    public void setModStocRifPerTratRaees(com.hyperborea.sira.ws.ModStocRifPerTratRaee[] modStocRifPerTratRaees) {
        this.modStocRifPerTratRaees = modStocRifPerTratRaees;
    }

    public com.hyperborea.sira.ws.ModStocRifPerTratRaee getModStocRifPerTratRaees(int i) {
        return this.modStocRifPerTratRaees[i];
    }

    public void setModStocRifPerTratRaees(int i, com.hyperborea.sira.ws.ModStocRifPerTratRaee _value) {
        this.modStocRifPerTratRaees[i] = _value;
    }


    /**
     * Gets the operazioni value for this CcostIgrTrattamentoRaee.
     * 
     * @return operazioni
     */
    public java.lang.String getOperazioni() {
        return operazioni;
    }


    /**
     * Sets the operazioni value for this CcostIgrTrattamentoRaee.
     * 
     * @param operazioni
     */
    public void setOperazioni(java.lang.String operazioni) {
        this.operazioni = operazioni;
    }


    /**
     * Gets the potenzialitaImpianto value for this CcostIgrTrattamentoRaee.
     * 
     * @return potenzialitaImpianto
     */
    public java.lang.Float getPotenzialitaImpianto() {
        return potenzialitaImpianto;
    }


    /**
     * Sets the potenzialitaImpianto value for this CcostIgrTrattamentoRaee.
     * 
     * @param potenzialitaImpianto
     */
    public void setPotenzialitaImpianto(java.lang.Float potenzialitaImpianto) {
        this.potenzialitaImpianto = potenzialitaImpianto;
    }


    /**
     * Gets the potenzialitaStoccaggio value for this CcostIgrTrattamentoRaee.
     * 
     * @return potenzialitaStoccaggio
     */
    public java.lang.Float getPotenzialitaStoccaggio() {
        return potenzialitaStoccaggio;
    }


    /**
     * Sets the potenzialitaStoccaggio value for this CcostIgrTrattamentoRaee.
     * 
     * @param potenzialitaStoccaggio
     */
    public void setPotenzialitaStoccaggio(java.lang.Float potenzialitaStoccaggio) {
        this.potenzialitaStoccaggio = potenzialitaStoccaggio;
    }


    /**
     * Gets the settoriIgrTrattamentoRaees value for this CcostIgrTrattamentoRaee.
     * 
     * @return settoriIgrTrattamentoRaees
     */
    public com.hyperborea.sira.ws.SettoriIgrTrattamentoRaee[] getSettoriIgrTrattamentoRaees() {
        return settoriIgrTrattamentoRaees;
    }


    /**
     * Sets the settoriIgrTrattamentoRaees value for this CcostIgrTrattamentoRaee.
     * 
     * @param settoriIgrTrattamentoRaees
     */
    public void setSettoriIgrTrattamentoRaees(com.hyperborea.sira.ws.SettoriIgrTrattamentoRaee[] settoriIgrTrattamentoRaees) {
        this.settoriIgrTrattamentoRaees = settoriIgrTrattamentoRaees;
    }

    public com.hyperborea.sira.ws.SettoriIgrTrattamentoRaee getSettoriIgrTrattamentoRaees(int i) {
        return this.settoriIgrTrattamentoRaees[i];
    }

    public void setSettoriIgrTrattamentoRaees(int i, com.hyperborea.sira.ws.SettoriIgrTrattamentoRaee _value) {
        this.settoriIgrTrattamentoRaees[i] = _value;
    }


    /**
     * Gets the supStoccContaminate value for this CcostIgrTrattamentoRaee.
     * 
     * @return supStoccContaminate
     */
    public java.lang.Float getSupStoccContaminate() {
        return supStoccContaminate;
    }


    /**
     * Sets the supStoccContaminate value for this CcostIgrTrattamentoRaee.
     * 
     * @param supStoccContaminate
     */
    public void setSupStoccContaminate(java.lang.Float supStoccContaminate) {
        this.supStoccContaminate = supStoccContaminate;
    }


    /**
     * Gets the superficiePavimentata value for this CcostIgrTrattamentoRaee.
     * 
     * @return superficiePavimentata
     */
    public java.lang.Float getSuperficiePavimentata() {
        return superficiePavimentata;
    }


    /**
     * Sets the superficiePavimentata value for this CcostIgrTrattamentoRaee.
     * 
     * @param superficiePavimentata
     */
    public void setSuperficiePavimentata(java.lang.Float superficiePavimentata) {
        this.superficiePavimentata = superficiePavimentata;
    }


    /**
     * Gets the superficieTotale value for this CcostIgrTrattamentoRaee.
     * 
     * @return superficieTotale
     */
    public java.lang.Float getSuperficieTotale() {
        return superficieTotale;
    }


    /**
     * Sets the superficieTotale value for this CcostIgrTrattamentoRaee.
     * 
     * @param superficieTotale
     */
    public void setSuperficieTotale(java.lang.Float superficieTotale) {
        this.superficieTotale = superficieTotale;
    }


    /**
     * Gets the tipoRifiuti value for this CcostIgrTrattamentoRaee.
     * 
     * @return tipoRifiuti
     */
    public java.lang.String getTipoRifiuti() {
        return tipoRifiuti;
    }


    /**
     * Sets the tipoRifiuti value for this CcostIgrTrattamentoRaee.
     * 
     * @param tipoRifiuti
     */
    public void setTipoRifiuti(java.lang.String tipoRifiuti) {
        this.tipoRifiuti = tipoRifiuti;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostIgrTrattamentoRaee)) return false;
        CcostIgrTrattamentoRaee other = (CcostIgrTrattamentoRaee) obj;
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
            ((this.modStocRifLiqTratRaees==null && other.getModStocRifLiqTratRaees()==null) || 
             (this.modStocRifLiqTratRaees!=null &&
              java.util.Arrays.equals(this.modStocRifLiqTratRaees, other.getModStocRifLiqTratRaees()))) &&
            ((this.modStocRifNonPerTratRaees==null && other.getModStocRifNonPerTratRaees()==null) || 
             (this.modStocRifNonPerTratRaees!=null &&
              java.util.Arrays.equals(this.modStocRifNonPerTratRaees, other.getModStocRifNonPerTratRaees()))) &&
            ((this.modStocRifPerTratRaees==null && other.getModStocRifPerTratRaees()==null) || 
             (this.modStocRifPerTratRaees!=null &&
              java.util.Arrays.equals(this.modStocRifPerTratRaees, other.getModStocRifPerTratRaees()))) &&
            ((this.operazioni==null && other.getOperazioni()==null) || 
             (this.operazioni!=null &&
              this.operazioni.equals(other.getOperazioni()))) &&
            ((this.potenzialitaImpianto==null && other.getPotenzialitaImpianto()==null) || 
             (this.potenzialitaImpianto!=null &&
              this.potenzialitaImpianto.equals(other.getPotenzialitaImpianto()))) &&
            ((this.potenzialitaStoccaggio==null && other.getPotenzialitaStoccaggio()==null) || 
             (this.potenzialitaStoccaggio!=null &&
              this.potenzialitaStoccaggio.equals(other.getPotenzialitaStoccaggio()))) &&
            ((this.settoriIgrTrattamentoRaees==null && other.getSettoriIgrTrattamentoRaees()==null) || 
             (this.settoriIgrTrattamentoRaees!=null &&
              java.util.Arrays.equals(this.settoriIgrTrattamentoRaees, other.getSettoriIgrTrattamentoRaees()))) &&
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
        if (getModStocRifLiqTratRaees() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getModStocRifLiqTratRaees());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getModStocRifLiqTratRaees(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getModStocRifNonPerTratRaees() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getModStocRifNonPerTratRaees());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getModStocRifNonPerTratRaees(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getModStocRifPerTratRaees() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getModStocRifPerTratRaees());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getModStocRifPerTratRaees(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getOperazioni() != null) {
            _hashCode += getOperazioni().hashCode();
        }
        if (getPotenzialitaImpianto() != null) {
            _hashCode += getPotenzialitaImpianto().hashCode();
        }
        if (getPotenzialitaStoccaggio() != null) {
            _hashCode += getPotenzialitaStoccaggio().hashCode();
        }
        if (getSettoriIgrTrattamentoRaees() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSettoriIgrTrattamentoRaees());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSettoriIgrTrattamentoRaees(), i);
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
        new org.apache.axis.description.TypeDesc(CcostIgrTrattamentoRaee.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrTrattamentoRaee"));
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
        elemField.setFieldName("modStocRifLiqTratRaees");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modStocRifLiqTratRaees"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifLiqTratRaee"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modStocRifNonPerTratRaees");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modStocRifNonPerTratRaees"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifNonPerTratRaee"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modStocRifPerTratRaees");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modStocRifPerTratRaees"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifPerTratRaee"));
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
        elemField.setFieldName("potenzialitaImpianto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzialitaImpianto"));
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
        elemField.setFieldName("settoriIgrTrattamentoRaees");
        elemField.setXmlName(new javax.xml.namespace.QName("", "settoriIgrTrattamentoRaees"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "settoriIgrTrattamentoRaee"));
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
