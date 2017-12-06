package br.unisinos.jgraphicscene.utilities.io.parsers;

import br.unisinos.jgraphicscene.graphics.Material;
import br.unisinos.jgraphicscene.graphics.Texture;
import br.unisinos.jgraphicscene.units.Color;

import java.util.ArrayList;
import java.util.List;

import static br.unisinos.jgraphicscene.utilities.io.parsers.RegularExpressions.*;

public class MaterialParser extends Parser<List<Material>> {
    private List<Material> materials;
    private Material currentMaterial;

    private MaterialParser(String filename) {
        super(filename);
        this.materials = new ArrayList<>();
        this.configure();
    }

    private void configure() {
        this
            .handle(NEWMTL, this::createMaterial)
            .handle(D, this::setOpacityD)
            .handle(TR, this::setOpacityTr)
            .handle(NS, this::setSpecularExponent)
            .handle(ILLUM, this::setIlluminationModel)
            .handle(KA, this::setAmbientColor)
            .handle(KD, this::setDiffuseColor)
            .handle(KS, this::setSpecularColor)
            .handle(MAP_KA, this::setAmbientMap)
            .handle(MAP_KD, this::setDiffuseMap)
            .handle(MAP_KS, this::setSpecularMap)
            .handle(SHININESS, this::setShininess)
            .ignore(COMMENT)
        ;
    }

    private void setShininess(String[] strings) {
        float s = Integer.parseInt(strings[0]);
        this.currentMaterial.setShininess(s);
    }

    @Override
    protected List<Material> retrieve() {
        return this.materials;
    }

    private void createMaterial(String[] args) {
        String name = args[0];
        this.currentMaterial = new Material(name);
        this.materials.add(this.currentMaterial);
    }

    private void setOpacityD(String[] args) {
        float d = Float.parseFloat(args[0]);
        this.setOpacity(d);
    }

    private void setOpacityTr(String[] args) {
        float tr = 1 - Float.parseFloat(args[0]);
        this.setOpacity(tr);
    }

    private void setOpacity(float opacity) {
        this.currentMaterial.setOpacity(opacity);
    }

    private void setSpecularExponent(String[] args) {
        float ns = Float.parseFloat(args[0]);
        this.currentMaterial.setSpecularExponent(ns);
    }

    private void setIlluminationModel(String[] args) {
        int illum = Integer.parseInt(args[0]);
        this.currentMaterial.setIlluminationModel(illum);
    }

    private void setAmbientColor(String[] args) {
        float[] rgb = this.toFloatArray(args);
        this.currentMaterial.setAmbientColor(new Color(rgb[0], rgb[1], rgb[2]));
    }

    private void setDiffuseColor(String[] args) {
        float[] rgb = this.toFloatArray(args);
        this.currentMaterial.setDiffuseColor(new Color(rgb[0], rgb[1], rgb[2]));
    }

    private void setSpecularColor(String[] args) {
        float[] rgb = this.toFloatArray(args);
        this.currentMaterial.setSpecularColor(new Color(rgb[0], rgb[1], rgb[2]));
    }

    private void setAmbientMap(String[] args) {
        String ambientMap = args[0];
        String filename = this.resolvePath(ambientMap);
        this.currentMaterial.setAmbientMap(new Texture(filename));
    }

    private void setDiffuseMap(String[] args) {
        String diffuseMap = args[0];
        String filename = this.resolvePath(diffuseMap);
        this.currentMaterial.setDiffuseMap(new Texture(filename));
    }

    private void setSpecularMap(String[] args) {
        String specularMap = args[0];
        String filename = this.resolvePath(specularMap);
        this.currentMaterial.setSpecularMap(new Texture(filename));
    }

    public static List<Material> parse(String filename) {
        return new MaterialParser(filename).parse();
    }
}
