package br.unisinos.jgraphicscene.utilities.io.parsers;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Parser<T> {
    private Matcher matcher;
    private Map<Pattern, Consumer<String[]>> handlers;
    private List<Pattern> ignores;
    private String filename;

    protected Parser(String filename) {
        this.filename = filename;
        this.handlers = new HashMap<>();
        this.ignores = new ArrayList<>();
    }

    protected T parse() {
        try {
            List<String> lines = FileUtils.readLines(new File(this.filename), Charset.defaultCharset());

            for (String line : lines) {
                this.process(line);
            }

            return this.retrieve();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    protected abstract T retrieve();

    protected Parser<T> handle(Pattern pattern, Consumer<String[]> handler) {
        this.handlers.put(pattern, handler);
        return this;
    }

    protected Parser<T> ignore(Pattern pattern) {
        this.ignores.add(pattern);
        return this;
    }

    private boolean matches(Pattern pattern, String line) {
        this.matcher = pattern.matcher(line);
        return this.matcher.matches();
    }

    private void process(String line) {
        if (this.ignores.stream().anyMatch(pattern -> pattern.matcher(line).matches())) {
            return;
        }

        for (Pattern pattern : this.handlers.keySet()) {
            if (this.matches(pattern, line)) {
                this.handlers.get(pattern).accept(this.getGroups());
            }
        }
    }

    private String[] getGroups() {
        String[] groups = new String[matcher.groupCount()];

        for (int i = 0; i < matcher.groupCount(); i++) {
            groups[i] = matcher.group(i + 1);
        }

        return groups;
    }

    protected float[] toFloatArray(String[] elements) {
        float[] array = new float[elements.length];

        for (int i = 0; i < elements.length; i++) {
            try {
                array[i] = Float.parseFloat(elements[i]);
            } catch (Exception e) {
                array[i] = 0;
            }
        }

        return array;
    }

    protected int[] toIntArray(String[] elements) {
        int[] array = new int[elements.length];

        for (int i = 0; i < elements.length; i++) {
            try {
                array[i] = Integer.parseInt(elements[i]);
            } catch (Exception e) {
                array[i] = 0;
            }
        }

        return array;
    }

    public String getFilename() {
        return filename;
    }
}
