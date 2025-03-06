package it.eng.client.applet.fileProvider;

import java.applet.AppletContext;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JApplet;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;
import it.eng.common.json.JSONObject;
import it.eng.fileOperation.clientws.DnType;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;
import netscape.javascript.JSObject;

public class AurigaMultiHashFileOutputProvider implements FileOutputProvider {

	private String outputUrl;
	private String idSelected;
	private String callBack;
	private String cookie;
	private AppletContext appletContext;
	private String callBackStart;
	private JApplet applet;
	
	private boolean autoClosePostSign = false;
	private String callBackAskForClose = null;

	
	@Override
	public FileOutputProviderOperationResultEnum saveOutputFile(String id, InputStream in, String fileInputName, String tipoBusta, X509Certificate certificatoDiFirma, String... params) throws Exception {
		LogWriter.writeLog("Metodo saveOutputFile");
		FileOutputProviderOperationResultEnum operationResult = FileOutputProviderOperationResultEnum.OK;
		if (getOutputUrl() != null) {
			CloseableHttpClient lDefaultHttpClient = ProxyDefaultHttpClient.getClientToUse();
			CloseableHttpResponse response = null;
			String lStringOriginalFileName = fileInputName;
			File lFileOut;
			
			try {
				HttpPost request = new HttpPost(getOutputUrl());
				RequestConfig config = RequestConfig.custom().build();
				lFileOut = File.createTempFile("fileTemp", ".tmp");
				LogWriter.writeLog("idDoc " + params[0]);

				List<NameValuePair> lListParames = new ArrayList<NameValuePair>();
				if(params[0]!=null){
					LogWriter.writeLog("idDoc " + params[0]);
					lListParames.add(new BasicNameValuePair("idDoc", params[0]));
				}
				if(params[1]!=null){
					lListParames.add(new BasicNameValuePair("signedBean", params[1]));
				}
				if(params[2]!=null){
					LogWriter.writeLog("firmatario " + params[2]);
					lListParames.add(new BasicNameValuePair("firmatario", params[2]));
				}
				if(params[3]!=null){
					LogWriter.writeLog("pathFileTemp " + params[3]);
					lListParames.add(new BasicNameValuePair("pathFileTemp", params[3]));
				}
				if(params[4]!=null){
					LogWriter.writeLog("versioneDoc " + params[4]);
					lListParames.add(new BasicNameValuePair("versioneDoc", params[4]));
				}
				if(params[5]!=null){
					LogWriter.writeLog("firmaPresente " + params[5]);
					lListParames.add(new BasicNameValuePair("firmaPresente", params[5]));
				}
				if(params[6]!=null){
					LogWriter.writeLog("firmaValida " + params[6]);
					lListParames.add(new BasicNameValuePair("firmaValida", params[6]));
				}
				if(params[7]!=null){
					LogWriter.writeLog("modalitaFirma " + params[7]);
					lListParames.add(new BasicNameValuePair("modalitaFirma", params[7]));
				}
				if (params[8] != null) {
					LogWriter.writeLog("subjectX500Principal " + params[8]);
					lListParames.add(new BasicNameValuePair("subjectX500Principal", params[8]));
				}
				
				HttpUriRequest upload = RequestBuilder.post().setUri(new URI(getOutputUrl())).addParameters(lListParames.toArray(new NameValuePair[]{})).build();
				
				String result = null;
				BufferedReader br = null;
				response = lDefaultHttpClient.execute(upload);
				System.out.println(response.getStatusLine());
				StringBuilder sb = new StringBuilder();
				String line;
				br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				result = sb.toString();
				if (response.getStatusLine().getStatusCode() != 200) {
					LogWriter.writeLog("Il server ha risposto: " + response.getStatusLine() + ": " + result);
					if ("VERIFICA_CONFORMITA_CERTIFICATO_FIRMA_ERROR".equalsIgnoreCase(result)) {
						operationResult = FileOutputProviderOperationResultEnum.VERIFICA_COERENZA_CERTIFICATO_ERROR;
					} else {
						operationResult = FileOutputProviderOperationResultEnum.FILE_UPLOAD_ERROR;
					}
				} else {
					// Aggiungo le informazioni del certificato nel result
					Map<String, String> mappaAttributiCertificatoFirma = getMappaAttributiCertificatoDiFirma(certificatoDiFirma);
					JSONObject attributiCertificatoFirmaJso = new JSONObject(mappaAttributiCertificatoFirma);
					String attributiCertificatoFirmaStr = attributiCertificatoFirmaJso.toString();
					result = result + "#" + attributiCertificatoFirmaStr;
					operationResult = FileOutputProviderOperationResultEnum.OK;
					if (callBack != null && !callBack.equals("")) {
						String lStrToInvoke = "javascript:" + callBack + "('" + result + "');";
						JSObject.getWindow(applet).eval(lStrToInvoke);
					}
				}
			} catch (Exception e) {
				operationResult = FileOutputProviderOperationResultEnum.FILE_UPLOAD_ERROR;
				LogWriter.writeLog("Errore", e);
			} finally {
				if (response != null) {
					response.close();
				}
			}
		}
		return operationResult;
	}

	@Override
	public void saveOutputParameter(JApplet applet) throws Exception {
		String urlOutput = null;
		try {
			urlOutput = PreferenceManager.getString( PreferenceKeys.PROPERTY_OUTPUTURL );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_OUTPUTURL + ": " + urlOutput);
			setOutputUrl( urlOutput );

			if( applet!=null ){		
				try {
					String cookie = (String)JSObject.getWindow(applet).eval("document.cookie");
					LogWriter.writeLog("cookie " + cookie);
					setCookie( cookie );
				} catch (Exception e){

				}
				setAppletContext( applet.getAppletContext() );
			}
			idSelected = PreferenceManager.getString( PreferenceKeys.PROPERTY_IDSELECTED );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_IDSELECTED + ": " + idSelected);
			setIdSelected( idSelected );

			callBack = PreferenceManager.getString( PreferenceKeys.PROPERTY_CALLBACK );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_CALLBACK + ": " + callBack);
			setCallBack(callBack);

			callBackStart = PreferenceManager.getString( PreferenceKeys.PROPERTY_CALLBACK_START );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_CALLBACK_START + ": " + callBackStart);
			setCallBackStart(callBackStart);

			String autoClosePostSignString = PreferenceManager.getString( PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN);
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN + ": " + autoClosePostSignString);
			if( autoClosePostSignString!=null )
				autoClosePostSign  = Boolean.valueOf( autoClosePostSignString ); 

			callBackAskForClose = PreferenceManager.getString( PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE + ": " + callBackAskForClose);
			
			
			if( applet!=null ){			
				setApplet(applet);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public String getOutputUrl() {
		return outputUrl;
	}

	public void setOutputUrl(String outputUrl) {
		this.outputUrl = outputUrl;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getIdSelected() {
		return idSelected;
	}

	public void setIdSelected(String idSelected) {
		this.idSelected = idSelected;
	}

	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	public AppletContext getAppletContext() {
		return appletContext;
	}

	public void setAppletContext(AppletContext appletContext) {
		this.appletContext = appletContext;
	}

	public void setCallBackStart(String callBackStart) {
		this.callBackStart = callBackStart;
	}

	public String getCallBackStart() {
		return callBackStart;
	}

	public JApplet getApplet() {
		return applet;
	}

	public void setApplet(JApplet applet) {
		this.applet = applet;
	}

	@Override
	public boolean getAutoClosePostSign() {
		return autoClosePostSign;
	}
	
	@Override
	public String getCallBackAskForClose() {
		return callBackAskForClose;
	}
	
	private Map<String, String> getMappaAttributiCertificatoDiFirma(X509Certificate certificatoDiFirma) throws CertificateEncodingException {
		Map<String, String> mappaAttributiCertificato = new HashMap<String, String>();
		X500Name x500name = new JcaX509CertificateHolder(certificatoDiFirma).getSubject();
		Map<String, String> x509NameSubject = getX509Name(certificatoDiFirma, "Subject");
		// Creo subjectDn
		DnType subjectDn = new DnType();
		// Setto Cn
		RDN[] cnsSubject = x500name.getRDNs(BCStyle.CN);
		if (cnsSubject != null && cnsSubject.length > 0) {
			RDN cnSubject = cnsSubject[0];
			if (cnSubject != null && cnSubject.getFirst() != null) {
				mappaAttributiCertificato.put("cnSubject", IETFUtils.valueToString(cnSubject.getFirst().getValue()));
			} else {
				mappaAttributiCertificato.put("cnSubject", x509NameSubject.get("CN"));
			}
		}
		// Setto serial number
		RDN[] serialNumbersSubject = x500name.getRDNs(BCStyle.SERIALNUMBER);
		if (serialNumbersSubject != null && serialNumbersSubject.length > 0) {
			RDN serialNumberSubject = serialNumbersSubject[0];
			if (serialNumberSubject != null && serialNumberSubject.getFirst() != null) {
				mappaAttributiCertificato.put("serialNumberSubject",IETFUtils.valueToString(serialNumberSubject.getFirst().getValue()));
			} else {
				mappaAttributiCertificato.put("serialNumberSubject", x509NameSubject.get("SERIALNUMBER"));
			}
		}
		// Setto givenName
		RDN[] givenNamesSubject = x500name.getRDNs(BCStyle.GIVENNAME);
		if (givenNamesSubject != null && givenNamesSubject.length > 0) {
			RDN givenNameSubject = givenNamesSubject[0];
			if (givenNameSubject != null && givenNameSubject.getFirst() != null) {
				subjectDn.setGivenName(IETFUtils.valueToString(givenNameSubject.getFirst().getValue()));
				mappaAttributiCertificato.put("givenNameSubject", IETFUtils.valueToString(givenNameSubject.getFirst().getValue()));
			} else {
				mappaAttributiCertificato.put("givenNameSubject", x509NameSubject.get("GIVENNAME"));
			}
		}
		// Setto Dn
		RDN[] dnsSubject = x500name.getRDNs(BCStyle.DN_QUALIFIER);
		if (dnsSubject != null && dnsSubject.length > 0) {
			RDN dnSubject = dnsSubject[0];
			if (dnSubject != null && dnSubject.getFirst() != null) {
				mappaAttributiCertificato.put("dnSubject", IETFUtils.valueToString(dnSubject.getFirst().getValue()));
			} else {
				mappaAttributiCertificato.put("dnSubject", x509NameSubject.get("DN"));
			}
		}
		// Setto surname
		RDN[] surnamesSubject = x500name.getRDNs(BCStyle.SURNAME);
		if (surnamesSubject != null && surnamesSubject.length > 0) {
			RDN surnameSubject = surnamesSubject[0];
			if (surnameSubject != null && surnameSubject.getFirst() != null) {
				mappaAttributiCertificato.put("surnameSubject", IETFUtils.valueToString(surnameSubject.getFirst().getValue()));
			} else {
				mappaAttributiCertificato.put("surnameSubject", x509NameSubject.get("SURNAME"));
			}
		}
		// Setto ou
		RDN[] ousSubject = x500name.getRDNs(BCStyle.OU);
		if (ousSubject != null && ousSubject.length > 0) {
			RDN ouSubject = ousSubject[0];
			if (ouSubject != null && ouSubject.getFirst() != null) {
				mappaAttributiCertificato.put("ouSubject", IETFUtils.valueToString(ouSubject.getFirst().getValue()));
			}
		}
		// Setto o
		RDN[] osSubject = x500name.getRDNs(BCStyle.O);
		if (osSubject != null && osSubject.length > 0) {
			RDN oSubject = osSubject[0];
			if (oSubject != null && oSubject.getFirst() != null) {
				subjectDn.setO(IETFUtils.valueToString(oSubject.getFirst().getValue()));
				mappaAttributiCertificato.put("oSubject", IETFUtils.valueToString(oSubject.getFirst().getValue()));
			}
		}
		// Setto c
		RDN[] csSubject = x500name.getRDNs(BCStyle.C);
		if (csSubject != null && csSubject.length > 0) {
			RDN cSubject = csSubject[0];
			if (cSubject != null && cSubject.getFirst() != null) {
				mappaAttributiCertificato.put("cSubject", IETFUtils.valueToString(cSubject.getFirst().getValue()));
			}
		}
		// Setto issuerDn
		DnType issuerDn = new DnType();
		X500Name x500nameIssuer = new JcaX509CertificateHolder(certificatoDiFirma).getIssuer();
		RDN[] osIssuer = x500nameIssuer.getRDNs(BCStyle.O);
		if (osIssuer != null && osIssuer.length > 0) {
			RDN oIssuer = osIssuer[0];
			if (oIssuer != null && oIssuer.getFirst() != null) {
				mappaAttributiCertificato.put("oIssuer", IETFUtils.valueToString(oIssuer.getFirst().getValue()));
			}
		}
		if (certificatoDiFirma.getNotBefore() != null) {
			mappaAttributiCertificato.put("notBefore", certificatoDiFirma.getNotBefore().getTime() + "");
		}
		if (certificatoDiFirma.getNotAfter() != null) {
			mappaAttributiCertificato.put("notAfter", certificatoDiFirma.getNotAfter().getTime() + "");
			
		}
		if (certificatoDiFirma.getSerialNumber() != null) {
			mappaAttributiCertificato.put("serialNumber", certificatoDiFirma.getSerialNumber().toString());
		}
		List<String> keyUsageList = getKeyUsage(certificatoDiFirma);
		mappaAttributiCertificato.put("keyUsage", keyUsageList.toString());
		return mappaAttributiCertificato;
	}

	public static Map<String, String> getX509Name(X509Certificate certificato, String tipo) {
		Map<String, String> X500Name = new HashMap<String, String>();
		Certificate xc509;
		try {
			xc509 = getX509CertificateStructure(certificato);
			org.bouncycastle.asn1.x500.X500Name _aName = null;
			if (tipo != null && tipo.equalsIgnoreCase("Subject")) {
				_aName = xc509.getSubject();
			}
			if (tipo != null && tipo.equalsIgnoreCase("Issuer")) {
				_aName = xc509.getIssuer();
			}
			if (_aName != null) {
				ASN1ObjectIdentifier[] oidv = _aName.getAttributeTypes();
				RDN[] values = _aName.getRDNs();
				for (int i = 0; i < oidv.length; i++) {
					RDN cn = values[i];
					AttributeTypeAndValue[] cns = cn.getTypesAndValues();
					for(AttributeTypeAndValue x : cns){
						String attributeId=BCStyle.CN.getId();
						if (BCStyle.C.equals(x.getType())) {
				            attributeId = "C";
				        } else if (BCStyle.O.equals(x.getType())) {
				            attributeId = "O";
				        } else if (BCStyle.OU.equals(x.getType())) {
				            attributeId = "OU";
				        } else if (BCStyle.DN_QUALIFIER.equals(x.getType())) {
				            attributeId = "DN_QUALIFIER";
				        } else if (BCStyle.ST.equals(x)) {
				            attributeId = "ST";
				        } else if (BCStyle.L.equals(x.getType())) {
				            attributeId = "L";
				        } else if (BCStyle.CN.equals(x.getType())) {
				            attributeId = "CN";
				        } else if (BCStyle.SN.equals(x.getType())) {
				            attributeId = "SN";
				        } else if (BCStyle.DC.equals(x.getType())) {
				            attributeId = "DC";
				        }
						X500Name.put(attributeId, x.getValue().toString());
					}
				}
			}
		} catch (Exception e) {
			LogWriter.writeLog("Eccezione getX509Name", e);
		}
		return X500Name;
	}
	
	private static Certificate getX509CertificateStructure(X509Certificate certificato) throws CertificateEncodingException, IOException {
		byte[] derdata = certificato.getEncoded();
		ByteArrayInputStream as = new ByteArrayInputStream(derdata);
		ASN1InputStream aderin = new ASN1InputStream(as);
		ASN1Primitive ado = aderin.readObject();
		Certificate xc509 = Certificate.getInstance(ado);
		return xc509;
	}
	
	public static List<String> getKeyUsage(X509Certificate certificato) {

		List<String> keyUsages = new ArrayList<String>();

		Extensions xc509Ext = getX509Extensions(certificato);

		if (xc509Ext != null) {

			KeyUsage ku = KeyUsage.fromExtensions(xc509Ext);

			if (ku != null) {
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.digitalSignature) != 0)
					keyUsages.add("digitalSignature");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.nonRepudiation) != 0)
					keyUsages.add("nonRepudiation");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.keyEncipherment) != 0)
					keyUsages.add("keyEncipherment");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.dataEncipherment) != 0)
					keyUsages.add("dataEncipherment");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.keyAgreement) != 0)
					keyUsages.add("keyAgreement");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.keyCertSign) != 0)
					keyUsages.add("keyCertSign");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.cRLSign) != 0)
					keyUsages.add("cRLSign");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.encipherOnly) != 0)
					keyUsages.add("encipherOnly");
				if ((((DERBitString) ku.toASN1Primitive()).intValue() & KeyUsage.decipherOnly) != 0)
					keyUsages.add("decipherOnly");
			}
		}
		return keyUsages;
	}
	
	private static Extensions getX509Extensions(X509Certificate certificato) {
		Certificate xc509;
		try {
			xc509 = getX509CertificateStructure(certificato);
			return xc509.getTBSCertificate().getExtensions();
		} catch (CertificateEncodingException e) {
			LogWriter.writeLog("Eccezione getX509Extensions", e);
		} catch (IOException e) {
			LogWriter.writeLog("Eccezione getX509Extensions", e);
		}
		return null;
	}

}
