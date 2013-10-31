(ns wmonitor.queries
  (:gen-class)
  (:use [wmonitor.core]))

(require '[clojure.data.json :as json])

(def report1 
   {
                          :limit 50,
                          :title "Users By Hour",
                          :index 0,
    
                          :columns [
                                    {
                                     :scope "visitors",
                                     :hide false,
                                     :name "People",
                                     :_format "#,##0",
                                     :method "count",
                                     :render "number_format(cell('People'), '#,##0')"
                                     }
                                    ],
                          :group_by [
                                     {
                                      :scope "visits",
                                      :key "time",
                                      :transforms [
                                                   {
                                                    :params [
                                                             "HH"
                                                             ],
                                                    :function "date_format"
                                                    }
                                                   ]
                                      }
                                     ],
                          :order_by "",
                          :render ""
                          }
   )
(def report2
  {
   :limit 50,
   :title "My Report",
   :columns [
             {
              :name "Visitors",
              :method "count",
              :scope "visitors",
              :hide false,
              :_format "#,##0",
              :render "number_format(cell('Visitors'), '#,##0')"
              },
             {
              :name "Errors",
              :method "count",
              :scope "actions",
              :flags {
                      :scan_policy "ONLY_MATCHING_ACTION"
                      },
              :constraints {
                            :operator "AND",
                            :filters [
                                      {
                                       :scope "actions",
                                       :_uikey "actions:errors on page",
                                       :value "errors on page",
                                       :match "match",
                                       :key "name"
                                       }
                                      ]
                            },
              :hide false,
              :_format "#,##0",
              :render "number_format(cell('Errors'), '#,##0')"
              },
             {
              :name "Orders",
              :method "count",
              :scope "actions",
              :flags {
                      :scan_policy "ONLY_MATCHING_ACTION"
                      },
              :constraints {
                            :operator "AND",
                            :filters [
                                      {
                                       :match "match",
                                       :value "order complete",
                                       :scope "actions",
                                       :key "name",
                                       :_uikey "actions:order complete"
                                       }
                                      ]
                            },
              :hide false,
              :_format "#,##0",
              :render "number_format(cell('Orders'), '#,##0')"
              },
             {
              :name "Portins",
              :method "count",
              :scope "actions",
              :flags {
                      :scan_policy "ONLY_MATCHING_ACTION"
                      },
              :constraints {
                            :operator "AND",
                            :filters [
                                      {
                                       :match "match",
                                       :value "order info - transfer",
                                       :scope "actions",
                                       :key "name",
                                       :_uikey "actions:order info - transfer"
                                       }
                                      ]
                            },
              :hide false,
              :_format "#,##0",
              :render "number_format(cell('Portins'), '#,##0')"
              }
             ],
   :_index 1,
   :group_by [
              {
               :scope "visits",
               :key "day",
               :transforms []
               }
              ],
   :order_by "",
   :render ""
   }
  )
(defn -main [& args]
  (loop []
    (def response (woopra_request(woopra_report report2)))
    (let [[visitors errors orders portins] 
          (get (get (json/read-str (:body response)) "total") "src")]
      (log-timestamped (format "Total Visitors: %s Errors: %s Orders: %s Portins: %s" visitors errors orders portins))
      (if (neg? visitors) 
        (errlog-timestamped (format "Encountered error %s" (:error response)))))
    (java.lang.Thread/sleep 300000)
    (recur)))

