package it.eng.utility.cryptosigner.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.security.cert.CRL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.cms.CMSSignedDataParser;
import org.bouncycastle.cms.CMSTypedStream;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.tsp.cms.CMSTimeStampedDataParser;

import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.data.signature.ISignature;
import it.eng.utility.cryptosigner.data.type.SignerType;
import it.eng.utility.cryptosigner.data.util.CopyThread;
import it.eng.utility.cryptosigner.utils.InstanceCMSSignedDataParser;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;

/**
 * Implementa i controlli su firme di tipo CAdES. Il contenuto di un file è riconosciuto se implementa le specifiche RFC5544
 * 
 * Implementa i controlli su firme di tipo CAdES. Il contenuto di un file e' riconosciuto se implementa le specifiche RFC5544
 * 
 * @author Rigo Michele
 *
 */
public class TsdSigner extends CAdESSigner {

	private static final Logger log = Logger.getLogger(TsdSigner.class);

	private Map<byte[], TimeStampToken> timestamptokensBySignature = null;
	private List<ISignature> firme;

	@Override
	public SignerType getFormat() {
		return SignerType.TSD;
	}

	public static void main(String[] args) {
		TsdSigner signer = new TsdSigner();
		File file = new File("C:/Users/Anna Tesauro/Desktop/testPDF - Copia.pdf");
		boolean isOk = signer.isSignedType(file);
		System.out.println(isOk);

		boolean esitoCancellazione = file.delete();
		System.out.println(esitoCancellazione);
	}

	public boolean isSignedType(File file) {
		boolean signed = false;
		InputStream stream = null;
		//this.file = file;
		try {
			stream = FileUtils.openInputStream(file);
			CMSTimeStampedDataParser data = new CMSTimeStampedDataParser(stream);
			timestamptokens = data.getTimeStampTokens();
			signed = true;
		} catch (Exception e) {
			log.info("Errore in isSignedType di TsdSigner - " + e.getMessage() + " - Il file non ha firma tsd"/*, e*/);
			signed = false;
		} finally {
			if (stream != null) {
				IOUtils.closeQuietly(stream);
			}
		}
		return signed;
	}

	@Override
	public Map<byte[], TimeStampToken> getMapSignatureTimeStampTokens(File file) {
		if (timestamptokens == null || timestamptokens.length == 0)
			return null;
		Map<byte[], TimeStampToken> map = new HashMap<byte[], TimeStampToken>();
		TimeStampToken timestamptoken = timestamptokens[0];
		if (firme == null)
			firme = getSignatures(file);
		for (ISignature firma : firme) {
			map.put(firma.getSignatureBytes(), timestamptoken);
		}
		return map;
	}

	public List<ISignature> getSignatures(File file) {

		//super.file = getContent();
		if (firme == null)
			firme = super.getSignatures(file);
		if ( firme == null )
			firme = new ArrayList<>();
		return firme;
	}

	public Collection<CRL> getEmbeddedCRLs() {
		return null;
	}

	@Override
	public TimeStampToken[] getTimeStampTokens(File file) {
		return timestamptokens;
	}

	public ValidationInfos validateTimeStampTokensEmbedded(File file) {
		ValidationInfos validationInfos = new ValidationInfos();
		if (this.timestamptokens == null) {
			if (!this.isSignedType(file)) {
				validationInfos.addError(MessageHelper.getMessage(MessageConstants.FV_FILE_FORMAT_ERROR, this.getFormat()));
				validationInfos.addErrorWithCode(MessageConstants.FV_FILE_FORMAT_ERROR,
						MessageHelper.getMessage(MessageConstants.FV_FILE_FORMAT_ERROR, this.getFormat()));
				return validationInfos;
			}
		}
		return validationInfos;
	}

	// public static void main(String[] args) {
	// File file = new File( "C:/Users/Anna Tesauro/Desktop/domanda_dia_ok.pdf.p7m.tsd" );
	// TsdSigner signer = new TsdSigner();
	// boolean esito = signer.isSignedType(file);
	// System.out.println(esito);
	// if( esito ){
	// File p7MFile = new File( "C:/Users/Anna Tesauro/Desktop/domanda_dia_ok.pdf.p7m" );
	// File contentFile = signer.getContent();
	// try {
	// IOUtils.copyLarge(new FileInputStream(contentFile), new FileOutputStream(p7MFile));
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// System.out.println(p7MFile);
	//
	// File unsignedFile = signer.getUnsignedFileContent();
	// try {
	// File sbustato = new File( "C:/Users/Anna Tesauro/Desktop/domanda_dia_ok.pdf" );;
	// IOUtils.copyLarge(new FileInputStream(unsignedFile), new FileOutputStream(sbustato));
	//
	// System.out.println(sbustato);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
	// }

	private File getContent(File file) {
		InputStream stream = null;
		File fileextactp7m = null;

		try {
			fileextactp7m = File.createTempFile("ExtractP7M", "ext");

			stream = FileUtils.openInputStream(file);
			CMSTimeStampedDataParser data = new CMSTimeStampedDataParser(stream);
			IOUtils.copyLarge(data.getContent(), new FileOutputStream(fileextactp7m));
		} catch (Exception e) {
			log.error("Eccezione getContent", e);
		}

		return fileextactp7m;
	}

	@Override
	public InputStream getUnsignedContent(File file) {
		InputStream stream = null;
		File fileextactp7m = null;
		try {
			File fileextact = File.createTempFile("Extract", "ext");
			fileextactp7m = File.createTempFile("ExtractP7M", "ext");

			stream = FileUtils.openInputStream(file);
			CMSTimeStampedDataParser data = new CMSTimeStampedDataParser(stream);
			IOUtils.copyLarge(data.getContent(), new FileOutputStream(fileextactp7m));

			CMSSignedDataParser cmsSignedData = InstanceCMSSignedDataParser.getCMSSignedDataParser(FileUtils.openInputStream(fileextactp7m), false);
			// new CMSSignedDataParser(FileUtils.openInputStream(fileextactp7m));

			CMSTypedStream datastream = cmsSignedData.getSignedContent();

			IOUtils.copyLarge(datastream.getContentStream(), new FileOutputStream(fileextact));

			PipedInputStream in = new PipedInputStream();
			PipedOutputStream out = new PipedOutputStream(in);

			CopyThread thread = new CopyThread(fileextact, out);
			thread.start();

			return in;
		} catch (Exception e) {
			log.error("Eccezione getUnsignedContent", e);
			return null;
		} finally {
			if (stream != null) {
				IOUtils.closeQuietly(stream);
			}
			// FileUtil.deleteFile(fileextactp7m);
		}
	}

	public File getUnsignedFileContent(File file) {
		InputStream stream = null;
		File fileextactp7m = null;
		try {
			File fileextact = File.createTempFile("Extract", "ext");
			fileextactp7m = File.createTempFile("ExtractP7M", "ext");

			stream = FileUtils.openInputStream(file);
			CMSTimeStampedDataParser data = new CMSTimeStampedDataParser(stream);
			IOUtils.copyLarge(data.getContent(), new FileOutputStream(fileextactp7m));

			// CMSSignedDataParser cmsSignedData = new CMSSignedDataParser(null, FileUtils.openInputStream(fileextactp7m));
			// CMSTypedStream datastream = cmsSignedData.getSignedContent();

			// IOUtils.copyLarge(datastream.getContentStream(), new FileOutputStream(fileextact));

			return fileextact;
		} catch (Exception e) {
			log.error("Eccezione getUnsignedFileContent", e);
			return null;
		} finally {
			if (stream != null) {
				IOUtils.closeQuietly(stream);
			}
			// FileUtil.deleteFile(fileextactp7m);
		}

	}

	// public static void main(String[] args) {
	// String path = "C:/sviluppo/eclipseJ2eeHeliosAuriga/workspace/FileOperationWar/test/verificaFirme/tsd/test1.p7m.tsd";
	// File file = new File( path );
	// TsdSigner signer = new TsdSigner();
	//
	// boolean esito = signer.isSignedType( file );
	// System.out.println(esito);
	//
	// java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	// if( esito ){
	// //signer.getUnsignedContent();
	//
	// List<ISignature> signatures = signer.getSignatures();
	// System.out.println("---->" + signatures);
	// if( signatures !=null ){
	// System.out.println("num firme " + signatures.size() );
	// for( ISignature signature : signatures ){
	// System.out.println(signature );
	// }
	// }
	// TimeStampToken[] marche = signer.getTimeStampTokens();
	// System.out.println(marche);
	// if( marche !=null ){
	// System.out.println(marche.length);
	// for( TimeStampToken marca : marche ){
	// System.out.println( marca.getTimeStampInfo().getGenTime() );
	// }
	// }
	// }
	// }

}
