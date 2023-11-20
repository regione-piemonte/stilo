/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.ws.handler.MessageContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginBean;
import it.eng.auriga.database.store.dmpk_login.store.impl.LoginImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.configuration.DefaultConfigBean;
import it.eng.spring.utility.SpringAppContext;
import it.eng.storeutil.AnalyzeResult;

/**
 * @author Antonio Peluso
 */

public class AurigaWSAuthentication {
	
	static Logger log = Logger.getLogger(AurigaWSAuthentication.class);

	@SuppressWarnings("rawtypes")
	public static boolean authenticate(MessageContext mctx) throws Exception {

	    try {
			//Prendo username e password dagli headers http
			Map httpHeaders = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
			String username = null;
			String password = null;

			List userList = (List) httpHeaders.get("Username");
			List passList = (List) httpHeaders.get("Password");

			// provo prima a trovare username e password negli headers http
			if ((userList!=null && userList.size()>0) && (passList!=null && passList.size()>0)) {
			    username = userList.get(0).toString();
			    password = passList.get(0).toString();
			}

			// Non è stato trovato l'username negli headers, provo con l'autenticazione base (Es. Authorization : Basic 76jhbfs7ff3b45= (decodificato: Basic username:password))
			if (username == null) {
			    List auth = (List) httpHeaders.get("Authorization");
			    if (auth!=null && auth.size()>0) {
			        String[] authArray = basicAuthentication(auth.get(0).toString());
			        if (authArray != null) {
			            username = authArray[0];
			            password = authArray[1];
			        }
			    }
			}

			if (username != null && password != null) {
			    	checkLogIn(username, password);			    				    				   
			}else {
				throw new Exception("Username e\\o Passowrd non presenti");
			}
		} catch (Exception e) {
			log.error("Errore durante l'autenticazione: " + e.getMessage(), e);
			throw new Exception("Errore durante l'autenticazione: " + e.getMessage());
		}
	    
	    return true;
	}


	private static void checkLogIn(String username, String password) throws Exception {

		Session session = null;
		
		try {
			DefaultConfigBean defaultConfigBean = (DefaultConfigBean) SpringAppContext.getContext()
					.getBean("defaultConfigBean");
			
			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(defaultConfigBean.getSchema());
			SubjectUtil.subject.set(subject);

			session = HibernateUtil.begin();

			DmpkLoginLoginBean lLoginInput = new DmpkLoginLoginBean();
			lLoginInput.setUsernamein(username);
			lLoginInput.setPasswordin(password);
			lLoginInput.setFlgrollbckfullin(null);
			lLoginInput.setFlgautocommitin(1);

			final LoginImpl lLogin = new LoginImpl();
			lLogin.setBean(lLoginInput);
			session.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lLogin.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkLoginLoginBean> result = new StoreResultBean<DmpkLoginLoginBean>();
			AnalyzeResult.analyze(lLoginInput, result);
			result.setResultBean(lLoginInput);
			
//			session.flush();

			if (result.isInError()) {
				throw new Exception(result.getDefaultMessage());
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			HibernateUtil.release(session);
		}
	}


	/**
	 * ritorna username and password per la basic authentication
	 * 
	 * @param authorizeString
	 * @return
	 */
	public static String[] basicAuthentication(String authorizeString) {

	    if (authorizeString != null) {
	        StringTokenizer st = new StringTokenizer(authorizeString);
	        if (st.hasMoreTokens()) {
	            String basic = st.nextToken();
	            if (basic.equalsIgnoreCase("Basic")) {
	                String credentials = st.nextToken();
	                String userPass = new String(
	                        Base64.decodeBase64(credentials.getBytes()));
	                String[] userPassArray = userPass.split(":");
	                if (userPassArray != null && userPassArray.length == 2) {
	                    String username = userPassArray[0];
	                    String password = userPassArray[1];
	                    return new String[] { username, password };
	                }
	            }
	        }
	    }

	    return null;
	}
	
}
