import logging

import azure.functions as func


def main(req: func.HttpRequest) -> func.HttpResponse:
    logging.info('Python HTTP trigger function processed a request.')
    try:
        request_json = req.get_json()
        for json in request_json:
            logging.info(json)
    except Exception:
        try:
            logging.info(dir(req))
        except Exception:
            logging.info("실패")
        
    
    
    return func.HttpResponse(
                "This HTTP triggered function executed successfully. Pass a name in the query string or in the request body for a personalized response.",
                status_code=200
            )

