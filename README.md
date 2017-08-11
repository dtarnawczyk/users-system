# users-system
System for managing users

### UI

Service available on (demo user: firstUser password: test4321):
```sh
http://localhost:8080
```

##### API authentication based on OAUTH2:

For functional styled defined routs calls:
  1. Asking for token:
  
```sh
curl -ufirstUser:test4321 -X POST \
  'http://localhost:8080/customers-system/oauth/token?grant_type=password&username=firstUser&password=test4321' \
  -H 'content-type: application/json'
```
  2. Retrieving resource with token. 
  Example: getting information about user with ID 1000
```sh
curl http://localhost:8080/customers-system/api/customers/1000 \
  -H 'authorization: bearer <token>' \
  -H 'content-type: application/json'
```

