package com.github.the_only_true_bob.the_bob.dao.entitites;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {

    private Long id;
    private String vkId;
    // 1-female, 2-male, 3-none
    private String acceptableSex;
    private Integer acceptableAgeDiff;

    private Set<EventUserEntity> eventUsers = new HashSet<EventUserEntity>();

    public UserEntity(){}

    public UserEntity(final String vkId) {
        this.vkId = vkId;
    }

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getVkId() {
        return vkId;
    }

    public void setVkId(final String vkId) {
        this.vkId = vkId;
    }

    public String getAcceptableSex() {
        return acceptableSex;
    }

    public void setAcceptableSex(String acceptableSex) {
        this.acceptableSex = acceptableSex;
    }

    public Integer getAcceptableAgeDiff() {
        return acceptableAgeDiff;
    }

    public void setAcceptableAgeDiff(Integer acceptableAgeDiff) {
        this.acceptableAgeDiff = acceptableAgeDiff;
    }

    @OneToMany(mappedBy = "user")
    public Set<EventUserEntity> getEventUserEntity() {
        return eventUsers;
    }

    public void setEventUserEntity(final Set<EventUserEntity> eventUserEntity) {
        this.eventUsers = eventUserEntity;
    }

    public void addUserEventEntity(EventUserEntity eventUserEntity) {
        this.eventUsers.add(eventUserEntity);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final UserEntity that = (UserEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (vkId != null ? !vkId.equals(that.vkId) : that.vkId != null) return false;
        return eventUsers != null ? eventUsers.equals(that.eventUsers) : that.eventUsers == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (vkId != null ? vkId.hashCode() : 0);
        result = 31 * result + (eventUsers != null ? eventUsers.hashCode() : 0);
        return result;
    }
}
