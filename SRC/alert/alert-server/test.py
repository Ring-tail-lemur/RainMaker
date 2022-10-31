from mssql import ms_sql
from dataframesModule import dataModule
import pandas as pd
if __name__ == '__main__':
    data_module = dataModule.DataModule()
    burn_out_repository = pd.DataFrame(columns=['repository_id'])
    
    above_repository = data_module.get_above_average()
    burn_out_repository = pd.concat([burn_out_repository, above_repository])

    




    