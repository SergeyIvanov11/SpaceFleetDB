CREATE TYPE rank_enum AS ENUM (
    'CADET',          -- курсант
    'ENSIGN',         -- мичман
    'LIEUTENANT',     -- лейтенант
    'LIEUTENANT_COMMANDER',
    'COMMANDER',
    'CAPTAIN',
    'COMMODORE',
    'ADMIRAL'
);

CREATE TYPE specialization_enum AS ENUM (
    'PILOT',
    'ENGINEER',
    'MEDIC',
    'NAVIGATOR',
    'WEAPONS_OFFICER',
    'COMMUNICATIONS',
    'SCIENCE_OFFICER',
    'SECURITY',
    'TECHNICIAN'
);

CREATE TYPE ship_category AS ENUM (
    'SCOUT',       -- разведчик
    'FRIGATE',     -- фрегат
    'DESTROYER',   -- эсминец
    'CRUISER',     -- крейсер
    'CARRIER',     -- авианосец
    'DREADNOUGHT', -- линкор
    'MEDICAL',     -- медицинский корабль
    'TRANSPORT',   -- грузовой
    'RESEARCH'     -- научный
);

CREATE TYPE weapon_status AS ENUM ('READY', 'RELOADING', 'OFFLINE', 'DAMAGED');
CREATE TYPE weapon_class AS ENUM ('LASER', 'MISSILE', 'PLASMA', 'RAILGUN', 'ION');

-- Tables
CREATE TABLE if not exists public.fleet
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(50) NOT NULL UNIQUE,
    alien_race     VARCHAR(50) NOT NULL,
    established_at DATE        NOT NULL,
    active         BOOLEAN     NOT NULL DEFAULT TRUE
);

CREATE TABLE if not exists public.ship
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(50)   NOT NULL UNIQUE,
    fleet_id        INT           NOT NULL REFERENCES fleet (id) ON DELETE CASCADE,
    ship_type       ship_category NOT NULL,
    crew_capacity   INT           NOT NULL CHECK (crew_capacity > 0),
    commissioned_at DATE          NOT NULL,
    is_operational  BOOLEAN       NOT NULL DEFAULT TRUE
);

CREATE TABLE if not exists public.crew_member
(
    id             SERIAL PRIMARY KEY,
    ship_id        INT                 NOT NULL REFERENCES ship (id) ON DELETE CASCADE,
    full_name      VARCHAR(100)        NOT NULL,
    rank           rank_enum           NOT NULL,
    specialization specialization_enum NOT NULL,
    birth_date     DATE                NOT NULL,
    is_active      BOOLEAN             NOT NULL DEFAULT TRUE
);

CREATE TABLE if not exists public.weapon_type
(
    id         SERIAL PRIMARY KEY,
    title      VARCHAR(100) NOT NULL UNIQUE,
    class      weapon_class NOT NULL,
    max_damage INT CHECK (max_damage > 0)
);

CREATE TABLE if not exists public.ship_weapon
(
    id             SERIAL PRIMARY KEY,
    ship_id        INT NOT NULL REFERENCES ship (id) ON DELETE CASCADE,
    weapon_type_id INT NOT NULL REFERENCES weapon_type (id),
    status         weapon_status DEFAULT 'READY',
    ammo_count     INT           DEFAULT 0 CHECK (ammo_count >= 0)
);

CREATE TABLE if not exists public.commander
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