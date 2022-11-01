class User():
    def get_alert_user_deduplicate(self, user_list):
        import sys, os
        sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))
        from mssql import ms_sql
        import pandas as pd
        from datetime import datetime
        import datetime as dt
        now = datetime.now().strftime("%Y-%m-%d")
        day_ago = now+dt.timedelta(days=-1).strftime("%Y-%m-%d")
        db = ms_sql.MsSql()

        alert_user_today_query = """SELECT * FROM alert_log 
                        WHERE alert_type LIKE 'BurnOut' AND
                        created_date BETWEEN '{} 23:59:59.0000000'
                        AND '{} 23:59:59.0000000'
                        """.format(day_ago, now)
        
        alert_user_today_df = db.execute_pd(alert_user_today_query)

        
        