package it.eng.utility.client.acta.object.repository;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.doqui.acta.acaris.repository.AcarisRepositoryEntryType;
import it.doqui.acta.acaris.repository.RepositoryService;
import it.doqui.acta.acaris.repository.RepositoryServicePort;
import it.eng.utility.client.acta.WSActa;
import it.eng.utility.client.acta.object.WsActaObject;


public class WSActaRepository extends WSActa {
	
	protected String endpointRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(WsActaObject.class);
	
	public String getRepository( String repoName ){
		try {
			final URL url = new URL( getEndpointRepository() );
			final RepositoryService repositoryService = new RepositoryService(url);
            RepositoryServicePort repositoryServicePort = repositoryService.getRepositoryServicePort();
            ((BindingProvider) repositoryServicePort).getBinding();
            setTimeout(repositoryServicePort);
            List<AcarisRepositoryEntryType> repositories = repositoryServicePort.getRepositories();
            String repoId = null;
            if( repositories!=null ){
            	logger.debug("Num totale di rep trovati: "+ repositories.size());
            	logger.debug("Filtro per repositoryName " + repoName );
            	for(AcarisRepositoryEntryType repo : repositories){
            		//logger.debug("Repo name " + repo.getRepositoryName() );
            		//logger.debug( "Repo id: " + repo.getRepositoryId().getValue());
            		//logger.debug("\n");
            		if( repoName.equalsIgnoreCase(repo.getRepositoryName()))
            			repoId = repo.getRepositoryId().getValue();
            	}
            }
            //logger.debug("\nRepositoryId " + repoId );
            return repoId;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (it.doqui.acta.acaris.repository.AcarisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getEndpointRepository() {
		return endpointRepository;
	}
	public void setEndpointRepository(String endpointRepository) {
		this.endpointRepository = endpointRepository;
	}
	
}
