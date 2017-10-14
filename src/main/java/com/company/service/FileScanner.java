package com.company.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileScanner {
	private static final Logger LOG = Logger.getLogger(FileScanner.class.getName());
	private static final String TXT_FORMAT = ".txt";

	/**
	 * Recursive scans all .txt files in specified folders
	 *
	 * @param urls to scan, if empty or null, current directory will be scanned
	 * @return pair of file names and file lines
	 */

	public static Map<String, List<String>> readFilesFromFolders(String... urls) {
		Map<String, List<String>> allFoldersResult = new HashMap<>();
		if (urls == null || urls.length == 0) {
			urls = new String[]{"."};
		}

		for (String url : urls) {
			allFoldersResult.putAll(readSingleFolder(url));
		}
		return allFoldersResult;
	}

	private static Map<String, List<String>> readSingleFolder(String url) {
		Map<String, List<String>> result = new HashMap<>();
		try {
			try (Stream<Path> paths = Files.walk(Paths.get(url))) {
				paths
						.filter(Files::isRegularFile)
						.filter(path -> path.getFileName().toString().endsWith(TXT_FORMAT))
						.forEach(path -> result.put(path.toFile().getAbsolutePath(), readLines(path)));
			}
		} catch (IOException e) {
			LOG.log(Level.WARNING, "File couldn't be found, ignored", e);
		}


		return result;
	}

	private static List<String> readLines(Path path) {
		LOG.info("file found " + path.getFileName());
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
			return bufferedReader.lines().collect(Collectors.toList());
		} catch (IOException e) {
			LOG.log(Level.WARNING, "Error occurred while file reading", e);
			return null;
		}
	}
}
