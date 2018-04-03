(defproject clojure_demo "0.1.0-SNAPSHOT"
  :description "Clojure for the Brave and True"
  :url "https://www.braveclojure.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot clojure-demo.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
