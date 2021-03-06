'''
Created on Mar 4, 2016

@author: shivam.maharshi
'''

# List in Python can be hetrogenous type.

aList = ['abc', 1234, "cde", 2.4, 546]
bList = [1, 2]

print(aList[0])
print(aList[2:4])
print (aList + bList)
# Update list
aList[2] = 1000
aList.append(999999)
print (aList)

# Tuples size cannot be changed - read only lists.

aTuple = ( "abc", 12, 4.5, "bcde")
bTuple = ("a", 98, 33, 'a')

print(aTuple[0])
print(bTuple*2)
print(aTuple[1:3])
print(aTuple + bTuple)

# Dictionary is equivalent of Maps in python

dic = {}
# The key in dictionary must be immutable and hashable.
dic['key'] = "This is my value."
dic[2] = "This is integer value."

print (dic['key'])
print (dic.keys())
print (dic.values())
del dic[2]
print (dic)
dic.clear()
print(dic)
