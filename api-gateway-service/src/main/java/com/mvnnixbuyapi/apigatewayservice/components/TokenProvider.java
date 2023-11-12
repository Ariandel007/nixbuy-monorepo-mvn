package com.mvnnixbuyapi.apigatewayservice.components;

import com.mvnnixbuyapi.apigatewayservice.exceptions.JwtTokenMalformedException;
import com.mvnnixbuyapi.apigatewayservice.exceptions.JwtTokenMissingException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements Serializable {


    @Value("${jwt.signing.key}")
    public String SIGNING_KEY;

    @Value("${jwt.authorities.key}")
    public String AUTHORITIES_KEY;


    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().verifyWith(this.getSecretKey()).build().parseSignedClaims(token).getPayload();
        // Old Way:
        //return Jwts.parser().setSigningKey(SIGNING_KEY).build().parseClaimsJws(token).getPayload();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public void validateToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            Jwts.parser().verifyWith(this.getSecretKey()).build().parseSignedClaims(token);
        } catch (SignatureException ex) {
            throw new JwtTokenMalformedException("INVALID_JWT_SIGNATURE","Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtTokenMalformedException("INVALID_JWT_TOKEN","Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenMalformedException("EXPIRED_JWT_TOKEN", "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenMalformedException("UNSUPPORTED_JET","Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenMissingException("EMPTY_CLAIMS", "JWT claims string is empty");
        }
    }

    UsernamePasswordAuthenticationToken getAuthenticationToken(final String token,
                                                               final UserDetails userDetails) {
        final Claims claims = getAllClaimsFromToken(token);
        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    private SecretKey getSecretKey() {
        // decode the base64 encoded string
        byte[] bytesKey = Base64.getDecoder().decode(SIGNING_KEY);
        // rebuild key using SecretKeySpec
        SecretKey secretKey = new SecretKeySpec(bytesKey, 0, bytesKey.length, "HmacSHA256");
        return secretKey;
    }

}