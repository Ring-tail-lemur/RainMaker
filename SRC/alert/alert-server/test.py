import pandas as pd
import extractor
if __name__ == '__main__':
    ext = extractor.Extractor()
    alert_user = ext.get_burn_out_user()
    print(alert_user)
    




    