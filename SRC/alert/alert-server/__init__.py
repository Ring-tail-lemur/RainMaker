import datetime
import logging
from dataframesModule import dataModule
import azure.functions as func


def main(mytimer: func.TimerRequest) -> None:
    utc_timestamp = datetime.datetime.utcnow().replace(
        tzinfo=datetime.timezone.utc).isoformat()

    if mytimer.past_due:
        logging.info('The timer is past due!')

    logging.info('Python timer trigger function ran at %s', utc_timestamp)

    data_module = dataModule.DataModule()

    above_repository = data_module.get_above_average()
    print(above_repository)
    
