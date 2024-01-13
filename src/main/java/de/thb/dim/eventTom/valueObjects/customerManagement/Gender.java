package de.thb.dim.eventTom.valueObjects.customerManagement;

public enum Gender {
	M(1), F(2), D(3), U(4);

	private final int number;

	private Gender(int nr) {
		this.number = nr;
	}

	public int toNumber() {
		return number;
	}

	public String toString() {
		switch(number) {
			case 1:
				return "male";
			case 2:
				return "female";
			case 3:
				return "diverse";
			default:
				return "unknown";
		}
	}
}