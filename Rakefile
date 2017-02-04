task :gyazo_upload do
  require 'net/http'
  require 'pp'
  require 'json'
  require 'base64'

  gyazo_id = 'dummy-id'
  image_data = File.read(ENV.fetch('UPLOAD'))
  if ENV['API_ID']
    host = "#{ENV['API_ID']}.execute-api.ap-northeast-1.amazonaws.com"
    port = 443
    path = '/prod/gyazo'

    # Ideally we shouldn't use JSON to post binary. Using JSON and sending Base64-encoded
    # binary in it because Lambda's Input mapping is too difficult to configure :(
    data = {
      id: gyazo_id,
      imagedata: Base64.strict_encode64(image_data),
    }.to_json
    headers = {
      'Content-Type' => 'application/json',
      'User-Agent' => 'Gyazo/2.0',
    }
  else
    host = 'upload.gyazo.com'
    port = 80
    path = '/upload.cgi'

    boundary = '----BOUNDARYBOUNDARY----'
    data = <<-DATA
--#{boundary}\r
content-disposition: form-data; name="id"\r
\r
#{gyazo_id}\r
--#{boundary}\r
content-disposition: form-data; name="imagedata"; filename="gyazo.com"\r
\r
#{image_data}\r
--#{boundary}--\r
    DATA
    headers = {
      'Content-Length' => data.length.to_s,
      'Content-Type' => "multipart/form-data; boundary=#{boundary}",
      'User-Agent' => 'Gyazo/2.0',
    }
  end

  resp = Net::HTTP.start(host, port, use_ssl: port == 443) do |http|
    http.post(path, data, headers)
  end

  puts "Response Body: #{resp.response.body}"
  puts "X-Gyazo-Id: #{resp.response['X-Gyazo-Id'].inspect}"
  p resp
  pp resp.header.to_hash
end
task default: :gyazo_upload
