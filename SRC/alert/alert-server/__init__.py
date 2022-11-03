

def main(mytimer: func.TimerRequest) -> None:
    try:
        import datetime
        import logging
        from azure import function as func
        import extractor
        from . import chooser
    except:
        print('hi')
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
    
