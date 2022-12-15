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
    book_id bigint not null,
    constraint fk_comments_book foreign key (book_id) REFERENCES books (id)
);

insert into books(title, author, isbn, rating) values
('First book', 'Bezoz', 'Anyisbn1', 5),
('Second book', 'Gates', 'Anyisbn2', 4),
('Third book', 'Musk', 'Anyisbn3', 3);

insert into comments(value, book_id) values
('first comment to first book', 1),
('second comment to first book', 1),
('third comment to first book', 1),
('fourth comment to first book', 1),
('fifth comment to first book', 1),
('sixth comment to first book', 1),
('first comment to second book', 2);
