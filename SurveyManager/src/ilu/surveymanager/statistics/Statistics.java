package ilu.surveymanager.statistics;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ilu.surveytool.databasemanager.QuestionDB;
import ilu.surveytool.databasemanager.ResponsesDB;
import ilu.surveytool.databasemanager.DataObject.OptionsGroup;

public class Statistics {
	
	int numVisits;//
	int numCompleteResponses;//
	int numCompleteMandatoryResponses;//
	HashMap<Date, Integer> visitsByDay;//
	HashMap<Date, int[]> completedQuestionnairesByDay;//day,[notmandatory, mandatory]
	HashMap<Integer, StatisticsQuestion> statisticsByQuestion;//

	public Statistics() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getNumVisits(){
		return numVisits;
	}

	public int getNumCompleteResponses(){
		return numCompleteResponses;
	}

	public int getNumCompleteMandatoryResponses(){
		return numCompleteMandatoryResponses;
	}
	
	public StatisticsQuestion getStatisticsByQuestion(int idQuestion){
		System.out.println(statisticsByQuestion);
		StatisticsQuestion question = statisticsByQuestion.get(idQuestion);
		
		return question;
	}
	
	public Map<Date, Integer> getVisitsByDay(){
		Map<Date, Integer> visits = new TreeMap<Date, Integer>(visitsByDay);
		return visits;
	}
	
 	public void loadData(int surveyId, String language) {
		ResponsesDB responsesDB = new ResponsesDB();
		
		//anonymousUserId, createDate, questionId, optionsGroupId, value, timestamp
		HashMap<Integer, HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>> responses = responsesDB.getAnonimousResponseCompleteBySurveyId(surveyId);
		
		numVisits = responses.size();
		
		loadVisitsByDay(responses);
		
		int[] questions = responsesDB.getNumQuestionsQuestionnaires(surveyId); //0-->No mandatory, 1-->Mandatory
		HashMap<Integer,Boolean> questionMandatories = responsesDB.getQuestionsID_Mandatory_BySurveyId(surveyId);
		loadDataOfCompleted(responses, questions, questionMandatories);
		
		loadQuestions(responses, language);
	}
	
	public void loadVisitsByDay(HashMap<Integer, HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>> responses){
		visitsByDay = new HashMap<Date, Integer>();
		
		Iterator it = responses.entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry pair = (Map.Entry)it.next();
		    HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>> times = (HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>)pair.getValue();
		    
		    Iterator it2 = times.entrySet().iterator();
		    while (it2.hasNext()) {
		    	Map.Entry pair2 = (Map.Entry)it2.next();	
		    
		    	Date moment = new Date(((Timestamp)(pair2.getKey())).getTime());
		    	if(!visitsByDay.containsKey(moment))
				{
		    		visitsByDay.put(moment, 1);
				}
		    	else{
		    		int count = visitsByDay.get(moment);
		    		visitsByDay.put(moment, count+1);
		    	}
		    }
		}
	}
	
	public void loadDataOfCompleted(HashMap<Integer, HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>> responses, int[] questions, HashMap<Integer,Boolean> questionMandatories){
		
		completedQuestionnairesByDay = new HashMap<Date, int[]>();
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
		    	if(!completedQuestionnairesByDay.containsKey(moment))
		    		completedQuestionnairesByDay.put(moment, new int[]{0,0});
				
		    	int numMandatories = 0;
		    	int numNotMandatories = 0;
			    Iterator it3 = questionsList.entrySet().iterator();
			    while (it3.hasNext()) {
			    	Map.Entry pair3 = (Map.Entry)it3.next();	
			    	
			    	if(questionMandatories!=null && ((Boolean)(questionMandatories.get(((Integer)(pair3.getKey())).intValue()))).booleanValue()){
			    		numMandatories++;
			    		System.out.println("Question "+(((Integer)(pair3.getKey())).intValue())+" is mandatory");
			    	}else{
			    		numNotMandatories++;
			    		System.out.println("Question "+(((Integer)(pair3.getKey())).intValue())+" is not mandatory");
			    	}
			    }
			    
			    int countMandatory = completedQuestionnairesByDay.get(moment)[1];
			    int countNotMandatory = completedQuestionnairesByDay.get(moment)[0];
			    System.out.println("Mandatories "+numMandatories+", not mandatory "+numNotMandatories+", questions[1] "+questions[1]+", questions[0]"+questions[0]);
			    if((numMandatories+numNotMandatories)==(questions[1]+questions[0])){
			    	countNotMandatory++;
					numCompleteResponses++;
					System.out.println("Incremento numCompleteResponses "+numCompleteResponses);
			    }
			    if(numMandatories==questions[0]){
			    	countMandatory++;
			    	numCompleteMandatoryResponses++;
			    	System.out.println("Incremento numCompleteMandatoryResponses "+numCompleteMandatoryResponses);
			    }
			    completedQuestionnairesByDay.put(moment, new int[]{countNotMandatory,countMandatory});
		    	
		    }
		}
	}

	public void loadQuestions(HashMap<Integer, HashMap<Timestamp, HashMap<Integer, HashMap<Integer, HashMap<String,Timestamp>>>>> responses, String lang){
		statisticsByQuestion = new HashMap<Integer, StatisticsQuestion>();
		
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
			    		statisticsByQuestion.get(pair3.getKey()).setNumResponses(statisticsByQuestion.get(pair3.getKey()).getNumResponses()+1);
			    	}
			    	else{
			    		QuestionDB questionDB = new QuestionDB();
				    	type = questionDB.getQuestionTypeByQuestionID (((Integer)(pair3.getKey())).intValue());
				    	statisticsByQuestion.put(((Integer)(pair3.getKey())).intValue(), new StatisticsQuestion());
				    	statisticsByQuestion.get(pair3.getKey()).setQuestionType(type);
				    	
				    	(statisticsByQuestion.get(((Integer)(pair3.getKey())).intValue())).fillLabels(((Integer)(pair3.getKey())).intValue(), lang);
				    	
				    	statisticsByQuestion.get(pair3.getKey()).setNumResponses(1);
			    	}
			    	
			    	statisticsByQuestion.get(pair3.getKey()).setResponses((HashMap<Integer, HashMap<String,Timestamp>>)pair3.getValue());
			    	
			    }
		    }
		}
		
		System.out.println("La variabel de questiones es:"+statisticsByQuestion);
	}
	
}
