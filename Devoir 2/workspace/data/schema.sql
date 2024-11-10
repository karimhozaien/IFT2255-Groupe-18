CREATE TABLE IF NOT EXISTS Work (
                                    id TEXT PRIMARY KEY,
                                    title TEXT NOT NULL,
                                    typeOfWork TEXT,
                                    neighborhood TEXT,
                                    status TEXT
);

CREATE TABLE IF NOT EXISTS User (
    name TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE
);