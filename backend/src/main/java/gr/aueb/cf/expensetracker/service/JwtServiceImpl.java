package gr.aueb.cf.expensetracker.service;

import gr.aueb.cf.expensetracker.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.bearer}")
    private String bearer;

    /**
     * Retrieves the JWT bearer type.
     *
     * @return  JWT bearer type.
     */
    @Override
    public String getBearer() {
        return bearer;
    }

    /**
     * Creates a JWT token for the given user.
     *
     * @param user  User object for which JWT token is created.
     * @return      JWT token string.
     */
    @Override
    public String createToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Retrieves the signing key used for JWT token generation.
     *
     * @return Signing key.
     */
    @Override
    public Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token JWT token string.
     * @return      username extracted from the token.
     */
    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token JWT token string.
     * @return      expiration date of the token.
     */
    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token JWT token string
     * @return      all claims extracted from the token.
     */
    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token JWT token string.
     * @return      true if token is expired, false otherwise.
     */
    @Override
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validates the JWT token against the provided UserDetails.
     *
     * @param token         JWT token string.
     * @param userDetails   UserDetails object representing the user details.
     * @return              true if token is valid for the userDetails, false otherwise.
     */
    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Extracts a specific claim from the JWT token using the given claimsResolver function.
     *
     * @param token             JWT token string.
     * @param claimsResolver    function to resolve the specific claim from Claims object
     * @return                  extracted claim value.
     * @param <T>               type of the claim value.
     */
    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}
