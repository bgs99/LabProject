import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import bgs99c.shared.UserStats;
import models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

class DBManager {
	private EntityManager manager;
	private static ReentrantLock lock = new ReentrantLock();

    DBManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory( "HIBERNATE_JPA" );
        manager = factory.createEntityManager();
    }

    private User getUserByName(String name) {
    	try {
    		lock.lock();
			return manager.createNamedQuery("User.findByName", User.class).setParameter("name", name)
					.getResultStream().findAny().orElse(null);
		}
		finally {
    		lock.unlock();
		}
	}

	UserStats[] getUsers() {
    	System.out.println("getUsers() called");

        List<User> users = null;

    	try {
            lock.lock();
            Stream<User> st = manager.createNamedQuery("User.findAll", User.class).getResultStream();
            System.out.println("Created named query.");

            users = st.collect(Collectors.toList());
            System.out.println("Got users list.");
        }
        finally {
    	    lock.unlock();
        }

		List<UserStats> stats = new ArrayList<>();
		users.forEach(u -> stats.add(new UserStats(
				u.getName(),
				u.lastScore(),
				u.averageScore(),
				0,
				Loader.teamInfo(u.getName())
		)));

		return stats.toArray(new UserStats[stats.size()]);

//		return st.map(q -> new UserStats(
//                        q.getName(),
//                        q.lastScore(),
//                        q.averageScore(),
//                        0,
//                        Loader.teamInfo(q.getName())
//                )).toArray(UserStats[]::new);
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

	byte getSalt(String name) throws Exception {
    	//if (!manager.isOpen())
    	//	throw new Exception("Trying to use closed EntityManager.");
    	//List<User> u = manager.createQuery("select u from User u", User.class).getResultList();
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
