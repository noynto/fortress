CREATE TABLE transactions
(
    id                TEXT    NOT NULL PRIMARY KEY,
    description       TEXT    NOT NULL,
    amount_cents      INTEGER NOT NULL,
    type              TEXT    NOT NULL,
    state             TEXT    NOT NULL,
    owner_identity_id TEXT    NOT NULL,
    issue_date        TEXT    NOT NULL,
    application_date  TEXT    NOT NULL
);