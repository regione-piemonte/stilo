/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.StringUtil;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.TileRecord;
import com.smartgwt.client.widgets.tile.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordDoubleClickHandler;

public class MultiLookupTileGrid extends TileGrid {

	private CustomLayout layout;
	private List<TileRecord> multiLookupData = new ArrayList<TileRecord>();
		
	public MultiLookupTileGrid(CustomLayout pLayout) {
		// TODO Auto-generated constructor stub
		
		layout = pLayout;
		
		setTileHeight(40);
		setTileWidth(80);
		setTileHMargin(2);
		setTileVMargin(2);
		setHeight100();
		setWidth100();
		setBackgroundColor("white");
		setMargin(0);
		setShowEdges(false);
		setAutoFetchData(true);
		setShowAllRecords(true);	
		setDataSource(new MultiLookupDataSource());
		setSelectionType(SelectionStyle.NONE);
		setCanHover(true);
		setShowHover(true);
		setShowHoverComponents(true);
		setOverflow(Overflow.AUTO);
				
		addRecordDoubleClickHandler(new RecordDoubleClickHandler() {			
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
						
				if(removeRecord(event.getRecord())) {
					layout.multiLookupUndo(event.getRecord());
				}
			}
		});		
				
	}	
	
	@Override
	protected String getTileHTML(Record record) {
		
//		return super.getTileHTML(record);
		String icona = record.getAttributeAsString("icona");
		String nome = record.getAttributeAsString("nome");
		return "<TABLE BORDER=0 CELLSPACING=0 CLASS=normal WIDTH='100%' CELLPADDING=0 STYLE='table-layout:fixed' title='" + StringUtil.asHTML(nome) + "'>" +
			   "<TR><TD CLASS='tileValue' style='overflow:hidden;text-align:center;' NOWRAP><NOBR>" +
			   "<img src='images/" + icona + "' height=\"16\" width=\"16\" align='TEXTTOP' border='0' suppress='TRUE'/></TD></TR>" +
			   "<TR><TD CLASS='tileValue' style='overflow:hidden;text-align:center;' NOWRAP><NOBR>" + nome + "</TD></TR></TABLE>";	
	}
	
	public Boolean addRecord(Record record) {
		
		boolean trovato = false;
		for(TileRecord tileRecord : multiLookupData) {			
			if(tileRecord.getAttributeAsString("id").equals(record.getAttributeAsString("id"))) {
				trovato = true;
				break;
			}
		}
		if(!trovato) {
			TileRecord newRecord = new TileRecord(record.getJsObj());				
			multiLookupData.add(newRecord);						
			TileRecord[] data = new TileRecord[multiLookupData.size()];		
			for(int i = 0; i < data.length; i++) {
				data[i] = multiLookupData.get(i);
			}
			setData(data);										
		}
		return !trovato;
	}				
	
	public Boolean removeRecord(Record record) {
		
		boolean cancellato = false;
		for(TileRecord rec : multiLookupData) {
			if(rec.getAttributeAsString("id").equals(record.getAttributeAsString("id"))) {
				cancellato = multiLookupData.remove(rec);
				break;
			}
		}
		if(cancellato) {
			TileRecord[] data = new TileRecord[multiLookupData.size()];		
			for(int i = 0; i < data.length; i++) {
				data[i] = multiLookupData.get(i);
			}
			setData(data);
		}	
		return cancellato;
	}
    
}
