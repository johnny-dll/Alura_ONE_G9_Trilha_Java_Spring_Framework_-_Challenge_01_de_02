package br.com.alura.literalura.service;

import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    // =========================
    // LISTAR TODOS OS AUTORES
    // =========================
    public void listarAutores() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado no banco.");
            return;
        }

        System.out.println("\n----- AUTORES CADASTRADOS -----\n");
        autores.forEach(this::imprimirAutor);
    }

    // =========================================
    // LISTAR AUTORES VIVOS EM UM ANO ESPECÍFICO
    // =========================================
    public void listarAutoresVivosNoAno(Integer ano) {
        List<Autor> autores = autorRepository.buscarAutoresVivosNoAno(ano);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor estava vivo no ano " + ano);
            return;
        }

        System.out.println("\n----- AUTORES VIVOS EM " + ano + " -----\n");
        autores.forEach(this::imprimirAutor);
    }

    // =========================
    // MÉTODO PRIVADO PARA PRINT PADRÃO
    // =========================
    private void imprimirAutor(Autor autor) {
        System.out.println("Autor: " + autor.getNome());
        System.out.println("Ano de nascimento: " + autor.getAnoNascimento());
        System.out.println("Ano de falecimento: " + (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "Vivo"));
        System.out.println("-------------------------------");
    }
}