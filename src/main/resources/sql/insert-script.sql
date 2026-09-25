INSERT INTO users (username, password, enabled) VALUES
('jobportal', '{noop}user@7213', true),
('jobportaladmin', '{bcrypt}$2a$12$gcRthxnLXjZorpnaQin.2u1F.3meWxc7VxO3nglsbpWy79f60OhIa', true);

INSERT INTO authorities (username, authority) VALUES
('jobportal', 'read'),
('jobportaladmin', 'admin');


#password for jobportaladmin is admin@7213
#password for senthil7213@gmail.com is users@7213
#password for ashu7213@gmail.com is usera@7213
insert into customer (email, pwd, role) values
('senthil7213@gmail.com', '{noop}users@7213', 'read'),
('ashu7213@gmail.com', '{bcrypt}$2a$12$mVWNUG0FkrRY/C0nBd.SwOCrYdedMeDKEBxeSW6CavGmSuYyIiIYG', 'read');