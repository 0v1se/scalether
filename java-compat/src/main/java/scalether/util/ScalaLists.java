package scalether.util;

import scala.collection.immutable.List;
import scalether.java.Lists;

import java.util.Arrays;

public class ScalaLists {
    @SafeVarargs
    public static <T> List<T> asList(T... items) {
        return Lists.toScala(Arrays.asList(items));
    }
}
