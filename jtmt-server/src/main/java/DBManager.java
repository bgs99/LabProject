import java.util.List;
import java.util.stream.Stream;

import bgs99c.shared.UserStats;
import models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

class DBManager {
	private EntityManager manager;

    DBManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory( "HIBERNATE_JPA" );
        manager = factory.createEntityManager();
    }

    private User getUserByName(String name) {
		return manager.createNamedQuery("User.findByName", User.class).setParameter("name", name)
				.getResultStream().findAny().orElse(null);
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

	void recordTournament(List<String> players) {
    	manager.getTransaction().begin();
		for(int i = 0; i < players.size(); i++) {
            manager.persist(new Tournament(
                    getUserByName(players.get(i)),
                    players.size() - i
            ));
		}
		manager.getTransaction().commit();
	}

	byte getSalt(String name) {
    	List<User> u = manager.createQuery("select u from User u").getResultList();
    	return getUserByName(name).getSalt();
	}

	boolean checkPassword(String name, String password, byte sessionSalt) {
		User u = getUserByName(name);
		System.out.println("Got user from DB.");
        if(u == null)
            return false;
        System.out.println("User is not null.");

        return u.checkPassword(password, sessionSalt);
	}
	boolean addUser(String name, String password) {
		if(getUserByName(name) != null) {
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
