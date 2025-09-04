(ns com.hyperphor.way.demo.draggable
  (:require [reagent.core :as reagent]
            ["react-draggable" :default Draggable]))

(def draggable-adapter (reagent/adapt-react-class Draggable))

;;; That was easy
(defn view
  []
  [:div
   [draggable-adapter
    [:div "drag me baby"]]
   [draggable-adapter
    [:div "all over town"]]
   [draggable-adapter
    [:div "can't take these blues"]]
   [draggable-adapter
    [:div "they're dragging me down"]]])

;;; TODO various options, also recording and replaying positions
;;; see https://www.npmjs.com/package/react-draggable
