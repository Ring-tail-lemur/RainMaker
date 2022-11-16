import extractor
import chooser
import sender
if __name__ == "__main__":
    try:
        ext = extractor.Extractor()
        choose = chooser.Chooser() 
        alert_user = ext.get_burn_out_user()
        alert_user_with_deduplication = choose.get_alert_user(alert_user)
        print("",alert_user_with_deduplication)
        sender.send(alert_user_with_deduplication)
    except Exception as e:
        print(e)
        print('hi')