package com.company.model;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class LineStatisticTest {
	private static final int LINE_LENGTH = 0;
	private static final String TEST_LINE = "This is test line! ";
	private LineStatistic statistic = new LineStatistic(LINE_LENGTH, TEST_LINE);

	@Test
	public void lineLengthTest() {
		Assert.assertThat(statistic.getLineLength(), Is.is(19));
	}

	@Test
	public void wordSplittingTest() {
		Assert.assertThat(statistic.getWords(), Is.is(Arrays.asList("This", "is", "test", "line!")));
	}

	@Test
	public void longestWordTest() {
		Assert.assertThat(statistic.getLongestWord(), Is.is("line!"));
	}

	@Test
	public void shortestWordTest() {
		Assert.assertThat(statistic.getShortestWord(), Is.is("is"));
	}

	@Test
	public void averageWordLength() {
		Assert.assertThat(statistic.getAverageWordLength(), Is.is(3.75));
	}
}