package locus.auth.service.util;

import java.util.Objects;

public class Validate {
    public static void paramNullCheck(Object obj1) {
        if(Objects.isNull(obj1))
            throw new IllegalArgumentException("input param 1 is null");
    }

    public static void paramNullCheck(Object obj1, Object obj2) {
        if(Objects.isNull(obj1))
            throw new IllegalArgumentException("input param 1 is null");
        if(Objects.isNull(obj2))
            throw new IllegalArgumentException("input param 2 is null");
    }

}
