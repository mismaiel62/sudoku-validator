package com.muhammad.practice.sudoku.validator.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBoardResponse {

  private long id;

  private int[][] board;

}