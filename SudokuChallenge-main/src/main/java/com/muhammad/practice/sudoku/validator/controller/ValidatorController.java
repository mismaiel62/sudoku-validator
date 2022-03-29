package com.muhammad.practice.sudoku.validator.controller;

import com.muhammad.practice.sudoku.validator.model.dto.request.CreateBoardRequest;
import com.muhammad.practice.sudoku.validator.model.dto.request.UpdateBoardRequest;
import com.muhammad.practice.sudoku.validator.model.dto.request.ValidateBoardRequest;
import com.muhammad.practice.sudoku.validator.model.dto.response.CreateBoardResponse;
import com.muhammad.practice.sudoku.validator.model.dto.response.DeleteBoardResponse;
import com.muhammad.practice.sudoku.validator.model.dto.response.GetBoardResponse;
import com.muhammad.practice.sudoku.validator.model.dto.response.UpdateBoardResponse;
import com.muhammad.practice.sudoku.validator.model.dto.response.ValidateBoardResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ValidatorController {

  @PostMapping("/board")
  CreateBoardResponse createBoard(@Valid @RequestBody CreateBoardRequest createBoardRequest);

  @PutMapping("/board")
  UpdateBoardResponse updateBoard(@Valid @RequestBody UpdateBoardRequest updateBoardRequest);

  @DeleteMapping("/board/{id}")
  DeleteBoardResponse deleteBoard(@PathVariable("id") @NotBlank long id);

  @GetMapping("/board/{id}")
  GetBoardResponse getBoard(@PathVariable("id") @NotBlank long id);

  @GetMapping("/board/{id}/isvalid")
  ValidateBoardResponse validateBoard(@PathVariable("id") @NotBlank long id);

  @GetMapping("/board/isvalid")
  ValidateBoardResponse validateBoardGrid(
      @Valid @RequestBody ValidateBoardRequest validateBoardRequest);
}
