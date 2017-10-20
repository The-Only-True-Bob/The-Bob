package com.github.the_only_true_bob.the_bob.dao.entitites;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
public class EventEntity {

    private int	id;
    private String afisha_id;

    private Set<EventUserEntity> eventUsers = new HashSet<EventUserEntity>();

    public EventEntity(){}

    public EventEntity(final String afisha_id) {
        this.afisha_id = afisha_id;
    }


    @Id
    @GeneratedValue
    @Column(name = "event_id")
    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getAfisha_id() {
        return afisha_id;
    }

    public void setAfisha_id(final String afisha_id) {
        this.afisha_id = afisha_id;
    }

    @OneToMany(mappedBy = "event")
    public Set<EventUserEntity> getEventUsers() {
        return eventUsers;
    }

    public void setEventUsers(final Set<EventUserEntity> eventUsers) {
        this.eventUsers = eventUsers;
    }

    public void addEventUser(EventUserEntity eventUserEntity) {
        this.eventUsers.add(eventUserEntity);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final EventEntity that = (EventEntity) o;

        if (id != that.id) return false;
        if (afisha_id != null ? !afisha_id.equals(that.afisha_id) : that.afisha_id != null) return false;
        return eventUsers != null ? eventUsers.equals(that.eventUsers) : that.eventUsers == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (afisha_id != null ? afisha_id.hashCode() : 0);
        result = 31 * result + (eventUsers != null ? eventUsers.hashCode() : 0);
        return result;
    }
}
