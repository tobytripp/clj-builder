(require 'boot.repl)
(swap! boot.repl/*default-dependencies*
       concat '[[cider/cider-nrepl "0.18.0-SNAPSHOT"]])

(swap! boot.repl/*default-middleware*
       concat '[cider.nrepl/cider-middleware])
