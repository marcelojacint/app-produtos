package br.com.equipe4.app_produtos.controller;

import br.com.equipe4.app_produtos.model.Produtos;
import br.com.equipe4.app_produtos.repository.ProdutosRepository;
import br.com.equipe4.app_produtos.service.ProdutosService;
import br.com.equipe4.app_produtos.service.dto.ProdutoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/produtos/")
public class ProdutosController {

    private final ProdutosRepository produtosRepository;
    private final ProdutosService produtosService;

    @PostMapping("produto")
    public ResponseEntity<Produtos> criaProduto(@RequestBody Produtos produto) {
        Produtos saved = produtosRepository.save(produto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Produtos>> listaProdutos() {
        List<Produtos> produtos = produtosRepository.findAll();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<Produtos> listaProdutoPorId(@PathVariable Long id) {
        Produtos produto = produtosRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(produto);
    }

    /**
     * Exemplo de retorno de um Record.
     * @param id
     * @return
     */
    @GetMapping("/dto/{id}")
    public ResponseEntity<ProdutoDTO> listaProdutoDtoPorId(@PathVariable Long id) {
        ProdutoDTO produtoDto = produtosRepository.findByIdDto(id);

        final var produto = new Produtos();
        produto.setNome(produtoDto.nome());
        produto.setPreco(produtoDto.preco());
        produto.setCodigoBarras(produtoDto.codigoBarras());
        produtosRepository.saveAndFlush(produto);

        return ResponseEntity.ok(produtoDto);
    }

    @PutMapping("atualiza")
    public ResponseEntity<Optional<Produtos>> atualizaProduto(@RequestBody Produtos produto) {
        final var produtoExistente = produtosService.atualizaProduto(produto);
        return ResponseEntity.ok(produtoExistente);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletaProduto(@PathVariable Long id) {
        //Exemplo construindo Record
        final var p = new ProdutoDTO(1L, "dfs", "sdfa", new BigDecimal("25.6"));
        //Exemplo construindo Builder do record
        final var p2 = ProdutoDTO.builder()
                .id(1L)
                .codigoBarras("dfs")
                .nome("sdfa")
                .preco(new BigDecimal("25.6"))
                .build();
        produtosRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}