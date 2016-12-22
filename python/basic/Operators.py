'''
Created on Mar 4, 2016

@author: shivam.maharshi
'''

# Arithmetic operations

print(10*2)
print(10**2)
print(11/2)
print(11.0/2)
print(11//2)

# Comparison operations - compares value.

b = 100
print(100 == b)
print(10 <= b)
print(100 != b)
print((10 == b) == False)

# Bitwise operations

a = 7
b = 8

print(a&b)  # And
print(a|b)  # Or
print(a^b)  # Xor
print(~a)   # Negation - One's complement

# Membership operations - checks for value.

tup = (1,2,8)

print(8 in tup)
print(7 in tup)
print(7 not in tup)

# Identity operations - checks for the actual object reference.
c = a

print(a is b)
print(c is a)
print(c is not a)
