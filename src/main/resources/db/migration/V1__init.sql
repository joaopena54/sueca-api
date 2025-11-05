-- Create ENUM types
CREATE TYPE match_status_enum AS ENUM ('IN_PROGRESS', 'COMPLETED', 'ABANDONED');
CREATE TYPE team_enum AS ENUM ('TEAM_A', 'TEAM_B');

CREATE SEQUENCE IF NOT EXISTS sueca_id_seq;

CREATE TABLE IF NOT EXISTS "user" (
    id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "match" (
    id BIGINT PRIMARY KEY,
    started_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    finished_at TIMESTAMP,
    status match_status_enum NOT NULL DEFAULT 'IN_PROGRESS',
    winner_team team_enum,
    team_a_score INTEGER NOT NULL DEFAULT 0,
    team_b_score INTEGER NOT NULL DEFAULT 0,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS match_participant (
    id BIGINT PRIMARY KEY,
    match_id BIGINT NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    team team_enum NOT NULL,
    won BOOLEAN,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_match_participant_match FOREIGN KEY (match_id) REFERENCES "match"(id) ON DELETE CASCADE,
    CONSTRAINT fk_match_participant_user FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE,
    CONSTRAINT unique_match_user UNIQUE (match_id, user_id)
);

CREATE TABLE IF NOT EXISTS player_statistic (
    id BIGINT PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL UNIQUE,
    total_matches INTEGER DEFAULT 0,
    matches_won INTEGER DEFAULT 0,
    matches_lost INTEGER DEFAULT 0,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_player_statistics_user FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_match_started_at ON "match"(started_at);
CREATE INDEX IF NOT EXISTS idx_match_status ON "match"(status);
CREATE INDEX IF NOT EXISTS idx_match_participant_match_id ON match_participant(match_id);
CREATE INDEX IF NOT EXISTS idx_match_participant_user_id ON match_participant(user_id);
CREATE INDEX IF NOT EXISTS idx_user_username ON "user"(username);




