package ilu.surveytool.databasemanager.DataObject;

public class QDependenceValue {
	
	int itemId;
	int qid;
	int pid;
	String qname;
	int ogid;
	int oid; 
	String oname;

	public QDependenceValue() {
		super();
	}

	public QDependenceValue(int itemId, int qid, int pid, String qname, int ogid, int oid, String oname) {
		super();
		this.itemId = itemId;
		this.qid = qid;
		this.pid = pid;
		this.qname = qname;
		this.ogid = ogid;
		this.oid = oid;
		this.oname = oname;
	}
	
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getQid() {
		return qid;
	}

	public void setQid(int qid) {
		this.qid = qid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getQName() {
		return qname;
	}

	public void setQName(String qname) {
		this.qname = qname;
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

	public String getOName() {
		return oname;
	}

	public void setOName(String oname) {
		this.oname = oname;
	}

	@Override
	public String toString() {
		return "QDependence [itemId = "+itemId+", qid = "+qid+", pid = "+pid+", qname = "+qname+", ogid = "+ogid+", oid = "+oid+", oname = "+oname+ "]";
	}

}