package fr.epita.terroirauth.controller;

import fr.epita.terroirauth.database.ConfirmationToken;
import fr.epita.terroirauth.dto.ConnectionDto;
import fr.epita.terroirauth.dto.UtilisateurDto;
import fr.epita.terroirauth.service.ConfirmationTokenService;
import fr.epita.terroirauth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Api("Api utilisateur controller")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @PostMapping("/signIn")
    @ApiOperation("api de connexion")
    public ResponseEntity signIn(@RequestBody ConnectionDto connectionDto) {
        try {
            String token = userService.signInUser(connectionDto);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signUp")
    @ApiOperation("api d'inscription")
    public ResponseEntity singUp(@RequestBody UtilisateurDto user) {
        try {
            userService.signUpUser(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/confirm")
    @ApiOperation("api de confirmation du mail")
    public void confirmMail(@RequestParam("token") String token) {
        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);
        optionalConfirmationToken.ifPresent(userService::confirmUser);
    }


}
