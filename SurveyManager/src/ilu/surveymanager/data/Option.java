package ilu.surveymanager.data;

public class Option {
	
	String text = "";
	int index;
	int qid;
	int ogid;
	int oid;
	String otype = "";
	String language = ""; 
	

	public Option() {
		super();
	}

	public Option(String text, int index, int qid, int ogid, int oid, String otype, String language) {
		super();
		this.text = text;
		this.index = index;
		this.qid = qid;
		this.ogid = ogid;
		this.oid = oid;
		this.otype = otype;
		this.language = language;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getQid() {
		return qid;
	}

	public void setQid(int qid) {
		this.qid = qid;
	}

	public int getOgid() {
		return ogid;
	}

	public void setOgid(int ogid) {
		this.ogid = ogid;
	}
	
	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public String getOtype() {
		return otype;
	}

	public void setOtype(String otype) {
		this.otype = otype;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "Option [text=" + text + ", index=" + index + ", qid=" + qid + ", ogid=" + ogid + ", oid=" + oid
				+ ", otype=" + otype + ", language=" + language + "]";
	}

}
