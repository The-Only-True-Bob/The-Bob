package com.github.the_only_true_bob.the_bob.dao.entitites;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class EventEntity {

    private int	id;
    private String afisha_id;

    private Set<EventUserEntity> eventUserEntitys = new HashSet<EventUserEntity>();

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

    @OneToMany(mappedBy = "eventEntity")
    public Set<EventUserEntity> getEventUserEntitys() {
        return eventUserEntitys;
    }

    public void setEventUserEntitys(final Set<EventUserEntity> eventUserEntitys) {
        this.eventUserEntitys = eventUserEntitys;
    }

    public void addEventUser(EventUserEntity eventUserEntity) {
        this.eventUserEntitys.add(eventUserEntity);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final EventEntity that = (EventEntity) o;

        if (id != that.id) return false;
        if (afisha_id != null ? !afisha_id.equals(that.afisha_id) : that.afisha_id != null) return false;
        return eventUserEntitys != null ? eventUserEntitys.equals(that.eventUserEntitys) : that.eventUserEntitys == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (afisha_id != null ? afisha_id.hashCode() : 0);
        result = 31 * result + (eventUserEntitys != null ? eventUserEntitys.hashCode() : 0);
        return result;
    }
}
