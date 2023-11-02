package javspring.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javspring.tutorial.model.RegisterUserRequest;
import javspring.tutorial.model.UserResponse;
import javspring.tutorial.model.WebResponse;
import javspring.tutorial.service.UserService;

import java.util.List;

@RestController
public class userController {

    @Autowired
    private UserService userService;

//buat get all users
     @GetMapping(
        path = "/api/users")
     public WebResponse<List<UserResponse>> getAllUsers(){
         List<UserResponse> userResponse = userService.getAllUsers();
        return WebResponse.<List<UserResponse>>builder().data(userResponse).build();
     }

     @PostMapping(
        path = "/api/register",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public WebResponse<RegisterUserRequest>register(@RequestBody RegisterUserRequest request){
        RegisterUserRequest response = userService.register(request);
        return WebResponse.<RegisterUserRequest>builder()
                .data(response)
                .build();
    }
   
}
