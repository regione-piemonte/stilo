/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;

public class FilterDataSource extends DataSource {
	
	public FilterDataSource(DataSourceField[] fields){
		super();
		addRecursivelyOperator(I18NUtil.getMessages().operators_recursivelyTitle());
		addTipoOperator(I18NUtil.getMessages().operators_tipoTitle());
		addAllTheWordsOperator(I18NUtil.getMessages().operators_allTheWordsTitle());
		addOneOrMoreWordsOperator(I18NUtil.getMessages().operators_oneOrMoreWordsTitle()); 
		addWordsStartWithOperator(I18NUtil.getMessages().operators_wordsStartWithTitle());
		addLikeOperator(I18NUtil.getMessages().operators_likeTitle());
		addLastNDaysOperator(I18NUtil.getMessages().operators_lastNDaysTitle());
		setFields(fields);
		adjustOperatorTitles();		
    }
	
	// Per risolvere il problema delle label degli operatori nei filtri: 
	// ad es. nei filtri "data_senza_ora" (FieldType.CUSTOM) con operatore "equals" metteva la label 
	// "uguale a (case-insensitive)", che corrisponde alla label di "iEquals"
	public native void adjustOperatorTitles()/*-{
		var operators = $wnd.isc.DataSource.getSearchOperators();		
		operators.equals.titleProperty="equalsTitle";
		operators.equals.textTitleProperty="equalsTitle";				
		operators.notEqual.titleProperty="notEqualTitle";
		operators.notEqual.textTitleProperty="notEqualTitle";					
		operators.between.titleProperty="betweenTitle";
		operators.between.textTitleProperty="betweenTitle";		
		operators.betweenInclusive.titleProperty="betweenInclusiveTitle";
		operators.betweenInclusive.textTitleProperty="betweenInclusiveTitle";				
		operators.startsWith.titleProperty="startsWithTitle";
		operators.startsWith.textTitleProperty="startsWithTitle";		
		operators.endsWith.titleProperty="endsWithTitle";
		operators.endsWith.textTitleProperty="endsWithTitle";				
		operators.contains.titleProperty="containsTitle";
		operators.contains.textTitleProperty="containsTitle";				
		operators.notContains.titleProperty="notContainsTitle";
		operators.notContains.textTitleProperty="notContainsTitle";			
		operators.notStartsWith.titleProperty="notStartsWithTitle";
		operators.notStartsWith.textTitleProperty="notStartsWithTitle";		
		operators.notEndsWith.titleProperty="notEndsWithTitle";
		operators.notEndsWith.textTitleProperty="notEndsWithTitle";				
		operators.regexp.titleProperty="regexpTitle";
		operators.regexp.textTitleProperty="regexpTitle";			
	}-*/;
	
	public native void addRecursivelyOperator(String operatorTitle)/*-{
		$wnd.isc.Operators.addClassProperties({recursivelyTitle: operatorTitle});
		$wnd.isc.DataSource.addSearchOperator(
		        {
		            ID: 'recursively',
		            titleProperty: "recursivelyTitle",
		            valueType: "fieldType",
		            requiresServer: true,
		            hidden: false,
		            condition: function(value, record, fieldName, criterion, operator) { return true; }
		        }
		);
	}-*/;
	
	public native void addTipoOperator(String operatorTitle)/*-{
	    $wnd.isc.Operators.addClassProperties({tipoTitle: operatorTitle});
	    $wnd.isc.DataSource.addSearchOperator(
	            {
	                ID: 'tipo',
	                titleProperty: "tipoTitle",
	                valueType: "fieldType",
	                requiresServer: true,
	                hidden: false,
	                condition: function(value, record, fieldName, criterion, operator) { return true; }
	            }
	    );
	}-*/;
	
	public native void addAllTheWordsOperator(String operatorTitle)/*-{
		$wnd.isc.Operators.addClassProperties({allTheWordsTitle: operatorTitle});
		$wnd.isc.DataSource.addSearchOperator(
		        {
		            ID: 'allTheWords',
		            titleProperty: "allTheWordsTitle",
		            valueType: "fieldType",
		            requiresServer: true,
		            hidden: false,
		            condition: function(value, record, fieldName, criterion, operator) { return true; }
		        }
		);
	}-*/;

	public native void addOneOrMoreWordsOperator(String operatorTitle)/*-{
	    $wnd.isc.Operators.addClassProperties({oneOrMoreWordsTitle: operatorTitle});
	    $wnd.isc.DataSource.addSearchOperator(
	            {
	                ID: 'oneOrMoreWords',
	                titleProperty: "oneOrMoreWordsTitle",
	                valueType: "fieldType",
	                requiresServer: true,
	                hidden: false,
	                condition: function(value, record, fieldName, criterion, operator) { return true; }
	            }
	    );
	}-*/;

	public native void addWordsStartWithOperator(String operatorTitle)/*-{
	    $wnd.isc.Operators.addClassProperties({wordsStartWithTitle: operatorTitle});
	    $wnd.isc.DataSource.addSearchOperator(
	            {
	                ID: 'wordsStartWith',
	                titleProperty: "wordsStartWithTitle",
	                valueType: "fieldType",
	                requiresServer: true,
	                hidden: false,
	                condition: function(value, record, fieldName, criterion, operator) { return true; }
	            }
	    );
	}-*/;
	
	public native void addLikeOperator(String operatorTitle)/*-{
	    $wnd.isc.Operators.addClassProperties({likeTitle: operatorTitle});
	    $wnd.isc.DataSource.addSearchOperator(
	            {
	                ID: 'like',
	                titleProperty: "likeTitle",
	                valueType: "fieldType",
	                requiresServer: true,
	                hidden: false,
	                condition: function(value, record, fieldName, criterion, operator) { return true; }
	            }
	    );
	}-*/;
	
	public native void addLastNDaysOperator(String operatorTitle)/*-{
	    $wnd.isc.Operators.addClassProperties({lastNDaysTitle: operatorTitle});
	    $wnd.isc.DataSource.addSearchOperator(
	            {
	                ID: 'lastNDays',
	                titleProperty: "lastNDaysTitle",
	                valueType: "fieldType",
	                requiresServer: true,
	                hidden: false,
	                condition: function(value, record, fieldName, criterion, operator) { return true; }
	            }
	    );
	}-*/;
	
}
