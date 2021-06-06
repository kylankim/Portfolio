"""Lab 1: Expressions and Control Structures"""

# Coding Practice
def square(b):
    return b * b

def repeated(f, n, x):
    """Returns the result of composing f n times on x.

    >>> def square(x):
    ...     return x * x
    ...
    >>> repeated(square, 2, 3)  # square(square(3)), or 3 ** 4
    81
    >>> repeated(square, 1, 4)  # square(4)
    16
    >>> repeated(square, 6, 2)  # big number
    18446744073709551616
    >>> def opposite(b):
    ...     return not b
    ...
    >>> repeated(opposite, 4, True)
    True
    >>> repeated(opposite, 5, True)
    False
    >>> repeated(opposite, 631, 1)
    False
    >>> repeated(opposite, 3, 0)
    True
    """
    a = 1
    tot = x
    while a <= n:
        tot = f(tot)
        a+=1
    return tot

print (repeated(square,2,3))


def sum_digits(n):
    """Sum all the digits of n.

    >>> sum_digits(10) # 1 + 0 = 1
    1
    >>> sum_digits(4224) # 4 + 2 + 2 + 4 = 12
    12
    >>> sum_digits(1234567890)
    45
    """
    a = 0
    tot = 0
    while n >= 10:
        a = n % 10
        tot += a
        n = n //10
    tot = tot + n
    return tot

print (sum_digits(10))



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
    "*** YOUR CODE HERE ***"
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
