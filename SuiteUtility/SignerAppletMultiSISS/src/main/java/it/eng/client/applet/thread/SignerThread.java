package it.eng.client.applet.thread;

import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.panel.PanelSign;
import it.eng.common.LogWriter;
import it.eng.common.bean.FileBean;
import it.eng.common.bean.HashFileBean;
import it.eng.common.type.SignerType;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.xml.parsers.DocumentBuilderFactory;

public class SignerThread extends Thread{

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
				LogWriter.writeLog("Modalità hash");
				boolean esitoFirmaComplessiva = true;
				for( int i=0;i<hashfilebean.size();i++ ){
					//Deserializzo l'oggetto
					HashFileBean hash = hashfilebean.get(i);

					bar.setValue(i+1);
					bar.setString("Firma file "+hash.getFileName() +" in corso ("+(i+1)+"/"+ hashfilebean.size() +").");
					LogWriter.writeLog("Firma file "+hash.getFileName() +" in corso ("+(i+1) + "/" + hashfilebean.size() + ").");

					boolean esitoFirma = true;
					String str = "";
					hash.setSignedBean( str );
					LogWriter.writeLog("Il file " + hash.getFileName() + " è stato firmato" );
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
					LogWriter.writeLog("Modalita� file");
				
					boolean esitoFirmaComplessiva = true;
					FirmaSISS firmaSISS = new FirmaSISS( fileBeanList.size() );
					if( fileBeanList!=null ){
						int i=0;
						for(FileBean bean: fileBeanList){
							bar.setValue(i+1);
							bar.setString("Firma file " + (i+1) + " in corso ("+(i+1)+"/"+ fileBeanList.size() +").");
							LogWriter.writeLog("Firma file "+bean.getFileName()+" in corso ("+(i+1) + "/" + fileBeanList.size() + ").");

							LogWriter.writeLog("Firma congiunta? " + bean.isFirmaCongiuntaRequired() );
							
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
							LogWriter.writeLog("Firmo il file con id " + bean.getIdFile() );
							boolean esitoRichiestaFirma = firmaSISS.richiediFirmaFile( bean.getFile(), /*bean.getFileName()*/bean.getIdFile(), primoFile, ultimoFile, tipoFile);
							LogWriter.writeLog("esitoRichiestaFirma " + esitoRichiestaFirma);
//							if( esitoRichiestaFirma ){
//								String contenutoFirmato = firmaSISS.ottieniFileFirmato(bean.getFileName(), primoFile, ultimoFile, bean.getOutputFile() );
//								if( contenutoFirmato!=null )
//									esitoFirma = true;
//								else
//									esitoFirma = false;
//							}
//							
//							LogWriter.writeLog("File " + bean.getFileName() + " firmato con successo: " +esitoFirma );
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
							LogWriter.writeLog("Firma file "+bean.getFileName()+" in corso ("+(i+1) + "/" + fileBeanList.size() + ").");

							LogWriter.writeLog("Firma congiunta? " + bean.isFirmaCongiuntaRequired() );
							
							boolean esitoFirma = true;
							boolean primoFile = false;
							if( i==0)
								primoFile = true;
							boolean ultimoFile = false;
							if( i==fileBeanList.size()-1)
								ultimoFile = true;
							LogWriter.writeLog("Richiedo file firmato con id "  + bean.getIdFile() );
							String contenutoFirmato = firmaSISS.ottieniFileFirmato(/*bean.getFileName()*/bean.getIdFile(), primoFile, ultimoFile, bean.getOutputFile() );
							if( contenutoFirmato!=null )
								esitoFirma = true;
							else
								esitoFirma = false;
							
							
							LogWriter.writeLog("File " + bean.getFileName() + " firmato con successo: " +esitoFirma );
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
			LogWriter.writeLog("Errore", e);
			lListException.add("Errore " + e.getMessage());
		} catch (Throwable e) {
			LogWriter.writeLog("Errore", e);
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