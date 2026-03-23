.DEFAULT_GOAL := help

help: ## Показать справку по командам
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-15s\033[0m %s\n", $$1, $$2}'

up: ## Запустить контейнеры в фоновом режиме
	docker-compose --env-file .env up -d

down: ## Остановка и удаление: контейнеры и виртуальные сети.
	docker-compose down

stop: ## Просто остановка контейнеров (без удаления)
	docker-compose stop

start: ## Запустить ранее остановленные контейнеры
	docker-compose start

restart: ## Перезапустить контейнеры
	docker-compose stop
	docker-compose --env-file .env up -d

logs: ## Посмотреть логи контейнеров
	docker-compose logs -f

ps: ## Список запущенных контейнеров
	docker-compose ps

images: ## Показать список всех локальных образов
	docker images

clean: ## Остановка и удаление: контейнеров, сетей и всех данных в базе (volumes)
	docker-compose down -v

prune: ## Очистка остановленных контейнеров, неиспользуемые сети и «битых» образов (volumes)
	docker system prune -f

prune-all: ## Удаляет все образы, все остановленные контейнеры и все данные (volumes)
	docker-compose down
	docker system prune -a --volumes -f
