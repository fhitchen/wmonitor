(ns woopramonitor.test)
(require '[clojure.data.json :as json])
(def var "{\"total\":{\"cells\":[\"12,193\",\"2,539\",54,55],\"count\":1,\"src\":[12193,2539,54,55]},\"model\":{\"limit\":2000,\"report\":{\"limit\":2000,\"title\":\"My Report\",\"_index\":1,\"columns\":[{\"scope\":\"visitors\",\"hide\":false,\"_format\":\"#,##0\",\"name\":\"Visitors\",\"method\":\"count\",\"constraints-hash\":\"_230566771\",\"render\":\"number_format(cell('Visitors'), '#,##0')\"},{\"flags\":{\"scan_policy\":\"ONLY_MATCHING_ACTION\"},\"scope\":\"actions\",\"hide\":false,\"_format\":\"#,##0\",\"name\":\"Errors\",\"constraints\":{\"filters\":[{\"scope\":\"actions\",\"_uikey\":\"actions:errors on page\",\"value\":\"errors on page\",\"match\":\"match\",\"key\":\"name\"}],\"operator\":\"AND\"},\"method\":\"count\",\"constraints-hash\":\"_548552044\",\"render\":\"number_format(cell('Errors'), '#,##0')\"},{\"flags\":{\"scan_policy\":\"ONLY_MATCHING_ACTION\"},\"scope\":\"actions\",\"hide\":false,\"_format\":\"#,##0\",\"name\":\"Orders\",\"constraints\":{\"filters\":[{\"scope\":\"actions\",\"_uikey\":\"actions:order complete\",\"value\":\"order complete\",\"match\":\"match\",\"key\":\"name\"}],\"operator\":\"AND\"},\"method\":\"count\",\"constraints-hash\":\"_1581614766\",\"render\":\"number_format(cell('Orders'), '#,##0')\"},{\"flags\":{\"scan_policy\":\"ONLY_MATCHING_ACTION\"},\"scope\":\"actions\",\"hide\":false,\"_format\":\"#,##0\",\"name\":\"Portins\",\"constraints\":{\"filters\":[{\"scope\":\"actions\",\"_uikey\":\"actions:order info - transfer\",\"value\":\"order info - transfer\",\"match\":\"match\",\"key\":\"name\"}],\"operator\":\"AND\"},\"method\":\"count\",\"constraints-hash\":\"_732810302\",\"render\":\"number_format(cell('Portins'), '#,##0')\"}],\"date_format\":\"yyyy-MM-dd\",\"group_by\":[{\"scope\":\"visits\",\"::\":\"_1595718630\",\"transforms\":[{\"params\":[\"yyyy-D\"],\"function\":\"date_format\"}],\"key\":\"time\"}],\"end_day\":\"2013-10-21\",\"offset\":0,\"order_by\":\"\",\"start_day\":\"2013-10-21\",\"render\":\"date_format(group_by(0) ,'yyyy-MM-dd')\"},\"offset\":0},\"population\":12218,\"rows\":[{\"cells\":[\"12,193\",\"2,539\",54,55],\"drill\":[{\"segments\":[{\"did\":{\"operator\":\"AND\",\"filters\":[{\"aggregation\":{\"scope\":\"actions\",\"value\":\"0\",\"method\":\"count\",\"match\":\"eq\"},\"action\":{\"operator\":\"AND\",\"filters\":[{\"scope\":\"actions\",\"value\":\"merged|_mrg|_merge\",\"match\":\"regexp\",\"key\":\"name\"}]},\"timeframe\":{\"to\":\"2013-10-21\",\"timezone\":\"EST5EDT\",\"method\":\"absolute\",\"from\":\"2013-10-21\"},\"visit\":{\"operator\":\"AND\",\"filters\":[]}}]}},{\"are\":{\"filters\":[],\"operator\":\"AND\"},\"did\":{\"filters\":[{\"aggregation\":{\"value\":1,\"method\":\"count\",\"match\":\"gte\"},\"action\":{\"filters\":[{\"scope\":\"actions\",\"value\":\"\",\"match\":\"contains\",\"key\":\"name\"}],\"operator\":\"AND\"},\"timeframe\":{\"to\":\"2013-10-21\",\"method\":\"absolute\",\"from\":\"2013-10-21\"},\"visit\":{\"filters\":[{\"scope\":\"visits\",\"value\":\"2013-294\",\"match\":\"match\",\"transforms\":[{\"params\":[\"yyyy-D\"],\"function\":\"date_format\"}],\"key\":\"time\"}],\"operator\":\"AND\"}}],\"operator\":\"AND\"}}],\"date_format\":\"yyyy-MM-dd\",\"end_day\":\"2013-10-21\",\"start_day\":\"2013-10-21\"},-1,-1,-1],\"src\":[12193,2539,54,55],\"k\":\"2013-10-21\",\"i\":\"2013-294\"}]}")

(print var)

(def j (json/read-str var))

(def i (get j "model"))
(def t (get j "total"))
(class t)
(def nums (get t "src"))

(keys i)
(get (first (get (get i "report") "columns")) "name")
(def labels (map #(str (get % "name"))
                 (seq (get (get i "report") "columns"))))


(first nums)
(doseq [num nums] [label labels]
  (prn label num))
