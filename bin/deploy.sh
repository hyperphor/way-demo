# Heroku deployment
set -e
lein uberjar
heroku deploy:jar target/way-demo-0.2.2-standalone.jar --jdk=21.0.2
