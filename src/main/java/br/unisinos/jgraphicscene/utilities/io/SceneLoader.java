package br.unisinos.jgraphicscene.utilities.io;

import br.unisinos.jgraphicscene.graphics.Scene;
import br.unisinos.jgraphicscene.utilities.io.dto.SceneDTO;
import com.jsoniter.JsonIterator;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SceneLoader {
    public static List<Scene> load(String file) {
        try {
            String json = FileUtils.readFileToString(new File(file), Charset.defaultCharset());
            SceneDTO[] sceneDTO = JsonIterator.deserialize(json, SceneDTO[].class);

            return Arrays.stream(sceneDTO).map(SceneDTO::transfer).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }
}
