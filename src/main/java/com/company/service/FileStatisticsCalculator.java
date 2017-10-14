package com.company.service;

import com.company.model.FileStatistic;
import com.company.model.LineStatistic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileStatisticsCalculator {

	public static List<FileStatistic> calculateFileStatistics(Map<String, List<String>> files) {
		List<FileStatistic> result = new ArrayList<>(files.size());
		for (Map.Entry<String, List<String>> fileEntry : files.entrySet()) {
			result.add(calculateFileStatistics(fileEntry.getKey(), fileEntry.getValue()));
		}
		return result;
	}

	static FileStatistic calculateFileStatistics(String fileName, List<String> lines) {
		FileStatistic result = new FileStatistic(fileName);
		result.setLineStatistics(calculateLineStatistics(lines));
		return result;
	}

	static List<LineStatistic> calculateLineStatistics(List<String> lines) {
		ArrayList<LineStatistic> result = new ArrayList<>(lines.size());

		for (int i = 0; i < lines.size(); i++) {
			result.add(new LineStatistic(i, lines.get(i)));
		}
		return result;
	}
}