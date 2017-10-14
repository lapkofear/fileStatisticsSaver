package com.company.service;

import com.company.model.FileStatistic;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class FileStatisticsCalculatorTest {

	private static final String FILE_NAME = "fileName";
	private static final List<String> LINES = Arrays.asList("This is the first line", " ", "shortest word line a", "This is the last one ");

	@Test
	public void calculateStatisticsTest() {
		FileStatistic actual = FileStatisticsCalculator.calculateFileStatistics(FILE_NAME, LINES);
		Assert.assertThat(actual.getLineStatistics().get(0).getLineNumber(), Is.is(0));
		Assert.assertThat(actual.getLineStatistics().get(0).getLongestWord(), Is.is("first"));

		Assert.assertThat(actual.getLineStatistics().get(1).getAverageWordLength(), Is.is(0.0));

		Assert.assertThat(actual.getLineStatistics().get(2).getShortestWord(), Is.is("a"));

		Assert.assertThat(actual.getLineStatistics().get(3).getLongestWord(), Is.is("This"));
	}
}