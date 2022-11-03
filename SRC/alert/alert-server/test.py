import pandas as pd
import extractor
import chooser
if __name__ == '__main__':
    ext = extractor.Extractor()
    chooser = chooser.Chooser() 
    alert_user = ext.get_burn_out_user()
    alert_user_with_deduplication = chooser.get_alert_user(alert_user)
    print(alert_user_with_deduplication)




    