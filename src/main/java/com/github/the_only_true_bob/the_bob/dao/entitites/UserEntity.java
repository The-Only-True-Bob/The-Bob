package com.github.the_only_true_bob.the_bob.dao.entitites;

import com.github.the_only_true_bob.the_bob.handler.CommandStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    private Long id;
    private String vkId;
    // 1-female, 2-male, 3-none
    private String acceptableSex = "3";
    private Integer acceptableAgeDiff = 5;
    private String status = CommandStatus.NONE;

    private List<EventUserEntity> eventUsers = new ArrayList<>();

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

    public void setAcceptableSex(final String acceptableSex) {
        this.acceptableSex = acceptableSex;
    }

    public Integer getAcceptableAgeDiff() {
        return acceptableAgeDiff;
    }

    public void setAcceptableAgeDiff(final Integer acceptableAgeDiff) {
        this.acceptableAgeDiff = acceptableAgeDiff;
    }

    @OneToMany(mappedBy = "user")
    public List<EventUserEntity> getEventUserEntity() {
        return eventUsers;
    }

    public void setEventUserEntity(final List<EventUserEntity> eventUserEntity) {
        this.eventUsers = eventUserEntity;
    }

    public void addUserEventEntity(final EventUserEntity eventUserEntity) {
        this.eventUsers.add(eventUserEntity);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final UserEntity that = (UserEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (vkId != null ? !vkId.equals(that.vkId) : that.vkId != null) return false;
        if (acceptableSex != null ? !acceptableSex.equals(that.acceptableSex) : that.acceptableSex != null)
            return false;
        if (acceptableAgeDiff != null ? !acceptableAgeDiff.equals(that.acceptableAgeDiff) : that.acceptableAgeDiff != null)
            return false;
        return eventUsers != null ? eventUsers.equals(that.eventUsers) : that.eventUsers == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (vkId != null ? vkId.hashCode() : 0);
        result = 31 * result + (acceptableSex != null ? acceptableSex.hashCode() : 0);
        result = 31 * result + (acceptableAgeDiff != null ? acceptableAgeDiff.hashCode() : 0);
        result = 31 * result + (eventUsers != null ? eventUsers.hashCode() : 0);
        return result;
    }
}
