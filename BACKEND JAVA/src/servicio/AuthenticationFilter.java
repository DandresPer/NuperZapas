package servicio;

import java.io.IOException;
import java.security.Key;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Base64;

@Secured
@Provider
@Priority (Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
	
	private static final String REALM="example";
	private static final String AUTHENTICATION_SCHEME="Bearer";
	@Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println("TOKEN1");
        // Get the Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader
                            .substring(AUTHENTICATION_SCHEME.length()+1).trim();
        //haz esto o muerete intentando averiguar que pasa (NO MOLA NADA)
        token = token.replace("\"", "");
        //token= token +"f";
        try {

            // Validate the token
            validateToken(token);

        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {
		System.out.println("TOKEN BASED AUTHENTICATION" + authorizationHeader);
        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                    .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
		System.out.println("TOKEN ABORTANDO");
        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, 
                                AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
                        .build());
    }

    private void validateToken(String token) throws Exception {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
		System.out.println("TOKEN VALIDAR: " + token);
		if(!APIRESTService.opera.checkToken(token)) {
			throw new Exception("ASDAkjSD"); 
		}
		
		/*
		 * try { APIRESTService.opera.checkToken(token); }catch(Exception e) {
		 * System.out.println(e.getMessage()); } throw new Exception("ASDAkjSD");
		 */
    	
    	
    }
    
    
    }


	
