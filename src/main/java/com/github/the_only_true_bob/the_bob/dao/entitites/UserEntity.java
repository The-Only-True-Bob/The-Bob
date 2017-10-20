package com.github.the_only_true_bob.the_bob.dao.entitites;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserEntity {

    private int	id;
    private String vkId;

    private Set<EventUserEntity> eventUserEntitys = new HashSet<EventUserEntity>();

    public UserEntity(){}

    public UserEntity(final String vkId) {
        this.vkId = vkId;
    }

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getVkId() {
        return vkId;
    }

    public void setVkId(final String vkId) {
        this.vkId = vkId;
    }

    @OneToMany(mappedBy = "userEntity")
    public Set<EventUserEntity> getEventUserEntity() {
        return eventUserEntitys;
    }

    public void setEventUserEntity(final Set<EventUserEntity> eventUserEntity) {
        this.eventUserEntitys = eventUserEntity;
    }

    public void addUserEventEntity(EventUserEntity eventUserEntity) {
        this.eventUserEntitys.add(eventUserEntity);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (vkId != null ? !vkId.equals(that.vkId) : that.vkId != null) return false;
        return eventUserEntitys != null ? eventUserEntitys.equals(that.eventUserEntitys) : that.eventUserEntitys == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (vkId != null ? vkId.hashCode() : 0);
        result = 31 * result + (eventUserEntitys != null ? eventUserEntitys.hashCode() : 0);
        return result;
    }
}
