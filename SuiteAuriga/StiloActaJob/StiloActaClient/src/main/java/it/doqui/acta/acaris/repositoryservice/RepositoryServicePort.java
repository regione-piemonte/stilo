/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.repositoryservice;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import it.doqui.acta.acaris.archive.AcarisRepositoryEntryType;
import it.doqui.acta.acaris.archive.AcarisRepositoryInfoType;
import it.doqui.acta.acaris.common.ObjectIdType;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebService(name = "RepositoryServicePort", targetNamespace = "repositoryservice.acaris.acta.doqui.it")
@XmlSeeAlso({
    it.doqui.acta.acaris.archive.ObjectFactory.class,
    it.doqui.acta.acaris.common.ObjectFactory.class
})
public interface RepositoryServicePort {


    /**
     * 
     * @return
     *     returns java.util.List<it.doqui.acta.acaris.archive.AcarisRepositoryEntryType>
     * @throws AcarisException
     */
    @WebMethod
    @WebResult(name = "repository", targetNamespace = "archive.acaris.acta.doqui.it")
    @RequestWrapper(localName = "getRepositories", targetNamespace = "archive.acaris.acta.doqui.it", className = "it.doqui.acta.acaris.archive.GetRepositories")
    @ResponseWrapper(localName = "getRepositoriesResponse", targetNamespace = "archive.acaris.acta.doqui.it", className = "it.doqui.acta.acaris.archive.GetRepositoriesResponse")
    public List<AcarisRepositoryEntryType> getRepositories()
        throws AcarisException
    ;

    /**
     * 
     * @param repositoryId
     * @return
     *     returns it.doqui.acta.acaris.archive.AcarisRepositoryInfoType
     * @throws AcarisException
     */
    @WebMethod
    @WebResult(name = "repositoryInfo", targetNamespace = "archive.acaris.acta.doqui.it")
    @RequestWrapper(localName = "getRepositoryInfo", targetNamespace = "archive.acaris.acta.doqui.it", className = "it.doqui.acta.acaris.archive.GetRepositoryInfo")
    @ResponseWrapper(localName = "getRepositoryInfoResponse", targetNamespace = "archive.acaris.acta.doqui.it", className = "it.doqui.acta.acaris.archive.GetRepositoryInfoResponse")
    public AcarisRepositoryInfoType getRepositoryInfo(
        @WebParam(name = "repositoryId", targetNamespace = "archive.acaris.acta.doqui.it")
        ObjectIdType repositoryId)
        throws AcarisException
    ;

}
