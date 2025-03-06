package it.eng.hybrid.module.stampaFiles.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.RenderedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.MultiDocPrintService;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.rendering.PDFRenderer;

import it.eng.hybrid.module.stampaFiles.bean.FileBean;
import it.eng.hybrid.module.stampaFiles.messages.MessageKeys;
import it.eng.hybrid.module.stampaFiles.messages.Messages;
import it.eng.hybrid.module.stampaFiles.preferences.PreferenceKeys;
import it.eng.hybrid.module.stampaFiles.preferences.PreferenceManager;
import it.eng.hybrid.module.stampaFiles.util.PrinterScanner;

public class PanelStampaFile  extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static Logger logger = Logger.getLogger(PanelStampaFile.class);
	
	private JPanel lJPanelPrincipal = null;

	private JButton jButtonStampa = null;
	private JButton jButtonStampaProperties = null;
	private JProgressBar jProgressBar = null;
	private JComboBox listaStampanti = null;

	private final String PRINT = "print";
	private final String PRINT_PROP = "printProperties";
	private final String SELECT_PRINTER = "selectPrinter";
	private final int DEFAULT_DPI = 300; // risoluzione di stampa di default nel caso non venisse trovata nessuna conf impostata

	private String stampanteDefault;
	private StampaFileApplication appl;

	private boolean skipUserInterface;
	private PrintRequestAttributeSet pras;
	private DocFlavor flavor;

	public PanelStampaFile(StampaFileApplication appl) {
		super(new BorderLayout());
		this.appl = appl;
		stampanteDefault = PreferenceManager.getString( PreferenceKeys.PROPERTY_STAMPANTE_SELEZIONATA );

		skipUserInterface = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SKIP_USER_INTERFACE );

	}
	
	public void init(){
		if( !skipUserInterface ){
			initialize();
		} else {
			PrintService service = mostraProprietaStampante();
			if (service != null){
				stampanteDefault = service.getName();
				if( listaStampanti!=null ){
					int indiceSelezionato = 0;
					for( int i = 0; i<listaStampanti.getItemCount(); i++){
						if( ((String)listaStampanti.getItemAt(i)).equalsIgnoreCase( stampanteDefault ) ){
							indiceSelezionato = i;
						}
					}
					listaStampanti.setSelectedIndex( indiceSelezionato );
				}
				
				stampa(service);
			} else {
				closeFrame(stampanteDefault);
			}
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		lJPanelPrincipal = new JPanel();
		lJPanelPrincipal.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		c.gridx=0;
		c.anchor=GridBagConstraints.NORTH;

		c.fill=GridBagConstraints.BOTH;

		c.gridx=0;
		c.weightx=1.0;
		c.insets = new Insets(20,10,2,10);
		int y=0;		

		c.gridy=y++;
		lJPanelPrincipal.add( createSelezioneStampantiPanel(), c );

//		c.gridy=y++;
//		lJPanelPrincipal.add( createFilePanel(), c );

		c.gridy=y++;
		c.anchor=GridBagConstraints.CENTER;
		c.insets = new Insets(10,10,10,10);
		lJPanelPrincipal.add( createStampaPanel(), c );

		add(lJPanelPrincipal, BorderLayout.NORTH);
	}

	private JPanel createSelezioneStampantiPanel(){
		JPanel lJPanel = new JPanel( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;

		JPanel panel1 = new JPanel( new GridBagLayout() );
		GridBagConstraints c1 = new GridBagConstraints();
		c1.insets = new Insets(2,0,2,10);
		c1.anchor = GridBagConstraints.LINE_START;

		JPanel panelPrinters = new JPanel(new GridLayout(0, 1));
		List<String> printersAvailable = (new PrinterScanner()).printerAvailable();
		listaStampanti = new JComboBox( printersAvailable.toArray() );
		int indiceSelezionato = 0;
		for( int i = 0; i<printersAvailable.size(); i++){
			if( printersAvailable.get(i).equalsIgnoreCase( stampanteDefault ) ){
				indiceSelezionato = i;
			}
		}
		listaStampanti.setSelectedIndex( indiceSelezionato );

		listaStampanti.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Ricevuto comando " + listaStampanti.getSelectedItem());
				stampanteDefault = (String) listaStampanti.getSelectedItem();
			}
		});

		panelPrinters.add(listaStampanti);

		c1.gridx = 1;
		c1.gridy = 0;
		panel1.add( panelPrinters, c1 );

		c.gridx = 0;
		c.gridy = 0;
		lJPanel.add( panel1, c);

		return lJPanel;
	}

	private JPanel createFilePanel(){
		JPanel panelFile = new JPanel( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.insets = new Insets(5, 0, 0, 0);

		JPanel signFilesPanel = new JPanel(new GridBagLayout());
		signFilesPanel.setBackground(Color.WHITE);

		JScrollPane scrollFilePanel = new JScrollPane(signFilesPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollFilePanel.setMinimumSize(new Dimension(300, 70));
		scrollFilePanel.setPreferredSize(new Dimension(300, 70));

		c.insets = new Insets(10, 0, 10, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth=2;
		//panelFile.add( scrollFilePanel, c );

		JLabel labelFile = new JLabel( );
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 0, 5, 10);
		c.gridwidth=2;
		panelFile.add( labelFile, c );

		return panelFile;
	}

	private JPanel createStampaPanel(){
		JPanel firmaPanel = new JPanel( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		//c.insets = new Insets(10, 10, 2, 10);
		c.gridy=0;
		firmaPanel.add( createBottonieraPanel(), c);

		c.gridy=1;
		firmaPanel.add( createProgressBarPanel(), c);
		//firmaPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );

		return firmaPanel;
	}

	private JPanel createBottonieraPanel(){
		//pannello per la bottoniera
		JPanel bottonieraPanel = new JPanel();

		bottonieraPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		bottonieraPanel.add(getJButtonStampa(),BorderLayout.CENTER);
		//bottonieraPanel.add(getJButtonStampaProperties(),BorderLayout.CENTER);

		//jButtonStampa.setVisible(false);
		jButtonStampa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		return bottonieraPanel;
	}
	
	private JPanel createProgressBarPanel(){
		//pannello per la bottoniera
		JPanel progressBarPanel = new JPanel();

		progressBarPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		getJProgressBar().setVisible(false);
		progressBarPanel.add(getJProgressBar());

		return progressBarPanel;
	}

	public JButton getJButtonStampa(){
		if (jButtonStampa == null) {
			URL picURL = getClass().getResource("printing.png");
			ImageIcon cup = new ImageIcon(picURL);
			jButtonStampa = new JButton(cup);
			jButtonStampa.setToolTipText( Messages.getMessage( MessageKeys.PANEL_BUTTON_PRINT ) );
			jButtonStampa.setActionCommand( PRINT );
			jButtonStampa.addActionListener(this);
		}
		return jButtonStampa;
	}

	public JButton getJButtonStampaProperties(){
		if (jButtonStampaProperties == null) {
			URL picURL = getClass().getResource("printing.png");
			ImageIcon cup = new ImageIcon(picURL);
			jButtonStampaProperties = new JButton(cup);
			jButtonStampaProperties.setToolTipText( Messages.getMessage( MessageKeys.PANEL_BUTTON_PRINT_PROPERTIES ) );
			jButtonStampaProperties.setActionCommand( PRINT_PROP );
			jButtonStampaProperties.addActionListener(this);
		}
		return jButtonStampaProperties;
	}

	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
		}
		return jProgressBar;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		String command=e.getActionCommand();
		logger.info("command:"+command);

		if( command.equalsIgnoreCase(PRINT_PROP)) {
			PrintService service = mostraProprietaStampante();
			if (service != null){
				stampanteDefault = service.getName();
				int indiceSelezionato = 0;
				for( int i = 0; i<listaStampanti.getItemCount(); i++){
					if( ((String)listaStampanti.getItemAt(i)).equalsIgnoreCase( stampanteDefault ) ){
						indiceSelezionato = i;
					}
				}
				listaStampanti.setSelectedIndex( indiceSelezionato );
				
				stampa(service);
			}
		}
		if( command.equalsIgnoreCase(PRINT)) {
			inviaInStampa();
		}

	}

	private static class JobCompleteMonitor extends PrintJobAdapter {
		private boolean completed = false;
		@Override
		public void printJobCanceled(PrintJobEvent pje) {
			logger.info("JobCanceled");
			signalCompletion();
		}

		@Override
		public void printJobCompleted(PrintJobEvent pje) {
			logger.info("JobCompleted");
			signalCompletion();
		}

		@Override
		public void printJobFailed(PrintJobEvent pje) {
			logger.info("JobFailed");
			signalCompletion();
		}

		@Override
		public void printJobNoMoreEvents(PrintJobEvent pje) {
			logger.info("JobNoMoreEvents" );
			signalCompletion();
		}
		
		private void signalCompletion() {

			synchronized (JobCompleteMonitor.this) {
				completed = true;
				JobCompleteMonitor.this.notify();
			}
		}
		
		public synchronized void waitForJobCompletion() {
			try {
				while (!completed) {
					wait();
					Thread.sleep(5000);
				}
			} catch (InterruptedException e) {
			}
		}

		public boolean isCompleted() {
			return completed;
		}

		public void setCompleted(boolean completed) {
			this.completed = completed;
		}
		
	}


	public PrintService mostraProprietaStampante(){
		flavor = DocFlavor.INPUT_STREAM.AUTOSENSE; 
		pras = new HashPrintRequestAttributeSet();

		PrintService printServices[] =PrintServiceLookup.lookupPrintServices(flavor, pras);
		logger.info("Print Service:"+printServices);

		PrintService defaultService = null;
		for(PrintService printService : printServices){
			if( printService.getName().equalsIgnoreCase( stampanteDefault )){
				defaultService = printService;
			}
		}

		//pras.add(new Copies(1));
		PrintService service = ServiceUI.printDialog(null, 200, 200,printServices, defaultService, flavor, pras);
		return service;
	}
	
	private PrintService creaPrintService(){
		flavor = DocFlavor.INPUT_STREAM.AUTOSENSE; 
		DocFlavor[] flavors = new DocFlavor[1];
		flavors[0] = flavor;
		pras = new HashPrintRequestAttributeSet();
		AttributeSet attributes  = pras;

		PrintService printServices[] =PrintServiceLookup.lookupPrintServices(flavor, pras);
		logger.info("Print Service:"+printServices);

		MultiDocPrintService[] printServices1 = PrintServiceLookup.lookupMultiDocPrintServices(flavors, attributes);
		logger.info("Print Service1:" + printServices1);
		for(PrintService printService : printServices1){
			System.out.println("-------------"+ printService.getName());
		}
		
		PrintService defaultService = null;
		for(PrintService printService : printServices){
			if( printService.getName().equalsIgnoreCase( stampanteDefault )){
				defaultService = printService;
			}
		}

		//pras.add(new Copies(1));
		return defaultService;
	}
	
	/**
	 * I test dei vari metodi possono essere effettuati con PDFCreator, impostando la dimensione della pagina di stampa uguale a quella effettiva che verrà usata nelle stampanti.
	 * In questo modo si può avere una idea del foglio che verrà inviato alla stampante
	 *  
	 * Ci sono vari metodi di stampa possibili, in quanto si è osservato che non tutti i metodi funzionano con tutte le stampanti
	 * 
	 * Metodi possibili:
	 * - printerJobPDFPageable: Utilizza un PrinterJob per la stampa e un PDFPageable per la paginazione. Su alcuni modelli di stampanti la paginazione non riesce bene
	 * - printerJobPrintable: Utilizza un PrinterJob per la stampa e un Printable per la paginazione. Su alcuni modelli di stampanti l'oggetto Printable non riceve 
	 *                        le dimensioni corrette della pagina di destinazione
	 * - printerJobBook: Utilizza un PrinterJob per la stampa, e un oggetto Book per la paginazione. Sembra essere la modalità con cui il PrinterJob funziona meglio
	 * - docPrintJob: Utilizza un DocPrintJob per la stampa. Non sembra dare problemi di paginazione, ma non tutte le stampanti funzionano con questo metodo di stampa             
	 * 
	 */
	private void stampa(PrintService service) {

		avviaStampa();
				
		logger.debug("Stampante: " + service.getName());

		List<FileBean> listFile = appl.getFileBeanListInput();
		String metodoDiStampa = PreferenceManager.getString("metodoStampaFile");
		PDDocument document = null;
		int risoluzioneDPIFile;
		
		try {
			risoluzioneDPIFile = PreferenceManager.getInt("risoluzioneDPIFile");
			logger.info("Trovata risoluzione di " + risoluzioneDPIFile + " dpi tra le conf "
					+ "per i metodi di stampa printerJobPrintable, printerJobBookPrintable");
		} catch (NoSuchElementException e) {
			risoluzioneDPIFile = DEFAULT_DPI;
			logger.info("Non � stata trovata nessuna configurazione per la risoluzione DPI della stampa dei file. "
					+ "Si utilizzer� quella di default (" + DEFAULT_DPI + " dpi) per i metodi di stampa printerJobPrintable, printerJobBookPrintable");
		}
		final int stampaDPI = risoluzioneDPIFile; // il campo viene utilizzato all'interno del metodo Printable.
		
		for (FileBean file : listFile) {
			try {
				document = PDDocument.load(file.getFile());
				final int docNumberPages = document.getNumberOfPages();
				final PDFRenderer pdfRenderer = new PDFRenderer(document);
				
				Printable printable = new Printable() {
	
					public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
						try {
							if ((pageIndex >= 0) && (pageIndex < docNumberPages)) {
								
								final RenderedImage image = pdfRenderer.renderImageWithDPI(pageIndex, stampaDPI);
								Graphics2D g2 = (Graphics2D) graphics;
								double widthScale = 1;
								double heightScale = 1;
								double xTranslate = 0;
								double yTranslate = 0;
								double rotation = 0;
									
								logger.debug("Dimensione pageFormat: Width= " + pageFormat.getWidth() + " Height= " + pageFormat.getHeight());
								logger.debug("Dimensione image: Width: " + image.getWidth() + " Height: " + image.getHeight());
								
								int imageRealHeight = image.getHeight(); 
								int imageRealWidth = image.getWidth(); 
								
								// Verifico se la pagina va ruotata
								if (!haveSameOrientation(image, pageFormat)) {
									// Devo ruotare la pagina, per semplificare il calcolo della scala inverto altezza e largheza
									imageRealHeight = image.getWidth(); 
									imageRealWidth = image.getHeight();
									rotation = Math.PI/2;
									xTranslate = image.getHeight();
									yTranslate = 0;
								}
								
								// Devo utilizzare un fattore di scala tale che l'immagine non sbordi in altezza o larghezza
								if (imageRealWidth >= pageFormat.getWidth()) {
									widthScale = pageFormat.getWidth() / imageRealWidth;
								}
								if (imageRealHeight >= pageFormat.getHeight()) {
									heightScale = pageFormat.getHeight() / imageRealHeight;
								}
		
								if (widthScale > heightScale) {
									widthScale = heightScale;
								} else {
									heightScale = widthScale;
								}
								
								logger.debug("Valori di scale: WidthScale= " + widthScale + " heightScale= " + heightScale);
								logger.debug("Valori di translate: xTraslate= " + xTranslate + " yTraslate= " + yTranslate);
								logger.debug("Valori di rotation: " + rotation);
		
								// Le trasformazioni affini vengono eseguite in ordine inverso, dell'ultima applicata alla prima
								AffineTransform at = new AffineTransform();						
								at.scale(widthScale, heightScale);
								at.translate(xTranslate, yTranslate);
								at.rotate(rotation,0, 0);
		
								g2.drawRenderedImage(image, at);
								return PAGE_EXISTS;
							} else {
								return NO_SUCH_PAGE;
							}
						} catch (IOException e) {
							logger.error("Errore nell'ottenimento della pagina (in renderImageWithDPI)");
						}
						return NO_SUCH_PAGE;
					}
					
					private boolean haveSameOrientation (RenderedImage image, PageFormat pageFormat) {
						//Controllo se sono entrambi verticali
						if (image.getHeight() >= image.getWidth() && pageFormat.getHeight() >= pageFormat.getWidth()) {
							// Sono entrambi verticali
							return true;
						}
						//Controllo se sono entrambi orizzontali
						if (image.getWidth() >= image.getHeight() && pageFormat.getWidth() >= pageFormat.getHeight()) {
							// Sono entrambi orizzontali
							return true;
						}
						// Uno è orizzontale e l'altro verticale
						return false;
						
					}
					
				};
				if ("printerJobPageable".equalsIgnoreCase(metodoDiStampa)) {
					logger.debug("Utilizzo metodo di stampa printerJobPageable");
					try {
						PrinterJob printerJob = PrinterJob.getPrinterJob();
						printerJob.setPrintService(service);
						printerJob.setPageable(new PDFPageable(document));
						printerJob.print();
					} catch (PrinterException e) {
						logger.error(e.getMessage(), e);
					}
				}else if ("printerJobPrintable".equalsIgnoreCase(metodoDiStampa)) {
					logger.debug("Utilizzo metodo di stampa printerJobPrintable");
					try {
						PrinterJob printerJob = PrinterJob.getPrinterJob();
						printerJob.setPrintService(service);
						PageFormat pf = printerJob.defaultPage();
						Paper paper = pf.getPaper();
					    double margin = 0;
					    paper.setImageableArea(margin, margin, paper.getWidth() - margin * 2, paper.getHeight() - margin * 2);
					    pf.setPaper(paper);  
						printerJob.setPrintable(printable, pf);
						printerJob.print();
					} catch (PrinterException e) {
						logger.error(e.getMessage(), e);
					}
				}else if ("printerJobPDFPrintable".equalsIgnoreCase(metodoDiStampa)) {
					logger.debug("Utilizzo metodo di stampa printerJobPDFPrintable");
					try {
						PrinterJob printerJob = PrinterJob.getPrinterJob();
						printerJob.setPrintService(service);
						PageFormat pf = printerJob.defaultPage();
						Paper paper = pf.getPaper();
					    double margin = 0;
					    paper.setImageableArea(margin, margin, paper.getWidth() - margin * 2, paper.getHeight() - margin * 2);
					    pf.setPaper(paper);  
						printerJob.setPrintable(new PDFPrintable(document), pf);
						printerJob.print();
					} catch (PrinterException e) {
						logger.error(e.getMessage(), e);
					}
				}else if ("printerJobBookPrintable".equalsIgnoreCase(metodoDiStampa)) {
					logger.debug("Utilizzo metodo di stampa printerJobBookPrintable");
					try {
						PrinterJob printerJob = PrinterJob.getPrinterJob();
						printerJob.setPrintService(service);
						PageFormat pf = printerJob.defaultPage();
						Paper paper = pf.getPaper();
					    double margin = 0;
					    paper.setImageableArea(margin, margin, paper.getWidth() - margin * 2, paper.getHeight() - margin * 2);
					    pf.setPaper(paper);  
						Book book = new Book();
						book.append(printable, pf, document.getNumberOfPages());
						printerJob.setPageable(book);
						printerJob.print();
					} catch (PrinterException e) {
						logger.error(e.getMessage(), e);
					}  	
				}else if ("printerJobBookPDFPrintable".equalsIgnoreCase(metodoDiStampa)) {
					logger.debug("Utilizzo metodo di stampa printerJobBookPDFPrintable");
					try {
						PrinterJob printerJob = PrinterJob.getPrinterJob();
						printerJob.setPrintService(service);
						PageFormat pf = printerJob.defaultPage();
						Paper paper = pf.getPaper();
					    double margin = 0;
					    paper.setImageableArea(margin, margin, paper.getWidth() - margin * 2, paper.getHeight() - margin * 2);
					    pf.setPaper(paper);  
						Book book = new Book();
						book.append(new PDFPrintable(document), pf, document.getNumberOfPages());
						printerJob.setPageable(book);
						printerJob.print();
					} catch (PrinterException e) {
						logger.error(e.getMessage(), e);
					}  
				}else {
					logger.debug("Utilizzo il metodo di stampa docPrintJob");
					if (file.getFile() != null) {
						logger.debug("Stampa in corso del file " + file.getFileName());
		
						DocPrintJob docPrintJob = service.createPrintJob();
		
						JobCompleteMonitor monitor = new JobCompleteMonitor();
						docPrintJob.addPrintJobListener(monitor);
		
						if (flavor == null)
							flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
						if (pras == null)
							pras = new HashPrintRequestAttributeSet();
		
						try {
							monitor.setCompleted(false);
							FileInputStream fis = new FileInputStream(file.getFile());
							Doc mydoc = new SimpleDoc(fis, flavor, null);
							docPrintJob.print(mydoc, pras);
		
							monitor.waitForJobCompletion();
							fis.close();
		
						} catch (PrintException e1) {
							logger.error(e1.getMessage(), e1);
						} catch (FileNotFoundException e1) {
							logger.error(e1.getMessage(), e1);
						} catch (IOException e1) {
							logger.error(e1.getMessage(), e1);
						}
					}
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			} finally {
				if (document != null) {
					try {
						document.close();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}

		fineStampa();
		closeFrame(service.getName());
	}

	public void inviaInStampa(){
		PrintService service = creaPrintService();
		if (service != null){
			stampa(service);
		}
	}
	
	public void avviaStampa(){
		getJProgressBar().setVisible(true);
		getJButtonStampa().setVisible(false);
		getJButtonStampaProperties().setVisible(false);
	}
	public void fineStampa(){
		getJProgressBar().setVisible(false);
		getJButtonStampa().setVisible(true);
		getJButtonStampaProperties().setVisible(true);
	}
	
	public void closeFrame(String stampanteSelezionata){
		appl.closeFrame(stampanteSelezionata);
	}
	
}
