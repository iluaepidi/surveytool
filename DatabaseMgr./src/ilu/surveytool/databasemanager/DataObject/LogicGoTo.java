package ilu.surveytool.databasemanager.DataObject;

public class LogicGoTo {
	
	int qid;
	int qdestid;
	String qDest;
	int ogid;
	int oid;
	String option;

	public LogicGoTo() {
		super();
	}

	public LogicGoTo(int qid, int qdestid, String qDest, int ogid, int oid, String option) {
		super();
		this.qid = qid;
		this.qdestid = qdestid;
		this.qDest = qDest;
		this.ogid = ogid;
		this.oid = oid;
		this.option = option;
	}
	
	public int getQDestId() {
		return qdestid;
	}

	public void setQDestId(int qdestid) {
		this.qdestid = qdestid;
	}
	
	public String getQDest() {
		return qDest;
	}

	public void setQDest(String qDest) {
		this.qDest = qDest;
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
	
	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	@Override
	public String toString() {
		return "LogicGoTo [qDestId = "+qdestid+", qdest = "+qDest+", qid = "+qid+", ogid = "+ogid+", oid = "+oid+", option = "+option+" ]";
	}

}