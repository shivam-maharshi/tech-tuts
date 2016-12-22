from unittest import TestCase

'''
Testing unit test here

When python -m unittest test_unittest.py is ran from
CLI it will pass here.
'''

def func(x):
        return x + 1
    
class Test(TestCase):
    
    def setUp(self):
        return 0
     
    def test_answer(self):
        self.assertEqual(func(3), 4)
