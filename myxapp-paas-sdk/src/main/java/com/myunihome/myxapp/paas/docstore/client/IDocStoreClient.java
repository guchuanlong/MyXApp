package com.myunihome.myxapp.paas.docstore.client;

import java.io.File;
import java.util.Date;

public abstract interface IDocStoreClient
{
  public abstract String save(File file, String remark);

  public abstract String save(byte[] bytes, String remark);

  public abstract byte[] read(String id);

  public abstract boolean delete(String id);

  public abstract void update(String id, byte[] bytes);

  public abstract void update(String id, File file);

  public abstract Date getLastUpdateTime(String id);
}