"""
Kevin Porter
CEG-4750 Homework 1

Includes a brute-force password-cracking method and a dictionary-based
password cracking method.

This is very slow, by nature. Also note that I discovered after coding this
that Python is unable to decrypt AES encrypted zip files, so this actually
is ineffective, but I wanted to point out the work I did.

At the last minute I added a line that should work given that the user is
on Windows (I included 7-Zip).
"""


from subprocess import call
from threading import Thread
from zipfile import ZipFile
import multiprocessing
import os
import platform
import random
import string

def unzip(password):
    with ZipFile('KevinPorter.zip') as f:
        try:
            if 'Windows' in platform.platform() or \
            'windows' in platform.platform():
                call('7za.exe e KevinPorter.zip p=' + password,
                     stdout=open(os.devnull, 'wb'))
            f.extractall(pwd=password)
            print 'Success! The password is: ' + password
            quit()
        except RuntimeError:
            pass

def unzip_list(l):
    for i in l:
        for j in xrange(10):
            unzip(i + str(j))

threads = multiprocessing.cpu_count()

def brute_force(high):
    """
    A brute force attack.
    @author Kevin Porter
    """
    for i in range(0, high):
        N = random.randint(4,12)
        password = ''.join(random.SystemRandom().choice(string.ascii_uppercase + string.ascii_lowercase + string.digits) for _ in range(N))
        unzip(password)

# make use of multithreaded machines to be more efficient
for i in range(0, threads):
    t = Thread(target=brute_force, args=(1000000,))
    t.start()

def dictionary_attack():
    """
    A dictionary-based attack.
    @author Kevin Porter
    """
    if not os.path.isfile('blink.txt'):
        print('ERROR: Download and place blink.txt in this script\'s ' +
              'directory.')
        quit()
    dictionary = []
    with open('blink.txt', 'r') as f:
        for line in f:
            for word in line.split():
                dictionary.append(word)
    # split the dict into equal pieces for each thread
    dict_list = [dictionary[i::threads] for i in xrange(threads) ]
    # again, make use of multithreading
    for i in range(0, threads):
        t = Thread(target=unzip_list, args=(dict_list[i], ))
        t.start()

dictionary_attack()
