package br.com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LivroDto {

    @JsonAlias({"title", "book_title"}) // aceita "title" ou "book_title"
    private String title;

    @JsonAlias({"language", "languages"})
    private String language;

    @JsonAlias({"downloads", "download_count"})
    private int downloads;

    @JsonAlias({"author_name", "name"})
    private String authorName;

    // ========================
    // Getters e Setters
    // ========================
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public int getDownloads() { return downloads; }
    public void setDownloads(int downloads) { this.downloads = downloads; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    @Override
    public String toString() {
        return "LivroDto{" +
                "title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", downloads=" + downloads +
                ", authorName='" + authorName + '\'' +
                '}';
    }
}