package com.vgbnd.auth_service.repository;

import com.vgbnd.auth_service.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    
    Optional<Usuario> findByEmail(String email);
    
    Optional<Usuario> findByGoogleSub(String googleSub);
}