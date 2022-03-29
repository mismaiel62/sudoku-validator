package com.muhammad.practice.sudoku.validator.model.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBoardRequest {

  @NotNull
  private long id;

  @NotEmpty
  private int[][] board;

}
