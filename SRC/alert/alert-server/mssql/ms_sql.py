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
        configObj = self.make_ms_config()
        self.conn = pymssql.connect(server=configObj.get("server"), user=configObj.get("user"), password=configObj.get("password"), database=configObj.get("database"), as_dict =True) 

    def execute(self, query):
        self.cursor = self.conn.cursor()
        if(query[0] == 'S'):
            return self.select_query(query)
        else:
            return self.modify_query(query)
    
    def select_query(self, query):
        self.cursor.execute(query)
        result = list()
        row = self.cursor.fetchone()
        while row:
            result.append(row)
            row = self.cursor.fetchone()
        return result

    def execute_pd(self,query):
        self.cursor = self.conn.cursor()
        if(query[0] == 'S'):
            # print("SELECT")
            return self.select_query_df(query)
        else:
            # print("Modifying")
            return self.modify_query(query)

    def select_query_df(self, query):
        print(query)
        logging.info(query)
        self.cursor.execute(query)
        result = self.cursor.fetchall()
        result_df = pd.DataFrame(result)
        return result_df

    def modify_query(self, query):
        print(query)
        logging.info(query)
        self.cursor.execute(query)
        result = list()
        row = self.cursor.fetchone()
        while row:
            result.append(row)
            row = self.cursor.fetchone()
        self.cursor.execute(query)
        self.cursor.commit()
        return result
    def close(self):
        self.conn.close()

    def make_ms_config(self):
        import os
        script_dir = os.path.dirname(__file__)
        with open(os.path.join(script_dir, 'ms-sql.json')) as file:
            jsonString = json.load(file)
            return jsonString
