'''
Created on Mar 4, 2016

@author: shivam.maharshi
'''

count = 0

# While Loop
while(count < 10) :
    print("The count is: " + str(count))
    print("The count is:", count)
    count+=1
    
print("The While End")    
    
# For loop
for letter in "Python" :
    print("The letter is: " + letter)
    
print("The For End")    

fruits = ["banana", "apple", "mango", "grape", "orange"]

for index in range(len(fruits)):
    print("The current fruit is: " + fruits[index])
    
print("The Fruit For End")

for index in range(1,3):
    print("The current fruit is:" +fruits[index])
    
    