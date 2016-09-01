package ilu.surveymanager.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveymanager.data.Option;
import ilu.surveymanager.exportdata.ExportData;
import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.OptionDB;
import ilu.surveytool.databasemanager.PageDB;
import ilu.surveytool.databasemanager.QuestionDB;
import ilu.surveytool.databasemanager.QuotasDB;
import ilu.surveytool.databasemanager.ResponsesDB;
import ilu.surveytool.databasemanager.SectionDB;
import ilu.surveytool.databasemanager.SurveyDB;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.Project;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Response;
import ilu.surveytool.databasemanager.DataObject.ResponseSimple;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.DataObject.SurveyTableInfo;
import ilu.surveytool.databasemanager.constants.DBConstants;

public class QuotaHandler {

	public QuotaHandler() {
		super();
	}
	
	public String saveQuotaOption(int idSurvey, Option option, int max, int min)
	{
		ContentDB contentDB = new ContentDB();
		QuotasDB quotaDB = new QuotasDB();
		JSONObject response = new JSONObject();
		
		//Check si existe una cuota para ese survey, question, ogid y oid.
		if(quotaDB.getQuotasByQuestionnarie(idSurvey,option.getQid(), option.getOgid(), option.getOid())){
			//Update
			quotaDB.updateQuota(idSurvey,option.getQid(), option.getOgid(), option.getOid(), max, min);
		}else{
			//insert
			quotaDB.insertQuota(idSurvey,option.getQid(), option.getOgid(), option.getOid(), max, min);
		}
		return response.toString();
	}

}
