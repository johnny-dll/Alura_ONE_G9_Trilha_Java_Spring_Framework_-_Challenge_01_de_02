package br.com.alura.literalura.service;

import br.com.alura.literalura.dto.BookResponse;
import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookMapper {

    private final AutorRepository autorRepository;

    public BookMapper(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    /**
     * Converte uma lista de BookResponse.Book em uma lista de Livro pronta para salvar.
     */
    public List<Livro> mapToLivros(List<BookResponse.Book> books) {
        List<Livro> livros = new ArrayList<>();

        for (BookResponse.Book bookDto : books) {
            // Para cada autor do livro
            for (BookResponse.Book.Author authorDto : bookDto.getAuthors()) {

                // Checa se o autor já existe no banco
                Optional<Autor> autorExistente = autorRepository.findByNome(authorDto.getName());

                Autor autor = autorExistente.orElseGet(() -> {
                    // Se não existe, cria novo Autor
                    Autor novoAutor = new Autor(
                            authorDto.getName(),
                            authorDto.getBirthYear(),
                            authorDto.getDeathYear()
                    );
                    // Salva no banco
                    return autorRepository.save(novoAutor);
                });

                // Cria o Livro com o autor correspondente
                Livro livro = new Livro(
                        bookDto.getTitle(),
                        String.join(",", bookDto.getLanguage()), // converte lista de idiomas para string
                        bookDto.getDownloads(),
                        autor
                );

                livros.add(livro);
            }
        }

        return livros;
    }
}