package com.seoultech.gaemanda.pet;

public enum Species {

  MALTESE("말티즈"),
  POODLE("푸들"),
  POMERANIAN("포메라니안"),
  CHIHUAHUA("치와와"),
  SHIHTZU("시츄"),
  ETC("기타");

  private final String name;

  Species(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static boolean find(String value) {

    for (Species species : values()) {
      if (species.getName().equals(value)) {
        return true;
      }
    }
    return false;
  }


}
