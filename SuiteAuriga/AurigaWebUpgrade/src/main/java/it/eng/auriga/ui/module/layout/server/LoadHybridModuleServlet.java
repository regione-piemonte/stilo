/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import it.eng.areas.hybrid.deploy.HybridConfig;

@SuppressWarnings("serial")
public class LoadHybridModuleServlet extends HttpServlet {

	Logger logger = Logger.getLogger(LoadHybridModuleServlet.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			// Carico SignerMultiHybridBundle
			File bundleFileSignerMulti;
			bundleFileSignerMulti = new File(config.getServletContext().getRealPath("/hybrid/plugins/SignerMultiHybridBundle.jar"));
			logger.info("Servlet - bundleFile " + bundleFileSignerMulti);
			HybridConfig.getInstance().registerModule(new HybridConfig.UrlModuleFactory(bundleFileSignerMulti.toURI().toURL()));

			// Carico PrintScannerHybridBundle
			File bundleFilePrintScanner;
			bundleFilePrintScanner = new File(config.getServletContext().getRealPath("/hybrid/plugins/PrinterScannerHybridBundle.jar"));
			logger.info("Servlet - bundleFile " + bundleFilePrintScanner);
			HybridConfig.getInstance().registerModule(new HybridConfig.UrlModuleFactory(bundleFilePrintScanner.toURI().toURL()));
			
			// Carico PortScannerHybridBundle
			File bundleFilePortScanner;
			bundleFilePortScanner = new File(config.getServletContext().getRealPath("/hybrid/plugins/PortScannerHybridBundle.jar"));
			logger.info("Servlet - bundleFile " + bundleFilePortScanner);
			HybridConfig.getInstance().registerModule(new HybridConfig.UrlModuleFactory(bundleFilePortScanner.toURI().toURL()));

			// Carico StampaEtichetteHybridBundle
			File bundleFileStampaEtichette;
			bundleFileStampaEtichette = new File(config.getServletContext().getRealPath("/hybrid/plugins/StampaEtichetteHybridBundle.jar"));
			logger.info("Servlet - bundleFile " + bundleFileStampaEtichette);
			HybridConfig.getInstance().registerModule(new HybridConfig.UrlModuleFactory(bundleFileStampaEtichette.toURI().toURL()));

			// Carico WordOpenerHybridBundle
			File bundleFileWordOpener;
			bundleFileWordOpener = new File(config.getServletContext().getRealPath("/hybrid/plugins/WordOpenerHybridBundle.jar"));
			logger.info("Servlet - bundleFile " + bundleFileWordOpener);
			HybridConfig.getInstance().registerModule(new HybridConfig.UrlModuleFactory(bundleFileWordOpener.toURI().toURL()));

			// Carico ScanHybridBundle
			File bundleScan;
			bundleScan = new File(config.getServletContext().getRealPath("/hybrid/plugins/ScanHybridBundle.jar"));
			logger.info("Servlet - bundleFile " + bundleScan);
			HybridConfig.getInstance().registerModule(new HybridConfig.UrlModuleFactory(bundleScan.toURI().toURL()));

			// Carico PieChartHybridBundle
			File bundlePieChart;
			bundlePieChart = new File(config.getServletContext().getRealPath("/hybrid/plugins/PieChartHybridBundle.jar"));
			logger.info("Servlet - bundleFile " + bundlePieChart);
			HybridConfig.getInstance().registerModule(new HybridConfig.UrlModuleFactory(bundlePieChart.toURI().toURL()));

			// Carico SelectCertificatoBundle
			File bundleSelectCertificato;
			bundleSelectCertificato = new File(config.getServletContext().getRealPath("/hybrid/plugins/SelectCertificatoHybridBundle.jar"));
			logger.info("Servlet - bundleFile " + bundleSelectCertificato);
			HybridConfig.getInstance().registerModule(new HybridConfig.UrlModuleFactory(bundleSelectCertificato.toURI().toURL()));

			// Carico FirmaCertificatoBundle
			File bundleFirmaCertificato;
			bundleFirmaCertificato = new File(config.getServletContext().getRealPath("/hybrid/plugins/FirmaCertificatoHybridBundle.jar"));
			logger.info("Servlet - bundleFile " + bundleFirmaCertificato);
			HybridConfig.getInstance().registerModule(new HybridConfig.UrlModuleFactory(bundleFirmaCertificato.toURI().toURL()));

			// Carico PrinterHybridFiles
			File bundlePrinterFiles;
			bundlePrinterFiles = new File(config.getServletContext().getRealPath("/hybrid/plugins/StampaFilesHybridBundle.jar"));
			logger.info("Servlet - bundleFile " + bundlePrinterFiles);
			HybridConfig.getInstance().registerModule(new HybridConfig.UrlModuleFactory(bundlePrinterFiles.toURI().toURL()));

			// File bundleFile3;
			// bundleFile3 = new File(config.getServletContext().getRealPath("/hybrid/plugins/ScanHybridBundle_1.0.0.9999.jar"));
			// logger.info("Servlet - bundleFile " + bundleFile3);
			// HybridConfig.getInstance().registerModule(new HybridConfig.UrlModuleFactory(bundleFile3.toURI().toURL()));
			//
			// File bundleFile4;
			// bundleFile4 = new File(config.getServletContext().getRealPath("/hybrid/plugins/SISSHybridBundle_1.0.0.9999.jar"));
			// logger.info("Servlet - bundleFile " + bundleFile4);
			// HybridConfig.getInstance().registerModule(new HybridConfig.UrlModuleFactory(bundleFile4.toURI().toURL()));
			//
			// File bundleFile5;
			// bundleFile5 = new File(config.getServletContext().getRealPath("/WEB-INF/hybrid/plugins/JpedalHybridBundle_1.0.0.9999.jar"));
			// logger.info("Servlet - bundleFile " + bundleFile5);
			// HybridConfig.getInstance().registerModule(new HybridConfig.UrlModuleFactory(bundleFile5.toURI().toURL()));

		} catch (Exception e) {
			throw new ServletException(e);

		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
