package test;



public class PDFSignTest {

//	public byte[] SignerHash(PrivateKey key, X509Certificate certificate,byte[] input,String provider) throws Exception {
//		byte[] fout = null;
//		try{
//            List certList = new ArrayList();
//            certList.add(certificate);
//            CertStore certs = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
//            
//            
//            CMSSignedHashGenerator gen = new CMSSignedHashGenerator(CMSSignedDataGenerator.DIGEST_SHA1);
//            
//            gen.addSigner(key, certificate, CMSSignedDataGenerator.DIGEST_SHA1);
//            
//            //CMSSignedDataGenerator.ENCRYPTION_RSA
//            
//            
//            SignerObjectBean bean = gen.generate(DigestUtils.sha(input), Security.getProvider(provider));
//                
//            //Serializzo il bean
//            byte[] str = SerializationUtils.serialize(bean);
//            
//            String strs = Base64.encodeBase64String(str);
//                                  
//            byte[] yy = Base64.decodeBase64(strs); 
//            
//            //Deserializzo il bean
//            SignerObjectBean bean2 = (SignerObjectBean)SerializationUtils.deserialize(yy);
//            
//            CMSSignedData data = signedData(bean2, input);
//            
//            fout = data.getEncoded();
// 		}catch(Exception e){
//			throw new Exception(e); 
//		}
//		return fout;
//	}
//	
//	
//	public byte[] SignerHashPdf(PrivateKey key, X509Certificate certificate,byte[] input,String provider) throws Exception {
//		try{
//	        // reader and stamper
//	        PdfReader reader = new PdfReader(input);
//	        ByteArrayOutputStream fout = new ByteArrayOutputStream();
//	        PdfStamper stp = PdfStamper.createSignature(reader, fout, '\0');
//	        PdfSignatureAppearance sap = stp.getSignatureAppearance();
//	        sap.setCrypto(key, new Certificate[]{certificate}, null, PdfSignatureAppearance.SELF_SIGNED);
//	        sap.setReason("I'm the author");
//	        sap.setLocation("Lisbon");
//	        // comment next line to have an invisible signature
//	        sap.setVisibleSignature(new Rectangle(100, 100, 200, 200), 1, null);
//	        sap.setExternalDigest(new byte[128], null, "RSA");
//	        sap.preClose();
//	        PdfPKCS7 sig = sap.getSigStandard().getSigner();
//	        Signature sign = Signature.getInstance("SHA1withRSA");
//	        sign.initSign(key);
//	        byte buf[] = new byte[8192];
//	        int n;
//	        InputStream inp = sap.getRangeStream();
//	        while ((n = inp.read(buf)) > 0) {
//	            sign.update(buf, 0, n);
//	        }
//	        sig.setExternalDigest(sign.sign(), null, "RSA");
//	        PdfDictionary dic = new PdfDictionary();
//	        dic.put(PdfName.CONTENTS, new PdfString(sig.getEncodedPKCS1()).setHexWriting(true));
//	        sap.close(dic);
//           
//            return fout.toByteArray();
// 		}catch(Exception e){
//			throw new Exception(e); 
//		}
//		
//	}
//	
//	
//	
//	public CMSSignedData signedData(SignerObjectBean bean,byte[] contentByte) throws Exception{
//		ASN1Set certificates = null;
//		
//		List _certs = new ArrayList();
//		List certList = new ArrayList();
//        for(X509Certificate certificate:bean.getCertificates()){
//			certList.add(certificate);
//		}
//		CertStore certStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
//		_certs.addAll(CMSUtils.getCertificatesFromStore(certStore));
//		
//        if (_certs.size() != 0)
//        {
//            certificates = CMSUtils.createBerSetFromList(_certs);
//        }
//
//        ASN1Set certrevlist = null;
//        
//        
//        ByteArrayOutputStream   bOut = new ByteArrayOutputStream();
//        
//        CMSProcessableByteArray content = new CMSProcessableByteArray(contentByte);
//        ASN1OctetString octs = null;
//        
//	    if (content != null)
//	            {
//	                try
//	                {
//	                    content.write(bOut);
//	                }
//	                catch (IOException e)
//	                {
//	                    throw new CMSException("encapsulation error.", e);
//	                }
//	            }
//	
//	            octs = new BERConstructedOctetString(bOut.toByteArray());
//        
//
//        ContentInfo encInfo = new ContentInfo(CMSObjectIdentifiers.data, octs);
//        
//        DERSet digest = (DERSet)new ASN1InputStream(bean.getDigestAlgs()).readObject();
//        DERSet signerInfos = (DERSet)new ASN1InputStream(bean.getSignerInfo()).readObject();
//        
//        
//        SignedData  sd = new SignedData(
//        						 digest,
//                                 encInfo, 
//                                 certificates, 
//                                 certrevlist, 
//                                 signerInfos);
//
//        ContentInfo contentInfo = new ContentInfo(CMSObjectIdentifiers.signedData, sd);
//        return new CMSSignedData(content, contentInfo);
//	}
//	
//	public CMSSignedData signedData(SignerObjectBean bean) throws Exception{
//		ASN1Set certificates = null;
//		
//		List _certs = new ArrayList();
//		List certList = new ArrayList();
//        for(X509Certificate certificate:bean.getCertificates()){
//			certList.add(certificate);
//		}
//		CertStore certStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
//		_certs.addAll(CMSUtils.getCertificatesFromStore(certStore));
//		
//        if (_certs.size() != 0)
//        {
//            certificates = CMSUtils.createBerSetFromList(_certs);
//        }
//
//        ASN1Set certrevlist = null;
//        ASN1OctetString octs = null;
//        CMSProcessable content = null;
//
//        ContentInfo encInfo = new ContentInfo(CMSObjectIdentifiers.data, octs);
//        
//        DERSet digest = (DERSet)new ASN1InputStream(bean.getDigestAlgs()).readObject();
//        DERSet signerInfos = (DERSet)new ASN1InputStream(bean.getSignerInfo()).readObject();
//                
//        SignedData  sd = new SignedData(
//        						 digest,
//                                 encInfo, 
//                                 certificates, 
//                                 certrevlist, 
//                                 signerInfos);
//
//        ContentInfo contentInfo = new ContentInfo(CMSObjectIdentifiers.signedData, sd);
//        return new CMSSignedData(content, contentInfo);
//	}
//	
//	public byte[] signerP7M(PrivateKey key, X509Certificate certificate, byte[] input,String provider) throws Exception {
//		byte[] fout = null;
//		try{
//            
//			
//			List crl = new ArrayList();
//			
//			List certList = new ArrayList();
//           
//            certList.add(certificate);
//            CertStore certs = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
//            
//            
//            Util util = new Util();
//            crl.add(util.getCRL(certificate));
//            CertStore certs2 = CertStore.getInstance("Collection", new CollectionCertStoreParameters(crl), "BC"); 
//            
//            CMSProcessable msg = new CMSProcessableByteArray(input);
//            
//            CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
//                    
//            String alg = CMSSignedDataGenerator.DIGEST_SHA1;
//                              
//            gen.addSigner(key, certificate, alg);
//            	            
//            gen.addCertificatesAndCRLs(certs);
//            gen.addCertificatesAndCRLs(certs2);
//            
//            
//            CMSSignedData signedData = gen.generate(msg, true, provider);
//            
//            fout = signedData.getEncoded();
//		}catch(Exception e){
//			throw new Exception(e); 
//		}
//		return fout;
//	}
//	
//	
//	
//	public void loadAllCertificate(char[] pin) throws Exception{
//	
//		boolean isLoad = false;
//		//Carico i provider
//		String dir = "c:\\Temp";
//		File tmp = new File(dir);
//		File directory = new File(tmp+File.separator+"dllCrypto");
//		File[] dll = directory.listFiles();
//		AuthProvider pkcs11Provider = null;
//	 	KeyStore keyStore = null;
//	 	 PKCS11.loadNative();
//	 	for(int i=0;i<dll.length;i++){
//	 		try{
//	 			
//		    	StringBuffer cardConfig=new StringBuffer();
//		        cardConfig.append ("name=SmartCards \n");
//		        cardConfig.append ("library="+dll[i].getAbsolutePath()+" \n");
//		        
//		        
//		        ByteArrayInputStream confStream = new ByteArrayInputStream(cardConfig.toString().getBytes());
//		               
//		        // Create the provider
//		        Class sunPkcs11Class = Class.forName("sun.security.pkcs11.SunPKCS11");
//		        Constructor pkcs11Constr = sunPkcs11Class.getConstructor(java.io.InputStream.class);
//		        
//		        Object obj = pkcs11Constr.newInstance(confStream);
//		        
//		        
//		     // TextCallbackHandler prompts and reads the command line for 
//		     // name and password information.
//		     //CallbackHandler cmdLineHdlr = new com.sun.security.auth.callback.TextCallbackHandler();
//		     //KeyStore.Builder ksBuilder = KeyStore.Builder.getInstance("PKCS11", null,  new KeyStore.CallbackHandlerProtection(cmdLineHdlr));
//
//		     pkcs11Provider = (AuthProvider)obj;
//		        
//		     //Subject sub = new Subject();
//		     //sub.getPrivateCredentials().add("11433".toCharArray());
//		     //pkcs11Provider.login(sub, new MyCallback());
//		     
//		     KeyStore.Builder ksBuileder = KeyStore.Builder.newInstance( "PKCS11", pkcs11Provider, new KeyStore.PasswordProtection("11433".toCharArray()));
//		     
//		     KeyStore ks = ksBuileder.getKeyStore();
//			
//		     System.out.println(((PasswordProtection)ksBuileder.getProtectionParameter("")).getPassword());
//		     
//		     System.out.println(ksBuileder.getKeyStore().size());
//		     
//		     //String alias = "Authentication";
//			//PrivateKey privKey = (PrivateKey) ks.getKey(alias, null);
//		    		
//			//System.out.println(ks.aliases().nextElement());
//			
//		     //KeyStore ks = KeyStore.getInstance("PKCS11",pkcs11Provider);
//		     //ks.load(null, "46303".toCharArray());
//		     	
//		     //System.out.println(ks.aliases().hasMoreElements());
//		     
//		     
//		     
//		     
//		     
//		     
//		     
//		     Enumeration eumerat = ks.aliases();
//		     while(eumerat.hasMoreElements()){
//		    	 System.out.println(eumerat.nextElement());
//		     }
//		     
//		     pkcs11Provider.logout();
//		        
//
////		                
////		        Iterator<String> itera = pkcs11Provider.stringPropertyNames().iterator();
////		        while(itera.hasNext()){
////		        	String key = itera.next();
////		        	System.out.println(key+":"+pkcs11Provider.getProperty(key));
////		        }        
////        
////		        Subject sub = new Subject();
////		        sub.getPrivateCredentials().add("11433".toCharArray());
////		        pkcs11Provider.login(sub, new MyCallback());
////		        
////		        
////		        KeyStore store = KeyStore.getInstance("PKCS11", pkcs11Provider);
////		        store.load(null, "11433".toCharArray());
////		        
////		        String ksloc = "PIPPPO";
////		        KeyStore fromKS = KeyStore.getInstance("JKS",pkcs11Provider);
////		        BufferedInputStream bin = new BufferedInputStream(new ByteArrayInputStream(ksloc.getBytes()));
////	            fromKS.load(null, "46303".toCharArray());
////		        
////	            System.out.println("ALIASSSSS::"+fromKS.aliases());
////	            
////	            System.out.println("CERT:"+fromKS.getCertificateChain("CNS0"));
////		        
////		        
////		        
////		        
////		        System.out.println(pkcs11Provider.keys());
////		        
////		        Iterator<Service> set = pkcs11Provider.getServices().iterator();
////		        while(set.hasNext()){
////		        	System.out.println(set.next());
////		        }
////		        
////		        
////		        
////		        //Service serv = pkcs11Provider.getService("RSA", "SHA256");
////		        
////		        //System.out.println(serv); 
////		        
////		        System.out.println("ARRIVO QUIII");
////		           
////			 	//keyStore = KeyStore.getInstance("Signature",pkcs11Provider);
////			 			 	
////		        //keyStore.load(null, "46303".toCharArray());
////		        
////		        
////		        
//////		        KeyStore keyStore2 = KeyStore.getInstance("PKCS11",pkcs11Provider.getName());
//////		        
//////		        keyStore2.load(null, "46303".toCharArray());
////		        
//////		        Subject sub2 = new Subject();
//////		        sub2.getPrivateCredentials().add("46303".toCharArray());
//////		        pkcs11Provider.login(sub2, new MyCallback2());
////		        
////		        Security.addProvider(pkcs11Provider); 
////		        KeyStore store2 = KeyStore.getInstance("PKCS11");
////		        ByteArrayOutputStream out = new ByteArrayOutputStream();
////		        
////		        store2.load(null, "46303".toCharArray());
////		        
////		        store2.store(out, "46303".toCharArray());
////		        
////		        //Signature sign = Signature.getInstance("SHA256withRSA",pkcs11Provider);
////		                
////		        //byte[] ss = sign.sign();
////		        
////		        
////		        //System.out.println(ss);
////		        
////		        
////		        System.out.println("OUTTT:"+out);
////		        
////		        //store2.load(null, "46303".toCharArray());
////		        
////		        //System.out.println(store2.aliases().hasMoreElements());
////		        System.out.println(store2.size());
////		        
////		        
////		        
////		        
////		        
////		        //System.out.println(keyStore2.size());
////		        
////		        
////		        
////		        //System.out.println(keyStore.size());
////		        
////		        isLoad = true;
////		        
////		       
////		        break;
//		 	}catch(Exception e){
//		 		e.printStackTrace();
//		 	}
//	 	}	
//	 	if(!isLoad){
//	 		throw new Exception("Errore loading Certificati!");
//	 		
//	 	}
//	 	System.out.println("CERTIFICATO CARICATO!");
//	 	
// 	 }
//	
//	
//	
//	
//	
//	public byte[] newSignerPDF(PrivateKey key, X509Certificate certificate,byte[] hash,String provider) throws Exception{
//			
//		
//		Calendar cal = Calendar.getInstance();
//		PdfPKCS7 pk7 = new PdfPKCS7(key, new Certificate[]{certificate}, null, "SHA1", provider, false);
//		byte sh[] = pk7.getAuthenticatedAttributeBytes(hash, cal,null);
//		pk7.update(sh, 0, sh.length);
//		byte sg[] = pk7.getEncodedPKCS7(hash, cal);
//		
//		return sg;
//	}
//	
//	
//	public byte[] createPDF(byte[] sg,PdfSignatureAppearance sap,ByteArrayOutputStream fout) throws Exception{
//			
//		PdfDictionary dic2 = new PdfDictionary();
//		byte out[] = new byte[0x2500 / 2];
//		System.arraycopy(sg, 0, out, 0, sg.length);
//		dic2.put(PdfName.CONTENTS, new PdfString(out).setHexWriting(true));
//		sap.close(dic2);
//		
//		
//		
//		return fout.toByteArray();
//	}
//	
//	
//	
//	public PdfSignatureAppearance getHash(byte[] input,ByteArrayOutputStream fout) throws Exception{
//		PdfReader reader = new PdfReader(input);
//		PdfStamper stp = PdfStamper.createSignature(reader, fout, '\0');
//		PdfSignatureAppearance sap = stp.getSignatureAppearance();
//				
//		// comment next line to have an invisible signature
//		sap.setVisibleSignature(new Rectangle(100, 100, 200, 200), 1, null);
//		sap.setLayer2Text("This is some custom made text.\n\nDate: some date");
//		Calendar cal = Calendar.getInstance();
//		PdfDictionary dic = new PdfDictionary();
//		dic.put(PdfName.FT, PdfName.SIG);
//		dic.put(PdfName.FILTER,PdfName.ADOBE_PPKLITE);
//		dic.put(PdfName.SUBFILTER, PdfName.ADBE_PKCS7_DETACHED);
//		dic.put(PdfName.M, new PdfDate(cal));
//		//dic.put(PdfName.NAME, new PdfString(PdfPKCS7.getSubjectFields(certificate).getField("CN")));
//		sap.setCryptoDictionary(dic);
//		HashMap exc = new HashMap();
//		exc.put(PdfName.CONTENTS, new Integer(0x2502));
//		sap.preClose(exc);
//		
//		return sap;
//	}
//	
//	
//	 public void signerfile(byte[] in,char[] pin,String provider,String signType) throws Exception {
//		  	
//		   String dir = "c:\\Temp";
//		 	//Carico i provider
//			File tmp = new File(dir);
//			File directory = new File(tmp+File.separator+"dllCrypto");
//		 	File[] dll = directory.listFiles();
//	 	
//		 	KeyStore keyStore = KeyStore.getInstance("PKCS11",provider);
//		 	keyStore.load(null, pin);		 	
//		 	System.out.println(keyStore.getProvider());    
//		 	keyStore.load(null, "46303".toCharArray());
//		 	
//	        // Get the first private key in the keystore:
//	        PrivateKey privateKey;
//	        
//	        Enumeration enumeration = keyStore.aliases();
//	        
//	        
//	        
//	        System.out.println(keyStore.getProvider());
//	        
//	        while(enumeration.hasMoreElements()){
//	        	System.out.println("QUIIIIIII");
//	        	String alias = enumeration.nextElement().toString();
//            
//	        	privateKey = (PrivateKey)keyStore.getKey(alias, null); 
//	        
//	            X509Certificate certificate=(X509Certificate)keyStore.getCertificate(alias);
//	            
//	            //controllo se e' un certificato di firma
//	            
//	            boolean[] usage = certificate.getKeyUsage();
//	            
//	            for(boolean h : usage){
//	            	String us = "";
//	            	
//	            	if(h){
//	            		us+="1";
//	            	}else{
//	            		us+="0";
//	            	}
//	            	
//	            }
//	            	            
//	            
//	            //Come da normativa deve contenere il flag di noRepudiation a true per essere un certificato di firma valido
//	            //String controlAll = System.getProperty(Constants.ALL_CERTIFICATE_USE);
//	            //if(usage[1] || controlAll!=null){
//	            	//SignerFileObject ret = new SignerFileObject();   			
//	        			//if(signType.equals("PDF")){
//	        				try {
//		        				//Controllo se e' effettivamente un file pdf
//	        					
//	        					
////	        					Factory fact = new Factory(DigestAlgorithm.SHA1, SignerType.PDF);
////	        					
////	        					//fact.signerfile(file, pin, provider);
////	        					
////	        					
////	        					ByteArrayOutputStream out = new ByteArrayOutputStream();
////	        					PdfSignatureAppearance sap = getHash(in,out);
////	        					    					
////	        					byte[] retr = this.newSignerPDF(privateKey, certificate, DigestUtils.sha(sap.getRangeStream()),provider);
//	        					 			
//	        					
//	        					byte[] ret = signerP7M(privateKey, certificate,in,provider);
//	        					  					  					
//	        					
//	        					System.out.println(ret.length);
//	        					
//	        					File fout = new File("c:\\ODG2.pdf.p7m");
//	        					
//	        					FileUtils.writeByteArrayToFile(fout, ret);
//	        					
//	        					System.out.println("FILE FIRMATO");
//	        				}catch (Exception e) {
//	        					
//	        					e.printStackTrace();
//	        				}
//	        			}
//	            //}		
//
//	        			
//	            //return signerXML(privateKey,certificate,in,provider);
//		        
//	        
//
//
//	 }
//	 
//	
////	public void signerPDF(PrivateKey key, X509Certificate certificate, byte[] input) throws Exception {
////
////		Certificate[] chain = new Certificate[]{certificate};
////
////		PdfReader reader = new PdfReader("c:\\Michele\\ODG.pdf");
////		FileOutputStream fout = new FileOutputStream(new File("c:\\out.pdf"));
////		PdfStamper stp = PdfStamper.createSignature(reader, fout, '\0');
////		PdfSignatureAppearance sap = stp.getSignatureAppearance();
////		sap.setCrypto(key, chain, null, PdfSignatureAppearance.SELF_SIGNED);
////
////		sap.setVisibleSignature(new Rectangle(100, 100, 200, 200), 1, null);
////		sap.setExternalDigest(null, null, "RSA");
////		sap.preClose();
////		PdfPKCS7 sig = sap.getSigStandard().getSigner();
////		Signature sign = Signature.getInstance("SHA1withRSA");
////		sign.initSign(key);
////		byte buf[] = new byte[8192];
////		int n;
////		InputStream inp = sap.getRangeStream();
////		while ((n = inp.read(buf)) > 0) {
////		    sign.update(buf, 0, n);
////		}
////		sig.setExternalDigest(sign.sign(), null, "RSA");
////		PdfDictionary dic = new PdfDictionary();
////		dic.put(PdfName.CONTENTS, new PdfString(sig.getEncodedPKCS1()).setHexWriting(true));
////		sap.close(dic);
////
////
////		
////		
////
////	}
//	
////	public static void signEncapsulated(PrivateKey key, X509Certificate certificate, byte[] input) {
////	    try {
////	        Security.addProvider(new BouncyCastleProvider());
////
////	        Certificate[] chain = new Certificate[]{certificate};
////
////	        PdfReader reader = new PdfReader(input);
////	        PdfStamper stp = PdfStamper.createSignature(reader, new FileOutputStream("c:\\out.pdf"), '\0');
////	        PdfSignatureAppearance sap = stp.getSignatureAppearance();
////	        sap.setVisibleSignature(new Rectangle(100, 100, 300, 200), 1, null);
////	        sap.setSignDate(new GregorianCalendar());
////	        sap.setCrypto(null, chain, null, null);
////	        
////	        sap.setReason("I like to sign");
////	        sap.setLocation("Universe");
////	        sap.setAcro6Layers(true);
////	        sap.setVisibleSignature(new Rectangle(100, 100, 200, 200), 1, null);
////	        PdfSignature dic = new PdfSignature(new PdfName("SHA-256"), new PdfName("SHA-256"));
////	        dic.setDate(new PdfDate(sap.getSignDate()));
////	        dic.setName(PdfPKCS7.getSubjectFields((X509Certificate)chain[0]).getField("CN"));
////	        if (sap.getReason() != null)
////	            dic.setReason(sap.getReason());
////	        if (sap.getLocation() != null)
////	            dic.setLocation(sap.getLocation());
////	        sap.setCryptoDictionary(dic);
////	        int csize = 4000;
////	        HashMap exc = new HashMap();
////	        exc.put(PdfName.CONTENTS, new Integer(csize * 2 + 2));
////	        sap.preClose(exc);
////
//////	        MessageDigest md = MessageDigest.getInstance("SHA-256");
//////	        InputStream s = sap.getRangeStream();
//////	        int read = 0;
//////	        byte[] buff = new byte[8192];
//////	        while ((read = s.read(buff, 0, 8192)) > 0) {
//////	            md.update(buff, 0, read);
//////	        }
////
////	        CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
////	        generator.addSigner(key, (X509Certificate)chain[0], CMSSignedDataGenerator.DIGEST_SHA256);
////
////	        ArrayList list = new ArrayList();
////	        for (int i = 0; i < chain.length; i++) {
////	            list.add(chain[i]);
////	        }
////	        CertStore chainStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(list), "BC");
////	        generator.addCertificatesAndCRLs(chainStore);
////	        CMSProcessable content = new CMSProcessableByteArray(input);
////	        CMSSignedData signedData = generator.generate(content, true, "SunPKCS11-SmartCards");
////	        byte[] pk = signedData.getEncoded();
////
////	        byte[] outc = new byte[csize];
////
////	        PdfDictionary dic2 = new PdfDictionary();
////
////	        System.arraycopy(pk, 0, outc, 0, pk.length);
////
////	        dic2.put(PdfName.CONTENTS, new PdfString(outc).setHexWriting(true));
////	        sap.close(dic2);
////	    }
////	    catch (Exception e) {
////	        e.printStackTrace();
////	    }
////	}
//
////	 public void copyProvider() throws Exception{
////		 
////		 String[] names = new String[]{"bit4ipki.dll","incryptoki2.dll","eTPKCS11.dll","SI_PKCS11.dll"};
////		 File tmp = new File(dir);
////		 File directory = new File(tmp+File.separator+"dllCrypto");
////		 //directory.delete();
////		 if(!directory.exists()){
////			 directory.mkdirs();
////		 }
////		 for(int i=0;i<names.length;i++){
////			 log.debug("File:"+names[i]);
////			 File file = new File(directory.getAbsolutePath()+File.separator+names[i]);
////			 if(!file.exists()){
////				 FileOutputStream out =  new FileOutputStream(directory.getAbsolutePath()+File.separator+names[i]); 
////				 IOUtils.copy(this.getClass().getResourceAsStream("/it/eng/provider/"+names[i]), out);
////				 IOUtils.closeQuietly(out);
////			 }
////		 }
////		 log.debug("FINE COPIA PROVIDER!");
//// 	 }
//	
//	 
//	 public void signerVerifiy(byte[] signer) throws Exception{
//		 
//		 CMSSignedData signedData = new CMSSignedData(signer);
//		 
//		 Collection<SignerInformation> infos = signedData.getSignerInfos().getSigners();
//         	 
//         for(SignerInformation info:infos){
//         	AttributeTable table = info.getSignedAttributes();
//         	
//         	Hashtable hash = table.toHashtable();
//         	
//         	Iterator iter = hash.keySet().iterator();
//         	
//         	while(iter.hasNext()){
//         		System.out.println(iter.next());
//         	}
//         	
//         	System.out.println("**********************************");
//         	
//         	AttributeTable table2 = info.getUnsignedAttributes();
//         	        	
//         	Hashtable hash2 = table2.toHashtable();
//         	
//         	Iterator iter2 = hash2.keySet().iterator();
//         	
//         	while(iter2.hasNext()){
//         		
//         		DERObjectIdentifier ite = (DERObjectIdentifier)iter2.next();
//         		
//         		System.out.println(ite);
//         		
//         		if(ite.getId().equals(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken.getId())){
//         			         			
//         			Attribute my = (Attribute)hash2.get(ite);
//         			
//         			System.out.println(my.getAttrValues().getObjectAt(0));
//         			      			
//         			TimeStampToken token2 = new TimeStampToken(new CMSSignedData(my.getAttrValues().getObjectAt(0).getDERObject().getEncoded()));
//         			            			           			
//         			System.out.println(TSPAlgorithms.SHA256+"---"+new String(token2.getTimeStampInfo().getMessageImprintAlgOID()));	  			
// 			         			
//         			TimeStampRequestGenerator reqGen = new TimeStampRequestGenerator();
//         	        			
//         			Attribute my2 = (Attribute)hash.get(PKCSObjectIdentifiers.id_aa_signingCertificateV2);
//         			
//         			System.out.println(my2);
//         			
//         			CertificateFactory factorys = CertificateFactory.getInstance("X509", BouncyCastleProvider.PROVIDER_NAME);
//         			
//         			X509Certificate certs = (X509Certificate)factorys.generateCertificate(new ByteArrayInputStream(my2.getAttrValues().getObjectAt(0).getDERObject().getEncoded()));
//         			         			
//         			info.verify(certs, "BC");
//         			
//         			System.out.println(new String(info.getContentDigest()));
//         			System.out.println(new String(token2.getTimeStampInfo().getMessageImprintDigest()));
//         			         			        			
//         	        TimeStampRequest request = reqGen.generate(info.getDigestAlgOID(), info.getContentDigest());
//         			
//         	       
//         	        
//         	        TimeStampResp respr = TimeStampResp.getInstance(new ASN1InputStream(token2.getEncoded()).readObject()); 
//         	        TimeStampResponse resp = new TimeStampResponse(respr);
//         			
//         	        
//         	        
//         			try{
//         				 validate(request,token2);
//         				System.out.println("VALIDO");
//         			}catch(Exception e){
//         				System.out.println("NON VALIDO");
//         				e.printStackTrace();
//         			}
//         			
//         			
//         		}
//         		
//         	}
//         }
//		 
//		 
//		 
//	 }
//	 
//	 public static void validate(
//		        TimeStampRequest    request,TimeStampToken token)
//		        throws TSPException
//		    {
//		        TimeStampToken tok = token;
//		        
//		        if (tok != null)
//		        {
//		            TimeStampTokenInfo  tstInfo = tok.getTimeStampInfo();
//		            
//		            if (request.getNonce() != null && !request.getNonce().equals(tstInfo.getNonce()))
//		            {
//		                throw new TSPValidationException("response contains wrong nonce value.");
//		            }
//		            
////		            if (this.getStatus() != PKIStatus.GRANTED && this.getStatus() != PKIStatus.GRANTED_WITH_MODS)
////		            {
////		                throw new TSPValidationException("time stamp token found in failed request.");
////		            }
//		            
//		            System.out.println(request.getMessageImprintDigest());
//		            
//		            System.out.println(new String(request.getMessageImprintDigest()));
//		            System.out.println(new String(tstInfo.getMessageImprintDigest()));
//		            
//		            if (!Arrays.constantTimeAreEqual(request.getMessageImprintDigest(), tstInfo.getMessageImprintDigest()))
//		            {
//		                throw new TSPValidationException("response for different message imprint digest.");
//		            }
//		            
//		            if (!tstInfo.getMessageImprintAlgOID().equals(request.getMessageImprintAlgOID()))
//		            {
//		                throw new TSPValidationException("response for different message imprint algorithm.");
//		            }
//
//		            Attribute scV1 = tok.getSignedAttributes().get(PKCSObjectIdentifiers.id_aa_signingCertificate);
//		            Attribute scV2 = tok.getSignedAttributes().get(PKCSObjectIdentifiers.id_aa_signingCertificateV2);
//
//		            if (scV1 == null && scV2 == null)
//		            {
//		                throw new TSPValidationException("no signing certificate attribute present.");
//		            }
//
//		            if (scV1 != null && scV2 != null)
//		            {
//		                throw new TSPValidationException("conflicting signing certificate attributes present.");
//		            }
//
//		            if (request.getReqPolicy() != null && !request.getReqPolicy().equals(tstInfo.getPolicy()))
//		            {
//		                throw new TSPValidationException("TSA policy wrong for request.");
//		            }
//		        }
////		        else if (this.getStatus() == PKIStatus.GRANTED || this.getStatus() == PKIStatus.GRANTED_WITH_MODS)
////		        {
////		            throw new TSPValidationException("no time stamp token found and one expected.");
////		        }
//		    }
//	 
//	
//		public byte[] signerP7M(PrivateKey key, X509Certificate certificate, byte[] input,String provider) throws Exception {
//			byte[] fout = null;
//			try{
//	            List certList = new ArrayList();
//	            certList.add(certificate);
//	            CertStore certs = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
//	            
//	            CMSProcessable msg = new CMSProcessableByteArray(input);
//	            
//	            CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
//	                    
//	            String alg = CMSSignedDataGenerator.DIGEST_SHA256;
////	            if(this.digest == "SHA256"){
////	            	alg = CMSSignedDataGenerator.DIGEST_SHA256;	
////	            }
//	            
//	            //CRL
//	            Util util = new Util();
//	            X509CRL crl = util.getCRL(certificate);
//	            ASN1InputStream by = new ASN1InputStream(crl.getEncoded());
//	            DERSet attrValue = new DERSet(by.readObject());
//	            Attribute unsignAtt = new Attribute(PKCSObjectIdentifiers.id_aa_ets_revocationValues, attrValue);
//	            
//	            //Timestamping
//	        	TimeStampTokenGetter tmp = new TimeStampTokenGetter("http://timestamping.edelweb.fr/service/tsp", DigestUtils.sha256(input));
//	        	TimeStampToken token = tmp.getTimeStampToken();
//	            
//	        	ASN1InputStream by2 = new ASN1InputStream(token.getEncoded());
//	        	DERSet attrValue2 = new DERSet(by2.readObject());
//	        	Attribute unsignAtt2 = new Attribute(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken, attrValue2);
//	            	            
//	            Hashtable attrs = new Hashtable(); 
//  	            attrs.put(PKCSObjectIdentifiers.id_aa_ets_revocationValues,unsignAtt); 
//  	            attrs.put(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken,unsignAtt2); 
//  	                 
//  	            
//  	            AttributeTable unsignedAttr = new AttributeTable(attrs); 
//  	                  
//  	            //Firmati
//  	            Hashtable attrsigned = new Hashtable();  
//  	         
//	  	        ASN1InputStream cert = new ASN1InputStream(certificate.getEncoded());
//	  	        DERSet attrValuecert = new DERSet(cert.readObject());
//	  	        Attribute signAttcert = new Attribute(PKCSObjectIdentifiers.id_aa_signingCertificateV2, attrValuecert); 
//  	          
//	  	        attrsigned.put(PKCSObjectIdentifiers.id_aa_signingCertificateV2, signAttcert);
//  	            
//  	            
//  	            AttributeTable signedAttr = new AttributeTable(attrsigned); 
//  	                
//  	            gen.addSigner(key, certificate, alg,signedAttr,unsignedAttr);
//  	            gen.addCertificatesAndCRLs(certs);
//                      
//	            CMSSignedData signedData = gen.generate(msg, true, provider);
//	
//	            
//	            Collection<SignerInformation> infos = signedData.getSignerInfos().getSigners();
//	            
//	            for(SignerInformation info:infos){
//	            	AttributeTable table = info.getSignedAttributes();
//	            	
//	            	Hashtable hash = table.toHashtable();
//	            	
//	            	Iterator iter = hash.keySet().iterator();
//	            	
//	            	while(iter.hasNext()){
//	            		System.out.println(iter.next());
//	            	}
//	            	
//	            	System.out.println("**********************************");
//	            	
//	            	AttributeTable table2 = info.getUnsignedAttributes();
//	            	
//	            	Hashtable hash2 = table2.toHashtable();
//	            	
//	            	Iterator iter2 = hash2.keySet().iterator();
//	            	
//	            	while(iter2.hasNext()){
//	            		
//	            		DERObjectIdentifier ite = (DERObjectIdentifier)iter2.next();
//	            		
//	            		System.out.println(ite);
//	            		
//	            		if(ite.getId().equals(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken.getId())){
//	            			
//	            			
//	            			Attribute my = (Attribute)hash2.get(ite);
//	            			
//	            			System.out.println(my.getAttrValues().getObjectAt(0));
//	            			      			
//	            			TimeStampToken token2 = new TimeStampToken(new CMSSignedData(my.getAttrValues().getObjectAt(0).getDERObject().getEncoded()));
//	            			            			           			
//	            			System.out.println(TSPAlgorithms.SHA256+"---"+new String(token2.getTimeStampInfo().getMessageImprintAlgOID()));	  			
//	            			         		
//	            			
////	            			TimeStampRequestGenerator reqGen = new TimeStampRequestGenerator();
////	            	        reqGen.setCertReq(true);
////	            	                 
////	            	        TimeStampRequest request = reqGen.generate(TSPAlgorithms.SHA256, info.getContentDigest());
////	            			   			
////	            			
////	            			TimeStampResponse resp = new TimeStampResponse(token2.getEncoded());
////	            			
////	            			try{
////	            				resp.validate(request);
////	            				System.out.println("VALIDO");
////	            			}catch(Exception e){
////	            				System.out.println("NON VALIDO");
////	            				e.printStackTrace();
////	            			}
//	            			
//	            			
//	            		}
//	            		
//	            	}
//	            	          	
//	            	
//	            	
//	            	
//	            	
//	            	
//	            	
//	            }
//	            
//	            
//	            
//	           
//	            
//	            
//	           	fout = signedData.getEncoded();
//			}catch(Exception e){
//				e.printStackTrace();
//				throw new Exception(e); 
//			}
//			return fout;
//		}
//	
////	public byte[] signerPDF(PrivateKey key, X509Certificate certificate, byte[] input,String provider) throws Exception {
////		ByteArrayOutputStream fout = null;
////		try{
////			PdfReader reader = new PdfReader(input);
////			fout = new ByteArrayOutputStream();
////			PdfStamper stp = PdfStamper.createSignature(reader, fout, '\0');
////			
////			
////			
////			PdfSignatureAppearance sap = stp.getSignatureAppearance();
////						
////			Rectangle rect = reader.getPageSize(1);
////		
////			float w = rect.getWidth();
////			float h = rect.getHeight();
////							
////			//sap.setCrypto(key, new Certificate[]{certificate}, null , PdfSignatureAppearance.SELF_SIGNED);
////			sap.setVisibleSignature(new Rectangle(w-20,h-20,w-120,h-120), 1, null);
////			//sap.setExternalDigest(null, null, "RSA");
////			//sap.preClose();
////						
////			//PdfPKCS7 sig = new PdfPKCS7(key, certificate.getEncoded(), provider);
////			String alg = "SHA1withRSA";
//////            if(this.digest.equals(DigestAlgorithm.SHA256)){
//////            	alg = "SHA256withRSA";	
//////            }
//////			Signature sign = Signature.getInstance(alg);
//////			sign.initSign(key);
//////			byte buf[] = new byte[8192];
//////			int n;
//////			InputStream inp = sap.getRangeStream();
//////			while ((n = inp.read(buf)) > 0) {
//////			    sign.update(buf, 0, n);
//////			}
////					
////			//sig.setExternalDigest(DigestUtils.sha(input), null, "RSA");
////			PdfDictionary dic = new PdfDictionary();
////			//dic.put(PdfName.CONTENTS, new PdfString(sig.getEncodedPKCS1()).setHexWriting(true));
////			
////			
////			
////			
////			sap.close(dic);
////			
////			
////			fout.close();
////		}catch(Exception e){
////			e.printStackTrace();
////			throw new Exception(e); 
////		}
////		return fout.toByteArray();
////	}
//	
//	
////	public byte[] signEt(PrivateKey key,X509Certificate certificate,byte[] input,String provider) throws Exception{
////		try {
////
////
////			
////			PdfReader reader = new PdfReader(input);
////			ByteArrayOutputStream fout = new ByteArrayOutputStream();
////			PdfStamper stp = PdfStamper.createSignature(reader, fout, '\0');
////			 PdfSignatureAppearance sap = stp.getSignatureAppearance();
////		        sap.setVisibleSignature(new Rectangle(100, 100, 300, 200), 1, null);
////		        sap.setSignDate(new GregorianCalendar());
////		        sap.setCrypto(null, new Certificate[]{certificate}, null, null);
////		        sap.setReason("I like to sign");
////		        sap.setLocation("Universe");
////		        sap.setAcro6Layers(true);
////		        //sap.setRender(PdfSignatureAppearance.SignatureRenderNameAndDescription);
////		        PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKMS, PdfName.ADBE_PKCS7_SHA1);
////		        dic.setDate(new PdfDate(sap.getSignDate()));
////		        dic.setName(PdfPKCS7.getSubjectFields(certificate).getField("CN"));
////		        if (sap.getReason() != null)
////		            dic.setReason(sap.getReason());
////		        if (sap.getLocation() != null)
////		            dic.setLocation(sap.getLocation());
////		        sap.setCryptoDictionary(dic);
////		        
////		        
////		        int csize = 4200;
////		        HashMap exc = new HashMap();
////		        exc.put(PdfName.CONTENTS, new Integer(csize * 2 + 2));
////		        sap.preClose(exc);
////
////		        CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
////		        generator.addSigner(key, certificate, CMSSignedDataGenerator.DIGEST_SHA256);
////
////		        ArrayList list = new ArrayList();
////		        list.add(certificate);
////		        
////		        CertStore chainStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(list), "BC");
////		        generator.addCertificatesAndCRLs(chainStore);
////		        CMSProcessable content = new CMSProcessableByteArray(DigestUtils.sha(sap.getRangeStream()));
////		        CMSSignedData signedData = generator.generate(content, true, provider);
////		        byte[] pk = signedData.getEncoded();
////		         
////		        
////		        byte[] outc = new byte[csize];
////
////		        PdfDictionary dic2 = new PdfDictionary();
////		        	        
////		        System.arraycopy(pk, 0, outc, 0, pk.length);
////
////		        dic2.put(PdfName.CONTENTS, new PdfString(outc).setHexWriting(true));
////		        sap.close(dic2);
////		        
////		        
////		        PdfPKCS7 pdf = new PdfPKCS7(key, new Certificate[]{certificate}, null,"SHA1", provider, true);
////		        pdf.setExternalDigest(DigestUtils.sha(sap.getRangeStream()), null, "SHA1");
////		        
////		        
////		        
////		        
////
////	        return fout.toByteArray();
////	    }
////	    catch (Exception e) {
////	        e.printStackTrace();
////	        throw new Exception(e);
////	    }
////	}
//	
//	 public byte[] signerPDF(PrivateKey key,X509Certificate certificate,byte[] input,String provider)throws Exception{
//			PdfReader reader = new PdfReader(input);
//			ByteArrayOutputStream fout = new ByteArrayOutputStream();
//			 PdfStamper stp = PdfStamper.createSignature(reader, fout, '\0');
//		        PdfSignatureAppearance sap = stp.getSignatureAppearance();
//		        sap.setVisibleSignature(new Rectangle(72, 732, 144, 780), 1, "Signature");
//		        sap.setCrypto(null, new X509Certificate[]{certificate}, null, PdfSignatureAppearance.WINCER_SIGNED);
//		        PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, new PdfName("adbe.pkcs7.detached"));
//		        dic.setReason(sap.getReason());
//		        dic.setLocation(sap.getLocation());
//		        dic.setContact(sap.getContact());
//		        dic.setDate(new PdfDate(sap.getSignDate()));
//		        sap.setCryptoDictionary(dic);
//			// comment next line to have an invisible signature
//		        int contentEstimated = 15000;
//		        HashMap<PdfName,Integer> exc = new HashMap<PdfName,Integer>();
//		        exc.put(PdfName.CONTENTS, new Integer(contentEstimated * 2 + 2));
//		        sap.preClose(exc);
//			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
//			byte buf[] = new byte[8192];
//			int n;
//			InputStream inp = sap.getRangeStream();
//			while ((n = inp.read(buf)) > 0) {
//			    messageDigest.update(buf, 0, n);
//			}
//			
//			Calendar cal = Calendar.getInstance();
//			
//			
////			 TSAClient tsc = null;
////		        if (true) {
////		            String tsa_url    = "http://www.tecnes.com/javasign/timestamp";
////                    tsc = new TSAClientBouncyCastle(tsa_url,"mirigo","fv54kagz");
////		        }
//		        // If we use OCSP:
//		       // byte[] ocsp = null;
////		        if (false) {
////		            String url = PdfPKCS7.getOCSPURL((X509Certificate)chain[0]);
////		            CertificateFactory cf = CertificateFactory.getInstance("X509");
////		            FileInputStream is = new FileInputStream(properties.getProperty("ROOTCERT"));
////		            X509Certificate root = (X509Certificate) cf.generateCertificate(is);
////		            ocsp = new OcspClientBouncyCastle((X509Certificate)chain[0], root, url).getEncoded();
////		        }
//			
//			
//			// Create the signature
//	        it.eng.client.applet.operation.pdf.PdfPKCS7 sgn = new it.eng.client.applet.operation.pdf.PdfPKCS7(key, new Certificate[]{certificate}, null, "SHA1", provider, false);
//	        
//	    	TimeStampTokenGetter tmp = new TimeStampTokenGetter("https://ns.szikszi.hu:8443/tsa", IOUtils.toByteArray(new FileInputStream("c:\\odg.pdf")));
//	    	TimeStampToken token = tmp.getTimeStampToken();
//	        
//	        
//	        //byte sh[] = sgn.getAuthenticatedAttributeBytes(messageDigest.digest(), cal, ocsp);
//	        //sgn.update(sh, 0, sh.length);
//	    	
//	    	System.out.println(token);
//	    	
//	        byte[] encodedSig = null;/*sgn.getEncodedPKCS7(messageDigest.digest(), cal, token);*/
//	 
////	        if (contentEstimated + 2 < encodedSig.length)
////	            throw new DocumentException("Not enough space");
//	        //int contentEstimated = 15000;
//	        byte[] paddedSig = new byte[contentEstimated];
//	        System.arraycopy(encodedSig, 0, paddedSig, 0, encodedSig.length);
//	        // Replace the contents
//	        PdfDictionary dic2 = new PdfDictionary();
//	        dic2.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));
//	        sap.close(dic2);
//			return fout.toByteArray();
//		}
//	 
//	 
//	
//	public byte[] signUU(PrivateKey key,X509Certificate certificate,byte[] input,String provider) throws Exception{
//
//        // reader and stamper
//        PdfReader reader = new PdfReader(input);
//        ByteArrayOutputStream fout = new ByteArrayOutputStream();
//        PdfStamper stp = PdfStamper.createSignature(reader, fout, '\0');
//        PdfSignatureAppearance sap = stp.getSignatureAppearance();
//        sap.setVisibleSignature(new Rectangle(72, 732, 144, 780), 1, null);
//        sap.setCrypto(null, new Certificate[]{certificate}, null, null);
//        sap.setAcro6Layers(true);
//        sap.setRenderingMode(PdfSignatureAppearance.RenderingMode.DESCRIPTION);
//        PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
//        dic.setDate(new PdfDate(sap.getSignDate()));
//        dic.setName(PdfPKCS7.getSubjectFields(certificate).getField("CN"));
//        dic.setReason("Signed with BC");
//        dic.setLocation("Foobar");
//        sap.setCryptoDictionary(dic);
//        int csize = 4000;
//        HashMap<PdfName,Integer> exc = new HashMap<PdfName,Integer>();
//        exc.put(PdfName.CONTENTS, new Integer(csize * 2 + 2));
//        sap.preClose(exc);
//        // signature
//        CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
//        generator.addSigner(key, certificate, CMSSignedDataGenerator.DIGEST_SHA256);
// 
//        ArrayList<Certificate> list = new ArrayList<Certificate>();
//        list.add(certificate);
//        
//        CertStore chainStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(list), "BC");
//        generator.addCertificatesAndCRLs(chainStore);
//        CMSProcessable content = new CMSProcessableByteArray(DigestUtils.sha256(sap.getRangeStream()));
//        CMSSignedData  signedData = generator.generate(content, true, provider);
//            
//        byte[] pk = signedData.getEncoded();
// 
//        byte[] outc = new byte[csize];
//        PdfDictionary dic2 = new PdfDictionary();
//        System.arraycopy(pk, 0, outc, 0, pk.length);
//        dic2.put(PdfName.CONTENTS, new PdfString(outc).setHexWriting(true));
//            
//        
//        sap.close(dic2);
//        
//        return fout.toByteArray();
//        
//	}
//	
//	
//	
//	
//	public byte[] signEncapsulated(PrivateKey key,X509Certificate certificate,byte[] input,String provider) throws Exception {
//		try{
//
//		PdfReader reader = new PdfReader(input);
//		ByteArrayOutputStream fout = new ByteArrayOutputStream();
//		PdfStamper stp = PdfStamper.createSignature(reader, fout, '\0');
//		PdfSignatureAppearance sap = stp.getSignatureAppearance();
//		sap.setCrypto(null, new Certificate[]{certificate}, null, PdfSignatureAppearance.SELF_SIGNED);
//		sap.setVisibleSignature(new Rectangle(100, 100, 200, 200), 1, null);
//		sap.setExternalDigest(new byte[128], null, "RSA");
//		sap.preClose();
//		PdfPKCS7 sig = sap.getSigStandard().getSigner();
//		
//		
//		
//		Signature sign = Signature.getInstance("SHA256withRSA");
//		sign.initSign(key);
//		
//		sign.update(IOUtils.toByteArray(sap.getRangeStream()));
//		
////		byte buf[] = new byte[8192];
////		int n;
////		InputStream inp = sap.getRangeStream();
////		while ((n = inp.read(buf)) > 0) {
////		    sign.update(buf, 0, n);
////		}
//		
//		
//		sig.setExternalDigest(sign.sign(), null, "RSA");
//		
//		
//		
//		
//		PdfDictionary dic = new PdfDictionary();
//		dic.put(PdfName.CONTENTS, new PdfString(sig.getEncodedPKCS1()).setHexWriting(true));
//		
//		System.out.println(sap.getFilter().toString());
//		
//		
//		
//		
//		
//	sap.close(dic);
//
//	System.out.println(sap.getFilter().toString());
//	System.out.println(sap.getSigStandard().get(PdfName.SUBFILTER));
//	System.out.println(sap.getSigStandard().get(PdfName.FILTER));
//		return fout.toByteArray();
//
//	    }catch (Exception e) {
//	        e.printStackTrace();
//	        throw new Exception(e);
//	    }
//	}
//
//
//	
//	
//	public static void main(String[] args) throws IOException, Exception {
//		PDFSignTest test = new PDFSignTest();
//			
//		Security.addProvider(new BouncyCastleProvider());
////		
////		
////		
////		Factory fact = new Factory(DigestAlgorithm.SHA1, SignerType.PDF);
////		fact.copyConfiguration();
////		fact.copyProvider();
////		fact.loadAllCertificate("11433".toCharArray());
////		
////		HashFileBean bean = new HashFileBean();
////		bean.setHashPdf();
////		bean.setFileType(SignerType.PDF);
////		bean.get
////		
////		
////		fact.signerfile(file, pin, provider)
////		
//
//		
//		test.loadAllCertificate("46303".toCharArray());
//		test.signerfile(FileUtils.readFileToByteArray(new File("C:\\ODG.pdf")), "46303".toCharArray(), "SunPKCS11-SmartCards", "PDF");
//		
//		//test.signerVerifiy(FileUtils.readFileToByteArray(new File("C:\\ODG2.pdf.p7m")));
//		
//		
//	}
//    
//	private class CMSProcessableRange implements CMSProcessable {
//	    private PdfSignatureAppearance sap;
//	    private byte[] buf = new byte[8192];
//
//	    public CMSProcessableRange(PdfSignatureAppearance sap) {
//	        this.sap = sap;
//	    }
//
//	    public void write(OutputStream outputStream) throws IOException, CMSException {
//	        InputStream s = sap.getRangeStream();
//	        int read = 0;
//	        while ((read = s.read(buf, 0, buf.length)) > 0) {
//	            outputStream.write(buf, 0, read);
//	        }
//	    }
//
//	    public Object getContent() {
//	        return sap;
//	    }
//	}

	
}


