(ns com.hyperphor.way.demo.draggable
  (:require
   [re-frame.core :as rf]
   [org.candelbio.multitool.core :as u]
   [com.hyperphor.way.form :as f]
   [com.hyperphor.way.draggable :as d]
   ["markdown-it" :as MarkdownIt]
   ))

;; Initialize markdown-it with options
(def md (MarkdownIt. #js {:html true
                          :linkify true
                          :typographer true}))

;; Convert markdown to HTML and render safely
(defn markdown->html [text]
  (.render md text))

;; Component to render markdown as HTML
(defn markdown-content [text]
  [:div {:dangerouslySetInnerHTML {:__html (markdown->html text)}}])


;; Layout control panel
(defn layout-controls []
  (let [layout-name @(rf/subscribe [:form-field-value [:draggable :layout]])
        current-layouts @(rf/subscribe [:draggable/saved-layouts])]
    [:div.layout-controls {:style {:position "fixed"
                                   :top "10px"
                                   :right "10px"
                                   :background "white"
                                   :padding "15px"
                                   :border-radius "8px"
                                   :box-shadow "0 2px 8px rgba(0,0,0,0.15)"
                                   :z-index "1000"
                                   :min-width "200px"}}
     [:h5 {:style {:margin "0 0 10px 0" :font-size "14px"}} "Layout Controls"]
     
     ;; Save new layout
     [:div.hstack {:style {:margin-bottom "10px"}}
      [f/form-field {:id :layout :path [:draggable :layout] :label "Layout name"}]
      [:button {:on-click #(when true 
                             (rf/dispatch [:draggable/save-layout layout-name])
                             (rf/dispatch [:set-form-field-value [:draggable :layout] ""]))
                :style {:background "#007bff" :color "white" :border "none"
                        :padding "5px 10px" :border-radius "4px" :cursor "pointer"}}
       "Save"]]
     
     ;; Load existing layouts
     (when (seq current-layouts)
       [:div
        [:h6 {:style {:margin "10px 0 5px 0" :font-size "12px"}} "Saved Layouts:"]
        [:div
         (for [layout-key (keys current-layouts)]
           ^{:key layout-key}
           [:div {:style {:display "flex" :margin-bottom "3px"}}
            [:button {:on-click #(rf/dispatch [:draggable/load-layout (get current-layouts layout-key)])
                      :style {:flex "1" :border "none"
                              :padding "3px 8px" :border-radius "3px" :cursor "pointer"
                              :margin-right "5px" :font-size "11px"}}
             (name layout-key)]
            [:button {:on-click #(rf/dispatch [:draggable/delete-layout layout-key])
                      :style {:border "none"
                              :padding "3px 6px" :border-radius "3px" :cursor "pointer"
                              :font-size "11px"}}
             "×"]])]])]))

;;; Demo view with multiple widgets
(defn view
  []
  [:div {:style {:position "relative" :height "100vh" :background "#f8f9fa"}}
   
   ;; Layout controls
   [layout-controls]
   
   [d/draggable-widget 
    {:id "welcome"
     :title "Welcome Widget"
     :content [markdown-content
               "# Hello World\n\nThis is a **draggable widget** with markdown support!\n\n- Drag me by the title bar\n- I support basic markdown\n- Pretty neat, right?\n\n**Positions are now saved automatically!**"]
     :initial-x 50
     :initial-y 50
     :width "350px"}]
   
   [d/draggable-widget
    {:id "code"
     :title "Code Example" 
     :content [markdown-content
               "## ClojureScript Code\n\n```clojure\n(defn hello [name]\n  (str \"Hello, \" name \"!\"))\n```\n\n### Features\n- Syntax highlighting (coming soon)\n- Live examples\n- Interactive demos\n- **Position recording & replay!**"]
     :initial-x 420
     :initial-y 80
     :width "400px"}]
   
   [d/draggable-widget
    {:id "todo"
     :title "Todo List"
     :content [markdown-content
               "# My Tasks\n\n- ✓ Implement drag handles\n- ✓ Add markdown support\n- ✓ Style the widgets\n- ✓ **Record widget positions**\n- ✓ **Replay saved layouts**\n- Add resize functionality\n\n## Done ✓\n- Basic dragging\n- Title bars\n- Multiple widgets\n- **Position persistence!**"]
     :initial-x 100
     :initial-y 300
     :width "280px"
     :height "280px"}]
   
   [d/draggable-widget
    {:id "note"
     :title "Quick Note"
     :content [markdown-content
               "Just a simple note that you can drag around!\n\n**Your position will be remembered!**\n\nTry:\n1. Move me around\n2. Save a layout\n3. Refresh the page\n4. Load the layout"]
     :initial-x 500
     :initial-y 350
     :width "250px"}]])

;;; TODO various options, also recording and replaying positions
;;; see https://www.npmjs.com/package/react-draggable
