package br.ifrn.edu.jeferson.ecommerce.repository;

import br.ifrn.edu.jeferson.ecommerce.domain.ProdutoCategoria;
import br.ifrn.edu.jeferson.ecommerce.domain.ProdutoCategoriaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoCategoriaRepository extends JpaRepository<ProdutoCategoria, ProdutoCategoriaId> {
    List<ProdutoCategoria> findByCategoriaId(Long categoriaId);
}
