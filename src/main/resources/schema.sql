CREATE TABLE IF NOT EXISTS rate_history (
                                            id BIGSERIAL PRIMARY KEY,
                                            rate DOUBLE PRECISION,
                                            rate_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS consumption_history (
                                                   id BIGSERIAL PRIMARY KEY,
                                                   email VARCHAR(255),
                                                   consumption DOUBLE PRECISION,
                                                   carbon_rate DOUBLE PRECISION,
                                                   green_rate DOUBLE PRECISION,
                                                   total_cost DOUBLE PRECISION,
                                                   consumption_time TIMESTAMP
);