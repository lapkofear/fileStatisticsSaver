package com.company.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class LineStatistic {
	private static Pattern compiledPattern = Pattern.compile("\\s+");

	private int lineNumber;
	private String line;
	private List<String> words;
	private String longestWord;
	private String shortestWord;
	private int lineLength;
	private Double averageWordLength;

	public LineStatistic(int lineNumber, String line) {
		if (line == null) {
			throw new IllegalArgumentException("line should NOT be null");
		}
		this.lineNumber = lineNumber;
		this.line = line;
		this.lineLength = line.length();
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getLongestWord() {
		if (longestWord == null) {
			longestWord = getWords().stream().max(Comparator.comparingInt(String::length)).orElse(null);
		}
		return longestWord;
	}

	public String getShortestWord() {
		if (shortestWord == null) {
			shortestWord = getWords().stream().min(Comparator.comparingInt(String::length)).orElse(null);
		}
		return shortestWord;
	}

	public int getLineLength() {
		return lineLength;
	}

	public double getAverageWordLength() {
		if (averageWordLength == null) {
			averageWordLength = getWords().stream().mapToInt(String::length).average().orElse(0.0);
		} return averageWordLength;
	}

	List<String> getWords() {
		if (words == null) {
			words = Arrays.asList(compiledPattern.split(line));
		}
		return words;
	}
}