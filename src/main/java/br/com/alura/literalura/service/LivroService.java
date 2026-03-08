package br.com.alura.literalura.service;

import br.com.alura.literalura.dto.BookResponse;
import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final ConsumoApi consumoApi;
    private final ConverteDados converteDados;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    private static final String ENDERECO_API = "https://gutendex.com/books/?search=";

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.consumoApi = new ConsumoApi();
        this.converteDados = new ConverteDados();
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    // =========================
    // BUSCAR E SALVAR LIVRO POR TÍTULO
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

        if (livroRepository.existsByTitulo(titulo)) {
            System.out.println("Livro já cadastrado no banco: " + titulo);
            return;
        }

        if (bookDto.getAuthors() == null || bookDto.getAuthors().isEmpty()) {
            System.out.println("Livro sem autor ignorado: " + titulo);
            return;
        }

        Autor autor = recuperarOuCriarAutor(bookDto.getAuthors().get(0));

        // ✅ Padroniza idioma em minúsculo para consistência
        String idioma = (bookDto.getLanguage() != null && !bookDto.getLanguage().isEmpty())
                ? bookDto.getLanguage().get(0).toLowerCase()
                : "desconhecido";

        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setIdioma(idioma);
        livro.setDownloads(bookDto.getDownloads());
        livro.setAutor(autor);

        livroRepository.save(livro);
        System.out.println("Livro salvo com sucesso!");
        System.out.println(livro);
    }

    // =========================
    // RECUPERAR OU CRIAR AUTOR
    // =========================
    private Autor recuperarOuCriarAutor(BookResponse.Book.Author authorDto) {
        return autorRepository.findByNome(authorDto.getName())
                .orElseGet(() -> autorRepository.save(
                        new Autor(authorDto.getName(), authorDto.getBirthYear(), authorDto.getDeathYear())
                ));
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
        String idiomaLower = idioma.toLowerCase(); // garante consistência
        List<Livro> livros = livroRepository.findByIdioma(idiomaLower);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma: " + idioma);
            return;
        }
        livros.forEach(System.out::println);
    }

    // =========================
    // CONTAR LIVROS POR IDIOMA (ESTATÍSTICA)
    // =========================
    public long contarPorIdioma(String idioma) {
        return livroRepository.countByIdioma(idioma.toLowerCase()); // padroniza minúsculo
    }

    // =========================
    // ESTATÍSTICAS COMPLETAS POR IDIOMA
    // =========================
    public Map<String, Long> estatisticasPorIdioma() {
        return livroRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(l -> l.getIdioma().toLowerCase(), Collectors.counting()));
    }
}