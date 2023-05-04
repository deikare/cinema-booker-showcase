#!/bin/bash

server_fqdn="localhost:8080"

curl "$server_fqdn/cinemas" | json_pp
echo ""

curl "$server_fqdn/movies" | json_pp
echo ""

curl "$server_fqdn/screenings" | json_pp

curl "$server_fqdn/rooms" | json_pp