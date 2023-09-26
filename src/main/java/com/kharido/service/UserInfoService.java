package com.kharido.service;

import com.kharido.entity.User;
import com.kharido.exception.UserAlreadyExistsException;
import com.kharido.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        return user.map(UserInfoDetails::new).orElseThrow(()->new UsernameNotFoundException("User not found " + username));
    }

    public User addUser(User user) {
        boolean userExists = checkExistsByEmail(user.getEmail());
        if(!userExists) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        else
        {
            throw new UserAlreadyExistsException(user.getEmail());
        }
    }

    public boolean checkExistsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

}
