package com.vgbnd.auth_service.dto;

import com.vgbnd.auth_service.domain.Papel;
import java.util.UUID;

public record UsuarioResponseDto(
    UUID id,
    String nome,
    String email,
    String foto,
    Papel papel
) {}