#!/usr/bin/python3
import requests

url = "http://localhost:8080/reservations"

obj = {
    "screeningId": "e9a5d6c3-1711-4c2f-8ce6-5240b5ae86d9",
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
