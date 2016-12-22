'''
Created on May 22, 2016

Given two strings, find out the minimum edit distance between them using either:
INSERT,
DELETE or
REPLACE
operation. Given the cost of each is 1.

Link: http://www.geeksforgeeks.org/dynamic-programming-set-5-edit-distance/

@author: shivam.maharshi
'''

def naiveGet(a, b, m, n):
    if (m==0 or n==0):
        return max(m, n);
    elif (a[m] == b[n]):
        return naiveGet(a, b, m-1, n-1);
    else:
        return min(naiveGet(a, b, m-1, n), naiveGet(a, b, m, n-1), naiveGet(a, b, m-1, n-1)) + 1;
    
a = "geek";
b = "gesek";

print(naiveGet(a, b, len(a)-1, len(b)-1));
    
def get(a, b, m, n, dp):
    if(dp[m][n] is not -1):
        return dp[m][n];
    elif(m==0 or n==0):
        dp[m][n] = max(m, n);
    elif(a[m] == b[n]):
        dp[m][n] = get(a, b, m-1, n-1, dp);
    else:
        dp[m][n] = min(get(a, b, m-1, n-1, dp), get(a, b, m-1, n, dp), get(a, b, m, n-1, dp)) + 1;
    return dp[m][n];
            
dp = [[0 for y in range(len(b))] for x in range(len(a))];

for x in range(len(a)):
    for y in range(len(b)):
        dp[x][y] = -1;
        
print(get(a, b, len(a)-1, len(b)-1, dp));
    
    
    