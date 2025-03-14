/**
 * MguServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.sira.mgu.ws;

public class MguServiceSoapBindingStub extends org.apache.axis.client.Stub implements it.eng.sira.mgu.ws.MguServiceEndPoint {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[9];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getDelegheRicevute");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguDelegaDTO"));
        oper.setReturnClass(it.eng.sira.mgu.ws.MguDelegaDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "UnknowUserException"),
                      "it.eng.sira.mgu.ws.UnknowUserExceptionbean",
                      new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "unknowUserExceptionbean"), 
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getDominioFromId");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"), java.lang.Long.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguDominioDTO"));
        oper.setReturnClass(it.eng.sira.mgu.ws.MguDominioDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getRisorse");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguRisorsaDTO"));
        oper.setReturnClass(it.eng.sira.mgu.ws.MguRisorsaDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "UnknowUserException"),
                      "it.eng.sira.mgu.ws.UnknowUserExceptionbean",
                      new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "unknowUserExceptionbean"), 
                      true
                     ));
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getPermessiRisorseRadice");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguPermessoRisorsaDTO"));
        oper.setReturnClass(it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "UnknowUserException"),
                      "it.eng.sira.mgu.ws.UnknowUserExceptionbean",
                      new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "unknowUserExceptionbean"), 
                      true
                     ));
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getPermessiRisorse");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguPermessoRisorsaDTO"));
        oper.setReturnClass(it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "UnknowUserException"),
                      "it.eng.sira.mgu.ws.UnknowUserExceptionbean",
                      new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "unknowUserExceptionbean"), 
                      true
                     ));
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUtente");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguUtenteDTO"));
        oper.setReturnClass(it.eng.sira.mgu.ws.MguUtenteDTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "UnknowUserException"),
                      "it.eng.sira.mgu.ws.UnknowUserExceptionbean",
                      new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "unknowUserExceptionbean"), 
                      true
                     ));
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getPermessi");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguPermessoDTO"));
        oper.setReturnClass(it.eng.sira.mgu.ws.MguPermessoDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "UnknowUserException"),
                      "it.eng.sira.mgu.ws.UnknowUserExceptionbean",
                      new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "unknowUserExceptionbean"), 
                      true
                     ));
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getDomini");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguDominioDTO"));
        oper.setReturnClass(it.eng.sira.mgu.ws.MguDominioDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "UnknowUserException"),
                      "it.eng.sira.mgu.ws.UnknowUserExceptionbean",
                      new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "unknowUserExceptionbean"), 
                      true
                     ));
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getRisorseRadice");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "arg1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguRisorsaDTO"));
        oper.setReturnClass(it.eng.sira.mgu.ws.MguRisorsaDTO[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "UnknowUserException"),
                      "it.eng.sira.mgu.ws.UnknowUserExceptionbean",
                      new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "unknowUserExceptionbean"), 
                      true
                     ));
        _operations[8] = oper;

    }

    public MguServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public MguServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public MguServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguDelegaDTO");
            cachedSerQNames.add(qName);
            cls = it.eng.sira.mgu.ws.MguDelegaDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguDominioDTO");
            cachedSerQNames.add(qName);
            cls = it.eng.sira.mgu.ws.MguDominioDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguPermessoDTO");
            cachedSerQNames.add(qName);
            cls = it.eng.sira.mgu.ws.MguPermessoDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguPermessoRisorsaDTO");
            cachedSerQNames.add(qName);
            cls = it.eng.sira.mgu.ws.MguPermessoRisorsaDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguRisorsaDTO");
            cachedSerQNames.add(qName);
            cls = it.eng.sira.mgu.ws.MguRisorsaDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguSgiuridicoOstDTO");
            cachedSerQNames.add(qName);
            cls = it.eng.sira.mgu.ws.MguSgiuridicoOstDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguUtenteDTO");
            cachedSerQNames.add(qName);
            cls = it.eng.sira.mgu.ws.MguUtenteDTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "stato");
            cachedSerQNames.add(qName);
            cls = it.eng.sira.mgu.ws.Stato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "unknowUserExceptionbean");
            cachedSerQNames.add(qName);
            cls = it.eng.sira.mgu.ws.UnknowUserExceptionbean.class;
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

    public it.eng.sira.mgu.ws.MguDelegaDTO[] getDelegheRicevute(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "getDelegheRicevute"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.eng.sira.mgu.ws.MguDelegaDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.eng.sira.mgu.ws.MguDelegaDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.eng.sira.mgu.ws.MguDelegaDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.eng.sira.mgu.ws.UnknowUserExceptionbean) {
              throw (it.eng.sira.mgu.ws.UnknowUserExceptionbean) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.eng.sira.mgu.ws.MguDominioDTO getDominioFromId(java.lang.Long arg0) throws java.rmi.RemoteException {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "getDominioFromId"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.eng.sira.mgu.ws.MguDominioDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.eng.sira.mgu.ws.MguDominioDTO) org.apache.axis.utils.JavaUtils.convert(_resp, it.eng.sira.mgu.ws.MguDominioDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.eng.sira.mgu.ws.MguRisorsaDTO[] getRisorse(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "getRisorse"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.eng.sira.mgu.ws.MguRisorsaDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.eng.sira.mgu.ws.MguRisorsaDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.eng.sira.mgu.ws.MguRisorsaDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.eng.sira.mgu.ws.UnknowUserExceptionbean) {
              throw (it.eng.sira.mgu.ws.UnknowUserExceptionbean) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[] getPermessiRisorseRadice(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "getPermessiRisorseRadice"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0, arg1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.eng.sira.mgu.ws.UnknowUserExceptionbean) {
              throw (it.eng.sira.mgu.ws.UnknowUserExceptionbean) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[] getPermessiRisorse(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "getPermessiRisorse"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.eng.sira.mgu.ws.UnknowUserExceptionbean) {
              throw (it.eng.sira.mgu.ws.UnknowUserExceptionbean) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.eng.sira.mgu.ws.MguUtenteDTO getUtente(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "getUtente"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.eng.sira.mgu.ws.MguUtenteDTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.eng.sira.mgu.ws.MguUtenteDTO) org.apache.axis.utils.JavaUtils.convert(_resp, it.eng.sira.mgu.ws.MguUtenteDTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.eng.sira.mgu.ws.UnknowUserExceptionbean) {
              throw (it.eng.sira.mgu.ws.UnknowUserExceptionbean) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.eng.sira.mgu.ws.MguPermessoDTO[] getPermessi(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "getPermessi"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.eng.sira.mgu.ws.MguPermessoDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.eng.sira.mgu.ws.MguPermessoDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.eng.sira.mgu.ws.MguPermessoDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.eng.sira.mgu.ws.UnknowUserExceptionbean) {
              throw (it.eng.sira.mgu.ws.UnknowUserExceptionbean) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.eng.sira.mgu.ws.MguDominioDTO[] getDomini(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "getDomini"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.eng.sira.mgu.ws.MguDominioDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.eng.sira.mgu.ws.MguDominioDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.eng.sira.mgu.ws.MguDominioDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.eng.sira.mgu.ws.UnknowUserExceptionbean) {
              throw (it.eng.sira.mgu.ws.UnknowUserExceptionbean) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.eng.sira.mgu.ws.MguRisorsaDTO[] getRisorseRadice(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean {
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
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "getRisorseRadice"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {arg0, arg1});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.eng.sira.mgu.ws.MguRisorsaDTO[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.eng.sira.mgu.ws.MguRisorsaDTO[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.eng.sira.mgu.ws.MguRisorsaDTO[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.eng.sira.mgu.ws.UnknowUserExceptionbean) {
              throw (it.eng.sira.mgu.ws.UnknowUserExceptionbean) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}
