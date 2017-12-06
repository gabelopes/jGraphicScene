package br.unisinos.jgraphicscene.utilities.pools;

import br.unisinos.jgraphicscene.obj.Obj;

import java.util.HashMap;
import java.util.Map;

public class ObjPool {
    private static Map<String, Obj> objs;

    static {
        objs = new HashMap<>();
    }

    public static Obj get(String id) {
        return objs.get(id);
    }

    public static void register(String id, Obj obj) {
        objs.put(id, obj);
    }
}
