package it.eng.auriga.opentext.aspect;

import java.net.MalformedURLException;
import java.util.Date;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import it.eng.auriga.opentext.exception.AuthenticationException;
import it.eng.auriga.opentext.exception.ContentServerException;
import it.eng.auriga.opentext.service.cs.AuthenticationCSService;
import it.eng.auriga.opentext.service.cs.bean.OTAuthenticationInfo;

@Configuration
public class ContentServerTokenRefresh {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ContentServerTokenRefresh.class);

	protected void refreshToken() {
	}

	@Autowired
	private AuthenticationCSService authCSService;

	public Object profile(String username, String token, Date tokenDate) throws Throwable {
		// recupero lo username per andare su db e leggere il csToken staccato da
		// inserire in tutti i metodi che vanno a Content
		return refreshCsToken(username, token, tokenDate);
	}

	/**
	 * metodo che effettua il refresh del token se necessario. La sessione utente
	 * per ora dura un giorno
	 * 
	 * @param username
	 * @return
	 * @throws ContentServerException
	 * @throws MalformedURLException
	 * @throws AuthenticationException
	 */
	private String refreshCsToken(String username, String token, Date tokenDate)
			throws MalformedURLException, AuthenticationException, ContentServerException {
		String newToken = null;
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> START");
		logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> username " + username);
		logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> " + "ContentServer Token Utente: "
				+ token);
		if (tokenDate.before(new Date())) {
			logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> "
					+ "Sessione utente content server terminata: " + username + " Ritento la connessione");

			OTAuthenticationInfo authInfo;
			if ((authInfo = authCSService.executeImpersonation(username, null, null)) != null) {
				logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> "
						+ "Connessione content server riattivata per utente " + username);
				newToken = authInfo.getToken();

				logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + " >> "
						+ "Salvate sul database informazioni aggiornate per utente: " + username);
			}
		}
		return token;
	}

}