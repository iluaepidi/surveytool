package ilu.surveytool.databasemanager.DataObject;

public class OptionsByGroup {

	int optionsGroupId;
	int optionId;
	int index;
	
	public OptionsByGroup() {
		super();
	}

	public OptionsByGroup(int optionsGroupId, int optionId, int index) {
		super();
		this.optionsGroupId = optionsGroupId;
		this.optionId = optionId;
		this.index = index;
	}

	public int getOptionsGroupId() {
		return optionsGroupId;
	}

	public void setOptionsGroupId(int optionsGroupId) {
		this.optionsGroupId = optionsGroupId;
	}

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "OptionsByGroup [optionByGroupId=" + optionsGroupId + ", optionId=" + optionId + ", index=" + index
				+ "]";
	}
	
}
