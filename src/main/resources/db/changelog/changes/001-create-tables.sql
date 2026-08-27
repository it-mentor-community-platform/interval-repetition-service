CREATE TABLE specializations
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE categories
(
    id                BIGSERIAL PRIMARY KEY,
    specialization_id BIGINT       NOT NULL,
    name              VARCHAR(255) NOT NULL,

    CONSTRAINT fk_categories_specialization
        FOREIGN KEY (specialization_id)
            REFERENCES specializations (id)
            ON DELETE CASCADE
);

CREATE TABLE questions
(
    id          BIGSERIAL PRIMARY KEY,
    category_id BIGINT  NOT NULL,
    title       TEXT    NOT NULL UNIQUE,
    answer      TEXT    NOT NULL,
    enabled     BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_questions_category
        FOREIGN KEY (category_id)
            REFERENCES categories (id)
            ON DELETE CASCADE
);

CREATE TABLE user_question_schedules
(
    id                         BIGSERIAL PRIMARY KEY,
    user_id                    BIGINT           NOT NULL,
    question_id                BIGINT           NOT NULL,
    successful_repetitions     INTEGER          NOT NULL DEFAULT 0,
    ease_factor                DOUBLE PRECISION NOT NULL DEFAULT 2.5,
    interval                   INTEGER          NOT NULL DEFAULT 0,
    next_review_at             BIGINT           NOT NULL,
    last_review_at             BIGINT,

    CONSTRAINT fk_uqs_question
        FOREIGN KEY (question_id)
            REFERENCES questions (id)
            ON DELETE CASCADE
);

CREATE TABLE review_attempts
(
    id                        BIGSERIAL PRIMARY KEY,
    user_question_schedule_id BIGINT           NOT NULL,
    quality                   SMALLINT         NOT NULL,
    ease_factor               DOUBLE PRECISION NOT NULL,
    new_ease_factor           DOUBLE PRECISION NOT NULL,
    answered_at               BIGINT           NOT NULL,

    CONSTRAINT fk_review_attempt_schedule
        FOREIGN KEY (user_question_schedule_id)
            REFERENCES user_question_schedules (id)
            ON DELETE CASCADE,

    CONSTRAINT chk_review_attempt_quality
        CHECK (quality BETWEEN 0 AND 5)
);


CREATE TABLE user_category_selections
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT  NOT NULL,
    category_id BIGINT  NOT NULL,

    CONSTRAINT fk_user_category_selections_category
        FOREIGN KEY (category_id)
            REFERENCES categories (id)
            ON DELETE CASCADE
);


CREATE INDEX ix_categories_specialization_id
    ON categories (specialization_id);

CREATE UNIQUE INDEX ux_user_category_selections_user_id_category_id
    ON user_category_selections (user_id, category_id);

CREATE INDEX ix_user_category_selections_user_id
    ON user_category_selections (user_id);

CREATE INDEX ix_questions_category_id
    ON questions (category_id);

CREATE UNIQUE INDEX ux_user_question_schedule_user_id_question_id
    ON user_question_schedules (user_id, question_id);

CREATE INDEX ix_user_question_schedule_user_id_next_review_at
    ON user_question_schedules (user_id, next_review_at);

CREATE INDEX ix_review_attempts_user_question_schedule_id
    ON review_attempts (user_question_schedule_id);