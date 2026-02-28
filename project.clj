(defproject hyperphor/way-demo "0.2.2" 
  :description "Way"
  :url "https://shrouded-escarpment-03060-744eda4cc53f.herokuapp.com/"
  :plugins [[lein-shell "0.5.0"]]
  :dependencies [[org.clojure/clojure "1.12.4"]
                 [com.hyperphor/way "0.2.2"]]
  :main ^:skip-aot hyperphor.way.demo.core
  :source-paths ["src/cljc" "src/clj" "src/cljs"] 
  :clean-targets  ^{:protect false} ["target" ".shadow-cljs" "resources/public/cljs-out"]

  :profiles {:uberjar {:aot :all
                       :omit-source true
                       :prep-tasks [["shell" "shadow-cljs" "release" "app"]
                                    "javac" ;NOTE if you omit the javac compile items, :aot stops working! 
                                    "compile"] 
                       :resource-paths ["resources"]
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]
                       }
             :dev {:dependencies [[thheller/shadow-cljs "3.3.6"]
                                  [day8.re-frame/tracing "0.6.2"]     
                                  [day8.re-frame/re-frame-10x "1.11.0"]]}
             }
  )
