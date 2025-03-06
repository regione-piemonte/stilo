package it.eng.client.applet.thread;

import it.eng.client.applet.SmartCard;

import it.eng.client.applet.bean.PrivateKeyAndCert;
import it.eng.client.applet.exception.SmartCardAuthorizationException;
import it.eng.client.applet.exception.SmartCardException;
import it.eng.client.applet.fileProvider.FileOutputProvider;
import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.operation.AbstractSigner;
import it.eng.client.applet.operation.BaseSigner;
import it.eng.client.applet.operation.Factory;
import it.eng.client.applet.operation.FactorySigner;
import it.eng.client.applet.operation.ISigner;
import it.eng.client.applet.panel.PanelSign;
import it.eng.client.applet.util.CertificateUtils;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.client.applet.util.WSClientUtils;
import it.eng.common.CMSUtils;
import it.eng.common.LogWriter;
import it.eng.common.bean.HashFileBean;
import it.eng.common.bean.SignerObjectBean;
import it.eng.common.bean.ValidationInfos;
import it.eng.common.type.SignatureMerge;
import it.eng.common.type.SignerType;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.AuthProvider;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SerializationUtils;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sun.security.pkcs11.SunPKCS11;


public class SignerThread extends Thread{
 
	
	DocumentBuilderFactory documentBuilderFactory;
	
	private static final String wsPostPrototype = "" +
			"<soapenv:Envelope " +
			"	xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
			"	xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
			"	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
			"	<soapenv:Body>" +
			"		<authorize xmlns=\"http://ws.server.eng.it\">" +
			"			<certificate>%1$s</certificate>" +
			"		</authorize>" +
			"	</soapenv:Body>" +
			"</soapenv:Envelope>";
	
	private SmartCard card;
	private PanelSign panelSign;
	private SignerType tipoFirma;
	private String envMerge;
	private char[] pin;
	private int slot;
	private JProgressBar bar;
	private boolean timemark=false;//se apporre la marca alla firma
	private boolean counterSign=false;//se controfirmare 
	private boolean congiunta=false;
	private OutputStream outputStream=null;	
	
	public SignerThread(SmartCard card, 
			SignerType type, String envMerge, char[] pin,
			JProgressBar bar,int slot, boolean congiunta,
			boolean timemark,boolean counterSign, OutputStream outputStream) {
		this.card = card;
		this.panelSign = card.getPanelsign();
		this.tipoFirma = type;
		this.envMerge = envMerge;
		this.pin =pin;
		this.bar = bar;
		this.bar.setMaximum(card.getHashfilebean().size());
		this.bar.setMinimum(0);
		this.slot = slot;
		this.timemark=timemark;
		this.congiunta = congiunta;
		this.counterSign=counterSign;
		this.outputStream = outputStream;
	}
	
	@Override
	public void run() {
		
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		
		Security.addProvider( new BouncyCastleProvider() );
		
		panelSign.signStarted();
		
		bar.setVisible( true );
		bar.setStringPainted( true );
		bar.setString( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_PROVIDERLOADING ) );
		
//		AbstractSigner[] lAbstractSigners = null;
//		try {
//			lAbstractSigners = Factory.getSigner( PreferenceManager.getSignerDigestAlg(), PreferenceManager.getEnvelopeType(), pin, slot, timemark );
//			if (lAbstractSigners == null || lAbstractSigners.length == 0){
//				throw new Exception( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_NOPROVIDER ) );
//			}
//		} catch (Exception e1) {
//			LogWriter.writeLog("Errore", e1);
//			panelSign.gestisciEccezione( e1.getMessage() );
//			panelSign.signStopped();
//			return;
//		}
		
		List<String> pathProviders = null;
		try {
			pathProviders = Factory.getPathProviders();
			if (pathProviders == null || pathProviders.size() == 0){
				throw new Exception( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_NOPROVIDER ) );
			}
		} catch (Exception e1) {
			LogWriter.writeLog("Errore", e1);
			panelSign.gestisciEccezione( e1.getMessage() );
			panelSign.signStopped(false);
			return;
		}
		
		List<String> lListException = new ArrayList<String>();
		boolean success = false;
		//for (AbstractSigner absSigner : lAbstractSigners){
		for (String path : pathProviders){
			SunPKCS11 sunProvider;
			AbstractSigner absSigner = null;
			try {
				sunProvider = Factory.loginProvider(new File(path), slot, pin);
				if(sunProvider!=null){
					absSigner = new BaseSigner( PreferenceManager.getSignerDigestAlg(), PreferenceManager.getEnvelopeType(), sunProvider);
				}
				
				bar.setString(Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_PROVIDERLOADED ) );
				
				PrivateKeyAndCert[] listaPKC = null;
				try {
					listaPKC = absSigner.getPrivateKeyAndCert( pin, pin );
				} catch (SmartCardException e) {
					LogWriter.writeLog("Errore nel metodo getPrivateKeyAndCert, richiedo il secondo pin");
					String certPin = null;
					JPasswordField pf = new JPasswordField();
					JOptionPane.showMessageDialog(panelSign, pf, "Inserisci il pin della carta", JOptionPane.INFORMATION_MESSAGE);

					certPin = new String(pf.getPassword());
					listaPKC = absSigner.getPrivateKeyAndCert(pin, certPin.toCharArray());
					panelSign.gestisciEccezione( e.getMessage() );
				}
				if (listaPKC==null || listaPKC.length==0){
					throw new SmartCardException( Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_NOSIGNINGCERT ) );
				}
				
				X509Certificate certificate=listaPKC[0].getCertificate();
				bar.setString( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_SIGNINGCERTCHEKING ) );
				ValidationInfos certvalinfo = new ValidationInfos();
				if( certificate!=null ){
					certvalinfo  = CertificateUtils.checkCertificate( certificate );
				} else {
					throw new SmartCardException( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_NOSIGNINGCERT ) );
				}
				if(!certvalinfo.isValid()){
					throw new SmartCardException( certvalinfo.getErrorsString() );
				}
				bar.setString( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_SIGNINGCERTCHEKED ) );
			
				//controllo MACRO
				boolean codeCheck = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_CODECHECKENABLED );
				LogWriter.writeLog("Proprietà " + PreferenceKeys.PROPERTY_SIGN_CODECHECKENABLED + "=" + codeCheck );
				if( codeCheck ){
					ValidationInfos macroValInfo = null;
					try {
						bar.setString( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_MACROVERIFING ) );
						macroValInfo = WSClientUtils.checkFileForMacro( card.getFile() );
					} catch(Exception e){
						LogWriter.writeLog("Errore nella verifica macro, il controllo non è stato eseguito", e);
					}
					if( macroValInfo!=null && !macroValInfo.isValid() ){
						throw new SmartCardException( macroValInfo.getErrorsString() );
					} else {
						bar.setString( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_MACROVERIFIED ) );
					}
				} else {
					LogWriter.writeLog("Controllo macro non eseguito");
				}
			
				ValidationInfos authorizationInfos = preSignFunction( absSigner );
				// controllo di avere le autorizzazioni per procedere alla firma
				if (authorizationInfos.isValid()){
				
					LogWriter.writeLog("authorizationInfos.isValid()");
					if(PreferenceManager.enabled("hashOperationMode")){
						for( int i=0;i<card.getHashfilebean().size();i++ ){
		
							//Deserializzo l'oggetto
							HashFileBean hash = card.getHashfilebean().get(i);
							hash.setIdSmartCard(String.valueOf(pin));
		
							bar.setValue(i+1);
							bar.setString("Firma file "+hash.getFileName()+" in corso ("+(i+1)+"/"+card.getHashfilebean().size()+").");
		
							//Firmo l'oggetto
							PrivateKeyAndCert pvc = listaPKC[0];
							String str = absSigner.signerfile(hash,pvc, pin);
							
							//TODO per ora lo ricostruisco quì
							SignerObjectBean bean = (SignerObjectBean)SerializationUtils.deserialize(Base64.decodeBase64(str));
							//Se il file è firmato verifica se devi fare la firma congiunta o verticale
		
							if(card.getTipoBusta()!=null && envMerge.equalsIgnoreCase(SignatureMerge.CONGIUNTA.value()) ){
								CMSUtils.addSignerInfo(card.getFile(), bean,outputStream);
								IOUtils.closeQuietly(outputStream);
							}else{
								//default verticale 
								CMSSignedData data=CMSUtils.buildFileSigned(bean, card.getFile());
								File firmato = new File(card.getFile().getAbsolutePath()+".p7m");
								FileUtils.writeByteArrayToFile(firmato, data.getEncoded());
							}
							//Effettuo il download sul client
							LogWriter.writeLog("Vado a chiamare il file output");
							FileOutputProvider fop = card.getFileOutputProvider();
							File fileInput = card.getFile();
							if(fop!=null && fileInput!=null && card.getPanelsign().getOutputFile()!=null){
								LogWriter.writeLog("Effettuo la chiamata");
								InputStream in;
								try {
									in = new FileInputStream( card.getPanelsign().getOutputFile());
									if( card.getFileNameConvertito()!=null ){
										LogWriter.writeLog("Utilizzo il nome convertito " + card.getFileNameConvertito() );
										fop.saveOutputFile(in, card.getFileNameConvertito(), (String)card.getPanelsign().getTipoFirma().getSelectedItem());
									}
									else if(PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME )!=null){
										LogWriter.writeLog("Utilizzo il nome dalla preference " + PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME ) );
										fop.saveOutputFile(in, PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME ), (String)card.getPanelsign().getTipoFirma().getSelectedItem());
									}
									else {
										LogWriter.writeLog("Utilizzo il nome del file " + fileInput.getName() );
										fop.saveOutputFile(in, fileInput.getName(), (String)card.getPanelsign().getTipoFirma().getSelectedItem());
									}
									
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
									LogWriter.writeLog("Errore - " + e1.getMessage());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							JOptionPane.showMessageDialog( card.getJTabbedPane(),Messages.getMessage( MessageKeys.MSG_OPSUCCESS ),"", JOptionPane.INFORMATION_MESSAGE);
							success = true;
							//JOptionPane.showMessageDialog(card.getJTabbedPane(),"operazione eseguita","", JOptionPane.INFORMATION_MESSAGE);
							//JSObject.getWindow((SmartCard)card).call("addObjectSigner", new Object[]{str});
						}
					} else{
						LogWriter.writeLog("Modalità file");
						//evito di ricaricare il provider 
						AuthProvider provider = absSigner.getProvider();
						PrivateKeyAndCert pvc = listaPKC[0];
						
						bar.setString("Firma file in corso");
						LogWriter.writeLog("Firma file in corso");
						
						LogWriter.writeLog("Firma congiunta? " + congiunta );
						
						//TODO se il file non è PDF non puoi fare la firma PDF
						// verifica se hai scelto PDF come busta ma il file non è PDF proponi cades
						if(!card.isPDF() && tipoFirma.equals(SignerType.PDF)){
							//TODO avvisare l'utente
							LogWriter.writeLog("cambio il tipo busta a cades bes poiche' il file non è un PDF non posso fare una busta PADES");
							tipoFirma=SignerType.CAdES_BES;
						}
						ISigner signer=FactorySigner.getSigner( tipoFirma );
						LogWriter.writeLog( "Controfirma? " + counterSign );
						boolean esitoFirma = false;
						if(counterSign){
							//se contro firmi devi mantenere al busta attuale,
							//per cui devi usare il signer corrispondente alal busta
							signer=FactorySigner.getSigner(card.getTipoBusta());
							signer.addCounterSignature( card.getFile(), outputStream, pvc, null, provider );
						} else {
							LogWriter.writeLog("Signer " + signer );
							esitoFirma = signer.firma( card.getFile(), outputStream, pvc, provider, timemark, false, congiunta, card.getTipoBusta()!=null);
						}
						try{
							provider.logout();
						}catch(Exception e){
							LogWriter.writeLog("Errore", e);
						}
						if( esitoFirma ){
							LogWriter.writeLog("Vado a chiamare il file output");
							FileOutputProvider fop = card.getFileOutputProvider();
							File fileInput = card.getFile();
							if(fop!=null && fileInput!=null && card.getPanelsign().getOutputFile()!=null){
								LogWriter.writeLog("Effettuo la chiamata");
								InputStream in;
								try {
									in = new FileInputStream( card.getPanelsign().getOutputFile());
									if( card.getFileNameConvertito()!=null ){
										LogWriter.writeLog("Utilizzo il nome convertito " + card.getFileNameConvertito() );
										fop.saveOutputFile(in, card.getFileNameConvertito(), (String)card.getPanelsign().getTipoFirma().getSelectedItem());
									}
									else if(PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME )!=null){
										LogWriter.writeLog("Utilizzo il nome dalla preference " + PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME ) );
										fop.saveOutputFile(in, PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME ), (String)card.getPanelsign().getTipoFirma().getSelectedItem());
									}
									else {
										LogWriter.writeLog("Utilizzo il nome del file " + fileInput.getName() );
										fop.saveOutputFile(in, fileInput.getName(), (String)card.getPanelsign().getTipoFirma().getSelectedItem());
									}
									
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
									LogWriter.writeLog("Errore - " + e1.getMessage());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
							panelSign.showMessageDialog( Messages.getMessage( MessageKeys.MSG_OPSUCCESS ), "", JOptionPane.INFORMATION_MESSAGE );
							success = true;
							break;
						}
					}
				} else{
					throw new Exception(" Il certificato di firma fornito non e' quello associato all'utenza di lavoro: " + authorizationInfos.getErrorsString());
				}
			
			}catch(SmartCardException e){
				LogWriter.writeLog("Errore", e);
				lListException.add("Errore " + e.getMessage());
			} catch(Exception e){
				LogWriter.writeLog("Errore", e);

				String exceptionMessage = new SmartCardException(e).getMessage();

				if( exceptionMessage.equalsIgnoreCase(Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_PINLOCKED )) ||
					exceptionMessage.equalsIgnoreCase(Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_TOKENNOTPRESENT )) ||
					exceptionMessage.equalsIgnoreCase(Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_PININVALID )) ||
					exceptionMessage.equalsIgnoreCase(Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_PININCORRECT )) ||
					exceptionMessage.equalsIgnoreCase(Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_PINEXPIRED )) )
					lListException.add(exceptionMessage);
				else
					lListException.add(e.getMessage());
				break;
			}catch (Throwable e) {
				LogWriter.writeLog("Errore", e);
				lListException.add("Errore " + e.getMessage());
			}
		}
		if (!success){
			for (String lString : lListException)
				panelSign.gestisciEccezione(lString);
		}
		panelSign.signStopped(success);

	}
	
	
	// Controlla la validita' del certificato del 
	// firmatario rispetto al'identit� dell'utente
	private ValidationInfos preSignFunction(AbstractSigner signer) throws SmartCardAuthorizationException{
		ValidationInfos authorizationInfos = new ValidationInfos();

		String wsAuth = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_ENVELOPEWSAUTH );
		if (wsAuth!=null && !"".equals(wsAuth.trim())){ 
			try{
				URL endPoint = new URL(wsAuth); 
				X509Certificate[] certificates = signer.getSigningCertificates(pin);
				if (certificates==null || certificates.length==0){
					throw new SmartCardException( Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_NOSIGNINGCERT ));
				}
				Base64Encoder encoder = new Base64Encoder();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] certificateEncoded = certificates[0].getEncoded();
				encoder.encode(certificateEncoded, 0, certificateEncoded.length, baos);
				String certificateEncodedB64 = new String(baos.toByteArray());

				String request = String.format(wsPostPrototype, certificateEncodedB64);

				HttpURLConnection connection= (HttpURLConnection)endPoint.openConnection();
				connection.setRequestProperty("SOAPAction", "");
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				PrintWriter out = new PrintWriter(connection.getOutputStream());
				out.print(request);
				out.close();
				InputStream is = null;
				Document document = null;
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				try{
					connection.connect();
					is = connection.getInputStream();
					document = documentBuilder.parse(is);
				} finally{
					if (is!=null)
						is.close();
				}

				Element root = document.getDocumentElement();
				NodeList authorizeReturnList = root.getElementsByTagName("authorizeReturn");
				if (authorizeReturnList==null || authorizeReturnList.getLength()!=1)
					throw new  SmartCardAuthorizationException( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_NOAUTHTOSIGN ) );
				Element authorizeReturnElement = (Element)authorizeReturnList.item(0);
				NodeList authorizationInfosList = authorizeReturnElement.getElementsByTagName("authorizationInfos");
				if (authorizationInfosList==null || authorizationInfosList.getLength()!=1)
					throw new SmartCardAuthorizationException( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_NOAUTHTOSIGN  ) );
				Element authorizationInfosElement = (Element)authorizationInfosList.item(0);
				authorizationInfos = parseAuthorizationInfos(authorizationInfosElement);

			} catch(Exception e){
				throw new SmartCardAuthorizationException( e.getMessage() );
			}
		}
		return authorizationInfos;	
	}
	
	/*
	 * La struttura XML della response del WS e' la seguente:
	 *  <authorizationInfos>
     *    <errors>
     *       <errors>Errore 1</errors>
     *       <errors>Errore 2</errors>
     *     </errors>
     *     <valid>false</valid>
     *     <warnings>
     *       <warnings>Warning 1</warnings>
     *       <warnings>Warning 2</warnings>
     *     </warnings>
     *   </authorizationInfos>
	 */
	 private ValidationInfos parseAuthorizationInfos(
			Element authorizationInfosElement) throws SmartCardAuthorizationException{
		ValidationInfos validationInfos = new ValidationInfos();
		Element errorsElement = getChildByTagName(authorizationInfosElement, "errors");
		Element warningsElement = getChildByTagName(authorizationInfosElement, "warnings");
		
		// Aggiungo gli errori
		if (errorsElement!=null){
			NodeList errorsList = errorsElement.getElementsByTagName("errors");
			if (errorsList!=null && errorsList.getLength()!=0){
				for (int i=0; i<errorsList.getLength(); i++){
					Element errorElement = (Element)errorsList.item(i);
					validationInfos.addError(errorElement.getNodeValue());
				}
			}
		}
		
		// Aggiungo i warning
		if (warningsElement!=null){
			NodeList warningsList = errorsElement.getElementsByTagName("warnings");
			if (warningsList!=null && warningsList.getLength()!=0){
				for (int i=0; i<warningsList.getLength(); i++){
					Element warningElment = (Element)warningsList.item(i);
					validationInfos.addWarning(warningElment.getNodeValue());
				}
			}
		}
		
		return validationInfos;
	}

	public String convertStreamToString(InputStream is)
     throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}
	
	public Element getChildByTagName(Element parent, String name) {
	    for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
	      if (child instanceof Element && name.equals(child.getNodeName())) {
	    	  return (Element) child;
	      }
	    }
	    return null;
	}
	
	
}