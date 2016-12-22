'''
Created on Mar 4, 2016

@author: shivam.maharshi
'''
greetings = "Hello "
name = "Shivam Maharshi"
print (greetings + name[0:6] + ",")
print("We would like to refer to you as Mr. " + name[7:16])
var = "\nThis is a message to %s from the %s community."
print( var % ("Mr. " + name[7:16], "Python"))
del var

bigData = """This is a big data that can
reside in separate lines and still be referred
by different part of the code. Believe me. This
is the proof."""

print(bigData)