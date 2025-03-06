/**
 * CcostFrantoiOleari.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostFrantoiOleari  implements java.io.Serializable {
    private java.lang.Float capacitaMagazzOli;

    private java.lang.Float capacitaPotenzEff;

    private java.lang.Float capacitaUnitariaCaricoOlive;

    private com.hyperborea.sira.ws.ContStoccaggio[] contStoccaggios;

    private java.util.Calendar fineCampagna;

    private java.lang.Integer idCcost;

    private java.util.Calendar inizioCampagna;

    private java.lang.String lineeLavBiologico;

    private java.lang.Integer modificheCampagnaPrec;

    private java.lang.Float potProduttiva;

    private java.lang.Float prodMedSanse;

    private java.lang.Float prodStimAcque;

    private java.lang.Float prodStimSanse;

    private java.lang.Float totAcquaOttenuta;

    private java.lang.Float totOlioOttenuto;

    private java.lang.Float totOliveEntrate;

    private java.lang.Float totOliveMolite;

    private java.lang.Float totSansaOttenuta;

    private com.hyperborea.sira.ws.VocFrTipoCicloLav vocFrTipoCicloLav;

    public CcostFrantoiOleari() {
    }

    public CcostFrantoiOleari(
           java.lang.Float capacitaMagazzOli,
           java.lang.Float capacitaPotenzEff,
           java.lang.Float capacitaUnitariaCaricoOlive,
           com.hyperborea.sira.ws.ContStoccaggio[] contStoccaggios,
           java.util.Calendar fineCampagna,
           java.lang.Integer idCcost,
           java.util.Calendar inizioCampagna,
           java.lang.String lineeLavBiologico,
           java.lang.Integer modificheCampagnaPrec,
           java.lang.Float potProduttiva,
           java.lang.Float prodMedSanse,
           java.lang.Float prodStimAcque,
           java.lang.Float prodStimSanse,
           java.lang.Float totAcquaOttenuta,
           java.lang.Float totOlioOttenuto,
           java.lang.Float totOliveEntrate,
           java.lang.Float totOliveMolite,
           java.lang.Float totSansaOttenuta,
           com.hyperborea.sira.ws.VocFrTipoCicloLav vocFrTipoCicloLav) {
           this.capacitaMagazzOli = capacitaMagazzOli;
           this.capacitaPotenzEff = capacitaPotenzEff;
           this.capacitaUnitariaCaricoOlive = capacitaUnitariaCaricoOlive;
           this.contStoccaggios = contStoccaggios;
           this.fineCampagna = fineCampagna;
           this.idCcost = idCcost;
           this.inizioCampagna = inizioCampagna;
           this.lineeLavBiologico = lineeLavBiologico;
           this.modificheCampagnaPrec = modificheCampagnaPrec;
           this.potProduttiva = potProduttiva;
           this.prodMedSanse = prodMedSanse;
           this.prodStimAcque = prodStimAcque;
           this.prodStimSanse = prodStimSanse;
           this.totAcquaOttenuta = totAcquaOttenuta;
           this.totOlioOttenuto = totOlioOttenuto;
           this.totOliveEntrate = totOliveEntrate;
           this.totOliveMolite = totOliveMolite;
           this.totSansaOttenuta = totSansaOttenuta;
           this.vocFrTipoCicloLav = vocFrTipoCicloLav;
    }


    /**
     * Gets the capacitaMagazzOli value for this CcostFrantoiOleari.
     * 
     * @return capacitaMagazzOli
     */
    public java.lang.Float getCapacitaMagazzOli() {
        return capacitaMagazzOli;
    }


    /**
     * Sets the capacitaMagazzOli value for this CcostFrantoiOleari.
     * 
     * @param capacitaMagazzOli
     */
    public void setCapacitaMagazzOli(java.lang.Float capacitaMagazzOli) {
        this.capacitaMagazzOli = capacitaMagazzOli;
    }


    /**
     * Gets the capacitaPotenzEff value for this CcostFrantoiOleari.
     * 
     * @return capacitaPotenzEff
     */
    public java.lang.Float getCapacitaPotenzEff() {
        return capacitaPotenzEff;
    }


    /**
     * Sets the capacitaPotenzEff value for this CcostFrantoiOleari.
     * 
     * @param capacitaPotenzEff
     */
    public void setCapacitaPotenzEff(java.lang.Float capacitaPotenzEff) {
        this.capacitaPotenzEff = capacitaPotenzEff;
    }


    /**
     * Gets the capacitaUnitariaCaricoOlive value for this CcostFrantoiOleari.
     * 
     * @return capacitaUnitariaCaricoOlive
     */
    public java.lang.Float getCapacitaUnitariaCaricoOlive() {
        return capacitaUnitariaCaricoOlive;
    }


    /**
     * Sets the capacitaUnitariaCaricoOlive value for this CcostFrantoiOleari.
     * 
     * @param capacitaUnitariaCaricoOlive
     */
    public void setCapacitaUnitariaCaricoOlive(java.lang.Float capacitaUnitariaCaricoOlive) {
        this.capacitaUnitariaCaricoOlive = capacitaUnitariaCaricoOlive;
    }


    /**
     * Gets the contStoccaggios value for this CcostFrantoiOleari.
     * 
     * @return contStoccaggios
     */
    public com.hyperborea.sira.ws.ContStoccaggio[] getContStoccaggios() {
        return contStoccaggios;
    }


    /**
     * Sets the contStoccaggios value for this CcostFrantoiOleari.
     * 
     * @param contStoccaggios
     */
    public void setContStoccaggios(com.hyperborea.sira.ws.ContStoccaggio[] contStoccaggios) {
        this.contStoccaggios = contStoccaggios;
    }

    public com.hyperborea.sira.ws.ContStoccaggio getContStoccaggios(int i) {
        return this.contStoccaggios[i];
    }

    public void setContStoccaggios(int i, com.hyperborea.sira.ws.ContStoccaggio _value) {
        this.contStoccaggios[i] = _value;
    }


    /**
     * Gets the fineCampagna value for this CcostFrantoiOleari.
     * 
     * @return fineCampagna
     */
    public java.util.Calendar getFineCampagna() {
        return fineCampagna;
    }


    /**
     * Sets the fineCampagna value for this CcostFrantoiOleari.
     * 
     * @param fineCampagna
     */
    public void setFineCampagna(java.util.Calendar fineCampagna) {
        this.fineCampagna = fineCampagna;
    }


    /**
     * Gets the idCcost value for this CcostFrantoiOleari.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostFrantoiOleari.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the inizioCampagna value for this CcostFrantoiOleari.
     * 
     * @return inizioCampagna
     */
    public java.util.Calendar getInizioCampagna() {
        return inizioCampagna;
    }


    /**
     * Sets the inizioCampagna value for this CcostFrantoiOleari.
     * 
     * @param inizioCampagna
     */
    public void setInizioCampagna(java.util.Calendar inizioCampagna) {
        this.inizioCampagna = inizioCampagna;
    }


    /**
     * Gets the lineeLavBiologico value for this CcostFrantoiOleari.
     * 
     * @return lineeLavBiologico
     */
    public java.lang.String getLineeLavBiologico() {
        return lineeLavBiologico;
    }


    /**
     * Sets the lineeLavBiologico value for this CcostFrantoiOleari.
     * 
     * @param lineeLavBiologico
     */
    public void setLineeLavBiologico(java.lang.String lineeLavBiologico) {
        this.lineeLavBiologico = lineeLavBiologico;
    }


    /**
     * Gets the modificheCampagnaPrec value for this CcostFrantoiOleari.
     * 
     * @return modificheCampagnaPrec
     */
    public java.lang.Integer getModificheCampagnaPrec() {
        return modificheCampagnaPrec;
    }


    /**
     * Sets the modificheCampagnaPrec value for this CcostFrantoiOleari.
     * 
     * @param modificheCampagnaPrec
     */
    public void setModificheCampagnaPrec(java.lang.Integer modificheCampagnaPrec) {
        this.modificheCampagnaPrec = modificheCampagnaPrec;
    }


    /**
     * Gets the potProduttiva value for this CcostFrantoiOleari.
     * 
     * @return potProduttiva
     */
    public java.lang.Float getPotProduttiva() {
        return potProduttiva;
    }


    /**
     * Sets the potProduttiva value for this CcostFrantoiOleari.
     * 
     * @param potProduttiva
     */
    public void setPotProduttiva(java.lang.Float potProduttiva) {
        this.potProduttiva = potProduttiva;
    }


    /**
     * Gets the prodMedSanse value for this CcostFrantoiOleari.
     * 
     * @return prodMedSanse
     */
    public java.lang.Float getProdMedSanse() {
        return prodMedSanse;
    }


    /**
     * Sets the prodMedSanse value for this CcostFrantoiOleari.
     * 
     * @param prodMedSanse
     */
    public void setProdMedSanse(java.lang.Float prodMedSanse) {
        this.prodMedSanse = prodMedSanse;
    }


    /**
     * Gets the prodStimAcque value for this CcostFrantoiOleari.
     * 
     * @return prodStimAcque
     */
    public java.lang.Float getProdStimAcque() {
        return prodStimAcque;
    }


    /**
     * Sets the prodStimAcque value for this CcostFrantoiOleari.
     * 
     * @param prodStimAcque
     */
    public void setProdStimAcque(java.lang.Float prodStimAcque) {
        this.prodStimAcque = prodStimAcque;
    }


    /**
     * Gets the prodStimSanse value for this CcostFrantoiOleari.
     * 
     * @return prodStimSanse
     */
    public java.lang.Float getProdStimSanse() {
        return prodStimSanse;
    }


    /**
     * Sets the prodStimSanse value for this CcostFrantoiOleari.
     * 
     * @param prodStimSanse
     */
    public void setProdStimSanse(java.lang.Float prodStimSanse) {
        this.prodStimSanse = prodStimSanse;
    }


    /**
     * Gets the totAcquaOttenuta value for this CcostFrantoiOleari.
     * 
     * @return totAcquaOttenuta
     */
    public java.lang.Float getTotAcquaOttenuta() {
        return totAcquaOttenuta;
    }


    /**
     * Sets the totAcquaOttenuta value for this CcostFrantoiOleari.
     * 
     * @param totAcquaOttenuta
     */
    public void setTotAcquaOttenuta(java.lang.Float totAcquaOttenuta) {
        this.totAcquaOttenuta = totAcquaOttenuta;
    }


    /**
     * Gets the totOlioOttenuto value for this CcostFrantoiOleari.
     * 
     * @return totOlioOttenuto
     */
    public java.lang.Float getTotOlioOttenuto() {
        return totOlioOttenuto;
    }


    /**
     * Sets the totOlioOttenuto value for this CcostFrantoiOleari.
     * 
     * @param totOlioOttenuto
     */
    public void setTotOlioOttenuto(java.lang.Float totOlioOttenuto) {
        this.totOlioOttenuto = totOlioOttenuto;
    }


    /**
     * Gets the totOliveEntrate value for this CcostFrantoiOleari.
     * 
     * @return totOliveEntrate
     */
    public java.lang.Float getTotOliveEntrate() {
        return totOliveEntrate;
    }


    /**
     * Sets the totOliveEntrate value for this CcostFrantoiOleari.
     * 
     * @param totOliveEntrate
     */
    public void setTotOliveEntrate(java.lang.Float totOliveEntrate) {
        this.totOliveEntrate = totOliveEntrate;
    }


    /**
     * Gets the totOliveMolite value for this CcostFrantoiOleari.
     * 
     * @return totOliveMolite
     */
    public java.lang.Float getTotOliveMolite() {
        return totOliveMolite;
    }


    /**
     * Sets the totOliveMolite value for this CcostFrantoiOleari.
     * 
     * @param totOliveMolite
     */
    public void setTotOliveMolite(java.lang.Float totOliveMolite) {
        this.totOliveMolite = totOliveMolite;
    }


    /**
     * Gets the totSansaOttenuta value for this CcostFrantoiOleari.
     * 
     * @return totSansaOttenuta
     */
    public java.lang.Float getTotSansaOttenuta() {
        return totSansaOttenuta;
    }


    /**
     * Sets the totSansaOttenuta value for this CcostFrantoiOleari.
     * 
     * @param totSansaOttenuta
     */
    public void setTotSansaOttenuta(java.lang.Float totSansaOttenuta) {
        this.totSansaOttenuta = totSansaOttenuta;
    }


    /**
     * Gets the vocFrTipoCicloLav value for this CcostFrantoiOleari.
     * 
     * @return vocFrTipoCicloLav
     */
    public com.hyperborea.sira.ws.VocFrTipoCicloLav getVocFrTipoCicloLav() {
        return vocFrTipoCicloLav;
    }


    /**
     * Sets the vocFrTipoCicloLav value for this CcostFrantoiOleari.
     * 
     * @param vocFrTipoCicloLav
     */
    public void setVocFrTipoCicloLav(com.hyperborea.sira.ws.VocFrTipoCicloLav vocFrTipoCicloLav) {
        this.vocFrTipoCicloLav = vocFrTipoCicloLav;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostFrantoiOleari)) return false;
        CcostFrantoiOleari other = (CcostFrantoiOleari) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.capacitaMagazzOli==null && other.getCapacitaMagazzOli()==null) || 
             (this.capacitaMagazzOli!=null &&
              this.capacitaMagazzOli.equals(other.getCapacitaMagazzOli()))) &&
            ((this.capacitaPotenzEff==null && other.getCapacitaPotenzEff()==null) || 
             (this.capacitaPotenzEff!=null &&
              this.capacitaPotenzEff.equals(other.getCapacitaPotenzEff()))) &&
            ((this.capacitaUnitariaCaricoOlive==null && other.getCapacitaUnitariaCaricoOlive()==null) || 
             (this.capacitaUnitariaCaricoOlive!=null &&
              this.capacitaUnitariaCaricoOlive.equals(other.getCapacitaUnitariaCaricoOlive()))) &&
            ((this.contStoccaggios==null && other.getContStoccaggios()==null) || 
             (this.contStoccaggios!=null &&
              java.util.Arrays.equals(this.contStoccaggios, other.getContStoccaggios()))) &&
            ((this.fineCampagna==null && other.getFineCampagna()==null) || 
             (this.fineCampagna!=null &&
              this.fineCampagna.equals(other.getFineCampagna()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.inizioCampagna==null && other.getInizioCampagna()==null) || 
             (this.inizioCampagna!=null &&
              this.inizioCampagna.equals(other.getInizioCampagna()))) &&
            ((this.lineeLavBiologico==null && other.getLineeLavBiologico()==null) || 
             (this.lineeLavBiologico!=null &&
              this.lineeLavBiologico.equals(other.getLineeLavBiologico()))) &&
            ((this.modificheCampagnaPrec==null && other.getModificheCampagnaPrec()==null) || 
             (this.modificheCampagnaPrec!=null &&
              this.modificheCampagnaPrec.equals(other.getModificheCampagnaPrec()))) &&
            ((this.potProduttiva==null && other.getPotProduttiva()==null) || 
             (this.potProduttiva!=null &&
              this.potProduttiva.equals(other.getPotProduttiva()))) &&
            ((this.prodMedSanse==null && other.getProdMedSanse()==null) || 
             (this.prodMedSanse!=null &&
              this.prodMedSanse.equals(other.getProdMedSanse()))) &&
            ((this.prodStimAcque==null && other.getProdStimAcque()==null) || 
             (this.prodStimAcque!=null &&
              this.prodStimAcque.equals(other.getProdStimAcque()))) &&
            ((this.prodStimSanse==null && other.getProdStimSanse()==null) || 
             (this.prodStimSanse!=null &&
              this.prodStimSanse.equals(other.getProdStimSanse()))) &&
            ((this.totAcquaOttenuta==null && other.getTotAcquaOttenuta()==null) || 
             (this.totAcquaOttenuta!=null &&
              this.totAcquaOttenuta.equals(other.getTotAcquaOttenuta()))) &&
            ((this.totOlioOttenuto==null && other.getTotOlioOttenuto()==null) || 
             (this.totOlioOttenuto!=null &&
              this.totOlioOttenuto.equals(other.getTotOlioOttenuto()))) &&
            ((this.totOliveEntrate==null && other.getTotOliveEntrate()==null) || 
             (this.totOliveEntrate!=null &&
              this.totOliveEntrate.equals(other.getTotOliveEntrate()))) &&
            ((this.totOliveMolite==null && other.getTotOliveMolite()==null) || 
             (this.totOliveMolite!=null &&
              this.totOliveMolite.equals(other.getTotOliveMolite()))) &&
            ((this.totSansaOttenuta==null && other.getTotSansaOttenuta()==null) || 
             (this.totSansaOttenuta!=null &&
              this.totSansaOttenuta.equals(other.getTotSansaOttenuta()))) &&
            ((this.vocFrTipoCicloLav==null && other.getVocFrTipoCicloLav()==null) || 
             (this.vocFrTipoCicloLav!=null &&
              this.vocFrTipoCicloLav.equals(other.getVocFrTipoCicloLav())));
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
        if (getCapacitaMagazzOli() != null) {
            _hashCode += getCapacitaMagazzOli().hashCode();
        }
        if (getCapacitaPotenzEff() != null) {
            _hashCode += getCapacitaPotenzEff().hashCode();
        }
        if (getCapacitaUnitariaCaricoOlive() != null) {
            _hashCode += getCapacitaUnitariaCaricoOlive().hashCode();
        }
        if (getContStoccaggios() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getContStoccaggios());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getContStoccaggios(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFineCampagna() != null) {
            _hashCode += getFineCampagna().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getInizioCampagna() != null) {
            _hashCode += getInizioCampagna().hashCode();
        }
        if (getLineeLavBiologico() != null) {
            _hashCode += getLineeLavBiologico().hashCode();
        }
        if (getModificheCampagnaPrec() != null) {
            _hashCode += getModificheCampagnaPrec().hashCode();
        }
        if (getPotProduttiva() != null) {
            _hashCode += getPotProduttiva().hashCode();
        }
        if (getProdMedSanse() != null) {
            _hashCode += getProdMedSanse().hashCode();
        }
        if (getProdStimAcque() != null) {
            _hashCode += getProdStimAcque().hashCode();
        }
        if (getProdStimSanse() != null) {
            _hashCode += getProdStimSanse().hashCode();
        }
        if (getTotAcquaOttenuta() != null) {
            _hashCode += getTotAcquaOttenuta().hashCode();
        }
        if (getTotOlioOttenuto() != null) {
            _hashCode += getTotOlioOttenuto().hashCode();
        }
        if (getTotOliveEntrate() != null) {
            _hashCode += getTotOliveEntrate().hashCode();
        }
        if (getTotOliveMolite() != null) {
            _hashCode += getTotOliveMolite().hashCode();
        }
        if (getTotSansaOttenuta() != null) {
            _hashCode += getTotSansaOttenuta().hashCode();
        }
        if (getVocFrTipoCicloLav() != null) {
            _hashCode += getVocFrTipoCicloLav().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostFrantoiOleari.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostFrantoiOleari"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacitaMagazzOli");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capacitaMagazzOli"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacitaPotenzEff");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capacitaPotenzEff"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacitaUnitariaCaricoOlive");
        elemField.setXmlName(new javax.xml.namespace.QName("", "capacitaUnitariaCaricoOlive"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contStoccaggios");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contStoccaggios"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "contStoccaggio"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fineCampagna");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fineCampagna"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField.setFieldName("inizioCampagna");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inizioCampagna"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lineeLavBiologico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lineeLavBiologico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modificheCampagnaPrec");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modificheCampagnaPrec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potProduttiva");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potProduttiva"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodMedSanse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodMedSanse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodStimAcque");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodStimAcque"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodStimSanse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodStimSanse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totAcquaOttenuta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totAcquaOttenuta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totOlioOttenuto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totOlioOttenuto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totOliveEntrate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totOliveEntrate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totOliveMolite");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totOliveMolite"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totSansaOttenuta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totSansaOttenuta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocFrTipoCicloLav");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocFrTipoCicloLav"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocFrTipoCicloLav"));
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
