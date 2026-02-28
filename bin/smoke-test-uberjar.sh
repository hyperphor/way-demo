lein clean
shadow-cljs release app
lein uberjar
java -jar target/way-demo-0.1.7-standalone.jar 1882
