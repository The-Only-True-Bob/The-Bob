package com.github.the_only_true_bob.the_bob.vk;

import java.util.Optional;

public class User {
    // 1-female, 2-male, 3-none
    private String sex;
    private String about;
    private String birthday;
    private String city;
    private String homeTown;
    private String music;

    private User(String sex, String about, String birthday, String city, String homeTown, String music) {
        this.sex = sex;
        this.about = about;
        this.birthday = birthday;
        this.city = city;
        this.homeTown = homeTown;
        this.music = music;
    }

    private User() {}

    public static Builder builder() {
        return new Builder();
    }

    public static User empty() {
        return new User();
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

    public Optional<String> music() {
        return Optional.ofNullable(music);
    }

    public static class Builder {

        private String sex;
        private String about;
        private String birthday;
        private String city;
        private String homeTown;
        private String music;

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
            this.music = music;
            return this;
        }

        public User build() {
            return new User(sex, about, birthday, city, homeTown, music);
        }
    }
}
