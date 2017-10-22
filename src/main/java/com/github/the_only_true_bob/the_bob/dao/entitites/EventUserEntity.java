package com.github.the_only_true_bob.the_bob.dao.entitites;

import com.github.the_only_true_bob.the_bob.handler.CommandStatus;

import javax.persistence.*;

@Entity
@Table(
        name = "events_users",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"event_id", "user_id"}) }
)
public class EventUserEntity {

    private Long id;
    private String status = CommandStatus.NONE;
    private String stage;
    private UserEntity user;
    private EventEntity event;
    private int number;

    public EventUserEntity(){}

    @Id
    @GeneratedValue
    @Column(name = "event_user_id")
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
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

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(final UserEntity user) {
        this.user = user;
    }

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(final EventEntity event) {
        this.event = event;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final EventUserEntity that = (EventUserEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (stage != null ? !stage.equals(that.stage) : that.stage != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return event != null ? event.equals(that.event) : that.event == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (stage != null ? stage.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (event != null ? event.hashCode() : 0);
        return result;
    }
}
