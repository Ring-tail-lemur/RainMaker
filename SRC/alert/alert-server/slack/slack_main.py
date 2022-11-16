import logging
import sys, os
import requests
sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))
from mssql import ms_sql
class SlackSender():
    message = """안녕하신가요? 팀의 건강을 챙길 시간이 된 것 같습니다. \n
    Burnout의 위험이 현재 팀 내부에 존재하고 있습니다.\n
    <점검 사항>\n
    1. PR이 최근 너무 많아진 것은 아닐까요? 작업량이 최근 많아진 것 일 수도 있습니다.\n
    2. WIP가 3개 인 직원이 있나 찾아보세요. 동시에 진행중인 작업이 4개 이상이면 작업자가 정신적인 피로를 느낄 수 있습니다.\n
    3. 팀의 멤버들의 작업일이 달의 90% 이상일 수도 있어요. 휴가를 보내보시는건 어떨까요?
    """
    def __init__(self):
        logging.info('slack_sender')
    
    def send_slack_message(self,user_id):
        print('hi')
        slack_url = self.find_slack_url_with_user_id(user_id)
        if slack_url is not None:

            self.insert_log_to_db(user_id)
            print(slack_url)
            payload = {"text" : "{}".format(self.message)}
            r = requests.post(slack_url, json = payload)
            

    def find_slack_url_with_user_id(self, user_id):
        user_info_df = self.get_user_info_with_id(user_id)
        now_user_info_df = user_info_df.loc[0]['slack_url']
        logging.info(now_user_info_df)
        if now_user_info_df != None:
            return now_user_info_df
        else:
            return None

    
    def get_user_info_with_id(self, user_id):
        get_user_info_with_id_query = "SELECT * FROM oauth_user WHERE user_remote_id = {}".format(user_id)
        db = ms_sql.MsSql()
        user_info_df = db.execute_pd(get_user_info_with_id_query)
        return user_info_df

    def insert_log_to_db(self, user_id):
        insert_log_query = """INSERT INTO alert_log (user_remote_id, alert_type, created_date, modified_date) VALUES ({}, '{}', DEFAULT, DEFAULT);""".format(user_id, "BURNOUT")
        db = ms_sql.MsSql()
        result = db.execute(insert_log_query)
