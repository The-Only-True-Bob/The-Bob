package com.github.the_only_true_bob.the_bob.vk;

public class User {
    private String about;
    private String birthday;
    private String city;
    private String homeTown;
    private String music;

    private User(String about, String birthday, String city, String homeTown, String music) {
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

    public String getAbout() {
        return about;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getCity() {
        return city;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public String getMusic() {
        return music;
    }

    public static class Builder {

        private String about;
        private String birthday;
        private String city;
        private String homeTown;
        private String music;

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
            return new User(about, birthday, city, homeTown, music);
        }
    }
}
