package br.com.equipe4.app_produtos.repository;

import br.com.equipe4.app_produtos.model.Produtos;
import br.com.equipe4.app_produtos.service.dto.ProdutoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos, Long> {

    //Projection
    @Query(nativeQuery = true, value = """
            SELECT p.id, 
            p.codigo_barras AS codigoBarras, 
            p.nome, 
            p.preco
            FROM tb_produtos p 
            WHERE p.id = :id
            """)
    ProdutoDTO findByIdDto(long id);
}
