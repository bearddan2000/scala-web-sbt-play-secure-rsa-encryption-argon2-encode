# scala-web-sbt-play-secure-rsa-encryption-argon2-encode

## Description
A call to playframework web
with simple login forms argon2 hash
with rsa encryption.

## Tech stack
- scala
- sbt
  - play
  - rsa
  - gzip compression
  - argon2

## Docker stack
- hseeberger/scala-sbt:11.0.2-oraclelinux7_1.3.5_2.12.10

## To run
`sudo ./install.sh -u`
http://localhost
- username: user
- password: pass

## To stop (optional)
`sudo ./install.sh -d`

## For help
`sudo ./install.sh -h`

## Credit
- [Code based on](https://github.com/alvinj/PlayFrameworkLoginAuthenticationExample.git)
