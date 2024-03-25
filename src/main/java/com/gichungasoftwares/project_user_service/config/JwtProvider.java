package com.gichungasoftwares.project_user_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtProvider {
    static SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
    public static String generateToken(Authentication authentication){
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        String roles = populateAuthorities(grantedAuthorities);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 60*60*24*1000))
                .claim("email", authentication.getName())
                .claim("authorities", roles)
                .signWith(secretKey)
                .compact();
    }

    public static String populateAuthorities(Collection<? extends GrantedAuthority> collection){
        //convert the set of roles into string
        Set<String> authorities = new HashSet<>();
        for(GrantedAuthority authority:collection){
            authorities.add(authority.getAuthority());
        }
        return String.join(",", authorities);
    }

    public String getEmailFromJwtToken(String jwt){
        jwt = jwt.substring(7);
        Claims claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJwt(jwt).getBody();
        return String.valueOf(claims.get("email"));
    }
}
