package controller;

import chaosgameclasses.ChaosGameDescription;

public interface Observer {
  void update(ChaosGameDescription.ChaosGame chaosGame);
}
