package br.unisinos.jgraphicscene.graphics;

import br.unisinos.jgraphicscene.units.Color;

import java.util.Arrays;
import java.util.List;

public class Material {
    private String name;

    private Color ambientColor;
    private Color diffuseColor;
    private Color specularColor;

    private Texture ambientMap;
    private Texture diffuseMap;
    private Texture specularMap;

    private float opacity;
    private float shininess;
    private float specularExponent;

    private int illuminationModel;

    public Material(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(Color ambientColor) {
        this.ambientColor = ambientColor;
    }

    public Color getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Color diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public Color getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(Color specularColor) {
        this.specularColor = specularColor;
    }

    public Texture getAmbientMap() {
        return ambientMap;
    }

    public void setAmbientMap(Texture ambientMap) {
        this.ambientMap = ambientMap;
    }

    public Texture getDiffuseMap() {
        return diffuseMap;
    }

    public void setDiffuseMap(Texture diffuseMap) {
        this.diffuseMap = diffuseMap;
    }

    public Texture getSpecularMap() {
        return specularMap;
    }

    public void setSpecularMap(Texture specularMap) {
        this.specularMap = specularMap;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public float getSpecularExponent() {
        return specularExponent;
    }

    public void setSpecularExponent(float specularExponent) {
        this.specularExponent = specularExponent;
    }

    public int getIlluminationModel() {
        return illuminationModel;
    }

    public void setIlluminationModel(int illuminationModel) {
        this.illuminationModel = illuminationModel;
    }

    public List<Texture> getTextures() {
        return Arrays.asList(ambientMap, diffuseMap, specularMap);
    }
}
