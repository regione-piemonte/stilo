package it.eng.utility.oomanager;

import java.io.File;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jodconverter.LocalConverter;
import org.jodconverter.document.DocumentFormat;
import org.jodconverter.document.DocumentFormatRegistry;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;

import it.eng.utility.oomanager.config.OpenOfficeConfiguration;
import it.eng.utility.oomanager.config.OpenOfficeInstance;
import it.eng.utility.oomanager.exception.OpenOfficeException;

/**
 * Classe di gestione delle istanze di openoffice
 * @author Administrator
 *
 */
public class OpenOfficeConverter {

	private OpenOfficeConverter(){}
	
	private static Logger log = LogManager.getLogger(OpenOfficeConverter.class);
		
	private static OpenOfficeConfiguration oo_configuration = null;
	//private static int indexOpenOfficeCurrentUse = 0;
	private static OfficeManager officeManager = null;
	private LocalConverter documentConverter = null;
	
	private static boolean managerAttivo = false;
		
	public static void configure(OpenOfficeConfiguration configuration) throws OpenOfficeException {
		log.debug("Configurazione openOffice");
		if (configuration.getInstances() != null) {
			if (configuration.getInstances().isEmpty()) {
				throw new OpenOfficeException("Configurazioni OpenOffice vuote!");
			}
		} else {
			throw new OpenOfficeException("Configurazioni OpenOffice null!");
		}
		oo_configuration = configuration;

		List<OpenOfficeInstance> oo_istances = oo_configuration.getInstances();

		boolean errorManager = true;
		for (OpenOfficeInstance oo_istance : oo_istances) {
			if (errorManager) {
				String officeHome = oo_istance.getOfficeServiceHome();
				log.debug("officeHome " + officeHome);

				int[] portList = new int[oo_istance.getPortList().size()];
				for (int i = 0; i < oo_istance.getPortList().size(); i++) {
					try {
						int port = Integer.parseInt(oo_istance.getPortList().get(i));
						log.debug("port " + port);
						portList[i] = port;
					} catch (Exception e) {

					}
				}

				try {

					startManager(officeHome, portList);
					log.debug("Office manager avviato");

					errorManager = false;
					managerAttivo = true;

				} catch (OfficeException e) {
					log.error(e);
					errorManager = true;
				}
			}
		}

		if (errorManager) {
			log.error("Inizializzazione manager openOffice non riuscita");
			managerAttivo = false;
		}
	}
	
	private static void startManager (String officeHome, int[] portList) throws OfficeException {
		officeManager = LocalOfficeManager.builder().install().
				officeHome(officeHome).
				portNumbers(portList).
				//processTimeout(30000).
				//processRetryInterval(250).
				//taskExecutionTimeout(60000).
				build();
		
		officeManager.start();
	}
	
	public static synchronized OpenOfficeConverter newInstance() throws OpenOfficeException {
		if (oo_configuration == null) {
			throw new OpenOfficeException("OpenOfficeConverter non confgiurato!");
		}
		
		if (!managerAttivo) {
			throw new OpenOfficeException("OpenOffice Manager non attivo!");
		}
		
		if (!officeManager.isRunning()) {
			log.info("------------------ START MANAGER");
			try {
				officeManager.start();
			} catch (OfficeException e) {
				log.error("Errore nell'avvio del manager Office ", e);
				throw new OpenOfficeException("Errore nell'avvio del manager Office");
			}
		}
		
		return new OpenOfficeConverter();
	}
	
	/*public void convert(File input,File output,DocumentFormat outputformat)throws OpenOfficeException{
		convert(input, null, output, outputformat);		
	}*/
	
	public void convert(File input, File output) throws OpenOfficeException{
		genericConvert(input, null, output, null);
	}
	
	public void convertByOutExt(File input, String mimeInputFile, File output, String extOutputFile) throws OpenOfficeException {
		log.debug("convertByOutExt");
		documentConverter = LocalConverter.make(officeManager);
		DocumentFormatRegistry registry = documentConverter.getFormatRegistry();
		DocumentFormat outputformat = registry.getFormatByExtension(extOutputFile);
		DocumentFormat inputDocumentFormat = registry.getFormatByMediaType(mimeInputFile);
		genericConvert(input, inputDocumentFormat,  output, outputformat);
		
		
	}
	
	public void convertByOutMime(File input, String mimeInputFile, File output, String mimeOutputFile) throws OpenOfficeException {
		log.debug("convertByOutMime");
		documentConverter = LocalConverter.make(officeManager);
		DocumentFormatRegistry registry = documentConverter.getFormatRegistry();
		DocumentFormat outputformat = registry.getFormatByMediaType(mimeOutputFile);
		DocumentFormat inputDocumentFormat = registry.getFormatByMediaType(mimeInputFile);
		genericConvert(input, inputDocumentFormat, output, outputformat);
	}
		
	public void genericConvert(File input, DocumentFormat inputformat, File output, DocumentFormat outputformat) throws OpenOfficeException {
		
		int currenttry = 0;
		boolean isconvert = false;
		Exception lastException = null;
		
		//LocalConverter documentConverter = LocalConverter.make(officeManager);
		
		log.info("currenttry " + currenttry + " maxTrytoconvert " + oo_configuration.getMaxTrytoconvert().intValue());
		while(currenttry<=oo_configuration.getMaxTrytoconvert().intValue()){
			try{
				
				if(inputformat!=null && outputformat!=null && input!=null && output!=null){
					log.debug("Chiamata metodo converter.convert(input,inputformat ,output, outputformat)");
					//converter.convert(input,inputformat ,output, outputformat);
					documentConverter
					.convert(input).as(inputformat)
		            .to(output).as(outputformat)
		            .execute();
				} else if(outputformat!=null && input!=null && output!=null){
					log.debug("Chiamata metodo converter.convert(input,output, outputformat);");
					//converter.convert(input,output, outputformat);
					documentConverter
					.convert(input)
		            .to(output).as(outputformat)
		            .execute();
				}else if(input!=null && output!=null){
					log.debug("Chiamata metodo converter.convert(input,output);");
					//converter.convert(input,output);
					documentConverter
					.convert(input)
		            .to(output)
		            .execute();
				}else{
					throw new OpenOfficeException("Impossibile convertire il documento! input:" + input + " -- format:" + inputformat + " <--> output:" + output + " -- format:" + outputformat);
				}
				//Conversione eseguita fine del ciclo
				isconvert = true;
				break;
			} catch(OfficeException e) {
				
				lastException = e;
				
				log.warn("Errore conversione tentativo " + currenttry, e);
				
				currenttry++;
				//Non rilancio l'eccezione ma ritento con un'altra istanza di openoffice, prova a riavviare l'istanza andata in errore
				/*indexOpenOfficeCurrentUse++;
				if(indexOpenOfficeCurrentUse==configuration.getInstances().size()){
					indexOpenOfficeCurrentUse = 0;
				}*/
				/*if(instance!=null){
					OpenOfficeService service = new OpenOfficeService(instance);
					service.start();
				}*/
			}
			/*finally{
				if(connection!=null){
					if(connection.isConnected()){
						connection.disconnect();
					}
				}
			}*/
		}
		if(!isconvert){
			
			String message = String.format("Non è stato possibile convertire il documento, numero massimo di tentativi di conversione(%1$s) raggiunto!", oo_configuration.getMaxTrytoconvert());
			log.error(message, lastException);
			throw new RuntimeException(message, lastException);
		}				
	}
 
}