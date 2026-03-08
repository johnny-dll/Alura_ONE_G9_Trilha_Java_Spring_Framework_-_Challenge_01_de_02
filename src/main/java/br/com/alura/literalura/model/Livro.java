package br.com.alura.literalura.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidade que representa um Livro.
 * Mapeada para a tabela 'livro' no banco de dados.
 */
@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String idioma;

    private Integer downloads;

    /**
     * Ano de publicação do livro
     */
    private Integer dataPublicacao;

    /**
     * Data em que o livro foi salvo no banco
     */
    private LocalDate dataSalvo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    // ========================
    // Construtores
    // ========================

    public Livro() {}

    /**
     * Construtor principal usado ao salvar livros da API
     */
    public Livro(String titulo, String idioma, Integer downloads, Autor autor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.downloads = downloads;
        this.autor = autor;
    }

    /**
     * Construtor alternativo caso queira informar publicação
     */
    public Livro(String titulo, String idioma, Integer downloads, Integer dataPublicacao, Autor autor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.downloads = downloads;
        this.dataPublicacao = dataPublicacao;
        this.autor = autor;
    }

    // ========================
    // JPA Lifecycle
    // ========================

    /**
     * Define automaticamente a data quando o livro é salvo no banco
     */
    @PrePersist
    public void prePersist() {
        this.dataSalvo = LocalDate.now();
    }

    // ========================
    // Getters e Setters
    // ========================

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Integer getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Integer dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public LocalDate getDataSalvo() {
        return dataSalvo;
    }

    public void setDataSalvo(LocalDate dataSalvo) {
        this.dataSalvo = dataSalvo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    // ========================
    // toString
    // ========================

    @Override
    public String toString() {

        String autorNome = (autor != null && autor.getNome() != null)
                ? autor.getNome()
                : "Autor não definido";

        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", downloads=" + downloads +
                ", dataPublicacao=" + dataPublicacao +
                ", dataSalvo=" + dataSalvo +
                ", autor=" + autorNome +
                '}';
    }
}