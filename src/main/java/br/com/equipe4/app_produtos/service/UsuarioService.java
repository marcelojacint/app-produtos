package br.com.equipe4.app_produtos.service;

import br.com.equipe4.app_produtos.model.Usuario;
import br.com.equipe4.app_produtos.repository.UsuarioRepository;
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

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> listaUsuarios = usuarioRepository.findAll();
        if (listaUsuarios.isEmpty()) {
            log.warn("Nenhum usuário encontrado no banco de dados!");
            return Collections.emptyList();
        }
        return listaUsuarios;
    }

    public void atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        usuarioRepository.findById(id).map(usuario -> {
            usuario.setId(usuario.getId());
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setSenha(usuarioAtualizado.getSenha());
            return usuarioRepository.save(usuario);
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
