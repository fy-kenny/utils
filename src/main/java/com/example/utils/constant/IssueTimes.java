package com.example.utils.constant;

import lombok.Getter;

/**
 * @author Kenny Fang
 */
public enum IssueTimes {

  DEFAULT(100),
  TIMES_0(0),
  TIMES_30(30),
  TIMES_50(50),
  TIMES_100(100),
  TIMES_200(200),
  TIMES_300(300);

  @Getter
  private int times;

  IssueTimes(int times) {
    this.times = times;
  }

  public boolean eq(int times) {
    return this.getTimes() == times;
  }

  public boolean lt(int times) {
    return this.times < times;
  }

  public boolean gte(int times) {
    return this.times >= times;
  }

}
