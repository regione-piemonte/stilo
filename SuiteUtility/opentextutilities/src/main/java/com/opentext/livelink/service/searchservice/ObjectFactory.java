
package com.opentext.livelink.service.searchservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.opentext.livelink.service.searchservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _OTAuthentication_QNAME = new QName("urn:api.ecm.opentext.com", "OTAuthentication");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.opentext.livelink.service.searchservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetResultPageDescriptionResponse }
     * 
     */
    public GetResultPageDescriptionResponse createGetResultPageDescriptionResponse() {
        return new GetResultPageDescriptionResponse();
    }

    /**
     * Create an instance of {@link GetSupportedQueryLanguages }
     * 
     */
    public GetSupportedQueryLanguages createGetSupportedQueryLanguages() {
        return new GetSupportedQueryLanguages();
    }

    /**
     * Create an instance of {@link GetSupportedQueryLanguagesResponse }
     * 
     */
    public GetSupportedQueryLanguagesResponse createGetSupportedQueryLanguagesResponse() {
        return new GetSupportedQueryLanguagesResponse();
    }

    /**
     * Create an instance of {@link Search }
     * 
     */
    public Search createSearch() {
        return new Search();
    }

    /**
     * Create an instance of {@link SingleSearchRequest }
     * 
     */
    public SingleSearchRequest createSingleSearchRequest() {
        return new SingleSearchRequest();
    }

    /**
     * Create an instance of {@link GetDataCollections }
     * 
     */
    public GetDataCollections createGetDataCollections() {
        return new GetDataCollections();
    }

    /**
     * Create an instance of {@link GetFieldInfoResponse }
     * 
     */
    public GetFieldInfoResponse createGetFieldInfoResponse() {
        return new GetFieldInfoResponse();
    }

    /**
     * Create an instance of {@link FieldInfo }
     * 
     */
    public FieldInfo createFieldInfo() {
        return new FieldInfo();
    }

    /**
     * Create an instance of {@link GetQueryLanguageDescription }
     * 
     */
    public GetQueryLanguageDescription createGetQueryLanguageDescription() {
        return new GetQueryLanguageDescription();
    }

    /**
     * Create an instance of {@link GetQueryLanguageDescriptionResponse }
     * 
     */
    public GetQueryLanguageDescriptionResponse createGetQueryLanguageDescriptionResponse() {
        return new GetQueryLanguageDescriptionResponse();
    }

    /**
     * Create an instance of {@link GetResultPageDescription }
     * 
     */
    public GetResultPageDescription createGetResultPageDescription() {
        return new GetResultPageDescription();
    }

    /**
     * Create an instance of {@link GetDataCollectionsResponse }
     * 
     */
    public GetDataCollectionsResponse createGetDataCollectionsResponse() {
        return new GetDataCollectionsResponse();
    }

    /**
     * Create an instance of {@link GetFieldInfo }
     * 
     */
    public GetFieldInfo createGetFieldInfo() {
        return new GetFieldInfo();
    }

    /**
     * Create an instance of {@link SearchResponse }
     * 
     */
    public SearchResponse createSearchResponse() {
        return new SearchResponse();
    }

    /**
     * Create an instance of {@link SingleSearchResponse }
     * 
     */
    public SingleSearchResponse createSingleSearchResponse() {
        return new SingleSearchResponse();
    }

    /**
     * Create an instance of {@link SEdge }
     * 
     */
    public SEdge createSEdge() {
        return new SEdge();
    }

    /**
     * Create an instance of {@link SNode }
     * 
     */
    public SNode createSNode() {
        return new SNode();
    }

    /**
     * Create an instance of {@link DataBagType }
     * 
     */
    public DataBagType createDataBagType() {
        return new DataBagType();
    }

    /**
     * Create an instance of {@link NV }
     * 
     */
    public NV createNV() {
        return new NV();
    }

    /**
     * Create an instance of {@link SGraph }
     * 
     */
    public SGraph createSGraph() {
        return new SGraph();
    }

    /**
     * Create an instance of {@link ListDescription }
     * 
     */
    public ListDescription createListDescription() {
        return new ListDescription();
    }

    /**
     * Create an instance of {@link SResultPage }
     * 
     */
    public SResultPage createSResultPage() {
        return new SResultPage();
    }

    /**
     * Create an instance of {@link OTAuthentication }
     * 
     */
    public OTAuthentication createOTAuthentication() {
        return new OTAuthentication();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OTAuthentication }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:api.ecm.opentext.com", name = "OTAuthentication")
    public JAXBElement<OTAuthentication> createOTAuthentication(OTAuthentication value) {
        return new JAXBElement<OTAuthentication>(_OTAuthentication_QNAME, OTAuthentication.class, null, value);
    }

}
