package ch.agility.training.jpa.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "master")
public class Master {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String stringAttribute;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "master")
	private Set<Detail> detailSet = new HashSet<>();

	public int getId() {
		return id;
	}

	public String getStringAttribute() {
		return stringAttribute;
	}

	public void setStringAttribute(String stringAttribute) {
		this.stringAttribute = stringAttribute;
	}

	public void addDetail(Detail detail) {
		detailSet.add(detail);
		detail.setMaster(this);
	}

}
