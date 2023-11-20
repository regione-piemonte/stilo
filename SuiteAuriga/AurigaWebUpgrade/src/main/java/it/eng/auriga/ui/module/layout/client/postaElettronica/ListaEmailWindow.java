/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ListaEmailWindow extends ModalWindow
{
	private RecordList listaRecords;
	private boolean isMassive;
	private CustomLayout postaElettonicaLayout;
	private BricioleDiPaneToolStrip briciole;
	
	public ListaEmailWindow(RecordList listaRecords,BricioleDiPaneToolStrip dettPE)
	{
		super("tree_email", true);
		setListaRecords(listaRecords);
		setBriciole(dettPE);
		designWindow();
	}
	
	public RecordList getListaRecords() {
		return listaRecords;
	}

	public void setListaRecords(RecordList listaRecords) {
		this.listaRecords = listaRecords;
	}

	public boolean isMassive() {
		return isMassive;
	}

	public void setMassive(boolean isMassive) {
		this.isMassive = isMassive;
	}

	public CustomLayout getPostaElettonicaLayout() {
		return postaElettonicaLayout;
	}

	public void setPostaElettonicaLayout(CustomLayout postaElettonicaLayout) {
		this.postaElettonicaLayout = postaElettonicaLayout;
	}

	public BricioleDiPaneToolStrip getBriciole() {
		return briciole;
	}

	public void setBriciole(BricioleDiPaneToolStrip briciole) {
		this.briciole = briciole;
	}

	private void designWindow(){
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		//setWidth(700);		
		//setHeight(400);
		setKeepInParentRect(true);
		setTitle("Lista Email");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		
		setShowTitle(true);
		

        Tree tree  = new Tree();  
		tree.setModelType(TreeModelType.PARENT);  
		tree.setIdField("idEmail");
		tree.setParentIdField("idEmailColl");
		tree.setNameProperty("Name");
		tree.setRootValue(1);
		
		tree.setData(getDataRoot());   
		
		
		final TreeGrid treeGrid = new TreeGrid();  
        //treeGrid.setLoadDataOnDemand(false);  
        treeGrid.setWidth(780);  
        treeGrid.setHeight(360);  
        //treeGrid.setDataSource(employeeDS);  
        treeGrid.setNodeIcon("buttons/send.png");  
        treeGrid.setFolderIcon("buttons/send.png");  
        treeGrid.setShowOpenIcons(false);  
        treeGrid.setShowDropIcons(false);  
        treeGrid.setClosedIconSuffix("");  
        treeGrid.setAutoFetchData(true);  
        treeGrid.setData(tree);
  
        TreeGridField field = new TreeGridField();  
        field.setName("Name");  
        field.setCellFormatter(new CellFormatter() 
        {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,int colNum) 
			{
				/*String d = "";
				
				if(record.getAttribute("flgIO").equals("I"))
					d = record.getAttribute("accountMittente");
				else
					d = record.getAttribute("destinatariPrincipali");
				
				String descUO =  record.getAttribute("desUOAssegnataria") != null ? record.getAttribute("desUOAssegnataria") : "";
				
				return record.getAttribute("id") + ": " + record.getAttribute("tipo") + "- " + record.getAttribute("sottotipo") + " " + record.getAttribute("tsInvio") + " " + descUO + " " + d;
				*/
				return record.getAttribute("descrizione");
			}
		});
        
       /* TreeGridField mittente = new TreeGridField();  
        mittente.setName("Mittente");  
        mittente.setCellFormatter(new CellFormatter() 
        {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,int colNum) 
			{
				String d = "";
				
				if(record.getAttribute("flgIO").equals("I"))
					d = record.getAttribute("accountMittente");
				
				return d;  
			}
		});
        
        
        TreeGridField destinatari = new TreeGridField();  
        destinatari.setName("Destinatari");  
        destinatari.setCellFormatter(new CellFormatter() 
        {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,int colNum) 
			{
				String d = "";
				
				if(!record.getAttribute("flgIO").equals("I"))
					d = record.getAttribute("destinatariPrincipali")!= null ? record.getAttribute("destinatariPrincipali") : " TODO ";
				
				return d;  
			}
		});
		
        treeGrid.setFields(field,mittente,destinatari);  
  */
        treeGrid.setFields(field);
        
        treeGrid.addCellClickHandler(new CellClickHandler() {			
			@Override
			public void onCellClick(CellClickEvent event) {
				event.cancel();
				manageOnCellClick(event);
			}

			
		});
		
		
		
		
		//tree.setData(nodi);	
//		treeGrid.addDrawHandler(new DrawHandler() {  
//			public void onDraw(DrawEvent event) { 
//				for (TreeNode lTreeNode : finalOpenFolders)
//					tree.openFolder(lTreeNode);
//			}  
//		});  

		
		
		
        
        
        /*treeGrid.addDataArrivedHandler(new DataArrivedHandler() 
        {
		    public void onDataArrived(DataArrivedEvent event) {  
                treeGrid.getData().openAll();  
            }  
        });  */
  
        addItem(treeGrid);
        
        
	}
	
	private void manageOnCellClick(CellClickEvent event) 
	{
		
		Record record = event.getRecord();
		getBriciole().caricaLivello(record);
		
		//this.markForDestroy();
		
	}
	
	private TreeNode[] getDataRoot() 
	{
		RecordList livelliPredecessori = getListaRecords();
		
		
		TreeNode[] nodi = new TreeNode[livelliPredecessori.getLength()];
		
		
		/*String  prec = "1";
		int count =0;
		for (int i = 0; livelliPredecessori != null  && i < livelliPredecessori.getLength(); i++) 
		{
			Record recordNodo = livelliPredecessori.get(i);
			
			
				EmailTreeNode lTreeNode = new EmailTreeNode(recordNodo,prec);
			
				prec = recordNodo.getAttribute("idEmail");
				nodi[count] = lTreeNode;
				count++;
			
		}*/
		
		
		EmailTreeNode lTreeRoot = new EmailTreeNode(livelliPredecessori.get(0),"1");
		nodi[0] = lTreeRoot;  
		String  root = livelliPredecessori.get(0).getAttribute("idEmail");
		
		int count =1;
		for (int i = 1; livelliPredecessori != null  && i < livelliPredecessori.getLength(); i++) 
		{
			Record recordNodo = livelliPredecessori.get(i);
			
			
				EmailTreeNode lTreeNode = new EmailTreeNode(recordNodo,root);
			
				//root = recordNodo.getAttribute("idEmail");
				nodi[count] = lTreeNode;
				count++;
			
		}
		
		
		
		/*TreeNode [] nodi = new TreeNode[] {
			      new EmailTreeNode(livelliPredecessori.get(0).getAttribute("idEmail"), "1", livelliPredecessori.get(0).getAttribute("id")),
			      new EmailTreeNode(livelliPredecessori.get(2).getAttribute("idEmail"), livelliPredecessori.get(0).getAttribute("idEmail"), livelliPredecessori.get(2).getAttribute("id")),
			      new EmailTreeNode(livelliPredecessori.get(4).getAttribute("idEmail"), livelliPredecessori.get(2).getAttribute("idEmail"), livelliPredecessori.get(4).getAttribute("id")),
			      new EmailTreeNode(livelliPredecessori.get(6).getAttribute("idEmail"), livelliPredecessori.get(2).getAttribute("idEmail"), livelliPredecessori.get(6).getAttribute("id")),
			      new EmailTreeNode(livelliPredecessori.get(8).getAttribute("idEmail"), livelliPredecessori.get(0).getAttribute("idEmail"), livelliPredecessori.get(8).getAttribute("id")) };
		*/
		return nodi;
		
	}

	
	public class EmailTreeNode extends TreeNode 
	{  
        public EmailTreeNode(Record record, String IdEmailColl) {  
            this(record,IdEmailColl,new EmailTreeNode[] {});  
        }  
  
        public EmailTreeNode(Record record, String IdEmailColl,EmailTreeNode... children) {  
            setAttribute("idEmail", record.getAttribute("idEmail"));  
            setAttribute("idEmailColl", IdEmailColl);  
            setAttribute("descrizione", record.getAttribute("descrizione"));
            /*setAttribute("id", record.getAttribute("id"));  
            setAttribute("categoria",record.getAttribute("categoria"));
			setAttribute("tipo",record.getAttribute("tipo"));
			setAttribute("sottotipo",record.getAttribute("sottotipo"));
			setAttribute("tsInvio",record.getAttribute("tsInvio"));
			setAttribute("accountMittente",record.getAttribute("accountMittente"));
			setAttribute("desUOAssegnataria",record.getAttribute("desUOAssegnataria"));
			setAttribute("destinatariPrincipali",record.getAttribute("destinatariPrincipali"));
			setAttribute("flgIO",record.getAttribute("flgIO"));*/
		
        }  
    }
}
