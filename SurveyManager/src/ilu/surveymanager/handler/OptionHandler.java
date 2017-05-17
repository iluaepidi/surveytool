package ilu.surveymanager.handler;

import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveymanager.data.Option;
import ilu.surveymanager.data.OptionsGroupSurveyManager;
import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.OptionDB;
import ilu.surveytool.databasemanager.QuestionParameterDB;
import ilu.surveytool.databasemanager.ResourceDB;
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
				if(!option.getText().equals("") || (option.getText().equals("") && option.isOther())){
					int contentId = contentDB.insertContentIndex();
					if(contentId != 0)
					{
						option.setOid(optionDB.insertOption(contentId));
						
						if(option.isOther()) optionDB.updateOptionOther(option.getOid(), option.isOther());
						
						if(!option.getText().equals("")) contentDB.insertContent(contentId, option.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, option.getText());
						if(option.getOid() != 0 && option.getOgid() != 0)
						{
							optionDB.updateOptionsByGroupIncrementIndex(option.getOgid(), option.getIndex());
							optionDB.insertOptionsByGroup(option.getOgid(), option.getOid(), option.getIndex());
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

	public String createOptionOther(Option option)
	{
		String response = "";
		OptionDB optionDB = new OptionDB();

		response = this.saveOption(option);
			
		return response;
	}
	
	/*public boolean setOptionOther(Option option, int pageId)
	{
		boolean updated = false;
		OptionDB optionDB = new OptionDB();

		updated = optionDB.updateOptionOther(option.getOgid(), option.getQid(), value);
		
		if(!option.isOther() && updated)
		{
			QuestionParameterDB questionParameterDB = new QuestionParameterDB();
			questionParameterDB.removeQuestionParameters(option.getQid(), pageId);
			ContentDB contentDB = new ContentDB();
			int contentId = optionDB.getContentIdByOptionsGroupId(option.getOgid());
			contentDB.removeContentByType(contentId, DBConstants.s_VALUE_CONTENTTYPE_NAME_OTHER_LABEL);
		}
			
		return updated;
	}*/
	
	public String updateTextOption(Option option)
	{
		ContentDB contentDB = new ContentDB();
		OptionDB optionDB = new OptionDB();
		JSONObject response = new JSONObject();
		
		try {
			
			if(option.getOgid() == 0)
			{
				int contentId = contentDB.insertContentIndex();
				option.setOgid(optionDB.insertOptionsGroup(option.getQid(), option.getOtype(), contentId, 1));
				response.put("ogid", String.valueOf(option.getOgid()));
			}
			
			if(option.getOid() == 0)
			{
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
					contentDB.insertContent(contentId, option.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, option.getText());
				}
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response.toString();
	}
	
	public boolean updateTextOtherOption(Option option)
	{
		boolean updated = false;
		ContentDB contentDB = new ContentDB();
		OptionDB optionDB = new OptionDB();
		int contentId = optionDB.getContentIdByOptionId(option.getOid());
		boolean exist = contentDB.existContent(contentId, option.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE);
		if(exist)
		{
			contentDB.updateContentText(contentId, option.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, option.getText());
			updated = true;
		}
		else
		{
			updated = contentDB.insertContent(contentId, option.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, option.getText());
		}
		return updated;
	}
	
	public String saveOptionWithoutContent(Option option)
	{
		ContentDB contentDB = new ContentDB();
		OptionDB optionDB = new OptionDB();
		JSONObject response = new JSONObject();
		
		try {
			
			if(option.getOgid() == 0)
			{
				int contentId = contentDB.insertContentIndex();
				option.setOgid(optionDB.insertOptionsGroup(option.getQid(), option.getOtype(), contentId, 1));
				response.put("ogid", String.valueOf(option.getOgid()));
			}
			
			if(option.getOid() == 0)
			{
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
							{
								optionDB.updateOptionsByGroupIncrementIndex(og, option.getIndex());
								optionDB.insertOptionsByGroup(og, option.getOid(), option.getIndex());
							}
						}
						else if(option.getOid() != 0 && optionsGroupId.isEmpty()){
							int contentIdGroup = contentDB.insertContentIndex();
							int id = optionDB.insertOptionsGroup(option.getQid(), option.getOtype(), contentIdGroup, 1);
							optionDB.insertOptionsByGroup(id, option.getOid(), option.getIndex());
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
					
					int contentId = 0;
					int optionsGroupId = 0;
					List<Integer> optionsGroupIdList = optionDB.getOptionsGroupIdByQuestionId(optionsGroup.getQid());
					if(!optionsGroupIdList.isEmpty() && optionsGroupIdList.size()==1 && contentDB.getCountContentByIdAndType(optionDB.getContentIdByOptionsGroupId(optionsGroupIdList.get(0)), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE)==0){
						optionsGroupId= optionsGroupIdList.get(0);						
						contentId = optionDB.getContentIdByOptionsGroupId(optionsGroupId);
						
						optionsGroup.setOgid(optionsGroupId);
						contentDB.insertContent(contentId, optionsGroup.getLanguage(), DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE, optionsGroup.getText());							
							
						response.put("ogid", String.valueOf(optionsGroup.getOgid()));
					}
					else{
						contentId = contentDB.insertContentIndex();
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
		int contentId = optionDB.getContentIdByOptionId(optionId);
		int resourceId = optionDB.getResourceIdByOptionId(optionId);
		optionDB.removeOption(optionId);
		ContentDB contentDB = new ContentDB();
		contentDB.removeContent(contentId);
		
		if(resourceId != 0)
		{
			ResourceDB resourceDB = new ResourceDB();
			resourceDB.removeResource(resourceId);
		}
		
		if(optionsGroupId.size()>0){
			List<OptionsByGroup> optionsByGroup = optionDB.getOptionsByGroupById(optionsGroupId.get(0));
			int index = 1;
			for(OptionsByGroup optionByGroup : optionsByGroup)
			{
				if(optionByGroup.getIndex() != index && optionByGroup.getIndex() != 999)
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
		System.out.println("In removeOptionsGroupMatrix");
		List<OptionsGroup> optionsGroup = optionDB.getOptionsGroupByQuestionId(questionId, "", "");
		if(optionsGroup!=null && optionsGroup.size()==1){
			System.out.println("In removeOptionsGroupMatrix, one item");
			optionDB.removeOptionsGroupContent(optionsGroupId);
			removed=true;
		}
		else if(optionsGroup!=null && optionsGroup.size()>1){
			System.out.println("In removeOptionsGroupMatrix, more than one item");
			optionDB.removeOptionsGroup(optionsGroupId);
			
			optionsGroup = optionDB.getOptionsGroupByQuestionId(questionId, "", "");
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
		}	
		
		removed=true;
		
		
		return removed;
	}
}
