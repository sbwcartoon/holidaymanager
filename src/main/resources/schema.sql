CREATE TABLE IF NOT EXISTS holiday
(
    id           VARCHAR(64) PRIMARY KEY,
    country_code VARCHAR(2) NOT NULL,
    `date`       DATE NOT NULL,
    local_name   VARCHAR(255) NOT NULL,
    `name`       VARCHAR(255) NOT NULL,
    `global`     BOOLEAN NOT NULL,
    launch_year  INT
);

CREATE TABLE IF NOT EXISTS holiday_county
(
    id         VARCHAR(64) PRIMARY KEY,
    holiday_id VARCHAR(64) NOT NULL,
    code       VARCHAR(16) NOT NULL,

    CONSTRAINT fk_holiday_county_holiday
        FOREIGN KEY (holiday_id) REFERENCES holiday (id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS holiday_type
(
    id         VARCHAR(64) PRIMARY KEY,
    holiday_id VARCHAR(64) NOT NULL,
    code       VARCHAR(20) NOT NULL CHECK (
        code IN ('Public', 'Bank', 'School', 'Authorities', 'Optional', 'Observance')
    ),

    CONSTRAINT fk_holiday_type_holiday
        FOREIGN KEY (holiday_id) REFERENCES holiday (id)
            ON DELETE CASCADE
);
