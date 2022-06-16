package GroovyTraining_Task_1

import groovy.json.JsonSlurper

def input_file() {
    print "Enter the path to the JSON file:  "
    def JSONFILE_Path = System.in.newReader().readLine() // /home/fabe273394/Downloads/av_dxp_android11-verify_1471.json
    println "Json file is at " + JSONFILE_Path

    print "Enter the path to the CSV file:  "
    def CSVFILE_Path = System.in.newReader().readLine() //  /home/fabe273394/Downloads/result.csv
    println "Json file is at " + CSVFILE_Path

    file_creation(JSONFILE_Path, CSVFILE_Path)
}

def file_creation(JSONFILE_Path, CSVFILE_Path) {
    def JSONFILE = new File(JSONFILE_Path)

    file_check(JSONFILE)

    def slurper = new JsonSlurper()
    def jsonFile = slurper.parse(JSONFILE)

    def csvFile = new File(CSVFILE_Path)

    parse_csv(jsonFile, csvFile)
}

def file_check(JSONFILE) {
    try {
        JSONFILE.isFile()
    } catch (FileNotFoundException e) {
        println("Exception: ${e}")
    }
}

def parse_csv(jsonFile, csvFile) {
    def keys = jsonFile.keySet()
    keys.each { key ->
        if (key != '') {
            for (i = 0; i <= jsonFile.get(key).size()-1; i++) {
                def rows = jsonFile.get(key)[i].toSpreadMap().values()
                if (csvFile.length() == 0) {
                    csvFile.append('Name,')
                    def columns = jsonFile.get(key)[i].toSpreadMap().keySet()
                    columns.each { column ->
                        csvFile.append(column + ',')
                    }
                    csvFile.append("\n")
                    csvFile.append(key + ',')
                    rows.each { row ->
                        csvFile.append(row + ',')
                    }
                    csvFile.append("\n")
                }
                else {
                    csvFile.append(key + ',')
                    rows.each { row ->
                        csvFile.append(row + ',')
                    }
                    csvFile.append("\n")
                }
            }
        }
    }
}

input_file()

