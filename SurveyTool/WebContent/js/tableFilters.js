'use strict'
var accesibilityTableRecords = "";
var accesibilityTableFind = "";
var accesibilityTableState = "";
var accesibleString = [];

function writeOnHtml(HTMLtext){
	var accessibilitySpan = document.getElementById('table_accesible_filters');
	
	if(!accessibilitySpan){
		var selector = document.getElementById('DataTables_Table_0_wrapper');
		var div = document.createElement('div');
		div.className = 'col-sm-12 center';
		
		accessibilitySpan = document.createElement('span');
		accessibilitySpan.setAttribute("id","table_accesible_filters");
		accessibilitySpan.setAttribute("aria-atomic","true");
		accessibilitySpan.setAttribute("aria-relevant","text");
		accessibilitySpan.setAttribute("aria-live","assertive");
		
		div.appendChild(accessibilitySpan)
		selector.childNodes[0].appendChild(div);
	}
	
	accessibilitySpan.innerHTML = HTMLtext;
}

function filteredByRecords(){
	var records =  document.getElementsByName('DataTables_Table_0_length')[0].value;
	records = (records == -1 )?'All':records;

	var tmpAccesibilityTableRecords = accesibilityTableRecords + ' ' + records;
	
	writeOnHtml(tmpAccesibilityTableRecords);
}

function filteredByFind(){
	var inputStr = document.getElementById('searchSurvey').value;
	var tmpAccesibilityTableFind = accesibilityTableFind + ' "' + inputStr + '"';
	
	writeOnHtml(tmpAccesibilityTableFind)
}

function filteredByState(){
	var state = document.getElementById('stateFilter').value;
	state = (state == '')?'All':state;
	var tmpAccesibilityTableState = accesibilityTableState + ' "' + state+'"';
	
	writeOnHtml(tmpAccesibilityTableState)
}



function prepareAccesibilityEvents() {
	setTimeout(function (){
		var selectLength = document.getElementsByName('DataTables_Table_0_length');
		selectLength[0].addEventListener('change',function(){ filteredByRecords() }, false);
		
		document.getElementById('searchSurvey').addEventListener('keyup',function(){ filteredByFind() }, false);

		document.getElementById('stateFilter').addEventListener('change',function(){ filteredByState() }, false);
	},400)
}