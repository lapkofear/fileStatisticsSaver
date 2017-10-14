package com.company.dao;

import com.company.model.FileStatistic;
import com.company.model.LineStatistic;
import com.company.utils.DbChecker;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class FileStatisticDaoTest {
	private FileStatistic file1;
	private FileStatistic file2;

	@Before
	public void beforeEachTest() {
		DbChecker.executeQuery("TRUNCATE files CASCADE");
		DbChecker.executeQuery("TRUNCATE line_statistics CASCADE");
		file1 = new FileStatistic("file1");
		file2 = new FileStatistic("file2");
		file1.setLineStatistics(Arrays.asList(new LineStatistic(1, "first1"), new LineStatistic(2, "second1")));
		file2.setLineStatistics(Arrays.asList(new LineStatistic(1, "first2"), new LineStatistic(2, "second2"), new LineStatistic(3, "3"), new
				LineStatistic(4, "fourth")));
	}

	@Test
	public void insertionTest() {
		FileStatisticDao.insertStatistics(Arrays.asList(file1, file2));

		DbChecker.checkDbByQuery("SELECT count(*) = 2 FROM files");
		DbChecker.checkDbByQuery("SELECT count(*) = 6 FROM line_statistics");
	}

	@Test
	public void updatingExistingFilesTest() {
		FileStatisticDao.insertStatistics(Collections.singletonList(file1));

		file1.setLineStatistics(Collections.singletonList(new LineStatistic(1, "first2")));
		FileStatisticDao.insertStatistics(Collections.singletonList(file1));
		DbChecker.checkDbByQuery("SELECT count(*) = 1 FROM files");
		DbChecker.checkDbByQuery("SELECT count(*) = 1 FROM line_statistics");
	}
}