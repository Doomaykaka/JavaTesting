CREATE TABLE
	IF NOT EXISTS "test_sequence" ("next_val" bigint);

CREATE TABLE
	IF NOT EXISTS "tests" (
		"id" bigint NOT NULL,
		"data" varchar NOT NULL,
		PRIMARY KEY ("id")
	);

INSERT INTO
    "test_sequence"
VALUES
    (61);

INSERT INTO
	"tests"
VALUES
	(1, 'Test');

INSERT INTO
    "tests"
VALUES
    (22, 'Test2');
