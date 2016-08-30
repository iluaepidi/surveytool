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
import ilu.surveytool.databasemanager.constants.DBFieldNames;

public class StatisticsQuestion {
	
	int numResponses;
	int questionType;
	List<OptionsGroup> optionsGroup;
	List<Option> options;
	List<OptionsByGroup> optionsByOptionsGroup;

	public StatisticsQuestion() {
		super();
		optionsGroup = new ArrayList<OptionsGroup>();
		options = new ArrayList<Option>();
		optionsByOptionsGroup = new ArrayList<OptionsByGroup>();
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}
	
	public int getQuestionType(){
		return questionType;
	}
	
	public int getNumResponses(){
		return numResponses;
	}
	
	public void setNumResponses(int numResponses){
		this.numResponses = numResponses;
	}
	
	public List<OptionsGroup> getOptionsGroup(){
		return optionsGroup;
	}
	
	public void fillLabels(int questionID, String lang){
		if (questionType>2)
			fillLabelsWithOptions(questionID, lang);
	}
	
	public void fillLabelsWithOptions(int questionID, String lang){
		QuestionDB qDB = new QuestionDB();
		HashMap<Integer,OptionsGroup> labels = qDB.getQuestionContentsByQuestionIdLang(questionID, lang);
		
		Iterator it = labels.entrySet().iterator();
				
		while (it.hasNext()) {
		    Map.Entry pair = (Map.Entry)it.next();
		    
		    optionsGroup.add((OptionsGroup)pair.getValue());
		    
		    List<Option> listoption = ((OptionsGroup)pair.getValue()).getOptions();
		    for (int i =0; i<listoption.size(); i++){
		    	options.add(listoption.get(i));
		    	optionsByOptionsGroup.add(new OptionsByGroup(((Integer)pair.getKey()).intValue(), listoption.get(i).getId(), 0));
		    }
		}
	}

	public void setResponses(HashMap<Integer, HashMap<String,Timestamp>> response){
		if (questionType>2){
			Iterator it = response.entrySet().iterator();
		    while (it.hasNext()) {
		    	Map.Entry optionGroup = (Map.Entry)it.next();
		    	HashMap<String,Timestamp> optionsList = (HashMap<String,Timestamp>)optionGroup.getValue();
		    	
		    	Iterator it2 = optionsList.entrySet().iterator();
			    while (it2.hasNext()) {
			    	Map.Entry option = (Map.Entry)it2.next();
			    	
			    	for(int i=0;i<optionsByOptionsGroup.size();i++){
			    		if(optionsByOptionsGroup.get(i).getOptionId()==Integer.parseInt((String)option.getKey()))
			    			optionsByOptionsGroup.get(i).setNumResponses(optionsByOptionsGroup.get(i).getNumResponses()+1);
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
