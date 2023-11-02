package javspring.tutorial.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import javspring.tutorial.userRepository;
import javspring.tutorial.BCrypt;
import javspring.tutorial.model.RegisterUserRequest;
import javspring.tutorial.model.UserResponse;
import javspring.tutorial.model.users;

@Service
public class UserService {
    
    @Autowired
    private userRepository userRepository;

    @Autowired
    private Validator validator;

    @Transactional
    public RegisterUserRequest register(RegisterUserRequest request){
        Set<ConstraintViolation<RegisterUserRequest>> constraintViolations = validator.validate(request);
        if(!constraintViolations.isEmpty()){
            throw new ConstraintViolationException(constraintViolations);
        }
        if (userRepository.existsById(request.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User already exists");
        }

        users users = new users();
        users.setUsername(request.getUsername());
        users.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        users.setPhone(request.getPhone());
        users.setAddress(request.getAddress());

        userRepository.save(users);
        return request;
    }
    public List<UserResponse> getAllUsers() {
        List<users> users = (List<javspring.tutorial.model.users>) userRepository.findAll();
        return users.stream()
                .map(user -> UserResponse.builder()
                        .username(user.getUsername())
                        .phone(user.getPhone())
                        .address(user.getAddress())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
