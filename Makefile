start_database:
	docker run --name postgres_test -e POSTGRES_PASSWORD=melon -d -p 5432:5432 postgres

restart_database:
	docker restart postgres_test

stop_database:
	docker stop postgres_test

