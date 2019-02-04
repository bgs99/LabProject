package models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="tournaments")
public class Tournament {
    @Id
    @GeneratedValue(generator = "tournaments_id_seq")
    @SequenceGenerator(name = "tournaments_id_seq", sequenceName = "tournaments_id_seq", allocationSize = 1)
    private int id;
    @ManyToOne
    @JoinColumn(name = "player")
    private User player;
    private int position;
    private Timestamp date;

    protected Tournament() {

    }

    public Tournament(User player, int position) {
        this.player = player;
        this.position = position;
        this.date = new Timestamp(System.nanoTime());
    }

    public int getId() {
        return id;
    }

    public User getPlayer() {
        return player;
    }

    public int getPosition() {
        return position;
    }

    public Timestamp getDate() {
        return date;
    }
}
