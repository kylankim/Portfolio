def double_eights(n):
    """Return true if n has two eights in a row.
    >>> double_eights(8)
    False
    >>> double_eights(88)
    True
    >>> double_eights(880088)
    True
    >>> double_eights(12345)
    False
    >>> double_eights(80808080)
    False
    """
    a = 0
    if n > 10:
        while n > 10:
            if n % 100 == 88:
                a = True
                n = 0
                print(a)
                break
                return

            else:

                n = n // 10


                if n < 10:
                    a = False
                    print(a)


    else:
        a = False
        print(a)
