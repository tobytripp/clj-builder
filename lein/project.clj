(defproject clj-builder "1.0.0"
  :description "Generate Docker containers for building clojure projects"
  :license {:name         "Eclipse Public License - v 1.0"
            :url          "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments     "same as Clojure"}

  :dependencies [[org.clojure/clojure         "1.9.0"]
                 [environ                     "1.1.0"]
                 [com.stuartsierra/component  "0.3.2"]

                 [com.taoensso/timbre         "4.10.0"]
                 [metosin/compojure-api       "2.0.0-alpha21"]
                 [http-kit                    "2.2.0"]
                 [buddy/buddy-auth            "2.1.0"]
                 [hiccup                      "1.0.5"]]

  :main ^:skip-aot builder.core

  :target-path "target/%s"
  :source-paths   ["src" "src/clj"]
  :test-paths     ["test" "test/clj" "src/clj/test"]
  :resource-paths ["src/resources" "resources"]

  :plugins        [[lein-pprint "1.1.1"]]

  :profiles {:dev     {:plugins [[lein-ring "0.10.0"]]}
             :repl    {:dependencies [[cider/cider-nrepl "0.17.0"]]}
             :uberjar {:aot :all}}
  :global-vars {*warn-on-reflection* true
                *assert*             true}
  :jvm-opts ["-Xmx1g"]
  :bootclasspath true)
