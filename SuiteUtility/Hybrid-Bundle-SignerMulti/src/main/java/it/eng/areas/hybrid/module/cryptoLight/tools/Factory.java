package it.eng.areas.hybrid.module.cryptoLight.tools;

import static sun.security.pkcs11.wrapper.PKCS11Constants.CKF_OS_LOCKING_OK;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.swing.JProgressBar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.cryptoLight.detectors.CadesDetector;
import it.eng.areas.hybrid.module.cryptoLight.detectors.EnvelopeDetector;
import it.eng.areas.hybrid.module.cryptoLight.detectors.P7MDetector;
import it.eng.areas.hybrid.module.cryptoLight.detectors.PadesDetetector;
import it.eng.areas.hybrid.module.cryptoLight.exception.SmartCardException;
import it.eng.areas.hybrid.module.cryptoLight.signers.AbstractSigner;
import it.eng.areas.hybrid.module.cryptoLight.util.MessageKeys;
import it.eng.areas.hybrid.module.cryptoLight.util.Messages;
import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceKeys;
import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceManager;
import it.eng.common.type.SignerType;
import sun.security.pkcs11.SunPKCS11;
import sun.security.pkcs11.wrapper.CK_C_INITIALIZE_ARGS;
import sun.security.pkcs11.wrapper.CK_TOKEN_INFO;
import sun.security.pkcs11.wrapper.PKCS11;
import sun.security.pkcs11.wrapper.PKCS11Constants;
import sun.security.pkcs11.wrapper.PKCS11Exception;

public class Factory {

	public final static Logger logger = Logger.getLogger(Factory.class);

	// classi che consentono di rilevare i vari tipi di firme
	private static Class[] clazzs = { P7MDetector.class, CadesDetector.class, PadesDetetector.class };

	// ultimo provider con login successo
	public static File lastDll = null;

	private static PKCS11 lastPkcs11 = null;
	
	public static PKCS11 getLastPkcs11() {
		return lastPkcs11;
	}
	
	private static void setLastPkcs11(PKCS11 pcks11) {
		finalizePkcs(getLastPkcs11());
		lastPkcs11 = pcks11;
	}
	
	public static SunPKCS11 loginProvider(File dllFile, int slot, char[] pin) throws Throwable {
		logger.info("Tento il login con la dll " + dllFile);
		SunPKCS11 provider = null;
		if (dllFile.isFile()) {
			String os = System.getProperty("os.name");
			String osname = "windows";
			if (os.toLowerCase().startsWith("window")) {
				osname = "windows";
			} else {
				osname = "linux";
			}
			if (osname.equals("windows") || (osname.equals("linux") && dllFile.getAbsolutePath().toLowerCase().endsWith("so"))) {
				StringBuffer cardConfig = new StringBuffer();
				cardConfig.append("name=SmartCards \n");
				PKCS11 pkcs11 = null;
				finalizePkcs(getLastPkcs11());
				try {
					pkcs11 = inizialize(dllFile.getAbsolutePath());
					long[] slots = getSlotsWithTokens(pkcs11);
					if (slots != null) {
						for (int i = 0; i < slots.length; i++) {
							logger.info("Slot " + slots[i]);
						}
						if (slots.length > 0) {
							slot = new Long(slots[0]).intValue();
						}
					}
				} catch (Exception e) {
					logger.error("Errore in init o getSlot - Provider non caricato ", e);
					finalizePkcs(pkcs11);
					throw e;
				}
				cardConfig.append("slot=" + slot + " \n");
				cardConfig.append("library=" + dllFile.getAbsolutePath() + " \n");
				cardConfig.append("disabledMechanisms={ CKM_SHA1_RSA_PKCS }");
				logger.info("---> cardConfig " + cardConfig);
				try {
					ByteArrayInputStream confStream = new ByteArrayInputStream(cardConfig.toString().getBytes());
					provider = new sun.security.pkcs11.SunPKCS11(confStream);
					if (provider != null) {
						logger.info("File Provider:" + provider.getInfo().toLowerCase().trim());
						//Tento una login per vedere se ho caricato la dll corretta
						provider.login(new Subject(), new PasswordHandler(pin));
						Security.removeProvider(provider.getName());
						Security.addProvider(provider);
						logger.info(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_PROVIDERLOADED));
						setLastPkcs11(pkcs11);
					}
				} catch (Exception e) {
					finalizePkcs(pkcs11);
					logger.error("Provider non caricato", e);
					throw e;
				} catch (Throwable e) {
					finalizePkcs(pkcs11);
					logger.error("Provider non caricato", e);
					throw e;
				}
			}
		}
		return provider;
	}

	public static List<String> getPathProviders() throws SmartCardException {
		logger.info("Inizio controllo provider disponibili");
		List<String> pathProviders = new ArrayList<String>();
		String os = System.getProperty("os.name");
		String osname = "windows";
		if (os.toLowerCase().startsWith("window")) {
			osname = "windows";
		} else {
			osname = "linux";
		}
		String[] names = new String[0];
		if (osname.equals("windows")) {
			names = PreferenceManager.getStringArray(PreferenceKeys.SMART_PROVIDER_WIND);
			if (names.length == 1 && StringUtils.isEmpty(names[0])) {
				names = new String[0];
			}
			for (String lStrDllName : names) {
				String dll64 = "C:\\windows\\SysWOW64\\" + lStrDllName;
				File dll64File = new File(dll64);
				if (dll64File.exists()) {
					pathProviders.add(dll64);
				} else {
					logger.info("Il provider " + dll64 + " non esiste.");
				}
			}
			for (String lStrDllName : names) {
				String dll32 = "C:\\windows\\System32\\" + lStrDllName;
				File dll32File = new File(dll32);
				if (dll32File.exists()) {
					pathProviders.add(dll32);
				} else {
					logger.info("Il provider " + dll32 + " non esiste.");
				}
			}
			for (String lStrDllName : names) {
				String dll = AbstractSigner.dir + File.separator + AbstractSigner.cryptodll + File.separator + lStrDllName;
				File dllFile = new File(dll);
				if (dllFile.exists()) {
					pathProviders.add(dll);
				} else {
					logger.info("Il provider " + dll + " non esiste.");
				}
			}
		} else {
			names = PreferenceManager.getStringArray(PreferenceKeys.SMART_PROVIDER_LINUX);
			String dllDirectory = AbstractSigner.dir + File.separator + AbstractSigner.cryptodll + File.separator;
			for (String name : names) {
				File dllFile = new File(dllDirectory + name);
				if (dllFile.exists()) {
					pathProviders.add(dllDirectory + name);
				} else {
					logger.info("Il provider " + dllDirectory + name + " non esiste.");
				}
			}
		}
		logger.info("Providers " + pathProviders);
		return pathProviders;
	}

	public void copyProvider(JProgressBar bar) throws Exception {
		logger.info("INIZIO COPIA PROVIDER!");
		String os = System.getProperty("os.name");
		String osname = "windows";
		if (os.toLowerCase().startsWith("window")) {
			osname = "windows";
		} else {
			osname = "linux";
		}
		String[] names = new String[0];
		if (osname.equals("windows")) {
			names = PreferenceManager.getStringArray(PreferenceKeys.SMART_PROVIDER_WIND);
			if (names.length == 1 && StringUtils.isEmpty(names[0])) {
				names = new String[0];	
			}
		} else {
			names = PreferenceManager.getStringArray(PreferenceKeys.SMART_PROVIDER_LINUX);
		}
		bar.setMinimum(0);
		bar.setMaximum(names.length);
		File directory = new File(AbstractSigner.dir + File.separator + AbstractSigner.cryptodll);
		// directory.delete();
		if (!directory.exists()) {
			directory.mkdirs();
		}
		for (int i = 0; i < names.length; i++) {
			// logger.info("File:"+names[i]);
			// File file = new File(directory.getAbsolutePath()+File.separator+names[i]);
			// bar.setValue(i+1);
			// bar.setString("Caricamento provider "+names[i]+" ("+(i+1)+"/"+names.length+")");
			// if(!file.exists()){
			// FileOutputStream out = new FileOutputStream(directory.getAbsolutePath()+File.separator+names[i]);
			// IOUtils.copy(this.getClass().getResourceAsStream("/it/eng/client/provider/"+osname+"/"+names[i]), out);
			// out.flush();
			// out.close();
			// }
		}
		// Controllo la directory di configurazione dei provider
		File directoryConfig = new File(AbstractSigner.dir + File.separator + AbstractSigner.cryptoConfig);
		File configfile = new File(directoryConfig + File.separator + "crypto.config");
		if (!directoryConfig.exists()) {
			directoryConfig.mkdirs();
		}
		if (configfile.exists()) {
			configfile.createNewFile();
		}
		logger.info("FINE COPIA PROVIDER!");
	}

	/**
	 * verifica se il file passato e' firmato, ritorna il tipo di busta riconoscito se rileva la firma null altrimenti
	 * 
	 * @param file
	 * @return
	 * @throws CryptoSignerException
	 */
	public static SignerType isSigned(File file) {
		SignerType ret = null;
		// provo tutti gli envelop detector
		for (int i = 0; i < clazzs.length; i++) {
			try {
				Object signer = clazzs[i].newInstance();
				if (signer instanceof EnvelopeDetector) {
					ret = ((EnvelopeDetector) signer).isSignedType(file);
					if (ret != null) {
						break;// prendo il primo che mi rileva il formato
					}
				}
			} catch (InstantiationException e) {
				logger.error(e);
			} catch (IllegalAccessException e) {
				logger.error(e);			}
		}
		return ret;
	}

	public static long[] getSlotsWithTokens(String libraryPath) throws Exception {
		CK_C_INITIALIZE_ARGS initArgs = new CK_C_INITIALIZE_ARGS();
		String functionList = "C_GetFunctionList";
		initArgs.flags = CKF_OS_LOCKING_OK;
		PKCS11 tmpPKCS11 = null;
		long[] slotList = null;
		try {
			try {
				tmpPKCS11 = PKCS11.getInstance(libraryPath, functionList, initArgs, false);
			} catch (Exception ex) {
				logger.error(ex);
				throw ex;
			}
		} catch (PKCS11Exception e) {
			try {
				initArgs = null;
				tmpPKCS11 = PKCS11.getInstance(libraryPath, functionList, initArgs, true);
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
		try {
			slotList = tmpPKCS11.C_GetSlotList(true);
			for (long slot : slotList) {
				CK_TOKEN_INFO tokenInfo = tmpPKCS11.C_GetTokenInfo(slot);
				logger.info("slot: " + slot + "\nmanufacturerID: " + String.valueOf(tokenInfo.manufacturerID) + "\nmodel: " + String.valueOf(tokenInfo.model));
			}
		} catch (PKCS11Exception ex) {
			logger.error(ex);
		} catch (Throwable t) {
			logger.error(t);
		}
		return slotList;
	}
	
	public static PKCS11 inizialize(String libraryPath) throws Exception {
		Field moduleMapField = PKCS11.class.getDeclaredField("moduleMap");  
		moduleMapField.setAccessible(true);  
		Map<?, ?> moduleMap = (Map<?, ?>) moduleMapField.get(null);  
		moduleMap.clear();
		CK_C_INITIALIZE_ARGS initArgs = new CK_C_INITIALIZE_ARGS();
		String functionList = "C_GetFunctionList";
		initArgs.flags = CKF_OS_LOCKING_OK;
		PKCS11 tmpPKCS11 = null;
		try {
			try {
				tmpPKCS11  = PKCS11.getInstance(libraryPath, functionList, initArgs, false);
			} catch (Exception ex) {
				logger.error(ex);
				throw ex;
			}
		} catch (PKCS11Exception e) {
			try {
				initArgs = null;
				tmpPKCS11 = PKCS11.getInstance(libraryPath, functionList, initArgs, true);
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
		return tmpPKCS11;
	}
	
	public static long[] getSlotsWithTokens(PKCS11 tmpPKCS11) throws Exception {
		long[] slotList = null;
		try {
			slotList = tmpPKCS11.C_GetSlotList(true);
			logger.info("slotList " + slotList);
			logger.info("slotList size " + slotList.length);
			for (long slot : slotList) {
				CK_TOKEN_INFO tokenInfo = tmpPKCS11.C_GetTokenInfo(slot);
				logger.info("slot: " + slot + "\nmanufacturerID: " + String.valueOf(tokenInfo.manufacturerID) + "\nmodel: " + String.valueOf(tokenInfo.model));
			}
		} catch (PKCS11Exception ex) {
			logger.error(ex);

		} catch (Throwable t) {
			logger.error(t);
		}
		return slotList;
	}
	
	public static void finalizePkcs(PKCS11 tmpPKCS11)  {
		if (tmpPKCS11 != null) {
			logger.info("finalize su tmpPKCS11");
			try {
				tmpPKCS11.C_Finalize(PKCS11Constants.NULL_PTR);
			} catch (PKCS11Exception e) {
				logger.error("Errore nel finalize di tmpPKCS11: " + e.getMessage());
			}
			tmpPKCS11 = null;
		}
		if (Factory.lastPkcs11 != null) {
			logger.info("finalize su lastPkcs11");
			try {
				Factory.lastPkcs11.C_Finalize(PKCS11Constants.NULL_PTR);
			} catch (PKCS11Exception e) {
				logger.error("Errore nel finalize di lastPkcs11: " + e.getMessage());
			}
			Factory.lastPkcs11 = null;
		}
	}
	
}
