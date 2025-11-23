package br.com.equipe4.app_produtos.controller;

import br.com.equipe4.app_produtos.controller.utils.UriUtils;
import br.com.equipe4.app_produtos.model.Order;
import br.com.equipe4.app_produtos.service.OrderService;
import br.com.equipe4.app_produtos.service.dto.request.OrderRequestDTO;
import br.com.equipe4.app_produtos.service.dto.response.OrderResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderRequestDTO dto) {
        OrderResponseDTO orderResponseDTO = orderService.create(dto);
        URI uri = UriUtils.criarUriParaRecurso(orderResponseDTO.id());
        return ResponseEntity.created(uri).body(orderResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }
}