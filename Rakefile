task :gyazo_upload do
  require 'net/http'
  require 'pp'

  id = 'dummy-id'
  imagedata = File.read(ENV.fetch('UPLOAD'))

  resp = Net::HTTP.start('upload.gyazo.com', 80) do |http|
    boundary = '----BOUNDARYBOUNDARY----'
    data = <<-DATA
--#{boundary}\r
content-disposition: form-data; name="id"\r
\r
#{id}\r
--#{boundary}\r
content-disposition: form-data; name="imagedata"; filename="gyazo.com"\r
\r
#{imagedata}\r
--#{boundary}--\r
    DATA
    headers = {
      'Content-Length' => data.length.to_s,
      'Content-Type' => "multipart/form-data; boundary=#{boundary}",
      'User-Agent' => 'Gyazo/2.0',
    }

    http.post('/upload.cgi', data, headers)
  end

  puts "url: #{resp.response.body}"
  puts "id: #{resp.response['X-Gyazo-Id']}"
  p resp
  pp resp.header.to_hash
end
task default: :gyazo_upload
