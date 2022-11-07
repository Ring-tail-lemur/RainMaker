import datetime
import logging
import azure.functions as func
from . import extractor
from . import chooser
def main(mytimer: func.TimerRequest) -> None:
    utc_timestamp = datetime.datetime.utcnow().replace(
        tzinfo=datetime.timezone.utc).isoformat()

    if mytimer.past_due:
        logging.info('The timer is past due!')

    logging.info('Python timer trigger function ran at %s', utc_timestamp)
    try:
        ext = extractor.Extractor()
        choose = chooser.Chooser() 
        alert_user = ext.get_burn_out_user()
        if len(alert_user) > 0:
            alert_user_with_deduplication = choose.get_alert_user(alert_user)
            if len(alert_user_with_deduplication) > 0:
                print("",alert_user_with_deduplication)
                logging.info("이 유저들에게 알림 보내야함~",alert_user_with_deduplication)
    except Exception as e:
        logging.error(e)
        logging.info('hi')
    
