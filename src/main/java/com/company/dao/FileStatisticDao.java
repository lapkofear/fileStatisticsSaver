package com.company.dao;

import com.company.db.connections.ConnectionProvider;
import com.company.model.FileStatistic;
import com.company.model.LineStatistic;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class FileStatisticDao {

	public static void insertStatistics(List<FileStatistic> statistics) {
		ConnectionProvider.runWithConnection(getInsertOperation(statistics));
	}

	private static ConnectionProvider.OperationWithConnection getInsertOperation(List<FileStatistic> statistics) {
		return connection -> {
			PreparedStatement deleteStatement = deletePreviouslyLoadedFilesStatement(connection, statistics);
			PreparedStatement filesStatement = insertFilesStatement(connection);
			PreparedStatement linesStatement = insertLinesStatement(connection);

			for (FileStatistic statistic: statistics) {
				fillFileAndLineStatements(statistic, filesStatement, linesStatement);
			}

			deleteStatement.executeUpdate();
			filesStatement.executeBatch();
			linesStatement.executeBatch();
		};
	}

	private static PreparedStatement deletePreviouslyLoadedFilesStatement(Connection connection, List<FileStatistic> statistics) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM files WHERE file_name in (" +
				 StringUtils.repeat(",?", statistics.size()).substring(1) + ") ");
		for (int i = 0; i < statistics.size(); i++) {
			preparedStatement.setString(i + 1, statistics.get(i).getFileName());
		}
		return preparedStatement;
	}

	private static PreparedStatement insertFilesStatement(Connection connection) throws SQLException {
		return connection.prepareStatement("INSERT INTO public.files VALUES (?, ?)");
	}

	private static PreparedStatement insertLinesStatement(Connection connection) throws SQLException {
		return connection.prepareStatement(
				"INSERT INTO public.line_statistics(file_id, line_number, line_length, longest_word, shortest_word, average_word_length) VALUES (?, ?, ?, ?, ?, ?)");
	}

	private static String getFilenamesPart(Collection<FileStatistic> statistics) {
		return String.join(",", statistics.stream()
				.map(fileStatistic ->fileStatistic.getFileName())
				.collect(Collectors.toList()));
	}

	private static void fillFileAndLineStatements(FileStatistic statistic, PreparedStatement filesStatement, PreparedStatement linesStatement) throws SQLException {
		String fileId = UUID.randomUUID().toString();
		filesStatement.setObject(1, fileId, Types.OTHER);
		filesStatement.setString(2, statistic.getFileName());
		filesStatement.addBatch();
		for (LineStatistic lineStatistic : statistic.getLineStatistics()) {
			linesStatement.setObject(1, fileId, Types.OTHER);
			linesStatement.setInt(2, lineStatistic.getLineNumber());
			linesStatement.setInt(3, lineStatistic.getLineLength());
			linesStatement.setString(4, lineStatistic.getLongestWord());
			linesStatement.setString(5, lineStatistic.getShortestWord());
			linesStatement.setDouble(6, lineStatistic.getAverageWordLength());
			linesStatement.addBatch();
		}
	}
}