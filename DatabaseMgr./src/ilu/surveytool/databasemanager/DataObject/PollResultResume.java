package ilu.surveytool.databasemanager.DataObject;

public class PollResultResume {
	
	int optionId;
	String value;
	int numResposnes;
	float percentage;

	public PollResultResume() {
		super();
		optionId = 0;
		value = "";
		numResposnes = 0;
		percentage = 0;
	}

	public PollResultResume(int optionId, String value, int numResposnes) {
		super();
		this.optionId = optionId;
		this.value = value;
		this.numResposnes = numResposnes;
		this.percentage = 0;
	}
	
	public PollResultResume(int optionId, String value, int numResposnes, float percentage) {
		super();
		this.optionId = optionId;
		this.value = value;
		this.numResposnes = numResposnes;
		this.percentage = percentage;
	}

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getNumResposnes() {
		return numResposnes;
	}

	public void setNumResposnes(int numResposnes) {
		this.numResposnes = numResposnes;
	}
	
	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	@Override
	public String toString() {
		return "PollResultResume [optionId=" + optionId + ", value=" + value + ", numResposnes=" + numResposnes
				+ ", percentage=" + percentage + "]";
	}
	
}
