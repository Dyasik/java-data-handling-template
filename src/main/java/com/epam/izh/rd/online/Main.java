package com.epam.izh.rd.online;

import com.epam.izh.rd.online.repository.SimpleFileRepository;

public class Main {

    public static void main(String[] args) {
        SimpleFileRepository fileRepository = new SimpleFileRepository();
        fileRepository.countFilesInDirectory("testDirCountFiles");
    }

}
