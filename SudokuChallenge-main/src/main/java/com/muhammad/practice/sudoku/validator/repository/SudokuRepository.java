package com.muhammad.practice.sudoku.validator.repository;

import com.muhammad.practice.sudoku.validator.model.entity.Board;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface SudokuRepository extends CrudRepository<Board, Long> {

  Optional<Board> findById(Long id);
}
