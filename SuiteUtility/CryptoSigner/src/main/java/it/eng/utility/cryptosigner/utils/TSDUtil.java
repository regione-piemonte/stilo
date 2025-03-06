package it.eng.utility.cryptosigner.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
//import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TimeStampResponse;

public class TSDUtil {

	private HashMap<String, Object> map;

	public static final ASN1ObjectIdentifier tsdOID = new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.1.31");

	public static final ASN1ObjectIdentifier pKCS_OID = new ASN1ObjectIdentifier("1.2.840.113549");
	public static final ASN1ObjectIdentifier pKCS7_OID = new ASN1ObjectIdentifier("1.2.840.113549.1.7");
	public static final ASN1ObjectIdentifier pKCS7_DATA_OID = new ASN1ObjectIdentifier("1.2.840.113549.1.7.1");
	public static final ASN1ObjectIdentifier PKCS7_SIGNED_OID = new ASN1ObjectIdentifier("1.2.840.113549.1.7.2");
	public static final ASN1ObjectIdentifier pKCS7_ENVDATA_OID = new ASN1ObjectIdentifier("1.2.840.113549.1.7.3");
	public static final ASN1ObjectIdentifier PKCS7_SIGNEDENVDATA_OID = new ASN1ObjectIdentifier("1.2.840.113549.1.7.4");
	public static final ASN1ObjectIdentifier pKCS7_DIGESTEDDATA_OID = new ASN1ObjectIdentifier("1.2.840.113549.1.7.5");
	public static final ASN1ObjectIdentifier PKCS7_ENCRIPTDATA_OID = new ASN1ObjectIdentifier("1.2.840.113549.1.7.6");

	public static final String CMS_TAG = "CMS";
	public static final String CMSCONTENT_TAG = "CMS_CONTENT";
	public static final String CMSOCTETSTRING_TAG = "CMS_OCTETSTRING";

	private static final Logger log = Logger.getLogger(TSDUtil.class);

	private InputStream tsd = null;

	private boolean checkedTsd;

	public TSDUtil(String tsdFilePath) throws FileNotFoundException {
		File f = new File(tsdFilePath);
		__TSDUtil(new FileInputStream(f));
	}

	public TSDUtil(InputStream tsd) {
		__TSDUtil(tsd);
	}

	public TSDUtil(File tsd) throws FileNotFoundException {
		__TSDUtil(new FileInputStream(tsd));
	}

	public boolean isTsd() {
		return checkedTsd;
	}

	public HashMap<String, Object> getDataMap() {
		return map;
	}

	private void __TSDUtil(InputStream tsd) {
		this.tsd = tsd;
		checkedTsd = false;
		map = new HashMap<String, Object>();
		Parse();
	}

	/*
	 * Salva il file p7m contenuto all'interno di un file tsd
	 */
	public boolean saveP7M(File fout) throws IOException {
		boolean res = false;

		if (fout != null) {
			FileOutputStream fos = new FileOutputStream(fout);
			if (isTsd() && map != null) {
				DEROctetString os = (DEROctetString) map.get(TSDUtil.CMSOCTETSTRING_TAG);
				if (os != null) {
					copyFile(os.getOctetStream(), fos);
					res = true;
				}
			}
			if (fos != null) {
				fos.close();
				fos = null;
			}
		}
		return res;
	}

	/*
	 * Salva il contenuto del file p7m, contenuto all'interno del file tsd
	 */
	public boolean saveContent(File fout) throws IOException, CMSException {
		boolean res = false;

		if (fout != null) {
			FileOutputStream fos = new FileOutputStream(fout);
			if (isTsd() && map != null) {
				CMSSignedData cms = (CMSSignedData) map.get(TSDUtil.CMS_TAG);
				if (cms != null) {
					cms.getSignedContent().write(fos);
					res = true;
				}
			}
			if (fos != null) {
				fos.close();
				fos = null;
			}
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	private void Parse() {

		final ASN1InputStream asn1Stream = new ASN1InputStream(tsd);

		ASN1Primitive doi = null;

		/*try {
			doi = asn1Stream.readObject();
			ASN1Sequence seq = ASN1Sequence.getInstance(doi);

			Enumeration<DERObject> e = seq.getObjects();

			ASN1ObjectIdentifier objectIdentifier = null;

			DERTaggedObject taggedObject = null;

			while (e.hasMoreElements()) {
				Object elem = e.nextElement();

				if (elem instanceof ASN1ObjectIdentifier) {
					objectIdentifier = (ASN1ObjectIdentifier) elem;
					if (!objectIdentifier.equals(TSDUtil.tsdOID)) {
						checkedTsd = false;
					} else
						checkedTsd = true;
				} else if (elem instanceof DERTaggedObject) {
					taggedObject = (DERTaggedObject) elem;
				}
			}
*/
			/*
			 * Se arrivo a questo punto significa che sto leggendo un TSD. Leggo la sequenza ASN1 e faccio il parsing del contenuto del TSD
			 * 
			 */
		/*	if (checkedTsd)
				extractData(taggedObject);

		} catch (IOException e1) {
			log.warn("Eccezione Parse", e1);
		}*/
	}

	@SuppressWarnings({ "unchecked" })
	private void extractData(DERTaggedObject dt_obj) {
		ASN1Sequence timeStampedData = DERSequence.getInstance(dt_obj.getObject());

		DERTaggedObject taggedObject = null;
		DEROctetString octetString = null;
		CMSSignedData cmsObj = null;

		/*
		 * Estraggo gli elementi che compongono il content data del TSD in base al RFC 5544
		 */
		try {
			TimeStampResponse resp = new TimeStampResponse(timeStampedData.getEncoded(ASN1Encoding.DER));
		} catch (TSPException e2) {
			log.warn("Eccezione extractData", e2);
		} catch (IOException e2) {
			log.warn("Eccezione extractData", e2);
		}

		Enumeration<ASN1Primitive> e = timeStampedData.getObjects();
		try {
			while (e.hasMoreElements()) {
				Object elem = e.nextElement();

				if (elem instanceof DEROctetString) {
					octetString = (DEROctetString) elem;
				} else if (elem instanceof DERTaggedObject) {
					taggedObject = (DERTaggedObject) elem;
				}
			}
			boolean checkedP7M = false;

			if (taggedObject != null) {

				ASN1Sequence seq = DERSequence.getInstance(taggedObject.getObject());
				e = seq.getObjects();
				DERSequence dseq = null;
				ASN1ObjectIdentifier oid = null;

				if (e.hasMoreElements()) {
					dseq = (DERSequence) e.nextElement();
					e = dseq.getObjects();

					while (e.hasMoreElements()) {
						Object elem = e.nextElement();

						if (elem instanceof ASN1ObjectIdentifier) {
							oid = (ASN1ObjectIdentifier) elem;
							if (oid.equals(TSDUtil.PKCS7_SIGNED_OID))
								checkedP7M = true;
						}
					}
				}

			}

			if (checkedP7M && octetString != null) {
				cmsObj = new CMSSignedData(octetString.getOctetStream());
				Object obj = cmsObj.getSignedContent().getContent();

				map.put(TSDUtil.CMS_TAG, cmsObj);
				map.put(TSDUtil.CMSCONTENT_TAG, obj);
				map.put(TSDUtil.CMSOCTETSTRING_TAG, octetString);
			}
		} catch (CMSException e1) {
			map = null;
			log.warn("Eccezione extractData", e1);
		}
	}

	private static final int IO_BUFFER_SIZE = 4 * 1024;

	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] b = new byte[IO_BUFFER_SIZE];
		int read;
		while ((read = in.read(b)) != -1) {
			out.write(b, 0, read);
		}
	}

}
