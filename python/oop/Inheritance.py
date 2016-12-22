'''
Created on Mar 5, 2016

@author: shivam.maharshi
'''
from oop.Employee import Employee
from oop.Citizen import Citizen
from logging import Manager

class Manager(Employee, Citizen):
    " This is Manager class. A sub class of an Employee class."
    
    def __init__(self, name="Manager", salary=1000, level=1):
        print("In the manager constructor.")
        self.level = level
        setattr(self, "name", name)
        setattr(self, "salary", salary)
    
    def __del__(self):
        print("Collecting A class object.")
        
class TeamLead(Employee, Citizen):
    "This is a Team Leader class. A sub class of an Employee class."
    __secret = 0 # This is a secret/private field of the TL.
    
    
    def __init__(self, manager, name="TeamLead", salary=500):
        self.manager = manager
        self.__secret = 10
        setattr(self, "name", name)
        setattr(self, "salary", salary)
        
class HRManager(Manager):
    "This is my parent B class."
    
    def __init__(self, dept, name="HRManager", salary=1000, level=1):
        self.dept = dept
        setattr(self, "name", name)
        setattr(self, "salary", salary)
        setattr(self, "level", level)
        
# Lets check how inheritance work.

ctz = Citizen("USA")
emp = Employee("Uncle Sam")
hrMng = HRManager(name = "Cheap HR Manager", salary=250, dept = "Human Resource")
tl = TeamLead(manager=hrMng)

print(getattr(ctz, "country"))
print(getattr(emp, "name"), " : ", getattr(emp, "salary") )
print(getattr(hrMng, "level"))
print(getattr(tl, "manager"))

print("Citizen class dictionary: ", ctz.__dict__)
print("Employee class dictionary: ", emp.__dict__)
print("HR Manager class dictionary: ", hrMng.__dict__)
print("Team Lead class dictionary: ", tl.__dict__)
