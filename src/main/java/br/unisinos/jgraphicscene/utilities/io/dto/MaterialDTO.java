package br.unisinos.jgraphicscene.utilities.io.dto;

import br.unisinos.jgraphicscene.graphics.Material;
import br.unisinos.jgraphicscene.graphics.Texture;
import br.unisinos.jgraphicscene.units.Color;
import br.unisinos.jgraphicscene.utilities.Classes;

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

    public Material transferWithName(String name) {
        this.name = name;
        return transfer();
    }

    @Override
    public Material transfer() {
        Material material = new Material(name);

        material.setAmbientColor(Classes.instance(Color.class, ambientColor));
        material.setDiffuseColor(Classes.instance(Color.class, diffuseColor));
        material.setSpecularColor(Classes.instance(Color.class, specularColor));

        material.setAmbientMap(new Texture(ambientMap));
        material.setDiffuseMap(new Texture(diffuseMap));
        material.setSpecularMap(new Texture(specularMap));

        material.setOpacity(opacity);
        material.setShininess(shininess);
        material.setSpecularExponent(specularExponent);

        material.setIlluminationModel(illuminationModel);

        return material;
    }
}
