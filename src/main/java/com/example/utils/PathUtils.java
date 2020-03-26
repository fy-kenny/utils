package com.example.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Java maven project path utilities
 *
 * @author Kenny Fang
 */
public interface PathUtils {

    String DIR_SRC = "src";
    String DIR_MAIN = "main";
    String DIR_TEST = "test";
    String DIR_JAVA = "java";
    String DIR_RESOURCES = "resources";
    String DIR_TARGET_DEFAULT = "target";

    /**
     * Return full path string of current class
     */
    static String current() {
        return current(PathUtils.class);
    }

    /**
     * Return full path string of the specified clazz
     */
    static String current(Class clazz) {

        String className = StringUtils.appendIfMissing(clazz.getSimpleName(), ".class");


        String path = EMPTY;
        try {
            path = Paths.get(PathUtils.class.getResource(className).toURI())
                    .toAbsolutePath().getParent().toString();
        } catch (URISyntaxException ignored) {
        }

        return path;
    }

    /**
     * Return full path string of current project
     */
    static String projectDir() {
        return Paths.get("")
                .toAbsolutePath().toString();
    }

    /**
     * Return full path string of current project parent
     */
    static String projectParentDir() {
        return Paths.get("")
                .toAbsolutePath().getParent().toAbsolutePath().toString();
    }

    /**
     * Return root path string of current project
     */
    static String rootDir() {
        return Paths.get("").toAbsolutePath().getRoot()
                .toAbsolutePath().toString();
    }

    /**
     * Return src directory path string of current project
     */
    static String src() {
        return projectDir() + File.separator + DIR_SRC;
    }

    /**
     * Return main directory path string of current project
     */
    static String main() {
        return src() + File.separator + DIR_MAIN;
    }

    /**
     * Return test directory path string of current project
     */
    static String test() {
        return src() + File.separator + DIR_TEST;
    }

    /**
     * Return main/java directory path string of current project
     */
    static String java() {
        return main() + File.separator + DIR_JAVA;
    }

    /**
     * Return main/resources directory path string of current project
     */
    static String resources() {
        return main() + File.separator + DIR_RESOURCES;
    }

    /**
     * Return default target directory path string of current project
     */
    static String targetDefault() {
        return projectDir() + File.separator + DIR_TARGET_DEFAULT;
    }
}
