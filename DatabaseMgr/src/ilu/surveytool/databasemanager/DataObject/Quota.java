package ilu.surveytool.databasemanager.DataObject;

public class Quota {
	
	private int idQuestionnaire;
	private int idQuestion;
	private int idOptionsGroup;
	private int idOption;
	private int maxResponses;
	private int minResponses;
	
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
}
