/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.csi.siac.stilo.svc._1_0;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.2.5
 * 2020-05-26T19:01:17.513+02:00
 * Generated source version: 3.2.5
 *
 */
public final class StiloService_StiloServicePort_Client {

    private static final QName SERVICE_NAME = new QName("http://siac.csi.it/stilo/svc/1.0", "StiloService");

    private StiloService_StiloServicePort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = StiloService_Service.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        StiloService_Service ss = new StiloService_Service(wsdlURL, SERVICE_NAME);
        StiloService port = ss.getStiloServicePort();

        {
        System.out.println("Invoking elaboraAttiAmministrativiAsync...");
        it.csi.siac.documenti.svc._1.ElaboraAttiAmministrativi _elaboraAttiAmministrativiAsync_elaboraAttiAmministrativiAsync = null;
        it.csi.siac.documenti.svc._1.ElaboraAttiAmministrativiAsyncResponse _elaboraAttiAmministrativiAsync__return = port.elaboraAttiAmministrativiAsync(_elaboraAttiAmministrativiAsync_elaboraAttiAmministrativiAsync);
        System.out.println("elaboraAttiAmministrativiAsync.result=" + _elaboraAttiAmministrativiAsync__return);


        }
        {
        System.out.println("Invoking elaboraAttiAmministrativi...");
        it.csi.siac.documenti.svc._1.ElaboraAttiAmministrativi _elaboraAttiAmministrativi_elaboraAttiAmministrativi = null;
        it.csi.siac.documenti.svc._1.ElaboraAttiAmministrativiResponse _elaboraAttiAmministrativi__return = port.elaboraAttiAmministrativi(_elaboraAttiAmministrativi_elaboraAttiAmministrativi);
        System.out.println("elaboraAttiAmministrativi.result=" + _elaboraAttiAmministrativi__return);


        }
        {
        System.out.println("Invoking ricercaMovimentoGestione...");
        it.csi.siac.stilo.svc._1.RicercaMovimentoGestioneStilo _ricercaMovimentoGestione_ricercaMovimentoGestione = null;
        it.csi.siac.stilo.svc._1.RicercaMovimentoGestioneStiloResponse _ricercaMovimentoGestione__return = port.ricercaMovimentoGestione(_ricercaMovimentoGestione_ricercaMovimentoGestione);
        System.out.println("ricercaMovimentoGestione.result=" + _ricercaMovimentoGestione__return);


        }

        System.exit(0);
    }

}
