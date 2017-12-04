package br.unisinos.jgraphicscene.obj;

import br.unisinos.jgraphicscene.units.Color;

import java.util.List;

public class Material {
    private String name;

    private Color ambientColor;
    private Color diffuseColor;
    private Color specularColor;

    private String ambientMap;
    private String diffuseMap;
    private String specularMap;

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

    public String getAmbientMap() {
        return ambientMap;
    }

    public void setAmbientMap(String ambientMap) {
        this.ambientMap = ambientMap;
    }

    public String getDiffuseMap() {
        return diffuseMap;
    }

    public void setDiffuseMap(String diffuseMap) {
        this.diffuseMap = diffuseMap;
    }

    public String getSpecularMap() {
        return specularMap;
    }

    public void setSpecularMap(String specularMap) {
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

    public String[] getMaps() {
        return new String[] { this.getAmbientMap(), this.getDiffuseMap(), this.getSpecularMap() };
    }
}
