'use strict'
var accesibilityGeneral = "";
var accesibilityTableRecords = "";
var accesibilityTableFind = "";
var accesibilityTableState = "";
var accesibilityIds = [
	'survey_records',
	'survey_find',
	'survey_status',
	'poll_records',
	'poll_find'
];

function writeOnHtml(ariaElement, text){
	clearPreviousMessages();
	
	setTimeout(function (){
		clearPreviousMessages();
		ariaElement.innerHTML = accesibilityGeneral;
		ariaElement.appendChild(text);
	}, 400);
}
function clearPreviousMessages(){
	for (var id of accesibilityIds){
		var el = document.getElementById(id);
		if(el){
			while(el.hasChildNodes()){
				el.removeChild(el.childNodes[0])
			}
		}
		//if(el) el.parentElement.removeChild(el);
	}
}
function createSpan(hiddenText){
	/*var accesibilityDiv = document.createElement('div');
	accesibilityDiv.setAttribute("aria-atomic","true");
	accesibilityDiv.setAttribute("aria-relevant","text");
	accesibilityDiv.setAttribute("aria-live","assertive");
	accesibilityDiv.innerHTML= accesibilityGeneral*/
	
	var accesibilitySpan = document.createElement('span');
	accesibilitySpan.className = 'sr-only';
	accesibilitySpan.innerHTML= hiddenText;
	
	//accesibilityDiv.appendChild(accesibilitySpan);
	
	return accesibilitySpan;
}
function createDiv(){
	var div = document.createElement('div');
	div.setAttribute("aria-live","assertive");
	div.setAttribute("aria-atomic","true");
	div.setAttribute("aria-relevant","text");
	return div;
}
function initDivs(){
	
	var accesibilityDiv = createDiv();
	accesibilityDiv.setAttribute('id','survey_status')
	document.getElementById('divStateFilter').appendChild(accesibilityDiv);
	
	accesibilityDiv = createDiv();
	accesibilityDiv.setAttribute('id','survey_find')
	document.getElementById('divSearchSurvey').childNodes[3].appendChild(accesibilityDiv);
	
	var pollFind = document.getElementById('DataTables_Table_1_filter');
	if(pollFind){
		accesibilityDiv = createDiv();
		accesibilityDiv.setAttribute('id','poll_find')
		accesibilityDiv.setAttribute('style', 'margin-left: 4.4em;text-align: left;')
		pollFind.firstChild.appendChild(accesibilityDiv);
	}
	
	var surveyRecord = document.getElementById('DataTables_Table_0_length');
	if(surveyRecord){
		accesibilityDiv = createDiv();
		accesibilityDiv.setAttribute('id','survey_records')
		surveyRecord.appendChild(accesibilityDiv);
	}
	
	var pollRecord = document.getElementById('DataTables_Table_1_length');
	if(pollRecord){
		accesibilityDiv = createDiv();
		accesibilityDiv.setAttribute('id','poll_records')
		pollRecord.appendChild(accesibilityDiv);
	}
	
	var surveyStatus = document.getElementById('DataTables_Table_1_length');
	if(surveyStatus){
		accesibilityDiv = createDiv();
		accesibilityDiv.setAttribute('id','survey_status')
		surveyStatus.appendChild(accesibilityDiv);
	}
	
	
	
}
function filteredByRecords(isPoll){
	isPoll=(isPoll)?isPoll:false;
	
	var recordsItem;
	var recordsValue;
	var node;
	var accesibilitySpan;
	var changedMessage;
	
	if(isPoll){
		
		node = document.getElementById('poll_records');
		recordsItem = document.getElementById('DataTables_Table_1_length').firstElementChild.firstElementChild;
		recordsValue = (recordsItem.value == -1)?'All':recordsItem.value;
		
		changedMessage = accesibilityTableRecords.replace('$VAL', recordsValue);
		accesibilitySpan = createSpan(', '+changedMessage);
	}else{
		node = document.getElementById('survey_records');
		recordsItem = document.getElementById('DataTables_Table_0_length').firstElementChild.firstElementChild;
		recordsValue = (recordsItem.value == -1)?'All':recordsItem.value;
				
		changedMessage = accesibilityTableRecords.replace('$VAL', recordsValue);
		accesibilitySpan = createSpan(', '+changedMessage);
		
	}
	
	writeOnHtml(node, accesibilitySpan);
	
	/*
	var recordsItem =  document.getElementsByName('DataTables_Table_0_length');
	var recordsValue = (recordsItem[0].value == -1 )?'All':records;

	var tmpAccesibilityTableRecords = accesibilityTableRecords + ' ' + records;
	
	writeOnHtml(tmpAccesibilityTableRecords);*/
}

function filteredByFind(isPoll){
	isPoll=(isPoll)?isPoll:false;
	var inputItem;
	var node;
	var accesibilitySpan
	
	if(isPoll){
		node = document.getElementById('poll_find');
		inputItem = document.getElementById('DataTables_Table_1_filter').firstChild.firstElementChild;
		
		accesibilitySpan = createSpan(accesibilityTableFind + '"'+ inputItem.value+'"');
		//accesibilitySpan.setAttribute('id','poll_find');
		
	}else {
		
		node = document.getElementById('survey_find');
		inputItem = document.getElementById('searchSurvey');
		
		//accesibilityDiv = createDiv(accesibilityTableFind + '"'+ inputItem.value+'"');
		accesibilitySpan = createSpan(accesibilityTableFind + '"'+ inputItem.value+'"');
		//accesibilityDiv.setAttribute('id','survey_find');
	}
	
	writeOnHtml(node, accesibilitySpan);
}

function filteredByState(){
	var stateItem = document.getElementById('stateFilter');
	var stateValue = (stateItem.value == '')?'All':stateItem.value;
	var node = document.getElementById('survey_status');
	
	var accesibilitySpan = createSpan(accesibilityTableState + ' "' + stateValue+'"');
	
	writeOnHtml(node,accesibilitySpan)
}



function prepareAccesibilityEvents() {
	setTimeout(function (){
		initDivs();
		var surveyRecords = document.getElementsByName('DataTables_Table_0_length');
		surveyRecords[0].addEventListener('change',function(){ filteredByRecords() }, false);
		
		var pollRecords = document.getElementsByName('DataTables_Table_1_length');
		pollRecords[0].addEventListener('change',function(){ filteredByRecords(true) }, false);
		
		document.getElementById('searchSurvey').addEventListener('keyup',function(){ filteredByFind() }, false);
		document.getElementById('DataTables_Table_1_filter').firstChild.firstElementChild.addEventListener('keyup',function(){ filteredByFind(true) }, false);;
		

		document.getElementById('stateFilter').addEventListener('change',function(){ filteredByState() }, false);
	},400)
}