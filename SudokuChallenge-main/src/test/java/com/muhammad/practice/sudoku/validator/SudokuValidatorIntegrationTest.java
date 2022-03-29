package com.muhammad.practice.sudoku.validator;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muhammad.practice.sudoku.validator.model.dto.request.CreateBoardRequest;
import com.muhammad.practice.sudoku.validator.model.dto.request.UpdateBoardRequest;
import com.muhammad.practice.sudoku.validator.model.dto.request.ValidateBoardRequest;
import com.muhammad.practice.sudoku.validator.model.dto.response.CreateBoardResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SudokuValidatorIntegrationTest {

  private static final int[][] THE_ONE_BOARD_GRID = new int[][]{
      {1, 2, 3, 4, 5, 6, 7, 8, 9},
      {9, 8, 7, 1, 2, 3, 4, 5, 6},
      {4, 5, 6, 0, 0, 0, 0, 0, 0},

      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},

      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0}};

  private static final int[][] THE_BAD_BOARD_GRID = new int[][]{
      {1, 2, 3, 4, 5, 6, 7, 8, 9},
      {9, 8, 7, 1, 2, 3, 4, 5, 6},
      {4, 5, 6, 0, 0, 0, 0, 0, 0},

      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},

      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0, 0, 0, 0},
      {9, 0, 0, 0, 0, 0, 0, 0, 0}};

  private static final int[][] THE_BAD_SUBGRID_BOARD = new int[][]{
      {1, 4, 6, 0, 0, 0, 0, 0, 3},
      {2, 5, 0, 0, 0, 1, 0, 0, 0},
      {3, 0, 9, 0, 0, 0, 0, 0, 0},
      {0, 8, 0, 0, 2, 0, 0, 0, 4},
      {0, 0, 0, 4, 1, 0, 0, 2, 0},
      {9, 0, 0, 0, 0, 0, 6, 0, 0},
      {0, 0, 3, 0, 0, 0, 8, 0, 9},
      {4, 0, 0, 0, 0, 2, 0, 0, 8},
      {0, 0, 1, 0, 0, 8, 0, 0, 7},
  };

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void uploadingTheOneBoard() throws Exception {
    final var createBoardRequest = objectMapper.writeValueAsString(
        CreateBoardRequest.builder().board(THE_ONE_BOARD_GRID).build());
    this.mockMvc.perform(post("/board")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createBoardRequest))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(isA(Integer.class)))
        .andExpect(jsonPath("$.board").value(isJsonArray(THE_ONE_BOARD_GRID)));
  }

  @Test
  void uploadingAndUpdatingTheOneBoard() throws Exception {
    final var createBoardRequest = objectMapper.writeValueAsString(
        CreateBoardRequest.builder().board(THE_ONE_BOARD_GRID).build());
    var createBoardResponse = asObject(
        mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createBoardRequest))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(isA(Integer.class)))
            .andExpect(jsonPath("$.board").value(isJsonArray(THE_ONE_BOARD_GRID)))
            .andReturn()
            .getResponse()
            .getContentAsString(), CreateBoardResponse.class);

    Assertions.assertNotNull(createBoardResponse);
    final var updateBoardRequest = objectMapper.writeValueAsString(
        UpdateBoardRequest.builder()
            .board(THE_BAD_BOARD_GRID)
            .id(createBoardResponse.getId())
            .build());

    var updateBoardResponse = asObject(
        mockMvc.perform(put("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateBoardRequest))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(isA(Integer.class)))
            .andExpect(jsonPath("$.board").value(isJsonArray(THE_BAD_BOARD_GRID)))
            .andReturn()
            .getResponse()
            .getContentAsString(), CreateBoardResponse.class);

    Assertions.assertNotNull(updateBoardResponse);
    Assertions.assertEquals(createBoardResponse.getId(), updateBoardResponse.getId());
    Assertions.assertNotEquals(createBoardResponse.getBoard(), updateBoardResponse.getBoard());

  }

  @Test
  void retrievingAJustUploadedBoard() throws Exception {
    long id = upload(THE_ONE_BOARD_GRID);
    this.mockMvc.perform(get("/board/{id}", id))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id));
  }

  @Test
  void retrievingNonExistingBoard() throws Exception {
    this.mockMvc.perform(get("/board/{id}", Long.MIN_VALUE))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void deletingAJustUploadedBoard() throws Exception {
    long id = upload(THE_ONE_BOARD_GRID);
    this.mockMvc.perform(delete("/board/{id}", id))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id));
  }

  @Test
  void deletingNonExistingBoard() throws Exception {
    this.mockMvc.perform(delete("/board/{id}", Long.MIN_VALUE))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void theValidBoardIsValid() throws Exception {
    long id = upload(THE_ONE_BOARD_GRID);
    this.mockMvc.perform(get("/board/{id}/isvalid", id))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.valid").value(true));
  }

  @Test
  void theBadBoardIsInvalid() throws Exception {
    long id = upload(THE_BAD_BOARD_GRID);
    this.mockMvc.perform(get("/board/{id}/isvalid", id))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.valid").value(false));
  }

  @Test
  void theValidGridIsValid() throws Exception {
    final var validateBoardRequest = objectMapper.writeValueAsString(
        ValidateBoardRequest.builder().board(THE_ONE_BOARD_GRID).build());
    this.mockMvc.perform(get("/board/isvalid")
            .contentType(MediaType.APPLICATION_JSON)
            .content(validateBoardRequest))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.valid").value(true));
  }

  @Test
  void theBadGridIsInValid() throws Exception {
    final var validateBoardRequest = objectMapper.writeValueAsString(
        ValidateBoardRequest.builder().board(THE_BAD_BOARD_GRID).build());
    this.mockMvc.perform(get("/board/isvalid")
            .contentType(MediaType.APPLICATION_JSON)
            .content(validateBoardRequest))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.valid").value(false));
  }

  @Test
  void theBadSubGridIsInValid() throws Exception {
    final var validateBoardRequest = objectMapper.writeValueAsString(
        ValidateBoardRequest.builder().board(THE_BAD_SUBGRID_BOARD).build());
    this.mockMvc.perform(get("/board/isvalid")
            .contentType(MediaType.APPLICATION_JSON)
            .content(validateBoardRequest))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.valid").value(false));
  }

  private Matcher<Iterable<? extends Iterable<? extends Integer>>> isJsonArray(int[][] grid) {
    return contains(Arrays.stream(grid)
        .map(array -> contains(
            Arrays.stream(array).mapToObj(Matchers::is).collect(Collectors.toList())))
        .collect(Collectors.toList()));
  }

  private long upload(int[][] board) throws Exception {
    final var createBoardRequest = objectMapper.writeValueAsString(
        CreateBoardRequest.builder().board(board).build());
    final var mvcResult = this.mockMvc.perform(post("/board")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createBoardRequest))
        .andExpect(status().isOk())
        .andReturn();
    final var returnedBody = mvcResult.getResponse().getContentAsString();
    final var resultingBoard = objectMapper
        .readValue(returnedBody, CreateBoardResponse.class);
    return resultingBoard.getId();
  }

  private <T> T asObject(String json, Class<T> clazz) {
    try {
      return objectMapper.readValue(json, clazz);
    } catch (IOException e) {
      return null;
    }
  }

}