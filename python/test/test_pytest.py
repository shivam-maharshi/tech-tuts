'''
Testing py test here which is known for its
non verbosity. Far less work than required for unittest.p

Run py.test test_pytest.py from CLI
'''

def func(x):
    return x+1;

def test_answer():
    assert func(3) == 4;
