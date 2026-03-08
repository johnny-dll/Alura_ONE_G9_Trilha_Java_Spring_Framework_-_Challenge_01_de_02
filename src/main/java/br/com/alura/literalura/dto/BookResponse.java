package br.com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookResponse {

    private int count;
    private String next;
    private String previous;
    private List<Book> results;

    // ===== Getters e Setters =====
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    public String getNext() { return next; }
    public void setNext(String next) { this.next = next; }

    public String getPrevious() { return previous; }
    public void setPrevious(String previous) { this.previous = previous; }

    public List<Book> getResults() { return results; }
    public void setResults(List<Book> results) { this.results = results; }

    @Override
    public String toString() {
        return "BookResponse{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + results +
                '}';
    }

    // =========================
    // CLASSE INTERNA Book
    // =========================
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Book {

        @JsonAlias({"title", "book_title"})
        private String title;

        @JsonAlias({"download_count", "downloads"})
        private int downloads;

        @JsonProperty("languages")
        private List<String> language;

        @JsonProperty("authors")
        private List<Author> authors;

        // ===== Getters e Setters =====
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public int getDownloads() { return downloads; }
        public void setDownloads(int downloads) { this.downloads = downloads; }

        public List<String> getLanguage() { return language; }
        public void setLanguage(List<String> language) { this.language = language; }

        public List<Author> getAuthors() { return authors; }
        public void setAuthors(List<Author> authors) { this.authors = authors; }

        @Override
        public String toString() {
            return "Book{" +
                    "title='" + title + '\'' +
                    ", downloads=" + downloads +
                    ", language=" + language +
                    ", authors=" + authors +
                    '}';
        }

        // =========================
        // CLASSE INTERNA Author
        // =========================
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Author {

            @JsonAlias({"name", "author_name"})
            private String name;

            @JsonAlias({"birth_year"})
            private Integer birthYear;

            @JsonAlias({"death_year"})
            private Integer deathYear;

            // ===== Getters e Setters =====
            public String getName() { return name; }
            public void setName(String name) { this.name = name; }

            public Integer getBirthYear() { return birthYear; }
            public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }

            public Integer getDeathYear() { return deathYear; }
            public void setDeathYear(Integer deathYear) { this.deathYear = deathYear; }

            @Override
            public String toString() {
                return "Author{" +
                        "name='" + name + '\'' +
                        ", birthYear=" + birthYear +
                        ", deathYear=" + deathYear +
                        '}';
            }
        }
    }
}