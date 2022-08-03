import logging
from pull_request_closed import pullRequestClosed

def pullRequestMain(inputJson, outputDict):
    
    outputDict["action"] = inputJson["action"]
    if(outputDict["action"] == "opened"):
        logging.info("pullRequest opened.")
    elif(outputDict["action"] == "closed"):
        logging.info("pullRequest closed.")
        pullRequestClosed(inputJson, outputDict)
    else:
        logging.info(f"""pullRequest {outputDict["action"]}""")