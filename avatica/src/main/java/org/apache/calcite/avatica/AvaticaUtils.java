/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.calcite.avatica;

import java.util.AbstractList;
import java.util.List;

/** Avatica utilities. */
public class AvaticaUtils {
  private AvaticaUtils() {}

  /**
   * Does nothing with its argument. Call this method when you have a value
   * you are not interested in, but you don't want the compiler to warn that
   * you are not using it.
   */
  public static void discard(Object o) {
    if (false) {
      discard(o);
    }
  }

  /**
   * Adapts a primitive array into a {@link List}. For example,
   * {@code asList(new double[2])} returns a {@code List&lt;Double&gt;}.
   */
  public static List<?> primitiveList(final Object array) {
    // REVIEW: A per-type list might be more efficient. (Or might not.)
    return new AbstractList() {
      public Object get(int index) {
        return java.lang.reflect.Array.get(array, index);
      }

      public int size() {
        return java.lang.reflect.Array.getLength(array);
      }
    };
  }

  /**
   * Converts a camelCase name into an upper-case underscore-separated name.
   * For example, {@code camelToUpper("myJdbcDriver")} returns
   * "MY_JDBC_DRIVER".
   */
  public static String camelToUpper(String name) {
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < name.length(); i++) {
      char c = name.charAt(i);
      if (Character.isUpperCase(c)) {
        buf.append('_');
      } else {
        c = Character.toUpperCase(c);
      }
      buf.append(c);
    }
    return buf.toString();
  }

  /**
   * Converts an underscore-separated name into a camelCase name.
   * For example, {@code uncamel("MY_JDBC_DRIVER")} returns "myJdbcDriver".
   */
  public static String toCamelCase(String name) {
    StringBuilder buf = new StringBuilder();
    int nextUpper = -1;
    for (int i = 0; i < name.length(); i++) {
      char c = name.charAt(i);
      if (c == '_') {
        nextUpper = i + 1;
        continue;
      }
      if (nextUpper == i) {
        c = Character.toUpperCase(c);
      } else {
        c = Character.toLowerCase(c);
      }
      buf.append(c);
    }
    return buf.toString();
  }
}

// End AvaticaUtils.java
