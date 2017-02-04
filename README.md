# lambda-gyazo-s3

AWS Lambda function to provide a Gyazo server clone with S3 backend using Amazon API Gateway.

## Build

```bash
$ gradle jar
```

## Test

```bash
# Request to real Gyazo
$ UPLOAD=~/Pictures/a.png rake

# Request to API gateway
$ UPLOAD=~/Pictures/a.png API_ID= rake
```

## Specification
Implemented to work as [Gyazo server](https://github.com/gyazo/Gyazo/blob/2d72acbdca855d96f1ab01d84497361512428a62/Server/upload.cgi)
for [Gyazo client](https://github.com/gyazo/Gyazo/blob/2d72acbdca855d96f1ab01d84497361512428a62/Gyazo/script).

### Status: 200

```
> POST /upload.cgi HTTP/1.1
> Host: upload.gyazo.com
> Accept: */*
> Content-Length: 335996
> Content-type: multipart/form-data; boundary=----BOUNDARYBOUNDARY----
> User-Agent: Gyazo/2.0
>
------BOUNDARYBOUNDARY----
content-disposition: form-data; name="id"

#{id}
------BOUNDARYBOUNDARY----
content-disposition: form-data; name="imagedata"; filename="gyazo.com"

#{imagedata}
------BOUNDARYBOUNDARY----

< HTTP/1.1 200 OK
< Server: nginx/1.6.2
< Date: Sat, 04 Feb 2017 10:32:56 GMT
< Content-Type: text/html; charset=utf-8
< Connection: keep-alive
< X-Content-Type-Options: nosniff
< X-Gyazo-Id: 0123456789abcdef0123456789abcdef
< X-Request-Id: 01234567-89ab-cdef-0123-456789abcdef
< X-Runtime: 0.047343
<
https://gyazo.com/0123456789abcdef0123456789abcdef
```

### Status: 400

```
> POST /upload.cgi HTTP/1.1
> Host: upload.gyazo.com
> Accept: */*
> User-Agent: Gyazo/2.0
>
< HTTP/1.1 400 Bad Request
< Server: nginx/1.6.2
< Date: Sat, 04 Feb 2017 10:32:56 GMT
< Content-Type: text/plain; charset=utf-8
< Content-Length: 13
< Connection: keep-alive
< X-Content-Type-Options: nosniff
<
No imagedata
```

## API Gateway Configuration
### Method Request
#### Model Schema (multipart/form-data)

```json
{}
```

### Integration Request
#### Body Mapping Templates (multipart/form-data)

```json
#set($inputParams = $util.base64Encode($input.path('$')))
{
  "body" : "$inputParams"
}
```

### Integration Response
#### Body Mapping Templates (text/html)

```json
$input.path('$')
```

### Method Response
#### Model Schema (text/html)

```json
{
  "$schema": "http://json-schema.org/draft-04/schema#",
  "title" : "Empty Schema",
  "type" : "object"
}
```

## License

MIT License
