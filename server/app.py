from flask import Flask, request, abort
import os

app = Flask(__name__)
base_directory = "data"
base_filepath = "./" + base_directory + "/"

@app.route('/api/<path:endpoint>', methods=['POST', 'PUT'])             
def upsert_endpoint(endpoint):
  print("yup here")
  data_file = base_filepath + endpoint + "/data.txt"
  print(data_file)
  if os.path.exists(base_filepath + endpoint) and os.path.exists(data_file):
    f = open(data_file, "w")
    f.seek(0)
    f.write(request.data)
    f.truncate()
    f.close()
    return "Endpoint {} updated!\nResponse: {}\n".format(endpoint, request.data)
  elif os.path.exists(base_filepath + endpoint) and not os.path.exists(data_file):
    # children endpoints already exist, creating new file
    f = open(data_file, "w")
    f.write(request.data)
    f.close()
    return "Endpoint {} added!\nResponse: {}\n".format(endpoint, request.data)
  else:
    os.makedirs(base_filepath + endpoint)
    f = open(data_file, "w")
    f.write(request.data)
    f.close()
    return "Endpoint {} added!\nResponse: {}\n".format(endpoint, request.data)

@app.route('/api/<path:endpoint>', methods=['DELETE'])
def delete_endpoint(endpoint):
  data_file = base_filepath + endpoint + "/data.txt"
  if os.path.exists(data_file):
    os.remove(data_file)
    for dirpath, dirnames, files in os.walk(base_filepath + endpoint):
      if not dirnames:
        os.rmdir(base_filepath + endpoint)
      return "Endpoint {} deleted!\n".format(endpoint)
  else:
    abort(404)

@app.route('/api/<path:endpoint>', methods=['GET'])
def get_endpoint(endpoint):
  data_file = base_filepath + endpoint + "/data.txt"
  if os.path.exists(data_file):
    f = open(data_file, "r")
    data = f.read()
    f.close()
    return data
  else:
    abort(404)

def main():
  app.run(host="0.0.0.0", port=5000, debug=True)

if __name__ == '__main__':
  main()


