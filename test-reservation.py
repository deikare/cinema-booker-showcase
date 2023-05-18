#!/usr/bin/python3
import requests

url = "http://localhost:8080/reservations"

obj = {
    "screeningId": "034412eb-ae2e-4a50-93dd-e2ab21b4beb7",
    "name": "Rafał",
    "surname": "Wąchocki-Mińkowskił",
    "seats": {
        1: {
            "first": 1,
            "types": ["ADULT", "CHILD", "STUDENT"]
        },
        2: {
            "first": 4,
            "types": ["STUDENT", "CHILD", "ADULT"]
        }
    }
}

# obj = {
#     "screeningId": "f79acdfa-7e3b-44cd-afdd-94cfe80cde44",
#     "name": "Xyzasd",
#     "surname": "Xyzasd",
#     "seats": {
#         1: []
#     }
# }

r = requests.post(url, json=obj)
print(r.text)
