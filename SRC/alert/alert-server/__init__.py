import datetime
import logging
import azure.functions as func
from . import extractor
from . import chooser
from . import sender
import traceback
def main(mytimer: func.TimerRequest) -> None:
    utc_timestamp = datetime.datetime.utcnow().replace(
        tzinfo=datetime.timezone.utc).isoformat()

    if mytimer.past_due:
        logging.info('The timer is past due!')

    logging.info('Python timer trigger function ran at %s', utc_timestamp)
    try:
        ext = extractor.Extractor()
        choose = chooser.Chooser() 
        send = sender.Sender()
        alert_user = ext.get_burn_out_user()
        if len(alert_user) > 0:
            alert_user_with_deduplication = choose.get_alert_user(alert_user)
            if len(alert_user_with_deduplication) > 0:
                logging.info('Sender Start')
                send.sending_alert_users(alert_user_with_deduplication)
    except Exception as e:
        logging.error(traceback.format_list())
        logging.info('hi')
    
