###curl samples

###get all users
curl -s http://localhost:8080/topjava/rest/admin/users

### get user id 100000
curl -s http://localhost:8080/topjava/rest/admin/users/100000

#### get all meals

curl -s http://localhost:8080/topjava/rest/profile/meals

### get meals 100007

curl -s http://localhost:8080/topjava/rest/profile/meals/100007

#### filter meals

curl -s "http://localhost:8080/topjava/rest/profile/meals/filter?startDate=2020-01-30&startTime=07:00:00&endDate=2020-01-31&endTime=11:00:00"

#### get meals not found

curl -s -v http://localhost:8080/topjava/rest/profile/meals/10000

#### delete meals 

curl -s -X DELETE "http://localhost:8080/topjava/rest/profile/meals/100008"

#### create meals

curl -s -X POST -d '{"dateTime":"2020-11-25T20:16","description":"add Meal","calories":800}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/rest/profile/meals


### update meals

curl -s -X PUT -d '{"dateTime":"2020-01-30T07:00", "description":"Updated breakfast", "calories":200}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/profile/meals/100007