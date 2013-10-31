(ns wmonitor.core
  (use [clojure.string :only (join split)]))

(require '[clj-http.client :as client])
(require '[clojure.data.json :as json])

(defn print-logger
  [writer]                   
  #(binding [*out* writer]   
     (println %))) 

(defn file-logger
  "Log messages to $WOOPRA_LOG_DIR/YYYYmmDD.messages.log, if environment not defined, then use $PWD."
  []                                                     
  #(with-open [f (clojure.java.io/writer 
                   (format "%1s/%2$tY%2$tm%2$td.messages.log" 
                           (get (System/getenv) "WOOPRA_LOG_DIR" (get (System/getenv) "PWD"))
                           (java.util.Date.)) :append true)] 
     ((print-logger f) %))) 

(defn multi-logger
  [& logger-fns]           
  #(doseq [f logger-fns]   
     (f %)))

(defn timestamped-logger
  [logger]
  #(logger (format "[%1$tY-%1$tm-%1$te %1$tH:%1$tM:%1$tS] %2$s" (java.util.Date.) %))) 

(def log-timestamped (timestamped-logger
                       (multi-logger
                         ;(print-logger *out*)
                         (file-logger))))

(def errlog-timestamped (timestamped-logger
                          (multi-logger
                            (print-logger *err*)
                            ;(comment file-logger)
                            )))

(defn woopra_request
  "Make a POST request to www.woopra.com/rest/report. Use $WOOPRA_PROXY to override default cmiproxy:8080"
  [query]
  (let [[host port ] (split (get (System/getenv) "WOOPRA_PROXY" "cmiproxy.corp.amdocs.com:8080") #":")]
    (try
    (client/post "http://www.woopra.com/rest/report"
                 {:throw-entire-message? false
                  :proxy-host host
                  :proxy-port (read-string port)
                  :body query
                  :headers {"X-Api-Version" "2.0"
                            "X-Access-Id" "C84TEC26W5CBA4QOAP6OGUPTXR6JNGED"
                            "X-Access-Secret" "DbnhWW0Pva8RXzktD8tnDfG266igMRQWWJapOkwLfKj33Io6gE2yVAGrAU1xcaNL"
                            }
                  :content-type :json 
                  :accept :json})
    (catch Exception e (eval {:body "{\"total\":{\"src\":[-1,-1,-1,-1]}}" :error (.getMessage e)})))))
    
(defn woopra_report
  "Format a JSON report request query"
  [report]
  (json/write-str {
                 :website "www.aiowireless.com",
                 :date_format "MM/dd/yyyy",
                 :start_day   (format "%1$tm/%1$td/%1$tY" (java.util.Date.)),
                 :end_day     (format "%1$tm/%1$td/%1$tY" (java.util.Date.)),
                 :limit 100,
                 :offset 0,
                 :report report
                 }))

