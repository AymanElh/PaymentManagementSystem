-- Database for payment management system

CREATE
    DATABASE IF NOT EXISTS payment_management_system;

USE
    payment_management_system;

CREATE TABLE IF NOT EXISTS users
(
    id         BIGINT PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    phone      VARCHAR(25),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS departments
(
    id          BIGINT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    manager_id  BIGINT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS agents
(
    id            BIGINT PRIMARY KEY,
    user_id       BIGINT NOT NULL,
    type          ENUM ('employee', 'department_manager', 'director', 'intern'),
    department_id BIGINT NULL,
    start_date    TIMESTAMP,
    salary        DECIMAL(10, 2),
    is_active     BOOLEAN   DEFAULT true,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_agent_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_department_agent FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE SET NULL
);


CREATE TABLE IF NOT EXISTS payments
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    type                 ENUM ('SALARY', 'PRIME', 'BONUS', 'INDEMNITE') NOT NULL,
    amount               DECIMAL(10, 2)                                 NOT NULL CHECK (amount > 0),
    payment_date         DATE                                           NOT NULL,
    condition_validation BOOLEAN   DEFAULT FALSE,
    agent_id             BIGINT                                         NOT NULL,
    created_by           BIGINT,
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_payment_agent
        FOREIGN KEY (agent_id)
            REFERENCES agents (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_payment_manager FOREIGN KEY (created_by) REFERENCES agents(id)
);

