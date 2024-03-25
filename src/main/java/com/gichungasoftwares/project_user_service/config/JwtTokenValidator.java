package com.gichungasoftwares.project_user_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);
        System.out.println("jwt ----- " + jwt);
        if(jwt!=null){
            jwt = jwt.substring(7);
            try{
                //parse a jwt token and extract the email and authorities from it to set up authentication from it
                SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                System.out.println("secret key -- hmac " +secretKey);
                Claims claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();

                System.out.println("claims ----------- " +claims);
                //null checks on the parsed claim
                if(claims != null){
                    String email = String.valueOf(claims.get("email"));
                    System.out.println("email ---- " +email);
                    String authorities = String.valueOf(claims.get("authorities"));

                    List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, grantedAuthorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch (Exception e){
//                logger.error(new ParameterizedMessage("Error parsing JWT token: {}", e.getMessage()),e);
                throw new BadCredentialsException("Invalid token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
