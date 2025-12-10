CREATE TYPE rank_enum AS ENUM (
    'cadet',          -- курсант
    'ensign',         -- мичман
    'lieutenant',     -- лейтенант
    'lieutenant_commander',
    'commander',
    'captain',
    'commodore',
    'admiral'
);

CREATE TYPE specialization_enum AS ENUM (
    'pilot',
    'engineer',
    'medic',
    'navigator',
    'weapons_officer',
    'communications',
    'science_officer',
    'security',
    'technician'
);

CREATE TYPE ship_category AS ENUM (
    'scout',       -- разведчик
    'frigate',     -- фрегат
    'destroyer',   -- эсминец
    'cruiser',     -- крейсер
    'carrier',     -- авианосец
    'dreadnought', -- линкор
    'medical',     -- медицинский корабль
    'transport',   -- грузовой
    'research'     -- научный
);

CREATE TYPE weapon_status AS ENUM ('ready', 'reloading', 'offline', 'damaged');
CREATE TYPE weapon_class AS ENUM ('laser', 'missile', 'plasma', 'railgun', 'ion');

-- Tables
CREATE TABLE fleet
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(50) NOT NULL UNIQUE,
    alien_race     VARCHAR(50) NOT NULL,
    established_at DATE        NOT NULL,
    active         BOOLEAN     NOT NULL DEFAULT TRUE
);

CREATE TABLE ship
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(50)   NOT NULL UNIQUE,
    fleet_id        INT           NOT NULL REFERENCES fleet (id) ON DELETE CASCADE,
    ship_type       ship_category NOT NULL,
    crew_capacity   INT           NOT NULL CHECK (crew_capacity > 0),
    commissioned_at DATE          NOT NULL,
    is_operational  BOOLEAN       NOT NULL DEFAULT TRUE
);

CREATE INDEX idx_ship_fleet ON ship (fleet_id);
CREATE INDEX idx_ship_type ON ship (ship_type);

CREATE TABLE crew_member
(
    id             SERIAL PRIMARY KEY,
    ship_id        INT                 NOT NULL REFERENCES ship (id) ON DELETE CASCADE,
    full_name      VARCHAR(100)        NOT NULL,
    rank           rank_enum           NOT NULL,
    specialization specialization_enum NOT NULL,
    birth_date     DATE                NOT NULL,
    is_active      BOOLEAN             NOT NULL DEFAULT TRUE
);

CREATE INDEX idx_crew_ship ON crew_member (ship_id);

CREATE TABLE weapon_type
(
    id         SERIAL PRIMARY KEY,
    title      VARCHAR(100) NOT NULL UNIQUE,
    class      weapon_class NOT NULL,
    max_damage INT CHECK (max_damage > 0)
);

CREATE TABLE ship_weapon
(
    id             SERIAL PRIMARY KEY,
    ship_id        INT NOT NULL REFERENCES ship (id) ON DELETE CASCADE,
    weapon_type_id INT NOT NULL REFERENCES weapon_type (id),
    status         weapon_status DEFAULT 'ready',
    ammo_count     INT           DEFAULT 0 CHECK (ammo_count >= 0)
);

CREATE UNIQUE INDEX idx_ship_weapon_unique
    ON ship_weapon (ship_id, weapon_type_id);

CREATE TABLE commander
(
    id                SERIAL PRIMARY KEY,
    full_name         VARCHAR(100) NOT NULL,
    rank              rank_enum    NOT NULL,
    assigned_fleet_id INT REFERENCES fleet (id),
    assigned_ship_id  INT REFERENCES ship (id),
    CHECK (
        (assigned_fleet_id IS NOT NULL AND assigned_ship_id IS NULL)
            OR
        (assigned_fleet_id IS NULL AND assigned_ship_id IS NOT NULL)
        )
);

CREATE INDEX idx_commander_fleet ON commander (assigned_fleet_id);
CREATE INDEX idx_commander_ship ON commander (assigned_ship_id);

-- Triggers
--нельзя добавить члена экипажа, если корабль переполнен
CREATE FUNCTION check_crew_capacity()
    RETURNS trigger AS $$
DECLARE
current_count INT;
    capacity
INT;
BEGIN
SELECT COUNT(*)
INTO current_count
FROM crew_member
WHERE ship_id = NEW.ship_id;
SELECT crew_capacity
INTO capacity
FROM ship
WHERE id = NEW.ship_id;

IF
current_count >= capacity THEN
        RAISE EXCEPTION 'Ship capacity exceeded';
END IF;

RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER trg_check_crew_capacity
    BEFORE INSERT
    ON crew_member
    FOR EACH ROW EXECUTE FUNCTION check_crew_capacity();

--автоматический контроль возраста экипажа
CREATE FUNCTION check_crew_member_age()
RETURNS trigger AS $$
DECLARE
years int;
BEGIN
SELECT DATE_PART('year', AGE(NEW.birth_date))
INTO years;

IF
years < 18 THEN
        RAISE EXCEPTION 'Crew member must be at least 18 years old. Given age: %', years;
END IF;

RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER trg_check_crew_member_age
    BEFORE INSERT
    ON crew_member
    FOR EACH ROW
    EXECUTE FUNCTION check_crew_member_age();

-- Views
--боевой потенциал корабля
CREATE VIEW ship_combat_power AS
SELECT s.id                           AS ship_id,
       s.name                         AS ship_name,
       COALESCE(SUM(w.max_damage), 0) AS total_damage
FROM ship s
         LEFT JOIN ship_weapon sw ON sw.ship_id = s.id
         LEFT JOIN weapon_type w ON w.id = sw.weapon_type_id
GROUP BY s.id, s.name;

--количество членов экипажа на каждом корабле
CREATE VIEW ship_crew_count AS
SELECT s.id        AS ship_id,
       s.name      AS ship_name,
       COUNT(c.id) AS crew_count
FROM ship s
         LEFT JOIN crew_member c ON c.ship_id = s.id
GROUP BY s.id, s.name
ORDER BY crew_count DESC;