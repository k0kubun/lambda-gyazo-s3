task :gyazo_upload do
  require 'net/http'
  require 'json'
  require 'base64'
  require 'pry'
  require 'pp'

  class GyazoClient
    def initialize(host:, port:, upload_path:)
      @host = host
      @port = port
      @upload_path = upload_path
    end

    # @return [Net::HTTPResponse]
    def upload(gyazo_id:, image_data:, use_json:)
      if use_json
        upload_with_json(gyazo_id, image_data)
      else
        upload_with_multipart(gyazo_id, image_data)
      end
    end

    private

    def upload_with_json(gyazo_id, image_data)
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
      post(@upload_path, data, headers)
    end

    def upload_with_multipart(gyazo_id, image_data)
      boundary = '----BOUNDARYBOUNDARY----'
      data = <<~DATA
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
      post(@upload_path, data, headers)
    end

    def post(path, data, headers = {})
      Net::HTTP.start(@host, @port, use_ssl: @port == 443) do |http|
        http.post(path, data, headers)
      end
    end
  end

  module ResponseFormatter
    class << self
      def format(resp)
        result = []
        result << "Response Body: #{resp.response.body}"
        result << "X-Gyazo-Id: #{resp.response['X-Gyazo-Id'].inspect}"
        result << resp.inspect
        result << pp_str(resp.header.to_hash)
        result.join("\n")
      end

      private

      def pp_str(obj)
        io = StringIO.new('', 'r+')
        PP.pp(obj, io)
        io.rewind
        Pry.Code(io.read).highlighted
      end
    end
  end

  params = { host: 'upload.gyazo.com', port: 80, upload_path: '/upload.cgi' }
  if ENV['API_ID']
    params.merge!(
      host: "#{ENV['API_ID']}.execute-api.ap-northeast-1.amazonaws.com",
      port: 443,
      upload_path: '/prod/gyazo',
    )
  end

  resp = GyazoClient.new(params).upload(
    gyazo_id:   'dummy-id',
    image_data: File.read(ENV.fetch('UPLOAD')),
    use_json:   ENV.key?('API_ID'),
  )
  puts ResponseFormatter.format(resp)
end
task default: :gyazo_upload
