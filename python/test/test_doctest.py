
def square(x):
    """ Return the square of x.
    
    >>> square(2)
    4
    >>> square(-2)
    3
    
    The above is supposed to fail and when
    python test_doctest.py is run from CLI
    it will complain.
    """
    
    return x*x

if __name__ == '__main__':
    import doctest
    doctest.testmod()
