FROM ubuntu
MAINTAINER John Norwood

# Install python stuff
RUN apt-get update
RUN apt-get install -y build-essential git
RUN apt-get install -y python python-dev python-setuptools
RUN apt-get install -y nginx supervisor
RUN easy_install pip

# # install uwsgi now because it takes a little while
RUN pip install uwsgi flask

# Copy the files over
ADD app.py /home/docker/pasteboard/
ADD etc/uwsgi.ini /home/docker/pasteboard/

# Start the app
EXPOSE 5000
WORKDIR /home/docker/pasteboard/
CMD ["python", "app.py"]
#CMD ["uwsgi", "uwsgi.ini"]
