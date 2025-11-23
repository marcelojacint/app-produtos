package br.com.equipe4.app_produtos.mapper;

import br.com.equipe4.app_produtos.model.OrderItem;
import br.com.equipe4.app_produtos.service.dto.request.OrderItemRequestDTO;
import br.com.equipe4.app_produtos.service.dto.response.OrderItemResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(OrderItemRequestDTO dto);

    OrderItemResponseDTO toDTO(OrderItem entity);
}