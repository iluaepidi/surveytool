package ilu.surveymanager.data;

import java.util.List;

public class QDependence {
	
	int id;
	int questionId;
	int show;
	String dependenceType;
	List<QDependenceValue> qdepval;

	public QDependence() {
		super();
	}

	public QDependence(int id, int questionId, int show, String dependenceType, List<QDependenceValue> qdepval) {
		super();
		this.id = id;
		this.questionId = questionId;
		this.show = show;
		this.dependenceType = dependenceType;
		this.qdepval = qdepval;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	public int getShow() {
		return show;
	}

	public void setShow(int show) {
		this.show = show;
	}
	
	public String getDependenceType() {
		return dependenceType;
	}

	public void setDependenceType(String dependenceType) {
		this.dependenceType = dependenceType;
	}

	public List<QDependenceValue> getqdepval() {
		return qdepval;
	}

	public void setqdepval(List<QDependenceValue> qdepval) {
		this.qdepval = qdepval;
	}
	
	@Override
	public String toString() {
		return "QDependence [id = "+id+", questionId = "+questionId+", show = "+show+", dependenceType = "
				+dependenceType+", values = "+ qdepval.toString() + "]";
	}

}