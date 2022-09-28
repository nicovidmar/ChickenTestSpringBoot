package chickenTest.ChickenTest.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Farm {
	@Id
	private int id;
	private double cash;
	private Date date;
	private int maxEggs;
	private int maxChickens;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public Farm() {

	}

	public int getMaxEggs() {
		return maxEggs;
	}

	public void setMaxEggs(int maxEggs) {
		this.maxEggs = maxEggs;
	}

	public int getMaxChickens() {
		return maxChickens;
	}

	public void setMaxChickens(int maxChickens) {
		this.maxChickens = maxChickens;
	}

}
