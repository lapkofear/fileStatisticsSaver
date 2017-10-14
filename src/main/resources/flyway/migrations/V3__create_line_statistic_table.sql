CREATE TABLE line_statistics(
	line_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	file_id UUID REFERENCES files ON DELETE CASCADE,
	line_number INTEGER,
	line_length INTEGER,
	longest_word CHARACTER VARYING,
	shortest_word CHARACTER VARYING,
	average_word_length DECIMAL
);