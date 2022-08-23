const axios = require('axios');

async function getDoraMetricsbyAxios(start_time, end_time, repo, url) {

    console.log("url = ", url);
    const Message = await axios.get(url, {
        params : {
            start_time : start_time,
            end_time : end_time,
            repo_id : repo
        }
    })

    let info;
    if (Message.data.leadTimeForChangeMap) info = Message.data.leadTimeForChangeMap;
    if (Message.data.deploymentFrequencyMap) info = Message.data.deploymentFrequencyMap;
    if (Message.data.changeFailureRateMap) info = Message.data.changeFailureRateMap;
    if (Message.data.timeToRestoreServiceMap) info = Message.data.timeToRestoreServiceMap;

    console.log("================================== Message\n", Message.data);
    let date_arr = [];
    let average_time = [];

    const st = new Date(start_time);
    const en = new Date(end_time);

    console.log("info: ",info);
    while(st.getDate() <= en.getDate()) {
        date_arr.push(dateFormat(st));
        if( info[dateFormat(st)] ) {
            average_time.push(info[dateFormat(st)]);
        } else {
            average_time.push(0);
        }
        st.setDate(st.getDate() + 1);
    }


    const testMessage =
        [
            {
                start_time : Message.data.start_time,
                end_time : Message.data.end_time,
                level : Message.data.level,
                average_time_data : average_time
            }
        ]

    console.log("111111111111111111111111 testMsg : " , testMessage, date_arr);
    return [testMessage, date_arr];
}

function dateFormat(date) {

    let month = date.getMonth() + 1;
    let day = date.getDate();

    month = month >= 10 ? month : '0' + month;
    day = day >= 10 ? day : '0' + day;

    return date.getFullYear() + '-' + month + '-' + day;
}

module.exports.getDoraMetricsbyAxios = getDoraMetricsbyAxios;