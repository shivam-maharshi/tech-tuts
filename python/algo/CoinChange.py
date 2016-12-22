'''
Created on May 23, 2016

Given a value an denominators, find out the different ways to achieve the final sum.
Different combinations are not counted.

Link: http://www.geeksforgeeks.org/dynamic-programming-set-7-coin-change/

@author: shivam.maharshi
'''

def getNaive(s, val, index):
    if(val==0):
        return 1;
    elif(val<0):
        return 0;
    elif(index<0):
        return 0;
    else:
        return getNaive(s, val-s[index], index) + getNaive(s, val, index-1);
        # Include the index element at least once in the first case.
        # Do not include the index element even once too.
        
s = [3,2,1]        
print(getNaive(s, 4, 2));

