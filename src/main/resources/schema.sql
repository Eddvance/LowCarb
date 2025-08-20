CREATE TABLE IF NOT EXISTS rate_history (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            rate DOUBLE,
                                            rate_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS consumption_history (
                                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                   email VARCHAR(255),
                                                   consumption DOUBLE,
                                                   carbon_rate DOUBLE,
                                                   green_rate DOUBLE,
                                                   total_cost DOUBLE,
                                                   consumption_time TIMESTAMP
);