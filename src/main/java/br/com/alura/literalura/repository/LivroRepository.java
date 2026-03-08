package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Consulta derivada para buscar livros por idioma
    List<Livro> findByIdioma(String idioma);

    // Novo método para verificar se o livro já existe pelo título
    Optional<Livro> findByTitulo(String titulo);}