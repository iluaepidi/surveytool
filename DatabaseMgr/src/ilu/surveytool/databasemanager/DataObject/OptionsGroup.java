package ilu.surveytool.databasemanager.DataObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OptionsGroup {
	
	int id = 1;
	HashMap<String, Content> contents;
	String optionType = "";
	boolean random = false;
	List<Option> options;
	int index;
	int questionId = 0;
	boolean otherOption = false;

	public OptionsGroup() 
	{
		super();
		options = new ArrayList<Option>();
	}

	public OptionsGroup(int id, HashMap<String, Content> contents, String optionType, boolean random, int index, int questionId,
			List<Option> options) {
		super();
		this.id = id;
		this.contents = contents;
		this.optionType = optionType;
		this.random = random;
		this.options = options;
		this.index = index;
		this.questionId = questionId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public HashMap<String, Content> getContents() {
		return contents;
	}

	public void setContents(HashMap<String, Content> contents) {
		this.contents = contents;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public boolean isRandom() {
		return random;
	}

	public void setRandom(boolean random) {
		this.random = random;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public boolean isOtherOption() {
		return otherOption;
	}

	public void setOtherOption(boolean otherOption) {
		this.otherOption = otherOption;
	}

	@Override
	public String toString() {
		return "OptionsGroup [id=" + id + ", contents=" + contents + ", optionType=" + optionType + ", random=" + random
				+ ", options=" + options + "]";
	}
	
}
