package com.github.the_only_true_bob.the_bob.vk;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class User {
    // 1-female, 2-male, 3-none
    private String vkId;
    private String firstName;
    private String lastName;
    private List<User> friends;
    private List<Group> groups;
    private String sex;
    private String about;
    private String birthday;
    private String city;
    private String homeTown;
    private List<String> music;

    private User(final String vkId, final String firstName, final String lastName, final List<User> friends, final List<Group> groups, final String sex, final String about, final String birthday, final String city, final String homeTown, final List<String> music) {
        this.vkId = vkId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.friends = friends;
        this.groups = groups;
        this.sex = sex;
        this.about = about;
        this.birthday = birthday;
        this.city = city;
        this.homeTown = homeTown;
        this.music = music;
    }

    private User() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static User empty() {
        return new User();
    }

    public String vkId() {
        return vkId;
    }

    public Optional<String> firstName() {
        return Optional.ofNullable(firstName);
    }

    public Optional<String> lastName() {
        return Optional.ofNullable(lastName);
    }

    public List<User> friends() {
        return Optional.ofNullable(friends).orElseGet(Collections::emptyList);
    }

    public List<Group> groups() {
        return Optional.ofNullable(groups).orElseGet(Collections::emptyList);
    }

    public Optional<String> sex() {
        return Optional.ofNullable(sex);
    }

    public Optional<String> about() {
        return Optional.ofNullable(about);
    }

    public Optional<String> birthday() {
        return Optional.ofNullable(birthday);
    }

    public Optional<String> city() {
        return Optional.ofNullable(city);
    }

    public Optional<String> homeTown() {
        return Optional.ofNullable(homeTown);
    }

    public List<String> music() {
        return Optional.ofNullable(music).orElseGet(Collections::emptyList);
    }

    public static class Builder {

        private String vkId;
        private String sex;
        private String about;
        private String birthday;
        private String city;
        private String homeTown;
        private List<String> music;
        private String firstName;
        private String lastName;
        private List<User> friends;
        private List<Group> groups;

        public Builder setVkId(final String vkId) {
            this.vkId = vkId;
            return this;
        }

        public Builder setFirstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setFriends(final List<User> friends) {
            this.friends = friends;
            return this;
        }

        public Builder setGroups(final List<Group> groups) {
            this.groups = groups;
            return this;
        }

        public Builder setSex(String sex) {
            this.sex = sex;
            return this;
        }

        public Builder setAbout(String about) {
            this.about = about;
            return this;
        }

        public Builder setBirthday(String birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setHomeTown(String homeTown) {
            this.homeTown = homeTown;
            return this;
        }

        public Builder setMusic(String music) {
            this.music =
                    Arrays.stream(music.split(",\\s*"))
                            .map(String::trim)
                            .collect(toList());
            return this;
        }

        public User build() {
            return new User(vkId, firstName, lastName, friends, groups, sex, about, birthday, city, homeTown, music);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final User user = (User) o;

        return vkId != null ? vkId.equals(user.vkId) : user.vkId == null;
    }

    @Override
    public int hashCode() {
        return vkId != null ? vkId.hashCode() : 0;
    }
}
