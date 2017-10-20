package com.github.the_only_true_bob.the_bob.dao.entitites;

import javax.persistence.*;

@Entity
public class EventUserEntity {

    private int	id;
    private String status;
    private String stage;
    private UserEntity userEntity;
    private EventEntity eventEntity;

    @Id
    @GeneratedValue
    @Column(name = "event_user_id")
    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(final String stage) {
        this.stage = stage;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "user_id")
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(final UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "event_id")
    public EventEntity getEventEntity() {
        return eventEntity;
    }

    public void setEventEntity(final EventEntity eventEntity) {
        this.eventEntity = eventEntity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final EventUserEntity that = (EventUserEntity) o;

        if (id != that.id) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (stage != null ? !stage.equals(that.stage) : that.stage != null) return false;
        if (userEntity != null ? !userEntity.equals(that.userEntity) : that.userEntity != null) return false;
        return eventEntity != null ? eventEntity.equals(that.eventEntity) : that.eventEntity == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (stage != null ? stage.hashCode() : 0);
        result = 31 * result + (userEntity != null ? userEntity.hashCode() : 0);
        result = 31 * result + (eventEntity != null ? eventEntity.hashCode() : 0);
        return result;
    }
}
