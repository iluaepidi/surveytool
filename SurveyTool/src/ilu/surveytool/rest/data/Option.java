package ilu.surveytool.rest.data;

public class Option {
	
	String text = "";
	int index;
	int qid;
	int ogid;

	public Option() {
		super();
	}

	public Option(String text, int index, int qid, int ogid) {
		super();
		this.text = text;
		this.index = index;
		this.qid = qid;
		this.ogid = ogid;
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

	@Override
	public String toString() {
		return "Option [text=" + text + ", index=" + index + ", qid=" + qid + ", ogid=" + ogid + "]";
	}

}
