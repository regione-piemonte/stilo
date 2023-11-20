/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailCreacasellacomecopiaBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailGetparametricasellaBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailSetparametricasellaBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailTrovacaselleBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.caselleEmail.datasource.bean.AuthenticationBean;
import it.eng.auriga.ui.module.layout.server.caselleEmail.datasource.bean.AuthenticationResultBean;
import it.eng.auriga.ui.module.layout.server.caselleEmail.datasource.bean.CasellaEmailBean;
import it.eng.auriga.ui.module.layout.server.caselleEmail.datasource.bean.FiltriTrovaCaselle;
import it.eng.auriga.ui.module.layout.server.caselleEmail.datasource.bean.ParametroCasellaBean;
import it.eng.client.DmpkIntMgoEmailCreacasellacomecopia;
import it.eng.client.DmpkIntMgoEmailGetparametricasella;
import it.eng.client.DmpkIntMgoEmailSetparametricasella;
import it.eng.client.DmpkIntMgoEmailTrovacaselle;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.utility.cryptography.AES;
import it.eng.utility.storageutil.manager.dao.DaoUtilizzatoriStorage;
import it.eng.utility.storageutil.manager.entity.UtilizzatoriStorage;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import java.util.Properties;

@Datasource(id="CaselleEmailDatasource")
public class CaselleEmailDatasource extends AbstractFetchDataSource<CasellaEmailBean>{
	
	private static Logger mLogger = Logger.getLogger(CaselleEmailDatasource.class);

	
	@Override
	public PaginatorBean<CasellaEmailBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
 		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		List<CasellaEmailBean> data = new ArrayList<CasellaEmailBean>();   
        
		FiltriTrovaCaselle scFiltriTrovaCaselle = new FiltriTrovaCaselle();		
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){
				if("account".equals(crit.getFieldName())) {
					scFiltriTrovaCaselle.setAccount(getValueStringaFullTextMista(crit));						
					scFiltriTrovaCaselle.setOperAccount(getOperatorStringaFullTextMista(crit));						
				} else if("desOwner".equals(crit.getFieldName())) {
					scFiltriTrovaCaselle.setDesOwner(getValueStringaFullTextMista(crit));						
					scFiltriTrovaCaselle.setOperDesOwner(getOperatorStringaFullTextMista(crit));						
				} else if("tipoAccount".equals(crit.getFieldName())) {
					scFiltriTrovaCaselle.setTipoAccount(getTextFilterValue(crit));	
				} else if("giorniUltimoCambioPwd".equals(crit.getFieldName())) {
					String[] estremiGiorniUltimoCambioPwd = getNumberFilterValue(crit);
					scFiltriTrovaCaselle.setGiorniUltimoCambioPwdDa(estremiGiorniUltimoCambioPwd[0] != null ? estremiGiorniUltimoCambioPwd[0] : null);						
					scFiltriTrovaCaselle.setGiorniUltimoCambioPwdA(estremiGiorniUltimoCambioPwd[1] != null ? estremiGiorniUltimoCambioPwd[1] : null);						
				}		
			}
		}
		
        
        DmpkIntMgoEmailTrovacaselleBean input = new DmpkIntMgoEmailTrovacaselleBean();              
        input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	
		input.setFlgsenzapaginazionein(new Integer(1));
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
        input.setFiltriio(lXmlUtilitySerializer.bindXml(scFiltriTrovaCaselle));
		        
        DmpkIntMgoEmailTrovacaselle store = new DmpkIntMgoEmailTrovacaselle();
        StoreResultBean<DmpkIntMgoEmailTrovacaselleBean> output = store.execute(getLocale(), loginBean, input);
        
        if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
        
        if(StringUtils.isNotBlank(output.getResultBean().getResultout())) {
        	data = XmlListaUtility.recuperaLista(output.getResultBean().getResultout(), CasellaEmailBean.class);
        }
        
        if(isAttivaCifraturaPwd()) {
        	for(int i = 0; i < data.size(); i++) {
        		try {
        			data.get(i).setPassword(AES.decrypt(data.get(i).getPassword(), getChiaveCifraturaPwd()));    
        		} catch(Exception e) {}        		
        	}
        }
        
        PaginatorBean<CasellaEmailBean> lPaginatorBean = new PaginatorBean<CasellaEmailBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;
	}	
	
	@Override
	public CasellaEmailBean get(CasellaEmailBean bean) throws Exception {
					
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());			
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkIntMgoEmailGetparametricasellaBean input = new DmpkIntMgoEmailGetparametricasellaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	
		input.setIdcasellain(bean.getIdCasella());
		
		DmpkIntMgoEmailGetparametricasella store = new DmpkIntMgoEmailGetparametricasella();
        StoreResultBean<DmpkIntMgoEmailGetparametricasellaBean> output = store.execute(getLocale(), loginBean, input);
        
        if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
        
        List<ParametroCasellaBean> parametriCasella = new ArrayList<ParametroCasellaBean>();   
        if(StringUtils.isNotBlank(output.getResultBean().getXmlparametriout())) {
        	parametriCasella = XmlListaUtility.recuperaLista(output.getResultBean().getXmlparametriout(), ParametroCasellaBean.class);
        }
        
        if(isAttivaCifraturaPwd()) {
        	for(int i = 0; i < parametriCasella.size(); i++) {
        		if(parametriCasella.get(i).getNome().equalsIgnoreCase("mail.password")) {
        			try {
        				parametriCasella.get(i).setValore(AES.decrypt(parametriCasella.get(i).getValore(), getChiaveCifraturaPwd()));
        			} catch(Exception e) {} 
        		}
        	}
        }
        
        bean.setParametriCasella(parametriCasella);
        
		return bean;		
	}
	
	@Override
	public CasellaEmailBean update(CasellaEmailBean bean, CasellaEmailBean oldvalue) throws Exception {
					
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());			
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkIntMgoEmailSetparametricasellaBean input = new DmpkIntMgoEmailSetparametricasellaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	
		input.setIdcasellain(bean.getIdCasella());
		
		Lista listaParametriCasella = new Lista();
        if(bean.getParametriCasella() != null) {
        	  for(int i = 0; i < bean.getParametriCasella().size(); i++) {              		  
        		  if(isAttivaCifraturaPwd()) {
        			  if(bean.getParametriCasella().get(i).getNome().equalsIgnoreCase("mail.password")) {
        				  try {
        					  bean.getParametriCasella().get(i).setValore(AES.encrypt(bean.getParametriCasella().get(i).getValore(), getChiaveCifraturaPwd()));
        				  } catch(Exception e) {}
        			  }
        	      }        		  
        		  Riga riga = new Riga();
        		  Colonna col1 = new Colonna();
        		  col1.setNro(new BigInteger("1"));
        		  col1.setContent(bean.getParametriCasella().get(i).getNome());
        		  riga.getColonna().add(col1);
        		  Colonna col2 = new Colonna();
        		  col2.setNro(new BigInteger("2"));
        		  col2.setContent(bean.getParametriCasella().get(i).getValore());
        		  riga.getColonna().add(col2);
        		  listaParametriCasella.getRiga().add(riga);
        	  } 
        }
        
        StringWriter sw = new StringWriter();
		Marshaller marshaller = SingletonJAXBContext.getInstance().createMarshaller();
		marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		marshaller.marshal(listaParametriCasella, sw);
		input.setXmlparametriin(sw.toString());
         
		DmpkIntMgoEmailSetparametricasella store = new DmpkIntMgoEmailSetparametricasella();
        StoreResultBean<DmpkIntMgoEmailSetparametricasellaBean> output = store.execute(getLocale(), loginBean, input);
        
        if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
        
		return bean;		
	}
	
	public CasellaEmailBean creaCasellaComeCopia(CasellaEmailBean bean) throws Exception {
					
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());			
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkIntMgoEmailCreacasellacomecopiaBean input = new DmpkIntMgoEmailCreacasellacomecopiaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	
		
		input.setIdcaselladacopiarein(bean.getIdCasella());
		
		input.setIdenteaooin(StringUtils.isNotBlank(bean.getIdSpAoo()) ? new Integer(bean.getIdSpAoo()) : null);
//		input.setDesenteaooin(null);
//		input.setIdfruitoreenteaooin(null);

		input.setAccountcasellain(bean.getNuovoIndirizzoEmail());
		input.setFlgattivascaricoin((bean.getFlgAttivaScarico() != null && bean.getFlgAttivaScarico()) ? new Integer("1") : null);
		
		Lista listaParametriCasella = new Lista();
        if(bean.getParametriCasella() != null) {
        	  for(int i = 0; i < bean.getParametriCasella().size(); i++) {              		  
        		  if(isAttivaCifraturaPwd()) {
        			  if(bean.getParametriCasella().get(i).getNome().equalsIgnoreCase("mail.password")) {
        				  try {
        					  bean.getParametriCasella().get(i).setValore(AES.encrypt(bean.getParametriCasella().get(i).getValore(), getChiaveCifraturaPwd()));
        				  } catch(Exception e) {}
        			  }
        	      }        		  
        		  Riga riga = new Riga();
        		  Colonna col1 = new Colonna();
        		  col1.setNro(new BigInteger("1"));
        		  col1.setContent(bean.getParametriCasella().get(i).getNome());
        		  riga.getColonna().add(col1);
        		  Colonna col2 = new Colonna();
        		  col2.setNro(new BigInteger("2"));
        		  col2.setContent(bean.getParametriCasella().get(i).getValore());
        		  riga.getColonna().add(col2);
        		  listaParametriCasella.getRiga().add(riga);
        	  } 
        }
        
        StringWriter sw = new StringWriter();
		Marshaller marshaller = SingletonJAXBContext.getInstance().createMarshaller();
		marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		marshaller.marshal(listaParametriCasella, sw);
		input.setXmlparametriin(sw.toString());
				
		DmpkIntMgoEmailCreacasellacomecopia store = new DmpkIntMgoEmailCreacasellacomecopia();
        StoreResultBean<DmpkIntMgoEmailCreacasellacomecopiaBean> output = store.execute(getLocale(), loginBean, input);
        
        if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
        
        String idCasellaNew = output.getResultBean().getIdcasellanewout();
        
        try {
        	UtilizzatoriStorage utilizzatoreStorageMailArchiveCasella = DaoUtilizzatoriStorage.newInstance().get("MailArchive." + bean.getIdCasella());
            UtilizzatoriStorage utilizzatoriStorageMailArchiveCasellaNew = new UtilizzatoriStorage("MailArchive." + idCasellaNew, utilizzatoreStorageMailArchiveCasella.getStorages());
			DaoUtilizzatoriStorage.newInstance().save(utilizzatoriStorageMailArchiveCasellaNew);			
        } catch(Exception e) {
        	mLogger.warn(e);
        	addMessage("MailArchive." + idCasellaNew + " non è stato censito nella tabella degli utilizzatori dello storage", "", MessageType.WARNING);
        }
        
		try {
			UtilizzatoriStorage utilizzatoreStorageMailConnectCasella = DaoUtilizzatoriStorage.newInstance().get("MailConnect." + bean.getIdCasella());               		
			UtilizzatoriStorage utilizzatoreStorageMailConnectCasellaNew = new UtilizzatoriStorage("MailConnect." + idCasellaNew, utilizzatoreStorageMailConnectCasella.getStorages());
			DaoUtilizzatoriStorage.newInstance().save(utilizzatoreStorageMailConnectCasellaNew);
		} catch(Exception e) {
			mLogger.warn(e);
        	addMessage("MailConnect." + idCasellaNew + " non è stato censito nella tabella degli utilizzatori dello storage", "", MessageType.WARNING);
        }
        
		bean.setIdCasella(idCasellaNew);
        bean.setIndirizzoEmail(bean.getNuovoIndirizzoEmail());
       
		return bean;		
	}
	
	@Override
	public CasellaEmailBean add(CasellaEmailBean bean) throws Exception {
        		
		return null;	
	}
		
	@Override
	public CasellaEmailBean remove(CasellaEmailBean bean) throws Exception {
				
		return null;
	}	
	
	public boolean isAttivaCifraturaPwd() {
		return true;//ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_CIFRATURA_PWD");
	}
	
	public String getChiaveCifraturaPwd() {
		return ParametriDBUtil.getParametroDB(getSession(), "STRING#1");
	}
	
	public AuthenticationResultBean testConnectSmtp(AuthenticationBean bean) throws Exception {
		
		String host = bean.getHost();
		String port = bean.getPort();
		String username = bean.getUsername();
		String password = bean.getPassword();
		String auth = bean.getAuth();
		String starttls = bean.getStarttls();
		String sslEnable = bean.getSslEnable();
		String smtpSocketFactoryClass = bean.getSmtpSocketFactoryClass();
		String smtpSocketFactoryFallback= bean.getSmtpSocketFactoryFallback();
		
		AuthenticationResultBean result = new AuthenticationResultBean();
		
		try {
				// Controllo obbligatorieta' dei parametri 
				if (StringUtils.isBlank(host)){
					throw new Exception("Impossibile effettuare il test,manca il parametro HOST.");	
				}
				
				if (StringUtils.isBlank(port)){
					throw new Exception("Impossibile effettuare il test,manca il parametro PORT.");	
				}
				
				if (StringUtils.isBlank(username)){
					throw new Exception("Impossibile effettuare il test,manca il parametro USERNAME.");	
				}
				
				// Controllo la validita' dei valori dei parametri
				if (StringUtils.isNotBlank(auth)){
					if (!auth.equalsIgnoreCase("true") && !auth.equalsIgnoreCase("false")){
						throw new Exception("Impossibile effettuare il test,il valore del parametro mail.smtp.auth non e' corretto. Deve essere true o false.");
					}
				}
				
				if (StringUtils.isNotBlank(starttls)){
					if (!starttls.equalsIgnoreCase("true") && !starttls.equalsIgnoreCase("false")){
						throw new Exception("Impossibile effettuare il test,il valore del parametro parametro mail.smtp.starttls.enable non e' corretto. Deve essere true o false.");	
					}
				}
				
				if (StringUtils.isNotBlank(sslEnable)){
					if (!sslEnable.equalsIgnoreCase("true") && !sslEnable.equalsIgnoreCase("false")){
						throw new Exception("Impossibile effettuare il test,il valore del parametro parametro mail.smtp.ssl.enable non e' corretto. Deve essere true o false.");	
					}
				}
				
				if (StringUtils.isNotBlank(smtpSocketFactoryFallback)){
					if (!smtpSocketFactoryFallback.equalsIgnoreCase("true") && !smtpSocketFactoryFallback.equalsIgnoreCase("false")){
						throw new Exception("Impossibile effettuare il test,il valore del parametro parametro mail.smtp.socketFactory.fallback non e' corretto. Deve essere true o false.");	
					}
				}
	    	    	
				// Setto i parametri
	    		String pTransport = "smtp";
		        Properties props = new Properties();
		        
		        if (StringUtils.isNotBlank(auth)){
			        props.setProperty("mail.smtp.auth", auth); 
		        }
		        if (StringUtils.isNotBlank(starttls)){
			        props.setProperty("mail.smtp.startssl.enable", starttls); 
			    }		        
		        if (StringUtils.isNotBlank(sslEnable)){
			        props.setProperty("mail.smtp.ssl.enable", sslEnable); 
		        }
		        if (StringUtils.isNotBlank(smtpSocketFactoryFallback)){
		        	props.setProperty("mail.smtp.socketFactory.fallback", smtpSocketFactoryFallback); 
		        }
		        if (StringUtils.isNotBlank(smtpSocketFactoryClass)){
			        props.setProperty("mail.smtp.socketFactory.class", smtpSocketFactoryClass); 
		        }		        
		        if (StringUtils.isNotBlank(sslEnable)){
		        	if (sslEnable.equals("true")) {
			            pTransport = "smtps";
			        } else { 
			            pTransport = "smtp";
			        }
		        }
		        
		        props.setProperty("mail.smtps.ssl.protocols", "TLSv1.2");
		        
		        Session session = Session.getInstance(props, null);
		        Transport transport = session.getTransport(pTransport);
		        int portint = Integer.parseInt(port);
		        transport.connect(host, portint, username, password);
		        transport.close();
		        result.setAuthenticationOK(true);

	    } catch(AuthenticationFailedException e) {
	    	result.setAuthenticationOK(false);
	    	result.setMsgError(e.getMessage());
	    } catch(MessagingException e) {
	    	result.setAuthenticationOK(false);
	    	result.setMsgError(e.getMessage());
	    } catch (Exception e) {
	    	result.setAuthenticationOK(false);
	    	result.setMsgError(e.getMessage());
	    }
	    return result;
	}
	
	public AuthenticationResultBean testConnectInBox(AuthenticationBean bean) throws Exception {
		
		String host = bean.getHost();
		String port = bean.getPort();
		String username = bean.getUsername();
		String password = bean.getPassword();

		AuthenticationResultBean result = new AuthenticationResultBean();
		try {			
				if (StringUtils.isBlank(host)){
					throw new Exception("Impossibile effettuare il test, manca il parametro HOST.");	
				}		
				if (StringUtils.isBlank(port)){
					throw new Exception("Impossibile effettuare il test, manca il parametro PORT.");	
				}		
				if (StringUtils.isBlank(username)){
					throw new Exception("Impossibile effettuare il test, manca il parametro USERNAME.");	
				}
					    			
			 	URLName url = new URLName("imaps", host, 993, "INBOX", username, password);			 
		    	Store store;
				Folder folder;		    	
		        Properties props = new Properties();
		        props.setProperty("mail.imaps.ssl.protocols", "TLSv1.2");
		        
		        Session session = Session.getInstance(props, null);
		        store = session.getStore(url);
		        store.connect();
				folder = store.getFolder(url);
				folder.open(Folder.READ_ONLY);
				
				int messageCountInBox = 0;
				messageCountInBox = folder.getMessageCount();
		        result.setAuthenticationOK(true);
		        result.setMessageCountInBox(messageCountInBox);
	    } catch(AuthenticationFailedException e) {
	    	result.setAuthenticationOK(false);
	    	result.setMsgError(e.getMessage());
	    } catch(MessagingException e) {
	    	result.setAuthenticationOK(false);
	    	result.setMsgError(e.getMessage());
	    } catch (Exception e) {
	    	result.setAuthenticationOK(false);
	    	result.setMsgError(e.getMessage());
	    }
	    return result;	    
	}
}