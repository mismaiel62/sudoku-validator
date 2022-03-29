package com.muhammad.practice.sudoku.validator.controller;

import com.muhammad.practice.sudoku.validator.model.dto.request.CreateBoardRequest;
import com.muhammad.practice.sudoku.validator.model.dto.request.UpdateBoardRequest;
import com.muhammad.practice.sudoku.validator.model.dto.request.ValidateBoardRequest;
import com.muhammad.practice.sudoku.validator.model.dto.response.CreateBoardResponse;
import com.muhammad.practice.sudoku.validator.model.dto.response.DeleteBoardResponse;
import com.muhammad.practice.sudoku.validator.model.dto.response.GetBoardResponse;
import com.muhammad.practice.sudoku.validator.model.dto.response.UpdateBoardResponse;
import com.muhammad.practice.sudoku.validator.model.dto.response.ValidateBoardResponse;
import com.muhammad.practice.sudoku.validator.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidatorControllerImpl implements ValidatorController {

  @Autowired
  private ValidatorService validatorService;

  @Override
  public CreateBoardResponse createBoard(CreateBoardRequest createBoardRequest) {
    return validatorService.createBoard(createBoardRequest);
  }

  @Override
  public UpdateBoardResponse updateBoard(UpdateBoardRequest updateBoardRequest) {
    return validatorService.updateBoard(updateBoardRequest);
  }

  @Override
  public DeleteBoardResponse deleteBoard(long id) {
    return validatorService.deleteBoard(id);
  }

  @Override
  public GetBoardResponse getBoard(long id) {
    return validatorService.getBoard(id);
  }

  @Override
  public ValidateBoardResponse validateBoard(long id) {
    return validatorService.isValidSudoku(id);
  }

  @Override
  public ValidateBoardResponse validateBoardGrid(ValidateBoardRequest validateBoardRequest) {
    return validatorService.isValidSudoku(validateBoardRequest.getBoard());
  }

}
