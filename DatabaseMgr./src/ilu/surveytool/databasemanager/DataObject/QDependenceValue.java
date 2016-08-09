package ilu.surveytool.databasemanager.DataObject;

public class QDependenceValue {
	
	int itemId;
	int qid;
	int ogid;
	int oid; 

	public QDependenceValue() {
		super();
	}

	public QDependenceValue(int itemId, int qid, int ogid, int oid) {
		super();
		this.itemId = itemId;
		this.qid = qid;
		this.ogid = ogid;
		this.oid = oid;
	}public int getItemId() {
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

	@Override
	public String toString() {
		return "QDependence [itemId = "+itemId+", qid = "+qid+", ogid = "+ogid+", oid = "+oid + "]";
	}

}