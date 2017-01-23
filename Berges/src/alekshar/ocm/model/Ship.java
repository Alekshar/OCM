package alekshar.ocm.model;

public class Ship {
	private int arrival;
	private int duration;
	private int length;
	private int id;
	private static int count = 0;
	
	public Ship(int arrival, int duration, int length) {
		this.arrival = arrival;
		this.duration = duration;
		this.length = length;
		this.id = count++;
	}

	public int getArrival() {
		return arrival;
	}

	public int getDuration() {
		return duration;
	}

	public int getLength() {
		return length;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ship [arrival=");
		builder.append(arrival);
		builder.append(", duration=");
		builder.append(duration);
		builder.append(", length=");
		builder.append(length);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ship other = (Ship) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public static void resetCounter() {
		count=0;
	}

}
