package gr.aueb.cf.expensetracker.service;

import gr.aueb.cf.expensetracker.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Claims;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    String getBearer();
    String createToken(User user);
    Key getSignKey();
    String extractUsername(String token);
    Date extractExpiration(String token);
    Claims extractAllClaims(String token);
    Boolean isTokenExpired(String token);
    Boolean validateToken(String token, UserDetails userDetails);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
}
