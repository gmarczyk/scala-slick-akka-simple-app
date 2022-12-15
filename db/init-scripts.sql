create table if not exists books(
    id bigserial primary key not null,
    title varchar default null,
    author varchar default null,
    isbn varchar default null unique,
    rating bigint not null
);

create table if not exists comments(
    id bigserial primary key not null,
    value varchar not null,
    book_id bigint not null
);

insert into books(id, title, authot, isbn, rating) values
(1, 'First book', 'Bezoz', 'Anyisbn1', 5),
(2, 'Second book', 'Gates', 'Anyisbn2', 4),
(3, 'Third book', 'Musk', 'Anyisbn3', 3);

insert into comments(id, value, book_id) values
(1, 'first comment to first book', 1),
(2, 'second comment to first book', 1),
(3, 'third comment to first book', 1),
(4, 'fourth comment to first book', 1),
(5, 'fifth comment to first book', 1),
(6, 'sixth comment to first book', 1),
(7, 'first comment to second book', 2),
