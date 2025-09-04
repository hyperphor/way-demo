(ns com.hyperphor.way.demo.draggable
  (:require [reagent.core :as reagent]
            ["react-draggable" :default Draggable]
            ["markdown-it" :as MarkdownIt]))

(def draggable-adapter (reagent/adapt-react-class Draggable))

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

;;; Draggable widget with title bar and markdown content
(defn draggable-widget 
  [{:keys [title content initial-x initial-y width height]}]
  [draggable-adapter 
   {:handle ".drag-handle"
    :defaultPosition {:x (or initial-x 0) :y (or initial-y 0)}}
   [:div.card {:style {:width (or width "300px")
                       :height (or height "auto")
                       :background "white"
                       :border "1px solid #ccc"
                       :border-radius "4px"
                       :box-shadow "0 2px 4px rgba(0,0,0,0.1)"
                       :font-family "system-ui, sans-serif"}}
    
    ;; Title bar (draggable handle)
    [:div.drag-handle {:style {:background "#f5f5f5"
                               :border-bottom "1px solid #ddd"
                               :padding "8px 12px"
                               :cursor "move"
                               :user-select "none"
                               :border-radius "4px 4px 0 0"
                               :font-weight "bold"}}
     title]
    
    ;; Content area
    [:div.content {:style {:padding "12px"
                           :overflow "auto"
                           :max-height (when height (str "calc(" height " - 40px)"))}}
     [markdown-content content]]]])

;;; Demo view with multiple widgets
(defn view
  []
  [:div {:style {:position "relative" :height "100vh" :background "#f8f9fa"}}
   
   [draggable-widget 
    {:title "Welcome Widget"
     :content "# Hello World\n\nThis is a **draggable widget** with markdown support!\n\n- Drag me by the title bar\n- I support basic markdown\n- Pretty neat, right?"
     :initial-x 50
     :initial-y 50
     :width "350px"}]
   
   [draggable-widget
    {:title "Code Example" 
     :content "## ClojureScript Code\n\n```clojure\n(defn hello [name]\n  (str \"Hello, \" name \"!\"))\n```\n\n### Features\n- Syntax highlighting (coming soon)\n- Live examples\n- Interactive demos"
     :initial-x 420
     :initial-y 80
     :width "400px"}]
   
   [draggable-widget
    {:title "Todo List"
     :content "# My Tasks\n\n- Implement drag handles\n- Add markdown support\n- Style the widgets\n- Add resize functionality\n- Save widget positions\n\n## Done ✓\n- Basic dragging\n- Title bars\n- Multiple widgets"
     :initial-x 100
     :initial-y 300
     :width "280px"
     :height "250px"}]
   
   [draggable-widget
    {:title "Quick Note"
     :content "Just a simple note that you can drag around!\n\nClick and drag the title bar to move me."
     :initial-x 500
     :initial-y 350
     :width "250px"}]])

;;; TODO various options, also recording and replaying positions
;;; see https://www.npmjs.com/package/react-draggable
