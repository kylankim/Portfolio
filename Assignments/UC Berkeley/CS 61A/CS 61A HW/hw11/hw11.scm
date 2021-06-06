(define (find s predicate)
  (cond
    ((equal? s nil) False)
    ((predicate (car s)) (car s))
    (else (find (cdr-stream s) predicate))
  )
)

(define (scale-stream s k)
  (if (equal? s nil) nil
     (cons-stream (* k (car s))
     (scale-stream (cdr-stream s) k))
  )
)

(define (has-cycle s)
   (define (check a b)
   	   (cond
   	   	   ((null? a) #f)
   	   	   ((eq? a b) #t)
   	   	   (else (check (cdr-stream a) b))))
   (if (null? s) #f
   	   (check (cdr-stream s) s)
    )
)


(define (has-cycle-constant s)
  'YOUR-CODE-HERE
)
