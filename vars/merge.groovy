import groovy.json.JsonSlurper

def response = "curl -s -u fabe273394:f483d5ded1fee76c1bb9f8109bb345793a1532c4 https://git.elektrobitautomotive.com/api/v3/repos/EB-EST-COS-A-4/COS_DEVOPS_SANDBOX/pulls?state=open".execute()
response_json = new JsonSlurper().parseText(response.text)
response_json.each { it ->
    pulls_url = it["url"]
    pulls_url_content = "curl -s -u fabe273394:f483d5ded1fee76c1bb9f8109bb345793a1532c4 ${pulls_url}".execute().text
    pulls_url_json = new JsonSlurper().parseText(pulls_url_content)
    if (pulls_url_json.mergeable_state == "clean") {
        println pulls_url_json.id + " is mergeable"
        merge_url = pulls_url_json.url + "/merge"
        "curl -s -u fabe273394:f483d5ded1fee76c1bb9f8109bb345793a1532c4 ${merge_url}".execute()
    }
    else {
        println "for id:" + pulls_url_json.id + " mergeable_state is " + pulls_url_json.mergeable_state
    }
}