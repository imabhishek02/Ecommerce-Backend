package com.ecommerce.site.Controller;

import com.ecommerce.site.Dto.ChangePasswordRequest;
import com.ecommerce.site.Dto.LoginRequest;
import com.ecommerce.site.Model.User;
import com.ecommerce.site.Service.JwtService;
import com.ecommerce.site.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

   @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

    final UserDetails userDetails = userService.getUserByEmail(loginRequest.getEmail());
    final String token = jwtService.createToken(userDetails.getUsername());
    return new ResponseEntity<>(token, HttpStatus.OK);

   }

   @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user){
           return new ResponseEntity<>(userService.userRegistor(user),HttpStatus.CREATED);
   }
   @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody  ChangePasswordRequest request){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String email = authentication.getName();
       userService.changePassword(email,request);
       return  ResponseEntity.ok().body("Password Changed");
   }

}
