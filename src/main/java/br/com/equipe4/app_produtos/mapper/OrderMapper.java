package br.com.equipe4.app_produtos.mapper;

import br.com.equipe4.app_produtos.model.Order;
import br.com.equipe4.app_produtos.service.dto.request.OrderRequestDTO;
import br.com.equipe4.app_produtos.service.dto.response.OrderResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "items", source = "items")
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "createdAt", ignore = true)
    Order toEntity(OrderRequestDTO dto);

    @Mapping(target = "items", source = "items")
    OrderResponseDTO toDTO(Order entity);
}
