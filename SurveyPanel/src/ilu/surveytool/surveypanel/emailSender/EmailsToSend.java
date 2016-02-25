package ilu.surveytool.surveypanel.emailSender;

import java.util.List;

import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.OptionDB;
import ilu.surveytool.databasemanager.QuestionDB;
import ilu.surveytool.databasemanager.SurveyDB;
import ilu.surveytool.databasemanager.UserDB;
import ilu.surveytool.databasemanager.DataObject.Response;
import ilu.surveytool.databasemanager.DataObject.Option;
import ilu.surveytool.databasemanager.DataObject.Survey;
import ilu.surveytool.databasemanager.constants.DBConstants;

public class EmailsToSend {

	private String language= "";
	
	public EmailsToSend(String language)
	{
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public boolean sendUserResponse(int surveyId, int anonymousUserId, List<Response> anonymousResponses)
	{
		boolean sent = false;
		String msg = "";
		
		QuestionDB questionDB = new QuestionDB();
		OptionDB optionDB = new OptionDB();
		SurveyDB surveyDB = new SurveyDB();
		ContentDB contentDB = new ContentDB();
		Survey survey = surveyDB.getQuestionnairesById(surveyId);
		
		msg = "<h1>Survey result for anonymous user: " + anonymousUserId + "</h1>";
		String surveyTitle = survey.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
		msg += "<h2>Survey: " + surveyTitle + "</h2>";
		
		msg += "<ul>";
		
		int index = 1;
		for(Response anonymousResponse : anonymousResponses)
		{
			msg += "<li>";
			String qtitle = questionDB.getQuestionContentByQuestionId(anonymousResponse.getQuestionId(), language).get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE);
			msg += "<h3>Question " + index + ": " + qtitle + "</h3>";
			index++;
			
			String value = anonymousResponse.getValue();
			if(anonymousResponse.getOptionsGroupId() != 0)
			{
				int contentId = optionDB.getContentIdByOptionId(Integer.parseInt(anonymousResponse.getValue()));
				value = contentDB.getContentByIdAndLanguage(contentId, language).get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();				
			}
			
			msg += "<p>" + value + "</p>";
			msg += "</li>";
		}
		
		msg += "</ul>";
		
		String htmlMsg = "<html><head></head><body>" + msg + "</body></html>";
		System.out.println(htmlMsg);
		
		UserDB userDB = new UserDB();
		String email = userDB.getEmailByUserId(survey.getAuthor());
		
		String subject = "Survey response: " + anonymousUserId + " - " + surveyTitle;
		
		EmailSender sender = new EmailSender();
		sender.send(email, subject, htmlMsg);
		
		return sent;
	}
	
}
