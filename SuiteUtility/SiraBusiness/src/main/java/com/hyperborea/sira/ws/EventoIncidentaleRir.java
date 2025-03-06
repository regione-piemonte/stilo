/**
 * EventoIncidentaleRir.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class EventoIncidentaleRir  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer abitazioniDanneggiate;

    private java.lang.String classeMeteo;

    private java.lang.Float danniCosta1;

    private java.lang.Float danniCostaMolti;

    private java.lang.Float danniDelta1;

    private java.lang.Float danniDeltaMolti;

    private java.lang.Float danniFalda;

    private java.lang.Float danniFiume;

    private java.lang.Float danniHabitatEsteso;

    private java.lang.Float danniHabitatProtetto;

    private java.lang.Float danniLago;

    private java.util.Calendar dataEvento;

    private java.lang.Float durataDisservizio;

    private java.lang.Float durataEvacuazione;

    private java.lang.Float durataEvento;

    private java.lang.Integer esterniFeriti;

    private com.hyperborea.sira.ws.EventoIncidentaleOst[] eventoIncidentaleOsts;

    private java.lang.Integer idEvento;

    private java.lang.Integer idFeature;

    private java.lang.Integer interniFeriti;

    private java.lang.Integer morti;

    private java.lang.Integer personeEvacuate;

    private java.lang.Float quantitaSostanza;

    private java.lang.Integer serviziInterrotti;

    private java.lang.String topEvent;

    private com.hyperborea.sira.ws.VocTipoEvento vocTipoEvento;

    private com.hyperborea.sira.ws.VocTipoScenario vocTipoScenario;

    public EventoIncidentaleRir() {
    }

    public EventoIncidentaleRir(
           java.lang.Integer abitazioniDanneggiate,
           java.lang.String classeMeteo,
           java.lang.Float danniCosta1,
           java.lang.Float danniCostaMolti,
           java.lang.Float danniDelta1,
           java.lang.Float danniDeltaMolti,
           java.lang.Float danniFalda,
           java.lang.Float danniFiume,
           java.lang.Float danniHabitatEsteso,
           java.lang.Float danniHabitatProtetto,
           java.lang.Float danniLago,
           java.util.Calendar dataEvento,
           java.lang.Float durataDisservizio,
           java.lang.Float durataEvacuazione,
           java.lang.Float durataEvento,
           java.lang.Integer esterniFeriti,
           com.hyperborea.sira.ws.EventoIncidentaleOst[] eventoIncidentaleOsts,
           java.lang.Integer idEvento,
           java.lang.Integer idFeature,
           java.lang.Integer interniFeriti,
           java.lang.Integer morti,
           java.lang.Integer personeEvacuate,
           java.lang.Float quantitaSostanza,
           java.lang.Integer serviziInterrotti,
           java.lang.String topEvent,
           com.hyperborea.sira.ws.VocTipoEvento vocTipoEvento,
           com.hyperborea.sira.ws.VocTipoScenario vocTipoScenario) {
        this.abitazioniDanneggiate = abitazioniDanneggiate;
        this.classeMeteo = classeMeteo;
        this.danniCosta1 = danniCosta1;
        this.danniCostaMolti = danniCostaMolti;
        this.danniDelta1 = danniDelta1;
        this.danniDeltaMolti = danniDeltaMolti;
        this.danniFalda = danniFalda;
        this.danniFiume = danniFiume;
        this.danniHabitatEsteso = danniHabitatEsteso;
        this.danniHabitatProtetto = danniHabitatProtetto;
        this.danniLago = danniLago;
        this.dataEvento = dataEvento;
        this.durataDisservizio = durataDisservizio;
        this.durataEvacuazione = durataEvacuazione;
        this.durataEvento = durataEvento;
        this.esterniFeriti = esterniFeriti;
        this.eventoIncidentaleOsts = eventoIncidentaleOsts;
        this.idEvento = idEvento;
        this.idFeature = idFeature;
        this.interniFeriti = interniFeriti;
        this.morti = morti;
        this.personeEvacuate = personeEvacuate;
        this.quantitaSostanza = quantitaSostanza;
        this.serviziInterrotti = serviziInterrotti;
        this.topEvent = topEvent;
        this.vocTipoEvento = vocTipoEvento;
        this.vocTipoScenario = vocTipoScenario;
    }


    /**
     * Gets the abitazioniDanneggiate value for this EventoIncidentaleRir.
     * 
     * @return abitazioniDanneggiate
     */
    public java.lang.Integer getAbitazioniDanneggiate() {
        return abitazioniDanneggiate;
    }


    /**
     * Sets the abitazioniDanneggiate value for this EventoIncidentaleRir.
     * 
     * @param abitazioniDanneggiate
     */
    public void setAbitazioniDanneggiate(java.lang.Integer abitazioniDanneggiate) {
        this.abitazioniDanneggiate = abitazioniDanneggiate;
    }


    /**
     * Gets the classeMeteo value for this EventoIncidentaleRir.
     * 
     * @return classeMeteo
     */
    public java.lang.String getClasseMeteo() {
        return classeMeteo;
    }


    /**
     * Sets the classeMeteo value for this EventoIncidentaleRir.
     * 
     * @param classeMeteo
     */
    public void setClasseMeteo(java.lang.String classeMeteo) {
        this.classeMeteo = classeMeteo;
    }


    /**
     * Gets the danniCosta1 value for this EventoIncidentaleRir.
     * 
     * @return danniCosta1
     */
    public java.lang.Float getDanniCosta1() {
        return danniCosta1;
    }


    /**
     * Sets the danniCosta1 value for this EventoIncidentaleRir.
     * 
     * @param danniCosta1
     */
    public void setDanniCosta1(java.lang.Float danniCosta1) {
        this.danniCosta1 = danniCosta1;
    }


    /**
     * Gets the danniCostaMolti value for this EventoIncidentaleRir.
     * 
     * @return danniCostaMolti
     */
    public java.lang.Float getDanniCostaMolti() {
        return danniCostaMolti;
    }


    /**
     * Sets the danniCostaMolti value for this EventoIncidentaleRir.
     * 
     * @param danniCostaMolti
     */
    public void setDanniCostaMolti(java.lang.Float danniCostaMolti) {
        this.danniCostaMolti = danniCostaMolti;
    }


    /**
     * Gets the danniDelta1 value for this EventoIncidentaleRir.
     * 
     * @return danniDelta1
     */
    public java.lang.Float getDanniDelta1() {
        return danniDelta1;
    }


    /**
     * Sets the danniDelta1 value for this EventoIncidentaleRir.
     * 
     * @param danniDelta1
     */
    public void setDanniDelta1(java.lang.Float danniDelta1) {
        this.danniDelta1 = danniDelta1;
    }


    /**
     * Gets the danniDeltaMolti value for this EventoIncidentaleRir.
     * 
     * @return danniDeltaMolti
     */
    public java.lang.Float getDanniDeltaMolti() {
        return danniDeltaMolti;
    }


    /**
     * Sets the danniDeltaMolti value for this EventoIncidentaleRir.
     * 
     * @param danniDeltaMolti
     */
    public void setDanniDeltaMolti(java.lang.Float danniDeltaMolti) {
        this.danniDeltaMolti = danniDeltaMolti;
    }


    /**
     * Gets the danniFalda value for this EventoIncidentaleRir.
     * 
     * @return danniFalda
     */
    public java.lang.Float getDanniFalda() {
        return danniFalda;
    }


    /**
     * Sets the danniFalda value for this EventoIncidentaleRir.
     * 
     * @param danniFalda
     */
    public void setDanniFalda(java.lang.Float danniFalda) {
        this.danniFalda = danniFalda;
    }


    /**
     * Gets the danniFiume value for this EventoIncidentaleRir.
     * 
     * @return danniFiume
     */
    public java.lang.Float getDanniFiume() {
        return danniFiume;
    }


    /**
     * Sets the danniFiume value for this EventoIncidentaleRir.
     * 
     * @param danniFiume
     */
    public void setDanniFiume(java.lang.Float danniFiume) {
        this.danniFiume = danniFiume;
    }


    /**
     * Gets the danniHabitatEsteso value for this EventoIncidentaleRir.
     * 
     * @return danniHabitatEsteso
     */
    public java.lang.Float getDanniHabitatEsteso() {
        return danniHabitatEsteso;
    }


    /**
     * Sets the danniHabitatEsteso value for this EventoIncidentaleRir.
     * 
     * @param danniHabitatEsteso
     */
    public void setDanniHabitatEsteso(java.lang.Float danniHabitatEsteso) {
        this.danniHabitatEsteso = danniHabitatEsteso;
    }


    /**
     * Gets the danniHabitatProtetto value for this EventoIncidentaleRir.
     * 
     * @return danniHabitatProtetto
     */
    public java.lang.Float getDanniHabitatProtetto() {
        return danniHabitatProtetto;
    }


    /**
     * Sets the danniHabitatProtetto value for this EventoIncidentaleRir.
     * 
     * @param danniHabitatProtetto
     */
    public void setDanniHabitatProtetto(java.lang.Float danniHabitatProtetto) {
        this.danniHabitatProtetto = danniHabitatProtetto;
    }


    /**
     * Gets the danniLago value for this EventoIncidentaleRir.
     * 
     * @return danniLago
     */
    public java.lang.Float getDanniLago() {
        return danniLago;
    }


    /**
     * Sets the danniLago value for this EventoIncidentaleRir.
     * 
     * @param danniLago
     */
    public void setDanniLago(java.lang.Float danniLago) {
        this.danniLago = danniLago;
    }


    /**
     * Gets the dataEvento value for this EventoIncidentaleRir.
     * 
     * @return dataEvento
     */
    public java.util.Calendar getDataEvento() {
        return dataEvento;
    }


    /**
     * Sets the dataEvento value for this EventoIncidentaleRir.
     * 
     * @param dataEvento
     */
    public void setDataEvento(java.util.Calendar dataEvento) {
        this.dataEvento = dataEvento;
    }


    /**
     * Gets the durataDisservizio value for this EventoIncidentaleRir.
     * 
     * @return durataDisservizio
     */
    public java.lang.Float getDurataDisservizio() {
        return durataDisservizio;
    }


    /**
     * Sets the durataDisservizio value for this EventoIncidentaleRir.
     * 
     * @param durataDisservizio
     */
    public void setDurataDisservizio(java.lang.Float durataDisservizio) {
        this.durataDisservizio = durataDisservizio;
    }


    /**
     * Gets the durataEvacuazione value for this EventoIncidentaleRir.
     * 
     * @return durataEvacuazione
     */
    public java.lang.Float getDurataEvacuazione() {
        return durataEvacuazione;
    }


    /**
     * Sets the durataEvacuazione value for this EventoIncidentaleRir.
     * 
     * @param durataEvacuazione
     */
    public void setDurataEvacuazione(java.lang.Float durataEvacuazione) {
        this.durataEvacuazione = durataEvacuazione;
    }


    /**
     * Gets the durataEvento value for this EventoIncidentaleRir.
     * 
     * @return durataEvento
     */
    public java.lang.Float getDurataEvento() {
        return durataEvento;
    }


    /**
     * Sets the durataEvento value for this EventoIncidentaleRir.
     * 
     * @param durataEvento
     */
    public void setDurataEvento(java.lang.Float durataEvento) {
        this.durataEvento = durataEvento;
    }


    /**
     * Gets the esterniFeriti value for this EventoIncidentaleRir.
     * 
     * @return esterniFeriti
     */
    public java.lang.Integer getEsterniFeriti() {
        return esterniFeriti;
    }


    /**
     * Sets the esterniFeriti value for this EventoIncidentaleRir.
     * 
     * @param esterniFeriti
     */
    public void setEsterniFeriti(java.lang.Integer esterniFeriti) {
        this.esterniFeriti = esterniFeriti;
    }


    /**
     * Gets the eventoIncidentaleOsts value for this EventoIncidentaleRir.
     * 
     * @return eventoIncidentaleOsts
     */
    public com.hyperborea.sira.ws.EventoIncidentaleOst[] getEventoIncidentaleOsts() {
        return eventoIncidentaleOsts;
    }


    /**
     * Sets the eventoIncidentaleOsts value for this EventoIncidentaleRir.
     * 
     * @param eventoIncidentaleOsts
     */
    public void setEventoIncidentaleOsts(com.hyperborea.sira.ws.EventoIncidentaleOst[] eventoIncidentaleOsts) {
        this.eventoIncidentaleOsts = eventoIncidentaleOsts;
    }

    public com.hyperborea.sira.ws.EventoIncidentaleOst getEventoIncidentaleOsts(int i) {
        return this.eventoIncidentaleOsts[i];
    }

    public void setEventoIncidentaleOsts(int i, com.hyperborea.sira.ws.EventoIncidentaleOst _value) {
        this.eventoIncidentaleOsts[i] = _value;
    }


    /**
     * Gets the idEvento value for this EventoIncidentaleRir.
     * 
     * @return idEvento
     */
    public java.lang.Integer getIdEvento() {
        return idEvento;
    }


    /**
     * Sets the idEvento value for this EventoIncidentaleRir.
     * 
     * @param idEvento
     */
    public void setIdEvento(java.lang.Integer idEvento) {
        this.idEvento = idEvento;
    }


    /**
     * Gets the idFeature value for this EventoIncidentaleRir.
     * 
     * @return idFeature
     */
    public java.lang.Integer getIdFeature() {
        return idFeature;
    }


    /**
     * Sets the idFeature value for this EventoIncidentaleRir.
     * 
     * @param idFeature
     */
    public void setIdFeature(java.lang.Integer idFeature) {
        this.idFeature = idFeature;
    }


    /**
     * Gets the interniFeriti value for this EventoIncidentaleRir.
     * 
     * @return interniFeriti
     */
    public java.lang.Integer getInterniFeriti() {
        return interniFeriti;
    }


    /**
     * Sets the interniFeriti value for this EventoIncidentaleRir.
     * 
     * @param interniFeriti
     */
    public void setInterniFeriti(java.lang.Integer interniFeriti) {
        this.interniFeriti = interniFeriti;
    }


    /**
     * Gets the morti value for this EventoIncidentaleRir.
     * 
     * @return morti
     */
    public java.lang.Integer getMorti() {
        return morti;
    }


    /**
     * Sets the morti value for this EventoIncidentaleRir.
     * 
     * @param morti
     */
    public void setMorti(java.lang.Integer morti) {
        this.morti = morti;
    }


    /**
     * Gets the personeEvacuate value for this EventoIncidentaleRir.
     * 
     * @return personeEvacuate
     */
    public java.lang.Integer getPersoneEvacuate() {
        return personeEvacuate;
    }


    /**
     * Sets the personeEvacuate value for this EventoIncidentaleRir.
     * 
     * @param personeEvacuate
     */
    public void setPersoneEvacuate(java.lang.Integer personeEvacuate) {
        this.personeEvacuate = personeEvacuate;
    }


    /**
     * Gets the quantitaSostanza value for this EventoIncidentaleRir.
     * 
     * @return quantitaSostanza
     */
    public java.lang.Float getQuantitaSostanza() {
        return quantitaSostanza;
    }


    /**
     * Sets the quantitaSostanza value for this EventoIncidentaleRir.
     * 
     * @param quantitaSostanza
     */
    public void setQuantitaSostanza(java.lang.Float quantitaSostanza) {
        this.quantitaSostanza = quantitaSostanza;
    }


    /**
     * Gets the serviziInterrotti value for this EventoIncidentaleRir.
     * 
     * @return serviziInterrotti
     */
    public java.lang.Integer getServiziInterrotti() {
        return serviziInterrotti;
    }


    /**
     * Sets the serviziInterrotti value for this EventoIncidentaleRir.
     * 
     * @param serviziInterrotti
     */
    public void setServiziInterrotti(java.lang.Integer serviziInterrotti) {
        this.serviziInterrotti = serviziInterrotti;
    }


    /**
     * Gets the topEvent value for this EventoIncidentaleRir.
     * 
     * @return topEvent
     */
    public java.lang.String getTopEvent() {
        return topEvent;
    }


    /**
     * Sets the topEvent value for this EventoIncidentaleRir.
     * 
     * @param topEvent
     */
    public void setTopEvent(java.lang.String topEvent) {
        this.topEvent = topEvent;
    }


    /**
     * Gets the vocTipoEvento value for this EventoIncidentaleRir.
     * 
     * @return vocTipoEvento
     */
    public com.hyperborea.sira.ws.VocTipoEvento getVocTipoEvento() {
        return vocTipoEvento;
    }


    /**
     * Sets the vocTipoEvento value for this EventoIncidentaleRir.
     * 
     * @param vocTipoEvento
     */
    public void setVocTipoEvento(com.hyperborea.sira.ws.VocTipoEvento vocTipoEvento) {
        this.vocTipoEvento = vocTipoEvento;
    }


    /**
     * Gets the vocTipoScenario value for this EventoIncidentaleRir.
     * 
     * @return vocTipoScenario
     */
    public com.hyperborea.sira.ws.VocTipoScenario getVocTipoScenario() {
        return vocTipoScenario;
    }


    /**
     * Sets the vocTipoScenario value for this EventoIncidentaleRir.
     * 
     * @param vocTipoScenario
     */
    public void setVocTipoScenario(com.hyperborea.sira.ws.VocTipoScenario vocTipoScenario) {
        this.vocTipoScenario = vocTipoScenario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EventoIncidentaleRir)) return false;
        EventoIncidentaleRir other = (EventoIncidentaleRir) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.abitazioniDanneggiate==null && other.getAbitazioniDanneggiate()==null) || 
             (this.abitazioniDanneggiate!=null &&
              this.abitazioniDanneggiate.equals(other.getAbitazioniDanneggiate()))) &&
            ((this.classeMeteo==null && other.getClasseMeteo()==null) || 
             (this.classeMeteo!=null &&
              this.classeMeteo.equals(other.getClasseMeteo()))) &&
            ((this.danniCosta1==null && other.getDanniCosta1()==null) || 
             (this.danniCosta1!=null &&
              this.danniCosta1.equals(other.getDanniCosta1()))) &&
            ((this.danniCostaMolti==null && other.getDanniCostaMolti()==null) || 
             (this.danniCostaMolti!=null &&
              this.danniCostaMolti.equals(other.getDanniCostaMolti()))) &&
            ((this.danniDelta1==null && other.getDanniDelta1()==null) || 
             (this.danniDelta1!=null &&
              this.danniDelta1.equals(other.getDanniDelta1()))) &&
            ((this.danniDeltaMolti==null && other.getDanniDeltaMolti()==null) || 
             (this.danniDeltaMolti!=null &&
              this.danniDeltaMolti.equals(other.getDanniDeltaMolti()))) &&
            ((this.danniFalda==null && other.getDanniFalda()==null) || 
             (this.danniFalda!=null &&
              this.danniFalda.equals(other.getDanniFalda()))) &&
            ((this.danniFiume==null && other.getDanniFiume()==null) || 
             (this.danniFiume!=null &&
              this.danniFiume.equals(other.getDanniFiume()))) &&
            ((this.danniHabitatEsteso==null && other.getDanniHabitatEsteso()==null) || 
             (this.danniHabitatEsteso!=null &&
              this.danniHabitatEsteso.equals(other.getDanniHabitatEsteso()))) &&
            ((this.danniHabitatProtetto==null && other.getDanniHabitatProtetto()==null) || 
             (this.danniHabitatProtetto!=null &&
              this.danniHabitatProtetto.equals(other.getDanniHabitatProtetto()))) &&
            ((this.danniLago==null && other.getDanniLago()==null) || 
             (this.danniLago!=null &&
              this.danniLago.equals(other.getDanniLago()))) &&
            ((this.dataEvento==null && other.getDataEvento()==null) || 
             (this.dataEvento!=null &&
              this.dataEvento.equals(other.getDataEvento()))) &&
            ((this.durataDisservizio==null && other.getDurataDisservizio()==null) || 
             (this.durataDisservizio!=null &&
              this.durataDisservizio.equals(other.getDurataDisservizio()))) &&
            ((this.durataEvacuazione==null && other.getDurataEvacuazione()==null) || 
             (this.durataEvacuazione!=null &&
              this.durataEvacuazione.equals(other.getDurataEvacuazione()))) &&
            ((this.durataEvento==null && other.getDurataEvento()==null) || 
             (this.durataEvento!=null &&
              this.durataEvento.equals(other.getDurataEvento()))) &&
            ((this.esterniFeriti==null && other.getEsterniFeriti()==null) || 
             (this.esterniFeriti!=null &&
              this.esterniFeriti.equals(other.getEsterniFeriti()))) &&
            ((this.eventoIncidentaleOsts==null && other.getEventoIncidentaleOsts()==null) || 
             (this.eventoIncidentaleOsts!=null &&
              java.util.Arrays.equals(this.eventoIncidentaleOsts, other.getEventoIncidentaleOsts()))) &&
            ((this.idEvento==null && other.getIdEvento()==null) || 
             (this.idEvento!=null &&
              this.idEvento.equals(other.getIdEvento()))) &&
            ((this.idFeature==null && other.getIdFeature()==null) || 
             (this.idFeature!=null &&
              this.idFeature.equals(other.getIdFeature()))) &&
            ((this.interniFeriti==null && other.getInterniFeriti()==null) || 
             (this.interniFeriti!=null &&
              this.interniFeriti.equals(other.getInterniFeriti()))) &&
            ((this.morti==null && other.getMorti()==null) || 
             (this.morti!=null &&
              this.morti.equals(other.getMorti()))) &&
            ((this.personeEvacuate==null && other.getPersoneEvacuate()==null) || 
             (this.personeEvacuate!=null &&
              this.personeEvacuate.equals(other.getPersoneEvacuate()))) &&
            ((this.quantitaSostanza==null && other.getQuantitaSostanza()==null) || 
             (this.quantitaSostanza!=null &&
              this.quantitaSostanza.equals(other.getQuantitaSostanza()))) &&
            ((this.serviziInterrotti==null && other.getServiziInterrotti()==null) || 
             (this.serviziInterrotti!=null &&
              this.serviziInterrotti.equals(other.getServiziInterrotti()))) &&
            ((this.topEvent==null && other.getTopEvent()==null) || 
             (this.topEvent!=null &&
              this.topEvent.equals(other.getTopEvent()))) &&
            ((this.vocTipoEvento==null && other.getVocTipoEvento()==null) || 
             (this.vocTipoEvento!=null &&
              this.vocTipoEvento.equals(other.getVocTipoEvento()))) &&
            ((this.vocTipoScenario==null && other.getVocTipoScenario()==null) || 
             (this.vocTipoScenario!=null &&
              this.vocTipoScenario.equals(other.getVocTipoScenario())));
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
        if (getAbitazioniDanneggiate() != null) {
            _hashCode += getAbitazioniDanneggiate().hashCode();
        }
        if (getClasseMeteo() != null) {
            _hashCode += getClasseMeteo().hashCode();
        }
        if (getDanniCosta1() != null) {
            _hashCode += getDanniCosta1().hashCode();
        }
        if (getDanniCostaMolti() != null) {
            _hashCode += getDanniCostaMolti().hashCode();
        }
        if (getDanniDelta1() != null) {
            _hashCode += getDanniDelta1().hashCode();
        }
        if (getDanniDeltaMolti() != null) {
            _hashCode += getDanniDeltaMolti().hashCode();
        }
        if (getDanniFalda() != null) {
            _hashCode += getDanniFalda().hashCode();
        }
        if (getDanniFiume() != null) {
            _hashCode += getDanniFiume().hashCode();
        }
        if (getDanniHabitatEsteso() != null) {
            _hashCode += getDanniHabitatEsteso().hashCode();
        }
        if (getDanniHabitatProtetto() != null) {
            _hashCode += getDanniHabitatProtetto().hashCode();
        }
        if (getDanniLago() != null) {
            _hashCode += getDanniLago().hashCode();
        }
        if (getDataEvento() != null) {
            _hashCode += getDataEvento().hashCode();
        }
        if (getDurataDisservizio() != null) {
            _hashCode += getDurataDisservizio().hashCode();
        }
        if (getDurataEvacuazione() != null) {
            _hashCode += getDurataEvacuazione().hashCode();
        }
        if (getDurataEvento() != null) {
            _hashCode += getDurataEvento().hashCode();
        }
        if (getEsterniFeriti() != null) {
            _hashCode += getEsterniFeriti().hashCode();
        }
        if (getEventoIncidentaleOsts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEventoIncidentaleOsts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEventoIncidentaleOsts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIdEvento() != null) {
            _hashCode += getIdEvento().hashCode();
        }
        if (getIdFeature() != null) {
            _hashCode += getIdFeature().hashCode();
        }
        if (getInterniFeriti() != null) {
            _hashCode += getInterniFeriti().hashCode();
        }
        if (getMorti() != null) {
            _hashCode += getMorti().hashCode();
        }
        if (getPersoneEvacuate() != null) {
            _hashCode += getPersoneEvacuate().hashCode();
        }
        if (getQuantitaSostanza() != null) {
            _hashCode += getQuantitaSostanza().hashCode();
        }
        if (getServiziInterrotti() != null) {
            _hashCode += getServiziInterrotti().hashCode();
        }
        if (getTopEvent() != null) {
            _hashCode += getTopEvent().hashCode();
        }
        if (getVocTipoEvento() != null) {
            _hashCode += getVocTipoEvento().hashCode();
        }
        if (getVocTipoScenario() != null) {
            _hashCode += getVocTipoScenario().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EventoIncidentaleRir.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "eventoIncidentaleRir"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("abitazioniDanneggiate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "abitazioniDanneggiate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classeMeteo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "classeMeteo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("danniCosta1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "danniCosta1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("danniCostaMolti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "danniCostaMolti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("danniDelta1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "danniDelta1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("danniDeltaMolti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "danniDeltaMolti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("danniFalda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "danniFalda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("danniFiume");
        elemField.setXmlName(new javax.xml.namespace.QName("", "danniFiume"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("danniHabitatEsteso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "danniHabitatEsteso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("danniHabitatProtetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "danniHabitatProtetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("danniLago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "danniLago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataEvento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataEvento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("durataDisservizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "durataDisservizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("durataEvacuazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "durataEvacuazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("durataEvento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "durataEvento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esterniFeriti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "esterniFeriti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventoIncidentaleOsts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eventoIncidentaleOsts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "eventoIncidentaleOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEvento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idEvento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFeature");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idFeature"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interniFeriti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "interniFeriti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("morti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "morti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personeEvacuate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "personeEvacuate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantitaSostanza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quantitaSostanza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviziInterrotti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviziInterrotti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topEvent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topEvent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoEvento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoEvento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoEvento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoScenario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoScenario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoScenario"));
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
