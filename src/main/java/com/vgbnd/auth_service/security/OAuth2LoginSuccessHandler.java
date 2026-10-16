package com.vgbnd.auth_service.security;

import com.vgbnd.auth_service.domain.Usuario;
import com.vgbnd.auth_service.repository.UsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public OAuth2LoginSuccessHandler(UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        
        String email = oAuth2User.getAttribute("email");
        String nome = oAuth2User.getAttribute("name");
        String googleSub = oAuth2User.getAttribute("sub");
        String foto = oAuth2User.getAttribute("picture");

       
       //RF02: Aceitar apenas e-mails do domínio da empresa
       
   //    if (email == null || !email.endsWith("@empresa.com.br")) {
   //         response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado: E-mail fora do domínio da empresa.");
   //         return;
   //    }
       

        // RF03 e RF04: Criar utilizador ou atualizar dados
        Optional<Usuario> usuarioExistente = usuarioRepository.findByGoogleSub(googleSub);
        Usuario usuario;

        if (usuarioExistente.isPresent()) {
            usuario = usuarioExistente.get();
            // RF08: Bloquear acesso se o utilizador estiver bloqueado
            if (usuario.isBloqueado()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado: Utilizador bloqueado.");
                return;
            }
            usuario.setNome(nome);
            usuario.setFoto(foto);
            usuario = usuarioRepository.save(usuario);
        } else {
            usuario = new Usuario(googleSub, nome, email, foto);
            usuario = usuarioRepository.save(usuario);
        }

        // RF05 e RF06: Emitir os nossos tokens
        String accessToken = jwtService.gerarAccessToken(usuario);
        String refreshToken = jwtService.gerarRefreshToken(usuario);

        // Devolver os tokens ao cliente (para testes, retornamos em JSON diretamente na resposta)
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.format("{\"access_token\": \"%s\", \"refresh_token\": \"%s\"}", accessToken, refreshToken));
    }
}