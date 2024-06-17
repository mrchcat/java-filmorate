BEGIN;

INSERT INTO "friend_status"("id","status")
VALUES (1,'requested'),(2,'accepted');

INSERT INTO "genre"("name")
VALUES ('Комедия'),('Драма'),('Мультфильм'),('Триллер'),('Документальный'),('Боевик');

INSERT INTO "mpa_rating" ("name")
VALUES ('G'),('PG'),('PG-13'),('R'),('NC-17');

COMMIT;