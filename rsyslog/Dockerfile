FROM ubuntu:24.04

RUN apt-get update \
    && apt-get install -y rsyslog procps \
    && rm -rf /var/lib/apt/lists/*

COPY rsyslog.conf /etc/rsyslog.conf

RUN mkdir -p /logs

EXPOSE 514/tcp
EXPOSE 514/udp

CMD ["rsyslogd", "-n"]