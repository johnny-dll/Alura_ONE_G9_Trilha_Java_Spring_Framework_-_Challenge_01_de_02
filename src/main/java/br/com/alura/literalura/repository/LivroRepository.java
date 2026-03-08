package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    /**
     * Buscar livros pelo idioma (ex.: "português", "english").
     * O idioma deve estar padronizado em minúsculo para consistência.
     */
    List<Livro> findByIdioma(String idioma);

    /**
     * Buscar um livro pelo título exato.
     */
    Optional<Livro> findByTitulo(String titulo);

    /**
     * Verifica se um livro já existe pelo título.
     */
    boolean existsByTitulo(String titulo);

    /**
     * Contar livros por idioma (retorna um long).
     */
    long countByIdioma(String idioma);

    /**
     * Consulta customizada para retornar estatísticas completas por idioma.
     * Retorna lista de objetos [idioma, quantidade]
     */
    @Query("SELECT l.idioma, COUNT(l) FROM Livro l GROUP BY l.idioma")
    List<Object[]> countLivrosPorIdioma();
}