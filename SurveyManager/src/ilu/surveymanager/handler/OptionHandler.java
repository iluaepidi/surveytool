package ilu.surveymanager.handler;

import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveymanager.data.Option;
import ilu.surveymanager.data.OptionsGroupSurveyManager;
import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.OptionDB;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.OptionsByGroup;
import ilu.surveytool.databasemanager.DataObject.OptionsGroup;
import ilu.surveytool.databasemanager.constants.DBConstants;

public class OptionHandler {

	public OptionHandler() {
		super();
	}
	
	public String saveOption(Option option)
	{
		ContentDB contentDB = new ContentDB();
		OptionDB optionDB = new OptionDB();
		JSONObject response = new JSONObject();
		
		try {
			
			if(option.getOgid() == 0)
			{
				if(!option.getText().equals("")){
					int contentId = contentDB.insertContentIndex();
					option.setOgid(optionDB.insertOptionsGroup(option.getQid(), option.getOtype(), contentId, 1));
					response.put("ogid", String.valueOf(option.getOgid()));
				}
			}
			
			if(option.getOid() == 0)
			{
				//if(!option.getText().equals("")){
					int contentId = contentDB.insertContentIndex();
					if(contentId != 0)
					{
						option.setOid(optionDB.insertOption(contentId));
						contentDB.insertContent(contentId, option.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, option.getText());
						if(option.getOid() != 0 && option.getOgid() != 0)
						{
							optionDB.insertOptionsByGroup(option.getOgid(), option.getOid(), option.getIndex());
						}
					}
					response.put("oid", String.valueOf(option.getOid()));
				//}
			}
			else
			{
				int contentId = optionDB.getContentIdByOptionId(option.getOid());
				if(contentDB.existContent(contentId, option.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE))
				{					
						contentDB.updateContentText(contentId, option.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, option.getText());
				}
				else
				{
					if(!option.getText().equals(""))
					contentDB.insertContent(contentId, option.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, option.getText());
				}
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response.toString();
	}
	
	public String saveOptionMatrix(Option option)
	{
		ContentDB contentDB = new ContentDB();
		OptionDB optionDB = new OptionDB();
		JSONObject response = new JSONObject();
		
		try {
			
			/*if(option.getOgid() == 0)
			{
				int contentId = contentDB.insertContentIndex();
				
				option.setOgid(optionDB.insertOptionsGroup(option.getQid(), option.getOtype(), contentId, 1));
				response.put("ogid", String.valueOf(option.getOgid()));
			}*/
			if(option.getOid() == 0)
			{
				if(!option.getText().equals("")){
				List<Integer> optionsGroupId = optionDB.getOptionsGroupIdByQuestionId(option.getQid());
				
				int contentId = contentDB.insertContentIndex();
				if(contentId != 0)
				{
					option.setOid(optionDB.insertOption(contentId));
					
					contentDB.insertContent(contentId, option.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, option.getText());
					
					if(option.getOid() != 0 && !optionsGroupId.isEmpty())
					{
						for(int og: optionsGroupId)
							optionDB.insertOptionsByGroup(og, option.getOid(), option.getIndex());
					}
				}
				response.put("oid", String.valueOf(option.getOid()));
				}
			}
			else
			{
				int contentId = optionDB.getContentIdByOptionId(option.getOid());
				if(contentDB.existContent(contentId, option.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE))
				{
					contentDB.updateContentText(contentId, option.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, option.getText());
				}
				else
				{
					if(!option.getText().equals(""))
					contentDB.insertContent(contentId, option.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, option.getText());
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response.toString();
	}
	
	public String saveOptionsGroupMatrix(OptionsGroupSurveyManager optionsGroup)
	{
		ContentDB contentDB = new ContentDB();
		OptionDB optionDB = new OptionDB();
		JSONObject response = new JSONObject();
		
		try {
			
			if(optionsGroup.getOgid() == 0)
			{
				System.out.println("createOptionsGroup porque ogid=0");
				if(!optionsGroup.getText().equals("")){
				List<Integer> options = optionDB.getOptionIdByQuestionId(optionsGroup.getQid());
				
				int contentId = contentDB.insertContentIndex();
				if(contentId != 0)
				{
					optionsGroup.setOgid(optionDB.insertOptionsGroup(optionsGroup.getQid(), optionsGroup.getOtype(), contentId, optionsGroup.getIndex()));
					
					contentDB.insertContent(contentId, optionsGroup.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, optionsGroup.getText());
					
					if(optionsGroup.getOgid() != 0 && !options.isEmpty())
					{
						int index = 1;
						for(int o: options){
							optionDB.insertOptionsByGroup(optionsGroup.getOgid(), o, index);
							index++;
						}
					}
				}
				response.put("ogid", String.valueOf(optionsGroup.getOgid()));
				}
			}
			else
			{
				int contentId = optionDB.getContentIdByOptionsGroupId(optionsGroup.getOgid());
				if(contentDB.existContent(contentId, optionsGroup.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE))
				{
					System.out.println("update");
					contentDB.updateContentText(contentId, optionsGroup.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, optionsGroup.getText());
				}
				else
				{
					if(!optionsGroup.getText().equals("")){
					System.out.println("insert");
						contentDB.insertContent(contentId, optionsGroup.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, optionsGroup.getText());
					
					}
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response.toString();
	}
	
	public void createOptionsGroup(OptionsGroup optionsGroup, int questionId)
	{
		ContentDB contentDB = new ContentDB();
		OptionDB optionDB = new OptionDB();
		System.out.println("createOptionsGroup");
		
		int contentId = contentDB.insertContentIndex();
		int optionsGroupId = optionDB.insertOptionsGroup(questionId, optionsGroup.getOptionType(), contentId, 1);
		
		for(ilu.surveytool.databasemanager.DataObject.Option option : optionsGroup.getOptions())
		{
			int optionContentId = contentDB.insertContentIndex();
			if(contentId != 0)
			{
				int optionId = optionDB.insertOption(optionContentId);
				Content content = option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE);
				contentDB.insertContent(optionContentId, content.getLanguage(), content.getContentType(), content.getText());
				if(optionsGroupId != 0 && optionId != 0)
				{
					optionDB.insertOptionsByGroup(optionsGroupId, optionId, option.getIndex());
				}
			}
		}
	}
	
	public boolean removeOption(int questionId, int optionGroupId, int optionId)
	{
		boolean removed = false;
		
		OptionDB optionDB = new OptionDB();
		List<Integer> optionsGroupId = optionDB.getOptionByGroupIdByOptionId(optionId);
		optionDB.removeOption(optionId);
		
		if(optionsGroupId.size()>0){
			List<OptionsByGroup> optionsByGroup = optionDB.getOptionsByGroupById(optionsGroupId.get(0));
			int index = 1;
			for(OptionsByGroup optionByGroup : optionsByGroup)
			{
				if(optionByGroup.getIndex() != index)
				{
					optionDB.updateOptionsByGroupIndex(optionsGroupId.get(0), optionByGroup.getOptionId(), index);
				}
				index++;
			}
		}
		
		LogicGoToHandler logicGoToHandler = new LogicGoToHandler();
		logicGoToHandler.removeLogicGoTo(questionId, optionGroupId, optionId);
		
		removed = true;
		
		return removed;
	}
	
	public boolean removeOptionMatrix(int optionId)
	{
		boolean removed = false;
		
		OptionDB optionDB = new OptionDB();
		List<Integer> optionsGroupId = optionDB.getOptionByGroupIdByOptionId(optionId);
		optionDB.removeOption(optionId);
		
		for(int optionsGroupIdIndex : optionsGroupId)
		{
			List<OptionsByGroup> optionsByGroup = optionDB.getOptionsByGroupById(optionsGroupIdIndex);
			int index = 1;
			for(OptionsByGroup optionByGroup : optionsByGroup)
			{
				if(optionByGroup.getIndex() != index)
				{
					optionDB.updateOptionsByGroupIndex(optionsGroupIdIndex, optionByGroup.getOptionId(), index);
				}
				index++;
			}			
		}
		removed = true;
		
		return removed;
	}

	public boolean removeOptionsGroupMatrix(int optionsGroupId)
	{
		boolean removed = false;
		
		OptionDB optionDB = new OptionDB();
		int questionId = optionDB.getQuestionIdByOptionsGroupId(optionsGroupId);
		optionDB.removeOptionsGroup(optionsGroupId);
		
		List<OptionsGroup> optionsGroup = optionDB.getOptionsGroupByQuestionId(questionId, "", "");
		int index = 1;
		
		for(OptionsGroup optionGroup : optionsGroup)
		{
			if(optionGroup.getIndex() != index)
			{
				optionDB.updateOptionsGroupIndex(optionGroup.getId(), index);
			}
			index++;
		}
		removed = true;
		
		return removed;
	}
}
