package com.myunihome.myxapp.paas.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenTestUtil
{
  private static int T1 = 1;

  private static int T2 = 2;

  private static int T3 = 3;

  private static int T4 = 4;

  private static List<String> paramNames = new ArrayList();
  private static Class<?> clazz;
  private static String testClass = "";
  private static String exception = "";

  public static void main(String[] args) {
    testClass = System.getProperty("testClass");
    exception = System.getProperty("exception");
    if ((testClass == null) || (testClass.trim().length() == 0))
      throw new RuntimeException("请提供需要测试的接口");
    if ((exception == null) || (exception.length() == 0))
      exception = "IllegalArgumentException";
    genTest(testClass);
  }

  private static void genTest(String pkgName)
  {
    try
    {
      clazz = Class.forName(pkgName);
      String className = get(pkgName);
      System.out.println("className:" + className);

      String newPkg = getNewPkg(pkgName, className);
      System.out.println("newPkg:" + newPkg);

      String localPath = getLocalPath(newPkg);

      genPkgsAndBaseClass(localPath, className, newPkg);

      Method[] methods = clazz.getMethods();
      for (Method method : methods)
      {
        String methodName = method.getName();
        System.out.println("方法名称:" + methodName);

        genMethodClass(method, localPath, className, newPkg);
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static void genMethodClass(Method method, String localPath, String className, String newPkg)
  {
    String methodName = method.getName();
    String superName = className.startsWith("I") ? className.substring(1) : className;

    String filedName = className.substring(0, 1).toLowerCase() + className.substring(1);

    String classNameAll = localPath + File.separator;
    String mclassName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1) + getTestCLassName(localPath, methodName, method);

    String testFile = classNameAll + mclassName + "Test.java";

    File file = new File(testFile);

    System.out.println("create Test" + testFile);
    try {
      file.delete();
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try
    {
      FileWriter fw = new FileWriter(file, true);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write("package " + newPkg + ";\n");
      bw.write("import " + newPkg + ".base." + superName + ";\n");
      bw.write("import " + testClass + ";\n");
      bw.write("import org.junit.Before;\n");
      bw.write("import org.junit.Test;\n");
      bw.write("public class " + mclassName + "Test extends " + superName + "{\n");

      bw.write("private " + className + " " + filedName + "  = null;\n");
      bw.write("\n");

      bw.write("@Before\n");
      bw.write("public void setUp() throws Exception {\n");
      bw.write(filedName + " = super.getClient();\n");
      bw.write("}\n");
      bw.write("\n");

      Class[] parameterTypes = method.getParameterTypes();
      int num = parameterTypes.length;

      for (int t = T1; t <= T3; t++) {
        if (t == T1) {
          paramNames.clear();
          appendNorMalMoth(bw, methodName, t, parameterTypes);
          appendEndMoth(bw, filedName, methodName);
        } else {
          for (int i = 0; i < num; i++) {
            paramNames.clear();
            appendExpMoth(bw, methodName, t, i, parameterTypes);
            appendEndMoth(bw, filedName, methodName);
          }
        }
      }

      bw.write("}\n");

      bw.flush();
      bw.close();
      fw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String getTestCLassName(String localPath, String methodName, Method method)
  {
    String className = "";
    int count = 0;
    Method[] ms = clazz.getMethods();
    for (Method m : ms) {
      if (methodName.equals(m.getName())) {
        count++;
      }
    }
    if (count > 1) {
      String temp = "";
      Class[] parameterTypes = method.getParameterTypes();

      if (parameterTypes.length > 1) {
        temp = getPName(parameterTypes[0].getName()) + getPName(parameterTypes[(parameterTypes.length - 1)].getName()) + parameterTypes.length;
      }
      else
      {
        temp = getPName(parameterTypes[0].getName()) + parameterTypes.length;
      }
      className = className + temp;
    }

    return className;
  }

  private static String getPName(String parameterType) {
    if (("int".equals(parameterType)) || ("java.lang.Integer".equals(parameterType)))
    {
      return "Int";
    }
    if (("double".equals(parameterType)) || ("java.lang.Double".equals(parameterType)))
    {
      return "Dou";
    }
    if (("long".equals(parameterType)) || ("java.lang.Long".equals(parameterType)))
    {
      return "Long";
    }
    if (("boolean".equals(parameterType)) || ("java.lang.Boolean".equals(parameterType)))
    {
      return "Boo";
    }

    if ("java.lang.String".equals(parameterType)) {
      return "Str";
    }
    if ("java.util.Map".equals(parameterType)) {
      return "Map";
    }
    if ("java.util.List".equals(parameterType)) {
      return "List";
    }
    if ("java.util.Set".equals(parameterType)) {
      return "Set";
    }
    if ("java.lang.Object".equals(parameterType)) {
      return "Obj";
    }
    if ("java.io.File".equals(parameterType)) {
      return "File";
    }

    if ("[B".equals(parameterType)) {
      return "Byt";
    }

    if ("[[B".equals(parameterType)) {
      return "Byts";
    }

    if ("[Ljava.lang.String;".equals(parameterType)) {
      return "Strs";
    }
    return "";
  }

  private static void appendEndMoth(BufferedWriter bw, String filedName, String methodName) throws IOException
  {
    String params = "";
    if ((paramNames != null) && (paramNames.size() > 0)) {
      for (int i = 0; i < paramNames.size(); i++) {
        if (i == 0)
          params = params + (String)paramNames.get(i);
        else
          params = params + "," + (String)paramNames.get(i);
      }
    }
    bw.write(filedName + "." + methodName + "(" + params + ");\n");
    bw.write("}\n");
    bw.write("\n");
  }

  private static void appendExpMoth(BufferedWriter bw, String methodName, int t, int i, Class<?>[] parameterTypes)
    throws IOException
  {
    switch (t) {
    case 2:
      bw.write("/*** null测试*/\n");
      bw.write("@Test(expected = " + exception + ".class)\n");
      String expName1 = getTestMoth(methodName, i) + "Null";
      bw.write("public void " + expName1 + "()  {\n");
      for (int k = 0; k < parameterTypes.length; k++) {
        Class clas = parameterTypes[k];
        String parameterType = clas.getName();
        appendParam(bw, parameterType, k == i ? t : T1, k);
      }
      break;
    case 3:
      bw.write("/*** 空对象*/\n");
      bw.write("@Test(expected = " + exception + ".class)\n");
      String expName2 = getTestMoth(methodName, i) + "Blank";
      bw.write("public void " + expName2 + "()  {\n");
      for (int k = 0; k < parameterTypes.length; k++) {
        Class clas = parameterTypes[k];
        String parameterType = clas.getName();
        appendParam(bw, parameterType, k == i ? t : T1, k);
      }
      break;
    case 4:
      bw.write("/*** 超长测试*/\n");
      bw.write("@Test(expected = " + exception + ".class)\n");
      String expName3 = getTestMoth(methodName, i) + "Huge";
      bw.write("public void " + expName3 + "()  {\n");
      for (int k = 0; k < parameterTypes.length; k++) {
        Class clas = parameterTypes[k];
        String parameterType = clas.getName();
        appendParam(bw, parameterType, k == i ? t : T1, k);
      }
      break;
    }
  }

  private static String getTestMoth(String methodName, int i)
  {
    switch (i + 1) {
    case 1:
      return methodName += "First";
    case 2:
      return methodName += "Second";
    case 3:
      return methodName += "Third";
    case 4:
      return methodName += "Fourth";
    case 5:
      return methodName += "Fifth";
    case 6:
      return methodName += "Sixth";
    case 7:
      return methodName += "Seventh";
    case 8:
      return methodName += "Eighth";
    }

    return methodName + i;
  }

  private static void appendNorMalMoth(BufferedWriter bw, String methodName, int t, Class<?>[] parameterTypes) throws IOException
  {
    switch (t) {
    case 1:
      bw.write("/*** 正常情况测试*/\n");
      bw.write("@Test\n");
      bw.write("public void " + methodName + "()  {\n");
      for (int k = 0; k < parameterTypes.length; k++) {
        Class clas = parameterTypes[k];
        String parameterType = clas.getName();
        appendParam(bw, parameterType, T1, k);
      }
      break;
    }
  }

  private static void appendParam(BufferedWriter bw, String parameterType, int type, int index)
    throws IOException
  {
    if ("int".equals(parameterType)) {
      switch (type) {
      case 1:
        bw.write("int int" + index + " = 100;\n");
        paramNames.add("int" + index);
        break;
      case 2:
        bw.write("int int" + index + " = 0;\n");
        paramNames.add("int" + index);
        break;
      case 3:
        bw.write("int int" + index + " = 0;\n");
        paramNames.add("int" + index);
        break;
      }

    }

    if ("java.lang.Integer".equals(parameterType)) {
      switch (type) {
      case 1:
        bw.write("Integer int" + index + " = 100;\n");
        paramNames.add("int" + index);
        break;
      case 2:
        bw.write("Integer int" + index + " = null;\n");
        paramNames.add("int" + index);
        break;
      case 3:
        bw.write("Integer int" + index + " = new Integer(0);\n");
        paramNames.add("int" + index);
        break;
      case 4:
        bw.write("Integer int" + index + " = 100...;\n");
        paramNames.add("int" + index);
        break;
      }

    }

    if ("double".equals(parameterType)) {
      switch (type) {
      case 1:
        bw.write("double double" + index + " = 100.1;\n");
        paramNames.add("double" + index);
        break;
      case 2:
        bw.write("double double" + index + " = 0.0;\n");
        paramNames.add("double" + index);
        break;
      case 3:
        bw.write("double double" + index + " = 0.0;\n");
        paramNames.add("double" + index);
        break;
      case 4:
        bw.write("double double" + index + " = 100...;\n");
        paramNames.add("double" + index);
        break;
      }

    }

    if ("java.lang.Double".equals(parameterType)) {
      switch (type) {
      case 1:
        bw.write("Double double" + index + " = 100.1;\n");
        paramNames.add("double" + index);
        break;
      case 2:
        bw.write("Double double" + index + " = null;\n");
        paramNames.add("double" + index);
        break;
      case 3:
        bw.write("Double double" + index + " = new Double(0);\n");
        paramNames.add("double" + index);
        break;
      case 4:
        bw.write("Double double" + index + " = 100...;\n");
        paramNames.add("double" + index);
        break;
      }

    }

    if ("long".equals(parameterType)) {
      switch (type) {
      case 1:
        bw.write("long long" + index + " = 10000000l;\n");
        paramNames.add("long" + index);
        break;
      case 2:
        bw.write("long long" + index + " = 0l;\n");
        paramNames.add("long" + index);
        break;
      case 3:
        bw.write("long long" + index + " = 0l;\n");
        paramNames.add("long" + index);
        break;
      }

    }

    if ("java.lang.Long".equals(parameterType)) {
      switch (type) {
      case 1:
        bw.write("Long long" + index + " = 10000000l;\n");
        paramNames.add("long" + index);
        break;
      case 2:
        bw.write("Long long" + index + " = null;\n");
        paramNames.add("long" + index);
        break;
      case 3:
        bw.write("Long long" + index + " = new Long(0l);\n");
        paramNames.add("long" + index);
        break;
      case 4:
        bw.write("Long long" + index + " = 100...;\n");
        paramNames.add("long" + index);
        break;
      }

    }

    if (("boolean".equals(parameterType)) || ("java.lang.Boolean".equals(parameterType)))
    {
      switch (type) {
      case 1:
        bw.write("Boolean boolean" + index + " = true;\n");
        paramNames.add("boolean" + index);
        break;
      case 2:
        bw.write("Boolean boolean" + index + " = null;\n");
        paramNames.add("boolean" + index);
        break;
      case 3:
        bw.write("Boolean boolean" + index + " = new Boolean();\n");
        paramNames.add("boolean" + index);
        break;
      case 4:
        bw.write("Boolean boolean" + index + " = false;\n");
        paramNames.add("boolean" + index);
        break;
      }

    }

    if ("java.lang.String".equals(parameterType)) {
      switch (type) {
      case 1:
        bw.write("String str" + index + " = \"thenormaltest-str" + getRandom() + "\";\n");
        paramNames.add("str" + index);
        break;
      case 2:
        bw.write("String str" + index + " = null;\n");
        paramNames.add("str" + index);
        break;
      case 3:
        bw.write("String str" + index + " = \"\";\n");
        paramNames.add("str" + index);
        break;
      case 4:
        bw.write("String str" + index + " = \"theHuge...\";\n");
        paramNames.add("str" + index);
        break;
      }

    }

    if ("java.util.Map".equals(parameterType)) {
      switch (type) {
      case 1:
        bw.write("java.util.Map map" + index + " = new java.util.HashMap();\n");

        bw.write("//TODO \n");
        paramNames.add("map" + index);
        break;
      case 2:
        bw.write("java.util.Map map" + index + " = null;\n");
        paramNames.add("map" + index);
        break;
      case 3:
        bw.write("java.util.Map map" + index + " = new java.util.HashMap();\n");

        paramNames.add("map" + index);
        break;
      case 4:
        bw.write("java.util.Map map" + index + " = new java.util.HashMap();\n");

        bw.write("map" + index + ".put(\"errokey\",\"theHuge...\");\n");
        paramNames.add("map" + index);
        break;
      }

    }

    if ("java.util.List".equals(parameterType)) {
      switch (type) {
      case 1:
        bw.write("java.util.List list" + index + " = new java.util.ArrayList();\n");

        bw.write("//TODO \n");
        paramNames.add("list" + index);
        break;
      case 2:
        bw.write("java.util.List list" + index + " = null;\n");
        paramNames.add("list" + index);
        break;
      case 3:
        bw.write("java.util.List list" + index + " = new java.util.ArrayList();\n");

        paramNames.add("list" + index);
        break;
      case 4:
        bw.write("java.util.List list" + index + " = new java.util.ArrayList();\n");

        bw.write("list" + index + ".add(\"theHugetest...\");\n");
        paramNames.add("list" + index);
        break;
      }

    }

    if ("java.util.Set".equals(parameterType)) {
      switch (type) {
      case 1:
        bw.write("java.util.Set set" + index + " = new java.util.HashSet();\n");

        bw.write("//TODO \n");
        paramNames.add("set" + index);
        break;
      case 2:
        bw.write("java.util.Set set" + index + " = null;\n");
        paramNames.add("set" + index);
        break;
      case 3:
        bw.write("java.util.Set set" + index + " = new java.util.HashSet();\n");

        paramNames.add("set" + index);
        break;
      case 4:
        bw.write("java.util.Set set" + index + " = new java.util.HashSet();\n");

        bw.write("set" + index + ".add(\"theHugetest...\");\n");
        paramNames.add("set" + index);
        break;
      }

    }

    if ("java.lang.Object".equals(parameterType)) {
      switch (type) {
      case 1:
        bw.write("Object obj" + index + " = new Object();\n");
        paramNames.add("obj" + index);
        break;
      case 2:
        bw.write("Object obj" + index + " = null;\n");
        paramNames.add("obj" + index);
        break;
      case 3:
        bw.write("Object obj" + index + " = new Object();\n");
        paramNames.add("obj" + index);
        break;
      case 4:
        break;
      }

    }

    if ("java.io.File".equals(parameterType)) {
      switch (type) {
      case 1:
        bw.write("// TODO;\n");
        bw.write("java.io.File file" + index + " = new java.io.File(\"\");\n");
        paramNames.add("file" + index);
        break;
      case 2:
        bw.write("java.io.File file" + index + " = null;\n");
        paramNames.add("file" + index);
        break;
      case 3:
        bw.write("// TODO;\n");
        bw.write("java.io.File file" + index + " = new java.io.File(\"\");\n");
        paramNames.add("file" + index);
        break;
      case 4:
        break;
      }

    }

    if ("[B".equals(parameterType)) {
      switch (type) {
      case 1:
        bw.write("byte[] byte" + index + " = \"thenormaltest-byte" + getRandom() + "\".getBytes();\n");

        paramNames.add("byte" + index);
        break;
      case 2:
        bw.write("byte[] byte" + index + " = null;\n");
        paramNames.add("byte" + index);
        break;
      case 3:
        bw.write("byte[] byte" + index + " = new byte[0];\n");
        paramNames.add("byte" + index);
        break;
      case 4:
        bw.write("byte[] byte" + index + " = \"theHuge...\";\n");
        paramNames.add("byte" + index);
        break;
      }

    }

    if ("[[B".equals(parameterType)) {
      switch (type) {
      case 1:
        bw.write("byte[] abyte" + index + " = \"thenormaltest-bytes1" + getRandom() + "\".getBytes();\n");

        paramNames.add("abyte" + index);
        bw.write("byte[] bbyte" + index + " = \"thenormaltest-bytes2" + getRandom() + "\".getBytes();\n");

        paramNames.add("bbyte" + index);
        break;
      case 2:
        bw.write("byte[] abyte" + index + " = null;\n");
        paramNames.add("abyte" + index);
        bw.write("byte[] bbyte" + index + " = null;\n");
        paramNames.add("bbyte" + index);
        break;
      case 3:
        bw.write("byte[] abyte" + index + " = new byte[0];\n");
        paramNames.add("abyte" + index);
        bw.write("byte[] bbyte" + index + " = new byte[0];\n");
        paramNames.add("bbyte" + index);
        break;
      case 4:
        bw.write("byte[] abyte" + index + " = \"theHuge...\";\n");
        paramNames.add("abyte" + index);
        bw.write("byte[] bbyte" + index + " = \"theHuge...\";\n");
        paramNames.add("bbyte" + index);
        break;
      }

    }

    if ("[Ljava.lang.String;".equals(parameterType))
      switch (type) {
      case 1:
        bw.write("String astr" + index + " = \"thenormaltest-str1" + getRandom() + "\";\n");
        paramNames.add("astr" + index);
        bw.write("String bstr" + index + " = \"thenormaltest-str2" + getRandom() + "\";\n");
        paramNames.add("bstr" + index);
        break;
      case 2:
        bw.write("String astr" + index + " = null;\n");
        paramNames.add("astr" + index);
        bw.write("String bstr" + index + " = null;\n");
        paramNames.add("bstr" + index);
        break;
      case 3:
        bw.write("String astr" + index + " = \"\";\n");
        paramNames.add("astr" + index);
        bw.write("String bstr" + index + " = \"\";\n");
        paramNames.add("bstr" + index);
        break;
      case 4:
        bw.write("String astr" + index + " = \"theHuge...\";\n");
        paramNames.add("astr" + index);
        bw.write("String bstr" + index + " = \"theHuge...\";\n");
        paramNames.add("bstr" + index);
        break;
      }
  }

  private static String getRandom()
  {
    Random rand = new Random();
    int i = rand.nextInt(90000) + 10000;
    String requirepass = i + "";
    return requirepass;
  }

  private static void genPkgsAndBaseClass(String localPath, String className, String newPkg)
  {
    String path = localPath + File.separator + "base";
    String name = className.startsWith("I") ? className.substring(1) : className;

    String filedName = className.substring(0, 1).toLowerCase() + className.substring(1);

    String baseClassFile = path + File.separator + name + ".java";
    File file = new File(baseClassFile);
    System.out.println("create base" + baseClassFile);

    if (!file.getParentFile().exists())
      file.getParentFile().mkdirs();
    try
    {
      file.delete();
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try
    {
      FileWriter fw = new FileWriter(file, true);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write("package " + newPkg + ".base;\n");
      bw.write("import " + testClass + ";\n");
      bw.write("import com.ai.paas.ipaas.uac.vo.AuthDescriptor;\n");
      bw.write("public class " + name + "{\n");
      bw.write("private final String AUTH_ADDR = \"http://10.1.228.198:14821/iPaas-Auth/service/check\";\n");
      bw.write("private AuthDescriptor ad = null;\n");
      bw.write("private " + className + " " + filedName + "  = null;\n");
      bw.write("public " + className + " getClient() throws Exception {\n");

      bw.write("//TODO\n");
      bw.write("return " + filedName + ";\n");
      bw.write("}\n");
      bw.write("}\n");
      bw.flush();
      bw.close();
      fw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String getLocalPath(String newPkg)
  {
    String userDir = System.getProperty("user.dir");
    System.out.println("userDir:" + userDir);
    userDir = userDir + File.separator + "src" + File.separator + "test" + File.separator + "java";

    String path = userDir;
    String[] tempPath = newPkg.split("\\.");
    for (String temp : tempPath) {
      path = path + File.separator + temp;
    }
    return path;
  }

  private static String getNewPkg(String pkgName, String className)
  {
    if ((pkgName != null) && (pkgName.contains("."))) {
      String[] strs = pkgName.split("\\.");
      if (strs.length > 4) {
        String newPkg = "test." + strs[0] + "." + strs[1] + "." + strs[2] + "." + strs[3] + "." + strs[4] + ".";

        if (className.startsWith("I"))
          newPkg = newPkg + className.substring(1).toLowerCase();
        else
          newPkg = newPkg + className.toLowerCase();
        return newPkg;
      }
    }

    return "test." + pkgName;
  }

  private static String get(String pkgName)
  {
    if ((pkgName != null) && (pkgName.contains(".")))
      return pkgName.substring(pkgName.lastIndexOf(".") + 1);
    return pkgName;
  }
}