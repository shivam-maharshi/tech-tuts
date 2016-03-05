'''
Created on Mar 5, 2016

@author: shivam.maharshi
'''

class Employee:
    '''
    Base class for Employees
    '''
    empCount = 0;   # This is a class variable shared by the whole class.


    def __init__(self, name, salary):
        '''
        Constructor
        '''
        # These are instance variables for individual objects.
        print("In the Employee constructor.")
        self.name = name
        self.salary = salary
        Employee.empCount += 1
        
    def __del__(self):
        class_name = self.__class__.__name__
        print (class_name, "destroyed")
        
    def displayCount(self):
        print("The total number of employees are: ", Employee.empCount)
        
    def displayEmployee(self):
        print("Name: ", self.name, ", Salary: ", self.salary)
        
        