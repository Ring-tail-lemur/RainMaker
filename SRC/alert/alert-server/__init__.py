import datetime
import logging
import azure.functions as func
import extractor
from . import chooser

def main(mytimer: func.TimerRequest) -> None:
    utc_timestamp = datetime.datetime.utcnow().replace(
        tzinfo=datetime.timezone.utc).isoformat()

    if mytimer.past_due:
        logging.info('The timer is past due!')

    logging.info('Python timer trigger function ran at %s', utc_timestamp)

    ext = extractor.Extractor()
    choose = chooser.Chooser() 
    alert_user = ext.get_burn_out_user()
    alert_user_with_deduplication = choose.get_alert_user(alert_user)
    print("",alert_user_with_deduplication)
    
