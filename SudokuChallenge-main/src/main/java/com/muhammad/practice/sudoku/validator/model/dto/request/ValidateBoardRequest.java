package com.muhammad.practice.sudoku.validator.model.dto.request;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateBoardRequest {

  @NotEmpty
  private int[][] board;

}
