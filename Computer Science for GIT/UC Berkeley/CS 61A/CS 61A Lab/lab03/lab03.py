""" Lab 3: Recursion and Midterm Review """

def gcd(a, b):
    """Returns the greatest common divisor of a and b.
    Should be implemented using recursion.

    >>> gcd(34, 19)
    1
    >>> gcd(39, 91)
    13
    >>> gcd(20, 30)
    10
    >>> gcd(40, 40)
    40
    """
    "*** YOUR CODE HERE ***"
    while not a == b:
        a, b = max(a,b)-min(a,b) ,min(a,b)
    return a

def hailstone(n):
    """Print out the hailstone sequence starting at n, and return the
    number of elements in the sequence.

    >>> a = hailstone(10)
    10
    5
    16
    8
    4
    2
    1
    >>> a
    7
    """
    "*** YOUR CODE HERE ***"
    a = 0
    print(n)
    a += 1
    while n > 1:
        n = n // 2
        a += 1
        print(n)
        if n == 5:
            n = n * 3 + 1
            print(n)
            a += 1
    return a

