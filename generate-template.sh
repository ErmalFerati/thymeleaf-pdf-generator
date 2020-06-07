if [ -z "$PORT" ]; then
  echo "Port not passed as an arguement. Using the default one (9010)..."
  PORT="9010"
fi

if [ -z "$URL" ]; then
  URL="http://localhost"
fi

if [ -z "$1" ]; then
  echo "ERROR: No template name specified. Exiting..." >&2
  return
else
  TEMPLATE_NAME=$1
fi

if [ ! -f "data.json" ]; then
  echo "ERROR: File does not exist. Exiting..." >&2
  return
fi

echo "Starting application..."
mvn spring-boot:run &

while [ "$(curl -s -o /dev/null -I -w '%{http_code}' "$URL":$PORT/health-check)" != 200 ]; do
  echo "Waiting for the application to run..."
  sleep 1
done

echo "Executing request to generate template $TEMPLATE_NAME"
curl --request POST \
  --header "Content-Type: application/json" \
  --data "$(cat data.json)" "$URL:$PORT/api/generate-template/$TEMPLATE_NAME" \
  --output "$TEMPLATE_NAME".pdf

kill -9 "$(lsof -t -i:$PORT)"

echo "Done"
