import logging
class User():
    def get_alert_user_deduplicate(self, user_list):
        import sys, os
        sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))
        from mssql import ms_sql
        import pandas as pd
        from datetime import datetime
        import datetime as dt
        now = datetime.now().strftime("%Y-%m-%d")
        day_ago = (datetime.now()+dt.timedelta(days=-1)).strftime("%Y-%m-%d")
        db = ms_sql.MsSql()
        user_list = [str(item[0]) for item in user_list]
        # print(user_list)
        user_list_joined = ','.join(user_list)
        alert_user_today_query = """SELECT user_remote_id AS USER_ID FROM alert_log 
                        WHERE alert_type LIKE 'BurnOut' AND
                        created_date BETWEEN '{} 23:59:59.0000000'
                        AND '{} 23:59:59.0000000' AND user_remote_id IN ({})
                        """.format(day_ago, now, user_list_joined)
        
        
        alert_user_today_df = db.execute_pd(alert_user_today_query)
        logging.WARNING('user.py')
        logging.WARNING(alert_user_today_df)
        for idx in alert_user_today_df.index:
            user_list.remove(alert_user_today_df[idx,'USER_ID'])
        return user_list

        
        