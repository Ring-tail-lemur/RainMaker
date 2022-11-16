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
        logging.info('==============================================')
        ext = extractor.Extractor()
        logging.info('Extract Done')
        choose = chooser.Chooser() 
        send = sender.Sender()
        alert_user = ext.get_burn_out_user()
        logging.info('ALERT USER LIST')
        logging.info(alert_user)
        logging.info('==============================================')
        if len(alert_user) > 0:
            alert_user_with_deduplication = choose.get_alert_user(alert_user)
            logging.info("Chooser Done")
            logging.info("ALERT USER WITH DEDUPLICATION")
            logging.info(alert_user_with_deduplication)
            logging.info('==============================================')
            if len(alert_user_with_deduplication) > 0:
                send.sending_alert_users(alert_user_with_deduplication)
    except Exception as e:
        logging.error(traceback.format_list())
    
