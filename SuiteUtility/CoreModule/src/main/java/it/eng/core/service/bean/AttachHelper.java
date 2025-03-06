package it.eng.core.service.bean;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.core.annotation.Attachment;
import it.eng.core.business.KeyGenerator;
import it.eng.core.config.ClassUtil;
import it.eng.core.service.bean.AttachDescription.FileIdAssociation;
import it.eng.core.service.bean.BeanParamsAttachDescription.BeanParamDescription;
import it.eng.core.service.bean.EmptyListParamDescription.EmptyListPropertyDescription;
import it.eng.core.service.client.config.Configuration;
import it.eng.core.service.serialization.IServiceSerialize;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.IAttachStorage;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;

/**
 * helper per gestire gli attach TODO move to Serializer si può spostare nei serializer
 * 
 * @author Russo
 *
 */
public class AttachHelper {

	private static final Logger log = Logger.getLogger(AttachHelper.class);
	
	/**
	 * Serializzazione dell'oggetto come attach description
	 * 
	 * @param parpos
	 * @param obj
	 * @return
	 */
	public static AttachDescription serializeAsAttach(int parpos, Serializable obj, IServiceSerialize serializerUtil) throws Exception {		
		try {
			if (obj instanceof java.io.File) {
				log.debug("il parametro " + parpos + " è un file");
				// aggiungi il file come attach
				URL fileUrl = ((java.io.File) obj).toURI().toURL();

				// trasferisco un oggetto fileattachDescription fittizio
				// altrimenti il lnumero di parametri del metodo da invocare non è lo stesso
				// DefaultMediaTypePredictor mtp =DefaultMediaTypePredictor.getInstance();
				FileAttachDescription fad = new FileAttachDescription(parpos, fileUrl.toExternalForm());
				fad.addFile((java.io.File) obj);
				return fad;
			} // Se abbiamo a che fare con un Set di files
			else if (isCollectionOfFile(obj)) {
				log.debug("il parametro " + parpos + " è una collection di file");
				Collection<File> lFiles = (Collection<File>) obj;
				// try {
				//
				// lFiles = (Collection<File>)obj;
				// } catch (Exception e){
				// log.debug("verifica se il paramentero è un Collection File false ",e);
				// }
				// Se sono riuscito a convertire
				if (lFiles != null) {
					int count = 0;
					ArrayList<Integer> lListParPosition = new ArrayList<Integer>();
					ArrayList<String> lListContent = new ArrayList<String>();
					CollectionFileAttachDescription lFileListAttachDescription = new CollectionFileAttachDescription();
					lFileListAttachDescription.setMarker(CollectionFileAttachDescription.collMarker);
					lFileListAttachDescription.setCollectionClassName(obj.getClass().getName());
					for (File lFile : lFiles) {
						// URL fileUrl= ((java.io.File) lFile).toURI().toURL();
						lFileListAttachDescription.addFile((java.io.File) lFile);
						count++;
					}
					lFileListAttachDescription.setContentDisposition(lListContent);
					lFileListAttachDescription.setListSize(count);

					return lFileListAttachDescription;
				}
			}
			// verifica se dobbiamo inviare allegati per un bean con parametri di tipo file
			else {
				BeanWrapperImpl wrapperObj = BeanPropertyUtility.getBeanWrapper(obj);
				return getAttachProp(parpos, obj, serializerUtil, wrapperObj);
			}
		} catch (Exception ex) {
			log.fatal("exception serializing attach " + ex, ex);
			throw new AttachSerializationException(ex);
		}
		log.debug("il par " + parpos + " non è un attach " + obj);
		return null;
	}
	
	private static boolean isCollectionOfFile(Serializable obj) {
		boolean ret = false;
		if (obj instanceof Collection<?>) {
			ret = true;
			for (Object object : (Collection) obj) {
				if (!(object instanceof File)) {
					ret = false;
					break;
				}
			}
		}
		return ret;
	}

	//TODO Gestire il funzionamento per altri oggetti quali stream etc.
	private static BeanParamsAttachDescription getAttachProp(int parpos, Object outputobject, IServiceSerialize serializerUtil, BeanWrapperImpl wrapperOutputobject) throws Exception {
		if (outputobject == null) {
			return null;
		}
		IAttachStorage attachStorage = null;
		try {
			attachStorage = (IAttachStorage)SpringAppContext.getContext().getBean("AttachStorage");			
        } catch (Exception e) {
        	attachStorage = null;
        }	
		BeanParamsAttachDescription bat = new BeanParamsAttachDescription();
		// imposto la string ache mi consente di capire in json che è un bean attach
		bat.setMarker(BeanParamsAttachDescription.beanParamMarker);
		bat.setParPosition(parpos);
		bat.setSerializedData(serializerUtil.serialize(outputobject));
		log.debug("finding prop with annotation Attach");
		Field[] fields = ClassUtil.getAnnotatedDeclaredFields(outputobject.getClass(), Attachment.class, true);
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			// il campo deve avere anche l'annotation @XmlTransient
			if (field.getType().equals(File.class)) {
				log.debug("get value for name " + field.getName());
				File file = (File) BeanPropertyUtility.getPropertyValue(outputobject, wrapperOutputobject, field.getName());
				// File file = (File) BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(outputobject, field.getName());				
				if (file != null) {
					BeanParamDescription pard = new BeanParamDescription();
					pard.setPropName(field.getName());
					pard.setPropType(field.getType().getName());
					String uri = null;
					if (Boolean.TRUE.equals(Configuration.getInstance().getSendAttachmentsByUri())) {						
						try {
							if(attachStorage != null) {
								uri = attachStorage.storeTempFile(file);
							} else {
								uri = file.getAbsolutePath();
							}
						} catch(Exception e) {}
					}
					if (StringUtils.isNotBlank(uri)) {
						pard.addUriAttach(uri);
					} else {
						pard.addIdAttach(field.getName());
						bat.addFile(field.getName(), file);
					}
					bat.addParam(pard);
				}
			} else if (Collection.class.isAssignableFrom(field.getType())) {
				Collection colls = (Collection) BeanPropertyUtility.getPropertyValue(outputobject, wrapperOutputobject, field.getName());
				// Collection colls = (Collection) BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(outputobject, field.getName());
				if (colls != null) {
					BeanParamDescription pard = new BeanParamDescription();
					pard.setPropName(field.getName());
					pard.setPropType(field.getType().getName());
					// imposto la classe della collection
					pard.setCollectionClassName(colls.getClass().getName());
					List<String> uriAttachesForProp = null;
					if (Boolean.TRUE.equals(Configuration.getInstance().getSendAttachmentsByUri())) {
						uriAttachesForProp = new ArrayList<String>();
						for (Object object : colls) {
							if (object != null && object instanceof File) {
								File file = (File) object;
								String uri = null;								
								try {
									if(attachStorage != null) {
										uri = attachStorage.storeTempFile(file);									
									} else {
										uri = file.getAbsolutePath();
									}	
								} catch(Exception e) {}
								if (StringUtils.isNotBlank(uri)) {
									uriAttachesForProp.add(uri);									
								} else {									
									// se va' in errore anche solo uno annullo tutto e invio i file come attach
									uriAttachesForProp = null;
									break;
								}
							}
						}
					}
					if(uriAttachesForProp != null && uriAttachesForProp.size() > 0) {
						for (String uri : uriAttachesForProp) {
							pard.addUriAttach(uri);
						}
					} else {
						for (Object object : colls) {
							if (object != null && object instanceof File) {
								String idAttach = KeyGenerator.gen();
								pard.addIdAttach(idAttach);
								bat.addFile(idAttach, (File) object);
							}
						}
					}
					bat.addParam(pard);
				}
			} else {
				log.warn(">>>>>> field name " + field.getName() + " non gestibile come attach");
			}
		}
		if (bat.getParams().size() > 0) {
			return bat;
		} else {
			// nessun parametro tipo file
			return null;
		}
	}

	/**
	 * Deserializzazione attach description nell'oggetto originale
	 * 
	 * @param ad
	 * @param files
	 * @param serUtil
	 * @param paramClass
	 * @param paramType
	 * @return
	 */
	public static Serializable deserialize(AttachDescription ad, Map<String, File> files, IServiceSerialize serUtil, Class paramClass, Type paramType)
			throws AttachDeSerializationException {
		IAttachStorage attachStorage = null;
		try {
			attachStorage = (IAttachStorage)SpringAppContext.getContext().getBean("AttachStorage");			
        } catch (Exception e) {
        	attachStorage = null;
        }	
		try {
			if (ad instanceof FileAttachDescription) {
				log.debug("ricostruisco il parametro File");
				File ret;
				if (ad.getContents() != null && ad.getContents().size() == 1) {
					String idFile = ad.getContents().get(0).getId();
					ret = files.get(idFile);
					if (ret == null)
						throw new AttachDeSerializationException("file con id:" + idFile + " non trovato");
					return ret;
				}
			} else if (ad instanceof CollectionFileAttachDescription) {
				log.debug("ricostruisco il parametro come collection di file");
				// caso in cui hai inviato una collection di file
				if (Collection.class.isAssignableFrom(paramClass)) {
					// istanzio la classe inviata
					String clazzName = ((CollectionFileAttachDescription) ad).getCollectionClassName();
					// Collection obj=(Collection)paramClass.newInstance();
					Collection obj = (Collection) Class.forName(clazzName).newInstance();
					// prelevo i file associati
					List<FileIdAssociation> paramFiles = ad.getContents();
					for (FileIdAssociation fileIdAssociation : paramFiles) {
						String key = fileIdAssociation.getId();
						File tmp = files.get(key);
						if (tmp == null)
							throw new AttachDeSerializationException("file con id:" + key + " non trovato");

						obj.add(tmp);
					}
					return (Serializable) obj;
				}
				log.fatal("parametro non di tipo collection seriallizzato come una collection di file");
				// TODO
			} else if (ad instanceof BeanParamsAttachDescription) {
				log.debug("ricostruisco il bean con parametri di tipo file");
				BeanParamsAttachDescription bad = (BeanParamsAttachDescription) ad;
				String xmlObject = bad.getSerializedData();
				Serializable ret = serUtil.deserialize(xmlObject, paramClass, paramType);
				BeanWrapperImpl wrappedRet = BeanPropertyUtility.getBeanWrapper(ret);
				List<BeanParamDescription> props = bad.getParams();
				for (BeanParamDescription beanParamDescription : props) {
					String propName = beanParamDescription.getPropName();
					// verifico se è una collection altrimenti prendo il primo
					if (!Collection.class.isAssignableFrom(Class.forName(beanParamDescription.getPropType()))) {
						log.debug("simple prop detected " + propName);
						File tmp = null;
						List<String> uriAttachesForProp = beanParamDescription.getUriAttach();
						if(uriAttachesForProp != null && uriAttachesForProp.size() > 0) {
							String uri = uriAttachesForProp.get(0);
							if(StringUtils.isNotBlank(uri)) {
								try {									
									if(attachStorage != null && uri.startsWith("[")) {
										tmp = attachStorage.extractTempFile(uri);
									} else {
										tmp = new File(uri);
									}
								} catch(Exception e) {}		
							}
							if (tmp == null || !tmp.exists()) {
								throw new AttachDeSerializationException("file con uri:" + uri + " non trovato");	
							}
						} else {
							String idFile = beanParamDescription.getIdAttach().get(0);
							String key = idFile;
							tmp = files.get(key);
							if (tmp == null || !tmp.exists()) {
								throw new AttachDeSerializationException("file con id:" + key + " non trovato");
							}
						}
						BeanPropertyUtility.setPropertyValue(ret, wrappedRet, propName, tmp);
						// BeanUtilsBean2.getInstance().setProperty(ret, propName, tmp);
					} else {
						log.debug(" collection  prop detected " + propName);
						Collection obj = (Collection) Class.forName(beanParamDescription.getCollectionClassName()).newInstance();						
						List<String> uriAttachesForProp = beanParamDescription.getUriAttach();
						if(uriAttachesForProp != null && uriAttachesForProp.size() > 0) {
							for (String uri : uriAttachesForProp) {
								File tmp = null;	
								if(StringUtils.isNotBlank(uri)) {
									try {										
										if(attachStorage != null && uri.startsWith("[")) {
											tmp = attachStorage.extractTempFile(uri);
										} else {
											tmp = new File(uri);
										}										
									} catch(Exception e) {}	
								}
								if (tmp == null || !tmp.exists()) {
									throw new AttachDeSerializationException("file con uri:" + uri + " non trovato");
								}
								obj.add(tmp);
							}
						} else {
							// prelevo i file associati alla proprietà
							List<String> attachesForProp = beanParamDescription.getIdAttach();
							// List<FileIdAssociation> paramFiles=ad.getContents();
							for (String key : attachesForProp) {
								// String key=fileIdAssociation.getId();
								File tmp = files.get(key);
								if (tmp == null || !tmp.exists()) {
									throw new AttachDeSerializationException("file con id:" + key + " non trovato");
								}
								obj.add(tmp);
							}
						}
						BeanPropertyUtility.setPropertyValue(ret, wrappedRet, propName, obj);
						// BeanUtilsBean2.getInstance().setProperty(ret, propName, obj);
					}

				}
				return ret;
			} else if (ad instanceof EmptyListParamDescription) {
				log.debug("ricostruisco il bean di tipo empty");
				EmptyListParamDescription lEmptyListParamDescription = (EmptyListParamDescription) ad;
				Serializable recostructedObj = null;
				if (lEmptyListParamDescription.getBean() != null) {
					recostructedObj = AttachHelper.deserialize((AttachDescription) lEmptyListParamDescription.getBean(), files, serUtil, paramClass, paramType);
				} else {
					String xmlObject = lEmptyListParamDescription.getSerializedData();
					recostructedObj = serUtil.deserialize(xmlObject, paramClass, paramType);
				}
				BeanWrapperImpl wrappedRecostructedObj = BeanPropertyUtility.getBeanWrapper(recostructedObj);
				List lList = new ArrayList();
				for (EmptyListPropertyDescription lEmptyListPropertyDescription : lEmptyListParamDescription.getEpmtyList()) {
					BeanPropertyUtility.setPropertyValue(recostructedObj, wrappedRecostructedObj, lEmptyListPropertyDescription.getPropName(), lList);
					// BeanUtilsBean2.getInstance().setProperty(recostructedObj, lEmptyListPropertyDescription.getPropName(), lList);
				}
				return recostructedObj;
			}
		} catch (Exception ex) {
			log.fatal("error deseriaalizing attach paramclass:" + paramClass + " type:" + paramType, ex);
			throw new AttachDeSerializationException("error deseriaalizing attach paramclass:" + paramClass + " type:" + paramType, ex);
		}
		return null;
	}

	// public static String buildKeyFromId(String id){
	// return id;
	// //return "<"+id+">";
	// }
}

