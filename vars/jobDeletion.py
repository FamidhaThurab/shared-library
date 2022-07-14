#!/usr/bin/python -u

###############################################################################################
# To delete the jobs which were not triggered by the nightly snapshot.  #                     #
# The script filters the builds of SQT pipeline and filters them out based the causes         #
# which triggered the job and delete them if not triggered by nightly snapshot#               #
# Author: Famidha Thurab                                                                      #
# Email: famidha.begum@elektrobit.com                                                         #
###############################################################################################

import requests
import sys
import os


user = "fabe273394"
token = "119c4700e44e674faac19c776bad215a93"
job = "https://asterix2-jenkins.ebgroup.elektrobit.com/job/ASTERIX2-CT/job/Software-Qualification-Test/job/SQT-Pipeline-Hardware/job/aed2_eb_2_0/api/json"


# Deletes the job which were not triggered by nightly snapshot
def delete_job(build_url):
    #requests.post(job + "doDelete", verify=False, auth=(os.environ['CREDS_USR'], os.environ['CREDS_PSW']))
    requests.post(build_url + "doDelete", verify=False, auth=(user, token))
    print(build_url + " has been deleted")


# Gets the list of builds from the above given job
def get_builds():
    builds = requests.get(job, verify=False, auth=(user, token)).json()['builds']
    return builds


# From the above list of builds, checks for the builds
# which were not triggered by the nightly snapshots and
# calls the delete function on them
def get_causes(builds):
    for build in builds:
        build_url = build['url']
        try:
            if len(requests.get(build_url + "api/json", verify=False, auth=(user, token)).json()
                   ['actions'][0]['causes']) is not 1:
                delete_job(build_url)
        except KeyError:
            delete_job(build_url)


def main():
    get_causes(get_builds())


if __name__ == '__main__':
    sys.exit(main())
