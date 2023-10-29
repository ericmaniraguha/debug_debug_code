-- noinspection SqlNoDataSourceInspectionForFile

CREATE TABLE IF NOT EXISTS MissingRecord (
    id BIGINT NOT NULL PRIMARY KEY,
    msg VARCHAR(255),
    produced_at TIMESTAMP(6) WITHOUT TIME ZONE
);