import logging

class SlackSender():

    def __init__(self):
        logging.info('slack_sender')
    
    def send_slack_message_with_user_id(self, user_id):
        user_info_df = self.get_user_info_with_id(user_id)
        now_user_info_df = user_info_df.loc[0]
        logging.info(now_user_info_df)

    
    def get_user_info_with_id(self, user_id):
        from ..mssql import ms_sql
        get_user_info_with_id_query = "SELECT * FROM oauth_user WHERE user_id = {}".format(user_id)
        db = ms_sql.MsSql()
        user_info_df = db.execute_pd(get_user_info_with_id_query)
        return user_info_df