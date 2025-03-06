package it.eng.areas.hybrid.module.siss.thread;

import it.eng.areas.hybrid.module.siss.bean.FileBean;
import it.eng.areas.hybrid.module.siss.bean.HashFileBean;
import it.eng.areas.hybrid.module.siss.messages.MessageKeys;
import it.eng.areas.hybrid.module.siss.messages.Messages;
import it.eng.areas.hybrid.module.siss.ui.PanelSign;
import it.eng.areas.hybrid.module.siss.util.SignerType;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;

public class SignerThread extends Thread{

	public final static Logger logger = Logger.getLogger(SignerThread.class);
	
	DocumentBuilderFactory documentBuilderFactory;

	private PanelSign panelSign;
	private List<HashFileBean> hashfilebean;
	private List<FileBean> fileBeanList;

	private SignerType tipoFirma;
	private String modalitaFirma;
	
	private JProgressBar bar;
	private boolean timemark=false;//se apporre la marca alla firma
	
	public SignerThread(PanelSign panelSign, List<HashFileBean> hashfilebean, List<FileBean> fileBeanList,
			SignerType type, String modalitaFirma, 
			JProgressBar bar, boolean timemark) {
		this.panelSign = panelSign;
		this.hashfilebean = hashfilebean;
		this.fileBeanList = fileBeanList;
		this.tipoFirma = type;
		this.modalitaFirma = modalitaFirma;
		this.bar = bar;
		if( this.hashfilebean!=null && this.hashfilebean.size()>0)
			this.bar.setMaximum( this.hashfilebean.size() );
		if( this.fileBeanList!=null && this.fileBeanList.size()>0)
			this.bar.setMaximum( this.fileBeanList.size() );
		this.bar.setMinimum(0);
		this.timemark=timemark;
	}

	@Override
	public void run() {

		documentBuilderFactory = DocumentBuilderFactory.newInstance();

		panelSign.signStarted();

		bar.setVisible( true );
		bar.setStringPainted( true );
		bar.setString( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_PROVIDERLOADING ) );

		List<String> lListException = new ArrayList<String>();
		boolean success = false;

		try {
			if( hashfilebean!=null && !hashfilebean.isEmpty() ){
				logger.info("Modalita'� hash");
				boolean esitoFirmaComplessiva = true;
				for( int i=0;i<hashfilebean.size();i++ ){
					//Deserializzo l'oggetto
					HashFileBean hash = hashfilebean.get(i);

					bar.setValue(i+1);
					bar.setString("Firma file "+hash.getFileName() +" in corso ("+(i+1)+"/"+ hashfilebean.size() +").");
					logger.info("Firma file "+hash.getFileName() +" in corso ("+(i+1) + "/" + hashfilebean.size() + ").");

					boolean esitoFirma = true;
					String str = "";
					hash.setSignedBean( str );
					logger.info("Il file " + hash.getFileName() + " è stato firmato" );
					panelSign.getTextArea().append("Il file " + hash.getFileName() + " è stato firmato\n");
					if( str== null )
						esitoFirma = false;
					esitoFirmaComplessiva = esitoFirma && esitoFirmaComplessiva ;
				}

				if( esitoFirmaComplessiva ){
					//Effettuo il download sul client
					panelSign.saveOutputFiles();
					success = true;
				}			
			} else{
					logger.info("Modalita' file");
				
					boolean esitoFirmaComplessiva = true;
					FirmaSISS firmaSISS = new FirmaSISS( fileBeanList.size() );
					if( fileBeanList!=null ){
						int i=0;
						for(FileBean bean: fileBeanList){
							bar.setValue(i+1);
							bar.setString("Firma file " + (i+1) + " in corso ("+(i+1)+"/"+ fileBeanList.size() +").");
							logger.info("Firma file "+bean.getFileName()+" in corso ("+(i+1) + "/" + fileBeanList.size() + ").");

							logger.info("Firma congiunta? " + bean.isFirmaCongiuntaRequired() );
							
							boolean esitoFirma = true;
							boolean primoFile = false;
							if( i==0)
								primoFile = true;
							boolean ultimoFile = false;
							if( i==fileBeanList.size()-1)
								ultimoFile = true;
							String tipoFile = "TESTO";
							if( bean.getIsPdf() )
								tipoFile = "PDF";
							logger.info("Firmo il file con id " + bean.getIdFile() );
							boolean esitoRichiestaFirma = firmaSISS.richiediFirmaFile( bean.getFile(), /*bean.getFileName()*/bean.getIdFile(), primoFile, ultimoFile, tipoFile);
							logger.info("esitoRichiestaFirma " + esitoRichiestaFirma);
//							if( esitoRichiestaFirma ){
//								String contenutoFirmato = firmaSISS.ottieniFileFirmato(bean.getFileName(), primoFile, ultimoFile, bean.getOutputFile() );
//								if( contenutoFirmato!=null )
//									esitoFirma = true;
//								else
//									esitoFirma = false;
//							}
//							
//							logger.info("File " + bean.getFileName() + " firmato con successo: " +esitoFirma );
//							if( esitoFirma )
//								panelSign.getTextArea().append("File " + bean.getFileName() + " firmato con successo.\n");
//							else
//								panelSign.getTextArea().append("File " + bean.getFileName() + " firmato con errore.\n");
//							esitoFirmaComplessiva = esitoFirma && esitoFirmaComplessiva ;
							
							i++;
						}
						i=0;
						for(FileBean bean: fileBeanList){
							bar.setValue(i+1);
							bar.setString("Firma file " + (i+1) + " in corso ("+(i+1)+"/"+ fileBeanList.size() +").");
							logger.info("Firma file "+bean.getFileName()+" in corso ("+(i+1) + "/" + fileBeanList.size() + ").");

							logger.info("Firma congiunta? " + bean.isFirmaCongiuntaRequired() );
							
							boolean esitoFirma = true;
							boolean primoFile = false;
							if( i==0)
								primoFile = true;
							boolean ultimoFile = false;
							if( i==fileBeanList.size()-1)
								ultimoFile = true;
							logger.info("Richiedo file firmato con id "  + bean.getIdFile() );
							String contenutoFirmato = firmaSISS.ottieniFileFirmato(/*bean.getFileName()*/bean.getIdFile(), primoFile, ultimoFile, bean.getOutputFile() );
							if( contenutoFirmato!=null )
								esitoFirma = true;
							else
								esitoFirma = false;
							
							
							logger.info("File " + bean.getFileName() + " firmato con successo: " +esitoFirma );
							if( esitoFirma )
								panelSign.getTextArea().append("File " + bean.getFileName() + " firmato con successo.\n");
							else
								panelSign.getTextArea().append("File " + bean.getFileName() + " firmato con errore.\n");
							esitoFirmaComplessiva = esitoFirma && esitoFirmaComplessiva ;
							
							i++;
						}
					}
					
					if( esitoFirmaComplessiva ){
						panelSign.saveOutputFiles();
						if( fileBeanList!=null){
							for(FileBean bean: fileBeanList){
							//	FileUtility.deleteTempDirectory( bean.getRootFileDirectory() );
							}
						}

						panelSign.showMessageDialog( Messages.getMessage( MessageKeys.MSG_OPSUCCESS ), "", JOptionPane.INFORMATION_MESSAGE );
						success = true;
					}
				}
			
		} catch(Exception e){
			logger.info("Errore", e);
			lListException.add("Errore " + e.getMessage());
		} catch (Throwable e) {
			logger.info("Errore", e);
			lListException.add("Errore " + e.getMessage());
		}
		
		if (!success){
			String exceptionMessage = "";
			for (String lString : lListException){
				exceptionMessage += "\n" + lString ;
			}
			panelSign.gestisciEccezione(exceptionMessage);
		}
		panelSign.signStopped(success);
	}



	


}