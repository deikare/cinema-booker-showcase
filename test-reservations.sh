#!/bin/bash

server_address="http://localhost:8080"

prompt () {
  line_width=$(tput cols)
  for arg in "$@"; do
    case $arg in
      -s|--small)
        line_width=$((line_width / 2))
        shift
        ;;
    esac
  done

  horizontal_line=$(printf '%.0s-' $(seq 1 "$line_width"))
  formatted_message="$horizontal_line\n$(echo "$1" | fold -w "$line_width")\n$horizontal_line"
  echo -e "$formatted_message"
}

prompt "Step 2"
echo ""

prompt -s "A: wrong time interval, start one year ago"
start=$(date -u -d "+1 year" +'%Y-%m-%dT%H:%M:%SZ')
curl "$server_address/movies?start=$start"
echo -e "\n"


prompt -s "B: wrong time interval, end is epoch"
utc_time="00:00:00"
end="1970-01-01T${utc_time}Z"
curl "$server_address/movies?end=$end"
echo -e "\n"

prompt -s "C: proper request - start one hour ago"
start=$(date -u -d '2 hour ago' +'%Y-%m-%dT%H:%M:%SZ')
curl "$server_address/movies?start=$start"
echo -e "\n"

page=1
size=2
prompt -s "D: proper request - same start, page=$page, size=$size"
start=$(date -u -d '2 hour ago' +'%Y-%m-%dT%H:%M:%SZ')
curl "$server_address/movies?start=$start&page=$page&size=$size"
echo -e "\n"

prompt -s "E: proper request - no time bounds"
response=$(curl "$server_address/movies" -s)
echo "$response"
echo -e "\n"

prompt "Step 4"
echo ""

prompt -s "Screening info:"
size=$(echo "$response" | jq -r '.page.totalElements') #get count of all screenings
response=$(curl "$server_address/movies?size=$size" -s ) #
random_screening_link=$(echo "$response" | jq -r '._embedded.movieWithScreeningsModels[].screenings[]."_links".self.href' | shuf -n 1)
response=$(curl "$random_screening_link" -s)
echo -e "\n"


prompt "Step 5"
echo ""

prompt -s "A: wrong name format"
screening_id=$(basename "$random_screening_link")
largest_row=$(echo "$response" | jq '.seatRows | max_by(.row) | .row')
smallest_row=$(echo "$response" | jq '.seatRows | min_by(.row) | .row')
request="{
  \"screeningId\": \"$screening_id\",
  \"name\": \"Ab123\",
  \"surname\": \"Wąchocki-Mińkowskił\",
  \"seats\": {
    \"$largest_row\": {
      \"first\": 1,
      \"types\": [\"ADULT\", \"CHILD\", \"STUDENT\"]
    },
    \"$smallest_row\": {
      \"first\": 3,
      \"types\": [\"STUDENT\", \"CHILD\", \"ADULT\"]
    }
  }
}
"

url="$server_address/reservations"
curl -X POST -H "Content-Type: application/json" --data "$request" "$url"