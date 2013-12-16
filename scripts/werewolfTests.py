import requests
import json

#ROOT = "http://murmuring-cliffs-5802.herokuapp.com/"
ROOT = "http://localhost:8080/cs420/"
ADMIN_AUTH = ("erroday","123456")

def doGet(url,auth):
    r = requests.get(url,auth=auth)
    if not r.ok:
        r.raise_for_status()
    elif r.json()["status"] != 0:
        e = requests.exceptions.HTTPError(r.json()["object"])
        raise e
    else:
        print(r.json()["object"])

def doGetWithParams(url,params,auth):
    r = requests.get(url,data=params,auth=auth)
    if not r.ok:
        r.raise_for_status()
    elif r.json()["status"] != 0:
        e = requests.exceptions.HTTPError(r.json()["object"])
        raise e
    else:
        print(r.json()["object"])

def doPost(url,params,auth):
    r = requests.post(url,data=params,auth=auth)
    if not r.ok:
        r.raise_for_status()
    elif r.json()["status"] != 0:
        e = requests.exceptions.HTTPError(r.json()["status"])
        raise e
    else:
        print(r.json()["object"])

def registerUser(firstName,lastName,username,password,lat,lng):
    params = {
        "firstName":firstName,
        "lastName":lastName,
        "emailHandle":username,
        "password":password,
        "lat":lat,
        "lng":lng
        }
    doPost(ROOT+"register",params,ADMIN_AUTH)

def shareLocation(lat,lng,auth):
    params = {
        "lat":lat,
        "lng":lng
        }
    doPost(ROOT+"user/me/shareLocation",params,auth)

def getNearbyToUser(lat,lng,auth):
    params = {
        "lat":lat,
        "lng":lng
    }
    doPost(ROOT+"user/me/nearby",params,auth)

def makeConnectionRequest(emailHandle,auth):
    params = {
        "emailHandle":emailHandle
    }
    doPost(ROOT+"user/me/makeRequest",params,auth)

def acceptConnectionRequest(emailHandle,auth):
    params = {
        "emailHandle":emailHandle
    }
    doPost(ROOT+"user/me/acceptRequest",params,auth)

def stopShare(auth):
    doPost(ROOT+"user/me/stopSharingLocation",None,auth)

def getUser(auth):
    doGet(ROOT+"user/me",auth)

def getAdmin():
    getUser(ADMIN_AUTH)

def addUserPrompt():
    raw = ""
    while raw != "n":
        firstName = input("First Name: ")
        lastName = input("Last Name: ")
        username = input("Username: ")
        password = input("Password: ")
        confirm = input("Confirm password: ")
        if (password != confirm):
            raise Exception("Passwords do not match")
        lat = float(input("Latitude: "))
        lng = float(input("Longitude: "))
        try:
            registerUser(firstName, lastName, username,password,lat,lng)
            print("Added user "+username)
        except Exception as e:
            print(str(e))
        raw = input("Continue (y/n)? ") 

def hardCodedLocationUpdates():
    shareLocation(37.270215,-76.709800,("erroday","123456"))
    shareLocation(37.270215,-76.709800,("camckenna","123456"))
    shareLocation(37.270215,-76.709850,("jlchen","123456"))
    shareLocation(37.270190,-76.709875,("bgreynoso","123456"))
    shareLocation(37.270215,-76.709900,("jnvelascomadde","123456"))
    shareLocation(37.270295,-76.709925,("aablohm","123456"))
    stopShare(("bgreynoso","123456"))


def makeFriends(toAuth,fromAuth):
    makeConnectionRequest(toAuth[0],fromAuth)
    acceptConnectionRequest(fromAuth[0],toAuth)
    
hardCodedLocationUpdates()
makeFriends(("jlchen","123456"),("camckenna","123456"))
makeFriends(("camckenna","123456"),("erroday","123456"))
