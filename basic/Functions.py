'''
Created on Mar 4, 2016

@author: shivam.maharshi
'''

# Functions in Python

# Arguments are pass by reference. A change in function is reflected in calling function.
def customPrint(string):
    print("Custom Printing: " + string)
    return 1

def add(a, *varTuple, b=100):
    a += 2 * b
    for var in varTuple:
        a += var
    return a

customPrint("Aye man! I want my bird.")
a = 1
b = 2
print(add(a, b))        # Required Arguments : Order is important.
print(add(b=10, a=1))   # Keyword Arguments : Order does not matter here.
print(add(a))           # Default Arguments : Default arguments will be used.
print(add(a, 1, 2, 3, b=1000))  # Variable Arguments : Multiple number of arguments passed.


# Lambda Functions

s = lambda arg1, arg2: arg1 + arg2; # Can contain only a single statement.

print("The sum using lambda is: ", s(1, 2))