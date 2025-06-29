package kr.ac.kopo.su.dnf_login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Add view controllers to map URLs directly to view names without requiring a controller method
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redirect the root path "/" to "main.html".
        // This ensures that when a user accesses http://localhost:8080/, they are directed
        // to the main page which is set to be publicly accessible in SecurityConfig.
        registry.addRedirectViewController("/", "/main.html");

        // Explicitly map /main.html to the main view, though it might be automatically handled.
        // Corrected typo: addViewController instead of addViewControler
        registry.addViewController("/main.html").setViewName("main");
        // Map /login.html to the login view
        registry.addViewController("/login.html").setViewName("login");
        // Map /signup.html to the signup view
        registry.addViewController("/signup.html").setViewName("signup");
        // Map /home.html to the home view
        registry.addViewController("/home.html").setViewName("home");
        // Map /search.html to the search view
        registry.addViewController("/search.html").setViewName("search");
    }
}