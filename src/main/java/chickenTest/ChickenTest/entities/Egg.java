package chickenTest.ChickenTest.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Egg {
	@Id
	@GeneratedValue
	private Integer id;

	private Date date;

	public Egg(Date date) {
		this.date = date;
	}

	public Egg() {
		this.date = new Date();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Egg [id=" + id + ", date=" + date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}