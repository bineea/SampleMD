package my.sample.dao.entity.dict;

public enum Male {
	MALE("男") {},
	FEMAIL("女") {},
	NEUTER("要你管") {};

	private String value;

	private Male(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
