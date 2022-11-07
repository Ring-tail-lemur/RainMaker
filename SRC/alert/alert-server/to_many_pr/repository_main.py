class Singleton(type):
    _instances = {}
    def __call__(cls, *args, **kwargs):
        if cls not in cls._instances:
            cls._instances[cls] = super(Singleton, cls).__call__(*args, **kwargs)
        else:
            cls._instances[cls].__init__(*args, **kwargs)
        return cls._instances[cls]
class Repository(metaclass=Singleton):
    # def __init__(self):
        # print('repository')
    
    def get_repo_and_user_info_list(self,repo_list):
        import sys, os
        import logging
        sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))
        from ..mssql import ms_sql
        repo_list = list(map(str,repo_list))
        repo_list_str = ','.join(repo_list)
        find_burnout_user_query = """SELECT oauth_user_id AS USER_ID FROM oauth_user_repository_table WHERE repository_id IN ({})
        """.format(repo_list_str)
        db = ms_sql.MsSql()
        self.burn_out_owner_user = db.execute_pd(find_burnout_user_query)
        logging.info(self.burn_out_owner_user)
        # print(self.burn_out_owner_user)
        return self.burn_out_owner_user
        
