package com.seoultech.gaemanda.pet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PetSpeciesResponse {

  private String name;

  public static PetSpeciesResponse from(String name) {
    return new PetSpeciesResponse(name);
  }

}
