package br.com.alura.literalura.service;

import br.com.alura.literalura.dto.BookResponse;
import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;

import java.util.List;

/**
 * Classe utilitária para imprimir livros e autores de forma legível.
 * Pode ser usada para debug ou menu de teste.
 */
public class BookPrinter {

    /** Exibe livros obtidos da API (BookResponse) */
    public static void printBooksFromDto(List<BookResponse.Book> livrosDto) {
        if (livrosDto.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
            return;
        }

        for (BookResponse.Book livroDto : livrosDto) {
            System.out.println("------------------------------------------------");
            System.out.println("Título: " + livroDto.getTitle());
            System.out.println("Downloads: " + livroDto.getDownloads());
            System.out.println("Idiomas: " + livroDto.getLanguage());
            System.out.println("Autores:");
            for (BookResponse.Book.Author a : livroDto.getAuthors()) {
                System.out.println("  Nome: " + a.getName());
                System.out.println("  Nascimento: " + a.getBirthYear());
                System.out.println("  Falecimento: " + a.getDeathYear());
            }
        }
    }

    /** Converte BookResponse.Book para Livro + Autor e exibe */
    public static void printBooksAsModels(List<BookResponse.Book> livrosDto) {
        if (livrosDto.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
            return;
        }

        for (BookResponse.Book livroDto : livrosDto) {
            Autor autor = null;
            if (!livroDto.getAuthors().isEmpty()) {
                BookResponse.Book.Author aDto = livroDto.getAuthors().get(0);
                autor = new Autor(aDto.getName(), aDto.getBirthYear(), aDto.getDeathYear());
            }

            Livro livro = new Livro(
                    livroDto.getTitle(),
                    livroDto.getLanguage().isEmpty() ? "desconhecido" : livroDto.getLanguage().get(0),
                    livroDto.getDownloads(),
                    autor
            );

            System.out.println(livro);
        }
    }
}