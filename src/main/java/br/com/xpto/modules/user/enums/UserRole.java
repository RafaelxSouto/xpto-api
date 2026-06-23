package br.com.xpto.modules.user.enums;

import lombok.Getter;

@Getter
public enum UserRole {
  USER(0),
  ADMIN(1);

  private final int level;

  UserRole(int level){
      this.level = level;
  }
}
