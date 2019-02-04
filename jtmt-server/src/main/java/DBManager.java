import java.util.List;
import java.util.stream.Stream;

import bgs99c.shared.UserStats;
import models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBManager {
	private EntityManager manager;

    DBManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory( "HIBERNATE_JPA" );
        manager = factory.createEntityManager();
    }

	UserStats[] getUsers() {
		Stream<User> st = manager.createNamedQuery("User.findAll", User.class).getResultStream();
		
		return st.map(q -> new UserStats(
                        q.getName(),
                        q.lastScore(),
                        q.averageScore(),
                        0,
                        Loader.teamInfo(q.getName())
                )).toArray(UserStats[]::new);
	}

	public void recordTournament(List<String> players) {
		for(int i = 0; i < players.size(); i++) {
            manager.persist(new Tournament(
                    manager.createNamedQuery("User.findByName", User.class)
                        .setParameter("name", players.get(i)).getSingleResult(),
                    players.size() - i
            ));
		}
	}

	public byte getSalt(String name) {
    	User u = manager.createNamedQuery("User.findByName", User.class).setParameter("name", name)
				.getSingleResult();
    	return u.getSalt();
	}

	public boolean checkPassword(String name, String password, byte sessionSalt) {
		User u = manager.createNamedQuery("User.findByName", User.class)
				.setParameter("name", name).getSingleResult();
        if(u == null)
            return false;

        return u.checkPassword(password, sessionSalt);
	}
	public boolean addUser(String name, String password) {
		if(manager.createNamedQuery("User.findByName", User.class)
                .setParameter("name", name).getResultList().size() > 0) {
		    System.out.println("User " + name + " is already registered");
		    return false;
        }
		User u = new User(name, password);
		manager.getTransaction().begin();
		manager.persist(u);
		manager.getTransaction().commit();
		return true;
	}
}
