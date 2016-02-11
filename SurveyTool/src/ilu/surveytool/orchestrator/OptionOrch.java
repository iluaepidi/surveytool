package ilu.surveytool.orchestrator;

import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveytool.data.Option;
import ilu.surveytool.databasemanager.ContentDB;
import ilu.surveytool.databasemanager.OptionDB;
import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.OptionsByGroup;
import ilu.surveytool.databasemanager.DataObject.OptionsGroup;
import ilu.surveytool.databasemanager.constants.DBConstants;

public class OptionOrch {

	public OptionOrch() {
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
				int contentId = contentDB.insertContentIndex();
				option.setOgid(optionDB.insertOptionsGroup(option.getQid(), option.getOtype(), contentId));
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
	
	public void createOptionsGroup(OptionsGroup optionsGroup, int questionId)
	{
		ContentDB contentDB = new ContentDB();
		OptionDB optionDB = new OptionDB();
		
		int contentId = contentDB.insertContentIndex();
		int optionsGroupId = optionDB.insertOptionsGroup(questionId, optionsGroup.getOptionType(), contentId);
		
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
	
	public boolean removeOption(int optionId)
	{
		boolean removed = false;
		
		OptionDB optionDB = new OptionDB();
		int optionsGroupId = optionDB.getOptionByGroupIdByOptionId(optionId);
		optionDB.removeOption(optionId);
		List<OptionsByGroup> optionsByGroup = optionDB.getOptionsByGroupById(optionsGroupId);
		int index = 1;
		for(OptionsByGroup optionByGroup : optionsByGroup)
		{
			if(optionByGroup.getIndex() != index)
			{
				optionDB.updateOptionsByGroupIndex(optionsGroupId, optionByGroup.getOptionId(), index);
			}
			index++;
		}
		removed = true;
		
		return removed;
	}

}
