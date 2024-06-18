DROP TABLE IF EXISTS friend_status CASCADE;
DROP TABLE IF EXISTS genre CASCADE;
DROP TABLE IF EXISTS mpa_rating CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS film CASCADE;
DROP TABLE IF EXISTS film_genre CASCADE;
DROP TABLE IF EXISTS friends CASCADE;
DROP TABLE IF EXISTS likes CASCADE;

CREATE TABLE IF NOT EXISTS friend_status (
  id integer PRIMARY KEY,
  status varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS genre (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS mpa_rating (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  email varchar NOT NULL,
  login varchar NOT NULL,
  username varchar NOT NULL,
  birthday date NOT NULL
);

CREATE TABLE IF NOT EXISTS film (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar(50) NOT NULL,
  description varchar(200),
  release_date date NOT NULL,
  duration integer NOT NULL,
  mpa_rating_id integer NOT NULL REFERENCES mpa_rating(id)
);

CREATE TABLE IF NOT EXISTS film_genre (
  film_id integer REFERENCES film (id),
  genre_id integer REFERENCES genre (id),
  PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS friends (
  user_id integer REFERENCES users (id),
  friend_id integer REFERENCES users (id),
  status_id integer NOT NULL REFERENCES friend_status (id),
  PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS likes (
  film_id integer NOT NULL REFERENCES film (id),
  user_id integer NOT NULL REFERENCES users (id),
  PRIMARY KEY (film_id, user_id)
);