'''
Created on Mar 5, 2016

@author: shivam.maharshi
'''
from oop.Employee import Employee

def createEmployeeObjects(name="Shivam", salary=10):
    emp1 = Employee(name = name, salary=salary)
    print("The emp count is: ", emp1.displayCount())
    return emp1
    
# An object's reference count increases when it is assigned a new name or placed in a container (list, tuple, or dictionary).
createdEmp = createEmployeeObjects("Sam", 100)
print(createdEmp.displayEmployee())
print("Class dictionary: ", createdEmp.__dict__)
print("Class documentation: ", createdEmp.__doc__)
print("Module name: ", createdEmp.__module__)

# We can add attributes to Employee object in runtime using implicit operations.
createdEmp.age = 10 # Now age has been added to createdEmp object.
print("Age of the employee: ", createdEmp.age)

# Use implicit methods on class attributes.
print("Is Age present in Employee class? - ", hasattr(createdEmp, "age"))
print("Value of Name attribute in Employee class: ", getattr(createdEmp, "name"))
print("Set the value of salary in Employee class: ", setattr(createdEmp, "name", "Sam Maharshi"))
print("Delete the age attribute. We don't need it. ", delattr(createdEmp, "age"))
createdEmp.displayEmployee()
# The object's reference count decreases when it's deleted with del, its reference is reassigned, or its reference goes out of scope.
# Garbage Collection: When an object's reference count reaches zero, Python collects it automatically.
# __del__ method is invoked when an object is Garbage collected.
del createdEmp