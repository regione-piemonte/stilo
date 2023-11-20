/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.stilo.logic.exception.DatabaseException;
import it.eng.stilo.logic.jpa.SingleRootCriteriaQuery;
import it.eng.stilo.model.core.Storage;
import it.eng.stilo.model.core.Storage_;
import it.eng.stilo.util.file.FileHandler;
import it.eng.stilo.util.fsconfig.FileSystemStorageConfig;
import it.eng.stilo.util.xml.XMLHandler;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.xml.bind.JAXBException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Stateless
public class StorageEJB extends DataAccessEJB {

    private static final String RESOURCE_FILE = "/res.properties";
    private static final String NFS_REPO_PROPERTY = "nfs-repo";
    private static final String NFS_REPO_PLACEHOLDER = "$NFSREPOSITORY";
    private static final String LEFT_INDEX_OF = "[";
    private static final String RIGTH_INDEX_OF = "]";

    /**
     * Loads {@link Storage}.
     *
     * @return The List of all storages.
     * @throws DatabaseException if an exception occurs.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Storage> loadWithUsers() throws DatabaseException {
        logger.info(getClass().getSimpleName() + "-loadWithUsers");

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final SingleRootCriteriaQuery<Storage> container = helper.criteria(cb, Storage.class);
        container.getRoot().fetch(Storage_.userStorages, JoinType.LEFT);

        try {
            return this.executeFind(container, null).getEntries();
        } catch (final PersistenceException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * Get a bytes array from provided uri.
     *
     * @param uri The input uri.
     * @return The bytes array related to provided uri.
     * @throws JAXBException
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public byte[] find(final String uri) throws DatabaseException, JAXBException {
        logger.info(getClass().getSimpleName() + "-find[" + uri + "]");

        final String storageType = getStorageTypeFromUri(uri);
        final Storage storage = load(Storage.class, storageType);
        final XMLHandler<FileSystemStorageConfig> handler = new XMLHandler<>(FileSystemStorageConfig.class);
        final FileSystemStorageConfig element = handler.toElement(storage.getConfiguration());

        final Optional<String> nfsRepository = FileHandler.getInstance().getPropertyValue(NFS_REPO_PROPERTY,
                RESOURCE_FILE);

        logger.info(String.format("NFSRepo[%s]-RepoLocation[%s]-UriPath[%s]", nfsRepository.orElse(""),
                element.getRepositoryLocations(), getPathFromUri(uri)));

        final String completePath = element.getRepositoryLocations().replace(NFS_REPO_PLACEHOLDER,
                nfsRepository.orElse("")) + getPathFromUri(uri);
        logger.info("CompletePath[" + completePath + "]");

        return FileHandler.getInstance().getBytesFromFile(completePath);
    }

    public InputStream getStream(final String uri) throws DatabaseException, JAXBException {
        logger.info(getClass().getSimpleName() + "-find[" + uri + "]");

        final String storageType = getStorageTypeFromUri(uri);
        final Storage storage = load(Storage.class, storageType);
        final XMLHandler<FileSystemStorageConfig> handler = new XMLHandler<>(FileSystemStorageConfig.class);
        final FileSystemStorageConfig element = handler.toElement(storage.getConfiguration());

        final Optional<String> nfsRepository = FileHandler.getInstance().getPropertyValue(NFS_REPO_PROPERTY,
                RESOURCE_FILE);

        logger.info(String.format("NFSRepo[%s]-RepoLocation[%s]-UriPath[%s]", nfsRepository.orElse(""),
                element.getRepositoryLocations(), getPathFromUri(uri)));

        final String completePath = element.getRepositoryLocations().replace(NFS_REPO_PLACEHOLDER,
                nfsRepository.orElse("")) + getPathFromUri(uri);
        logger.info("CompletePath[" + completePath + "]");

        File file = new File( completePath );
        if( file.exists() ){
			try {
				return new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }  
        	
        return null;
    }
    
    // Expected uri format like [storagetype]/pathTo
    private String getStorageTypeFromUri(final String uri) {
        return uri.substring(uri.lastIndexOf(LEFT_INDEX_OF) + 1, uri.indexOf(RIGTH_INDEX_OF));
    }

    // Expected uri format like [storagetype]/pathTo
    private String getPathFromUri(final String uri) {
        return uri.substring(uri.lastIndexOf(RIGTH_INDEX_OF) + 1);
    }
}
