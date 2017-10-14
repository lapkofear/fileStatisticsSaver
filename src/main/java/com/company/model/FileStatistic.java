package com.company.model;

import java.util.List;

public class FileStatistic {
	private String fileName;
	private List<LineStatistic> lineStatistics;

	public FileStatistic(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setLineStatistics(List<LineStatistic> lineStatistics) {
		this.lineStatistics = lineStatistics;
	}

	public List<LineStatistic> getLineStatistics() {
		return lineStatistics;
	}
}