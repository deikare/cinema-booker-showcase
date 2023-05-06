#!/usr/bin/python3
import requests

url = "http://localhost:8080/reservations"

obj = {
    "screeningId": "f79acdfa-7e3b-44cd-afdd-94cfe80cde44",
    "name": "Xyzasd",
    "surname": "Xyzasd",
    "seats": {
        1: [
            {"number": 1, "type": "STANDARD"},
            {"number": 2, "type": "CHILD"},
            {"number": 3, "type": "STUDENT"},
        ],
        2: [
            {"number": 4, "type": "STANDARD"},
            {"number": 5, "type": "CHILD"},
            {"number": 6, "type": "STUDENT"},
        ]
    }
}

r = requests.post(url, json=obj)
print(r.json())
