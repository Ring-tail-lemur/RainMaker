import logging
class Singleton(type):
    _instances = {}
    def __call__(cls, *args, **kwargs):
        if cls not in cls._instances:
            cls._instances[cls] = super(Singleton, cls).__call__(*args, **kwargs)
        else:
            cls._instances[cls].__init__(*args, **kwargs)
        return cls._instances[cls]


class Extractor(metaclass=Singleton):
    def __init__(self):
        logging.info("Extractor")
        self.extract_burnout_user()

    def extract_burnout_user(self):
        try:
            from to_many_pr import to_many_pr
            import pandas as pd
            self.alert_user_df = pd.DataFrame(index=range(0,0), columns=['USER_ID'])
            
            pr_extractor = to_many_pr.PrExtractor()
            to_many_pr_org_owner_user = pr_extractor.get_user_above_average()
            self.alert_user_df = pd.concat([self.alert_user_df, to_many_pr_org_owner_user])
        except Exception as e:
            logging.info(e)
            raise Exception('extractor 오류')

        self.alert_user_df = self.alert_user_df.drop_duplicates()
        
    def get_burn_out_user(self):
        return self.alert_user_df