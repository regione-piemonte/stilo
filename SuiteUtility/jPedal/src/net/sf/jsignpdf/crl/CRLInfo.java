/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * 
 * The Original Code is 'JSignPdf, a free application for PDF signing'.
 * 
 * The Initial Developer of the Original Code is Josef Cacek.
 * Portions created by Josef Cacek are Copyright (C) Josef Cacek. All Rights Reserved.
 * 
 * Contributor(s): Josef Cacek.
 * 
 * Alternatively, the contents of this file may be used under the terms
 * of the GNU Lesser General Public License, version 2.1 (the  "LGPL License"), in which case the
 * provisions of LGPL License are applicable instead of those
 * above. If you wish to allow use of your version of this file only
 * under the terms of the LGPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the LGPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the LGPL License.
 */
package net.sf.jsignpdf.crl;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.input.CountingInputStream;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.x509.extension.X509ExtensionUtil;
import org.jpedal.examples.viewer.objects.SignData;

import com.itextpdf.text.pdf.security.CertificateUtil;
import com.itextpdf.text.pdf.security.CrlClientOnline;
import com.itextpdf.text.pdf.security.PdfPKCS7;

/**
 * Helper bean for holding CRL related data.
 * 
 * @author Josef Cacek
 * 
 */
public class CRLInfo {

	private static long byteCount = 0L;
	
	/**
	 * Returns CRLs for the certificate chain.
	 * 
	 * @return
	 * @throws CertificateParsingException 
	 */
	public static CRL[] getCrls(Certificate[] certChain) throws CertificateParsingException {
		
		
		final Set<String> urls = new HashSet<String>();
		for (Certificate cert : certChain) {
			if (cert instanceof X509Certificate) {
				urls.add(CertificateUtil.getCRLURL((X509Certificate) cert));
			}
		}
		
		final Set<CRL> crlSet = new HashSet<CRL>();
		for (final String urlStr : urls) {
			try {
				System.out.println("console.crlinfo.loadCrl"+ urlStr);
				final URL tmpUrl = new URL(urlStr);
				final CountingInputStream inStream = new CountingInputStream(tmpUrl.openConnection(createProxy()).getInputStream());
				final CertificateFactory cf = CertificateFactory.getInstance("X.509");
				final CRL crl = cf.generateCRL(inStream);
				final long tmpBytesRead = inStream.getByteCount();
				System.out.println("console.crlinfo.crlSize"+ String.valueOf(tmpBytesRead));
				if (!crlSet.contains(crl)) {
					byteCount += tmpBytesRead;
					crlSet.add(crl);
				} else {
					System.out.println("console.crlinfo.alreadyLoaded");
				}
				inStream.close();
			} catch (MalformedURLException e) {
				
			} catch (IOException e) {
				
			} catch (CertificateException e) {
				
			} catch (CRLException e) {
				
			}
		}
		return crlSet.toArray(new CRL[crlSet.size()]);
	}

	/**
	 * Returns byte count, which should
	 * 
	 * @return
	 * @throws CertificateParsingException 
	 */
	public static long getByteCount(Certificate[] certChain) throws CertificateParsingException {
		final Set<String> urls = new HashSet<String>();
		for (Certificate cert : certChain) {
			if (cert instanceof X509Certificate) {
				urls.add(CertificateUtil.getCRLURL((X509Certificate) cert));
			}
		}
		final Set<CRL> crlSet = new HashSet<CRL>();
		for (final String urlStr : urls) {
			try {
				System.out.println("console.crlinfo.loadCrl"+ urlStr);
				final URL tmpUrl = new URL(urlStr);
				final CountingInputStream inStream = new CountingInputStream(tmpUrl.openConnection(createProxy()).getInputStream());
				final CertificateFactory cf = CertificateFactory.getInstance("X.509");
				final CRL crl = cf.generateCRL(inStream);
				final long tmpBytesRead = inStream.getByteCount();
				System.out.println("console.crlinfo.crlSize"+ String.valueOf(tmpBytesRead));
				if (!crlSet.contains(crl)) {
					byteCount += tmpBytesRead;
					crlSet.add(crl);
				} else {
					System.out.println("console.crlinfo.alreadyLoaded");
				}
				inStream.close();
			} catch (MalformedURLException e) {
				
			} catch (IOException e) {
				
			} catch (CertificateException e) {
				
			} catch (CRLException e) {
				
			}
		}
		
		return byteCount;
	}

	/**
	 * Returns (initialized, but maybe empty) set of URLs of CRLs for given
	 * certificate.
	 * 
	 * @param aCert
	 *          X509 certificate.
	 * @return
	 */
	private static Set<String> getCrlUrls(final X509Certificate aCert) {
		final Set<String> tmpResult = new HashSet<String>();
		System.out.println("console.crlinfo.retrieveCrlUrl" + aCert.getSubjectX500Principal().getName());
		final byte[] crlDPExtension = aCert.getExtensionValue(X509Extension.cRLDistributionPoints.getId());
		if (crlDPExtension != null) {
			CRLDistPoint crlDistPoints = null;
			try {
				crlDistPoints = CRLDistPoint.getInstance(X509ExtensionUtil.fromExtensionValue(crlDPExtension));
			} catch (IOException e) {
				
			}
			if (crlDistPoints != null) {
				final DistributionPoint[] distPoints = crlDistPoints.getDistributionPoints();
				distPoint: for (DistributionPoint dp : distPoints) {
					final DistributionPointName dpName = dp.getDistributionPoint();
					final GeneralNames generalNames = (GeneralNames) dpName.getName();
					if (generalNames != null) {
						final GeneralName[] generalNameArr = generalNames.getNames();
						if (generalNameArr != null) {
							for (final GeneralName generalName : generalNameArr) {
								if (generalName.getTagNo() == GeneralName.uniformResourceIdentifier) {
//									final DERString derString = (DERString) generalName.getName();
//									final String uri = derString.getString();
//									if (uri != null && uri.startsWith("http")) {
//										// ||uri.startsWith("ftp")
//										System.out.println("console.crlinfo.foundCrlUri" + uri);
//										tmpResult.add(uri);
//										continue distPoint;
//									}
								}
							}
						}
						System.out.println("console.crlinfo.noUrlInDistPoint");
					}
				}
			}
		} else {
			System.out.println("console.crlinfo.distPointNotSupported");
		}
		return tmpResult;
	}
	
	public static Proxy createProxy() {
		Proxy tmpResult = Proxy.NO_PROXY;
		return tmpResult;
	}
}
