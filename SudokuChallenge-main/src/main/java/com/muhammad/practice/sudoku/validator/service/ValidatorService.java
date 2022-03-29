package com.muhammad.practice.sudoku.validator.service;

import com.muhammad.practice.sudoku.validator.model.dto.request.CreateBoardRequest;
import com.muhammad.practice.sudoku.validator.model.dto.request.UpdateBoardRequest;
import com.muhammad.practice.sudoku.validator.model.dto.response.CreateBoardResponse;
import com.muhammad.practice.sudoku.validator.model.dto.response.DeleteBoardResponse;
import com.muhammad.practice.sudoku.validator.model.dto.response.GetBoardResponse;
import com.muhammad.practice.sudoku.validator.model.dto.response.UpdateBoardResponse;
import com.muhammad.practice.sudoku.validator.model.dto.response.ValidateBoardResponse;

public interface ValidatorService {

  CreateBoardResponse createBoard(CreateBoardRequest createBoardRequest);

  GetBoardResponse getBoard(long id);

  UpdateBoardResponse updateBoard(UpdateBoardRequest updateBoardRequest);

  DeleteBoardResponse deleteBoard(long id);

  ValidateBoardResponse isValidSudoku(long boardId);

  ValidateBoardResponse isValidSudoku(int[][] grid);
}
