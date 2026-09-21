```
docker port rsyslog-server
```

```
514/tcp -> 0.0.0.0:514
514/tcp -> [::]:514
514/udp -> 0.0.0.0:514
514/udp -> [::]:514
```

---
```
docker compose ps
```

```
time="2026-09-21T18:59:46-03:00" level=warning msg="C:\\dev\\eclipse-workspace\\blog-api\\docker-compose.yml: the attribute `version` is obsolete, it will be ignored, please remove it to avoid potential confusion"
NAME             IMAGE                              COMMAND                  SERVICE   CREATED              STATUS                          PORTS
apache-logs      loesterbotelho/apache-logs:1.0.0   "httpd-foreground"       apache    About a minute ago   Up About a minute (unhealthy)   0.0.0.0:8081->80/tcp, [::]:8081->80/tcp
meu_mariadb      mariadb:11.8                       "docker-entrypoint.s…"   mariadb   About a minute ago   Up About a minute (healthy)     0.0.0.0:3306->3306/tcp, [::]:3306->3306/tcp
rsyslog-server   loesterbotelho/rsyslog:1.0.0       "rsyslogd -n"            rsyslog   About a minute ago   Up About a minute (healthy)     0.0.0.0:514->514/tcp, 0.0.0.0:514->514/udp, [::]:514->514/tcp, [::]:514->514/udp
```
