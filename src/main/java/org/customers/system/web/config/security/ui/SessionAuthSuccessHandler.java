package org.customers.system.web.config.security.ui;

import lombok.RequiredArgsConstructor;
import org.customers.system.web.controllers.ui.profileForm.ProfileFormDto;
import org.customers.system.web.controllers.ui.profileForm.ProfileSession;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionAuthSuccessHandler implements AuthenticationSuccessHandler{

    private final ProfileSession profileSession;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        ProfileFormDto profileForm = (ProfileFormDto) authentication.getPrincipal();

        profileSession.saveProfile(profileForm);

        boolean admin = authentication.getAuthorities()
                .stream().anyMatch(g->g.getAuthority().equals("ROLE_ADMIN"));
        if(admin){
            redirectStrategy.sendRedirect(request, response, "/admin");
        }else {
            redirectStrategy.sendRedirect(request, response, "/logged");
        }
    }
}
