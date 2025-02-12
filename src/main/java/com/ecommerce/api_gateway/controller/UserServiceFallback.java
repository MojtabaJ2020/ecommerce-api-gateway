package com.ecommerce.api_gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class UserServiceFallback
{
  @GetMapping("/user")
  public ResponseEntity<?> getUser()
  {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                         .body("User Service is temporarily unavailable (Get request). Please try again later.");
  }
  @PostMapping ("/user")
  public ResponseEntity<?> postUser()
  {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                         .body("User Service is temporarily unavailable (Post request). Please try again later.");
  }
  @DeleteMapping ("/user")
  public ResponseEntity<?> deleteUser()
  {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                         .body("User Service is temporarily unavailable (Delete request). Please try again later.");
  }
  
  //for all other methods
  @RequestMapping("/user")
  public ResponseEntity<?> user()
  {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                         .body("User Service is temporarily unavailable (Other request). Please try again later.");
  }
  
}
