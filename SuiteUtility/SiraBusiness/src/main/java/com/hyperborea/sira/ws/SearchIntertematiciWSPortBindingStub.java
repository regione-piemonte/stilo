/**
 * SearchIntertematiciWSPortBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class SearchIntertematiciWSPortBindingStub extends org.apache.axis.client.Stub implements com.hyperborea.sira.ws.SearchIntertematiciWS {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[37];
        _initOperationDesc1();
        _initOperationDesc2();
        _initOperationDesc3();
        _initOperationDesc4();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("countSoggettiFisici");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCountSoggettiFisiciRequest"), com.hyperborea.sira.ws.WsCountSoggettiFisiciRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCountSoggettiFisiciResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsCountSoggettiFisiciResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("searchSoggettiFisici");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSearchSoggettiFisiciRequest"), com.hyperborea.sira.ws.WsSearchSoggettiFisiciRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSearchSoggettiFisiciResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsSearchSoggettiFisiciResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("searchUnitaLocali");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSearchUnitaLocaliRequest"), com.hyperborea.sira.ws.WsSearchUnitaLocaliRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSearchUnitaLocaliResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsUnitaLocaliRef[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "wsUnitaLocaliRefs"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("viewSoggettiFisici");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewSoggettiFisiciRequest"), com.hyperborea.sira.ws.WsViewSoggettiFisiciRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewSoggettiFisiciResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsViewSoggettiFisiciResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("newSoggettiFisici");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewSoggettiFisiciRequest"), com.hyperborea.sira.ws.WsNewSoggettiFisiciRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewSoggettiFisiciResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsNewSoggettiFisiciResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("editSoggettiFisici");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsEditSoggettiFisiciRequest"), com.hyperborea.sira.ws.WsEditSoggettiFisiciRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsEditSoggettiFisiciResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsEditSoggettiFisiciResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("newSfOst");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewSfOstRequest"), com.hyperborea.sira.ws.WsNewSfOstRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewSfOstResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsNewSfOstResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getVocabularyDescription");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetVocabularyDescriptionRequest"), com.hyperborea.sira.ws.WsGetVocabularyDescriptionRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetVocabularyDescriptionResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsGetVocabularyDescriptionResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getVocabularyDescriptionByVocClassname");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetVocabularyDescriptionByVocClassnameRequest"), com.hyperborea.sira.ws.WsGetVocabularyDescriptionByVocClassnameRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetVocabularyDescriptionResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsGetVocabularyDescriptionResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("newCampione");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewCampioneRequest"), com.hyperborea.sira.ws.WsNewCampioneRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewCampioneResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsNewCampioneResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("newAttivitaEsterna");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewAttivitaEsternaRequest"), com.hyperborea.sira.ws.WsNewAttivitaEsternaRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewAttivitaEsternaResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsNewAttivitaEsternaResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getTipoCampioneList");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipoCampioneListRequest"), com.hyperborea.sira.ws.WsGetTipoCampioneListRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipoCampioneListResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsTipoCampioneRef[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "wsTipoCampioneRefs"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getTipoCampione");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipoCampioneRequest"), com.hyperborea.sira.ws.WsGetTipoCampioneRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipoCampioneResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsGetTipoCampioneResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("newUbicazioniOst");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewUbicazioniOstRequest"), com.hyperborea.sira.ws.WsNewUbicazioniOstRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewUbicazioniOstResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsNewUbicazioniOstResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("newReteMonitoraggio");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewRetiMonitoraggioRequest"), com.hyperborea.sira.ws.WsNewRetiMonitoraggioRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewRetiMonitoraggioResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsNewRetiMonitoraggioResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getTipiSchedaMonitoraggio");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipiSchedaMonitoraggioRequest"), com.hyperborea.sira.ws.WsGetTipiSchedaMonitoraggioRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipiSchedaMonitoraggioResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsGetTipiSchedaMonitoraggioResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getTipiSchedaMonitoraggioList");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipiSchedaMonitoraggioListRequest"), com.hyperborea.sira.ws.WsGetTipiSchedaMonitoraggioListRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipiSchedaMonitoraggioListResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioRef[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "wsTipiSchedaMonitoraggioRef"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("newSchedaMonitoraggio");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewSchedaMonitoraggioRequest"), com.hyperborea.sira.ws.WsNewSchedaMonitoraggioRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewSchedaMonitoraggioResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsNewSchedaMonitoraggioResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("viewSchedaMonitoraggio");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewSchedaMonitoraggioRequest"), com.hyperborea.sira.ws.WsViewSchedaMonitoraggioRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewSchedaMonitoraggioResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsViewSchedaMonitoraggioResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("searchSchedaMonitoraggio");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSearchSchedaMonitoraggioRequest"), com.hyperborea.sira.ws.WsSearchSchedaMonitoraggioRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSearchSchedaMonitoraggioResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsSearchSchedaMonitoraggioResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("strLenght");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        oper.setReturnClass(java.lang.Long.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("countOst");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchComand"), com.hyperborea.sira.ws.CcostAnySearchComand.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        oper.setReturnClass(java.lang.Long.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("searchOst");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSearchOstRequest"), com.hyperborea.sira.ws.WsSearchOstRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSearchOstResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsSearchOstResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[22] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("visitOst");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsVisitOstRequest"), com.hyperborea.sira.ws.WsVisitOstRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsVisitOstResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsVisitedOst[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "wsVisitedOst"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[23] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("viewOst");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewOstRequest"), com.hyperborea.sira.ws.WsViewOstRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewOstResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsViewOstResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[24] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("newCcost");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewCcostRequest"), com.hyperborea.sira.ws.WsNewCcostRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewCcostResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsNewCcostResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[25] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("viewFonte");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewFonteRequest"), com.hyperborea.sira.ws.WsViewFonteRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewFonteResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsViewFonteResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[26] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("newFontiDati");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewFontiDatiRequest"), com.hyperborea.sira.ws.WsNewFontiDatiRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsFontiDatiResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsFontiDatiResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[27] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("newOst");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewOstRequest"), com.hyperborea.sira.ws.WsNewOstRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewOstResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsNewOstResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[28] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("newRelazioniOst");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewRostRequest"), com.hyperborea.sira.ws.WsNewRostRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewRostResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsNewRostResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[29] = oper;

    }

    private static void _initOperationDesc4(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCategoriaMetadata");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetCategoriaMetadataRequest"), com.hyperborea.sira.ws.WsGetCategoriaMetadataRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetCategoriaMetadataResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsGetCategoriaMetadataResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[30] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCategoriaInfo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetCategoriaInfoRequest"), com.hyperborea.sira.ws.WsGetCategoriaInfoRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetCategoriaInfoResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsGetCategoriaInfoResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[31] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAvailableCategoriaList");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetAvailableCategoriaListRequest"), com.hyperborea.sira.ws.WsGetAvailableCategoriaListRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetAvailableCategoriaListResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsCostNostRef[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "wsCostNostRefs"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[32] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("newRapportoDiProva");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSubmitRapportoDiProvaRequest"), com.hyperborea.sira.ws.WsSubmitRapportoDiProvaRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSubmitRapportoDiProvaResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsSubmitRapportoDiProvaResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[33] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("beginTransaction");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsBeginTransactionRequest"), com.hyperborea.sira.ws.WsBeginTransactionRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsBeginTransactionResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsBeginTransactionResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[34] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("closeTransaction");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCommitTransactionRequest"), com.hyperborea.sira.ws.WsCommitTransactionRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCommitTransactionResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsCommitTransactionResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[35] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("newStatiOst");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsStatiOstRequest"), com.hyperborea.sira.ws.WsStatiOstRequest.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsStatiOstResponse"));
        oper.setReturnClass(com.hyperborea.sira.ws.WsStatiOstResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[36] = oper;

    }

    public SearchIntertematiciWSPortBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public SearchIntertematiciWSPortBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public SearchIntertematiciWSPortBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        addBindings0();
        addBindings1();
        addBindings2();
        addBindings3();
        addBindings4();
        addBindings5();
        addBindings6();
        addBindings7();
        addBindings8();
    }

    private void addBindings0() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "aeEnergiaConsumi");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.AeEnergiaConsumi.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "aeMaterieConsumi");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.AeMaterieConsumi.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "approvvAntincendio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ApprovvAntincendio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "areaRamsar");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.AreaRamsar.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "areeStoccMat");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.AreeStoccMat.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "areeStoccRifiuti");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.AreeStoccRifiuti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ateco");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Ateco.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "atecoId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.AtecoId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attEcoSvolta");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.AttEcoSvolta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attivitaEsterna");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.AttivitaEsterna.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attTecConnessa");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.AttTecConnessa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "autoparchiAntincendio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.AutoparchiAntincendio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "beginTransaction");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.BeginTransaction.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "beginTransactionResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.BeginTransactionResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "bonifiche");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Bonifiche.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "bonificheAmianto");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.BonificheAmianto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "campateEdCaviInterrati");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CampateEdCaviInterrati.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "campione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Campione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "campiSchedaMon");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CampiSchedaMon.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "canali");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Canali.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "capacitaProduttiva");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CapacitaProduttiva.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "caratterizzazioniOst");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CaratterizzazioniOst.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "carattIntervento");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CarattIntervento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "carattInterventoId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CarattInterventoId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "categoriaIppc");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CategoriaIppc.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "categoriaNace");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CategoriaNace.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "categoriaNose");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CategoriaNose.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "categorieIipa");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CategorieIipa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "categorieOst");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CategorieOst.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "cavaAltriDati");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CavaAltriDati.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "cavaImpiantiMezzi");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CavaImpiantiMezzi.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAcqueSotterranee");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAcqueSotterranee.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAcquifero");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAcquifero.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostActr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostActr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAmbitoTerritoriale");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAmbitoTerritoriale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAmc");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAmc.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAmp");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAmp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchComand");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAnySearchComand.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchCriteria");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAnySearchCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchDateRangeCriteria");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAnySearchDateRangeCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchDoubleRangeCriteria");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAnySearchDoubleRangeCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchFloatRangeCriteria");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAnySearchFloatRangeCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchIntegerCriteria");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAnySearchIntegerCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchIntegerRangeCriteria");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAnySearchIntegerRangeCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchSmCriteria");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAnySearchSmCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchStringCriteria");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAnySearchStringCriteria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostApparecchiPcb");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostApparecchiPcb.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAreaDannoFauna");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAreaDannoFauna.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAreaEstrattiva");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAreaEstrattiva.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAreaGallinaPrataiola");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAreaGallinaPrataiola.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAreaLegge3189");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAreaLegge3189.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAreaPericoloIdrogeo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAreaPericoloIdrogeo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAreaProtetta");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAreaProtetta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAreaRicercaForestale");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAreaRicercaForestale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAreaRischioIdrogeo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAreaRischioIdrogeo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAreaVincoloIdrogeo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAreaVincoloIdrogeo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAreeEmissioneAtm");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAreeEmissioneAtm.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostArt");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostArt.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAutoparcoEf");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostAutoparcoEf.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostBacinoIdrografico");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostBacinoIdrografico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCampateEd");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostCampateEd.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCava");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostCava.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCentralineBordoni");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostCentralineBordoni.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCentroFaunaEf");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostCentroFaunaEf.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCollettoreFognario");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostCollettoreFognario.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostComplessoAcquifero");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostComplessoAcquifero.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostComplessoForestale");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostComplessoForestale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCondottaAcque");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostCondottaAcque.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCorpoIdricoSup");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostCorpoIdricoSup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCorpoIdrSott");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostCorpoIdrSott.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCorridoioFaunistico");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostCorridoioFaunistico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCostaAntropizzata");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostCostaAntropizzata.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCostaRocciosa");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostCostaRocciosa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostDigaSbarramento");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostDigaSbarramento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostEeBiomasse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostEeBiomasse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostEeBm");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostEeBm.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostEeEo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostEeEo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostEeFs");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostEeFs.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostEeFt");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostEeFt.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostEeFv");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostEeFv.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostEeIe");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostEeIe.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostEeSr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostEeSr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostElementoAcquedottistico");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostElementoAcquedottistico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostEs");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostEs.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostFabbricatoEf");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostFabbricatoEf.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostFasciaParafuoco");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostFasciaParafuoco.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostForestaCertificata");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostForestaCertificata.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostFrantoiOleari");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostFrantoiOleari.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostGasdotto");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostGasdotto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostGrotta");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostGrotta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrAutodemolizione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostIgrAutodemolizione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrCoinceneritore");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostIgrCoinceneritore.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrCompostaggioCdr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostIgrCompostaggioCdr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrDiscarica");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostIgrDiscarica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrInceneritore");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostIgrInceneritore.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrMobile");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostIgrMobile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrRecupero");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostIgrRecupero.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrSelezione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostIgrSelezione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrStoccaggioProvv");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostIgrStoccaggioProvv.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrTrattamentoRaee");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostIgrTrattamentoRaee.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings1() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrTrattAnaerobico");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostIgrTrattAnaerobico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrTrattChifisBio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostIgrTrattChifisBio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpAerotermico");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostImpAerotermico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpdepurazione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostImpdepurazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpdisinquinamento");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostImpdisinquinamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpGeotermico");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostImpGeotermico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpGestioneRifiuti");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostImpGestioneRifiuti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpiantiEd");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostImpiantiEd.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpiantiRc");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostImpiantiRc.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpiantoProdEnergia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostImpiantoProdEnergia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpiantoProdEnTermica");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostImpiantoProdEnTermica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpiantoTermico");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostImpiantoTermico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpIdrotermico");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostImpIdrotermico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpProdEnTermicaFer");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostImpProdEnTermicaFer.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpProdEnTermicaFt");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostImpProdEnTermicaFt.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpTermicoSolare");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostImpTermicoSolare.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImptrattacque");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostImptrattacque.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIncidentiFauna");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostIncidentiFauna.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostInfrasEd");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostInfrasEd.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostInfrasRc");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostInfrasRc.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostInfrasTerrit");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostInfrasTerrit.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostInfrasTraspComb");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostInfrasTraspComb.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostInfrDistrRaccAcque");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostInfrDistrRaccAcque.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostLa");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostLa.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostLaghettoEf");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostLaghettoEf.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostLineeEd");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostLineeEd.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostLottiSc");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostLottiSc.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostMiniera");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostMiniera.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostOggIdrografico");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostOggIdrografico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostOperaCaptIdr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostOperaCaptIdr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostOpificioEf");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostOpificioEf.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostParcoNazionale");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostParcoNazionale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostParteAcqueCostiere");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostParteAcqueCostiere.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostParteAcqueTransizione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostParteAcqueTransizione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostParteCorsoAcqua");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostParteCorsoAcqua.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostParteLago");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostParteLago.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostPiezometri");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostPiezometri.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostPozzi");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostPozzi.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostPuntiControlloOcc");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostPuntiControlloOcc.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostPuntiEmissioneAtm");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostPuntiEmissioneAtm.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostPuntiMisura");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostPuntiMisura.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostPuntiMonitoraggioCon");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostPuntiMonitoraggioCon.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostPuntiMonitoraggioPer");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostPuntiMonitoraggioPer.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostScarichiIdrici");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostScarichiIdrici.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSediLegali");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSediLegali.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSic");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSic.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSitiAmianto");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSitiAmianto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSiticontaminati");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSiticontaminati.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSitiRc");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSitiRc.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSitiSpand");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSitiSpand.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSitoSpandimentoEz");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSitoSpandimentoEz.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSoggettiGiuridici");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSoggettiGiuridici.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSondaggiGeo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSondaggiGeo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSorgAcusticaLineare");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSorgAcusticaLineare.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSorgAcusticaPuntuale");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSorgAcusticaPuntuale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSorgentiEmissioniAtm");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSorgentiEmissioniAtm.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSorgentiIdr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSorgentiIdr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSorgInqAcustico");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSorgInqAcustico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSorgLumArtificiale");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSorgLumArtificiale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSorgRadIonizzanti");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSorgRadIonizzanti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSostegniEd");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSostegniEd.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSpandimentoFanghi");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSpandimentoFanghi.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSpiaggia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSpiaggia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostStazioniCor");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostStazioniCor.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostStazioniGrass");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostStazioniGrass.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostStazioniQualAcque");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostStazioniQualAcque.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostStazioniSar");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostStazioniSar.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostStradeForestali");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostStradeForestali.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostStrutturaEf");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostStrutturaEf.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSubparticellaForestale");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostSubparticellaForestale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostTerreniEf");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostTerreniEf.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostTratteEd");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostTratteEd.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostTronchiEd");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostTronchiEd.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostUnitaFisiografica");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostUnitaFisiografica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostUnitaGestionaleEf");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostUnitaGestionaleEf.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostUnitaLocali");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostUnitaLocali.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostUnitaTecnica");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostUnitaTecnica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostVivaioEf");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostVivaioEf.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostZoneOmogeneeAcusticamente");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostZoneOmogeneeAcusticamente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostZoneUmide");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostZoneUmide.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostZps");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostZps.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostZsc");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CcostZsc.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "classiAcustiche");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ClassiAcustiche.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "classiRost");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ClassiRost.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "classiValori");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ClassiValori.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "closeTransaction");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CloseTransaction.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "closeTransactionResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CloseTransactionResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "cmpFat");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CmpFat.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "cmpFatId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CmpFatId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "coenFat");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CoenFat.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "coenFatId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CoenFatId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "colLitostratigrafica");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ColLitostratigrafica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "combustibili");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Combustibili.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "compSpecSubparticella");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CompSpecSubparticella.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "comuniItalia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ComuniItalia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "comuniItaliaCoinvolti");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ComuniItaliaCoinvolti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "comuniItaliaCoinvoltiId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ComuniItaliaCoinvoltiId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "comuniItaliaId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ComuniItaliaId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consProd");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ConsProd.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumi");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Consumi.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings2() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumieo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Consumieo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumifv");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Consumifv.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumiie");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Consumiie.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumisr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Consumisr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumoEnergia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ConsumoEnergia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumoMatPrime");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ConsumoMatPrime.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumoRisIdriche");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ConsumoRisIdriche.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "contStoccaggio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ContStoccaggio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "costNost");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CostNost.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "costNostId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CostNostId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "countOst");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CountOst.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "countOstResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CountOstResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "countSoggettiFisici");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CountSoggettiFisici.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "countSoggettiFisiciResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CountSoggettiFisiciResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "cpL3A");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CpL3A.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "cridFat");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CridFat.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "cridFatId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.CridFatId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "danniSubparticella");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.DanniSubparticella.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "datiCatastali");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.DatiCatastali.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "daticatSitispand");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.DaticatSitispand.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "depuratoreCompetenza");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.DepuratoreCompetenza.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "depuratoreCompetenzaPrra");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.DepuratoreCompetenzaPrra.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "depuratoreSezioni");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.DepuratoreSezioni.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "depuratoreSezioniParametri");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.DepuratoreSezioniParametri.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "depuratoreTipoOpere");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.DepuratoreTipoOpere.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "destMaterialiRecuperati");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.DestMaterialiRecuperati.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "destMaterialiRecuperatiId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.DestMaterialiRecuperatiId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "dmOperazRecupero");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.DmOperazRecupero.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "dominioValori");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.DominioValori.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "editSoggettiFisici");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.EditSoggettiFisici.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "editSoggettiFisiciResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.EditSoggettiFisiciResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "effZooDatiAzienda");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.EffZooDatiAzienda.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "emissioniConv");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.EmissioniConv.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "emissioniInquinanteAtm");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.EmissioniInquinanteAtm.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "emissioniInquinantiAe");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.EmissioniInquinantiAe.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "emissioniNonconv");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.EmissioniNonconv.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "fasi");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Fasi.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "fasiAttEco");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.FasiAttEco.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "fontiDati");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.FontiDati.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "funzioniSf");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.FunzioniSf.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getAvailableCategoriaList");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetAvailableCategoriaList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getAvailableCategoriaListResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetAvailableCategoriaListResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getCategoriaInfo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetCategoriaInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getCategoriaInfoResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetCategoriaInfoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getCategoriaMetadata");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetCategoriaMetadata.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getCategoriaMetadataResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetCategoriaMetadataResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getTipiSchedaMonitoraggio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetTipiSchedaMonitoraggio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getTipiSchedaMonitoraggioList");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetTipiSchedaMonitoraggioList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getTipiSchedaMonitoraggioListResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetTipiSchedaMonitoraggioListResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getTipiSchedaMonitoraggioResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetTipiSchedaMonitoraggioResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getTipoCampione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetTipoCampione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getTipoCampioneList");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetTipoCampioneList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getTipoCampioneListResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetTipoCampioneListResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getTipoCampioneResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetTipoCampioneResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getVocabularyDescription");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetVocabularyDescription.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getVocabularyDescriptionByVocClassname");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetVocabularyDescriptionByVocClassname.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getVocabularyDescriptionByVocClassnameResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetVocabularyDescriptionByVocClassnameResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getVocabularyDescriptionResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GetVocabularyDescriptionResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "grotteInteressi");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GrotteInteressi.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "grotteInteressiId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GrotteInteressiId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "gruppoRifiutiGestiti");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.GruppoRifiutiGestiti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "habitat");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Habitat.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "igrQuantitaGiornoUm");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.IgrQuantitaGiornoUm.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "igrQuantitaOraUm");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.IgrQuantitaOraUm.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "impDepTipiProcesso");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ImpDepTipiProcesso.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "impDepTipiTrattamento");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ImpDepTipiTrattamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "impDepTrattamenti");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ImpDepTrattamenti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "impiantiEdTipo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ImpiantiEdTipo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "impiantiRcTipo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ImpiantiRcTipo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "impiantiRcTiposervizio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ImpiantiRcTiposervizio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "impiantiStrutturaEf");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ImpiantiStrutturaEf.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "infrastrRicercaForestale");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.InfrastrRicercaForestale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ingressi");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Ingressi.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ingressiFT");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.IngressiFT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "inquinantiEmAcqua");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.InquinantiEmAcqua.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "inquinantiEmAcquaId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.InquinantiEmAcquaId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "inquinantiEmNonconv");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.InquinantiEmNonconv.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "inquinantiEmNonconvId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.InquinantiEmNonconvId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "intervalloValori");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.IntervalloValori.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "isotopiSorgRadioattivita");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.IsotopiSorgRadioattivita.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "lineeEdTensioneNominale");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.LineeEdTensioneNominale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "lineeEdTipoCorrente");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.LineeEdTipoCorrente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "litologieAcquifero");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.LitologieAcquifero.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "litologieAcquiferoId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.LitologieAcquiferoId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "marchi");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Marchi.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "materialeCostiero");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.MaterialeCostiero.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "materialeEstratto");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.MaterialeEstratto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "materialiConduttori");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.MaterialiConduttori.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "materialiStoccati");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.MaterialiStoccati.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "matriciContaminate");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.MatriciContaminate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "metodiMisura");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.MetodiMisura.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "minieraAcqueProduzione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.MinieraAcqueProduzione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "minieraComuni");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.MinieraComuni.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "minieraComuniId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.MinieraComuniId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "minieraMinerali");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.MinieraMinerali.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "minieraMineraliProduzione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.MinieraMineraliProduzione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "minieraTavoletteIgm");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.MinieraTavoletteIgm.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "misuraAnalitica");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.MisuraAnalitica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "misure");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Misure.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "misurePreventiveSicurezza");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.MisurePreventiveSicurezza.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings3() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifLiqTratRaee");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ModStocRifLiqTratRaee.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifLiqTratRaeeId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ModStocRifLiqTratRaeeId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifLiquidiAutodem");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ModStocRifLiquidiAutodem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifLiquidiAutodemId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ModStocRifLiquidiAutodemId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifNonPerAutodem");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ModStocRifNonPerAutodem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifNonPerAutodemId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ModStocRifNonPerAutodemId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifNonPerTratRaee");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ModStocRifNonPerTratRaee.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifNonPerTratRaeeId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ModStocRifNonPerTratRaeeId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifPerAutodem");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ModStocRifPerAutodem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifPerAutodemId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ModStocRifPerAutodemId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifPerTratRaee");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ModStocRifPerTratRaee.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "modStocRifPerTratRaeeId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ModStocRifPerTratRaeeId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "moduloA1A2SitoSpandEz");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ModuloA1A2SitoSpandEz.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "natureOst");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NatureOst.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newAttivitaEsterna");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewAttivitaEsterna.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newAttivitaEsternaResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewAttivitaEsternaResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newCampione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewCampione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newCampioneResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewCampioneResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newCcost");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewCcost.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newCcostResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewCcostResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newFontiDati");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewFontiDati.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newFontiDatiResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewFontiDatiResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newOst");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewOst.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newOstResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewOstResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newRapportoDiProva");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewRapportoDiProva.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newRapportoDiProvaResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewRapportoDiProvaResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newRelazioniOst");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewRelazioniOst.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newRelazioniOstResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewRelazioniOstResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newReteMonitoraggio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewReteMonitoraggio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newReteMonitoraggioResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewReteMonitoraggioResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newSchedaMonitoraggio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewSchedaMonitoraggio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newSchedaMonitoraggioResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewSchedaMonitoraggioResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newSfOst");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewSfOst.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newSfOstResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewSfOstResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newSoggettiFisici");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewSoggettiFisici.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newSoggettiFisiciResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewSoggettiFisiciResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newStatiOst");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewStatiOst.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newStatiOstResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewStatiOstResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newUbicazioniOst");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewUbicazioniOst.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newUbicazioniOstResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.NewUbicazioniOstResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "oggettiStruttureTerritoriali");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.OggettiStruttureTerritoriali.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "opDl15206AllBc");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.OpDl15206AllBc.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "opdl152CcostIgr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Opdl152CcostIgr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "opdl152Puntodm");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Opdl152Puntodm.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "opdl152PuntodmId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Opdl152PuntodmId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pannelli");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Pannelli.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "parametriAmbientali");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ParametriAmbientali.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pemFat");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.PemFat.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pemFatId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.PemFatId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pianiStralcio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.PianiStralcio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pianteVivaioEf");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.PianteVivaioEf.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pressioneAcque");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.PressioneAcque.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pressioneAcqueId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.PressioneAcqueId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooCatAnimali1");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooCatAnimali1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooCatAnimali1Grp");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooCatAnimali1Grp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooCatAnimali2");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooCatAnimali2.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooMatNonPalabile");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooMatNonPalabile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooMatPalabile");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooMatPalabile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaA");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooTabellaA.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaB");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooTabellaB.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaC");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooTabellaC.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaCAltro");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooTabellaCAltro.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaD");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooTabellaD.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaDDati");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooTabellaDDati.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaE");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooTabellaE.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaF");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooTabellaF.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaFDati");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooTabellaFDati.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaG");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooTabellaG.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaH");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooTabellaH.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaI");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooTabellaI.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaJ");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooTabellaJ.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaK");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooTabellaK.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTipoStab1");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooTipoStab1.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTipoStab2");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdEffZooTipoStab2.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodottiFinitiIgrMobile");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdottiFinitiIgrMobile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodottiForestaCertificata");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdottiForestaCertificata.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodottiUscitaIgrMobile");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdottiUscitaIgrMobile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodottiUscitaIgrMobileId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProdottiUscitaIgrMobileId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "produzioneEnergia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProduzioneEnergia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "produzioneFanghi");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProduzioneFanghi.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "produzioneRifiuti");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProduzioneRifiuti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "produzioni");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Produzioni.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "produzionieo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Produzionieo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "produzionifv");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Produzionifv.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "produzioniie");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Produzioniie.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "produzionisr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Produzionisr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "profiliProva");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProfiliProva.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "progettiRicercaForestale");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProgettiRicercaForestale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "proveCombustioneImpTermici");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ProveCombustioneImpTermici.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "puntiLuce");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.PuntiLuce.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "puntiSospensione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.PuntiSospensione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "puntoDm");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.PuntoDm.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "radiazioniSorgRadioattiva");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RadiazioniSorgRadioattiva.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "rapportiProva");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RapportiProva.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "referencedBean");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ReferencedBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relazioniOst");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RelazioniOst.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relazioniOstId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RelazioniOstId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relCampiTipiSchedaMon");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RelCampiTipiSchedaMon.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relCampiTipiSchedaMonPK");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RelCampiTipiSchedaMonPK.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relSiticontOstMinerari");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RelSiticontOstMinerari.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings4() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relSottoschede");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RelSottoschede.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relSottoschedePK");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RelSottoschedePK.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "retiMonitoraggio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RetiMonitoraggio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "retiMonitoraggioCcostPuntiMonitoraggioCon");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RetiMonitoraggioCcostPuntiMonitoraggioCon.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "retiMonitoraggioCcostPuntiMonitoraggioConId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RetiMonitoraggioCcostPuntiMonitoraggioConId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "retiMonitoraggioCcostPuntiMonitoraggioPer");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RetiMonitoraggioCcostPuntiMonitoraggioPer.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "retiMonitoraggioCcostPuntiMonitoraggioPerId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RetiMonitoraggioCcostPuntiMonitoraggioPerId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "riferimentiNormativi");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RiferimentiNormativi.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "rifiutiCer");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RifiutiCer.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "rifiutiGestiti");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RifiutiGestiti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "rifiutiGestitiId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RifiutiGestitiId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "rifiutiProdottiIgrMobile");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RifiutiProdottiIgrMobile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "rifiutiProdottiIgrMobileId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RifiutiProdottiIgrMobileId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ripascimentoAreaCostiera");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.RipascimentoAreaCostiera.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "scarichiParziali");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ScarichiParziali.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "scFaseAccertamento");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ScFaseAccertamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "schedaMonitoraggio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SchedaMonitoraggio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "scTipoMatrice");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ScTipoMatrice.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "searchOst");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SearchOst.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "searchOstResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SearchOstResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "searchSchedaMonitoraggio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SearchSchedaMonitoraggio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "searchSchedaMonitoraggioResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SearchSchedaMonitoraggioResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "searchSoggettiFisici");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SearchSoggettiFisici.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "searchSoggettiFisiciResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SearchSoggettiFisiciResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "searchUnitaLocali");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SearchUnitaLocali.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "searchUnitaLocaliResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SearchUnitaLocaliResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "settoriIgrAutodemolizione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SettoriIgrAutodemolizione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "settoriIgrAutodemolizioneId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SettoriIgrAutodemolizioneId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "settoriIgrTrattamentoRaee");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SettoriIgrTrattamentoRaee.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "settoriIgrTrattamentoRaeeId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SettoriIgrTrattamentoRaeeId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sezioniSchedaMon");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SezioniSchedaMon.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sfOst");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SfOst.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sfOstId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SfOstId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sisradMarchi");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SisradMarchi.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sisradMarchiId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SisradMarchiId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sistemiradianti");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Sistemiradianti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sitoTipologia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SitoTipologia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "soggettiFisici");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SoggettiFisici.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "soggettiFisiciSearchCommand");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SoggettiFisiciSearchCommand.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgAcuPeriodoAttivita");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SorgAcuPeriodoAttivita.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgAcuRicettori");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SorgAcuRicettori.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgentiAffioramento");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SorgentiAffioramento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgentiOpera");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SorgentiOpera.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgentiRegime");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SorgentiRegime.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgentiTipoacqua");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SorgentiTipoacqua.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgOdore");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SorgOdore.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgRumore");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SorgRumore.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostanzePericolose");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SostanzePericolose.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostanzeRilevate");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SostanzeRilevate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostanzeRilevateId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SostanzeRilevateId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostanzeTab3A");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SostanzeTab3A.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostegniEdTipoOrientamento");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SostegniEdTipoOrientamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostegniEdTipoSostegno");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SostegniEdTipoSostegno.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "specie");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.Specie.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "specieAnimali");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SpecieAnimali.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "specieAnimaliId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SpecieAnimaliId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "specieVegetali");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SpecieVegetali.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "specieVegetaliId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SpecieVegetaliId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "spL3A");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.SpL3A.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "statiOperativi");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.StatiOperativi.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "statioperativiCostNost");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.StatioperativiCostNost.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "statioperativiCostNostId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.StatioperativiCostNostId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "statiOst");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.StatiOst.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "statiOstId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.StatiOstId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "stoccaggioFanghi");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.StoccaggioFanghi.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "strLenght");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.StrLenght.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "strLenghtResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.StrLenghtResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tecnologieTrattamento");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TecnologieTrattamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tecnologieTrattamentoId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TecnologieTrattamentoId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tecnologieUtilizzate");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TecnologieUtilizzate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "temiAmbientali");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TemiAmbientali.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiAntenne");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipiAntenne.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiBaseSostegno");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipiBaseSostegno.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiCarattint");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipiCarattint.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiDatoSchedaMon");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipiDatoSchedaMon.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiElementoSchedaMonitoraggio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipiElementoSchedaMonitoraggio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiMisure");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipiMisure.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipimisureProfiliprova");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipimisureProfiliprova.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipimisureProfiliprovaId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipimisureProfiliprovaId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipirostCostNost");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipirostCostNost.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipirostCostNostId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipirostCostNostId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiSchedaMonitoraggio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipiSchedaMonitoraggio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiTestaSostegno");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipiTestaSostegno.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiTrasmettitori");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipiTrasmettitori.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiTratteEd");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipiTratteEd.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipoCampione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipoCampione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipoIsolamento");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipoIsolamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieFontiDati");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipologieFontiDati.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieRost");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipologieRost.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieSpecie");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TipologieSpecie.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "trattamentiPostSelezione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TrattamentiPostSelezione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "trattamentiPostSelezioneId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TrattamentiPostSelezioneId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "trattamentoEmissioni");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.TrattamentoEmissioni.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ubicazioniOst");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.UbicazioniOst.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "unitaDiMisuraCia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.UnitaDiMisuraCia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "unitaIdrogeologica");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.UnitaIdrogeologica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "unitaIdrogeologicaId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.UnitaIdrogeologicaId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "unitaLocaliSearchCommand");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.UnitaLocaliSearchCommand.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaN");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.UtilEffZooTabellaN.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaO");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.UtilEffZooTabellaO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings5() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaP");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.UtilEffZooTabellaP.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaQ");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.UtilEffZooTabellaQ.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaR");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.UtilEffZooTabellaR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaS");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.UtilEffZooTabellaS.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaT");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.UtilEffZooTabellaT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaU");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.UtilEffZooTabellaU.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "valCampiRipSchedeMon");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ValCampiRipSchedeMon.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "valCampiSchedeMon");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ValCampiSchedeMon.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "valCampiSchedeMonPK");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ValCampiSchedeMonPK.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "valoriLimite");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ValoriLimite.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "valoriLimiteId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ValoriLimiteId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vedetteAntincendio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VedetteAntincendio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "veicoliAutoparcoEf");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VeicoliAutoparcoEf.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "viabilitaAntincendio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ViabilitaAntincendio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "viewFonte");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ViewFonte.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "viewFonteResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ViewFonteResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "viewOst");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ViewOst.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "viewOstResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ViewOstResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "viewSchedaMonitoraggio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ViewSchedaMonitoraggio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "viewSchedaMonitoraggioResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ViewSchedaMonitoraggioResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "viewSoggettiFisici");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ViewSoggettiFisici.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "viewSoggettiFisiciResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ViewSoggettiFisiciResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "visitOst");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VisitOst.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "visitOstResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VisitOstResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocabolariDiDominio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocabolariDiDominio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocabularyDescription");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocabularyDescription.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocabularyItem");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocabularyItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocabularyItemExtra");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocabularyItemExtra.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAliment");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAliment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAlimentComb");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAlimentComb.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAlimentPompaCalore");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAlimentPompaCalore.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmAccessibilita");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmAccessibilita.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmAttivitaFunzione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmAttivitaFunzione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmDensitaPopolazione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmDensitaPopolazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmDistanzaAbitato");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmDistanzaAbitato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmEstensione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmEstensione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmEstensioneAffioramento");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmEstensioneAffioramento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmEtaMedia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmEtaMedia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmFibreAerodisperse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmFibreAerodisperse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmFreqUtilizzo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmFreqUtilizzo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmMaterialeAffioramento");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmMaterialeAffioramento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmPeriodoDismissione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmPeriodoDismissione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmPresenzaAffioramento");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmPresenzaAffioramento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmQuantitaCompatto");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmQuantitaCompatto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmQuantitaFriabile");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmQuantitaFriabile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmStatoConservazione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmStatoConservazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmStatoFinanziamento");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmStatoFinanziamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmSuperficieEsposta");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmSuperficieEsposta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmTipologia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmTipologia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmTipologiaAmianto");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmTipologiaAmianto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmTipologiaBonifica");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmTipologiaBonifica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmTipologiaFondi");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmTipologiaFondi.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmTipologiaIntervento");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmTipologiaIntervento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmUso");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAmUso.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAttivita");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocAttivita.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocBmaliment");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocBmaliment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocBmimpazoto");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocBmimpazoto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocBmtipoprod");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocBmtipoprod.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocBmumis");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocBmumis.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocCaratterizzazioneRischio");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocCaratterizzazioneRischio.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocCategoriaZu");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocCategoriaZu.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClassCAntr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocClassCAntr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClasseChimica");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocClasseChimica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClassePericoloIdrogeo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocClassePericoloIdrogeo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClasseQuantitativo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocClasseQuantitativo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClasseRischioIdrogeo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocClasseRischioIdrogeo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClassificazioneEta");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocClassificazioneEta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClassificazioneStrada");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocClassificazioneStrada.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClassifLitologia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocClassifLitologia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClassiInformazioni");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocClassiInformazioni.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocDepTipologieOpere");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocDepTipologieOpere.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocDepTipologieParametri");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocDepTipologieParametri.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocDepTipologieSezioni");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocDepTipologieSezioni.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocDestinazioneEffluente");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocDestinazioneEffluente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocDestinazioneUsoTerm");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocDestinazioneUsoTerm.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocDestino");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocDestino.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "voceDiVocabolario");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VoceDiVocabolario.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfAbbRinnFustaie");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfAbbRinnFustaie.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfAccidentalita");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfAccidentalita.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfAttivitaPrimaria");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfAttivitaPrimaria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfCausaDannoSubpart");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfCausaDannoSubpart.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfClasseCopertura");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfClasseCopertura.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfClasseEta");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfClasseEta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfDistribuzioneDanno");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfDistribuzioneDanno.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfDistrMatricinatura");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfDistrMatricinatura.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfEntitaMatricinatura");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfEntitaMatricinatura.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfFunzionePrevalente");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfFunzionePrevalente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfFunzionePrevalenteSF");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfFunzionePrevalenteSF.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfGEvolCopForestale");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfGEvolCopForestale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfGravitaDanno");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfGravitaDanno.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfMaterialeFondo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfMaterialeFondo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfModelloCombustibile");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfModelloCombustibile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfNumCeppaieEttaro");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfNumCeppaieEttaro.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfOrigineBosco");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfOrigineBosco.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfOrSelvicolturale");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfOrSelvicolturale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfOutputCategory");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfOutputCategory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfPietrosita");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfPietrosita.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfProfonditaSuolo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfProfonditaSuolo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfProprietaComp");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfProprietaComp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfRegimazioneIdrica");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfRegimazioneIdrica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings6() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfRimbPrepTerreno");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfRimbPrepTerreno.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfRocciosita");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfRocciosita.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfStatoUso");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfStatoUso.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfStatoVegetativoRinn");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfStatoVegetativoRinn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfSuperficieDanno");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfSuperficieDanno.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipoAmministrativo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfTipoAmministrativo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipoCertificato");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfTipoCertificato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipoColturale");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfTipoColturale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipoCopForestale");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfTipoCopForestale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipoFabbr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfTipoFabbr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipologiaDanno");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfTipologiaDanno.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipologiaImpianto");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfTipologiaImpianto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipologiaProdotto");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfTipologiaProdotto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipologiaTitolo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfTipologiaTitolo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipologiaTitoloGestione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfTipologiaTitoloGestione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipologiaUgb");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfTipologiaUgb.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipologiaUsoSuolo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfTipologiaUsoSuolo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipologiaVeicolo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfTipologiaVeicolo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipoTracciato");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfTipoTracciato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTransitabilita");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocEfTransitabilita.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocFonteEnergia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocFonteEnergia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocFonti");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocFonti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocFontiEnergetiche");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocFontiEnergetiche.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocFrCoperturaContenitore");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocFrCoperturaContenitore.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocFrDaticatSitispTitPoss");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocFrDaticatSitispTitPoss.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocFrTipoCicloLav");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocFrTipoCicloLav.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocFrTipoContenitore");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocFrTipoContenitore.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocGiudizioFunzionalitaOpAcq");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocGiudizioFunzionalitaOpAcq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocGradoAntrSpiaggia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocGradoAntrSpiaggia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocGradoQualita");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocGradoQualita.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocIdrologiaIngressoGrotta");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocIdrologiaIngressoGrotta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocImpatti");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocImpatti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocImpiantoMobile");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocImpiantoMobile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocImpIns");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocImpIns.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocIntArch");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocIntArch.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocIntegrazione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocIntegrazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocInteresseGrotte");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocInteresseGrotte.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocInternaEsterna");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocInternaEsterna.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocIsotopo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocIsotopo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocLineaTrattamento");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocLineaTrattamento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocLitologie");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocLitologie.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocMaterialeCAntr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocMaterialeCAntr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocMaterialePrevalenteOpAcq");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocMaterialePrevalenteOpAcq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocMaterialeTubazioni");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocMaterialeTubazioni.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocModalita");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocModalita.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocMorfologiaIngressoGrotta");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocMorfologiaIngressoGrotta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocMorfUnitaFis");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocMorfUnitaFis.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocOpDl15206AllBc");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocOpDl15206AllBc.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocPericolositaInquinante");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocPericolositaInquinante.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocPosizione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocPosizione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocPosizioneImpianto");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocPosizioneImpianto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocPressioneAcque");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocPressioneAcque.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocProduzioneUtilizzo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocProduzioneUtilizzo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocProprieta");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocProprieta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocProtezione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocProtezione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocQuantitativo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocQuantitativo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocRegioneBiogeografica");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocRegioneBiogeografica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocServiziEcosistemici");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocServiziEcosistemici.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocSettoreRicerca");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocSettoreRicerca.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocSidTipoTubazione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocSidTipoTubazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocSorgScambioTermico");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocSorgScambioTermico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocStatoFisico");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocStatoFisico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocStatoSostRadioattiva");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocStatoSostRadioattiva.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTendEvolCRocc");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTendEvolCRocc.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTendEvolSpiaggia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTendEvolSpiaggia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTessituraSpiaggia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTessituraSpiaggia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipAerogen");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipAerogen.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipContratt");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipContratt.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipContrattBiomasse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipContrattBiomasse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipContrattEo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipContrattEo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipContrattFv");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipContrattFv.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipContrattIe");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipContrattIe.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipImp");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipImp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipo1CAntr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipo1CAntr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipo2CAntr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipo2CAntr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipo3CAntr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipo3CAntr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipo4CAntr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipo4CAntr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipo5CAntr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipo5CAntr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipo6CAntr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipo6CAntr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoAzienda");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoAzienda.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoCaldaia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoCaldaia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoCombustibile");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoCombustibile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoDune");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoDune.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoFunzionamentoOpAcq");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoFunzionamentoOpAcq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoGeneratore");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoGeneratore.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoImpiantoMezzo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoImpiantoMezzo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaAcqueSott");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaAcqueSott.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaArea");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaArea.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaContenitore");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaContenitore.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaCorpoIdrico");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaCorpoIdrico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaDiStrada");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaDiStrada.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaEffluente");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaEffluente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaEmissione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaEmissione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaFasciaParafuoco");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaFasciaParafuoco.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaFerrovia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaFerrovia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaInfr");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaInfr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaLampade");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaLampade.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaPannelli");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaPannelli.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings7() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaPossesso");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaPossesso.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaRicettori");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaRicettori.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaRIR");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaRIR.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaScaricoTerm");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaScaricoTerm.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaSorgente");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaSorgente.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaSorgenteLuce");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaSorgenteLuce.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaStrada");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaStrada.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaSuperficie");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaSuperficie.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaTitoloMinerario");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaTitoloMinerario.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaUnitaFis");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaUnitaFis.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaZu");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaZu.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaZuRamsar");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipologiaZuRamsar.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoMaterialeAemc");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoMaterialeAemc.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoMateriaPrima");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoMateriaPrima.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoMineraleProdotto");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoMineraleProdotto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoOperaAcq");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoOperaAcq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoPericoloIdrogeo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoPericoloIdrogeo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoPompaCalore");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoPompaCalore.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoRadiazione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoRadiazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoRecettore");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoRecettore.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoRicettoreAcustico");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoRicettoreAcustico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoRischioIdrogeo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoRischioIdrogeo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoScarico");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoScarico.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoScaricoParziale");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoScaricoParziale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoSitiCont");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoSitiCont.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoSorgAcustica");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoSorgAcustica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoSorgentePrimaria");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoSorgentePrimaria.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoSuperficie");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoSuperficie.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoVegetazione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipoVegetazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipPannelli");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipPannelli.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipPannelliFv");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTipPannelliFv.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTitoloGestione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocTitoloGestione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocUnitaIdrogeologica");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocUnitaIdrogeologica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocUnitaMisuraAreale");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocUnitaMisuraAreale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocUtilizzo");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocUtilizzo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocValidita");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocValidita.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocVarchiSpiaggia");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocVarchiSpiaggia.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocZona");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.VocZona.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsAttivitaEsternaRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsAttivitaEsternaRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsBeginTransactionRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsBeginTransactionRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsBeginTransactionResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsBeginTransactionResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCampioneRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsCampioneRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCampiSchedaMonitoraggioRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsCampiSchedaMonitoraggioRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCcostDescriptor");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsCcostDescriptor.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCcostRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsCcostRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCommitTransactionRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsCommitTransactionRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCommitTransactionResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsCommitTransactionResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCostNostRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsCostNostRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCountSoggettiFisiciRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsCountSoggettiFisiciRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCountSoggettiFisiciResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsCountSoggettiFisiciResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsEditSoggettiFisiciRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsEditSoggettiFisiciRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsEditSoggettiFisiciResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsEditSoggettiFisiciResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsFonteGenerica");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsFonteGenerica.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsFonteRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsFonteRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsFontiDatiRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsFontiDatiRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsFontiDatiResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsFontiDatiResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetAvailableCategoriaListRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsGetAvailableCategoriaListRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetAvailableCategoriaListResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsCostNostRef[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCostNostRef");
            qName2 = new javax.xml.namespace.QName("", "wsCostNostRefs");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetCategoriaInfoRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsGetCategoriaInfoRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetCategoriaInfoResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsGetCategoriaInfoResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetCategoriaMetadataRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsGetCategoriaMetadataRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetCategoriaMetadataResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsGetCategoriaMetadataResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipiSchedaMonitoraggioListRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsGetTipiSchedaMonitoraggioListRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipiSchedaMonitoraggioListResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioRef[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTipiSchedaMonitoraggioRef");
            qName2 = new javax.xml.namespace.QName("", "wsTipiSchedaMonitoraggioRef");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipiSchedaMonitoraggioRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsGetTipiSchedaMonitoraggioRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipiSchedaMonitoraggioResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsGetTipiSchedaMonitoraggioResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipoCampioneListRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsGetTipoCampioneListRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipoCampioneListResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsTipoCampioneRef[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTipoCampioneRef");
            qName2 = new javax.xml.namespace.QName("", "wsTipoCampioneRefs");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipoCampioneRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsGetTipoCampioneRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetTipoCampioneResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsGetTipoCampioneResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetVocabularyDescriptionByVocClassnameRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsGetVocabularyDescriptionByVocClassnameRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetVocabularyDescriptionRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsGetVocabularyDescriptionRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsGetVocabularyDescriptionResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsGetVocabularyDescriptionResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsIntegerRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsIntegerRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsMetadataDescriptorBase");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsMetadataDescriptorBase.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewAttivitaEsternaRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewAttivitaEsternaRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewAttivitaEsternaResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewAttivitaEsternaResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewCampioneRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewCampioneRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewCampioneResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewCampioneResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewCcostRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewCcostRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewCcostResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewCcostResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewFontiDatiRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewFontiDatiRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewOstRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewOstRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewOstResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewOstResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewRetiMonitoraggioRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewRetiMonitoraggioRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewRetiMonitoraggioResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewRetiMonitoraggioResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewRostRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewRostRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewRostResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewRostResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewSchedaMonitoraggioRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewSchedaMonitoraggioRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewSchedaMonitoraggioResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewSchedaMonitoraggioResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewSfOstRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewSfOstRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewSfOstResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewSfOstResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewSoggettiFisiciRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewSoggettiFisiciRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewSoggettiFisiciResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewSoggettiFisiciResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewUbicazioniOstRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewUbicazioniOstRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewUbicazioniOstResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsNewUbicazioniOstResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsOstRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsOstRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsPage");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsPage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsProfiliProvaRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsProfiliProvaRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsPropertyDescriptor");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsPropertyDescriptor.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings8() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsRetiMonitoraggioRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsRetiMonitoraggioRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsRiferimentiNormativiRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsRiferimentiNormativiRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSchedaMonitoraggioRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsSchedaMonitoraggioRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSearchOstRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsSearchOstRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSearchOstResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsSearchOstResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSearchSchedaMonitoraggioRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsSearchSchedaMonitoraggioRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSearchSchedaMonitoraggioResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsSearchSchedaMonitoraggioResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSearchSoggettiFisiciRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsSearchSoggettiFisiciRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSearchSoggettiFisiciResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsSearchSoggettiFisiciResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSearchUnitaLocaliRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsSearchUnitaLocaliRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSearchUnitaLocaliResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsUnitaLocaliRef[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsUnitaLocaliRef");
            qName2 = new javax.xml.namespace.QName("", "wsUnitaLocaliRefs");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSoggettiFisiciRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsSoggettiFisiciRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsStatiOstRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsStatiOstRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsStatiOstRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsStatiOstRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsStatiOstResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsStatiOstResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSubmitRapportoDiProvaRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsSubmitRapportoDiProvaRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSubmitRapportoDiProvaResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsSubmitRapportoDiProvaResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTaskReference");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsTaskReference.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTipiMisureRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsTipiMisureRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTipiSchedaMonitoraggioDescriptor");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioDescriptor.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTipiSchedaMonitoraggioRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTipoCampioneRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsTipoCampioneRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsToken");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsToken.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsUbicazioniOstRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsUbicazioniOstRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsUnitaLocaliRef");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsUnitaLocaliRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewFonteRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsViewFonteRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewFonteResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsViewFonteResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewOstRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsViewOstRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewOstResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsViewOstResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewSchedaMonitoraggioRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsViewSchedaMonitoraggioRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewSchedaMonitoraggioResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsViewSchedaMonitoraggioResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewSoggettiFisiciRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsViewSoggettiFisiciRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewSoggettiFisiciResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsViewSoggettiFisiciResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsVisitedOst");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsVisitedOst.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsVisitOstRequest");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsVisitOstRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsVisitOstResponse");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.WsVisitedOst[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsVisitedOst");
            qName2 = new javax.xml.namespace.QName("", "wsVisitedOst");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zonaOmogeneaRicettori");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ZonaOmogeneaRicettori.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zuAttivitaUmane");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ZuAttivitaUmane.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zuAttivitaUmaneId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ZuAttivitaUmaneId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zuImpatti");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ZuImpatti.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zuImpattiId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ZuImpattiId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zuServiziEcosistemici");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ZuServiziEcosistemici.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zuServiziEcosistemiciId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ZuServiziEcosistemiciId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zuStatoProtezione");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ZuStatoProtezione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zuStatoProtezioneId");
            cachedSerQNames.add(qName);
            cls = com.hyperborea.sira.ws.ZuStatoProtezioneId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.hyperborea.sira.ws.WsCountSoggettiFisiciResponse countSoggettiFisici(com.hyperborea.sira.ws.WsCountSoggettiFisiciRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "countSoggettiFisici"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsCountSoggettiFisiciResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsCountSoggettiFisiciResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsCountSoggettiFisiciResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsSearchSoggettiFisiciResponse searchSoggettiFisici(com.hyperborea.sira.ws.WsSearchSoggettiFisiciRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "searchSoggettiFisici"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsSearchSoggettiFisiciResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsSearchSoggettiFisiciResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsSearchSoggettiFisiciResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsUnitaLocaliRef[] searchUnitaLocali(com.hyperborea.sira.ws.WsSearchUnitaLocaliRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "searchUnitaLocali"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsUnitaLocaliRef[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsUnitaLocaliRef[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsUnitaLocaliRef[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsViewSoggettiFisiciResponse viewSoggettiFisici(com.hyperborea.sira.ws.WsViewSoggettiFisiciRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "viewSoggettiFisici"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsViewSoggettiFisiciResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsViewSoggettiFisiciResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsViewSoggettiFisiciResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsNewSoggettiFisiciResponse newSoggettiFisici(com.hyperborea.sira.ws.WsNewSoggettiFisiciRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newSoggettiFisici"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsNewSoggettiFisiciResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsNewSoggettiFisiciResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsNewSoggettiFisiciResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsEditSoggettiFisiciResponse editSoggettiFisici(com.hyperborea.sira.ws.WsEditSoggettiFisiciRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "editSoggettiFisici"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsEditSoggettiFisiciResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsEditSoggettiFisiciResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsEditSoggettiFisiciResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsNewSfOstResponse newSfOst(com.hyperborea.sira.ws.WsNewSfOstRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newSfOst"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsNewSfOstResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsNewSfOstResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsNewSfOstResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsGetVocabularyDescriptionResponse getVocabularyDescription(com.hyperborea.sira.ws.WsGetVocabularyDescriptionRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getVocabularyDescription"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsGetVocabularyDescriptionResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsGetVocabularyDescriptionResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsGetVocabularyDescriptionResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsGetVocabularyDescriptionResponse getVocabularyDescriptionByVocClassname(com.hyperborea.sira.ws.WsGetVocabularyDescriptionByVocClassnameRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getVocabularyDescriptionByVocClassname"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsGetVocabularyDescriptionResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsGetVocabularyDescriptionResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsGetVocabularyDescriptionResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsNewCampioneResponse newCampione(com.hyperborea.sira.ws.WsNewCampioneRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newCampione"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsNewCampioneResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsNewCampioneResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsNewCampioneResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsNewAttivitaEsternaResponse newAttivitaEsterna(com.hyperborea.sira.ws.WsNewAttivitaEsternaRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newAttivitaEsterna"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsNewAttivitaEsternaResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsNewAttivitaEsternaResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsNewAttivitaEsternaResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsTipoCampioneRef[] getTipoCampioneList(com.hyperborea.sira.ws.WsGetTipoCampioneListRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getTipoCampioneList"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsTipoCampioneRef[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsTipoCampioneRef[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsTipoCampioneRef[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsGetTipoCampioneResponse getTipoCampione(com.hyperborea.sira.ws.WsGetTipoCampioneRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getTipoCampione"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsGetTipoCampioneResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsGetTipoCampioneResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsGetTipoCampioneResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsNewUbicazioniOstResponse newUbicazioniOst(com.hyperborea.sira.ws.WsNewUbicazioniOstRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newUbicazioniOst"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsNewUbicazioniOstResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsNewUbicazioniOstResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsNewUbicazioniOstResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsNewRetiMonitoraggioResponse newReteMonitoraggio(com.hyperborea.sira.ws.WsNewRetiMonitoraggioRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newReteMonitoraggio"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsNewRetiMonitoraggioResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsNewRetiMonitoraggioResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsNewRetiMonitoraggioResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsGetTipiSchedaMonitoraggioResponse getTipiSchedaMonitoraggio(com.hyperborea.sira.ws.WsGetTipiSchedaMonitoraggioRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getTipiSchedaMonitoraggio"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsGetTipiSchedaMonitoraggioResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsGetTipiSchedaMonitoraggioResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsGetTipiSchedaMonitoraggioResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioRef[] getTipiSchedaMonitoraggioList(com.hyperborea.sira.ws.WsGetTipiSchedaMonitoraggioListRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getTipiSchedaMonitoraggioList"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioRef[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioRef[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioRef[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsNewSchedaMonitoraggioResponse newSchedaMonitoraggio(com.hyperborea.sira.ws.WsNewSchedaMonitoraggioRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newSchedaMonitoraggio"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsNewSchedaMonitoraggioResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsNewSchedaMonitoraggioResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsNewSchedaMonitoraggioResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsViewSchedaMonitoraggioResponse viewSchedaMonitoraggio(com.hyperborea.sira.ws.WsViewSchedaMonitoraggioRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "viewSchedaMonitoraggio"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsViewSchedaMonitoraggioResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsViewSchedaMonitoraggioResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsViewSchedaMonitoraggioResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsSearchSchedaMonitoraggioResponse searchSchedaMonitoraggio(com.hyperborea.sira.ws.WsSearchSchedaMonitoraggioRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "searchSchedaMonitoraggio"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsSearchSchedaMonitoraggioResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsSearchSchedaMonitoraggioResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsSearchSchedaMonitoraggioResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.Long strLenght(java.lang.String arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "strLenght"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.Long) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.Long) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.Long.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.Long countOst(com.hyperborea.sira.ws.CcostAnySearchComand arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[21]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "countOst"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.Long) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.Long) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.Long.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsSearchOstResponse searchOst(com.hyperborea.sira.ws.WsSearchOstRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[22]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "searchOst"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsSearchOstResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsSearchOstResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsSearchOstResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsVisitedOst[] visitOst(com.hyperborea.sira.ws.WsVisitOstRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[23]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "visitOst"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsVisitedOst[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsVisitedOst[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsVisitedOst[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsViewOstResponse viewOst(com.hyperborea.sira.ws.WsViewOstRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[24]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "viewOst"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsViewOstResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsViewOstResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsViewOstResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsNewCcostResponse newCcost(com.hyperborea.sira.ws.WsNewCcostRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[25]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newCcost"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsNewCcostResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsNewCcostResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsNewCcostResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsViewFonteResponse viewFonte(com.hyperborea.sira.ws.WsViewFonteRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[26]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "viewFonte"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsViewFonteResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsViewFonteResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsViewFonteResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsFontiDatiResponse newFontiDati(com.hyperborea.sira.ws.WsNewFontiDatiRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[27]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newFontiDati"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsFontiDatiResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsFontiDatiResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsFontiDatiResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsNewOstResponse newOst(com.hyperborea.sira.ws.WsNewOstRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[28]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newOst"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsNewOstResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsNewOstResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsNewOstResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsNewRostResponse newRelazioniOst(com.hyperborea.sira.ws.WsNewRostRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[29]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newRelazioniOst"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsNewRostResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsNewRostResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsNewRostResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsGetCategoriaMetadataResponse getCategoriaMetadata(com.hyperborea.sira.ws.WsGetCategoriaMetadataRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[30]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getCategoriaMetadata"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsGetCategoriaMetadataResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsGetCategoriaMetadataResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsGetCategoriaMetadataResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsGetCategoriaInfoResponse getCategoriaInfo(com.hyperborea.sira.ws.WsGetCategoriaInfoRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[31]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getCategoriaInfo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsGetCategoriaInfoResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsGetCategoriaInfoResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsGetCategoriaInfoResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsCostNostRef[] getAvailableCategoriaList(com.hyperborea.sira.ws.WsGetAvailableCategoriaListRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[32]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "getAvailableCategoriaList"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsCostNostRef[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsCostNostRef[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsCostNostRef[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsSubmitRapportoDiProvaResponse newRapportoDiProva(com.hyperborea.sira.ws.WsSubmitRapportoDiProvaRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[33]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newRapportoDiProva"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsSubmitRapportoDiProvaResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsSubmitRapportoDiProvaResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsSubmitRapportoDiProvaResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsBeginTransactionResponse beginTransaction(com.hyperborea.sira.ws.WsBeginTransactionRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[34]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "beginTransaction"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsBeginTransactionResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsBeginTransactionResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsBeginTransactionResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsCommitTransactionResponse closeTransaction(com.hyperborea.sira.ws.WsCommitTransactionRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[35]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "closeTransaction"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsCommitTransactionResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsCommitTransactionResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsCommitTransactionResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.hyperborea.sira.ws.WsStatiOstResponse newStatiOst(com.hyperborea.sira.ws.WsStatiOstRequest arg0) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[36]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "newStatiOst"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.hyperborea.sira.ws.WsStatiOstResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.hyperborea.sira.ws.WsStatiOstResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.hyperborea.sira.ws.WsStatiOstResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
