package com.altice.infra.config;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "Altice E-commerce API", version = "1.0.0", description = "Rest API for e-commerce operations including product management, shopping cart, checkout, and analytics", contact = @Contact(name = "Altice Labs Team", email = "tech@altice.com", url = "https://www.alticelabs.com"), license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT")), servers = {
        @Server(url = "http://localhost:8091", description = "Development server"),
}, tags = {
        @Tag(name = "Products", description = "Product catalog management"),
        @Tag(name = "Users", description = "User account management"),
        @Tag(name = "Shopping Carts", description = "Shopping cart operations"),
        @Tag(name = "Checkout", description = "Checkout and payment processing"),
        @Tag(name = "Analytics", description = "Business analytics and reporting")
})
public class OpenAPIConfig extends Application {
  
}