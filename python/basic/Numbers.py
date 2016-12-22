'''
Created on Mar 4, 2016

@author: shivam.maharshi
'''
from math import ceil, fabs, log, sqrt, pi, e
from random import choice, random, shuffle, uniform

a = -5.5 * pi
b = 6.7 * e

print(abs(a))
print(ceil(a))
print(fabs(a))
print(log(b))
print(max(a,b))
print(min(a,b))
print(pow(a, b))
print(sqrt(b))

# Random number functions

lis = [1,2,3,"4",5,6,7,"hey"]

print(choice(lis))
print(random())
shuffle(lis)
print(lis)
print(uniform(1,10))







