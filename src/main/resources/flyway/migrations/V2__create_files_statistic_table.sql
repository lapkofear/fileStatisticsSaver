CREATE TABLE files (
	file_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	file_name CHARACTER VARYING
);