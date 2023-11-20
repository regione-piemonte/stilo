/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.module.foutility.beans.generated.CertVerificationStatusType;
import it.eng.module.foutility.beans.generated.VerificationInfo;
import it.eng.module.foutility.beans.generated.VerificationRequest;
import it.eng.module.foutility.beans.generated.VerificationResultType;
import it.eng.module.foutility.beans.generated.VerificationResults;
import it.eng.module.foutility.beans.generated.VerificationTypes;
import it.eng.utility.cryptosigner.context.CryptoSignerApplicationContextProvider;
import it.eng.utility.cryptosigner.controller.ISignerController;
import it.eng.utility.cryptosigner.controller.MasterCertController;
import it.eng.utility.cryptosigner.controller.bean.InputCertBean;
import it.eng.utility.cryptosigner.controller.bean.OutputCertBean;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.impl.cert.CertificateExpiration;
import it.eng.utility.cryptosigner.controller.impl.cert.CertificateReliability;
import it.eng.utility.cryptosigner.controller.impl.cert.CertificateRevocation;

@Service(name = "VerificaCertificato")
@WebService(endpointInterface = "it.eng.module.foutility.cert.CertificateVerifier", targetNamespace = "verify.cryptoutil.eng.it")
public class CertificateVerifierImpl implements CertificateVerifier {

	private static final Logger log = LogManager.getLogger(CertificateVerifierImpl.class);

	@Override
	@Operation(name = "check")
	public VerificationResults check(VerificationRequest input) {
		try {
			log.info("avvio verifiche certificato");
			// javax.security.cert.X509Certificate cert=javax.security.cert.X509Certificate.getInstance(input.getX509Cert());
			Security.addProvider(new BouncyCastleProvider());
			CertificateFactory factory = CertificateFactory.getInstance("X509", BouncyCastleProvider.PROVIDER_NAME);
			X509Certificate cert = (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(input.getX509Cert()));
			log.debug("ho letto il certificato da verificare:" + cert);
			InputCertBean ib = new InputCertBean();
			// X509Certificate certificato=readCert();
			ib.setCertificate(cert);
			if (input.getDateRif() != null) {
				// TODO verificare con time zone differneti
				ib.setReferenceDate(input.getDateRif().toGregorianCalendar().getTime());
			}
			// System.out.println("serialNumber:"+cert.getSerialNumber());
			// System.out.println("cert:"+cert.toString());
			List<VerificationInfo> vinfos = input.getVerificationInfo();
			// List<VerificationTypes> verifIDRequested=new ArrayList<VerificationTypes>();
			// //elimino le verifiche duplicate
			// List<VerificationInfo> realVInfos=new ArrayList<VerificationInfo>();
			// for (VerificationInfo vInfo : vinfos) {
			// System.out.println("veriinfo:"+vInfo.getName());
			// if(!verifIDRequested.contains(vInfo.getVerificationID())){
			// verifIDRequested.add(vInfo.getVerificationID());
			// realVInfos.add(vInfo);
			// }
			// }
			// imposta i controller in base alle verifiche richieste
			// in modo da eseguire solo quelle nella richiesta
			log.debug("costruisco i controller");
			MasterCertController manager = buildController(vinfos);
			log.debug("eseguo i controlli richiesti");
			OutputCertBean outputCertBean = manager.executeControll(ib, true);
			log.debug("controlli eseguiti costruisco il risultato");
			return buildResult(outputCertBean);
		} catch (Exception e) {
			log.warn("Eccezione check", e);
			return null;
		}
	}

	private VerificationResults buildResult(OutputCertBean outputCertBean) {
		VerificationResults ret = new VerificationResults();
		VerificationTypes[] types = VerificationTypes.values();
		for (int i = 0; i < types.length; i++) {
			ret.getVerificationResult().add(buildVResultForType(types[i], outputCertBean));
		}
		return ret;
	}

	private VerificationResultType buildVResultForType(VerificationTypes type, OutputCertBean outputCertBean) {
		VerificationResultType result = new VerificationResultType();
		result.setVerificationID(type);
		log.debug("costruisco il risultato per il controllo " + type);
		Map<String, ValidationInfos> vinfos = outputCertBean.getPropsOfType(ValidationInfos.class);
		ValidationInfos validationInfo = vinfos.get(type.name());
		if (validationInfo != null) {

			if (validationInfo.isValid()) {
				result.setVerificationStatus(CertVerificationStatusType.OK);
			} else if (validationInfo.getErrors() != null && validationInfo.getErrors().length > 0) {
				result.setVerificationStatus(CertVerificationStatusType.KO);
				// se c'è un errore per cui non poso eseguire il controllo imposto ad error lo stato
				if (validationInfo.isCannotExecute()) {
					result.setVerificationStatus(CertVerificationStatusType.ERROR);
				}
				result.setErrorMessage(validationInfo.getErrorsString());
			}
			// set warning anyway
			String[] warns = validationInfo.getWarnings();
			if (warns != null && warns.length > 0) {
				VerificationResultType.Warnings w = new VerificationResultType.Warnings();
				for (int i = 0; i < warns.length; i++) {
					w.getWarnMesage().add(warns[i]);
				}
				result.setWarnings(w);
			}
		} else {
			result.setVerificationStatus(CertVerificationStatusType.SKIPPED);
		}
		return result;
	}

	/**
	 * costruisce il controller con i controlli da eseguire in base a quelli richiesti
	 * 
	 * @param vinfos
	 * @return
	 * @throws Exception
	 */
	private MasterCertController buildController(List<VerificationInfo> vinfos) throws Exception {
		MasterCertController manager = (MasterCertController) CryptoSignerApplicationContextProvider.getContext().getBean("MasterCertController");
		// controller da eseguire
		List<ISignerController> ctrls = new ArrayList<ISignerController>();
		List<VerificationTypes> verifRequested = new ArrayList<VerificationTypes>();
		for (VerificationInfo vInfo : vinfos) {
			ISignerController ctrl = getControllerFromVerificationID(vInfo.getVerificationID());
			// non duplico i controlli se nella richiesta vengono inseriti due volte gli stessi
			if (!verifRequested.contains(vInfo.getVerificationID())) {
				verifRequested.add(vInfo.getVerificationID());
				ctrls.add(ctrl);// aggiungo il controllo all'elenco di quelli da eseguire
				log.debug("aggiungo il controllo " + ctrl.getClass());
			}
		}
		// TODO occorre anche poter impostare i flag in base alla richiesta
		// al momento pernde quelli nel file di spring
		log.debug("impostati " + ctrls.size() + " controlli");
		manager.setControllers(ctrls);
		return manager;
	}

	/**
	 * determina il controller da eseguire in base alla verifica passata in ingresso
	 * 
	 * @param id
	 * @return
	 */
	private ISignerController getControllerFromVerificationID(VerificationTypes id) {
		ISignerController ctrl = null;
		switch (id) {
		case CERTIFICATE_EXPIRATION:
			ctrl = new CertificateExpiration();
			break;
		case CA_VERIFY:
			ctrl = new CertificateReliability();
			break;
		case CRL_VERIFY:
			ctrl = new CertificateRevocation();
			break;
		default:
			break;
		}
		return ctrl;
	}

	private X509Certificate readCert() throws Exception {
		String fileName = "C:/spazilavoro/aurigaserv/CryptoSigner/src/test/resources/certificationAuthorities/elencoPubblico/Infocert/CA_InfoCertFirmaQualificata.crt";
		// String
		// fileName="C:/spazilavoro/aurigaserv/CryptoSigner/src/test/resources/certificationAuthorities/elencoPubblico/IntesaSanpaolo/CA_IntesaSanpaolo.cer";
		// String fileName="C:/spazilavoro/aurigaserv/CryptoSigner/src/test/resources/examples/testFiles/prova.txt.p7m";
		// String fileName="C:/spazilavoro/aurigaserv/CryptoSigner/src/test/resources/examples/esempi/license.txt.p7m";//non valido
		// String fileName="C:/spazilavoro/aurigaserv/CryptoSigner/src/test/resources/examples/esempi/InfoCert_Certification_Authority.cer";
		// String fileName="C:/spazilavoro/aurigaserv/CryptoSigner/src/test/resources/examples/esempi/rfc3161.txt.revocato.b64.p7m"; //non riesci a tirar fuori
		// nessun certificato

		// File cert = new File(");
		CertificateFactory factorys = CertificateFactory.getInstance("X509", BouncyCastleProvider.PROVIDER_NAME);
		FileInputStream fis = new FileInputStream(fileName);
		BufferedInputStream bis = new BufferedInputStream(fis);
		X509Certificate certificato = (X509Certificate) factorys.generateCertificate(bis);
		return certificato;

	}

}
