package br.com.alura.literalura.service;

import br.com.alura.literalura.dto.BookResponse;
import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private final ConsumoApi consumoApi;
    private final ConverteDados converteDados;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    private final String ENDERECO_API = "https://gutendex.com/books/?search=";

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.consumoApi = new ConsumoApi();
        this.converteDados = new ConverteDados();
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    // =========================
    // BUSCAR LIVRO POR TÍTULO
    // =========================
    public void buscarLivroPorTitulo(String tituloBusca) {

        String url = ENDERECO_API + tituloBusca.replace(" ", "%20");

        String json = consumoApi.obterDados(url);

        BookResponse resposta = converteDados.obterDados(json, BookResponse.class);

        if (resposta.getResults() == null || resposta.getResults().isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
            return;
        }

        BookResponse.Book bookDto = resposta.getResults().get(0);

        String titulo = bookDto.getTitle();

        Optional<Livro> livroExistente = livroRepository.findByTitulo(titulo);
        if (livroExistente.isPresent()) {
            System.out.println("Livro já está salvo no banco.");
            return;
        }

        if (bookDto.getAuthors() == null || bookDto.getAuthors().isEmpty()) {
            System.out.println("Livro sem autor ignorado: " + titulo);
            return;
        }

        String nomeAutor = bookDto.getAuthors().get(0).getName();

        Autor autor = autorRepository
                .findByNome(nomeAutor)
                .orElseGet(() -> autorRepository.save(new Autor(nomeAutor)));

        String idioma = "desconhecido";
        if (bookDto.getLanguage() != null && !bookDto.getLanguage().isEmpty()) {
            idioma = bookDto.getLanguage().get(0);
        }

        Integer downloads = bookDto.getDownloads();

        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setIdioma(idioma);
        livro.setDownloads(downloads);
        livro.setAutor(autor);

        livroRepository.save(livro);

        System.out.println("Livro salvo com sucesso!");
        System.out.println(livro);
    }

    // =========================
    // LISTAR TODOS OS LIVROS
    // =========================
    public void listarLivros() {

        List<Livro> livros = livroRepository.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }

        livros.forEach(System.out::println);
    }

    // =========================
    // LISTAR LIVROS POR IDIOMA
    // =========================
    public void listarLivrosPorIdioma(String idioma) {

        List<Livro> livros = livroRepository.findByIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma: " + idioma);
            return;
        }

        livros.forEach(System.out::println);
    }
}