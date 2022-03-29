package com.muhammad.practice.sudoku.validator.mapper;

import com.muhammad.practice.sudoku.validator.Constants;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class SudokuMapper {

  public List<Integer> convertToList(int[][] array) {
    return Stream.of(array)
        .flatMapToInt(IntStream::of)
        .boxed()
        .collect(Collectors.toList());
  }

  public int[][] convertToArray(List<Integer> list) {
    var grid = new int[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
    for (var i = 0; i < Constants.BOARD_SIZE; i++) {
      for (var j = 0; j < Constants.BOARD_SIZE; j++) {
        grid[i][j] = list.get(Constants.BOARD_SIZE * i + j);
      }
    }
    return grid;
  }

}
