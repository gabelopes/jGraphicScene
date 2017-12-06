package br.unisinos.jgraphicscene.utilities.io.dto;

import br.unisinos.jgraphicscene.graphics.Material;
import br.unisinos.jgraphicscene.graphics.Texture;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.utilities.Classes;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MaterialDTO implements DTO<Material> {
    private Float[] ambientColor;
    private Float[] diffuseColor;
    private Float[] specularColor;

    private String ambientMap;
    private String diffuseMap;
    private String specularMap;

    private float opacity;
    private float shininess;
    private float specularExponent;

    private int illuminationModel;

    private String name;
    private String path;

    public Path getPath() {
        return Paths.get(path);
    }

    public Material transferForConfiguration(String name) {
        this.name = name;

        return transfer();
    }

    private String resolvePath(String filename) {
        if (filename == null) {
            return null;
        }

        if (path == null) {
            return name;
        }

        return getPath().resolve(filename).toString();
    }

    @Override
    public Material transfer() {
        Material material = new Material(name);

        material.setAmbientColor(Classes.instance(Color.class, ambientColor));
        material.setDiffuseColor(Classes.instance(Color.class, diffuseColor));
        material.setSpecularColor(Classes.instance(Color.class, specularColor));

        material.setAmbientMap(resolvePath(ambientMap));
        material.setDiffuseMap(resolvePath(diffuseMap));
        material.setSpecularMap(resolvePath(specularMap));

        material.setOpacity(opacity);
        material.setShininess(shininess);
        material.setSpecularExponent(specularExponent);

        material.setIlluminationModel(illuminationModel);

        return material;
    }
}
