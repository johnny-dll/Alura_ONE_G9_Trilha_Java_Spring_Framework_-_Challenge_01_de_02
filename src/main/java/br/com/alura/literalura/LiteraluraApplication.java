package br.com.alura.literalura;

import br.com.alura.literalura.service.LivroService;
import br.com.alura.literalura.service.AutorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final LivroService livroService;
	private final AutorService autorService;
	private final Scanner scanner = new Scanner(System.in);

	public LiteraluraApplication(LivroService livroService, AutorService autorService) {
		this.livroService = livroService;
		this.autorService = autorService;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("=== Bem-vindo ao Literalura ===");

		boolean continuar = true;
		while (continuar) {
			exibirMenu();
			String opcao = scanner.nextLine().trim();

			switch (opcao) {
				case "1" -> buscarLivro();
				case "2" -> livroService.listarLivros();
				case "3" -> exibirEstatisticas();
				case "4" -> autorService.listarAutores();
				case "5" -> listarAutoresVivos();
				case "0" -> continuar = false;
				default -> System.out.println("Opção inválida!");
			}
		}

		System.out.println("Encerrando o Literalura. Até mais!");
	}

	// =========================
	// MÉTODOS AUXILIARES
	// =========================

	private void exibirMenu() {
		System.out.println("\nEscolha uma opção:");
		System.out.println("1 - Buscar e adicionar livro por título");
		System.out.println("2 - Listar todos os livros");
		System.out.println("3 - Estatísticas por idioma");
		System.out.println("4 - Listar todos os autores");
		System.out.println("5 - Listar autores vivos em um ano");
		System.out.println("0 - Sair");
		System.out.print("Opção: ");
	}

	private void buscarLivro() {
		System.out.print("Digite o título do livro: ");
		String titulo = scanner.nextLine().trim();
		if (titulo.isEmpty()) {
			System.out.println("Título inválido!");
			return;
		}
		try {
			livroService.buscarLivroPorTitulo(titulo);
		} catch (Exception e) {
			System.out.println("Erro ao buscar o livro: " + e.getMessage());
		}
	}

	private void exibirEstatisticas() {
		System.out.println("=== Estatísticas por idioma ===");
		Map<String, Long> estatisticas = livroService.estatisticasPorIdioma();
		if (estatisticas.isEmpty()) {
			System.out.println("Sem estatísticas pois não há livros cadastrados.");
			return;
		}
		estatisticas.forEach((idioma, qtd) ->
				System.out.println("Idioma: " + idioma + " | Quantidade: " + qtd));
	}

	private void listarAutoresVivos() {
		System.out.print("Digite o ano para listar autores vivos: ");
		String inputAno = scanner.nextLine().trim();
		try {
			int ano = Integer.parseInt(inputAno);
			autorService.listarAutoresVivosNoAno(ano);
		} catch (NumberFormatException e) {
			System.out.println("Ano inválido! Digite um número inteiro.");
		}
	}
}