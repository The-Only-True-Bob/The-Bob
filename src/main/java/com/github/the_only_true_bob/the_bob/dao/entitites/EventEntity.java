package com.github.the_only_true_bob.the_bob.dao.entitites;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
public class EventEntity {

    private Long id;
    private String afishaId;
    private String afishaUrl;
    private String afishaImgUrl;
    private String date;
    private String placeName;
    private String placeAddress;
    private String type;
    private String name;

    @OneToMany(mappedBy = "event")
    private Set<EventUserEntity> eventUsers = new HashSet<>();

    public EventEntity(){}

    public EventEntity(final String afishaId, final String placeName, final String placeAddress, final String type, final String name) {
        this.afishaId = afishaId;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.type = type;
        this.name = name;
    }

    @Id
    @GeneratedValue
    @Column(name = "event_id")
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getAfishaId() {
        return afishaId;
    }

    public void setAfishaId(final String afishaId) {
        this.afishaId = afishaId;
    }

    public Set<EventUserEntity> getEventUsers() {
        return eventUsers;
    }

    public void setEventUsers(final Set<EventUserEntity> eventUsers) {
        this.eventUsers = eventUsers;
    }

    public void addEventUser(EventUserEntity eventUserEntity) {
        this.eventUsers.add(eventUserEntity);
    }

    public String getAfishaUrl() {
        return afishaUrl;
    }

    public void setAfishaUrl(final String afishaUrl) {
        this.afishaUrl = afishaUrl;
    }

    public String getAfishaImgUrl() {
        return afishaImgUrl;
    }

    public void setAfishaImgUrl(final String afishaImgUrl) {
        this.afishaImgUrl = afishaImgUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(final String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(final String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final EventEntity that = (EventEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (afishaId != null ? !afishaId.equals(that.afishaId) : that.afishaId != null) return false;
        if (afishaUrl != null ? !afishaUrl.equals(that.afishaUrl) : that.afishaUrl != null) return false;
        if (afishaImgUrl != null ? !afishaImgUrl.equals(that.afishaImgUrl) : that.afishaImgUrl != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (placeName != null ? !placeName.equals(that.placeName) : that.placeName != null) return false;
        if (placeAddress != null ? !placeAddress.equals(that.placeAddress) : that.placeAddress != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return eventUsers != null ? eventUsers.equals(that.eventUsers) : that.eventUsers == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (afishaId != null ? afishaId.hashCode() : 0);
        result = 31 * result + (afishaUrl != null ? afishaUrl.hashCode() : 0);
        result = 31 * result + (afishaImgUrl != null ? afishaImgUrl.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (placeName != null ? placeName.hashCode() : 0);
        result = 31 * result + (placeAddress != null ? placeAddress.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (eventUsers != null ? eventUsers.hashCode() : 0);
        return result;
    }
}
