
CREATE TABLE jobs (
    id                   VARCHAR(20) NOT NULL,
    name                 VARCHAR(200) NOT NULL,
    state                VARCHAR(20) NOT NULL,

    CONSTRAINT jobs_pk PRIMARY KEY (id)
);

CREATE INDEX idx_jobs_name ON jobs (name);
CREATE INDEX idx_jobs_state ON jobs (state);


CREATE TABLE nodes (
    id                   VARCHAR(20) NOT NULL,
    job_id               VARCHAR(20) NOT NULL,
    name                 VARCHAR(200) NOT NULL,
    description          TEXT,
    type                 VARCHAR(20) NOT NULL,
    class_name           VARCHAR(200) NOT NULL,
    state                VARCHAR(20) NOT NULL,

    CONSTRAINT nodes_pk PRIMARY KEY (id),
    CONSTRAINT nodes_fk_job_id FOREIGN KEY (job_id) REFERENCES jobs (id) ON DELETE CASCADE
);


CREATE TABLE edges (
    id                   VARCHAR(20) NOT NULL,
    job_id               VARCHAR(20) NOT NULL,
    begin_node           VARCHAR(20) NOT NULL,
    end_node             VARCHAR(20) NOT NULL,
    label                VARCHAR(200) NOT NULL,
    state                VARCHAR(20) NOT NULL,

    CONSTRAINT edges_pk PRIMARY KEY (id),
    CONSTRAINT edges_fk_job_id FOREIGN KEY (job_id) REFERENCES jobs (id) ON DELETE CASCADE,
    CONSTRAINT edges_fk_begin_node FOREIGN KEY (begin_node) REFERENCES nodes (id) ON DELETE CASCADE,
    CONSTRAINT edges_fk_end_node FOREIGN KEY (end_node) REFERENCES nodes (id) ON DELETE CASCADE
);


CREATE TABLE runs (
    id                   VARCHAR(20) NOT NULL,
    job_id               VARCHAR(20) NOT NULL,
    user_id              VARCHAR(80) NOT NULL,
    state                VARCHAR(20) NOT NULL,
    auths                TEXT,
    start                TIMESTAMP NOT NULL,
    stop                 TIMESTAMP,

    CONSTRAINT runs_pk PRIMARY KEY (id),
    CONSTRAINT runs_fk_job_id FOREIGN KEY (job_id) REFERENCES jobs (id) ON DELETE CASCADE
);


CREATE TABLE node_runs (
    job_id               VARCHAR(20) NOT NULL,
    run_id               VARCHAR(20) NOT NULL,
    node_id              VARCHAR(20) NOT NULL,
    state                VARCHAR(20) NOT NULL,
    start                TIMESTAMP NOT NULL,
    stop                 TIMESTAMP,

    CONSTRAINT node_runs_pk PRIMARY KEY (run_id, node_id),
    CONSTRAINT node_runs_fk_job_id FOREIGN KEY (job_id) REFERENCES jobs (id) ON DELETE CASCADE,
    CONSTRAINT node_runs_fk_run_id FOREIGN KEY (run_id) REFERENCES runs (id) ON DELETE CASCADE,
    CONSTRAINT node_runs_fk_node_id FOREIGN KEY (node_id) REFERENCES nodes (id) ON DELETE CASCADE
);


CREATE TABLE sessions (
    id                   VARCHAR(20) NOT NULL,
    name                 VARCHAR(200) NOT NULL,
    user_id              VARCHAR(80) NOT NULL,
    creation             TIMESTAMP NOT NULL,
    expiration           TIMESTAMP NOT NULL,

    CONSTRAINT sessions_pk PRIMARY KEY (id)
);

