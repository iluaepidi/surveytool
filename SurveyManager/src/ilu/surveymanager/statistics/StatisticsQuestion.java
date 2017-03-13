package ilu.surveymanager.statistics;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ilu.surveytool.databasemanager.QuestionDB;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.Option;
import ilu.surveytool.databasemanager.DataObject.OptionsByGroup;
import ilu.surveytool.databasemanager.DataObject.OptionsGroup;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;

public class StatisticsQuestion {
	int questionId;
	int numResponses;
	int questionType;
	List<OptionsGroup> optionsGroup;
	List<Option> options;
	List<OptionsByGroup> optionsByOptionsGroup;

	public StatisticsQuestion() {
		super();
		numResponses=0;
		optionsGroup = new ArrayList<OptionsGroup>();
		options = new ArrayList<Option>();
		optionsByOptionsGroup = new ArrayList<OptionsByGroup>();
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	public int getQuestionType(){
		return questionType;
	}
	
	public int getNumResponses(){
		//System.out.println("getnumresponses");
		return numResponses;
	}
	
	public void setNumResponses(int numResponses){
		this.numResponses = numResponses;
	}
	
	public List<OptionsGroup> getOptionsGroup(){
		return optionsGroup;
	}
	
	public List<Option> getOptions(){
		return options;
	}
	
	public List<OptionsByGroup> getOptionsByGroup(){
		return optionsByOptionsGroup;
	}
	
	public void fillLabels(int questionID, String defaultLanguage){
		if (questionType>2)
			fillLabelsWithOptionsDB(questionID, defaultLanguage);
	}
	
	public void fillLabelsWithOptionsDB(int questionID, String defaultLanguage){
		QuestionDB qDB = new QuestionDB();
		
		HashMap<Integer,OptionsGroup> labels = qDB.getQuestionContentsByQuestionIdLang(questionID, defaultLanguage);
		
		//System.out.println("En fillLabelsWithOptions, lang:"+defaultLanguage);
		Iterator it = labels.entrySet().iterator();
				
		while (it.hasNext()) {
		    Map.Entry pair = (Map.Entry)it.next();
		    
		    optionsGroup.add((OptionsGroup)pair.getValue());
		    
		    List<Option> listoption = ((OptionsGroup)pair.getValue()).getOptions();
		    for (int i =0; i<listoption.size(); i++){
		    	//System.out.println("opción insertada:"+listoption.get(i).getContents().get("text").getText());
		    	options.add(listoption.get(i));
		    	optionsByOptionsGroup.add(new OptionsByGroup(((Integer)pair.getKey()).intValue(), listoption.get(i).getId(), 0));
		    }
		}
	}
	
	public void fillLabelsWithOptions(int questionID, HashMap<Integer,OptionsGroup> labels){
		QuestionDB qDB = new QuestionDB();
		
		//System.out.println("En fillLabelsWithOptions, lang:"+defaultLanguage);
		Iterator it = labels.entrySet().iterator();
				
		while (it.hasNext()) {
		    Map.Entry pair = (Map.Entry)it.next();
		    
		    optionsGroup.add((OptionsGroup)pair.getValue());
		    
		    List<Option> listoption = ((OptionsGroup)pair.getValue()).getOptions();
		    for (int i =0; i<listoption.size(); i++){
		    	//System.out.println(questionID+" --> En optionsgroup "+((OptionsGroup)pair.getValue()).getContents().get("text").getText()+" opción insertada:"+listoption.get(i).getContents().get("text").getText());
		    	options.add(listoption.get(i));
		    	optionsByOptionsGroup.add(new OptionsByGroup(((Integer)pair.getKey()).intValue(), listoption.get(i).getId(), 0));
		    }
		}
	}

	public void setResponses(HashMap<Integer, HashMap<String,Timestamp>> response){
		if (questionType>2){
			//System.out.println("setrespones en statisticsQuestion, questioType:"+questionType);
			if(questionType==7){
				//System.out.println("question scale, opstiongroup id:"+response.size());
				
				Iterator it = response.entrySet().iterator();
			    while (it.hasNext()) {
			    	Map.Entry optionGroup = (Map.Entry)it.next();
			    	HashMap<String,Timestamp> optionsList = (HashMap<String,Timestamp>)optionGroup.getValue();
			    	
			    	Iterator it2 = optionsList.entrySet().iterator();
				    while (it2.hasNext()) {				    	
				    	if(optionsByOptionsGroup.isEmpty()){
				    		OptionsByGroup obg = new OptionsByGroup(1, 1, 0);
					    	obg.setNumResponses(0);
				    		optionsByOptionsGroup.add(obg);
				    		
				    		obg = new OptionsByGroup(1, 2, 0);
					    	obg.setNumResponses(0);
				    		optionsByOptionsGroup.add(obg);
				    		
				    		obg = new OptionsByGroup(1, 3, 0);
					    	obg.setNumResponses(0);
				    		optionsByOptionsGroup.add(obg);
				    		
				    		obg = new OptionsByGroup(1, 4, 0);
					    	obg.setNumResponses(0);
				    		optionsByOptionsGroup.add(obg);
				    		
				    		obg = new OptionsByGroup(1, 5, 0);
					    	obg.setNumResponses(0);
				    		optionsByOptionsGroup.add(obg);
				    		
				    		obg = new OptionsByGroup(1, 6, 0);
					    	obg.setNumResponses(0);
				    		optionsByOptionsGroup.add(obg);
				    		
				    		obg = new OptionsByGroup(1, 7, 0);
					    	obg.setNumResponses(0);
				    		optionsByOptionsGroup.add(obg);
				    	}
				    	
				    	Map.Entry option = (Map.Entry)it2.next();
				    	//System.out.println("Option del map:"+Integer.parseInt((String)option.getKey())+", en String:"+(String)option.getKey());
				    	for(int i=0;i<optionsByOptionsGroup.size();i++){
				    		if(optionsByOptionsGroup.get(i).getOptionId()==Integer.parseInt((String)option.getKey())){
				    			optionsByOptionsGroup.get(i).setNumResponses(optionsByOptionsGroup.get(i).getNumResponses()+1);				    									    	
				    		}
				    	}
				    }
			    }
			}
			else{
				Iterator it = response.entrySet().iterator();
			    while (it.hasNext()) {
			    	Map.Entry optionGroup = (Map.Entry)it.next();
			    	HashMap<String,Timestamp> optionsList = (HashMap<String,Timestamp>)optionGroup.getValue();
			    	//System.out.println("optiongroupId en set responses: "+((Integer)optionGroup.getKey()).intValue());
			    	Iterator it2 = optionsList.entrySet().iterator();
				    while (it2.hasNext()) {
				    	Map.Entry option = (Map.Entry)it2.next();
				    	for(int i=0;i<optionsByOptionsGroup.size();i++){
				    		String optionIdKey = (String)option.getKey();
				    		if(optionIdKey.contains(DBConstants.s_VALUE_TOKEN)) optionIdKey = optionIdKey.split(DBConstants.s_VALUE_TOKEN)[0];
				    		if((optionsByOptionsGroup.get(i).getOptionId()==Integer.parseInt(optionIdKey)) && ((((Integer)optionGroup.getKey()).intValue())==optionsByOptionsGroup.get(i).getOptionsGroupId())){
				    			optionsByOptionsGroup.get(i).setNumResponses(optionsByOptionsGroup.get(i).getNumResponses()+1);
				    			//System.out.println("optionId "+(String)option.getKey()+" en set responses: "+optionsByOptionsGroup.get(i).getNumResponses());
						    	
				    		}
				    	}
				    }
			    }
			}
		}
		else{
			Iterator it = response.entrySet().iterator();
		    while (it.hasNext()) {
		    	Map.Entry optionGroup = (Map.Entry)it.next();
		    	HashMap<String,Timestamp> values = (HashMap<String,Timestamp>)optionGroup.getValue();
		    	
		    	Iterator it2 = values.entrySet().iterator();
			    while (it2.hasNext()) {
			    	Map.Entry option = (Map.Entry)it2.next();
			    	HashMap<String, Content> contentsOG = new HashMap<String, Content>();
	   				Content c = new Content();
	   				c.setText((String)option.getKey());
	   				contentsOG.put("text", c);
			    	OptionsGroup og = new OptionsGroup(optionsByOptionsGroup.size(), contentsOG, "", false, 0, 0,null);
			    	optionsGroup.add(og);
			    	
			    	OptionsByGroup obg = new OptionsByGroup(og.getId(), 0, 0);
			    	obg.setNumResponses(1);
			    	optionsByOptionsGroup.add(obg);
			    }
		    }
		}
	}
}
