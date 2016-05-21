package com.myunihome.myxapp.paas.i18n;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class MultiResourceBundle extends ResourceBundle
{
  protected static final ResourceBundle.Control CONTROL = new MultiResourceBundleControl();
  private static Properties properties = new Properties();

  public MultiResourceBundle()
  {
  }

  public MultiResourceBundle(String baseName) {
    setParent(ResourceBundle.getBundle(baseName, CONTROL));
  }

  protected Object handleGetObject(String key)
  {
    return properties != null ? properties.get(key) : this.parent.getObject(key);
  }

  public Enumeration<String> getKeys()
  {
    return properties != null ? (Enumeration<String>)properties.propertyNames() : this.parent.getKeys();
  }

  protected static class MultiResourceBundleControl extends ResourceBundle.Control
  {
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
      throws IllegalAccessException, InstantiationException, IOException
    {
      MultiResourceBundle.properties.putAll(load(baseName, locale, loader));
      return new MultiResourceBundle();
    }

    private Properties load(String baseName, Locale locale, ClassLoader loader) throws IOException
    {
      Properties properties = new Properties();
      properties.load(loader.getResourceAsStream(baseName + "_" + locale.getDisplayName() + ".properties"));

      return properties;
    }
  }
}