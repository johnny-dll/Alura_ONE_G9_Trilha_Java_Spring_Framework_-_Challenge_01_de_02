package br.com.alura.literalura.model;

import jakarta.persistence.*;

/**
 * Entidade que representa um Livro.
 * Mapeada para a tabela 'livro' no banco de dados.
 */
@Entity
@Table(name = "livro") // garante o nome da tabela
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private Integer downloads;

    @ManyToOne(optional = false) // todo livro deve ter um autor
    @JoinColumn(name = "autor_id", nullable = false) // coluna foreign key
    private Autor autor;

    // ========================
    // Construtores
    // ========================

    public Livro() {}

    public Livro(String titulo, String idioma, Integer downloads, Autor autor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.downloads = downloads;
        this.autor = autor;
    }

    // ========================
    // Getters e Setters
    // ========================

    public Long getId() { return id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public Integer getDownloads() { return downloads; }
    public void setDownloads(Integer downloads) { this.downloads = downloads; }

    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }

    // ========================
    // toString
    // ========================

    @Override
    public String toString() {
        String autorNome = (autor != null && autor.getNome() != null) ? autor.getNome() : "Autor não definido";
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", downloads=" + downloads +
                ", autor=" + autorNome +
                '}';
    }
}