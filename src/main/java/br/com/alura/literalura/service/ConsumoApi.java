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
import java.time.LocalDate;
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

    /** Faz requisição HTTP GET e retorna JSON */
    public String obterDados(String endereco) {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .header("User-Agent", "Mozilla/5.0")
                .build();

        try {

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao consultar API", e);
        }
    }

    /** Consulta a API Gutendex */
    public BookResponse buscarLivroGutendex(String titulo) {

        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "+");
        String json = obterDados(url);

        try {
            return mapper.readValue(json, BookResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao converter JSON", e);
        }
    }

    /** Salva livros encontrados no banco */
    public int salvarLivrosGutendex(String titulo) {

        BookResponse response = buscarLivroGutendex(titulo);

        int contador = 0;

        for (BookResponse.Book bookDto : response.getResults()) {

            // Evitar que livros dupliquem
            Optional<Livro> livroExistente = livroRepository.findByTitulo(bookDto.getTitle());

            if (livroExistente.isPresent()) {
                continue;
            }

            // Ignorar livros sem autor
            if (bookDto.getAuthors() == null || bookDto.getAuthors().isEmpty()) {
                System.out.println("Livro sem autor ignorado: " + bookDto.getTitle());
                continue;
            }

            BookResponse.Book.Author aDto = bookDto.getAuthors().get(0);

            Optional<Autor> autorExistente = autorRepository.findByNome(aDto.getName());

            Autor autor = autorExistente.orElseGet(() -> {
                Autor novoAutor = new Autor(aDto.getName(), aDto.getBirthYear(), aDto.getDeathYear());
                return autorRepository.save(novoAutor);
            });

            String idioma = (bookDto.getLanguage() == null || bookDto.getLanguage().isEmpty())
                    ? "desconhecido"
                    : bookDto.getLanguage().get(0);

            Livro livro = new Livro(
                    bookDto.getTitle(),
                    idioma,
                    bookDto.getDownloads(),
                    null,
                    autor
            );

            livro.setDataSalvo(LocalDate.now());

            livroRepository.save(livro);

            contador++;
        }

        return contador;
    }

    /** Busca livros sem salvar (modo teste) */
    public List<Livro> buscarLivrosSemSalvar(String titulo) {

        BookResponse response = buscarLivroGutendex(titulo);

        return response.getResults()
                .stream()
                .map(dto -> new Livro(
                        dto.getTitle(),
                        obterIdioma(dto),
                        dto.getDownloads(),
                        criarAutorTemporario(dto)
                ))
                .toList();
    }

    /** Obtém idioma do livro */
    private String obterIdioma(BookResponse.Book dto) {

        return (dto.getLanguage() == null || dto.getLanguage().isEmpty())
                ? "desconhecido"
                : dto.getLanguage().get(0);
    }

    /** Obtém autor do banco ou cria um novo */
    private Autor obterOuCriarAutor(BookResponse.Book dto) {

        if (dto.getAuthors().isEmpty()) return null;

        BookResponse.Book.Author a = dto.getAuthors().get(0);

        return autorRepository.findByNome(a.getName())
                .orElseGet(() ->
                        autorRepository.save(
                                new Autor(a.getName(), a.getBirthYear(), a.getDeathYear())
                        )
                );
    }

    /** Cria autor temporário (sem salvar no banco) */
    private Autor criarAutorTemporario(BookResponse.Book dto) {

        if (dto.getAuthors().isEmpty()) return null;

        BookResponse.Book.Author a = dto.getAuthors().get(0);

        return new Autor(a.getName(), a.getBirthYear(), a.getDeathYear());
    }
}