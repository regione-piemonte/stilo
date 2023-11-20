/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.xml.ws.BindingProvider;

import it.doqui.acta.acaris.archive.AcarisRepositoryEntryType;
import it.doqui.acta.acaris.archive.AcarisRepositoryInfoType;
//import it.doqui.acta.acaris.common.ObjectIdType;
//import it.doqui.acta.acaris.repositoryservice.RepositoryService;
//import it.doqui.acta.acaris.repositoryservice.RepositoryServicePort;
import it.doqui.acta.acaris.common.ObjectIdType;
import it.doqui.acta.acaris.repositoryservice.RepositoryService;
import it.doqui.acta.acaris.repositoryservice.RepositoryServicePort;

@Dependent
public class RepositoryServiceClient extends AbstractServiceClient {

    private static final String SERVICE_NAME = "service.repository";
    private RepositoryServicePort repositoryServicePort;

    public RepositoryServiceClient() {
        super(SERVICE_NAME);
    }

    @Override
    protected void initService() {
        printInfo(SERVICE_NAME, "init");
        final URL url = getServiceURL(SERVICE_NAME);
        if (repositoryServicePort == null) {
            try {
                final RepositoryService repositoryService = new RepositoryService(url);
                repositoryServicePort = repositoryService.getRepositoryServicePort();
                setHandler(((BindingProvider) repositoryServicePort).getBinding());
                printInfo(SERVICE_NAME, "initialized");
            } catch (Exception e) {
                printError(SERVICE_NAME, "init", e);
                throw e;
            }
        }
    }

    public List<AcarisRepositoryEntryType> getRepositories(final Map<String, Object> paramMap) throws Exception {
        printInfo(SERVICE_NAME, "get-repositories");
        List<AcarisRepositoryEntryType> repositories = Collections.emptyList();
        try {
            initService();
            setServiceIdentifier((BindingProvider) repositoryServicePort, paramMap);
            repositories = repositoryServicePort.getRepositories();
        } catch (Exception e) {
            printError(SERVICE_NAME, "get-repositories", e.getMessage());
            throw e;
        }
        return repositories;
    }

    public AcarisRepositoryInfoType getRepositoryInfo(final ObjectIdType repositoryId,
                                                      final Map<String, Object> paramMap) throws Exception{
        printInfo(SERVICE_NAME, "get-repositoryInfo");
        AcarisRepositoryInfoType repositoryInfo = null;
        try {
            initService();
            setServiceIdentifier((BindingProvider) repositoryServicePort, paramMap);
            repositoryInfo = repositoryServicePort.getRepositoryInfo(repositoryId);
        } catch (Exception e) {
            printError(SERVICE_NAME, "get-repositoryInfo", e.getMessage());
            throw e;
        }
        return repositoryInfo;
    }

}
