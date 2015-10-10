package com.myunihome.myxapp.paas.uniconfig.zookeeper;

import org.apache.zookeeper.CreateMode;

import com.myunihome.myxapp.paas.uniconfig.exception.UniConfigException;

public enum ZKAddMode
{
  PERSISTENT(0), 

  PERSISTENT_SEQUENTIAL(2), 

  EPHEMERAL(1), 

  EPHEMERAL_SEQUENTIAL(3);

  private int flag;

  private ZKAddMode(int flag) { this.flag = flag; }

  public int getFlag()
  {
    return this.flag;
  }

  public static CreateMode convertMode(int flag) throws UniConfigException {
    switch (flag) {
    case 0:
      return CreateMode.PERSISTENT;
    case 1:
      return CreateMode.EPHEMERAL;
    case 2:
      return CreateMode.PERSISTENT_SEQUENTIAL;
    case 3:
      return CreateMode.EPHEMERAL_SEQUENTIAL;
    }
    String errMsg = "Received an invalid flag value: " + flag + " to convert to a CreateMode";

    throw new UniConfigException(errMsg);
  }
}