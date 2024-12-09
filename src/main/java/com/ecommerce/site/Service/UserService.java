package com.ecommerce.site.Service;

import com.ecommerce.site.Model.User;
import com.ecommerce.site.Repository.UserRepo;
import com.ecommerce.site.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ecommerce.site.Dto.ChangePasswordRequest;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public User userRegistor(User user){
        if(userRepo.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalStateException("Email already exists");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(User.Role.USER);
        return userRepo.save(user);
    }

    public User getUserByEmail(String email){
        return userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }
    public void changePassword(String email,ChangePasswordRequest request){
        User user = getUserByEmail(email);

        if(!encoder.matches(request.getOldPassword(),user.getPassword())){
            throw new BadCredentialsException("Current Password is Incorrect");
        }
        user.setPassword(encoder.encode(request.getNewPassword()));
        userRepo.save(user);
    }


}
