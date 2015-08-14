package ch.agility.training.jpa;

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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "master")
	private Set<Detail> detailSet = new HashSet<>();

	public void addDetail(Detail detail) {
		detailSet.add(detail);
		detail.setMaster(this);
	}

}
