package com.project.shop.config.security.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.*;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtPayload implements Serializable {

    @Serial
    private static final long serialVersionUID = 6577356056565058074L;

    private String email;
    private String username;
    private String firstname;
    private String role;

    public static JwtPayload parseJson(final String payload) throws IOException {
        return new ObjectMapper().readValue(payload, JwtPayload.class);
    }

    public String toJsonString() throws IOException {
        final ObjectWriter ow = new ObjectMapper().writer();
        return ow.writeValueAsString(this);
    }

}
