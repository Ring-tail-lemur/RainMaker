class Singleton(type):
    _instances = {}
    def __call__(cls, *args, **kwargs):
        if cls not in cls._instances:
            cls._instances[cls] = super(Singleton, cls).__call__(*args, **kwargs)
        else:
            cls._instances[cls].__init__(*args, **kwargs)
        return cls._instances[cls]

class Chooser(metaclass=Singleton):
    def __init__(self):
        print("Choose alert User")
    def get_alert_user(self, user_df):
        import pandas as pd
        user_list = user_df.values.tolist()
        
