Перед запуском необходимо запустить PostgreSQL:

`docker run --name bankbook-postgres -p 5432:5432 -e POSTGRES_USER=user1 -e POSTGRES_PASSWORD=password1 -e POSTGRES_DB=bankbook -d postgres:14
`

Затем накатить sql-скрипты на базу.