package models;

import bgs99c.shared.Security;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "User.findByName", query = "select u from User u where u.name = :name"),
        @NamedQuery(name = "User.findAll", query = "select u from User u")
})
@Table(name="logins")
public class User {
    @Id
    @GeneratedValue(generator = "logins_id_seq")
    @SequenceGenerator(name = "logins_id_seq", sequenceName = "logins_id_seq", allocationSize = 1)
    private int id;
    private String name;
    private byte salt;
    private String password;
    @OneToMany(mappedBy = "player")
    private List<Tournament> tournaments;

    protected User() {

    }

    public User(String name, String password) {
        this.salt = Security.createSalt();
        this.name = name;
        this.password = Security.saltUTFString(password, salt);;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int lastScore() {
        if (tournaments == null)
            return -1;

        Tournament[] t = tournaments.stream().toArray(Tournament[]::new);
        if (t.length < 1)
            return -1;

        Arrays.sort(t, Comparator.comparing(Tournament::getDate));
        return t[t.length - 1].getPosition();

        /*return tournaments.stream().sorted()
               .max(Comparator.comparing(Tournament::getDate))
               .map(Tournament::getPosition).orElse(-1);*/
    }

    public double averageScore() {
        double len = tournaments.size();
        return tournaments.stream().map(Tournament::getPosition).reduce(0, (a, b) -> a+b) / len;
    }

    public boolean checkPassword(String password, byte sessionSalt){
        try {
            return Security.saltHashString(this.password, sessionSalt).equals(password);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public byte getSalt() {
        return salt;
    }
}
