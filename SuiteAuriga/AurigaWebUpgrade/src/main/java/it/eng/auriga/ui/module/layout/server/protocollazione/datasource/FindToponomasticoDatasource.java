/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityFindintopograficoBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DatiToponomasticoXMLIOBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.FindToponomasticoBean;
import it.eng.client.DmpkUtilityFindintopografico;
import it.eng.document.NumeroColonna;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Riga;
import it.eng.jaxb.variabili.Riga.Colonna;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
@Datasource(id = "FindToponomasticoDatasource")
public class FindToponomasticoDatasource extends AbstractServiceDataSource<FindToponomasticoBean, FindToponomasticoBean>{
	
	private static Logger mLogger = Logger.getLogger(FindToponomasticoDatasource.class);

	@Override
	public FindToponomasticoBean call(FindToponomasticoBean bean) throws Exception {		
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		FindToponomasticoBean toponomasticoInfo = new FindToponomasticoBean();		
		try {
		
			    //***********************************
		        // INPUT:
			    //***********************************
			    // Iddominioin          
			    // Flgsolovldin      = 1
			    // Flgcompletadatiin = 1
				// DatiToponomasticoXMLIOBean ( settare solo quelli valorizzati) :
				//   - id Toponimo
				//   - codice rapido
				//   - nome
  			    //   - descrizione			
				DmpkUtilityFindintopograficoBean input = new DmpkUtilityFindintopograficoBean();				
				input.setIddominioin(lAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
				input.setFlgsolovldin(1);
				input.setFlgcompletadatiin(1);

				DatiToponomasticoXMLIOBean lDatiToponomasticoXMLIOBean = new DatiToponomasticoXMLIOBean();
				if (bean.getIdToponimo() != null && !bean.getIdToponimo().equalsIgnoreCase("")){
					lDatiToponomasticoXMLIOBean.setIdToponimo(bean.getIdToponimo());
				}
				
				if (bean.getCodRapidoToponimo() != null && !bean.getCodRapidoToponimo().equalsIgnoreCase("")){
					lDatiToponomasticoXMLIOBean.setCodRapidoToponimo(bean.getCodRapidoToponimo());
				}				
				
				if (bean.getNomeToponimo() != null && !bean.getNomeToponimo().equalsIgnoreCase("")){
					lDatiToponomasticoXMLIOBean.setNomeToponimo(bean.getNomeToponimo());
				}
				if (bean.getDescrToponimo() != null && !bean.getDescrToponimo().equalsIgnoreCase("")){
					lDatiToponomasticoXMLIOBean.setDescrToponimo(bean.getDescrToponimo());
				}
				BeanWrapperImpl wrapperDatiToponomasticoXMLIOBean = BeanPropertyUtility.getBeanWrapper(lDatiToponomasticoXMLIOBean);
				
				Riga lRigaIn=null;
				try {
					lRigaIn = buildRiga(lDatiToponomasticoXMLIOBean, wrapperDatiToponomasticoXMLIOBean);
				} catch (IllegalAccessException e) {
					mLogger.warn(e);
				} catch (InvocationTargetException e) {
					mLogger.warn(e);
				} catch (NoSuchMethodException e) {
					mLogger.warn(e);
				}
				
				
				String xmlDatiToponimoIn = transformRigaToXml(lRigaIn);
				
				input.setDatitoponimoxmlio(xmlDatiToponimoIn);
				
				//***********************************
				// OUTPUT
				//***********************************
				// idtoponimoout
				// colonna 1 : id toponimo
				// colonna 2 : codice rapido
				// colonna 3 : nome
				// colonna 4 : descrizione					
				SchemaBean lSchemaBean = new SchemaBean();
				lSchemaBean.setSchema(lAurigaLoginBean.getSchema());
				DmpkUtilityFindintopografico lDmpkUtilityFindintopografico = new DmpkUtilityFindintopografico();
				StoreResultBean<DmpkUtilityFindintopograficoBean> output = lDmpkUtilityFindintopografico.execute(getLocale(), lSchemaBean, input);
				if (output.isInError()){
//					addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.ERROR);
					FindToponomasticoBean lFindToponomasticoBean = new FindToponomasticoBean();
					lFindToponomasticoBean.setVuoto(true);
					return lFindToponomasticoBean;
				} 		
					
				String xmlDatiToponimoOut = output.getResultBean().getDatitoponimoxmlio();
				
				Riga lRiga =   transformXmlToRiga(xmlDatiToponimoOut);
				
				toponomasticoInfo = populateToponimo(lRiga);
				
				toponomasticoInfo.setIdToponimoOut(output.getResultBean().getIdtoponimoout());			// id toponimo out
								
			} catch (Exception e) {			
				throw e;
			}			
		return toponomasticoInfo;
	}
	
	
	private FindToponomasticoBean populateToponimo(Riga lRiga) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {		
		FindToponomasticoBean toponomasticoInfo = new FindToponomasticoBean();
		String nroColonna;
		String valueColonna = "";		

		for (int i=0; i<lRiga.getColonna().size(); i++)
			{	
				nroColonna = lRiga.getColonna().get(i).getNro().toString();
				valueColonna = lRiga.getColonna().get(i).getContent();				
				switch(Integer.parseInt(nroColonna))
				{				
					case 1:																			    // colonna 1 : id toponimo
						toponomasticoInfo.setIdToponimo(valueColonna);
						break;						
					case 2:																			    // colonna 2 : cod rapido
						toponomasticoInfo.setCodRapidoToponimo(valueColonna);
						break;						
					case 3:																			    // colonna 3 : nome
						toponomasticoInfo.setNomeToponimo(valueColonna);
						break;							
					case 4:																			    // colonna 4 : descrizione
						toponomasticoInfo.setDescrToponimo(valueColonna);
						break;								        
					}
			}		
		return toponomasticoInfo;
	}
	
	private Riga transformXmlToRiga(String xmlStringIn)
	throws JAXBException {			
		Riga rigaOut = (Riga) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(new StringReader(xmlStringIn));
		return rigaOut;
	}
	
	private String transformRigaToXml(Riga lRigaIn) 
	throws JAXBException {		
			StringWriter lStringWriter = new StringWriter();
			SingletonJAXBContext.getInstance().createMarshaller().marshal(lRigaIn, lStringWriter);			
			return lStringWriter.toString();
	}
	
	private Riga buildRiga(DatiToponomasticoXMLIOBean lDatiToponomasticoXMLIOBean, BeanWrapperImpl wrapperDatiToponomasticoXMLIOBean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Riga lRiga = new Riga();
		Field[] lFields = lDatiToponomasticoXMLIOBean.getClass().getDeclaredFields();
		for (Field lField : lFields){
			NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
			if (lNumeroColonna!=null){
				String value = BeanPropertyUtility.getPropertyValueAsString(lDatiToponomasticoXMLIOBean, wrapperDatiToponomasticoXMLIOBean, lField.getName());
				// String value = BeanUtilsBean2.getInstance().getProperty(lDatiToponomasticoXMLIOBean, lField.getName());
				if (value!=null && !value.equalsIgnoreCase("")){
					Colonna lColonna = new Colonna();
					lColonna.setContent(value);
					lColonna.setNro(new BigInteger(lNumeroColonna.numero()));
					lRiga.getColonna().add(lColonna);					
				}		
			}
		}
		return lRiga;		
	}
}
