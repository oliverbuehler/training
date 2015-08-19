package ch.agility.training.jpa;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;

import org.junit.Ignore;
import org.junit.Test;

import ch.agility.training.jpa.entity.Master;

public class TestJPA extends DatabaseTest {

	@Test
	public void testPersistence() throws SQLException {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("ch.agility.training.jpa");
		EntityManager em = emf.createEntityManager();

		System.out.println(DatabaseHelper.executeSqlQuery("show schemas"));
		System.out.println(DatabaseHelper.executeSqlQuery("show tables"));

		Master masterEntity = new Master();
		masterEntity.setStringAttribute("string1");

		em.getTransaction().begin();
		em.persist(masterEntity);
		em.getTransaction().commit();
		System.out.println(masterEntity.getId());
		System.out.println(
				DatabaseHelper.executeSqlQuery("select * from jpa.master"));

		Master reloaded = em.find(Master.class, masterEntity.getId());
		System.out.println(reloaded.getStringAttribute());

		em.close();
		emf.close();
	}

	@Test
	@Ignore
	public void testPersistenceLockModes() throws SQLException {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("ch.agility.training.jpa");
		EntityManager em = emf.createEntityManager();

		System.out.println(DatabaseHelper.executeSqlQuery("show schemas"));
		System.out.println(DatabaseHelper.executeSqlQuery("show tables"));

		Master masterEntity = new Master();
		masterEntity.setStringAttribute("string1");

		em.getTransaction().begin();
		em.persist(masterEntity);
		em.getTransaction().commit();
		System.out.println(masterEntity.getId());
		System.out.println(
				DatabaseHelper.executeSqlQuery("select * from jpa.master"));

		// TODO Oliver test locking
		Master reloaded = em.find(Master.class, masterEntity.getId(),
				LockModeType.PESSIMISTIC_READ);
		System.out.println(reloaded.getStringAttribute());

		em.close();
		emf.close();
	}

}
