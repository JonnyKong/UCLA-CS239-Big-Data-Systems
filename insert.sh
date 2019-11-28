#!/bin/bash

curl -H 'Content-Type: application/json' \
    -X POST http://127.0.0.1:5984/baseball \
    -d '{"key": "value"}'