
package it.eng.hsm.client.medas.syncsign.generated;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ScrybaSignServerSync", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ScrybaSignServerSync {


    /**
     * 
     * @param certListRequest
     * @return
     *     returns it.medas_solutions.scrybasignserver.CertListResp
     */
    @WebMethod(operationName = "CertList", action = "http://demo.medas-solutions.it/ScrybaSignServer/SyncSign/CertList")
    @WebResult(name = "CertListResp", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/", partName = "CertListResponse")
    public CertListResp certList(
        @WebParam(name = "CertListReq", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/", partName = "CertListRequest")
        CertListReq certListRequest);

    /**
     * 
     * @param getUserInfoRequest
     * @return
     *     returns it.medas_solutions.scrybasignserver.GetUserInfoResp
     */
    @WebMethod(operationName = "GetUserInfo", action = "http://demo.medas-solutions.it/ScrybaSignServer/SyncSign/GetUserInfo")
    @WebResult(name = "GetUserInfoResp", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/", partName = "GetUserInfoResponse")
    public GetUserInfoResp getUserInfo(
        @WebParam(name = "GetUserInfoReq", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/", partName = "GetUserInfoRequest")
        GetUserInfoReq getUserInfoRequest);

    /**
     * 
     * @param openSignSessionRequest
     * @return
     *     returns it.medas_solutions.scrybasignserver.OpenSignSessionResp
     */
    @WebMethod(operationName = "OpenSignSession", action = "http://demo.medas-solutions.it/ScrybaSignServer/SyncSign/OpenSignSession")
    @WebResult(name = "OpenSignSessionResp", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/", partName = "OpenSignSessionResponse")
    public OpenSignSessionResp openSignSession(
        @WebParam(name = "OpenSignSessionReq", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/", partName = "OpenSignSessionRequest")
        OpenSignSessionReq openSignSessionRequest);

    /**
     * 
     * @param getUserInfoRequest2
     * @return
     *     returns it.medas_solutions.scrybasignserver.GetUserInfoResp2
     */
    @WebMethod(operationName = "GetUserInfo2", action = "http://demo.medas-solutions.it/ScrybaSignServer/SyncSign/GetUserInfo2")
    @WebResult(name = "GetUserInfoResp2", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/", partName = "GetUserInfoResponse2")
    public GetUserInfoResp2 getUserInfo2(
        @WebParam(name = "GetUserInfoReq2", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/", partName = "GetUserInfoRequest2")
        GetUserInfoReq2 getUserInfoRequest2);

    /**
     * 
     * @param signDocRequest
     * @return
     *     returns it.medas_solutions.scrybasignserver.SignDocResp
     */
    @WebMethod(operationName = "SignDoc", action = "http://demo.medas-solutions.it/ScrybaSignServer/SyncSign/SignDoc")
    @WebResult(name = "SignDocResp", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/", partName = "SignDocResponse")
    public SignDocResp signDoc(
        @WebParam(name = "SignDocReq", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/", partName = "SignDocRequest")
        SignDocReq signDocRequest);

    /**
     * 
     * @param closeSignSessionRequest
     * @return
     *     returns it.medas_solutions.scrybasignserver.CloseSignSessionResp
     */
    @WebMethod(operationName = "CloseSignSession", action = "http://demo.medas-solutions.it/ScrybaSignServer/SyncSign/CloseSignSession")
    @WebResult(name = "CloseSignSessionResp", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/", partName = "CloseSignSessionResponse")
    public CloseSignSessionResp closeSignSession(
        @WebParam(name = "CloseSignSessionReq", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/", partName = "CloseSignSessionRequest")
        CloseSignSessionReq closeSignSessionRequest);

    /**
     * 
     * @param despatchOtpRequest
     * @return
     *     returns it.medas_solutions.scrybasignserver.DespatchOtpResp
     */
    @WebMethod(operationName = "DespatchOtp", action = "http://demo.medas-solutions.it/ScrybaSignServer/SyncSign/DespatchOtp")
    @WebResult(name = "DespatchOtpResp", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/", partName = "DespatchOtpResponse")
    public DespatchOtpResp despatchOtp(
        @WebParam(name = "DespatchOtpReq", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/", partName = "DespatchOtpRequest")
        DespatchOtpReq despatchOtpRequest);

    /**
     * 
     * @param signOneDocRequest
     * @return
     *     returns it.medas_solutions.scrybasignserver.SignOneDocResp
     */
    @WebMethod(operationName = "SignOneDoc", action = "http://demo.medas-solutions.it/ScrybaSignServer/SyncSign/SignOneDoc")
    @WebResult(name = "SignOneDocResp", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/", partName = "SignOneDocResponse")
    public SignOneDocResp signOneDoc(
        @WebParam(name = "SignOneDocReq", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/", partName = "SignOneDocRequest")
        SignOneDocReq signOneDocRequest);

}
