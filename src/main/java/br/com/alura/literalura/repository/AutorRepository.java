package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    /**
     * Buscar todos os autores que estavam vivos em um ano específico.
     * Um autor é considerado vivo se:
     *  - nasceu antes ou no ano informado
     *  - e (faleceu depois do ano informado ou ainda está vivo)
     */
    List<Autor> findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqualOrAnoFalecimentoIsNull(Integer anoNascimento, Integer anoFalecimento);

    /**
     * Buscar autor pelo nome exato.
     */
    Optional<Autor> findByNome(String nome);
}