package ilu.surveytool.databasemanager.DataObject;

public class ResourceType {

	private int id;
	private String name;
	
	public ResourceType() {
		super();
	}

	public ResourceType(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ResourceType [id=" + id + ", name=" + name + "]";
	}

}
