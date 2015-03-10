from flask import Flask, request, abort
app = Flask(__name__)
 
responseMap = {
      'hello': 'Kasey does what he wants'
    }
  
@app.route('/api/<path:endpoint>', methods=['POST', 'PUT'])
def add_endpoint(endpoint):
  responseMap[endpoint] = request.data
  return "Endpoint {} added!\nResponse: {}\n".format(endpoint, responseMap[endpoint])
       
@app.route('/api/<path:endpoint>', methods=['DELETE'])
def del_endpoint(endpoint):
  del responseMap[endpoint]
  return "Endpoint {} deleted!\n".format(endpoint)
           
@app.route('/api/<path:endpoint>', methods=['GET'])
def get_endpoint(endpoint):
  if endpoint not in responseMap:
    abort(404)

  return responseMap[endpoint]
             

def main():
  app.run(host="0.0.0.0", port=5000)

if __name__ == '__main__':
  main()
