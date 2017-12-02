package br.unisinos.jgraphicscene.utilities;

import org.apache.commons.lang3.reflect.ConstructorUtils;

public class Classes {
    public static <T> T instance(Class<T> clazz, Object[] parameters) {
        try {
            return ConstructorUtils.invokeConstructor(clazz, parameters);
        } catch (Exception e) {
            return null;
        }
    }
}
