(ns com.hyperphor.way.demo.violin
  (:require [com.hyperphor.way.violin :as vi]
            [com.hyperphor.way.form :as form]
            [com.hyperphor.way.feeds :as f]
            [re-frame.core :as rf]
            )
  )

(defn ui
  []
  (let [data (f/from-url "https://way-demo-4ed0361a3a3b.herokuapp.com/data/cd133.tsv")
        vertical? @(rf/subscribe [:form-field-value [:violin :vertical?]])]
    [:div
     [:div.wform
      (form/form-field-row
       {:type :boolean :label "vertical?" :path [:violin :vertical?]}
       {})]
     [vi/violin data :final_diagnosis :feature_value {:vertical? vertical?
                                                      :patches {:axes [{:title "CD133"} nil]}}]
     ])) 
