package it.eng.utility.cryptosigner.manager.factory;

import it.eng.utility.cryptosigner.CryptoConfiguration;
import it.eng.utility.cryptosigner.context.CryptoSignerApplicationContextProvider;
import it.eng.utility.cryptosigner.controller.ISignerController;
import it.eng.utility.cryptosigner.controller.MasterSignerController;
import it.eng.utility.cryptosigner.controller.MasterTimeStampController;
import it.eng.utility.cryptosigner.controller.bean.TimeStampValidityBean;
import it.eng.utility.cryptosigner.controller.impl.signature.CertificateAssociation;
import it.eng.utility.cryptosigner.controller.impl.signature.CertificateExpiration;
import it.eng.utility.cryptosigner.controller.impl.signature.CertificateReliability;
import it.eng.utility.cryptosigner.controller.impl.signature.CertificateRevocation;
import it.eng.utility.cryptosigner.controller.impl.signature.ContentExtraction;
import it.eng.utility.cryptosigner.controller.impl.signature.FormatValidity;
import it.eng.utility.cryptosigner.controller.impl.signature.SignatureAssociation;
import it.eng.utility.cryptosigner.controller.impl.signature.SignatureExtraction;
import it.eng.utility.cryptosigner.controller.impl.timestamp.TSAReliability;
import it.eng.utility.cryptosigner.controller.impl.timestamp.TSARevocation;
import it.eng.utility.cryptosigner.controller.impl.timestamp.TimeStampExtraction;
import it.eng.utility.cryptosigner.controller.impl.timestamp.TimeStampValidator;
import it.eng.utility.cryptosigner.data.AbstractSigner;
import it.eng.utility.cryptosigner.data.DataSigner;
import it.eng.utility.cryptosigner.manager.SignatureManager;
import it.eng.utility.cryptosigner.storage.ICAStorage;
import it.eng.utility.cryptosigner.storage.ICRLStorage;
import it.eng.utility.cryptosigner.storage.IConfigStorage;
import it.eng.utility.cryptosigner.storage.impl.filesystem.FileSystemCAStorage;
import it.eng.utility.cryptosigner.storage.impl.filesystem.FileSystemCRLStorage;
import it.eng.utility.cryptosigner.storage.impl.filesystem.FileSystemConfigStorage;

import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Properties;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class SignatureManagerFactory {

	private ICAStorage caStorage;
	private ICRLStorage crlStorage;
	private IConfigStorage configStorage;
	
	private CryptoConfiguration configuration;	
	
	private SignatureManagerFactory() {
	}

	public static SignatureManagerFactory newInstance(ICAStorage caStorage, ICRLStorage crlStorage, IConfigStorage configStorage) {
		SignatureManagerFactory instance = new SignatureManagerFactory();
		instance.setCaStorage(caStorage);
		instance.setCrlStorage(crlStorage);
		instance.setConfigStorage(configStorage);
		return instance;
	}
	
	public static SignatureManagerFactory newInstance(String caStoragePath, String crlStoragePath, String configStoragePath) {
		SignatureManagerFactory instance  = newInstance(new File(caStoragePath), new File(crlStoragePath), new File(configStoragePath)); 
		return instance;
	}
	
	public static SignatureManagerFactory newInstance(String caStoragePath, String crlStoragePath, String configStoragePath,CryptoConfiguration configuration) {
		SignatureManagerFactory instance  = newInstance(new File(caStoragePath), new File(crlStoragePath), new File(configStoragePath)); 
		instance.configuration = configuration;
		return instance;
	}
	
	public static SignatureManagerFactory newInstance(File caStorageFolder, File crlStorageFolder, File configStorageFolder,CryptoConfiguration configuration) {
		SignatureManagerFactory instance  = newInstance(caStorageFolder, crlStorageFolder,configStorageFolder); 
		instance.configuration = configuration;
		return instance;
	}
	
	
	public static SignatureManagerFactory newInstance(File caStorageFolder, File crlStorageFolder, File configStorageFolder) {
		
		SignatureManagerFactory instance = new SignatureManagerFactory();
		
		FileSystemCAStorage localCaStorage = new FileSystemCAStorage();
		FileSystemCRLStorage localCRLStorage = new FileSystemCRLStorage();
		FileSystemConfigStorage localConfigStorage = new FileSystemConfigStorage();
		
		localCaStorage.setDirectory(caStorageFolder.getAbsolutePath());
		localCRLStorage.setDirectory(crlStorageFolder.getAbsolutePath());
		localConfigStorage.setDirectory(configStorageFolder.getAbsolutePath());
		
		instance.setCaStorage(localCaStorage);
		instance.setCrlStorage(localCRLStorage);
		instance.setConfigStorage(localConfigStorage);
		
		return instance;
	}
	
	public SignatureManager newSignatureManager() {
		
		setupApplicationContext();
		
		// MasterTimeStamp
		MasterTimeStampController masterTimeStamp = new MasterTimeStampController();

		// MasterTimeStamp Controllers
		masterTimeStamp.setControllers(new ArrayList<ISignerController>() {
			private static final long serialVersionUID = 1L;
			{
				this.add(new TimeStampExtraction() {
					{
						this.setCritical(true);
					}
				});

				this.add(new TSAReliability() {
					{
						this.setCritical(true);
					}
				});

				this.add(new TSARevocation() {
					{
						this.setCritical(true);
					}
				});

			}
		});

		// MasterTimeStamp checks
		masterTimeStamp.setChecks(new HashMap<String, Boolean>() {
			private static final long serialVersionUID = 1L;

			{
				this.put("performTSAReliability", true);
				this.put("performTSARevocation", true);
			}
		});

		// MasterTimeStamp timeStampValidity
		masterTimeStamp
				.setTimeStampValidity(new ArrayList<TimeStampValidityBean>() {
					private static final long serialVersionUID = 1L;
					{
						this.add(new TimeStampValidityBean() {
							{
								this.setEnd(new GregorianCalendar(2009, 12, 3)
										.getTime());
								this.setYears(100);
							}
						});

						this.add(new TimeStampValidityBean() {
							{
								this.setEnd(new GregorianCalendar(2009, 12, 3)
										.getTime());
								this.setYears(5);
							}
						});

						this.add(new TimeStampValidityBean() {
							{
								this.setBegin(new GregorianCalendar(2009, 12, 4)
										.getTime());
								this.setYears(20);
							}
						});
					}
				});

		// MasterTimeStamp timeStampValidator
		masterTimeStamp.setTimeStampValidator(new TimeStampValidator());

		// MasterSigner
		MasterSignerController masterSigner = new MasterSignerController();
		masterSigner.setControllers(new ArrayList<ISignerController>() {

			private static final long serialVersionUID = 1L;

			{
				this.add(new FormatValidity() {
					{
						this.setCritical(false);
						this.setValidityProperties(new Properties());
					}
				});

				this.add(new ContentExtraction() {
					{
						this.setCritical(false);
					}
				});

				this.add(new SignatureExtraction() {
					{
						this.setCritical(false);
						this.setPerformCounterSignaturesCheck(true);
					}
				});

				this.add(new SignatureAssociation() {
					{
						this.setCritical(false);
						this.setPerformCounterSignaturesCheck(true);
					}
				});

				this.add(new CertificateReliability() {
					{
						this.setCritical(false);
						this.setPerformCounterSignaturesCheck(false);
					}
				});

				this.add(new CertificateExpiration() {
					{
						this.setCritical(false);
						this.setPerformCounterSignaturesCheck(true);
					}
				});

				this.add(new CertificateAssociation() {
					{
						this.setCritical(false);
						this.setPerformCounterSignaturesCheck(true);
					}
				});

				this.add(new CertificateRevocation() {
					{
						this.setCritical(false);
						this.setPerformCounterSignaturesCheck(true);
					}
				});
			}
		});
		
		masterSigner.setChecks(new HashMap<String, Boolean>() {

			private static final long serialVersionUID = 1L;

			{
				this.put("performFormatValidity", true);
				this.put("performCertificateReliability", true);
				this.put("performCertificateExpiration", true);
				this.put("performCertificateAssociation", true);
				this.put("performCertificateRevocation", true);
			}
		});
				
		// SignatureManager
		SignatureManager signatureManager = new SignatureManager();
		signatureManager.setMasterTimeStampController(masterTimeStamp);
		signatureManager.setMasterSignerController(masterSigner);
		
		return signatureManager;
	}
	
	private void setupApplicationContext() {
	
		// Il contesto Spring non e stato trovato!
		DataSigner dataSigner = new DataSigner() {
			{
				this.setSignersManager(new ArrayList<AbstractSigner>() {

					private static final long serialVersionUID = 1L;

					{
						this.add(new it.eng.utility.cryptosigner.data.M7MSigner());
						this.add(new it.eng.utility.cryptosigner.data.TsrSigner());
						this.add(new it.eng.utility.cryptosigner.data.CAdESSigner());
						this.add(new it.eng.utility.cryptosigner.data.P7MSigner());
						this.add(new it.eng.utility.cryptosigner.data.XMLSigner());
						this.add(new it.eng.utility.cryptosigner.data.PdfSigner());
					}
				});
			}
		};
		
		/*
		 * FIXME: certificateAuthorityUpdate e dataSigner devono NON essere singleton..
		 */
		it.eng.utility.cryptosigner.ca.impl.TSLCertificateAuthority certificateAuthorityUpdate = new it.eng.utility.cryptosigner.ca.impl.TSLCertificateAuthority();
		
		DefaultListableBeanFactory parentBeanFactory = new DefaultListableBeanFactory();
		parentBeanFactory.registerSingleton("CAStorage", caStorage);
		parentBeanFactory.registerSingleton("CRLStorage", crlStorage);
		parentBeanFactory.registerSingleton("ConfigStorage", configStorage);
		parentBeanFactory.registerSingleton("DataSigner", dataSigner);
		parentBeanFactory.registerSingleton("CertificateAuthorityUpdate", certificateAuthorityUpdate);
		parentBeanFactory.registerSingleton("CryptoConfiguration", configuration);

		
		
		ApplicationContext newApplicationContext = new GenericApplicationContext(parentBeanFactory);
		CryptoSignerApplicationContextProvider provider = new CryptoSignerApplicationContextProvider();
		provider.setApplicationContext(newApplicationContext);
		
	}

	/**
	 * @return the caStorage
	 */
	public ICAStorage getCaStorage() {
		return caStorage;
	}

	/**
	 * @param caStorage the caStorage to set
	 */
	public void setCaStorage(ICAStorage caStorage) {
		this.caStorage = caStorage;
	}

	/**
	 * @return the crlStorage
	 */
	public ICRLStorage getCrlStorage() {
		return crlStorage;
	}

	/**
	 * @param crlStorage the crlStorage to set
	 */
	public void setCrlStorage(ICRLStorage crlStorage) {
		this.crlStorage = crlStorage;
	}

	/**
	 * @return the configStorage
	 */
	public IConfigStorage getConfigStorage() {
		return configStorage;
	}

	/**
	 * @param configStorage the configStorage to set
	 */
	public void setConfigStorage(IConfigStorage configStorage) {
		this.configStorage = configStorage;
	}
	
}
