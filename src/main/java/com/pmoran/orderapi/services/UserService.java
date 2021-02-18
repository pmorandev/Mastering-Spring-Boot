package com.pmoran.orderapi.services;

import com.pmoran.orderapi.converters.UserConverter;
import com.pmoran.orderapi.dtos.LoginRequestDTO;
import com.pmoran.orderapi.dtos.LoginResponseDTO;
import com.pmoran.orderapi.entity.User;
import com.pmoran.orderapi.exceptions.GeneralServiceException;
import com.pmoran.orderapi.exceptions.NoDataFoundException;
import com.pmoran.orderapi.exceptions.ValidateServiceException;
import com.pmoran.orderapi.repository.UserRepository;
import com.pmoran.orderapi.validators.UserValidator;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class UserService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user){
        try{
            UserValidator.save(user);

            User userExists = userRepository.findByUserName(user.getUserName())
                    .orElse(null);

            if (userExists != null) throw new ValidateServiceException("User name already exists.");

            String passEncode = passwordEncoder.encode(user.getPassword());
            user.setPassword(passEncode);

            return userRepository.save(user);
        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public LoginResponseDTO login(LoginRequestDTO request){
        try{
            User user = userRepository.findByUserName(request.getUsername())
                    .orElseThrow(()->
                            new ValidateServiceException("Username or password are incorrect."));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
                throw new ValidateServiceException("Username or password are incorrect.");

            String token = createToken(user);

            return new LoginResponseDTO(userConverter.fromEntity(user), token);
        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    private String createToken(User user){
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + (1000 * 60 * 60));

        return Jwts.builder()
                .setSubject(user.getUserName())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (UnsupportedJwtException e) {
            log.error("JWT in a particular format/configuration that does not match the format expected");
        }catch (MalformedJwtException e) {
            log.error(" JWT was not correctly constructed and should be rejected");
        }catch (SignatureException e) {
            log.error("Signature or verifying an existing signature of a JWT failed");
        }catch (ExpiredJwtException e) {
            log.error("JWT was accepted after it expired and must be rejected");
        }
        return false;
    }

    public String getUserFromToken(String token){
        try {
            String username = Jwts.parser().setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody().getSubject();
            return username;
        }catch (UnsupportedJwtException e) {
            log.error("JWT in a particular format/configuration that does not match the format expected");
        }catch (MalformedJwtException e) {
            log.error(" JWT was not correctly constructed and should be rejected");
        }catch (SignatureException e) {
            log.error("Signature or verifying an existing signature of a JWT failed");
        }catch (ExpiredJwtException e) {
            log.error("JWT was accepted after it expired and must be rejected");
        }
        return null;
    }

}
