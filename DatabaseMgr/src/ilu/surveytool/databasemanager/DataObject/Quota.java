package ilu.surveytool.databasemanager.DataObject;

public class Quota {
	
	private int idQuestionnaire;
	private int idQuestion;
	private int idOptionsGroup;
	private int idOption;
	private int maxResponses;
	private int minResponses;
	//result quota
	private int valueProgress;
	private String nameQuestion;
	private String nameOption;
	
	public int getIdQuestionnaire() {
		return idQuestionnaire;
	}
	public void setIdQuestionnaire(int idQuestionnaire) {
		this.idQuestionnaire = idQuestionnaire;
	}
	public int getIdQuestion() {
		return idQuestion;
	}
	public void setIdQuestion(int idQuestion) {
		this.idQuestion = idQuestion;
	}
	public int getIdOptionsGroup() {
		return idOptionsGroup;
	}
	public void setIdOptionsGroup(int idOptionsGroup) {
		this.idOptionsGroup = idOptionsGroup;
	}
	public int getIdOption() {
		return idOption;
	}
	public void setIdOption(int idOption) {
		this.idOption = idOption;
	}
	public int getMaxResponses() {
		return maxResponses;
	}
	public void setMaxResponses(int maxResponses) {
		this.maxResponses = maxResponses;
	}
	public int getMinResponses() {
		return minResponses;
	}
	public void setMinResponses(int minResponses) {
		this.minResponses = minResponses;
	}
	
	@Override
	public String toString() {
		return "Quota [idQuestionnaire=" + idQuestionnaire + ", idQuestion=" + idQuestion + ", idOptionsGroup=" + idOptionsGroup + ", idOption="
				+ idOption + ", maxResponses="+ maxResponses + ", minResponses="+ minResponses + "]";
	}
	public int getValueProgress() {
		return valueProgress;
	}
	public void setValueProgress(int valueProgress) {
		this.valueProgress = valueProgress;
	}
	public String getNameQuestion() {
		return nameQuestion;
	}
	public void setNameQuestion(String nameQuestion) {
		this.nameQuestion = nameQuestion;
	}
	public String getNameOption() {
		return nameOption;
	}
	public void setNameOption(String nameOption) {
		this.nameOption = nameOption;
	}
}
