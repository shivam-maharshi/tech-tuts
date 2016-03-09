'''
Created on Mar 8, 2016

@author: shivam.maharshi
'''
import socket

s = socket.socket()
host = socket.gethostname()
port = 12345
s.bind((host, port))
s.listen(5)

while True:
    c, addr = s.accept()
    print("Got connection from address: ", addr)
    # Convert the string to bytes before sending over socket channel.
    c.send(bytes("Thank you for connecting.", 'UTF-8')) 
    c.close()
    