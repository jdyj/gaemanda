package com.seoultech.gaemanda.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListResponse<T> {

  private int count;
  private List<T> data;

  public static <T> ListResponse<T> from(List<T> collect) {
    return new ListResponse<>(collect.size(), collect);
  }

}
