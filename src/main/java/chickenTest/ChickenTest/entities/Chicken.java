package chickenTest.ChickenTest.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Chicken {
	@Id
	@GeneratedValue
	private Integer id;
	private Date date;

	public Egg depositEgg() {
		return new Egg();
	}

	public Chicken(Date date) {
		this.date = date;
	}

	public Chicken() {

	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Chicken [id=" + getId() + ", date=" + date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
