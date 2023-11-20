/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ProcessTreeWindow extends ModalWindow {
	
	private ProcessTreeWindow _window;
	private TreeGrid treeGrid;
	private Tree tree;

	public ProcessTreeWindow(String nomeEntita) {
		super(nomeEntita);
		

		_window = this;
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		initTree();
	}
	
	private void initTree() {
		treeGrid = new TreeGrid();  
		treeGrid.setWidth100() ; 
		treeGrid.setHeight100();

		TreeGridField field = new TreeGridField("title", "Nome procedimento");  
		field.setCanSort(false);  
		field.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				
				return record.getAttributeAsString("altToShow");
			}
		});
		
		treeGrid.setFields(field); 
		treeGrid.setShowHeader(false);
		treeGrid.setCanHover(true);
		tree  = new Tree();  
		tree.setModelType(TreeModelType.PARENT);  
		tree.setNameProperty("title");  
		tree.setIdField("id");  
		tree.setParentIdField("parent");  
		
		
		addItem(treeGrid);
		
	}

	public void loadRecord(String idProcesso, String nomeFascicolo){
		setTitle("Iter " + nomeFascicolo);
		Record lRecord = new Record();
		lRecord.setAttribute("idProcesso", idProcesso);
		GWTRestService<Record, Record> lGwtRestService =
			new GWTRestService<Record, Record>("AlberoProcessoDatasource");
		lGwtRestService.call(lRecord, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				popolaTree(object);
			}
		});
	}
	
	protected void popolaTree(Record lRecord) {
		Record[] lRecordNodi = lRecord.getAttributeAsRecordArray("nodi"); 
		TreeNode[] nodi = new TreeNode[lRecordNodi.length];
		int count = 0;
		for (Record recordNodo : lRecordNodi){
			TreeNode lTreeNode = new TreeNode();
			lTreeNode.setAttribute("id", recordNodo.getAttribute("id"));
			lTreeNode.setAttribute("title", recordNodo.getAttribute("title"));
			lTreeNode.setAttribute("altToShow", recordNodo.getAttribute("altToShow"));
			lTreeNode.setAttribute("parent", recordNodo.getAttribute("parent"));
			if (lTreeNode.getAttribute("parent") != null && !lTreeNode.getAttribute("parent").equals("")){
				lTreeNode.setIcon("buttons/gear.png");
			}
			nodi[count] = lTreeNode;
			count++;
		}
		tree.setData(nodi);	
//		treeGrid.addDrawHandler(new DrawHandler() {  
//			public void onDraw(DrawEvent event) { 
//				for (TreeNode lTreeNode : finalOpenFolders)
//					tree.openFolder(lTreeNode);
//			}  
//		});  

		treeGrid.setData(tree);
		show();
	}

	
}
