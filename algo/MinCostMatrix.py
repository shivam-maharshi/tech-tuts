'''
Created on May 22, 2016

Given a matrix with traversal cost on the nodes, find the lowest cost of travelling from
point 0,0 to m,n.

Link: http://www.geeksforgeeks.org/dynamic-programming-set-6-min-cost-path/

@author: shivam.maharshi
'''

def naiveGet(a, x, y, dx, dy, val):
    if(x > dx or y > dy):
        return 111111111;
    val = val + a[x][y];
    if(x==dx and y==dy):
        return val;
    else:
        return min(naiveGet(a, x+1, y, dx, dy, val), naiveGet(a, x, y+1, dx, dy, val), naiveGet(a, x+1, y+1, dx, dy, val));

def get(a, x, y, dx, dy, val, dp):
    if(x > dx or y > dy):
        return 111111111;
    elif(dp[x][y] is -1):
        val = val + a[x][y];
        if(x==dx and y==dy):
            dp[x][y] = val;
        else:
            dp[x][y] = min(naiveGet(a, x+1, y, dx, dy, val), naiveGet(a, x, y+1, dx, dy, val), naiveGet(a, x+1, y+1, dx, dy, val));
    return dp[x][y];

a = [];
a = [[0 for i in range(3)] for j in range(3)];
a[0][0] = 1;
a[0][1] = 2;
a[0][2] = 3;
a[1][0] = 4;
a[1][1] = 8;
a[1][2] = 2;
a[2][0] = 1;
a[2][1] = 5;
a[2][2] = 3;

print(naiveGet(a, 0, 0, 2, 2, 0));

dp = [[0 for i in range (3)] for j in range(3)];

for i in range (3):
    for j in range (3):
        dp[i][j] = -1;   
     
print(get(a, 0, 0, 2, 2, 0, dp));
