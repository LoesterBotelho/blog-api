@echo off

echo Subindo os containers em segundo plano (detached mode)...
docker compose up -d --pull always

echo.
echo Processo concluido! Container em execucao.
pause