
CREATE TABLE jobs (
    id                   TEXT NOT NULL,
    name                 TEXT NOT NULL,
    state                TEXT NOT NULL,

    CONSTRAINT jobs_pk PRIMARY KEY (id)
);

CREATE INDEX idx_jobs_name ON jobs (name);
CREATE INDEX idx_jobs_state ON jobs (state);


CREATE TABLE nodes (
    id                   TEXT NOT NULL,
    job_id               TEXT NOT NULL,
    name                 TEXT NOT NULL,
    description          TEXT,
    type                 TEXT NOT NULL,
    class_name           TEXT NOT NULL,
    state                TEXT NOT NULL,

    CONSTRAINT nodes_pk PRIMARY KEY (id),
    CONSTRAINT nodes_fk_job_id FOREIGN KEY (job_id) REFERENCES jobs (id) ON DELETE CASCADE
);


CREATE TABLE edges (
    id                   TEXT NOT NULL,
    job_id               TEXT NOT NULL,
    begin_node           TEXT NOT NULL,
    end_node             TEXT NOT NULL,
    label                TEXT NOT NULL,
    state                TEXT NOT NULL,

    CONSTRAINT edges_pk PRIMARY KEY (id),
    CONSTRAINT edges_fk_job_id FOREIGN KEY (job_id) REFERENCES jobs (id) ON DELETE CASCADE,
    CONSTRAINT edges_fk_begin_node FOREIGN KEY (begin_node) REFERENCES nodes (id) ON DELETE CASCADE,
    CONSTRAINT edges_fk_end_node FOREIGN KEY (end_node) REFERENCES nodes (id) ON DELETE CASCADE
);


CREATE TABLE runs (
    id                   TEXT NOT NULL,
    job_id               TEXT NOT NULL,
    user_id              TEXT NOT NULL,
    state                TEXT NOT NULL,
    auths                TEXT NOT NULL,
    start                TIMESTAMP NOT NULL,
    stop                 TIMESTAMP,

    CONSTRAINT runs_pk PRIMARY KEY (id),
    CONSTRAINT runs_fk_job_id FOREIGN KEY (job_id) REFERENCES jobs (id) ON DELETE CASCADE
);


CREATE TABLE node_runs (
    run_id               TEXT NOT NULL,
    node_id              TEXT NOT NULL,
    state                TEXT NOT NULL,
    start                TIMESTAMP NOT NULL,
    stop                 TIMESTAMP,

    CONSTRAINT node_runs_pk PRIMARY KEY (run_id, node_id),
    CONSTRAINT node_runs_fk_run_id FOREIGN KEY (run_id) REFERENCES runs (id) ON DELETE CASCADE,
    CONSTRAINT node_runs_fk_node_id FOREIGN KEY (node_id) REFERENCES nodes (id) ON DELETE CASCADE
);


