#! /bin/sh

scp -i Desktop/aws.pem IdeaProjects/debty-api/build/libs/debty-0.0.1-SNAPSHOT.jar ubuntu@ec2-3-93-154-97.compute-1.amazonaws.com:/home/ubuntu
