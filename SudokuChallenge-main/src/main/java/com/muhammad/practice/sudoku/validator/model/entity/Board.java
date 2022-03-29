package com.muhammad.practice.sudoku.validator.model.entity;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ElementCollection
  private List<Integer> cells;

}
