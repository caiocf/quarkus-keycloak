package com.example;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/")
@Authenticated
@RequestScoped
public class ExampleResource {

    @Inject
    private SecurityIdentity identity;

    @Inject
    private JsonWebToken jwt;

    @Inject
    @Claim(standard = Claims.family_name)
    private String lastName;

    @GET
    @Path("users")
    @RolesAllowed("user")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloUsers() {
        return "Hello User " + identity.getPrincipal().getName();
    }


    @GET
    @Path("admins")
    @RolesAllowed("admin")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloAdmin() {
        return "Hello Admin " + identity.getPrincipal().getName();
    }

    @GET
    @Path("users_admins")
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.TEXT_PLAIN)
    public String helloAdminUser() {
        return "Hello Admins e Users de " + identity.getPrincipal().getName();
    }

    @GET
    @Path("public")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPublic() {
        return "public RESTEasy";
    }

    @GET
    @Path("authenticated")
    @Produces(MediaType.TEXT_PLAIN)
    @Authenticated
    public String helloAuthenticated() {
        return "authenticated com e-mail " +
                jwt.getClaim("email") +
                ", nome: " + identity.getPrincipal().getName() +
                ", sobrenome: " + lastName;

    }
}
