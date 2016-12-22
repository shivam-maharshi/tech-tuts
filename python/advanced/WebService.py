'''
Created on Mar 5, 2016

@author: shivam.maharshi
'''

#!/Python34/python 

# Import modules for CGI handling 
import cgi, cgitb 

# Create instance of FieldStorage 
form = cgi.FieldStorage() 

# Get data from fields
name = form.getvalue('name')

print ("Content-type:text/html\r\n\r\n")
print ('<html><head>')
print ('<title>Hello Word - First CGI Program</title>')
print ('</head><body>')
print ('<h2>Hello %s! This is my first CGI program</h2>' % (name))
print ('</body></html>')