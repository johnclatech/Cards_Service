package com.johncla.cards.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
        contact = @Contact(
                name = "John Karu",
                email = "infojohnka@gmail.com",
                url = "https://github.com/johnclatech/Cards_Service.git"
        ),
        description = "Interview project for a card program",
        title = "OpenApi Specification - jkaru",
        version = "v1.0.1",
        license = @License(
                name = "No Licence applicable",
                url = "https://github.com/johnclatech/Cards_Service.git"
        ),
        termsOfService = "Terms of Service"
        ),
        servers = {
                @Server(
                        description = "Developer Environment",
                        url = "http://localhost:8081/"
                ),
                @Server(
                        description = "UAT Environment",
                        url = "http://localhost:8081/"
                ),
                @Server(
                        description = "Production Environment",
                        url = "http://localhost:8086/"
                )
        }
        /***SETTING BEARER TOKEN GLOBALLY TO ALL END POINTS***/
//        ,
//        security = {
//               @SecurityRequirement(
//                       name = "bearerAuth"
//               )
//        }
        )
        @SecurityScheme(
                name = "bearerAuth",
                description = "JWT toke generation",
                scheme = "bearer",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT",
                in = SecuritySchemeIn.HEADER

)

public class OpenApiConfig {

}
