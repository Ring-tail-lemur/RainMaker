import logging


class Sender():
    def __init__(self):
        logging.info("Sender")
    def sending_alert_users(self, user_list):
        from .slack import slack_main
        for user in user_list:
            real_sender = slack_main.SlackSender()
            real_sender.send_slack_message_with_user_id(user)