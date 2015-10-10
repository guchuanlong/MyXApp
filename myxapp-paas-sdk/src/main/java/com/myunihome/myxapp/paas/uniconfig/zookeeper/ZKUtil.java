package com.myunihome.myxapp.paas.uniconfig.zookeeper;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

public class ZKUtil
{
  public static List<ACL> createWritableACL(String authInfo)
    throws NoSuchAlgorithmException
  {
    List<ACL> acls = new ArrayList<ACL>();
    Id id2 = new Id("digest", DigestAuthenticationProvider.generateDigest(authInfo));
    ACL userACL = new ACL(31, id2);
    acls.add(userACL);
    return acls;
  }
}