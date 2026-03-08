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
        if (tituloBusca == null || tituloBusca.trim().isEmpty()) {
            System.out.println("Título inválido!");
            return;
        }

        try {
            String url = ENDERECO_API + tituloBusca.trim().replace(" ", "%20");
            String json = consumoApi.obterDados(url);

            if (json == null || json.isEmpty()) {
                System.out.println("Nenhuma resposta da API. Tente novamente mais tarde.");
                return;
            }

            BookResponse resposta = converteDados.obterDados(json, BookResponse.class);
            BookResponse.Book bookDto = obterPrimeiroLivro(resposta, tituloBusca);
            if (bookDto == null) return;

            Autor autor = recuperarOuCriarAutor(bookDto.getAuthors().get(0));
            String idioma = obterIdioma(bookDto);

            Livro livro = criarLivro(bookDto.getTitle(), idioma, bookDto.getDownloads(), autor);

            livroRepository.save(livro);
            System.out.println("Livro salvo com sucesso!");
            System.out.println(livro);

        } catch (Exception e) {
            System.out.println("Erro ao buscar ou salvar o livro: " + e.getMessage());
        }
    }

    // =========================
    // MÉTODOS AUXILIARES
    // =========================

    private BookResponse.Book obterPrimeiroLivro(BookResponse resposta, String tituloBusca) {
        if (resposta.getResults() == null || resposta.getResults().isEmpty()) {
            System.out.println("Nenhum livro encontrado para: " + tituloBusca);
            return null;
        }

        BookResponse.Book bookDto = resposta.getResults().get(0);

        if (livroRepository.existsByTitulo(bookDto.getTitle())) {
            System.out.println("Livro já cadastrado no banco: " + bookDto.getTitle());
            return null;
        }

        if (bookDto.getAuthors() == null || bookDto.getAuthors().isEmpty()) {
            System.out.println("Livro sem autor ignorado: " + bookDto.getTitle());
            return null;
        }

        return bookDto;
    }

    private String obterIdioma(BookResponse.Book bookDto) {
        return (bookDto.getLanguage() != null && !bookDto.getLanguage().isEmpty())
                ? bookDto.getLanguage().get(0).toLowerCase()
                : "desconhecido";
    }

    private Livro criarLivro(String titulo, String idioma, int downloads, Autor autor) {
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setIdioma(idioma);
        livro.setDownloads(downloads);
        livro.setAutor(autor);
        return livro;
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
        if (idioma == null || idioma.trim().isEmpty()) {
            System.out.println("Idioma inválido!");
            return;
        }

        List<Livro> livros = livroRepository.findByIdioma(idioma.toLowerCase().trim());
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
        if (idioma == null || idioma.trim().isEmpty()) return 0;
        return livroRepository.countByIdioma(idioma.toLowerCase().trim());
    }

    // =========================
    // ESTATÍSTICAS COMPLETAS POR IDIOMA
    // =========================
    public Map<String, Long> estatisticasPorIdioma() {
        Map<String, Long> stats = livroRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(l -> l.getIdioma().toLowerCase(), Collectors.counting()));

        if (stats.isEmpty()) {
            System.out.println("Sem estatísticas pois não há livros em sua tabela.");
        }

        return stats;
    }
}