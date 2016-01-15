package ilu.surveytool.databasemanager.DataObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OptionsGroup {
	
	int id;
	HashMap<String, Content> contents;
	String optionType = "";
	boolean random = false;
	List<Option> options;

	public OptionsGroup() 
	{
		super();
		options = new ArrayList<Option>();
	}

	public OptionsGroup(int id, HashMap<String, Content> contents, String optionType, boolean random,
			List<Option> options) {
		super();
		this.id = id;
		this.contents = contents;
		this.optionType = optionType;
		this.random = random;
		this.options = options;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "OptionsGroup [id=" + id + ", contents=" + contents + ", optionType=" + optionType + ", random=" + random
				+ ", options=" + options + "]";
	}
	
}
