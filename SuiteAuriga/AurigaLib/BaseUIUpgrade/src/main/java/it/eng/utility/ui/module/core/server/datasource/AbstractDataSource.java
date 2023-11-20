/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.document.function.bean.Firmatario;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.exception.SessionCloseException;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.FileUtil;
import it.eng.utility.FilterUtil;
import it.eng.utility.InfoGenericFile;
import it.eng.utility.MessageUtil;
import it.eng.utility.export.ExportManager;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.util.StorageUtil;
import it.eng.utility.ui.config.FilterConfigurator;
import it.eng.utility.ui.config.FilterSpecConfigurator;
import it.eng.utility.ui.config.FilterToHideConfigurator;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterFieldSpecBean;
import it.eng.utility.ui.module.layout.shared.bean.FilterSpecBean;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.module.layout.shared.bean.ItemFilterBean;
import it.eng.utility.ui.servlet.bean.Firmatari;
import it.eng.utility.ui.servlet.bean.InfoFirmaMarca;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.UserUtil;

public abstract class AbstractDataSource<T,E> {
	
	private static Logger mLogger = Logger.getLogger(AbstractDataSource.class);
	
	@Deprecated
	/**
	 * Utilizzare Locale getLocale()
	 */
	protected Locale locale = UserUtil.getLocale();
	
	public static final String  FMT_STD_DATA = "dd/MM/yyyy";
	public static final String  FMT_STD_TIMESTAMP = "dd/MM/yyyy HH:mm";
	public static final String  FMT_STD_TIMESTAMP_WITH_SEC = "dd/MM/yyyy HH:mm:ss";
	public static final String  DATETIME_ATTR_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String  DATE_ATTR_FORMAT = "yyyy-MM-dd";

	
	public static final String KEY_FIELD_NAME = "keyproperty";
	
	private List<MessageBean> messages = null;
	
	private HttpSession session;
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;

	private Map<String,String> extraparams;
	
	private String uuid;
	
	public abstract E get(T bean) throws Exception;	
	
	public abstract E add(T bean) throws Exception;
	
	public abstract E remove(T bean) throws Exception;
	
	public abstract E update(T bean,T oldvalue) throws Exception;
	
	public ComparatorResultBean compareOldAndNew(ComparatorBean<T> bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		DefaultComparator<T> lDefaultComparator = new DefaultComparator<T>();
		return lDefaultComparator.compare(bean);
	}
	
	public abstract PaginatorBean<E> fetch(AdvancedCriteria criteria,Integer startRow,Integer endRow,List<OrderByBean> orderby) throws Exception;
	
	public PaginatorBean<E> stoppableFetch(AdvancedCriteria criteria,Integer startRow,Integer endRow,List<OrderByBean> orderby) throws Exception {
		try {
			return fetch(criteria, startRow, endRow, orderby);
		} catch (Exception e) {
			if (StringUtils.isNotEmpty(e.getMessage()) && e.getMessage().equals("Chiusura forzata_Chiusura forzata")){
				throw new SessionCloseException(e.getMessage(),e);
			} else throw e;
		}
	}
	
	public abstract Map<String,ErrorBean> validate(T bean) throws Exception;		
		
	/**
	 * Lancia la generazione del documento contenente i record visualizzati nella lista, presenti all'interno del bean
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public ExportBean export(ExportBean bean) throws Exception {			
		ExportManager manager = new ExportManager();		
		File file = File.createTempFile("export", "");		
		if(manager.export(file, bean)){
			if(bean.isSaveTempFileInStorage()) {
				StorageService storageService = StorageImplementation.getStorage();		
				bean.setTempFileOut(storageService.store(file));
			} else {
				bean.setTempFileOut(file.getAbsolutePath());
			}
		}		
		return bean;		
	}		

	public List<MessageBean> getMessages() {
		return messages;
	}
	
	public void setMessages(List<MessageBean> messages) {
		this.messages = messages;
	}

	public void addMessage(String message,String detail,MessageType type){
		
		if(messages==null){
			messages = new ArrayList<MessageBean>();
		}
		messages.add(new MessageBean(message, detail, type));
	}
	
	public void setExtraparams(Map<String, String> extraparams) {
		this.extraparams = extraparams;
	}

	public Map<String, String> getExtraparams() {
		if (extraparams == null) {
			return new LinkedHashMap<String, String>();
		}
		return extraparams;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * Retituisce la classe del bean generics
	 * @return
	 */
	public Class getBeanClassIn(){
		Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType)type;
                return (Class<T>) paramType.getActualTypeArguments()[0];
        }
        return null;
	}
	
	/**
	 * Retituisce la classe del bean generics
	 * @return
	 */
	public Class getBeanClassOut(){
		Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType)type;
                return (Class<T>) paramType.getActualTypeArguments()[0];
        }
        return null;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Locale getLocale(){
		if (getSession() != null){
			return UserUtil.getLocale(getSession());
		}
		else{
			return new Locale("it", "IT");
		}
			
	}
	
	// Federico Cacco 14-10-2015
	/**
	 * Traduce l'xml contenente i filtri, da usare se il filtro già tradotto salvato in sessione non è recuperabile.
	 * Il filtro tradotto viene sia restituito che messo in sessione
	 */
	protected FilterConfigurator getXmlFiltriTradotto() throws Exception {
		String nomeFiltroTradotto = FilterUtil.getNomeFiltroTradotto(session);
		// Federico Cacco 29.03.2016
		// Restutuisco, tramite FilterUtil, il filtro tradotto. Tale filtro può trovarsi in sessione o a livello applicativo,
		// a seconda dell'implementazione presente nel MessageUtil utilizzato
		FilterConfigurator lFilterConfigurator = FilterUtil.getFilterConfiguratorTradotto(nomeFiltroTradotto, getSession());
		if (lFilterConfigurator == null){
			//IMPORTANTE: bisogna recuperare l'applicationName direttamente dalle configurazioni, solo se qui non è settato provo a recuperarmelo dalla sessione
			String applicationName = ((GenericConfigBean)SpringAppContext.getContext().getBean("GenericPropertyConfigurator")).getApplicationName();
			if(StringUtils.isBlank(applicationName)) {
				applicationName = session != null ? (String) session.getAttribute("APPLICATION_NAME") : null;				
			}
			lFilterConfigurator = (FilterConfigurator) SpringAppContext.getContext().getBean("FilterConfigurator");
			if(applicationName != null && applicationName.equalsIgnoreCase("EIIFact")) {
				FilterConfigurator lEIIFactFilterConfigurator = (FilterConfigurator) SpringAppContext.getContext().getBean("EIIFactFilterConfigurator");	
				lFilterConfigurator.getListe().putAll(lEIIFactFilterConfigurator.getListe());
			} else if(applicationName != null && applicationName.equalsIgnoreCase("SinaDoc")) {
				FilterConfigurator lSinaDocFilterConfigurator = (FilterConfigurator) SpringAppContext.getContext().getBean("SinaDocFilterConfigurator");		
				lFilterConfigurator.getListe().putAll(lSinaDocFilterConfigurator.getListe());
			}
			//TODO una volta sostituiti tutti i filterToHide.xml con i filterSpec.xml bisogna togliere il bean FilterToHideConfigurator
			FilterSpecConfigurator lFilterSpecConfigurator = null;
			try {
				lFilterSpecConfigurator = (FilterSpecConfigurator) SpringAppContext.getContext().getBean("FilterSpecConfigurator");
			} catch(Exception e) {
				// nessun filtro specializzato configurato
			}
			FilterToHideConfigurator lFilterToHideConfigurator = null;
			try {
				lFilterToHideConfigurator = (FilterToHideConfigurator) SpringAppContext.getContext().getBean("FilterToHideConfigurator");
			} catch(Exception e) {
				// nessun filtro da nascondere configurato
			}
			String userLanguage = getLocale().getLanguage();
			if(lFilterConfigurator.getListe() != null) {
				for(String lista : lFilterConfigurator.getListe().keySet()) {
					HashSet<String> setFieldsToHide = null;
					HashMap<String, String> setFieldsSpec = null;
					if(lFilterSpecConfigurator != null) {
						setFieldsToHide = new HashSet<String>();
						setFieldsSpec = new HashMap<String, String>();
						FilterSpecBean lFilterSpecBean = lFilterSpecConfigurator.getListe() != null ? lFilterSpecConfigurator.getListe().get(lista) : null;
						if(lFilterSpecBean != null) {
							for(FilterFieldSpecBean lFilterFieldSpecBean : lFilterSpecBean.getFields()) {
								if(lFilterFieldSpecBean.isToHide()) {
									setFieldsToHide.add(lFilterFieldSpecBean.getName());
								} else {
									setFieldsSpec.put(lFilterFieldSpecBean.getName(), lFilterFieldSpecBean.getTitle());
								}
							}
						}
					} else if(lFilterToHideConfigurator != null) {
						List<String> listaFieldsToHide = lFilterToHideConfigurator.getFieldsToHide() != null ? lFilterToHideConfigurator.getFieldsToHide().get(lista) : null;
						if(listaFieldsToHide != null) {
							setFieldsToHide = new HashSet<String>(listaFieldsToHide);
						}
					}
					List<FilterFieldBean> lListFilterFieldBean = new ArrayList<FilterFieldBean>();					
					if(lFilterConfigurator.getListe().get(lista) != null && lFilterConfigurator.getListe().get(lista).getFields() != null) {
						for(FilterFieldBean field : lFilterConfigurator.getListe().get(lista).getFields()) {
							boolean isFieldToHide = setFieldsToHide != null && setFieldsToHide.contains(field.getName());
							boolean isFieldSpec = setFieldsSpec != null && setFieldsSpec.keySet().contains(field.getName());
							// Se il filtro è uno di quelli da nascondere, oppure è uno di quelli da mostrare solo se specializzato e non lo è, allora non lo devo aggiungere alla lista
							boolean isFieldToSkip = isFieldToHide || (field.isToShowIfSpec() && !isFieldSpec);
							if(!isFieldToSkip) {
								String fieldSpecTitle = setFieldsSpec != null && setFieldsSpec.keySet().contains(field.getName()) ? setFieldsSpec.get(field.getName()) : null;
								String fieldTitle = fieldSpecTitle != null && !"".equals(fieldSpecTitle) ? fieldSpecTitle : field.getTitle();
								if(StringUtils.isNotBlank(fieldTitle)) {
									field.setTitle(MessageUtil.getValue(userLanguage, getSession(), fieldTitle));
								}
								String defaultDisplayNameFieldTitle = MessageUtil.getValue(userLanguage, getSession(), field.getDefaultDisplayNameField());
								if(StringUtils.isNotBlank(defaultDisplayNameFieldTitle)) {
									field.setDefaultDisplayNameField(defaultDisplayNameFieldTitle);
								}
								if(field.getSelect() != null) {
									if(field.getSelect().getLayout() != null) {
										if(field.getSelect().getLayout().getFields() != null) {
											for(ItemFilterBean selectField : field.getSelect().getLayout().getFields()) {
												String selectFieldTitle = MessageUtil.getValue(userLanguage, getSession(), selectField.getTitle());
												if(StringUtils.isNotBlank(selectFieldTitle)) {
													selectField.setTitle(selectFieldTitle);										
												}								
											}	
										}
									}
									if(field.getSelect().getValueMap() != null) {
										Map<String, String> valueMap = new LinkedHashMap<String, String>();
										for(String key : field.getSelect().getValueMap().keySet()) {								
											String value = MessageUtil.getValue(userLanguage, getSession(), field.getSelect().getValueMap().get(key));
											if(StringUtils.isNotBlank(value)) {
												valueMap.put(key, value);										
											} else {
												valueMap.put(key, field.getSelect().getValueMap().get(key));
											}									
										}
										field.getSelect().setValueMap(valueMap);
									}
								}
								lListFilterFieldBean.add(field);
							}							
						}
						lFilterConfigurator.getListe().get(lista).setFields(lListFilterFieldBean);						
					}
				}
			}
			// Verifico se è  presente l'utente in sessione
			// Non voglio salvare il filtro tradotto se non è presente un utente in sessione (non conosco la reale lingua dell'utente,
			// e il filtro è stato tradotto con una lingua preimpostata)
			it.eng.utility.ui.module.layout.shared.bean.LoginBean sessionLoginBean = (it.eng.utility.ui.module.layout.shared.bean.LoginBean) getSession().getAttribute("LOGIN_INFO");
			if (sessionLoginBean != null){
				// Ho un utente in sessione, salvo il filtro
				// Federico Cacco 29.03.2016
				// Salvo, tramite MessageUtil, il filtro tradotto. Tale filtro può trovarsi in sessione o a livello applicativo,
				// a seconda dell'implementazione presente nel MessageUtil utilizzato
				FilterUtil.saveFilterConfiguratorTradotto(nomeFiltroTradotto, lFilterConfigurator, getSession());
			} else {
				FilterUtil.removeFilterConfiguratorTradotto(nomeFiltroTradotto, getSession());
			}
		}
		return lFilterConfigurator;
	}
	
	public void setProprietaGenericFile(GenericFile lGenericFile, MimeTypeFirmaBean lMimeTypeFirmaBean) throws ParseException {
		
		InfoGenericFile.setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);

	}
	
	public void addFileZip(MimeTypeFirmaBean infoFile, String uriFile, String attachmentFileName, File currentAttachmentTemp, File zipFile)
			throws Exception, StorageException, IOException, FileNotFoundException {
		
		File currentAttachment;
		try {
			String pathWithoutTempName= currentAttachmentTemp.getPath().replaceAll(FilenameUtils.getName(currentAttachmentTemp.getPath()), attachmentFileName);
			
			currentAttachment = new File(pathWithoutTempName);
			
			currentAttachmentTemp.renameTo(currentAttachment);
		} catch (Exception e) {
			mLogger.error("Errore durante la rinomina del file da inserire nello zip: " + e.getMessage(), e);
			currentAttachment = currentAttachmentTemp;
		}

		if (!StringUtils.isBlank(uriFile)) {
			FileUtil.writeStreamToFile(StorageImplementation.getStorage().extract(uriFile), currentAttachment);
			StorageUtil.addFileToZip(currentAttachment, zipFile.getAbsolutePath());

		} else if (infoFile != null && infoFile.isFirmato()) {
			String displayFilename = attachmentFileName;

			if (attachmentFileName.toLowerCase().endsWith(".p7m") || attachmentFileName.toLowerCase().endsWith(".tsd")) {
				displayFilename = attachmentFileName.substring(0, attachmentFileName.length() - 4);
			}
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			InputStream sbustatoStream = lInfoFileUtility.sbusta(currentAttachment, attachmentFileName);

			File currentAttachmentSbustato = File.createTempFile("temp", displayFilename);
			FileUtil.writeStreamToFile(sbustatoStream, currentAttachmentSbustato);

			StorageUtil.addFileToZip(currentAttachmentSbustato, zipFile.getAbsolutePath());

			FileDeleteStrategy.FORCE.delete(currentAttachmentSbustato);
		}
		FileDeleteStrategy.FORCE.delete(currentAttachment);
	}
	
}
