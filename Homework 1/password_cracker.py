from zipfile import ZipFile
import random
import string


# brute force
for i in range(0, 100000):
    N = random.randint(1,12)
    password = ''.join(random.SystemRandom().choice(string.ascii_uppercase + string.ascii_lowercase + string.digits) for _ in range(N))
    with ZipFile('KevinPorter.zip') as f:
        try:
            f.extractall(pwd=password)
        except RuntimeError:
            pass

# dictionary

