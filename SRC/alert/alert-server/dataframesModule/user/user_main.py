from mssql import ms_sql

db = ms_sql.MsSql()
result = db.execute("SELECT * FROM pull_request")
for row in result:
    print(row)