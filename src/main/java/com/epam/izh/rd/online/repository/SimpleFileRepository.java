package com.epam.izh.rd.online.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class SimpleFileRepository implements FileRepository {

    private ClassLoader classLoader = this.getClass().getClassLoader();

    private long countFilesOrDirsInDirectory(String path, boolean countFiles) {
        if (path == null) {
            throw new IllegalArgumentException("Argument should be not null.");
        }

        File dir = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());

        if (!dir.exists() || !dir.isDirectory()) {
            throw new IllegalArgumentException("Given path should point to an existing directory.");
        }

        File[] files = dir.listFiles();

        long result = countFiles ? 0 : 1;

        if (files == null) {
            return result;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                result += countFilesOrDirsInDirectory(
                    path + File.separator + file.getName(),
                    countFiles
                );
            } else if (countFiles) {
                result++;
            }
        }

        return result;
    }

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) throws IllegalArgumentException {
        return countFilesOrDirsInDirectory(path, true);
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        return countFilesOrDirsInDirectory(path, false);
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Arguments should be not null.");
        }

        File sourceDir = new File(Objects.requireNonNull(classLoader.getResource(from)).getFile());
        File targetDir = new File(Objects.requireNonNull(classLoader.getResource(to)).getFile());
        boolean isSourceDirValid = sourceDir.exists() || sourceDir.isDirectory();
        boolean isTargetDirValid = targetDir.exists() || targetDir.isDirectory();

        if (!isSourceDirValid || !isTargetDirValid) {
            throw new IllegalArgumentException("Arguments should point to existing directories.");
        }

        File[] filesToMove = sourceDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (filesToMove == null || filesToMove.length == 0) {
            return;
        }

        for (File file : filesToMove) {
            try {
                Files.copy(
                    Paths.get(file.getPath()),
                    Paths.get(targetDir.getPath(), file.getName())
                );
            } catch (IOException e) {
                System.err.println("Failed to copy a file. " + e);
            }
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        if (path == null || name == null) {
            throw new IllegalArgumentException("Arguments should be not null.");
        }

        File resourceDir = new File(Objects.requireNonNull(classLoader.getResource("")).getFile());
        File dir = new File(resourceDir.getPath() + File.separator + path);

        if (!dir.exists()) {
            boolean mkdirSuccess = dir.mkdir();

            if (!mkdirSuccess) {
                System.err.println("Failed to create directory " + path);
                return false;
            }
        } else if (!dir.isDirectory()) {
            System.err.println(path + " already exists and it's not a directory");
            return false;
        }

        String relativePath = dir.getPath() + File.separator + name;

        try {
            Files.createFile(Paths.get(relativePath));
        } catch (IOException e) {
            System.err.printf("Failed to create file as `%s`: %s%n", relativePath, e);
            return false;
        }

        return true;
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) throws IllegalArgumentException {
        if (fileName == null) {
            throw new IllegalArgumentException("Argument should be not null.");
        }

        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());

        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("fileName should point to existing file.");
        }

        FileReader fileReader;

        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't read from file.");
            return null;
        }

        StringBuilder result = new StringBuilder();
        char[] buffer = new char[10];
        int charsRead;
        try {
            while ((charsRead = fileReader.read(buffer)) != -1) {
                result.append(Arrays.copyOf(buffer, charsRead));
            }
        } catch (IOException e) {
            System.err.println("Couldn't read from file.");
            return null;
        }

        return result.toString();
    }
}
