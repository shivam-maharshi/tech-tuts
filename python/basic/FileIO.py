'''
Created on Mar 5, 2016

@author: shivam.maharshi
'''
from pip._vendor.distlib.compat import raw_input
from os.path import os

#str = raw_input("Enter your input: ");
#print("Received input is: ", str);

# input is same as raw_input but assumes that input is a valid Python expression.
# Hence this is equivalent to eval(raw_input())

#expressionOutput = input("Enter your input: ");
#print("Received input is: ", expressionOutput)

# Open a File
file = open(file = "text.txt", mode='wb', buffering=1)
print("Name of the title: ", file.name)
print("Closed or not: ", file.closed)
print("Opening mode: ", file.mode)
file.write(bytes("I am not a psychopath, I am a high functioning sociopath.\n", "UTF-8"))
file.close()

file = open(file = "text.txt", mode = "r", buffering=1)
print("Position: ", file.tell()) # Tell the position of the current seeking point.
data = file.read() # Read all bytes
file.close()
print("The read data is: ", data)
print("Closed or not: ", file.closed)

# Use the OS path library.
os.rename("text.txt", "newText.txt") # Rename the file name.
os.remove("newText.txt") # Remove the file with this file name.
os.mkdir("folder", mode=0o777)
os.chdir("folder"); # Changes the current directory with this.
os.mkdir("test") # Makes folder in folder with name test.

print("The current dir: ", os.getcwd()) # Prints the current directory path.
os.rmdir("folder");         # Removes the directory.











