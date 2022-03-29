package com.muhammad.practice.sudoku.validator.controller;

import com.muhammad.practice.sudoku.validator.model.dto.request.CreateUserRequest;
import com.muhammad.practice.sudoku.validator.model.dto.response.CreateUserResponse;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserController {

  @PostMapping("/user")
  CreateUserResponse createUser(@Valid @RequestBody CreateUserRequest createUserRequest);

}
