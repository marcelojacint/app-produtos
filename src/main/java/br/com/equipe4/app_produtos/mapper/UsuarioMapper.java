package br.com.equipe4.app_produtos.mapper;

import br.com.equipe4.app_produtos.model.Usuario;
import br.com.equipe4.app_produtos.service.dto.request.UsuarioRequestDto;
import br.com.equipe4.app_produtos.service.dto.response.UsuarioResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario paraUsuario(UsuarioRequestDto dto);

    UsuarioResponseDto paraUsuarioResponseDto(Usuario entidade);
}
