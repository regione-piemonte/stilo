package it.eng.client.applet;

import it.eng.client.applet.connection.HttpConnection;
import it.eng.client.applet.model.FileListTableModel;
import it.eng.client.data.SignerTypeUtil;
import it.eng.common.bean.HashFileBean;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;

public class FileList extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable table;
	private FileListTableModel model;
	private File lastdirectory = null;
	private JButton btnConferma = null; 
	
	private String applicationurl = null;
	private String cookie = null;
	private ISmartCard card = null;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileList frame = new FileList("","",null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void setFileList(List<HashFileBean> files){
		model.setFiles(files);
	}
	
	
	/**
	 * Create the frame.
	 */
	public FileList(String url,String cookiestr,ISmartCard cardtmp){
		super();
		
		this.applicationurl = url;
		this.cookie = cookiestr;
		this.card = cardtmp;
		
		
		NimRODTheme nt = new NimRODTheme("it/eng/client/lookfeel/Default.theme");
		NimRODLookAndFeel nf = new NimRODLookAndFeel();
		NimRODLookAndFeel.setCurrentTheme(nt);
			
		try {
			UIManager.setLookAndFeel(nf);
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		model = new FileListTableModel();
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 422, 208);
		
		contentPane.add(scrollPane);
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setRowSelectionAllowed(true);
		table.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent evt) {
				
				//Recupero la colonna selezionata
				int col = table.getSelectedColumn();
				int row = table.getSelectedRow();
				HashFileBean bean = model.getFile(row);
				
				System.out.println("ID BEAN:"+bean.getId());
				
				if(col==1){
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					if(lastdirectory!=null){
						chooser.setCurrentDirectory(lastdirectory);
					}
					int option = chooser.showSaveDialog(FileList.this);
					if (option == JFileChooser.APPROVE_OPTION) {
						lastdirectory = chooser.getSelectedFile();
						File file = new File(chooser.getSelectedFile(),bean.getFileName());
						try {
							//TODO DA impostare come parametro di configurazione
							HttpConnection.downloadfile(applicationurl+"signerservlet", file, bean,null,cookie);				
						} catch (Exception e) {
							//TODO DA DEFINIRE L'ERRORE
							e.printStackTrace();
						}	
					}
					
					
				}else if(col==2){
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					if(lastdirectory!=null){
						chooser.setCurrentDirectory(lastdirectory);
					}
					int option = chooser.showOpenDialog(FileList.this);
					if (option == JFileChooser.APPROVE_OPTION) {
						File signerfile = chooser.getSelectedFile();
						
						//Effettuo un controllo sul file
						try {
							if(SignerTypeUtil.controlFile(signerfile, bean)){
								model.updatestatus(1, row);
							
								//Effettuo l'upload del file
								HttpConnection.uploadfile(applicationurl+"signerservlet", signerfile, bean, null,cookie);
								
							
							}else{
								model.updatestatus(2, row);
								JOptionPane.showMessageDialog((Component)FileList.this,"Il file associato non corrisponde al file originale!","Errore", JOptionPane.ERROR_MESSAGE);
							}
						} catch (Exception e) {
							model.updatestatus(2, row);
							JOptionPane.showMessageDialog((Component)FileList.this,"Errore nella verifica della firma!","Errore", JOptionPane.ERROR_MESSAGE);
						}finally{
							//Controllo lo stato del bottone
							btnConferma.setEnabled(model.isValid());
						}
										
					}
				}
			}
		});
		table.setModel(model);
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(24);
		model.initColumnSizes(table,scrollPane.getWidth());
		
		scrollPane.setViewportView(table);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		
		
		btnConferma = new JButton("Conferma");
		btnConferma.setIcon(new ImageIcon(FileList.class.getResource("/it/eng/client/applet/conferma.png")));
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Chiamo la fine della chiamata
				FileList.this.dispose();
				((SmartCard)card).getPanelsign().endSignerUpload();
			}
		});
		btnConferma.setBounds(180, 239, 120, 23);
		contentPane.add(btnConferma);
		btnConferma.setEnabled(false);
		

		
	}
}