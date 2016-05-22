'''
Created on May 22, 2016

@author: shivam.maharshi
'''

def naive_lcs(a, b, m, n):
    if(m==0 or n==0):
        return 0;
    elif(a[m-1] == b[n-1]):
        return naive_lcs(a, b, m-1, n-1) + 1;
    else:
        return max(naive_lcs(a, b, m-1, n), naive_lcs(a, b, m, n-1));
 
'''   
def lcs(a, b, m, n, dp):
    if(m==0 or n==0):
        return 0;
    elif(dp[m][n] is not 0):
        return dp[m][n];
    elif(a[m-1] == b[n-1]):
        dp[m][n] = lcs(a, b, m-1, n-1, dp) + 1;
        return dp[m][n];
    else:
        dp[m][n] = max(lcs(a, b, m-1, n, dp), lcs(a, b, m, n-1, dp));
        return dp[m][n];
'''    
    
def lcs_new(a, b, m, n):
    dp = [[0 for y in range(n)] for x in range(m)];
    for x in range(m):
        for y in range(n):
            if(x==0 or y==0):
                dp[x][y]=0;
            elif(a[x-1] == b[y-1]):
                dp[x][y] = dp[x-1][y-1]+1;
            else:
                dp[x][y] = max(dp[x-1][y], dp[x][y-1]);
    return dp[m-1][n-1];

X = "AGGTAB";
Y = "GXTXAYB";

#print(naive_lcs(X, Y, len(X), len(Y)));

print(lcs_new(X, Y, len(X)+1, len(Y)+1));
'''
dp = [[0 for y in range(len(Y))] for x in range(len(X))];

for x in range(len(X)):
    for y in range(len(Y)):
        print(dp[x][y], end = ""),
    print("");

print(lcs(X, Y, len(X)-1, len(Y)-1, dp));
'''