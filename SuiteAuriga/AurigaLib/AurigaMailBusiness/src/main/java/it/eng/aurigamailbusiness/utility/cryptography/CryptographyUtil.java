/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// 18.12.2015 Federico Cacco
// Modificata classe di crittografia in modo da poterne
// parametrizzare il secrets e l'abilitazione
// La classe è stata collocata in questo package in modo da poterla utilizzare sia da
// MailServer che da AurigaMailBusiness

package it.eng.aurigamailbusiness.utility.cryptography;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.helper.StringUtil;

import it.eng.aurigamailbusiness.database.dao.DaoTParameters;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.aurigamailbusiness.database.utility.TParametersConfigKey;
import it.eng.aurigamailbusiness.sender.AccountConfigKey;

public class CryptographyUtil {

	private static Logger log = LogManager.getLogger(CryptographyUtil.class);

	/**
	 * Verifica se la crittografia delle password degli account mail è abilitata
	 * 
	 * @return true se la cifratura è abilitata
	 * @throws Exception
	 */
	public static boolean isEnablePasswordAccountEncryption() {
		String cifraturaAbilitata = "false";
		try {
			DaoTParameters daoParametri = (DaoTParameters) DaoFactory.getDao(DaoTParameters.class);
			cifraturaAbilitata = daoParametri.get(TParametersConfigKey.ATTIVA_CIFRATURA_PWD.keyname()).getStrValue();
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug("Errore nella lettura del parametro di abilitazione cifratura, verrà forzato a false", e);
			}
			cifraturaAbilitata = "false";
		}
		if (log.isDebugEnabled()) {
			log.debug("Parametro abilitazione cifratura impostato a: " + cifraturaAbilitata);
		}
		return (!StringUtil.isBlank(cifraturaAbilitata)) && ("true".equalsIgnoreCase(cifraturaAbilitata));
	}

	public static String decryptionAccountPasswordWithAES(Properties accountConfig) throws InvalidEncryptionKeyException {
		// Se gestita la crittografia password esegui decrittografia
		String accountPasswordOutput = accountConfig.getProperty(AccountConfigKey.ACCOUNT_PASSWORD.keyname());
		if (isEnablePasswordAccountEncryption()) {
			if (log.isDebugEnabled()) {
				log.debug("Avvio la decriptazione");
			}
			accountPasswordOutput = AES.decrypt(accountPasswordOutput);
		}
		return accountPasswordOutput;
	}

	public static String encryptionAccountPasswordWithAES(Properties accountConfig) throws InvalidEncryptionKeyException {
		// Se gestita la crittografia password esegui crittografia
		String accountPasswordOutput = accountConfig.getProperty(AccountConfigKey.ACCOUNT_PASSWORD.keyname());
		if (isEnablePasswordAccountEncryption()) {
			if (log.isDebugEnabled()) {
				log.debug("Avvio la criptazione");
			}
			accountPasswordOutput = AES.encrypt(accountPasswordOutput);
		}
		return accountPasswordOutput;
	}
}
