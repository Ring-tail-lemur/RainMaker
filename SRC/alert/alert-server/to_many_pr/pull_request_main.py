class Singleton(type):
    _instances = {}
    def __call__(cls, *args, **kwargs):
        if cls not in cls._instances:
            cls._instances[cls] = super(Singleton, cls).__call__(*args, **kwargs)
        else:
            cls._instances[cls].__init__(*args, **kwargs)
        return cls._instances[cls]



class PullRequest(metaclass=Singleton):
    pull_request_in_month = None
    def __init__(self):
        self.set_pull_request_df()
    
    def get_pull_request(self):
        return self.pull_request_in_month

    def set_pull_request_df(self):
        import sys, os
        sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))
        from __app__.mssql import ms_sql
        import pandas as pd
        from datetime import datetime
        import datetime as dt
        now = datetime.now()
        week_ago = now+dt.timedelta(weeks=-4)
        now = now.strftime("%Y-%m-%d")
        week_ago = week_ago.strftime("%Y-%m-%d")
        pull_request_in_month_query = """SELECT * FROM pull_request 
                    WHERE created_date BETWEEN '{} 00:00:00.0000000'
                    AND '{} 11:59:59.0000000'
                """.format(week_ago, now)
        db = ms_sql.MsSql()
        pull_request_in_month = db.execute_pd(pull_request_in_month_query)
        self.pull_request_in_month = pull_request_in_month
    
    def get_repository_id_list(self):
        repository_list = self.pull_request_in_month.drop_duplicates(['repository_id'])
        return repository_list['repository_id'].values.tolist()
