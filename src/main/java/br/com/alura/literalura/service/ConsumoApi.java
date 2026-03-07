package br.com.alura.literalura.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.alura.literalura.dto.BookResponse;
import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class ConsumoApi {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final ObjectMapper mapper;

    public ConsumoApi(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
        this.mapper = new ObjectMapper();
    }

    /** Faz requisição HTTP GET e retorna o JSON como String. */
    public String obterDados(String endereco) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .header("User-Agent", "Mozilla/5.0")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao consultar a API: " + e.getMessage(), e);
        }
    }

    /** Busca livros na API Gutendex e converte o JSON para BookResponse. */
    public BookResponse buscarLivroGutendex(String titulo) {
        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "+");
        String json = obterDados(url);

        try {
            return mapper.readValue(json, BookResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar JSON do Gutendex: " + e.getMessage(), e);
        }
    }

    /** Salva livros e autores no banco usando Optional<Autor>. */
    public int salvarLivrosGutendex(String titulo) {
        BookResponse response = buscarLivroGutendex(titulo);
        int contador = 0;

        for (BookResponse.Book bookDto : response.getResults()) {
            Autor autor = null;

            if (!bookDto.getAuthors().isEmpty()) {
                BookResponse.Book.Author aDto = bookDto.getAuthors().get(0);

                // Busca autor existente como Optional
                Optional<Autor> autorExistente = autorRepository.findByNome(aDto.getName());
                autor = autorExistente.orElseGet(() -> {
                    Autor novoAutor = new Autor(aDto.getName(), aDto.getBirthYear(), aDto.getDeathYear());
                    return autorRepository.save(novoAutor);
                });
            }

            String idioma = (bookDto.getLanguage() == null || bookDto.getLanguage().isEmpty())
                    ? "desconhecido"
                    : bookDto.getLanguage().get(0);

            Livro livro = new Livro(bookDto.getTitle(), idioma, bookDto.getDownloads(), autor);
            livroRepository.save(livro);
            contador++;
        }

        return contador;
    }

    /**
     * Busca livros na API e retorna a lista de livros convertidos sem salvar no banco.
     * Útil para testes e inspeção de dados antes do passo de persistência.
     */
    public List<Livro> buscarLivrosSemSalvar(String titulo) {
        BookResponse response = buscarLivroGutendex(titulo);

        return response.getResults().stream().map(bookDto -> {
            Autor autor = null;
            if (!bookDto.getAuthors().isEmpty()) {
                BookResponse.Book.Author aDto = bookDto.getAuthors().get(0);
                autor = new Autor(aDto.getName(), aDto.getBirthYear(), aDto.getDeathYear());
            }

            String idioma = (bookDto.getLanguage() == null || bookDto.getLanguage().isEmpty())
                    ? "desconhecido"
                    : bookDto.getLanguage().get(0);

            return new Livro(bookDto.getTitle(), idioma, bookDto.getDownloads(), autor);
        }).toList();
    }
}