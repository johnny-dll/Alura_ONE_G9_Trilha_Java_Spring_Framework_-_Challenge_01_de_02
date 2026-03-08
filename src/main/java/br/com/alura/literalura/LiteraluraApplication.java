package br.com.alura.literalura;

import br.com.alura.literalura.service.LivroService;
import br.com.alura.literalura.service.AutorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
			System.out.println("\nEscolha uma opção:");
			System.out.println("1 - Buscar e adicionar livro por título");
			System.out.println("2 - Listar todos os livros");
			System.out.println("3 - Estatísticas por idioma");
			System.out.println("4 - Listar todos os autores");
			System.out.println("0 - Sair");

			String opcao = scanner.nextLine();

			switch (opcao) {
				case "1":
					System.out.print("Digite o título do livro: ");
					String titulo = scanner.nextLine();
					livroService.buscarLivroPorTitulo(titulo);
					break;
				case "2":
					livroService.listarLivros();
					break;
				case "3":
					System.out.println("=== Estatísticas por idioma ===");
					livroService.estatisticasPorIdioma()
							.forEach((idioma, qtd) -> System.out.println("Idioma: " + idioma + " | Quantidade: " + qtd));
					break;
				case "4":
					autorService.listarAutores();
					break;
				case "0":
					continuar = false;
					break;
				default:
					System.out.println("Opção inválida!");
			}
		}

		System.out.println("Encerrando o Literalura. Até mais!");
	}
}