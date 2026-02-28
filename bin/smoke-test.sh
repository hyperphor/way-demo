# Verify that demo builds and runs
# TODO version suitable for CI
lein clean
shadow-cljs release app
lein run 1881

