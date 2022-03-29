package com.muhammad.practice.sudoku.validator.service;

import static com.muhammad.practice.sudoku.validator.Constants.BOARD_SIZE;

import com.muhammad.practice.sudoku.validator.exception.ResourceNotFoundException;
import com.muhammad.practice.sudoku.validator.mapper.SudokuMapper;
import com.muhammad.practice.sudoku.validator.model.dto.request.CreateBoardRequest;
import com.muhammad.practice.sudoku.validator.model.dto.request.UpdateBoardRequest;
import com.muhammad.practice.sudoku.validator.model.dto.response.CreateBoardResponse;
import com.muhammad.practice.sudoku.validator.model.dto.response.DeleteBoardResponse;
import com.muhammad.practice.sudoku.validator.model.dto.response.GetBoardResponse;
import com.muhammad.practice.sudoku.validator.model.dto.response.UpdateBoardResponse;
import com.muhammad.practice.sudoku.validator.model.dto.response.ValidateBoardResponse;
import com.muhammad.practice.sudoku.validator.model.entity.Board;
import com.muhammad.practice.sudoku.validator.repository.SudokuRepository;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidatorServiceImpl implements ValidatorService {

  private final SudokuRepository sudokuRepository;

  @Autowired
  private SudokuMapper sudokuMapper;

  @Override
  public CreateBoardResponse createBoard(CreateBoardRequest createBoardRequest) {
    var board = sudokuRepository.save(
        Board.
            builder()
            .cells(
                sudokuMapper.convertToList(createBoardRequest.getBoard()))
            .build());
    return CreateBoardResponse.builder()
        .id(board.getId())
        .board(
            sudokuMapper.convertToArray(board.getCells()))
        .build();
  }

  @Override
  public UpdateBoardResponse updateBoard(UpdateBoardRequest updateBoardRequest) {
    var board = sudokuRepository.findById(updateBoardRequest.getId())
        .orElseThrow(() -> new ResourceNotFoundException(
            "No board found with id: " + updateBoardRequest.getId()));
    board.setCells(sudokuMapper.convertToList(updateBoardRequest.getBoard()));
    board = sudokuRepository.save(board);
    return UpdateBoardResponse.builder()
        .id(board.getId())
        .board(
            sudokuMapper.convertToArray(board.getCells()))
        .build();
  }

  @Override
  public DeleteBoardResponse deleteBoard(long id) {
    var board = sudokuRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No board found with id: " + id));
    sudokuRepository.delete(board);
    return DeleteBoardResponse.builder()
        .id(board.getId())
        .build();
  }

  @Override
  public GetBoardResponse getBoard(long id) {
    var board = sudokuRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No board found with id: " + id));
    return GetBoardResponse.builder()
        .id(board.getId())
        .board(
            sudokuMapper.convertToArray(board.getCells()))
        .build();
  }

  @Override
  public ValidateBoardResponse isValidSudoku(long id) {
    var board = sudokuRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "No board found with id: " + id));
    return ValidateBoardResponse.builder()
        .valid(checkGridValidity(
            sudokuMapper.convertToArray(board.getCells())))
        .build();
  }

  @Override
  public ValidateBoardResponse isValidSudoku(int[][] grid) {
    return ValidateBoardResponse.builder()
        .valid(checkGridValidity(grid))
        .build();
  }

  private boolean checkGridValidity(int[][] grid) {
    for (var row = 0; row < BOARD_SIZE; row++) {
      if (!checkRowAndColumnValidity(row, grid)) {
        return false;
      }
    }
    for (var row = 0; row < BOARD_SIZE; row += 3) {
      for (var col = 0; col < BOARD_SIZE; col += 3) {
        if (!checkSubGridValidity(row, col, grid)) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean checkSubGridValidity(int row, int col, int[][] grid) {
    Set<Integer> blockSet = new HashSet<>();
    for (var i = 0; i < 3; i++) {
      for (var j = 0; j < 3; j++) {
        var blockRow = row + i;
        var blockCol = col + j;
        var blockVal = grid[blockRow][blockCol];
        if (blockVal != 0 && !blockSet.add(blockVal)) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean checkRowAndColumnValidity(int row, int[][] grid) {
    Set<Integer> rowSet = new HashSet<>();
    Set<Integer> colSet = new HashSet<>();
    for (var col = 0; col < BOARD_SIZE; col++) {
      var rowValue = grid[row][col];
      var colValue = grid[col][row];

      if (rowValue < 0 || rowValue > 9
          || colValue < 0 || colValue > 9) {
        return false;
      }

      if (rowValue != 0 && !rowSet.add(rowValue)) {
        return false;
      }
      if (colValue != 0 && !colSet.add(colValue)) {
        return false;
      }

    }
    return true;
  }

}
