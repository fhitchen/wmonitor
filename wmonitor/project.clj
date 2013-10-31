(defproject woopramonitor "0.1.0-SNAPSHOT"
  :description "Simple monitor for AIO Woopra"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-http "0.7.7"]
                 [org.clojure/data.json "0.2.4"]]
  :main woopramonitor.queries
  :aot  [woopramonitor.queries])
  