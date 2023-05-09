#!/usr/bin/python3
import requests

url = "http://localhost:8080/reservations"

obj = {
    "screeningId": "f79acdfa-7e3b-44cd-afdd-94cfe80cde44",
    "name": "Rafał",
    "surname": "Wąchocki-Mińkowskił",
    "seats": {
        1: {
            "start": 1,
            "end": 3,
            "types": ["STANDARD", "CHILD", "STUDENT"]
        },
        2: {
            "start": 4,
            "end": 6,
            "types": ["STUDENT", "CHILD", "STANDARD"]
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
print(r.json())
