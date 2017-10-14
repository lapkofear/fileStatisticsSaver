package com.company.service;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItems;

public class FileScannerTest {
	private static final String TEST_FILES_ROOT = "src/test/testFiles/";

	@Test
	public void singleSpecificFileTest() {
		Map<String, List<String>> result = FileScanner.readFilesFromFolders(TEST_FILES_ROOT + "file1.txt");
		Assert.assertThat(result.size(), Is.is(1));
		Assert.assertThat(result.values().iterator().next(), Is.is(Arrays.asList("this is file1", "peace", "", "end of the file")));
	}

	@Test
	public void recursiveLoadingTest() {
		Map<String, List<String>> result = FileScanner.readFilesFromFolders(TEST_FILES_ROOT);
		Assert.assertThat(result.size(), Is.is(6));
		Assert.assertThat(result.keySet(), hasItems(
				containsString("file1.txt"),
				containsString("file2.txt"),
				containsString("file2_1.txt"),
				containsString("file2_1_1.txt"),
				containsString("file2_1_2.txt"),
				containsString("file1_1.txt")));
	}

	@Test
	public void currentDirectoryLoadingTest() {
		Map<String, List<String>> result = FileScanner.readFilesFromFolders();
		Assert.assertTrue(result.size() >= 6);      //Some other txt files could be loaded (surefire-reports for example)
		Assert.assertThat(result.keySet(), hasItems(
				containsString("file1.txt"),
				containsString("file2.txt"),
				containsString("file2_1.txt"),
				containsString("file2_1_1.txt"),
				containsString("file2_1_2.txt"),
				containsString("file1_1.txt")));
	}
}