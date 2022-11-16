import logging


class Sender():
    def sending_alert_users(self, user_list):
        from .slack import slack_main
        logging.info("Send!")
        for user in user_list:
            real_sender = slack_main.SlackSender()
            real_sender.send_slack_message(user)