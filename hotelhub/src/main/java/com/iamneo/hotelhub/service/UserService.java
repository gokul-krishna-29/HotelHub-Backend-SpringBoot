package com.iamneo.hotelhub.service;

import com.iamneo.hotelhub.impl.UserRequest;
import com.iamneo.hotelhub.impl.UserResponse;
import com.iamneo.hotelhub.repository.UserRepository;
import com.iamneo.hotelhub.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository repository; // You need to create a UserRepository interface

    public UserResponse getUserProfile(String userEmail) {
        Optional<Users> userOptional = repository.findByEmail(userEmail);

        if (userOptional.isPresent()) {
            Users user = userOptional.get();

            UserResponse userResponse = new UserResponse();
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setAge(user.getAge());
            userResponse.setEmail(user.getEmail());
            userResponse.setPhone(user.getPhone());
            userResponse.setCity(user.getCity());
            userResponse.setGender(user.getGender());
            userResponse.setCountry(user.getCountry());
            // Set other fields as needed

            return userResponse;
        } else {
            return null; // or return an appropriate response if the user is not found
        }
    }

    public ResponseEntity<String> updateUserProfile(String userEmail, UserRequest userRequest) {
        // Get the user by email
        Optional<Users> userOptional = repository.findByEmail(userEmail);

        if (userOptional.isPresent()) {
            Users user = userOptional.get();

            // Update the user's information based on the request
            if (userRequest.getFirstName() != null) {
                user.setFirstName(userRequest.getFirstName());
            }
            if (userRequest.getLastName() != null) {
                user.setLastName(userRequest.getLastName());
            }
            if (userRequest.getAge() != null) {
                user.setAge(userRequest.getAge());
            }
            if (userRequest.getCity() != null) {
                user.setCity(userRequest.getCity());
            }
            if (userRequest.getCountry() != null) {
                user.setCountry(userRequest.getCountry());
            }
            if (userRequest.getPhone() != null) {
                user.setPhone(userRequest.getPhone());
            }
            if (userRequest.getPassword() != null) {
                user.setPassword(userRequest.getPassword());
            }
            if(userRequest.getGender()!=null){
                    user.setGender(userRequest.getGender());
            }

            // Save the updated user
            repository.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("Profile updated successfully"); // Return a success message
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User not found"); // Return a success message
        }
    }

    public ResponseEntity<String> deleteUserProfile(String email) {
        // Check if the user exists
        Optional<Users> user = repository.findByEmail(email);

        if (user.isPresent()) {
            // If the user exists, you can delete the user from the database
            repository.delete(user.get());
            return ResponseEntity.noContent().build();
        } else {
            // User not found, return an error response
            return ResponseEntity.notFound().build();
        }
    }
}

