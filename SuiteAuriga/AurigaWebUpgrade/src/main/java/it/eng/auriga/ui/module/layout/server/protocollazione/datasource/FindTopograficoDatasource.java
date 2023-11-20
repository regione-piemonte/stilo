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
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DatiTopograficoXMLIOBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.FindTopograficoBean;
import it.eng.client.DmpkUtilityFindintopografico;
import it.eng.document.NumeroColonna;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Riga;
import it.eng.jaxb.variabili.Riga.Colonna;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
@Datasource(id = "FindTopograficoDatasource")
public class FindTopograficoDatasource extends AbstractServiceDataSource<FindTopograficoBean, FindTopograficoBean>{
	
	private static Logger mLogger = Logger.getLogger(FindTopograficoDatasource.class);

	@Override
	public FindTopograficoBean call(FindTopograficoBean bean) throws Exception {		
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		FindTopograficoBean topograficoInfo = new FindTopograficoBean();		
		try {
		
			    //***********************************
		        // INPUT:
			    //***********************************
			    // Iddominioin          
			    // Flgsolovldin      = 1
			    // Flgcompletadatiin = 1
				// DatiTopograficoXMLIOBean ( settare solo quelli valorizzati) :
				//   - id Topografico
				//   - codice rapido
				//   - nome
  			    //   - descrizione			
				DmpkUtilityFindintopograficoBean input = new DmpkUtilityFindintopograficoBean();				
				input.setIddominioin(lAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
				input.setFlgsolovldin(1);
				input.setFlgcompletadatiin(1);

				DatiTopograficoXMLIOBean lDatiTopograficoXMLIOBean = new DatiTopograficoXMLIOBean();
				if (bean.getIdTopografico() != null && !bean.getIdTopografico().equalsIgnoreCase("")){
					lDatiTopograficoXMLIOBean.setIdTopografico(bean.getIdTopografico());
				}
				
				if (bean.getCodRapidoTopografico() != null && !bean.getCodRapidoTopografico().equalsIgnoreCase("")){
					lDatiTopograficoXMLIOBean.setCodRapidoTopografico(bean.getCodRapidoTopografico());
				}				
				
				if (bean.getNomeTopografico() != null && !bean.getNomeTopografico().equalsIgnoreCase("")){
					lDatiTopograficoXMLIOBean.setNomeTopografico(bean.getNomeTopografico());
				}
				if (bean.getDescrTopografico() != null && !bean.getDescrTopografico().equalsIgnoreCase("")){
					lDatiTopograficoXMLIOBean.setDescrTopografico(bean.getDescrTopografico());
				}
				BeanWrapperImpl wrapperDatiTopograficoXMLIOBean = BeanPropertyUtility.getBeanWrapper(lDatiTopograficoXMLIOBean);
				
				Riga lRigaIn=null;
				try {
					lRigaIn = buildRiga(lDatiTopograficoXMLIOBean, wrapperDatiTopograficoXMLIOBean);
				} catch (IllegalAccessException e) {
					mLogger.warn(e);
				} catch (InvocationTargetException e) {
					mLogger.warn(e);
				} catch (NoSuchMethodException e) {
					mLogger.warn(e);
				}
				
				
				String xmlDatiTopograficoIn = transformRigaToXml(lRigaIn);
				
				input.setDatitoponimoxmlio(xmlDatiTopograficoIn);
				
				//***********************************
				// OUTPUT
				//***********************************
				// idTopograficoout
				// colonna 1 : id Topografico
				// colonna 2 : codice rapido
				// colonna 3 : nome
				// colonna 4 : descrizione					
				SchemaBean lSchemaBean = new SchemaBean();
				lSchemaBean.setSchema(lAurigaLoginBean.getSchema());
				DmpkUtilityFindintopografico lDmpkUtilityFindintopografico = new DmpkUtilityFindintopografico();
				StoreResultBean<DmpkUtilityFindintopograficoBean> output = lDmpkUtilityFindintopografico.execute(getLocale(), lSchemaBean, input);
				if (output.isInError()){
//					addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.ERROR);
					FindTopograficoBean lFindTopograficoBean = new FindTopograficoBean();
					lFindTopograficoBean.setVuoto(true);
					return lFindTopograficoBean;
				} 		
					
				String xmlDatiTopograficoOut = output.getResultBean().getDatitoponimoxmlio();
				
				Riga lRiga =   transformXmlToRiga(xmlDatiTopograficoOut);
				
				topograficoInfo = populateTopografico(lRiga);
				
				topograficoInfo.setIdTopograficoOut(output.getResultBean().getIdtoponimoout());			// id Topografico out
								
			} catch (Exception e) {			
				throw e;
			}			
		return topograficoInfo;
	}
	
	
	private FindTopograficoBean populateTopografico(Riga lRiga) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {		
		FindTopograficoBean topograficoInfo = new FindTopograficoBean();
		String nroColonna;
		String valueColonna = "";		

		for (int i=0; i<lRiga.getColonna().size(); i++)
			{	
				nroColonna = lRiga.getColonna().get(i).getNro().toString();
				valueColonna = lRiga.getColonna().get(i).getContent();				
				switch(Integer.parseInt(nroColonna))
				{				
					case 1:																			    // colonna 1 : id Topografico
						topograficoInfo.setIdTopografico(valueColonna);
						break;						
					case 2:																			    // colonna 2 : cod rapido
						topograficoInfo.setCodRapidoTopografico(valueColonna);
						break;						
					case 3:																			    // colonna 3 : nome
						topograficoInfo.setNomeTopografico(valueColonna);
						break;							
					case 4:																			    // colonna 4 : descrizione
						topograficoInfo.setDescrTopografico(valueColonna);
						break;								        
					}
			}		
		return topograficoInfo;
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
	
	private Riga buildRiga(DatiTopograficoXMLIOBean lDatiTopograficoXMLIOBean, BeanWrapperImpl wrapperDatiTopograficoXMLIOBean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Riga lRiga = new Riga();
		Field[] lFields = lDatiTopograficoXMLIOBean.getClass().getDeclaredFields();
		for (Field lField : lFields){
			NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
			if (lNumeroColonna!=null){
				String value = BeanPropertyUtility.getPropertyValueAsString(lDatiTopograficoXMLIOBean, wrapperDatiTopograficoXMLIOBean, lField.getName());
				// String value = BeanUtilsBean2.getInstance().getProperty(lDatiTopograficoXMLIOBean, lField.getName());
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
