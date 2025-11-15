package br.com.equipe4.app_produtos.service;

import br.com.equipe4.app_produtos.mapper.UsuarioMapper;
import br.com.equipe4.app_produtos.model.Usuario;
import br.com.equipe4.app_produtos.repository.UsuarioRepository;
import br.com.equipe4.app_produtos.service.dto.request.UsuarioRequestDto;
import br.com.equipe4.app_produtos.service.dto.response.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper mapper;

    public UsuarioResponseDto criarUsuario(UsuarioRequestDto usuarioRequestDto) {
        Usuario usuario = mapper.paraUsuario(usuarioRequestDto);

        return mapper.paraUsuarioResponseDto(usuarioRepository.save(usuario));
    }

    public List<UsuarioResponseDto> listarUsuarios() {
        List<Usuario> listaUsuarios = usuarioRepository.findAll();

        if (listaUsuarios.isEmpty()) {
            log.warn("Nenhum usuário encontrado no banco de dados!");
            return Collections.emptyList();
        }
        return listaUsuarios.stream().map(mapper::paraUsuarioResponseDto).toList();
    }

    public void atualizarUsuario(Long id, UsuarioRequestDto usuarioRequestDto) {
        usuarioRepository.findById(id).map(usuario -> {
            usuario.setId(usuario.getId());
            usuario.setNome(usuarioRequestDto.nome());
            usuario.setEmail(usuarioRequestDto.email());
            usuario.setSenha(usuarioRequestDto.senha());
            return mapper.paraUsuarioResponseDto(usuarioRepository.save(usuario));
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado ID: " + id));

    }

    public void removerUsuario(Long id) {
        verificarExistenciaDoUsuario(id);
        usuarioRepository.deleteById(id);
    }

    private void verificarExistenciaDoUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado!");
        }
    }
}
