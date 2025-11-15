package br.com.equipe4.app_produtos.controller;

import br.com.equipe4.app_produtos.controller.utils.UriUtils;
import br.com.equipe4.app_produtos.mapper.UsuarioMapper;
import br.com.equipe4.app_produtos.model.Usuario;
import br.com.equipe4.app_produtos.service.UsuarioService;
import br.com.equipe4.app_produtos.service.dto.request.UsuarioRequestDto;
import br.com.equipe4.app_produtos.service.dto.response.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v1/usuario/")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UriUtils uriUtils;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> criarUsuario(@RequestBody UsuarioRequestDto usuarioRequestDto) {

        UsuarioResponseDto usuarioResponseDto = usuarioService.criarUsuario(usuarioRequestDto);

        URI uri = uriUtils.criarUriParaRecurso(usuarioResponseDto.id());

        return ResponseEntity.created(uri).body(usuarioResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listarUsuarios() {

        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDto usuarioRequestDto) {
        usuarioService.atualizarUsuario(id, usuarioRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
        usuarioService.removerUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
