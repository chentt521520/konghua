package com.shijiucheng.konghua.main.Json_;

import java.util.List;

public class JsonDataParse {

    /**
     * key : {"programmers":[{"firstName":"Brett","lastName":"McLaughlin","email":"aaaa"},{"firstName":"Jason","lastName":"Hunter","email":"bbbb"},{"firstName":"Elliotte","lastName":"Harold","email":"cccc"}],"authors":[{"firstName":"Isaac","lastName":"Asimov","genre":"science fiction"},{"firstName":"Tad","lastName":"Williams","genre":"fantasy"},{"firstName":"Frank","lastName":"Peretti","genre":"christian fiction"}],"musicians":[{"firstName":"Eric","lastName":"Clapton","instrument":"guitar"},{"firstName":"Sergei","lastName":"Rachmaninoff","instrument":"piano"}]}
     */

    private KeyBean key;

    public KeyBean getKey() {
        return key;
    }

    public void setKey(KeyBean key) {
        this.key = key;
    }

    public static class KeyBean {
        private List<ProgrammersBean> programmers;
        private List<AuthorsBean> authors;
        private List<MusiciansBean> musicians;

        public List<ProgrammersBean> getProgrammers() {
            return programmers;
        }

        public void setProgrammers(List<ProgrammersBean> programmers) {
            this.programmers = programmers;
        }

        public List<AuthorsBean> getAuthors() {
            return authors;
        }

        public void setAuthors(List<AuthorsBean> authors) {
            this.authors = authors;
        }

        public List<MusiciansBean> getMusicians() {
            return musicians;
        }

        public void setMusicians(List<MusiciansBean> musicians) {
            this.musicians = musicians;
        }

        public static class ProgrammersBean {
            /**
             * firstName : Brett
             * lastName : McLaughlin
             * email : aaaa
             */

            private String firstName;
            private String lastName;
            private String email;

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }
        }

        public static class AuthorsBean {
            /**
             * firstName : Isaac
             * lastName : Asimov
             * genre : science fiction
             */

            private String firstName;
            private String lastName;
            private String genre;

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getGenre() {
                return genre;
            }

            public void setGenre(String genre) {
                this.genre = genre;
            }
        }

        public static class MusiciansBean {
            /**
             * firstName : Eric
             * lastName : Clapton
             * instrument : guitar
             */

            private String firstName;
            private String lastName;
            private String instrument;

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getInstrument() {
                return instrument;
            }

            public void setInstrument(String instrument) {
                this.instrument = instrument;
            }
        }
    }
}
