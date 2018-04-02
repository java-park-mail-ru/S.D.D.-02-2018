create extension citext;

create table if not exists user_entity (
    id serial primary key,
    nickname varchar(255) constraint nickname_constraint unique,
    email varchar(255) constraint email_constraint unique,
    password_hash varchar(255),
    avatar_path varchar(255),
    game_results_id integer
);

create table if not exists game_results (
    id serial primary key,
    count_wins integer,
    count_games integer,
    rating integer
);

-- add indexes, and fix

create unique index if not exists nickname_index on user_entity(nickname);
create unique index if not exists email_index on user_entity(email);