# List in Python can be hetrogenous type.

aList = ['abc', 1234, "cde", 2.4, 546]
bList = [1, 2]

print(aList[0])
print(aList[2:4])
print (aList + bList)
# Update list
aList[2] = 1000
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
dic['key'] = "This is my value."
dic[2] = "This is integer value."

print (dic['key'])
print (dic.keys())
print (dic.values())

