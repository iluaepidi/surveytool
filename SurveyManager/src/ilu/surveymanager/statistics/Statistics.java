package ilu.surveymanager.statistics;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ilu.surveytool.databasemanager.QuestionDB;
import ilu.surveytool.databasemanager.ResponsesDB;
import ilu.surveytool.databasemanager.DataObject.Option;
import ilu.surveytool.databasemanager.DataObject.OptionsByGroup;
import ilu.surveytool.databasemanager.DataObject.OptionsGroup;

public class Statistics {
	
	int numVisits;//
	//int numCompleteResponses;//
	int numCompleteMandatoryResponses;//
	TreeMap<Date, Integer> visitsByDay;//
	TreeMap<Date, Integer> completedQuestionnairesByDay;//day,[notmandatory, mandatory]
	HashMap<Integer, StatisticsQuestion> statisticsByQuestion;//

	public Statistics() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getNumVisits(){
		return numVisits;
	}

	/*public int getNumCompleteResponses(){
		return numCompleteResponses;
	}*/

	public int getNumCompleteMandatoryResponses(){
		return numCompleteMandatoryResponses;
	}
	
	public StatisticsQuestion getStatisticsByQuestion(int idQuestion){
		StatisticsQuestion question = statisticsByQuestion.get(idQuestion);
		
		return question;
	}
	
	public Map<Date, Integer> getVisitsByDay(){
		Map<Date, Integer> visits = new TreeMap<Date, Integer>(visitsByDay);
		return visits;
	}
	
 	public void loadData(int surveyId, String language, String languageDefault) {
		ResponsesDB responsesDB = new ResponsesDB();
		
		//anonymousUserId, createDate, questionId, optionsGroupId, value, timestamp
		HashMap<Integer, HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>> responses = responsesDB.getAnonimousResponseCompleteWithoptionIdBySurveyId(surveyId);
		
		numVisits = responses.size();
		
		loadVisitsByDay(responses);
		
		int[] questions = responsesDB.getNumQuestionsQuestionnaires(surveyId); //0-->No mandatory, 1-->Mandatory
		HashMap<Integer,Boolean> questionMandatories = responsesDB.getQuestionsID_Mandatory_BySurveyId(surveyId);
		loadDataOfCompleted(responses, questions, questionMandatories);
		loadQuestions(surveyId, responses, languageDefault);
	}
	
	public void loadVisitsByDay(HashMap<Integer, HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>> responses){
		HashMap<Date, Integer> visitsByDay2 = new HashMap<Date, Integer>();
		
		Iterator it = responses.entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry pair = (Map.Entry)it.next();
		    HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>> times = (HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>)pair.getValue();
		    
		    Iterator it2 = times.entrySet().iterator();
		    while (it2.hasNext()) {
		    	Map.Entry pair2 = (Map.Entry)it2.next();	
		    
		    	Date moment = new Date(((Timestamp)(pair2.getKey())).getTime());
		    	Calendar cal = Calendar.getInstance(); // locale-specific
		    	cal.setTime(moment);
		    	cal.set(Calendar.HOUR_OF_DAY, 0);
		    	cal.set(Calendar.MINUTE, 0);
		    	cal.set(Calendar.SECOND, 0);
		    	cal.set(Calendar.MILLISECOND, 0);
		    	
		    	if(!visitsByDay2.containsKey(cal.getTime()))
				{
		    		visitsByDay2.put(cal.getTime(), new Integer(1));
				}
		    	else{
		    		int count = ((Integer)visitsByDay2.get(cal.getTime())).intValue();
		    		visitsByDay2.put(cal.getTime(), new Integer(count+1));
		    	}
		    }
		}
		
		//Sort the HashMap
		visitsByDay = new TreeMap<Date, Integer>(visitsByDay2);
	}
	
	public String[][] groupVisitsByDay(int numMaxLabels){
		String[][] data;
		
		long minX = Long.MAX_VALUE;
      	long maxX = 0;
      	
      	if(!visitsByDay.isEmpty()){
      		
      		//System.out.println("visits is not empty");
          	Iterator it = visitsByDay.entrySet().iterator();
    		while (it.hasNext()) {
    		    Map.Entry pair = (Map.Entry)it.next();
    		    
    		    if(((Date)(pair.getKey())).getTime()>maxX)
    		    	maxX=((Date)(pair.getKey())).getTime();
    		    if(((Date)(pair.getKey())).getTime()<minX)
    		    	minX=((Date)(pair.getKey())).getTime(); 

          		//System.out.println("min="+minX+", max="+maxX);
    		}
    		
    		Date[] dates;
    		double interval;
    		int[] labelY;
    		int numDays = (int)Math.ceil(((maxX-minX)*1.0)/(1000.0*60.0*60.0*24.0));
    		if((numDays+1)>numMaxLabels){	
    			//System.out.println("numDays>numMaxLabels");
    			data = new String[numMaxLabels][2];
    			if(numMaxLabels>1)
    				interval = ((maxX-minX)*1.0)/((numMaxLabels-1)*1.0);
    			else
    				interval = Integer.MAX_VALUE;
    			
        		dates = new Date[numMaxLabels];
        		labelY = new int[numMaxLabels];
    		}
    		else{
    			data = new String[numDays+1][2];
    			dates = new Date[numDays+1];
        		labelY = new int[numDays+1];
        		interval = ((maxX-minX)*1.0)/(numDays*1.0);
    		}
    			
    		
    		long labelX = minX;
    		int indexDate = 0;
    		dates[indexDate] = new Date((long)((labelX)));
			//System.out.println(labelX);
    		while((labelX+interval)<=maxX){
    			indexDate++;
    			dates[indexDate] = new Date((long)((labelX+interval)));
    			labelX = (labelX + (long)interval);					        			
    			//System.out.println(labelX);
    		}
    		
    		int index = 0;
    		it = visitsByDay.entrySet().iterator();
    		int previous = 0;
    		boolean accumulated = false;
    		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    		
    		while (it.hasNext()) {
    		    Map.Entry pair = (Map.Entry)it.next();
    		    long x = ((Date)(pair.getKey())).getTime();
    		    
    		    String label = "";
    		    
    		    if(index<labelY.length){
	    		    if(accumulated){
	    		    	
	    		    	if (x==dates[index].getTime()){
		    		    	//System.out.println(df.format(dates[index]));
		    		    	previous = previous+((Integer)(pair.getValue())).intValue();
		    		    	labelY[index]=previous;
		    		    	accumulated=false;
		    		    	index++;
		    		    }
		    		    else if(x>dates[index].getTime()){
		    		    	labelY[index]=previous;
	        		    	index++; 
	        		    	
		    		    	while (x>dates[index].getTime()){
		        		    	//System.out.println(df.format(dates[index]));	        		    	
		        		    	labelY[index]=0;
		        		    	index++; 
		        		    }
		        		    
		        		    //System.out.println(df.format(x));
		        		    previous = ((Integer)(pair.getValue())).intValue();
		        		    labelY[index]=previous;
		    		    	accumulated=false;
		    		    	index++;
		    		    }
		    		    else
		    		    	previous = previous+((Integer)(pair.getValue())).intValue();
	    		    }
	    		    else{
		    		    if (x==dates[index].getTime()){
		    		    	//System.out.println(df.format(dates[index]));
		    		    	previous = ((Integer)(pair.getValue())).intValue();
		    		    	labelY[index]=previous;	    		    	
		    		    	index++;
		    		    }
		    		    else if(x>dates[index].getTime()){
		    		    	while (x>dates[index].getTime()){
		        		    	//System.out.println(df.format(dates[index]));
		        		    	labelY[index]=0;
		        		    	index++; 
		        		    }
		        		    previous =((Integer)(pair.getValue())).intValue();
		        		    //System.out.println(df.format(x));
		        		    labelY[index]=previous;
		        		    index++;
		    		    }
		    		    else{
		    		    	accumulated = true;
		    		    	previous = ((Integer)(pair.getValue())).intValue();
		    		    }
	    		    }
	    		}
    		}
    		
    		
    		for (int i=0;i<labelY.length;i++){
    			data[i][0]=df.format(dates[i]);
    			data[i][1]=""+labelY[i];
    		}
      	}
      	else{
      		//System.out.println("visits is empty");
      		data = new String[1][2];
      		data[0][0] = "-";
      		data[0][1] = "0";      		
      	}
		
		return data;
	}
	
	/*public void loadDataOfCompleted(HashMap<Integer, HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>> responses, int[] questions, HashMap<Integer,Boolean> questionMandatories){
		
		HashMap<Date, int[]> completedQuestionnairesByDay2 = new HashMap<Date, int[]>();
		numCompleteResponses = 0;
		numCompleteMandatoryResponses = 0;
		
		Iterator it = responses.entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry pair = (Map.Entry)it.next();
		    HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>> times = (HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>)pair.getValue();
		    
		    Iterator it2 = times.entrySet().iterator();
		    while (it2.hasNext()) {
		    	Map.Entry pair2 = (Map.Entry)it2.next();	
		    	HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>> questionsList = (HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>)pair2.getValue();
		    	
		    	Date moment = new Date(((Timestamp)(pair2.getKey())).getTime());
		    	Calendar cal = Calendar.getInstance(); // locale-specific
		    	cal.setTime(moment);
		    	cal.set(Calendar.HOUR_OF_DAY, 0);
		    	cal.set(Calendar.MINUTE, 0);
		    	cal.set(Calendar.SECOND, 0);
		    	cal.set(Calendar.MILLISECOND, 0);
		    	
		    	int numMandatories = 0;
		    	int numNotMandatories = 0;
			    Iterator it3 = questionsList.entrySet().iterator();
			    while (it3.hasNext()) {
			    	Map.Entry pair3 = (Map.Entry)it3.next();	
			    	
			    	if(questionMandatories!=null && ((Boolean)(questionMandatories.get(((Integer)(pair3.getKey())).intValue()))).booleanValue()){
			    		numMandatories++;
			    		//System.out.println("Question "+(((Integer)(pair3.getKey())).intValue())+" is mandatory");
			    	}else{
			    		numNotMandatories++;
			    		//System.out.println("Question "+(((Integer)(pair3.getKey())).intValue())+" is not mandatory");
			    	}
			    }
			    
			    int countMandatory = 0;
			    int countNotMandatory = 0;
			    if(!completedQuestionnairesByDay2.isEmpty()){
			    	if(completedQuestionnairesByDay2.containsKey(cal.getTime())){
				    	countMandatory = completedQuestionnairesByDay2.get(cal.getTime())[1];
					    countNotMandatory = completedQuestionnairesByDay2.get(cal.getTime())[0];
			    	}
			    }
			    
			    //System.out.println("Mandatories "+numMandatories+", not mandatory "+numNotMandatories+", questions[1] "+questions[1]+", questions[0]"+questions[0]);
			    if((numMandatories+numNotMandatories)==(questions[1]+questions[0])){
			    	countNotMandatory++;
					numCompleteResponses++;
					//System.out.println("Incremento numCompleteResponses "+numCompleteResponses);
			    }
			    if(numMandatories==questions[0]){
			    	if((numMandatories+numNotMandatories)!=(questions[1]+questions[0])) countMandatory++;
			    	numCompleteMandatoryResponses++;
			    	//System.out.println("Incremento numCompleteMandatoryResponses "+numCompleteMandatoryResponses);
			    }
			    
			  System.out.println("Lo que inserto en el array ("+cal.getTime()+"): countNotMandatory= "+countNotMandatory+", countMandatory= "+ countMandatory);
			  completedQuestionnairesByDay2.put(cal.getTime(), new int[]{countNotMandatory, countMandatory});
			    
		    }
		}
		
		//Sort the HashMap
		completedQuestionnairesByDay = new TreeMap<Date, int[]>(completedQuestionnairesByDay2);
	}
*/
	
	public void loadDataOfCompleted(HashMap<Integer, HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>> responses, int[] questions, HashMap<Integer,Boolean> questionMandatories){
		
		HashMap<Date, Integer> completedQuestionnairesByDay2 = new HashMap<Date, Integer>();
		//numCompleteResponses = 0;
		numCompleteMandatoryResponses = 0;
		
		Iterator it = responses.entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry pair = (Map.Entry)it.next();
		    HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>> times = (HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>)pair.getValue();
		    
		    Iterator it2 = times.entrySet().iterator();
		    while (it2.hasNext()) {
		    	Map.Entry pair2 = (Map.Entry)it2.next();	
		    	HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>> questionsList = (HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>)pair2.getValue();
		    	
		    	Date moment = new Date(((Timestamp)(pair2.getKey())).getTime());
		    	Calendar cal = Calendar.getInstance(); // locale-specific
		    	cal.setTime(moment);
		    	cal.set(Calendar.HOUR_OF_DAY, 0);
		    	cal.set(Calendar.MINUTE, 0);
		    	cal.set(Calendar.SECOND, 0);
		    	cal.set(Calendar.MILLISECOND, 0);
		    	
		    	int numMandatories = 0;
			    Iterator it3 = questionsList.entrySet().iterator();
			    while (it3.hasNext()) {
			    	Map.Entry pair3 = (Map.Entry)it3.next();	
			    	
			    	if(questionMandatories!=null && ((Boolean)(questionMandatories.get(((Integer)(pair3.getKey())).intValue()))).booleanValue()){
			    		numMandatories++;
			    		//System.out.println("Question "+(((Integer)(pair3.getKey())).intValue())+" is mandatory");
			    	}
			    }
			    
			    int countMandatory = 0;
			    if(!completedQuestionnairesByDay2.isEmpty()){
			    	if(completedQuestionnairesByDay2.containsKey(cal.getTime())){
				    	countMandatory = ((Integer)completedQuestionnairesByDay2.get(cal.getTime())).intValue();
			    	}
			    }
			    
			    //System.out.println("Mandatories "+numMandatories+", not mandatory "+numNotMandatories+", questions[1] "+questions[1]+", questions[0]"+questions[0]);
			    if(numMandatories==questions[0]){
			    	countMandatory++;
			    	numCompleteMandatoryResponses++;
			    	//System.out.println("Incremento numCompleteMandatoryResponses "+numCompleteMandatoryResponses);
			    }
			    
			  //System.out.println("Lo que inserto en el array ("+cal.getTime()+"): countMandatory= "+ countMandatory);
			  completedQuestionnairesByDay2.put(cal.getTime(), countMandatory);
			    
		    }
		}
		
		//Sort the HashMap
		completedQuestionnairesByDay = new TreeMap<Date, Integer>(completedQuestionnairesByDay2);
	}


	/*public String[][] groupCompletedByDay(int numMaxLabels){
		String[][] data;
		int[][] labelY;
		
		long minX = Long.MAX_VALUE;
      	long maxX = 0;
      	
      	if(!completedQuestionnairesByDay.isEmpty()){
      		
      		//System.out.println("completedQuestionnairesByDay is not empty");
          	Iterator it = completedQuestionnairesByDay.entrySet().iterator();
    		while (it.hasNext()) {
    		    Map.Entry pair = (Map.Entry)it.next();
    		    
    		    if(((Date)(pair.getKey())).getTime()>maxX)
    		    	maxX=((Date)(pair.getKey())).getTime();
    		    if(((Date)(pair.getKey())).getTime()<minX)
    		    	minX=((Date)(pair.getKey())).getTime(); 

          		//System.out.println("min="+minX+", max="+maxX);
    		}
    		
    		Date[] dates;
    		double interval;
    		
    		int numDays = (int)Math.ceil(((maxX-minX)*1.0)/(1000.0*60.0*60.0*24.0));
    		if((numDays+1)>numMaxLabels){	
    			//System.out.println("numDays>numMaxLabels");
    			data = new String[numMaxLabels][3];
    			if(numMaxLabels>1)
    				interval = ((maxX-minX)*1.0)/((numMaxLabels-1)*1.0);
    			else
    				interval = Integer.MAX_VALUE;
    			
        		dates = new Date[numMaxLabels];
        		labelY = new int[numMaxLabels][2];
    		}
    		else{
    			data = new String[numDays+1][3];
    			dates = new Date[numDays+1];
        		labelY = new int[numDays+1][2];
        		interval = ((maxX-minX)*1.0)/(numDays*1.0);
    		}
    			
    		
    		long labelX = minX;
    		int indexDate = 0;
    		dates[indexDate] = new Date((long)((labelX)));
			//System.out.println(labelX);
    		while((labelX+interval)<=maxX){
    			indexDate++;
    			dates[indexDate] = new Date((long)((labelX+interval)));
    			labelX = (labelX + (long)interval);					        			
    			System.out.println("label X="+labelX);
    		}
    		
    		int index = 0;
    		it = completedQuestionnairesByDay.entrySet().iterator();
    		int previousMandatory = 0;
    		int previousNotMandatory = 0;
    		boolean accumulated = false;
    		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    		
    		while (it.hasNext()) {
    		    Map.Entry pair = (Map.Entry)it.next();
    		    long x = ((Date)(pair.getKey())).getTime();
    		    System.out.println("Loq ue obtengo de eje X="+(Date)(pair.getKey())+", y0="+((int[])(pair.getValue()))[0]+", y1="+((int[])(pair.getValue()))[1]);
    		    String label = "";
    		    
    		    if(index<labelY.length){
    		    	int[] values = new int[2];
    		    	if (pair.getValue() instanceof int[]){
        		    	//System.out.println("en instances");
        		    	values[0] = ((int[])(pair.getValue()))[0];
        		    	values[1] = ((int[])(pair.getValue()))[1];
    		    	}
    		    	else
    		    		values = new int[2];
    		    	
    		    	
	    		    if(accumulated){
	    		    	if (x==dates[index].getTime()){
		    		    	//System.out.println(df.format(dates[index]));
		    		    	previousNotMandatory = previousNotMandatory+values[0];
		    		    	labelY[index][0]=previousNotMandatory;
		    		    	previousMandatory = previousMandatory+values[1];
		    		    	labelY[index][1]=previousMandatory;
		    		    	accumulated=false;
		    		    	System.out.println("Acumulado y x ("+x+") igual a fecha del label ("+dates[index].getTime()+"): y0="+previousNotMandatory+", y1="+previousMandatory);
		    		    	index++;
		    		    }
		    		    else if(x>dates[index].getTime()){
		    		    	labelY[index][0]=previousNotMandatory;
		    		    	labelY[index][1]=previousMandatory;
		    		    	
	        		    	index++;
	        		    	
		    		    	while (x>dates[index].getTime()){
		        		    	//System.out.println(df.format(dates[index]));
		    		    		System.out.println("Acumulado y x ("+x+") > a fecha del label ("+dates[index].getTime()+"): y0=0, y1=0");
			    		    	labelY[index][0]=0;
			    		    	labelY[index][1]=0;
		        		    	index++; 
		        		    }
		        		    
		        		    //System.out.println(df.format(x));
		        		    previousNotMandatory = values[0];
		    		    	labelY[index][0]=previousNotMandatory;
		    		    	previousMandatory = values[1];
		    		    	labelY[index][1]=previousMandatory;
		    		    	System.out.println("Acumulado y x ("+x+") > a fecha del label ("+dates[index].getTime()+"): y0="+previousNotMandatory+", y1="+previousMandatory);
		    		    	accumulated=false;
		    		    	index++;
		    		    }
		    		    else{
		    		    	previousNotMandatory = previousNotMandatory+values[0];
		    		    	previousMandatory = previousMandatory+values[1];
		    		    	System.out.println("Acumulado y x ("+x+") < a fecha del label ("+dates[index].getTime()+"): y0="+previousNotMandatory+", y1="+previousMandatory);
		    		    	
		    		    }
	    		    }
	    		    else{
		    		    if (x==dates[index].getTime()){
		    		    	previousNotMandatory = values[0];
		    		    	labelY[index][0]=previousNotMandatory;
		    		    	previousMandatory = values[1];
		    		    	labelY[index][1]=previousMandatory;    		    	
		    		    	System.out.println("No acumulado y x ("+x+") igual a fecha del label ("+dates[index].getTime()+"): y0="+previousNotMandatory+", y1="+previousMandatory);
		    		    	index++;
		    		    }
		    		    else if(x>dates[index].getTime()){
		    		    	while (x>dates[index].getTime()){
		    		    		System.out.println("No acumulado y x ("+x+") > a fecha del label ("+dates[index].getTime()+"): y0=0, y1=0");
			    		    	//System.out.println(df.format(dates[index]));
			    		    	labelY[index][0]=0;
			    		    	labelY[index][1]=0;
		        		    	index++; 
		        		    }
		    		    	previousNotMandatory = values[0];
		    		    	labelY[index][0]=previousNotMandatory;
		    		    	previousMandatory = values[1];
		    		    	labelY[index][1]=previousMandatory;
		    		    	System.out.println("No acumulado y x ("+x+") = a fecha del label ("+dates[index].getTime()+"): y0="+previousNotMandatory+", y1="+previousMandatory);
		    		    	//System.out.println(df.format(x));
		    		    	index++;
		    		    }
		    		    else{
		    		    	
		    		    	accumulated = true;
		    		    	previousNotMandatory = values[0];
		    		    	previousMandatory = values[1];

		    		    	System.out.println("No acumulado y x ("+x+") < a fecha del label ("+dates[index].getTime()+"): y0="+previousNotMandatory+", y1="+previousMandatory);
		    		    }
	    		    }
	    		}
    		}
    		
    		
    		for (int i=0;i<labelY.length;i++){
    			data[i][0]=df.format(dates[i]);
    			data[i][1]=""+labelY[i][0];
    			data[i][2]=""+labelY[i][1];
    			System.out.println("Relleno ejes: x="+data[i][0]+", y0="+labelY[i][0]+", y1="+labelY[i][1]);
    		}
      	}
      	else{
      		//System.out.println("completedQuestionnairesByDay is empty");
      		data = new String[1][3];
      		data[0][0] = "-";
      		data[0][1] = "0";  
      		data[0][2] = "0";
      	}
		
		return data;
	}*/
	
	public String[][] groupCompletedByDay(int numMaxLabels){
		String[][] data;
		int[] labelY;
		
		long minX = Long.MAX_VALUE;
      	long maxX = 0;
      	
      	if(!completedQuestionnairesByDay.isEmpty()){
      		
      		//System.out.println("completedQuestionnairesByDay is not empty");
          	Iterator it = completedQuestionnairesByDay.entrySet().iterator();
    		while (it.hasNext()) {
    		    Map.Entry pair = (Map.Entry)it.next();
    		    
    		    if(((Date)(pair.getKey())).getTime()>maxX)
    		    	maxX=((Date)(pair.getKey())).getTime();
    		    if(((Date)(pair.getKey())).getTime()<minX)
    		    	minX=((Date)(pair.getKey())).getTime(); 

          		//System.out.println("min="+minX+", max="+maxX);
    		}
    		
    		Date[] dates;
    		double interval;
    		
    		int numDays = (int)Math.ceil(((maxX-minX)*1.0)/(1000.0*60.0*60.0*24.0));
    		if((numDays+1)>numMaxLabels){	
    			//System.out.println("numDays>numMaxLabels");
    			data = new String[numMaxLabels][2];
    			if(numMaxLabels>1)
    				interval = ((maxX-minX)*1.0)/((numMaxLabels-1)*1.0);
    			else
    				interval = Integer.MAX_VALUE;
    			
        		dates = new Date[numMaxLabels];
        		labelY = new int[numMaxLabels];
    		}
    		else{
    			data = new String[numDays+1][2];
    			dates = new Date[numDays+1];
        		labelY = new int[numDays+1];
        		interval = ((maxX-minX)*1.0)/(numDays*1.0);
    		}
    			
    		
    		long labelX = minX;
    		int indexDate = 0;
    		dates[indexDate] = new Date((long)((labelX)));
			//System.out.println(labelX);
    		while((labelX+interval)<=maxX){
    			indexDate++;
    			dates[indexDate] = new Date((long)((labelX+interval)));
    			labelX = (labelX + (long)interval);					        			
    			//System.out.println("label X="+labelX);
    		}
    		
    		int index = 0;
    		it = completedQuestionnairesByDay.entrySet().iterator();
    		int previousMandatory = 0;
    		int previousNotMandatory = 0;
    		boolean accumulated = false;
    		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    		
    		while (it.hasNext()) {
    		    Map.Entry pair = (Map.Entry)it.next();
    		    long x = ((Date)(pair.getKey())).getTime();
    		    //System.out.println("Lo que obtengo de eje X="+(Date)(pair.getKey())+", y="+((Integer)(pair.getValue())).intValue());
    		    String label = "";
    		    
    		    if(index<labelY.length){
    		    	int value = ((Integer)(pair.getValue())).intValue();
    		    	
    		    	
	    		    if(accumulated){
	    		    	if (x==dates[index].getTime()){
		    		    	previousMandatory = previousMandatory+value;
		    		    	labelY[index]=previousMandatory;
		    		    	accumulated=false;
		    		    	//System.out.println("Acumulado y x ("+x+") igual a fecha del label ("+dates[index].getTime()+"): y="+previousMandatory);
		    		    	index++;
		    		    }
		    		    else if(x>dates[index].getTime()){
		    		    	labelY[index]=previousMandatory;
		    		    	
	        		    	index++;
	        		    	
		    		    	while (x>dates[index].getTime()){
		        		    	//System.out.println(df.format(dates[index]));
		    		    		//System.out.println("Acumulado y x ("+x+") > a fecha del label ("+dates[index].getTime()+"): y=0");
			    		    	labelY[index]=0;
		        		    	index++; 
		        		    }
		        		    
		        		    //System.out.println(df.format(x));
		        		    previousMandatory = value;
		    		    	labelY[index]=previousMandatory;
		    		    	//System.out.println("Acumulado y x ("+x+") > a fecha del label ("+dates[index].getTime()+"): y="+previousMandatory);
		    		    	accumulated=false;
		    		    	index++;
		    		    }
		    		    else{
		    		    	previousMandatory = previousMandatory+value;
		    		    	//System.out.println("Acumulado y x ("+x+") < a fecha del label ("+dates[index].getTime()+"): y="+previousMandatory);
		    		    	
		    		    }
	    		    }
	    		    else{
		    		    if (x==dates[index].getTime()){
		    		    	previousMandatory = value;
		    		    	labelY[index]=previousMandatory;    		    	
		    		    	//System.out.println("No acumulado y x ("+x+") igual a fecha del label ("+dates[index].getTime()+"): y="+previousMandatory);
		    		    	index++;
		    		    }
		    		    else if(x>dates[index].getTime()){
		    		    	while (x>dates[index].getTime()){
		    		    		//System.out.println("No acumulado y x ("+x+") > a fecha del label ("+dates[index].getTime()+"): y=0");
			    		    	//System.out.println(df.format(dates[index]));
			    		    	labelY[index]=0;
		        		    	index++; 
		        		    }
		    		    	previousMandatory = value;
		    		    	labelY[index]=previousMandatory;
		    		    	//System.out.println("No acumulado y x ("+x+") = a fecha del label ("+dates[index].getTime()+"): y="+previousMandatory);
		    		    	//System.out.println(df.format(x));
		    		    	index++;
		    		    }
		    		    else{
		    		    	
		    		    	accumulated = true;
		    		    	previousMandatory = value;

		    		    	//System.out.println("No acumulado y x ("+x+") < a fecha del label ("+dates[index].getTime()+"): y="+previousMandatory);
		    		    }
	    		    }
	    		}
    		}
    		
    		
    		for (int i=0;i<labelY.length;i++){
    			data[i][0]=df.format(dates[i]);
    			data[i][1]=""+labelY[i];
    			//System.out.println("Relleno ejes: x="+data[i][0]+", y="+data[i][1]);
    		}
      	}
      	else{
      		//System.out.println("completedQuestionnairesByDay is empty");
      		data = new String[1][2];
      		data[0][0] = "-";
      		data[0][1] = "0";
      	}
		
		return data;
	}

	
	public void loadQuestions(int surveyId, HashMap<Integer, HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>> responses, String langDefault){
		filllabelsQuestions(surveyId, langDefault);
				
		Iterator it = responses.entrySet().iterator();
		
		
		while (it.hasNext()) {
			//By each user
		    Map.Entry pair = (Map.Entry)it.next();
		    HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>> times = (HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>)pair.getValue();
		    
		    Iterator it2 = times.entrySet().iterator();
		    while (it2.hasNext()) {
		    	Map.Entry pair2 = (Map.Entry)it2.next();	
		    	HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>> questionsList = (HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>)pair2.getValue();
		    	
		    	Iterator it3 = questionsList.entrySet().iterator();
			    while (it3.hasNext()) {
			    	Map.Entry pair3 = (Map.Entry)it3.next();
			    	int type=0;
			    	if(statisticsByQuestion.containsKey(pair3.getKey())){
			    		type = statisticsByQuestion.get(pair3.getKey()).getQuestionType();
			    		//System.out.println("loadQuestions en statistics en if Type:"+type);
			    		statisticsByQuestion.get(pair3.getKey()).setNumResponses(statisticsByQuestion.get(pair3.getKey()).getNumResponses()+1);
			    	}
			    	else{
			    		//This is because the question has open answer (short or long)
			    		QuestionDB questionDB = new QuestionDB();
				    	type = questionDB.getQuestionTypeByQuestionID(((Integer)pair3.getKey()).intValue());
				    	//System.out.println("loadQuestions en statistics en else Type:"+type);
				    	statisticsByQuestion.put((Integer)pair3.getKey(), new StatisticsQuestion());
				    	statisticsByQuestion.get(pair3.getKey()).setQuestionType(type);
				    	statisticsByQuestion.get(pair3.getKey()).setNumResponses(1);
			    	}
			    	
			    	statisticsByQuestion.get(pair3.getKey()).setResponses((HashMap<Integer, HashMap<String,Timestamp>>)pair3.getValue());
			    	
			    }
		    }
		}
		
		//System.out.println("La variabel de questiones es:"+statisticsByQuestion);
	}
	
	
	public void filllabelsQuestions(int surveyId, String defaultLanguage){
		statisticsByQuestion = new HashMap<Integer, StatisticsQuestion>();
		QuestionDB questionDB = new QuestionDB();
		HashMap<Integer,HashMap<Integer,OptionsGroup>> allLabels = questionDB.getSurveyQuestionsContentsLang(surveyId, defaultLanguage);
		StatisticsQuestion sQAux = new StatisticsQuestion();
		statisticsByQuestion = new HashMap<Integer, StatisticsQuestion>();
		
		Iterator it = allLabels.entrySet().iterator();
		
		while (it.hasNext()) {
		    Map.Entry pair = (Map.Entry)it.next();
		    
		    sQAux = new StatisticsQuestion();
		    sQAux.fillLabelsWithOptions(((Integer)pair.getKey()).intValue(), ((HashMap<Integer,OptionsGroup>)pair.getValue()));
		    sQAux.setQuestionType(questionDB.getQuestionTypeByQuestionID(((Integer)pair.getKey()).intValue()));
		    //System.out.println("filllabelsQuestions en statistics Type:"+sQAux.getQuestionType());
		    statisticsByQuestion.put((Integer)pair.getKey(), sQAux);
		}
	}
}
