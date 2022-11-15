import pymssql
import pandas as pd
import logging
import json
class Singleton(type):
    _instances = {}
    def __call__(cls, *args, **kwargs):
        if cls not in cls._instances:
            cls._instances[cls] = super(Singleton, cls).__call__(*args, **kwargs)
        else:
            cls._instances[cls].__init__(*args, **kwargs)
        return cls._instances[cls]

class MsSql(metaclass=Singleton):
    def __init__(self):
        self.configObj = self.make_ms_config()
        self.conn = pymssql.connect(server=self.configObj.get("server"), user=self.configObj.get("user"), password=self.configObj.get("password"), database=self.configObj.get("database"), as_dict =True) 

    def execute(self, query):
        if(query[0] == 'S'):
            return self.select_query(query)
        else:
            return self.modify_query(query)
    
    def select_query(self, query):
        cursor = self.conn.cursor()
        cursor.execute(query)
        result = list()
        row = self.cursor.fetchone()
        while row:
            result.append(row)
            row = cursor.fetchone()
        return result

    def execute_pd(self,query):
        if(query[0] == 'S'):
            # print("SELECT")
            return self.select_query_df(query)
        else:
            # print("Modifying")
            return self.modify_query(query)

    def select_query_df(self, query):
        print(query)
        logging.info(query)
        cursor = self.conn.cursor()
        cursor.execute(query)
        result = cursor.fetchall()
        result_df = pd.DataFrame(result)
        return result_df

    def modify_query(self, query):
        new_conn = pymssql.connect(server=self.configObj.get("server"), user=self.configObj.get("user"), password=self.configObj.get("password"), database=self.configObj.get("database"), as_dict =True) 
        print("\"{}\"".format(query))
        logging.info(query)
        new_cursor = new_conn.cursor()
        new_cursor.execute(query)
        new_conn.commit()
        # while row:  
        #     print("Inserted Product ID : " +str(row[0]))
        #     row = new_cursor.fetchone()  
        # print('success')
        new_conn.close()
        return True

    def close(self):
        self.conn.close()

    def make_ms_config(self):
        import os
        script_dir = os.path.dirname(__file__)
        with open(os.path.join(script_dir, 'ms-sql.json')) as file:
            jsonString = json.load(file)
            return jsonString


a = MsSql()
a.execute("INSERT INTO alert_log (user_remote_id, alert_type, created_date, modified_date) VALUES (81180977, \'BURNOUT\', DEFAULT, DEFAULT)")